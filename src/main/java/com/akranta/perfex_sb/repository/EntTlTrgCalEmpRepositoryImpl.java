package com.akranta.perfex_sb.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class EntTlTrgCalEmpRepositoryImpl implements EntTlTrgCalEmpRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int countUniqueEmployeePopup(String etcmKeyid,
                                        String refdocid,
                                        String flid,
                                        String factoryId,
                                        boolean uniquePos,
                                        String empType,
                                        String empGender,
                                        String roleLevel,
                                        String filterCond) {

        SqlWithParams sql = buildPopupBaseSql(etcmKeyid, refdocid, flid, factoryId, uniquePos, empType, empGender, roleLevel);
        Query q = entityManager.createNativeQuery("SELECT COUNT(*) FROM ( " + sql.sql() + " ) t WHERE 1 = 1 " + safeCond(filterCond));
        applyParams(q, sql.params());
        Object cntObj = q.getSingleResult();
        return cntObj == null ? 0 : Integer.parseInt(cntObj.toString());
    }

    @Override
    public List<Object[]> findUniqueEmployeePopup(String etcmKeyid,
                                                  String refdocid,
                                                  String flid,
                                                  String factoryId,
                                                  boolean uniquePos,
                                                  String empType,
                                                  String empGender,
                                                  String roleLevel,
                                                  String filterCond,
                                                  Integer fromRow,
                                                  Integer toRow) {

        SqlWithParams sql = buildPopupBaseSql(etcmKeyid, refdocid, flid, factoryId, uniquePos, empType, empGender, roleLevel);

        StringBuilder pagedSql = new StringBuilder("""
                SELECT * FROM (
                  SELECT ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS slno, a.*
                  FROM ( %s ) a
                  WHERE 1 = 1 %s
                ) sub
                """.formatted(sql.sql(), safeCond(filterCond)));

        boolean hasPaging = toRow != null && toRow > 0;
        if (hasPaging) {
            pagedSql.append(" WHERE slno >= :fromRow AND slno <= :toRow");
        }

        Query q = entityManager.createNativeQuery(pagedSql.toString());
        applyParams(q, sql.params());

        if (hasPaging) {
            q.setParameter("fromRow", fromRow == null ? 1 : fromRow);
            q.setParameter("toRow", toRow);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();
        return rows == null ? new ArrayList<>() : rows;
    }

    private SqlWithParams buildPopupBaseSql(String etcmKeyid,
                                            String refdocid,
                                            String flid,
                                            String factoryId,
                                            boolean uniquePos,
                                            String empType,
                                            String empGender,
                                            String roleLevel) {
        if (hasText(refdocid)) {
            return buildRefdocPopupSql(etcmKeyid, refdocid);
        }
        return buildMainPopupSql(etcmKeyid, flid, factoryId, uniquePos, empType, empGender, roleLevel);
    }

    private SqlWithParams buildMainPopupSql(String etcmKeyid,
                                            String flid,
                                            String factoryId,
                                            boolean uniquePos,
                                            String empType,
                                            String empGender,
                                            String roleLevel) {

        String empTypeCase = """
                CASE e.empm_employeetype
                  WHEN 'R' THEN 'Employee'
                  WHEN 'M' THEN 'Manager'
                  WHEN 'C' THEN 'Contract'
                  WHEN 'A' THEN 'Asosciate'
                  WHEN 'B' THEN 'Badli'
                  ELSE ''
                END
                """;
        String genderCase = """
                CASE e.empm_gender
                  WHEN 'M' THEN 'Male'
                  WHEN 'F' THEN 'Female'
                  ELSE ''
                END
                """;
        String sessionCase = """
                CASE
                  WHEN etce.etce_etcs_keyid = '{}' THEN ' '
                  ELSE etcs.etcs_name
                END
                """;
        String etcmDisplayCase = """
                CASE
                  WHEN etce.etce_keyid IS NULL THEN ' '
                  ELSE etcm.etcm_keyid
                END
                """;

        boolean hasKey = hasText(etcmKeyid);
        boolean hasFactory = hasText(factoryId);
        boolean needsUniquePosFilter = !hasFactory && uniquePos;
        boolean useEtcmParam = hasKey || needsUniquePosFilter;

        StringBuilder base = new StringBuilder();
        base.append("""
                SELECT DISTINCT
                  '' AS selctVal,
                  e.empm_keyid AS Empm_keyid,
                  e.empm_code AS EMPM_CODE,
                  e.empm_name AS EMPM_NAME,
                """);
        base.append("  ").append(empTypeCase).append(" AS EMPM_EMPLOYEETYPE_DESC,\n");
        base.append("  ").append(genderCase).append(" AS EMPM_GENDER_DESC,\n");
        base.append("  ").append(sessionCase).append(" AS Ses,\n");
        base.append("""
                  r.role_name AS ROLE_NAME,
                  etcq.etcq_currentlevel AS ETCQ_CURRENTLEVEL,
                  MAX(etcq.etcq_currentleveldate) AS ETCQ_CURRENTLEVELDATE,
                  c.sect_keyid AS SECTIONID,
                  c.sect_name AS SECT_NAME,
                  c.cell_keyid AS CELLID,
                  c.cell_name AS CELL_NAME,
                  e.empm_roleid AS roleid,
                """);
        base.append("  ").append(etcmDisplayCase).append(" AS ETCM_KEYID_DISPLAY,\n");
        base.append("  etce.etce_keyid AS ETCEKEYID\n");
        base.append("FROM gen_tl_employeemst e\n");
        base.append("JOIN gen_tl_fnlnroleteam frt ON e.empm_keyid = frt.frt_empm_keyid\n");
        base.append("JOIN gen_vw_fnln c ON frt.frt_fnln_keyid = c.fnln_keyid\n");
        base.append("LEFT JOIN gen_tl_rolemst r ON e.empm_roleid = r.role_keyid\n");
        base.append("LEFT JOIN ent_tl_trgcalquad etcq ON etcq.etcq_empm_keyid = frt.frt_empm_keyid\n");

        if (hasKey) {
            base.append("LEFT JOIN ent_tl_trgcalmst etcm ON etcm.etcm_keyid = :etcmKeyid\n");
        } else {
            base.append("LEFT JOIN ent_tl_trgcalmst etcm ON 1 = 0\n");
        }

        if (hasKey) {
            base.append("LEFT JOIN ent_tl_trgcalemp etce ON etce.etce_empm_keyid = e.empm_keyid AND etce.etce_etcm_keyid = etcm.etcm_keyid\n");
            base.append("LEFT JOIN ent_tl_trgcalsession etcs ON etcs.etcs_keyid = etce.etce_etcs_keyid AND etcq.etcq_topicid = etcm.etcm_topicid\n");
        } else {
            base.append("LEFT JOIN ent_tl_trgcalemp etce ON etce.etce_empm_keyid = e.empm_keyid\n");
            base.append("LEFT JOIN ent_tl_trgcalsession etcs ON etcs.etcs_keyid = etce.etce_etcs_keyid\n");
        }

        if (hasFactory) {
            base.append("JOIN gen_mv_flidhierarchy h ON c.fnln_keyid = h.flid\n");
        }

        base.append("WHERE e.empm_active = 'Y'\n");

        if (hasFactory) {
            base.append("  AND position((SELECT fnln_keyid FROM gen_tl_functionallocn WHERE fnln_originalid = :factoryId) in (h.parentflids || '/' || h.flid)) > 0\n");
        } else {
            if (!uniquePos && hasText(flid)) {
                base.append("  AND c.fnln_keyid = :flid\n");
            } else if (needsUniquePosFilter) {
                base.append("  AND e.empm_roleid IN (\n");
                base.append("        SELECT etcu_role_keyid FROM ent_tl_trgcalunqp WHERE etcu_etcm_keyid = :etcmKeyid\n");
                base.append("      )\n");
            }
        }

        if (hasText(empType)) {
            base.append("  AND e.empm_employeetype = :empType\n");
        }
        if (hasText(empGender)) {
            base.append("  AND e.empm_gender = :empGender\n");
        }
        if (hasText(roleLevel)) {
            base.append("  AND frt.frt_role_keyid = :roleLevel\n");
        }

        base.append("GROUP BY\n");
        base.append("  e.empm_name,\n");
        base.append("  e.empm_code,\n");
        base.append("  e.empm_keyid,\n");
        base.append("  ").append(empTypeCase).append(",\n");
        base.append("  ").append(genderCase).append(",\n");
        base.append("  ").append(sessionCase).append(",\n");
        base.append("  r.role_name,\n");
        base.append("  etcq.etcq_currentlevel,\n");
        base.append("  c.sect_name,\n");
        base.append("  c.sect_keyid,\n");
        base.append("  c.cell_keyid,\n");
        base.append("  c.cell_name,\n");
        base.append("  e.empm_roleid,\n");
        base.append("  ").append(etcmDisplayCase).append(",\n");
        base.append("  etce.etce_keyid\n");
        base.append("ORDER BY etce.etce_keyid\n");

        Map<String, Object> paramValues = new HashMap<>();
        if (useEtcmParam) {
            paramValues.put("etcmKeyid", hasKey ? etcmKeyid.trim() : "");
        }
        if (hasFactory) {
            paramValues.put("factoryId", factoryId.trim());
        }
        if (!uniquePos && hasText(flid)) {
            paramValues.put("flid", flid.trim());
        }
        if (hasText(empType)) {
            paramValues.put("empType", empType.trim());
        }
        if (hasText(empGender)) {
            paramValues.put("empGender", empGender.trim());
        }
        if (hasText(roleLevel)) {
            paramValues.put("roleLevel", roleLevel.trim());
        }

        String wrapped = """
                SELECT *
                  FROM (
                    SELECT
                      selctVal,
                      Empm_keyid,
                      EMPM_CODE,
                      EMPM_NAME,
                      EMPM_EMPLOYEETYPE_DESC,
                      EMPM_GENDER_DESC,
                      Ses,
                      ROLE_NAME,
                      ETCQ_CURRENTLEVEL,
                      ETCQ_CURRENTLEVELDATE,
                      SECTIONID,
                      SECT_NAME,
                      CELLID,
                      CELL_NAME,
                      roleid,
                      ETCM_KEYID_DISPLAY,
                      ETCEKEYID
                    FROM (
                      SELECT base.*,
                             ROW_NUMBER() OVER (
                               PARTITION BY base.Empm_keyid, base.ETCM_KEYID_DISPLAY
                               ORDER BY base.ETCQ_CURRENTLEVEL DESC,
                                        base.ETCQ_CURRENTLEVELDATE DESC,
                                        base.ETCEKEYID DESC
                             ) AS LVL_RN
                      FROM (
                        %s
                      ) base
                    ) lvl
                    WHERE lvl.LVL_RN = 1
                  ) popup_base
                """.formatted(base);

        return new SqlWithParams(wrapped, paramValues);
    }

    private SqlWithParams buildRefdocPopupSql(String etcmKeyid, String refdocid) {
        String sessionCase = """
                  CASE
                    WHEN etce.etce_etcs_keyid = '{}' THEN ' '
                    ELSE etcs.etcs_name
                  END
                """;
        String etcmDisplayCase = """
                  CASE
                    WHEN etce.etce_keyid IS NULL THEN ' '
                    ELSE etcm.etcm_keyid
                  END
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("refdocid", refdocid.trim());

        boolean hasKey = hasText(etcmKeyid);
        StringBuilder base = new StringBuilder();
        base.append("""
                SELECT DISTINCT
                  '' AS selctVal,
                  e.empm_keyid AS keyid,
                  e.empm_code AS empcode,
                  e.empm_name AS name,
                """);
        base.append("  ").append(sessionCase).append(" AS ses,\n");
        base.append("""
                  r.role_name AS ROLENAME,
                  etcq.etcq_currentlevel AS currLevel,
                  MAX(etcq.etcq_currentleveldate) AS LastUpdate,
                  c.sect_keyid AS SECTIONID,
                  c.sect_name AS DMT,
                  c.cell_keyid AS CELLID,
                  c.cell_name AS JH,
                  e.empm_roleid AS roleid,
                """);
        base.append("  ").append(etcmDisplayCase).append(" AS ETCM_KEYID_DISPLAY,\n");
        base.append("  etce.etce_keyid AS ETCEKEYID\n");
        base.append("""
                FROM gen_tl_mom_groupmst    mgrm
                JOIN gen_tl_mom_groupdtl    mgrd ON mgrd.mgrd_mgrm_keyid = mgrm.mgrm_keyid
                JOIN gen_tl_employeemst     e    ON e.empm_keyid = mgrd.mgrd_empm_keyid
                LEFT JOIN gen_tl_rolemst    r    ON e.empm_roleid = r.role_keyid
                JOIN gen_tl_fnlnroleteam    frt  ON frt.frt_empm_keyid = mgrd.mgrd_empm_keyid
                JOIN gen_vw_fnln            c    ON c.fnln_keyid = frt.frt_fnln_keyid
                """);

        if (hasKey) {
            base.append("LEFT JOIN ent_tl_trgcalmst  etcm ON etcm.etcm_keyid = :etcmKeyid\n");
            params.put("etcmKeyid", etcmKeyid.trim());
        } else {
            base.append("LEFT JOIN ent_tl_trgcalmst  etcm ON 1 = 0\n");
        }

        base.append("""
                LEFT JOIN ent_tl_trgcalemp  etce ON etce.etce_empm_keyid = e.empm_keyid
                                                 AND etce.etce_etcm_keyid = etcm.etcm_keyid
                LEFT JOIN ent_tl_trgcalquad etcq ON etcq.etcq_empm_keyid = mgrd.mgrd_empm_keyid
                                                 AND etcq.etcq_topicid = etcm.etcm_topicid
                LEFT JOIN ent_tl_trgcalsession etcs ON etcs.etcs_keyid = etce.etce_etcs_keyid
                WHERE mgrd.mgrd_mgrm_keyid = :refdocid
                GROUP BY
                  e.empm_name,
                  e.empm_code,
                  e.empm_keyid,
                  r.role_name,
                  c.sect_name,
                  c.sect_keyid,
                  c.cell_keyid,
                  c.cell_name,
                  e.empm_roleid,
        """);
        base.append("  ").append(sessionCase).append(",\n");
        base.append("""
                  etcq.etcq_currentlevel,
                """);
        base.append("  ").append(etcmDisplayCase).append(",\n");
        base.append("""
                  etce.etce_keyid
                ORDER BY etce.etce_keyid
                """);

        String wrapped = "SELECT * FROM ( " + base + " ) popup_base";
        return new SqlWithParams(wrapped, params);
    }

    private void applyParams(Query query, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return;
        }
        params.forEach(query::setParameter);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String safeCond(String cond) {
        return cond == null ? "" : cond;
    }

    private record SqlWithParams(String sql, Map<String, Object> params) {
    }
}
