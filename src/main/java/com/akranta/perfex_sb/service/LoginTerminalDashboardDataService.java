package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.LoginTerminalDashboardDataDto;

public interface LoginTerminalDashboardDataService {

    LoginTerminalDashboardDataDto loadByBrowserToken(
            String rawBrowserToken);
}
