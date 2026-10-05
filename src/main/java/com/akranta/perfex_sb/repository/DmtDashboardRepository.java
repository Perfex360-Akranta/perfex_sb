package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.dto.DmtDashboardLevelCountsResponse;
import com.akranta.perfex_sb.dto.DmtDashboardLevelCountsResponse.DmtDashboardLevelMetricDto;
import com.akranta.perfex_sb.dto.DmtDashboardLossAnalysisResponse;
import com.akranta.perfex_sb.dto.DmtDashboardTrainingSummaryResponse;
import com.akranta.perfex_sb.dto.DmtDashboardAbnormalityClosureResponse;
import com.akranta.perfex_sb.dto.DmtDashboardActionPlanClosureResponse;
import com.akranta.perfex_sb.dto.DmtDashboardAttendanceGaugeResponse;
import com.akranta.perfex_sb.dto.DmtDashboardEmployeeCountResponse;
import com.akranta.perfex_sb.dto.DmtDashboardKaizenBenefitTrendResponse;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.dto.DmtDashboardTransactionSummaryResponse;

import java.time.LocalDate;

@Repository
public class DmtDashboardRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public DmtDashboardRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public DmtDashboardLevelCountsResponse getLevelCountsByFlid(String flid) {

    String sql = """
        WITH current_node AS (
            SELECT
                   fnln_keyid,
                   displaycode,
                   fnln_originalid,
                   fnln_elementid,
                   functionalloc,
                   comp_keyid,
                   locn_keyid,
                   sbut_keyid,
                   pbut_keyid,
                   sect_keyid,
                   cell_keyid
            FROM gen_vw_fnln
            WHERE fnln_keyid = :flid
            LIMIT 1
        ),
        desc_rows AS (
            SELECT v.*
            FROM gen_vw_fnln v
            JOIN current_node c ON 1 = 1
            WHERE
                (
                    COALESCE(NULLIF(TRIM(c.comp_keyid), ''), '-') IN ('-', '{}')
                    OR v.comp_keyid = c.comp_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.locn_keyid), ''), '-') IN ('-', '{}')
                    OR v.locn_keyid = c.locn_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.sbut_keyid), ''), '-') IN ('-', '{}')
                    OR v.sbut_keyid = c.sbut_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.pbut_keyid), ''), '-') IN ('-', '{}')
                    OR v.pbut_keyid = c.pbut_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.sect_keyid), ''), '-') IN ('-', '{}')
                    OR v.sect_keyid = c.sect_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.cell_keyid), ''), '-') IN ('-', '{}')
                    OR v.cell_keyid = c.cell_keyid
                )
        )
        SELECT
               c.fnln_keyid,
               c.displaycode,
               c.fnln_originalid,
               c.fnln_elementid,
               c.functionalloc,

               COUNT(DISTINCT CASE
                   WHEN COALESCE(l.locn_active, 'N') = 'Y'
                    AND d.locn_keyid IS NOT NULL
                    AND TRIM(d.locn_keyid) NOT IN ('', '{}', '-')
                   THEN d.locn_keyid
               END) AS location_count,

               COUNT(DISTINCT CASE
                   WHEN COALESCE(s.sbut_active, 'N') = 'Y'
                    AND d.sbut_keyid IS NOT NULL
                    AND TRIM(d.sbut_keyid) NOT IN ('', '{}', '-')
                   THEN d.sbut_keyid
               END) AS sbu_count,

               COUNT(DISTINCT CASE
                   WHEN COALESCE(p.pbut_active, 'N') = 'Y'
                    AND d.pbut_keyid IS NOT NULL
                    AND TRIM(d.pbut_keyid) NOT IN ('', '{}', '-')
                   THEN d.pbut_keyid
               END) AS pbu_count,

               COUNT(DISTINCT CASE
                   WHEN COALESCE(sec.sect_active, 'N') = 'Y'
                    AND d.sect_keyid IS NOT NULL
                    AND TRIM(d.sect_keyid) NOT IN ('', '{}', '-')
                   THEN d.sect_keyid
               END) AS dmt_count,

               COUNT(DISTINCT CASE
                   WHEN COALESCE(cell.cell_active, 'N') = 'Y'
                    AND d.cell_keyid IS NOT NULL
                    AND TRIM(d.cell_keyid) NOT IN ('', '{}', '-')
                   THEN d.cell_keyid
               END) AS jh_count

        FROM current_node c
        LEFT JOIN desc_rows d ON TRUE

        LEFT JOIN gen_tl_locationmst l
               ON l.locn_keyid = d.locn_keyid

        LEFT JOIN gen_tl_sbumst s
               ON s.sbut_keyid = d.sbut_keyid

        LEFT JOIN gen_tl_pbumst p
               ON p.pbut_keyid = d.pbut_keyid

        LEFT JOIN gen_tl_sectionmst sec
               ON sec.sect_keyid = d.sect_keyid

        LEFT JOIN gen_tl_cellmst cell
               ON cell.cell_keyid = d.cell_keyid

        GROUP BY
               c.fnln_keyid,
               c.displaycode,
               c.fnln_originalid,
               c.fnln_elementid,
               c.functionalloc
        """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("flid", flid);

