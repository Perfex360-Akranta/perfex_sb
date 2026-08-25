package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.BrowserRegistrationStateDto;
import com.akranta.perfex_sb.repository.BrowserRegistrationRepository;
import com.akranta.perfex_sb.repository.BrowserRegistrationRepository.BrowserRegistrationStateRecord;
import com.akranta.perfex_sb.service.BrowserRegistrationStateService;
import com.akranta.perfex_sb.service.CurrentAuthenticatedUserService;
import com.akranta.perfex_sb.service.TerminalBrowserCookieService;
import com.akranta.perfex_sb.util.TerminalTokenHashUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BrowserRegistrationStateServiceImpl
        implements BrowserRegistrationStateService {

    private final CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private final TerminalBrowserCookieService terminalBrowserCookieService;

    private final BrowserRegistrationRepository browserRegistrationRepository;

    public BrowserRegistrationStateServiceImpl(
            CurrentAuthenticatedUserService currentAuthenticatedUserService,
            TerminalBrowserCookieService terminalBrowserCookieService,
            BrowserRegistrationRepository browserRegistrationRepository) {

        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.terminalBrowserCookieService = terminalBrowserCookieService;
        this.browserRegistrationRepository = browserRegistrationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public BrowserRegistrationStateDto getState(
            HttpServletRequest request) {

        /*
         * Important performance decision:
         *
         * Do not call getRequiredCurrentUser() here. That method performs
         * a separate JPA query.
         *
         * The employee key is already available in the authenticated JWT
         * details. The repository uses it to resolve the active user and
         * every other part of the state in one JDBC query.
         */
        String employeeKeyId = currentAuthenticatedUserService
                .getRequiredEmployeeKeyId();

        String browserTokenHash = terminalBrowserCookieService
                .readBrowserToken(request)
                .map(TerminalTokenHashUtil::hashToken)
                .orElse(null);

        List<BrowserRegistrationStateRecord> rows = browserRegistrationRepository
                .findStateByEmployeeKeyId(
                        employeeKeyId,
                        browserTokenHash);

        if (rows == null || rows.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "No active Perfex user is mapped to the authenticated employee.");
        }

        if (rows.size() > 1) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Multiple active Perfex users are mapped to the authenticated employee.");
        }

        BrowserRegistrationStateRecord state = rows.get(0);

        boolean eligibleForRegistration = !state.registrationExists();

        String message = buildMessage(state);

        return new BrowserRegistrationStateDto(
                state.userKeyId(),
                state.employeeKeyId(),
                state.userName(),
                state.loginId(),
                state.registrationExists(),
                state.currentBrowserRegistered(),
                eligibleForRegistration,
                state.terminalName(),
                state.registeredOn(),
                state.roleId(),
                state.roleCode(),
                state.roleName(),
                state.roleLevel(),
                message);
    }

    private static String buildMessage(
            BrowserRegistrationStateRecord state) {

        if (state.currentBrowserRegistered()) {
            return "This browser is registered for the current user.";
        }

        if (state.registrationExists()) {
            return "An active browser registration already exists for "
                    + "the current user. Use the recovery file to register "
                    + "this browser.";
        }

        return "No active browser registration exists for the current user.";
    }
}
