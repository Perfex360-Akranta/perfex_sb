package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wom_tl_sparecostactual", schema = "public")
public class BAL_WomTlSparecostactual {

    @EmbeddedId
    private BAL_WomTlSparecostactualId id;

    @Column(name = "wsca_sitreference", length = 15, nullable = false)
    private String sitreference;

    @Column(name = "wsca_quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "wsca_rate", nullable = false)
    private BigDecimal rate;

    @Column(name = "wsca_value", nullable = false)
    private BigDecimal value;

    @Column(name = "wsca_refdocno", length = 20, nullable = false)
    private String refdocno;

    @Column(name = "wsca_doctype", length = 3, nullable = false)
    private String doctype;

    @Column(name = "wsca_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "wsca_tempfield1", length = 500, nullable = false)
    private String tempfield1;

    @Column(name = "wsca_tempfield2", length = 500, nullable = false)
    private String tempfield2;

    @Column(name = "wsca_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "wsca_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "wsca_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    // ── Composite id ─────────────────────────────────────────────────────
    public BAL_WomTlSparecostactualId getId() { return id; }
    public void setId(BAL_WomTlSparecostactualId id) { this.id = id; }

    // ── Convenience accessors delegating into the embedded id ─────────────
    public String getWoid() { return id != null ? id.getWoid() : null; }
    public void setWoid(String woid) {
        if (id == null) id = new BAL_WomTlSparecostactualId();
        id.setWoid(woid);
    }

    public String getRequestedby() { return id != null ? id.getRequestedby() : null; }
    public void setRequestedby(String requestedby) {
        if (id == null) id = new BAL_WomTlSparecostactualId();
        id.setRequestedby(requestedby);
    }

    public String getSparesid() { return id != null ? id.getSparesid() : null; }
    public void setSparesid(String sparesid) {
        if (id == null) id = new BAL_WomTlSparecostactualId();
        id.setSparesid(sparesid);
    }

    public String getSitreference() { return sitreference; }
    public void setSitreference(String sitreference) { this.sitreference = sitreference; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public String getRefdocno() { return refdocno; }
    public void setRefdocno(String refdocno) { this.refdocno = refdocno; }

    public String getDoctype() { return doctype; }
    public void setDoctype(String doctype) { this.doctype = doctype; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getTempfield1() { return tempfield1; }
    public void setTempfield1(String tempfield1) { this.tempfield1 = tempfield1; }

    public String getTempfield2() { return tempfield2; }
    public void setTempfield2(String tempfield2) { this.tempfield2 = tempfield2; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) { this.modifiedon = modifiedon; }
}