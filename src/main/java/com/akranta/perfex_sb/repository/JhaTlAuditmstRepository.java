package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import com.akranta.perfex_sb.model.JhaTlAuditdtl;
import com.akranta.perfex_sb.model.JhaTlAuditmst;

import jakarta.transaction.Transactional;

public interface JhaTlAuditmstRepository extends JpaRepository<JhaTlAuditmst, String> {

        // JhaTlAuditdtl save(JhaTlAuditdtl jhaTlAuditdtl);

        // @Query(value = "SELECT * FROM JHA_TL_AUDITMST WHERE JHAM_KEYID = ( " +
        // "SELECT MAX(JHAM_KEYID) FROM JHA_TL_AUDITMST, JHA_TL_AUDITDTL " +
        // "WHERE JHAD_JHAUDITMASTERID = JHAM_KEYID " +
        // "AND JHAM_FLID = :flId AND JHAM_AUDITTEAMID = :templateId " +
        // "AND JHAM_AUDITTYPE = :auditType " +
        // "AND JHAM_JHSTEPID = :stepId " +
        // "AND JHAM_AUDITDATE::date = TO_DATE(:date, 'DD-Mon-YYYY') )",
        // nativeQuery = true)
        // JhaTlAuditmst findExistingWithDate(@Param("templateId") String templateId,
        // @Param("flId") String flId,
        // @Param("date") String date,
        // @Param("auditType") String auditType,
        // @Param("stepId") String stepId);

        // @Query(value = "SELECT * FROM JHA_TL_AUDITMST WHERE JHAM_KEYID = ( " +
        // "SELECT MAX(JHAM_KEYID) FROM JHA_TL_AUDITMST, JHA_TL_AUDITDTL " +
        // "WHERE JHAD_JHAUDITMASTERID = JHAM_KEYID " +
        // "AND JHAM_FLID = :flId " +
        // "AND JHAM_AUDITTEAMID = :templateId " +
        // "AND JHAM_AUDITTYPE = :auditType " +
        // "AND JHAM_JHSTEPID = :stepId " +
        // "AND JHAM_AUDITDATE::date = TO_DATE(:date, 'DD-Mon-YYYY') )",
        // nativeQuery = true)
        // JhaTlAuditmst findExistingWithDate(@Param("templateId") String templateId,
        // @Param("flId") String flId,
        // @Param("date") String date,
        // @Param("auditType") String auditType,
        // @Param("stepId") String stepId);

        // @Query(value = "SELECT * FROM JHA_TL_AUDITMST WHERE JHAM_KEYID = ( " +
        // "SELECT MAX(JHAM_KEYID) FROM JHA_TL_AUDITMST, JHA_TL_AUDITDTL " +
        // "WHERE JHAD_JHAUDITMASTERID = JHAM_KEYID " +
        // "AND JHAM_FLID = :flId AND JHAM_AUDITTEAMID = :templateId " +
        // "AND JHAM_AUDITTYPE = :auditType " +
        // "AND JHAM_JHSTEPID = :stepId )",
        // nativeQuery = true)
        // JhaTlAuditmst findExistingWithoutDate(@Param("templateId") String templateId,
        // @Param("flId") String flId,
        // @Param("auditType") String auditType,
        // @Param("stepId") String stepId);

        // @Query(value = "SELECT * FROM JHA_TL_AUDITMST WHERE JHAM_KEYID = ( " +
        // "SELECT MAX(JHAM_KEYID) FROM JHA_TL_AUDITMST, JHA_TL_AUDITDTL " +
        // "WHERE JHAD_JHAUDITMASTERID = JHAM_KEYID " +
        // "AND JHAM_FLID = :flId " +
        // "AND JHAM_AUDITTEAMID = :templateId " +
        // "AND JHAM_AUDITTYPE = :auditType " +
        // "AND JHAM_JHSTEPID = :stepId )",
        // nativeQuery = true)
        // JhaTlAuditmst findExistingWithoutDate(@Param("templateId") String templateId,
        // @Param("flId") String flId,
        // @Param("auditType") String auditType,
        // @Param("stepId") String stepId);

        // @Query(value = "SELECT * FROM JHA_TL_AUDITMST WHERE JHAM_KEYID = ( " +
        // "SELECT MAX(JHAM_KEYID) FROM JHA_TL_AUDITMST, JHA_TL_AUDITDTL " +
        // "WHERE JHAD_JHAUDITMASTERID = JHAM_KEYID " +
        // "AND JHAM_FLID = :flId " +
        // "AND JHAM_AUDITTEAMID = :templateId " +
        // "AND JHAM_AUDITTYPE = :auditType " +
        // "AND (:stepId IS NULL OR :stepId = '' OR :stepId = '-' OR JHAM_JHSTEPID =
        // :stepId) " +
        // "AND JHAM_AUDITDATE::date = TO_DATE(:date, 'DD-Mon-YYYY') )", nativeQuery =
        // true)
        // JhaTlAuditmst findExistingWithDate(@Param("templateId") String templateId,
        // @Param("flId") String flId,
        // @Param("date") String date,
        // @Param("auditType") String auditType,
        // @Param("stepId") String stepId);

