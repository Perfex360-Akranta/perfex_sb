package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "kzn_tl_projectcreationmst", schema = "public")
public class KznTlProjectcreationmst {
    @Id
    @Column(name = "kzpm_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "kzpm_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "kzpm_startdate", nullable = false)
    private LocalDateTime startdate;

    @Column(name = "kzpm_enddate", nullable = false)
    private LocalDateTime enddate;

    @Column(name = "kzpm_projectname", length = 500, nullable = false)
    private String projectname;

    @Column(name = "kzpm_area", length = 50, nullable = false)
    private String area;

    @Column(name = "kzpm_projectchamp", length = 10, nullable = false)
    private String projectchamp;

    @Column(name = "kzpm_projectno", length = 50, nullable = false)
    private String projectno;

    @Column(name = "kzpm_benefits", length = 50, nullable = false)
    private String benefits;

    @Column(name = "kzpm_savings", length = 1000, nullable = false)
    private String savings;

    @Column(name = "kzpm_projectmetrics", length = 500, nullable = false)
    private String projectmetrics;

    @Column(name = "kzpm_problemstatement", length = 2000, nullable = false)
    private String problemstatement;

    @Column(name = "kzpm_businesscase", length = 1000, nullable = false)
    private String businesscase;

    @Column(name = "kzpm_goalobj", length = 1000, nullable = false)
    private String goalobj;

    @Column(name = "kzpm_scopeconst", length = 1000, nullable = false)
    private String scopeconst;

    @Column(name = "kzpm_definestage", columnDefinition = "char(1)", nullable = false)
    private String definestage;

    @Column(name = "kzpm_measurestage", columnDefinition = "char(1)", nullable = false)
    private Character measurestage;

    @Column(name = "kzpm_analysestage", columnDefinition = "char(1)", nullable = false)
    private Character analysestage;

    @Column(name = "kzpm_controlstage", columnDefinition = "char(1)", nullable = false)
    private Character controlstage;

    @Column(name = "kzpm_improvestage", columnDefinition = "char(1)", nullable = false)
    private Character improvestage;

    @Column(name = "kzpm_closurestage", columnDefinition = "char(1)", nullable = false)
    private Character closurestage;

    @Column(name = "kzpm_definetargetdate")
    private LocalDateTime definetargetdate;

    @Column(name = "kzpm_measuretargetdate")
    private LocalDateTime measuretargetdate;

    @Column(name = "kzpm_analysetargetdate")
    private LocalDateTime analysetargetdate;

    @Column(name = "kzpm_controltargetdate")
    private LocalDateTime controltargetdate;

    @Column(name = "kzpm_improvetargetdate")
    private LocalDateTime improvetargetdate;

    @Column(name = "kzpm_closuretargetdate")
    private LocalDateTime closuretargetdate;

    @Column(name = "kzpm_definecompleteddate")
    private LocalDateTime definecompleteddate;

    @Column(name = "kzpm_measurecompleteddate")
    private LocalDateTime measurecompleteddate;

    @Column(name = "kzpm_analysecompleteddate")
    private LocalDateTime analysecompleteddate;

    @Column(name = "kzpm_controlcompleteddate")
    private LocalDateTime controlcompleteddate;

    @Column(name = "kzpm_improvecompleteddate")
    private LocalDateTime improvecompleteddate;

    @Column(name = "kzpm_closurecompleteddate")
    private LocalDateTime closurecompleteddate;

    @Column(name = "kzpm_imprcategory", length = 10, nullable = false)
    private String imprcategory;

    @Column(name = "kzpm_istangible",columnDefinition = "char(1)", nullable = false)
    private Character istangible;

    @Column(name = "kzpm_isintangible", columnDefinition = "char(1)", nullable = false)
    private Character isintangible;

    @Column(name = "kzpm_verifiedamnt", nullable = false)
    private BigDecimal verifiedamnt;

    @Column(name = "kzpm_finalamnt", nullable = false)
    private BigDecimal finalamnt;

    @Column(name = "kzpm_amtverifyremarks", length = 500)
    private String amtverifyremark;

    @Column(name = "kzpm_wave")
    private BigDecimal wave;

    @Column(name = "kzpm_oldresponsibility", length = 200)
    private String oldresponsibility;

    @Column(name = "kzpm_belt", length = 10, nullable = false)
    private String belt;

    @Column(name = "kzpm_tempfield4", columnDefinition = "char(1)")
    private Character tempfield4;

    @Column(name = "kzpm_active", columnDefinition = "char(1)")
    private Character active;

    @Column(name = "kzpm_createdby", length = 8)
    private String createdby;

    @Column(name = "kzpm_createdon")
    private LocalDateTime createdon;

    @Column(name = "kzpm_modifiedon")
    private LocalDateTime modifiedon;

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

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProjectchamp() {
        return projectchamp;
    }

    public void setProjectchamp(String projectchamp) {
        this.projectchamp = projectchamp;
    }

    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    public String getProjectmetrics() {
        return projectmetrics;
    }

    public void setProjectmetrics(String projectmetrics) {
        this.projectmetrics = projectmetrics;
    }

    public String getProblemstatement() {
        return problemstatement;
    }

    public void setProblemstatement(String problemstatement) {
        this.problemstatement = problemstatement;
    }

    public String getBusinesscase() {
        return businesscase;
    }

    public void setBusinesscase(String businesscase) {
        this.businesscase = businesscase;
    }

