package com.akranta.perfex_sb.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.akranta.perfex_sb.dto.DmtDashboardAbnormalityClosureResponse;
import com.akranta.perfex_sb.dto.DmtDashboardActionPlanClosureResponse;
import com.akranta.perfex_sb.dto.DmtDashboardAttendanceGaugeResponse;
import com.akranta.perfex_sb.dto.DmtDashboardEmployeeCountResponse;
import com.akranta.perfex_sb.dto.DmtDashboardKaizenBenefitTrendResponse;
import com.akranta.perfex_sb.dto.DmtDashboardLevelCountsResponse;
import com.akranta.perfex_sb.dto.DmtDashboardLossAnalysisResponse;
import com.akranta.perfex_sb.dto.DmtDashboardTrainingSummaryResponse;
import com.akranta.perfex_sb.dto.DmtDashboardTransactionSummaryResponse;
import com.akranta.perfex_sb.service.DmtDashboardService;

@RestController
@RequestMapping("/api/abnormality/dmt-dashboard")
public class DmtDashboardController {

        private final DmtDashboardService dmtDashboardService;

        public DmtDashboardController(DmtDashboardService dmtDashboardService) {
                this.dmtDashboardService = dmtDashboardService;
        }

        @GetMapping("/level-counts")
        public ResponseEntity<DmtDashboardLevelCountsResponse> getLevelCounts(
                        @RequestParam String flid) {

                return ResponseEntity.ok(
                                dmtDashboardService.getLevelCounts(flid));
        }

        @GetMapping("/employee-count")
        public ResponseEntity<DmtDashboardEmployeeCountResponse> getEmployeeCount(
                        @RequestParam String flid) {

                return ResponseEntity.ok(
                                dmtDashboardService.getEmployeeCount(flid));
        }

        @GetMapping("/transaction-summary")
        public ResponseEntity<DmtDashboardTransactionSummaryResponse> getTransactionSummary(
                        @RequestParam String flid,

                        @RequestParam LocalDate fromDate,

                        @RequestParam LocalDate toDate) {

                // System.out.println("DMT TRANSACTION SUMMARY CONTROLLER START");
                // System.out.println("FLID : " + flid);
                // System.out.println("From Date : " + fromDate);
                // System.out.println("To Date : " + toDate);

                DmtDashboardTransactionSummaryResponse response = dmtDashboardService.getTransactionSummary(flid,
                                fromDate,
                                toDate);

                System.out.println("DMT TRANSACTION SUMMARY CONTROLLER END");
                System.out.println("Total Transactions : " + response.totalTransactions());

                return ResponseEntity.ok(response);
        }

        @GetMapping("/training-summary")
        public ResponseEntity<DmtDashboardTrainingSummaryResponse> getTrainingSummary(
                        @RequestParam String flid,

                        @RequestParam LocalDate fromDate,

                        @RequestParam LocalDate toDate) {

                return ResponseEntity.ok(
                                dmtDashboardService.getTrainingSummary(
                                                flid,
                                                fromDate,
                                                toDate));
        }

        @GetMapping("/abnormality-closure-chart")
        public ResponseEntity<DmtDashboardAbnormalityClosureResponse> getAbnormalityClosureChart(
                        @RequestParam String flid,

                        @RequestParam LocalDate fromDate,

                        @RequestParam LocalDate toDate) {

                return ResponseEntity.ok(
                                dmtDashboardService.getAbnormalityClosureChart(
                                                flid,
                                                fromDate,
                                                toDate));
        }

        @GetMapping("/loss-analysis")
        public ResponseEntity<DmtDashboardLossAnalysisResponse> getLossAnalysis(
                        @RequestParam String flid,

                        @RequestParam LocalDate fromDate,

                        @RequestParam LocalDate toDate) {

                return ResponseEntity.ok(
                                dmtDashboardService.getLossAnalysis(
                                                flid,
                                                fromDate,
                                                toDate));
        }

        @GetMapping("/action-plan-closure-chart")
        public ResponseEntity<DmtDashboardActionPlanClosureResponse> getActionPlanClosureChart(
                        @RequestParam String flid,

                        @RequestParam LocalDate fromDate,

                        @RequestParam LocalDate toDate) {

                return ResponseEntity.ok(
                                dmtDashboardService.getActionPlanClosureChart(
                                                flid,
                                                fromDate,
                                                toDate));
        }

        @GetMapping("/attendance-gauge")
        public ResponseEntity<DmtDashboardAttendanceGaugeResponse> getAttendanceGauge(
                        @RequestParam String flid,

                        @RequestParam

                        LocalDate fromDate,

                        @RequestParam

                        LocalDate toDate) {

                return ResponseEntity.ok(
                                dmtDashboardService.getAttendanceGauge(
                                                flid,
                                                fromDate,
                                                toDate));
        }

        @GetMapping("/kaizen-benefit-trend")
        public ResponseEntity<DmtDashboardKaizenBenefitTrendResponse> getKaizenBenefitTrend(
                        @RequestParam String flid,

                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,

                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

                return ResponseEntity.ok(
                                dmtDashboardService.getKaizenBenefitTrend(
                                                flid,
                                                fromDate,
                                                toDate));
        }

}