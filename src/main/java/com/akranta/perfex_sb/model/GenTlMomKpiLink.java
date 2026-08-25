package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gen_tl_mom_kpi_link", schema = "public")
public class GenTlMomKpiLink {
    @Id
    @Column(name = "mokp_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "mokp_moms_keyid", length = 15, nullable = false)
    private String moms_keyid;

    @Column(name = "mokp_momd_keyid", length = 15, nullable = false)
    private String momd_keyid;

    @Column(name = "mokp_kink_keyid", length = 15, nullable = false)
    private String kink_keyid;

    @Column(name = "mokp_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "mokp_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "mokp_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "mokp_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "mokp_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "mokp_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "mokp_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "mokp_createdon", length = 15, nullable = false)
    private LocalDateTime createdon;

    @Column(name = "mokp_modifiedon", length = 15, nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getMoms_keyid() {
        return moms_keyid;
    }

    public void setMoms_keyid(String moms_keyid) {
        this.moms_keyid = moms_keyid;
    }

    public String getMomd_keyid() {
        return momd_keyid;
    }

    public void setMomd_keyid(String momd_keyid) {
        this.momd_keyid = momd_keyid;
    }

    public String getKink_keyid() {
        return kink_keyid;
    }

    public void setKink_keyid(String kink_keyid) {
        this.kink_keyid = kink_keyid;
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
