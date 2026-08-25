package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "kzn_tl_project_kaizen_link", schema = "public")
public class KznTlProjectKaizenLink {
    @Id
    @Column(name = "kplk_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "kplk_kzpm_keyid", length = 15, nullable = false)
    private String kzpm_keyid;

    @Column(name = "kplk_kznm_keyid", length = 15, nullable = false)
    private String kznm_keyid;

    @Column(name = "kplk_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "kplk_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "kplk_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "kplk_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "kplk_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "kplk_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "kplk_active",columnDefinition = "char(1)", nullable = false)
    private Character active;

    @Column(name = "kplk_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kplk_modifiedon", nullable = false)
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

    public String getKznm_keyid() {
        return kznm_keyid;
    }

    public void setKznm_keyid(String kznm_keyid) {
        this.kznm_keyid = kznm_keyid;
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
