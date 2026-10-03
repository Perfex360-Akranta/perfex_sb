package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wom_tl_servicecostactual", schema = "public")
public class BAL_WomTlServicecostactual {

    @EmbeddedId
    private BAL_WomTlServicecostactualId id;

    @Column(name = "svca_doctype", length = 3, nullable = false)
    private String doctype;

    @Column(name = "svca_billno", length = 100, nullable = false)
    private String billno;

    @Column(name = "svca_billvalue", nullable = false)
    private BigDecimal billvalue;

    @Column(name = "svca_billdateflag", length = 1, nullable = false)
    private Character billdateflag;

    @Column(name = "svca_billdate", nullable = false)
    private LocalDateTime billdate;

    @Column(name = "svca_jobdescription", length = 500, nullable = false)
    private String jobdescription;

    @Column(name = "svca_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "svca_tempfield1", length = 500, nullable = false)
    private String tempfield1;

    @Column(name = "svca_tempfield2", length = 500, nullable = false)
    private String tempfield2;

    @Column(name = "svca_tempfield3", length = 500, nullable = false)
    private String tempfield3;

    @Column(name = "svca_tempfield4", length = 500, nullable = false)
    private String tempfield4;

    @Column(name = "svca_tempfield5", length = 500, nullable = false)
    private String tempfield5;

    @Column(name = "svca_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "svca_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "svca_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    // ── Composite id ─────────────────────────────────────────────────────
    public BAL_WomTlServicecostactualId getId() { return id; }
    public void setId(BAL_WomTlServicecostactualId id) { this.id = id; }

    // ── Convenience accessors delegating into the embedded id ─────────────
    public String getWoid() { return id != null ? id.getWoid() : null; }
    public void setWoid(String woid) {
        if (id == null) id = new BAL_WomTlServicecostactualId();
        id.setWoid(woid);
    }

    public String getServiceid() { return id != null ? id.getServiceid() : null; }
    public void setServiceid(String serviceid) {
        if (id == null) id = new BAL_WomTlServicecostactualId();
        id.setServiceid(serviceid);
    }

    public String getDoctype() { return doctype; }
    public void setDoctype(String doctype) { this.doctype = doctype; }

    public String getBillno() { return billno; }
    public void setBillno(String billno) { this.billno = billno; }

    public BigDecimal getBillvalue() { return billvalue; }
    public void setBillvalue(BigDecimal billvalue) { this.billvalue = billvalue; }

    public Character getBilldateflag() { return billdateflag; }
    public void setBilldateflag(Character billdateflag) { this.billdateflag = billdateflag; }

    public LocalDateTime getBilldate() { return billdate; }
    public void setBilldate(LocalDateTime billdate) { this.billdate = billdate; }

    public String getJobdescription() { return jobdescription; }
    public void setJobdescription(String jobdescription) { this.jobdescription = jobdescription; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getTempfield1() { return tempfield1; }
    public void setTempfield1(String tempfield1) { this.tempfield1 = tempfield1; }

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