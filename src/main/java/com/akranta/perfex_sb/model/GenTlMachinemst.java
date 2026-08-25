package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "gen_tl_machinemst", schema = "public")
public class GenTlMachinemst {
    
    @Id
    @Column(name = "mchm_keyid", length = 10, nullable = false)
    private String keyid;

    @Column(name = "mchm_machineno", length = 50, nullable = false)
    private String machineno;

    @Column(name = "mchm_machinename", length = 200, nullable = false)
    private String machinename;

    @Column(name = "mchm_cellid", length = 10, nullable = false)
    private String cellid;

    @Column(name = "mchm_subcellid", length = 10, nullable = false)
    private String subcellid;

    @Column(name = "mchm_equipmentgroup", length = 15, nullable = false)
    private String equipmentgroup;

    @Column(name = "mchm_controltype", length = 30, nullable = false)
    private String controltype;

    @Column(name = "mchm_purpose", length = 30, nullable = false)
    private String purpose;

    @Column(name = "mchm_category", length = 30, nullable = false)
    private String category;

    @Column(name = "mchm_subcategory", length = 30, nullable = false)
    private String subcategory;

    @Column(name = "mchm_machinerank", length = 10, nullable = false)
    private String machinerank;

    @Column(name = "mchm_jhstep", length = 6, nullable = false)
    private String jhstep;

    @Column(name = "mchm_jhstepdate", nullable = false)
    private LocalDateTime jhstepdate;

    @Column(name = "mchm_phase", length = 2, nullable = false)
    private String phase;

    @Column(name = "mchm_wires", length = 2, nullable = false)
    private String wires;

    @Column(name = "mchm_ipvolt", length = 20, nullable = false)
    private String ipvolt;

    @Column(name = "mchm_ipvoltmin", length = 20, nullable = false)
    private String ipvoltmin;

    @Column(name = "mchm_ipvoltmax", length = 20, nullable = false)
    private String ipvoltmax;

    @Column(name = "mchm_ipfreq", length = 20, nullable = false)
    private String ipfreq;

    @Column(name = "mchm_ipfreqmin", length = 20, nullable = false)
    private String ipfreqmin;

    @Column(name = "mchm_ipfreqmax", length = 20, nullable = false)
    private String ipfreqmax;

    @Column(name = "mchm_powersupply", length = 20, nullable = false)
    private String powersupply;

    @Column(name = "mchm_connectedload", length = 20, nullable = false)
    private String connectedload;

    @Column(name = "mchm_dbno", length = 50, nullable = false)
    private String dbno;

    @Column(name = "mchm_sbno", length = 50, nullable = false)
    private String sbno;

    @Column(name = "mchm_specification", length = 500, nullable = false)
    private String specification;

