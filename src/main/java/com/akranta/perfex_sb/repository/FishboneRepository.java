package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.dto.FishboneChildSaveRequest;
import com.akranta.perfex_sb.dto.FishboneMasterSaveRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class FishboneRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public FishboneRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertMaster(FishboneMasterSaveRequest m) {
        String sql = """
                INSERT INTO gen_tl_fishbonemst (
                    fism_keyid, fism_flid, fism_elementid, fism_refdocid, fism_refdoctype,
                    fism_title, fism_problem, fism_revisionno, fism_prepareddate, fism_preparedby,
                    fism_approveddate, fism_approvedby, fism_status, fism_defect,
                    fism_tempfield2, fism_tempfield3, fism_tempfield4, fism_tempfield5,
                    fism_active, fism_createdby, fism_createdon, fism_modifiedon
                ) VALUES (
                    :fism_keyid, :fism_flid, :fism_elementid, :fism_refdocid, :fism_refdoctype,
                    :fism_title, :fism_problem, :fism_revisionno, :fism_prepareddate, :fism_preparedby,
                    :fism_approveddate, :fism_approvedby, :fism_status, :fism_defect,
                    :fism_tempfield2, :fism_tempfield3, :fism_tempfield4, :fism_tempfield5,
                    :fism_active, :fism_createdby, :fism_createdon, :fism_modifiedon
                )
                """;
        return jdbcTemplate.update(sql, masterParams(m));
    }

    public int updateMaster(FishboneMasterSaveRequest m) {
        String sql = """
                UPDATE gen_tl_fishbonemst SET
                    fism_flid = :fism_flid,
                    fism_elementid = :fism_elementid,
                    fism_refdocid = :fism_refdocid,
                    fism_refdoctype = :fism_refdoctype,
                    fism_title = :fism_title,
                    fism_problem = :fism_problem,
                    fism_revisionno = :fism_revisionno,
                    fism_prepareddate = :fism_prepareddate,
                    fism_preparedby = :fism_preparedby,
                    fism_approveddate = :fism_approveddate,
                    fism_approvedby = :fism_approvedby,
                    fism_status = :fism_status,
                    fism_defect = :fism_defect,
                    fism_tempfield2 = :fism_tempfield2,
                    fism_tempfield3 = :fism_tempfield3,
                    fism_tempfield4 = :fism_tempfield4,
                    fism_tempfield5 = :fism_tempfield5,
                    fism_active = :fism_active,
                    fism_createdby = :fism_createdby,
                    fism_createdon = :fism_createdon,
                    fism_modifiedon = :fism_modifiedon
                WHERE fism_keyid = :fism_keyid
                """;
        return jdbcTemplate.update(sql, masterParams(m));
    }

    public int insertDetail(FishboneChildSaveRequest d) {
        String sql = """
                INSERT INTO gen_tl_fishbonedtl (
                    fisd_keyid, fisd_fism_keyid, fisd_cause, fisd_parentid,
                    fisd_orderno, fisd_levelno, fisd_tempfield1, fisd_tempfield2,
                    fisd_tempfield3, fisd_tempfield4, fisd_tempfield5,
                    fisd_active, fisd_createdby, fisd_createdon, fisd_modifiedon, fisd_remarks
                ) VALUES (
                    :fisd_keyid, :fisd_fism_keyid, :fisd_cause, :fisd_parentid,
                    :fisd_orderno, :fisd_levelno, :fisd_tempfield1, :fisd_tempfield2,
                    :fisd_tempfield3, :fisd_tempfield4, :fisd_tempfield5,
                    :fisd_active, :fisd_createdby, :fisd_createdon, :fisd_modifiedon, :fisd_remarks
                )
                """;
        return jdbcTemplate.update(sql, detailParams(d));
    }

    public int updateDetail(FishboneChildSaveRequest d) {
        String sql = """
                UPDATE gen_tl_fishbonedtl SET
                    fisd_fism_keyid = :fisd_fism_keyid,
                    fisd_cause = :fisd_cause,
                    fisd_parentid = :fisd_parentid,
                    fisd_orderno = :fisd_orderno,
                    fisd_levelno = :fisd_levelno,
                    fisd_tempfield1 = :fisd_tempfield1,
                    fisd_tempfield2 = :fisd_tempfield2,
                    fisd_tempfield3 = :fisd_tempfield3,
                    fisd_tempfield4 = :fisd_tempfield4,
                    fisd_tempfield5 = :fisd_tempfield5,
                    fisd_active = :fisd_active,
                    fisd_createdby = :fisd_createdby,
                    fisd_createdon = :fisd_createdon,
                    fisd_modifiedon = :fisd_modifiedon,
                    fisd_remarks = :fisd_remarks
                WHERE fisd_keyid = :fisd_keyid
                """;
        return jdbcTemplate.update(sql, detailParams(d));
    }

    public int updateDetailCause(String detailKeyId, String newCause) {
        String sql = "UPDATE gen_tl_fishbonedtl SET fisd_cause = :cause WHERE fisd_keyid = :id";
        return jdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("cause", newCause)
                        .addValue("id", detailKeyId)
        );
    }

    public int updateWorkOrderFishboneRef(String workOrderKeyId, String fishboneKeyId) {
        String sql = "UPDATE wom_tl_workorder_mst SET woms_fishbone_refno = :fbKey WHERE woms_keyid = :woKey";
        return jdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("fbKey", fishboneKeyId)
                        .addValue("woKey", workOrderKeyId)
        );
    }

    public java.util.List<java.util.Map<String, Object>> findChildren(String masterId, String parentId) {
        String sql = """
                SELECT d.fisd_cause,
                       d.fisd_parentid,
                       d.fisd_orderno,
                       d.fisd_levelno,
                       d.fisd_fism_keyid,
                       d.fisd_keyid
                  FROM gen_tl_fishbonemst m
                  JOIN gen_tl_fishbonedtl d ON m.fism_keyid = d.fisd_fism_keyid
                 WHERE m.fism_keyid = :masterId
                   AND d.fisd_parentid = :parentId
                ORDER BY d.fisd_orderno, d.fisd_cause
                """;
        var params = new MapSqlParameterSource()
                .addValue("masterId", masterId)
                .addValue("parentId", parentId);
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            java.util.Map<String, Object> m = new java.util.LinkedHashMap<>();
            m.put("fisdCause", rs.getString("fisd_cause"));
            m.put("fisdParentid", rs.getString("fisd_parentid"));
            m.put("fisdOrderno", rs.getInt("fisd_orderno"));
            m.put("fisdLevelno", rs.getInt("fisd_levelno"));
            m.put("fisdFismKeyid", rs.getString("fisd_fism_keyid"));
            m.put("fisdKeyid", rs.getString("fisd_keyid"));
            return m;
        });
    }

    public java.util.List<String> searchChildPath(String searchNode, String originalId) {
        String sql = """
                SELECT childpath
                  FROM vw_fishbone
                 WHERE 1 = 1
                   AND name = :searchNode
                   AND fishboneno = :originalId
                """;
        var params = new MapSqlParameterSource()
                .addValue("searchNode", searchNode)
                .addValue("originalId", originalId);
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getString("childpath"));
    }

    public int deleteDetail(String detailKeyId) {
        String sql = "DELETE FROM gen_tl_fishbonedtl WHERE fisd_keyid = :id";
        return jdbcTemplate.update(sql, new MapSqlParameterSource("id", detailKeyId));
    }

    public int deleteChildrenByParent(String parentId) {
        String sql = "DELETE FROM gen_tl_fishbonedtl WHERE fisd_parentid = :pid";
        return jdbcTemplate.update(sql, new MapSqlParameterSource("pid", parentId));
    }

    public FishboneMasterSaveRequest findMasterById(String keyId) {
        String sql = """
                SELECT fism_keyid, fism_flid, fism_elementid, fism_refdocid, fism_refdoctype,
                       fism_title, fism_problem, fism_revisionno, fism_prepareddate,
                       fism_preparedby, fism_approveddate, fism_approvedby, fism_status,
                       fism_defect, fism_tempfield2, fism_tempfield3, fism_tempfield4,
                       fism_tempfield5, fism_active, fism_createdby, fism_createdon,
                       fism_modifiedon
                  FROM gen_tl_fishbonemst
                 WHERE fism_keyid = :id
                """;
        var params = new MapSqlParameterSource("id", keyId);
        var list = jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            FishboneMasterSaveRequest m = new FishboneMasterSaveRequest();
            m.setFismKeyid(rs.getString("fism_keyid"));
            m.setFismFlid(rs.getString("fism_flid"));
            m.setFismElementid(rs.getString("fism_elementid"));
            m.setFismRefdocid(rs.getString("fism_refdocid"));
            m.setFismRefdoctype(rs.getString("fism_refdoctype"));
            m.setFismTitle(rs.getString("fism_title"));
            m.setFismProblem(rs.getString("fism_problem"));
            m.setFismRevisionno(rs.getString("fism_revisionno"));
            m.setFismPrepareddate(toLocal(rs.getTimestamp("fism_prepareddate")));
            m.setFismPreparedby(rs.getString("fism_preparedby"));
            m.setFismApproveddate(toLocal(rs.getTimestamp("fism_approveddate")));
            m.setFismApprovedby(rs.getString("fism_approvedby"));
            m.setFismStatus(rs.getString("fism_status"));
            m.setFismDefect(rs.getString("fism_defect"));
            m.setFismTempfield2(rs.getString("fism_tempfield2"));
            m.setFismTempfield3(rs.getString("fism_tempfield3"));
            m.setFismTempfield4(rs.getString("fism_tempfield4"));
            m.setFismTempfield5(rs.getString("fism_tempfield5"));
            m.setFismActive(rs.getString("fism_active"));
            m.setFismCreatedby(rs.getString("fism_createdby"));
            m.setFismCreatedon(toLocal(rs.getTimestamp("fism_createdon")));
            m.setFismModifiedon(toLocal(rs.getTimestamp("fism_modifiedon")));
            return m;
        });
        return list.isEmpty() ? null : list.get(0);
    }

    public java.util.List<java.util.Map<String, Object>> findReportGrid(String keyId) {
        String condKeyD = "";
        String condKeyC = "";
        if (keyId != null && !keyId.trim().isEmpty()) {
            condKeyD = " AND d.fisd_fism_keyid = :keyId ";
            condKeyC = " AND c.fisd_fism_keyid = :keyId ";
        }

        StringBuilder sql = new StringBuilder();
        sql.append(" WITH RECURSIVE tree (fisd_keyid, fisd_parentid, fisd_cause, lvl, path_ids) AS ( ");
        sql.append("   SELECT d.fisd_keyid, d.fisd_parentid, d.fisd_cause, 1 AS lvl, ");
        sql.append("          ARRAY[d.fisd_keyid::text]::text[] AS path_ids ");
        sql.append("     FROM gen_tl_fishbonedtl d ");
        sql.append("    WHERE d.fisd_parentid = 'FB001' ");
        sql.append(condKeyD);
        sql.append("   UNION ALL ");
        sql.append("   SELECT c.fisd_keyid, c.fisd_parentid, c.fisd_cause, t.lvl + 1 AS lvl, ");
        sql.append("          (t.path_ids || c.fisd_keyid::text)::text[] AS path_ids ");
        sql.append("     FROM gen_tl_fishbonedtl c ");
        sql.append("     JOIN tree t ON c.fisd_parentid = t.fisd_keyid ");
        sql.append("    WHERE t.lvl < 30 ");
        sql.append(condKeyC);
        sql.append("      AND NOT (c.fisd_keyid::text = ANY(t.path_ids::text[])) ");
        sql.append(" ) ");
        sql.append(" SELECT ");
        for (int i = 1; i <= 30; i++) {
            sql.append(" CASE WHEN lvl = ").append(i).append(" THEN fisd_cause END AS level_").append(i);
            if (i < 30) sql.append(", ");
        }
        sql.append(" FROM tree ");
        sql.append(" ORDER BY path_ids ");

        var params = new MapSqlParameterSource("keyId", keyId);
        return jdbcTemplate.queryForList(sql.toString(), params);
    }

    private MapSqlParameterSource masterParams(FishboneMasterSaveRequest m) {
        return new MapSqlParameterSource()
                .addValue("fism_keyid", m.getFismKeyid())
                .addValue("fism_flid", m.getFismFlid())
                .addValue("fism_elementid", m.getFismElementid())
                .addValue("fism_refdocid", m.getFismRefdocid())
                .addValue("fism_refdoctype", m.getFismRefdoctype())
                .addValue("fism_title", m.getFismTitle())
                .addValue("fism_problem", m.getFismProblem())
                .addValue("fism_revisionno", m.getFismRevisionno())
                .addValue("fism_prepareddate", toTs(m.getFismPrepareddate()))
                .addValue("fism_preparedby", m.getFismPreparedby())
                .addValue("fism_approveddate", toTs(m.getFismApproveddate()))
                .addValue("fism_approvedby", m.getFismApprovedby())
                .addValue("fism_status", m.getFismStatus())
                .addValue("fism_defect", m.getFismDefect())
                .addValue("fism_tempfield2", m.getFismTempfield2())
                .addValue("fism_tempfield3", m.getFismTempfield3())
                .addValue("fism_tempfield4", m.getFismTempfield4())
                .addValue("fism_tempfield5", m.getFismTempfield5())
                .addValue("fism_active", m.getFismActive())
                .addValue("fism_createdby", m.getFismCreatedby())
                .addValue("fism_createdon", toTs(m.getFismCreatedon()))
                .addValue("fism_modifiedon", toTs(m.getFismModifiedon()));
    }

    private MapSqlParameterSource detailParams(FishboneChildSaveRequest d) {
        return new MapSqlParameterSource()
                .addValue("fisd_keyid", d.getFisdKeyid())
                .addValue("fisd_fism_keyid", d.getFisdFismKeyid())
                .addValue("fisd_cause", d.getFisdCause())
                .addValue("fisd_parentid", d.getFisdParentid())
                .addValue("fisd_orderno", d.getFisdOrderno())
                .addValue("fisd_levelno", d.getFisdLevelno())
                .addValue("fisd_tempfield1", d.getFisdTempfield1())
                .addValue("fisd_tempfield2", d.getFisdTempfield2())
                .addValue("fisd_tempfield3", d.getFisdTempfield3())
                .addValue("fisd_tempfield4", d.getFisdTempfield4())
                .addValue("fisd_tempfield5", d.getFisdTempfield5())
                .addValue("fisd_active", d.getFisdActive())
                .addValue("fisd_createdby", d.getFisdCreatedby())
                .addValue("fisd_createdon", toTs(d.getFisdCreatedon()))
                .addValue("fisd_modifiedon", toTs(d.getFisdModifiedon()))
                .addValue("fisd_remarks", d.getFisdRemarks());
    }

    private Timestamp toTs(LocalDateTime ldt) {
        return ldt == null ? null : Timestamp.valueOf(ldt);
    }

    private LocalDateTime toLocal(Timestamp ts) {
        return ts == null ? null : ts.toLocalDateTime();
    }
}
