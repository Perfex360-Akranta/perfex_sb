package com.akranta.perfex_sb.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.BalWorespDtlSaveDto;
import com.akranta.perfex_sb.dto.BalWorespMstSaveDto;
import com.akranta.perfex_sb.service.BalWorespService;

@Service
public class BalWorespServiceImpl implements BalWorespService {

    private static final Logger logger = LoggerFactory.getLogger(BalWorespServiceImpl.class);

    @Autowired
    private DataSource dataSource;

    private static final int KEY_LENGTH = 15;
    private static final String DATE_FORMAT = "MMYY";

    @Override
    public BalWorespMstSaveDto create(BalWorespMstSaveDto dto) throws Exception {

        List<String> sqls = new ArrayList<>();
    List<BalWorespDtlSaveDto> workRespdtlList = dto.getWoRespDetail();

    logger.debug("Size List: {}", workRespdtlList == null ? 0 : workRespdtlList.size());

    boolean masterAlreadyHandled = false;   // 👈 NEW — tracks master insert/update across the whole loop
    String masterKeyIdForThisRequest = dto.getPwrmKeyid();   // in case it's an update (existing master)

    if (workRespdtlList != null && !workRespdtlList.isEmpty()) {

        for (BalWorespDtlSaveDto workRespDtl : workRespdtlList) {

            String newDtlKeyId = getSequenceNumber("BAL_PLM_TL_WORESPDTL", KEY_LENGTH, "PWR", DATE_FORMAT);
            workRespDtl.setPwrdKeyid(newDtlKeyId);

            // Stage 2 — insert-or-update master, but ONLY ONCE per request
            if (!masterAlreadyHandled) {
                if (masterKeyIdForThisRequest == null || masterKeyIdForThisRequest.trim().isEmpty()) {
                    masterKeyIdForThisRequest = getSequenceNumber("BAL_PLM_TL_WORESPMST", KEY_LENGTH, "PRM", DATE_FORMAT);
                    dto.setPwrmKeyid(masterKeyIdForThisRequest);
                    sqls.add(buildMasterInsertSql(dto));
                } else {
                    sqls.add(buildMasterUpdateSql(dto, masterKeyIdForThisRequest));
                }
                masterAlreadyHandled = true;   // 👈 block every subsequent iteration from repeating this
            }

            workRespDtl.setPwrdMasterid(masterKeyIdForThisRequest);   // every detail row points to the SAME master

                // Stage 3 — check if this machine is "new" to WORESPMST (EXCEPT query)
                String machineCheckSql =
                    "SELECT CELL_FACTORYID, CELL_SECTIONID, MCHM_CELLID, MCHM_KEYID FROM " +
                    "GEN_TL_MACHINEMST, GEN_TL_CELLMST WHERE MCHM_ACTIVE = 'Y' " +
                    "AND MCHM_CELLID = CELL_KEYID AND MCHM_KEYID = '" + dto.getPwrmMachineid() + "' EXCEPT " +
                    "SELECT PWRM_FACTORYID, PWRM_SECTIONID, PWRM_CELLID, PWRM_MACHINEID FROM BAL_PLM_TL_WORESPMST " +
                    "WHERE 1 = 1 AND PWRM_MACHINEID = '" + dto.getPwrmMachineid() + "'";

                List<String[]> getMachExists = getDataList(machineCheckSql);
                logger.debug("machineCheckSql: {}", machineCheckSql);

                if (!getMachExists.isEmpty()) {
                    String machineLevelSql =
                        "SELECT PWRM_FACTORYID, PWRM_SECTIONID, PWRM_CELLID, PWRM_MACHINEID, PWRM_KEYID " +
                        "FROM BAL_PLM_TL_WORESPMST WHERE 1 = 1 AND PWRM_MACHINEID = '" + dto.getPwrmMachineid() +
                        "' AND PWRM_LEVEL = 'M'";

                    List<String[]> getMachavail = getDataList(machineLevelSql);
                    logger.debug("machineLevelSql: {}", machineLevelSql);

                    if (!getMachavail.isEmpty()) {
                        String sqlUpdtPm;
                        String sqlUpdtCal;

                        if ("N".equals(dto.getPwrmTradewise())) {
                            sqlUpdtPm = "UPDATE BAL_PLM_TL_STANDARDS SET PMSD_PREPAREDBYID = '" + workRespDtl.getPwrdEmpid() +
                                        "' WHERE 1 = 1 AND PMSD_MACHINEID = '" + dto.getPwrmMachineid() + "'";
                            sqlUpdtCal = "UPDATE BAL_PLM_TL_CALENDAR SET PMCL_RESPONSIBILITY = '" + workRespDtl.getPwrdEmpid() +
                                         "' WHERE 1 = 1 AND PMCL_MACHINEID = '" + dto.getPwrmMachineid() + "' AND PMCL_STATUS = 'X'";
                        } else {
                            sqlUpdtPm = "UPDATE BAL_PLM_TL_STANDARDS SET PMSD_PREPAREDBYID = '" + workRespDtl.getPwrdEmpid() +
                                        "' WHERE PMSD_TRADEID = '" + workRespDtl.getPwrdTradeid() +
                                        "' AND PMSD_MACHINEID = '" + dto.getPwrmMachineid() + "'";
                            sqlUpdtCal = "UPDATE BAL_PLM_TL_CALENDAR SET PMCL_RESPONSIBILITY = '" + workRespDtl.getPwrdEmpid() +
                                         "' WHERE PMCL_TRADEID = '" + workRespDtl.getPwrdTradeid() +
                                         "' AND PMCL_MACHINEID = '" + dto.getPwrmMachineid() + "' AND PMCL_STATUS = 'X'";
                        }
                        sqls.add(sqlUpdtPm);
                        sqls.add(sqlUpdtCal);
                    }
                }

                // Stage 4 — always insert the detail row
                sqls.add(buildDetailInsertSql(workRespDtl));
            }
        }

        executeStatements(sqls);

        return dto;
    }

