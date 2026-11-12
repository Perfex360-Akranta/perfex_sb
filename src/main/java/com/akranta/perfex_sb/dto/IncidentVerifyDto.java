package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

public class IncidentVerifyDto {
    private String verifiedBy;
    private LocalDateTime dateVerified;
    private String verifyStatus;
    private String verifyRemarks;
    private String keyId;

    // Getters and Setters

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public LocalDateTime getDateVerified() {
        return dateVerified;
    }

    public void setDateVerified(LocalDateTime dateVerified) {
        this.dateVerified = dateVerified;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getVerifyRemarks() {
        return verifyRemarks;
    }

    public void setVerifyRemarks(String verifyRemarks) {
        this.verifyRemarks = verifyRemarks;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
}
