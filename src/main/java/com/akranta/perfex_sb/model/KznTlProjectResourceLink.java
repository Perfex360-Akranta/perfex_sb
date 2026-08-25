package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity
@Table(name = "kzn_tl_project_resource_link", schema = "public")
public class KznTlProjectResourceLink {
    @Id
    @Column(name = "kprl_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "kprl_kzpm_keyid", length = 15, nullable = false)
    private String kzpm_keyid;

    @Column(name = "kprl_lead_memb", length = 1, nullable = false)
    private String lead_memb;

    @Column(name = "kprl_empm_keyid", length = 8, nullable = false)
    private String empm_keyid;

    @Column(name = "kprl_role_keyid", length = 50, nullable = false)
    private String role_keyid;

    @Column(name = "kprl_hrsestimate", nullable = false)
    private BigDecimal hrsestimate;

    @Column(name = "kprl_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "kprl_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "kprl_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "kprl_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "kprl_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "kprl_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "kprl_active",  columnDefinition = "char(1)", nullable = false)
    private Character active;

    @Column(name = "kprl_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kprl_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getKzpm_keyid() {
        return kzpm_keyid;
    }

    public void setKzpm_keyid(String kzpm_keyid) {
        this.kzpm_keyid = kzpm_keyid;
    }

    public String getLead_memb() {
        return lead_memb;
    }

    public void setLead_memb(String lead_memb) {
        this.lead_memb = lead_memb;
    }

    public String getEmpm_keyid() {
        return empm_keyid;
    }

    public void setEmpm_keyid(String empm_keyid) {
        this.empm_keyid = empm_keyid;
    }

    public String getRole_keyid() {
        return role_keyid;
    }

    public void setRole_keyid(String role_keyid) {
        this.role_keyid = role_keyid;
    }

    public BigDecimal getHrsestimate() {
        return hrsestimate;
    }

    public void setHrsestimate(BigDecimal hrsestimate) {
        this.hrsestimate = hrsestimate;
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

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
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
