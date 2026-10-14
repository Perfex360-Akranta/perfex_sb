package com.akranta.perfex_sb.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class GenmaintenanceCompletionMobileDTO {

    @JsonAlias({ "gmntkeyid", "gmnt_keyid", "docno" })
    private String keyid;

    @JsonAlias({ "refdocid", "wono" })
    private String wno;

    @JsonAlias({ "completedBy" })
    private String completedby;

    @JsonAlias({ "startdate", "gmntWostartdate", "workstartdate" })
    private String wostartdate;

    @JsonAlias({ "enddate", "gmntWoenddate", "workenddate" })
    private String woenddate;

    @JsonAlias({ "completedDate", "completedon", "gmntCompleteddate" })
    private String completeddate;

    @JsonAlias({ "gmntRemarks" })
    private String remarks;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getWno() {
        return wno;
    }

    public void setWno(String wno) {
        this.wno = wno;
    }

    public String getCompletedby() {
        return completedby;
    }

    public void setCompletedby(String completedby) {
        this.completedby = completedby;
    }

    public String getWostartdate() {
        return wostartdate;
    }

    public void setWostartdate(String wostartdate) {
        this.wostartdate = wostartdate;
    }

    public String getWoenddate() {
        return woenddate;
    }

    public void setWoenddate(String woenddate) {
        this.woenddate = woenddate;
    }

    public String getCompleteddate() {
        return completeddate;
    }

    public void setCompleteddate(String completeddate) {
        this.completeddate = completeddate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
