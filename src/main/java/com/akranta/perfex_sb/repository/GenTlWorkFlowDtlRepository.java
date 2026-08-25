package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.GenTlWorkFlowDtl;

public interface GenTlWorkFlowDtlRepository extends JpaRepository<GenTlWorkFlowDtl, String> {
    
    @Modifying
    @Query(value = " DELETE from  GEN_TL_WORKFLOWDTL  where  WRKD_KEYID in :ids ", nativeQuery = true)
    int deleteWorkFlowDtl(@Param("ids") List<String> ids);

    @Modifying
    @Query(value = " DELETE from  GEN_TL_WORKFLOWDTL  where  WRKD_WRKM_KEYID = :masterid ", nativeQuery = true)
    int deleteAllWorkFlowDtl(@Param("masterid") String masterid);
}
