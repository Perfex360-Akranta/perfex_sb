package com.akranta.perfex_sb.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class BreakdownCompletionMobileDTO {

    @JsonAlias({ "bdmskeyid", "bdms_keyid" })
    private String keyid;

    private String wno;

    @JsonAlias({ "completedBy" })
    private String completedby;

    @JsonAlias({ "completedDate", "completedon" })
    private String completeddate;

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

    public String getCompleteddate() {
        return completeddate;
    }

    public void setCompleteddate(String completeddate) {
        this.completeddate = completeddate;
    }
}
