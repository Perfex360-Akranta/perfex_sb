package com.akranta.perfex_sb.repository;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.KpiTlActual;

public interface KpiTlActualRepository extends JpaRepository<KpiTlActual, String> {

    // fetching the details
    @Query(value = """
            SELECT *
            FROM kpi_tl_actual
            WHERE 1 = 1
              AND (:keyid IS NULL OR kauk_keyid = :keyid)
              AND (:indicatorid IS NULL OR kauk_indicatorid = :indicatorid)  Limit 1
            """, nativeQuery = true)
    KpiTlActual findActuals(
            @Param("keyid") String keyid,
            @Param("indicatorid") String indicatorid);

    @Query(value = """
            SELECT
                COUNT(DEVIATION) as count
            FROM
                (
                    SELECT
                        KAUK_INDICATORID,
                        KINK_INDICATORNAME,
                        UOM,
                        KAUK_MONTHYEAR,
                        KAUK_FREQTYPE,
                        TARGET,
                        ACTUAL,
                        DEVIATION
                    FROM
                        (
                            SELECT
                                KAUK_INDICATORID,
                                KINK_INDICATORNAME,
                                UOM,
                                KAUK_MONTHYEAR,
                                KAUK_FREQTYPE,
                                TARGET,
                                ACTUAL,
                                CASE
                                    WHEN ACTUAL < TARGET THEN 'Deviation'
                                    ELSE '-'
                                END AS DEVIATION
                            FROM
                                (
                                    SELECT
                                        KAUK_INDICATORID,
                                        KINK_INDICATORNAME,
                                        UOMM_DESCRIPTION AS UOM,
                                        KAUK_MONTHYEAR,
                                        KAUK_FREQTYPE,
                                        MAX(
                                            CASE
                                                WHEN KAUK_ISACTUAL = 'N' THEN KAUK_VALUE
                                            END
                                        ) AS TARGET,
                                        MAX(
                                            CASE
                                                WHEN KAUK_ISACTUAL = 'Y' THEN KAUK_VALUE
                                            END
                                        ) AS ACTUAL
                                    FROM
                                        KPI_TL_ACTUAL,
                                        ADM_TL_UOMMST,
                                        KPI_TL_INDICATOR
                                    WHERE
                                        KINK_KEYID = KAUK_INDICATORID
                                        AND UOMM_KEYID = KINK_UOMID
                                        AND KAUK_DEPTID = :flid
                                        AND KAUK_CALENDARYEAR = :year
                                        AND KAUK_FREQTYPE = :frequency
                                        AND TO_CHAR(KAUK_MONTHYEAR, 'DD-Mon-yyyy') = :currDate
                                    GROUP BY
                                        KAUK_INDICATORID,
                                        KINK_INDICATORNAME,
                                        UOMM_DESCRIPTION,
                                        KAUK_MONTHYEAR,
                                        KAUK_FREQTYPE
                                ) INNER_QUERY
                        ) MIDDLE_QUERY
                    WHERE
                        DEVIATION <> '-'
                ) OUTER_QUERY
            """, nativeQuery = true)
    List<Map<String, Object>> getDeviationListif(
            @Param("flid") String flid,
            @Param("year") BigDecimal year,
            @Param("frequency") String frequency,
            @Param("currDate") String currDate);

    @Query(value = """
            SELECT
                COUNT(deviation) as count
            FROM
                (
                    SELECT
                        KAUK_INDICATORID,
                        KINK_INDICATORNAME,
                        UOM,
                        KAUK_MONTHYEAR,
                        KAUK_FREQTYPE,
                        Target,
                        Actual,
                        Deviation
                    FROM
                        (
                            SELECT
                                KAUK_INDICATORID,
                                KINK_INDICATORNAME,
                                UOM,
                                KAUK_MONTHYEAR,
                                KAUK_FREQTYPE,
                                Target,
                                Actual,
                                (CASE WHEN actual < target THEN 'Deviation' ELSE '-' END) AS Deviation
                            FROM
                                (
                                    SELECT
                                        KAUK_INDICATORID,
                                        KINK_INDICATORNAME,
                                        UOMM_DESCRIPTION AS UOM,
                                        KAUK_MONTHYEAR,
                                        KAUK_FREQTYPE,
                                        MAX(CASE WHEN kauk_isactual = 'N' THEN kauk_value ELSE NULL END) AS Target,
                                        MAX(CASE WHEN kauk_isactual = 'Y' THEN kauk_value ELSE NULL END) AS Actual
                                    FROM
                                        KPI_TL_ACTUAL,
                                        ADM_TL_UOMMST,
                                        KPI_TL_INDICATOR
                                    WHERE
                                        1=1
                                        AND KINK_KEYID = KAUK_INDICATORID
                                        AND UOMM_KEYID = KINK_UOMID
                                        AND KAUK_DEPTID = :flid
                                        AND KAUK_CALENDARYEAR = :year
                                        AND KAUK_FREQTYPE = :frequency
                                        AND TO_CHAR(KAUK_MONTHYEAR, 'Mon-yyyy') = :currMonthYear
                                    GROUP BY
                                        KAUK_INDICATORID,
                                        KINK_INDICATORNAME,
                                        UOMM_DESCRIPTION,
                                        KAUK_MONTHYEAR,
                                        KAUK_FREQTYPE
                                ) subquery1
                        ) subquery2
                    WHERE
                        deviation <> '-'
                ) subquery3
            """, nativeQuery = true)
    List<Map<String, Object>> getDeviationListelse(
            @Param("flid") String flid,
            @Param("year") BigDecimal year,
            @Param("frequency") String frequency,
            @Param("currMonthYear") String currMonthYear);

    // @Query(value = "SELECT FNLN_ELEMENTID, FNLN_KEYID, ROLE_LEVEL, ROLE_NAME,
    // ROLE_KEYID " +
    // "FROM GEN_TL_FUNCTIONALLOCN, GEN_TL_FNLNROLETEAM, ADM_TL_ROLEMST " +
    // "WHERE FNLN_KEYID = FRT_FNLN_KEYID " +
    // "AND FRT_ROLE_KEYID = ROLE_KEYID " +
    // "AND (:loginflid IS NULL OR :loginflid = '' OR FRT_FNLN_KEYID = :loginflid) "
    // +
    // "AND FRT_EMPM_KEYID = :empId " +
    // "AND ROLE_LEVEL = :loginlevel",
    // nativeQuery = true)
    // List<Map<String, Object>> getElementId(@Param("loginflid") String loginflid,
    // @Param("loginlevel") String loginlevel,
    // @Param("empId") String empId);

    @Query(value = """
            SELECT FNLN_ELEMENTID,
                   FNLN_KEYID,
                   ROLE_LEVEL,
                   ROLE_NAME,
                   ROLE_KEYID
            FROM GEN_TL_FUNCTIONALLOCN,
                 GEN_TL_FNLNROLETEAM,
                 ADM_TL_ROLEMST
            WHERE FNLN_KEYID = FRT_FNLN_KEYID
              AND FRT_ROLE_KEYID = ROLE_KEYID
              AND (:flnKeyId IS NULL OR FRT_FNLN_KEYID = :flnKeyId)
              AND FRT_EMPM_KEYID = :empId
              AND (:roleLevel IS NULL OR ROLE_LEVEL = :roleLevel)
            """, nativeQuery = true)
    List<Map<String, Object>> getElementId(
            @Param("flnKeyId") String flnKeyId,
            @Param("empId") String empId,
            @Param("roleLevel") Integer roleLevel);

}
