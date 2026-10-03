// package com.akranta.perfex_sb.repository;

// import java.time.LocalDateTime;
// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;

// import com.akranta.perfex_sb.model.BalPlanConfiguration;

// @Repository
// public interface BalPlanConfigurationRepository extends JpaRepository<BalPlanConfiguration,LocalDateTime> {

//     @Query(value =
//         "SELECT FREQ, TO_CHAR(MIN(NEXTDUEDATE),'DD-MON-YYYY') AS NEXTDUEDATE " +
//         "FROM ( " +
//         "   SELECT FREQ, " +
//         "       CASE FREQ " +
//         "           WHEN 'Y' THEN EDATE + ((12 * (ANTB_WEEKNAME + 1)) * INTERVAL '1 month') " +
//         "           WHEN 'H' THEN EDATE + ((6 * ANTB_WEEKNAME) * INTERVAL '1 month') " +
//         "           WHEN 'Q' THEN EDATE + ((3 * ANTB_WEEKNAME) * INTERVAL '1 month') " +
//         "           ELSE EDATE + ((1 * ANTB_WEEKNAME) * INTERVAL '1 month') " +
//         "       END AS NEXTDUEDATE " +
//         "   FROM BAL_GEN_TL_ANNUALTABLE, " +
//         "       (SELECT 'Q' AS FREQ UNION SELECT 'Y' AS FREQ UNION SELECT 'M' AS FREQ UNION SELECT 'H' AS FREQ) f, " +
//         "       (SELECT (TO_DATE('01-01-2026','DD-MM-YYYY') - INTERVAL '24 months') AS EDATE) e " +
//         "   WHERE ANTB_WEEKNAME >= 0 " +
//         ") sub " +
//         "WHERE NEXTDUEDATE >= DATE_TRUNC('month', CURRENT_DATE) " +
//         "GROUP BY FREQ ORDER BY FREQ",
//         nativeQuery = true)
//     List<Object[]> findNextDueDatesRaw();

// }



// package com.akranta.perfex_sb.repository;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

// import com.akranta.perfex_sb.model.BalPlanconfiguration;

// import jakarta.transaction.Transactional;

// public interface BalPlanconfigurationRepository extends JpaRepository<BalPlanconfiguration, String> {

//     @Query(value = """
//             select * from BAL_PLM_TL_PLANCONFIGURATION where pplc_keyid = :keyid
//             """, nativeQuery = true)
//     BalPlanconfiguration findByKeyid(@Param("keyid") String keyid);

//     // Stage 1 (assembly level) - replaces the "A" branch of the old delete
//     @Modifying
//     @Transactional
//     @Query(value = """
//             DELETE FROM BAL_PLM_TL_PLANCONFIGURATION
//             WHERE PPLC_MACHINEID = :machineId
//               AND PPLC_ASSEMBLYID <> '{}'
//               AND PPLC_LEVEL = 'A'
//             """, nativeQuery = true)
//     int deleteAssemblyLevelRows(@Param("machineId") String machineId);

//     // Stage 1 (machine level) - replaces the "M" branch of the old delete
//     @Modifying
//     @Transactional
//     @Query(value = """
//             DELETE FROM BAL_PLM_TL_PLANCONFIGURATION
//             WHERE PPLC_MACHINEID = :machineId
//               AND PPLC_ASSEMBLYID = '{}'
//               AND PPLC_LEVEL = 'M'
//             """, nativeQuery = true)
//     int deleteMachineLevelRows(@Param("machineId") String machineId);

//     // Stage 3 - distinct frequencies for the machine, from BAL_PLM_TL_STANDARDS
//     @Query(value = """
//             SELECT DISTINCT PMSD_FREQUENCYUNIT, PMSD_FREQUENCY
//             FROM BAL_PLM_TL_STANDARDS
//             WHERE PMSD_MACHINEID = :machineId
//             """, nativeQuery = true)
//     List<Object[]> findDistinctFrequencies(@Param("machineId") String machineId);

//     // Replaces the conditionally-built STANDARDS update: pass null for assemblyId/frequency
//     // when the old code would have skipped that filter, instead of omitting SQL fragments
//     @Modifying
//     @Transactional
//     @Query(value = """
//             UPDATE BAL_PLM_TL_STANDARDS
//             SET PMSD_EFFECTIVEDATE = :effDate,
//                 PMSD_MONTHWEEKNO = :weekno,
//                 PMSD_MODIFIEDON = CURRENT_TIMESTAMP,
//                 PMSD_PLANCONFIGSTATUS = 'Y'
//             WHERE PMSD_MACHINEID = :machineId
//               AND PMSD_ACTIVE = 'Y'
//               AND PMSD_FREQUENCYUNIT = :freqUnit
//               AND (:assemblyId IS NULL OR PMSD_ASSEMBLYID = :assemblyId)
//               AND (:frequency IS NULL OR PMSD_FREQUENCY = :frequency)
//             """, nativeQuery = true)
//     int updateStandardsEffectiveDate(
//             @Param("effDate") java.time.LocalDateTime effDate,
//             @Param("weekno") String weekno,
//             @Param("machineId") String machineId,
//             @Param("freqUnit") String freqUnit,
//             @Param("assemblyId") String assemblyId,
//             @Param("frequency") String frequency);