        // @Query(value = "SELECT * FROM JHA_TL_AUDITMST WHERE JHAM_KEYID = ( " +
        // "SELECT MAX(JHAM_KEYID) FROM JHA_TL_AUDITMST, JHA_TL_AUDITDTL " +
        // "WHERE JHAD_JHAUDITMASTERID = JHAM_KEYID " +
        // "AND JHAM_FLID = :flId " +
        // "AND JHAM_AUDITTEAMID = :templateId " +
        // "AND JHAM_AUDITTYPE = :auditType " +
        // "AND (:stepId IS NULL OR :stepId = '' OR :stepId = '-' OR JHAM_JHSTEPID =
        // :stepId) )", nativeQuery = true)
        // JhaTlAuditmst findExistingWithoutDate(@Param("templateId") String templateId,
        // @Param("flId") String flId,
        // @Param("auditType") String auditType,
        // @Param("stepId") String stepId);

        @Query(value = "SELECT * FROM JHA_TL_AUDITMST WHERE JHAM_KEYID = ( " +
                        "SELECT MAX(m.JHAM_KEYID) " +
                        "FROM JHA_TL_AUDITMST m " +
                        "LEFT JOIN JHA_TL_AUDITDTL d ON d.JHAD_JHAUDITMASTERID = m.JHAM_KEYID " +
                        "WHERE m.JHAM_FLID = :flId " +
                        "AND m.JHAM_AUDITTEAMID = :templateId " +
                        "AND m.JHAM_AUDITTYPE = :auditType " +
                        "AND (:stepId IS NULL OR :stepId = '' OR m.JHAM_JHSTEPID = :stepId) " +
                        "AND DATE(m.JHAM_AUDITDATE) = TO_DATE(:date, 'DD-Mon-YYYY') )", nativeQuery = true)
        JhaTlAuditmst findExistingWithDate(@Param("templateId") String templateId,
                        @Param("flId") String flId,
                        @Param("date") String date,
                        @Param("auditType") String auditType,
                        @Param("stepId") String stepId);

        @Query(value = "SELECT * FROM JHA_TL_AUDITMST WHERE JHAM_KEYID = ( " +
                        "SELECT MAX(m.JHAM_KEYID) " +
                        "FROM JHA_TL_AUDITMST m " +
                        "LEFT JOIN JHA_TL_AUDITDTL d ON d.JHAD_JHAUDITMASTERID = m.JHAM_KEYID " +
                        "WHERE m.JHAM_FLID = :flId " +
                        "AND m.JHAM_AUDITTEAMID = :templateId " +
                        "AND m.JHAM_AUDITTYPE = :auditType " +
                        "AND (:stepId IS NULL OR :stepId = '' OR m.JHAM_JHSTEPID = :stepId) )", nativeQuery = true)
        JhaTlAuditmst findExistingWithoutDate(@Param("templateId") String templateId,
                        @Param("flId") String flId,
                        @Param("auditType") String auditType,
                        @Param("stepId") String stepId);

        @Query(value = "SELECT COUNT(*) FROM JHA_TL_AUDITMST WHERE JHAM_FLID = :flId", nativeQuery = true)
        Long countAuditsByFlId(@Param("flId") String flId);

        // @Query(value = "SELECT * FROM JHA_TL_AUDITMST WHERE JHAM_AUDITPILLAR =
        // :auditPillar ORDER BY JHAM_CREATEDON DESC LIMIT 1",
        // nativeQuery = true)
        // JhaTlAuditmst findLatestByAuditPillar(@Param("auditPillar") String
        // auditPillar);

        /* 888 proper working */
        // @Query(value = "SELECT * FROM jha_tl_auditmst WHERE 1=1 :whereClause",
        // nativeQuery = true)
        // List<JhaTlAuditmst> findByDynamicConditions(@Param("whereClause") String
        // whereClause);

        // @Query(value = "SELECT * FROM jha_tl_auditmst WHERE 1=1 AND (:keyid IS NULL
        // OR jham_keyid = :keyid) AND (:flId IS NULL OR jham_flid = :flId) " +
        // " AND (:AuditTeamid IS NULL OR jham_auditteamid = :AuditTeamid) " +
        // " AND (:AuditType IS NULL OR jham_audittype = :AuditType) " +
        // " AND (:Auditpillar IS NULL OR jham_auditpillar = :Auditpillar) " +
        // " AND (:Auidtortype IS NULL OR jham_auditortype = :Auidtortype) " +
        // " AND (jham_auditdate ::date = :Auditdate ::date ) " +
        // " AND (:stepId IS NULL OR jham_jhstepid = :stepId)", nativeQuery = true)
        // List<JhaTlAuditmst> findByAuditParams(@Param("keyid") String
        // keyid,@Param("flId") String flId,@Param("AuditTeamid") String
        // AuditTeamid,@Param("AuditType") String AuditType,@Param("Auditpillar") String
        // Auditpillar,@Param("stepId") String stepId,@Param("Auditdate") LocalDateTime
        // Auditdate,@Param("Auidtortype") String Auidtortype);

