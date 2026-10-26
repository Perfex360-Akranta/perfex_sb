package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bal_bdm_tl_dtl", schema = "public")
public class BAL_BdmTlDtl {

    @Id
    @Column(name = "bdan_keyid", length = 14, nullable = false)
    private String keyid;

    @Column(name = "bdan_bdms_keyid", length = 20, nullable = false, unique = true)
    private String bdms_keyid;

    @Column(name = "bdan_finalphenomena", length = 8, nullable = false)
    private String finalphenomena;

    @Column(name = "bdan_finalcause", length = 500, nullable = false)
    private String finalcause;

    @Column(name = "bdan_finalaction", length = 500, nullable = false)
    private String finalaction;

    @Column(name = "bdan_tradeid", length = 15, nullable = false)
    private String tradeid;

    @Column(name = "bdan_countermeasure", length = 500, nullable = false)
    private String countermeasure;

    @Column(name = "bdan_wwrequired", length = 1, nullable = false)
    private Character wwrequired;

    @Column(name = "bdan_wwno", length = 12, nullable = false)
    private String wwno;

    @Column(name = "bdan_rootcause", length = 500, nullable = false)
    private String rootcause;

    @Column(name = "bdan_preventivemeasure", length = 500, nullable = false)
    private String preventivemeasure;

    @Column(name = "bdan_rootcauseid", length = 8, nullable = false)
    private String rootcauseid;

    @Column(name = "bdan_countermeasureid", length = 8, nullable = false)
    private String countermeasureid;

    @Column(name = "bdan_preventivemeasureid", length = 500, nullable = false)
    private String preventivemeasureid;

    @Column(name = "bdan_breakdowntime", nullable = false)
    private BigDecimal breakdowntime;

    @Column(name = "bdan_worktime", nullable = false)
    private BigDecimal worktime;

    @Column(name = "bdan_classificationid", length = 12, nullable = false)
    private String classificationid;

    @Column(name = "bdan_categoryid", length = 12, nullable = false)
    private String categoryid;

    @Column(name = "bdan_issparesreplaced", length = 1, nullable = false)
    private Character issparesreplaced;

    @Column(name = "bdan_alarmno", length = 15, nullable = false)
    private String alarmno;

    @Column(name = "bdan_manpowercost", nullable = false)
    private BigDecimal manpowercost;

    @Column(name = "bdan_contractorcost", nullable = false)
    private BigDecimal contractorcost;

    @Column(name = "bdan_sparescost", nullable = false)
    private BigDecimal sparescost;

    @Column(name = "bdan_othercost", nullable = false)
    private BigDecimal othercost;

    @Column(name = "bdan_status", length = 1, nullable = false)
    private Character status;

