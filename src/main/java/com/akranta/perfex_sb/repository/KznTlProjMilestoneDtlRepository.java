package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.KznTlProjMilestoneDtl;


public interface KznTlProjMilestoneDtlRepository extends JpaRepository<KznTlProjMilestoneDtl, String> {
     @Query(value = """
      Select KMMD_TARGETDATE from KZN_TL_PROJ_MILESTONE_DTL where kmmd_keyid =:keyid 
              """, nativeQuery = true)
    LocalDateTime getExistingTarget(@Param("keyid") String keyid);

    @Modifying
    @Query(value = """
      Delete from Kzn_Tl_PROJ_MILESTONE_DTL where kmmd_keyid = :keyid 
              """, nativeQuery = true)
    int deleteDetailMilestone(@Param("keyid") String keyid);

    @Modifying
    @Query(value = """
      Delete from Kzn_Tl_PROJ_MILESTONE_DTL where KMMD_KMMM_KEYID = :keyid 
              """, nativeQuery = true)
    int deleteAllDetailMilestone(@Param("keyid") String keyid);

    @Modifying
     @Query(value = """
       INSERT INTO Kzn_Tl_PROJ_MILESTONE_DTL_HIS 
							 SELECT  
							 KMMD_KEYID,KMMD_KMMM_KEYID,KMMD_MILESTONE,KMMD_DESCRIPTION,KMMD_TARGETDATE,
							 KMMD_EMPM_KEYID,KMMD_STATUS,KMMD_REMARKS,KMMD_TEMPFIELD1,KMMD_TEMPFIELD2,
							 KMMD_TEMPFIELD3,KMMD_TEMPFIELD4,KMMD_TEMPFIELD5,KMMD_CREATEDBY,KMMD_ACTIVE,KMMD_CREATEDON,KMMD_MODIFIEDON FROM Kzn_Tl_PROJ_MILESTONE_DTL 
							 WHERE KMMD_KEYID = :keyid 
              """, nativeQuery = true)
    int insertintoDtlHis(@Param("keyid") String keyid);

    @Modifying
     @Query(value = """
        UPDATE KZN_TL_PROJ_MILESTONE_MST 
				SET KMMM_STATUS = ( 
				    SELECT  
				        CASE  
				            WHEN COUNT(*) = SUM(CASE WHEN KMMD_STATUS = 'S' THEN 1 ELSE 0 END)  
				                THEN 'S' 
				            WHEN SUM(CASE WHEN KMMD_STATUS IN ('C','S') THEN 1 ELSE 0 END) = COUNT(*)  
				                THEN 'C' 
				            WHEN SUM(CASE WHEN KMMD_STATUS IN ('P','W') THEN 1 ELSE 0 END) = COUNT(*)  
				                THEN 'P' 
				            WHEN SUM(CASE WHEN KMMD_STATUS IN ('W','C') THEN 1 ELSE 0 END) = COUNT(*)  
				                THEN 'W' 
				            ELSE 'P' 
				        END AS STATUS 
				    FROM KZN_TL_PROJ_MILESTONE_DTL 
				    WHERE KMMD_KMMM_KEYID = :keyid 
				) WHERE KMMM_KEYID =  :keyid 
              """, nativeQuery = true)
    int updateMaster(@Param("keyid") String keyid);
}
