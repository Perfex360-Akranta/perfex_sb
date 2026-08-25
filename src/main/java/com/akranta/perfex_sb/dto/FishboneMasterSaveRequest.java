package com.akranta.perfex_sb.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDateTime;

public class FishboneMasterSaveRequest {

    @JsonAlias({"fismKeyId", "FISM_KEYID"})
    private String fismKeyid;
    private String fismFlid;
    private String fismElementid;
    private String fismRefdocid;
    private String fismRefdoctype;
    private String fismTitle;
    private String fismProblem;
    private String fismRevisionno;
    private LocalDateTime fismPrepareddate;
    private String fismPreparedby;
    private LocalDateTime fismApproveddate;
    private String fismApprovedby;
    private String fismStatus;
    private String fismDefect;
    private String fismTempfield2;
    private String fismTempfield3;
    private String fismTempfield4;
    private String fismTempfield5;
    private String fismActive;
    private String fismCreatedby;
    private LocalDateTime fismCreatedon;
    private LocalDateTime fismModifiedon;

    public String getFismKeyid() {
        return fismKeyid;
    }

    public void setFismKeyid(String fismKeyid) {
        this.fismKeyid = fismKeyid;
    }

    public String getFismFlid() {
        return fismFlid;
    }

    public void setFismFlid(String fismFlid) {
        this.fismFlid = fismFlid;
    }

    public String getFismElementid() {
        return fismElementid;
    }

    public void setFismElementid(String fismElementid) {
        this.fismElementid = fismElementid;
    }

    public String getFismRefdocid() {
        return fismRefdocid;
    }

    public void setFismRefdocid(String fismRefdocid) {
        this.fismRefdocid = fismRefdocid;
    }

    public String getFismRefdoctype() {
        return fismRefdoctype;
    }

    public void setFismRefdoctype(String fismRefdoctype) {
        this.fismRefdoctype = fismRefdoctype;
    }

    public String getFismTitle() {
        return fismTitle;
    }

    public void setFismTitle(String fismTitle) {
        this.fismTitle = fismTitle;
    }

    public String getFismProblem() {
        return fismProblem;
    }

    public void setFismProblem(String fismProblem) {
        this.fismProblem = fismProblem;
    }

    public String getFismRevisionno() {
        return fismRevisionno;
    }

    public void setFismRevisionno(String fismRevisionno) {
        this.fismRevisionno = fismRevisionno;
    }

    public LocalDateTime getFismPrepareddate() {
        return fismPrepareddate;
    }

    public void setFismPrepareddate(LocalDateTime fismPrepareddate) {
        this.fismPrepareddate = fismPrepareddate;
    }

    public String getFismPreparedby() {
        return fismPreparedby;
    }

    public void setFismPreparedby(String fismPreparedby) {
        this.fismPreparedby = fismPreparedby;
    }

    public LocalDateTime getFismApproveddate() {
        return fismApproveddate;
    }

    public void setFismApproveddate(LocalDateTime fismApproveddate) {
        this.fismApproveddate = fismApproveddate;
    }

    public String getFismApprovedby() {
        return fismApprovedby;
    }

    public void setFismApprovedby(String fismApprovedby) {
        this.fismApprovedby = fismApprovedby;
    }

    public String getFismStatus() {
        return fismStatus;
    }

    public void setFismStatus(String fismStatus) {
        this.fismStatus = fismStatus;
    }

    public String getFismDefect() {
        return fismDefect;
    }

    public void setFismDefect(String fismDefect) {
        this.fismDefect = fismDefect;
    }

    public String getFismTempfield2() {
        return fismTempfield2;
    }

    public void setFismTempfield2(String fismTempfield2) {
        this.fismTempfield2 = fismTempfield2;
    }

    public String getFismTempfield3() {
        return fismTempfield3;
    }

    public void setFismTempfield3(String fismTempfield3) {
        this.fismTempfield3 = fismTempfield3;
    }

    public String getFismTempfield4() {
        return fismTempfield4;
    }

    public void setFismTempfield4(String fismTempfield4) {
        this.fismTempfield4 = fismTempfield4;
    }

    public String getFismTempfield5() {
        return fismTempfield5;
    }

    public void setFismTempfield5(String fismTempfield5) {
        this.fismTempfield5 = fismTempfield5;
    }

    public String getFismActive() {
        return fismActive;
    }

    public void setFismActive(String fismActive) {
        this.fismActive = fismActive;
    }

    public String getFismCreatedby() {
        return fismCreatedby;
    }

    public void setFismCreatedby(String fismCreatedby) {
        this.fismCreatedby = fismCreatedby;
    }

    public LocalDateTime getFismCreatedon() {
        return fismCreatedon;
    }

    public void setFismCreatedon(LocalDateTime fismCreatedon) {
        this.fismCreatedon = fismCreatedon;
    }

    public LocalDateTime getFismModifiedon() {
        return fismModifiedon;
    }

    public void setFismModifiedon(LocalDateTime fismModifiedon) {
        this.fismModifiedon = fismModifiedon;
    }
}
