package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.dto.ProjectResponseDto;
import com.akranta.perfex_sb.model.KznTlProjectcreationmst;

public interface KznTlProjectcreationmstRepository extends JpaRepository<KznTlProjectcreationmst, String> {
    
    
    @Query(value = "SELECT ROLE_KEYID FROM ADM_TL_ROLEMST WHERE UPPER(COALESCE(ROLE_NAME, ' ')) = :rolename", nativeQuery = true)
    String getRoleKeyid(@Param("rolename") String roleName);

     @Query(value = """ 
        SELECT  K.KZPM_KEYID as keyid, K.KZPM_FLID as flid,to_char(K.KZPM_STARTDATE,'dd-Mon-YYYY') as startdate,to_char(K.KZPM_ENDDATE,'dd-Mon-YYYY') as enddate, K.KZPM_PROJECTNAME as projectname, K.KZPM_AREA as area,K.KZPM_PROJECTCHAMP as projectchamp, K.KZPM_PROJECTNO as projectno, K.KZPM_BENEFITS as benefits,
		 K.KZPM_SAVINGS as savings, K.KZPM_PROJECTMETRICS as projectmetrics, K.KZPM_PROBLEMSTATEMENT as problemstatement,K.KZPM_BUSINESSCASE as businesscase, K.KZPM_GOALOBJ as goalobj, K.KZPM_SCOPECONST as scopeconst, K.KZPM_DEFINESTAGE ::text as definestage, K.KZPM_MEASURESTAGE ::text as measurestage,K.KZPM_ANALYSESTAGE ::text as analysestage,K.KZPM_CONTROLSTAGE ::text as controlstage,K.KZPM_IMPROVESTAGE ::text as improvestage, K.KZPM_CLOSURESTAGE ::text as closurestage, 
		 to_char(K.KZPM_DEFINETARGETDATE,'dd-Mon-YYYY') as definetargetdate,to_char(K.KZPM_MEASURETARGETDATE,'dd-Mon-YYYY') as measuretargetdate,to_char(K.KZPM_ANALYSETARGETDATE,'dd-Mon-YYYY') as analysetargetdate,to_char(K.KZPM_IMPROVETARGETDATE,'dd-Mon-YYYY') as improvetargetdate,to_char(K.KZPM_CONTROLTARGETDATE,'dd-Mon-YYYY') as controltargetdate,to_char(K.KZPM_CLOSURETARGETDATE,'dd-Mon-YYYY')  as closuretargetdate,
		 to_char(K.KZPM_DEFINECOMPLETEDDATE,'dd-Mon-YYYY') as definecompleteddate,to_char(K.KZPM_MEASURECOMPLETEDDATE,'dd-Mon-YYYY') as measurecompleteddate,to_char(K.KZPM_ANALYSECOMPLETEDDATE,'dd-Mon-YYYY') as analysecompleteddate,to_char(K.KZPM_IMPROVECOMPLETEDDATE,'dd-Mon-YYYY') as improvecompleteddate,to_char(K.KZPM_CONTROLCOMPLETEDDATE,'dd-Mon-YYYY') as controlcompleteddate,to_char(K.KZPM_CLOSURECOMPLETEDDATE,'dd-Mon-YYYY') as closurecompleteddate,
		 K.KZPM_IMPRCATEGORY as imprcategory, K.KZPM_ISTANGIBLE ::text as istangible, K.KZPM_ISINTANGIBLE ::text as isintangible, KZPM_VERIFIEDAMNT ::text as verifiedamnt,KZPM_FINALAMNT ::text as finalamnt ,KZPM_AMTVERIFYREMARKS as amtverifyremark,KZPM_WAVE ::text as wave,KZPM_OLDRESPONSIBILITY as oldresponsibility,
		 KZPM_BELT as belt, K.KZPM_TEMPFIELD4 ::text as tempfield4,K.KZPM_ACTIVE ::text as active, K.KZPM_CREATEDBY  as createdby, K.KZPM_CREATEDON ::text as createdon, K.KZPM_MODIFIEDON ::text as modifiedon FROM KZN_TL_PROJECTCREATIONMST K WHERE KZPM_KEYID = :keyid 
    """, nativeQuery = true)
         ProjectResponseDto getProjectById(@Param("keyid") String keyid);


