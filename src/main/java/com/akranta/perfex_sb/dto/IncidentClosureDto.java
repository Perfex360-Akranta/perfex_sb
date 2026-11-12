package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

public class IncidentClosureDto {

    private String keyId;
    private String completedBy;
    private LocalDateTime dateCompleted;
    private LocalDateTime revisedDate;
    private String status;
    private String actionTaken;
    private String completedRemarks;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public LocalDateTime getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDateTime dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public LocalDateTime getRevisedDate() {
        return revisedDate;
    }

    public void setRevisedDate(LocalDateTime revisedDate) {
        this.revisedDate = revisedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public String getCompletedRemarks() {
        return completedRemarks;
    }

    public void setCompletedRemarks(String completedRemarks) {
        this.completedRemarks = completedRemarks;
    }
}