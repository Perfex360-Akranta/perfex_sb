package com.akranta.perfex_sb.util;

public final class ValidationUtil {
    private ValidationUtil() {}

    public static boolean isValidKeyId(String keyId) {
        return keyId != null
                && !keyId.trim().isEmpty()
                && !keyId.equals("{}")
                && !keyId.equals("<**>")
                && !keyId.equals("-")
                && !"null".equalsIgnoreCase(keyId)
                && !"undefined".equalsIgnoreCase(keyId);
    }
}
