package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name = "bal_plm_tl_spareconsumed")
public class BalPlmTlSpareconsumed {

    @Id
    @Column(name = "pspc_keyid", length = 12)
    private String keyid;

    @Column(name = "pspc_wodetailid", length = 12, nullable = false)
    private String wodetailid;

    @Column(name = "pspc_pmcalendarid", length = 12, nullable = false)
    private String pmcalendarid;

    @Column(name = "pspc_spareid", length = 20, nullable = false)
    private String spareid;

    @Column(name = "pspc_quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "pspc_cost", nullable = false)
    private BigDecimal cost;

    @Column(name = "pspc_isactivitydone", length = 1, nullable = false)
    private String isactivitydone;

    @Column(name = "pspc_remarks", length = 200, nullable = false)
    private String remarks;

    @Column(name = "pspc_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "pspc_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "pspc_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getWodetailid() {
        return wodetailid;
    }

    public void setWodetailid(String wodetailid) {
        this.wodetailid = wodetailid;
    }

    public String getPmcalendarid() {
        return pmcalendarid;
    }

    public void setPmcalendarid(String pmcalendarid) {
        this.pmcalendarid = pmcalendarid;
    }

    public String getSpareid() {
        return spareid;
    }

    public void setSpareid(String spareid) {
        this.spareid = spareid;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getIsactivitydone() {
        return isactivitydone;
    }

    public void setIsactivitydone(String isactivitydone) {
        this.isactivitydone = isactivitydone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
