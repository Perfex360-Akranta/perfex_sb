package com.akranta.perfex_sb.service;

/**
 * Internal result of a successful simplified browser registration.
 *
 * rawBrowserToken is security-sensitive. It must never be logged,
 * serialized into the response body, or stored directly in PostgreSQL.
 */
public record BrowserRegistrationCreationResult(

        String rawBrowserToken,

        String recoveryFileName,

        String recoveryFileContent) {
}
