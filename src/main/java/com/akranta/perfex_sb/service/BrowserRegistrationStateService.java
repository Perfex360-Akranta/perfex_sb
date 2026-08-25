package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.BrowserRegistrationStateDto;

import jakarta.servlet.http.HttpServletRequest;

public interface BrowserRegistrationStateService {

    BrowserRegistrationStateDto getState(
            HttpServletRequest request);
}
