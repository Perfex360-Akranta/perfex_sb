package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.DmtDashboardLevelCountsResponse;
import com.akranta.perfex_sb.dto.DmtDashboardLossAnalysisResponse;
import com.akranta.perfex_sb.dto.DmtDashboardTrainingSummaryResponse;
import com.akranta.perfex_sb.dto.DmtDashboardTransactionSummaryResponse;

import java.time.LocalDate;

import com.akranta.perfex_sb.dto.DmtDashboardAbnormalityClosureResponse;
import com.akranta.perfex_sb.dto.DmtDashboardActionPlanClosureResponse;
import com.akranta.perfex_sb.dto.DmtDashboardAttendanceGaugeResponse;
import com.akranta.perfex_sb.dto.DmtDashboardEmployeeCountResponse;
import com.akranta.perfex_sb.dto.DmtDashboardKaizenBenefitTrendResponse;

public interface DmtDashboardService {

        DmtDashboardLevelCountsResponse getLevelCounts(String flid);

        DmtDashboardEmployeeCountResponse getEmployeeCount(String flid);

        DmtDashboardTransactionSummaryResponse getTransactionSummary(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate);

        DmtDashboardTrainingSummaryResponse getTrainingSummary(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate);

        DmtDashboardAbnormalityClosureResponse getAbnormalityClosureChart(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate);

        DmtDashboardLossAnalysisResponse getLossAnalysis(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate);

        DmtDashboardActionPlanClosureResponse getActionPlanClosureChart(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate);

        DmtDashboardAttendanceGaugeResponse getAttendanceGauge(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate);

        DmtDashboardKaizenBenefitTrendResponse getKaizenBenefitTrend(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate);

}