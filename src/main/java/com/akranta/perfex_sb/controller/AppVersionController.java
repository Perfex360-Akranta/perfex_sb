package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.AppVersionResponse;
import com.akranta.perfex_sb.dto.AppVersionReleaseRequest;
import com.akranta.perfex_sb.service.AppVersionService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/auth/app-version")
public class AppVersionController {

    private final AppVersionService appVersionService;

    public AppVersionController(
            AppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    // CHECK APP VERSION
    @GetMapping("/check")
    public ResponseEntity<AppVersionResponse> checkVersion(
            @RequestParam String appName,
            @RequestParam String platform,
            @RequestParam String version,
            @RequestParam int buildNumber) {

        AppVersionResponse response =
                appVersionService.checkVersion(
                        appName,
                        platform,
                        version,
                        buildNumber
                );

        return ResponseEntity.ok(response);
    }

    // UPLOAD NEW APK RELEASE
    // @PostMapping(
    //     value = "/release",
    //     consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    // )
    // public ResponseEntity<String> uploadRelease(
    //         @RequestPart("release") AppVersionReleaseRequest request,
    //         @RequestPart("file") MultipartFile file) {

    //     String result =
    //             appVersionService.uploadRelease(request, file);

    //     return ResponseEntity.ok(result);
    // }
//     @PostMapping(
//     value = "/release",
//     consumes = MediaType.MULTIPART_FORM_DATA_VALUE
// )
// public ResponseEntity<String> uploadRelease(

//         @RequestParam("appName") String appName,
//         @RequestParam("platform") String platform,
//         @RequestParam("currentVersion") String currentVersion,
//         @RequestParam("minVersion") String minVersion,
//         @RequestParam("buildNumber") Integer buildNumber,
//         @RequestParam("forceUpdate") String forceUpdate,
//         @RequestParam("active") String active,
//         @RequestParam("releaseNotes") String releaseNotes,
//         @RequestParam("createdBy") String createdBy,

//         @RequestPart("file") MultipartFile file
// ) {

//     AppVersionReleaseRequest request =
//         new AppVersionReleaseRequest(
//             appName,
//             platform,
//             currentVersion,
//             minVersion,
//             buildNumber,
//             forceUpdate,
//             active,
//             releaseNotes,
//             createdBy
//         );

//     String result =
//         appVersionService.uploadRelease(request, file);

//     return ResponseEntity.ok(result);
// }

@PostMapping(
    value = "/release",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
)
public ResponseEntity<String> uploadRelease(

        @RequestParam String appName,
        @RequestParam String platform,
        @RequestParam String currentVersion,
        @RequestParam String minVersion,
        @RequestParam Integer buildNumber,
        @RequestParam String forceUpdate,
        @RequestParam String active,
        @RequestParam(required = false) String releaseNotes,
        @RequestParam String createdBy,

        @RequestPart("file") MultipartFile file
) {

    AppVersionReleaseRequest request =
        new AppVersionReleaseRequest(
            appName,
            platform,
            currentVersion,
            minVersion,
            buildNumber,
            forceUpdate,
            active,
            releaseNotes,
            createdBy
        );

    String result =
        appVersionService.uploadRelease(request, file);

    return ResponseEntity.ok(result);
}

    // DOWNLOAD LATEST APK
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadApk(
            @RequestParam String appName,
            @RequestParam String platform) {

        Resource resource =
                appVersionService.downloadLatestApk(
                        appName,
                        platform
                );

        return ResponseEntity.ok()
                .contentType(
                    MediaType.parseMediaType(
                        "application/vnd.android.package-archive"))
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"Perfex360.apk\"")
                .body(resource);
    }
}

