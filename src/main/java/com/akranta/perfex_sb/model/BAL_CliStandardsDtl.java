package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "bal_cli_tl_standards_dtl", schema = "public")
public class BAL_CliStandardsDtl {

    @Id
    @Column(name = "clisd_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "clisdr_keyid", length = 12, nullable = false, unique = true)
    private String standardsKeyid;

    @Column(name = "clisd_howicn", length = 20)
    private String howicn;

    @Column(name = "clisd_tempfeild1", length = 500)
    private String tempfield1;

    @Column(name = "clisd_tempfeild2", length = 500)
    private String tempfield2;

    @Column(name = "clisd_tempfeild3", length = 500)
    private String tempfield3;

    @Column(name = "clisd_tempfeild4", length = 500)
    private String tempfield4;

    @Column(name = "clisd_tempfeild5", length = 500)
    private String tempfield5;

    @Column(name = "clisd_tempfeild6", length = 500)
    private String tempfield6;

    @Column(name = "clisd_tempfeild7", length = 500)
    private String tempfield7;

    @Column(name = "clisd_tempfeild8", length = 500)
    private String tempfield8;

    @Column(name = "clisd_tempfeild9", length = 500)
    private String tempfield9;

    @Column(name = "clisd_tempfeild10", length = 500)
    private String tempfield10;

    @Column(name = "clisd_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "clisd_createdon", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "clisd_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getStandardsKeyid() {
        return standardsKeyid;
    }

    public void setStandardsKeyid(String standardsKeyid) {
        this.standardsKeyid = standardsKeyid;
    }

    public String getHowicn() {
        return howicn;
    }

    public void setHowicn(String howicn) {
        this.howicn = howicn;
    }

    public String getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(String tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public String getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(String tempfield2) {
        this.tempfield2 = tempfield2;
    }

    public String getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(String tempfield3) {
        this.tempfield3 = tempfield3;
    }

    public String getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(String tempfield4) {
        this.tempfield4 = tempfield4;
    }

    public String getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(String tempfield5) {
        this.tempfield5 = tempfield5;
    }

    public String getTempfield6() {
        return tempfield6;
    }

    public void setTempfield6(String tempfield6) {
        this.tempfield6 = tempfield6;
    }

    public String getTempfield7() {
        return tempfield7;
    }

    public void setTempfield7(String tempfield7) {
        this.tempfield7 = tempfield7;
    }

    public String getTempfield8() {
        return tempfield8;
    }

    public void setTempfield8(String tempfield8) {
        this.tempfield8 = tempfield8;
    }

    public String getTempfield9() {
        return tempfield9;
    }

    public void setTempfield9(String tempfield9) {
        this.tempfield9 = tempfield9;
    }

    public String getTempfield10() {
        return tempfield10;
    }

    public void setTempfield10(String tempfield10) {
        this.tempfield10 = tempfield10;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDateTime getCreatedon() {
        return createdon;
    }

    public void setCreatedon(LocalDateTime createdon) {
        this.createdon = createdon;
    }

    public LocalDateTime getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(LocalDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }
}