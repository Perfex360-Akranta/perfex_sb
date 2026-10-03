

package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.Balworespmst;

import jakarta.transaction.Transactional;

public interface BalworespmstRepository extends JpaRepository<Balworespmst, String> {
   //for planconfigsave
  long countByPwrmmachineid(String pwrmmachineid);


    //13 sep
    @Query(value = "SELECT CELL_FACTORYID, CELL_SECTIONID, MCHM_CELLID, MCHM_KEYID " +
            "FROM GEN_TL_MACHINEMST, GEN_TL_CELLMST WHERE MCHM_ACTIVE = 'Y' " +
            "AND MCHM_CELLID = CELL_KEYID AND MCHM_KEYID = :machineId " +
            "EXCEPT " +
            "SELECT PWRM_FACTORYID, PWRM_SECTIONID, PWRM_CELLID, PWRM_MACHINEID " +
            "FROM BAL_PLM_TL_WORESPMST WHERE PWRM_MACHINEID = :machineId",
            nativeQuery = true)
    List<Object[]> findMachineWithoutResponsibility(@Param("machineId") String machineId);

    @Query(value = "SELECT PWRM_FACTORYID, PWRM_SECTIONID, PWRM_CELLID, PWRM_MACHINEID, PWRM_KEYID " +
            "FROM BAL_PLM_TL_WORESPMST WHERE PWRM_MACHINEID = :machineId AND PWRM_LEVEL = 'M'",
            nativeQuery = true)
    List<Object[]> findMachineLevelResponsibility(@Param("machineId") String machineId);
}