    // ===================== SQL BUILDERS =====================

    private String buildMasterInsertSql(BalWorespMstSaveDto dto) {
        return "INSERT INTO BAL_PLM_TL_WORESPMST " +
            "(PWRM_KEYID, PWRM_FACTORYID, PWRM_SECTIONID, PWRM_CELLID, PWRM_MACHINEID, PWRM_LEVEL, " +
            "PWRM_TRADEWISE, PWRM_GENERAL, PWRM_TEMPFIELD1, PWRM_TEMPFIELD2, PWRM_TEMPFIELD3, PWRM_TEMPFIELD4, " +
            "PWRM_ACTIVE, PWRM_CREATEDBY, PWRM_CREATEDON, PWRM_MODIFIEDON) VALUES (" +
            "'" + dto.getPwrmKeyid() + "', " +
            nullableStr(dto.getPwrmFactoryid()) + ", " +
            "'" + dto.getPwrmSectionid() + "', " +
            "'" + dto.getPwrmCellid() + "', " +
            "'" + dto.getPwrmMachineid() + "', " +
            "'" + dto.getPwrmLevel() + "', " +
            "'" + dto.getPwrmTradewise() + "', " +
            "'" + safe(dto.getPwrmGeneral(), "N") + "', " +
            "'" + safe(dto.getPwrmTempfield1(), "") + "', " +
            "'" + safe(dto.getPwrmTempfield2(), "") + "', " +
            "'" + safe(dto.getPwrmTempfield3(), "") + "', " +
            "'" + safe(dto.getPwrmTempfield4(), "") + "', " +
            "'" + safe(dto.getPwrmActive(), "Y") + "', " +
            "'" + safe(dto.getPwrmCreatedby(), "SYSTEM") + "', " +
            "CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
    }

