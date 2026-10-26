package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "wom_tl_sparecostplan", schema = "public")
public class BAL_WomTlSparecostplan {

    @EmbeddedId
    private BAL_WomTlSparecostplanId id;

    @Column(name = "wscp_doctype", length = 3, nullable = false)
    private String doctype;

    @Column(name = "wscp_quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "wscp_rate", nullable = false)
    private BigDecimal rate;

    @Column(name = "wscp_value", nullable = false)
    private BigDecimal value;

    @Column(name = "wscp_refdocno", length = 20, nullable = false)
    private String refdocno;

    @Column(name = "wscp_date", nullable = false)
    private LocalDate date;

    @Column(name = "wscp_tempfield1", length = 500, nullable = false)
    private String tempfield1;

    @Column(name = "wscp_tempfield2", length = 500, nullable = false)
    private String tempfield2;

    @Column(name = "wscp_tempfield3", length = 500, nullable = false)
    private String tempfield3;

    @Column(name = "wscp_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "wscp_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "wscp_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "wscp_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    // ── Composite id ─────────────────────────────────────────────────────
    public BAL_WomTlSparecostplanId getId() { return id; }
    public void setId(BAL_WomTlSparecostplanId id) { this.id = id; }

    // ── Convenience accessors delegating into the embedded id ─────────────
    public String getWoid() { return id != null ? id.getWoid() : null; }
    public void setWoid(String woid) {
        if (id == null) id = new BAL_WomTlSparecostplanId();
        id.setWoid(woid);
    }

    public String getRequestedby() { return id != null ? id.getRequestedby() : null; }
    public void setRequestedby(String requestedby) {
        if (id == null) id = new BAL_WomTlSparecostplanId();
        id.setRequestedby(requestedby);
    }

    public String getSparesid() { return id != null ? id.getSparesid() : null; }
    public void setSparesid(String sparesid) {
        if (id == null) id = new BAL_WomTlSparecostplanId();
        id.setSparesid(sparesid);
    }

    public String getDoctype() { return doctype; }
    public void setDoctype(String doctype) { this.doctype = doctype; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public String getRefdocno() { return refdocno; }
    public void setRefdocno(String refdocno) { this.refdocno = refdocno; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getTempfield1() { return tempfield1; }
    public void setTempfield1(String tempfield1) { this.tempfield1 = tempfield1; }

    public String getTempfield2() { return tempfield2; }
    public void setTempfield2(String tempfield2) { this.tempfield2 = tempfield2; }

    public String getTempfield3() { return tempfield3; }
    public void setTempfield3(String tempfield3) { this.tempfield3 = tempfield3; }

    public Character getActive() { return active; }
    public void setActive(Character active) { this.active = active; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) { this.modifiedon = modifiedon; }
}