package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "adm_tl_configurationmst", schema = "public")
public class AdmTlConfigurationmst {

    @Id
    @Column(name = "cnfm_keyid", length = 8, nullable = false)
    private String keyid;

    @Column(name = "cnfm_code", length = 30, nullable = false)
    private String code;

    @Column(name = "cnfm_settingvalue", length = 30, nullable = false)
    private String settingvalue;

    @Column(name = "cnfm_fromdate", nullable = false)
    private LocalDateTime fromdate;

    @Column(name = "cnfm_tilldate", nullable = false)
    private LocalDateTime tilldate;

    @Column(name = "cnfm_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "cnfm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "cnfm_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "cnfm_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSettingvalue() {
        return settingvalue;
    }

    public void setSettingvalue(String settingvalue) {
        this.settingvalue = settingvalue;
    }

    public LocalDateTime getFromdate() {
        return fromdate;
    }

    public void setFromdate(LocalDateTime fromdate) {
        this.fromdate = fromdate;
    }

    public LocalDateTime getTilldate() {
        return tilldate;
    }

    public void setTilldate(LocalDateTime tilldate) {
        this.tilldate = tilldate;
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