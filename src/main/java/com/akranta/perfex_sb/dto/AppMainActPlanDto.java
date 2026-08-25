package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

public class AppMainActPlanDto {
    private String actionPlanId;
    private String detailId;
    private String status;
    private LocalDateTime completedOn;
    private String completedBy;
    private String counterMeasure;

    public AppMainActPlanDto() {
    }

    public String getActionPlanId() {
        return actionPlanId;
    }

    public void setActionPlanId(String actionPlanId) {
        this.actionPlanId = actionPlanId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(LocalDateTime completedOn) {
        this.completedOn = completedOn;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public String getCounterMeasure() {
        return counterMeasure;
    }

    public void setCounterMeasure(String counterMeasure) {
        this.counterMeasure = counterMeasure;
    }

}
