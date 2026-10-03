package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.BalPlmTlWoFeedBack;

import jakarta.transaction.Transactional;

@Repository
public interface BalPlmTlWoFeedBackRepo extends JpaRepository<BalPlmTlWoFeedBack,String> 
{

     @Modifying
@Transactional
@Query(value = """
    UPDATE bal_plm_tl_wodtl
    SET pwdd_feedbackid = :feedbackId,
        pwdd_completedby = :completedBy,
        pwdd_completeddate = :completedDate,
        pwdd_status = 'C'
    WHERE pwdd_wodetailid = :woDetailId
    """, nativeQuery = true)
int updateWorkOrderDetail(
        @Param("feedbackId") String feedbackId,
        @Param("completedBy") String completedBy,
        @Param("completedDate") LocalDateTime completedDate,
        @Param("woDetailId") String woDetailId);


          @Query(value = """
        SELECT
            '1' as orderno,
            '2' as alloteddate,
            '3' as week,
            '4' as schdmonth,
            '5' as tradename,
            '6' as totalactivity,
            '7' as completedcount,
            '8' as pendingcount,
            '9' as status
        UNION
        SELECT
            orderno,
            alloteddate,
            week,
            schdmonth,
            tradename,
            COUNT(orderno) AS totalactivity,
            SUM(compstatus) AS completedcount,
            SUM(pendstatus) AS pendingcount,
            CASE
                WHEN (SUM(pendstatus) + SUM(compstatus)) = SUM(compstatus)
                THEN 'COMPLETED'
                ELSE 'PENDING'
            END AS status
        FROM (
            SELECT DISTINCT
                pwdm_workorderno AS orderno,
                pwdm_alloteddate AS alloteddate,
                'W' || pmcl_scheduledweek AS week,
                TO_CHAR(pmcl_fromdate,'MON-YYYY') AS schdmonth,
                trdm_name AS tradename,
                CASE WHEN pmcl_status = 'A' THEN 1 ELSE 0 END AS pendstatus,
                CASE WHEN pmcl_status = 'Y' THEN 1 ELSE 0 END AS compstatus
            FROM bal_plm_tl_womst join bal_plm_tl_calendar on pwdm_workorderno = pwdd_womasterid
                                  join bal_plm_tl_wodtl on  pwdd_wodetailid = pmcl_workorderid
                                  join gen_tl_trademst on pmcl_tradeid = trdm_keyid
                 
                 
            WHERE 
                  pmcl_active = 'Y'
              AND pwdm_active = 'Y'
              AND pmcl_status = 'A'
              AND pmcl_entrytype <> 'G'
              AND pmcl_pmsource IN ('E','I')
              AND pmcl_pmfreq IN ('D','W','F','M','Q','H','Y','X')
              AND pwdm_machineid = :machineId
              AND (
                    DATE(pmcl_fromdate) BETWEEN TO_DATE(:fromDate,'DD-MON-YYYY')
                                           AND TO_DATE(:toDate,'DD-MON-YYYY')
                 OR DATE(pmcl_tilldate) BETWEEN TO_DATE(:fromDate,'DD-MON-YYYY')
                                           AND TO_DATE(:toDate,'DD-MON-YYYY')
              )
        ) A
        GROUP BY orderno, week, schdmonth, tradename, alloteddate
        ORDER BY schdmonth, week
        """, nativeQuery = true)
    List<Map<String,Object>> getAllWoGenDataMobile(
            @Param("machineId") String machineId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate);


            @Modifying
@Transactional
@Query(value = """
    UPDATE bal_plm_tl_wodtl
    SET pwdd_completedby = :completedBy,
        pwdd_completeddate = :completedDate
    WHERE pwdd_wodetailid = :woDetailId
    """, nativeQuery = true)
int updateCompletedDetails(
        @Param("woDetailId") String woDetailId,
        @Param("completedBy") String completedBy,
        @Param("completedDate") LocalDateTime completedDate);

         @Modifying
    @Transactional
    @Query(value = """
        UPDATE BAL_PLM_TL_WOMST
        SET PWDM_ALLOTEDTO = :allottedTo
        WHERE PWDM_WORKORDERNO = :workOrderNo
        """, nativeQuery = true)
    int updateAllottedTo(
            @Param("workOrderNo") String workOrderNo,
            @Param("allottedTo") String allottedTo);

//*************************************CANCEL ALLLOCATED QUERIES********************************************************* */

         @Modifying
    @Query(value = """
        UPDATE BAL_PLM_TL_WOMST
        SET PWDM_NUMOFACTIVITIES = PWDM_NUMOFACTIVITIES - :noOfActivities
        WHERE PWDM_WORKORDERNO = :workOrderNo
        """, nativeQuery = true)
    int updateNoOfActivities(
            @Param("workOrderNo") String workOrderNo,
            @Param("noOfActivities") Integer noOfActivities);


