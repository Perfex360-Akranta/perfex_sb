package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="kpi_tl_indicator",schema = "public")
public class KpiTlIndicator {

    @Id
    @Column(name = "kink_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "kink_indicatorname", length = 200, nullable = false)
    private String indicatorname;

    @Column(name = "kink_indicatorcode", length = 500, nullable = false)
    private String indicatorcode;

    @Column(name = "kink_description", length = 500, nullable = false)
    private String description;

    @Column(name = "kink_parentid", length = 15, nullable = false)
    private String parentid;

    @Column(name = "kink_levelno", nullable = false)
    private Integer levelno;

    @Column(name = "kink_sortno", length = 100, nullable = false)
    private String sortno;

    @Column(name = "kink_ischild", length = 1, nullable = false)
    private Character ischild;

    @Column(name = "kink_inputtype", length = 1, nullable = false)
    private Character inputtype;

    @Column(name = "kink_inputentry", length = 1, nullable = false)
    private Character inputentry;

    @Column(name = "kink_identifier", length = 5, nullable = false)
    private String identifier;

    @Column(name = "kink_manualcalctype", length = 1, nullable = false)
    private Character manualcalctype;

    @Column(name = "kink_uomid", length = 10, nullable = false)
    private String uomid;

    @Column(name = "kink_frequency", length = 1, nullable = false)
    private Character frequency;

    @Column(name = "kink_excelname", length = 200, nullable = false)
    private String excelname;

    @Column(name = "kink_dept_keyid", length = 6, nullable = false)
    private String dept_keyid;

    @Column(name = "kink_costarea", length = 6, nullable = false)
    private String costarea;

    @Column(name = "kink_targetneed", length = 1, nullable = false)
    private String targetneed;

    @Column(name = "kink_pillarid", length = 6, nullable = false)
    private String pillarid;

    @Column(name = "kink_type", length = 4, nullable = false)
    private String type;

    @Column(name = "kink_impactarea", length = 12, nullable = false)
    private String impactarea;

    @Column(name = "kink_goals", length = 200, nullable = false)
    private String goals;

    @Column(name = "kink_sourceofkpi", length = 12, nullable = false)
    private String sourceofkpi;

    @Column(name = "kink_kpireason", length = 200, nullable = false)
    private String kpireason;

    @Column(name = "kink_annualtarget", length = 50, nullable = false)
    private String annualtarget;

    @Column(name = "kink_startdate", nullable = false)
    private LocalDateTime startdate;

    @Column(name = "kink_enddate", nullable = false)
    private LocalDateTime enddate;

    @Column(name = "kink_location", length = 10, nullable = false)
    private String location;

    @Column(name = "kink_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "kink_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "kink_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kink_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getIndicatorname() {
        return indicatorname;
    }

    public void setIndicatorname(String indicatorname) {
        this.indicatorname = indicatorname;
    }

    public String getIndicatorcode() {
        return indicatorcode;
    }

    public void setIndicatorcode(String indicatorcode) {
        this.indicatorcode = indicatorcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Integer getLevelno() {
        return levelno;
    }

    public void setLevelno(Integer levelno) {
        this.levelno = levelno;
    }

    public String getSortno() {
        return sortno;
    }

    public void setSortno(String sortno) {
        this.sortno = sortno;
    }

    public Character getIschild() {
        return ischild;
    }

    public void setIschild(Character ischild) {
        this.ischild = ischild;
    }

    public Character getInputtype() {
        return inputtype;
    }

    public void setInputtype(Character inputtype) {
        this.inputtype = inputtype;
    }

    public Character getInputentry() {
        return inputentry;
    }

    public void setInputentry(Character inputentry) {
        this.inputentry = inputentry;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Character getManualcalctype() {
        return manualcalctype;
    }

    public void setManualcalctype(Character manualcalctype) {
        this.manualcalctype = manualcalctype;
    }

    public String getUomid() {
        return uomid;
    }

    public void setUomid(String uomid) {
        this.uomid = uomid;
    }

    public Character getFrequency() {
        return frequency;
    }

    public void setFrequency(Character frequency) {
        this.frequency = frequency;
    }

    public String getExcelname() {
        return excelname;
    }

    public void setExcelname(String excelname) {
        this.excelname = excelname;
    }

    public String getDept_keyid() {
        return dept_keyid;
    }

    public void setDept_keyid(String dept_keyid) {
        this.dept_keyid = dept_keyid;
    }

    public String getCostarea() {
        return costarea;
    }

    public void setCostarea(String costarea) {
        this.costarea = costarea;
    }

    public String getTargetneed() {
        return targetneed;
    }

    public void setTargetneed(String targetneed) {
        this.targetneed = targetneed;
    }

    public String getPillarid() {
        return pillarid;
    }

    public void setPillarid(String pillarid) {
        this.pillarid = pillarid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImpactarea() {
        return impactarea;
    }

    public void setImpactarea(String impactarea) {
        this.impactarea = impactarea;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getSourceofkpi() {
        return sourceofkpi;
    }

    public void setSourceofkpi(String sourceofkpi) {
        this.sourceofkpi = sourceofkpi;
    }

    public String getKpireason() {
        return kpireason;
    }

    public void setKpireason(String kpireason) {
        this.kpireason = kpireason;
    }

    public String getAnnualtarget() {
        return annualtarget;
    }

    public void setAnnualtarget(String annualtarget) {
        this.annualtarget = annualtarget;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
