package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.LoginTerminalProfileResponseDto;

/**
 * Resolves public login-page context from the raw HttpOnly browser token.
 */
public interface LoginTerminalProfileService {

    LoginTerminalProfileResponseDto resolveByBrowserToken(
            String rawBrowserToken);
}
