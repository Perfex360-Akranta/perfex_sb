package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "gen_tl_workflowmst", schema = "public")
public class GenTlWorkFlowMst {
    
    @Id
    @Column(name = "wrkm_keyid", length = 10, nullable = false)
    private String keyid;

    @Column(name = "wrkm_name", length = 200, nullable = false)
    private String name;

    @Column(name = "wrkm_noofstage", nullable = false)
    private Integer noofstage;

    @Column(name = "wrkm_tempfield1", length = 15, nullable = false)
    private String tempfield1;

    @Column(name = "wrkm_tempfield2", length = 1, nullable = false)
    private Character tempfield2;

    @Column(name = "wrkm_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column(name = "wrkm_tempfield4", length = 1, nullable = false)
    private Character tempfield4;

    @Column(name = "wrkm_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "wrkm_createdby", length = 12, nullable = false)
    private String createdby;

    @Column(name = "wrkm_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "wrkm_modifiedon", nullable = false)
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

    public Integer getNoofstage() {
        return noofstage;
    }

    public void setNoofstage(Integer noofstage) {
        this.noofstage = noofstage;
    }

    public String getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(String tempfield1) {
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
