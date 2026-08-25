package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.GenTlMchmaintteamlink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenTlMchmaintteamlinkRepository extends JpaRepository<GenTlMchmaintteamlink, String> {
    
    @Modifying
    @Query(value = "DELETE FROM GEN_TL_MCHMAINTTEAMLINK WHERE MCMT_MACHINEID = :mchmKeyid", 
           nativeQuery = true)
    int deleteByMachineId(@Param("mchmKeyid") String mchmKeyid);
}