    return jdbcTemplate.query(sql, params, rs -> {
      if (!rs.next()) {
        return emptyResponse(flid);
      }

      return mapLevelCounts(rs);
    });
  }

  private DmtDashboardLevelCountsResponse mapLevelCounts(ResultSet rs)
      throws SQLException {

    String flid = value(rs.getString("fnln_keyid"));
    String displayCode = value(rs.getString("displaycode"));
    String currentLevel = mapCurrentLevel(displayCode);

    Long locationCount = rs.getLong("location_count");
    Long sbuCount = rs.getLong("sbu_count");
    Long pbuCount = rs.getLong("pbu_count");
    Long dmtCount = rs.getLong("dmt_count");
    Long jhCount = rs.getLong("jh_count");

    List<DmtDashboardLevelMetricDto> metrics = new ArrayList<>();

    metrics.add(metric(
        "locationCount",
        "Locations",
        locationCount,
        "active locations",
        "blue",
        isChildLevelVisible(currentLevel, "LOCATION"),
        "LOCATION"));

    metrics.add(metric(
        "sbuCount",
        "SBUs",
        sbuCount,
        "active SBUs",

        "cyan",
        isChildLevelVisible(currentLevel, "SBU"),
        "SBU"));

    metrics.add(metric(
        "pbuCount",
        "PBUs",
        pbuCount,
        "active PBUs",

        "orange",
        isChildLevelVisible(currentLevel, "PBU"),
        "PBU"));

    metrics.add(metric(
        "dmtCount",
        "DMTs",
        dmtCount,
        "active DMT teams",

        "purple",
        isChildLevelVisible(currentLevel, "DMT"),
        "DMT"));

    metrics.add(metric(
        "jhCount",
        "JHs",
        jhCount,
        "active JH teams",

        "green",
        isChildLevelVisible(currentLevel, "JH"),
        "JH"));

    return new DmtDashboardLevelCountsResponse(
        flid,
        currentLevel,
        value(rs.getString("fnln_originalid")),
        value(rs.getString("fnln_elementid")),
        displayCode,
        metrics);
  }

  public DmtDashboardEmployeeCountResponse getEmployeeCountByFlid(String flid) {

    String sql = """
        WITH current_node AS (
            SELECT
                   fnln_keyid,
                   displaycode,
                   fnln_originalid,
                   fnln_elementid,
                   functionalloc,
                   comp_keyid,
                   locn_keyid,
                   sbut_keyid,
                   pbut_keyid,
                   sect_keyid,
                   cell_keyid
            FROM gen_vw_fnln
            WHERE fnln_keyid = :flid
            LIMIT 1
        ),
        desc_rows AS (
            SELECT v.*
            FROM gen_vw_fnln v
            JOIN current_node c ON 1 = 1
            WHERE
                (
                    COALESCE(NULLIF(TRIM(c.comp_keyid), ''), '-') IN ('-', '{}')
                    OR v.comp_keyid = c.comp_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.locn_keyid), ''), '-') IN ('-', '{}')
                    OR v.locn_keyid = c.locn_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.sbut_keyid), ''), '-') IN ('-', '{}')
                    OR v.sbut_keyid = c.sbut_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.pbut_keyid), ''), '-') IN ('-', '{}')
                    OR v.pbut_keyid = c.pbut_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.sect_keyid), ''), '-') IN ('-', '{}')
                    OR v.sect_keyid = c.sect_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.cell_keyid), ''), '-') IN ('-', '{}')
                    OR v.cell_keyid = c.cell_keyid
                )
        ),
        employee_base AS (
            SELECT DISTINCT
                   frt.frt_empm_keyid
            FROM desc_rows d
            JOIN gen_tl_fnlnroleteam frt
              ON frt.frt_fnln_keyid = d.fnln_keyid
            WHERE COALESCE(TRIM(frt.frt_active), 'N') = 'Y'
              AND frt.frt_empm_keyid IS NOT NULL
              AND TRIM(frt.frt_empm_keyid) NOT IN ('', '-', '{}')
        )
        SELECT
               c.fnln_keyid,
               c.displaycode,
               c.fnln_originalid,
               c.fnln_elementid,
               COUNT(DISTINCT e.frt_empm_keyid) AS employee_count
        FROM current_node c
        LEFT JOIN employee_base e ON TRUE
        GROUP BY
               c.fnln_keyid,
               c.displaycode,
               c.fnln_originalid,
               c.fnln_elementid
        """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("flid", flid);

    return jdbcTemplate.query(sql, params, rs -> {
      if (!rs.next()) {
        return new DmtDashboardEmployeeCountResponse(
            value(flid),
            "UNKNOWN",
            "",
            "",
            "",
            0L,
            "Employees",
            "active employees",
            "cyan",
            true);
      }

      String currentDisplayCode = value(rs.getString("displaycode"));
      String currentLevel = mapCurrentLevel(currentDisplayCode);

      return new DmtDashboardEmployeeCountResponse(
          value(rs.getString("fnln_keyid")),
          currentLevel,
          value(rs.getString("fnln_originalid")),
          value(rs.getString("fnln_elementid")),
          currentDisplayCode,
          rs.getLong("employee_count"),
          "Employees",
          "active employees",
          "cyan",
          !"JH".equalsIgnoreCase(currentLevel));
    });
  }

  private DmtDashboardLevelMetricDto metric(
      String id,
      String title,
      Long value,
      String subtitle,

      String variant,
      Boolean visible,
      String levelCode) {

    return new DmtDashboardLevelMetricDto(
        id,
        title,
        value == null ? 0L : value,
        subtitle,
        variant,
        visible,
        levelCode);
  }

  private DmtDashboardLevelCountsResponse emptyResponse(String flid) {
    return new DmtDashboardLevelCountsResponse(
        value(flid),
        "UNKNOWN",
        "",
        "",
        "",
        List.of());
  }

  private String mapCurrentLevel(String displayCode) {
    String code = value(displayCode).toUpperCase();

    if ("COMP".equals(code)) {
      return "COMPANY";
    }

    if ("LOCN".equals(code)) {
      return "LOCATION";
    }

    if ("SBU".equals(code)) {
      return "SBU";
    }

    if ("PBU".equals(code)) {
      return "PBU";
    }

    if ("SECT".equals(code) || "L".equals(code)) {
      return "DMT";
    }

    if ("CELL".equals(code)) {
      return "JH";
    }

    return "UNKNOWN";
  }

  private boolean isChildLevelVisible(String currentLevel, String metricLevel) {
    return levelRank(metricLevel) > levelRank(currentLevel);
  }

  private int levelRank(String level) {
    if ("COMPANY".equals(level)) {
      return 1;
    }

    if ("LOCATION".equals(level)) {
      return 2;
    }

    if ("SBU".equals(level)) {
      return 3;
    }

    if ("PBU".equals(level)) {
      return 4;
    }

    if ("DMT".equals(level)) {
      return 5;
    }

    if ("JH".equals(level)) {
      return 6;
    }

    return 99;
  }

  private String value(String value) {
    return value == null ? "" : value.trim();
  }

  public DmtDashboardTrainingSummaryResponse getTrainingSummaryByFlid(
      String flid,
      LocalDate fromDate,
      LocalDate toDate) {

    String sql = """
        SELECT
            COUNT(DISTINCT etcm.etcm_keyid)::bigint AS identified_count,

            COUNT(DISTINCT CASE
                WHEN COALESCE(etcm.etcm_chkcompleted, 'N') = 'Y'
                THEN etcm.etcm_keyid
            END)::bigint AS completed_count,

            COUNT(DISTINCT CASE
                WHEN COALESCE(etcm.etcm_chkcompleted, 'N') = 'N'
                THEN etcm.etcm_keyid
            END)::bigint AS pending_count

        FROM ent_tl_trgcalmst etcm
        JOIN gen_mv_flidhierarchy h
          ON etcm.etcm_flid = h.flid

        WHERE etcm.etcm_caldate::date BETWEEN :fromDate AND :toDate
          AND position(:flid in h.parentflids || '-' || h.flid) > 0
        """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("flid", flid)
        .addValue("fromDate", fromDate)
        .addValue("toDate", toDate);

    return jdbcTemplate.query(sql, params, rs -> {
      if (!rs.next()) {
        return DmtDashboardTrainingSummaryResponse.empty(
            value(flid),
            fromDate,
            toDate);
      }

      Long identifiedCount = longValue(rs, "identified_count");
      Long completedCount = longValue(rs, "completed_count");
      Long pendingCount = longValue(rs, "pending_count");

      Double completionPercentage = 0.0;

      if (identifiedCount != null && identifiedCount > 0) {
        completionPercentage = Math.round(((completedCount * 100.0) / identifiedCount) * 100.0) / 100.0;
      }

      return new DmtDashboardTrainingSummaryResponse(
          value(flid),
          fromDate,
          toDate,
          identifiedCount,
          completedCount,
          pendingCount,
          completionPercentage);
    });
  }

  public DmtDashboardTransactionSummaryResponse getTransactionSummaryByFlid(
      String flid,
      LocalDate fromDate,
      LocalDate toDate) {

    String sql = """
        WITH raw AS (

            SELECT 'MOM' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM gen_tl_mommst m
            JOIN gen_mv_flidhierarchy h
              ON m.moms_flid = h.flid
            WHERE date_trunc('day', m.moms_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'MOMDISCNCNT' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM gen_tl_mommst m
            JOIN gen_tl_momdtl d
              ON m.moms_keyid = d.momd_moms_keyid
            JOIN gen_mv_flidhierarchy h
              ON m.moms_flid = h.flid
            WHERE date_trunc('day', m.moms_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'SUG' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM kzn_tl_kaizenbankmst k
            JOIN gen_mv_flidhierarchy h
              ON k.kzbn_flid = h.flid
            WHERE date_trunc('day', k.kzbn_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'SAFETYSUG' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM kzn_tl_kaizenbankmst k
            JOIN gen_mv_flidhierarchy h
              ON k.kzbn_flid = h.flid
            WHERE k.kzbn_pqcdsme = 'S'
              AND date_trunc('day', k.kzbn_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'KZN' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM kzn_tl_mst k
            JOIN gen_mv_flidhierarchy h
              ON k.kznm_flid = h.flid
            WHERE date_trunc('day', k.kznm_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'SAFETYKZN' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM kzn_tl_mst k
            JOIN gen_mv_flidhierarchy h
              ON k.kznm_flid = h.flid
            WHERE k.kznm_resultarea = 'S'
              AND date_trunc('day', k.kznm_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'OPL' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM opl_tl_mst o
            JOIN gen_mv_flidhierarchy h
              ON o.oplm_flid = h.flid
            WHERE date_trunc('day', o.oplm_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'ABN' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM abn_tl_abnormality a
            JOIN gen_mv_flidhierarchy h
              ON a.abnm_flid = h.flid
            WHERE a.abnm_active = 'Y'
              AND date_trunc('day', a.abnm_detectiondate)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'ABNCLD' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM abn_tl_abnormality a
            JOIN gen_mv_flidhierarchy h
              ON a.abnm_flid = h.flid
            WHERE a.abnm_active = 'Y'
              AND a.abnm_status = 'C'
              AND date_trunc('day', a.abnm_detectiondate)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'ABNPND30' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM abn_tl_abnormality a
            JOIN gen_mv_flidhierarchy h
              ON a.abnm_flid = h.flid
            WHERE a.abnm_status = 'P'
              AND date_trunc('day', a.abnm_detectiondate)::date BETWEEN :fromDate AND :toDate
              AND current_date - a.abnm_detectiondate::date > 30
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'APS' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM abn_tl_abnormality a
            JOIN gen_mv_flidhierarchy h
              ON a.abnm_flid = h.flid
            WHERE a.abnm_active = 'Y'
              AND a.abnm_categoryid IN ('ABC00062', 'ABC00067', 'ABC00068')
              AND date_trunc('day', a.abnm_detectiondate)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'APC' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM abn_tl_abnormality a
            JOIN gen_mv_flidhierarchy h
              ON a.abnm_flid = h.flid
            WHERE a.abnm_active = 'Y'
              AND a.abnm_status = 'C'
              AND a.abnm_categoryid IN ('ABC00062', 'ABC00067', 'ABC00068')
              AND date_trunc('day', a.abnm_detectiondate)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'APG' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM abn_tl_abnormality a
            JOIN gen_mv_flidhierarchy h
              ON a.abnm_flid = h.flid
            WHERE a.abnm_status = 'P'
              AND a.abnm_categoryid IN ('ABC00062', 'ABC00067', 'ABC00068')
              AND date_trunc('day', a.abnm_detectiondate)::date BETWEEN :fromDate AND :toDate
              AND current_date - a.abnm_detectiondate::date > 30
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'NEARMISS' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM gen_tl_nearmissreportmstnew n
            JOIN gen_mv_flidhierarchy h
              ON n.nmrn_flnid = h.flid
            WHERE date_trunc('day', n.nmrn_occurrencedatetime)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'WHYWHY' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM bdm_tl_whywhymst w
            JOIN gen_mv_flidhierarchy h
              ON w.wwms_flid = h.flid
            WHERE date_trunc('day', w.wwms_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'ACTIONPLAN' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM gen_tl_actionplanmst a
            JOIN gen_mv_flidhierarchy h
              ON a.aplm_flid = h.flid
            WHERE date_trunc('day', a.aplm_plandate)::date BETWEEN :fromDate AND :toDate
              AND a.aplm_refdoctype <> 'ABN'
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'LOSS' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM pcs_tl_losscapture l
            JOIN gen_mv_flidhierarchy h
              ON l.plos_flid = h.flid
            WHERE date_trunc('day', l.plos_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'KPI' AS entrytype, COUNT(DISTINCT k.kidl_indicatorid)::bigint AS trncnt
            FROM kpi_tl_indicator_dept_link k
            JOIN gen_mv_flidhierarchy h
              ON k.kidl_deptid = h.flid
            WHERE k.kidl_active = 'Y'
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'SUSA' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM gen_tl_susamstnew s
            JOIN gen_mv_flidhierarchy h
              ON s.susn_flid = h.flid
            WHERE date_trunc('day', s.susn_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'PJO' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM she_tl_plannedjobobservation p
            JOIN gen_mv_flidhierarchy h
              ON p.pjob_flid = h.flid
            WHERE date_trunc('day', p.pjob_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'PSI' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM she_tl_plantsafetymst p
            JOIN gen_mv_flidhierarchy h
              ON p.spsm_flid = h.flid
            WHERE date_trunc('day', p.spsm_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'PSIOBSERVATION' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM she_tl_plantsafetymst p
            JOIN she_tl_plantsafetydtl d
              ON p.spsm_keyid = d.spsd_spsm_keyid
            JOIN gen_mv_flidhierarchy h
              ON p.spsm_flid = h.flid
            WHERE date_trunc('day', p.spsm_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'PSICLOSED' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM she_tl_plantsafetymst p
            JOIN she_tl_plantsafetydtl d
              ON p.spsm_keyid = d.spsd_spsm_keyid
            JOIN gen_mv_flidhierarchy h
              ON p.spsm_flid = h.flid
            WHERE d.spsd_status = 'C'
              AND date_trunc('day', p.spsm_date)::date BETWEEN :fromDate AND :toDate
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0

            UNION ALL

            SELECT 'PSIPENDING' AS entrytype, COUNT(*)::bigint AS trncnt
            FROM she_tl_plantsafetymst p
            JOIN she_tl_plantsafetydtl d
              ON p.spsm_keyid = d.spsd_spsm_keyid
            JOIN gen_mv_flidhierarchy h
              ON p.spsm_flid = h.flid
            WHERE d.spsd_status = 'P'
              AND current_date - p.spsm_date::date > 45
              AND position(:flid in coalesce(h.parentflids, '') || '/' || h.flid) > 0
        ),
        summary AS (
            SELECT
                coalesce(SUM(CASE WHEN entrytype = 'SUG'            THEN trncnt ELSE 0 END), 0)::bigint AS suggestions,
                coalesce(SUM(CASE WHEN entrytype = 'SAFETYSUG'      THEN trncnt ELSE 0 END), 0)::bigint AS safety_suggestions,
                coalesce(SUM(CASE WHEN entrytype = 'KZN'            THEN trncnt ELSE 0 END), 0)::bigint AS kaizens,
                coalesce(SUM(CASE WHEN entrytype = 'SAFETYKZN'      THEN trncnt ELSE 0 END), 0)::bigint AS safety_kaizens,
                coalesce(SUM(CASE WHEN entrytype = 'OPL'            THEN trncnt ELSE 0 END), 0)::bigint AS opl,
                coalesce(SUM(CASE WHEN entrytype = 'ABN'            THEN trncnt ELSE 0 END), 0)::bigint AS abnormalities,
                coalesce(SUM(CASE WHEN entrytype = 'ABNCLD'         THEN trncnt ELSE 0 END), 0)::bigint AS abnormalities_closed,
                coalesce(SUM(CASE WHEN entrytype = 'ABNPND30'       THEN trncnt ELSE 0 END), 0)::bigint AS abnormalities_pending30,
                coalesce(SUM(CASE WHEN entrytype = 'APS'            THEN trncnt ELSE 0 END), 0)::bigint AS abnormality_psi,
                coalesce(SUM(CASE WHEN entrytype = 'APC'            THEN trncnt ELSE 0 END), 0)::bigint AS abnormality_psi_closed,
                coalesce(SUM(CASE WHEN entrytype = 'APG'            THEN trncnt ELSE 0 END), 0)::bigint AS abnormality_psi_greater,
                coalesce(SUM(CASE WHEN entrytype = 'MOM'            THEN trncnt ELSE 0 END), 0)::bigint AS meetings,
                coalesce(SUM(CASE WHEN entrytype = 'MOMDISCNCNT'    THEN trncnt ELSE 0 END), 0)::bigint AS meeting_discussions,
                coalesce(SUM(CASE WHEN entrytype = 'NEARMISS'       THEN trncnt ELSE 0 END), 0)::bigint AS near_miss,
                coalesce(SUM(CASE WHEN entrytype = 'WHYWHY'         THEN trncnt ELSE 0 END), 0)::bigint AS why_why,
                coalesce(SUM(CASE WHEN entrytype = 'ACTIONPLAN'     THEN trncnt ELSE 0 END), 0)::bigint AS action_plans,
                coalesce(SUM(CASE WHEN entrytype = 'LOSS'           THEN trncnt ELSE 0 END), 0)::bigint AS loss,
                coalesce(SUM(CASE WHEN entrytype = 'KPI'            THEN trncnt ELSE 0 END), 0)::bigint AS kpi,
                coalesce(SUM(CASE WHEN entrytype = 'SUSA'           THEN trncnt ELSE 0 END), 0)::bigint AS susa,
                coalesce(SUM(CASE WHEN entrytype = 'PJO'            THEN trncnt ELSE 0 END), 0)::bigint AS pjo,
                coalesce(SUM(CASE WHEN entrytype = 'PSI'            THEN trncnt ELSE 0 END), 0)::bigint AS psi,
                coalesce(SUM(CASE WHEN entrytype = 'PSIOBSERVATION' THEN trncnt ELSE 0 END), 0)::bigint AS psi_observations,
                coalesce(SUM(CASE WHEN entrytype = 'PSICLOSED'      THEN trncnt ELSE 0 END), 0)::bigint AS psi_closed,
                coalesce(SUM(CASE WHEN entrytype = 'PSIPENDING'     THEN trncnt ELSE 0 END), 0)::bigint AS psi_pending
            FROM raw
        )
        SELECT
            *,
            (
                suggestions +
                safety_suggestions +
                kaizens +
                safety_kaizens +
                opl +
                abnormalities +
                abnormalities_closed +
                abnormalities_pending30 +
                abnormality_psi +
                abnormality_psi_closed +
                abnormality_psi_greater +
                meetings +
                meeting_discussions +
                near_miss +
                why_why +
                action_plans +
                loss +
                kpi +
                susa +
                pjo +
                psi +
                psi_observations +
                psi_closed +
                psi_pending
            )::bigint AS total_transactions
        FROM summary
        """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("flid", flid)
        .addValue("fromDate", fromDate)
        .addValue("toDate", toDate);

    return jdbcTemplate.query(sql, params, rs -> {
      if (!rs.next()) {
        return emptyTransactionSummary(flid, fromDate, toDate);
      }

      return new DmtDashboardTransactionSummaryResponse(
          flid,
          fromDate,
          toDate,

          longValue(rs, "suggestions"),
          longValue(rs, "safety_suggestions"),
          longValue(rs, "kaizens"),
          longValue(rs, "safety_kaizens"),
          longValue(rs, "opl"),
          longValue(rs, "abnormalities"),
          longValue(rs, "abnormalities_closed"),
          longValue(rs, "abnormalities_pending30"),
          longValue(rs, "abnormality_psi"),
          longValue(rs, "abnormality_psi_closed"),
          longValue(rs, "abnormality_psi_greater"),
          longValue(rs, "meetings"),
          longValue(rs, "meeting_discussions"),
          longValue(rs, "near_miss"),
          longValue(rs, "why_why"),
          longValue(rs, "action_plans"),
          longValue(rs, "loss"),
          longValue(rs, "kpi"),
          longValue(rs, "susa"),
          longValue(rs, "pjo"),
          longValue(rs, "psi"),
          longValue(rs, "psi_observations"),
          longValue(rs, "psi_closed"),
          longValue(rs, "psi_pending"),

          longValue(rs, "total_transactions"));
    });
  }

  private DmtDashboardTransactionSummaryResponse emptyTransactionSummary(
      String flid,
      LocalDate fromDate,
      LocalDate toDate) {

    return DmtDashboardTransactionSummaryResponse.empty(
        value(flid),
        fromDate,
        toDate);
  }

  private Long longValue(ResultSet rs, String columnName) throws SQLException {
    Object dbValue = rs.getObject(columnName);

    if (dbValue == null) {
      return 0L;
    }

    if (dbValue instanceof Number) {
      return ((Number) dbValue).longValue();
    }

    try {
      return Long.parseLong(String.valueOf(dbValue).trim());
    } catch (Exception e) {
      return 0L;
    }
  }

  private record AbnormalityClosureDbRow(
      String currentDisplayCode,
      String groupLevel,
      DmtDashboardAbnormalityClosureResponse.DmtDashboardAbnormalityClosureRow row) {
  }

  public DmtDashboardAbnormalityClosureResponse getAbnormalityClosureChartByFlid(
      String flid,
      LocalDate fromDate,
      LocalDate toDate) {

    String sql = """
           WITH current_node AS (
               SELECT
                      fnln_keyid,
                      displaycode,
                      comp_keyid,
                      locn_keyid,
                      sbut_keyid,
                      pbut_keyid,
                      sect_keyid,
                      cell_keyid
               FROM gen_vw_fnln
               WHERE fnln_keyid = :flid
               LIMIT 1
           ),
           scoped_nodes AS (
               SELECT v.*
               FROM gen_vw_fnln v
               JOIN current_node c ON 1 = 1
               WHERE
                   (
                       COALESCE(NULLIF(TRIM(c.comp_keyid), ''), '-') IN ('-', '{}')
                       OR v.comp_keyid = c.comp_keyid
                   )
               AND (
                       COALESCE(NULLIF(TRIM(c.locn_keyid), ''), '-') IN ('-', '{}')
                       OR v.locn_keyid = c.locn_keyid
                   )
               AND (
                       COALESCE(NULLIF(TRIM(c.sbut_keyid), ''), '-') IN ('-', '{}')
                       OR v.sbut_keyid = c.sbut_keyid
                   )
               AND (
                       COALESCE(NULLIF(TRIM(c.pbut_keyid), ''), '-') IN ('-', '{}')
                       OR v.pbut_keyid = c.pbut_keyid
                   )
               AND (
                       COALESCE(NULLIF(TRIM(c.sect_keyid), ''), '-') IN ('-', '{}')
                       OR v.sect_keyid = c.sect_keyid
                   )
               AND (
                       COALESCE(NULLIF(TRIM(c.cell_keyid), ''), '-') IN ('-', '{}')
                       OR v.cell_keyid = c.cell_keyid
                   )
           ),
           mapped_nodes AS (
               SELECT
                   UPPER(COALESCE(c.displaycode, '')) AS current_displaycode,

                   CASE
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'COMP' THEN 'LOCATION'
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'LOCN' THEN 'SBU'
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'SBU'  THEN 'PBU'
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'PBU'  THEN 'DMT'
                       WHEN UPPER(COALESCE(c.displaycode, '')) IN ('SECT', 'L') THEN 'JH'
                       ELSE 'NONE'
                   END AS group_level,

                   CASE
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'COMP' THEN v.locn_keyid
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'LOCN' THEN v.sbut_keyid
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'SBU'  THEN v.pbut_keyid
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'PBU'  THEN sec.sect_keyid
                       WHEN UPPER(COALESCE(c.displaycode, '')) IN ('SECT', 'L') THEN v.cell_keyid
                       ELSE NULL
                   END AS group_key,

                   CASE
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'COMP' THEN COALESCE(NULLIF(TRIM(v.locn_name), ''), v.locn_keyid)
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'LOCN' THEN COALESCE(NULLIF(TRIM(v.sbut_name), ''), v.sbut_keyid)
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'SBU'  THEN COALESCE(NULLIF(TRIM(v.pbut_name), ''), v.pbut_keyid)
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'PBU'  THEN COALESCE(NULLIF(TRIM(sec.sect_name), ''), sec.sect_keyid)
                       WHEN UPPER(COALESCE(c.displaycode, '')) IN ('SECT', 'L') THEN COALESCE(NULLIF(TRIM(v.cell_name), ''), v.cell_keyid)
                       ELSE NULL
                   END AS group_label,

                   CASE
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'COMP' THEN COALESCE(l.locn_active, 'N')
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'LOCN' THEN COALESCE(s.sbut_active, 'N')
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'SBU'  THEN COALESCE(p.pbut_active, 'N')
                       WHEN UPPER(COALESCE(c.displaycode, '')) = 'PBU'  THEN COALESCE(sec.sect_active, 'N')
                       WHEN UPPER(COALESCE(c.displaycode, '')) IN ('SECT', 'L') THEN COALESCE(cell.cell_active, 'N')
                       ELSE 'N'
                   END AS group_active,

                   v.fnln_keyid AS node_flid

               FROM scoped_nodes v
               CROSS JOIN current_node c
               LEFT JOIN gen_tl_locationmst l
                 ON l.locn_keyid = v.locn_keyid
               LEFT JOIN gen_tl_sbumst s
                 ON s.sbut_keyid = v.sbut_keyid
               LEFT JOIN gen_tl_pbumst p
                 ON p.pbut_keyid = v.pbut_keyid
               LEFT JOIN gen_tl_sectionmst sec
                 ON sec.sect_keyid = v.sect_keyid
                  AND (
           UPPER(COALESCE(c.displaycode, '')) <> 'PBU'
           OR sec.sect_factoryid = c.pbut_keyid
        )
               LEFT JOIN gen_tl_cellmst cell
                 ON cell.cell_keyid = v.cell_keyid
           ),
           valid_nodes AS (
               SELECT *
               FROM mapped_nodes
               WHERE group_key IS NOT NULL
                 AND TRIM(group_key) NOT IN ('', '{}', '-')
                 AND COALESCE(group_active, 'N') = 'Y'
           ),
           groups AS (
               SELECT DISTINCT
                      current_displaycode,
                      group_level,
                      group_key,
                      COALESCE(NULLIF(TRIM(group_label), ''), group_key) AS group_label
               FROM valid_nodes
           )
           SELECT
               g.current_displaycode,
               g.group_level,
               g.group_key,
               g.group_label,

               COUNT(DISTINCT a.abnm_keyid)::bigint AS identified_count,

               COUNT(DISTINCT CASE
                   WHEN COALESCE(a.abnm_status, '') = 'C'
                   THEN a.abnm_keyid
               END)::bigint AS closed_count,

               COUNT(DISTINCT CASE
                   WHEN a.abnm_keyid IS NOT NULL
                    AND COALESCE(a.abnm_status, '') <> 'C'
                   THEN a.abnm_keyid
               END)::bigint AS pending_count

           FROM groups g
           LEFT JOIN valid_nodes n
             ON n.group_key = g.group_key
           LEFT JOIN abn_tl_abnormality a
             ON a.abnm_flid = n.node_flid
            AND COALESCE(a.abnm_active, 'N') = 'Y'
            AND a.abnm_detectiondate::date BETWEEN :fromDate AND :toDate

           GROUP BY
               g.current_displaycode,
               g.group_level,
               g.group_key,
               g.group_label

           ORDER BY
               g.group_label
           """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("flid", flid)
        .addValue("fromDate", fromDate)
        .addValue("toDate", toDate);

    List<AbnormalityClosureDbRow> dbRows = jdbcTemplate.query(
        sql,
        params,
        (rs, rowNum) -> {
          Long identifiedCount = longValue(rs, "identified_count");
          Long closedCount = longValue(rs, "closed_count");
          Long pendingCount = longValue(rs, "pending_count");

          Double closurePercentage = calculatePercentage(
              closedCount,
              identifiedCount);

          return new AbnormalityClosureDbRow(
              value(rs.getString("current_displaycode")),
              value(rs.getString("group_level")),
              new DmtDashboardAbnormalityClosureResponse.DmtDashboardAbnormalityClosureRow(
                  value(rs.getString("group_key")),
                  value(rs.getString("group_label")),
                  identifiedCount,
                  closedCount,
                  pendingCount,
                  closurePercentage));
        });

    if (dbRows.isEmpty()) {
      return DmtDashboardAbnormalityClosureResponse.empty(
          value(flid),
          fromDate,
          toDate);
    }

    String currentLevel = mapCurrentLevel(dbRows.get(0).currentDisplayCode());
    String groupLevel = dbRows.get(0).groupLevel();

    List<DmtDashboardAbnormalityClosureResponse.DmtDashboardAbnormalityClosureRow> rows = dbRows.stream()
        .map(AbnormalityClosureDbRow::row)
        .toList();

    return new DmtDashboardAbnormalityClosureResponse(
        value(flid),
        fromDate,
        toDate,
        currentLevel,
        groupLevel,
        rows);
  }

  private Double calculatePercentage(Long numerator, Long denominator) {

    if (denominator == null || denominator <= 0) {
      return 0.0;
    }

    long safeNumerator = numerator == null ? 0L : numerator;

    return Math.round(((safeNumerator * 100.0) / denominator) * 100.0) / 100.0;
  }

  private record LossChildDbRow(
      String currentDisplayCode,
      String groupLevel,
      DmtDashboardLossAnalysisResponse.DmtDashboardLossChildContribution row) {
  }

  public DmtDashboardLossAnalysisResponse getLossAnalysisByFlid(
      String flid,
      LocalDate fromDate,
      LocalDate toDate) {

    LocalDate monthStart = fromDate.withDayOfMonth(1);
    LocalDate monthEnd = toDate.withDayOfMonth(1);

    List<DmtDashboardLossAnalysisResponse.DmtDashboardLossTrendPoint> monthlyTrend = getLossMonthlyTrendByFlid(flid,
        monthStart, monthEnd);

    List<LossChildDbRow> childDbRows = getLossChildContributionRowsByFlid(flid, monthStart, monthEnd);

    Long totalLossMinutes = monthlyTrend.stream()
        .map(DmtDashboardLossAnalysisResponse.DmtDashboardLossTrendPoint::lossMinutes)
        .filter(Objects::nonNull)
        .reduce(0L, Long::sum);

    String currentLevel = "UNKNOWN";
    String groupLevel = "NONE";

    if (!childDbRows.isEmpty()) {
      currentLevel = mapCurrentLevel(childDbRows.get(0).currentDisplayCode());
      groupLevel = childDbRows.get(0).groupLevel();
    }

    List<DmtDashboardLossAnalysisResponse.DmtDashboardLossChildContribution> childRows = childDbRows.stream()
        .map(LossChildDbRow::row)
        .toList();

    return new DmtDashboardLossAnalysisResponse(
        value(flid),
        fromDate,
        toDate,
        currentLevel,
        groupLevel,
        totalLossMinutes,
        minutesToHours(totalLossMinutes),
        minutesToText(totalLossMinutes),
        monthlyTrend,
        childRows);
  }

  private List<DmtDashboardLossAnalysisResponse.DmtDashboardLossTrendPoint> getLossMonthlyTrendByFlid(
      String flid,
      LocalDate monthStart,
      LocalDate monthEnd) {

    String sql = """
        WITH month_series AS (
            SELECT
                date_trunc('month', d)::date AS month_start
            FROM generate_series(
                date_trunc('month', CAST(:fromDate AS date)),
                date_trunc('month', CAST(:toDate AS date)),
                interval '1 month'
            ) AS d
        ),
        loss_base AS (
            SELECT
                date_trunc('month', l.plos_fromtime)::date AS month_start,
                COALESCE(SUM(l.plos_losstime::integer), 0)::bigint AS loss_minutes
            FROM pcs_tl_losscapture l
            JOIN pcs_tl_logconfiguration c
              ON l.plos_lossid = c.plcm_keyid
             AND c.plcm_isloss = 'M'
            JOIN gen_mv_flidhierarchy h
              ON l.plos_flid = h.flid
            WHERE l.plos_fromtime::date >= date_trunc('month', CAST(:fromDate AS date))::date
              AND l.plos_fromtime::date <  (date_trunc('month', CAST(:toDate AS date)) + interval '1 month')::date
              AND position(:flid in coalesce(h.parentflids, '') || '/' || coalesce(h.flid, '')) > 0
            GROUP BY date_trunc('month', l.plos_fromtime)::date
        )
        SELECT
            to_char(ms.month_start, 'YYYY-MM') AS month_key,
            to_char(ms.month_start, 'MON-YYYY') AS month_label,
            COALESCE(lb.loss_minutes, 0)::bigint AS loss_minutes
        FROM month_series ms
        LEFT JOIN loss_base lb
          ON lb.month_start = ms.month_start
        ORDER BY ms.month_start
        """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("flid", flid)
        .addValue("fromDate", monthStart)
        .addValue("toDate", monthEnd);

    return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
      Long lossMinutes = longValue(rs, "loss_minutes");

      return new DmtDashboardLossAnalysisResponse.DmtDashboardLossTrendPoint(
          value(rs.getString("month_key")),
          value(rs.getString("month_label")),
          lossMinutes,
          minutesToHours(lossMinutes),
          minutesToText(lossMinutes));
    });
  }

  private List<LossChildDbRow> getLossChildContributionRowsByFlid(
      String flid,
      LocalDate monthStart,
      LocalDate monthEnd) {

    String sql = """
        WITH current_node AS (
            SELECT
                   fnln_keyid,
                   displaycode
            FROM gen_vw_fnln
            WHERE fnln_keyid = :flid
            LIMIT 1
        ),
        loss_base AS (
            SELECT
                cur.displaycode AS current_displaycode,
                'LOSS TYPE' AS group_level,
                l.plos_lossid AS group_key,
                upper(c.plcm_parametername)::text AS group_label,
                COALESCE(SUM(l.plos_losstime::integer), 0)::bigint AS loss_minutes

            FROM pcs_tl_losscapture l

            JOIN pcs_tl_logconfiguration c
              ON l.plos_lossid = c.plcm_keyid
             AND c.plcm_isloss = 'M'

            JOIN gen_mv_flidhierarchy h
              ON l.plos_flid = h.flid

            CROSS JOIN current_node cur

            WHERE l.plos_fromtime::date >= date_trunc('month', CAST(:fromDate AS date))::date
              AND l.plos_fromtime::date <  (date_trunc('month', CAST(:toDate AS date)) + interval '1 month')::date
              AND position(:flid in coalesce(h.parentflids, '') || '/' || coalesce(h.flid, '')) > 0

            GROUP BY
                cur.displaycode,
                l.plos_lossid,
                upper(c.plcm_parametername)
        ),
        total_loss AS (
            SELECT COALESCE(SUM(loss_minutes), 0)::bigint AS total_minutes
            FROM loss_base
        )
        SELECT
            lb.current_displaycode,
            lb.group_level,
            lb.group_key,
            lb.group_label,
            lb.loss_minutes,
            CASE
                WHEN tl.total_minutes > 0
                THEN ROUND(((lb.loss_minutes * 100.0) / tl.total_minutes)::numeric, 2)
                ELSE 0
            END AS contribution_percentage
        FROM loss_base lb
        CROSS JOIN total_loss tl
        WHERE lb.loss_minutes > 0
        ORDER BY lb.loss_minutes DESC, lb.group_label
        """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("flid", flid)
        .addValue("fromDate", monthStart)
        .addValue("toDate", monthEnd);

    return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
      Long lossMinutes = longValue(rs, "loss_minutes");

      return new LossChildDbRow(
          value(rs.getString("current_displaycode")),
          value(rs.getString("group_level")),
          new DmtDashboardLossAnalysisResponse.DmtDashboardLossChildContribution(
              value(rs.getString("group_key")),
              value(rs.getString("group_label")),
              lossMinutes,
              minutesToHours(lossMinutes),
              minutesToText(lossMinutes),
              doubleValue(rs, "contribution_percentage")));
    });
  }

  private Double doubleValue(ResultSet rs, String columnName) throws SQLException {
    Object dbValue = rs.getObject(columnName);

    if (dbValue == null) {
      return 0.0;
    }

    if (dbValue instanceof Number) {
      return ((Number) dbValue).doubleValue();
    }

    try {
      return Double.parseDouble(String.valueOf(dbValue).trim());
    } catch (Exception e) {
      return 0.0;
    }
  }

  private Double minutesToHours(Long minutes) {
    long safeMinutes = minutes == null ? 0L : minutes;
    return Math.round((safeMinutes / 60.0) * 100.0) / 100.0;
  }

  private String minutesToText(Long minutes) {
    long safeMinutes = minutes == null ? 0L : minutes;

    long hours = safeMinutes / 60;
    long remainingMinutes = safeMinutes % 60;

    return hours + ":" + String.format("%02d", remainingMinutes);
  }

  public DmtDashboardKaizenBenefitTrendResponse getKaizenBenefitTrendByFlid(
      String flid,
      LocalDate fromDate,
      LocalDate toDate) {

    LocalDate monthStart = fromDate.withDayOfMonth(1);
    LocalDate monthEnd = toDate.withDayOfMonth(1);

    String sql = """
        WITH month_series AS (
            SELECT
                date_trunc('month', d)::date AS month_start
            FROM generate_series(
                date_trunc('month', CAST(:fromDate AS date)),
                date_trunc('month', CAST(:toDate AS date)),
                interval '1 month'
            ) AS d
        ),
        kaizen_base AS (
            SELECT
                date_trunc('month', k.kznm_date)::date AS month_start,

                COALESCE(SUM(
                    CASE
                        WHEN k.kznm_benefitvalue IS NULL
                          OR TRIM(k.kznm_benefitvalue) = ''
                          OR k.kznm_benefitvalue = '{}'
                          OR k.kznm_benefitvalue = '-'
                        THEN 0::numeric
                        ELSE COALESCE(
                            NULLIF(
                                REGEXP_REPLACE(k.kznm_benefitvalue, '[^0-9\\.]', '', 'g'),
                                ''
                            )::numeric,
                            0::numeric
                        )
                    END
                ), 0)::numeric AS benefit_amount,

                COALESCE(SUM(
                    CASE
                        WHEN k.kznm_verifyamount IS NULL
                          OR TRIM(k.kznm_verifyamount) = ''
                          OR k.kznm_verifyamount = '{}'
                          OR k.kznm_verifyamount = '-'
                        THEN 0::numeric
                        ELSE COALESCE(
                            NULLIF(
                                REGEXP_REPLACE(k.kznm_verifyamount, '[^0-9\\.]', '', 'g'),
                                ''
                            )::numeric,
                            0::numeric
                        )
                    END
                ), 0)::numeric AS verify_amount

            FROM kzn_tl_mst k

            JOIN gen_mv_flidhierarchy h
              ON k.kznm_flid = h.flid

            WHERE k.kznm_benefittype NOT IN ('S', 'NS')
              AND k.kznm_date::date >= date_trunc('month', CAST(:fromDate AS date))::date
              AND k.kznm_date::date <  (date_trunc('month', CAST(:toDate AS date)) + interval '1 month')::date
              AND position(:flid in coalesce(h.parentflids, '') || coalesce(h.flid, '')) > 0

            GROUP BY date_trunc('month', k.kznm_date)::date
        )
        SELECT
            to_char(ms.month_start, 'YYYY-MM') AS month_key,
            to_char(ms.month_start, 'Mon-YYYY') AS month_label,
            COALESCE(kb.benefit_amount, 0)::numeric AS benefit_amount,
            COALESCE(kb.verify_amount, 0)::numeric AS verify_amount
        FROM month_series ms
        LEFT JOIN kaizen_base kb
          ON kb.month_start = ms.month_start
        ORDER BY ms.month_start
        """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("flid", flid)
        .addValue("fromDate", monthStart)
        .addValue("toDate", monthEnd);

    List<DmtDashboardKaizenBenefitTrendResponse.DmtDashboardKaizenBenefitTrendPoint> trend = jdbcTemplate.query(sql,
        params, (rs, rowNum) -> new DmtDashboardKaizenBenefitTrendResponse.DmtDashboardKaizenBenefitTrendPoint(
            value(rs.getString("month_key")),
            value(rs.getString("month_label")),
            decimalValue(rs, "benefit_amount"),
            decimalValue(rs, "verify_amount")));

    BigDecimal totalBenefitAmount = trend.stream()
        .map(DmtDashboardKaizenBenefitTrendResponse.DmtDashboardKaizenBenefitTrendPoint::benefitAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal totalVerifyAmount = trend.stream()
        .map(DmtDashboardKaizenBenefitTrendResponse.DmtDashboardKaizenBenefitTrendPoint::verifyAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    return new DmtDashboardKaizenBenefitTrendResponse(
        value(flid),
        fromDate,
        toDate,
        totalBenefitAmount,
        totalVerifyAmount,
        trend);
  }

  private BigDecimal decimalValue(ResultSet rs, String columnName) throws SQLException {
    Object dbValue = rs.getObject(columnName);

    if (dbValue == null) {
      return BigDecimal.ZERO;
    }

    if (dbValue instanceof BigDecimal) {
      return (BigDecimal) dbValue;
    }

    if (dbValue instanceof Number) {
      return BigDecimal.valueOf(((Number) dbValue).doubleValue());
    }

    try {
      return new BigDecimal(String.valueOf(dbValue).trim());
    } catch (Exception e) {
      return BigDecimal.ZERO;
    }
  }

  private record ActionPlanClosureDbRow(
      String currentDisplayCode,
      String groupLevel,
      DmtDashboardActionPlanClosureResponse.DmtDashboardActionPlanClosureRow row) {
  }

  public DmtDashboardActionPlanClosureResponse getActionPlanClosureChartByFlid(
      String flid,
      LocalDate fromDate,
      LocalDate toDate) {

    String sql = """
        WITH current_node AS (
            SELECT
                   fnln_keyid,
                   displaycode,
                   comp_keyid,
                   locn_keyid,
                   sbut_keyid,
                   pbut_keyid,
                   sect_keyid,
                   cell_keyid
            FROM gen_vw_fnln
            WHERE fnln_keyid = :flid
            LIMIT 1
        ),
        scoped_nodes AS (
            SELECT v.*
            FROM gen_vw_fnln v
            JOIN current_node c ON 1 = 1
            WHERE
                (
                    COALESCE(NULLIF(TRIM(c.comp_keyid), ''), '-') IN ('-', '{}')
                    OR v.comp_keyid = c.comp_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.locn_keyid), ''), '-') IN ('-', '{}')
                    OR v.locn_keyid = c.locn_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.sbut_keyid), ''), '-') IN ('-', '{}')
                    OR v.sbut_keyid = c.sbut_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.pbut_keyid), ''), '-') IN ('-', '{}')
                    OR v.pbut_keyid = c.pbut_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.sect_keyid), ''), '-') IN ('-', '{}')
                    OR v.sect_keyid = c.sect_keyid
                )
            AND (
                    COALESCE(NULLIF(TRIM(c.cell_keyid), ''), '-') IN ('-', '{}')
                    OR v.cell_keyid = c.cell_keyid
                )
        ),
        mapped_nodes AS (
            SELECT
                UPPER(COALESCE(c.displaycode, '')) AS current_displaycode,

                CASE
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'COMP' THEN 'LOCATION'
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'LOCN' THEN 'SBU'
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'SBU'  THEN 'PBU'
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'PBU'  THEN 'DMT'
                    WHEN UPPER(COALESCE(c.displaycode, '')) IN ('SECT', 'L') THEN 'JH'
                    ELSE 'NONE'
                END AS group_level,

                CASE
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'COMP' THEN v.locn_keyid
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'LOCN' THEN v.sbut_keyid
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'SBU'  THEN v.pbut_keyid
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'PBU'  THEN sec.sect_keyid
                    WHEN UPPER(COALESCE(c.displaycode, '')) IN ('SECT', 'L') THEN v.cell_keyid
                    ELSE NULL
                END AS group_key,

                CASE
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'COMP' THEN COALESCE(NULLIF(TRIM(v.locn_name), ''), v.locn_keyid)
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'LOCN' THEN COALESCE(NULLIF(TRIM(v.sbut_name), ''), v.sbut_keyid)
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'SBU'  THEN COALESCE(NULLIF(TRIM(v.pbut_name), ''), v.pbut_keyid)
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'PBU'  THEN COALESCE(NULLIF(TRIM(sec.sect_name), ''), sec.sect_keyid)
                    WHEN UPPER(COALESCE(c.displaycode, '')) IN ('SECT', 'L') THEN COALESCE(NULLIF(TRIM(v.cell_name), ''), v.cell_keyid)
                    ELSE NULL
                END AS group_label,

                CASE
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'COMP' THEN COALESCE(l.locn_active, 'N')
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'LOCN' THEN COALESCE(s.sbut_active, 'N')
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'SBU'  THEN COALESCE(p.pbut_active, 'N')
                    WHEN UPPER(COALESCE(c.displaycode, '')) = 'PBU'  THEN COALESCE(sec.sect_active, 'N')
                    WHEN UPPER(COALESCE(c.displaycode, '')) IN ('SECT', 'L') THEN COALESCE(cell.cell_active, 'N')
                    ELSE 'N'
                END AS group_active,

                v.fnln_keyid AS node_flid

            FROM scoped_nodes v
            CROSS JOIN current_node c
            LEFT JOIN gen_tl_locationmst l
              ON l.locn_keyid = v.locn_keyid
            LEFT JOIN gen_tl_sbumst s
              ON s.sbut_keyid = v.sbut_keyid
            LEFT JOIN gen_tl_pbumst p
              ON p.pbut_keyid = v.pbut_keyid
            LEFT JOIN gen_tl_sectionmst sec
              ON sec.sect_keyid = v.sect_keyid
             AND (
                    UPPER(COALESCE(c.displaycode, '')) <> 'PBU'
                    OR sec.sect_factoryid = c.pbut_keyid
                 )
            LEFT JOIN gen_tl_cellmst cell
              ON cell.cell_keyid = v.cell_keyid
        ),
        valid_nodes AS (
            SELECT *
            FROM mapped_nodes
            WHERE group_key IS NOT NULL
              AND TRIM(group_key) NOT IN ('', '{}', '-')
              AND COALESCE(group_active, 'N') = 'Y'
        ),
        groups AS (
            SELECT DISTINCT
                   current_displaycode,
                   group_level,
                   group_key,
                   COALESCE(NULLIF(TRIM(group_label), ''), group_key) AS group_label
            FROM valid_nodes
        )
        SELECT
            g.current_displaycode,
            g.group_level,
            g.group_key,
            g.group_label,

            COUNT(DISTINCT d.apld_keyid)::bigint AS identified_count,

            COUNT(DISTINCT CASE
                WHEN COALESCE(d.apld_status, '') = 'C'
                THEN d.apld_keyid
            END)::bigint AS completed_count,

            COUNT(DISTINCT CASE
                WHEN COALESCE(d.apld_status, '') = 'W'
                THEN d.apld_keyid
            END)::bigint AS work_in_progress_count,

            COUNT(DISTINCT CASE
                WHEN d.apld_keyid IS NOT NULL
                 AND COALESCE(d.apld_status, '') <> 'C'
                THEN d.apld_keyid
            END)::bigint AS pending_count

        FROM groups g
        LEFT JOIN valid_nodes n
          ON n.group_key = g.group_key
        LEFT JOIN gen_tl_actionplanmst m
          ON m.aplm_flid = n.node_flid
         AND m.aplm_plandate::date BETWEEN :fromDate AND :toDate
        LEFT JOIN gen_tl_actionplandtl d
          ON d.apld_aplm_keyid = m.aplm_keyid

        GROUP BY
            g.current_displaycode,
            g.group_level,
            g.group_key,
            g.group_label

        ORDER BY
            g.group_label
        """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("flid", flid)
        .addValue("fromDate", fromDate)
        .addValue("toDate", toDate);

    List<ActionPlanClosureDbRow> dbRows = jdbcTemplate.query(
        sql,
        params,
        (rs, rowNum) -> {
          Long identifiedCount = longValue(rs, "identified_count");
          Long completedCount = longValue(rs, "completed_count");
          Long pendingCount = longValue(rs, "pending_count");
          Long workInProgressCount = longValue(rs, "work_in_progress_count");

          Double completionPercentage = calculatePercentage(
              completedCount,
              identifiedCount);

          return new ActionPlanClosureDbRow(
              value(rs.getString("current_displaycode")),
              value(rs.getString("group_level")),
              new DmtDashboardActionPlanClosureResponse.DmtDashboardActionPlanClosureRow(
                  value(rs.getString("group_key")),
                  value(rs.getString("group_label")),
                  identifiedCount,
                  completedCount,
                  pendingCount,
                  workInProgressCount,
                  completionPercentage));
        });

    if (dbRows.isEmpty()) {
      return DmtDashboardActionPlanClosureResponse.empty(
          value(flid),
          fromDate,
          toDate);
    }

    String currentLevel = mapCurrentLevel(dbRows.get(0).currentDisplayCode());
    String groupLevel = dbRows.get(0).groupLevel();

    List<DmtDashboardActionPlanClosureResponse.DmtDashboardActionPlanClosureRow> rows = dbRows.stream()
        .map(ActionPlanClosureDbRow::row)
        .toList();

    return new DmtDashboardActionPlanClosureResponse(
        value(flid),
        fromDate,
        toDate,
        currentLevel,
        groupLevel,
        rows);
  }

  public DmtDashboardAttendanceGaugeResponse getAttendanceGaugeByFlid(
      String flid,
      LocalDate fromDate,
      LocalDate toDate) {

    LocalDate monthStart = fromDate.withDayOfMonth(1);
    LocalDate monthEnd = toDate.withDayOfMonth(1);

    String sql = """
        WITH month_series AS (
            SELECT
                date_trunc('month', d)::date AS month_start
            FROM generate_series(
                date_trunc('month', CAST(:fromDate AS date)),
                date_trunc('month', CAST(:toDate AS date)),
                interval '1 month'
            ) AS d
        ),
        monthly_attendance AS (
            SELECT
                m2.moma_employeeid AS emp_id,
                date_trunc('month', m1.moms_date)::date AS month_start,

                COUNT(m2.moma_attandance)::bigint AS meetings,

                SUM(CASE
                    WHEN m2.moma_attandance = 'P'
                    THEN 1 ELSE 0
                END)::bigint AS present_count,

                SUM(CASE
                    WHEN m2.moma_attandance = 'D'
                    THEN 1 ELSE 0
                END)::bigint AS on_duty_count

            FROM gen_tl_mommst m1

            JOIN gen_tl_momattendance m2
              ON m1.moms_keyid = m2.moma_moms_keyid

            JOIN gen_tl_employeemst emp
              ON emp.empm_keyid = m2.moma_employeeid
             AND COALESCE(emp.empm_active, 'N') = 'Y'

            JOIN gen_mv_flidhierarchy h
              ON m1.moms_flid = h.flid

            WHERE m2.moma_attandance <> '-'
              AND m1.moms_date::date >= date_trunc('month', CAST(:fromDate AS date))::date
              AND m1.moms_date::date <  (date_trunc('month', CAST(:toDate AS date)) + interval '1 month')::date
              AND position(:flid in coalesce(h.parentflids, '') || '/' || coalesce(h.flid, '')) > 0

            GROUP BY
                m2.moma_employeeid,
                date_trunc('month', m1.moms_date)::date
        ),
        employee_scope AS (
            SELECT DISTINCT emp_id
            FROM monthly_attendance
            WHERE emp_id IS NOT NULL
        ),
        employee_monthly AS (
            SELECT
                es.emp_id,
                ms.month_start,

                COALESCE(ma.meetings, 0)::bigint AS meetings,
                COALESCE(ma.present_count, 0)::bigint AS present_count,
                COALESCE(ma.on_duty_count, 0)::bigint AS on_duty_count,

                CASE
                    WHEN COALESCE(ma.meetings, 0) > 0
                    THEN ROUND(
                        (
                            (
                                COALESCE(ma.present_count, 0)
                                + COALESCE(ma.on_duty_count, 0)
                            ) * 100.0
                        ) / ma.meetings,
                        2
                    )
                    ELSE 0
                END AS month_attendance_percentage

            FROM employee_scope es
            CROSS JOIN month_series ms
            LEFT JOIN monthly_attendance ma
              ON ma.emp_id = es.emp_id
             AND ma.month_start = ms.month_start
        ),
        employee_summary AS (
            SELECT
                emp_id,
                SUM(meetings)::bigint AS meetings,
                SUM(present_count)::bigint AS present_count,
                SUM(on_duty_count)::bigint AS on_duty_count,
                ROUND(AVG(month_attendance_percentage)::numeric, 2) AS employee_attendance_percentage
            FROM employee_monthly
            GROUP BY emp_id
        )
        SELECT
            COUNT(DISTINCT emp_id)::bigint AS employee_count,
            COALESCE(SUM(meetings), 0)::bigint AS meeting_count,
            COALESCE(SUM(present_count), 0)::bigint AS present_count,
            COALESCE(SUM(on_duty_count), 0)::bigint AS on_duty_count,
            ROUND(COALESCE(AVG(employee_attendance_percentage), 0)::numeric, 2) AS attendance_percentage
        FROM employee_summary
        """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("flid", flid)
        .addValue("fromDate", monthStart)
        .addValue("toDate", monthEnd);

    return jdbcTemplate.query(sql, params, rs -> {
      if (!rs.next()) {
        return DmtDashboardAttendanceGaugeResponse.empty(
            value(flid),
            fromDate,
            toDate);
      }

      return new DmtDashboardAttendanceGaugeResponse(
          value(flid),
          fromDate,
          toDate,
          longValue(rs, "employee_count"),
          longValue(rs, "meeting_count"),
          longValue(rs, "present_count"),
          longValue(rs, "on_duty_count"),
          doubleValue(rs, "attendance_percentage"));
    });
  }

}