package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.JhaTlAudittemplate;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface JhaTlAudittemplateRepository extends JpaRepository<JhaTlAudittemplate, String> {

    /**
     * Find all active templates by masterid, ordered by review and criteria serial numbers
     */
    List<JhaTlAudittemplate> findByMasteridAndActiveOrderByReviewptslnoAscCriteriaslnoAsc(
            String masterid, String active);

    /**
     * Complex query to get grid data with left join to audit details
     * Exactly matches the Java SQL query logic:
     * - If jhamKeyid is valid: match d.jhad_jhauditmasterid = :jhamKeyid
     * - If jhamKeyid is NULL/invalid: match d.jhad_jhauditmasterid IS NULL
     */
    @Query(value = """
        SELECT 
            t.jaut_keyid,
            t.jaut_reviewptslno,
            t.jaut_parametername,
            t.jaut_criteriaslno AS ACTUALSCORE,
            t.jaut_parameterdescription,
            t.jaut_evidence,
            t.jaut_maximumpoints,
            '' AS PARAMETERID,
            '' AS PARAMETERNAME,
            d.jhad_pointsscored,
            d.jhad_remarks,
            d.jhad_keyid,
            d.jhad_ncremarks AS NC_remarks,
            d.jhad_ncactionplan AS AtionPlan,
            d.jhad_ncactionplan AS AtionPlanKeyId,
            CASE d.jhad_ncstatus 
                WHEN 'C' THEN 'Completed' 
                WHEN 'P' THEN 'Pending' 
                ELSE NULL 
            END AS status,
            CASE d.jhad_ncclosed 
                WHEN 'C' THEN 'Yes' 
                WHEN 'P' THEN 'No' 
                ELSE NULL 
            END AS nc_closed
        FROM jha_tl_audittemplate t
        LEFT JOIN jha_tl_auditdtl d 
            ON d.jhad_parameterid = t.jaut_keyid
            AND (
                (:jhamKeyid IS NOT NULL AND d.jhad_jhauditmasterid = :jhamKeyid)
                OR
                (:jhamKeyid IS NULL AND d.jhad_jhauditmasterid IS NULL)
            )
        WHERE t.jaut_active = 'Y'
            AND (:templateId IS NULL OR t.jaut_masterid = :templateId)
        ORDER BY 
            CAST(t.jaut_reviewptslno AS INTEGER),
            CAST(t.jaut_criteriaslno AS INTEGER)
    """, nativeQuery = true)
    List<Object[]> getAuditTemplateGrid(
            @Param("templateId") String templateId,
            @Param("jhamKeyid") String jhamKeyid);

    /**
     * Enhanced query with all filters matching getjhAuditGridSql
     * Includes: templateId, machineId, jhamKeyid, auditType, jhstepid
     */
    // @Query(value = """
    //     SELECT 
    //         t.jaut_keyid,
    //         t.jaut_reviewptslno,
    //         t.jaut_parametername,
    //         t.jaut_criteriaslno AS ACTUALSCORE,
    //         t.jaut_parameterdescription,
    //         t.jaut_evidence,
    //         t.jaut_maximumpoints,
    //         '' AS PARAMETERID,
    //         '' AS PARAMETERNAME,
    //         d.jhad_pointsscored,
    //         d.jhad_remarks,
    //         d.jhad_keyid,
    //         d.jhad_ncremarks AS NC_remarks,
    //         d.jhad_ncactionplan AS AtionPlan,
    //         d.jhad_ncactionplan AS AtionPlanKeyId,
    //         CASE d.jhad_ncstatus 
    //             WHEN 'C' THEN 'Completed' 
    //             WHEN 'P' THEN 'Pending' 
    //             ELSE NULL 
    //         END AS status,
    //         CASE d.jhad_ncclosed 
    //             WHEN 'C' THEN 'Yes' 
    //             WHEN 'P' THEN 'No' 
    //             ELSE NULL 
    //         END AS nc_closed
    //     FROM jha_tl_audittemplate t
    //     LEFT JOIN jha_tl_auditdtl d 
    //         ON d.jhad_parameterid = t.jaut_keyid
    //         AND (
    //             (:jhamKeyid IS NOT NULL AND d.jhad_jhauditmasterid = :jhamKeyid)
    //             OR
    //             (:jhamKeyid IS NULL AND d.jhad_jhauditmasterid IS NULL)
    //         )
    //     WHERE t.jaut_active = 'Y'
    //         AND (:templateId IS NULL OR t.jaut_masterid = :templateId)
    //         AND (:machineId IS NULL OR t.jaut_machineid = :machineId)
    //         AND (:auditType IS NULL OR t.jaut_audittype = :auditType)
    //         AND (:jhstepid IS NULL OR t.jaut_jhstepid = :jhstepid)
    //     ORDER BY 
    //         CAST(t.jaut_reviewptslno AS INTEGER),
    //         CAST(t.jaut_criteriaslno AS INTEGER)
    // """, nativeQuery = true)
    // List<Object[]> getjhAuditGridSql(
    //         @Param("templateId") String templateId,
    //         @Param("machineId") String machineId,
    //         @Param("jhamKeyid") String jhamKeyid,
    //         @Param("auditType") String auditType,
    //         @Param("jhstepid") String jhstepid);



    //PARAMETER
    @Query("SELECT j.keyid, j.reviewptslno, j.parametername, j.criteriaslno, " +
           "j.parameterdescription, j.evidence, j.maximumpoints " +
           "FROM JhaTlAudittemplate j " +
           "WHERE j.masterid = :templateId " +
           "ORDER BY j.reviewptslno, j.criteriaslno")
    List<Object[]> findAuditParametersByMasterId(@Param("templateId") String templateId);

    //JhaTlAudittemplate save(JhaTlAudittemplate jhaTlAudittemplate);



    @Modifying
    @Transactional
    @Query(value = "DELETE FROM JHA_TL_AUDITTEMPLATE WHERE JAUT_KEYID = :parameterId", 
           nativeQuery = true)
    void deleteByParameterId(@Param("parameterId") String parameterId);

    //Integer findminPoints(String templateId, String auditLevelId);

}