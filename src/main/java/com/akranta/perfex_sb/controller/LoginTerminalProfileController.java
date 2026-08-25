package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.LoginTerminalProfileResponseDto;
import com.akranta.perfex_sb.service.LoginTerminalProfileService;
import com.akranta.perfex_sb.service.TerminalBrowserCookieService;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Public login-page endpoint.
 *
 * The HttpOnly PERFEX terminal cookie is read by the server and never exposed
 * to Angular. The endpoint performs a read-only browser-registration lookup;
 * it does not inspect the client IP and does not write a last-seen timestamp.
 */
@RestController
@RequestMapping("/api/auth")
public class LoginTerminalProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            LoginTerminalProfileController.class);

    private final TerminalBrowserCookieService browserCookieService;
    private final LoginTerminalProfileService loginTerminalProfileService;

    public LoginTerminalProfileController(
            TerminalBrowserCookieService browserCookieService,
            LoginTerminalProfileService loginTerminalProfileService) {

        this.browserCookieService = browserCookieService;
        this.loginTerminalProfileService = loginTerminalProfileService;
    }

    @GetMapping(
            value = "/login-terminal-profile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginTerminalProfileResponseDto> getLoginTerminalProfile(
            HttpServletRequest request) {

        String rawBrowserToken = browserCookieService
                .readBrowserToken(request)
                .orElse("");

        LoginTerminalProfileResponseDto response =
                loginTerminalProfileService.resolveByBrowserToken(
                        rawBrowserToken);

        LOGGER.info(
                "Public login profile response prepared. matched={}, "
                        + "terminalName={}, roleId={}, roleCode={}, roleLevel={}, flid={}",
                response.matched(),
                response.terminalName(),
                response.roleId(),
                response.roleCode(),
                response.roleLevel(),
                response.flid());

        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .header(HttpHeaders.PRAGMA, "no-cache")
                .body(response);
    }
}
