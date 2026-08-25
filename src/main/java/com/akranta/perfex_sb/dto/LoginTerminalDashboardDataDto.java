package com.akranta.perfex_sb.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * Complete dashboard payload for the public-login terminal slideshow.
 *
 * The scope is resolved from the HttpOnly registered-browser cookie. No FLID
 * supplied by Angular is trusted by this endpoint.
 */
public record LoginTerminalDashboardDataDto(
        boolean available,
        boolean complete,
        LoginTerminalDashboardScopeDto scope,
        LocalDate fromDate,
        LocalDate toDate,
        DmtDashboardLevelCountsResponse levelCounts,
        DmtDashboardEmployeeCountResponse employeeCount,
        DmtDashboardTransactionSummaryResponse transactionSummary,
        DmtDashboardTrainingSummaryResponse trainingSummary,
        DmtDashboardAbnormalityClosureResponse abnormalityClosureChart,
        DmtDashboardLossAnalysisResponse lossAnalysis,
        DmtDashboardActionPlanClosureResponse actionPlanClosureChart,
        DmtDashboardAttendanceGaugeResponse attendanceGauge,
        DmtDashboardKaizenBenefitTrendResponse kaizenBenefitTrend,
        List<String> warnings,
        String message) {

    public LoginTerminalDashboardDataDto {
        warnings = warnings == null ? List.of() : List.copyOf(warnings);
        message = clean(message);
    }

    public static LoginTerminalDashboardDataDto unavailable(
            LoginTerminalDashboardScopeDto scope,
            LocalDate fromDate,
            LocalDate toDate,
            String message) {

        return new LoginTerminalDashboardDataDto(
                false,
                false,
                scope,
                fromDate,
                toDate,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(),
                message);
    }

    public static LoginTerminalDashboardDataDto available(
            LoginTerminalDashboardScopeDto scope,
            LocalDate fromDate,
            LocalDate toDate,
            DmtDashboardLevelCountsResponse levelCounts,
            DmtDashboardEmployeeCountResponse employeeCount,
            DmtDashboardTransactionSummaryResponse transactionSummary,
            DmtDashboardTrainingSummaryResponse trainingSummary,
            DmtDashboardAbnormalityClosureResponse abnormalityClosureChart,
            DmtDashboardLossAnalysisResponse lossAnalysis,
            DmtDashboardActionPlanClosureResponse actionPlanClosureChart,
            DmtDashboardAttendanceGaugeResponse attendanceGauge,
            DmtDashboardKaizenBenefitTrendResponse kaizenBenefitTrend,
            List<String> warnings) {

        List<String> safeWarnings = warnings == null
                ? List.of()
                : List.copyOf(warnings);

        boolean complete = safeWarnings.isEmpty();

        return new LoginTerminalDashboardDataDto(
                true,
                complete,
                scope,
                fromDate,
                toDate,
                levelCounts,
                employeeCount,
                transactionSummary,
                trainingSummary,
                abnormalityClosureChart,
                lossAnalysis,
                actionPlanClosureChart,
                attendanceGauge,
                kaizenBenefitTrend,
                safeWarnings,
                complete
                        ? "Terminal dashboard data loaded successfully."
                        : "Terminal dashboard data loaded with one or more unavailable sections.");
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
