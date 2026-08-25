package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.GenTlMchemplink;
import com.akranta.perfex_sb.model.GenTlMchemplinkId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenTlMchemplinkRepository extends JpaRepository<GenTlMchemplink, GenTlMchemplinkId> {
    
    @Modifying
    @Query(value = "DELETE FROM GEN_TL_MCHEMPLINK WHERE MCEM_MACHINEID = :machineId", 
           nativeQuery = true)
    int deleteByMachineId(@Param("machineId") String machineId);
}