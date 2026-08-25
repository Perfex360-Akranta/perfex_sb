package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // qtm_tl_intrejectiondtl
@Table(name = "QTM_TL_INTREJECTIONDTL")
public class QtmTlIntrejectiondtl {
    @Id
    @Column(name = "qird_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "qird_prodfamilyid", length = 15, nullable = false)
    private String prodfamilyid;

    @Column(name = "qird_processid", length = 15, nullable = false)
    private String processid;

    @Column(name = "qird_phenomenaid", length = 15, nullable = false)
    private String phenomenaid;

    @Column(name = "qird_causeid", length = 15, nullable = false)
    private String causeid;

    @Column(name = "qird_quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "qird_4mtype", length = 10, nullable = false)
    private String m4type;

    @Column(name = "qird_type", columnDefinition = "CHAR(1)", nullable = false)
    private Character type;

    @Column(name = "qird_backlogflag", columnDefinition = "CHAR(1)", nullable = false)
    private Character backlogflag;

    @Column(name = "qird_referenceid", length = 15, nullable = false)
    private String referenceid;

    @Column(name = "qird_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "qird_wwmasterid", length = 15, nullable = false)
    private String wwmasterid;

    @Column(name = "qird_entrytype",  length = 3, nullable = false)
    private String entrytype;

    @Column(name = "qird_masterid", length = 15, nullable = false)
    private String masterid;

    @Column(name = "qird_qhb_keyid", length = 15, nullable = false)
    private String qhb_keyid;

    @Column(name = "qird_plrk_keyid", length = 15, nullable = false)
    private String plrk_keyid;

    @Column(name = "qird_subprocessid", length = 15, nullable = false)
    private String subprocessid;

    @Column(name = "qird_qty", length = 15, nullable = false)
    private String qty;

    @Column(name = "qird_tempfield4", length = 15, nullable = false)
    private String tempfield4;

    @Column(name = "qird_tempfield5", length = 15, nullable = false)
    private String tempfield5;

    @Column(name = "qird_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "qird_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "qird_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "qird_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getProdfamilyid() {
        return prodfamilyid;
    }

    public void setProdfamilyid(String prodfamilyid) {
        this.prodfamilyid = prodfamilyid;
    }

    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }

    public String getPhenomenaid() {
        return phenomenaid;
    }

    public void setPhenomenaid(String phenomenaid) {
        this.phenomenaid = phenomenaid;
    }

    public String getCauseid() {
        return causeid;
    }

    public void setCauseid(String causeid) {
        this.causeid = causeid;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getM4type() {
        return m4type;
    }

    public void setM4type(String m4type) {
        this.m4type = m4type;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    public Character getBacklogflag() {
        return backlogflag;
    }

    public void setBacklogflag(Character backlogflag) {
        this.backlogflag = backlogflag;
    }

    public String getReferenceid() {
        return referenceid;
    }

    public void setReferenceid(String referenceid) {
        this.referenceid = referenceid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getWwmasterid() {
        return wwmasterid;
    }

    public void setWwmasterid(String wwmasterid) {
        this.wwmasterid = wwmasterid;
    }

    public String getEntrytype() {
        return entrytype;
    }

    public void setEntrytype(String entrytype) {
        this.entrytype = entrytype;
    }

    public String getMasterid() {
        return masterid;
    }

    public void setMasterid(String masterid) {
        this.masterid = masterid;
    }

    public String getQhb_keyid() {
        return qhb_keyid;
    }

    public void setQhb_keyid(String qhb_keyid) {
        this.qhb_keyid = qhb_keyid;
    }

    public String getPlrk_keyid() {
        return plrk_keyid;
    }

    public void setPlrk_keyid(String plrk_keyid) {
        this.plrk_keyid = plrk_keyid;
    }

    public String getSubprocessid() {
        return subprocessid;
    }

    public void setSubprocessid(String subprocessid) {
        this.subprocessid = subprocessid;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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