    @Modifying
    @Query(value = """
        DELETE FROM BAL_PLM_TL_WORKSUMMARY
        WHERE WKSM_WODETAILID = :workOrderNo
        """, nativeQuery = true)
    int deleteSummary(@Param("workOrderNo") String workOrderNo);


     @Modifying
    @Query(value = """
        DELETE FROM BAL_PLM_TL_WOFEEDBACK
        WHERE WOFB_WODETAILID = :workOrderNo
        """, nativeQuery = true)
    int deleteFeedback(@Param("workOrderNo") String workOrderNo);


    @Modifying
@Query(value="DELETE FROM BAL_WOM_TL_SPARECOSTPLAN WHERE WSCP_WOID=:workOrderNo", nativeQuery=true)
int deleteSpareCostPlan(@Param("workOrderNo") String workOrderNo);

@Modifying
@Query(value="DELETE FROM BAL_WOM_TL_UTILITYCOSTPLAN WHERE UTCP_WOKEYID=:workOrderNo", nativeQuery=true)
int deleteUtilityCostPlan(@Param("workOrderNo") String workOrderNo);

@Modifying
@Query(value="DELETE FROM BAL_WOM_TL_OTHERCOSTPLAN WHERE OTCP_WOID=:workOrderNo", nativeQuery=true)
int deleteOtherCostPlan(@Param("workOrderNo") String workOrderNo);

@Modifying
@Query(value="DELETE FROM BAL_WOM_TL_SERVICECOSTPLAN WHERE SVCP_WOID=:workOrderNo", nativeQuery=true)
int deleteServiceCostPlan(@Param("workOrderNo") String workOrderNo);

@Modifying
@Query(value="DELETE FROM BAL_WOM_TL_MANPOWERCOSTPLAN WHERE MPCP_WOID=:workOrderNo", nativeQuery=true)
int deleteManpowerCostPlan(@Param("workOrderNo") String workOrderNo);

 @Modifying
    @Query(value = """
        DELETE FROM BAL_PLM_TL_WOMST
        WHERE PWDM_WORKORDERNO = :workOrderNo
        AND PWDM_WOSTATUS = 'P'
        """, nativeQuery = true)
    int deleteWoMaster(@Param("workOrderNo") String workOrderNo);



    @Modifying
    @Query(value = """
        DELETE FROM BAL_PLM_TL_WODTL
        WHERE PWDD_WODETAILID IN (:woIds)
        """, nativeQuery = true)
    int deleteWoDetails(@Param("woIds") List<String> woIds);

     @Modifying
    @Query(value = """
        UPDATE BAL_PLM_TL_CALENDAR
        SET PMCL_STATUS = 'X',
            PMCL_SCHEDULEDFROM = PMCL_FROMDATE,
            PMCL_SCHEDULEDTILL = PMCL_TILLDATE,
            PMCL_SCHEDULEDWEEK = PMCL_MONTHWEEK,
            PMCL_WORKORDERID = '{}',
            PMCL_ALLOTTEDTO = '{}'
        WHERE PMCL_WORKORDERID IN (:woIds)
        """, nativeQuery = true)
    int resetCalendar(@Param("woIds") List<String> woIds);

    @Modifying
    @Query(value = """
        DELETE FROM BAL_PLM_TL_MULTIPLE_RESP
        WHERE PMRS_REFID = :workOrderNo
        AND PMRS_COMPLETED_EMPID = '-'
        """, nativeQuery = true)
    int deleteMultipleResp(@Param("workOrderNo") String workOrderNo);

 @Modifying
@Query(value = """
    WITH completed AS (
        SELECT wd.pwdd_womasterid
        FROM bal_plm_tl_wodtl wd
        WHERE wd.pwdd_calendarid = :calendarId
        GROUP BY wd.pwdd_womasterid
        HAVING COUNT(*) = COUNT(*) FILTER (WHERE wd.pwdd_status = 'C')
    ),
    update_womst AS (
        UPDATE bal_plm_tl_womst wm
        SET pwdm_wostatus = 'C',
            pwdm_modifiedon = NOW()
        WHERE wm.pwdm_workorderno IN (
            SELECT pwdd_womasterid
            FROM completed
        )
        RETURNING wm.pwdm_workorderno
    )
    UPDATE bal_plm_tl_calendar c
    SET pmcl_status = 'Y',
        pmcl_modifiedon = NOW()
    WHERE c.pmcl_keyid = :calendarId
      AND EXISTS (
          SELECT 1
          FROM update_womst
      )
    """, nativeQuery = true)
int updateCalendarAndWoMasterIfCompleted(
    @Param("calendarId") String calendarId
);

}
