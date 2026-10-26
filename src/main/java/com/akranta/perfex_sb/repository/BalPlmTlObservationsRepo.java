package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.BalPlmTlObservations;


public interface BalPlmTlObservationsRepo extends JpaRepository<BalPlmTlObservations,String>
 {

    @Modifying
@Transactional
@Query(value = """
       UPDATE BAL_PLM_TL_OBSERVATIONS
       SET OBSV_STATUS = 'A'
       WHERE OBSV_KEYID = :observationId
       """, nativeQuery = true)
int updateObservationStatus(
        @Param("observationId") String observationId);

}
