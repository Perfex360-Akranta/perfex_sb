package com.akranta.perfex_sb.dto;

import java.time.LocalDate;
import java.util.List;

public record DmtDashboardLossAnalysisResponse(
        String flid,
        LocalDate fromDate,
        LocalDate toDate,
        String currentLevel,
        String groupLevel,
        Long totalLossMinutes,
        Double totalLossHours,
        String totalLossText,
        List<DmtDashboardLossTrendPoint> monthlyTrend,
        List<DmtDashboardLossChildContribution> childContribution) {

    public record DmtDashboardLossTrendPoint(
            String monthKey,
            String monthLabel,
            Long lossMinutes,
            Double lossHours,
            String lossText) {
    }

    public record DmtDashboardLossChildContribution(
            String groupKey,
            String groupLabel,
            Long lossMinutes,
            Double lossHours,
            String lossText,
            Double contributionPercentage) {
    }

    public static DmtDashboardLossAnalysisResponse empty(
            String flid,
            LocalDate fromDate,
            LocalDate toDate) {

        return new DmtDashboardLossAnalysisResponse(
                flid,
                fromDate,
                toDate,
                "UNKNOWN",
                "NONE",
                0L,
                0.0,
                "0:00",
                List.of(),
                List.of());
    }
}