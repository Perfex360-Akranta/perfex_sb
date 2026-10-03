package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;
import jakarta.persistence.Transient;

@Entity
@Table(name = "BAL_PLM_TL_WORESPMST", schema = "public")
public class Balworespmst {

    @Transient
private List<Balworespdtl> woRespDetail;

public List<Balworespdtl> getWoRespDetail() { return woRespDetail; }
public void setWoRespDetail(List<Balworespdtl> woRespDetail) { this.woRespDetail = woRespDetail; }

    @Id
    @Column(name = "pwrm_keyid", length = 15)
    private String pwrmkeyid;

    @Column(name = "pwrm_factoryid", length = 10)
    private String pwrmfactoryid;

    @Column(name = "pwrm_sectionid", length = 10, nullable = false)
    private String pwrmsectionid;

    @Column(name = "pwrm_cellid", length = 10, nullable = false)
    private String pwrmcellid;

    @Column(name = "pwrm_machineid", length = 10, nullable = false)
    private String pwrmmachineid    ;

    @Column(name = "pwrm_level", length = 10, nullable = false)
    private String pwrmlevel;

    @Column(name = "pwrm_tradewise", length = 1, nullable = false)
    private Character pwrmtradewise;

    @Column(name = "pwrm_general", length = 1, nullable = false)
    private Character pwrmgeneral;

    @Column(name = "pwrm_tempfield1", length = 50)
    private String pwrmtempfield1;

    @Column(name = "pwrm_tempfield2", length = 500)
    private String pwrmtempfield2;

    @Column(name = "pwrm_tempfield3", length = 500)
    private String pwrmtempfield3;

    @Column(name = "pwrm_tempfield4", length = 500)
    private String pwrmtempfield4;

    @Column(name = "pwrm_active", length = 1, nullable = false)
    private Character pwrmactive;

    @Column(name = "pwrm_createdby", length = 8, nullable = false)
    private String pwrmcreatedby;

    @Column(name = "pwrm_createdon", nullable = false)
    private LocalDateTime pwrmcreatedon;

    @Column(name = "pwrm_modifiedon", nullable = false)
    private LocalDateTime pwrmmodifiedon;

    public String getPwrmkeyid() {
        return pwrmkeyid;
    }
    public void setPwrmkeyid(String pwrmkeyid) {
        this.pwrmkeyid = pwrmkeyid;
    }
    public String getPwrmfactoryid() {
        return pwrmfactoryid;
    }
    public void setPwrmfactoryid(String pwrmfactoryid) {
        this.pwrmfactoryid = pwrmfactoryid;
    }
    public String getPwrmsectionid() {
        return pwrmsectionid;
    }
    public void setPwrmsectionid(String pwrmsectionid) {
        this.pwrmsectionid = pwrmsectionid;
    }
    public String getPwrmcellid() {
        return pwrmcellid;
    }
    public void setPwrmcellid(String pwrmcellid) {
        this.pwrmcellid = pwrmcellid;
    }
    public String getPwrmmachineid() {
        return pwrmmachineid;
    }
    public void setPwrmmachineid(String pwrmmachineid) {
        this.pwrmmachineid = pwrmmachineid;
    }
    public String getPwrmlevel() {
        return pwrmlevel;
    }
    public void setPwrmlevel(String pwrmlevel) {
        this.pwrmlevel = pwrmlevel;
    }
    public Character getPwrmtradewise() {
        return pwrmtradewise;
    }
    public void setPwrmtradewise(Character pwrmtradewise) {
        this.pwrmtradewise = pwrmtradewise;
    }
    public Character getPwrmgeneral() {
        return pwrmgeneral;
    }
    public void setPwrmgeneral(Character pwrmgeneral) {
        this.pwrmgeneral = pwrmgeneral;
    }
    public String getPwrmtempfield1() {
        return pwrmtempfield1;
    }
    public void setPwrmtempfield1(String pwrmtempfield1) {
        this.pwrmtempfield1 = pwrmtempfield1;
    }
    public String getPwrmtempfield2() {
        return pwrmtempfield2;
    }
    public void setPwrmtempfield2(String pwrmtempfield2) {
        this.pwrmtempfield2 = pwrmtempfield2;
    }
    public String getPwrmtempfield3() {
        return pwrmtempfield3;
    }
    public void setPwrmtempfield3(String pwrmtempfield3) {
        this.pwrmtempfield3 = pwrmtempfield3;
    }
    public String getPwrmtempfield4() {
        return pwrmtempfield4;
    }
    public void setPwrmtempfield4(String pwrmtempfield4) {
        this.pwrmtempfield4 = pwrmtempfield4;
    }
    public Character getPwrmactive() {
        return pwrmactive;
    }
    public void setPwrmactive(Character pwrmactive) {
        this.pwrmactive = pwrmactive;
    }
    public String getPwrmcreatedby() {
        return pwrmcreatedby;
    }
    public void setPwrmcreatedby(String pwrmcreatedby) {
        this.pwrmcreatedby = pwrmcreatedby;
    }
    public LocalDateTime getPwrmcreatedon() {
        return pwrmcreatedon;
    }
    public void setPwrmcreatedon(LocalDateTime pwrmcreatedon) {
        this.pwrmcreatedon = pwrmcreatedon;
    }
    public LocalDateTime getPwrmmodifiedon() {
        return pwrmmodifiedon;
    }
    public void setPwrmmodifiedon(LocalDateTime pwrmmodifiedon) {
        this.pwrmmodifiedon = pwrmmodifiedon;
    }

    

   
}