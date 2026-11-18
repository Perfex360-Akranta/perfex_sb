package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "bal_pcs_tl_productiondata", schema = "public")
public class BAL_PcsEntryProductiondata {

    @Id 
    @Column(name="pprd_keyid", length=15, nullable=false)
    private String keyid;

    @Column(name="pprd_flid", length=12, nullable = false)
    private String flid;

    @Column(name="pprd_cellid", length=10, nullable=false)
    private String cellid;

    @Column (name="pprd_date", nullable = false)
    private LocalDateTime date;

    @Column(name="pprd_shiftid", length=8, nullable = false)
    private String shiftid;

    @Column (name="pprd_hour", nullable = false)
    private Integer hour;

    @Column (name="pprd_productid", length = 10, nullable = false)
    private String productid;

    @Column (name="pprd_productmodel", length=15, nullable = false)
    private String productmodel;

    @Column (name="pprd_manpower", length = 10, nullable = false)
    private String manpower;

    @Column(name="pprd_cycletime", nullable = false)
    private Integer cycletime;

    @Column(name="pprd_producedqty", nullable = false)
    private Integer producedqty;

    @Column (name="pprd_rejectedqty", nullable = false)
    private Integer rejectedqty;

    @Column (name="pprd_reworkqty", nullable = false)
    private Integer reworkqty;

    @Column (name="pprd_shiftnoplan", length=1, nullable=false)
    private String shiftnoplan;

    @Column (name="pprd_hourlynoplan", length = 1, nullable = false)
    private String hourlynoplan;

    @Column (name="pprd_noplanduration", nullable = false)
    private Integer noplanduration;

    @Column (name="pprd_remarks", length=500, nullable=false)
    private String remarks;

    @Column (name="pprd_tempfield1", length=15, nullable = false)
    private String tempfield1;

    @Column (name="pprd_tempfield2", length=15, nullable = false)
    private String tempfield2;

    @Column (name="pprd_tempfield3", length=1, nullable = false)
    private String tempfield3;

    @Column (name="pprd_active", length = 1, nullable = false)
    private Character active;

    @Column (name="pprd_createdby", length = 8, nullable = false)
    private String createdby;

    @Column (name="pprd_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column (name="pprd_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getShiftid() {
        return shiftid;
    }

    public void setShiftid(String shiftid) {
        this.shiftid = shiftid;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductmodel() {
        return productmodel;
    }

    public void setProductmodel(String productmodel) {
        this.productmodel = productmodel;
    }

    public String getManpower() {
        return manpower;
    }

    public void setManpower(String manpower) {
        this.manpower = manpower;
    }

    public Integer getCycletime() {
        return cycletime;
    }

    public void setCycletime(Integer cycletime) {
        this.cycletime = cycletime;
    }

    public Integer getProducedqty() {
        return producedqty;
    }

    public void setProducedqty(Integer producedqty) {
        this.producedqty = producedqty;
    }

    public Integer getRejectedqty() {
        return rejectedqty;
    }

    public void setRejectedqty(Integer rejectedqty) {
        this.rejectedqty = rejectedqty;
    }

    public Integer getReworkqty() {
        return reworkqty;
    }

    public void setReworkqty(Integer reworkqty) {
        this.reworkqty = reworkqty;
    }

    public String getShiftnoplan() {
        return shiftnoplan;
    }

    public void setShiftnoplan(String shiftnoplan) {
        this.shiftnoplan = shiftnoplan;
    }

    public String getHourlynoplan() {
        return hourlynoplan;
    }

    public void setHourlynoplan(String hourlynoplan) {
        this.hourlynoplan = hourlynoplan;
    }

    public Integer getNoplanduration() {
        return noplanduration;
    }

    public void setNoplanduration(Integer noplanduration) {
        this.noplanduration = noplanduration;
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
