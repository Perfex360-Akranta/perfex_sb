package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.EntTlTrgCalUnqp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class EntTlTrgCalUnqpRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public EntTlTrgCalUnqp save(EntTlTrgCalUnqp entity) {
        if (entity == null) return null;
        if (entity.getEtcuKeyid() == null || entity.getEtcuKeyid().isBlank()) {
            entityManager.persist(entity);
            return entity;
        }
        return entityManager.merge(entity);
    }

    public List<EntTlTrgCalUnqp> saveAll(List<EntTlTrgCalUnqp> entities) {
        if (entities == null || entities.isEmpty()) return new ArrayList<>();
        List<EntTlTrgCalUnqp> saved = new ArrayList<>();
        for (EntTlTrgCalUnqp e : entities) {
            saved.add(save(e));
        }
        return saved;
    }

    public List<EntTlTrgCalUnqp> findByEtcuEtcmKeyid(String etcmKeyid) {
        return entityManager.createQuery(
                        "SELECT e FROM EntTlTrgCalUnqp e WHERE e.etcuEtcmKeyid = :id", EntTlTrgCalUnqp.class)
                .setParameter("id", etcmKeyid)
                .getResultList();
    }

    public void deleteByEtcuEtcmKeyid(String etcmKeyid) {
        entityManager.createQuery("DELETE FROM EntTlTrgCalUnqp e WHERE e.etcuEtcmKeyid = :id")
                .setParameter("id", etcmKeyid)
                .executeUpdate();
    }

    public void deleteById(String keyId) {
        entityManager.createQuery("DELETE FROM EntTlTrgCalUnqp e WHERE e.etcuKeyid = :id")
                .setParameter("id", keyId)
                .executeUpdate();
    }

    public Optional<EntTlTrgCalUnqp> findById(String keyId) {
        if (keyId == null || keyId.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(entityManager.find(EntTlTrgCalUnqp.class, keyId));
    }

    public int countByCalendarAndRole(String etcmKeyid, String roleKeyid, String uniqueKeyid) {
        String sql = """
                select count(*)
                  from ent_tl_trgcalunqp
                 where etcu_etcm_keyid = :etcmKeyid
                   and etcu_role_keyid = :roleKeyid
                   and (:uniqueKeyid is null or :uniqueKeyid = '' or etcu_keyid <> :uniqueKeyid)
                """;
        Object cnt = entityManager.createNativeQuery(sql)
                .setParameter("etcmKeyid", etcmKeyid)
                .setParameter("roleKeyid", roleKeyid)
                .setParameter("uniqueKeyid", uniqueKeyid)
                .getSingleResult();
        return cnt == null ? 0 : Integer.parseInt(cnt.toString());
    }

    public List<Object[]> findUniquePositionGrid(String etcmKeyid) {
        String sql = """
                SELECT
                    u.etcu_keyid,
                    u.etcu_role_keyid,
                    r.role_name || ' - ' || r.fnln_displaycode AS unique_position,
                    s.sect_name AS dmt,
                    c.cell_name AS jh,
                    '' AS delete_col
                FROM ent_tl_trgcalunqp u
                JOIN ent_vw_rolemst r
                  ON u.etcu_role_keyid = r.role_keyid
                LEFT JOIN gen_tl_sectionmst s
                  ON u.etcu_roledmt = s.sect_keyid
                LEFT JOIN gen_tl_cellmst c
                  ON u.etcu_rolejh = c.cell_keyid
                WHERE u.etcu_etcm_keyid = :etcmKeyid
                ORDER BY u.etcu_keyid, unique_position
                """;
        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(sql)
                .setParameter("etcmKeyid", etcmKeyid)
                .getResultList();
        return rows == null ? new ArrayList<>() : rows;
    }

    public List<Object[]> findUniqueRoleSelection(String etcmKeyid, String flid) {
        String sql = """
                SELECT
                    '' as dummy,
                    u.etcu_keyid,
                    r.role_keyid,
                    r.role_name || '-' || r.fnln_displaycode AS uniqposition,
                    f.sect_keyid,
                    f.cell_keyid
                FROM ent_vw_rolemst r
                LEFT JOIN gen_vw_fnln f
                  ON f.fnln_keyid = r.flid
                LEFT JOIN ent_tl_trgcalunqp u
                  ON r.role_keyid = u.etcu_role_keyid
                 AND u.etcu_etcm_keyid = :etcmKeyid
                WHERE r.role_flid = r.flid
                  AND (:flid IS NULL OR :flid = '' OR POSITION(:flid IN (r.parentflids || r.flid)) > 0)
                ORDER BY u.etcu_keyid NULLS FIRST, r.role_name
                """;
        var query = entityManager.createNativeQuery(sql)
                .setParameter("etcmKeyid", etcmKeyid);
        query.setParameter("flid", flid);
        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();
        return rows == null ? new ArrayList<>() : rows;
    }

    private static final String BASE_UNIQUE_EMP_SQL = """
            SELECT DISTINCT
                ETCE.ETCE_KEYID AS etcekeyid,
                ETCA.ETCA_KEYID AS attnkeyid,
                EMPM.EMPM_KEYID AS keyid,
                EMPM.EMPM_CODE  AS empcode,
                EMPM.EMPM_NAME  AS name,
                CASE EMPM.EMPM_EMPLOYEETYPE
                    WHEN 'R' THEN 'Employee'
                    WHEN 'M' THEN 'Manager'
                    WHEN 'C' THEN 'Contract'
                    WHEN 'A' THEN 'Asosciate'
                    WHEN 'B' THEN 'Badli'
                    ELSE NULL
                END AS emptype,
                CASE EMPM.EMPM_GENDER
                    WHEN 'M' THEN 'Male'
                    WHEN 'F' THEN 'Female'
                    ELSE NULL
                END AS gender,
                CASE
                    WHEN ETCE.ETCE_ETCS_KEYID = '{}' THEN ' '
                    ELSE ETCS.ETCS_NAME
                END AS ses,
                ROL.ROLE_NAME AS rolename,
                ETCQ.ETCQ_CURRENTLEVEL AS currlevel,
                MAX(ETCQ.ETCQ_CURRENTLEVELDATE) AS lastupdate,
                SECT.SECT_NAME AS dmt,
                CELL.CELL_NAME AS jh,
                ROL.ROLE_NAME AS roleid
            FROM GEN_TL_EMPLOYEEMST EMPM
            JOIN ENT_TL_TRGCALEMP ETCE
                 ON EMPM.EMPM_KEYID = ETCE.ETCE_EMPM_KEYID
            LEFT JOIN GEN_TL_ROLEMST ROL
                 ON ETCE.ETCE_ROLE_KEYID = ROL.ROLE_KEYID
            JOIN ENT_TL_TRGCALMST ETCM
                 ON ETCE.ETCE_ETCM_KEYID = ETCM.ETCM_KEYID
            JOIN ENT_TL_TRGCALSESSION ETCS
                 ON ETCE.ETCE_ETCS_KEYID = ETCS.ETCS_KEYID
            JOIN ENT_TL_TOPICMST TOPI
                 ON TOPI.TOPI_KEYID = ETCM.ETCM_TOPICID
            LEFT JOIN ENT_TL_TRGCALQUAD ETCQ
                 ON ETCQ.ETCQ_TOPICID    = TOPI.TOPI_KEYID
                AND ETCQ.ETCQ_EMPM_KEYID = ETCE.ETCE_EMPM_KEYID
            LEFT JOIN GEN_TL_SECTIONMST SECT
                 ON ETCE.ETCE_ROLEDMT = SECT.SECT_KEYID
            LEFT JOIN GEN_TL_CELLMST CELL
                 ON ETCE.ETCE_ROLEJH = CELL.CELL_KEYID
            LEFT JOIN ENT_TL_TRGCALEMPATSCORE ETCA
                 ON ETCA.ETCA_ETCE_KEYID = ETCE.ETCE_KEYID
            WHERE ETCE.ETCE_ETCM_KEYID = :etcmKeyid
            GROUP BY
                ETCE.ETCE_KEYID,
                ETCA.ETCA_KEYID,
                EMPM.EMPM_KEYID,
                EMPM.EMPM_CODE,
                EMPM.EMPM_NAME,
                EMPM.EMPM_EMPLOYEETYPE,
                EMPM.EMPM_GENDER,
                ROL.ROLE_NAME,
                ETCQ.ETCQ_CURRENTLEVEL,
                SECT.SECT_NAME,
                CELL.CELL_NAME,
                CASE
                    WHEN ETCE.ETCE_ETCS_KEYID = '{}' THEN ' '
                    ELSE ETCS.ETCS_NAME
                END,
                ROL.ROLE_NAME
            """;

    // public int countUniqueEmployees(String etcmKeyid, String filterCond) {
    //     String countSql = "SELECT COUNT(*) FROM (" + BASE_UNIQUE_EMP_SQL + ") t WHERE 1 = 1 " + safeCond(filterCond);
    //     Object cntObj = entityManager.createNativeQuery(countSql)
    //             .setParameter("etcmKeyid", etcmKeyid)
    //             .getSingleResult();
    //     return cntObj == null ? 0 : Integer.parseInt(cntObj.toString());
    // }

    public int countUniqueEmployees(String etcmKeyid, String filterCond) {

        String countSql = """
                SELECT COUNT(*)
                FROM (
                    SELECT
                        d.etcekeyid,
                        d.attnkeyid,
                        d.keyid,
                        d.empcode,
                        d.name,
                        d.emptype,
                        d.gender,
                        d.ses,
                        d.rolename,
                        d.currlevel,
                        d.lastupdate,
                        d.dmt,
                        d.jh,
                        d.roleid
                    FROM (
                        SELECT a.*,
                               ROW_NUMBER() OVER (
                                   PARTITION BY a.etcekeyid
                                   ORDER BY a.currlevel DESC NULLS LAST,
                                            a.lastupdate DESC NULLS LAST
                               ) AS lvl_rn
                        FROM (
                            %s
                        ) a
                    ) d
                    WHERE d.lvl_rn = 1
                ) t
                WHERE 1 = 1 %s
                """.formatted(BASE_UNIQUE_EMP_SQL, safeCond(filterCond));

        Object cntObj = entityManager.createNativeQuery(countSql)
                .setParameter("etcmKeyid", etcmKeyid)
                .getSingleResult();

        return cntObj == null ? 0 : Integer.parseInt(cntObj.toString());
    }

    // public List<Object[]> findUniqueEmployees(String etcmKeyid, String filterCond, Integer fromRow, Integer toRow) {
    //     StringBuilder pagedSql = new StringBuilder("""
    //             SELECT * FROM (
    //               SELECT ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS slno, a.*
    //               FROM ( %s ) a
    //               WHERE 1 = 1 %s
    //             ) sub
    //             """.formatted(BASE_UNIQUE_EMP_SQL, safeCond(filterCond)));

    //     boolean hasPaging = toRow != null && toRow > 0;
    //     if (hasPaging) {
    //         pagedSql.append(" WHERE slno >= :fromRow AND slno <= :toRow");
    //     }

    //     var q = entityManager.createNativeQuery(pagedSql.toString())
    //             .setParameter("etcmKeyid", etcmKeyid);

    //     if (hasPaging) {
    //         q.setParameter("fromRow", fromRow == null ? 1 : fromRow);
    //         q.setParameter("toRow", toRow);
    //     }

    //     @SuppressWarnings("unchecked")
    //     List<Object[]> rows = q.getResultList();
    //     return rows == null ? new ArrayList<>() : rows;
    // }

       public List<Object[]> findUniqueEmployees(String etcmKeyid, String filterCond, Integer fromRow, Integer toRow) {

        StringBuilder pagedSql = new StringBuilder("""
                SELECT *
                FROM (
                    SELECT ROW_NUMBER() OVER (
                               ORDER BY d.etcekeyid
                           ) AS slno,
                           d.etcekeyid,
                           d.attnkeyid,
                           d.keyid,
                           d.empcode,
                           d.name,
                           d.emptype,
                           d.gender,
                           d.ses,
                           d.rolename,
                           d.currlevel,
                           d.lastupdate,
                           d.dmt,
                           d.jh,
                           d.roleid
                    FROM (
                        SELECT
                            x.etcekeyid,
                            x.attnkeyid,
                            x.keyid,
                            x.empcode,
                            x.name,
                            x.emptype,
                            x.gender,
                            x.ses,
                            x.rolename,
                            x.currlevel,
                            x.lastupdate,
                            x.dmt,
                            x.jh,
                            x.roleid
                        FROM (
                            SELECT a.*,
                                   ROW_NUMBER() OVER (
                                       PARTITION BY a.etcekeyid
                                       ORDER BY a.currlevel DESC NULLS LAST,
                                                a.lastupdate DESC NULLS LAST
                                   ) AS lvl_rn
                            FROM (
                                %s
                            ) a
                        ) x
                        WHERE x.lvl_rn = 1
                    ) d
                    WHERE 1 = 1 %s
                ) sub
                """.formatted(BASE_UNIQUE_EMP_SQL, safeCond(filterCond)));

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
}
