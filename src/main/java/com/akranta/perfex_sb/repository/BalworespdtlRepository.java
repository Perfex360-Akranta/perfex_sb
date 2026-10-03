
package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.Balworespdtl;

import jakarta.transaction.Transactional;

public interface BalworespdtlRepository extends JpaRepository<Balworespdtl, String> {

    List<Balworespdtl> findByPwrdmasterid(String pwrdmasterid); 

    // mirrors: select PWRD_KEYID from BAL_PLM_TL_WORESPDTL where PWRD_MASTERID = :masterId
    @Query(value = "SELECT PWRD_KEYID FROM BAL_PLM_TL_WORESPDTL WHERE PWRD_MASTERID = :masterId", nativeQuery = true)
    List<String> findKeyIdsByMasterId(@Param("masterId") String masterId);

    // mirrors: select PWRD_TRADEID from BAL_PLM_TL_WORESPDTL where PWRD_MASTERID = :masterId
    @Query(value = "SELECT PWRD_TRADEID FROM BAL_PLM_TL_WORESPDTL WHERE PWRD_MASTERID = :masterId", nativeQuery = true)
    List<String> findTradeIdsByMasterId(@Param("masterId") String masterId);

    // mirrors: DELETE FROM BAL_PLM_TL_WORESPDTL WHERE PWRD_MASTERID = :masterId AND PWRD_TRADEID = :tradeId
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM BAL_PLM_TL_WORESPDTL WHERE PWRD_MASTERID = :masterId AND PWRD_TRADEID = :tradeId", nativeQuery = true)
    int deleteByMasteridAndTradeid(@Param("masterId") String masterId, @Param("tradeId") String tradeId);
}