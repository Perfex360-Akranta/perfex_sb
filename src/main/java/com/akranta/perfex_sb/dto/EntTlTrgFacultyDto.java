package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

public class EntTlTrgFacultyDto {

    private String etcfKeyid;
    private String etcfEtcmKeyid;
    private String etcfEtcmFlid;

    @JsonAlias({"etcfFacultyId"})
    private String etcfFacultyid;
    private String etcfFacultytype;
    private LocalDateTime etcfDateadd;
    private String etcfTempfield1;
    private String etcfTempfield2;
    private String etcfTempfield3;
    private String etcfTempfield4;
    private String etcfTempfield5;
    private String etcfActive;
    private String etcfCreatedby;
    private LocalDateTime etcfCreatedon;
    private LocalDateTime etcfModifiedon;

    public String getEtcfKeyid() { return etcfKeyid; }
    public void setEtcfKeyid(String etcfKeyid) { this.etcfKeyid = etcfKeyid; }

    public String getEtcfEtcmKeyid() { return etcfEtcmKeyid; }
    public void setEtcfEtcmKeyid(String etcfEtcmKeyid) { this.etcfEtcmKeyid = etcfEtcmKeyid; }

    public String getEtcfEtcmFlid() { return etcfEtcmFlid; }
    public void setEtcfEtcmFlid(String etcfEtcmFlid) { this.etcfEtcmFlid = etcfEtcmFlid; }

    public String getEtcfFacultyid() { return etcfFacultyid; }
    public void setEtcfFacultyid(String etcfFacultyid) { this.etcfFacultyid = etcfFacultyid; }

    public String getEtcfFacultytype() { return etcfFacultytype; }
    public void setEtcfFacultytype(String etcfFacultytype) { this.etcfFacultytype = etcfFacultytype; }

    public LocalDateTime getEtcfDateadd() { return etcfDateadd; }
    public void setEtcfDateadd(LocalDateTime etcfDateadd) { this.etcfDateadd = etcfDateadd; }

    public String getEtcfTempfield1() { return etcfTempfield1; }
    public void setEtcfTempfield1(String etcfTempfield1) { this.etcfTempfield1 = etcfTempfield1; }

    public String getEtcfTempfield2() { return etcfTempfield2; }
    public void setEtcfTempfield2(String etcfTempfield2) { this.etcfTempfield2 = etcfTempfield2; }

    public String getEtcfTempfield3() { return etcfTempfield3; }
    public void setEtcfTempfield3(String etcfTempfield3) { this.etcfTempfield3 = etcfTempfield3; }

    public String getEtcfTempfield4() { return etcfTempfield4; }
    public void setEtcfTempfield4(String etcfTempfield4) { this.etcfTempfield4 = etcfTempfield4; }

    public String getEtcfTempfield5() { return etcfTempfield5; }
    public void setEtcfTempfield5(String etcfTempfield5) { this.etcfTempfield5 = etcfTempfield5; }

    public String getEtcfActive() { return etcfActive; }
    public void setEtcfActive(String etcfActive) { this.etcfActive = etcfActive; }

    public String getEtcfCreatedby() { return etcfCreatedby; }
    public void setEtcfCreatedby(String etcfCreatedby) { this.etcfCreatedby = etcfCreatedby; }

    public LocalDateTime getEtcfCreatedon() { return etcfCreatedon; }
    public void setEtcfCreatedon(LocalDateTime etcfCreatedon) { this.etcfCreatedon = etcfCreatedon; }

    public LocalDateTime getEtcfModifiedon() { return etcfModifiedon; }
    public void setEtcfModifiedon(LocalDateTime etcfModifiedon) { this.etcfModifiedon = etcfModifiedon; }
}
