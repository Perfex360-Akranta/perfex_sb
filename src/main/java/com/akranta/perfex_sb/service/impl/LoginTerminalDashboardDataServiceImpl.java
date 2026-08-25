package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.DmtDashboardAbnormalityClosureResponse;
import com.akranta.perfex_sb.dto.DmtDashboardActionPlanClosureResponse;
import com.akranta.perfex_sb.dto.DmtDashboardAttendanceGaugeResponse;
import com.akranta.perfex_sb.dto.DmtDashboardEmployeeCountResponse;
import com.akranta.perfex_sb.dto.DmtDashboardKaizenBenefitTrendResponse;
import com.akranta.perfex_sb.dto.DmtDashboardLevelCountsResponse;
import com.akranta.perfex_sb.dto.DmtDashboardLossAnalysisResponse;
import com.akranta.perfex_sb.dto.DmtDashboardTrainingSummaryResponse;
import com.akranta.perfex_sb.dto.DmtDashboardTransactionSummaryResponse;
import com.akranta.perfex_sb.dto.LoginTerminalDashboardDataDto;
import com.akranta.perfex_sb.dto.LoginTerminalDashboardScopeDto;
import com.akranta.perfex_sb.service.DmtDashboardService;
import com.akranta.perfex_sb.service.LoginTerminalDashboardDataService;
import com.akranta.perfex_sb.service.LoginTerminalDashboardScopeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Loads the public-login dashboard by resolving the permitted FLID from the
 * registered-browser cookie and delegating to the existing DMT dashboard
 * service. Angular never supplies the dashboard FLID.
 */
