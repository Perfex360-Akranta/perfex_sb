package com.akranta.perfex_sb.dto;

public record AppVersionReleaseRequest(
        String appName,
        String platform,
        String currentVersion,
        String minVersion,
        Integer buildNumber,
        String forceUpdate,
        String active,
        String releaseNotes,
        String createdBy
) {
}
