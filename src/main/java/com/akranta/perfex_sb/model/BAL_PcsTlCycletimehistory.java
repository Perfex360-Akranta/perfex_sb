package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name="bal_pcs_tl_cycletimehistory", schema = "public")
public class BAL_PcsTlCycletimehistory {

    @Id 
    @Column (name="cyth_keyid", length = 10, nullable = false)
    private String keyid;

    @Column (name="cyth_cellid", length = 10, nullable = false)
    private String cellid;

    @Column (name="cyth_productid", length=10, nullable = false)
    private String productid;

    @Column (name="cyth_cycletime", nullable = false)
    private Integer cycletime;

    @Column (name="cyth_manpower", nullable = false)
    private Integer manpower;

    @Column (name="cyth_fromdate", nullable = false)
    private LocalDateTime fromdate;

    @Column (name="cyth_tilldate", nullable = false)
    private LocalDateTime tilldate;

    @Column (name="cyth_prodgroupid", length = 15, nullable = false)
    private String prodgroupid;

    @Column (name="cyth_machineid", length=10, nullable = false)
    private String machineid;

    @Column (name = "cyth_sectionid", length = 10, nullable = false)
    private String sectionid;

    @Column (name="cyth_factoryid", length = 10, nullable = false)
    private String factoryid;

    @Column (name="cyth_cavity", nullable = false)
    private Integer cavity;

    @Column (name="cyth_mandrels", nullable = false)
    private Integer mandrels;

    @Column (name="cyth_tempfield1", length = 1, nullable = false)
    private Character tempfield1;

    @Column (name="cyth_tempfield2", length = 1, nullable = false)
    private Character tempfield2;

    @Column (name = "cyth_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column (name="cyth_active", length=1, nullable = false)
    private Character active;

    @Column (name = "cyth_createdby", length = 12, nullable = false)
    private String createdby;

    @Column (name = "cyth_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column (name = "cyth_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    //Getter Setter

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Integer getCycletime() {
        return cycletime;
    }

    public void setCycletime(Integer cycletime) {
        this.cycletime = cycletime;
    }

    public Integer getManpower() {
        return manpower;
    }

    public void setManpower(Integer manpower) {
        this.manpower = manpower;
    }

    public LocalDateTime getFromdate() {
        return fromdate;
    }

    public void setFromdate(LocalDateTime fromdate) {
        this.fromdate = fromdate;
    }

    public LocalDateTime getTilldate() {
        return tilldate;
    }

    public void setTilldate(LocalDateTime tilldate) {
        this.tilldate = tilldate;
    }

    public String getProdgroupid() {
        return prodgroupid;
    }

    public void setProdgroupid(String prodgroupid) {
        this.prodgroupid = prodgroupid;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getFactoryid() {
        return factoryid;
    }

    public void setFactoryid(String factoryid) {
        this.factoryid = factoryid;
    }

    public Integer getCavity() {
        return cavity;
    }

    public void setCavity(Integer cavity) {
        this.cavity = cavity;
    }

    public Integer getMandrels() {
        return mandrels;
    }

    public void setMandrels(Integer mandrels) {
        this.mandrels = mandrels;
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