    @Column(name = "bdan_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "bdan_actiontakenby", length = 8, nullable = false)
    private String actiontakenby;

    @Column(name = "bdan_completedby", length = 8, nullable = false)
    private String completedby;

    @Column(name = "bdan_costcentre", length = 20, nullable = false)
    private String costcentre;

    @Column(name = "bdan_erppoststatus", length = 1, nullable = false)
    private Character erppoststatus;

    @Column(name = "bdan_problemseverity", length = 20, nullable = false)
    private String problemseverity;

    @Column(name = "bdan_failuretype", length = 20, nullable = false)
    private String failuretype;

    @Column(name = "bdan_isapproved", length = 1, nullable = false)
    private Character isapproved;

    @Column(name = "bdan_approverdby", length = 15, nullable = false)
    private String approverdby;

    @Column(name = "bdan_erpnumber", length = 12, nullable = false)
    private String erpnumber;

    @Column(name = "bdan_otherfailuretype", length = 150, nullable = false)
    private String otherfailuretype;

    @Column(name = "bdan_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "bdan_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "bdan_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "bdan_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getBdms_keyid() {
        return bdms_keyid;
    }

    public void setBdms_keyid(String bdms_keyid) {
        this.bdms_keyid = bdms_keyid;
    }

    public String getFinalphenomena() {
        return finalphenomena;
    }

    public void setFinalphenomena(String finalphenomena) {
        this.finalphenomena = finalphenomena;
    }

    public String getFinalcause() {
        return finalcause;
    }

    public void setFinalcause(String finalcause) {
        this.finalcause = finalcause;
    }

    public String getFinalaction() {
        return finalaction;
    }

    public void setFinalaction(String finalaction) {
        this.finalaction = finalaction;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getCountermeasure() {
        return countermeasure;
    }

    public void setCountermeasure(String countermeasure) {
        this.countermeasure = countermeasure;
    }

    public Character getWwrequired() {
        return wwrequired;
    }

    public void setWwrequired(Character wwrequired) {
        this.wwrequired = wwrequired;
    }

    public String getWwno() {
        return wwno;
    }

    public void setWwno(String wwno) {
        this.wwno = wwno;
    }

    public String getRootcause() {
        return rootcause;
    }

    public void setRootcause(String rootcause) {
        this.rootcause = rootcause;
    }

    public String getPreventivemeasure() {
        return preventivemeasure;
    }

    public void setPreventivemeasure(String preventivemeasure) {
        this.preventivemeasure = preventivemeasure;
    }

    public String getRootcauseid() {
        return rootcauseid;
    }

    public void setRootcauseid(String rootcauseid) {
        this.rootcauseid = rootcauseid;
    }

    public String getCountermeasureid() {
        return countermeasureid;
    }

    public void setCountermeasureid(String countermeasureid) {
        this.countermeasureid = countermeasureid;
    }

    public String getPreventivemeasureid() {
        return preventivemeasureid;
    }

    public void setPreventivemeasureid(String preventivemeasureid) {
        this.preventivemeasureid = preventivemeasureid;
    }

    public BigDecimal getBreakdowntime() {
        return breakdowntime;
    }

    public void setBreakdowntime(BigDecimal breakdowntime) {
        this.breakdowntime = breakdowntime;
    }

    public BigDecimal getWorktime() {
        return worktime;
    }

    public void setWorktime(BigDecimal worktime) {
        this.worktime = worktime;
    }

    public String getClassificationid() {
        return classificationid;
    }

    public void setClassificationid(String classificationid) {
        this.classificationid = classificationid;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public Character getIssparesreplaced() {
        return issparesreplaced;
    }

    public void setIssparesreplaced(Character issparesreplaced) {
        this.issparesreplaced = issparesreplaced;
    }

    public String getAlarmno() {
        return alarmno;
    }

    public void setAlarmno(String alarmno) {
        this.alarmno = alarmno;
    }

    public BigDecimal getManpowercost() {
        return manpowercost;
    }

    public void setManpowercost(BigDecimal manpowercost) {
        this.manpowercost = manpowercost;
    }

    public BigDecimal getContractorcost() {
        return contractorcost;
    }

    public void setContractorcost(BigDecimal contractorcost) {
        this.contractorcost = contractorcost;
    }

    public BigDecimal getSparescost() {
        return sparescost;
    }

    public void setSparescost(BigDecimal sparescost) {
        this.sparescost = sparescost;
    }

    public BigDecimal getOthercost() {
        return othercost;
    }

    public void setOthercost(BigDecimal othercost) {
        this.othercost = othercost;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getActiontakenby() {
        return actiontakenby;
    }

    public void setActiontakenby(String actiontakenby) {
        this.actiontakenby = actiontakenby;
    }

    public String getCompletedby() {
        return completedby;
    }

    public void setCompletedby(String completedby) {
        this.completedby = completedby;
    }

    public String getCostcentre() {
        return costcentre;
    }

    public void setCostcentre(String costcentre) {
        this.costcentre = costcentre;
    }

    public Character getErppoststatus() {
        return erppoststatus;
    }

    public void setErppoststatus(Character erppoststatus) {
        this.erppoststatus = erppoststatus;
    }

    public String getProblemseverity() {
        return problemseverity;
    }

    public void setProblemseverity(String problemseverity) {
        this.problemseverity = problemseverity;
    }

    public String getFailuretype() {
        return failuretype;
    }

    public void setFailuretype(String failuretype) {
        this.failuretype = failuretype;
    }

    public Character getIsapproved() {
        return isapproved;
    }

    public void setIsapproved(Character isapproved) {
        this.isapproved = isapproved;
    }

    public String getApproverdby() {
        return approverdby;
    }

    public void setApproverdby(String approverdby) {
        this.approverdby = approverdby;
    }

    public String getErpnumber() {
        return erpnumber;
    }

    public void setErpnumber(String erpnumber) {
        this.erpnumber = erpnumber;
    }

    public String getOtherfailuretype() {
        return otherfailuretype;
    }

    public void setOtherfailuretype(String otherfailuretype) {
        this.otherfailuretype = otherfailuretype;
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