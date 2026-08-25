package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

public class EntTlTrgCalUnqpDto {

    private String etcuKeyid;
    private String etcuEtcmKeyid;
    private String etcuRoleKeyid;
    @JsonAlias({"etcuRoleDmt", "etcuRoledmt"})
    private String etcuRoledmt;
    @JsonAlias({"etcuRoleJh", "etcuRolejh"})
    private String etcuRolejh;
    private LocalDateTime etcuDateadd;
    private String etcuTempfield1;
    private String etcuTempfield2;
    private String etcuTempfield3;
    private String etcuTempfield4;
    private String etcuTempfield5;
    private String etcuCreatedby;
    private String etcuActive;
    private LocalDateTime etcuCreatedon;
    private LocalDateTime etcuModifiedon;

    public String getEtcuKeyid() { return etcuKeyid; }
    public void setEtcuKeyid(String etcuKeyid) { this.etcuKeyid = etcuKeyid; }

    public String getEtcuEtcmKeyid() { return etcuEtcmKeyid; }
    public void setEtcuEtcmKeyid(String etcuEtcmKeyid) { this.etcuEtcmKeyid = etcuEtcmKeyid; }

    public String getEtcuRoleKeyid() { return etcuRoleKeyid; }
    public void setEtcuRoleKeyid(String etcuRoleKeyid) { this.etcuRoleKeyid = etcuRoleKeyid; }

    public String getEtcuRoledmt() { return etcuRoledmt; }
    public void setEtcuRoledmt(String etcuRoledmt) { this.etcuRoledmt = etcuRoledmt; }

    public String getEtcuRolejh() { return etcuRolejh; }
    public void setEtcuRolejh(String etcuRolejh) { this.etcuRolejh = etcuRolejh; }

    public LocalDateTime getEtcuDateadd() { return etcuDateadd; }
    public void setEtcuDateadd(LocalDateTime etcuDateadd) { this.etcuDateadd = etcuDateadd; }

    public String getEtcuTempfield1() { return etcuTempfield1; }
    public void setEtcuTempfield1(String etcuTempfield1) { this.etcuTempfield1 = etcuTempfield1; }

    public String getEtcuTempfield2() { return etcuTempfield2; }
    public void setEtcuTempfield2(String etcuTempfield2) { this.etcuTempfield2 = etcuTempfield2; }

    public String getEtcuTempfield3() { return etcuTempfield3; }
    public void setEtcuTempfield3(String etcuTempfield3) { this.etcuTempfield3 = etcuTempfield3; }

    public String getEtcuTempfield4() { return etcuTempfield4; }
    public void setEtcuTempfield4(String etcuTempfield4) { this.etcuTempfield4 = etcuTempfield4; }

    public String getEtcuTempfield5() { return etcuTempfield5; }
    public void setEtcuTempfield5(String etcuTempfield5) { this.etcuTempfield5 = etcuTempfield5; }

    public String getEtcuCreatedby() { return etcuCreatedby; }
    public void setEtcuCreatedby(String etcuCreatedby) { this.etcuCreatedby = etcuCreatedby; }

    public String getEtcuActive() { return etcuActive; }
    public void setEtcuActive(String etcuActive) { this.etcuActive = etcuActive; }

    public LocalDateTime getEtcuCreatedon() { return etcuCreatedon; }
    public void setEtcuCreatedon(LocalDateTime etcuCreatedon) { this.etcuCreatedon = etcuCreatedon; }

    public LocalDateTime getEtcuModifiedon() { return etcuModifiedon; }
    public void setEtcuModifiedon(LocalDateTime etcuModifiedon) { this.etcuModifiedon = etcuModifiedon; }
}
