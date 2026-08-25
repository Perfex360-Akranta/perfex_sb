package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.BrowserRegistrationCreateRequestDto;

import org.springframework.web.multipart.MultipartFile;

public interface BrowserRegistrationService {

    BrowserRegistrationCreationResult registerCurrentBrowser(
            BrowserRegistrationCreateRequestDto request);

    /**
     * Validates a reusable encrypted recovery file and rotates only the
     * current browser credential.
     *
     * @return newly generated raw browser token for the HttpOnly cookie
     */
    String recoverCurrentBrowser(
            MultipartFile recoveryFile);
}
