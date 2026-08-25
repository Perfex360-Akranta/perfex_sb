package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

/**
 * Read-only state for the authenticated user's simplified browser
 * registration.
 *
 * registrationExists means that an ACTIVE registration exists for
 * the authenticated user. An inactive historical row is deliberately
 * treated as not registered.
 *
 * No raw token or token hash is returned to the browser.
 */
public record BrowserRegistrationStateDto(

        String userKeyId,

        String employeeKeyId,

        String userName,

        String loginId,

        boolean registrationExists,

        boolean currentBrowserRegistered,

        boolean eligibleForRegistration,

        String terminalName,

        LocalDateTime registeredOn,

        String roleId,

        String roleCode,

        String roleName,

        Integer roleLevel,

        String message) {
}
