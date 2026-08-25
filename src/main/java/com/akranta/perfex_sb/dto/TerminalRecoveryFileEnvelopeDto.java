package com.akranta.perfex_sb.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Non-sensitive outer structure of a .pfxterm file.
 *
 * The terminal code, user ID and recovery token are contained only
 * inside the encrypted ciphertext.
 */
@JsonPropertyOrder({
        "fileType",
        "fileVersion",
        "algorithm",
        "keyId",
        "iv",
        "ciphertext"
})
public class TerminalRecoveryFileEnvelopeDto {

    private String fileType;

    private int fileVersion;

    private String algorithm;

    /*
     * Identifies which server-side encryption key was used.
     *
     * The key itself is never written to the recovery file.
     */
    private String keyId;

    /*
     * Base64URL-encoded 12-byte AES-GCM IV.
     */
    private String iv;

    /*
     * Base64URL-encoded encrypted payload and GCM authentication tag.
     */
    private String ciphertext;

    public TerminalRecoveryFileEnvelopeDto() {
        /*
         * Required by Jackson.
         */
    }

    public TerminalRecoveryFileEnvelopeDto(
            String fileType,
            int fileVersion,
            String algorithm,
            String keyId,
            String iv,
            String ciphertext) {

        this.fileType = fileType;
        this.fileVersion = fileVersion;
        this.algorithm = algorithm;
        this.keyId = keyId;
        this.iv = iv;
        this.ciphertext = ciphertext;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(int fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }
}