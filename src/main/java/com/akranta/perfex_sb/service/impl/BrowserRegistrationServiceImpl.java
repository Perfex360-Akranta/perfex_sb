package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.BrowserRecoveryFilePayloadDto;
import com.akranta.perfex_sb.dto.BrowserRegistrationCreateRequestDto;
import com.akranta.perfex_sb.exception.TerminalRecoveryCryptoException;
import com.akranta.perfex_sb.repository.BrowserRegistrationRepository;
import com.akranta.perfex_sb.repository.BrowserRegistrationRepository.BrowserRegistrationTargetRecord;
import com.akranta.perfex_sb.service.BrowserRegistrationCreationResult;
import com.akranta.perfex_sb.service.BrowserRegistrationService;
import com.akranta.perfex_sb.service.CurrentAuthenticatedUserService;
import com.akranta.perfex_sb.service.TerminalRecoveryEncryptionService;
import com.akranta.perfex_sb.util.TerminalTokenGenerator;
import com.akranta.perfex_sb.util.TerminalTokenHashUtil;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
public class BrowserRegistrationServiceImpl
        implements BrowserRegistrationService {

    private static final int RECOVERY_PAYLOAD_VERSION = 1;

    private static final long MAXIMUM_RECOVERY_FILE_BYTES = 128L * 1024L;

    private final CurrentAuthenticatedUserService currentAuthenticatedUserService;

    private final BrowserRegistrationRepository browserRegistrationRepository;

    private final TerminalTokenGenerator terminalTokenGenerator;

    private final TerminalRecoveryEncryptionService terminalRecoveryEncryptionService;

    public BrowserRegistrationServiceImpl(
            CurrentAuthenticatedUserService currentAuthenticatedUserService,
            BrowserRegistrationRepository browserRegistrationRepository,
            TerminalTokenGenerator terminalTokenGenerator,
            TerminalRecoveryEncryptionService terminalRecoveryEncryptionService) {

        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
        this.browserRegistrationRepository = browserRegistrationRepository;
        this.terminalTokenGenerator = terminalTokenGenerator;
        this.terminalRecoveryEncryptionService = terminalRecoveryEncryptionService;
    }

    @Override
    @Transactional
    public BrowserRegistrationCreationResult registerCurrentBrowser(
            BrowserRegistrationCreateRequestDto request) {

        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Browser registration request is required.");
        }

        String terminalName = clean(request.terminalName());

        if (terminalName.length() < 3
                || terminalName.length() > 150) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Browser name must contain between 3 and 150 characters.");
        }

        /*
         * The employee key is already present in the authenticated JWT
         * details. This call does not query PostgreSQL.
         */
        String employeeKeyId = currentAuthenticatedUserService
                .getRequiredEmployeeKeyId();

        /*
         * One small database lookup resolves the active Perfex user and
         * whether that user already has an active simplified registration.
         */
        List<BrowserRegistrationTargetRecord> rows = browserRegistrationRepository
                .findRegistrationTargetByEmployeeKeyId(employeeKeyId);

        if (rows == null || rows.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "No active Perfex user is mapped to the authenticated employee.");
        }

        if (rows.size() > 1) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Multiple active Perfex users are mapped to the authenticated employee.");
        }

        BrowserRegistrationTargetRecord target = rows.get(0);

        if (target.activeRegistrationExists()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "An active browser registration already exists for the current user.");
        }

        /*
         * Browser and recovery credentials are generated independently.
         * Only their lowercase SHA-256 hashes are stored in PostgreSQL.
         */
        String rawBrowserToken = terminalTokenGenerator.generateBrowserToken();
        String rawRecoveryToken = terminalTokenGenerator.generateRecoveryToken();

        String browserTokenHash = TerminalTokenHashUtil.hashToken(
                rawBrowserToken);
        String recoveryTokenHash = TerminalTokenHashUtil.hashToken(
                rawRecoveryToken);

        BrowserRecoveryFilePayloadDto recoveryPayload = new BrowserRecoveryFilePayloadDto(
                RECOVERY_PAYLOAD_VERSION,
                target.userKeyId(),
                rawRecoveryToken,
                Instant.now().toString());

        /*
         * Encrypt before writing the row. If encryption fails, the
         * transaction has no database change to roll back.
         */
        String encryptedRecoveryFile = terminalRecoveryEncryptionService
                .encryptBrowserRecoveryToJson(recoveryPayload);

        /*
         * The conditional UPSERT performs exactly one database write:
         *
         * - insert when no row exists;
         * - reactivate when the existing row is inactive;
         * - affect zero rows if a concurrent request created an active row.
         */
        int affectedRows = browserRegistrationRepository
                .insertOrReactivateRegistration(
                        target.userKeyId(),
                        terminalName,
                        browserTokenHash,
                        recoveryTokenHash);

        if (affectedRows != 1) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "An active browser registration already exists for the current user.");
        }

        return new BrowserRegistrationCreationResult(
                rawBrowserToken,
                buildRecoveryFileName(target.userKeyId()),
                encryptedRecoveryFile);
    }


    @Override
    @Transactional
    public String recoverCurrentBrowser(
            MultipartFile recoveryFile) {

        String encryptedFileJson = readRecoveryFile(recoveryFile);

        BrowserRecoveryFilePayloadDto payload;

        try {
            payload = terminalRecoveryEncryptionService
                    .decryptBrowserRecoveryFromJson(encryptedFileJson);

        } catch (TerminalRecoveryCryptoException exception) {
            /*
             * Do not reveal whether the uploaded file contained invalid
             * JSON, altered metadata, damaged ciphertext or an unknown key.
             */
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The browser recovery file is invalid or cannot be processed.");
        }

        String employeeKeyId = currentAuthenticatedUserService
                .getRequiredEmployeeKeyId();

        String payloadUserKeyId = clean(payload.getUserKeyId());
        String rawRecoveryToken = clean(payload.getRecoveryToken());

        String recoveryTokenHash = TerminalTokenHashUtil.hashToken(
                rawRecoveryToken);

        String newRawBrowserToken = terminalTokenGenerator
                .generateBrowserToken();

        String newBrowserTokenHash = TerminalTokenHashUtil.hashToken(
                newRawBrowserToken);

        /*
         * One conditional UPDATE performs every database-side check:
         *
         * - the file user belongs to the authenticated employee;
         * - the simplified registration is active;
         * - the recovery token matches;
         * - only the browser token is rotated.
         *
         * BREG_MODIFIEDON is refreshed as an audit timestamp. The recovery
         * token hash remains unchanged, so the same backup file is reusable.
         */
        int affectedRows = browserRegistrationRepository
                .rotateBrowserTokenDuringRecovery(
                        employeeKeyId,
                        payloadUserKeyId,
                        recoveryTokenHash,
                        newBrowserTokenHash);

        if (affectedRows != 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The browser recovery file is invalid for the current user, "
                            + "or the browser registration is inactive.");
        }

        return newRawBrowserToken;
    }

    private static String readRecoveryFile(
            MultipartFile recoveryFile) {

        if (recoveryFile == null || recoveryFile.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A browser recovery file is required.");
        }

        long fileSize = recoveryFile.getSize();

        if (fileSize <= 0 || fileSize > MAXIMUM_RECOVERY_FILE_BYTES) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The browser recovery file exceeds the permitted size.");
        }

        byte[] fileBytes = null;

        try {
            fileBytes = recoveryFile.getBytes();

            return new String(
                    fileBytes,
                    StandardCharsets.UTF_8);

        } catch (IOException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The browser recovery file could not be read.");

        } finally {
            if (fileBytes != null) {
                Arrays.fill(fileBytes, (byte) 0);
            }
        }
    }

    private static String buildRecoveryFileName(
            String userKeyId) {

        String safeUserKeyId = clean(userKeyId)
                .replaceAll("[^A-Za-z0-9_-]", "_");

        return "PERFEX_BROWSER_RECOVERY_"
                + safeUserKeyId
                + ".pfxterm";
    }

    private static String clean(
            String value) {

        return value == null
                ? ""
                : value.trim();
    }
}
