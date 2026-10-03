package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bal_plm_tl_wofeedback_entry", schema = "public")
public class BalPlmTlWofeedbackEntry {


    @Id
    @Column(name = "wofbe_feedbackid", length = 14, nullable = false)
    private String feedbackid;

    @Column(name = "wofbe_wodetailid", length = 20, nullable = false)
    private String wodetailid;

    @Column(name = "wofbe_feedbackdate", nullable = false)
    private LocalDateTime feedbackdate;

    @Column(name = "wofbe_machineid", length = 10, nullable = false)
    private String machineid;

    @Column(name = "wofbe_status", length = 1, nullable = false)
    private String status;

    @Column(name = "wofbe_action", length = 500, nullable = false)
    private String action;

    @Column(name = "wofbe_startdate", nullable = false)
    private LocalDateTime startdate;

    @Column(name = "wofbe_enddate", nullable = false)
    private LocalDateTime enddate;

    @Column(name = "wofbe_completeddate", nullable = false)
    private LocalDateTime completeddate;

    @Column(name = "wofbe_duration", nullable = false)
    private BigDecimal duration;

    @Column(name = "wofbe_isprodstopped", length = 1, nullable = false)
    private String isprodstopped;

    @Column(name = "wofbe_prodstartdate", nullable = false)
    private LocalDateTime prodstartdate;

    @Column(name = "wofbe_currentreading", nullable = false)
    private BigDecimal currentreading;

    @Column(name = "wofbe_adjustedreading", nullable = false)
    private BigDecimal adjustedreading;

    @Column(name = "wofbe_uom", length = 8, nullable = false)
    private String uom;

    @Column(name = "wofbe_whywhyflag", length = 1, nullable = false)
    private String whywhyflag;

    @Column(name = "wofbe_whywhyid", length = 12, nullable = false)
    private String whywhyid;

    @Column(name = "wofbe_amcflag", length = 1, nullable = false)
    private String amcflag;

    @Column(name = "wofbe_amcdetailid", length = 12, nullable = false)
    private String amcdetailid;

    @Column(name = "wofbe_spareflag", length = 1, nullable = false)
    private String spareflag;

    @Column(name = "wofbe_sparecost", nullable = false)
    private BigDecimal sparecost;

    @Column(name = "wofbe_manpowercost", nullable = false)
    private BigDecimal manpowercost;

    @Column(name = "wofbe_contractorcost", nullable = false)
    private BigDecimal contractorcost;

    @Column(name = "wofbe_othercost", nullable = false)
    private BigDecimal othercost;

    @Column(name = "wofbe_observation", length = 500, nullable = false)
    private String observation;

    @Column(name = "wofbe_feedback", length = 500, nullable = false)
    private String feedback;

    @Column(name = "wofbe_completedby", length = 500, nullable = false)
    private String completedby;

    @Column(name = "wofbe_rescheduleflag", length = 8, nullable = false)
    private String rescheduleflag;

    @Column(name = "wofbe_reschedulereason", length = 500, nullable = false)
    private String reschedulereason;

    @Column(name = "wofbe_nextinspectiondate", nullable = false)
    private LocalDateTime nextinspectiondate;

