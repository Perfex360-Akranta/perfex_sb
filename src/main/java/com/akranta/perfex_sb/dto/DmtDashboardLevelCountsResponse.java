package com.akranta.perfex_sb.dto;

import java.util.List;

public record DmtDashboardLevelCountsResponse(
        String flid,
        String currentLevel,
        String currentOriginalId,
        String currentElementId,
        String currentDisplayCode,
        List<DmtDashboardLevelMetricDto> metrics) {

    public record DmtDashboardLevelMetricDto(
            String id,
            String title,
            Long value,
            String subtitle,
            // String icon,
            String variant,
            Boolean visible,
            String levelCode) {
    }
}