package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bal_plm_tl_calendar")
public class BalPlmTlCalendar {

    @Id
    @Column(name = "pmcl_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "pmcl_factoryid", length = 10, nullable = false)
    private String factoryid;

    @Column(name = "pmcl_sectionid", length = 10, nullable = false)
    private String sectionid;

    @Column(name = "pmcl_cellid", length = 10, nullable = false)
    private String cellid;

    @Column(name = "pmcl_machineid", length = 10, nullable = false)
    private String machineid;

    @Column(name = "pmcl_assemblyid", length = 15, nullable = false)
    private String assemblyid;

    @Column(name = "pmcl_subassemblyid", length = 15, nullable = false)
    private String subassemblyid;

    @Column(name = "pmcl_entrytype", length = 1, nullable = false)
    private String entrytype;

    @Column(name = "pmcl_pmfreq", length = 1, nullable = false)
    private String pmfreq;

    @Column(name = "pmcl_pmrefid", length = 15, nullable = false)
    private String pmrefid;

    @Column(name = "pmcl_jobtype", length = 3, nullable = false)
    private String jobtype;

    @Column(name = "pmcl_pmsource", length = 1, nullable = false)
    private String pmsource;

    @Column(name = "pmcl_tradeid", length = 15, nullable = false)
    private String tradeid;

    @Column(name = "pmcl_workorderid", length = 20, nullable = false)
    private String workorderid;

    @Column(name = "pmcl_whatactivity", length = 500, nullable = false)
    private String whatactivity;

    @Column(name = "pmcl_calendaryear", nullable = false)
    private Integer calendaryear;

    @Column(name = "pmcl_monthweek", nullable = false)
    private Integer monthweek;

    @Column(name = "pmcl_fromdate", nullable = false)
    private LocalDateTime fromdate;

    @Column(name = "pmcl_tilldate", nullable = false)
    private LocalDateTime tilldate;

    @Column(name = "pmcl_maxcompletiondate", nullable = false)
    private LocalDateTime maxcompletiondate;

    @Column(name = "pmcl_scheduledfrom", nullable = false)
    private LocalDateTime scheduledfrom;

    @Column(name = "pmcl_scheduledtill", nullable = false)
    private LocalDateTime scheduledtill;

    @Column(name = "pmcl_scheduledweek", nullable = false)
    private Integer scheduledweek;

    @Column(name = "pmcl_allottedto", length = 9, nullable = false)
    private String allottedto;

    @Column(name = "pmcl_status", length = 1, nullable = false)
    private String status;

    @Column(name = "pmcl_completedby", length = 9, nullable = false)
    private String completedby;

    @Column(name = "pmcl_feedbackid", length = 14, nullable = false)
    private String feedbackid;

    @Column(name = "pmcl_feedbackduedate", nullable = false)
    private LocalDateTime feedbackduedate;

    @Column(name = "pmcl_feedbackdate", nullable = false)
    private LocalDateTime feedbackdate;

    @Column(name = "pmcl_effplancompdate", nullable = false)
    private LocalDateTime effplancompdate;

    @Column(name = "pmcl_worksummaryid", length = 14, nullable = false)
    private String worksummaryid;

    @Column(name = "pmcl_duration", nullable = false)
    private Double duration;

    @Column(name = "pmcl_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "pmcl_machinecond", length = 10, nullable = false)
    private String machinecond;

    @Column(name = "pmcl_downtime", nullable = false)
    private Double downtime;

    @Column(name = "pmcl_starttime", nullable = false)
    private LocalDateTime starttime;

    @Column(name = "pmcl_endtime", nullable = false)
    private LocalDateTime endtime;

    @Column(name = "pmcl_responsibility", length = 8, nullable = false)
    private String responsibility;

    @Column(name = "pmcl_frequencyvalue", nullable = false)
    private Double frequencyvalue;

    @Column(name = "pmcl_issparereq", length = 1, nullable = false)
    private String issparereq;

    @Column(name = "pmcl_istoolsreq", length = 1, nullable = false)
    private String istoolsreq;

    @Column(name = "pmcl_relatedto", length = 3, nullable = false)
    private String relatedto;

    @Column(name = "pmcl_mouldid", length = 10, nullable = false)
    private String mouldid;

    @Column(name = "pmcl_locationid", length = 10, nullable = false)
    private String locationid;

    @Column(name = "pmcl_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "pmcl_tempfield5", length = 500, nullable = false)
    private String tempfield5;

    @Column(name = "pmcl_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "pmcl_active", length = 1, nullable = false)
    private String active;

    @Column(name = "pmcl_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "pmcl_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "pmcl_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFactoryid() {
        return factoryid;
    }

    public void setFactoryid(String factoryid) {
        this.factoryid = factoryid;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public String getAssemblyid() {
        return assemblyid;
    }

    public void setAssemblyid(String assemblyid) {
        this.assemblyid = assemblyid;
    }

    public String getSubassemblyid() {
        return subassemblyid;
    }

    public void setSubassemblyid(String subassemblyid) {
        this.subassemblyid = subassemblyid;
    }

    public String getEntrytype() {
        return entrytype;
    }

    public void setEntrytype(String entrytype) {
        this.entrytype = entrytype;
    }

    public String getPmfreq() {
        return pmfreq;
    }

    public void setPmfreq(String pmfreq) {
        this.pmfreq = pmfreq;
    }

    public String getPmrefid() {
        return pmrefid;
    }

    public void setPmrefid(String pmrefid) {
        this.pmrefid = pmrefid;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getPmsource() {
        return pmsource;
    }

    public void setPmsource(String pmsource) {
        this.pmsource = pmsource;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getWorkorderid() {
        return workorderid;
    }

    public void setWorkorderid(String workorderid) {
        this.workorderid = workorderid;
    }

    public String getWhatactivity() {
        return whatactivity;
    }

    public void setWhatactivity(String whatactivity) {
        this.whatactivity = whatactivity;
    }

    public Integer getCalendaryear() {
        return calendaryear;
    }

    public void setCalendaryear(Integer calendaryear) {
        this.calendaryear = calendaryear;
    }

    public Integer getMonthweek() {
        return monthweek;
    }

    public void setMonthweek(Integer monthweek) {
        this.monthweek = monthweek;
    }

    public LocalDateTime getFromdate() {
        return fromdate;
    }

    public void setFromdate(LocalDateTime fromdate) {
        this.fromdate = fromdate;
    }

    public LocalDateTime getTilldate() {
        return tilldate;
    }

    public void setTilldate(LocalDateTime tilldate) {
        this.tilldate = tilldate;
    }

    public LocalDateTime getMaxcompletiondate() {
        return maxcompletiondate;
    }

    public void setMaxcompletiondate(LocalDateTime maxcompletiondate) {
        this.maxcompletiondate = maxcompletiondate;
    }

    public LocalDateTime getScheduledfrom() {
        return scheduledfrom;
    }

    public void setScheduledfrom(LocalDateTime scheduledfrom) {
        this.scheduledfrom = scheduledfrom;
    }

    public LocalDateTime getScheduledtill() {
        return scheduledtill;
    }

    public void setScheduledtill(LocalDateTime scheduledtill) {
        this.scheduledtill = scheduledtill;
    }

    public Integer getScheduledweek() {
        return scheduledweek;
    }

    public void setScheduledweek(Integer scheduledweek) {
        this.scheduledweek = scheduledweek;
    }

    public String getAllottedto() {
        return allottedto;
    }

    public void setAllottedto(String allottedto) {
        this.allottedto = allottedto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompletedby() {
        return completedby;
    }

    public void setCompletedby(String completedby) {
        this.completedby = completedby;
    }

    public String getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(String feedbackid) {
        this.feedbackid = feedbackid;
    }

    public LocalDateTime getFeedbackduedate() {
        return feedbackduedate;
    }

    public void setFeedbackduedate(LocalDateTime feedbackduedate) {
        this.feedbackduedate = feedbackduedate;
    }

    public LocalDateTime getFeedbackdate() {
        return feedbackdate;
    }

    public void setFeedbackdate(LocalDateTime feedbackdate) {
        this.feedbackdate = feedbackdate;
    }

    public LocalDateTime getEffplancompdate() {
        return effplancompdate;
    }

    public void setEffplancompdate(LocalDateTime effplancompdate) {
        this.effplancompdate = effplancompdate;
    }

    public String getWorksummaryid() {
        return worksummaryid;
    }

    public void setWorksummaryid(String worksummaryid) {
        this.worksummaryid = worksummaryid;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMachinecond() {
        return machinecond;
    }

    public void setMachinecond(String machinecond) {
        this.machinecond = machinecond;
    }

    public Double getDowntime() {
        return downtime;
    }

    public void setDowntime(Double downtime) {
        this.downtime = downtime;
    }

    public LocalDateTime getStarttime() {
        return starttime;
    }

    public void setStarttime(LocalDateTime starttime) {
        this.starttime = starttime;
    }

    public LocalDateTime getEndtime() {
        return endtime;
    }

    public void setEndtime(LocalDateTime endtime) {
        this.endtime = endtime;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public Double getFrequencyvalue() {
        return frequencyvalue;
    }

    public void setFrequencyvalue(Double frequencyvalue) {
        this.frequencyvalue = frequencyvalue;
    }

    public String getIssparereq() {
        return issparereq;
    }

    public void setIssparereq(String issparereq) {
        this.issparereq = issparereq;
    }

    public String getIstoolsreq() {
        return istoolsreq;
    }

    public void setIstoolsreq(String istoolsreq) {
        this.istoolsreq = istoolsreq;
    }

    public String getRelatedto() {
        return relatedto;
    }

    public void setRelatedto(String relatedto) {
        this.relatedto = relatedto;
    }

    public String getMouldid() {
        return mouldid;
    }

    public void setMouldid(String mouldid) {
        this.mouldid = mouldid;
    }

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(String tempfield5) {
        this.tempfield5 = tempfield5;
    }

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
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

    // Getters and Setters


    

}

