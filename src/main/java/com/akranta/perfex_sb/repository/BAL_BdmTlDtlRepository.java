package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_BdmTlDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;


public interface BAL_BdmTlDtlRepository extends JpaRepository<BAL_BdmTlDtl, String> {

  //  Optional<BAL_BdmTlDtl> findByBdms_keyid(String bdmsKeyid);
   @Query(value = """
        SELECT * FROM BAL_BDM_TL_DTL
        WHERE bdan_bdms_keyid = :bdmsKeyid
        """, nativeQuery = true)
    List<BAL_BdmTlDtl> findByBdmsKeyidNative(@Param("bdmsKeyid") String bdmsKeyid);
    //delete 
        // Legacy: BAL_BdmTlDtlSql.getDeleteSql (skips BDs already linked to PCS)
    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE FROM BAL_BDM_TL_DTL
            WHERE BDAN_BDMS_KEYID = :bdmsKeyid
              AND NOT EXISTS (SELECT 1 FROM BAL_PCS_TL_PCSBD WHERE PBD_BDM_KEYID = :bdmsKeyid)
            """, nativeQuery = true)
    int deleteByBdmsKeyidNative(@Param("bdmsKeyid") String bdmsKeyid);
}
