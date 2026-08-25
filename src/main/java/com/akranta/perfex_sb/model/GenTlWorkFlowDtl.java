package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "gen_tl_workflowdtl", schema = "public")
public class GenTlWorkFlowDtl {
    
    @Id
    @Column(name = "wrkd_keyid", length = 20, nullable = false)
    private String keyid;

    @Column(name = "wrkd_wrkm_keyid", length = 10, nullable = false)
    private String wrkm_keyid;

    @Column(name = "wrkd_stage", length = 20, nullable = false)
    private String stage;

    @Column(name = "wrkd_type", length = 20, nullable = false)
    private String type;

    @Column(name = "wrkd_tempfield1", length = 1, nullable = false)
    private Character tempfield1;

    @Column(name = "wrkd_tempfield2", length = 1, nullable = false)
    private Character tempfield2;

    @Column(name = "wrkd_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column(name = "wrkd_tempfield4", length = 1, nullable = false)
    private Character tempfield4;

    @Column(name = "wrkd_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "wrkd_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "wrkd_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "wrkd_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getWrkm_keyid() {
        return wrkm_keyid;
    }

    public void setWrkm_keyid(String wrkm_keyid) {
        this.wrkm_keyid = wrkm_keyid;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Character getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(Character tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public Character getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(Character tempfield2) {
        this.tempfield2 = tempfield2;
    }

    public Character getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(Character tempfield3) {
        this.tempfield3 = tempfield3;
    }

    public Character getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(Character tempfield4) {
        this.tempfield4 = tempfield4;
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
