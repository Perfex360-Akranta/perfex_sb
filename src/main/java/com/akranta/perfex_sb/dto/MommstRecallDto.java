package com.akranta.perfex_sb.dto;

import java.time.LocalDate;

public class MommstRecallDto {
    private String keyId;
    private String shift;
    private String momDate;
    private String flid;
    private String type;
    private String pillarid;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getMomDate() {
        return momDate;
    }

    public void setMomDate(String momDate) {
        this.momDate = momDate;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPillarid() {
        return pillarid;
    }

    public void setPillarid(String pillarid) {
        this.pillarid = pillarid;
    }

}
