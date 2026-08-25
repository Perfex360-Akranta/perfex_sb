package com.akranta.perfex_sb.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.controller.ConditionalAppraisalController;
import com.akranta.perfex_sb.dto.ComboFilterDto;
import com.akranta.perfex_sb.dto.DropDownDto;
import com.akranta.perfex_sb.util.ValidationUtil;

@Repository
public class CommonFilterRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CommonFilterRepository.class);

    public List<DropDownDto> fillComboValuesWithoutKey(ComboFilterDto comboFilter, Object[] params) 
    {

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT DISTINCT ");

        sql.append(comboFilter.getIdField())
                .append(" AS VALUE, ");

        if (ValidationUtil.isValidKeyId(comboFilter.getNameField())
                && ValidationUtil.isValidKeyId(comboFilter.getCodeField())) {

            sql.append(comboFilter.getNameField())
                    .append(" || '-' || ")
                    .append(comboFilter.getCodeField())
                    .append(" AS LABEL ");

        } else if (ValidationUtil.isValidKeyId(comboFilter.getNameField())) {

            sql.append(comboFilter.getNameField()).append(" AS LABEL ");

        } else {

            sql.append(comboFilter.getCodeField())
                    .append(" AS LABEL ");
        }

        sql.append(" FROM ").append(comboFilter.getTableName()).append(" WHERE 1 = 1 ");

        if (ValidationUtil.isValidKeyId(comboFilter.getCondSql())) {

            sql.append(comboFilter.getCondSql());
        }

        if (ValidationUtil.isValidKeyId(comboFilter.getOrderByField())) {

            sql.append(" ORDER BY ")
                    .append(comboFilter.getOrderByField());

        } else {

            sql.append(" ORDER BY LABEL ");
        }

        


        return executeComboQuery(
                sql.toString(),
                params,
                comboFilter);
    }

    public List<DropDownDto> fillComboValues(ComboFilterDto comboFilter, Object[] params) 
    {

        StringBuilder sql = new StringBuilder();

        StringBuilder selectSql = new StringBuilder("SELECT DISTINCT ");

        selectSql.append(comboFilter.getIdField())
                .append(" AS VALUE, ");

        if (ValidationUtil.isValidKeyId(comboFilter.getNameField())
                && ValidationUtil.isValidKeyId(comboFilter.getCodeField())) {

            selectSql.append(comboFilter.getNameField())
                    .append(" || '-' || ")
                    .append(comboFilter.getCodeField())
                    .append(" AS LABEL ");

        } else if (ValidationUtil.isValidKeyId(comboFilter.getNameField())) {

            selectSql.append(comboFilter.getNameField()).append(" AS LABEL ");

        } else {

            selectSql.append(comboFilter.getCodeField())
                    .append(" AS LABEL ");
        }

        StringBuilder fromSql = new StringBuilder();
            fromSql.append(" FROM ")
           .append(comboFilter.getTableName())
           .append(" WHERE 1=1 ");

        if (ValidationUtil.isValidKeyId(comboFilter.getCondSql())) {

            fromSql.append(comboFilter.getCondSql());
        }

        

        boolean isCombo = false;
        String excludeSql = "";

        if  (!"grid".equals(comboFilter.getMode())&& ValidationUtil.isValidKeyId(comboFilter.getId())&& !ValidationUtil.isValidKeyId(comboFilter.getName())) 
        {
            isCombo = true;

            StringBuilder firstSelect = new StringBuilder(selectSql);
            firstSelect.append(", 1 AS R");
            firstSelect.append(fromSql);

            if(comboFilter.getId().contains(","))
            {
                    firstSelect.append(" AND ")
                       .append(comboFilter.getIdField())
                       .append(" IN ('")
                       .append(comboFilter.getId().replace(",", "','"))
                       .append("')");

            excludeSql = " AND " + comboFilter.getIdField()
                    + " NOT IN ('"
                    + comboFilter.getId().replace(",", "','")
                    + "')";

            }
            else 
            {

            firstSelect.append(" AND ")
                       .append(comboFilter.getIdField())
                       .append("='")
                       .append(comboFilter.getId())
                       .append("'");

            excludeSql = " AND "
                    + comboFilter.getIdField()
                    + "<>'"
                    + comboFilter.getId()
                    + "'";
            }
            
               sql.append(firstSelect).append(" UNION ");

        }
        sql.append(selectSql);

        if (isCombo) {
            sql.append(", 2 AS R");
        }

        sql.append(fromSql);

        if (isCombo) {
        sql.append(excludeSql);
        }

        sql.append(" ORDER BY ");

        if (isCombo) {
            sql.append("R, ");
        }

        if  (ValidationUtil.isValidKeyId(comboFilter.getOrderByField())) {
            sql.append(comboFilter.getOrderByField());
        } else {
            sql.append("LABEL");
        }
        logger.info("SQl {}",sql.toString());
    return executeComboQuery(sql.toString(),params,comboFilter);
    }

    private List<DropDownDto> executeComboQuery(String sql, Object[] params, ComboFilterDto comboFilter) {

        String mode = comboFilter.getMode();

        StringBuilder executeSql = new StringBuilder();

        if ("grid".equals(mode)) {

            /*
             * We will implement this later:
             *
             * 1. Grid filtering
             * 2. Total record count
             * 3. Pagination
             */

            throw new UnsupportedOperationException(
                    "Grid combo mode is not implemented yet");

        } else {

            executeSql.append("""
                    SELECT *
                    FROM (
                    """);

            executeSql.append(sql);

            executeSql.append("""
                    ) COMBO_DATA
                    """);
        }

        logger.info("SQL COMBO {}", executeSql.toString());
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new DropDownDto(
                        rs.getString("VALUE"),
                        rs.getString("LABEL")),
                params

        );
    }

    public List<DropDownDto> fillComboValues1(ComboFilterDto comboFilter, Object[] params)
{
    StringBuilder sql = new StringBuilder();

    StringBuilder selectSql = new StringBuilder("SELECT DISTINCT ");

    selectSql.append(comboFilter.getIdField())
            .append(" AS VALUE, ");

    if (ValidationUtil.isValidKeyId(comboFilter.getNameField())
            && ValidationUtil.isValidKeyId(comboFilter.getCodeField())) {

        selectSql.append(comboFilter.getNameField())
                .append(" || '-' || ")
                .append(comboFilter.getCodeField())
                .append(" AS LABEL ");

    } else if (ValidationUtil.isValidKeyId(comboFilter.getNameField())) {

        selectSql.append(comboFilter.getNameField()).append(" AS LABEL ");

    } else {

        selectSql.append(comboFilter.getCodeField())
                .append(" AS LABEL ");
    }

    // base FROM+WHERE — NO condSql here. Used for the r=1 (id-match) branch,
    // matching legacy DaoImpl behaviour where the id-match row bypassed
    // the extra cell/trade condition.
    StringBuilder baseFromSql = new StringBuilder();
    baseFromSql.append(" FROM ")
       .append(comboFilter.getTableName())
       .append(" WHERE 1=1 ");

    // this copy carries condSql — used ONLY for the r=2 (exclusion) branch
    StringBuilder fromSql = new StringBuilder(baseFromSql);
    if (ValidationUtil.isValidKeyId(comboFilter.getCondSql())) {
        fromSql.append(comboFilter.getCondSql());
    }

    boolean isCombo = false;
    String excludeSql = "";

    if (!"grid".equals(comboFilter.getMode())
            && ValidationUtil.isValidKeyId(comboFilter.getId())
            && !ValidationUtil.isValidKeyId(comboFilter.getName())) {

        isCombo = true;

        StringBuilder firstSelect = new StringBuilder(selectSql);
        firstSelect.append(", 1 AS R");
        firstSelect.append(baseFromSql);   // <-- condSql NOT included

        if (comboFilter.getId().contains(",")) {
            firstSelect.append(" AND ")
                       .append(comboFilter.getIdField())
                       .append(" IN ('")
                       .append(comboFilter.getId().replace(",", "','"))
                       .append("')");

            excludeSql = " AND " + comboFilter.getIdField()
                    + " NOT IN ('"
                    + comboFilter.getId().replace(",", "','")
                    + "')";

        } else {

            firstSelect.append(" AND ")
                       .append(comboFilter.getIdField())
                       .append("='")
                       .append(comboFilter.getId())
                       .append("'");

            excludeSql = " AND "
                    + comboFilter.getIdField()
                    + "<>'"
                    + comboFilter.getId()
                    + "'";
        }

        sql.append(firstSelect).append(" UNION ");
    }

    sql.append(selectSql);

    if (isCombo) {
        sql.append(", 2 AS R");
    }

    sql.append(fromSql);          // condSql applies HERE only

    if (isCombo) {
        sql.append(excludeSql);
    }

    sql.append(" ORDER BY ");

    if (isCombo) {
        sql.append("R, ");
    }

    if (ValidationUtil.isValidKeyId(comboFilter.getOrderByField())) {
        sql.append(comboFilter.getOrderByField());
    } else {
        sql.append("LABEL");
    }

    logger.info("SQl {}", sql.toString());
    return executeComboQuery(sql.toString(), params, comboFilter);
}
//----------------------------HARI-------------------------------

