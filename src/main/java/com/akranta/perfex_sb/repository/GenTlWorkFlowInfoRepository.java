package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.akranta.perfex_sb.model.GenTlWorkFlowInfo;

import jakarta.transaction.Transactional;

public interface GenTlWorkFlowInfoRepository extends JpaRepository<GenTlWorkFlowInfo, String>  { 
    

    	
    @Modifying
     @Query(value = """
        UPDATE GEN_TL_WORKFLOW_INFO SET WRIN_STATUS = 'A',WRIN_DATE = CURRENT_DATE where WRIN_REF_ID = :refId AND WRIN_STATUS IN ('C','E','-') AND WRIN_REF_TYPE IN ('PRODE','PRODEGE5L','PRODEGE1C') AND WRIN_EMPLOYEE_ID = :empId
        """, nativeQuery = true)
    int UpdateWorkFlowStatus(@Param("refId") String refId,@Param("empId") String empId);

    



    @Query(value = """ 
       SELECT COUNT(*) FROM GEN_TL_WORKFLOW_INFO 
 WHERE 1 = 1  AND WRIN_REF_TYPE = :refType  AND WRIN_REF_ID = :refId  
 AND WRIN_ROLE_ID IN ( 
  SELECT ROLE_KEYID  FROM ( SELECT ROLE_KEYID,ROLE_NAME,WRKD_KEYID, 
       COALESCE(WRIN_STATUS, 'Pending') AS WRIN_STATUS,WRIN_DATE, 
	WRIN_REMARKS, ROLE_LEVEL  FROM GEN_TL_WORKFLOW_MENU_LINK AS ML 
	JOIN GEN_TL_WORKFLOWDTL AS D  ON ML.WRML_WRKM_KEYID = D.WRKD_WRKM_KEYID 
	JOIN ADM_TL_ROLEMST AS R ON D.WRKD_STAGE = R.ROLE_KEYID 
	 LEFT JOIN GEN_TL_WORKFLOW_INFO AS I ON I.WRIN_WRML_KEYID = ML.WRML_KEYID 
	 AND I.WRIN_REF_ID = :refId  AND I.WRIN_REF_TYPE = :refType 
	WHERE ML.WRML_TRANS_CODE = :transCode 
	ORDER BY D.WRKD_KEYID DESC 
	LIMIT 1 
) AS X 
) AND WRIN_STATUS = 'A'
  """, nativeQuery = true)
int getWorkFlowStatusCount(@Param("refId") String refId,@Param("refType") String refType,@Param("transCode") String transCode);


     @Query(value = """
        SELECT WRML_KEYID FROM GEN_TL_WORKFLOW_MENU_LINK WHERE WRML_TRANS_CODE =:transCode
        """, nativeQuery = true)
    String getMenuLinkKeyid(@Param("transCode") String transCode);

     @Query(value = """
        SELECT WRKD_KEYID from GEN_TL_WORKFLOWDTL  WHERE WRKD_WRKM_KEYID = (SELECT WRML_WRKM_KEYID FROM GEN_TL_WORKFLOW_MENU_LINK WHERE WRML_TRANS_CODE= :transCode ) AND WRKD_STAGE= :roleId
        """, nativeQuery = true)
    String getDetailKeyid(@Param("transCode") String transCode,@Param("roleId") String roleId);


    @Modifying
    @Query(value = """
       UPDATE GEN_TL_WORKFLOW_INFO SET WRIN_STATUS = 'E' where WRIN_REF_ID = :refId  AND WRIN_STATUS ='A' AND WRIN_REF_TYPE = :refType
        """, nativeQuery = true)
    int updateReworkStatus(@Param("refId") String refId,@Param("refType") String refType);



    @Modifying
    @Query(value = """
       UPDATE ADM_APPROVALS_LIST SET APPROVALEMPID = :nextEmpId WHERE DOCUMENTNO = :refId
        """, nativeQuery = true)
    int updateAdmApproval(@Param("nextEmpId") String nextEmpId,@Param("refId") String refId);

    @Modifying
    @Query(value = """
       DELETE FROM  ADM_APPROVALS_LIST WHERE DOCUMENTNO = :refId
        """, nativeQuery = true)
    int deleteAdmApproval(@Param("refId") String refId);

    @Modifying
    @Query(value = """
      UPDATE KZN_TL_MST SET KZNM_STATUS= :status ,KZNM_APROV_LEVEL= :nextRoleName WHERE KZNM_KEYID= :keyid
        """, nativeQuery = true)
    int updateKZNStatus(@Param("status") Character status,@Param("nextRoleName") String nextRoleName,@Param("keyid") String keyid);

    @Modifying
    @Query(value = """
      UPDATE OPL_TL_MST SET OPLM_STATUS= :status ,OPLM_APROV_LEVEL= :nextRoleName WHERE OPLM_KEYID= :keyid
        """, nativeQuery = true)
    int updateOPLStatus(@Param("status") Character status,@Param("nextRoleName") String nextRoleName,@Param("keyid") String keyid);


    @Modifying
@Transactional
@Query(value = """   
                        UPDATE GEN_TL_WORKFLOW_INFO
                        SET WRIN_STATUS = 'E'
                        WHERE WRIN_REF_ID = :ref_id
                        AND WRIN_WRML_KEYID = :wrml_keyid
                        AND WRIN_REF_TYPE = :ref_type
                        
        """, nativeQuery = true)                  
        void updateReject(@Param("ref_id") String ref_id, @Param("wrml_keyid")String wrml_keyid ,@Param("ref_type") String ref_type);


}