@Service
public class LoginTerminalDashboardDataServiceImpl
        implements LoginTerminalDashboardDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            LoginTerminalDashboardDataServiceImpl.class);

    private final LoginTerminalDashboardScopeService scopeService;
    private final DmtDashboardService dmtDashboardService;

    public LoginTerminalDashboardDataServiceImpl(
            LoginTerminalDashboardScopeService scopeService,
            DmtDashboardService dmtDashboardService) {

        this.scopeService = scopeService;
        this.dmtDashboardService = dmtDashboardService;
    }

    @Override
    public LoginTerminalDashboardDataDto loadByBrowserToken(
            String rawBrowserToken) {

        LocalDate toDate = LocalDate.now();
        LocalDate fromDate = financialYearStart(toDate);

        LoginTerminalDashboardScopeDto scope =
                scopeService.resolveByBrowserToken(rawBrowserToken);

        if (!scope.available()) {
            return LoginTerminalDashboardDataDto.unavailable(
                    scope,
                    fromDate,
                    toDate,
                    scope.message());
        }

        String scopeFlid = clean(scope.scopeFlid());

        if (scopeFlid.isBlank()) {
            return LoginTerminalDashboardDataDto.unavailable(
                    scope,
                    fromDate,
                    toDate,
                    "The registered terminal has no permitted dashboard functional location.");
        }

        List<String> warnings = new ArrayList<>();

        DmtDashboardLevelCountsResponse levelCounts = safeLoad(
                "levelCounts",
                () -> dmtDashboardService.getLevelCounts(scopeFlid),
                emptyLevelCounts(scopeFlid),
                warnings,
                scope);

        DmtDashboardEmployeeCountResponse employeeCount = safeLoad(
                "employeeCount",
                () -> dmtDashboardService.getEmployeeCount(scopeFlid),
                emptyEmployeeCount(scopeFlid),
                warnings,
                scope);

        DmtDashboardTransactionSummaryResponse transactionSummary = safeLoad(
                "transactionSummary",
                () -> dmtDashboardService.getTransactionSummary(
                        scopeFlid,
                        fromDate,
                        toDate),
                DmtDashboardTransactionSummaryResponse.empty(
                        scopeFlid,
                        fromDate,
                        toDate),
                warnings,
                scope);

        DmtDashboardTrainingSummaryResponse trainingSummary = safeLoad(
                "trainingSummary",
                () -> dmtDashboardService.getTrainingSummary(
                        scopeFlid,
                        fromDate,
                        toDate),
                DmtDashboardTrainingSummaryResponse.empty(
                        scopeFlid,
                        fromDate,
                        toDate),
                warnings,
                scope);

        DmtDashboardAbnormalityClosureResponse abnormalityClosureChart = safeLoad(
                "abnormalityClosureChart",
                () -> dmtDashboardService.getAbnormalityClosureChart(
                        scopeFlid,
                        fromDate,
                        toDate),
                DmtDashboardAbnormalityClosureResponse.empty(
                        scopeFlid,
                        fromDate,
                        toDate),
                warnings,
                scope);

        DmtDashboardLossAnalysisResponse lossAnalysis = safeLoad(
                "lossAnalysis",
                () -> dmtDashboardService.getLossAnalysis(
                        scopeFlid,
                        fromDate,
                        toDate),
                DmtDashboardLossAnalysisResponse.empty(
                        scopeFlid,
                        fromDate,
                        toDate),
                warnings,
                scope);

        DmtDashboardActionPlanClosureResponse actionPlanClosureChart = safeLoad(
                "actionPlanClosureChart",
                () -> dmtDashboardService.getActionPlanClosureChart(
                        scopeFlid,
                        fromDate,
                        toDate),
                DmtDashboardActionPlanClosureResponse.empty(
                        scopeFlid,
                        fromDate,
                        toDate),
                warnings,
                scope);

        DmtDashboardAttendanceGaugeResponse attendanceGauge = safeLoad(
                "attendanceGauge",
                () -> dmtDashboardService.getAttendanceGauge(
                        scopeFlid,
                        fromDate,
                        toDate),
                DmtDashboardAttendanceGaugeResponse.empty(
                        scopeFlid,
                        fromDate,
                        toDate),
                warnings,
                scope);

        DmtDashboardKaizenBenefitTrendResponse kaizenBenefitTrend = safeLoad(
                "kaizenBenefitTrend",
                () -> dmtDashboardService.getKaizenBenefitTrend(
                        scopeFlid,
                        fromDate,
                        toDate),
                DmtDashboardKaizenBenefitTrendResponse.empty(
                        scopeFlid,
                        fromDate,
                        toDate),
                warnings,
                scope);

        LOGGER.info(
                "Terminal dashboard data loaded. userKeyId={}, roleId={}, "
                        + "scopeType={}, scopeFlid={}, complete={}, warnings={}",
                scope.userKeyId(),
                scope.roleId(),
                scope.scopeType(),
                scopeFlid,
                warnings.isEmpty(),
                warnings.size());

        return LoginTerminalDashboardDataDto.available(
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
                warnings);
    }

    private <T> T safeLoad(
            String section,
            Supplier<T> loader,
            T fallback,
            List<String> warnings,
            LoginTerminalDashboardScopeDto scope) {

        try {
            return loader.get();
        } catch (RuntimeException exception) {
            LOGGER.error(
                    "Terminal dashboard section failed. section={}, userKeyId={}, "
                            + "scopeFlid={}",
                    section,
                    scope.userKeyId(),
                    scope.scopeFlid(),
                    exception);

            warnings.add(section + " could not be loaded.");
            return fallback;
        }
    }

    private static LocalDate financialYearStart(LocalDate date) {
        int financialYear = date.getMonthValue() >= 4
                ? date.getYear()
                : date.getYear() - 1;

        return LocalDate.of(financialYear, 4, 1);
    }

    private static DmtDashboardLevelCountsResponse emptyLevelCounts(
            String flid) {

        return new DmtDashboardLevelCountsResponse(
                flid,
                "UNKNOWN",
                "",
                "",
                "",
                List.of());
    }

    private static DmtDashboardEmployeeCountResponse emptyEmployeeCount(
            String flid) {

        return new DmtDashboardEmployeeCountResponse(
                flid,
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

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
