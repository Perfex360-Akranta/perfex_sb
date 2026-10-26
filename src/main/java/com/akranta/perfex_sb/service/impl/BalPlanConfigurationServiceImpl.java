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

import com.akranta.perfex_sb.dto.NextDueDateDTO;
import com.akranta.perfex_sb.dto.BalPlanConfigurationSaveDto;
import com.akranta.perfex_sb.repository.BalPlanConfigurationRepository;
import com.akranta.perfex_sb.service.BalPlanConfigurationService;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class BalPlanConfigurationServiceImpl implements BalPlanConfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(BalPlanConfigurationServiceImpl.class);

    @Autowired
    private BalPlanConfigurationRepository repository;

    @Autowired
    private DataSource dataSource;   

    private static final String SEQ_IDENTIFIER = "BAL_PLM_TL_PLANCONFIGURATION";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX = "PPC";
    private static final String DATE_FORMAT = "MMYY";

    @Override
    public List<NextDueDateDTO> getNextDueDates() {
        List<Object[]> rawResults = repository.findNextDueDatesRaw();
        List<NextDueDateDTO> result = new ArrayList<>();

        for (Object[] row : rawResults) {
            String freq = (String) row[0];
            String nextDueDate = (String) row[1];
            result.add(new NextDueDateDTO(freq, nextDueDate));
        }
        return result;
    }

    //save
    @Override
    public BalPlanConfigurationSaveDto savePlanConfiguration(BalPlanConfigurationSaveDto dto) throws Exception {

        List<String> sqls = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        // Stage 1 — delete old config rows for this level
        if ("A".equals(dto.getPplcLevel())) {
            sql.append("DELETE FROM BAL_PLM_TL_PLANCONFIGURATION ")
               .append("WHERE PPLC_MACHINEID IN ('").append(dto.getPplcMachineid()).append("') AND ")
               .append("PPLC_ASSEMBLYID <> '{}' AND PPLC_LEVEL ='A'");
        } else if ("M".equals(dto.getPplcLevel())) {
            sql.append("DELETE FROM BAL_PLM_TL_PLANCONFIGURATION ")
               .append("WHERE PPLC_MACHINEID IN ('").append(dto.getPplcMachineid()).append("') AND ")
               .append("PPLC_ASSEMBLYID = '{}' AND PPLC_LEVEL ='M'");
        }
        logger.debug("DeleteData.. : {}", sql);
        sqls.add(sql.toString());

        // Stage 2 — generate sequence + build insert
        try {
            String newKeyId = getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, null);
            if (newKeyId == null || newKeyId.trim().isEmpty()) {
                throw new IllegalStateException("Failed to generate Plan Configuration Key ID - sequence returned null");
            }
            dto.setPplcKeyid(newKeyId);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate Plan Configuration Key ID: " + e.getMessage(), e);
        }

        String insertSql = buildInsertSql(dto);   // ⚠️ placeholder — needs real column list
        sqls.add(insertSql);

        // Stage 3 — get distinct frequencies for this machine
        StringBuilder sqlPm = new StringBuilder(
            "SELECT DISTINCT PMSD_FREQUENCYUNIT,PMSD_FREQUENCY FROM BAL_PLM_TL_STANDARDS WHERE PMSD_MACHINEID = '")
            .append(dto.getPplcMachineid()).append("'");

        List<String[]> frequencies;
        try {
            frequencies = getDataList(sqlPm.toString());

            for (String[] frequency : frequencies) {
                String freqUnit = frequency[0];
                String freq = frequency[1];

                if (ValidationUtil.isValidKeyId(freqUnit)) {
                    String getEffdate;

                    if (freqUnit.equals("W") || freqUnit.equals("F") || freqUnit.equals("M")) {
                        getEffdate = dto.getPplcMonthly();
                    } else if (freqUnit.equals("Q")) {
                        getEffdate = dto.getPplcQuarterly();
                    } else if (freqUnit.equals("H")) {
                        getEffdate = dto.getPplcHalfyearly();
                    } else if (freqUnit.equals("Y") && ValidationUtil.isValidKeyId(freq)) {
                        switch (Integer.parseInt(freq)) {
                            case 2: getEffdate = dto.getPplcYearly2(); break;
                            case 3: getEffdate = dto.getPplcYearly3(); break;
                            case 4: getEffdate = dto.getPplcYearly4(); break;
                            case 5: getEffdate = dto.getPplcYearly5(); break;
                            case 6: getEffdate = dto.getPplcYearly6(); break;
                            case 7: getEffdate = dto.getPplcYearly7(); break;
                            case 8: getEffdate = dto.getPplcYearly8(); break;
                            case 9: getEffdate = dto.getPplcYearly9(); break;
                            case 10: getEffdate = dto.getPplcYearly10(); break;
                            default: getEffdate = dto.getPplcYearly(); break;
                        }
                    } else {
                        getEffdate = dto.getPplcMonthly();
                    }

                    StringBuilder sqlUp = new StringBuilder(
                        " UPDATE BAL_PLM_TL_STANDARDS SET PMSD_EFFECTIVEDATE = '").append(getEffdate).append("'");
                    sqlUp.append(" ,PMSD_MONTHWEEKNO = '").append(dto.getPplcWeekno())
                         .append("', PMSD_MODIFIEDON = SYSDATE ,PMSD_PLANCONFIGSTATUS ='Y' ");
                    sqlUp.append(" WHERE PMSD_MACHINEID = '").append(dto.getPplcMachineid()).append("' ");
                    sqlUp.append(" AND PMSD_ACTIVE ='Y' AND PMSD_FREQUENCYUNIT = '").append(freqUnit).append("'");

                    if (ValidationUtil.isValidKeyId(dto.getPplcAssemblyid()))
                        sqlUp.append(" AND PMSD_ASSEMBLYID ='").append(dto.getPplcAssemblyid()).append("'");

                    if (freqUnit.equals("Y") && ValidationUtil.isValidKeyId(freq))
                        sqlUp.append(" AND PMSD_FREQUENCY ='").append(freq).append("'");

                    sqls.add(sqlUp.toString());
                }
            }
        } catch (Exception e) {
            logger.error("Error building STANDARDS update sqls: {}", e.getMessage());
        }

        // Stage 5 — deactivate old calendar entries
        StringBuilder sqlUpCal = new StringBuilder(
            " UPDATE BAL_PLM_TL_CALENDAR SET PMCL_ACTIVE = 'N', PMCL_MODIFIEDON = CURRENT_TIMESTAMP ");
        sqlUpCal.append(" WHERE PMCL_MACHINEID = '").append(dto.getPplcMachineid())
                .append("' AND PMCL_PMFREQ IN ('W','F','M','Q','H','Y') ");
        sqlUpCal.append(" AND PMCL_STATUS IN ('X','A') AND PMCL_ACTIVE='Y' ");

        if (ValidationUtil.isValidKeyId(dto.getPplcAssemblyid()))
            sqlUpCal.append(" AND PMCL_ASSEMBLYID ='").append(dto.getPplcAssemblyid()).append("'");

        sqls.add(sqlUpCal.toString());

        // Stage 6 — execute all statements
        String woRespData = null;
        try {
            executeStatements(sqls);

            StringBuilder sqlStd = new StringBuilder(
                "select pmsd_keyid from BAL_PLM_TL_STANDARDS where pmsd_machineid = '")
                .append(dto.getPplcMachineid()).append("'");
            String pmsdKeyId = getSingleValue(sqlStd.toString());

            if (ValidationUtil.isValidKeyId(pmsdKeyId)) {
                generateCalendar(
                    dto.getPplcYearly().substring(7, 11),
                    dto.getPplcFactoryid(),
                    dto.getPplcSectionid(),
                    dto.getPplcCellid(),
                    dto.getPplcMachineid(),
                    dto.getPplcAssemblyid(),
                    dto.getPplcFrequency(),
                    dto.getPplcYearly().substring(3, 6)
                );
            }

            StringBuilder sqlWo = new StringBuilder(
                "SELECT COUNT(PWRM_MACHINEID) FROM BAL_PLM_TL_WORESPMST WHERE PWRM_MACHINEID = '")
                .append(dto.getPplcMachineid()).append("'");

            woRespData = getSingleValue(sqlWo.toString());

            if (woRespData == null || Integer.parseInt(woRespData) == 0) {
                throw new RuntimeException("No Work Order Responsibility entry for the Equipment, Do you want to open?");
            }

        } catch (Exception e) {
            logger.error("Error executing plan configuration save: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }

        return dto;
    }

    // ⚠️ placeholder — needs real column list from BAL_PlmTlPlanconfigurationSql
    private String buildInsertSql(BalPlanConfigurationSaveDto dto) {

    String flid          = safe(dto.getPplcFlid());
    String elementid      = safe(dto.getPplcElementid());
    String tempfield1     = safe(dto.getPplcTempfield1());
    String tempfield2     = safe(dto.getPplcTempfield2());
    String tempfield3     = safe(dto.getPplcTempfield3());
    String tempfield4     = safe(dto.getPplcTempfield4());
    String active         = (dto.getPplcActive() == null || dto.getPplcActive().isBlank()) ? "Y" : dto.getPplcActive();
    String createdby      = (dto.getPplcCreatedby() == null || dto.getPplcCreatedby().isBlank()) ? "SYSTEM" : dto.getPplcCreatedby();

    String fallbackDate = dto.getPplcMonthly();

    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO BAL_PLM_TL_PLANCONFIGURATION (")
      .append("PPLC_KEYID, PPLC_FACTORYID, PPLC_SECTIONID, PPLC_CELLID, PPLC_MACHINEID, PPLC_LEVEL, ")
      .append("PPLC_WEEKNO, PPLC_MONTHLY, PPLC_QUARTERLY, PPLC_HALFYEARLY, PPLC_YEARLY, ")
      .append("PPLC_YEARLY2, PPLC_YEARLY3, PPLC_YEARLY4, PPLC_YEARLY5, PPLC_YEARLY6, ")
      .append("PPLC_YEARLY7, PPLC_YEARLY8, PPLC_YEARLY9, PPLC_YEARLY10, PPLC_FREQUENCY, ")
      .append("PPLC_ASSEMBLYID, PPLC_FLID, PPLC_ELEMENTID, PPLC_TEMPFIELD1, PPLC_TEMPFIELD2, ")
      .append("PPLC_TEMPFIELD3, PPLC_TEMPFIELD4, PPLC_ACTIVE, PPLC_CREATEDBY, PPLC_CREATEDON, PPLC_MODIFIEDON")
      .append(") VALUES (")
      .append("'").append(dto.getPplcKeyid()).append("', ")
      .append("'").append(dto.getPplcFactoryid()).append("', ")
      .append("'").append(dto.getPplcSectionid()).append("', ")
      .append("'").append(dto.getPplcCellid()).append("', ")
      .append("'").append(dto.getPplcMachineid()).append("', ")
      .append("'").append(dto.getPplcLevel()).append("', ")
      .append("'").append(dto.getPplcWeekno()).append("', ")
      .append(dateVal(dto.getPplcMonthly(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcQuarterly(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcHalfyearly(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcYearly(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcYearly2(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcYearly3(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcYearly4(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcYearly5(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcYearly6(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcYearly7(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcYearly8(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcYearly9(), fallbackDate)).append(", ")
      .append(dateVal(dto.getPplcYearly10(), fallbackDate)).append(", ")
      .append("'").append(dto.getPplcFrequency()).append("', ")
      .append("'").append(dto.getPplcAssemblyid()).append("', ")
      .append("'").append(flid).append("', ")
      .append("'").append(elementid).append("', ")
      .append("'").append(tempfield1).append("', ")
      .append("'").append(tempfield2).append("', ")
      .append("'").append(tempfield3).append("', ")
      .append("'").append(tempfield4).append("', ")
      .append("'").append(active).append("', ")
      .append("'").append(createdby).append("', ")
      .append("CURRENT_TIMESTAMP, CURRENT_TIMESTAMP")
      .append(")");

    return sb.toString();
}

private String dateVal(String value, String fallback) {
    String v = (value == null || value.isBlank()) ? fallback : value;
    if (v == null || v.isBlank()) {
        return "CURRENT_TIMESTAMP";
    }
    return "TO_TIMESTAMP('" + v + "','DD-Mon-YYYY')";
}

private String safe(String value) {
    return value == null ? "" : value;
}

    // ⚠️ placeholder — original generateCalendar() logic needs porting
    private void generateCalendar(String year, String factoryId, String sectionId, String cellId,
                                   String machineId, String assemblyId, String frequency, String month) {
        logger.info("generateCalendar called — logic pending from original DAO");
    }

    // ===================== LOCAL DB HELPER METHODS (kept inside this class only) =====================

    private String getSequenceNumber(String tableName, int keyLength, String preFix, String dateFormat, String formatReset) throws Exception {
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
            stmt.setString(6, formatReset);
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

    private String getSingleValue(String sql) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;

        } catch (SQLException e) {
            logger.error("SQL Exception in getSingleValue: {}", e.getMessage(), e);
            throw new Exception("getSingleValue SQL Error: " + e.getMessage());
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
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    logger.error("Rollback failed: {}", rollbackEx.getMessage(), rollbackEx);
                }
            }
            throw new Exception("executeStatements SQL Error: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ignore) {}
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
}