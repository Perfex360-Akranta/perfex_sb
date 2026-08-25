package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "plm_tl_conappraisalentry", schema = "public")
public class PlmTlConappraisalentry {
    
    @Id
    @Column(name = "cdap_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "cdap_cdam_keyid", length = 10, nullable = false)
    private String cdam_keyid;

    @Column(name = "cdap_component_type", length = 1, nullable = false)
    private Character component_type;

    @Column(name = "cdap_componentid", length = 20, nullable = false)
    private String componentid;

    @Column(name = "cdap_newcomponent", length = 100, nullable = false)
    private String newcomponent;

    @Column(name = "cdap_dimension", length = 100, nullable = false)
    private String dimension;

    @Column(name = "cdap_checkingtool", length = 100, nullable = false)
    private String checkingtool;

    @Column(name = "cdap_typeofcheck", length = 6, nullable = false)
    private String typeofcheck;

    @Column(name = "cdap_idealtype", length = 1, nullable = false)
    private String idealtype;

    @Column(name = "cdap_idealminimum", nullable = false)
    private BigDecimal idealminimum;

    @Column(name = "cdap_idealmaximum", nullable = false)
    private BigDecimal idealmaximum;

    @Column(name = "cdap_uom", length = 8, nullable = false)
    private String uom;

    @Column(name = "cdap_idealcondition", length = 500, nullable = false)
    private String idealcondition;

    @Column(name = "cdap_actualcondition", length = 500, nullable = false)
    private String actualcondition;

    @Column(name = "cdap_actualvalue", nullable = false)
    private BigDecimal actualvalue;

    @Column(name = "cdap_oknotok", length = 2, nullable = false)
    private String oknotok;

    @Column(name = "cdap_status", length = 1, nullable = false)
    private Character status;

    @Column(name = "cdap_actionrequired", length = 500, nullable = false)
    private String actionrequired;

    @Column(name = "cdap_refurbishment_status", length = 1, nullable = false)
    private Character refurbishment_status;

    @Column(name = "cdap_cdapkeyid", length = 15, nullable = false)
    private String Cdapkeyid;

    @Column(name = "cdap_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "cdap_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "cdap_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "cdap_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "cdap_tempfield6", length = 1, nullable = false)
    private String tempfield6;

    @Column(name = "cdap_tempfield7", length = 1, nullable = false)
    private String tempfield7;

    @Column(name = "cdap_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "cdap_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "cdap_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "cdap_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getCdam_keyid() {
        return cdam_keyid;
    }

    public void setCdam_keyid(String cdam_keyid) {
        this.cdam_keyid = cdam_keyid;
    }

    public Character getComponent_type() {
        return component_type;
    }

    public void setComponent_type(Character component_type) {
        this.component_type = component_type;
    }

    public String getComponentid() {
        return componentid;
    }

    public void setComponentid(String componentid) {
        this.componentid = componentid;
    }

    public String getNewcomponent() {
        return newcomponent;
    }

    public void setNewcomponent(String newcomponent) {
        this.newcomponent = newcomponent;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getCheckingtool() {
        return checkingtool;
    }

    public void setCheckingtool(String checkingtool) {
        this.checkingtool = checkingtool;
    }

    public String getTypeofcheck() {
        return typeofcheck;
    }

    public void setTypeofcheck(String typeofcheck) {
        this.typeofcheck = typeofcheck;
    }

    public String getIdealtype() {
        return idealtype;
    }

    public void setIdealtype(String idealtype) {
        this.idealtype = idealtype;
    }

    public BigDecimal getIdealminimum() {
        return idealminimum;
    }

    public void setIdealminimum(BigDecimal idealminimum) {
        this.idealminimum = idealminimum;
    }

    public BigDecimal getIdealmaximum() {
        return idealmaximum;
    }

    public void setIdealmaximum(BigDecimal idealmaximum) {
        this.idealmaximum = idealmaximum;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getIdealcondition() {
        return idealcondition;
    }

    public void setIdealcondition(String idealcondition) {
        this.idealcondition = idealcondition;
    }

    public String getActualcondition() {
        return actualcondition;
    }

    public void setActualcondition(String actualcondition) {
        this.actualcondition = actualcondition;
    }

    public BigDecimal getActualvalue() {
        return actualvalue;
    }

    public void setActualvalue(BigDecimal actualvalue) {
        this.actualvalue = actualvalue;
    }

    public String getOknotok() {
        return oknotok;
    }

    public void setOknotok(String oknotok) {
        this.oknotok = oknotok;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getActionrequired() {
        return actionrequired;
    }

    public void setActionrequired(String actionrequired) {
        this.actionrequired = actionrequired;
    }

    public Character getRefurbishment_status() {
        return refurbishment_status;
    }

    public void setRefurbishment_status(Character refurbishment_status) {
        this.refurbishment_status = refurbishment_status;
    }

    public String getCdapkeyid() {
        return Cdapkeyid;
    }

    public void setCdapkeyid(String Cdapkeyid) {
        this.Cdapkeyid = Cdapkeyid;
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

    public String getTempfield6() {
        return tempfield6;
    }

    public void setTempfield6(String tempfield6) {
        this.tempfield6 = tempfield6;
    }

    public String getTempfield7() {
        return tempfield7;
    }

    public void setTempfield7(String tempfield7) {
        this.tempfield7 = tempfield7;
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