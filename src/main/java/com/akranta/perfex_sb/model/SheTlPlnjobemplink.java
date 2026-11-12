package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "she_tl_plnjobemplink", schema = "public")
public class SheTlPlnjobemplink {

    @Id
    @Column(name = "pjel_keyid", length = 15)
    private String keyid;

    @Column(name = "pjel_pjob_keyid", length = 15)
    private String pjob_keyid;

    @Column(name = "pjel_pjob_employeeid", length = 20)
    private String pjob_employeeid;

    @Column(name = "pjel_tempfield1", length = 1)
    private String tempfield1;

    @Column(name = "pjel_tempfield2", length = 1)
    private String tempfield2;

    @Column(name = "pjel_tempfield3", length = 1)
    private String tempfield3;

    @Column(name = "pjel_tempfield4", length = 1)
    private String tempfield4;

    @Column(name = "pjel_active", length = 1)
    private Character active;

    @Column(name = "pjel_createdby", length = 10)
    private String createdby;

    @Column(name = "pjel_createdon")
    private LocalDateTime createdon;

    @Column(name = "pjel_modifiedon")
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getPjob_keyid() {
        return pjob_keyid;
    }

    public void setPjob_keyid(String pjob_keyid) {
        this.pjob_keyid = pjob_keyid;
    }

    public String getPjob_employeeid() {
        return pjob_employeeid;
    }

    public void setPjob_employeeid(String pjob_employeeid) {
        this.pjob_employeeid = pjob_employeeid;
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

    