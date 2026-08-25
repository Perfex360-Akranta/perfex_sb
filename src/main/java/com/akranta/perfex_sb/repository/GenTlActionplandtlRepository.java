package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.GenTlActionPlanDtl;

public interface GenTlActionplandtlRepository extends JpaRepository<GenTlActionPlanDtl, String> {
    
    @Modifying
     @Query(value = """
       DELETE from GEN_TL_ACTIONPLANDTL  where   APLD_KEYID = :keyid
        """, nativeQuery = true)
    int DeleteActionPlanDtl(@Param("keyid") String keyid);
}
