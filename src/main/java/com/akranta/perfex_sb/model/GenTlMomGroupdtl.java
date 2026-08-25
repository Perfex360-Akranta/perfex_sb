package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gen_tl_mom_groupdtl", schema = "public")

public class GenTlMomGroupdtl {
    @Id
    @Column(name = "mgrd_keyid", length = 16, nullable = false)
    private String keyid;
    @Column(name = "mgrd_mgrm_keyid", length = 15, nullable = false)
    private String mgrm_keyid;

    @Column(name = "mgrd_empm_keyid", length = 16, nullable = false)
    private String empm_keyid;

    @Column(name = "mgrd_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "mgrd_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "mgrd_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "mgrd_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "mgrd_createdon", nullable = false)
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "mgrd_modifiedon", nullable = false)
    private LocalDateTime modifiedon = LocalDateTime.now();

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getMgrm_keyid() {
        return mgrm_keyid;
    }

    public void setMgrm_keyid(String mgrm_keyid) {
        this.mgrm_keyid = mgrm_keyid;
    }

    public String getEmpm_keyid() {
        return empm_keyid;
    }

    public void setEmpm_keyid(String empm_keyid) {
        this.empm_keyid = empm_keyid;
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

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
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
