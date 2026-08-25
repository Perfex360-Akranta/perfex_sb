package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.BrowserRecoveryFilePayloadDto;
import com.akranta.perfex_sb.dto.TerminalRecoveryFileEnvelopeDto;

import com.akranta.perfex_sb.exception.TerminalRecoveryCryptoException;
import com.akranta.perfex_sb.security.TerminalRecoveryKeyProvider;
import com.fasterxml.jackson.core.JacksonException;
// import tools.jackson.core.JacksonException;
// import tools.jackson.databind.DeserializationFeature;
// import tools.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.springframework.stereotype.Service;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Pattern;

@Service
public class TerminalRecoveryEncryptionService {

    private static final String FILE_TYPE = "PERFEX_TERMINAL_RECOVERY";

    private static final int FILE_VERSION = 1;

    private static final int PAYLOAD_VERSION = 1;

    private static final String DISPLAY_ALGORITHM = "AES-256-GCM";

    private static final String CIPHER_TRANSFORMATION = "AES/GCM/NoPadding";

    private static final int GCM_IV_BYTE_LENGTH = 12;

    private static final int GCM_TAG_BIT_LENGTH = 128;

    private static final int MINIMUM_CIPHERTEXT_LENGTH = 16;

    private static final Pattern RECOVERY_TOKEN_PATTERN = Pattern.compile(
            "^[A-Za-z0-9_-]{43}$");

    private static final Base64.Encoder BASE64_URL_ENCODER = Base64
            .getUrlEncoder()
            .withoutPadding();

    private static final Base64.Decoder BASE64_URL_DECODER = Base64
            .getUrlDecoder();

    private final TerminalRecoveryKeyProvider keyProvider;

    // private final JsonMapper jsonMapper;
    private final ObjectMapper jsonMapper;

    private final SecureRandom secureRandom;

    public TerminalRecoveryEncryptionService(
            TerminalRecoveryKeyProvider keyProvider,
            ObjectMapper jsonMapper) {

        this.keyProvider = keyProvider;
        this.jsonMapper = jsonMapper;
        this.secureRandom = new SecureRandom();
    }

    /*
     * -----------------------------------------------------------------
     * Existing legacy terminal payload API.
     * These methods remain available so the old implementation continues
     * to compile and behave as before during migration.
     * -----------------------------------------------------------------
     */

    /*
     * -----------------------------------------------------------------
     * Simplified one-table browser-recovery payload API.
     * -----------------------------------------------------------------
     */

    public String encryptBrowserRecoveryToJson(
            BrowserRecoveryFilePayloadDto payload) {

        validateBrowserPayload(payload);

        TerminalRecoveryFileEnvelopeDto envelope = encryptPayload(
                payload,
                BrowserRecoveryFilePayloadDto.class);

        return serializeEnvelope(envelope);
    }

    public BrowserRecoveryFilePayloadDto decryptBrowserRecoveryFromJson(
            String encryptedFileJson) {

        BrowserRecoveryFilePayloadDto payload = decryptPayload(
                parseEnvelope(encryptedFileJson),
                BrowserRecoveryFilePayloadDto.class);

        validateBrowserPayload(payload);

        return payload;
    }

