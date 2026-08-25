package com.akranta.perfex_sb.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Minimal sensitive payload stored inside the encrypted .pfxterm file.
 *
 * The raw recovery token exists only in this encrypted payload. Its
 * SHA-256 hash is stored in PostgreSQL.
 */
@JsonPropertyOrder({
        "payloadVersion",
        "userKeyId",
        "recoveryToken",
        "issuedAt"
})
public class BrowserRecoveryFilePayloadDto {

    private int payloadVersion;

    private String userKeyId;

    private String recoveryToken;

    private String issuedAt;

    public BrowserRecoveryFilePayloadDto() {
        // Required by Jackson.
    }

    public BrowserRecoveryFilePayloadDto(
            int payloadVersion,
            String userKeyId,
            String recoveryToken,
            String issuedAt) {

        this.payloadVersion = payloadVersion;
        this.userKeyId = userKeyId;
        this.recoveryToken = recoveryToken;
        this.issuedAt = issuedAt;
    }

    public int getPayloadVersion() {
        return payloadVersion;
    }

    public void setPayloadVersion(int payloadVersion) {
        this.payloadVersion = payloadVersion;
    }

    public String getUserKeyId() {
        return userKeyId;
    }

    public void setUserKeyId(String userKeyId) {
        this.userKeyId = userKeyId;
    }

    public String getRecoveryToken() {
        return recoveryToken;
    }

    public void setRecoveryToken(String recoveryToken) {
        this.recoveryToken = recoveryToken;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }
}
