package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.SheTlIncidentEmployeenew;

public interface SheTlIncidentEmployeenewRepository extends JpaRepository<SheTlIncidentEmployeenew,String>{
     @Modifying
    @Transactional
    @Query(
    value = "UPDATE SHE_TL_INCIDENTMSTNEW " +
            "SET SINM_NOOFEMPLOYEES = :empCnt " +
            "WHERE SINM_KEYID = :incId",
    nativeQuery = true
    )
    int updateNoOfEmployees(
        @Param("empCnt") Integer empCnt,
        @Param("incId") String incId
    );

    @Query(
    value = "SELECT COUNT(*) " +
            "FROM SHE_TL_INCIDENT_EMPLOYEENEW " +
            "WHERE SINE_SINC_KEYID = :incId",
    nativeQuery = true
    )
    int getInjuryCount(@Param("incId") String incId);


      @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM SHE_TL_INCIDENT_EMPLOYEENEW WHERE SINE_SINC_KEYID = :keyId",
        nativeQuery = true
    )
    int deleteByKeyId(String keyId);
}
    