///newTrainingCalendar/Combo_Faculty
    public String resolveSectId(String fnlnKeyid) {

        String sql = """
                SELECT CASE
                  WHEN src.fnln_elementtype = 'L' THEN src.fnln_originalid
                  WHEN src.fnln_elementtype = 'C' THEN (
                    SELECT anc.fnln_originalid
                    FROM gen_tl_functionallocn anc
                    WHERE anc.fnln_elementtype = 'L'
                      AND POSITION(anc.fnln_originalid IN src.fnln_elementid) > 0
                    ORDER BY LENGTH(anc.fnln_elementid) DESC
                    LIMIT 1
                  )
                  ELSE NULL
                END AS sect_originalid
                FROM gen_tl_functionallocn src
                WHERE src.fnln_keyid = ?
                """;

        List<String> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getString("sect_originalid"),
                fnlnKeyid);

        return result.isEmpty() ? null : result.get(0);
    }
    public List<DropDownDto> fillComboValuesRole(ComboFilterDto comboFilter, Object[] params)
    {
        StringBuilder sql = new StringBuilder();
        StringBuilder tbl = new StringBuilder(" , ROLE_LEVEL FROM ")
                .append(comboFilter.getTableName())
                .append(" WHERE 1 = 1 ");

        StringBuilder selectSql = new StringBuilder(" SELECT DISTINCT ");
        List<Object> likeParams = new ArrayList<>();
        String likeSql = null;

        if (ValidationUtil.isValidKeyId(comboFilter.getIdField())) {
            selectSql.append(comboFilter.getIdField()).append(" AS VALUE ");
        }

        if (ValidationUtil.isValidKeyId(comboFilter.getNameField()) && ValidationUtil.isValidKeyId(comboFilter.getCodeField())) {
            selectSql.append(", ").append(comboFilter.getNameField()).append(" || '-' || ").append(comboFilter.getCodeField()).append(" AS LABEL ");
            if (ValidationUtil.isValidKeyId(comboFilter.getCode()) || ValidationUtil.isValidKeyId(comboFilter.getName())) {
                likeSql = " (UPPER(" + comboFilter.getCodeField() + ") LIKE UPPER(?) OR UPPER(" + comboFilter.getNameField() + ") LIKE UPPER(?)) ";
                likeParams.add("%" + (ValidationUtil.isValidKeyId(comboFilter.getCode()) ? comboFilter.getCode() : "") + "%");
                likeParams.add("%" + (ValidationUtil.isValidKeyId(comboFilter.getName()) ? comboFilter.getName() : "") + "%");
            }
        } else if (ValidationUtil.isValidKeyId(comboFilter.getNameField())) {
            selectSql.append(", ").append(comboFilter.getNameField()).append(" AS LABEL ");
            if (ValidationUtil.isValidKeyId(comboFilter.getName())) {
                likeSql = " UPPER(" + comboFilter.getNameField() + ") LIKE UPPER(?) ";
                likeParams.add("%" + comboFilter.getName() + "%");
            }
        } else if (ValidationUtil.isValidKeyId(comboFilter.getCodeField())) {
            selectSql.append(", ").append(comboFilter.getCodeField()).append(" AS LABEL ");
            if (ValidationUtil.isValidKeyId(comboFilter.getCode())) {
                likeSql = " UPPER(" + comboFilter.getCodeField() + ") LIKE UPPER(?) ";
                likeParams.add("%" + comboFilter.getCode() + "%");
            }
        }

        if (ValidationUtil.isValidKeyId(comboFilter.getOrderByField())) {
            selectSql.append(", ").append(comboFilter.getOrderByField().replace("desc", "").replace("asc", ""));
        }

        boolean isCombo = false;
        String excludeSql = "";
        List<Object> allParams = new ArrayList<>();

        if (!"grid".equals(comboFilter.getMode()) && ValidationUtil.isValidKeyId(comboFilter.getId()) && !ValidationUtil.isValidKeyId(comboFilter.getName())) {

            isCombo = true;

            StringBuilder firstSelect = new StringBuilder(selectSql);
            firstSelect.append(", 1 AS R");
            firstSelect.append(tbl);

            if (comboFilter.getId().indexOf(",") > 0) {
                firstSelect.append(" AND ").append(comboFilter.getIdField()).append(" IN ('")
                        .append(comboFilter.getId().replace(",", "','")).append("')");
                excludeSql = " AND " + comboFilter.getIdField() + " NOT IN ('" + comboFilter.getId().replace(",", "','") + "')";
            } else {
                firstSelect.append(" AND ").append(comboFilter.getIdField()).append(" = '").append(comboFilter.getId()).append("'");
                excludeSql = " AND " + comboFilter.getIdField() + " <> '" + comboFilter.getId() + "'";
            }

            if (ValidationUtil.isValidKeyId(comboFilter.getCondSql())) {
                firstSelect.append(comboFilter.getCondSql());
                if (params != null) {
                    allParams.addAll(java.util.Arrays.asList(params));
                }
            }

            sql.append(firstSelect).append(" UNION ");
        }

        sql.append(selectSql);
        if (isCombo) {
            sql.append(", 2 AS R");
        }
        sql.append(tbl);

        if (ValidationUtil.isValidKeyId(likeSql)) {
            sql.append(" AND ").append(likeSql);
            allParams.addAll(likeParams);
        }

        if (ValidationUtil.isValidKeyId(comboFilter.getCondSql())) {
            sql.append(comboFilter.getCondSql());
            if (params != null) {
                allParams.addAll(java.util.Arrays.asList(params));
            }
        }

        if (isCombo) {
            sql.append(excludeSql);
        }

        sql.append(" ORDER BY ");
        if (isCombo) {
            sql.append("R, ");
        }
        if (ValidationUtil.isValidKeyId(comboFilter.getOrderByField())) {
            sql.append(comboFilter.getOrderByField());
        } else {
            sql.append("ROLE_LEVEL ASC, LABEL ASC");
        }

        logger.info("SQL Role Combo {}", sql.toString());

        return executeComboQuery(sql.toString(), allParams.toArray(), comboFilter);
    }


    public String getCurrentShiftPrimary() {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT SFTM_KEYID FROM GEN_TL_SHIFTMST WHERE SFTM_ACTIVE = 'Y' AND CURRENT_TIME BETWEEN SFTM_STARTTIME AND SFTM_ENDTIME",
                    String.class);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String getCurrentShiftOvernightFallback() {
        try {
            return jdbcTemplate.queryForObject("""
                    SELECT SFTM_KEYID FROM GEN_TL_SHIFTMST
                    WHERE (NOW()::time BETWEEN SFTM_STARTTIME::time AND to_timestamp('23:59','HH24:MI')::time
                           OR NOW()::time BETWEEN to_timestamp('00:00','HH24:MI')::time AND SFTM_ENDTIME::time)
                    AND SFTM_ENDTIME::time < to_timestamp('06:01','HH24:MI')::time
                    AND SFTM_ACTIVE = 'Y'
                    """,
                    String.class);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
}
