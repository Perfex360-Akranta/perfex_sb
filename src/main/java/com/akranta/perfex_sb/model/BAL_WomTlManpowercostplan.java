package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wom_tl_manpowercostplan", schema = "public")
public class BAL_WomTlManpowercostplan {

    @EmbeddedId
    private BAL_WomTlManpowercostplanId id;

    @Column(name = "mpcp_doctype", length = 3, nullable = false)
    private String doctype;

    @Column(name = "mpcp_normalmins", nullable = false)
    private BigDecimal normalmins;

    @Column(name = "mpcp_holidaymins", nullable = false)
    private BigDecimal holidaymins;

    @Column(name = "mpcp_othermins", nullable = false)
    private BigDecimal othermins;

    @Column(name = "mpcp_normalcost", nullable = false)
    private BigDecimal normalcost;

    @Column(name = "mpcp_holidaycost", nullable = false)
    private BigDecimal holidaycost;

    @Column(name = "mpcp_othercost", nullable = false)
    private BigDecimal othercost;

    @Column(name = "mpcp_totalvalue", nullable = false)
    private BigDecimal totalvalue;

    @Column(name = "mpcp_noofhelpers", nullable = false)
    private BigDecimal noofhelpers;

    @Column(name = "mpcp_skillflag", length = 1, nullable = false)
    private Character skillflag;

    @Column(name = "mpcp_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "mpcp_activity", length = 500, nullable = false)
    private String activity;

    @Column(name = "mpcp_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "mpcp_tempfield1", length = 500, nullable = false)
    private String tempfield1;

    @Column(name = "mpcp_tempfield2", length = 500, nullable = false)
    private String tempfield2;

    @Column(name = "mpcp_tempfield3", length = 500, nullable = false)
    private String tempfield3;

    @Column(name = "mpcp_tempfield4", length = 500, nullable = false)
    private String tempfield4;

    @Column(name = "mpcp_tempfield5", length = 500, nullable = false)
    private String tempfield5;

    @Column(name = "mpcp_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "mpcp_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "mpcp_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "mpcp_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    // ── Composite id ─────────────────────────────────────────────────────
    public BAL_WomTlManpowercostplanId getId() { return id; }
    public void setId(BAL_WomTlManpowercostplanId id) { this.id = id; }

    // ── Convenience accessors delegating into the embedded id ─────────────
    public String getWoid() { return id != null ? id.getWoid() : null; }
    public void setWoid(String woid) {
        if (id == null) id = new BAL_WomTlManpowercostplanId();
        id.setWoid(woid);
    }

    public String getManpowerid() { return id != null ? id.getManpowerid() : null; }
    public void setManpowerid(String manpowerid) {
        if (id == null) id = new BAL_WomTlManpowercostplanId();
        id.setManpowerid(manpowerid);
    }

    public String getSkillid() { return id != null ? id.getSkillid() : null; }
    public void setSkillid(String skillid) {
        if (id == null) id = new BAL_WomTlManpowercostplanId();
        id.setSkillid(skillid);
    }

    public String getDoctype() { return doctype; }
    public void setDoctype(String doctype) { this.doctype = doctype; }

    public BigDecimal getNormalmins() { return normalmins; }
    public void setNormalmins(BigDecimal normalmins) { this.normalmins = normalmins; }

    public BigDecimal getHolidaymins() { return holidaymins; }
    public void setHolidaymins(BigDecimal holidaymins) { this.holidaymins = holidaymins; }

    public BigDecimal getOthermins() { return othermins; }
    public void setOthermins(BigDecimal othermins) { this.othermins = othermins; }

    public BigDecimal getNormalcost() { return normalcost; }
    public void setNormalcost(BigDecimal normalcost) { this.normalcost = normalcost; }

    public BigDecimal getHolidaycost() { return holidaycost; }
    public void setHolidaycost(BigDecimal holidaycost) { this.holidaycost = holidaycost; }

    public BigDecimal getOthercost() { return othercost; }
    public void setOthercost(BigDecimal othercost) { this.othercost = othercost; }

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

    public Character getActive() { return active; }
    public void setActive(Character active) { this.active = active; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) { this.modifiedon = modifiedon; }
}