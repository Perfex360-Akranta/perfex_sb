package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_BdmTlWhywhymst;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Native queries for the tables the legacy YY save touched besides the
 * why-why master/detail: BAL_BDM_TL_DTL, BAL_BDM_VW_CLASSIFICATION and GEN_TL_DOCUPDATES.
 */
public interface BAL_BdmTlWhywhyLinkRepository extends Repository<BAL_BdmTlWhywhymst, String> {

    /** Legacy: BAL_BdmTlWhywhymstSql.getClassification(rootCause, pillar) */
    @Query(value = """
            SELECT bclm_keyid
              FROM bal_bdm_vw_classification
             WHERE bclm_name = :rootCause
               AND (CAST(:tpmpCode AS varchar) IS NULL OR tpmp_code = CAST(:tpmpCode AS varchar))
            """, nativeQuery = true)
    List<String> findClassificationIds(@Param("rootCause") String rootCause,
                                       @Param("tpmpCode") String tpmpCode);

    /**
     * Legacy: BAL_BdmTlMstSql.updateYYSql(...)
     * A null parameter leaves the existing column value untouched
     * (same as the legacy "only append SET clause when valid").
     */
    @Modifying(flushAutomatically = true)
    @Query(value = """
            UPDATE bal_bdm_tl_dtl
               SET bdan_wwno              = :wwNo,
                   bdan_wwrequired        = 'Y',
                   bdan_rootcauseid       = COALESCE(CAST(:rootCauseId AS varchar),       bdan_rootcauseid),
                   bdan_rootcause         = COALESCE(CAST(:rootCause AS varchar),         bdan_rootcause),
                   bdan_countermeasure    = COALESCE(CAST(:counterMeasure AS varchar),    bdan_countermeasure),
                   bdan_classificationid  = COALESCE(CAST(:classificationId AS varchar),  bdan_classificationid),
                   bdan_preventivemeasure = COALESCE(CAST(:preventiveMeasure AS varchar), bdan_preventivemeasure)
             WHERE bdan_bdms_keyid = :bdmsId
            """, nativeQuery = true)
    int updateBreakdownDetail(@Param("bdmsId") String bdmsId,
                              @Param("wwNo") String wwNo,
                              @Param("rootCauseId") String rootCauseId,
                              @Param("rootCause") String rootCause,
                              @Param("counterMeasure") String counterMeasure,
                              @Param("classificationId") String classificationId,
                              @Param("preventiveMeasure") String preventiveMeasure);

    /** Legacy: GenTlDocupdatesSql.getInsertSql(...) */
    @Modifying(flushAutomatically = true)
    @Query(value = """
            INSERT INTO gen_tl_docupdates
                   (dcup_keyid, dcup_feedbackid, dcup_refdoctype, dcup_refdocid,
                    dcup_updatedoctype, dcup_detailid, dcup_createdby, dcup_createdon, dcup_modifiedon)
            VALUES (:keyId, :feedbackId, :refDocType, :refDocId,
                    :updateDocType, :detailId, :createdBy, :now, :now)
            """, nativeQuery = true)
    int insertDocUpdate(@Param("keyId") String keyId,
                        @Param("feedbackId") String feedbackId,
                        @Param("refDocType") String refDocType,
                        @Param("refDocId") String refDocId,
                        @Param("updateDocType") String updateDocType,
                        @Param("detailId") String detailId,
                        @Param("createdBy") String createdBy,
                        @Param("now") LocalDateTime now);
}