    private String buildMasterUpdateSql(BalWorespMstSaveDto dto, String masterKeyId) {
        return "UPDATE BAL_PLM_TL_WORESPMST SET " +
            "PWRM_FACTORYID = " + nullableStr(dto.getPwrmFactoryid()) + ", " +
            "PWRM_SECTIONID = '" + dto.getPwrmSectionid() + "', " +
            "PWRM_CELLID = '" + dto.getPwrmCellid() + "', " +
            "PWRM_MACHINEID = '" + dto.getPwrmMachineid() + "', " +
            "PWRM_LEVEL = '" + dto.getPwrmLevel() + "', " +
            "PWRM_TRADEWISE = '" + dto.getPwrmTradewise() + "', " +
            "PWRM_GENERAL = '" + safe(dto.getPwrmGeneral(), "N") + "', " +
            "PWRM_MODIFIEDON = CURRENT_TIMESTAMP " +
            "WHERE PWRM_KEYID = '" + masterKeyId + "'";
    }

    private String buildDetailInsertSql(BalWorespDtlSaveDto dtl) {
        return "INSERT INTO BAL_PLM_TL_WORESPDTL " +
            "(PWRD_KEYID, PWRD_MASTERID, PWRD_TRADEID, PWRD_EMPID, PWRD_EFFECTFROM, PWRD_EFFECTTILL, " +
            "PWRD_TEMPFIELD1, PWRD_TEMPFIELD2, PWRD_TEMPFIELD3, PWRD_TEMPFIELD4, " +
            "PWRD_ACTIVE, PWRD_CREATEDBY, PWRD_CREATEDON, PWRD_MODIFIEDON) VALUES (" +
            "'" + dtl.getPwrdKeyid() + "', " +
            "'" + dtl.getPwrdMasterid() + "', " +
            "'" + dtl.getPwrdTradeid() + "', " +
            "'" + dtl.getPwrdEmpid() + "', " +
            dateVal(dtl.getPwrdEffectfrom()) + ", " +
            dateVal(dtl.getPwrdEffecttill()) + ", " +
            "'" + safe(dtl.getPwrdTempfield1(), "") + "', " +
            "'" + safe(dtl.getPwrdTempfield2(), "") + "', " +
            "'" + safe(dtl.getPwrdTempfield3(), "") + "', " +
            "'" + safe(dtl.getPwrdTempfield4(), "") + "', " +
            "'" + safe(dtl.getPwrdActive(), "Y") + "', " +
            "'" + safe(dtl.getPwrdCreatedby(), "SYSTEM") + "', " +
            "CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
    }

    private String dateVal(String value) {
    if (value == null || value.isBlank() || "{}".equals(value)) {
        return "CURRENT_TIMESTAMP";
    }
    String v = value.trim();

    if (v.matches("\\d{2}-[A-Za-z]{3}-\\d{4} \\d{2}:\\d{2}:\\d{2}")) {
        // e.g. "05-Sep-2026 09:57:35"
        return "TO_TIMESTAMP('" + v + "','DD-Mon-YYYY HH24:MI:SS')";
    }
    if (v.matches("\\d{2}-[A-Za-z]{3}-\\d{4}")) {
        // e.g. "31-Dec-2100"
        return "TO_TIMESTAMP('" + v + "','DD-Mon-YYYY')";
    }
    if (v.matches("\\d{2}-\\d{2}-\\d{4}")) {
        // e.g. "01-01-2026" (numeric month, from Plan Configuration flow)
        return "TO_TIMESTAMP('" + v + "','DD-MM-YYYY')";
    }
    return "CURRENT_TIMESTAMP";
}

    private String nullableStr(String value) {
        return (value == null || value.isBlank()) ? "NULL" : "'" + value + "'";
    }

    private String safe(String value, String defaultVal) {
        return (value == null || value.isBlank()) ? defaultVal : value;
    }

    // ===================== LOCAL JDBC HELPERS (same pattern as BalPlanConfigurationServiceImpl) =====================

