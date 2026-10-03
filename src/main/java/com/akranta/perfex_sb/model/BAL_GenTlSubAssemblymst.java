package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "gen_tl_subassemblymst", schema = "public")
public class BAL_GenTlSubAssemblymst {

    @Id
    @Column(name = "sbam_keyid", length = 8, nullable = false)
    private String keyid;

    @Column(name = "sbam_assemblyid", length = 8, nullable = false)
    private String assemblyid;

    @Column(name = "sbam_code", length = 15, nullable = false)
    private String code;

    @Column(name = "sbam_name", length = 50, nullable = false)
    private String name;

    @Column(name = "sbam_description", length = 100, nullable = false)
    private String description;

    @Column(name = "sbam_remarks", length = 100, nullable = false)
    private String remarks;

    @Column(name = "sbam_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "sbam_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "sbam_createdon", length = 8, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "sbam_modifiedon")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getAssemblyid() {
        return assemblyid;
    }

    public void setAssemblyid(String assemblyid) {
        this.assemblyid = assemblyid;
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