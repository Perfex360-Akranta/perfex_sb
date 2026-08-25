package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.AbnTlAbnormality;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AbnTlAbnormalityRepository  extends JpaRepository<AbnTlAbnormality, String> {
    
	AbnTlAbnormality findByKeyid(String keyid);

 @Modifying
        @Transactional
        @Query(value = """
                       INSERT INTO ABN_TL_ABNHISTORY SELECT * FROM ABN_TL_ABNORMALITY WHERE ABNM_KEYID= :keyid
                        """, nativeQuery = true)
        void insertABNHISTORY(@Param("keyid") String keyid);

    @Query(value = """
        SELECT  '1' AS hdnAbnmdKeyid, 
						'2' AS dteAbnmDetectiondate, 
						'3' AS cmbAbnmDetectedby, 
						'4' AS Detectedby, 
						'5' AS txtAbnmDescription, 
						'6' AS cmbAbnmEquipmentid, 
						'7' AS Equipment, 
						'8' AS cmbAbnmTypeid, 
						'9' AS AbnormalityType, 
						'10' AS cmbAbnmSubtype, 
						'11' AS SubType, 
						'12' AS cmbAbnmCategoryid, 
						'13' AS AbnormalityCategory, 
						'14' AS cmbAbnmTagclassid, 
						'15' AS TagClass, 
						'16' AS cmbAbnmImpactid, 
						'17' AS AbnormalityImpact, 
						'18' AS cmbAbnmTradeid, 
						'19' AS MaintainanceSection, 
						'20' AS cmbAbnmResponsibleid, 
						'21' AS Responsibiltyby, 
						'22' AS cmbAbnmStatus, 
						'23' AS dteAbnmTargetdate, 
						'24' AS txtAbnmCountermeasure, 
						'25' AS cmbAbnmCompletedby, 
						'26' AS completedby, 
						'27' AS dteAbnmWoendtime, 
						'28' AS ISM, 
						'29' AS RefDocID, 
						'30' AS txtAbnmRemarks, '31' AS btnActionPlan, '32' AS btnFilManage
						Union All
                        SELECT ABNM_KEYID AS hdnAbnmdKeyid, 
						TO_CHAR(ABNM_DATE, 'DD-Mon-YYYY') AS dteAbnmDetectiondate, 
						CASE WHEN ABNM_DETECTEDBY = A.EMPM_KEYID THEN A.EMPM_NAME || '-' || A.EMPM_CODE ELSE '-' END AS cmbAbnmDetectedby, 
						ABNM_DETECTEDBY AS Detectedby, 
						ABNM_DESCRIPTION AS txtAbnmDescription, 
						ABNM_EQUIPMENTID AS cmbAbnmEquipmentid, 
						ABNM_EQUIPMENTID AS Equipment, 
						ABTM_NAME AS cmbAbnmTypeid, 
						ABNM_TYPEID AS AbnormalityType, 
						ABNM_SUBTYPE AS cmbAbnmSubtype, 
						ABNM_SUBTYPE AS SubType, 
						ABCM_NAME AS cmbAbnmCategoryid, 
						ABNM_CATEGORYID AS AbnormalityCategory, 
						TAGM_NAME AS cmbAbnmTagclassid, 
						ABNM_TAGCLASSID AS TagClass, 
						ABIM_NAME AS cmbAbnmImpactid, 
						ABNM_IMPACTID AS AbnormalityImpact, 
						TRDM_NAME AS cmbAbnmTradeid, 
						ABNM_TRADEID AS MaintainanceSection, 
						CASE WHEN ABNM_RESPONSIBLEID = B.EMPM_KEYID THEN B.EMPM_NAME || '-' || B.EMPM_CODE ELSE '-' END AS cmbAbnmResponsibleid, 
						ABNM_RESPONSIBLEID AS Responsibiltyby, 
						CASE ABNM_STATUS WHEN 'P' THEN 'Pending' WHEN 'C' THEN 'Completed' ELSE ABNM_STATUS END AS cmbAbnmStatus,  
						TO_CHAR(ABNM_TARGETDATE, 'DD-MON-YYYY') AS dteAbnmTargetdate, 
						ABNM_COUNTERMEASURE AS txtAbnmCountermeasure, 
						CASE WHEN ABNM_COMPLETEDBY = C.EMPM_KEYID THEN C.EMPM_NAME || '-' || C.EMPM_CODE ELSE '-' END AS cmbAbnmCompletedby, 
						ABNM_COMPLETEDBY AS completedby, 
						TO_CHAR(ABNM_WOENDTIME, 'DD-MON-YYYY') AS dteAbnmWoendtime, 
						'' AS ISM, 
						ABNM_REFDOCID AS RefDocID, 
						ABNM_REMARKS AS txtAbnmRemarks, '' AS btnActionPlan, '' AS btnFilManage
						 FROM ABN_TL_ABNORMALITY 
						LEFT JOIN GEN_TL_EMPLOYEEMST A ON ABNM_DETECTEDBY = A.EMPM_KEYID 
						LEFT JOIN GEN_TL_EMPLOYEEMST B ON ABNM_RESPONSIBLEID = B.EMPM_KEYID 
						LEFT JOIN GEN_TL_EMPLOYEEMST C  ON ABNM_COMPLETEDBY = C.EMPM_KEYID 
						LEFT JOIN ABN_TL_CATEGORYMST  ON ABNM_CATEGORYID = ABCM_KEYID 
						LEFT JOIN ABN_TL_IMPACTMST  ON ABNM_IMPACTID = ABIM_KEYID 
						LEFT JOIN ABN_TL_TYPEMST  ON ABNM_TYPEID = ABTM_KEYID 
						LEFT JOIN ABN_TL_TAGMST  ON ABNM_TAGCLASSID = TAGM_KEYID 
						LEFT JOIN GEN_TL_TRADEMST  ON ABNM_TRADEID = TRDM_KEYID 
						WHERE 1=1 
                        AND ABNM_KEYID IN (:abnKeyIds) ORDER BY hdnAbnmdKeyid  
        """, nativeQuery = true)
    List<Map<String, Object>> findMultipleAbn(@Param("abnKeyIds") List<String> abnKeyIds);


	@Query(value = """
        SELECT DISTINCT
            ''::text AS selectv,
            CASE WHEN dmdm_keyid IS NULL OR dmdm_keyid = '' THEN '' ELSE '+' END AS attachment,
            abnm_keyid AS tagno,
            upper(to_char(abnm_detectiondate, 'DD-MON-YYYY HH24:MI')) AS detecteddate,
            abnm_description AS item,
            mchm_machinename AS machinemname,
            a.empm_name AS detectedby,
            c.empm_name AS responsiblityby,
            CASE ABNM_REFDOCID WHEN '{}' THEN '-' ELSE ABNM_REFDOCID END AS mwno,
            tagm_name AS tagclass,
            functionalloc AS funloc,
            assm_name AS assembly,
            TO_CHAR(ABNM_DETECTIONDATE, 'DD-MON-YYYY') AS occureddate,
            abtm_name AS abnormalitytype,
            abnm_whyabnhappened AS whyabnormality,
            abnm_whatcause AS whatcause,
            abcm_name AS abnormalitycategory,
            abim_name AS abnormalityimpact,
            abnm_countermeasure AS countermeasure,
            abnm_remarks AS remarks,
            to_char(abnm_targetdate, 'DD-MON-YYYY') AS targetdate,
            CASE abnm_status
                WHEN 'P' THEN 'PENDING'
                WHEN 'W' THEN 'WORK ORDER'
                WHEN 'C' THEN 'COMPLETED'
                WHEN 'D' THEN 'CANCELLED'
                ELSE abnm_status
            END AS status,
            REPLACE(TO_CHAR(ABNM_WOSTARTTIME, 'DD-MON-YYYY HH24:MI'), '01-JAN-1801 00:00', '') AS wostart,
            CASE
                WHEN TAGM_ISTHROUGHWO = 'N' THEN
                    REPLACE(REPLACE(TO_CHAR(ABNM_WOENDTIME, 'DD-MON-YYYY HH24:MI'),'31-DEC-2100 00:00',''),'01-JAN-1801 00:00', '')
                ELSE
                    REPLACE(TO_CHAR(ABNM_WOENDTIME, 'DD-MON-YYYY HH24:MI'),'31-DEC-2100 00:00','')
            END AS woend,
            replace(replace(to_char(abnm_woendtime, 'DD-MON-YYYY'),'31-DEC-2100',''),'01-JAN-1801','') AS completeddate,
            CASE WHEN abnm_status = 'P' THEN '' ELSE b.empm_name END AS workdoneby,
            to_char(
                CASE
                    WHEN abnm_status = 'P' THEN
                        CASE WHEN current_date - abnm_detectiondate::date < 0 THEN 0
                             ELSE (current_date - abnm_detectiondate::date) END
                    WHEN abnm_status = 'C' THEN
                        CASE WHEN abnm_woendtime::date - abnm_detectiondate::date < 0 THEN 0
                             ELSE (abnm_woendtime::date - abnm_detectiondate::date) END
                    ELSE NULL
                END,
            'FM9999999') AS days,
            apld.apld_status,
            abnm_refdoctype,
            replace(abnm_refdocid,'{}','') AS refdoc,
            CASE
                WHEN TAGM_ISTHROUGHWO = 'N' THEN TO_CHAR(ABNM_WORECEIVEDDATE, 'DD-MON-YYYY')
                ELSE TO_CHAR(ABNM_WORECEIVEDDATE, 'DD-MON-YYYY HH24:MI')
            END AS abnm_receiveddate,
            abnm_downtime::text AS downtime,
            c.empm_name AS manpower,
            to_char(abnm_detectiondate, 'YYYYMMDD') AS orderdate
        FROM abn_tl_abnormality abn
            LEFT JOIN gen_tl_employeemst a ON abn.abnm_detectedby = a.empm_keyid
            LEFT JOIN gen_vw_fnln fnln ON abn.abnm_flid = fnln.fnln_keyid
            LEFT JOIN abn_tl_typemst abtm ON abn.abnm_typeid = abtm.abtm_keyid
            LEFT JOIN abn_tl_categorymst abcm ON abn.abnm_categoryid = abcm.abcm_keyid
            LEFT JOIN abn_tl_impactmst abim ON abn.abnm_impactid = abim.abim_keyid
            LEFT JOIN gen_tl_employeemst b ON abn.abnm_completedby = b.empm_keyid
            LEFT JOIN gen_tl_employeemst c ON abn.abnm_responsibleid = c.empm_keyid
            LEFT JOIN dcm_tl_documentmanager dmdm ON dmdm.dmdm_refdocno = abn.abnm_keyid
            LEFT JOIN abn_tl_tagmst tagm ON tagm.tagm_keyid = abn.abnm_tagclassid
            LEFT JOIN gen_tl_actionplanmst aplm ON aplm.aplm_detailrefid = abn.abnm_keyid
            LEFT JOIN gen_tl_actionplandtl apld ON aplm.aplm_keyid = apld.apld_aplm_keyid
            LEFT JOIN gen_tl_assemblymst ON abnm_assemblyid = assm_keyid
        WHERE abn.abnm_active = 'Y'
            AND abn.abnm_keyid IN (:keyIds)
        """, nativeQuery = true)
    List<Map<String, Object>> getUpdatedRowAbn(@Param("keyIds") List<String> keyIds);
}
