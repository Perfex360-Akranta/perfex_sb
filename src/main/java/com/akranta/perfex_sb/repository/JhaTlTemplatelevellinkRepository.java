package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.JhaTlTemplatelevellink;

public interface JhaTlTemplatelevellinkRepository
        extends JpaRepository<JhaTlTemplatelevellink, JhaTlTemplatelevellink.TemplateLevelLinkId> {

    // @Query(value = """
    // SELECT *
    // FROM JHA_TL_TEMPLATELEVELLINK
    // WHERE (:templateId IS NULL OR JTLL_TEMPLATEID = :templateId)
    // AND (:jhStepId IS NULL OR JTLL_AUDITLEVELID = :jhStepId)
    // ORDER BY JTLL_AUDITLEVELID
    // """, nativeQuery = true)

    // List<JhaTlTemplatelevellink> findAuditLevels(
    // @Param("templateId") String templateId,
    // @Param("jhStepId") String jhStepId);

    // @Query(value = "SELECT * FROM JHA_TL_TEMPLATELEVELLINK " +
    // "WHERE 1=1 " +
    // //"AND (:templateId = '' OR :templateId IS NULL OR JTLL_TEMPLATEID =
    // :templateId) " +
    // "AND (:templateId IS NULL OR :templateId = '' OR JTLL_TEMPLATEID =
    // :templateId)"+
    // "AND (:jhStepId = '' OR :jhStepId IS NULL OR JTLL_AUDITLEVELID = :jhStepId) "
    // +
    // "ORDER BY JTLL_AUDITLEVELID",
    // nativeQuery = true)
    // List<JhaTlTemplatelevellink> findAuditLevels(
    // @Param("templateId") String templateId,
    // @Param("jhStepId") String jhStepId
    // );

    @Query(value = "SELECT * FROM JHA_TL_TEMPLATELEVELLINK " +
            "WHERE 1=1 " +
            "AND (:templateId IS NULL OR :templateId = '' OR JTLL_TEMPLATEID = :templateId) " +
            "AND (:jhStepId IS NULL OR :jhStepId = '' OR JTLL_AUDITLEVELID = :jhStepId) " +
            "ORDER BY JTLL_AUDITLEVELID", nativeQuery = true)
    List<JhaTlTemplatelevellink> findAuditLevels(
            @Param("templateId") String templateId,
            @Param("jhStepId") String jhStepId);

    List<JhaTlTemplatelevellink> findAllByTemplateid(String jhauditmasterid);

    List<JhaTlTemplatelevellink> deleteByTemplateid(String TemplateId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM JHA_TL_TEMPLATELEVELLINK WHERE JTLL_TEMPLATEID = :templateId", nativeQuery = true)
    void deleteByTemplateId(@Param("templateId") String templateId);



    @Query(value = 
    "SELECT JTLL_MINIMUMPOINTS " +
    "FROM JHA_TL_TEMPLATELEVELLINK " +
    "WHERE JTLL_AUDITLEVELID = :auditLevel " +
    "AND JTLL_TEMPLATEID = :auditTemplate",
    nativeQuery = true)
Integer findMinimumPoints(
    @Param("auditLevel") String auditLevel,
    @Param("auditTemplate") String auditTemplate
);




}
