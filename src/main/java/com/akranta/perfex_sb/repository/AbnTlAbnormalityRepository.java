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
}
