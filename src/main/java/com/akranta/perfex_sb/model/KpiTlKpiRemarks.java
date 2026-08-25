package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="kpi_tl_kpiremarks",schema = "public")
public class KpiTlKpiRemarks {
    @Id
    @Column(name = "kprm_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "kprm_indicatorid", length = 25, nullable = false)
    private String indicatorid;

    @Column(name = "kprm_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "kprm_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "kprm_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "kprm_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "kprm_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "kprm_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "kprm_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "kprm_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "kprm_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "kprm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "kprm_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kprm_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getIndicatorid() {
        return indicatorid;
    }

    public void setIndicatorid(String indicatorid) {
        this.indicatorid = indicatorid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(String tempfield3) {
        this.tempfield3 = tempfield3;
    }

    public String getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(String tempfield4) {
        this.tempfield4 = tempfield4;
    }

    public String getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(String tempfield5) {
        this.tempfield5 = tempfield5;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
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
