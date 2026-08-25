package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.GenTlWorkFlowMst;

public interface GenTlWorkFlowMstRepository extends JpaRepository<GenTlWorkFlowMst, String> {
    @Modifying
    @Query(value = " DELETE from  GEN_TL_WORKFLOWMST  where  WRKM_KEYID = :keyid ", nativeQuery = true)
    int deleteWorkFlowMst(@Param("keyid") String keyid);
}
