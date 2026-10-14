package com.akranta.perfex_sb.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class BreakdownBookingMobileRepo {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BreakdownBookingMobileRepo(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Map<String, Object> findEquipmentHierarchy(String machineid, String flid) {
        String sql = """
                SELECT
                    mchm_keyid AS machineid,
                    fnln_keyid AS flid,
                    sect_keyid AS sectionid,
                    cell_keyid AS cellid,
                    fnln_elementid AS elementid,
                    sbut_keyid AS factoryid
                FROM gen_vw_fnln1
                WHERE (
                        (:machineid IS NOT NULL AND TRIM(:machineid) <> '' AND mchm_keyid = :machineid)
                     OR (:flid IS NOT NULL AND TRIM(:flid) <> '' AND fnln_keyid = :flid)
                )
                ORDER BY CASE
                    WHEN :machineid IS NOT NULL AND mchm_keyid = :machineid THEN 0
                    ELSE 1
                END
                LIMIT 1
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("machineid", machineid == null ? null : machineid.trim())
                .addValue("flid", flid == null ? null : flid.trim());

        try {
            return namedParameterJdbcTemplate.queryForMap(sql, params);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String findCurrentShiftId(String factoryid) {
        String factory = factoryid == null ? "" : factoryid.trim();

        String primarySql = """
                SELECT SFTM_KEYID
                FROM GEN_TL_SHIFTMST
                WHERE SFTM_ACTIVE = 'Y'
                  AND CURRENT_TIME BETWEEN SFTM_STARTTIME AND SFTM_ENDTIME
                  AND (:factoryid = '' OR SFTM_FACTORYID = :factoryid)
                ORDER BY SFTM_SHIFTORDER NULLS LAST, SFTM_KEYID
                LIMIT 1
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("factoryid", factory);

        try {
            return namedParameterJdbcTemplate.queryForObject(primarySql, params, String.class);
        } catch (EmptyResultDataAccessException e) {
            String overnightSql = """
                    SELECT SFTM_KEYID
                    FROM GEN_TL_SHIFTMST
                    WHERE SFTM_ACTIVE = 'Y'
                      AND (NOW()::time BETWEEN SFTM_STARTTIME::time AND to_timestamp('23:59','HH24:MI')::time
                           OR NOW()::time BETWEEN to_timestamp('00:00','HH24:MI')::time AND SFTM_ENDTIME::time)
                      AND SFTM_ENDTIME::time < to_timestamp('06:01','HH24:MI')::time
                      AND (:factoryid = '' OR SFTM_FACTORYID = :factoryid)
                    ORDER BY SFTM_SHIFTORDER NULLS LAST, SFTM_KEYID
                    LIMIT 1
                    """;
            try {
                return namedParameterJdbcTemplate.queryForObject(overnightSql, params, String.class);
            } catch (EmptyResultDataAccessException overnightMiss) {
                return null;
            }
        }
    }
}