    @Column(name = "mchm_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "mchm_manufacturerid", length = 100, nullable = false)
    private String manufacturerid;

    @Column(name = "mchm_manufactureddate", nullable = false)
    private LocalDateTime manufactureddate;

    @Column(name = "mchm_make", length = 30, nullable = false)
    private String make;

    @Column(name = "mchm_model", length = 30, nullable = false)
    private String model;

    @Column(name = "mchm_mfrslno", length = 30, nullable = false)
    private String mfrslno;

    @Column(name = "mchm_mfrremarks", length = 200, nullable = false)
    private String mfrremarks;

    @Column(name = "mchm_supplierid", length = 8, nullable = false)
    private String supplierid;

    @Column(name = "mchm_pono", length = 20, nullable = false)
    private String pono;

    @Column(name = "mchm_podate", nullable = false)
    private LocalDateTime podate;

    @Column(name = "mchm_purchasedate", nullable = false)
    private LocalDateTime purchasedate;

    @Column(name = "mchm_purchaseprice", nullable = false)
    private BigDecimal purchaseprice;

    @Column(name = "mchm_installeddate", nullable = false)
    private LocalDateTime installeddate;

    @Column(name = "mchm_isunderwarranty", length = 1, nullable = false)
    private Character isunderwarranty;

    @Column(name = "mchm_warrantydate", nullable = false)
    private LocalDateTime warrantydate;

    @Column(name = "mchm_supplierremarks", length = 200, nullable = false)
    private String supplierremarks;

    @Column(name = "mchm_isunderamc", length = 1, nullable = false)
    private Character isunderamc;

    @Column(name = "mchm_amcdate", nullable = false)
    private LocalDateTime amcdate;

    @Column(name = "mchm_amcvendor", length = 12, nullable = false)
    private String amcvendor;

    @Column(name = "mchm_amcrenewaldate", nullable = false)
    private LocalDateTime amcrenewaldate;

    @Column(name = "mchm_amcremarks", length = 200, nullable = false)
    private String amcremarks;

    @Column(name = "mchm_machineorder", nullable = false)
    private BigDecimal machineorder;

    @Column(name = "mchm_effectivedate", nullable = false)
    private LocalDateTime effectivedate;

    @Column(name = "mchm_inactivateddate", nullable = false)
    private LocalDateTime inactivateddate;

    @Column(name = "mchm_includeforproduction", length = 1, nullable = false)
    private Character includeforproduction;

    @Column(name = "mchm_givesfinaloutput", length = 1, nullable = false)
    private Character givesfinaloutput;

    @Column(name = "mchm_costcentreid", length = 8, nullable = false)
    private String costcentreid;

    @Column(name = "mchm_circleid", length = 8, nullable = false)
    private String circleid;

    @Column(name = "mchm_iscavityormandrel", length = 1)
    private Character iscavityormandrel;

    @Column(name = "mchm_maxmeterreading", length = 10, nullable = false)
    private String maxmeterreading;

    @Column(name = "mchm_currencyid", length = 12, nullable = false)
    private String currencyid;

    @Column(name = "mchm_workcenter", length = 12, nullable = false)
    private String workcenter;

    @Column(name = "mchm_technicalid", length = 75, nullable = false)
    private String technicalid;

    @Column(name = "mchm_type", length = 10, nullable = false)
    private String type;

    @Column(name = "mchm_tradeid", length = 15, nullable = false)
    private String tradeid;

    @Column(name = "mchm_tempfield3", length = 100, nullable = false)
    private String tempfield3;

    @Column(name = "mchm_tempfield4", length = 100, nullable = false)
    private String tempfield4;

    @Column(name = "mchm_tempfield5", length = 100, nullable = false)
    private String tempfield5;

    @Column(name = "mchm_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "mchm_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "mchm_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "mchm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "mchm_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "mchm_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getMachineno() {
        return machineno;
    }

    public void setMachineno(String machineno) {
        this.machineno = machineno;
    }

    public String getMachinename() {
        return machinename;
    }

    public void setMachinename(String machinename) {
        this.machinename = machinename;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getSubcellid() {
        return subcellid;
    }

    public void setSubcellid(String subcellid) {
        this.subcellid = subcellid;
    }

    public String getEquipmentgroup() {
        return equipmentgroup;
    }

    public void setEquipmentgroup(String equipmentgroup) {
        this.equipmentgroup = equipmentgroup;
    }

    public String getControltype() {
        return controltype;
    }

    public void setControltype(String controltype) {
        this.controltype = controltype;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getMachinerank() {
        return machinerank;
    }

    public void setMachinerank(String machinerank) {
        this.machinerank = machinerank;
    }

    public String getJhstep() {
        return jhstep;
    }

    public void setJhstep(String jhstep) {
        this.jhstep = jhstep;
    }

    public LocalDateTime getJhstepdate() {
        return jhstepdate;
    }

    public void setJhstepdate(LocalDateTime jhstepdate) {
        this.jhstepdate = jhstepdate;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getWires() {
        return wires;
    }

    public void setWires(String wires) {
        this.wires = wires;
    }

    public String getIpvolt() {
        return ipvolt;
    }

    public void setIpvolt(String ipvolt) {
        this.ipvolt = ipvolt;
    }

    public String getIpvoltmin() {
        return ipvoltmin;
    }

    public void setIpvoltmin(String ipvoltmin) {
        this.ipvoltmin = ipvoltmin;
    }

    public String getIpvoltmax() {
        return ipvoltmax;
    }

    public void setIpvoltmax(String ipvoltmax) {
        this.ipvoltmax = ipvoltmax;
    }

    public String getIpfreq() {
        return ipfreq;
    }

    public void setIpfreq(String ipfreq) {
        this.ipfreq = ipfreq;
    }

    public String getIpfreqmin() {
        return ipfreqmin;
    }

    public void setIpfreqmin(String ipfreqmin) {
        this.ipfreqmin = ipfreqmin;
    }

    public String getIpfreqmax() {
        return ipfreqmax;
    }

    public void setIpfreqmax(String ipfreqmax) {
        this.ipfreqmax = ipfreqmax;
    }

    public String getPowersupply() {
        return powersupply;
    }

    public void setPowersupply(String powersupply) {
        this.powersupply = powersupply;
    }

    public String getConnectedload() {
        return connectedload;
    }

    public void setConnectedload(String connectedload) {
        this.connectedload = connectedload;
    }

    public String getDbno() {
        return dbno;
    }

    public void setDbno(String dbno) {
        this.dbno = dbno;
    }

    public String getSbno() {
        return sbno;
    }

    public void setSbno(String sbno) {
        this.sbno = sbno;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getManufacturerid() {
        return manufacturerid;
    }

    public void setManufacturerid(String manufacturerid) {
        this.manufacturerid = manufacturerid;
    }

    public LocalDateTime getManufactureddate() {
        return manufactureddate;
    }

    public void setManufactureddate(LocalDateTime manufactureddate) {
        this.manufactureddate = manufactureddate;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMfrslno() {
        return mfrslno;
    }

    public void setMfrslno(String mfrslno) {
        this.mfrslno = mfrslno;
    }

    public String getMfrremarks() {
        return mfrremarks;
    }

    public void setMfrremarks(String mfrremarks) {
        this.mfrremarks = mfrremarks;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono;
    }

    public LocalDateTime getPodate() {
        return podate;
    }

    public void setPodate(LocalDateTime podate) {
        this.podate = podate;
    }

    public LocalDateTime getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(LocalDateTime purchasedate) {
        this.purchasedate = purchasedate;
    }

    public BigDecimal getPurchaseprice() {
        return purchaseprice;
    }

    public void setPurchaseprice(BigDecimal purchaseprice) {
        this.purchaseprice = purchaseprice;
    }

    public LocalDateTime getInstalleddate() {
        return installeddate;
    }

    public void setInstalleddate(LocalDateTime installeddate) {
        this.installeddate = installeddate;
    }

    public Character getIsunderwarranty() {
        return isunderwarranty;
    }

    public void setIsunderwarranty(Character isunderwarranty) {
        this.isunderwarranty = isunderwarranty;
    }

    public LocalDateTime getWarrantydate() {
        return warrantydate;
    }

    public void setWarrantydate(LocalDateTime warrantydate) {
        this.warrantydate = warrantydate;
    }

    public String getSupplierremarks() {
        return supplierremarks;
    }

    public void setSupplierremarks(String supplierremarks) {
        this.supplierremarks = supplierremarks;
    }

    public Character getIsunderamc() {
        return isunderamc;
    }

    public void setIsunderamc(Character isunderamc) {
        this.isunderamc = isunderamc;
    }

    public LocalDateTime getAmcdate() {
        return amcdate;
    }

    public void setAmcdate(LocalDateTime amcdate) {
        this.amcdate = amcdate;
    }

    public String getAmcvendor() {
        return amcvendor;
    }

    public void setAmcvendor(String amcvendor) {
        this.amcvendor = amcvendor;
    }

    public LocalDateTime getAmcrenewaldate() {
        return amcrenewaldate;
    }

    public void setAmcrenewaldate(LocalDateTime amcrenewaldate) {
        this.amcrenewaldate = amcrenewaldate;
    }

    public String getAmcremarks() {
        return amcremarks;
    }

    public void setAmcremarks(String amcremarks) {
        this.amcremarks = amcremarks;
    }

    public BigDecimal getMachineorder() {
        return machineorder;
    }

    public void setMachineorder(BigDecimal machineorder) {
        this.machineorder = machineorder;
    }

    public LocalDateTime getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(LocalDateTime effectivedate) {
        this.effectivedate = effectivedate;
    }

    public LocalDateTime getInactivateddate() {
        return inactivateddate;
    }

    public void setInactivateddate(LocalDateTime inactivateddate) {
        this.inactivateddate = inactivateddate;
    }

    public Character getIncludeforproduction() {
        return includeforproduction;
    }

    public void setIncludeforproduction(Character includeforproduction) {
        this.includeforproduction = includeforproduction;
    }

    public Character getGivesfinaloutput() {
        return givesfinaloutput;
    }

    public void setGivesfinaloutput(Character givesfinaloutput) {
        this.givesfinaloutput = givesfinaloutput;
    }

    public String getCostcentreid() {
        return costcentreid;
    }

    public void setCostcentreid(String costcentreid) {
        this.costcentreid = costcentreid;
    }

    public String getCircleid() {
        return circleid;
    }

    public void setCircleid(String circleid) {
        this.circleid = circleid;
    }

    public Character getIscavityormandrel() {
        return iscavityormandrel;
    }

    public void setIscavityormandrel(Character iscavityormandrel) {
        this.iscavityormandrel = iscavityormandrel;
    }

    public String getMaxmeterreading() {
        return maxmeterreading;
    }

    public void setMaxmeterreading(String maxmeterreading) {
        this.maxmeterreading = maxmeterreading;
    }

    public String getCurrencyid() {
        return currencyid;
    }

    public void setCurrencyid(String currencyid) {
        this.currencyid = currencyid;
    }

    public String getWorkcenter() {
        return workcenter;
    }

    public void setWorkcenter(String workcenter) {
        this.workcenter = workcenter;
    }

    public String getTechnicalid() {
        return technicalid;
    }

    public void setTechnicalid(String technicalid) {
        this.technicalid = technicalid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
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

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
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