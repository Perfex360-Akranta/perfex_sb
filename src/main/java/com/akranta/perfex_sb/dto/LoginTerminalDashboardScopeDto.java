package com.akranta.perfex_sb.dto;

/**
 * Server-resolved dashboard scope for a registered public-login browser.
 *
 * Angular must use scopeFlid only as display/context information. Future
 * terminal-dashboard data endpoints will resolve this scope again from the
 * HttpOnly cookie and will not trust a client-supplied FLID.
 */
public record LoginTerminalDashboardScopeDto(
        boolean available,
        boolean administrator,
        String userKeyId,
        String employeeKeyId,
        String userName,
        String terminalName,
        String roleId,
        String roleCode,
        String roleName,
        Integer roleLevel,
        String scopeType,
        String scopeFlid,
        String scopeLevelCode,
        String scopeLabel,
        String scopePath,
        String message) {

    public static LoginTerminalDashboardScopeDto unavailable(String message) {
        return new LoginTerminalDashboardScopeDto(
                false,
                false,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                null,
                "",
                "",
                "",
                "",
                "",
                clean(message));
    }

    public static LoginTerminalDashboardScopeDto available(
            boolean administrator,
            String userKeyId,
            String employeeKeyId,
            String userName,
            String terminalName,
            String roleId,
            String roleCode,
            String roleName,
            Integer roleLevel,
            String scopeType,
            String scopeFlid,
            String scopeLevelCode,
            String scopeLabel,
            String scopePath) {

        return new LoginTerminalDashboardScopeDto(
                true,
                administrator,
                clean(userKeyId),
                clean(employeeKeyId),
                clean(userName),
                clean(terminalName),
                clean(roleId),
                clean(roleCode),
                clean(roleName),
                roleLevel,
                clean(scopeType),
                clean(scopeFlid),
                clean(scopeLevelCode),
                clean(scopeLabel),
                clean(scopePath),
                buildMessage(administrator, scopeType));
    }

    private static String buildMessage(
            boolean administrator,
            String scopeType) {

        if (administrator) {
            return "System-administrator terminal scope resolved to all locations.";
        }

        return switch (clean(scopeType)) {
            case "LOCATION" -> "Plant-level terminal dashboard scope resolved.";
            case "DMT" -> "DMT-level terminal dashboard scope resolved.";
            default -> "Functional-location terminal dashboard scope resolved.";
        };
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
