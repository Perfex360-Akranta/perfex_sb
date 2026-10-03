package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BalPlmTlMultipleResp;

import jakarta.transaction.Transactional;

public interface BalPlmTlMultipleRespRepo extends JpaRepository<BalPlmTlMultipleResp,String> 
{

    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM BAL_PLM_TL_MULTIPLE_RESP WHERE pmrs_keyid = :pmstandId",
        nativeQuery = true
    )
    int deleteAlloted(@Param("pmstandId") String pmstandId);



    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM bal_plm_tl_multiple_resp
            WHERE pmrs_refid = :refId
            AND pmrs_completed_empid = '-'
            """, nativeQuery = true)
    int deleteCompleted(@Param("refId") String refId);

}
