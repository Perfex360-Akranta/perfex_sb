package com.akranta.perfex_sb.dto;

import java.time.LocalDate;
import java.util.List;

public record DmtDashboardActionPlanClosureResponse(
        String flid,
        LocalDate fromDate,
        LocalDate toDate,
        String currentLevel,
        String groupLevel,
        List<DmtDashboardActionPlanClosureRow> rows) {

    public record DmtDashboardActionPlanClosureRow(
            String groupKey,
            String groupLabel,
            Long identifiedCount,
            Long completedCount,
            Long pendingCount,
            Long workInProgressCount,
            Double completionPercentage) {
    }

    public static DmtDashboardActionPlanClosureResponse empty(
            String flid,
            LocalDate fromDate,
            LocalDate toDate) {

        return new DmtDashboardActionPlanClosureResponse(
                flid,
                fromDate,
                toDate,
                "UNKNOWN",
                "NONE",
                List.of());
    }
}