package com.akranta.perfex_sb.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PhenomenaLossRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PhenomenaLossRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String basePhenomenaSql(String phenId, String lossId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT ")
           .append("  p.plpm_keyid AS phenid, ")
           .append("  p.plpm_name AS phenname ")
           .append("FROM pcs_tl_lossphenomenamst p ")
           .append("LEFT JOIN pcs_tl_lossphenfactorylink l ")
           .append("  ON p.plpm_keyid = l.ppfl_plpm_keyid ")
           .append("WHERE p.plpm_name <> '-' ")
           .append("  AND p.plpm_active = 'Y' ");
        if (phenId != null && !phenId.trim().isEmpty()) {
            sql.append(" AND p.plpm_keyid = :phenId ");
        }
        if (lossId != null && !lossId.trim().isEmpty()) {
            sql.append(" AND p.plpm_mainloss = :lossId ");
        }
        sql.append("ORDER BY phenname ASC ");
        return sql.toString();
    }

    public long countPhenomena(String phenId, String lossId) {
        String sql = "SELECT count(*) FROM (" + basePhenomenaSql(phenId, lossId) + ") x";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("phenId", phenId)
                .addValue("lossId", lossId);
        Long cnt = jdbcTemplate.queryForObject(sql, params, Long.class);
        return cnt == null ? 0 : cnt;
    }

    public List<Map<String, Object>> findPhenomena(String phenId, String lossId, int from, int to) {
        String inner = basePhenomenaSql(phenId, lossId);
        String finalSql = "SELECT * FROM ( " +
                "  SELECT row_number() OVER () AS slno, a.* " +
                "  FROM ( " + inner + " ) a " +
                ") s WHERE slno BETWEEN " + from + " AND " + to;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("phenId", phenId)
                .addValue("lossId", lossId);
        return jdbcTemplate.queryForList(finalSql, params);
    }

    public int insertPhenomena(Map<String, Object> values) {
        String sql = """
                INSERT INTO pcs_tl_lossphenomenamst (
                    plpm_keyid, plpm_name, plpm_mainloss, plpm_tempfield1, plpm_tempfield2, plpm_tempfield3,
                    plpm_active, plpm_createdby, plpm_createdon, plpm_modifiedon
                ) VALUES (
                    :plpm_keyid, :plpm_name, :plpm_mainloss, :plpm_tempfield1, :plpm_tempfield2, :plpm_tempfield3,
                    :plpm_active, :plpm_createdby, :plpm_createdon, :plpm_modifiedon
                )
                """;
        return jdbcTemplate.update(sql, new MapSqlParameterSource(values));
    }

    public int updatePhenomena(Map<String, Object> values) {
        String sql = """
                UPDATE pcs_tl_lossphenomenamst SET
                    plpm_name = :plpm_name,
                    plpm_mainloss = :plpm_mainloss,
                    plpm_tempfield1 = :plpm_tempfield1,
                    plpm_tempfield2 = :plpm_tempfield2,
                    plpm_tempfield3 = :plpm_tempfield3,
                    plpm_active = :plpm_active,
                    plpm_createdby = :plpm_createdby,
                    plpm_createdon = :plpm_createdon,
                    plpm_modifiedon = :plpm_modifiedon
                WHERE plpm_keyid = :plpm_keyid
                """;
        return jdbcTemplate.update(sql, new MapSqlParameterSource(values));
    }

    public List<Map<String, Object>> getComboTextContent(String keyId, String type) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select PLPM_KEYID, KEYID, PLPM_NAME ")
           .append(" from  pcs_tl_lossphenomenamst, pcs_vw_lossnames ")
           .append(" where PLPM_MAINLOSS = KEYID ");
        MapSqlParameterSource params = new MapSqlParameterSource();
        if ("PHENOMENA".equalsIgnoreCase(type)) {
            sql.append(" and plpm_keyid = :keyId ");
            params.addValue("keyId", keyId);
        } else if ("LOSS".equalsIgnoreCase(type)) {
            sql.append(" and PLPM_MAINLOSS = :keyId ");
            params.addValue("keyId", keyId);
        }
        return jdbcTemplate.queryForList(sql.toString(), params);
    }

    // -------- factory link support --------

    public int deleteFactoryLinks(String phenId) {
        return jdbcTemplate.update(
                "DELETE FROM pcs_tl_lossphenfactorylink WHERE ppfl_plpm_keyid = :id",
                new MapSqlParameterSource("id", phenId)
        );
    }

    public int insertFactoryLink(Map<String, Object> values) {
        String sql = """
                INSERT INTO pcs_tl_lossphenfactorylink (
                    ppfl_keyid, ppfl_plpm_keyid, ppfl_factoryid,
                    ppfl_tempfield1, ppfl_tempfield2, ppfl_active,
                    ppfl_createdby, ppfl_createdon, ppfl_modifiedon
                ) VALUES (
                    :ppfl_keyid, :ppfl_plpm_keyid, :ppfl_factoryid,
                    :ppfl_tempfield1, :ppfl_tempfield2, :ppfl_active,
                    :ppfl_createdby, :ppfl_createdon, :ppfl_modifiedon
                )
                """;
        return jdbcTemplate.update(sql, new MapSqlParameterSource(values));
    }

    // -------- factory grid (selection) --------

    private String baseFactorySql(String lossId, String phenId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT DISTINCT l.ppfl_factoryid ")
          .append(" FROM pcs_tl_lossphenfactorylink l ")
          .append(" JOIN pcs_tl_lossphenomenamst p ON p.plpm_keyid = l.ppfl_plpm_keyid ")
          .append(" WHERE p.plpm_name <> '-' ");
        if (lossId != null && !lossId.trim().isEmpty()) {
            sb.append(" AND p.plpm_mainloss = :lossId ");
        }
        if (phenId != null && !phenId.trim().isEmpty()) {
            sb.append(" AND p.plpm_keyid = :phenId ");
        }
        return sb.toString();
    }

    public long countFactoryGrid(String lossId, String phenId) {
        String sql = "SELECT count(*) FROM ( " +
                "SELECT CASE WHEN pf.ppfl_factoryid = c.cell_keyid THEN 1 ELSE 0 END AS selected, c.cell_keyid, c.cell_code, c.cell_name " +
                "FROM gen_tl_cellmst c " +
                "LEFT JOIN ( " + baseFactorySql(lossId, phenId) + " ) pf ON c.cell_keyid = pf.ppfl_factoryid " +
                ") x";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("lossId", lossId)
                .addValue("phenId", phenId);
        Long cnt = jdbcTemplate.queryForObject(sql, params, Long.class);
        return cnt == null ? 0 : cnt;
    }

    public List<Map<String, Object>> findFactoryGrid(String lossId, String phenId, int from, int to) {
        String baseSql =
                "SELECT CASE WHEN pf.ppfl_factoryid = c.cell_keyid THEN 1 ELSE 0 END AS selected, " +
                "c.cell_keyid, c.cell_code, c.cell_name " +
                "FROM gen_tl_cellmst c " +
                "LEFT JOIN ( " + baseFactorySql(lossId, phenId) + " ) pf ON c.cell_keyid = pf.ppfl_factoryid ";
        String finalSql = "SELECT * FROM ( " +
                "  SELECT row_number() OVER () AS slno, a.* " +
                "  FROM ( " + baseSql + " ) a " +
                ") s WHERE slno BETWEEN " + from + " AND " + to;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("lossId", lossId)
                .addValue("phenId", phenId);
        return jdbcTemplate.queryForList(finalSql, params);
    }

    // -------- delete + guards --------

    public int countLinksForPhenomena(String phenId) {
        Long cnt = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM pcs_tl_lossphenfactorylink WHERE ppfl_plpm_keyid = :id",
                new MapSqlParameterSource("id", phenId),
                Long.class
        );
        return cnt == null ? 0 : cnt.intValue();
    }

    public int countLossCaptureForPhenomenaFactory(String phenId, String factoryId) {
        Long cnt = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                  FROM pcs_tl_losscapture
                 WHERE plos_lossreason = :phenId
                   AND plos_flid = :factoryId
                """,
                new MapSqlParameterSource()
                        .addValue("phenId", phenId)
                        .addValue("factoryId", factoryId),
                Long.class
        );
        return cnt == null ? 0 : cnt.intValue();
    }

    public int deletePhenomena(String phenId) {
        return jdbcTemplate.update(
                "DELETE FROM pcs_tl_lossphenomenamst WHERE plpm_keyid = :id",
                new MapSqlParameterSource("id", phenId)
        );
    }

    public int deleteFactoryLink(String phenId, String factoryId) {
        return jdbcTemplate.update(
                "DELETE FROM pcs_tl_lossphenfactorylink WHERE ppfl_plpm_keyid = :phenId AND ppfl_factoryid = :factoryId",
                new MapSqlParameterSource()
                        .addValue("phenId", phenId)
                        .addValue("factoryId", factoryId)
        );
    }
}
