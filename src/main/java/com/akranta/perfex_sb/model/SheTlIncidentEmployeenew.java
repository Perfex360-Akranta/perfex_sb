package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "she_tl_incident_employeenew", schema = "public")
public class SheTlIncidentEmployeenew {

    @Id
    @Column(name = "sine_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "sine_sinc_keyid", length = 15)
    private String sinc_keyid;

    @Column(name = "sine_empm_keyid", length = 12)
    private String empm_keyid;

    @Column(name = "sine_injury_modeid", length = 6)
    private String injury_modeid;

    @Column(name = "sine_bodypart", length = 200)
    private String bodypart;

    @Column(name = "sine_remarks", length = 500)
    private String remarks;

    @Column(name = "sine_injury_typeid", length = 6)
    private String injury_typeid;

    @Column(name = "sine_mode", length = 100)
    private String mode;

    @Column(name = "sine_type", length = 100)
    private String type;

    @Column(name = "sine_location", length = 200)
    private String location;

    @Column(name = "sine_tempfield5", length = 100)
    private String tempfield5;

    /* ===================== GETTERS & SETTERS ===================== */

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getSinc_keyid() {
        return sinc_keyid;
    }

    public void setSinc_keyid(String sinc_keyid) {
        this.sinc_keyid = sinc_keyid;
    }

    public String getEmpm_keyid() {
        return empm_keyid;
    }

    public void setEmpm_keyid(String empm_keyid) {
        this.empm_keyid = empm_keyid;
    }

    public String getInjury_modeid() {
        return injury_modeid;
    }

    public void setInjury_modeid(String injury_modeid) {
        this.injury_modeid = injury_modeid;
    }

    public String getBodypart() {
        return bodypart;
    }

    public void setBodypart(String bodypart) {
        this.bodypart = bodypart;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getInjury_typeid() {
        return injury_typeid;
    }

    public void setInjury_typeid(String injury_typeid) {
        this.injury_typeid = injury_typeid;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(String tempfield5) {
        this.tempfield5 = tempfield5;
    }
}
