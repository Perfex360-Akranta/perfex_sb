package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name="bal_pcs_tl_cycletimemst", schema="public")
public class BAL_PcsTlCycletimemst {

    @Id 
    @Column(name ="cytm_keyid", length=10, nullable=false)
    private String keyid;

    @Column (name="cytm_cellid", length=10)
    private String cellid;

    @Column (name="cytm_productid", length=30)
    private String productid;

    @Column (name="cytm_cycletime")
    private Integer cycletime;

    @Column (name="cytm_manpower")
    private Integer manpower;

    @Column (name="cytm_fromdate", nullable = false)
    private LocalDateTime fromdate;

    @Column (name="cytm_tilldate", nullable = false)
    private LocalDateTime tilldate;

    @Column (name="cytm_prodgroupid", length=15, nullable = false)
    private String prodgroupid;

    @Column (name="cytm_machineid", length = 10, nullable = false)
    private String machineid;

    @Column (name="cytm_sectionid", length =10)
    private String sectionid;

    @Column (name="cytm_factoryid", length=10)
    private String factoryid;

    @Column (name="cytm_cavity")
    private Integer cavity;

    @Column (name="cytm_mandrels")
    private Integer mandrels;

    @Column (name="cytm_tempfield1", length = 1)
    private Character tempfield1;

    @Column (name="cytm_flid", length = 12, nullable = false)
    private String tempfield2;

    @Column (name="cytm_elementid", length = 500, nullable = false)
    private String tempfield3;

    @Column (name="cytm_active", length = 1, nullable = false)
    private Character active;

    @Column (name="cytm_createdby", length = 12, nullable = false)
    private String createdby;

    @Column (name="cytm_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column (name="cytm_modifiedon", nullable = false)
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