    private <T> TerminalRecoveryFileEnvelopeDto encryptPayload(
            T payload,
            Class<T> payloadType) {

        String activeKeyId = keyProvider.getActiveKeyId();
        SecretKey activeKey = keyProvider.getActiveKey();

        byte[] iv = new byte[GCM_IV_BYTE_LENGTH];
        secureRandom.nextBytes(iv);

        byte[] plaintext = null;

        try {
            plaintext = jsonMapper
                    .writerFor(payloadType)
                    .writeValueAsBytes(payload);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    activeKey,
                    new GCMParameterSpec(
                            GCM_TAG_BIT_LENGTH,
                            iv));

            cipher.updateAAD(
                    buildAdditionalAuthenticatedData(
                            FILE_TYPE,
                            FILE_VERSION,
                            DISPLAY_ALGORITHM,
                            activeKeyId));

            byte[] encryptedPayload = cipher.doFinal(plaintext);

            return new TerminalRecoveryFileEnvelopeDto(
                    FILE_TYPE,
                    FILE_VERSION,
                    DISPLAY_ALGORITHM,
                    activeKeyId,
                    BASE64_URL_ENCODER.encodeToString(iv),
                    BASE64_URL_ENCODER.encodeToString(encryptedPayload));

        } catch (GeneralSecurityException | IOException exception) {
            throw new TerminalRecoveryCryptoException(
                    "Terminal recovery file could not be encrypted.",
                    exception);

        } finally {
            if (plaintext != null) {
                Arrays.fill(plaintext, (byte) 0);
            }

            Arrays.fill(iv, (byte) 0);
        }
    }

    private <T> T decryptPayload(
            TerminalRecoveryFileEnvelopeDto envelope,
            Class<T> payloadType) {

        validateEnvelope(envelope);

        SecretKey decryptionKey = keyProvider.getRequiredKey(
                envelope.getKeyId());

        byte[] iv = decodeBase64Url(
                envelope.getIv(),
                "Recovery file IV");

        byte[] encryptedPayload = decodeBase64Url(
                envelope.getCiphertext(),
                "Recovery file ciphertext");

        if (iv.length != GCM_IV_BYTE_LENGTH
                || encryptedPayload.length < MINIMUM_CIPHERTEXT_LENGTH) {
            throw invalidRecoveryFile();
        }

        byte[] plaintext = null;

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    decryptionKey,
                    new GCMParameterSpec(
                            GCM_TAG_BIT_LENGTH,
                            iv));

            cipher.updateAAD(
                    buildAdditionalAuthenticatedData(
                            envelope.getFileType(),
                            envelope.getFileVersion(),
                            envelope.getAlgorithm(),
                            envelope.getKeyId()));

            plaintext = cipher.doFinal(encryptedPayload);

            return jsonMapper
                    .readerFor(payloadType)
                    .with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .readValue(plaintext);

        } catch (AEADBadTagException exception) {
            throw invalidRecoveryFile();

        } catch (GeneralSecurityException | IOException exception) {
            throw new TerminalRecoveryCryptoException(
                    "Terminal recovery file is invalid or cannot be processed.",
                    exception);

        } finally {
            Arrays.fill(iv, (byte) 0);
            Arrays.fill(encryptedPayload, (byte) 0);

            if (plaintext != null) {
                Arrays.fill(plaintext, (byte) 0);
            }
        }
    }

    private String serializeEnvelope(
            TerminalRecoveryFileEnvelopeDto envelope) {

        try {
            return jsonMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(envelope);

        } catch (IOException exception) {
            throw new TerminalRecoveryCryptoException(
                    "Encrypted terminal recovery file could not be serialized.",
                    exception);
        }
    }

    private TerminalRecoveryFileEnvelopeDto parseEnvelope(
            String encryptedFileJson) {

        if (encryptedFileJson == null
                || encryptedFileJson.isBlank()) {
            throw invalidRecoveryFile();
        }

        try {
            return jsonMapper
                    .readerFor(TerminalRecoveryFileEnvelopeDto.class)
                    .with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .readValue(encryptedFileJson);

        } catch (IOException exception) {
            throw invalidRecoveryFile();
        }
    }

    private static byte[] buildAdditionalAuthenticatedData(
            String fileType,
            int fileVersion,
            String algorithm,
            String keyId) {

        String authenticatedMetadata = fileType
                + "\n"
                + fileVersion
                + "\n"
                + algorithm
                + "\n"
                + keyId;

        return authenticatedMetadata.getBytes(StandardCharsets.UTF_8);
    }

    private static void validateEnvelope(
            TerminalRecoveryFileEnvelopeDto envelope) {

        if (envelope == null) {
            throw invalidRecoveryFile();
        }

        if (!FILE_TYPE.equals(clean(envelope.getFileType()))) {
            throw invalidRecoveryFile();
        }

        if (envelope.getFileVersion() != FILE_VERSION) {
            throw new TerminalRecoveryCryptoException(
                    "Unsupported terminal recovery file version.");
        }

        if (!DISPLAY_ALGORITHM.equals(clean(envelope.getAlgorithm()))) {
            throw invalidRecoveryFile();
        }

        requireText(
                envelope.getKeyId(),
                "Recovery encryption key ID",
                50);

        requireText(
                envelope.getIv(),
                "Recovery file IV",
                100);

        requireText(
                envelope.getCiphertext(),
                "Recovery file ciphertext",
                100_000);
    }

    private static void validateBrowserPayload(
            BrowserRecoveryFilePayloadDto payload) {

        if (payload == null) {
            throw invalidRecoveryFile();
        }

        validatePayloadVersion(payload.getPayloadVersion());
        requireText(payload.getUserKeyId(), "User key ID", 10);
        validateRecoveryToken(payload.getRecoveryToken());
        validateIssuedAt(payload.getIssuedAt());
    }

    private static void validatePayloadVersion(
            int payloadVersion) {

        if (payloadVersion != PAYLOAD_VERSION) {
            throw new TerminalRecoveryCryptoException(
                    "Unsupported terminal recovery payload version.");
        }
    }

    private static void validateRecoveryToken(
            String recoveryToken) {

        String normalizedRecoveryToken = requireText(
                recoveryToken,
                "Recovery token",
                100);

        if (!RECOVERY_TOKEN_PATTERN
                .matcher(normalizedRecoveryToken)
                .matches()) {
            throw invalidRecoveryFile();
        }
    }

    private static void validateIssuedAt(
            String issuedAt) {

        String normalizedIssuedAt = requireText(
                issuedAt,
                "Recovery issued date",
                50);

        try {
            Instant.parse(normalizedIssuedAt);

        } catch (DateTimeParseException exception) {
            throw invalidRecoveryFile();
        }
    }

    private static String requireText(
            String value,
            String fieldName,
            int maximumLength) {

        String normalizedValue = clean(value);

        if (normalizedValue.isBlank()
                || normalizedValue.length() > maximumLength) {
            throw new TerminalRecoveryCryptoException(
                    fieldName + " is missing or invalid.");
        }

        return normalizedValue;
    }

    private static byte[] decodeBase64Url(
            String value,
            String fieldName) {

        try {
            return BASE64_URL_DECODER.decode(
                    requireText(
                            value,
                            fieldName,
                            100_000));

        } catch (IllegalArgumentException exception) {
            throw invalidRecoveryFile();
        }
    }

    private static TerminalRecoveryCryptoException invalidRecoveryFile() {

        return new TerminalRecoveryCryptoException(
                "Recovery file is invalid, corrupted, modified, "
                        + "or was not created by this application.");
    }

    private static String clean(
            String value) {

        return value == null
                ? ""
                : value.trim();
    }
}
