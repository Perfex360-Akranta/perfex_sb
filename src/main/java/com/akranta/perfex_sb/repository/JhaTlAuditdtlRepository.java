package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.JhaTlAuditdtl;

import jakarta.transaction.Transactional;

import java.util.List;


public interface JhaTlAuditdtlRepository extends JpaRepository<JhaTlAuditdtl,String>{

    List<JhaTlAuditdtl> findByJhauditmasterid(String jhauditmasterid);

    //delete records in the detail table
     @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM jha_tl_auditdtl WHERE jhad_jhauditmasterid = :masterkeyid",
        nativeQuery = true
    )
    int deleteByMasterId(@Param("masterkeyid") String masterkeyid);
    
}
    