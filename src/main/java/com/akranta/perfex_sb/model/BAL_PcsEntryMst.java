package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name="bal_pcs_tl_mst", schema="public")
public class BAL_PcsEntryMst {

    @Id
    @Column(name="prlm_keyid", length=15, nullable=false)
    private String keyid;

    @Column(name="prlm_date", nullable = false)
    private LocalDateTime date;

    @Column (name="prlm_factoryid", length = 15, nullable = false)
    private String factoryid;

    @Column (name="prlm_sectionid", length = 10, nullable = false)
    private String sectionid;

    @Column (name="prlm_cellid", length = 10, nullable = false)
    private String cellid;

    @Column (name="prlm_machineid", length=10, nullable = false)
    private String machineid;

    @Column (name="prlm_shiftid", length = 6, nullable = false)
    private String shiftid;

    @Column (name="prlm_entrydate", nullable = false)
    private LocalDateTime entrydate;

    @Column (name="prlm_entryby", length = 8, nullable = false)
    private String entryby;

    @Column (name="prlm_updatedby", length=8, nullable = false)
    private String updatedby;

    @Column (name="prlm_updateddate", nullable = false)
    private LocalDateTime updateddate;

    @Column (name="prlm_approvedby", length = 8, nullable = false)
    private String approvedby;

    @Column (name="prlm_approvedate", nullable = false)
    private LocalDateTime approvedate;

    @Column (name="prlm_apporvedflag", length=1, nullable = false)
    private Character prlm_apporvedflag;

    @Column (name="prlm_shiftincharge", length=12, nullable = false)
    private String shiftincharge;

    @Column (name="prlm_subgroupid", length=15, nullable = false)
    private String subgroupid;

    @Column (name="prlm_isnoplan", length=1, nullable = false)
    private Character isnoplan;

    @Column (name="prlm_logtype", length=1, nullable = false)
    private Character logtype;

    @Column (name="prlm_timeorqty", length=2, nullable = false)
    private String timeorqty;

    @Column (name="prlm_completedflag", length=10, nullable = false)
    private String completedflag;

    @Column (name="prlm_tempfield2",length = 2, nullable = false)
    private String tempfield2;

    @Column (name="prlm_flid", length= 12, nullable = false)
    private String flid;

    @Column (name="prlm_elementid", length=250, nullable = false)
    private String elementid;

    @Column (name="prlm_active", length=1, nullable = false)
    private Character active;

    @Column (name="prlm_createdby", length=10, nullable = false)
    private String createdby;

    @Column (name="prlm_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name="prlm_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public String getShiftid() {
        return shiftid;
    }

    public void setShiftid(String shiftid) {
        this.shiftid = shiftid;
    }

    public LocalDateTime getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(LocalDateTime entrydate) {
        this.entrydate = entrydate;
    }

    public String getEntryby() {
        return entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public LocalDateTime getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(LocalDateTime updateddate) {
        this.updateddate = updateddate;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public LocalDateTime getApprovedate() {
        return approvedate;
    }

    public void setApprovedate(LocalDateTime approvedate) {
        this.approvedate = approvedate;
    }

    public Character getPrlm_apporvedflag() {
        return prlm_apporvedflag;
    }

    public void setPrlm_apporvedflag(Character prlm_apporvedflag) {
        this.prlm_apporvedflag = prlm_apporvedflag;
    }

    public String getShiftincharge() {
        return shiftincharge;
    }

    public void setShiftincharge(String shiftincharge) {
        this.shiftincharge = shiftincharge;
    }

    public String getSubgroupid() {
        return subgroupid;
    }

    public void setSubgroupid(String subgroupid) {
        this.subgroupid = subgroupid;
    }

    public Character getIsnoplan() {
        return isnoplan;
    }

    public void setIsnoplan(Character isnoplan) {
        this.isnoplan = isnoplan;
    }

    public Character getLogtype() {
        return logtype;
    }

    public void setLogtype(Character logtype) {
        this.logtype = logtype;
    }

    public String getTimeorqty() {
        return timeorqty;
    }

    public void setTimeorqty(String timeorqty) {
        this.timeorqty = timeorqty;
    }

    public String getCompletedflag() {
        return completedflag;
    }

    public void setCompletedflag(String completedflag) {
        this.completedflag = completedflag;
    }

    public String getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(String tempfield2) {
        this.tempfield2 = tempfield2;
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
