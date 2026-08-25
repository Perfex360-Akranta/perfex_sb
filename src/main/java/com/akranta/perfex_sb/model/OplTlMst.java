package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;




import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "opl_tl_mst", schema = "public")
public class OplTlMst {

    @Id
    @Column(name = "oplm_keyid")
    private String keyid;

    @Column(name = "oplm_date")
    private LocalDateTime date;

    @Column(name = "oplm_factoryid")
    private String factoryid;

    @Column(name = "oplm_tpmpillarid")
    private String tpmpillarid;

    @Column(name = "oplm_sectionid")
    private String sectionid;

    @Column(name = "oplm_cellid")
    private String cellid;

    @Column(name = "oplm_machineid")
    private String machineid;

    @Column(name = "oplm_theme")
    private String theme;

    @Column(name = "oplm_themecategoryid")
    private String themecategoryid;

    @Column(name = "oplm_classification", length = 6, nullable = false)
    private String classification;

    @Column(name = "oplm_classdescription")
    private String classdescription;

    @Column(name = "oplm_benefit")
    private String benefit;

    @Column(name = "oplm_type")
    private Character type;

    @Column(name = "oplm_tradeid")
    private String tradeid;

    @Column(name = "oplm_presentcondition")
    private String presentcondition;

    @Column(name = "oplm_presentimage")
    private String presentimage;

    @Column(name = "oplm_aftercondition")
    private String aftercondition;

    @Column(name = "oplm_afterimage")
    private String afterimage;

    @Column(name = "oplm_lesson")
    private String lesson;

    @Column(name = "oplm_preparedid")
    private String preparedid;

    @Column(name = "oplm_prepareddate")
    private LocalDateTime  prepareddate;

    @Column(name = "oplm_approvedid")
    private String approvedid;

    @Column(name = "oplm_approveddate")
    private LocalDateTime approveddate;

    @Column(name = "oplm_status")
    private Character status;

    @Column(name = "oplm_refdoctype")
    private String refdoctype;

    @Column(name = "oplm_refdocno")
    private String refdocno;

    @Column(name = "oplm_remarks")
    private String remarks;

    @Column(name = "oplm_relatedto")
    private String relatedto;

    @Column(name = "oplm_departmentmanager")
    private String departmentmanager;

    @Column(name = "oplm_sectionmanager")
    private String sectionmanager;

    @Column(name = "oplm_groupleader")
    private String groupleader;

    @Column(name = "oplm_requestflag")
    private Character requestflag;

    @Column(name = "oplm_related")
    private String related;

    @Column(name = "oplm_mouldid")
    private String mouldid;

    @Column(name = "oplm_isok")
    private String isok;

    @Column(name = "oplm_ispresent")
    private String ispresent;

    @Column(name = "oplm_elementid")
    private String elementid;

    @Column(name = "oplm_flid")
    private String flid;

    @Column(name = "oplm_process")
    private String process;

    @Column(name = "oplm_isupload")
    private Character isupload;

    @Column(name = "oplm_utiliseforfuture")
    private Character utiliseforfuture;

    @Column(name = "oplm_mpworthy")
    private Character mpworthy;

    @Column(name = "oplm_aprov_level")
    private String aprovLevel;

    @Column(name = "oplm_isgeneral")
    private String isgeneral;

    @Column(name = "oplm_oplupload")
    private Character oplupload;

    @Column(name = "oplm_tempfield4")
    private Character tempfield4;

    @Column(name = "oplm_tempfield5")
    private Character tempfield5;

    @Column(name = "oplm_active")
    private Character active;

    @Column(name = "oplm_createdby")
    private String createdby;

   
    @Column(name = "oplm_createdon")
    private LocalDateTime  createdon;

  
    @Column(name = "oplm_modifiedon")
    private LocalDateTime  modifiedon;

    // -------------------------
    // Getters & Setters
    // -------------------------