    private String getSequenceNumber(String tableName, int keyLength, String preFix, String dateFormat) throws Exception {
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = dataSource.getConnection();
            String procName = "{ ? = call RUNSEQUENCE(?,?,?,?,?) }";
            stmt = connection.prepareCall(procName);
            stmt.setString(2, tableName);
            stmt.setInt(3, keyLength);
            stmt.setString(4, preFix);
            stmt.setString(5, dateFormat);
            stmt.setString(6, null);
            stmt.registerOutParameter(1, Types.VARCHAR);
            stmt.execute();
            return stmt.getString(1);
        } catch (SQLException e) {
            logger.error("SQL Exception in sequence generation: {}", e.getMessage(), e);
            throw new Exception("Sequence No. Generation SQL Error: " + e.getMessage());
        } finally {
            closeConnection(null, null, stmt, null, connection);
        }
    }

    private List<String[]> getDataList(String sql) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String[]> resultList = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                String[] row = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = resultSet.getString(i + 1);
                }
                resultList.add(row);
            }
            return resultList;
        } catch (SQLException e) {
            logger.error("SQL Exception in getDataList: {}", e.getMessage(), e);
            throw new Exception("getDataList SQL Error: " + e.getMessage());
        } finally {
            closeConnection(resultSet, null, null, statement, connection);
        }
    }

    private void executeStatements(List<String> sqls) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (String sqlStmt : sqls) {
                logger.debug("Executing statement: {}", sqlStmt);
                statement.addBatch(sqlStmt);
            }
            statement.executeBatch();
            connection.commit();
            logger.info("Executed {} statements successfully", sqls.size());
        } catch (SQLException e) {
            logger.error("SQL Exception in executeStatements: {}", e.getMessage(), e);
            if (connection != null) {
                try { connection.rollback(); } catch (SQLException rollbackEx) {
                    logger.error("Rollback failed: {}", rollbackEx.getMessage(), rollbackEx);
                }
            }
            throw new Exception("executeStatements SQL Error: " + e.getMessage());
        } finally {
            if (connection != null) {
                try { connection.setAutoCommit(true); } catch (SQLException ignore) {}
            }
            closeConnection(null, null, null, statement, connection);
        }
    }

    private void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement,
                                 CallableStatement callableStatement, Statement statement, Connection connection) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (callableStatement != null) callableStatement.close();
            if (statement != null) statement.close();
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            logger.error("Error closing database connections: {}", e.getMessage(), e);
        }
    }

    //update
    @Override
public BalWorespMstSaveDto update(BalWorespMstSaveDto dto) throws Exception {

    List<String> sqls = new ArrayList<>();
    List<BalWorespDtlSaveDto> workRespdtlList = dto.getWoRespDetail();

    logger.debug("worresp Detail in update: {}", workRespdtlList == null ? 0 : workRespdtlList.size());

    if (workRespdtlList != null && !workRespdtlList.isEmpty()) {

        for (BalWorespDtlSaveDto workRespDtl : workRespdtlList) {

            workRespDtl.setPwrdMasterid(dto.getPwrmKeyid());

            String sqltrade = "select PWRD_TRADEID from BAL_PLM_TL_WORESPDTL where PWRD_MASTERID = '"
                    + workRespDtl.getPwrdMasterid() + "'";
            List<String[]> pmWorkTradeId = getDataList(sqltrade);

            String newId = workRespDtl.getPwrdTradeid();

            for (String[] row : pmWorkTradeId) {
                String existingTradeId = row[0];
                if (existingTradeId != null && (existingTradeId.equals(newId) || "{}".equals(newId))) {
                    sqls.add("DELETE FROM BAL_PLM_TL_WORESPDTL WHERE PWRD_MASTERID = '"
                            + dto.getPwrmKeyid() + "' AND PWRD_TRADEID = '" + existingTradeId + "'");
                }
            }

            String newDtlKeyId = getSequenceNumber("BAL_PLM_TL_WORESPDTL", KEY_LENGTH, "PWR", DATE_FORMAT);
            workRespDtl.setPwrdKeyid(newDtlKeyId);

            sqls.add(buildDetailInsertSql(workRespDtl));
        }

        executeStatements(sqls);
    }

    return dto;
}
}