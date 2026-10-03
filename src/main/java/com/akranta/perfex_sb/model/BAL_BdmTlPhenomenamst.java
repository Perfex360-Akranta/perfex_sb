package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "bdm_tl_phenomenamst", schema = "public")
public class BAL_BdmTlPhenomenamst {

    @Id
    @Column(name = "bphm_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "bphm_phenomenatype", columnDefinition = "CHAR(2)", nullable = false)
    private String phenomenatype;

    @Column(name = "bphm_phenomenaname", length = 100, nullable = false)
    private String phenomenaname;

    @Column(name = "bphm_shortname", length = 15, nullable = false)
    private String shortname;

    @Column(name = "bphm_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "bphm_assemblyid", length = 15, nullable = false)
    private String assemblyid;

    @Column(name = "bphm_levelno", length = 5, nullable = false)
    private String levelno;

    @Column(name = "bphm_childflag", columnDefinition = "CHAR(1)", nullable = false)
    private Character childflag;

    @Column(name = "bphm_isphnnotdefined", columnDefinition = "CHAR(1)", nullable = false)
    private Character isphnnotdefined;

    @Column(name = "bphm_causenotneeded", columnDefinition = "CHAR(1)", nullable = false)
    private Character causenotneeded;

    @Column(name = "bphm_relatedto", columnDefinition = "CHAR(3)", nullable = false)
    private String relatedto;

    @Column(name = "bphm_tempfield1", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield1;

    @Column(name = "bphm_tempfield2", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield2;

    @Column(name = "bphm_tempfield3", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield3;

    @Column(name = "bphm_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "bphm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "bphm_createdon", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "bphm_modifiedon", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getPhenomenatype() {
        return phenomenatype;
    }

    public void setPhenomenatype(String phenomenatype) {
        this.phenomenatype = phenomenatype;
    }

    public String getPhenomenaname() {
        return phenomenaname;
    }

    public void setPhenomenaname(String phenomenaname) {
        this.phenomenaname = phenomenaname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAssemblyid() {
        return assemblyid;
    }

    public void setAssemblyid(String assemblyid) {
        this.assemblyid = assemblyid;
    }

    public String getLevelno() {
        return levelno;
    }

    public void setLevelno(String levelno) {
        this.levelno = levelno;
    }

    public Character getChildflag() {
        return childflag;
    }

    public void setChildflag(Character childflag) {
        this.childflag = childflag;
    }

    public Character getIsphnnotdefined() {
        return isphnnotdefined;
    }

    public void setIsphnnotdefined(Character isphnnotdefined) {
        this.isphnnotdefined = isphnnotdefined;
    }

    public Character getCausenotneeded() {
        return causenotneeded;
    }

    public void setCausenotneeded(Character causenotneeded) {
        this.causenotneeded = causenotneeded;
    }

    public String getRelatedto() {
        return relatedto;
    }

    public void setRelatedto(String relatedto) {
        this.relatedto = relatedto;
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