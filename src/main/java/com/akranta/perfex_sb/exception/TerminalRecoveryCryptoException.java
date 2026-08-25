package com.akranta.perfex_sb.exception;

/**
 * Raised when a terminal recovery file cannot be encrypted,
 * decrypted, authenticated or validated.
 */
public class TerminalRecoveryCryptoException
        extends RuntimeException {

    public TerminalRecoveryCryptoException(
            String message) {

        super(message);
    }

    public TerminalRecoveryCryptoException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}