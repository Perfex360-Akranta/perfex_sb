package com.akranta.perfex_sb.repository;

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

    public List<DropDownDto> fillComboValues(ComboFilterDto comboFilter, Object[] params) {

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

}
