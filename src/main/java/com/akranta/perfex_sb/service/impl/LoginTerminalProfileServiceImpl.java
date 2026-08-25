package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.LoginTerminalProfileResponseDto;
import com.akranta.perfex_sb.repository.LoginTerminalProfileRepository;
import com.akranta.perfex_sb.repository.LoginTerminalProfileRepository.LoginTerminalProfileRecord;
import com.akranta.perfex_sb.service.LoginTerminalProfileService;
import com.akranta.perfex_sb.util.TerminalTokenHashUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Cookie-backed, read-only login-page profile resolution.
 *
 * Failure is intentionally non-fatal: the ordinary login page remains
 * available even when the cookie is absent, invalid or the database lookup
 * cannot be completed.
 */
@Service
public class LoginTerminalProfileServiceImpl
        implements LoginTerminalProfileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            LoginTerminalProfileServiceImpl.class);

    private final LoginTerminalProfileRepository repository;

    public LoginTerminalProfileServiceImpl(
            LoginTerminalProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginTerminalProfileResponseDto resolveByBrowserToken(
            String rawBrowserToken) {

        String normalizedToken = clean(rawBrowserToken);

        if (normalizedToken.isBlank()) {
            return LoginTerminalProfileResponseDto.unmatched(
                    "No registered browser cookie was supplied.");
        }

        try {
            String browserTokenHash = TerminalTokenHashUtil.hashToken(
                    normalizedToken);

            Optional<LoginTerminalProfileRecord> recordOptional =
                    repository.findActiveByBrowserTokenHash(
                            browserTokenHash);

            if (recordOptional.isEmpty()) {
                LOGGER.info(
                        "No active browser registration matched the public login request.");

                return LoginTerminalProfileResponseDto.unmatched(
                        "The browser cookie does not match an active registration.");
            }

            LoginTerminalProfileRecord record = recordOptional.get();

            LOGGER.info(
                    "Public login browser context resolved. userKeyId={}, "
                            + "terminalName={}, roleId={}, roleCode={}, flid={}",
                    record.userKeyId(),
                    record.terminalName(),
                    record.roleId(),
                    record.roleCode(),
                    record.flid());

            return LoginTerminalProfileResponseDto.matched(
                    record.userKeyId(),
                    record.employeeKeyId(),
                    record.userName(),
                    record.loginId(),
                    record.terminalName(),
                    record.registeredOn(),
                    record.roleId(),
                    record.roleCode(),
                    record.roleName(),
                    record.roleLevel(),
                    record.flid(),
                    record.originalId(),
                    record.fnlnDisplayCode(),
                    record.fnlnDescription(),
                    record.parentFlids(),
                    record.allParents(),
                    record.parents(),
                    record.elementType());

        } catch (IllegalArgumentException exception) {
            LOGGER.warn(
                    "Malformed browser token was rejected while resolving the public login context.");

            return LoginTerminalProfileResponseDto.unmatched(
                    "The browser cookie is invalid.");

        } catch (DataAccessException exception) {
            LOGGER.error(
                    "Database error while resolving the public login browser context. "
                            + "The normal login page will remain available.",
                    exception);

            return LoginTerminalProfileResponseDto.unmatched(
                    "Browser context is temporarily unavailable.");
        }
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
