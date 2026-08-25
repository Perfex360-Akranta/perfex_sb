package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

public class AbnAllocationDto {

    private String keyid;
    private String responsibleid;
    private String tradeid;
    private LocalDateTime effectivedate; 

    // Getters and Setters
    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getResponsibleid() {
        return responsibleid;
    }

    public void setResponsibleid(String responsibleid) {
        this.responsibleid = responsibleid;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }


    public LocalDateTime getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(LocalDateTime effectivedate) {
        this.effectivedate = effectivedate;
    }
}
