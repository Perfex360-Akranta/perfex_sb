package com.akranta.perfex_sb.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.regex.Pattern;

/**
 * SHA-256 hashing utility used only for cryptographically random
 * terminal browser and recovery tokens.
 *
 * This class must not be used for user-password hashing.
 *
 * Passwords require a slow password-hashing algorithm such as
 * BCrypt, Argon2 or PBKDF2.
 */
public final class TerminalTokenHashUtil {

    private static final String HASH_ALGORITHM = "SHA-256";

    /*
     * SHA-256 produces:
     *
     * 32 bytes
     * 64 lowercase hexadecimal characters
     */
    private static final Pattern SHA_256_HEX_PATTERN = Pattern.compile("^[0-9a-fA-F]{64}$");

    private TerminalTokenHashUtil() {
        /*
         * Static utility class.
         */
    }

    /**
     * Produces the lowercase SHA-256 hexadecimal hash of a raw
     * terminal token.
     *
     * The returned value can be stored in:
     *
     * ADM_TL_TERMINALBROWSER.TBRW_TOKENHASH
     *
     * or:
     *
     * ADM_TL_TERMINALRECOVERY.TRCV_TOKENHASH
     */
    public static String hashToken(
            String rawToken) {

        byte[] tokenHash = digestToken(rawToken);

        return HexFormat.of()
                .formatHex(tokenHash);
    }

    /**
     * Compares a raw token with a previously stored SHA-256 hash.
     *
     * MessageDigest.isEqual is used instead of a normal String.equals
     * comparison.
     */
    public static boolean matchesToken(
            String rawToken,
            String storedTokenHash) {

        if (rawToken == null
                || rawToken.isBlank()) {
            return false;
        }

        String normalizedStoredHash = storedTokenHash == null
                ? ""
                : storedTokenHash.trim();

        if (!SHA_256_HEX_PATTERN
                .matcher(normalizedStoredHash)
                .matches()) {
            return false;
        }

        byte[] calculatedHash = digestToken(rawToken);

        byte[] expectedHash;

        try {
            expectedHash = HexFormat.of()
                    .parseHex(
                            normalizedStoredHash);

        } catch (IllegalArgumentException exception) {
            return false;
        }

        return MessageDigest.isEqual(
                calculatedHash,
                expectedHash);
    }

    private static byte[] digestToken(
            String rawToken) {

        validateRawToken(rawToken);

        try {

            MessageDigest messageDigest = MessageDigest.getInstance(
                    HASH_ALGORITHM);

            return messageDigest.digest(
                    rawToken.getBytes(
                            StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException exception) {

            /*
             * SHA-256 is mandatory in standard Java implementations.
             * Reaching this block means that the JVM environment is
             * incorrectly configured.
             */
            throw new IllegalStateException(
                    "SHA-256 hashing is unavailable.",
                    exception);
        }
    }

    private static void validateRawToken(
            String rawToken) {

        if (rawToken == null
                || rawToken.isBlank()) {
            throw new IllegalArgumentException(
                    "Terminal token cannot be null or blank.");
        }
    }
}