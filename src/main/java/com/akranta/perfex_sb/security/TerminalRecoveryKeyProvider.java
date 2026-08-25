package com.akranta.perfex_sb.security;

import com.akranta.perfex_sb.exception.TerminalRecoveryCryptoException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Loads AES-256 terminal recovery keys from external configuration.
 *
 * Key-ring format:
 *
 * PFXTERM-K01=<base64-key>;PFXTERM-K00=<base64-key>
 *
 * The active key is used for new files.
 * Older keys remain available for decrypting older recovery files.
 */
@Component
public class TerminalRecoveryKeyProvider {

        private static final int AES_256_KEY_BYTE_LENGTH = 32;

        private static final String AES_ALGORITHM = "AES";

        private static final Pattern KEY_ID_PATTERN = Pattern.compile(
                        "^[A-Za-z0-9._-]{1,50}$");

        private final String activeKeyId;

        private final Map<String, SecretKey> keyRing;

        public TerminalRecoveryKeyProvider(

                        @Value("${perfex.terminal-recovery.crypto.active-key-id:}") String activeKeyId,

                        @Value("${perfex.terminal-recovery.crypto.key-ring:}") String keyRingConfiguration) {

                this.activeKeyId = validateAndCleanKeyId(
                                activeKeyId,
                                "Active terminal recovery key ID");

                this.keyRing = Collections.unmodifiableMap(
                                parseKeyRing(
                                                keyRingConfiguration));

                if (!this.keyRing.containsKey(this.activeKeyId)) {
                        throw new IllegalStateException(
                                        "Active terminal recovery key ID "
                                                        + this.activeKeyId
                                                        + " is not present in the configured key ring.");
                }
        }

        public String getActiveKeyId() {
                return activeKeyId;
        }

        public SecretKey getActiveKey() {
                return keyRing.get(activeKeyId);
        }

        /**
         * Retrieves the key referenced by an existing recovery file.
         */
        public SecretKey getRequiredKey(
                        String keyId) {

                String normalizedKeyId = clean(keyId);

                SecretKey key = keyRing.get(normalizedKeyId);

                if (key == null) {
                        throw new TerminalRecoveryCryptoException(
                                        "The recovery file references an unavailable encryption key.");
                }

                return key;
        }

        private static Map<String, SecretKey> parseKeyRing(
                        String configuration) {

                String normalizedConfiguration = clean(configuration);

                if (normalizedConfiguration.isBlank()) {
                        throw new IllegalStateException(
                                        "Terminal recovery encryption key ring is not configured.");
                }

                Map<String, SecretKey> parsedKeys = new LinkedHashMap<>();

                String[] entries = normalizedConfiguration.split(";");

                for (String rawEntry : entries) {

                        String entry = clean(rawEntry);

                        if (entry.isBlank()) {
                                continue;
                        }

                        /*
                         * Split only at the first '=' because Base64 values can also
                         * end with '=' padding characters.
                         */
                        int separatorIndex = entry.indexOf('=');

                        if (separatorIndex <= 0
                                        || separatorIndex == entry.length() - 1) {
                                throw new IllegalStateException(
                                                "Invalid terminal recovery key-ring entry.");
                        }

                        String keyId = validateAndCleanKeyId(
                                        entry.substring(
                                                        0,
                                                        separatorIndex),
                                        "Terminal recovery key ID");

                        String encodedKey = clean(
                                        entry.substring(
                                                        separatorIndex + 1));

                        byte[] decodedKey;

                        try {
                                decodedKey = Base64.getDecoder()
                                                .decode(encodedKey);

                        } catch (IllegalArgumentException exception) {
                                throw new IllegalStateException(
                                                "Terminal recovery key "
                                                                + keyId
                                                                + " is not valid Base64.",
                                                exception);
                        }

                        try {

                                if (decodedKey.length != AES_256_KEY_BYTE_LENGTH) {
                                        throw new IllegalStateException(
                                                        "Terminal recovery key "
                                                                        + keyId
                                                                        + " must decode to exactly 32 bytes.");
                                }

                                if (parsedKeys.containsKey(keyId)) {
                                        throw new IllegalStateException(
                                                        "Duplicate terminal recovery key ID: "
                                                                        + keyId);
                                }

                                parsedKeys.put(
                                                keyId,
                                                new SecretKeySpec(
                                                                decodedKey,
                                                                AES_ALGORITHM));

                        } finally {

                                /*
                                 * Remove the temporary decoded key material.
                                 */
                                Arrays.fill(
                                                decodedKey,
                                                (byte) 0);
                        }
                }

                if (parsedKeys.isEmpty()) {
                        throw new IllegalStateException(
                                        "Terminal recovery encryption key ring contains no valid keys.");
                }

                return parsedKeys;
        }

        private static String validateAndCleanKeyId(
                        String keyId,
                        String fieldName) {

                String normalizedKeyId = clean(keyId);

                if (normalizedKeyId.isBlank()) {
                        throw new IllegalStateException(
                                        fieldName
                                                        + " is not configured. "
                                                        + "Set environment variable "
                                                        + "PERFEX_TERMINAL_RECOVERY_ACTIVE_KEY_ID.");
                }

                if (!KEY_ID_PATTERN
                                .matcher(normalizedKeyId)
                                .matches()) {
                        throw new IllegalStateException(
                                        fieldName
                                                        + " must contain only letters, numbers, "
                                                        + "periods, underscores or hyphens.");
                }

                return normalizedKeyId;
        }

        private static String clean(
                        String value) {

                return value == null
                                ? ""
                                : value.trim();
        }
}