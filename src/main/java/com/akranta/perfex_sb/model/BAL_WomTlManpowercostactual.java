package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bal_wom_tl_manpowercostactual", schema = "public")
public class BAL_WomTlManpowercostactual {

    @EmbeddedId
    private BAL_WomTlManpowercostactualId id;

    @Column(name = "mpcs_doctype", length = 3, nullable = false)
    private String doctype;

    @Column(name = "mpcs_normalwt", nullable = false)
    private BigDecimal normalwt;

    @Column(name = "mpcs_holidaywt", nullable = false)
    private BigDecimal holidaywt;

    @Column(name = "mpcs_otherwt", nullable = false)
    private BigDecimal otherwt;

    @Column(name = "mpcs_normalrate", nullable = false)
    private BigDecimal normalrate;

    @Column(name = "mpcs_holidayrate", nullable = false)
    private BigDecimal holidayrate;

    @Column(name = "mpcs_otherrate", nullable = false)
    private BigDecimal otherrate;

    @Column(name = "mpcs_totalvalue", nullable = false)
    private BigDecimal totalvalue;

    @Column(name = "mpcs_noofhelpers", nullable = false)
    private BigDecimal noofhelpers;

    @Column(name = "mpcs_skillflag", length = 1, nullable = false)
    private Character skillflag;

    @Column(name = "mpcs_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "mpcs_activity", length = 500, nullable = false)
    private String activity;

    @Column(name = "mpcs_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "mpcs_tempfield1", length = 500, nullable = false)
    private String tempfield1;

    @Column(name = "mpcs_tempfield2", length = 500, nullable = false)
    private String tempfield2;

    @Column(name = "mpcs_tempfield3", length = 500, nullable = false)
    private String tempfield3;

    @Column(name = "mpcs_tempfield4", length = 500, nullable = false)
    private String tempfield4;

    @Column(name = "mpcs_tempfield5", length = 500, nullable = false)
    private String tempfield5;

    @Column(name = "mpcs_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "mpcs_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "mpcs_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    // ── Composite id ─────────────────────────────────────────────────────
    public BAL_WomTlManpowercostactualId getId() { return id; }
    public void setId(BAL_WomTlManpowercostactualId id) { this.id = id; }

    // ── Convenience accessors delegating into the embedded id ─────────────
    public String getMaintwoid() { return id != null ? id.getMaintwoid() : null; }
    public void setMaintwoid(String maintwoid) {
        if (id == null) id = new BAL_WomTlManpowercostactualId();
        id.setMaintwoid(maintwoid);
    }

    public String getManpowerid() { return id != null ? id.getManpowerid() : null; }
    public void setManpowerid(String manpowerid) {
        if (id == null) id = new BAL_WomTlManpowercostactualId();
        id.setManpowerid(manpowerid);
    }

    public String getSkillid() { return id != null ? id.getSkillid() : null; }
    public void setSkillid(String skillid) {
        if (id == null) id = new BAL_WomTlManpowercostactualId();
        id.setSkillid(skillid);
    }

    public String getDoctype() { return doctype; }
    public void setDoctype(String doctype) { this.doctype = doctype; }

    public BigDecimal getNormalwt() { return normalwt; }
    public void setNormalwt(BigDecimal normalwt) { this.normalwt = normalwt; }

    public BigDecimal getHolidaywt() { return holidaywt; }
    public void setHolidaywt(BigDecimal holidaywt) { this.holidaywt = holidaywt; }

    public BigDecimal getOtherwt() { return otherwt; }
    public void setOtherwt(BigDecimal otherwt) { this.otherwt = otherwt; }

    public BigDecimal getNormalrate() { return normalrate; }
    public void setNormalrate(BigDecimal normalrate) { this.normalrate = normalrate; }

    public BigDecimal getHolidayrate() { return holidayrate; }
    public void setHolidayrate(BigDecimal holidayrate) { this.holidayrate = holidayrate; }

    public BigDecimal getOtherrate() { return otherrate; }
    public void setOtherrate(BigDecimal otherrate) { this.otherrate = otherrate; }

    public BigDecimal getTotalvalue() { return totalvalue; }
    public void setTotalvalue(BigDecimal totalvalue) { this.totalvalue = totalvalue; }

    public BigDecimal getNoofhelpers() { return noofhelpers; }
    public void setNoofhelpers(BigDecimal noofhelpers) { this.noofhelpers = noofhelpers; }

    public Character getSkillflag() { return skillflag; }
    public void setSkillflag(Character skillflag) { this.skillflag = skillflag; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getActivity() { return activity; }
    public void setActivity(String activity) { this.activity = activity; }

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