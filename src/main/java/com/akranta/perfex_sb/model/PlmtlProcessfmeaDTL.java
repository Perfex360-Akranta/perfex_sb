package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "plm_tl_processfmeadtl", schema = "public")
public class PlmtlProcessfmeaDTL {

    @Id
    @Column(name = "fmpd_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "fmpd_fmpm_keyid", length = 15, nullable = false)
    private String fmpm_keyid;

    @Column(name = "fmpd_processstep", length = 100, nullable = false)
    private String processstep;

    @Column(name = "fmpd_keyprocessinput", length = 100, nullable = false)
    private String keyprocessinput;

    @Column(name = "fmpd_potentialfailmode", length = 200, nullable = false)
    private String potentialfailmode;

    @Column(name = "fmpd_potentialeffectfail", length = 200, nullable = false)
    private String potentialeffectfail;

    @Column(name = "fmpd_potentialcausefail", length = 200, nullable = false)
    private String potentialcausefail;

    @Column(name = "fmpd_severity_keyid", length = 15, nullable = false)
    private String severity_keyid;

    @Column(name = "fmpd_occurrence_keyid", length = 15, nullable = false)
    private String occurrence_keyid;

    @Column(name = "fmpd_detection_keyid", length = 15, nullable = false)
    private String detection_keyid;

    @Column(name = "fmpd_rpn", length = 100, nullable = false)
    private String rpn;

    @Column(name = "fmpd_currentcontrol", length = 200, nullable = false)
    private String currentcontrol;

    @Column(name = "fmpd_actionplan", length = 15, nullable = false)
    private String actionplan;

    @Column(name = "fmpd_reseverity_keyid", length = 15, nullable = false)
    private String reseverity_keyid;

    @Column(name = "fmpd_reoccurrence_keyid", length = 15, nullable = false)
    private String reoccurrence_keyid;

    @Column(name = "fmpd_redetection_keyid", length = 15, nullable = false)
    private String redetection_keyid;

    @Column(name = "fmpd_rerpn", nullable = false)
    private BigDecimal rerpn;

    @Column(name = "fmpd_reviewby", length = 15, nullable = false)
    private String reviewby;

    @Column(name = "fmpd_redate", nullable = false)
    private LocalDateTime redate;

    @Column(name = "fmpd_tempfield1", columnDefinition = "char(1)", nullable = false)
    private Character tempfield1;

    @Column(name = "fmpd_tempfield2", columnDefinition = "char(1)", nullable = false)
    private Character tempfield2;

    @Column(name = "fmpd_tempfield3", columnDefinition = "char(1)", nullable = false)
    private Character tempfield3;

    @Column(name = "fmpd_tempfield4", columnDefinition = "char(1)", nullable = false)
    private Character tempfield4;

    @Column(name = "fmpd_tempfield5", columnDefinition = "char(1)", nullable = false)
    private Character tempfield5;

    @Column(name = "fmpd_active", columnDefinition = "char(1)", nullable = false)
    private Character active;

    @Column(name = "fmpd_createdby", length = 15, nullable = false)
    private String createdby;

    @Column(name = "fmpd_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "fmpd_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFmpm_keyid() {
        return fmpm_keyid;
    }

    public void setFmpm_keyid(String fmpm_keyid) {
        this.fmpm_keyid = fmpm_keyid;
    }

    public String getProcessstep() {
        return processstep;
    }

    public void setProcessstep(String processstep) {
        this.processstep = processstep;
    }

    public String getKeyprocessinput() {
        return keyprocessinput;
    }

    public void setKeyprocessinput(String keyprocessinput) {
        this.keyprocessinput = keyprocessinput;
    }

    public String getPotentialfailmode() {
        return potentialfailmode;
    }

    public void setPotentialfailmode(String potentialfailmode) {
        this.potentialfailmode = potentialfailmode;
    }

    public String getPotentialeffectfail() {
        return potentialeffectfail;
    }

    public void setPotentialeffectfail(String potentialeffectfail) {
        this.potentialeffectfail = potentialeffectfail;
    }

    public String getPotentialcausefail() {
        return potentialcausefail;
    }

    public void setPotentialcausefail(String potentialcausefail) {
        this.potentialcausefail = potentialcausefail;
    }

    public String getSeverity_keyid() {
        return severity_keyid;
    }

    public void setSeverity_keyid(String severity_keyid) {
        this.severity_keyid = severity_keyid;
    }

    public String getOccurrence_keyid() {
        return occurrence_keyid;
    }

    public void setOccurrence_keyid(String occurrence_keyid) {
        this.occurrence_keyid = occurrence_keyid;
    }

    public String getDetection_keyid() {
        return detection_keyid;
    }

    public void setDetection_keyid(String detection_keyid) {
        this.detection_keyid = detection_keyid;
    }

    public String getRpn() {
        return rpn;
    }

    public void setRpn(String rpn) {
        this.rpn = rpn;
    }

    public String getCurrentcontrol() {
        return currentcontrol;
    }

    public void setCurrentcontrol(String currentcontrol) {
        this.currentcontrol = currentcontrol;
    }

    public String getActionplan() {
        return actionplan;
    }

    public void setActionplan(String actionplan) {
        this.actionplan = actionplan;
    }

    public String getReseverity_keyid() {
        return reseverity_keyid;
    }

    public void setReseverity_keyid(String reseverity_keyid) {
        this.reseverity_keyid = reseverity_keyid;
    }

    public String getReoccurrence_keyid() {
        return reoccurrence_keyid;
    }

    public void setReoccurrence_keyid(String reoccurrence_keyid) {
        this.reoccurrence_keyid = reoccurrence_keyid;
    }

    public String getRedetection_keyid() {
        return redetection_keyid;
    }

    public void setRedetection_keyid(String redetection_keyid) {
        this.redetection_keyid = redetection_keyid;
    }

    public BigDecimal getRerpn() {
        return rerpn;
    }

    public void setRerpn(BigDecimal rerpn) {
        this.rerpn = rerpn;
    }

    public String getReviewby() {
        return reviewby;
    }

    public void setReviewby(String reviewby) {
        this.reviewby = reviewby;
    }

    public LocalDateTime getRedate() {
        return redate;
    }

    public void setRedate(LocalDateTime redate) {
        this.redate = redate;
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

    public Character getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(Character tempfield5) {
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
