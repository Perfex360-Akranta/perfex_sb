package com.akranta.perfex_sb.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bdm_tl_yycountermeasurelink")
public class BdmTlYycountermeasurelink {

    @Id
    @Column(name = "yycm_keyid")
    @JsonAlias({"keyid", "yycm_keyid"})
    private String yycmKeyid;

    @Column(name = "yycm_yyid")
    @JsonAlias({"yyid", "yycm_yyid"})
    private String yycmYyid;

    @Column(name = "yycm_refdoctype")
    @JsonAlias({"refdoctype", "yycm_refdoctype"})
    private String yycmRefdoctype;

    @Column(name = "yycm_countermsrid")
    @JsonAlias({"countermsrid", "yycm_countermsrid"})
    private String yycmCountermsrid;

    @Column(name = "yycm_woid")
    @JsonAlias({"woid", "yycm_woid"})
    private String yycmWoid;

    @Column(name = "yycm_tempfield1")
    @JsonAlias({"tempfield1", "yycm_tempfield1"})
    private String yycmTempfield1;

    @Column(name = "yycm_tempfield2")
    @JsonAlias({"tempfield2", "yycm_tempfield2"})
    private String yycmTempfield2;

    @Column(name = "yycm_tempfield3")
    @JsonAlias({"tempfield3", "yycm_tempfield3"})
    private String yycmTempfield3;

    @Column(name = "yycm_tempfield4")
    @JsonAlias({"tempfield4", "yycm_tempfield4"})
    private String yycmTempfield4;

    @Column(name = "yycm_tempfield5")
    @JsonAlias({"tempfield5", "yycm_tempfield5"})
    private String yycmTempfield5;

    @Column(name = "yycm_active")
    @JsonAlias({"active", "yycm_active"})
    private Character yycmActive;

    @Column(name = "yycm_createdby")
    @JsonAlias({"createdby", "yycm_createdby"})
    private String yycmCreatedby;

    // NOTE: If your DB column is timestamp, change LocalDate -> LocalDateTime
    @Column(name = "yycm_createdon")
    @JsonAlias({"createdon", "yycm_createdon"})
    private LocalDateTime yycmCreatedon;

    // Your Eclipse model typo is "modifieyon" but DB is usually "modifiedon"
    @Column(name = "yycm_modifiedon")
    @JsonAlias({"modifiedon", "yycm_modifiedon", "modifieyon", "yycm_modifieyon"})
    private LocalDateTime yycmModifiedon;

    public BdmTlYycountermeasurelink() {}

    public String getYycmKeyid() { return yycmKeyid; }
    public void setYycmKeyid(String yycmKeyid) { this.yycmKeyid = yycmKeyid; }

    public String getYycmYyid() { return yycmYyid; }
    public void setYycmYyid(String yycmYyid) { this.yycmYyid = yycmYyid; }

    public String getYycmRefdoctype() { return yycmRefdoctype; }
    public void setYycmRefdoctype(String yycmRefdoctype) { this.yycmRefdoctype = yycmRefdoctype; }

    public String getYycmCountermsrid() { return yycmCountermsrid; }
    public void setYycmCountermsrid(String yycmCountermsrid) { this.yycmCountermsrid = yycmCountermsrid; }

    public String getYycmWoid() { return yycmWoid; }
    public void setYycmWoid(String yycmWoid) { this.yycmWoid = yycmWoid; }

    public String getYycmTempfield1() { return yycmTempfield1; }
    public void setYycmTempfield1(String yycmTempfield1) { this.yycmTempfield1 = yycmTempfield1; }

    public String getYycmTempfield2() { return yycmTempfield2; }
    public void setYycmTempfield2(String yycmTempfield2) { this.yycmTempfield2 = yycmTempfield2; }

    public String getYycmTempfield3() { return yycmTempfield3; }
    public void setYycmTempfield3(String yycmTempfield3) { this.yycmTempfield3 = yycmTempfield3; }

    public String getYycmTempfield4() { return yycmTempfield4; }
    public void setYycmTempfield4(String yycmTempfield4) { this.yycmTempfield4 = yycmTempfield4; }

    public String getYycmTempfield5() { return yycmTempfield5; }
    public void setYycmTempfield5(String yycmTempfield5) { this.yycmTempfield5 = yycmTempfield5; }

    public Character getYycmActive() { return yycmActive; }
    public void setYycmActive(Character yycmActive) { this.yycmActive = yycmActive; }

    public String getYycmCreatedby() { return yycmCreatedby; }
    public void setYycmCreatedby(String yycmCreatedby) { this.yycmCreatedby = yycmCreatedby; }

    public LocalDateTime getYycmCreatedon() { return yycmCreatedon; }
    public void setYycmCreatedon(LocalDateTime yycmCreatedon) { this.yycmCreatedon = yycmCreatedon; }

    public LocalDateTime getYycmModifiedon() { return yycmModifiedon; }
    public void setYycmModifiedon(LocalDateTime yycmModifiedon) { this.yycmModifiedon = yycmModifiedon; }
}
