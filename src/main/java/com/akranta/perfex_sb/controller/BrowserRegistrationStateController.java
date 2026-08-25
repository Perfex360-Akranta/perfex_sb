package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.BrowserRegistrationStateDto;
import com.akranta.perfex_sb.service.BrowserRegistrationStateService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Final read-only state endpoint for simplified browser registration.
 */
@RestController
@RequestMapping("/api/terminal-registration")
public class BrowserRegistrationStateController {

    private final BrowserRegistrationStateService browserRegistrationStateService;

    public BrowserRegistrationStateController(
            BrowserRegistrationStateService browserRegistrationStateService) {

        this.browserRegistrationStateService = browserRegistrationStateService;
    }

    @GetMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BrowserRegistrationStateDto> getState(
            HttpServletRequest request) {

        BrowserRegistrationStateDto response = browserRegistrationStateService
                .getState(request);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .header(HttpHeaders.PRAGMA, "no-cache")
                .body(response);
    }
}
