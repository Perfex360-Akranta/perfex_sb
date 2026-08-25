package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.EntTlTtgCalEmpatScore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class EntTlTtgCalEmpatScoreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String BASE_ATTENDANCE_SQL = """
            SELECT DISTINCT
                ETCM.ETCM_KEYID      AS hdnetcaetcmkeyid,
                ETCA.ETCA_KEYID      AS hdnetcakeyid,
                ETCE.ETCE_KEYID      AS hdnetcaetcekeyid,
                ETCE.ETCE_EMPM_KEYID AS hdnetcaetceempmkeyid,
                ETCM.ETCM_TOPICID    AS hdntopicid,
                EMP.EMPM_NAME        AS empname,
                CASE EMP.EMPM_EMPLOYEETYPE
                    WHEN 'M' THEN 'Manager'
                    WHEN 'R' THEN 'Employee'
                    WHEN 'B' THEN 'Badli'
                    WHEN 'C' THEN 'Contract'
                    WHEN 'A' THEN 'Asosciate'
                    WHEN 'O' THEN 'Others'
                END AS empm_employeetype,
                CASE EMP.EMPM_GENDER
                    WHEN 'M' THEN 'Male'
                    WHEN 'F' THEN 'FEMALE'
                END AS empm_gender,
                
                CASE ETCA.ETCA_PRSENTABSENT
                    WHEN 'P' THEN 'PRESENT'
                    WHEN 'A' THEN 'ABSENT'
                END AS cmbetcapresentabsent,
                TO_CHAR(COALESCE(ETCA.ETCA_ATTDATE,ETCS.ETCS_SESSIONDATE),'DD-MON-YYYY') AS dteetcaadddate,
                ETCA.ETCA_SCORE::text AS txtetcascore,
                CASE ETCA.ETCA_RESULT
                    WHEN 'P' THEN 'Pass'
                    WHEN 'F' THEN 'Fail'
                END AS txtetcaresult,
                ETCA.ETCA_REMARKS AS txtetcaremarks,
                '' AS btnfilmanage
            FROM ENT_TL_TRGCALMST              ETCM
            JOIN ENT_TL_TRGCALEMP              ETCE
                 ON ETCM.ETCM_KEYID = ETCE.ETCE_ETCM_KEYID
            JOIN ENT_TL_TRGCALSESSION             ETCS
                 ON ETCS.ETCS_KEYID = ETCE.ETCE_ETCS_KEYID
            JOIN GEN_TL_EMPLOYEEMST            EMP
                 ON ETCE.ETCE_EMPM_KEYID = EMP.EMPM_KEYID
           
            LEFT JOIN ENT_TL_TRGCALEMPATSCORE  ETCA
                 ON ETCA.ETCA_ETCE_KEYID = ETCE.ETCE_KEYID
            WHERE ETCM.ETCM_KEYID = :etcmKeyid
            """;

    public Optional<EntTlTtgCalEmpatScore> findById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(entityManager.find(EntTlTtgCalEmpatScore.class, id));
    }

    public EntTlTtgCalEmpatScore save(EntTlTtgCalEmpatScore entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getEtcaKeyid() == null || entity.getEtcaKeyid().isBlank()) {
            entityManager.persist(entity);
            return entity;
        }
        return entityManager.merge(entity);
    }

    public List<EntTlTtgCalEmpatScore> saveAll(List<EntTlTtgCalEmpatScore> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<EntTlTtgCalEmpatScore> saved = new ArrayList<>();
        for (EntTlTtgCalEmpatScore e : entities) {
            saved.add(save(e));
        }
        return saved;
    }

    public int countEmployeeAttendance(String etcmKeyid, String filterCond) {
        String countSql = "SELECT COUNT(*) FROM (" + BASE_ATTENDANCE_SQL + ") t WHERE 1 = 1 " + safeCond(filterCond);
        Object cntObj = entityManager.createNativeQuery(countSql)
                .setParameter("etcmKeyid", etcmKeyid)
                .getSingleResult();
        return cntObj == null ? 0 : Integer.parseInt(cntObj.toString());
    }

    public List<Object[]> findEmployeeAttendance(String etcmKeyid, String filterCond, Integer fromRow, Integer toRow) {
        StringBuilder pagedSql = new StringBuilder("""
                SELECT * FROM (
                  SELECT ROW_NUMBER() OVER (ORDER BY a.hdnetcakeyid) AS slno, a.*
                  FROM ( %s ) a
                  WHERE 1 = 1 %s
                ) sub
                """.formatted(BASE_ATTENDANCE_SQL, safeCond(filterCond)));

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

    public int countEmployeeAttendanceGrid(String etcmKeyid, String filterCond) {
        String countSql = "SELECT COUNT(*) FROM (" + BASE_ATTENDANCE_SQL_GRID + ") t WHERE 1 = 1 "
                + safeCond(filterCond);
        Object cntObj = entityManager.createNativeQuery(countSql)
                .setParameter("etcmKeyid", etcmKeyid)
                .getSingleResult();
        return cntObj == null ? 0 : Integer.parseInt(cntObj.toString());
    }

    public List<Object[]> findEmployeeAttendanceGrid(String etcmKeyid, String filterCond, Integer fromRow,
            Integer toRow) {
        StringBuilder pagedSql = new StringBuilder("""
                SELECT * FROM (
                  SELECT ROW_NUMBER() OVER (ORDER BY a.hdnetcakeyid) AS slno, a.*
                  FROM ( %s ) a
                  WHERE 1 = 1 %s
                ) sub
                """.formatted(BASE_ATTENDANCE_SQL_GRID, safeCond(filterCond)));

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

    private static final String BASE_ATTENDANCE_SQL_GRID = """
            SELECT DISTINCT
                ETCM.ETCM_KEYID      AS hdnetcaetcmkeyid,
                ETCA.ETCA_KEYID      AS hdnetcakeyid,
                ETCE.ETCE_KEYID      AS hdnetcaetcekeyid,
                ETCE.ETCE_EMPM_KEYID AS hdnetcaetceempmkeyid,
                ETCM.ETCM_TOPICID    AS hdntopicid,
                EMP.EMPM_NAME        AS empname,
                CASE EMP.EMPM_EMPLOYEETYPE
                    WHEN 'M' THEN 'Manager'
                    WHEN 'R' THEN 'Employee'
                    WHEN 'B' THEN 'Badli'
                    WHEN 'C' THEN 'Contract'
                    WHEN 'A' THEN 'Asosciate'
                    WHEN 'O' THEN 'Others'
                END AS empm_employeetype,
                CASE EMP.EMPM_GENDER
                    WHEN 'M' THEN 'Male'
                    WHEN 'F' THEN 'FEMALE'
                END AS empm_gender,
                TO_CHAR(ETCS.ETCS_SESSIONDATE,'DD-MON-YYYY') || '-' || ETCS.ETCS_NAME AS session_col,
                CASE ETCA.ETCA_PRSENTABSENT
                    WHEN 'P' THEN 'PRESENT'
                    WHEN 'A' THEN 'ABSENT'
                END AS cmbetcapresentabsent,
                TO_CHAR(COALESCE(ETCA.ETCA_ATTDATE,ETCS.ETCS_SESSIONDATE),'DD-MON-YYYY') AS dteetcaadddate,
                ETCA.ETCA_SCORE::text AS txtetcascore,
                CASE ETCA.ETCA_RESULT
                    WHEN 'P' THEN 'Pass'
                    WHEN 'F' THEN 'Fail'
                END AS txtetcaresult,
                ETCA.ETCA_REMARKS AS txtetcaremarks,
                '' AS btnfilmanage
            FROM ENT_TL_TRGCALMST              ETCM
            JOIN ENT_TL_TRGCALEMP              ETCE
                 ON ETCM.ETCM_KEYID = ETCE.ETCE_ETCM_KEYID
            JOIN ENT_TL_TRGCALSESSION          ETCS
                 ON ETCS.ETCS_KEYID = ETCE.ETCE_ETCS_KEYID
            JOIN GEN_TL_EMPLOYEEMST            EMP
                 ON ETCE.ETCE_EMPM_KEYID = EMP.EMPM_KEYID
            LEFT JOIN ENT_TL_TRGCALEMPATSCORE  ETCA
                 ON ETCA.ETCA_ETCE_KEYID = ETCE.ETCE_KEYID
            WHERE ETCM.ETCM_KEYID = :etcmKeyid
            """;

}
