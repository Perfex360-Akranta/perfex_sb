package com.akranta.perfex_sb.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BAL_PcsEntryMst;

public interface BAL_PcsEntryMstRepository extends JpaRepository<BAL_PcsEntryMst,String>{

    @Query("""
            select m.keyid from BAL_PcsEntryMst m
            where m.entrydate >= :start and m.entrydate < :end
              and m.shiftid = :shiftId
              and m.sectionid = :sectionId
              and m.cellid = :cellId
            order by m.keyid asc
            """)
    List<String> findKeyIds(@Param("start") LocalDateTime start,
                            @Param("end") LocalDateTime end,
                            @Param("shiftId") String shiftId,
                            @Param("sectionId") String sectionId,
                            @Param("cellId") String cellId);


                            //to get shift, cell, date
                            @Query(value = """
        SELECT PPRD_KEYID, PRDM_KEYID, PRMM_KEYID, PPRD_MANPOWER, PPRD_CYCLETIME
        FROM PCS_TL_DTL dt
        LEFT JOIN PCS_TL_PRODUCTMST ON dt.productid = PRDM_KEYID
        JOIN PCS_TL_PRODUCTMODELMST ON PRDM_MODEL = PRMM_KEYID
        JOIN BAL_PCS_TL_PRODUCTIONDATA ON PPRD_TEMPFIELD1 = dt.pldetailsid
        WHERE PRDM_ACTIVE = 'Y' AND PRMM_ACTIVE = 'Y'
          AND dt.PLDETAILSID IN (
              SELECT KEYID FROM (
                  SELECT MAX(PLDETAILSID) AS KEYID,
                         MAX(PRLM_ENTRYDATE) AS ENTRYDATE,
                         MAX(CASE SFTM_CODE WHEN 'A' THEN '1' WHEN 'B' THEN '2'
                                            WHEN 'C' THEN '3' ELSE SFTM_CODE END) AS SFTMCODE,
                         PRLM_CELLID,
                         MAX(MACHINEID) AS MACHINEID
                  FROM BAL_PCS_TL_MST
                  JOIN GEN_TL_SHIFTMST ON SFTM_KEYID = PRLM_SHIFTID
                  JOIN PCS_TL_DTL ON PLMASTERID = PRLM_KEYID
                  WHERE CAST(PRLM_ENTRYDATE AS date) <= CAST(:entryDate AS date)
                    AND PRODUCTID <> '{}'
                    AND PRLM_CELLID = :cellId
                    AND SFTM_FACTORYID = :factId
                  GROUP BY PRLM_CELLID
              ) sub
          )
        """, nativeQuery = true)
List<Object[]> findLastShiftProduct(@Param("factId") String factId,
                                    @Param("cellId") String cellId,
                                    @Param("entryDate") String entryDate);


                                    //to get the shift satart and end time

                                    @Query(value = """
        SELECT SFTM_STARTTIME, SFTM_ENDTIME, SFTM_BREAKTIME,
               TO_CHAR(SFTM_STARTTIME,'HH24:MI'), TO_CHAR(SFTM_ENDTIME,'HH24:MI'),
               SFTM_CODE, SFTM_SHIFTORDER
        FROM GEN_TL_SHIFTMST
        WHERE SFTM_KEYID = :shiftId
          AND SFTM_ACTIVE = 'Y'
        """, nativeQuery = true)
List<Object[]> findShiftStartEndTime(@Param("shiftId") String shiftId);

//to get holiday
@Query(value = """
        SELECT TO_CHAR(HOLM_DATE,'DD-Mon-YYYY'), HOLM_HOLIDAYFLAG
        FROM GEN_TL_HOLIDAYMST
        WHERE TO_CHAR(HOLM_DATE,'YYYYMMDD') = TO_CHAR(TO_DATE(:entryDate,'DD-Mon-YYYY'),'YYYYMMDD')
          AND HOLM_FACTORY = :factId
        ORDER BY HOLM_DATE
        """, nativeQuery = true)
List<Object[]> findHolidayDates(@Param("factId") String factId,
                                @Param("entryDate") String entryDate);

