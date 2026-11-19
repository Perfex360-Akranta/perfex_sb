package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.AppVersionResponse;
import com.akranta.perfex_sb.dto.AppVersionReleaseRequest;
import com.akranta.perfex_sb.service.AppVersionService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;

@Service
public class AppVersionServiceImpl implements AppVersionService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Value("${app.release.storage}")
    private String storagePath;

    @Value("${app.release.base-url}")
    private String baseUrl;

    public AppVersionServiceImpl(
            NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_SQL = """
        SELECT
            avm_appname,
            avm_platform,
            avm_currentversion,
            avm_minversion,
            avm_buildnumber,
            avm_forceupdate,
            avm_active,
            avm_apkfilename,
            avm_apkpath,
            avm_apksha256,
            avm_apksize,
            avm_releasenotes
        FROM public.gen_tl_appversionmst
        WHERE avm_appname = :appName
          AND avm_platform = :platform
        """;

    @Override
    public AppVersionResponse checkVersion(
            String appName,
            String platform,
            String installedVersion,
            int installedBuildNumber) {

        Map<String, Object> params = Map.of(
                "appName", appName,
                "platform", platform
        );

        List<Map<String, Object>> rows =
                jdbcTemplate.queryForList(SELECT_SQL, params);

        if (rows.isEmpty()) {
            throw new IllegalArgumentException(
                    "App version configuration not found");
        }

        Map<String, Object> row = rows.get(0);

        String latestVersion =
                row.get("avm_currentversion").toString();

        String minimumVersion =
                row.get("avm_minversion").toString();

        int latestBuild =
                ((Number) row.get("avm_buildnumber")).intValue();

        boolean appActive =
                "Y".equalsIgnoreCase(
                        row.get("avm_active").toString());

        boolean updateAvailable =
                compareVersions(installedVersion, latestVersion) < 0
                || (compareVersions(installedVersion, latestVersion) == 0
                    && installedBuildNumber < latestBuild);

        boolean belowMinimum =
                compareVersions(installedVersion, minimumVersion) < 0;

        boolean forceConfigured =
                "Y".equalsIgnoreCase(
                        row.get("avm_forceupdate").toString());

        boolean forceUpdate =
                belowMinimum || (forceConfigured && updateAvailable);

        String apkUrl = baseUrl
                + "/api/auth/app-version/download?appName="
                + appName
                + "&platform="
                + platform;

        return new AppVersionResponse(
                appName,
                platform,
                latestVersion,
                minimumVersion,
                latestBuild,
                installedVersion,
                updateAvailable,
                forceUpdate,
                appActive,
                Objects.toString(row.get("avm_releasenotes"), ""),
                apkUrl,
                Objects.toString(row.get("avm_apksha256"), ""),
                ((Number) row.get("avm_apksize")).longValue()
        );
    }

    @Override
    @Transactional
    public String uploadRelease(
            AppVersionReleaseRequest request,
            MultipartFile file) {

        validateRequest(request, file);

        String appName = request.appName().trim();
        String platform = request.platform().toUpperCase();

        String fileName = appName
                + "-"
                + request.currentVersion()
                + "-"
                + request.buildNumber()
                + ".apk";

        Path directory = Paths.get(storagePath)
                .toAbsolutePath()
                .normalize();

        Path destination = directory.resolve(fileName).normalize();

        if (!destination.startsWith(directory)) {
            throw new IllegalArgumentException("Invalid APK path");
        }

        try {
            Files.createDirectories(directory);

            Files.copy(
                    file.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            String sha256 = calculateSha256(destination);

            String keyId = "AVM"
                    + UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 12)
                        .toUpperCase();

            MapSqlParameterSource params =
                    new MapSqlParameterSource();

            params.addValue("keyId", keyId);
            params.addValue("appName", appName);
            params.addValue("platform", platform);
            params.addValue("version", request.currentVersion());
            params.addValue("minVersion", request.minVersion());
            params.addValue("buildNumber", request.buildNumber());
            params.addValue("forceUpdate", request.forceUpdate());
            params.addValue("active", request.active());
            params.addValue("fileName", fileName);
            params.addValue("apkPath", destination.toString());
            params.addValue("sha256", sha256);
            params.addValue("apkSize", Files.size(destination));
            params.addValue("releaseNotes", request.releaseNotes());
            params.addValue("createdBy", request.createdBy());

            String sql = """
                INSERT INTO public.gen_tl_appversionmst
                (
                    avm_keyid,
                    avm_appname,
                    avm_platform,
                    avm_currentversion,
                    avm_minversion,
                    avm_buildnumber,
                    avm_forceupdate,
                    avm_active,
                    avm_apkfilename,
                    avm_apkpath,
                    avm_apksha256,
                    avm_apksize,
                    avm_releasenotes,
                    avm_createdby,
                    avm_createddate
                )
                VALUES
                (
                    :keyId,
                    :appName,
                    :platform,
                    :version,
                    :minVersion,
                    :buildNumber,
                    :forceUpdate,
                    :active,
                    :fileName,
                    :apkPath,
                    :sha256,
                    :apkSize,
                    :releaseNotes,
                    :createdBy,
                    CURRENT_TIMESTAMP
                )
                ON CONFLICT (avm_appname, avm_platform)
                DO UPDATE SET
                    avm_currentversion = EXCLUDED.avm_currentversion,
                    avm_minversion = EXCLUDED.avm_minversion,
                    avm_buildnumber = EXCLUDED.avm_buildnumber,
                    avm_forceupdate = EXCLUDED.avm_forceupdate,
                    avm_active = EXCLUDED.avm_active,
                    avm_apkfilename = EXCLUDED.avm_apkfilename,
                    avm_apkpath = EXCLUDED.avm_apkpath,
                    avm_apksha256 = EXCLUDED.avm_apksha256,
                    avm_apksize = EXCLUDED.avm_apksize,
                    avm_releasenotes = EXCLUDED.avm_releasenotes,
                    avm_modifiedby = EXCLUDED.avm_createdby,
                    avm_modifieddate = CURRENT_TIMESTAMP
                """;

            jdbcTemplate.update(sql, params);

            return "Release " + request.currentVersion()
                    + " uploaded successfully";

        } catch (Exception ex) {
            throw new RuntimeException(
                    "APK upload failed: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Resource downloadLatestApk(
            String appName,
            String platform) {

        Map<String, Object> params = Map.of(
                "appName", appName,
                "platform", platform
        );

        List<Map<String, Object>> rows =
                jdbcTemplate.queryForList(SELECT_SQL, params);

        if (rows.isEmpty()) {
            throw new IllegalArgumentException(
                    "Release not found");
        }

        Map<String, Object> row = rows.get(0);

        if (!"Y".equalsIgnoreCase(
                row.get("avm_active").toString())) {
            throw new IllegalStateException("App is inactive");
        }

        String apkPath = row.get("avm_apkpath").toString();

        Path directory = Paths.get(storagePath)
                .toAbsolutePath()
                .normalize();

        Path file = Paths.get(apkPath)
                .toAbsolutePath()
                .normalize();

        if (!file.startsWith(directory)) {
            throw new IllegalArgumentException("Invalid APK path");
        }

        Resource resource = new FileSystemResource(file);

        if (!resource.exists() || !resource.isReadable()) {
            throw new IllegalArgumentException(
                    "APK file not found");
        }

        return resource;
    }

    private void validateRequest(
            AppVersionReleaseRequest request,
            MultipartFile file) {

        if (request == null || file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Release details and APK file are required");
        }

        if (!"ANDROID".equalsIgnoreCase(request.platform())) {
            throw new IllegalArgumentException(
                    "Only ANDROID platform is supported");
        }

        if (!file.getOriginalFilename()
                .toLowerCase()
                .endsWith(".apk")) {
            throw new IllegalArgumentException(
                    "Only APK files are allowed");
        }

        if (request.buildNumber() == null
                || request.buildNumber() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid build number");
        }

        if (!Set.of("Y", "N").contains(
                request.forceUpdate().toUpperCase())) {
            throw new IllegalArgumentException(
                    "forceUpdate must be Y or N");
        }

        if (!Set.of("Y", "N").contains(
                request.active().toUpperCase())) {
            throw new IllegalArgumentException(
                    "active must be Y or N");
        }

        if (compareVersions(
                request.minVersion(),
                request.currentVersion()) > 0) {
            throw new IllegalArgumentException(
                    "Minimum version cannot exceed current version");
        }
    }

    private int compareVersions(String v1, String v2) {

        String[] first = v1.split("\\.");
        String[] second = v2.split("\\.");

        int length = Math.max(first.length, second.length);

        for (int i = 0; i < length; i++) {

            int a = i < first.length
                    ? Integer.parseInt(first[i]) : 0;

            int b = i < second.length
                    ? Integer.parseInt(second[i]) : 0;

            if (a != b) {
                return Integer.compare(a, b);
            }
        }

        return 0;
    }

    private String calculateSha256(Path file)
            throws Exception {

        MessageDigest digest =
                MessageDigest.getInstance("SHA-256");

        byte[] bytes = Files.readAllBytes(file);

        byte[] hash = digest.digest(bytes);

        StringBuilder result = new StringBuilder();

        for (byte b : hash) {
            result.append(String.format("%02x", b));
        }

        return result.toString();
    }
}

