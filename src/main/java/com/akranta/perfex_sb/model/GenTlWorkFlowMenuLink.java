package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "gen_tl_workflow_menu_link", schema = "public")
public class GenTlWorkFlowMenuLink {

    @Id
    @Column(name = "wrml_keyid", length = 10, nullable = false)
    private String keyid;

    @Column(name = "wrml_menuno", nullable = false)
    private Integer menuno;

    @Column(name = "wrml_wrkm_keyid", length = 10, nullable = false)
    private String wrkm_keyid;

    @Column(name = "wrml_trans_code", length = 15, nullable = false)
    private String trans_code;

    @Column(name = "wrml_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "wrml_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "wrml_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column(name = "wrml_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "wrml_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "wrml_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "wrml_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public Integer getMenuno() {
        return menuno;
    }

    public void setMenuno(Integer menuno) {
        this.menuno = menuno;
    }

    public String getWrkm_keyid() {
        return wrkm_keyid;
    }

    public void setWrkm_keyid(String wrkm_keyid) {
        this.wrkm_keyid = wrkm_keyid;
    }

    public String getTrans_code() {
        return trans_code;
    }

    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code;
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

    public Character getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(Character tempfield3) {
        this.tempfield3 = tempfield3;
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
