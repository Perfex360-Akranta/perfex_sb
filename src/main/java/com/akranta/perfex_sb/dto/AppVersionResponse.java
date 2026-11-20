package com.akranta.perfex_sb.dto;

public record AppVersionResponse(
        String appName,
        String platform,
        String latestVersion,
        String minimumVersion,
        int latestBuildNumber,
        String installedVersion,
        boolean updateAvailable,
        boolean forceUpdate,
        boolean appActive,
        String releaseNotes,
        String apkUrl,
        String apkSha256,
        long apkSize
) {
}
