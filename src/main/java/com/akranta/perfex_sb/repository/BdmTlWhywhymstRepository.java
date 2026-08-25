package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BdmTlWhywhymst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
public interface BdmTlWhywhymstRepository extends JpaRepository<BdmTlWhywhymst, String> {
    BdmTlWhywhymst findByKeyid(String keyid);
    @Modifying
    @Query(value = "UPDATE BDM_TL_WHYWHYMST SET " +
                   "WWMS_APPROLEID = :apprRoleid, " +
                   "WWMS_APPROVEDBY = :approvedBy, " +
                   "WWMS_APPROVEDON = :approvedon, " +
                   "WWMS_APPSTATUS = :appstatus, " +
                   "WWMS_APPREMARKS = :appRemarks, " +
                   "WWMS_MODIFIEDON = :modifiedon " +
                   "WHERE WWMS_KEYID = :keyid", 
           nativeQuery = true)
    int updateApprovalFields(
        @Param("keyid") String keyid,
        @Param("apprRoleid") String apprRoleid,
        @Param("approvedBy") String approvedBy,
        @Param("approvedon") LocalDateTime approvedon,
        @Param("appstatus") String appstatus,
        @Param("appRemarks") String appRemarks,
        @Param("modifiedon") LocalDateTime modifiedon
    );

    @Modifying
    @Query(value = "UPDATE BDM_TL_WHYWHYMST SET " +
                   "WWMS_APPROLEID = :apprRoleid, " +
                   "WWMS_APPROVEDBY = :approvedBy, " +
                   "WWMS_APPROVEDON = :approvedon, " +
                   "WWMS_APPSTATUS = :appstatus, " +
                   "WWMS_APPREMARKS = :appRemarks, " +
                   "WWMS_MODIFIEDON = :modifiedon, " +
                   "WWMS_ISCOBD = :iscobd, " +
                   "WWMS_COBDVALUE = :cobdvalue, " +
                   "WWMS_COBDHOURS = :cobdhours " +
                   "WHERE WWMS_KEYID = :keyid", 
           nativeQuery = true)
    int updateApprovalAIFields(
        @Param("keyid") String keyid,
        @Param("apprRoleid") String apprRoleid,
        @Param("approvedBy") String approvedBy,
        @Param("approvedon") LocalDateTime approvedon,
        @Param("appstatus") String appstatus,
        @Param("appRemarks") String appRemarks,
        @Param("modifiedon") LocalDateTime modifiedon,
        @Param("iscobd") Character iscobd,
        @Param("cobdvalue") BigDecimal cobdvalue,
        @Param("cobdhours") BigDecimal cobdhours
    );

//      @Modifying
//     @Query(value = """
//                   select count(*) from gen_tl_employeemst join GEN_TL_FNLNROLETEAM ON FRT_EMPM_KEYID = EMPM_KEYID 
// JOIN   GEN_TL_TEAMTRADELINK  ON frp_frt_keyid = frt_keyid  
// JOIN  bdm_tl_whywhymst  ON WWMS_TRADEID = frp_tradeid  
// JOIN gen_mv_flidhierarchy ON FRT_FNLN_KEYID = FLID 
// join gen_tl_trade_role_link on gtrl_tradeid = WWMS_TRADEID and gtrl_roleid =  FRT_ROLE_KEYID
// AND POSITION((SELECT FLID FROM  gen_mv_flidhierarchy WHERE FNLN_ORIGINALID = WWMS_SECTIONID  ) IN parentflids || '-' || flid) > 0 
// WHERE empm_active ='Y' and WWMS_KEYID = :keyid 
//                   """,
//            nativeQuery = true)
//     int checkAreaInchargeApprovals(
//         @Param("keyid") String keyid
        
//     );

   
    @Query(value = """
                 select count(*) from gen_tl_employeemst join GEN_TL_FNLNROLETEAM ON FRT_EMPM_KEYID = EMPM_KEYID 
JOIN   GEN_TL_TEAMTRADELINK  ON frp_frt_keyid = frt_keyid  and frp_tradeid = :tradeid 
JOIN gen_mv_flidhierarchy ON FRT_FNLN_KEYID = FLID 
join gen_tl_trade_role_link on gtrl_tradeid = :tradeid and gtrl_roleid =  FRT_ROLE_KEYID
AND POSITION((SELECT FLID FROM  gen_mv_flidhierarchy WHERE FNLN_ORIGINALID = :sectionid  ) IN parentflids || '-' || flid) > 0 
WHERE empm_active ='Y'   
                  """,
           nativeQuery = true)
    int checkAreaInchargeApprovals(
        @Param("sectionid") String sectionid,
        @Param("tradeid") String tradeid
    );
    
