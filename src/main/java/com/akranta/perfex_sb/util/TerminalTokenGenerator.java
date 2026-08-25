package com.akranta.perfex_sb.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Generates cryptographically secure random tokens for terminal
 * browser registration and recovery-file credentials.
 *
 * Generated tokens:
 *
 * - contain 256 bits of randomness
 * - are URL-safe
 * - do not contain Base64 padding
 * - can safely be used as an HTTP cookie value
 *
 * The generated raw values must never be stored directly in the
 * database or written to application logs.
 */
@Component
public class TerminalTokenGenerator {

    /*
     * 32 random bytes = 256 bits of randomness.
     *
     * Base64 URL encoding without padding produces a token containing
     * 43 characters.
     */
    private static final int TOKEN_BYTE_LENGTH = 32;

    private static final Base64.Encoder TOKEN_ENCODER = Base64.getUrlEncoder()
            .withoutPadding();

    private final SecureRandom secureRandom;

    public TerminalTokenGenerator() {
        this.secureRandom = new SecureRandom();
    }

    /**
     * Generates the raw token that will later be placed in the
     * terminal browser's HttpOnly cookie.
     */
    public String generateBrowserToken() {
        return generateSecureToken();
    }

    /**
     * Generates the raw recovery credential that will later be placed
     * inside the encrypted terminal recovery file.
     *
     * The browser token and recovery token must always be generated
     * independently.
     */
    public String generateRecoveryToken() {
        return generateSecureToken();
    }

    private String generateSecureToken() {

        byte[] randomBytes = new byte[TOKEN_BYTE_LENGTH];

        secureRandom.nextBytes(randomBytes);

        return TOKEN_ENCODER.encodeToString(
                randomBytes);
    }
}