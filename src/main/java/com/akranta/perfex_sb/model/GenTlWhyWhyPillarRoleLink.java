package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "gen_tl_whywhy_pillar_rolelink", schema = "public")
public class GenTlWhyWhyPillarRoleLink {

    @Id
    @Column(name = "yyrl_keyid", length = 10, nullable = false)
    private String keyid;

    @Column(name = "yyrl_pillarid", length = 6)
    private String pillarid;

    @Column(name = "yyrl_pillarcode", length = 10)
    private String pillarcode;

    @Column(name = "yyrl_roleid", length = 8)
    private String roleid;

    @Column(name = "yyrl_rolecode", length = 15)
    private String rolecode;

    @Column(name = "yyrl_rolename", length = 100)
    private String rolename;

    @Column(name = "yyrl_active", columnDefinition = "CHAR(1)")
    private Character active;

    @Column(name = "yyrl_createdby", length = 10)
    private String createdby;

    @Column(name = "yyrl_createdon")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "yyrl_modifiedon")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getPillarid() {
        return pillarid;
    }

    public void setPillarid(String pillarid) {
        this.pillarid = pillarid;
    }

    public String getPillarcode() {
        return pillarcode;
    }

    public void setPillarcode(String pillarcode) {
        this.pillarcode = pillarcode;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
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