//     // Stage 5 - deactivate old calendar entries, assembly filter optional as before
//     @Modifying
//     @Transactional
//     @Query(value = """
//             UPDATE BAL_PLM_TL_CALENDAR
//             SET PMCL_ACTIVE = 'N', PMCL_MODIFIEDON = CURRENT_TIMESTAMP
//             WHERE PMCL_MACHINEID = :machineId
//               AND PMCL_PMFREQ IN ('W','F','M','Q','H','Y')
//               AND PMCL_STATUS IN ('X','A')
//               AND PMCL_ACTIVE = 'Y'
//               AND (:assemblyId IS NULL OR PMCL_ASSEMBLYID = :assemblyId)
//             """, nativeQuery = true)
//     int deactivateCalendarEntries(@Param("machineId") String machineId, @Param("assemblyId") String assemblyId);

//     // Stage 6 - pmsd_keyid lookup used to decide whether to generate the calendar
//     @Query(value = """
//             select pmsd_keyid from BAL_PLM_TL_STANDARDS where pmsd_machineid = :machineId
//             """, nativeQuery = true)
//     List<String> findStandardsKeyIdsByMachine(@Param("machineId") String machineId);

//     // Stage 6 - work-responsibility existence check
//     @Query(value = """
//             SELECT COUNT(PWRM_MACHINEID) FROM BAL_PLM_TL_WORESPMST WHERE PWRM_MACHINEID = :machineId
//             """, nativeQuery = true)
//     long countWorespByMachine(@Param("machineId") String machineId);


//     @Query(value =
//     "SELECT FREQ, TO_CHAR(MIN(NEXTDUEDATE),'DD-MON-YYYY') AS NEXTDUEDATE " +
//     "FROM ( " +
//     "   SELECT FREQ, " +
//     "       CASE FREQ " +
//     "           WHEN 'Y' THEN EDATE + ((12 * (ANTB_WEEKNAME + 1)) * INTERVAL '1 month') " +
//     "           WHEN 'H' THEN EDATE + ((6 * ANTB_WEEKNAME) * INTERVAL '1 month') " +
//     "           WHEN 'Q' THEN EDATE + ((3 * ANTB_WEEKNAME) * INTERVAL '1 month') " +
//     "           ELSE EDATE + ((1 * ANTB_WEEKNAME) * INTERVAL '1 month') " +
//     "       END AS NEXTDUEDATE " +
//     "   FROM BAL_GEN_TL_ANNUALTABLE, " +
//     "       (SELECT 'Q' AS FREQ UNION SELECT 'Y' AS FREQ UNION SELECT 'M' AS FREQ UNION SELECT 'H' AS FREQ) f, " +
//     "       (SELECT (TO_DATE('01-01-2026','DD-MM-YYYY') - INTERVAL '24 months') AS EDATE) e " +
//     "   WHERE ANTB_WEEKNAME >= 0 " +
//     ") sub " +
//     "WHERE NEXTDUEDATE >= DATE_TRUNC('month', CURRENT_DATE) " +
//     "GROUP BY FREQ ORDER BY FREQ",
//     nativeQuery = true)
// List<Object[]> findNextDueDatesRaw();
// }


//11sep
package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BalPlanconfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BalPlanconfigurationRepository extends JpaRepository<BalPlanconfiguration, String> {

    /**
     * Mirrors:
     * DELETE FROM PLANCONFIGURATION WHERE PPLC_MACHINEID = ? AND PPLC_ASSEMBLYID <> '{}' AND PPLC_LEVEL = 'A'
     */
    @Modifying
    @Query("DELETE FROM BalPlanconfiguration p WHERE p.machineid = :machineid AND p.assemblyid <> '{}' AND p.level = 'A'")
    void deleteAssemblyLevelConfig(@Param("machineid") String machineid);

    /**
     * Mirrors:
     * DELETE FROM PLANCONFIGURATION WHERE PPLC_MACHINEID = ? AND PPLC_ASSEMBLYID = '{}' AND PPLC_LEVEL = 'M'
     */
    @Modifying
    @Query("DELETE FROM BalPlanconfiguration p WHERE p.machineid = :machineid AND p.assemblyid = '{}' AND p.level = 'M'")
    void deleteMachineLevelConfig(@Param("machineid") String machineid);
}
