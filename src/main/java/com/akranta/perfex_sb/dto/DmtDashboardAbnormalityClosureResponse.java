package com.akranta.perfex_sb.dto;

import java.time.LocalDate;
import java.util.List;

public record DmtDashboardAbnormalityClosureResponse(
        String flid,
        LocalDate fromDate,
        LocalDate toDate,
        String currentLevel,
        String groupLevel,
        List<DmtDashboardAbnormalityClosureRow> rows) {

    public record DmtDashboardAbnormalityClosureRow(
            String groupKey,
            String groupLabel,
            Long identifiedCount,
            Long closedCount,
            Long pendingCount,
            Double closurePercentage) {
    }

    public static DmtDashboardAbnormalityClosureResponse empty(
            String flid,
            LocalDate fromDate,
            LocalDate toDate) {

        return new DmtDashboardAbnormalityClosureResponse(
                flid,
                fromDate,
                toDate,
                "UNKNOWN",
                "NONE",
                List.of());
    }
}