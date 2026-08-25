package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gen_tl_mom_groupmst", schema = "public")

public class GenTlMomGroupmst {
    @Id
    @Column(name = "mgrm_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "mgrm_name", length = 100, nullable = false)
    private String name;

    @Column(name = "mgrm_pillarid", length = 6, nullable = false)
    private String pillarid;

    @Column(name = "mgrm_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "mgrm_emailid", length = 50, nullable = false)
    private String emailid;

    @Column(name = "mgrm_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "mgrm_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "mgrm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "mgrm_createdon", nullable = false)
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "mgrm_modifiedon", nullable = false)
    private LocalDateTime modifiedon = LocalDateTime.now();

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

    public String getPillarid() {
        return pillarid;
    }

    public void setPillarid(String pillarid) {
        this.pillarid = pillarid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
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
