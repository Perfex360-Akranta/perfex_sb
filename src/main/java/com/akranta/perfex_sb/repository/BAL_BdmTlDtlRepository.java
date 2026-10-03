package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_BdmTlDtl;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
