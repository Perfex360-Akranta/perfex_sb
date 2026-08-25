package com.akranta.perfex_sb.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class EntTlTragcalmstRepositoryImpl implements EntTlTragcalmstRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String BASE_GRID_SQL = """
            SELECT DISTINCT
              etcm_keyid,
              etcm_dmt,
              etcm_jh,
              etcm_flid,
              etcm_location,
              etcm_createdatetime,
              etcm_anchoredby,
              etcm_anchoredby AS anchoredbyid,
              topi_name,
              etcm_topicid,
              tcat_name,
              etcm_topiccategory,
              CASE etcm_function
                  WHEN 'NB'  THEN 'Need Basic'
                  WHEN 'SD'  THEN 'Section D'
                  WHEN 'SI'  THEN 'Skill Index'
                  WHEN 'KU'  THEN 'Knowledge Upgradation'
                  WHEN 'KSA' THEN 'KSA (GAP Based)'
                  WHEN 'RT'  THEN 'Refresher Training'
                  WHEN 'EHS' THEN 'EHS'
                  ELSE NULL
              END AS idenfiedthg,
              etcm_function,
              trdm_name,
              etcm_trainingfunction,
              venu_name,
              etcm_venue,
              CASE
                  WHEN etcm_general   = 'Y' THEN 'General'
                  WHEN etcm_uniquepos = 'Y' THEN 'Unique Position'
                  WHEN etcm_msd       = 'Y' THEN 'MSD'
                  ELSE ''
              END AS uniqpose,
              CASE
                  WHEN etcm_general   = 'Y' THEN 'GN'
                  WHEN etcm_uniquepos = 'Y' THEN 'UQ'
                  WHEN etcm_msd       = 'Y' THEN 'MS'
                  ELSE ''
              END AS uniqposeid,
              etcm_caldate,
              etcm_permittedstrength,
              etcm_max_duration,
              CASE WHEN etcm_assessmentrequired = 'Y' THEN 'YES' ELSE 'NO' END AS assessment_text,
              etcm_assessmentrequired,
              CASE WHEN etcm_materialready = 'Y' THEN 'YES' ELSE 'NO' END AS material_text,
              etcm_materialready,
              CASE WHEN etcm_markbased = 'Y' THEN 'YES' ELSE 'NO' END AS markbased_text,
              etcm_markbased,
              (
                  SELECT string_agg(r.role_name, ',' ORDER BY r.role_name)
                  FROM ent_tl_trgcalunqp u
                  LEFT JOIN gen_tl_rolemst r ON u.etcu_role_keyid = r.role_keyid
                  WHERE u.etcu_etcm_keyid = etcm_keyid
              ) AS role_name,
              '' AS uniqpos,
              '' AS employeeadd,
              '' AS empattednce,
              (
                  SELECT string_agg(e2.empm_name, ',' ORDER BY e2.empm_name)
                  FROM gen_tl_employeemst e2
                  JOIN ent_tl_trgcalemp et2 ON et2.etce_empm_keyid = e2.empm_keyid
                  WHERE et2.etce_etcm_keyid = etcm_keyid
              ) AS plannedempm_name,
              CASE WHEN etcm_tempfield6 = 'Y' THEN 'YES' ELSE 'NO' END AS assemntcompl,
              etcm_tempfield6,
              CASE WHEN etcm_chkcompleted = 'Y' THEN 'YES' ELSE 'NO' END AS trncompl,
              etcm_chkcompleted AS trncomplid,
              CASE WHEN etcm_chkcompleted = 'Y' THEN etcm_completeddate ELSE NULL END AS compltdate,
              empm_name || '-' || empm_code AS completedby,
              etcm_completedby,
              etcm_rating,
              etcm_rating AS ratingid,
              etcm_comments,
              '' AS filemgr,
              COALESCE(plan_tbl.planed, 0) AS planedemp,
              COALESCE(attend_tbl.attend, 0) AS attndempl,
              COALESCE(ROUND((COALESCE(attend_tbl.attend, 0)::numeric /
                       (CASE WHEN COALESCE(plan_tbl.planed, 0) = 0 THEN 1 ELSE COALESCE(plan_tbl.planed, 0) END)::numeric) * 100, 2), 0) AS adherence,
              COALESCE(attend_tbl.attend * etcm_max_duration, 0) AS manhourse
            FROM ent_tl_trgcalmst
            LEFT JOIN ent_tl_topicmst ON topi_keyid = etcm_topicid
            LEFT JOIN ent_tl_venuemst ON venu_keyid = etcm_venue
            LEFT JOIN ent_tl_topiccategorymst ON tcat_keyid = etcm_topiccategory
            LEFT JOIN gen_tl_trademst ON trdm_keyid = etcm_trainingfunction
            LEFT JOIN gen_tl_employeemst ON empm_keyid = etcm_completedby
            LEFT JOIN (
                SELECT etce_etcm_keyid, COUNT(*) AS planed
                FROM ent_tl_trgcalemp
                GROUP BY etce_etcm_keyid
            ) plan_tbl ON plan_tbl.etce_etcm_keyid = etcm_keyid
            LEFT JOIN (
                SELECT etca_etcm_keyid, COUNT(*) AS attend
                FROM ent_tl_trgcalempatscore
                WHERE etca_prsentabsent = 'P'
                GROUP BY etca_etcm_keyid
            ) attend_tbl ON attend_tbl.etca_etcm_keyid = etcm_keyid
            WHERE etcm_keyid = :etcmKeyid
            """;

    @Override
    public int countGridCalendar(String etcmKeyid, String filterCond) {
        String countSql = "SELECT COUNT(*) FROM (" + BASE_GRID_SQL + ") t WHERE 1 = 1 " + safeCond(filterCond);
        Object cntObj = entityManager.createNativeQuery(countSql)
                .setParameter("etcmKeyid", etcmKeyid)
                .getSingleResult();
        return cntObj == null ? 0 : Integer.parseInt(cntObj.toString());
    }

    @Override
    public List<Object[]> findGridCalendar(String etcmKeyid, String filterCond, Integer fromRow, Integer toRow) {
        StringBuilder pagedSql = new StringBuilder("""
                SELECT * FROM (
                  SELECT ROW_NUMBER() OVER (ORDER BY etcm_keyid DESC) AS slno, a.*
                  FROM ( %s ) a
                  WHERE 1 = 1 %s
                ) sub
                """.formatted(BASE_GRID_SQL, safeCond(filterCond)));

        boolean hasPaging = toRow != null && toRow > 0;
        if (hasPaging) {
            pagedSql.append(" WHERE slno >= :fromRow AND slno <= :toRow");
        }

        var q = entityManager.createNativeQuery(pagedSql.toString())
                .setParameter("etcmKeyid", etcmKeyid);

        if (hasPaging) {
            q.setParameter("fromRow", fromRow == null ? 1 : fromRow);
            q.setParameter("toRow", toRow);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();
        return rows == null ? new ArrayList<>() : rows;
    }

    private String safeCond(String cond) {
        return cond == null ? "" : cond;
    }

    private static final String BASE_GRID_MODIFY_SQL = """
    SELECT DISTINCT
      etcm.etcm_keyid,
      etcm.etcm_dmt,
      etcm.etcm_jh,
      etcm.etcm_flid,
      etcm.etcm_location,
      etcm.etcm_createdatetime,
      etcm.etcm_anchoredby,
      etcm.etcm_anchoredby AS anchoredbyid,
      topi.topi_name,
      etcm.etcm_topicid,
      tcat.tcat_name,
      etcm.etcm_topiccategory,
      CASE etcm.etcm_function
          WHEN 'NB'  THEN 'Need Basic'
          WHEN 'SD'  THEN 'Section D'
          WHEN 'SI'  THEN 'Skill Index'
          WHEN 'KU'  THEN 'Knowledge Upgradation'
          WHEN 'KSA' THEN 'KSA (GAP Based)'
          WHEN 'RT'  THEN 'Refresher Training'
          WHEN 'EHS' THEN 'EHS'
          ELSE NULL
      END AS idenfiedthg,
      etcm.etcm_function,
      trdm.trdm_name,
      etcm.etcm_trainingfunction,
      venu.venu_name,
      etcm.etcm_venue,
      CASE WHEN etcm.etcm_general='Y' THEN 'General'
           WHEN etcm.etcm_uniquepos='Y' THEN 'Unique Position'
           WHEN etcm.etcm_msd='Y' THEN 'MSD' ELSE '' END AS uniqpose,
      CASE WHEN etcm.etcm_general='Y' THEN 'GN'
           WHEN etcm.etcm_uniquepos='Y' THEN 'UQ'
           WHEN etcm.etcm_msd='Y' THEN 'MS' ELSE '' END AS uniqposeid,
      etcm.etcm_caldate,
      etcm.etcm_permittedstrength,
      etcm.etcm_max_duration,
      CASE WHEN etcm.etcm_assessmentrequired='Y' THEN 'YES' ELSE 'NO' END AS assessmentrequiredtxt,
      etcm.etcm_assessmentrequired,
      CASE WHEN etcm.etcm_materialready='Y' THEN 'YES' ELSE 'NO' END AS materialreadytxt,
      etcm.etcm_materialready,
      CASE WHEN etcm.etcm_markbased='Y' THEN 'YES' ELSE 'NO' END AS markbasedtxt,
      etcm.etcm_markbased,
      (SELECT string_agg(r.role_name, ',' ORDER BY r.role_name)
         FROM ent_tl_trgcalunqp u
         LEFT JOIN gen_tl_rolemst r ON u.etcu_role_keyid = r.role_keyid
        WHERE u.etcu_etcm_keyid = etcm.etcm_keyid) AS role_name,
      '' AS uniqpos,
      '' AS employeeadd,
      '' AS empattednce,
      (SELECT string_agg(e2.empm_name, ',' ORDER BY e2.empm_name)
         FROM gen_tl_employeemst e2
         JOIN ent_tl_trgcalemp ce ON ce.etce_empm_keyid = e2.empm_keyid
        WHERE ce.etce_etcm_keyid = etcm.etcm_keyid) AS plannedempm_name,
      CASE WHEN etcm.etcm_tempfield6='Y' THEN 'YES' ELSE 'NO' END AS assemntcompl,
      etcm.etcm_tempfield6,
      CASE WHEN etcm.etcm_chkcompleted='Y' THEN 'YES' ELSE 'NO' END AS trncompl,
      etcm.etcm_chkcompleted AS trncomplid,
      CASE WHEN etcm.etcm_chkcompleted='Y' THEN etcm.etcm_completeddate ELSE NULL END AS compltdate,
      (emp.empm_name || '-' || emp.empm_code) AS completedby,
      etcm.etcm_completedby,
      etcm.etcm_rating,
      etcm.etcm_rating AS ratingid,
      etcm.etcm_comments,
      '' AS filemgr,
      COALESCE(plan.planed,0) AS planed,
      COALESCE(att.attend,0) AS attend,
      COALESCE(ROUND((COALESCE(att.attend,0)::numeric /
             (CASE WHEN COALESCE(plan.planed,0)=0 THEN 1 ELSE COALESCE(plan.planed,0) END)::numeric) * 100, 2), 0) AS adherence,
      COALESCE(att.attend * etcm.etcm_max_duration, 0) AS manhourse
    FROM ent_tl_trgcalmst etcm
    LEFT JOIN ent_tl_topicmst topi ON topi.topi_keyid = etcm.etcm_topicid
    LEFT JOIN ent_tl_topiccategorymst tcat ON tcat.tcat_keyid = etcm.etcm_topiccategory
    LEFT JOIN gen_tl_trademst trdm ON trdm.trdm_keyid = etcm.etcm_trainingfunction
    LEFT JOIN gen_tl_employeemst emp ON emp.empm_keyid = etcm.etcm_completedby
    LEFT JOIN ent_tl_venuemst venu ON venu.venu_keyid = etcm.etcm_venue
    JOIN gen_mv_flidhierarchy ON flid = etcm.etcm_flid
    LEFT JOIN (SELECT etce_etcm_keyid, COUNT(*) AS planed FROM ent_tl_trgcalemp GROUP BY etce_etcm_keyid) plan
           ON plan.etce_etcm_keyid = etcm.etcm_keyid
    LEFT JOIN (SELECT etca_etcm_keyid, COUNT(*) AS attend FROM ent_tl_trgcalempatscore WHERE etca_prsentabsent='P' GROUP BY etca_etcm_keyid) att
           ON att.etca_etcm_keyid = etcm.etcm_keyid
    WHERE etcm.etcm_chkcompleted = 'N' %s
    """;

private void bindParams(Query q, Map<String,Object> params) {
    if (params != null) params.forEach(q::setParameter);
}

@Override
public int countGridCalendarModify(String condSql, Map<String,Object> params) {
    String sql = "SELECT COUNT(*) FROM (" + BASE_GRID_MODIFY_SQL.formatted(condSql) + ") t";
    Object cnt = entityManager.createNativeQuery(sql).unwrap(Query.class)
            .setHint("org.hibernate.readOnly", true)
            .unwrap(Query.class);
    Query q = entityManager.createNativeQuery(sql);
    bindParams(q, params);
    Object val = q.getSingleResult();
    return val == null ? 0 : Integer.parseInt(val.toString());
}

@Override
    public List<Object[]> findGridCalendarModify(String condSql, Map<String,Object> params, Integer fromRow, Integer pageSize) {
        int offset = Math.max(0, (fromRow == null ? 1 : fromRow) - 1);
        int limit  = (pageSize == null || pageSize <= 0) ? 100 : Math.min(pageSize, 100);
        String sql = BASE_GRID_MODIFY_SQL.formatted(condSql) + " ORDER BY etcm.etcm_keyid DESC LIMIT :limit OFFSET :offset";
        Query q = entityManager.createNativeQuery(sql);
        bindParams(q, params);
        q.setParameter("limit", limit);
        q.setParameter("offset", offset);
        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();
        return rows == null ? new ArrayList<>() : rows;
    }

    private static final String BASE_GRID_VIEW_SQL = """
    SELECT
      etcm.etcm_keyid,
      etcm.etcm_dmt,
      etcm.etcm_jh,
      etcm.etcm_flid,
      etcm.etcm_location,
      etcm.etcm_createdatetime,
      etcm.etcm_anchoredby,
      etcm.etcm_anchoredby AS anchoredbyid,
      topi.topi_name,
      etcm.etcm_topicid,
      tcat.tcat_name,
      etcm.etcm_topiccategory,
      CASE etcm.etcm_function
          WHEN 'NB'  THEN 'Need Basic'
          WHEN 'SD'  THEN 'Section D'
          WHEN 'SI'  THEN 'Skill Index'
          WHEN 'KU'  THEN 'Knowledge Upgradation'
          WHEN 'KSA' THEN 'KSA (GAP Based)'
          WHEN 'RT'  THEN 'Refresher Training'
          WHEN 'EHS' THEN 'EHS'
          ELSE NULL
      END AS idenfiedthg,
      etcm.etcm_function,
      trdm.trdm_name,
      etcm.etcm_trainingfunction,
      venu.venu_name,
      etcm.etcm_venue,
      CASE WHEN etcm.etcm_general='Y' THEN 'General'
           WHEN etcm.etcm_uniquepos='Y' THEN 'Unique Position'
           WHEN etcm.etcm_msd='Y' THEN 'MSD' ELSE '' END AS uniqpose,
      CASE WHEN etcm.etcm_general='Y' THEN 'GN'
           WHEN etcm.etcm_uniquepos='Y' THEN 'UQ'
           WHEN etcm.etcm_msd='Y' THEN 'MS' ELSE '' END AS uniqposeid,
      (SELECT string_agg(r.role_name, ',' ORDER BY r.role_name)
         FROM ent_tl_trgcalunqp u
         LEFT JOIN gen_tl_rolemst r ON u.etcu_role_keyid = r.role_keyid
        WHERE u.etcu_etcm_keyid = etcm.etcm_keyid) AS role_name,
      etcm.etcm_caldate,
      (SELECT string_agg(s.etcs_name, ',' ORDER BY s.etcs_name)
         FROM ent_tl_trgcalsession s
        WHERE s.etcs_etcm_keyid = etcm.etcm_keyid) AS sessions,
      etcm.etcm_permittedstrength,
      etcm.etcm_max_duration,
      (SELECT string_agg(f.ftym_name, ',' ORDER BY f.ftym_name)
         FROM ent_tl_trgfaculty tf
         JOIN ent_tl_facultymst f ON f.ftym_keyid = tf.etcf_facultyid
        WHERE tf.etcf_etcm_keyid = etcm.etcm_keyid) AS ftym_name,
      CASE WHEN etcm.etcm_assessmentrequired='Y' THEN 'YES' ELSE 'NO' END AS assessmentrequiredtxt,
      etcm.etcm_assessmentrequired,
      CASE WHEN etcm.etcm_materialready='Y' THEN 'YES' ELSE 'NO' END AS materialreadytxt,
      etcm.etcm_materialready,
      CASE WHEN etcm.etcm_markbased='Y' THEN 'YES' ELSE 'NO' END AS markbasedtxt,
      etcm.etcm_markbased,
      '' AS uniqpos,
      '' AS employeeadd,
      '' AS empattednce,
      CASE WHEN etcm.etcm_tempfield6='Y' THEN 'YES' ELSE 'NO' END AS assemntcompl,
      etcm.etcm_tempfield6,
      CASE WHEN etcm.etcm_chkcompleted='Y' THEN 'YES' ELSE 'NO' END AS trncompl,
      etcm.etcm_chkcompleted AS trncomplid,
      CASE WHEN etcm.etcm_chkcompleted='Y' THEN etcm.etcm_completeddate ELSE NULL END AS compltdate,
      (emp.empm_name || '-' || emp.empm_code) AS completedby,
      etcm.etcm_completedby,
      (SELECT string_agg(e2.empm_name, ',' ORDER BY e2.empm_name)
         FROM gen_tl_employeemst e2
         JOIN ent_tl_trgcalemp ce ON ce.etce_empm_keyid = e2.empm_keyid
        WHERE ce.etce_etcm_keyid = etcm.etcm_keyid) AS plannedempm_name,
      (SELECT string_agg(e3.empm_name, ',' ORDER BY e3.empm_name)
         FROM gen_tl_employeemst e3
         JOIN ent_tl_trgcalempatscore ca ON ca.etca_etce_empm_keyid = e3.empm_keyid
        WHERE ca.etca_etcm_keyid = etcm.etcm_keyid
          AND ca.etca_prsentabsent = 'P') AS presentempm_name,
      COALESCE(plan.planed,0) AS planed,
      COALESCE(att.attend,0) AS attend,
      COALESCE(ROUND((COALESCE(att.attend,0)::numeric /
             (CASE WHEN COALESCE(plan.planed,0)=0 THEN 1 ELSE COALESCE(plan.planed,0) END)::numeric) * 100, 2), 0) AS adherence,
      COALESCE(att.attend * etcm.etcm_max_duration, 0) AS manhourse,
      COALESCE(plan.planed - att.attend, 0) AS absent,
      etcm.etcm_rating,
      etcm.etcm_rating AS ratingid,
      etcm.etcm_comments,
      '' AS filemgr
    FROM ent_tl_trgcalmst etcm
    LEFT JOIN ent_tl_topicmst topi ON topi.topi_keyid = etcm.etcm_topicid
    LEFT JOIN ent_tl_topiccategorymst tcat ON tcat.tcat_keyid = etcm.etcm_topiccategory
    LEFT JOIN gen_tl_trademst trdm ON trdm.trdm_keyid = etcm.etcm_trainingfunction
    LEFT JOIN gen_tl_employeemst emp ON emp.empm_keyid = etcm.etcm_completedby
    LEFT JOIN ent_tl_venuemst venu ON venu.venu_keyid = etcm.etcm_venue
    JOIN gen_mv_flidhierarchy ON flid = etcm.etcm_flid
    LEFT JOIN (SELECT etce_etcm_keyid, COUNT(*) AS planed FROM ent_tl_trgcalemp GROUP BY etce_etcm_keyid) plan
           ON plan.etce_etcm_keyid = etcm.etcm_keyid
    LEFT JOIN (SELECT etca_etcm_keyid, COUNT(*) AS attend FROM ent_tl_trgcalempatscore WHERE etca_prsentabsent='P' GROUP BY etca_etcm_keyid) att
           ON att.etca_etcm_keyid = etcm.etcm_keyid
    WHERE etcm.etcm_chkcompleted = 'Y' %s
    """;

    @Override
    public int countGridCalendarView(String condSql, Map<String,Object> params) {
        String sql = "SELECT COUNT(*) FROM (" + BASE_GRID_VIEW_SQL.formatted(condSql) + ") t";
        Query q = entityManager.createNativeQuery(sql);
        bindParams(q, params);
        Object val = q.getSingleResult();
        return val == null ? 0 : Integer.parseInt(val.toString());
    }

    @Override
    public List<Object[]> findGridCalendarView(String condSql, Map<String,Object> params, Integer fromRow, Integer pageSize) {
        int offset = Math.max(0, (fromRow == null ? 1 : fromRow) - 1);
        int limit  = (pageSize == null || pageSize <= 0) ? 100 : Math.min(pageSize, 100);
        String sql = BASE_GRID_VIEW_SQL.formatted(condSql) + " ORDER BY etcm.etcm_keyid DESC LIMIT :limit OFFSET :offset";
        Query q = entityManager.createNativeQuery(sql);
        bindParams(q, params);
        q.setParameter("limit", limit);
        q.setParameter("offset", offset);
        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();
        return rows == null ? new ArrayList<>() : rows;
    }

}
