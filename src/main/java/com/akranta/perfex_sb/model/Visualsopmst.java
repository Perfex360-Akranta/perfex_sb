package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "JHA_TL_VISUALSOPMST", schema = "public")

public class Visualsopmst {

    @Id
    @Column(name = "vsom_keyid", length = 15)
    private String keyid;

    @Column(name = "vsom_flnid", length = 12, nullable = false)
    private String flnid;

    @Column(name = "vsom_equipmentid", length = 15, nullable = false)
    private String equipmentid;

    @Column(name = "vsom_productid", length = 15, nullable = false)
    private String productid;

    @Column(name = "vsom_operation", length = 500, nullable = false)
    private String operation;

    @Column(name = "vsom_safetyinstruction", length = 500, nullable = false)
    private String safetyinstruction;

    @Column(name = "vsom_effectofnoncompliance", length = 500, nullable = false)
    private String effectofnoncompliance;

    @Column(name = "vsom_preparedby", length = 10, nullable = false)
    private String preparedby;

    @Column(name = "vsom_approvedby", length = 10, nullable = false)
    private String approvedby;

    @Column(name = "vsom_issuedby", length = 10, nullable = false)
    private String issuedby;

    @Column(name = "vsom_status", length = 1, nullable = false)
    private Character status;

    @Column(name = "vsom_nextlevel", length = 50)
    private String nextlevel;

    @Column(name = "vsom_maintsection", length = 15, nullable = false)
    private String maintsection;

    @Column(name = "vsom_tempfield4", length = 1, nullable = false)
    private Character tempfield4;

    @Column(name = "vsom_tempfield5", length = 1, nullable = false)
    private Character tempfield5;

    @Column(name = "vsom_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "vsom_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "vsom_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "vsom_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFlnid() {
        return flnid;
    }

    public void setFlnid(String flnid) {
        this.flnid = flnid;
    }

    public String getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(String equipmentid) {
        this.equipmentid = equipmentid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSafetyinstruction() {
        return safetyinstruction;
    }

    public void setSafetyinstruction(String safetyinstruction) {
        this.safetyinstruction = safetyinstruction;
    }

    public String getEffectofnoncompliance() {
        return effectofnoncompliance;
    }

    public void setEffectofnoncompliance(String effectofnoncompliance) {
        this.effectofnoncompliance = effectofnoncompliance;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public String getIssuedby() {
        return issuedby;
    }

    public void setIssuedby(String issuedby) {
        this.issuedby = issuedby;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getNextlevel() {
        return nextlevel;
    }

    public void setNextlevel(String nextlevel) {
        this.nextlevel = nextlevel;
    }

    public String getMaintsection() {
        return maintsection;
    }

    public void setMaintsection(String maintsection) {
        this.maintsection = maintsection;
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

    
    // getters and setters
}





