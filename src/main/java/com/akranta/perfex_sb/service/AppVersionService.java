package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.AppVersionResponse;
import com.akranta.perfex_sb.dto.AppVersionReleaseRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface AppVersionService {

    AppVersionResponse checkVersion(
            String appName,
            String platform,
            String installedVersion,
            int installedBuildNumber
    );

    String uploadRelease(
            AppVersionReleaseRequest request,
            MultipartFile file
    );

    Resource downloadLatestApk(
            String appName,
            String platform
    );
}