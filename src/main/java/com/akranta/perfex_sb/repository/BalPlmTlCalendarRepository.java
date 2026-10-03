package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.BalPlmTlCalendar;

public interface BalPlmTlCalendarRepository extends JpaRepository<BalPlmTlCalendar,String> 
{

     @Query(value = """
        SELECT
            '1' AS keyid,
            '2' AS jobtype,
            '3' AS pmfreq,
            '4' AS activity_desc,
            '5' AS duration,
            '6' AS ok,
            '7' AS notok,
            8 as actiontaken
        UNION
        SELECT
            c.pmcl_keyid as keyid,
            c.pmcl_jobtype as jobtype,
            c.pmcl_pmfreq as pmfreq,
            CASE
                WHEN COALESCE(sa.sbam_name, '') = ''
                THEN '([ ' || a.assm_name || ' ]) - ' || c.pmcl_whatactivity
                ELSE '([ ' || a.assm_name || ' / ' || sa.sbam_name || ' ]) - ' || c.pmcl_whatactivity
            END AS activity_desc,
            c.pmcl_duration as duration,
            '' AS ok,
            '' AS notok,
            '' AS actiontaken
        FROM bal_plm_tl_calendar c
        JOIN bal_plm_tl_wodtl wd
            ON c.pmcl_workorderid = wd.pwdd_wodetailid
        JOIN bal_plm_tl_womst wm
            ON wm.pwdm_workorderno = wd.pwdd_womasterid
        JOIN gen_tl_assemblymst a
            ON c.pmcl_assemblyid = a.assm_keyid
        LEFT JOIN gen_tl_subassemblymst sa
            ON c.pmcl_subassemblyid = sa.sbam_keyid
        WHERE c.pmcl_machineid = :machineId
          AND wm.pwdm_workorderno = :workOrderNo
          AND c.pmcl_active = 'Y'
          AND c.pmcl_status = 'A'
        """, nativeQuery = true)
    List<Map<String,Object>> getSchedule(
            @Param("machineId") String machineId,
            @Param("workOrderNo") String workOrderNo);


            //planconf
            @Query("SELECT c FROM BalPlmTlCalendar c WHERE c.machineid = :machineid "
     + "AND c.pmfreq IN ('W','F','M','Q','H','Y') "
     + "AND c.status IN ('X','A') AND c.active = 'Y' "
     + "AND (:assemblyid IS NULL OR c.assemblyid = :assemblyid)")
List<BalPlmTlCalendar> findActiveEntriesToDeactivate(@Param("machineid") String machineid,
                                                        @Param("assemblyid") String assemblyid);

     //13 sep worresp
      @Modifying
    @Query(value = "UPDATE BAL_PLM_TL_CALENDAR SET PMCL_RESPONSIBILITY = :empId " +
            "WHERE PMCL_MACHINEID = :machineId AND PMCL_STATUS = 'X'", nativeQuery = true)
    int updateResponsibilityMachineWide(@Param("empId") String empId, @Param("machineId") String machineId);

    @Modifying
    @Query(value = "UPDATE BAL_PLM_TL_CALENDAR SET PMCL_RESPONSIBILITY = :empId " +
            "WHERE PMCL_TRADEID = :tradeId AND PMCL_MACHINEID = :machineId AND PMCL_STATUS = 'X'", nativeQuery = true)
    int updateResponsibilityTradeWise(@Param("empId") String empId, @Param("tradeId") String tradeId, @Param("machineId") String machineId);
    
    @Modifying
@Transactional 
@Query(value = """
    UPDATE BAL_PLM_TL_CALENDAR
    SET PMCL_ACTIVE = 'N',
        PMCL_MODIFIEDON = CURRENT_TIMESTAMP
    WHERE PMCL_MACHINEID = :machineId
      AND PMCL_PMFREQ IN ('W', 'F', 'M', 'Q', 'H', 'Y')
      AND PMCL_STATUS IN ('X', 'A')
      AND PMCL_ACTIVE = 'Y'
      AND (
            :assemblyId IS NULL
            OR :assemblyId = ''
            OR :assemblyId = '{}'
            OR PMCL_ASSEMBLYID = :assemblyId
          )
    """, nativeQuery = true)
int deactivateCalendar(
        @Param("machineId") String machineId,
        @Param("assemblyId") String assemblyId
);

}

