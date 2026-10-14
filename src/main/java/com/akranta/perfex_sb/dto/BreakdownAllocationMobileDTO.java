package com.akranta.perfex_sb.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class BreakdownAllocationMobileDTO {

    @JsonAlias({ "bdmskeyid", "bdms_keyid" })
    private String keyid;

    private String wno;

    @JsonAlias({ "tradeid", "maintsection" })
    private String sectionid;

    @JsonAlias({ "startDate", "wostarttime" })
    private String startdate;

    @JsonAlias({ "endDate", "woendtime" })
    private String enddate;

    private String bookedby;

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

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getBookedby() {
        return bookedby;
    }

    public void setBookedby(String bookedby) {
        this.bookedby = bookedby;
    }
}
