package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bal_pcs_tl_losscapture", schema = "public")
public class BAL_PcsLossCapture {

    @Id
    @Column(name = "plos_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "plos_flid", length = 15, nullable = false)
    private String flid;

    @Column(name = "plos_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "plos_shiftid", length = 8, nullable = false)
    private String shiftid;

    @Column(name = "plos_fromtime", nullable = false)
    private LocalDateTime fromtime;

    @Column(name = "plos_totime", nullable = false)
    private LocalDateTime totime;

    @Column(name = "plos_losstime", length = 10, nullable = false)
    private String losstime;

    @Column(name = "plos_lossreason", length = 500, nullable = false)
    private String lossreason;

    @Column(name = "plos_lossid", length = 15, nullable = false)
    private String lossid;

    @Column(name = "plos_tradeid", length = 15, nullable = false)
    private String tradeid;

    @Column(name = "plos_prod_impact", length = 1, nullable = false)
    private Character prodImpact;

    @Column(name = "plos_prod_imp_qty", nullable = false)
    private BigDecimal prodImpQty;

    @Column(name = "plos_lossdescription", length = 500, nullable = false)
    private String lossdescription;

    @Column(name = "plos_pldetailsid", length = 15, nullable = false)
    private String pldetailsid;

    @Column(name = "plos_losshrno", length = 2, nullable = false)
    private String losshrno;

    @Column(name = "plos_remarks", length = 500, nullable = false)
    private String lossremarks;

    @Column(name = "plos_machineid", length = 15, nullable = false)
    private String machineid;

    @Column(name = "plos_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "plos_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "plos_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "plos_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    // ---------- nullable columns ----------

    @Column(name = "plos_source", length = 10)
    private String source;

    @Column(name = "plos_sourcename", length = 500)
    private String sourceName;

    @Column(name = "plos_cleantime")
    private BigDecimal cleanTime;

    @Column(name = "plos_lubtime")
    private BigDecimal lubTime;

    @Column(name = "plos_inspecttime")
    private BigDecimal inspectTime;

    @Column(name = "plos_tighttime")
    private BigDecimal tightTime;

    @Column(name = "plos_toolid", length = 15)
    private String toolId;

    @Column(name = "plos_tempfield2", length = 15)
    private String tempField2;

    @Column(name = "plos_tempfield3", length = 2)
    private String tempField3;

    // ---------- getters & setters ----------

    public String getKeyid() { return keyid; }
    public void setKeyid(String keyid) { this.keyid = keyid; }

    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getShiftid() { return shiftid; }
    public void setShiftid(String shiftid) { this.shiftid = shiftid; }

    public LocalDateTime getFromtime() { return fromtime; }
    public void setFromtime(LocalDateTime fromtime) { this.fromtime = fromtime; }

    public LocalDateTime getTotime() { return totime; }
    public void setTotime(LocalDateTime totime) { this.totime = totime; }

    public String getLosstime() { return losstime; }
    public void setLosstime(String losstime) { this.losstime = losstime; }

    public String getLossreason() { return lossreason; }
    public void setLossreason(String lossreason) { this.lossreason = lossreason; }

    public String getLossid() { return lossid; }
    public void setLossid(String lossid) { this.lossid = lossid; }

    public String getTradeid() { return tradeid; }
    public void setTradeid(String tradeid) { this.tradeid = tradeid; }

    public Character getProdImpact() { return prodImpact; }
    public void setProdImpact(Character prodImpact) { this.prodImpact = prodImpact; }

    public BigDecimal getProdImpQty() { return prodImpQty; }
    public void setProdImpQty(BigDecimal prodImpQty) { this.prodImpQty = prodImpQty; }

    public String getLossdescription() { return lossdescription; }
    public void setLossdescription(String lossdescription) { this.lossdescription = lossdescription; }

    public String getPldetailsid() { return pldetailsid; }
    public void setPldetailsid(String pldetailsid) { this.pldetailsid = pldetailsid; }

    public String getLosshrno() { return losshrno; }
    public void setLosshrno(String losshrno) { this.losshrno = losshrno; }

    public String getLossremarks() { return lossremarks; }
    public void setLossremarks(String lossremarks) { this.lossremarks = lossremarks; }

    public String getMachineid() { return machineid; }
    public void setMachineid(String machineid) { this.machineid = machineid; }

    public Character getActive() { return active; }
    public void setActive(Character active) { this.active = active; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) { this.modifiedon = modifiedon; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public BigDecimal getCleanTime() { return cleanTime; }
    public void setCleanTime(BigDecimal cleanTime) { this.cleanTime = cleanTime; }

    public BigDecimal getLubTime() { return lubTime; }
    public void setLubTime(BigDecimal lubTime) { this.lubTime = lubTime; }

    public BigDecimal getInspectTime() { return inspectTime; }
    public void setInspectTime(BigDecimal inspectTime) { this.inspectTime = inspectTime; }

    public BigDecimal getTightTime() { return tightTime; }
    public void setTightTime(BigDecimal tightTime) { this.tightTime = tightTime; }

    public String getToolId() { return toolId; }
    public void setToolId(String toolId) { this.toolId = toolId; }

    public String getTempField2() { return tempField2; }
    public void setTempField2(String tempField2) { this.tempField2 = tempField2; }

    public String getTempField3() { return tempField3; }
    public void setTempField3(String tempField3) { this.tempField3 = tempField3; }
}