package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pcs_tl_lossreasonlink", schema = "public")
public class BAL_PcsLossReasonLink {

    @Id
    @Column(name = "plrk_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "plrk_lossid", length = 15)
    private String lossid;

    @Column(name = "plrk_reasonid", length = 15)
    private String reasonid;

    @Column(name = "plrk_causeid", length = 15)
    private String causeid;

    @Column(name = "plrk_rootcauseid", length = 15)
    private String rootcauseid;

    @Column(name = "plrk_pldetailid", length = 15)
    private String pldetailid;

    @Column(name = "plrk_wno", length = 30)
    private String wno;

    @Column(name = "plrk_minutes")
    private BigDecimal minutes;

    @Column(name = "plrk_instance")
    private BigDecimal instance;

    @Column(name = "plrk_date")
    private LocalDateTime date;

    @Column(name = "plrk_shiftid", length = 10)
    private String shiftid;

    @Column(name = "plrk_hourno")
    private BigDecimal hourno;

    @Column(name = "plrk_factoryid", length = 10)
    private String factoryid;

    @Column(name = "plrk_sectionid", length = 10)
    private String sectionid;

    @Column(name = "plrk_cellid", length = 10)
    private String cellid;

    @Column(name = "plrk_machineid", length = 10)
    private String machineid;

    @Column(name = "plrk_subgroupid", length = 15)
    private String subgroupid;

    @Column(name = "plrk_remarks", length = 600)
    private String remarks;

    @Column(name = "plrk_msrno", length = 15)
    private String msrno;

    @Column(name = "plrk_processid", length = 10)
    private String processid;

    @Column(name = "plrk_flid", length = 12)
    private String flid;

    // ---------- NOT NULL columns ----------

    @Column(name = "plrk_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "plrk_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "plrk_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "plrk_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "plrk_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    // ---------- getters & setters ----------

    public String getKeyid() { return keyid; }
    public void setKeyid(String keyid) { this.keyid = keyid; }

    public String getLossid() { return lossid; }
    public void setLossid(String lossid) { this.lossid = lossid; }

    public String getReasonid() { return reasonid; }
    public void setReasonid(String reasonid) { this.reasonid = reasonid; }

    public String getCauseid() { return causeid; }
    public void setCauseid(String causeid) { this.causeid = causeid; }

    public String getRootcauseid() { return rootcauseid; }
    public void setRootcauseid(String rootcauseid) { this.rootcauseid = rootcauseid; }

    public String getPldetailid() { return pldetailid; }
    public void setPldetailid(String pldetailid) { this.pldetailid = pldetailid; }

    public String getWno() { return wno; }
    public void setWno(String wno) { this.wno = wno; }

    public BigDecimal getMinutes() { return minutes; }
    public void setMinutes(BigDecimal minutes) { this.minutes = minutes; }

    public BigDecimal getInstance() { return instance; }
    public void setInstance(BigDecimal instance) { this.instance = instance; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getShiftid() { return shiftid; }
    public void setShiftid(String shiftid) { this.shiftid = shiftid; }

    public BigDecimal getHourno() { return hourno; }
    public void setHourno(BigDecimal hourno) { this.hourno = hourno; }

    public String getFactoryid() { return factoryid; }
    public void setFactoryid(String factoryid) { this.factoryid = factoryid; }

    public String getSectionid() { return sectionid; }
    public void setSectionid(String sectionid) { this.sectionid = sectionid; }

    public String getCellid() { return cellid; }
    public void setCellid(String cellid) { this.cellid = cellid; }

    public String getMachineid() { return machineid; }
    public void setMachineid(String machineid) { this.machineid = machineid; }

    public String getSubgroupid() { return subgroupid; }
    public void setSubgroupid(String subgroupid) { this.subgroupid = subgroupid; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getMsrno() { return msrno; }
    public void setMsrno(String msrno) { this.msrno = msrno; }

    public String getProcessid() { return processid; }
    public void setProcessid(String processid) { this.processid = processid; }

    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }

    public String getElementid() { return elementid; }
    public void setElementid(String elementid) { this.elementid = elementid; }

    public Character getActive() { return active; }
    public void setActive(Character active) { this.active = active; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) { this.modifiedon = modifiedon; }
}