package com.akranta.perfex_sb.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DmtDashboardKaizenBenefitTrendResponse(
        String flid,
        LocalDate fromDate,
        LocalDate toDate,
        BigDecimal totalBenefitAmount,
        BigDecimal totalVerifyAmount,
        List<DmtDashboardKaizenBenefitTrendPoint> monthlyTrend) {

    public record DmtDashboardKaizenBenefitTrendPoint(
            String monthKey,
            String monthLabel,
            BigDecimal benefitAmount,
            BigDecimal verifyAmount) {
    }

    public static DmtDashboardKaizenBenefitTrendResponse empty(
            String flid,
            LocalDate fromDate,
            LocalDate toDate) {

        return new DmtDashboardKaizenBenefitTrendResponse(
                flid,
                fromDate,
                toDate,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                List.of());
    }
}