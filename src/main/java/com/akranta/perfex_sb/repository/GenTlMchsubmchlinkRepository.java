package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.GenTlMchsubmchlink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenTlMchsubmchlinkRepository extends JpaRepository<GenTlMchsubmchlink, String> {
    
    @Modifying
    @Query(value = "DELETE FROM GEN_TL_MCHSUBMCHLINK WHERE SCML_PARENTMCHID = :parentMachineId", 
           nativeQuery = true)
    int deleteByParentMachineId(@Param("parentMachineId") String parentMachineId);
}