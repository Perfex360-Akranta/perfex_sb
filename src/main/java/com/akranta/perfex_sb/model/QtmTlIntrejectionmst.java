package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "QTM_TL_INTREJECTIONMST")
public class QtmTlIntrejectionmst {

    @Id
    @Column(name = "qirm_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "qirm_factoryid", length = 10, nullable = false)
    private String factoryid;

    @Column(name = "qirm_sectionid", length = 10, nullable = false)
    private String sectionid;

    @Column(name = "qirm_cellid", length = 10, nullable = false)
    private String cellid;

    @Column(name = "qirm_machineid", length = 10, nullable = false)
    private String machineid;

    @Column(name = "qirm_plmasterid", length = 15, nullable = false)
    private String plmasterid;

    @Column(name = "qirm_pldetailid", length = 15, nullable = false)
    private String pldetailid;

    @Column(name = "qirm_prodgroupid", length = 10, nullable = false)
    private String prodgroupid;

    @Column(name = "qirm_productid", length = 10, nullable = false)
    private String productid;

    @Column(name = "qirm_cavity", nullable = false)
    private BigDecimal cavity;

    @Column(name = "qirm_inspectiondate", nullable = false)
    private LocalDateTime inspectiondate;

    @Column(name = "qirm_inspectionid", length = 10, nullable = false)
    private String inspectionid;

    @Column(name = "qirm_totalproduction", nullable = false)
    private BigDecimal totalproduction;

    @Column(name = "qirm_testingscrap", nullable = false)
    private BigDecimal testingscrap;

    @Column(name = "qirm_batchno", length = 15, nullable = false)
    private String batchno;

    @Column(name = "qirm_inspectionqty", nullable = false)
    private BigDecimal inspectionqty;

    @Column(name = "qirm_acceptedqty", nullable = false)
    private BigDecimal acceptedqty;

    @Column(name = "qirm_backlogqty", nullable = false)
    private BigDecimal backlogqty;

    @Column(name = "qirm_qaholdqty", nullable = false)
    private BigDecimal qaholdqty;

    @Column(name = "qirm_qaholdpercentage", nullable = false)
    private BigDecimal qaholdpercentage;

    @Column(name = "qirm_shiftdate", nullable = false)
    private LocalDateTime shiftdate;

    @Column(name = "qirm_shiftid", length = 6, nullable = false)
    private String shiftid;

    @Column(name = "qirm_entryby", length = 10, nullable = false)
    private String entryby;

    @Column(name = "qirm_productiondate", nullable = false)
    private LocalDateTime productiondate;

    @Column(name = "qirm_qualityentryby", length = 10, nullable = false)
    private String qualityentryby;

    @Column(name = "qirm_qualityentrydate", nullable = false)
    private LocalDateTime qualityentrydate;

    @Column(name = "qirm_approvalby", length = 10, nullable = false)
    private String approvalby;

    @Column(name = "qirm_approvaldate", nullable = false)
    private LocalDateTime approvaldate;

    @Column(name = "qirm_status", columnDefinition = "CHAR(1)", nullable = false)
    private Character status;

    @Column(name = "qirm_backlogflag", columnDefinition = "CHAR(1)", nullable = false)
    private Character backlogflag;

    @Column(name = "qirm_referencekeyid", length = 15, nullable = false)
    private String referencekeyid;

    @Column(name = "qirm_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "qirm_mrbqty", nullable = false)
    private BigDecimal mrbqty;

    @Column(name = "qirm_inspectedshiftid", length = 6, nullable = false)
    private String inspectedshiftid;

    @Column(name = "qirm_balanceqty", nullable = false)
    private BigDecimal balanceqty;

    @Column(name = "qirm_linkmasterid", length = 15, nullable = false)
    private String linkmasterid;

    @Column(name = "qirm_actualproduced", nullable = false)
    private BigDecimal actualproduced;

    @Column(name = "qirm_virtualproduced", nullable = false)
    private BigDecimal virtualproduced;

    @Column(name = "qirm_parentmasterid", length = 15, nullable = false)
    private String parentmasterid;

    @Column(name = "qirm_tempfield1", length = 15, nullable = false)
    private String tempfield1;

    @Column(name = "qirm_tempfield2", length = 15, nullable = false)
    private String tempfield2;

    @Column(name = "qirm_tempfield3", length = 15, nullable = false)
    private String tempfield3;

    @Column(name = "qirm_tempfield4", length = 15, nullable = false)
    private String tempfield4;

    @Column(name = "qirm_tempfield5", length = 15, nullable = false)
    private String tempfield5;

    @Column(name = "qirm_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "qirm_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "qirm_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "qirm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "qirm_createdon", length = 15, nullable = false)
    private LocalDateTime createdon;

    @Column(name = "qirm_modifiedon", length = 15, nullable = false)
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

    public String getPlmasterid() {
        return plmasterid;
    }

    public void setPlmasterid(String plmasterid) {
        this.plmasterid = plmasterid;
    }

    public String getPldetailid() {
        return pldetailid;
    }

    public void setPldetailid(String pldetailid) {
        this.pldetailid = pldetailid;
    }

    public String getProdgroupid() {
        return prodgroupid;
    }

    public void setProdgroupid(String prodgroupid) {
        this.prodgroupid = prodgroupid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public BigDecimal getCavity() {
        return cavity;
    }

    public void setCavity(BigDecimal cavity) {
        this.cavity = cavity;
    }

    public LocalDateTime getInspectiondate() {
        return inspectiondate;
    }

    public void setInspectiondate(LocalDateTime inspectiondate) {
        this.inspectiondate = inspectiondate;
    }

    public String getInspectionid() {
        return inspectionid;
    }

    public void setInspectionid(String inspectionid) {
        this.inspectionid = inspectionid;
    }

    public BigDecimal getTotalproduction() {
        return totalproduction;
    }

    public void setTotalproduction(BigDecimal totalproduction) {
        this.totalproduction = totalproduction;
    }

    public BigDecimal getTestingscrap() {
        return testingscrap;
    }

    public void setTestingscrap(BigDecimal testingscrap) {
        this.testingscrap = testingscrap;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public BigDecimal getInspectionqty() {
        return inspectionqty;
    }

    public void setInspectionqty(BigDecimal inspectionqty) {
        this.inspectionqty = inspectionqty;
    }

    public BigDecimal getAcceptedqty() {
        return acceptedqty;
    }

    public void setAcceptedqty(BigDecimal acceptedqty) {
        this.acceptedqty = acceptedqty;
    }

    public BigDecimal getBacklogqty() {
        return backlogqty;
    }

    public void setBacklogqty(BigDecimal backlogqty) {
        this.backlogqty = backlogqty;
    }

    public BigDecimal getQaholdqty() {
        return qaholdqty;
    }

    public void setQaholdqty(BigDecimal qaholdqty) {
        this.qaholdqty = qaholdqty;
    }

    public BigDecimal getQaholdpercentage() {
        return qaholdpercentage;
    }

    public void setQaholdpercentage(BigDecimal qaholdpercentage) {
        this.qaholdpercentage = qaholdpercentage;
    }

    public LocalDateTime getShiftdate() {
        return shiftdate;
    }

    public void setShiftdate(LocalDateTime shiftdate) {
        this.shiftdate = shiftdate;
    }

    public String getShiftid() {
        return shiftid;
    }

    public void setShiftid(String shiftid) {
        this.shiftid = shiftid;
    }

    public String getEntryby() {
        return entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public LocalDateTime getProductiondate() {
        return productiondate;
    }

    public void setProductiondate(LocalDateTime productiondate) {
        this.productiondate = productiondate;
    }

    public String getQualityentryby() {
        return qualityentryby;
    }

    public void setQualityentryby(String qualityentryby) {
        this.qualityentryby = qualityentryby;
    }

    public LocalDateTime getQualityentrydate() {
        return qualityentrydate;
    }

    public void setQualityentrydate(LocalDateTime qualityentrydate) {
        this.qualityentrydate = qualityentrydate;
    }

    public String getApprovalby() {
        return approvalby;
    }

    public void setApprovalby(String approvalby) {
        this.approvalby = approvalby;
    }

    public LocalDateTime getApprovaldate() {
        return approvaldate;
    }

    public void setApprovaldate(LocalDateTime approvaldate) {
        this.approvaldate = approvaldate;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Character getBacklogflag() {
        return backlogflag;
    }

    public void setBacklogflag(Character backlogflag) {
        this.backlogflag = backlogflag;
    }

    public String getReferencekeyid() {
        return referencekeyid;
    }

    public void setReferencekeyid(String referencekeyid) {
        this.referencekeyid = referencekeyid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getMrbqty() {
        return mrbqty;
    }

    public void setMrbqty(BigDecimal mrbqty) {
        this.mrbqty = mrbqty;
    }

    public String getInspectedshiftid() {
        return inspectedshiftid;
    }

    public void setInspectedshiftid(String inspectedshiftid) {
        this.inspectedshiftid = inspectedshiftid;
    }

    public BigDecimal getBalanceqty() {
        return balanceqty;
    }

    public void setBalanceqty(BigDecimal balanceqty) {
        this.balanceqty = balanceqty;
    }

    public String getLinkmasterid() {
        return linkmasterid;
    }

    public void setLinkmasterid(String linkmasterid) {
        this.linkmasterid = linkmasterid;
    }

    public BigDecimal getActualproduced() {
        return actualproduced;
    }

    public void setActualproduced(BigDecimal actualproduced) {
        this.actualproduced = actualproduced;
    }

    public BigDecimal getVirtualproduced() {
        return virtualproduced;
    }

    public void setVirtualproduced(BigDecimal virtualproduced) {
        this.virtualproduced = virtualproduced;
    }

    public String getParentmasterid() {
        return parentmasterid;
    }

    public void setParentmasterid(String parentmasterid) {
        this.parentmasterid = parentmasterid;
    }

    public String getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(String tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public String getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(String tempfield2) {
        this.tempfield2 = tempfield2;
    }

    public String getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(String tempfield3) {
        this.tempfield3 = tempfield3;
    }

    public String getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(String tempfield4) {
        this.tempfield4 = tempfield4;
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

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
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
