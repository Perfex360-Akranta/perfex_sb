package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "kzn_tl_proj_milestone_dtl", schema = "public")
public class KznTlProjMilestoneDtl {
    @Id
    @Column(name = "kmmd_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "kmmd_kmmm_keyid", length = 15, nullable = false)
    private String kmmm_keyid;

    @Column(name = "kmmd_milestone", length = 200, nullable = false)
    private String milestone;

    @Column(name = "kmmd_description", length = 200, nullable = false)
    private String description;

    @Column(name = "kmmd_targetdate", nullable = false)
    private LocalDateTime targetdate;

    @Column(name = "kmmd_empm_keyid", length = 8, nullable = false)
    private String empm_keyid;

    @Column(name = "kmmd_status", length = 1, nullable = false)
    private String status;

    @Column(name = "kmmd_remarks", length = 200, nullable = false)
    private String remarks;

    @Column(name = "kmmd_tempfield1", length = 10, nullable = false)
    private String tempfield1;

    @Column(name = "kmmd_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "kmmd_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "kmmd_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "kmmd_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "kmmd_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "kmmd_active",columnDefinition = "char(1)", nullable = false)
    private Character active;

    @Column(name = "kmmd_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kmmd_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    @Column(name = "kmmd_kzpm_keyid", length = 15)
    private String kzpm_keyid;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getKmmm_keyid() {
        return kmmm_keyid;
    }

    public void setKmmm_keyid(String kmmm_keyid) {
        this.kmmm_keyid = kmmm_keyid;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(LocalDateTime targetdate) {
        this.targetdate = targetdate;
    }

    public String getEmpm_keyid() {
        return empm_keyid;
    }

    public void setEmpm_keyid(String empm_keyid) {
        this.empm_keyid = empm_keyid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getKzpm_keyid() {
        return kzpm_keyid;
    }

    public void setKzpm_keyid(String kzpm_keyid) {
        this.kzpm_keyid = kzpm_keyid;
    }

    
}