                                //getAllTimeforHour
                                // master key for date + shift + cell
@Query(value = """
        SELECT PRLM_KEYID FROM BAL_PCS_TL_MST
        WHERE CAST(PRLM_ENTRYDATE AS date) = TO_DATE(:entryDate, 'DD-Mon-YYYY')
          AND PRLM_SHIFTID = :shiftId
          AND PRLM_CELLID = :cellId
        ORDER BY PRLM_KEYID
        LIMIT 1
        """, nativeQuery = true)
List<String> findMasterIdByDateShiftCell(@Param("entryDate") String entryDate,
                                         @Param("shiftId") String shiftId,
                                         @Param("cellId") String cellId);

// shift-wise totals
@Query(value = """
        SELECT COALESCE(SUM(calendartime),480), COALESCE(SUM(unaccountedtime),0), COALESCE(SUM(productiontime),0)
        FROM PCS_TL_DTL
        WHERE plmasterid = :plMasterId
        """, nativeQuery = true)
List<Object[]> sumTimeShiftWise(@Param("plMasterId") String plMasterId);

// one-hour totals (machineid holds the hour)
@Query(value = """
        SELECT COALESCE(SUM(calendartime),60), COALESCE(SUM(unaccountedtime),0), COALESCE(SUM(productiontime),0)
        FROM PCS_TL_DTL
        WHERE plmasterid = :plMasterId AND machineid = :hourId
        """, nativeQuery = true)
List<Object[]> sumTimeForHour(@Param("plMasterId") String plMasterId,
                              @Param("hourId") String hourId);

                              //to get cycle time
                              @Query(value = """
        SELECT DISTINCT CYTM_KEYID AS ID, CYTM_CYCLETIME AS TEXT
        FROM BAL_PCS_TL_CYCLETIMEMST
        WHERE CYTM_KEYID = :productId
        ORDER BY TEXT
        """, nativeQuery = true)
List<Object[]> findCycleTime(@Param("productId") String productId);

//getShiftTotaltime
@Query(value = """
        SELECT SFTM_BREAKTIME FROM GEN_TL_SHIFTMST
        WHERE SFTM_KEYID = :shiftId
        """, nativeQuery = true)
List<Object> findShiftBreakTime(@Param("shiftId") String shiftId);


@Query(value = """
    SELECT COUNT(*)
    FROM BAL_PCS_TL_MST
    WHERE PRLM_ENTRYDATE::date = TO_DATE(:entryDate, 'DD-MON-YYYY HH24:MI')::date
      AND PRLM_SHIFTID = :shiftId
      AND PRLM_SECTIONID = :sectionId
      AND PRLM_CELLID = :cellId
    """, nativeQuery = true)
Integer checkExistingMaster(
        @Param("entryDate") String entryDate,
        @Param("shiftId") String shiftId,
        @Param("sectionId") String sectionId,
        @Param("cellId") String cellId
);

@Query(value = """
    SELECT PRLM_KEYID
    FROM BAL_PCS_TL_MST
    WHERE PRLM_ENTRYDATE::date = TO_DATE(:entryDate, 'DD-MON-YYYY HH24:MI')::date
      AND PRLM_SHIFTID = :shiftId
      AND PRLM_SECTIONID = :sectionId
      AND PRLM_CELLID = :cellId
    LIMIT 1
    """, nativeQuery = true)
String findExistingMasterId(
        @Param("entryDate") String entryDate,
        @Param("shiftId") String shiftId,
        @Param("sectionId") String sectionId,
        @Param("cellId") String cellId
);

@Query(value = """
    SELECT plcm_mapfield
    FROM pcs_tl_logconfiguration
    WHERE plcm_keyid = :lossId
    """, nativeQuery = true)
String findLossMapField(@Param("lossId") String lossId);

@Query(value = """
    SELECT plcm_parametercode
    FROM pcs_tl_logconfiguration
    WHERE plcm_keyid = :lossId
    """, nativeQuery = true)
String findParameterCode(@Param("lossId") String lossId);

@Query(value = """
    SELECT COUNT(*)
    FROM pcs_tl_losscausemst
    WHERE plcs_keyid = :causeId
    """, nativeQuery = true)
long countCause(@Param("causeId") String causeId);

}
