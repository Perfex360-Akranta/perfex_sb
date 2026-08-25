package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="KPI_TL_INDICATOR_DEPT_LINK", schema = "public")
public class KpiTlIndicatorDeptLink {

    @Id 
    @Column(name = "kidl_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "kidl_indicatorid", length = 15, nullable = false)
    private String indicatorid;

    @Column(name = "kidl_deptid", length = 15, nullable = false)
    private String deptid;

    @Column(name = "kidl_depttype", length = 4, nullable = false)
    private String depttype;//depttype

    @Column(name = "kidl_pillarid", length = 6, nullable = false)
    private String pillarid;

    @Column(name = "kidl_effectivedate", nullable = false)
    private LocalDateTime effectivedate;

    @Column(name = "kidl_inactivedate", nullable = false)
    private LocalDateTime inactivedate;

    @Column(name = "kidl_tempfield1", length = 1, nullable = false)
    private Character tempfield1;

    @Column(name = "kidl_tempfield2", length = 1, nullable = false)
    private Character tempfield2;

    @Column(name = "kidl_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column(name = "kidl_tempfield4", length = 1, nullable = false)
    private Character tempfield4;

    @Column(name = "kidl_tempfield5", length = 1, nullable = false)
    private Character tempfield5;

    @Column(name = "kidl_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "kidl_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "kidl_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kidl_modifiedon", nullable = false)
    private LocalDateTime modifiedon;


    // =============TRANSIENT FIELDS =============
    
    @Transient
    private String isDelete = "";
    
    @Transient
    private List<KpiTlIndicatorDeptLink> methodPillarFactlink;

    

    // =============  METHODS start =============
    
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public List<KpiTlIndicatorDeptLink> getMethodPillarFactlink() {
        return methodPillarFactlink;
    }

    public void setMethodPillarFactlink(List<KpiTlIndicatorDeptLink> methodPillarFactlink) {
        this.methodPillarFactlink = methodPillarFactlink;
    }


     // =============  METHODS end =============

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

    public LocalDateTime getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(LocalDateTime effectivedate) {
        this.effectivedate = effectivedate;
    }

    public LocalDateTime getInactivedate() {
        return inactivedate;
    }

    public void setInactivedate(LocalDateTime inactivedate) {
        this.inactivedate = inactivedate;
    }

    public Character getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(Character tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public Character getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(Character tempfield2) {
        this.tempfield2 = tempfield2;
    }

    public Character getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(Character tempfield3) {
        this.tempfield3 = tempfield3;
    }

    public Character getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(Character tempfield4) {
        this.tempfield4 = tempfield4;
    }

    public Character getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(Character tempfield5) {
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