    @Column(name = "wofbe_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "wofbe_rootcause", length = 500, nullable = false)
    private String rootcause;

    @Column(name = "wofbe_countermeasure", length = 500, nullable = false)
    private String countermeasure;

    @Column(name = "wofbe_mchcondition", length = 1, nullable = false)
    private String mchcondition;

    @Column(name = "wofbe_machinetakeovertime", length = 40, nullable = false)
    private String machinetakeovertime;

    @Column(name = "wofbe_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "wofbe_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    @Column(name = "wofbe_createdon", nullable = false)
    private LocalDateTime createdon;

    public String getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(String feedbackid) {
        this.feedbackid = feedbackid;
    }

    public String getWodetailid() {
        return wodetailid;
    }

    public void setWodetailid(String wodetailid) {
        this.wodetailid = wodetailid;
    }

    public LocalDateTime getFeedbackdate() {
        return feedbackdate;
    }

    public void setFeedbackdate(LocalDateTime feedbackdate) {
        this.feedbackdate = feedbackdate;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

    public LocalDateTime getCompleteddate() {
        return completeddate;
    }

    public void setCompleteddate(LocalDateTime completeddate) {
        this.completeddate = completeddate;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public String getIsprodstopped() {
        return isprodstopped;
    }

    public void setIsprodstopped(String isprodstopped) {
        this.isprodstopped = isprodstopped;
    }

    public LocalDateTime getProdstartdate() {
        return prodstartdate;
    }

    public void setProdstartdate(LocalDateTime prodstartdate) {
        this.prodstartdate = prodstartdate;
    }

    public BigDecimal getCurrentreading() {
        return currentreading;
    }

    public void setCurrentreading(BigDecimal currentreading) {
        this.currentreading = currentreading;
    }

    public BigDecimal getAdjustedreading() {
        return adjustedreading;
    }

    public void setAdjustedreading(BigDecimal adjustedreading) {
        this.adjustedreading = adjustedreading;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getWhywhyflag() {
        return whywhyflag;
    }

    public void setWhywhyflag(String whywhyflag) {
        this.whywhyflag = whywhyflag;
    }

    public String getWhywhyid() {
        return whywhyid;
    }

    public void setWhywhyid(String whywhyid) {
        this.whywhyid = whywhyid;
    }

    public String getAmcflag() {
        return amcflag;
    }

    public void setAmcflag(String amcflag) {
        this.amcflag = amcflag;
    }

    public String getAmcdetailid() {
        return amcdetailid;
    }

    public void setAmcdetailid(String amcdetailid) {
        this.amcdetailid = amcdetailid;
    }

    public String getSpareflag() {
        return spareflag;
    }

    public void setSpareflag(String spareflag) {
        this.spareflag = spareflag;
    }

    public BigDecimal getSparecost() {
        return sparecost;
    }

    public void setSparecost(BigDecimal sparecost) {
        this.sparecost = sparecost;
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

    public BigDecimal getOthercost() {
        return othercost;
    }

    public void setOthercost(BigDecimal othercost) {
        this.othercost = othercost;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCompletedby() {
        return completedby;
    }

    public void setCompletedby(String completedby) {
        this.completedby = completedby;
    }

    public String getRescheduleflag() {
        return rescheduleflag;
    }

    public void setRescheduleflag(String rescheduleflag) {
        this.rescheduleflag = rescheduleflag;
    }

    public String getReschedulereason() {
        return reschedulereason;
    }

    public void setReschedulereason(String reschedulereason) {
        this.reschedulereason = reschedulereason;
    }

    public LocalDateTime getNextinspectiondate() {
        return nextinspectiondate;
    }

    public void setNextinspectiondate(LocalDateTime nextinspectiondate) {
        this.nextinspectiondate = nextinspectiondate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRootcause() {
        return rootcause;
    }

    public void setRootcause(String rootcause) {
        this.rootcause = rootcause;
    }

    public String getCountermeasure() {
        return countermeasure;
    }

    public void setCountermeasure(String countermeasure) {
        this.countermeasure = countermeasure;
    }

    public String getMchcondition() {
        return mchcondition;
    }

    public void setMchcondition(String mchcondition) {
        this.mchcondition = mchcondition;
    }

    public String getMachinetakeovertime() {
        return machinetakeovertime;
    }

    public void setMachinetakeovertime(String machinetakeovertime) {
        this.machinetakeovertime = machinetakeovertime;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDateTime getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(LocalDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }

    public LocalDateTime getCreatedon() {
        return createdon;
    }

    public void setCreatedon(LocalDateTime createdon) {
        this.createdon = createdon;
    }



    
    
}