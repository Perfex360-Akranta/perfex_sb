package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bal_plm_tl_cbmwocompdtl")
public class BalPlmTlCbmwocompdtl {

    @Id
    @Column(name = "cmcd_keyid", length = 15)
    private String keyid;

    @Column(name = "cmcd_pmstandardid", length = 12, nullable = false)
    private String pmstandardid;

    @Column(name = "cmcd_pmcalendarid", length = 12, nullable = false)
    private String pmcalendarid;

    @Column(name = "cmcd_wofeedbackid", length = 14, nullable = false)
    private String wofeedbackid;

    @Column(name = "cmcd_pmentrytype", length = 1, nullable = false)
    private String pmentrytype;

    @Column(name = "cmcd_pmjobtype", length = 3, nullable = false)
    private String pmjobtype;

    @Column(name = "cmcd_activitydate", nullable = false)
    private LocalDateTime activitydate;

    @Column(name = "cmcd_minimumreading", nullable = false)
    private BigDecimal minimumreading;

    @Column(name = "cmcd_maximumreading", nullable = false)
    private BigDecimal maximumreading;

    @Column(name = "cmcd_adjustedreading", nullable = false)
    private BigDecimal adjustedreading;

    @Column(name = "cmcd_currentreading", nullable = false)
    private BigDecimal currentreading;

    @Column(name = "cmcd_nextduedate", nullable = false)
    private LocalDateTime nextduedate;

    @Column(name = "cmcd_middlemax", length = 15, nullable = false)
    private String middlemax;

    @Column(name = "cmcd_tempfield1", length = 15, nullable = false)
    private String tempfield1;

    @Column(name = "cmcd_tempfield2", length = 500, nullable = false)
    private String tempfield2;

    @Column(name = "cmcd_tempfield3", length = 500, nullable = false)
    private String tempfield3;

    @Column(name = "cmcd_tempfield4", length = 500, nullable = false)
    private String tempfield4;

    @Column(name = "cmcd_active", length = 1, nullable = false)
    private String active;

    @Column(name = "cmcd_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "cmcd_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "cmcd_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getPmstandardid() {
        return pmstandardid;
    }

    public void setPmstandardid(String pmstandardid) {
        this.pmstandardid = pmstandardid;
    }

    public String getPmcalendarid() {
        return pmcalendarid;
    }

    public void setPmcalendarid(String pmcalendarid) {
        this.pmcalendarid = pmcalendarid;
    }

    public String getWofeedbackid() {
        return wofeedbackid;
    }

    public void setWofeedbackid(String wofeedbackid) {
        this.wofeedbackid = wofeedbackid;
    }

    public String getPmentrytype() {
        return pmentrytype;
    }

    public void setPmentrytype(String pmentrytype) {
        this.pmentrytype = pmentrytype;
    }

    public String getPmjobtype() {
        return pmjobtype;
    }

    public void setPmjobtype(String pmjobtype) {
        this.pmjobtype = pmjobtype;
    }

    public LocalDateTime getActivitydate() {
        return activitydate;
    }

    public void setActivitydate(LocalDateTime activitydate) {
        this.activitydate = activitydate;
    }

    public BigDecimal getMinimumreading() {
        return minimumreading;
    }

    public void setMinimumreading(BigDecimal minimumreading) {
        this.minimumreading = minimumreading;
    }

    public BigDecimal getMaximumreading() {
        return maximumreading;
    }

    public void setMaximumreading(BigDecimal maximumreading) {
        this.maximumreading = maximumreading;
    }

    public BigDecimal getAdjustedreading() {
        return adjustedreading;
    }

    public void setAdjustedreading(BigDecimal adjustedreading) {
        this.adjustedreading = adjustedreading;
    }

    public BigDecimal getCurrentreading() {
        return currentreading;
    }

    public void setCurrentreading(BigDecimal currentreading) {
        this.currentreading = currentreading;
    }

    public LocalDateTime getNextduedate() {
        return nextduedate;
    }

    public void setNextduedate(LocalDateTime nextduedate) {
        this.nextduedate = nextduedate;
    }

    public String getMiddlemax() {
        return middlemax;
    }

    public void setMiddlemax(String middlemax) {
        this.middlemax = middlemax;
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