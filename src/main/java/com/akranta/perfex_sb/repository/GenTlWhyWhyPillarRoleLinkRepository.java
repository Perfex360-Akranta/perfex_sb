package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GenTlWhyWhyPillarRoleLinkRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String findActiveFlag(String pillarId, String roleId) {
        String sql = "SELECT yyrl_active FROM gen_tl_whywhy_pillar_rolelink "
                + "WHERE yyrl_pillarid = ? AND yyrl_roleid = ?";
        List<String> result = jdbcTemplate.queryForList(sql, String.class, pillarId, roleId);
        return result.isEmpty() ? null : result.get(0);
    }

    public String findPillarCode(String pillarId) {
        String sql = "SELECT tpmp_code FROM gen_tl_tpmpillarmst WHERE tpmp_keyid = ?";
        List<String> result = jdbcTemplate.queryForList(sql, String.class, pillarId);
        return result.isEmpty() ? null : result.get(0);
    }

    public String findRoleCode(String roleId) {
        String sql = "SELECT role_code FROM adm_tl_rolemst WHERE role_keyid = ?";
        List<String> result = jdbcTemplate.queryForList(sql, String.class, roleId);
        return result.isEmpty() ? null : result.get(0);
    }

    public String findRoleName(String roleId) {
        String sql = "SELECT role_name FROM adm_tl_rolemst WHERE role_keyid = ?";
        List<String> result = jdbcTemplate.queryForList(sql, String.class, roleId);
        return result.isEmpty() ? null : result.get(0);
    }

    public void insertPillarRoleLink(String keyid, String pillarId, String pillarCode,
                                      String roleId, String roleCode, String roleName, String createdBy) {
        String sql = "INSERT INTO gen_tl_whywhy_pillar_rolelink "
                + "(yyrl_keyid, yyrl_pillarid, yyrl_pillarcode, yyrl_roleid, yyrl_rolecode, yyrl_rolename, "
                + "yyrl_active, yyrl_createdby, yyrl_createdon, yyrl_modifiedon) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'Y', ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql, keyid, pillarId, pillarCode, roleId, roleCode, roleName, createdBy);
    }

    public void reactivatePillarRoleLink(String pillarId, String roleId) {
        String sql = "UPDATE gen_tl_whywhy_pillar_rolelink SET "
                + "yyrl_active = 'Y', yyrl_modifiedon = CURRENT_TIMESTAMP "
                + "WHERE yyrl_pillarid = ? AND yyrl_roleid = ?";
        jdbcTemplate.update(sql, pillarId, roleId);
    }

    public int deactivatePillarRoleLink(String pillarId, String roleId) {
        String sql = "UPDATE gen_tl_whywhy_pillar_rolelink SET "
                + "yyrl_active = 'N', yyrl_modifiedon = CURRENT_TIMESTAMP "
                + "WHERE yyrl_pillarid = ? AND yyrl_roleid = ? AND yyrl_active = 'Y'";
        return jdbcTemplate.update(sql, pillarId, roleId);
    }

    /**
     * Keyid generation for the Spring Boot side.
     * IMPORTANT: this reads/updates gen_tl_seqnogen directly rather than reusing
     * the Eclipse-side dbActionTemplate.getSequenceNumber() logic, since that
     * method lives in the legacy stack and isn't reachable from here.
     */
    public synchronized String getNextSequence() {
        String selectSql = "SELECT seqg_prefix, seqg_latestvalue, seqg_totallength "
                + "FROM gen_tl_seqnogen WHERE seqg_formname = ?";
        Map<String, Object> row = jdbcTemplate.queryForMap(selectSql, "GEN_TL_WHYWHY_PILLAR_ROLELINK");

        String prefix = (String) row.get("seqg_prefix");
        int latestValue = ((Number) row.get("seqg_latestvalue")).intValue();
        int totalLength = ((Number) row.get("seqg_totallength")).intValue();

        int nextValue = latestValue + 1;
        int digitWidth = totalLength - prefix.length();
        String paddedNumber = String.format("%0" + digitWidth + "d", nextValue);
        String newKeyid = prefix + paddedNumber;

        String updateSql = "UPDATE gen_tl_seqnogen SET seqg_latestvalue = ?, seqg_lastcode = ? "
                + "WHERE seqg_formname = ?";
        jdbcTemplate.update(updateSql, nextValue, newKeyid, "GEN_TL_WHYWHY_PILLAR_ROLELINK");

        return newKeyid;
    }
}