package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "gen_tl_assemblymst", schema = "public")
public class BAL_GenTlAssemblymst {

    @Id
    @Column(name = "assm_keyid", length = 10, nullable = false)
    private String keyid;

    @Column(name = "assm_code", length = 15, nullable = false)
    private String code;

    @Column(name = "assm_name", length = 100, nullable = false)
    private String name;

    @Column(name = "assm_description", length = 100, nullable = false)
    private String description;

    @Column(name = "assm_remarks", length = 100, nullable = false)
    private String remarks;

    @Column(name = "assm_type", columnDefinition = "CHAR(3)", nullable = false)
    private String type;

    @Column(name = "assm_relatedto", length = 3, nullable = false)
    private String relatedto;

    @Column(name = "assm_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "assm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "assm_createdon", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "assm_modifiedon", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime modifiedon;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelatedto() {
        return relatedto;
    }

    public void setRelatedto(String relatedto) {
        this.relatedto = relatedto;
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