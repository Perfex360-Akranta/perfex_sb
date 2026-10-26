package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bal_cli_tl_standards", schema = "public")
public class CliTlStandards {

    @Id
    @Column(name = "clis_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "clis_date")
    private LocalDateTime date;

    @Column(name = "clis_factoryid", length = 10)
    private String factoryid;

    @Column(name = "clis_sectionid", length = 10)
    private String sectionid;

    @Column(name = "clis_cellid", length = 10)
    private String cellid;

    @Column(name = "clis_machineid", length = 10)
    private String machineid;

    @Column(name = "clis_assemblyid", length = 12)
    private String assemblyid;

    @Column(name = "clis_phenomenaid", length = 12)
    private String phenomenaid;

    @Column(name = "clis_causeid", length = 12)
    private String causeid;

    @Column(name = "clis_jhid", length = 12)
    private String jhid;

    @Column(name = "clis_tradeid", length = 15)
    private String tradeid;

    @Column(name = "clis_shiftid", length = 6)
    private String shiftid;

    @Column(name = "clis_time", length = 100)
    private String time;

    @Column(name = "clis_effectivedate")
    private LocalDateTime effectivedate;

    @Column(name = "clis_formatno", length = 30)
    private String formatno;

    @Column(name = "clis_issueno", length = 30)
    private String issueno;

    @Column(name = "clis_issuedate")
    private LocalDateTime issuedate;

    @Column(name = "clis_refdoctype", length = 6)
    private String refdoctype;

    @Column(name = "clis_refdocno", length = 20)
    private String refdocno;

    @Column(name = "clis_departmentmgr", length = 12)
    private String departmentmgr;

    @Column(name = "clis_sectionmgr", length = 10)
    private String sectionmgr;

    @Column(name = "clis_groupleader", length = 12)
    private String groupleader;

    @Column(name = "clis_groupno", length = 200)
    private String groupno;

    @Column(name = "clis_activitytype", length = 3)
    private String activitytype;

    @Column(name = "clis_howmuchduration")
    private Double howmuchduration;

    @Column(name = "clis_correctiveaction", length = 500)
    private String correctiveaction;

    @Column(name = "clis_whatactivity", length = 500)
    private String whatactivity;

    @Column(name = "clis_wherelocation", length = 500)
    private String wherelocation;

    @Column(name = "clis_standard", length = 500)
    private String standard;

    @Column(name = "clis_whyifnotdone", length = 500)
    private String whyifnotdone;

    @Column(name = "clis_frequencyunit", length = 1)
    private Character frequencyunit;

    @Column(name = "clis_frequency")
    private Double frequency;

    @Column(name = "clis_responsibilityid", length = 12)
    private String responsibilityid;

    @Column(name = "clis_responsibilitydesgid", length = 12)
    private String responsibilitydesgid;

    @Column(name = "clis_istoolsreq", length = 1)
    private Character istoolsreq;

    @Column(name = "clis_howmethod", length = 500)
    private String howmethod;

    @Column(name = "clis_startdate")
    private LocalDate startdate;

    @Column(name = "clis_startweekno")
    private Integer startweekno;

    @Column(name = "clis_lastdonedate")
    private LocalDate lastdonedate;

    @Column(name = "clis_lastweekno")
    private Integer lastweekno;

    @Column(name = "clis_nextduedate")
    private LocalDate nextduedate;

    @Column(name = "clis_nextdueweekno")
    private Integer nextdueweekno;

    @Column(name = "clis_monthweekno")
    private Integer monthweekno;

    @Column(name = "clis_preparedbyid", length = 12)
    private String preparedbyid;

    @Column(name = "clis_wogenflag", length = 1)
    private Character wogenflag;

    @Column(name = "clis_lastwoid", length = 14)
    private String lastwoid;

    @Column(name = "clis_lastwogendate")
    private LocalDateTime lastwogendate;

    @Column(name = "clis_lastfeedbackid", length = 14)
    private String lastfeedbackid;

    @Column(name = "clis_lastfeedbackdate")
    private LocalDateTime lastfeedbackdate;

    @Column(name = "clis_inactivateddate")
    private LocalDateTime inactivateddate;

    @Column(name = "clis_elementid", length = 250)
    private String elementid;

    @Column(name = "clis_flid", length = 12)
    private String flid;

    @Column(name = "clis_active", length = 1)
    private Character active = 'Y';

    @Column(name = "clis_createdby", length = 8)
    private String createdby;

    @Column(name = "clis_createdon")
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "clis_modifiedon")
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() { return keyid; }
    public void setKeyid(String keyid) { this.keyid = keyid; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getFactoryid() { return factoryid; }
    public void setFactoryid(String factoryid) { this.factoryid = factoryid; }

    public String getSectionid() { return sectionid; }
    public void setSectionid(String sectionid) { this.sectionid = sectionid; }

    public String getCellid() { return cellid; }
    public void setCellid(String cellid) { this.cellid = cellid; }

    public String getMachineid() { return machineid; }
    public void setMachineid(String machineid) { this.machineid = machineid; }

    public String getAssemblyid() { return assemblyid; }
    public void setAssemblyid(String assemblyid) { this.assemblyid = assemblyid; }

    public String getPhenomenaid() { return phenomenaid; }
    public void setPhenomenaid(String phenomenaid) { this.phenomenaid = phenomenaid; }

    public String getCauseid() { return causeid; }
    public void setCauseid(String causeid) { this.causeid = causeid; }

    public String getJhid() { return jhid; }
    public void setJhid(String jhid) { this.jhid = jhid; }

    public String getTradeid() { return tradeid; }
    public void setTradeid(String tradeid) { this.tradeid = tradeid; }

    public String getShiftid() { return shiftid; }
    public void setShiftid(String shiftid) { this.shiftid = shiftid; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public LocalDateTime getEffectivedate() { return effectivedate; }
    public void setEffectivedate(LocalDateTime effectivedate) { this.effectivedate = effectivedate; }

    public String getFormatno() { return formatno; }
    public void setFormatno(String formatno) { this.formatno = formatno; }

    public String getIssueno() { return issueno; }
    public void setIssueno(String issueno) { this.issueno = issueno; }

    public LocalDateTime getIssuedate() { return issuedate; }
    public void setIssuedate(LocalDateTime issuedate) { this.issuedate = issuedate; }

    public String getRefdoctype() { return refdoctype; }
    public void setRefdoctype(String refdoctype) { this.refdoctype = refdoctype; }

    public String getRefdocno() { return refdocno; }
    public void setRefdocno(String refdocno) { this.refdocno = refdocno; }

    public String getDepartmentmgr() { return departmentmgr; }
    public void setDepartmentmgr(String departmentmgr) { this.departmentmgr = departmentmgr; }

    public String getSectionmgr() { return sectionmgr; }
    public void setSectionmgr(String sectionmgr) { this.sectionmgr = sectionmgr; }

    public String getGroupleader() { return groupleader; }
    public void setGroupleader(String groupleader) { this.groupleader = groupleader; }

    public String getGroupno() { return groupno; }
    public void setGroupno(String groupno) { this.groupno = groupno; }

    public String getActivitytype() { return activitytype; }
    public void setActivitytype(String activitytype) { this.activitytype = activitytype; }

    public Double getHowmuchduration() { return howmuchduration; }
    public void setHowmuchduration(Double howmuchduration) { this.howmuchduration = howmuchduration; }

    public String getCorrectiveaction() { return correctiveaction; }
    public void setCorrectiveaction(String correctiveaction) { this.correctiveaction = correctiveaction; }

    public String getWhatactivity() { return whatactivity; }
    public void setWhatactivity(String whatactivity) { this.whatactivity = whatactivity; }

    public String getWherelocation() { return wherelocation; }
    public void setWherelocation(String wherelocation) { this.wherelocation = wherelocation; }

    public String getStandard() { return standard; }
    public void setStandard(String standard) { this.standard = standard; }

    public String getWhyifnotdone() { return whyifnotdone; }
    public void setWhyifnotdone(String whyifnotdone) { this.whyifnotdone = whyifnotdone; }

    public Character getFrequencyunit() { return frequencyunit; }
    public void setFrequencyunit(Character frequencyunit) { this.frequencyunit = frequencyunit; }

    public Double getFrequency() { return frequency; }
    public void setFrequency(Double frequency) { this.frequency = frequency; }

    public String getResponsibilityid() { return responsibilityid; }
    public void setResponsibilityid(String responsibilityid) { this.responsibilityid = responsibilityid; }

    public String getResponsibilitydesgid() { return responsibilitydesgid; }
    public void setResponsibilitydesgid(String responsibilitydesgid) { this.responsibilitydesgid = responsibilitydesgid; }

    public Character getIstoolsreq() { return istoolsreq; }
    public void setIstoolsreq(Character istoolsreq) { this.istoolsreq = istoolsreq; }

    public String getHowmethod() { return howmethod; }
    public void setHowmethod(String howmethod) { this.howmethod = howmethod; }

    public LocalDate getStartdate() { return startdate; }
    public void setStartdate(LocalDate startdate) { this.startdate = startdate; }

    public Integer getStartweekno() { return startweekno; }
    public void setStartweekno(Integer startweekno) { this.startweekno = startweekno; }

    public LocalDate getLastdonedate() { return lastdonedate; }
    public void setLastdonedate(LocalDate lastdonedate) { this.lastdonedate = lastdonedate; }

    public Integer getLastweekno() { return lastweekno; }
    public void setLastweekno(Integer lastweekno) { this.lastweekno = lastweekno; }

    public LocalDate getNextduedate() { return nextduedate; }
    public void setNextduedate(LocalDate nextduedate) { this.nextduedate = nextduedate; }

    public Integer getNextdueweekno() { return nextdueweekno; }
    public void setNextdueweekno(Integer nextdueweekno) { this.nextdueweekno = nextdueweekno; }

    public Integer getMonthweekno() { return monthweekno; }
    public void setMonthweekno(Integer monthweekno) { this.monthweekno = monthweekno; }

    public String getPreparedbyid() { return preparedbyid; }
    public void setPreparedbyid(String preparedbyid) { this.preparedbyid = preparedbyid; }

    public Character getWogenflag() { return wogenflag; }
    public void setWogenflag(Character wogenflag) { this.wogenflag = wogenflag; }

    public String getLastwoid() { return lastwoid; }
    public void setLastwoid(String lastwoid) { this.lastwoid = lastwoid; }

    public LocalDateTime getLastwogendate() { return lastwogendate; }
    public void setLastwogendate(LocalDateTime lastwogendate) { this.lastwogendate = lastwogendate; }

    public String getLastfeedbackid() { return lastfeedbackid; }
    public void setLastfeedbackid(String lastfeedbackid) { this.lastfeedbackid = lastfeedbackid; }

    public LocalDateTime getLastfeedbackdate() { return lastfeedbackdate; }
    public void setLastfeedbackdate(LocalDateTime lastfeedbackdate) { this.lastfeedbackdate = lastfeedbackdate; }

    public LocalDateTime getInactivateddate() { return inactivateddate; }
    public void setInactivateddate(LocalDateTime inactivateddate) { this.inactivateddate = inactivateddate; }

    public String getElementid() { return elementid; }
    public void setElementid(String elementid) { this.elementid = elementid; }

    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }

    public Character getActive() { return active; }
    public void setActive(Character active) { this.active = active; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) { this.modifiedon = modifiedon; }
}
