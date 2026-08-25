package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

public class ActionPlanCompDto {

    private String keyid;
    private String aplmkeyid;
    private String responsibility;
    private String countermeasure;
    private Character status;
    private String completedby;
    private String remarks;
    private LocalDateTime compleatedon;
    private LocalDateTime targetdate;

    public String getKeyid() {
        return keyid;
    }
    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }
    public String getAplmkeyid() {
        return aplmkeyid;
    }
    public void setAplmkeyid(String aplmkeyid) {
        this.aplmkeyid = aplmkeyid;
    }

      public String getResponsibility() {
        return responsibility;
    }
    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }
    public String getCountermeasure() {
        return countermeasure;
    }
    public void setCountermeasure(String countermeasure) {
        this.countermeasure = countermeasure;
    }
    public Character getStatus() {
        return status;
    }
    public void setStatus(Character status) {
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
    public LocalDateTime getCompleatedon() {
        return compleatedon;
    }
    public void setCompleatedon(LocalDateTime compleatedon) {
        this.compleatedon = compleatedon;
    }
    public LocalDateTime getTargetdate() {
        return targetdate;
    }
    public void setTargetdate(LocalDateTime targetdate) {
        this.targetdate = targetdate;
    }
    
}
