package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.KznTlProjectChecklistLink;

public interface KznTlProjectChecklistLinkRepository extends JpaRepository<KznTlProjectChecklistLink, String> {
    
    @Query(value = """
        SELECT 
                    '0' AS PCLM_SLNO,
				    '1' AS PCLL_KEYID,
				    '2' AS PCLL_PROJECTID,
				    '3' AS PCLM_KEYID,
				    '4' AS PCLM_CHECKLIST,
				    '5' AS PCLL_INCLUDE,
				    '6' AS NotInclude,
                    '7' AS NotAppl,
				    '8' AS PCLL_VERIFIEDSTATUS,
				    '9' AS NotOk,
				    '10' AS filemanager,
				    '11' AS oldval
        UNION All
        SELECT 
		            PCLM.PCLM_SLNO AS PCLM_SLNO,		    
                    PCLL.PCLL_KEYID,
				    PCLL.PCLL_PROJECTID,
				    PCLM.PCLM_KEYID,
				    PCLM.PCLM_CHECKLIST,
				    PCLL.PCLL_INCLUDE,
				    CASE 
				        WHEN PCLL.PCLL_INCLUDE = 'N' THEN 'Y'
				        WHEN PCLL.PCLL_INCLUDE = 'Y' THEN 'N'
				        ELSE PCLL.PCLL_INCLUDE
				    END AS NotInclude,

				    CASE 
				        WHEN PCLL.PCLL_INCLUDE = 'X' THEN 'Y' 
				        WHEN PCLL.PCLL_INCLUDE = 'Y' THEN 'N' 
				        ELSE PCLL.PCLL_INCLUDE
				    END AS NotAppl,

				    PCLL.PCLL_VERIFIEDSTATUS,

				    CASE 
				        WHEN PCLL.PCLL_VERIFIEDSTATUS = 'N' THEN 'Y' 
				        WHEN PCLL.PCLL_VERIFIEDSTATUS = 'Y' THEN 'N' 
				        ELSE PCLL.PCLL_VERIFIEDSTATUS
				    END AS NotOk,
				    COUNT(DMDM.DMDM_REFDOCNO) AS filemanager,
				    (PCLL.PCLL_INCLUDE || PCLL.PCLL_VERIFIEDSTATUS) AS oldval
				 FROM 
				    KZN_TL_PROJECT_CHECKLISTMST PCLM
				    LEFT JOIN KZN_TL_PROJECT_CHECKLIST_LINK PCLL 
				        ON PCLL.PCLL_CHECKLISTID = PCLM.PCLM_KEYID
				        AND PCLL.PCLL_PROJECTID  = :projectId   
				    LEFT JOIN DCM_TL_DOCUMENTMANAGER DMDM 
				        ON DMDM.DMDM_REFDOCNO = PCLL.PCLL_KEYID 
				 WHERE
				    PCLM.PCLM_TYPE = :stage  
				GROUP BY
				    PCLM.PCLM_SLNO,
				    PCLL.PCLL_KEYID,
				    PCLL.PCLL_PROJECTID,
				    PCLM.PCLM_KEYID,
				    PCLM.PCLM_CHECKLIST,
				    PCLL.PCLL_VERIFIEDSTATUS,
				    PCLL.PCLL_INCLUDE,
				    (PCLL.PCLL_INCLUDE || PCLL.PCLL_VERIFIEDSTATUS)
				    ORDER BY
				    PCLM_SLNO 
        """, nativeQuery = true)
    List<Map<String, Object>> getChecklistByStage(@Param("projectId") String projectId,@Param("stage") String stage);

}
