package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity
@Table(name = "kzn_tl_proj_milestone_mst", schema = "public")
public class KznTlProjMilestoneMst {
    @Id
    @Column(name = "kmmm_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "kmmm_kzpm_keyid", length = 15, nullable = false)
    private String kzpm_keyid;

    @Column(name = "kmmm_flid", length = 15, nullable = false)
    private String flid;

    @Column(name = "kmmm_stages", length = 1, nullable = false)
    private String stages;

    @Column(name = "kmmm_fromdate", nullable = false)
    private LocalDateTime fromdate;

    @Column(name = "kmmm_todate", nullable = false)
    private LocalDateTime todate;

    @Column(name = "kmmm_empm_keyid", length = 8, nullable = false)
    private String empm_keyid;

    @Column(name = "kmmm_status", length = 1, nullable = false)
    private String status;

    @Column(name = "kmmm_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "kmmm_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "kmmm_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "kmmm_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "kmmm_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "kmmm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "kmmm_active",columnDefinition = "char(1)", nullable = false)
    private Character active;

    @Column(name = "kmmm_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kmmm_modifiedon", nullable = false)
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

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getStages() {
        return stages;
    }

    public void setStages(String stages) {
        this.stages = stages;
    }

    public LocalDateTime getFromdate() {
        return fromdate;
    }

    public void setFromdate(LocalDateTime fromdate) {
        this.fromdate = fromdate;
    }

    public LocalDateTime getTodate() {
        return todate;
    }

    public void setTodate(LocalDateTime todate) {
        this.todate = todate;
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
