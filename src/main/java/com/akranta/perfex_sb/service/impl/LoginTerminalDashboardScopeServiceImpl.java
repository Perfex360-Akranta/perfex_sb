package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.LoginTerminalDashboardScopeDto;
import com.akranta.perfex_sb.repository.LoginTerminalDashboardScopeRepository;
import com.akranta.perfex_sb.repository.LoginTerminalDashboardScopeRepository.LoginTerminalDashboardScopeRecord;
import com.akranta.perfex_sb.service.LoginTerminalDashboardScopeService;
import com.akranta.perfex_sb.util.TerminalTokenHashUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoginTerminalDashboardScopeServiceImpl
        implements LoginTerminalDashboardScopeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            LoginTerminalDashboardScopeServiceImpl.class);

    private final LoginTerminalDashboardScopeRepository repository;

    public LoginTerminalDashboardScopeServiceImpl(
            LoginTerminalDashboardScopeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginTerminalDashboardScopeDto resolveByBrowserToken(
            String rawBrowserToken) {

        String normalizedToken = clean(rawBrowserToken);

        if (normalizedToken.isBlank()) {
            return LoginTerminalDashboardScopeDto.unavailable(
                    "No registered browser cookie was supplied.");
        }

        try {
            String tokenHash = TerminalTokenHashUtil.hashToken(
                    normalizedToken);

            Optional<LoginTerminalDashboardScopeRecord> recordOptional =
                    repository.findByBrowserTokenHash(tokenHash);

            if (recordOptional.isEmpty()) {
                return LoginTerminalDashboardScopeDto.unavailable(
                        "The browser cookie does not match an active registration.");
            }

            LoginTerminalDashboardScopeRecord record = recordOptional.get();

            boolean administrator = record.administrator();

            String scopeFlid = administrator
                    ? record.companyRootFlid()
                    : record.roleFlid();

            String scopeLevelCode = administrator
                    ? record.companyRootLevelCode()
                    : record.roleLevelCode();

            String scopeLabel = administrator
                    ? defaultIfBlank(record.companyRootLabel(), "All Locations")
                    : defaultIfBlank(
                            record.roleScopeLabel(),
                            record.roleName());

            String scopePath = administrator
                    ? record.companyRootPath()
                    : record.roleScopePath();

            if (scopeFlid.isBlank()) {
                LOGGER.warn(
                        "Registered browser has no dashboard scope. userKeyId={}, roleId={}",
                        record.userKeyId(),
                        record.roleId());

                return LoginTerminalDashboardScopeDto.unavailable(
                        administrator
                                ? "Company-root functional location is unavailable."
                                : "The current highest role has no functional-location assignment.");
            }

            String scopeType = resolveScopeType(
                    administrator,
                    scopeLevelCode,
                    record.roleCode(),
                    record.roleName());

            LOGGER.info(
                    "Terminal dashboard scope resolved. userKeyId={}, administrator={}, "
                            + "roleId={}, scopeType={}, scopeFlid={}",
                    record.userKeyId(),
                    administrator,
                    record.roleId(),
                    scopeType,
                    scopeFlid);

            return LoginTerminalDashboardScopeDto.available(
                    administrator,
                    record.userKeyId(),
                    record.employeeKeyId(),
                    record.userName(),
                    record.terminalName(),
                    record.roleId(),
                    record.roleCode(),
                    record.roleName(),
                    record.roleLevel(),
                    scopeType,
                    scopeFlid,
                    scopeLevelCode,
                    scopeLabel,
                    scopePath);

        } catch (IllegalArgumentException exception) {
            LOGGER.warn(
                    "Malformed browser token rejected while resolving terminal dashboard scope.");

            return LoginTerminalDashboardScopeDto.unavailable(
                    "The browser cookie is invalid.");

        } catch (DataAccessException exception) {
            LOGGER.error(
                    "Database error while resolving terminal dashboard scope.",
                    exception);

            return LoginTerminalDashboardScopeDto.unavailable(
                    "Terminal dashboard scope is temporarily unavailable.");
        }
    }

    private static String resolveScopeType(
            boolean administrator,
            String levelCode,
            String roleCode,
            String roleName) {

        if (administrator) {
            return "ALL_LOCATIONS";
        }

        String normalizedLevel = clean(levelCode).toUpperCase();
        String roleIdentity = (clean(roleCode) + " " + clean(roleName))
                .replace('_', ' ')
                .replace('-', ' ')
                .replaceAll("\\s+", " ")
                .trim()
                .toUpperCase();

        if ("COMP".equals(normalizedLevel)) {
            return "ALL_LOCATIONS";
        }

        if ("LOCN".equals(normalizedLevel)
                || roleIdentity.contains("PLANT HEAD")
                || roleIdentity.contains("UNIT HEAD")) {
            return "LOCATION";
        }

        if ("SECT".equals(normalizedLevel)
                || "L".equals(normalizedLevel)
                || roleIdentity.contains("DMT")) {
            return "DMT";
        }

        if ("SBU".equals(normalizedLevel)) {
            return "SBU";
        }

        if ("PBU".equals(normalizedLevel)) {
            return "PBU";
        }

        if ("CELL".equals(normalizedLevel)
                || roleIdentity.contains("JH")) {
            return "JH";
        }

        return "FUNCTIONAL_LOCATION";
    }

    private static String defaultIfBlank(
            String value,
            String fallback) {

        String cleaned = clean(value);
        return cleaned.isBlank() ? clean(fallback) : cleaned;
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
