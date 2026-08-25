package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PhenomenaLossSaveRequestDto {
    private String plpmKeyid;
    private String plpmName;
    private String plpmMainloss;
    private String plpmTempfield1;
    private String plpmTempfield2;
    private String plpmTempfield3;
    private String plpmActive;
    private String plpmCreatedby;
    private LocalDateTime plpmCreatedon;
    private LocalDateTime plpmModifiedon;
    // optional list of factory ids to link
    private List<String> factoryIds;

    public String getPlpmKeyid() {
        return plpmKeyid;
    }

    public void setPlpmKeyid(String plpmKeyid) {
        this.plpmKeyid = plpmKeyid;
    }

    public String getPlpmName() {
        return plpmName;
    }

    public void setPlpmName(String plpmName) {
        this.plpmName = plpmName;
    }

    public String getPlpmMainloss() {
        return plpmMainloss;
    }

    public void setPlpmMainloss(String plpmMainloss) {
        this.plpmMainloss = plpmMainloss;
    }

    public String getPlpmTempfield1() {
        return plpmTempfield1;
    }

    public void setPlpmTempfield1(String plpmTempfield1) {
        this.plpmTempfield1 = plpmTempfield1;
    }

    public String getPlpmTempfield2() {
        return plpmTempfield2;
    }

    public void setPlpmTempfield2(String plpmTempfield2) {
        this.plpmTempfield2 = plpmTempfield2;
    }

    public String getPlpmTempfield3() {
        return plpmTempfield3;
    }

    public void setPlpmTempfield3(String plpmTempfield3) {
        this.plpmTempfield3 = plpmTempfield3;
    }

    public String getPlpmActive() {
        return plpmActive;
    }

    public void setPlpmActive(String plpmActive) {
        this.plpmActive = plpmActive;
    }

    public String getPlpmCreatedby() {
        return plpmCreatedby;
    }

    public void setPlpmCreatedby(String plpmCreatedby) {
        this.plpmCreatedby = plpmCreatedby;
    }

    public LocalDateTime getPlpmCreatedon() {
        return plpmCreatedon;
    }

    public void setPlpmCreatedon(LocalDateTime plpmCreatedon) {
        this.plpmCreatedon = plpmCreatedon;
    }

    public LocalDateTime getPlpmModifiedon() {
        return plpmModifiedon;
    }

    public void setPlpmModifiedon(LocalDateTime plpmModifiedon) {
        this.plpmModifiedon = plpmModifiedon;
    }

    public List<String> getFactoryIds() {
        return factoryIds;
    }

    public void setFactoryIds(List<String> factoryIds) {
        this.factoryIds = factoryIds;
    }
}
