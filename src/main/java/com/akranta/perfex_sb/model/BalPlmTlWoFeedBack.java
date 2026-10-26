package com.akranta.perfex_sb.model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "BAL_PLM_TL_WOFEEDBACK")

public class BalPlmTlWoFeedBack {

 @Id
    @Column(name = "wofb_feedbackid", length = 14, nullable = false)
    private String feedbackid;

    @Column(name = "wofb_wodetailid", length = 20, nullable = false)
    private String wodetailid;

    @Column(name = "wofb_feedbackdate", nullable = false)
    private LocalDateTime feedbackdate;

    @Column(name = "wofb_machineid", length = 10, nullable = false)
    private String machineid;

    @Column(name = "wofb_status", length = 1, nullable = false)
    private String status;

    @Column(name = "wofb_action", length = 500, nullable = false)
    private String action;

    @Column(name = "wofb_startdate", nullable = false)
    private LocalDateTime startdate;

    @Column(name = "wofb_enddate", nullable = false)
    private LocalDateTime enddate;

    @Column(name = "wofb_completeddate", nullable = false)
    private LocalDateTime completeddate;

    @Column(name = "wofb_duration", nullable = false)
    private BigDecimal duration;

    @Column(name = "wofb_isprodstopped", length = 1, nullable = false)
    private String isprodstopped;

    @Column(name = "wofb_prodstartdate", nullable = false)
    private LocalDateTime prodstartdate;

    @Column(name = "wofb_currentreading", nullable = false)
    private BigDecimal currentreading;

    @Column(name = "wofb_adjustedreading", nullable = false)
    private BigDecimal adjustedreading;

    @Column(name = "wofb_uom", length = 8, nullable = false)
    private String uom;

    @Column(name = "wofb_whywhyflag", length = 1, nullable = false)
    private String whywhyflag;

    @Column(name = "wofb_whywhyid", length = 12, nullable = false)
    private String whywhyid;

    @Column(name = "wofb_amcflag", length = 1, nullable = false)
    private String amcflag;

    @Column(name = "wofb_amcdetailid", length = 12, nullable = false)
    private String amcdetailid;

    @Column(name = "wofb_spareflag", length = 1, nullable = false)
    private String spareflag;

    @Column(name = "wofb_sparecost", nullable = false)
    private BigDecimal sparecost;

    @Column(name = "wofb_manpowercost", nullable = false)
    private BigDecimal manpowercost;

    @Column(name = "wofb_contractorcost", nullable = false)
    private BigDecimal contractorcost;

    @Column(name = "wofb_othercost", nullable = false)
    private BigDecimal othercost;

    @Column(name = "wofb_observation", length = 500, nullable = false)
    private String observation;

    @Column(name = "wofb_feedback", length = 500, nullable = false)
    private String feedback;

    @Column(name = "wofb_completedby", length = 500, nullable = false)
    private String completedby;

    @Column(name = "wofb_rescheduleflag", length = 8, nullable = false)
    private String rescheduleflag;

    @Column(name = "wofb_reschedulereason", length = 500, nullable = false)
    private String reschedulereason;

    @Column(name = "wofb_nextinspectiondate", nullable = false)
    private LocalDateTime nextinspectiondate;

    @Column(name = "wofb_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "wofb_rootcause", length = 500, nullable = false)
    private String rootcause;

    @Column(name = "wofb_countermeasure", length = 500, nullable = false)
    private String countermeasure;

    @Column(name = "wofb_mchcondition", length = 1, nullable = false)
    private String mchcondition;

    @Column(
        name = "wofb_machinetakeovertime",
        length = 40,
        nullable = false
    )
    private String machinetakeovertime;

    @Column(name = "wofb_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "wofb_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    @Column(name = "wofb_createdon", nullable = false)
    private LocalDateTime createdon;

    
    // private BalPlmTlSpareconsumed spareconsumed;

    // private BalPlmTlSparecostactual sparecostactual;


    public BalPlmTlWoFeedBack() {
    }

    


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


    @Transient
    private LocalDateTime obsvTargetDate = null;
    @Transient
    private String obsvResponsibility = null;
    @Transient
    private BigDecimal cbmReadingValue = null;

    @Transient
    private LocalDateTime cbmNextDueDate = null;
    @Transient
    private BigDecimal cbmMinReading = null;
    @Transient
    private BigDecimal cbmMaxReading = null;
    @Transient
    private BigDecimal cbmAdjustedReading = null;
    @Transient
    private String pmstandId = null;
    @Transient
    private String pmCalendarId = null;

    

    public LocalDateTime getObsvTargetDate() {
        return obsvTargetDate;
    }

    public void setObsvTargetDate(LocalDateTime obsvTargetDate) {
        this.obsvTargetDate = obsvTargetDate;
    }

    public String getObsvResponsibility() {
        return obsvResponsibility;
    }

    public void setObsvResponsibility(String obsvResponsibility) {
        this.obsvResponsibility = obsvResponsibility;
    }






    public BigDecimal getCbmReadingValue() {
        return cbmReadingValue;
    }




    public void setCbmReadingValue(BigDecimal cbmReadingValue) {
        this.cbmReadingValue = cbmReadingValue;
    }




    public LocalDateTime getCbmNextDueDate() {
        return cbmNextDueDate;
    }




    public void setCbmNextDueDate(LocalDateTime cbmNextDueDate) {
        this.cbmNextDueDate = cbmNextDueDate;
    }




    public BigDecimal getCbmMinReading() {
        return cbmMinReading;
    }




    public void setCbmMinReading(BigDecimal cbmMinReading) {
        this.cbmMinReading = cbmMinReading;
    }




    public BigDecimal getCbmMaxReading() {
        return cbmMaxReading;
    }




    public void setCbmMaxReading(BigDecimal cbmMaxReading) {
        this.cbmMaxReading = cbmMaxReading;
    }




    public BigDecimal getCbmAdjustedReading() {
        return cbmAdjustedReading;
    }




    public void setCbmAdjustedReading(BigDecimal cbmAdjustedReading) {
        this.cbmAdjustedReading = cbmAdjustedReading;
    }




    public String getPmstandId() {
        return pmstandId;
    }




    public void setPmstandId(String pmstandId) {
        this.pmstandId = pmstandId;
    }




    public String getPmCalendarId() {
        return pmCalendarId;
    }




    public void setPmCalendarId(String pmCalendarId) {
        this.pmCalendarId = pmCalendarId;
    }





    

}

