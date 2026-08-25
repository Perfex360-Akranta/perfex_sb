package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

/**
 * Public login-page context resolved from the HttpOnly browser-registration
 * cookie.
 *
 * This response is presentation context only. Authentication and application
 * authorization must still use the normal authenticated session/JWT flow.
 */
public record LoginTerminalProfileResponseDto(
        boolean matched,
        String userKeyId,
        String employeeKeyId,
        String userName,
        String loginId,
        String terminalName,
        LocalDateTime registeredOn,
        boolean roleResolved,
        String roleId,
        String roleCode,
        String roleName,
        Integer roleLevel,
        String flid,
        String originalId,
        String fnlnDisplayCode,
        String fnlnDescription,
        String parentFlids,
        String allParents,
        String parents,
        String elementType,
        boolean idleDisplayEnabled,
        int idleTimeoutSeconds,
        int slideIntervalSeconds,
        String message) {

    private static final int DEFAULT_IDLE_TIMEOUT_SECONDS = 30;
    private static final int DEFAULT_SLIDE_INTERVAL_SECONDS = 15;

    public static LoginTerminalProfileResponseDto unmatched(String message) {
        return new LoginTerminalProfileResponseDto(
                false,
                "",
                "",
                "",
                "",
                "",
                null,
                false,
                "",
                "",
                "",
                null,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                false,
                DEFAULT_IDLE_TIMEOUT_SECONDS,
                DEFAULT_SLIDE_INTERVAL_SECONDS,
                clean(message));
    }

    public static LoginTerminalProfileResponseDto matched(
            String userKeyId,
            String employeeKeyId,
            String userName,
            String loginId,
            String terminalName,
            LocalDateTime registeredOn,
            String roleId,
            String roleCode,
            String roleName,
            Integer roleLevel,
            String flid,
            String originalId,
            String fnlnDisplayCode,
            String fnlnDescription,
            String parentFlids,
            String allParents,
            String parents,
            String elementType) {

        boolean roleResolved = !clean(roleId).isBlank();

        return new LoginTerminalProfileResponseDto(
                true,
                clean(userKeyId),
                clean(employeeKeyId),
                clean(userName),
                clean(loginId),
                clean(terminalName),
                registeredOn,
                roleResolved,
                clean(roleId),
                clean(roleCode),
                clean(roleName),
                roleLevel,
                clean(flid),
                clean(originalId),
                clean(fnlnDisplayCode),
                clean(fnlnDescription),
                clean(parentFlids),
                clean(allParents),
                clean(parents),
                clean(elementType),
                roleResolved,
                DEFAULT_IDLE_TIMEOUT_SECONDS,
                DEFAULT_SLIDE_INTERVAL_SECONDS,
                roleResolved
                        ? "Registered browser and current highest role resolved."
                        : "Registered browser resolved, but no active role is available.");
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