    public String getGoalobj() {
        return goalobj;
    }

    public void setGoalobj(String goalobj) {
        this.goalobj = goalobj;
    }

    public String getScopeconst() {
        return scopeconst;
    }

    public void setScopeconst(String scopeconst) {
        this.scopeconst = scopeconst;
    }

    public String getDefinestage() {
        return definestage;
    }

    public void setDefinestage(String definestage) {
        this.definestage = definestage;
    }

    public Character getMeasurestage() {
        return measurestage;
    }

    public void setMeasurestage(Character measurestage) {
        this.measurestage = measurestage;
    }

    public Character getAnalysestage() {
        return analysestage;
    }

    public void setAnalysestage(Character analysestage) {
        this.analysestage = analysestage;
    }

    public Character getControlstage() {
        return controlstage;
    }

    public void setControlstage(Character controlstage) {
        this.controlstage = controlstage;
    }

    public Character getImprovestage() {
        return improvestage;
    }

    public void setImprovestage(Character improvestage) {
        this.improvestage = improvestage;
    }

    public Character getClosurestage() {
        return closurestage;
    }

    public void setClosurestage(Character closurestage) {
        this.closurestage = closurestage;
    }

    public LocalDateTime getDefinetargetdate() {
        return definetargetdate;
    }

    public void setDefinetargetdate(LocalDateTime definetargetdate) {
        this.definetargetdate = definetargetdate;
    }

    public LocalDateTime getMeasuretargetdate() {
        return measuretargetdate;
    }

    public void setMeasuretargetdate(LocalDateTime measuretargetdate) {
        this.measuretargetdate = measuretargetdate;
    }

    public LocalDateTime getAnalysetargetdate() {
        return analysetargetdate;
    }

    public void setAnalysetargetdate(LocalDateTime analysetargetdate) {
        this.analysetargetdate = analysetargetdate;
    }

    public LocalDateTime getControltargetdate() {
        return controltargetdate;
    }

    public void setControltargetdate(LocalDateTime controltargetdate) {
        this.controltargetdate = controltargetdate;
    }

    public LocalDateTime getImprovetargetdate() {
        return improvetargetdate;
    }

    public void setImprovetargetdate(LocalDateTime improvetargetdate) {
        this.improvetargetdate = improvetargetdate;
    }

    public LocalDateTime getClosuretargetdate() {
        return closuretargetdate;
    }

    public void setClosuretargetdate(LocalDateTime closuretargetdate) {
        this.closuretargetdate = closuretargetdate;
    }

    public LocalDateTime getDefinecompleteddate() {
        return definecompleteddate;
    }

    public void setDefinecompleteddate(LocalDateTime definecompleteddate) {
        this.definecompleteddate = definecompleteddate;
    }

    public LocalDateTime getMeasurecompleteddate() {
        return measurecompleteddate;
    }

    public void setMeasurecompleteddate(LocalDateTime measurecompleteddate) {
        this.measurecompleteddate = measurecompleteddate;
    }

    public LocalDateTime getAnalysecompleteddate() {
        return analysecompleteddate;
    }

    public void setAnalysecompleteddate(LocalDateTime analysecompleteddate) {
        this.analysecompleteddate = analysecompleteddate;
    }

    public LocalDateTime getControlcompleteddate() {
        return controlcompleteddate;
    }

    public void setControlcompleteddate(LocalDateTime controlcompleteddate) {
        this.controlcompleteddate = controlcompleteddate;
    }

    public LocalDateTime getImprovecompleteddate() {
        return improvecompleteddate;
    }

    public void setImprovecompleteddate(LocalDateTime improvecompleteddate) {
        this.improvecompleteddate = improvecompleteddate;
    }

    public LocalDateTime getClosurecompleteddate() {
        return closurecompleteddate;
    }

    public void setClosurecompleteddate(LocalDateTime closurecompleteddate) {
        this.closurecompleteddate = closurecompleteddate;
    }

    public String getImprcategory() {
        return imprcategory;
    }

    public void setImprcategory(String imprcategory) {
        this.imprcategory = imprcategory;
    }

    public Character getIstangible() {
        return istangible;
    }

    public void setIstangible(Character istangible) {
        this.istangible = istangible;
    }

    public Character getIsintangible() {
        return isintangible;
    }

    public void setIsintangible(Character isintangible) {
        this.isintangible = isintangible;
    }

    public BigDecimal getVerifiedamnt() {
        return verifiedamnt;
    }

    public void setVerifiedamnt(BigDecimal verifiedamnt) {
        this.verifiedamnt = verifiedamnt;
    }

    public BigDecimal getFinalamnt() {
        return finalamnt;
    }

    public void setFinalamnt(BigDecimal finalamnt) {
        this.finalamnt = finalamnt;
    }

    public String getAmtverifyremark() {
        return amtverifyremark;
    }

    public void setAmtverifyremark(String amtverifyremark) {
        this.amtverifyremark = amtverifyremark;
    }

    public BigDecimal getWave() {
        return wave;
    }

    public void setWave(BigDecimal wave) {
        this.wave = wave;
    }

    public String getOldresponsibility() {
        return oldresponsibility;
    }

    public void setOldresponsibility(String oldresponsibility) {
        this.oldresponsibility = oldresponsibility;
    }

    public String getBelt() {
        return belt;
    }

    public void setBelt(String belt) {
        this.belt = belt;
    }

    public Character getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(Character tempfield4) {
        this.tempfield4 = tempfield4;
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
