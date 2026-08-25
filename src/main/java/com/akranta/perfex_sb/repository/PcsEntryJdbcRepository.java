package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PcsEntryJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PcsEntryJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String findSectionCode(String sectId) {
        return jdbcTemplate.queryForObject(
                "SELECT sect_code FROM gen_tl_sectionmst WHERE sect_keyid = :sectId",
                new MapSqlParameterSource("sectId", sectId),
                String.class
        );
    }

    public String findMasterId(String detailTable, String pldetailsid) {
        var params = new MapSqlParameterSource("pldetailsid", pldetailsid);
        var list = jdbcTemplate.query(
                "SELECT plmasterid FROM " + detailTable + " WHERE pldetailsid = :pldetailsid LIMIT 1",
                params,
                (rs, rowNum) -> rs.getString(1)
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public String findDetailIdByLossCaptureId(String lossCaptureId) {
        var list = jdbcTemplate.query(
                "SELECT plos_pldetailsid FROM pcs_tl_losscapture WHERE plos_keyid = :id LIMIT 1",
                new MapSqlParameterSource("id", lossCaptureId),
                (rs, rowNum) -> rs.getString(1)
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public int deleteDetail(String detailTable, String pldetailsid) {
        return jdbcTemplate.update(
                "DELETE FROM " + detailTable + " WHERE pldetailsid = :pldetailsid",
                new MapSqlParameterSource("pldetailsid", pldetailsid)
        );
    }

    public int deleteMaster(String prlmKeyid) {
        return jdbcTemplate.update(
                "DELETE FROM pcs_tl_mst WHERE prlm_keyid = :id",
                new MapSqlParameterSource("id", prlmKeyid)
        );
    }

    public int deleteLossReasonLink(String pldetailsid) {
        return jdbcTemplate.update(
                "DELETE FROM pcs_tl_lossreasonlink WHERE plrk_pldetailid = :pldetailsid",
                new MapSqlParameterSource("pldetailsid", pldetailsid)
        );
    }

    public int deleteLossCapture(String plosKeyid) {
        return jdbcTemplate.update(
                "DELETE FROM pcs_tl_losscapture WHERE plos_keyid = :id",
                new MapSqlParameterSource("id", plosKeyid)
        );
    }

    public int deleteLossCaptureByDetailId(String pldetailsid) {
        return jdbcTemplate.update(
                "DELETE FROM pcs_tl_losscapture WHERE plos_pldetailsid = :pldetailsid",
                new MapSqlParameterSource("pldetailsid", pldetailsid)
        );
    }

    public boolean hasAnyDetailRowsForMaster(String masterId) {
        List<String> tables = jdbcTemplate.query(
                """
                SELECT DISTINCT kcu.table_name
                FROM information_schema.table_constraints tc
                JOIN information_schema.key_column_usage kcu
                  ON tc.constraint_name = kcu.constraint_name
                 AND tc.table_schema = kcu.table_schema
                JOIN information_schema.constraint_column_usage ccu
                  ON tc.constraint_name = ccu.constraint_name
                 AND tc.table_schema = ccu.table_schema
                WHERE tc.constraint_type = 'FOREIGN KEY'
                  AND tc.table_schema = current_schema()
                  AND ccu.table_name = 'pcs_tl_mst'
                  AND ccu.column_name = 'prlm_keyid'
                  AND kcu.column_name = 'plmasterid'
                """,
                new MapSqlParameterSource(),
                (rs, rowNum) -> rs.getString(1)
        );

        for (String tableName : tables) {
            if (tableName == null || !tableName.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
                continue;
            }
            List<Integer> rows = jdbcTemplate.query(
                    "SELECT 1 FROM " + tableName + " WHERE plmasterid = :masterId LIMIT 1",
                    new MapSqlParameterSource("masterId", masterId),
                    (rs, rowNum) -> 1
            );
            if (!rows.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public int deleteAncilliaryTime(String pldetailsid) {
        return jdbcTemplate.update(
                "DELETE FROM pcs_tl_ancilliarytime WHERE ptat_pldeatilsid = :pldetailsid",
                new MapSqlParameterSource("pldetailsid", pldetailsid)
        );
    }
}
