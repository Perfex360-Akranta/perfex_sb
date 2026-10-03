package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_PlmTlStandards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalPlmTlStandardsRepository extends JpaRepository<BAL_PlmTlStandards, String> {

    /**
     * Mirrors:
     * SELECT DISTINCT PMSD_FREQUENCYUNIT, PMSD_FREQUENCY FROM STANDARDS WHERE PMSD_MACHINEID = ?
     * Returns Object[]{ frequencyunit (Character), frequency (Double) } per row.
     */
    @Query("SELECT DISTINCT s.frequencyunit, s.frequency FROM BAL_PlmTlStandards s WHERE s.machineid = :machineid")
    List<Object[]> findDistinctFrequenciesByMachineid(@Param("machineid") String machineid);

    /**
     * Mirrors the WHERE clause of the original PMSD UPDATE statement.
     * assemblyid / frequency are treated as optional filters, same as the original
     * "if (isValidKeyId(...)) sqlUp.append(...)" conditional appends.
     * Pass null for assemblyid/frequency to skip that filter.
     */
    @Query("SELECT s FROM BAL_PlmTlStandards s WHERE s.machineid = :machineid AND s.active = 'Y' "
         + "AND s.frequencyunit = :frequencyunit "
         + "AND (:assemblyid IS NULL OR s.assemblyid = :assemblyid) "
         + "AND (:frequency IS NULL OR s.frequency = :frequency)")
    List<BAL_PlmTlStandards> findMatchingStandards(@Param("machineid") String machineid,
                                                     @Param("frequencyunit") Character frequencyunit,
                                                     @Param("assemblyid") String assemblyid,
                                                     @Param("frequency") Double frequency);

    /**
     * Mirrors the existence check done before calling generateCalendar(...)
     * (original: "select pmsd_keyid from standards where machineid = ?" + isValidKeyId check).
     */
    boolean existsByMachineid(String machineid);

    //13 sep for worresp
    @Modifying
    @Query(value = "UPDATE BAL_PLM_TL_STANDARDS SET PMSD_PREPAREDBYID = :empId " +
            "WHERE PMSD_MACHINEID = :machineId", nativeQuery = true)
    int updatePreparedByMachineWide(@Param("empId") String empId, @Param("machineId") String machineId);

    @Modifying
    @Query(value = "UPDATE BAL_PLM_TL_STANDARDS SET PMSD_PREPAREDBYID = :empId " +
            "WHERE PMSD_TRADEID = :tradeId AND PMSD_MACHINEID = :machineId", nativeQuery = true)
    int updatePreparedByTradeWise(@Param("empId") String empId, @Param("tradeId") String tradeId, @Param("machineId") String machineId);
}
