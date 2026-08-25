package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

public class AbnCompletionDto {

    private String keyid;
    private String countermeasure;
    private String status;
    private String completedby;
    private String remarks;
    private LocalDateTime woendtime; 

    // Getters and Setters
    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getCountermeasure() {
        return countermeasure;
    }

    public void setCountermeasure(String countermeasure) {
        this.countermeasure = countermeasure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompletedby() {
        return completedby;
    }

    public void setCompletedby(String completedby) {
        this.completedby = completedby;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getWoendtime() {
        return woendtime;
    }

    public void setWoendtime(LocalDateTime woendtime) {
        this.woendtime = woendtime;
    }
    
}
