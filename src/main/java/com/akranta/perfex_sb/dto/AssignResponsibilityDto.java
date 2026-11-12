package com.akranta.perfex_sb.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AssignResponsibilityDto {

    private String SINM_KEYID;
    private String SINM_RESPONSIBILITY;
    private String SINM_RESPONSERMRKS;
    private LocalDateTime SINM_TARGETDATE;  // assuming target date is a date type

    // Getters and Setters
    public String getSINM_KEYID() {
        return SINM_KEYID;
    }

    public void setSINM_KEYID(String SINM_KEYID) {
        this.SINM_KEYID = SINM_KEYID;
    }

    public String getSINM_RESPONSIBILITY() {
        return SINM_RESPONSIBILITY;
    }

    public void setSINM_RESPONSIBILITY(String SINM_RESPONSIBILITY) {
        this.SINM_RESPONSIBILITY = SINM_RESPONSIBILITY;
    }

    public String getSINM_RESPONSERMRKS() {
        return SINM_RESPONSERMRKS;
    }

    public void setSINM_RESPONSERMRKS(String SINM_RESPONSERMRKS) {
        this.SINM_RESPONSERMRKS = SINM_RESPONSERMRKS;
    }

    public LocalDateTime getSINM_TARGETDATE() {
        return SINM_TARGETDATE;
    }

    public void setSINM_TARGETDATE(LocalDateTime SINM_TARGETDATE) {
        this.SINM_TARGETDATE = SINM_TARGETDATE;
    }

    @Override
    public String toString() {
        return "UpdateIncidentResponsibilityDto{" +
                "SINM_KEYID='" + SINM_KEYID + '\'' +
                ", SINM_RESPONSIBILITY='" + SINM_RESPONSIBILITY + '\'' +
                ", SINM_RESPONSERMRKS='" + SINM_RESPONSERMRKS + '\'' +
                ", SINM_TARGETDATE=" + SINM_TARGETDATE +
                '}';
    }
}