package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.BrowserRegistrationCreateRequestDto;
import com.akranta.perfex_sb.service.BrowserRegistrationCreationResult;
import com.akranta.perfex_sb.service.BrowserRegistrationService;
import com.akranta.perfex_sb.service.TerminalBrowserCookieService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

/**
 * Final command controller for the simplified browser-registration flow.
 *
 * Endpoints:
 * POST /api/terminal-registration/register
 * POST /api/terminal-registration/recover
 */
@RestController
@RequestMapping("/api/terminal-registration")
public class BrowserRegistrationCommandController {

    private static final MediaType RECOVERY_FILE_MEDIA_TYPE = MediaType.parseMediaType(
            "application/vnd.perfex.terminal-recovery+json");

    private final BrowserRegistrationService browserRegistrationService;
    private final TerminalBrowserCookieService terminalBrowserCookieService;

    public BrowserRegistrationCommandController(
            BrowserRegistrationService browserRegistrationService,
            TerminalBrowserCookieService terminalBrowserCookieService) {

        this.browserRegistrationService = browserRegistrationService;
        this.terminalBrowserCookieService = terminalBrowserCookieService;
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> registerCurrentBrowser(
            @Valid @RequestBody BrowserRegistrationCreateRequestDto request,
            HttpServletResponse servletResponse) {

        BrowserRegistrationCreationResult result = browserRegistrationService
                .registerCurrentBrowser(request);

        /*
         * The raw browser token leaves the server only through the
         * configured HttpOnly cookie.
         */
        terminalBrowserCookieService.writeBrowserCookie(
                servletResponse,
                result.rawBrowserToken());

        byte[] recoveryFileBytes = result.recoveryFileContent()
                .getBytes(StandardCharsets.UTF_8);

        String contentDisposition = ContentDisposition
                .attachment()
                .filename(
                        result.recoveryFileName(),
                        StandardCharsets.UTF_8)
                .build()
                .toString();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(RECOVERY_FILE_MEDIA_TYPE)
                .contentLength(recoveryFileBytes.length)
                .cacheControl(CacheControl.noStore())
                .header(HttpHeaders.PRAGMA, "no-cache")
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(recoveryFileBytes);
    }

    @PostMapping(
            value = "/recover",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> recoverCurrentBrowser(
            @RequestParam("file") MultipartFile recoveryFile,
            HttpServletResponse servletResponse) {

        String newRawBrowserToken = browserRegistrationService
                .recoverCurrentBrowser(recoveryFile);

        terminalBrowserCookieService.writeBrowserCookie(
                servletResponse,
                newRawBrowserToken);

        return ResponseEntity
                .noContent()
                .cacheControl(CacheControl.noStore())
                .header(HttpHeaders.PRAGMA, "no-cache")
                .build();
    }
}
