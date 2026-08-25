package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonSetter;

public class EntTlTrgCalEmpDto {
    private String etceKeyid;
    private String etceEtcmKeyid;
    private String etceEtcsKeyid;
    private String etceEmpmKeyid;
    private LocalDateTime etceDateadd;
    private String etceRoleKeyid;
    private String etceRoleDmt;
    private String etceRoleJh;
    private String etceTempfield1;
    private String etceTempfield2;
    private String etceTempfield3;
    private String etceTempfield4;
    private String etceTempfield5;
    private String etceCreatedby;
    private String etceActive;
    private LocalDateTime etceCreatedon;
    private LocalDateTime etceModifiedon;

    public String getEtceKeyid() { return etceKeyid; }
    @JsonSetter
    public void setEtceKeyid(Object etceKeyid) { this.etceKeyid = asString(etceKeyid); }

    public String getEtceEtcmKeyid() { return etceEtcmKeyid; }
    @JsonSetter
    public void setEtceEtcmKeyid(Object etceEtcmKeyid) { this.etceEtcmKeyid = asString(etceEtcmKeyid); }

    public String getEtceEtcsKeyid() { return etceEtcsKeyid; }
    @JsonSetter
    public void setEtceEtcsKeyid(Object etceEtcsKeyid) { this.etceEtcsKeyid = asString(etceEtcsKeyid); }

    public String getEtceEmpmKeyid() { return etceEmpmKeyid; }
    @JsonSetter
    public void setEtceEmpmKeyid(Object etceEmpmKeyid) { this.etceEmpmKeyid = asString(etceEmpmKeyid); }

    public LocalDateTime getEtceDateadd() { return etceDateadd; }
    public void setEtceDateadd(LocalDateTime etceDateadd) { this.etceDateadd = etceDateadd; }

    public String getEtceRoleKeyid() { return etceRoleKeyid; }
    @JsonSetter
    public void setEtceRoleKeyid(Object etceRoleKeyid) { this.etceRoleKeyid = asString(etceRoleKeyid); }

    public String getEtceRoleDmt() { return etceRoleDmt; }
    @JsonSetter
    public void setEtceRoleDmt(Object etceRoleDmt) { this.etceRoleDmt = asString(etceRoleDmt); }

    public String getEtceRoleJh() { return etceRoleJh; }
    @JsonSetter
    public void setEtceRoleJh(Object etceRoleJh) { this.etceRoleJh = asString(etceRoleJh); }

    public String getEtceTempfield1() { return etceTempfield1; }
    @JsonSetter
    public void setEtceTempfield1(Object etceTempfield1) { this.etceTempfield1 = asString(etceTempfield1); }

    public String getEtceTempfield2() { return etceTempfield2; }
    @JsonSetter
    public void setEtceTempfield2(Object etceTempfield2) { this.etceTempfield2 = asString(etceTempfield2); }

    public String getEtceTempfield3() { return etceTempfield3; }
    @JsonSetter
    public void setEtceTempfield3(Object etceTempfield3) { this.etceTempfield3 = asString(etceTempfield3); }

    public String getEtceTempfield4() { return etceTempfield4; }
    @JsonSetter
    public void setEtceTempfield4(Object etceTempfield4) { this.etceTempfield4 = asString(etceTempfield4); }

    public String getEtceTempfield5() { return etceTempfield5; }
    @JsonSetter
    public void setEtceTempfield5(Object etceTempfield5) { this.etceTempfield5 = asString(etceTempfield5); }

    public String getEtceCreatedby() { return etceCreatedby; }
    @JsonSetter
    public void setEtceCreatedby(Object etceCreatedby) { this.etceCreatedby = asString(etceCreatedby); }

    public String getEtceActive() { return etceActive; }
    @JsonSetter
    public void setEtceActive(Object etceActive) { this.etceActive = asString(etceActive); }

    public LocalDateTime getEtceCreatedon() { return etceCreatedon; }
    public void setEtceCreatedon(LocalDateTime etceCreatedon) { this.etceCreatedon = etceCreatedon; }

    public LocalDateTime getEtceModifiedon() { return etceModifiedon; }
    public void setEtceModifiedon(LocalDateTime etceModifiedon) { this.etceModifiedon = etceModifiedon; }

    private String asString(Object v) {
        if (v == null) return null;
        String s = String.valueOf(v);
        return "{}".equals(s.trim()) ? "{}" : s;
    }
}
