package com.akranta.perfex_sb.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OtherLossEntryDto {
    @JsonAlias({"olseKeyid", "txtOlseKeyid"})
    private String olseKeyid;
    private String olseFlid;
    private String olseElementid;
    private LocalDateTime olseDate;
    private String olseLossid;
    private LocalDateTime olseLossdate;
    @JsonAlias({"olseLossvalue", "txtOlseLossvalue"})
    private BigDecimal olseLossvalue;
    private String olseTempfield1;
    private String olseTempfield2;
    private String olseTempfield3;
    private String olseTempfield4;
    private String olseTempfield5;
    private String olseTempfield6;
    private String olseTempfield7;
    private String olseTempfield8;
    private String olseActive;
    private String olseCreatedby;
    private LocalDateTime olseCreatedon;
    private LocalDateTime olseModifiedon;

    public String getOlseKeyid() {
        return olseKeyid;
    }

    public void setOlseKeyid(String olseKeyid) {
        this.olseKeyid = olseKeyid;
    }

    public String getOlseFlid() {
        return olseFlid;
    }

    public void setOlseFlid(String olseFlid) {
        this.olseFlid = olseFlid;
    }

    public String getOlseElementid() {
        return olseElementid;
    }

    public void setOlseElementid(String olseElementid) {
        this.olseElementid = olseElementid;
    }

    public LocalDateTime getOlseDate() {
        return olseDate;
    }

    public void setOlseDate(LocalDateTime olseDate) {
        this.olseDate = olseDate;
    }

    public String getOlseLossid() {
        return olseLossid;
    }

    public void setOlseLossid(String olseLossid) {
        this.olseLossid = olseLossid;
    }

    public LocalDateTime getOlseLossdate() {
        return olseLossdate;
    }

    public void setOlseLossdate(LocalDateTime olseLossdate) {
        this.olseLossdate = olseLossdate;
    }

    public BigDecimal getOlseLossvalue() {
        return olseLossvalue;
    }

    public void setOlseLossvalue(BigDecimal olseLossvalue) {
        this.olseLossvalue = olseLossvalue;
    }

    public String getOlseTempfield1() {
        return olseTempfield1;
    }

    public void setOlseTempfield1(String olseTempfield1) {
        this.olseTempfield1 = olseTempfield1;
    }

    public String getOlseTempfield2() {
        return olseTempfield2;
    }

    public void setOlseTempfield2(String olseTempfield2) {
        this.olseTempfield2 = olseTempfield2;
    }

    public String getOlseTempfield3() {
        return olseTempfield3;
    }

    public void setOlseTempfield3(String olseTempfield3) {
        this.olseTempfield3 = olseTempfield3;
    }

    public String getOlseTempfield4() {
        return olseTempfield4;
    }

    public void setOlseTempfield4(String olseTempfield4) {
        this.olseTempfield4 = olseTempfield4;
    }

    public String getOlseTempfield5() {
        return olseTempfield5;
    }

    public void setOlseTempfield5(String olseTempfield5) {
        this.olseTempfield5 = olseTempfield5;
    }

    public String getOlseTempfield6() {
        return olseTempfield6;
    }

    public void setOlseTempfield6(String olseTempfield6) {
        this.olseTempfield6 = olseTempfield6;
    }

    public String getOlseTempfield7() {
        return olseTempfield7;
    }

    public void setOlseTempfield7(String olseTempfield7) {
        this.olseTempfield7 = olseTempfield7;
    }

    public String getOlseTempfield8() {
        return olseTempfield8;
    }

    public void setOlseTempfield8(String olseTempfield8) {
        this.olseTempfield8 = olseTempfield8;
    }

    public String getOlseActive() {
        return olseActive;
    }

    public void setOlseActive(String olseActive) {
        this.olseActive = olseActive;
    }

    public String getOlseCreatedby() {
        return olseCreatedby;
    }

    public void setOlseCreatedby(String olseCreatedby) {
        this.olseCreatedby = olseCreatedby;
    }

    public LocalDateTime getOlseCreatedon() {
        return olseCreatedon;
    }

    public void setOlseCreatedon(LocalDateTime olseCreatedon) {
        this.olseCreatedon = olseCreatedon;
    }

    public LocalDateTime getOlseModifiedon() {
        return olseModifiedon;
    }

    public void setOlseModifiedon(LocalDateTime olseModifiedon) {
        this.olseModifiedon = olseModifiedon;
    }
}
