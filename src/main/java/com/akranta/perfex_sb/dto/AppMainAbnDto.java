package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

public class AppMainAbnDto {
    private String abnmKeyid;
    private String status;
    private String counterMeasure;
    private LocalDateTime completedDate;
    private String completedBy;

    // ✅ Getters and Setters
    public String getAbnmKeyid() {
        return abnmKeyid;
    }

    public void setAbnmKeyid(String abnmKeyid) {
        this.abnmKeyid = abnmKeyid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCounterMeasure() {
        return counterMeasure;
    }

    public void setCounterMeasure(String counterMeasure) {
        this.counterMeasure = counterMeasure;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

}
