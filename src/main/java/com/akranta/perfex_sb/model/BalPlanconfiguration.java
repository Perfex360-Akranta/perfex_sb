
package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BAL_PLM_TL_PLANCONFIGURATION", schema = "public")
public class BalPlanconfiguration {

    @Id
    @Column(name = "pplc_keyid", length = 15)
    private String keyid;

    @Column(name = "pplc_factoryid", length = 10, nullable = false)
    private String factoryid;

    @Column(name = "pplc_sectionid", length = 10, nullable = false)
    private String sectionid;

    @Column(name = "pplc_cellid", length = 10, nullable = false)
    private String cellid;

    @Column(name = "pplc_machineid", length = 10, nullable = false)
    private String machineid;

    @Column(name = "pplc_level", length = 10, nullable = false)
    private String level;

    @Column(name = "pplc_weekno", length = 1, nullable = false)
    private Character weekno;

    @Column(name = "pplc_monthly", nullable = false)
    private LocalDateTime monthly;

    @Column(name = "pplc_quarterly", nullable = false)
    private LocalDateTime quarterly;

    @Column(name = "pplc_halfyearly", nullable = false)
    private LocalDateTime halfyearly;

    @Column(name = "pplc_yearly", nullable = false)
    private LocalDateTime yearly;

    @Column(name = "pplc_yearly2", nullable = false)
    private LocalDateTime yearly2;

    @Column(name = "pplc_yearly3", nullable = false)
    private LocalDateTime yearly3;

    @Column(name = "pplc_yearly4", nullable = false)
    private LocalDateTime yearly4;

    @Column(name = "pplc_yearly5", nullable = false)
    private LocalDateTime yearly5;

    @Column(name = "pplc_yearly6", nullable = false)
    private LocalDateTime yearly6;

    @Column(name = "pplc_yearly7", nullable = false)
    private LocalDateTime yearly7;

    @Column(name = "pplc_yearly8", nullable = false)
    private LocalDateTime yearly8;

    @Column(name = "pplc_yearly9", nullable = false)
    private LocalDateTime yearly9;

    @Column(name = "pplc_yearly10", nullable = false)
    private LocalDateTime yearly10;

    @Column(name = "pplc_frequency", length = 50)
    private String frequency;

    @Column(name = "pplc_assemblyid", length = 10, nullable = false)
    private String assemblyid;

    @Column(name = "pplc_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "pplc_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "pplc_tempfield1", length = 20, nullable = false)
    private String tempfield1;

    @Column(name = "pplc_tempfield2", length = 20, nullable = false)
    private String tempfield2;

    @Column(name = "pplc_tempfield3", length = 20, nullable = false)
    private String tempfield3;

    @Column(name = "pplc_tempfield4", length = 20, nullable = false)
    private String tempfield4;

    @Column(name = "pplc_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "pplc_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "pplc_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "pplc_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFactoryid() {
        return factoryid;
    }

    public void setFactoryid(String factoryid) {
        this.factoryid = factoryid;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Character getWeekno() {
        return weekno;
    }

    public void setWeekno(Character weekno) {
        this.weekno = weekno;
    }

    public LocalDateTime getMonthly() {
        return monthly;
    }

    public void setMonthly(LocalDateTime monthly) {
        this.monthly = monthly;
    }

    public LocalDateTime getQuarterly() {
        return quarterly;
    }

    public void setQuarterly(LocalDateTime quarterly) {
        this.quarterly = quarterly;
    }

    public LocalDateTime getHalfyearly() {
        return halfyearly;
    }

    public void setHalfyearly(LocalDateTime halfyearly) {
        this.halfyearly = halfyearly;
    }

    public LocalDateTime getYearly() {
        return yearly;
    }

    public void setYearly(LocalDateTime yearly) {
        this.yearly = yearly;
    }

    public LocalDateTime getYearly2() {
        return yearly2;
    }

    public void setYearly2(LocalDateTime yearly2) {
        this.yearly2 = yearly2;
    }

    public LocalDateTime getYearly3() {
        return yearly3;
    }

    public void setYearly3(LocalDateTime yearly3) {
        this.yearly3 = yearly3;
    }

    public LocalDateTime getYearly4() {
        return yearly4;
    }

    public void setYearly4(LocalDateTime yearly4) {
        this.yearly4 = yearly4;
    }

    public LocalDateTime getYearly5() {
        return yearly5;
    }

    public void setYearly5(LocalDateTime yearly5) {
        this.yearly5 = yearly5;
    }

    public LocalDateTime getYearly6() {
        return yearly6;
    }

    public void setYearly6(LocalDateTime yearly6) {
        this.yearly6 = yearly6;
    }

    public LocalDateTime getYearly7() {
        return yearly7;
    }

    public void setYearly7(LocalDateTime yearly7) {
        this.yearly7 = yearly7;
    }

    public LocalDateTime getYearly8() {
        return yearly8;
    }

    public void setYearly8(LocalDateTime yearly8) {
        this.yearly8 = yearly8;
    }

    public LocalDateTime getYearly9() {
        return yearly9;
    }

    public void setYearly9(LocalDateTime yearly9) {
        this.yearly9 = yearly9;
    }

    public LocalDateTime getYearly10() {
        return yearly10;
    }

    public void setYearly10(LocalDateTime yearly10) {
        this.yearly10 = yearly10;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getAssemblyid() {
        return assemblyid;
    }

    public void setAssemblyid(String assemblyid) {
        this.assemblyid = assemblyid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
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