    public String getKeyid() { return keyid; }
    public void setKeyid(String keyid) { this.keyid = keyid; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getFactoryid() { return factoryid; }
    public void setFactoryid(String factoryid) { this.factoryid = factoryid; }

    public String getTpmpillarid() { return tpmpillarid; }
    public void setTpmpillarid(String tpmpillarid) { this.tpmpillarid = tpmpillarid; }

    public String getSectionid() { return sectionid; }
    public void setSectionid(String sectionid) { this.sectionid = sectionid; }

    public String getCellid() { return cellid; }
    public void setCellid(String cellid) { this.cellid = cellid; }

    public String getMachineid() { return machineid; }
    public void setMachineid(String machineid) { this.machineid = machineid; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String getThemecategoryid() { return themecategoryid; }
    public void setThemecategoryid(String themecategoryid) { this.themecategoryid = themecategoryid; }

    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }

    public String getClassdescription() { return classdescription; }
    public void setClassdescription(String classdescription) { this.classdescription = classdescription; }

    public String getBenefit() { return benefit; }
    public void setBenefit(String benefit) { this.benefit = benefit; }

    public Character getType() { return type; }
    public void setType(Character type) { this.type = type; }

    public String getTradeid() { return tradeid; }
    public void setTradeid(String tradeid) { this.tradeid = tradeid; }

    public String getPresentcondition() { return presentcondition; }
    public void setPresentcondition(String presentcondition) { this.presentcondition = presentcondition; }

    public String getPresentimage() { return presentimage; }
    public void setPresentimage(String presentimage) { this.presentimage = presentimage; }

    public String getAftercondition() { return aftercondition; }
    public void setAftercondition(String aftercondition) { this.aftercondition = aftercondition; }

    public String getAfterimage() { return afterimage; }
    public void setAfterimage(String afterimage) { this.afterimage = afterimage; }

    public String getLesson() { return lesson; }
    public void setLesson(String lesson) { this.lesson = lesson; }

    public String getPreparedid() { return preparedid; }
    public void setPreparedid(String preparedid) { this.preparedid = preparedid; }

    public LocalDateTime  getPrepareddate() { return prepareddate; }
    public void setPrepareddate(LocalDateTime  prepareddate) { this.prepareddate = prepareddate; }

    public String getApprovedid() { return approvedid; }
    public void setApprovedid(String approvedid) { this.approvedid = approvedid; }

    public LocalDateTime getApproveddate() { return approveddate; }
    public void setApproveddate(LocalDateTime approveddate) { this.approveddate = approveddate; }

    public Character getStatus() { return status; }
    public void setStatus(Character status) { this.status = status; }

    public String getRefdoctype() { return refdoctype; }
    public void setRefdoctype(String refdoctype) { this.refdoctype = refdoctype; }

    public String getRefdocno() { return refdocno; }
    public void setRefdocno(String refdocno) { this.refdocno = refdocno; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getRelatedto() { return relatedto; }
    public void setRelatedto(String relatedto) { this.relatedto = relatedto; }

    public String getDepartmentmanager() { return departmentmanager; }
    public void setDepartmentmanager(String departmentmanager) { this.departmentmanager = departmentmanager; }

    public String getSectionmanager() { return sectionmanager; }
    public void setSectionmanager(String sectionmanager) { this.sectionmanager = sectionmanager; }

    public String getGroupleader() { return groupleader; }
    public void setGroupleader(String groupleader) { this.groupleader = groupleader; }

    public Character getRequestflag() { return requestflag; }
    public void setRequestflag(Character requestflag) { this.requestflag = requestflag; }

    public String getRelated() { return related; }
    public void setRelated(String related) { this.related = related; }

    public String getMouldid() { return mouldid; }
    public void setMouldid(String mouldid) { this.mouldid = mouldid; }

    public String getIsok() { return isok; }
    public void setIsok(String isok) { this.isok = isok; }

    public String getIspresent() { return ispresent; }
    public void setIspresent(String ispresent) { this.ispresent = ispresent; }

    public String getElementid() { return elementid; }
    public void setElementid(String elementid) { this.elementid = elementid; }

    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }

    public String getProcess() { return process; }
    public void setProcess(String process) { this.process = process; }

    public Character getIsupload() { return isupload; }
    public void setIsupload(Character isupload) { this.isupload = isupload; }

    public Character getUtiliseforfuture() { return utiliseforfuture; }
    public void setUtiliseforfuture(Character utiliseforfuture) { this.utiliseforfuture = utiliseforfuture; }

    public Character getMpworthy() { return mpworthy; }
    public void setMpworthy(Character mpworthy) { this.mpworthy = mpworthy; }
    public String getAprovLevel() { return aprovLevel; }
    public void setAprovLevel(String aprovLevel) { this.aprovLevel = aprovLevel; }

    public String getIsgeneral() { return isgeneral; }
    public void setIsgeneral(String isgeneral) { this.isgeneral = isgeneral; }

    public Character getOplupload() { return oplupload; }
    public void setOplupload(Character oplupload) { this.oplupload = oplupload; }

    public Character getTempfield4() { return tempfield4; }
    public void setTempfield4(Character tempfield4) { this.tempfield4 = tempfield4; }

    public Character getTempfield5() { return tempfield5; }
    public void setTempfield5(Character tempfield5) { this.tempfield5 = tempfield5; }
    
    public Character getActive() { return active; }
    public void setActive(Character active) { this.active = active; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime  getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime  createdon) { this.createdon = createdon; }

    public LocalDateTime  getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime  modifiedon) { this.modifiedon = modifiedon; }
}
