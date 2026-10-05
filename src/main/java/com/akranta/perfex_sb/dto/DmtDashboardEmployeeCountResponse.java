package com.akranta.perfex_sb.dto;

public record DmtDashboardEmployeeCountResponse(
        String flid,
        String currentLevel,
        String currentOriginalId,
        String currentElementId,
        String currentDisplayCode,
        Long employeeCount,
        String title,
        String subtitle,
        // String icon,
        String variant,
        Boolean visible) {
}