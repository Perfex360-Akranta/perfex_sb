package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "she_tl_plantsafetymst", schema = "public")
public class SheTlPlantSafetymst {

    @Id
    @Column(name = "spsm_keyid", length = 18, nullable = false)
    private String keyid;

    @Column(name = "spsm_flid", length = 18, nullable = false)
    private String flid;

    @Column(name = "spsm_date", nullable = false)
    private LocalDateTime safetydate;

    @Column(name = "spsm_doneby", length = 12, nullable = false)
    private String doneby;

    @Column(name = "spsm_type", length = 2, nullable = false)
    private String safetytype;

    @Column(name = "spsm_members", length = 500, nullable = false)
    private String members;

    @Column(name = "spsm_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "spsm_tempfield1", length = 10, nullable = false)
    private String tempfield1;

    @Column(name = "spsm_tempfield2", length = 10, nullable = false)
    private String tempfield2;

    @Column(name = "spsm_tempfield3", length = 10, nullable = false)
    private String tempfield3;

    @Column(name = "spsm_tempfield4", length = 10, nullable = false)
    private String tempfield4;

    @Column(name = "spsm_tempfield5", length = 10, nullable = false)
    private String tempfield5;

    @Column(name = "spsm_active", length = 1, nullable = false)
    private String active;

    @Column(name = "spsm_createdby", length = 12, nullable = false)
    private String createdby;

    @Column(name = "spsm_createdon", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "spsm_modifiedon", nullable = false)
    private LocalDateTime modifiedOn;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public LocalDateTime getSafetydate() {
        return safetydate;
    }

    public void setSafetydate(LocalDateTime safetydate) {
        this.safetydate = safetydate;
    }

    public String getDoneby() {
        return doneby;
    }

    public void setDoneby(String doneby) {
        this.doneby = doneby;
    }

    public String getSafetytype() {
        return safetytype;
    }

    public void setSafetytype(String safetytype) {
        this.safetytype = safetytype;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    
    
}