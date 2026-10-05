package com.akranta.perfex_sb.service.impl;

import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.DmtDashboardAbnormalityClosureResponse;
import com.akranta.perfex_sb.dto.DmtDashboardActionPlanClosureResponse;
import com.akranta.perfex_sb.dto.DmtDashboardAttendanceGaugeResponse;
import com.akranta.perfex_sb.dto.DmtDashboardEmployeeCountResponse;
import com.akranta.perfex_sb.dto.DmtDashboardKaizenBenefitTrendResponse;
import com.akranta.perfex_sb.dto.DmtDashboardLevelCountsResponse;
import com.akranta.perfex_sb.dto.DmtDashboardLossAnalysisResponse;
import com.akranta.perfex_sb.dto.DmtDashboardTrainingSummaryResponse;
import com.akranta.perfex_sb.repository.DmtDashboardRepository;
import com.akranta.perfex_sb.service.DmtDashboardService;
import com.akranta.perfex_sb.dto.DmtDashboardTransactionSummaryResponse;

import java.time.LocalDate;

@Service
public class DmtDashboardServiceImpl implements DmtDashboardService {

        private final DmtDashboardRepository dmtDashboardRepository;

        public DmtDashboardServiceImpl(DmtDashboardRepository dmtDashboardRepository) {
                this.dmtDashboardRepository = dmtDashboardRepository;
        }

        @Override
        public DmtDashboardLevelCountsResponse getLevelCounts(String flid) {

                if (!isValid(flid)) {
                        return new DmtDashboardLevelCountsResponse(
                                        "",
                                        "UNKNOWN",
                                        "",
                                        "",
                                        "",
                                        java.util.List.of());
                }

                return dmtDashboardRepository.getLevelCountsByFlid(flid);
        }

        private boolean isValid(String value) {
                return value != null
                                && !value.trim().isEmpty()
                                && !"null".equalsIgnoreCase(value.trim())
                                && !"undefined".equalsIgnoreCase(value.trim())
                                && !"{}".equals(value.trim())
                                && !"-".equals(value.trim());
        }

        @Override
        public DmtDashboardEmployeeCountResponse getEmployeeCount(String flid) {

                if (!isValid(flid)) {
                        return new DmtDashboardEmployeeCountResponse(
                                        "",
                                        "UNKNOWN",
                                        "",
                                        "",
                                        "",
                                        0L,
                                        "Employees",
                                        "active employees",
                                        "cyan",
                                        false);
                }

                return dmtDashboardRepository.getEmployeeCountByFlid(flid);
        }

        @Override
        public DmtDashboardTransactionSummaryResponse getTransactionSummary(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate) {

                if (!isValid(flid)) {
                        return DmtDashboardTransactionSummaryResponse.empty(
                                        "",
                                        fromDate,
                                        toDate);

                }

                LocalDate safeFromDate = fromDate != null
                                ? fromDate
                                : LocalDate.now().withMonth(4).withDayOfMonth(1);

                LocalDate safeToDate = toDate != null
                                ? toDate
                                : LocalDate.now();

                return dmtDashboardRepository.getTransactionSummaryByFlid(
                                flid,
                                safeFromDate,
                                safeToDate);
        }

        @Override
        public DmtDashboardTrainingSummaryResponse getTrainingSummary(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate) {

                if (!isValid(flid)) {
                        return DmtDashboardTrainingSummaryResponse.empty(
                                        "",
                                        fromDate,
                                        toDate);
                }

                LocalDate safeFromDate = fromDate != null
                                ? fromDate
                                : LocalDate.now().withMonth(4).withDayOfMonth(1);

                LocalDate safeToDate = toDate != null
                                ? toDate
                                : LocalDate.now();

                return dmtDashboardRepository.getTrainingSummaryByFlid(
                                flid,
                                safeFromDate,
                                safeToDate);
        }

        @Override
        public DmtDashboardAbnormalityClosureResponse getAbnormalityClosureChart(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate) {

                if (!isValid(flid)) {
                        return DmtDashboardAbnormalityClosureResponse.empty(
                                        "",
                                        fromDate,
                                        toDate);
                }

                LocalDate safeFromDate = fromDate != null
                                ? fromDate
                                : LocalDate.now().withMonth(4).withDayOfMonth(1);

                LocalDate safeToDate = toDate != null
                                ? toDate
                                : LocalDate.now();

                return dmtDashboardRepository.getAbnormalityClosureChartByFlid(
                                flid,
                                safeFromDate,
                                safeToDate);
        }

        @Override
        public DmtDashboardLossAnalysisResponse getLossAnalysis(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate) {

                if (!isValid(flid)) {
                        return DmtDashboardLossAnalysisResponse.empty(
                                        "",
                                        fromDate,
                                        toDate);
                }

                LocalDate safeFromDate = fromDate != null
                                ? fromDate
                                : LocalDate.now().withMonth(4).withDayOfMonth(1);

                LocalDate safeToDate = toDate != null
                                ? toDate
                                : LocalDate.now();

                return dmtDashboardRepository.getLossAnalysisByFlid(
                                flid,
                                safeFromDate,
                                safeToDate);
        }

        @Override
        public DmtDashboardActionPlanClosureResponse getActionPlanClosureChart(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate) {

                if (!isValid(flid)) {
                        return DmtDashboardActionPlanClosureResponse.empty(
                                        "",
                                        fromDate,
                                        toDate);
                }

                LocalDate safeFromDate = fromDate != null
                                ? fromDate
                                : LocalDate.now().withMonth(4).withDayOfMonth(1);

                LocalDate safeToDate = toDate != null
                                ? toDate
                                : LocalDate.now();

                return dmtDashboardRepository.getActionPlanClosureChartByFlid(
                                flid,
                                safeFromDate,
                                safeToDate);
        }

        @Override
        public DmtDashboardAttendanceGaugeResponse getAttendanceGauge(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate) {

                if (!isValid(flid)) {
                        return DmtDashboardAttendanceGaugeResponse.empty(
                                        "",
                                        fromDate,
                                        toDate);
                }

                LocalDate safeFromDate = fromDate != null
                                ? fromDate
                                : LocalDate.now().withMonth(4).withDayOfMonth(1);

                LocalDate safeToDate = toDate != null
                                ? toDate
                                : LocalDate.now();

                if (safeFromDate.isAfter(safeToDate)) {
                        LocalDate temp = safeFromDate;
                        safeFromDate = safeToDate;
                        safeToDate = temp;
                }

                return dmtDashboardRepository.getAttendanceGaugeByFlid(
                                flid,
                                safeFromDate,
                                safeToDate);
        }

        @Override
        public DmtDashboardKaizenBenefitTrendResponse getKaizenBenefitTrend(
                        String flid,
                        LocalDate fromDate,
                        LocalDate toDate) {

                if (!isValid(flid)) {
                        return DmtDashboardKaizenBenefitTrendResponse.empty(
                                        "",
                                        fromDate,
                                        toDate);
                }

                LocalDate safeFromDate = fromDate != null
                                ? fromDate
                                : LocalDate.now().withMonth(4).withDayOfMonth(1);

                LocalDate safeToDate = toDate != null
                                ? toDate
                                : LocalDate.now();

                if (safeFromDate.isAfter(safeToDate)) {
                        LocalDate temp = safeFromDate;
                        safeFromDate = safeToDate;
                        safeToDate = temp;
                }

                return dmtDashboardRepository.getKaizenBenefitTrendByFlid(
                                flid,
                                safeFromDate,
                                safeToDate);
        }

}