package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BAL_PLM_TL_WORESPDTL", schema = "public")
public class Balworespdtl {

    @Id
    @Column(name = "pwrd_keyid", length = 15)
    private String pwrdkeyid;

    @Column(name = "pwrd_masterid", length = 20, nullable = false)
    private String pwrdmasterid;

    @Column(name = "pwrd_tradeid", length = 20)
    private String pwrdtradeid;

    @Column(name = "pwrd_empid", length = 10, nullable = false)
    private String pwrdempid;

   
    @Column(name = "pwrd_effectfrom")
    private LocalDateTime pwrdeffectfrom;

    @Column(name = "pwrd_effecttill")
    private LocalDateTime pwrdeffecttill;

    @Column(name = "pwrd_tempfield1", length = 50)
    private String pwrdtempfield1;

    @Column(name = "pwrd_tempfield2", length = 500)
    private String pwrdtempfield2;

    @Column(name = "pwrd_tempfield3", length = 500)
    private String pwrdtempfield3;

    @Column(name = "pwrd_tempfield4", length = 500)
    private String pwrdtempfield4;

    @Column(name = "pwrd_active", length = 1, nullable = false)
    private Character pwrdactive;

    @Column(name = "pwrd_createdby", length = 8, nullable = false)
    private String pwrdcreatedby;

    @Column(name = "pwrd_createdon", nullable = false)
    private LocalDateTime pwrdcreatedon;

    @Column(name = "pwrd_modifiedon", nullable = false)
    private LocalDateTime pwrdmodifiedon;

    public String getPwrdkeyid() {
        return pwrdkeyid;
    }

    public void setPwrdkeyid(String pwrdkeyid) {
        this.pwrdkeyid = pwrdkeyid;
    }

    public String getPwrdmasterid() {
        return pwrdmasterid;
    }

    public void setPwrdmasterid(String pwrdmasterid) {
        this.pwrdmasterid = pwrdmasterid;
    }

    public String getPwrdtradeid() {
        return pwrdtradeid;
    }

    public void setPwrdtradeid(String pwrdtradeid) {
        this.pwrdtradeid = pwrdtradeid;
    }

    public String getPwrdempid() {
        return pwrdempid;
    }

    public void setPwrdempid(String pwrdempid) {
        this.pwrdempid = pwrdempid;
    }

    public LocalDateTime getPwrdeffectfrom() {
        return pwrdeffectfrom;
    }

    public void setPwrdeffectfrom(LocalDateTime pwrdeffectfrom) {
        this.pwrdeffectfrom = pwrdeffectfrom;
    }

    public LocalDateTime getPwrdeffecttill() {
        return pwrdeffecttill;
    }

    public void setPwrdeffecttill(LocalDateTime pwrdeffecttill) {
        this.pwrdeffecttill = pwrdeffecttill;
    }

    public String getPwrdtempfield1() {
        return pwrdtempfield1;
    }

    public void setPwrdtempfield1(String pwrdtempfield1) {
        this.pwrdtempfield1 = pwrdtempfield1;
    }

    public String getPwrdtempfield2() {
        return pwrdtempfield2;
    }

    public void setPwrdtempfield2(String pwrdtempfield2) {
        this.pwrdtempfield2 = pwrdtempfield2;
    }

    public String getPwrdtempfield3() {
        return pwrdtempfield3;
    }

    public void setPwrdtempfield3(String pwrdtempfield3) {
        this.pwrdtempfield3 = pwrdtempfield3;
    }

    public String getPwrdtempfield4() {
        return pwrdtempfield4;
    }

    public void setPwrdtempfield4(String pwrdtempfield4) {
        this.pwrdtempfield4 = pwrdtempfield4;
    }

    public Character getPwrdactive() {
        return pwrdactive;
    }

    public void setPwrdactive(Character pwrdactive) {
        this.pwrdactive = pwrdactive;
    }

    public String getPwrdcreatedby() {
        return pwrdcreatedby;
    }

    public void setPwrdcreatedby(String pwrdcreatedby) {
        this.pwrdcreatedby = pwrdcreatedby;
    }

    public LocalDateTime getPwrdcreatedon() {
        return pwrdcreatedon;
    }

    public void setPwrdcreatedon(LocalDateTime pwrdcreatedon) {
        this.pwrdcreatedon = pwrdcreatedon;
    }

    public LocalDateTime getPwrdmodifiedon() {
        return pwrdmodifiedon;
    }

    public void setPwrdmodifiedon(LocalDateTime pwrdmodifiedon) {
        this.pwrdmodifiedon = pwrdmodifiedon;
    }

    

    
}