    @Modifying
    @Query(value = "Update KZN_TL_PROJECTCREATIONMST set KZPM_DEFINESTAGE='P' where KZPM_KEYID= :kzpmKeyid ", nativeQuery = true)
    int UpdateDefineStage(@Param("kzpmKeyid") String kzpmKeyid);

    @Modifying
    @Query(value = "Update KZN_TL_PROJECTCREATIONMST set KZPM_DEFINESTAGE=:status where KZPM_KEYID= :kzpmKeyid ", nativeQuery = true)
    int UpdateDefineStageWithStatus(@Param("kzpmKeyid") String kzpmKeyid,@Param("status") Character status);

    @Modifying
    @Query(value = "Update KZN_TL_PROJECTCREATIONMST set KZPM_DEFINESTAGE='C',KZPM_DEFINECOMPLETEDDATE = CURRENT_TIMESTAMP where KZPM_KEYID= :kzpmKeyid ", nativeQuery = true)
    int UpdateDefineCompleteStage(@Param("kzpmKeyid") String kzpmKeyid);

    @Modifying
    @Query(value = "Update KZN_TL_PROJECTCREATIONMST set KZPM_MEASURESTAGE='P' where KZPM_KEYID= :kzpmKeyid ", nativeQuery = true)
    int UpdateMeasureStage(@Param("kzpmKeyid") String kzpmKeyid);


    @Modifying
    @Query(value = "Update KZN_TL_PROJECTCREATIONMST set KZPM_MEASURESTAGE='C' , KZPM_MEASURECOMPLETEDDATE = CURRENT_TIMESTAMP where KZPM_KEYID= :kzpmKeyid ", nativeQuery = true)
    int UpdateMeasureCompleteStage(@Param("kzpmKeyid") String kzpmKeyid);
    
    @Modifying
    @Query(value = "Update KZN_TL_PROJECTCREATIONMST set KZPM_ANALYSESTAGE='C' , KZPM_ANALYSECOMPLETEDDATE = CURRENT_TIMESTAMP where KZPM_KEYID= :kzpmKeyid ", nativeQuery = true)
    int UpdateAnalyseCompleteStage(@Param("kzpmKeyid") String kzpmKeyid);
    
    @Modifying
    @Query(value = "Update KZN_TL_PROJECTCREATIONMST set KZPM_IMPROVESTAGE='C' , KZPM_IMPROVECOMPLETEDDATE = CURRENT_TIMESTAMP where KZPM_KEYID= :kzpmKeyid ", nativeQuery = true)
    int UpdateImproveCompleteStage(@Param("kzpmKeyid") String kzpmKeyid);
    
    @Modifying
    @Query(value = "Update KZN_TL_PROJECTCREATIONMST set KZPM_CONTROLSTAGE='C' , KZPM_CONTROLCOMPLETEDDATE = CURRENT_TIMESTAMP where KZPM_KEYID= :kzpmKeyid ", nativeQuery = true)
    int UpdateControlCompleteStage(@Param("kzpmKeyid") String kzpmKeyid);

    @Modifying
    @Query(value = "Update KZN_TL_PROJECTCREATIONMST set KZPM_CLOSURESTAGE='C' , KZPM_CLOSURECOMPLETEDDATE = CURRENT_TIMESTAMP where KZPM_KEYID= :kzpmKeyid ", nativeQuery = true)
    int UpdateClosureCompleteStage(@Param("kzpmKeyid") String kzpmKeyid);

    @Modifying
    @Query(value = "Update KZN_TL_PROJECTCREATIONMST set KZPM_CLOSURESTAGE=:status  where KZPM_KEYID= :kzpmKeyid ", nativeQuery = true)
    int UpdateClosureStageWithStatus(@Param("kzpmKeyid") String kzpmKeyid,@Param("status") Character status);
                  
		
}
