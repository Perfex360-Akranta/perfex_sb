package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gen_tl_holidaymst")
public class GenTlHolidaymst {

    @Id
    @Column(name = "holm_keyid", length = 10)
    private String keyid;

    @Column(name = "holm_name")
    private String name;

    @Column(name = "holm_date")
    private LocalDateTime date;

    @Column(name = "holm_description")
    private String description;

    @Column(name = "holm_factory")
    private String factory;   // gen_tl_locationmst.locn_keyid ku reference

    @Column(name = "holm_holidayflag")
    private String holidayflag;   // 'H' = Holiday, 'W' = Weekly Off

    @Column(name = "holm_createdby")
    private String createdby;

    @Column(name = "holm_createdon")
    private LocalDateTime createdon;

    @Column(name = "holm_modifiedon")
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getHolidayflag() {
        return holidayflag;
    }

    public void setHolidayflag(String holidayflag) {
        this.holidayflag = holidayflag;
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

    