package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wom_tl_othercostactual", schema = "public")
public class BAL_WomTlOthercostactual {

    @EmbeddedId
    private BAL_WomTlOthercostactualId id;

    @Column(name = "otcd_doctype", length = 3, nullable = false)
    private String doctype;

    @Column(name = "otcd_amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "otcd_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "otcd_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "otcd_tempfield1", length = 500, nullable = false)
    private String tempfield1;

    @Column(name = "otcd_tempfield2", length = 500, nullable = false)
    private String tempfield2;

    @Column(name = "otcd_tempfield3", length = 500, nullable = false)
    private String tempfield3;

    @Column(name = "otcd_tempfield4", length = 500, nullable = false)
    private String tempfield4;

    @Column(name = "otcd_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "otcd_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "otcd_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    // ── Composite id ─────────────────────────────────────────────────────
    public BAL_WomTlOthercostactualId getId() { return id; }
    public void setId(BAL_WomTlOthercostactualId id) { this.id = id; }

    // ── Convenience accessors delegating into the embedded id ─────────────
    public String getWoid() { return id != null ? id.getWoid() : null; }
    public void setWoid(String woid) {
        if (id == null) id = new BAL_WomTlOthercostactualId();
        id.setWoid(woid);
    }

    public String getRequestedby() { return id != null ? id.getRequestedby() : null; }
    public void setRequestedby(String requestedby) {
        if (id == null) id = new BAL_WomTlOthercostactualId();
        id.setRequestedby(requestedby);
    }

    public String getOthercostmstid() { return id != null ? id.getOthercostmstid() : null; }
    public void setOthercostmstid(String othercostmstid) {
        if (id == null) id = new BAL_WomTlOthercostactualId();
        id.setOthercostmstid(othercostmstid);
    }

    public String getDoctype() { return doctype; }
    public void setDoctype(String doctype) { this.doctype = doctype; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getTempfield1() { return tempfield1; }
    public void setTempfield1(String tempfield1) { this.tempfield1 = tempfield1; }

    public String getTempfield2() { return tempfield2; }
    public void setTempfield2(String tempfield2) { this.tempfield2 = tempfield2; }

    public String getTempfield3() { return tempfield3; }
    public void setTempfield3(String tempfield3) { this.tempfield3 = tempfield3; }

    public String getTempfield4() { return tempfield4; }
    public void setTempfield4(String tempfield4) { this.tempfield4 = tempfield4; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) { this.modifiedon = modifiedon; }
}