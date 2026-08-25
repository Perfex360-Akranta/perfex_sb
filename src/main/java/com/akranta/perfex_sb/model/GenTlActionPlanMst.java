package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "gen_tl_actionplanmst", schema = "public")
public class GenTlActionPlanMst {
    
    @Id
    @Column(name = "aplm_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "aplm_masterrefid", length = 15, nullable = false)
    private String masterrefid;

    @Column(name = "aplm_detailrefid", length = 15, nullable = false)
    private String detailrefid;

    @Column(name = "aplm_refdoctype", length = 10, nullable = false)
    private String refdoctype;

    @Column(name = "aplm_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "aplm_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "aplm_maintask", length = 500, nullable = false)
    private String maintask;

    @Column(name = "aplm_pillarid", length = 10, nullable = false)
    private String pillarid;

    @Column(name = "aplm_status", columnDefinition = "char(1)", nullable = false)
    private Character status;

    @Column(name = "aplm_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "aplm_plandate", nullable = false)
    private LocalDateTime plandate;

    @Column(name = "aplm_tempfiled2", columnDefinition = "char(1)", nullable = false)
    private Character tempfiled2;

    @Column(name = "aplm_tempfiled3", columnDefinition = "char(1)", nullable = false)
    private Character tempfiled3;

    @Column(name = "aplm_tempfiled4", columnDefinition = "char(1)", nullable = false)
    private Character tempfiled4;

    @Column(name = "aplm_tempfiled5", columnDefinition = "char(1)", nullable = false)
    private Character tempfiled5;

    @Column(name = "aplm_active", columnDefinition = "char(1)", nullable = false)
    private Character active;

    @Column(name = "aplm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "aplm_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "aplm_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getMasterrefid() {
        return masterrefid;
    }

    public void setMasterrefid(String masterrefid) {
        this.masterrefid = masterrefid;
    }

    public String getDetailrefid() {
        return detailrefid;
    }

    public void setDetailrefid(String detailrefid) {
        this.detailrefid = detailrefid;
    }

    public String getRefdoctype() {
        return refdoctype;
    }

    public void setRefdoctype(String refdoctype) {
        this.refdoctype = refdoctype;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public String getMaintask() {
        return maintask;
    }

    public void setMaintask(String maintask) {
        this.maintask = maintask;
    }

    public String getPillarid() {
        return pillarid;
    }

    public void setPillarid(String pillarid) {
        this.pillarid = pillarid;
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

    public LocalDateTime getPlandate() {
        return plandate;
    }

    public void setPlandate(LocalDateTime plandate) {
        this.plandate = plandate;
    }

    public Character getTempfiled2() {
        return tempfiled2;
    }

    public void setTempfiled2(Character tempfiled2) {
        this.tempfiled2 = tempfiled2;
    }

    public Character getTempfiled3() {
        return tempfiled3;
    }

    public void setTempfiled3(Character tempfiled3) {
        this.tempfiled3 = tempfiled3;
    }

    public Character getTempfiled4() {
        return tempfiled4;
    }

    public void setTempfiled4(Character tempfiled4) {
        this.tempfiled4 = tempfiled4;
    }

    public Character getTempfiled5() {
        return tempfiled5;
    }

    public void setTempfiled5(Character tempfiled5) {
        this.tempfiled5 = tempfiled5;
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