        @Query(value = "SELECT * FROM jha_tl_auditmst WHERE 1=1 AND (:keyid IS NULL OR jham_keyid = :keyid)  AND (:flId IS NULL OR jham_flid = :flId) "
                        +
                        "  AND (:AuditTeamid IS NULL OR jham_auditteamid = :AuditTeamid) " +
                        "  AND (:AuditType IS NULL OR jham_audittype = :AuditType) " +
                        "  AND (:Auditpillar IS NULL OR jham_auditpillar = :Auditpillar) " +
                        "  AND (:Auidtortype IS NULL OR jham_auditortype = :Auidtortype) " +
                        "  AND (:Auditdate IS NULL OR jham_auditdate ::date = :Auditdate2 ::date ) " +
                        "  AND (:stepId IS NULL OR jham_jhstepid = :stepId)", nativeQuery = true)
        List<JhaTlAuditmst> findByAuditParams(@Param("keyid") String keyid, @Param("flId") String flId,
                        @Param("AuditTeamid") String AuditTeamid, @Param("AuditType") String AuditType,
                        @Param("Auditpillar") String Auditpillar, @Param("stepId") String stepId,
                        @Param("Auditdate") String Auditdate, @Param("Auidtortype") String Auidtortype,
                        @Param("Auditdate2") LocalDateTime Auditdate2);

        // Long getcountjhStep(String flid);

        // @Query(value = "SELECT * FROM jha_tl_auditmst WHERE 1=1 AND jham_flid = :flId
        // " +
        // " AND jham_auditteamid = :AuditTeamid " +
        // " AND jham_audittype = :AuditType " +
        // " AND jham_auditpillar = :Auditpillar " +
        // " AND jham_auditortype = :Auidtortype " +
        // " AND jham_auditdate ::date = :Auditdate ::date " +
        // " AND jham_jhstepid = :stepId ", nativeQuery = true)
        // List<JhaTlAuditmst> findByAuditParams(@Param("keyid") String
        // keyid,@Param("flId") String flId,@Param("AuditTeamid") String
        // AuditTeamid,@Param("AuditType") String AuditType,@Param("Auditpillar") String
        // Auditpillar,@Param("stepId") String stepId,@Param("Auditdate") LocalDateTime
        // Auditdate,@Param("Auidtortype") String Auidtortype);

        @Query(value = "SELECT COUNT(*) FROM JHA_TL_AUDITTEAM WHERE JHAT_KEYID NOT IN " +
                        "(SELECT JHAM_AUDITTEAMID FROM jha_tl_auditmst WHERE JHAM_FLID = :flid)", nativeQuery = true)
        Long countUnassignedAuditTeams(@Param("flid") String flid);

        @Query(value = "SELECT JHSM_KEYID FROM GEN_TL_JHSTEPMST WHERE JHSM_ACTIVE='Y' AND " +
                        "JHSM_CODE IN ( " +
                        "SELECT MIN(JHSM_CODE) FROM GEN_TL_JHSTEPMST WHERE JHSM_ACTIVE='Y' " +
                        "AND JHSM_KEYID NOT IN ( " +
                        "SELECT JHAP_AUDITLEVEL FROM JHA_TL_AUDITMST, JHA_TL_AUDITPARAMETER " +
                        "WHERE JHAM_AUDITTEAMID=JHAP_KEYID AND JHAM_FLID = :jhTemplateId AND JHAM_STATUS='P' " +
                        "))", nativeQuery = true)
        String getAuditLevelCurrent(@Param("jhTemplateId") String jhTemplateId);

        @Query(value = "SELECT JTLL_MINIMUMPOINTS " +
                        "FROM JHA_TL_TEMPLATELEVELLINK " +
                        "WHERE JTLL_TEMPLATEID = :templateId " +
                        "AND JTLL_AUDITLEVELID = :auditLevelId", nativeQuery = true)
        Integer findMinimumPoints(@Param("templateId") String templateId,
                        @Param("auditLevelId") String auditLevelId);

        // delete for the mst table

        @Modifying
        @Transactional
        @Query(value = "DELETE FROM jha_tl_auditmst WHERE jham_keyid = :keyId", nativeQuery = true)
        int deleteAuditMaster(@Param("keyId") String keyId);

}
