package com.akranta.perfex_sb.dto;

import java.time.LocalDate;

public record DmtDashboardAttendanceGaugeResponse(
        String flid,
        LocalDate fromDate,
        LocalDate toDate,
        Long employeeCount,
        Long meetingCount,
        Long presentCount,
        Long onDutyCount,
        Double attendancePercentage) {

    public static DmtDashboardAttendanceGaugeResponse empty(
            String flid,
            LocalDate fromDate,
            LocalDate toDate) {

        return new DmtDashboardAttendanceGaugeResponse(
                flid,
                fromDate,
                toDate,
                0L,
                0L,
                0L,
                0L,
                0.0);
    }
}