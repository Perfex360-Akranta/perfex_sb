package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bal_plm_tl_sparecostactual")
public class BalPlmTlSparecostactual {

    @Id
    @Column(name = "psca_pmcalendarid", length = 15)
    private String pmcalendarid;

    @Column(name = "psca_sitreference", length = 15, nullable = false)
    private String sitreference;

    @Column(name = "psca_sparesid", length = 15, nullable = false)
    private String sparesid;

    @Column(name = "psca_quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "psca_rate", nullable = false)
    private BigDecimal rate;

    @Column(name = "psca_value", nullable = false)
    private BigDecimal value;

    @Column(name = "psca_refdocno", length = 20, nullable = false)
    private String refdocno;

    @Column(name = "psca_doctype", length = 3, nullable = false)
    private String doctype;

    @Column(name = "psca_requestedby", length = 10, nullable = false)
    private String requestedby;

    @Column(name = "psca_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "psca_tempfield1", length = 500, nullable = false)
    private String tempfield1;

    @Column(name = "psca_tempfield2", length = 500, nullable = false)
    private String tempfield2;

    @Column(name = "psca_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "psca_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "psca_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getPmcalendarid() {
        return pmcalendarid;
    }

    public void setPmcalendarid(String pmcalendarid) {
        this.pmcalendarid = pmcalendarid;
    }

    public String getSitreference() {
        return sitreference;
    }

    public void setSitreference(String sitreference) {
        this.sitreference = sitreference;
    }

    public String getSparesid() {
        return sparesid;
    }

    public void setSparesid(String sparesid) {
        this.sparesid = sparesid;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getRefdocno() {
        return refdocno;
    }

    public void setRefdocno(String refdocno) {
        this.refdocno = refdocno;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getRequestedby() {
        return requestedby;
    }

    public void setRequestedby(String requestedby) {
        this.requestedby = requestedby;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
