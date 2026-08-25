package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.PlmTlConditionalappraisal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlmTlConditionalappraisalRepository extends JpaRepository<PlmTlConditionalappraisal, String> {
    
    /**
     * Recall single Conditional Appraisal detail by detail keyid
     * Returns header row plus matching detail record
     */
    @Query(value = """
            SELECT
                '0' AS cdap_keyid,
                '1' AS cdap_component_type,
                '2' AS cdap_componentid,
                '3' AS cdap_newcomponent,
                '4' AS cdap_dimension,
                '5' AS cdap_checkingtool,
                '6' AS cdap_typeofcheck,
                '7' AS cdap_idealtype,
                '8' AS cdap_idealminimum,
                '9' AS cdap_idealmaximum,
                '10' AS cdap_uom,
                '11' AS cdap_idealcondition,
                '12' AS cdap_actualcondition,
                '13' AS cdap_actualvalue,
                '14' AS cdap_oknotok,
                '15' AS cdap_status,
                '16' AS cdap_actionrequired,
                '17' AS cdap_refurbishment_status,
                '18' AS cdap_createdon
            UNION ALL
            SELECT
                COALESCE(cdap_keyid, '') AS cdap_keyid,
                COALESCE(cdap_component_type, '') AS cdap_component_type,
                COALESCE(cdap_componentid, '') AS cdap_componentid,
                COALESCE(cdap_newcomponent, '') AS cdap_newcomponent,
                COALESCE(cdap_dimension, '') AS cdap_dimension,
                COALESCE(cdap_checkingtool, '') AS cdap_checkingtool,
                COALESCE(cdap_typeofcheck, '') AS cdap_typeofcheck,
                COALESCE(cdap_idealtype, '') AS cdap_idealtype,
                COALESCE(cdap_idealminimum, 0) AS cdap_idealminimum,
                COALESCE(cdap_idealmaximum, 0) AS cdap_idealmaximum,
                COALESCE(cdap_uom, '') AS cdap_uom,
                COALESCE(cdap_idealcondition, '') AS cdap_idealcondition,
                COALESCE(cdap_actualcondition, '') AS cdap_actualcondition,
                COALESCE(cdap_actualvalue, 0) AS cdap_actualvalue,
                COALESCE(cdap_oknotok, '') AS cdap_oknotok,
                COALESCE(cdap_status, '') AS cdap_status,
                COALESCE(cdap_actionrequired, '') AS cdap_actionrequired,
                COALESCE(cdap_refurbishment_status, '') AS cdap_refurbishment_status,
                TO_CHAR(cdap_createdon, 'DD-MON-YYYY') AS cdap_createdon
            FROM plm_tl_conditionalappraisal
            WHERE cdap_keyid = :keyid
            """, nativeQuery = true)
    List<Map<String, Object>> recallConditionalAppraisalDetail(@Param("keyid") String keyid);
    @Modifying
    @Query(value = "DELETE FROM plm_tl_conditionalappraisal WHERE cdap_keyid = :keyid", nativeQuery = true)
    int deleteConditionalAppraisalDetail(@Param("keyid") String keyid);

@Modifying
    @Query(value = "DELETE FROM plm_tl_conditionalappraisal WHERE cdap_cdam_keyid = :cdamKeyid", 
           nativeQuery = true)
    int deleteAllDetailsByMasterKeyid(@Param("cdamKeyid") String cdamKeyid);

}
