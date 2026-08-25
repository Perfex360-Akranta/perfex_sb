package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "gen_tl_sectionmst", schema = "public")
public class GenTlSectionmst {

    @Id
    @Column(name = "sect_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "sect_factoryid", length = 10)
    private String factoryid;

    @Column(name = "sect_companyid", length = 10)
    private String companyid;

    @Column(name = "sect_sectiongroup", length = 10)
    private String sectiongroup;

    @Column(name = "sect_code", length = 40)
    private String code;

    @Column(name = "sect_name", length = 100)
    private String name;

    @Column(name = "sect_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "sect_active", length = 1, nullable = false)
    private String active = "Y";

    @Column(name = "sect_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "sect_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "sect_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFactoryid() {
        return factoryid;
    }

    public void setFactoryid(String factoryid) {
        this.factoryid = factoryid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getSectiongroup() {
        return sectiongroup;
    }

    public void setSectiongroup(String sectiongroup) {
        this.sectiongroup = sectiongroup;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
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
