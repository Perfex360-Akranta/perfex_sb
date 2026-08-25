package com.akranta.perfex_sb.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Central configuration contract for the Perfex terminal-browser cookie.
 *
 * The cookie contains only the raw random browser token.
 *
 * It must never contain:
 * - USRM_KEYID
 * - EMPM_KEYID
 * - terminal code
 * - role
 * - password
 * - JWT
 */
@Component
@ConfigurationProperties(prefix = "perfex.terminal.cookie")
public class TerminalBrowserCookieProperties
        implements InitializingBean {

    private static final Pattern COOKIE_NAME_PATTERN = Pattern.compile(
            "^[A-Za-z0-9_-]{1,80}$");

    private static final int MINIMUM_MAX_AGE_DAYS = 1;

    private static final int MAXIMUM_MAX_AGE_DAYS = 3650;

    /*
     * Development default.
     *
     * Production can use:
     * __Host-PERFEX-TERMINAL
     */
    private String name = "PERFEX_TERMINAL";

    /*
     * Lax is appropriate when Angular and Spring Boot are served
     * from the same site.
     */
    private String sameSite = "Lax";

    /*
     * false:
     * local HTTP development
     *
     * true:
     * production HTTPS
     */
    private boolean secure = false;

    /*
     * One year by default.
     */
    private int maxAgeDays = 365;

    @Override
    public void afterPropertiesSet() {
        validateAndNormalize();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = clean(name);
    }

    public String getSameSite() {
        return sameSite;
    }

    public void setSameSite(String sameSite) {
        this.sameSite = clean(sameSite);
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public int getMaxAgeDays() {
        return maxAgeDays;
    }

    public void setMaxAgeDays(int maxAgeDays) {
        this.maxAgeDays = maxAgeDays;
    }

    /**
     * All terminal cookies are created for the entire application.
     */
    public String getPath() {
        return "/";
    }

    /**
     * The cookie is intentionally always HttpOnly.
     *
     * This is not externally configurable because Angular must never
     * read the browser credential.
     */
    public boolean isHttpOnly() {
        return true;
    }

    public Duration getMaxAge() {
        return Duration.ofDays(maxAgeDays);
    }

    /**
     * Public to allow focused unit testing without starting
     * the Spring context.
     */
    public void validateAndNormalize() {

        name = clean(name);

        if (name.isBlank()
                || !COOKIE_NAME_PATTERN
                        .matcher(name)
                        .matches()) {
            throw new IllegalStateException(
                    "perfex.terminal.cookie.name must contain only "
                            + "letters, numbers, underscores or hyphens.");
        }

        sameSite = normalizeSameSite(sameSite);

        if ("None".equals(sameSite)
                && !secure) {
            throw new IllegalStateException(
                    "SameSite=None requires "
                            + "perfex.terminal.cookie.secure=true.");
        }

        if (name.startsWith("__Host-")
                && !secure) {
            throw new IllegalStateException(
                    "A __Host- terminal cookie requires "
                            + "perfex.terminal.cookie.secure=true.");
        }

        if (name.startsWith("__Secure-")
                && !secure) {
            throw new IllegalStateException(
                    "A __Secure- terminal cookie requires "
                            + "perfex.terminal.cookie.secure=true.");
        }

        if (maxAgeDays < MINIMUM_MAX_AGE_DAYS
                || maxAgeDays > MAXIMUM_MAX_AGE_DAYS) {
            throw new IllegalStateException(
                    "perfex.terminal.cookie.max-age-days must be "
                            + "between "
                            + MINIMUM_MAX_AGE_DAYS
                            + " and "
                            + MAXIMUM_MAX_AGE_DAYS
                            + ".");
        }
    }

    private static String normalizeSameSite(
            String value) {

        String normalized = clean(value)
                .toLowerCase(Locale.ROOT);

        return switch (normalized) {

            case "lax" ->
                "Lax";

            case "strict" ->
                "Strict";

            case "none" ->
                "None";

            default ->
                throw new IllegalStateException(
                        "perfex.terminal.cookie.same-site must be "
                                + "Lax, Strict or None.");
        };
    }

    private static String clean(String value) {

        return value == null
                ? ""
                : value.trim();
    }
}