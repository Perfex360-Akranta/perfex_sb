// ─── WomTlWomstRepository.java ─────────────────────────────────────────────
package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.WomTlWomst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Map;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface WomTlWomstRepository extends JpaRepository<WomTlWomst, String> {

    /**
     * Insert machine calendar time entry.
     * Equivalent to the legacy:
     *   INSERT INTO PCS_TL_MACHINECALTIME(...) VALUES (...)
     */
    @Modifying
    @Query(value = """
        INSERT INTO pcs_tl_machinecaltime
            (mctm_factoryid, mctm_sectionid, mctm_cellid,
             mctm_machineid, mctm_shiftid, mctm_shiftdate, mctm_caltime)
        VALUES
            (:factoryId, :sectionId, :cellId,
             :machineId, :shiftId, :shiftDate, :calTime)
        """, nativeQuery = true)
    void insertMachineCalTime(
            @Param("factoryId")  String factoryId,
            @Param("sectionId")  String sectionId,
            @Param("cellId")     String cellId,
            @Param("machineId")  String machineId,
            @Param("shiftId")    String shiftId,
            @Param("shiftDate")  LocalDateTime shiftDate,
            @Param("calTime")    BigDecimal calTime
    );

     @Query(value = """
        SELECT COALESCE(SLNO,7) AS SLNO, COALESCE(PARAM,'Total') AS PARAM,
               SUM(EST_VAL) AS EST_VAL, SUM(ACT_VAL) AS ACT_VAL
        FROM (
            SELECT CASE WHEN MPCP_SKILLFLAG='E' THEN 1 ELSE 2 END AS SLNO,
                   CASE WHEN MPCP_SKILLFLAG='E' THEN 'Employee Cost' WHEN MPCP_SKILLFLAG='H' THEN 'Contractor Cost' END AS PARAM,
                   MPCP_TOTALVALUE AS EST_VAL, 0 AS ACT_VAL
            FROM WOM_TL_MANPOWERCOSTPLAN
            WHERE MPCP_WOID = :woId AND MPCP_SKILLFLAG IN ('E','H')

            UNION ALL
            SELECT CASE WHEN MPCS_SKILLFLAG='E' THEN 1 ELSE 2 END AS SLNO,
                   CASE WHEN MPCS_SKILLFLAG='E' THEN 'Employee Cost' WHEN MPCS_SKILLFLAG='H' THEN 'Contractor Cost' END AS PARAM,
                   0 AS EST_VAL, MPCS_TOTALVALUE AS ACT_VAL
            FROM BAL_WOM_TL_MANPOWERCOSTACTUAL
            WHERE MPCS_MAINTWOID = :woId AND MPCS_SKILLFLAG IN ('E','H')

            UNION ALL
            SELECT 3 AS SLNO, 'Spares Cost' AS PARAM, WSCP_VALUE AS EST_VAL, 0 AS ACT_VAL
            FROM WOM_TL_SPARECOSTPLAN
            WHERE WSCP_WOID = :woId

            UNION ALL
            SELECT 3 AS SLNO, 'Spares Cost' AS PARAM, 0 AS EST_VAL, WSCA_VALUE AS ACT_VAL
            FROM WOM_TL_SPARECOSTACTUAL
            WHERE WSCA_WOID = :woId

            UNION ALL
            SELECT 4 AS SLNO, 'Service Cost' AS PARAM, SVCP_BILLVALUE AS EST_VAL, 0 AS ACT_VAL
            FROM WOM_TL_SERVICECOSTPLAN
            WHERE SVCP_WOID = :woId

            UNION ALL
            SELECT 4 AS SLNO, 'Service Cost' AS PARAM, 0 AS EST_VAL, SVCA_BILLVALUE AS ACT_VAL
            FROM WOM_TL_SERVICECOSTACTUAL
            WHERE SVCA_WOID = :woId

            UNION ALL
            SELECT 5 AS SLNO, 'Utility Cost' AS PARAM, UTCP_TOTALVALUE AS EST_VAL, 0 AS ACT_VAL
            FROM WOM_TL_UTILITYCOSTPLAN
            WHERE UTCP_WOKEYID = :woId

            UNION ALL
            SELECT 5 AS SLNO, 'Utility Cost' AS PARAM, 0 AS EST_VAL, UTCA_TOTALVALUE AS ACT_VAL
            FROM WOM_TL_UTILITYCOSTACTUAL
            WHERE UTCA_WOKEYID = :woId

            UNION ALL
            SELECT 6 AS SLNO, 'Other Cost' AS PARAM, OTCP_AMOUNT AS EST_VAL, 0 AS ACT_VAL
            FROM WOM_TL_OTHERCOSTPLAN
            WHERE OTCP_WOID = :woId

            UNION ALL
            SELECT 6 AS SLNO, 'Other Cost' AS PARAM, 0 AS EST_VAL, OTCD_AMOUNT AS ACT_VAL
            FROM WOM_TL_OTHERCOSTACTUAL
            WHERE OTCD_WOID = :woId

            UNION ALL SELECT 1, 'Employee Cost', 0, 0
            UNION ALL SELECT 2, 'Contractor Cost', 0, 0
            UNION ALL SELECT 3, 'Spares Cost', 0, 0
            UNION ALL SELECT 4, 'Service Cost', 0, 0
            UNION ALL SELECT 5, 'Utility Cost', 0, 0
            UNION ALL SELECT 6, 'Other Cost', 0, 0
        ) t
        GROUP BY GROUPING SETS ((SLNO,PARAM),())
        ORDER BY SLNO
        """, nativeQuery = true)
    List<Map<String, Object>> getGridSummary(@Param("woId") String woId);

    @Query(value = """
        SELECT MPCP_SKILLID, GRDM_NAME, MPCP_MANPOWERID, EMPM_EMPLOYEENUMBER, EMPM_NAME,
               MPCP_NORMALMINS, MPCP_NORMALCOST, MPCP_TOTALVALUE
        FROM WOM_TL_MANPOWERCOSTPLAN, GEN_TL_EMPLOYEEMST, GEN_TL_EMPGRADEMST
        WHERE MPCP_WOID = :woId AND MPCP_SKILLFLAG = 'E'
          AND MPCP_MANPOWERID = EMPM_KEYID AND MPCP_SKILLID = GRDM_KEYID
        ORDER BY MPCP_CREATEDON
        """, nativeQuery = true)
    List<Map<String, Object>> getEmpCostEstimate(@Param("woId") String woId);

    @Query(value = """
        SELECT AMVM_KEYID, AMVM_NAME, MPCP_MANPOWERID, UNGM_CODE, UNGM_NAME,
               MPCP_NORMALMINS, MPCP_NORMALCOST, MPCP_TOTALVALUE
        FROM WOM_TL_MANPOWERCOSTPLAN, PLM_TL_UNSKILLEDGRADEMST_I, GEN_TL_AMCVENDORMST
        WHERE MPCP_WOID = :woId AND MPCP_SKILLFLAG = 'H'
          AND MPCP_MANPOWERID = UNGM_KEYID AND MPCP_SKILLID = AMVM_KEYID
        ORDER BY MPCP_CREATEDON
        """, nativeQuery = true)
    List<Map<String, Object>> getContractorCostEstimate(@Param("woId") String woId);

    @Query(value = """
        SELECT WSCP_REQUESTEDBY, EMPM_NAME, SPRM_KEYID, SPRM_PARTNO, SPRM_PARTNAME,
               WSCP_QUANTITY, WSCP_RATE, WSCP_VALUE
        FROM WOM_TL_SPARECOSTPLAN, GEN_TL_EMPLOYEEMST, GEN_TL_SPARESMST
        WHERE WSCP_WOID = :woId
          AND WSCP_REQUESTEDBY = EMPM_KEYID AND WSCP_SPARESID = SPRM_KEYID
        ORDER BY WSCP_CREATEDON
        """, nativeQuery = true)
    List<Map<String, Object>> getSpareCostEstimate(@Param("woId") String woId);

    @Query(value = """
        SELECT AMVM_KEYID, AMVM_CODE, AMVM_NAME, SVCP_JOBDESCRIPTION,
               SVCP_BILLNO, SVCP_BILLVALUE, SVCP_REMARKS
        FROM WOM_TL_SERVICECOSTPLAN, GEN_TL_AMCVENDORMST
        WHERE SVCP_WOID = :woId AND SVCP_SERVICEID = AMVM_KEYID
        ORDER BY SVCP_CREATEDON
        """, nativeQuery = true)
    List<Map<String, Object>> getServiceCostEstimate(@Param("woId") String woId);

    @Query(value = """
        SELECT UTCP_REQUESTEDBY, EMPM_NAME, TOLM_KEYID, TOLM_CODE, TOLM_NAME,
               UTCP_QUANTITY, UTCP_MINUTES, UTCP_COST, UTCP_TOTALVALUE, UTCP_REMARKS
        FROM WOM_TL_UTILITYCOSTPLAN, GEN_TL_EMPLOYEEMST, GEN_TL_TOOLSMST
        WHERE UTCP_WOKEYID = :woId
          AND UTCP_REQUESTEDBY = EMPM_KEYID AND UTCP_UTILITYMSTID = TOLM_KEYID
        ORDER BY UTCP_CREATEDON
        """, nativeQuery = true)
    List<Map<String, Object>> getUtilityCostEstimate(@Param("woId") String woId);

    @Query(value = """
        SELECT OTCP_REQUESTEDBY, EMPM_NAME, OTCM_KEYID, OTCM_SHORTNAME, OTCM_COSTNAME,
               OTCP_AMOUNT, OTCP_REMARKS
        FROM WOM_TL_OTHERCOSTPLAN, GEN_TL_EMPLOYEEMST, WOM_TL_OTHERCOSTMST
        WHERE OTCP_WOID = :woId
          AND OTCP_REQUESTEDBY = EMPM_KEYID AND OTCP_OTHERCOSTMSTID = OTCM_KEYID
        ORDER BY OTCP_CREATEDON
        """, nativeQuery = true)
    List<Map<String, Object>> getOtherCostEstimate(@Param("woId") String woId);

    
@Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL, COUNT(MPCP_MANPOWERID) AS NOOFEMP
        FROM (
            SELECT ROUND(MPCP_TOTALVALUE, 2) AS TOTVAL, MPCP_MANPOWERID
            FROM WOM_TL_MANPOWERCOSTPLAN, GEN_TL_EMPLOYEEMST, GEN_TL_EMPGRADEMST
            WHERE EMPM_KEYID = MPCP_MANPOWERID
              AND MPCP_WOID = :woId
              AND MPCP_SKILLFLAG = 'E'
              AND GRDM_KEYID = MPCP_SKILLID

            UNION ALL

            SELECT ROUND(MPCP_TOTALVALUE, 2) AS TOTVAL, MPCP_MANPOWERID
            FROM WOM_TL_MANPOWERCOSTPLAN, GEN_TL_EMPLOYEEMST, GEN_TL_EMPGRADEMST
            WHERE EMPM_KEYID = MPCP_MANPOWERID
              AND MPCP_SKILLFLAG = 'E'
              AND GRDM_KEYID = MPCP_SKILLID
              AND MPCP_WOID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getEmpCostEstimatePageTotal(@Param("woId") String woId);

    @Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL, COUNT(MPCP_MANPOWERID) AS NOOFEMP
        FROM (
            SELECT ROUND(MPCP_TOTALVALUE,2) AS TOTVAL, MPCP_MANPOWERID
            FROM WOM_TL_MANPOWERCOSTPLAN, PLM_TL_UNSKILLEDGRADEMST_I, GEN_TL_AMCVENDORMST
            WHERE UNGM_KEYID = MPCP_MANPOWERID
              AND MPCP_WOID = :woId
              AND MPCP_SKILLFLAG = 'H'
              AND AMVM_KEYID = MPCP_SKILLID

            UNION ALL

            SELECT ROUND(MPCP_TOTALVALUE,2) AS TOTVAL, MPCP_MANPOWERID
            FROM WOM_TL_MANPOWERCOSTPLAN, PLM_TL_UNSKILLEDGRADEMST_I, GEN_TL_AMCVENDORMST
            WHERE UNGM_KEYID = MPCP_MANPOWERID
              AND MPCP_SKILLFLAG = 'H'
              AND AMVM_KEYID = MPCP_SKILLID
              AND MPCP_WOID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getContractorCostEstimatePageTotal(@Param("woId") String woId);

    @Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL FROM (
            SELECT ROUND(WSCP_VALUE,2) AS TOTVAL
            FROM WOM_TL_SPARECOSTPLAN, GEN_TL_SPARESMST, GEN_TL_EMPLOYEEMST
            WHERE EMPM_KEYID = WSCP_REQUESTEDBY AND SPRM_KEYID = WSCP_SPARESID AND WSCP_WOID = :woId

            UNION

            SELECT ROUND(WSCP_VALUE,2) AS TOTVAL
            FROM WOM_TL_SPARECOSTPLAN, GEN_TL_SPARESMST, GEN_TL_EMPLOYEEMST
            WHERE EMPM_KEYID = WSCP_REQUESTEDBY AND SPRM_KEYID = WSCP_SPARESID
              AND WSCP_WOID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getSpareCostEstimatePageTotal(@Param("woId") String woId);

    @Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL FROM (
            SELECT ROUND(SVCP_BILLVALUE,2) AS TOTVAL
            FROM WOM_TL_SERVICECOSTPLAN, GEN_TL_AMCVENDORMST
            WHERE AMVM_KEYID = SVCP_SERVICEID AND SVCP_WOID = :woId

            UNION

            SELECT ROUND(SVCP_BILLVALUE,2) AS TOTVAL
            FROM WOM_TL_SERVICECOSTPLAN, GEN_TL_AMCVENDORMST
            WHERE AMVM_KEYID = SVCP_SERVICEID
              AND SVCP_WOID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getServiceCostEstimatePageTotal(@Param("woId") String woId);

    @Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL FROM (
            SELECT ROUND(UTCP_TOTALVALUE,2) AS TOTVAL
            FROM WOM_TL_UTILITYCOSTPLAN, GEN_TL_TOOLSMST, GEN_TL_EMPLOYEEMST
            WHERE TOLM_KEYID = UTCP_UTILITYMSTID AND EMPM_KEYID = UTCP_REQUESTEDBY
              AND TOLM_TYPE = 'U' AND UTCP_WOKEYID = :woId

            UNION

            SELECT ROUND(UTCP_TOTALVALUE,2) AS TOTVAL
            FROM WOM_TL_UTILITYCOSTPLAN, GEN_TL_TOOLSMST, GEN_TL_EMPLOYEEMST
            WHERE TOLM_KEYID = UTCP_UTILITYMSTID AND EMPM_KEYID = UTCP_REQUESTEDBY AND TOLM_TYPE = 'U'
              AND UTCP_WOKEYID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getUtilityCostEstimatePageTotal(@Param("woId") String woId);

    @Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL FROM (
            SELECT ROUND(OTCP_AMOUNT,2) AS TOTVAL
            FROM WOM_TL_OTHERCOSTPLAN, WOM_TL_OTHERCOSTMST, GEN_TL_EMPLOYEEMST
            WHERE OTCM_KEYID = OTCP_OTHERCOSTMSTID AND EMPM_KEYID = OTCP_REQUESTEDBY AND OTCP_WOID = :woId

            UNION

            SELECT ROUND(OTCP_AMOUNT,2) AS TOTVAL
            FROM WOM_TL_OTHERCOSTPLAN, WOM_TL_OTHERCOSTMST, GEN_TL_EMPLOYEEMST
            WHERE OTCM_KEYID = OTCP_OTHERCOSTMSTID AND EMPM_KEYID = OTCP_REQUESTEDBY
              AND OTCP_WOID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getOtherCostEstimatePageTotal(@Param("woId") String woId);

    // ═════════════════════════ ACTUAL — PAGE TOTALS ═══════════════════════

    @Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL, COUNT(MPCS_MANPOWERID) AS NOOFEMP
        FROM (
            SELECT ROUND(MPCS_TOTALVALUE,2) AS TOTVAL, MPCS_MANPOWERID
            FROM BAL_WOM_TL_MANPOWERCOSTACTUAL, GEN_TL_EMPLOYEEMST, GEN_TL_EMPGRADEMST
            WHERE EMPM_KEYID = MPCS_MANPOWERID
              AND MPCS_MAINTWOID = :woId
              AND MPCS_SKILLFLAG = 'E'
              AND GRDM_KEYID = MPCS_SKILLID

            UNION ALL

            SELECT ROUND(MPCS_TOTALVALUE,2) AS TOTVAL, MPCS_MANPOWERID
            FROM BAL_WOM_TL_MANPOWERCOSTACTUAL, GEN_TL_EMPLOYEEMST, GEN_TL_EMPGRADEMST
            WHERE EMPM_KEYID = MPCS_MANPOWERID
              AND MPCS_SKILLFLAG = 'E'
              AND GRDM_KEYID = MPCS_SKILLID
              AND MPCS_MAINTWOID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getEmpCostActualPageTotal(@Param("woId") String woId);

    @Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL, COUNT(MPCS_MANPOWERID) AS NOOFEMP
        FROM (
            SELECT ROUND(MPCS_TOTALVALUE,2) AS TOTVAL, MPCS_MANPOWERID
            FROM BAL_WOM_TL_MANPOWERCOSTACTUAL, PLM_TL_UNSKILLEDGRADEMST_I, GEN_TL_AMCVENDORMST
            WHERE UNGM_KEYID = MPCS_MANPOWERID
              AND MPCS_MAINTWOID = :woId
              AND MPCS_SKILLFLAG = 'H'
              AND AMVM_KEYID = MPCS_SKILLID

            UNION ALL

            SELECT ROUND(MPCS_TOTALVALUE,2) AS TOTVAL, MPCS_MANPOWERID
            FROM BAL_WOM_TL_MANPOWERCOSTACTUAL, PLM_TL_UNSKILLEDGRADEMST_I, GEN_TL_AMCVENDORMST
            WHERE UNGM_KEYID = MPCS_MANPOWERID
              AND MPCS_SKILLFLAG = 'H'
              AND AMVM_KEYID = MPCS_SKILLID
              AND MPCS_MAINTWOID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getContractorCostActualPageTotal(@Param("woId") String woId);

    @Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL FROM (
            SELECT ROUND(WSCA_RATE,2) AS TOTVAL
            FROM WOM_TL_SPARECOSTACTUAL, GEN_TL_SPARESMST, GEN_TL_EMPLOYEEMST
            WHERE SPRM_KEYID = WSCA_SPARESID AND EMPM_KEYID = WSCA_REQUESTEDBY AND WSCA_WOID = :woId

            UNION

            SELECT ROUND(WSCA_VALUE,2) AS TOTVAL
            FROM WOM_TL_SPARECOSTACTUAL, GEN_TL_SPARESMST, GEN_TL_EMPLOYEEMST
            WHERE SPRM_KEYID = WSCA_SPARESID AND EMPM_KEYID = WSCA_REQUESTEDBY
              AND WSCA_WOID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getSpareCostActualPageTotal(@Param("woId") String woId);

    @Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL FROM (
            SELECT ROUND(SVCA_BILLVALUE,2) AS TOTVAL
            FROM WOM_TL_SERVICECOSTACTUAL, GEN_TL_AMCVENDORMST
            WHERE AMVM_KEYID = SVCA_SERVICEID AND SVCA_WOID = :woId

            UNION

            SELECT ROUND(SVCA_BILLVALUE,2) AS TOTVAL
            FROM WOM_TL_SERVICECOSTACTUAL, GEN_TL_AMCVENDORMST
            WHERE AMVM_KEYID = SVCA_SERVICEID
              AND SVCA_WOID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getServiceCostActualPageTotal(@Param("woId") String woId);

    @Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL FROM (
            SELECT ROUND(UTCA_TOTALVALUE,2) AS TOTVAL
            FROM WOM_TL_UTILITYCOSTACTUAL, GEN_TL_TOOLSMST, GEN_TL_EMPLOYEEMST
            WHERE TOLM_KEYID = UTCA_UTILITYMSTID AND EMPM_KEYID = UTCA_REQUESTEDBY
              AND TOLM_TYPE = 'U' AND UTCA_WOKEYID = :woId

            UNION

            SELECT ROUND(UTCA_TOTALVALUE,2) AS TOTVAL
            FROM WOM_TL_UTILITYCOSTACTUAL, GEN_TL_TOOLSMST, GEN_TL_EMPLOYEEMST
            WHERE TOLM_KEYID = UTCA_UTILITYMSTID AND EMPM_KEYID = UTCA_REQUESTEDBY AND TOLM_TYPE = 'U'
              AND UTCA_WOKEYID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getUtilityCostActualPageTotal(@Param("woId") String woId);

    @Query(value = """
        SELECT SUM(TOTVAL) AS TOTVAL FROM (
            SELECT ROUND(OTCD_AMOUNT,2) AS TOTVAL
            FROM WOM_TL_OTHERCOSTACTUAL, WOM_TL_OTHERCOSTMST, GEN_TL_EMPLOYEEMST
            WHERE OTCM_KEYID = OTCD_OTHERCOSTMSTID AND EMPM_KEYID = OTCD_REQUESTEDBY AND OTCD_WOID = :woId

            UNION

            SELECT ROUND(OTCD_AMOUNT,2) AS TOTVAL
            FROM WOM_TL_OTHERCOSTACTUAL, WOM_TL_OTHERCOSTMST, GEN_TL_EMPLOYEEMST
            WHERE OTCM_KEYID = OTCD_OTHERCOSTMSTID AND EMPM_KEYID = OTCD_REQUESTEDBY
              AND OTCD_WOID IN (SELECT PWDD_WOMASTERID FROM BAL_PLM_TL_WODTL WHERE PWDD_CALENDARID = :woId)
        ) A
        """, nativeQuery = true)
    List<Map<String, Object>> getOtherCostActualPageTotal(@Param("woId") String woId);


    //  @Query(value = """
    //     SELECT MPCS_DATE, MPCS_SKILLID, GRDM_NAME, MPCS_MANPOWERID, EMPM_EMPLOYEENUMBER, EMPM_NAME, MPCS_ACTIVITY,
    //            MPCS_NORMALWT, MPCS_HOLIDAYWT, MPCS_OTHERWT, MPCS_NORMALRATE, MPCS_HOLIDAYRATE, MPCS_OTHERRATE,
    //            MPCS_TOTALVALUE, MPCS_REMARKS
    //     FROM BAL_WOM_TL_MANPOWERCOSTACTUAL, GEN_TL_EMPLOYEEMST, GEN_TL_EMPGRADEMST
    //     WHERE MPCS_MAINTWOID = :woId AND MPCS_SKILLFLAG = 'E'
    //       AND MPCS_MANPOWERID = EMPM_KEYID AND MPCS_SKILLID = GRDM_KEYID
    //     ORDER BY MPCS_CREATEDON
    //     """, nativeQuery = true)
    // List<Map<String, Object>> getEmpCostActual(@Param("woId") String woId);
@Query(value = """
        SELECT TO_CHAR(MPCS_DATE, 'DD-Mon-YYYY') AS MPCS_DATE,
        
               MPCS_SKILLID, GRDM_NAME, MPCS_MANPOWERID, EMPM_EMPLOYEENUMBER, EMPM_NAME, MPCS_ACTIVITY,
               MPCS_NORMALWT, MPCS_HOLIDAYWT, MPCS_OTHERWT, MPCS_NORMALRATE, MPCS_HOLIDAYRATE, MPCS_OTHERRATE,
               MPCS_TOTALVALUE, MPCS_REMARKS
        FROM BAL_WOM_TL_MANPOWERCOSTACTUAL, GEN_TL_EMPLOYEEMST, GEN_TL_EMPGRADEMST
        WHERE MPCS_MAINTWOID = :woId AND MPCS_SKILLFLAG = 'E'
          AND MPCS_MANPOWERID = EMPM_KEYID AND MPCS_SKILLID = GRDM_KEYID
        ORDER BY MPCS_CREATEDON
        """, nativeQuery = true)
List<Map<String, Object>> getEmpCostActual(@Param("woId") String woId);
    @Query(value = """
        SELECT TO_CHAR(MPCS_DATE, 'DD-Mon-YYYY') AS MPCS_DATE, AMVM_KEYID, AMVM_NAME, MPCS_MANPOWERID, UNGM_CODE, UNGM_NAME, MPCS_ACTIVITY,
               MPCS_NORMALWT, MPCS_HOLIDAYWT, MPCS_OTHERWT, MPCS_NORMALRATE, MPCS_HOLIDAYRATE, MPCS_OTHERRATE,
               MPCS_TOTALVALUE, MPCS_REMARKS
        FROM BAL_WOM_TL_MANPOWERCOSTACTUAL, PLM_TL_UNSKILLEDGRADEMST_I, GEN_TL_AMCVENDORMST
        WHERE MPCS_MAINTWOID = :woId AND MPCS_SKILLFLAG = 'H'
          AND MPCS_MANPOWERID = UNGM_KEYID AND MPCS_SKILLID = AMVM_KEYID
        ORDER BY MPCS_CREATEDON
        """, nativeQuery = true)
    List<Map<String, Object>> getContractorCostActual(@Param("woId") String woId);

    @Query(value = """
        SELECT WSCA_REQUESTEDBY, EMPM_NAME, SPRM_KEYID, SPRM_PARTNO, SPRM_PARTNAME,
               WSCA_QUANTITY, WSCA_RATE, WSCA_VALUE
        FROM WOM_TL_SPARECOSTACTUAL, GEN_TL_EMPLOYEEMST, GEN_TL_SPARESMST
        WHERE WSCA_WOID = :woId
          AND WSCA_REQUESTEDBY = EMPM_KEYID AND WSCA_SPARESID = SPRM_KEYID
        ORDER BY WSCA_CREATEDON
        """, nativeQuery = true)
    List<Map<String, Object>> getSpareCostActual(@Param("woId") String woId);

    @Query(value = """
        SELECT AMVM_KEYID, AMVM_CODE, AMVM_NAME, SVCA_JOBDESCRIPTION,
               SVCA_BILLNO, TO_CHAR(SVCA_BILLDATE, 'DD-Mon-YYYY') AS SVCA_BILLDATE, SVCA_BILLVALUE, SVCA_REMARKS
               
        FROM WOM_TL_SERVICECOSTACTUAL, GEN_TL_AMCVENDORMST
        WHERE SVCA_WOID = :woId AND SVCA_SERVICEID = AMVM_KEYID
        ORDER BY SVCA_CREATEDON
        """, nativeQuery = true)
    List<Map<String, Object>> getServiceCostActual(@Param("woId") String woId);

    @Query(value = """
        SELECT UTCA_REQUESTEDBY, EMPM_NAME, TOLM_KEYID, TOLM_CODE, TOLM_NAME,
               TO_CHAR( UTCA_DATE, 'DD-Mon-YYYY') AS  UTCA_DATE, UTCA_QUANTITY, UTCA_MINUTES, UTCA_COST, UTCA_TOTALVALUE, UTCA_REMARKS
               
        FROM WOM_TL_UTILITYCOSTACTUAL, GEN_TL_EMPLOYEEMST, GEN_TL_TOOLSMST
        WHERE UTCA_WOKEYID = :woId
          AND UTCA_REQUESTEDBY = EMPM_KEYID AND UTCA_UTILITYMSTID = TOLM_KEYID
        ORDER BY UTCA_CREATEDON
        """, nativeQuery = true)
    List<Map<String, Object>> getUtilityCostActual(@Param("woId") String woId);

    @Query(value = """
        SELECT OTCD_REQUESTEDBY, EMPM_NAME, OTCM_KEYID, OTCM_SHORTNAME, OTCM_COSTNAME,
               TO_CHAR( OTCD_DATE, 'DD-Mon-YYYY') AS  OTCD_DATE, OTCD_AMOUNT, OTCD_REMARKS
               
        FROM WOM_TL_OTHERCOSTACTUAL, GEN_TL_EMPLOYEEMST, WOM_TL_OTHERCOSTMST
        WHERE OTCD_WOID = :woId
          AND OTCD_REQUESTEDBY = EMPM_KEYID AND OTCD_OTHERCOSTMSTID = OTCM_KEYID
        ORDER BY OTCD_CREATEDON
        """, nativeQuery = true)
    List<Map<String, Object>> getOtherCostActual(@Param("woId") String woId);

        // Native query equivalent of legacy: select * from BAL_WOM_TL_WOMST where woms_keyid=?
    @Query(value = """
        SELECT * FROM BAL_WOM_TL_WOMST
        WHERE woms_keyid = :keyid
        """, nativeQuery = true)
    WomTlWomst findByKeyidNative(@Param("keyid") String keyid);

}