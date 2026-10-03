package com.akranta.perfex_sb.dto;

import java.util.List;

public class BalWorespMstSaveDto {

    private String pwrmKeyid;
    private String pwrmFactoryid;   // nullable in DB
    private String pwrmSectionid;
    private String pwrmCellid;
    private String pwrmMachineid;
    private String pwrmLevel;
    private String pwrmTradewise;   // "Y" = trade-specific, "N" = overall (matches "Overall Responsibility" checkbox)
    private String pwrmGeneral;
    private String pwrmTempfield1;
    private String pwrmTempfield2;
    private String pwrmTempfield3;
    private String pwrmTempfield4;
    private String pwrmActive;
    private String pwrmCreatedby;

    private List<BalWorespDtlSaveDto> woRespDetail;   // grid rows: trade + responsibility

    public String getPwrmKeyid() { return pwrmKeyid; }
    public void setPwrmKeyid(String pwrmKeyid) { this.pwrmKeyid = pwrmKeyid; }

    public String getPwrmFactoryid() { return pwrmFactoryid; }
    public void setPwrmFactoryid(String pwrmFactoryid) { this.pwrmFactoryid = pwrmFactoryid; }

    public String getPwrmSectionid() { return pwrmSectionid; }
    public void setPwrmSectionid(String pwrmSectionid) { this.pwrmSectionid = pwrmSectionid; }

    public String getPwrmCellid() { return pwrmCellid; }
    public void setPwrmCellid(String pwrmCellid) { this.pwrmCellid = pwrmCellid; }

    public String getPwrmMachineid() { return pwrmMachineid; }
    public void setPwrmMachineid(String pwrmMachineid) { this.pwrmMachineid = pwrmMachineid; }

    public String getPwrmLevel() { return pwrmLevel; }
    public void setPwrmLevel(String pwrmLevel) { this.pwrmLevel = pwrmLevel; }

    public String getPwrmTradewise() { return pwrmTradewise; }
    public void setPwrmTradewise(String pwrmTradewise) { this.pwrmTradewise = pwrmTradewise; }

    public String getPwrmGeneral() { return pwrmGeneral; }
    public void setPwrmGeneral(String pwrmGeneral) { this.pwrmGeneral = pwrmGeneral; }

    public String getPwrmTempfield1() { return pwrmTempfield1; }
    public void setPwrmTempfield1(String pwrmTempfield1) { this.pwrmTempfield1 = pwrmTempfield1; }

    public String getPwrmTempfield2() { return pwrmTempfield2; }
    public void setPwrmTempfield2(String pwrmTempfield2) { this.pwrmTempfield2 = pwrmTempfield2; }

    public String getPwrmTempfield3() { return pwrmTempfield3; }
    public void setPwrmTempfield3(String pwrmTempfield3) { this.pwrmTempfield3 = pwrmTempfield3; }

    public String getPwrmTempfield4() { return pwrmTempfield4; }
    public void setPwrmTempfield4(String pwrmTempfield4) { this.pwrmTempfield4 = pwrmTempfield4; }

    public String getPwrmActive() { return pwrmActive; }
    public void setPwrmActive(String pwrmActive) { this.pwrmActive = pwrmActive; }

    public String getPwrmCreatedby() { return pwrmCreatedby; }
    public void setPwrmCreatedby(String pwrmCreatedby) { this.pwrmCreatedby = pwrmCreatedby; }

    public List<BalWorespDtlSaveDto> getWoRespDetail() { return woRespDetail; }
    public void setWoRespDetail(List<BalWorespDtlSaveDto> woRespDetail) { this.woRespDetail = woRespDetail; }
}