    @Query(value = """
        SELECT 
            wwms_problem AS problem,
            wwms_area AS area,
            wwms_timespent AS timespent
        FROM bdm_tl_whywhymst
        WHERE wwms_keyid = :keyId
        """, nativeQuery = true)
    List<Map<String, Object>> getSpentTimeByKeyId(@Param("keyId") String keyId); 


    @Query(value = """
    SELECT wrcm_keyid, '', wrcm_name 
    FROM bdm_tl_rootcausemst 
    WHERE wrcm_type = CASE 
        WHEN :openMode IS NOT NULL AND :openMode != '' 
        THEN :openMode 
        ELSE 'BDM' 
    END
    """, nativeQuery = true)
List<Map<String, Object>> getRootCauseBySql(@Param("openMode") String openMode);


    @Query(value = """
    SELECT 'Proposed preventive counter measures given below' AS type,
           'Proposed preventive counter measures given below' AS keyid,
           'Proposed preventive counter measures given below' AS btn,
           'Responsibility' AS respon,
           'Date' AS dte,
           'Status' AS status,
           'Theme' AS theme,
           0 as dataorder
    
    UNION ALL
    
    SELECT DISTINCT cname,
           MAX(keyid) AS keyid,
           MAX(aa) AS btn,
           MAX(empname) AS respon,
           MAX(cdate) AS dte,
           MAX(bb) AS status,
           MAX(theme) AS theme,
           dataorder as dataorder
    FROM (
        -- Change in Workpractice / Training / OPL
        SELECT 'Change in Workpractice / Training / OPL' AS cname,
               COALESCE(oplm_keyid, '') AS keyid,
               '' AS aa,
               COALESCE(empm_name, '') AS empname,
               COALESCE(TO_CHAR(oplm_prepareddate, 'YYYY-MM-DD'), '') AS cdate,
               COALESCE(yyed_effectiveid, '') AS bb,
               COALESCE(oplm_theme, '') AS theme,
               1 as dataorder
        FROM opl_tl_mst opl
        LEFT JOIN gen_tl_employeemst emp ON opl.oplm_preparedid = emp.empm_keyid
        LEFT JOIN bdm_tl_yyeffectivemst yyem ON yyem.yyef_wwms_keyid = :yyno
        LEFT JOIN bdm_tl_yyeffectivedtl yyed ON yyem.yyef_keyid = yyed.yyed_yyef_keyid 
              AND opl.oplm_keyid = yyed.yyed_countermesid
        WHERE opl.oplm_refdoctype = 'YY' AND opl.oplm_refdocno = :yyno
        
        UNION ALL
        SELECT 'Change in Workpractice / Training / OPL' AS cname,
               '' AS keyid, '' AS aa, '' AS empname, '' AS cdate,
               '' AS bb, '' AS theme, 1 as dataorder
        
        UNION ALL
        -- Routine Activity (CLTI)
        SELECT 'Routine Activity (CLTI)' AS cname,
               COALESCE(clis_keyid, '') AS keyid,
               '' AS aa,
               COALESCE(empm_name, '') AS empname,
               COALESCE(TO_CHAR(clis_effectivedate, 'YYYY-MM-DD'), '') AS cdate,
               COALESCE(yyed_effectiveid, '') AS bb,
               '' AS theme,
               3 as dataorder
        FROM cli_tl_standards clis
        LEFT JOIN gen_tl_employeemst emp ON clis.clis_responsibilityid = emp.empm_keyid
        LEFT JOIN bdm_tl_yyeffectivemst yyem ON yyem.yyef_wwms_keyid = :yyno
        LEFT JOIN bdm_tl_yyeffectivedtl yyed ON yyem.yyef_keyid = yyed.yyed_yyef_keyid 
              AND clis.clis_keyid = yyed.yyed_countermesid
        WHERE clis.clis_refdoctype = 'YY' AND clis.clis_refdocno = :yyno
        
        UNION ALL
        SELECT 'Routine Activity (CLTI)' AS cname,
               '' AS keyid, '' AS aa, '' AS empname, '' AS cdate,
               '' AS bb, '' AS theme, 3 as dataorder
        
        UNION ALL
        SELECT 'Condition Monitoring' AS cname,
               '' AS keyid, '' AS aa, '' AS empname, '' AS cdate,
               '' AS bb, '' AS theme, 4 as dataorder
        
        UNION ALL
        SELECT 'Condition Monitoring' AS cname,
               '' AS keyid, '' AS aa, '' AS empname, '' AS cdate,
               '' AS bb, '' AS theme, 4 as dataorder
        
        UNION ALL
        -- Preventive Maintenance
        SELECT 'Preventive Maintenance' AS cname,
               COALESCE(pmsd_keyid, '') AS keyid,
               '' AS aa,
               COALESCE(empm_name, '') AS empname,
               COALESCE(TO_CHAR(pmsd_effectivedate, 'YYYY-MM-DD'), '') AS cdate,
               COALESCE(yyed_effectiveid, '') AS bb,
               '' AS theme,
               5 as dataorder
        FROM plm_tl_standards plm
        LEFT JOIN gen_tl_employeemst emp ON plm.pmsd_preparedbyid = emp.empm_keyid
        LEFT JOIN bdm_tl_yyeffectivemst yyem ON yyem.yyef_wwms_keyid = :yyno
        LEFT JOIN bdm_tl_yyeffectivedtl yyed ON yyem.yyef_keyid = yyed.yyed_yyef_keyid 
              AND plm.pmsd_keyid = yyed.yyed_countermesid
        WHERE plm.pmsd_refdoctype = 'YY' AND plm.pmsd_refdocno = :yyno
        
        UNION ALL
        SELECT 'Preventive Maintenance' AS cname,
               '' AS keyid, '' AS aa, '' AS empname, '' AS cdate,
               '' AS bb, '' AS theme, 5 as dataorder
        
        UNION ALL
        SELECT 'Preventive Maintenance' AS cname,
               '' AS keyid, '' AS aa, '' AS empname, '' AS cdate,
               '' AS bb, '' AS theme, 5 as dataorder
        
        UNION ALL
        -- Modification (Kaizen)
        SELECT 'Modification (Kaizen)' AS cname,
               COALESCE(kzbn_keyid, '') AS keyid,
               '' AS aa,
               COALESCE(empm_name, '') AS empname,
               COALESCE(TO_CHAR(kzbn_date, 'YYYY-MM-DD'), '') AS cdate,
               COALESCE(yyed_effectiveid, '') AS bb,
               COALESCE(kzbn_kaizen, '') AS theme,
               6 as dataorder
        FROM kzn_tl_kaizenbankmst kzbn
        LEFT JOIN gen_tl_employeemst emp ON kzbn.kzbn_suggestedby = emp.empm_keyid
        LEFT JOIN kzn_tl_mst kznm ON 1=1
        LEFT JOIN bdm_tl_yyeffectivemst yyem ON yyem.yyef_wwms_keyid = :yyno
        LEFT JOIN bdm_tl_yyeffectivedtl yyed ON yyem.yyef_keyid = yyed.yyed_yyef_keyid 
              AND kznm.kznm_keyid = yyed.yyed_countermesid
        WHERE kzbn.kzbn_refdoctype = 'YY' AND kzbn.kzbn_refdocno = :yyno
        
        UNION ALL
        SELECT 'Modification (Kaizen)' AS cname,
               '' AS keyid, '' AS aa, '' AS empname, '' AS cdate,
               '' AS bb, '' AS theme, 6 as dataorder
        
        UNION ALL
        -- Action plan
        SELECT 'Action plan' AS cname,
               COALESCE(aplm_keyid, '') AS keyid,
               '' AS aa,
               COALESCE(empm_name, '') AS empname,
               COALESCE(TO_CHAR(apld_targetdate, 'YYYY-MM-DD'), '') AS cdate,
               COALESCE(yyed_effectiveid, '') AS bb,
               COALESCE(apld_actionplan, '') as theme,
               7 AS dataorder
        FROM gen_tl_actionplanmst aplm
        INNER JOIN gen_tl_actionplandtl apld ON apld.apld_aplm_keyid = aplm.aplm_keyid
        LEFT JOIN gen_tl_employeemst emp ON apld.apld_responsibility = emp.empm_keyid
        LEFT JOIN bdm_tl_yyeffectivemst yyem ON yyem.yyef_wwms_keyid = :yyno
        LEFT JOIN bdm_tl_yyeffectivedtl yyed ON yyem.yyef_keyid = yyed.yyed_yyef_keyid 
              AND aplm.aplm_keyid = yyed.yyed_countermesid
        WHERE aplm.aplm_refdoctype = 'YY' AND aplm.aplm_masterrefid = :yyno
        
        UNION ALL
        SELECT 'Action plan' AS cname,
               '' AS keyid, '' AS aa, '' AS empname, '' AS cdate,
               '' AS bb, '' AS theme, 7 AS dataorder
        
        UNION ALL
        SELECT 'Action plan' AS cname,
               '' AS keyid, '' AS aa, '' AS empname, '' AS cdate,
               '' AS bb, '' AS theme, 7 AS dataorder
    ) subquery
    GROUP BY cname, dataorder
    ORDER BY dataorder ASC
    """, nativeQuery = true)
List<Map<String, Object>> getCounterMeasureData(@Param("yyno") String yyno);

}
