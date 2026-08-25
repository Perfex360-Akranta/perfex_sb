package com.akranta.perfex_sb.dto;

import java.time.LocalDate;

public record DmtDashboardTrainingSummaryResponse(
        String flid,
        LocalDate fromDate,
        LocalDate toDate,
        Long identifiedCount,
        Long completedCount,
        Long pendingCount,
        Double completionPercentage) {

    public static DmtDashboardTrainingSummaryResponse empty(
            String flid,
            LocalDate fromDate,
            LocalDate toDate) {

        return new DmtDashboardTrainingSummaryResponse(
                flid,
                fromDate,
                toDate,
                0L,
                0L,
                0L,
                0.0);
    }
}