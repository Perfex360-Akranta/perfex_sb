package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wom_tl_utilitycostactual", schema = "public")
public class BAL_WomTlUtilitycostactual {

    @EmbeddedId
    private BAL_WomTlUtilitycostactualId id;

    @Column(name = "utca_doctype", length = 3, nullable = false)
    private String doctype;

    @Column(name = "utca_quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "utca_minutes", nullable = false)
    private BigDecimal minutes;

    @Column(name = "utca_cost", nullable = false)
    private BigDecimal cost;

    @Column(name = "utca_totalvalue", nullable = false)
    private BigDecimal totalvalue;

    @Column(name = "utca_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "utca_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "utca_tempfield2", length = 500, nullable = false)
    private String tempfield2;

    @Column(name = "utca_tempfield3", length = 500, nullable = false)
    private String tempfield3;

    @Column(name = "utca_tempfield4", length = 500, nullable = false)
    private String tempfield4;

    @Column(name = "utca_tempfield5", length = 500, nullable = false)
    private String tempfield5;

    @Column(name = "utca_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "utca_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "utca_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    // ── Composite id ─────────────────────────────────────────────────────
    public BAL_WomTlUtilitycostactualId getId() { return id; }
    public void setId(BAL_WomTlUtilitycostactualId id) { this.id = id; }

    // ── Convenience accessors delegating into the embedded id ─────────────
    public String getWokeyid() { return id != null ? id.getWokeyid() : null; }
    public void setWokeyid(String wokeyid) {
        if (id == null) id = new BAL_WomTlUtilitycostactualId();
        id.setWokeyid(wokeyid);
    }

    public String getRequestedby() { return id != null ? id.getRequestedby() : null; }
    public void setRequestedby(String requestedby) {
        if (id == null) id = new BAL_WomTlUtilitycostactualId();
        id.setRequestedby(requestedby);
    }

    public String getUtilitymstid() { return id != null ? id.getUtilitymstid() : null; }
    public void setUtilitymstid(String utilitymstid) {
        if (id == null) id = new BAL_WomTlUtilitycostactualId();
        id.setUtilitymstid(utilitymstid);
    }

    public String getDoctype() { return doctype; }
    public void setDoctype(String doctype) { this.doctype = doctype; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getMinutes() { return minutes; }
    public void setMinutes(BigDecimal minutes) { this.minutes = minutes; }

    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }

    public BigDecimal getTotalvalue() { return totalvalue; }
    public void setTotalvalue(BigDecimal totalvalue) { this.totalvalue = totalvalue; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getTempfield2() { return tempfield2; }
    public void setTempfield2(String tempfield2) { this.tempfield2 = tempfield2; }

    public String getTempfield3() { return tempfield3; }
    public void setTempfield3(String tempfield3) { this.tempfield3 = tempfield3; }

    public String getTempfield4() { return tempfield4; }
    public void setTempfield4(String tempfield4) { this.tempfield4 = tempfield4; }

    public String getTempfield5() { return tempfield5; }
    public void setTempfield5(String tempfield5) { this.tempfield5 = tempfield5; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) { this.modifiedon = modifiedon; }
}