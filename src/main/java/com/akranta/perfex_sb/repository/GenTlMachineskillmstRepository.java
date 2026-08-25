package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.GenTlMachineskillmst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenTlMachineskillmstRepository extends JpaRepository<GenTlMachineskillmst, String> {
    
    @Modifying
    @Query(value = "DELETE FROM GEN_TL_MACHINESKILLMST WHERE MSKM_MACHINEID = :mchmKeyid AND MSKM_SKILLFORDEPARTMENT = 'O'", 
           nativeQuery = true)
    int deleteOperatorSkills(@Param("mchmKeyid") String mchmKeyid);
    
    @Modifying
    @Query(value = "DELETE FROM GEN_TL_MACHINESKILLMST WHERE MSKM_MACHINEID = :mchmKeyid AND MSKM_SKILLFORDEPARTMENT = 'M'", 
           nativeQuery = true)
    int deleteMaintenanceSkills(@Param("mchmKeyid") String mchmKeyid);
}