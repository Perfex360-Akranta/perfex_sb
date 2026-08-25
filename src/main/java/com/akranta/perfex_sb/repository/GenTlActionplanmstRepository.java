package com.akranta.perfex_sb.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.GenTlActionPlanMst;

public interface GenTlActionplanmstRepository  extends JpaRepository<GenTlActionPlanMst, String> {
    
    @Modifying
     @Query(value = """
        UPDATE GEN_TL_ACTIONPLANMST SET   APLM_STATUS=COALESCE((SELECT DISTINCT APLD_STATUS FROM GEN_TL_ACTIONPLANDTL  WHERE APLD_APLM_KEYID=:aplmKeyid and APLD_STATUS='P'),'C')  WHERE APLM_KEYID= :aplmKeyid 
        """, nativeQuery = true)
    int UpdateActionPlanMSt(@Param("aplmKeyid") String keyid);

    @Modifying
    @Query(value = """
         UPDATE  JHA_TL_AUDITDTL Set JHAD_NCREMARKS= :remarks , JHAD_NCACTIONPLAN = :keyid,
         JHAD_NCSTATUS = (SELECT APLM_STATUS FROM GEN_TL_ACTIONPLANMST WHERE APLM_KEYID= :keyid ) , 
         JHAD_NCCLOSED = (SELECT APLM_STATUS FROM GEN_TL_ACTIONPLANMST WHERE APLM_KEYID= :keyid ) 
         Where JHAD_KEYID = :refId 
	 """, nativeQuery = true)
    int UpdateJHAuditDTl(@Param("keyid") String keyid,@Param("remarks") String remarks,@Param("refId") String refId);

    @Modifying
    @Query(value = """
         UPDATE  JHA_TL_AUDITDTL Set JHAD_NCSTATUS = 'C' , JHAD_NCCLOSED = 'C' Where JHAD_KEYID IN ( Select APLM_DETAILREFID FROM  GEN_TL_ACTIONPLANMST  WHERE APLM_STATUS = 'C'  AND APLM_KEYID = :aplmKeyid ) 
	 """, nativeQuery = true)
    int UpdateJHAuditDTl(@Param("aplmKeyid") String keyid);

    @Modifying
    @Query(value = """
                   UPDATE moc_tl_reccomendations Set MOCR_STATUS = 'C' Where MOCR_WH_KEYID IN ( Select APLM_DETAILREFID FROM  GEN_TL_ACTIONPLANMST  WHERE APLM_STATUS = 'C'  AND APLM_KEYID = :aplmKeyid )
            """, nativeQuery = true)
    int UpdateMOCRecommendation(@Param("aplmKeyid") String keyid);

     @Modifying
    @Query(value = """
                   UPDATE PSSR_TL_RECCOMENDATIONS Set PSRR_STATUS = 'C' Where PSRR_KEYID IN ( Select APLM_DETAILREFID FROM  GEN_TL_ACTIONPLANMST  WHERE APLM_STATUS = 'C'  AND APLM_KEYID = :aplmKeyid )
            """, nativeQuery = true)
    int UpdateMOCPSSRecommendation(@Param("aplmKeyid") String keyid);

    @Modifying
     @Query(value = """
       DELETE from GEN_TL_ACTIONPLANMST a where NOT EXISTS ( select APLD_APLM_KEYID from GEN_TL_ACTIONPLANDTL  where APLD_APLM_KEYID = a.APLM_KEYID  and APLD_APLM_KEYID = :keyid ) 	and  APLM_KEYID = :keyid
        """, nativeQuery = true)
    int DeleteActionPlanMSt(@Param("keyid") String keyid);

}
