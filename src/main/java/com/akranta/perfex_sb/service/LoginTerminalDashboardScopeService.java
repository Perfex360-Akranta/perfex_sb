package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.LoginTerminalDashboardScopeDto;

public interface LoginTerminalDashboardScopeService {

    LoginTerminalDashboardScopeDto resolveByBrowserToken(
            String rawBrowserToken);
}
