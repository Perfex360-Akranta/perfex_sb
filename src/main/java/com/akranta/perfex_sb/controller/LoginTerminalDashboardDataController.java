package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.LoginTerminalDashboardDataDto;
import com.akranta.perfex_sb.service.LoginTerminalDashboardDataService;
import com.akranta.perfex_sb.service.TerminalBrowserCookieService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cookie-scoped dashboard-data endpoint for the public login page.
 *
 * There is intentionally no FLID request parameter. The permitted hierarchy
 * root is resolved from the HttpOnly browser-registration cookie.
 */
@RestController
@RequestMapping("/api/auth/login-terminal-dashboard")
public class LoginTerminalDashboardDataController {

    private final TerminalBrowserCookieService browserCookieService;
    private final LoginTerminalDashboardDataService dashboardDataService;

    public LoginTerminalDashboardDataController(
            TerminalBrowserCookieService browserCookieService,
            LoginTerminalDashboardDataService dashboardDataService) {

        this.browserCookieService = browserCookieService;
        this.dashboardDataService = dashboardDataService;
    }

    @GetMapping(
            value = "/data",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginTerminalDashboardDataDto> getDashboardData(
            HttpServletRequest request) {

        String rawBrowserToken = browserCookieService
                .readBrowserToken(request)
                .orElse("");

        LoginTerminalDashboardDataDto response =
                dashboardDataService.loadByBrowserToken(rawBrowserToken);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .header(HttpHeaders.PRAGMA, "no-cache")
                .body(response);
    }
}
