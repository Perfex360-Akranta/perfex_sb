package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="kpi_tl_actual",schema = "public")
public class KpiTlActual {

    @Id
    @Column(name = "kauk_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "kauk_indicatorid", length = 15, nullable = false)
    private String indicatorid;

    @Column(name = "kauk_deptid", length = 15, nullable = false)
    private String deptid;

    @Column(name = "kauk_depttype", length = 15, nullable = false)
    private String depttype;

    @Column(name = "kauk_pillarid", length = 15, nullable = false)
    private String pillarid;

    @Column(name = "kauk_calendaryear", nullable = false)
    private BigDecimal calendaryear;

    @Column(name = "kauk_monthyear", nullable = false)
    private LocalDateTime monthyear;

    @Column(name = "kauk_excellencevalue", nullable = false)
    private BigDecimal excellencevalue;

    @Column(name = "kauk_benchmarkvalue", nullable = false)
    private BigDecimal benchmarkvalue;

    @Column(name = "kauk_value", nullable = false)
    private BigDecimal value;

    @Column(name = "kauk_isactual", length = 1, nullable = false)
    private Character isactual;

    @Column(name = "kauk_freqtype", length = 1, nullable = false)
    private Character freqtype;

    @Column(name = "kauk_status", length = 1, nullable = false)
    private Character status;

    @Column(name = "kauk_tempfield1", length = 100, nullable = false)
    private String tempfield1;

    @Column(name = "kauk_tempfield2", length = 100, nullable = false)
    private String tempfield2;

    @Column(name = "kauk_tempfield3", length = 100, nullable = false)
    private String tempfield3;

    @Column(name = "kauk_active", length = 1 , nullable = false)
    private Character active;

    @Column(name = "kauk_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "kauk_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kauk_modifiedon", nullable = false)
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

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDepttype() {
        return depttype;
    }

    public void setDepttype(String depttype) {
        this.depttype = depttype;
    }

    public String getPillarid() {
        return pillarid;
    }

    public void setPillarid(String pillarid) {
        this.pillarid = pillarid;
    }

    public BigDecimal getCalendaryear() {
        return calendaryear;
    }

    public void setCalendaryear(BigDecimal calendaryear) {
        this.calendaryear = calendaryear;
    }

    public LocalDateTime getMonthyear() {
        return monthyear;
    }

    public void setMonthyear(LocalDateTime monthyear) {
        this.monthyear = monthyear;
    }

    public BigDecimal getExcellencevalue() {
        return excellencevalue;
    }

    public void setExcellencevalue(BigDecimal excellencevalue) {
        this.excellencevalue = excellencevalue;
    }

    public BigDecimal getBenchmarkvalue() {
        return benchmarkvalue;
    }

    public void setBenchmarkvalue(BigDecimal benchmarkvalue) {
        this.benchmarkvalue = benchmarkvalue;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Character getIsactual() {
        return isactual;
    }

    public void setIsactual(Character isactual) {
        this.isactual = isactual;
    }

    public Character getFreqtype() {
        return freqtype;
    }

    public void setFreqtype(Character freqtype) {
        this.freqtype = freqtype;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
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
