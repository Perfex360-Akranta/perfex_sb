package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="bal_pcs_tl_enablelosscapture", schema="public")
public class BAL_PcsTlEnablelosscapture {

    @Id
    @Column(name="pelc_keyid", length=15, nullable =false)
    private String keyid;

    @Column (name="pelc_factoryid", length=10, nullable = false)
    private String factoryid;

    @Column (name="pelc_cellid", length=10, nullable = false)
    private String cellid;

    @Column (name="pelc_ispcsenabled", length = 1, nullable = false)
    private Character ispcsenabled;

    @Column (name="pelc_effectivefrom", nullable = false)
    private LocalDateTime effectivefrom;

    @Column (name = "pelc_effectivetill", nullable = false)
    private LocalDateTime effectivetill;

    @Column (name="pelc_qtyortimebased", length = 1, nullable = false)
    private Character qtyortimebased;

    @Column (name="pelc_isgroupbased", length =1, nullable = false)
    private  Character isgroupbased;

    @Column (name="pelc_ishtlog", length = 1, nullable = false)
    private Character ishtlog;

    @Column (name="pelc_type", length = 3, nullable=false)
    private String type;

    @Column (name = "pelc_machineid", length = 10, nullable = false)
    private String machineid;

    @Column (name="pelc_option", length = 1, nullable = false)
    private Character option;

    @Column (name = "pelc_lineid", length = 10, nullable = false)
    private String lineid;

    @Column (name="pelc_flid", length = 12, nullable = false)
    private String flid;

    @Column (name="pelc_elementid", length = 500, nullable = false)
    private String elementid;

    @Column (name="pelc_active", length = 1, nullable = false)
    private Character active;

    @Column (name="pelc_createdby", length = 12, nullable = false)
    private String createdby;

    @Column (name="pelc_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column (name="pelc_modifiedon", nullable = false)
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

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public Character getIspcsenabled() {
        return ispcsenabled;
    }

    public void setIspcsenabled(Character ispcsenabled) {
        this.ispcsenabled = ispcsenabled;
    }

    public LocalDateTime getEffectivefrom() {
        return effectivefrom;
    }

    public void setEffectivefrom(LocalDateTime effectivefrom) {
        this.effectivefrom = effectivefrom;
    }

    public LocalDateTime getEffectivetill() {
        return effectivetill;
    }

    public void setEffectivetill(LocalDateTime effectivetill) {
        this.effectivetill = effectivetill;
    }

    public Character getQtyortimebased() {
        return qtyortimebased;
    }

    public void setQtyortimebased(Character qtyortimebased) {
        this.qtyortimebased = qtyortimebased;
    }

    public Character getIsgroupbased() {
        return isgroupbased;
    }

    public void setIsgroupbased(Character isgroupbased) {
        this.isgroupbased = isgroupbased;
    }

    public Character getIshtlog() {
        return ishtlog;
    }

    public void setIshtlog(Character ishtlog) {
        this.ishtlog = ishtlog;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public Character getOption() {
        return option;
    }

    public void setOption(Character option) {
        this.option = option;
    }

    public String getLineid() {
        return lineid;
    }

    public void setLineid(String lineid) {
        this.lineid = lineid;
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
