package com.akranta.perfex_sb.model;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "qtm_tl_capa", schema = "public")
public class Qtmtlcapa {

    @Id
    @Column(name = "qcap_keyid", length = 14, nullable = false)
    private String keyid;

    @Column(name = "qcap_flid", length = 14, nullable = false)
    private String flid;

    @Column(name = "qcap_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "qcap_qualityissue", length = 1000, nullable = false)
    private String qualityissue;

    @Column(name = "qcap_quantityeffect", nullable = false)
    private BigDecimal quantityeffect;

    @Column(name = "qcap_defect", length = 12, nullable = false)
    private String defect;

    @Column(name = "qcap_uom", length = 12, nullable = false)
    private String uom;

    @Column(name = "qcap_todmt", length = 12, nullable = false)
    private String todmt;

    @Column(name = "qcap_tojh", length = 12, nullable = false)
    private String tojh;

    @Column(name = "qcap_complantraisedby", length = 12, nullable = false)
    private String complaintraisedby;

    @Column(name = "qcap_todmtleader", length = 12, nullable = false)
    private String todmtleaader;

    @Column(name = "qcap_areaincharge", length = 12, nullable = false)
    private String areaincharge;

    @Column(name = "qcap_complainttype", length = 3)
    private String type;

    @Column(name = "qcap_tempfield2", length = 2)
    private String tempfield2;

    @Column(name = "qcap_tempfield3", length = 2)
    private String tempfield3;

    @Column(name = "qcap_tempfield4", length = 2)
    private String tempfield4;

    @Column(name = "qcap_tempfield5", length = 2)
    private String tempfield5;

    @Column(name = "qcap_active", length = 1, nullable = false)
    private String active;

    @Column(name = "qcap_createdby", length = 12, nullable = false)
    private String createdby;

    @Column(name = "qcap_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "qcap_modifiedon", nullable = false)
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getQualityissue() {
        return qualityissue;
    }

    public void setQualityissue(String qualityissue) {
        this.qualityissue = qualityissue;
    }

    public BigDecimal getQuantityeffect() {
        return quantityeffect;
    }

    public void setQuantityeffect(BigDecimal quantityeffect) {
        this.quantityeffect = quantityeffect;
    }

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getTodmt() {
        return todmt;
    }

    public void setTodmt(String todmt) {
        this.todmt = todmt;
    }

    public String getTojh() {
        return tojh;
    }

    public void setTojh(String tojh) {
        this.tojh = tojh;
    }

    public String getComplaintraisedby() {
        return complaintraisedby;
    }

    public void setComplaintraisedby(String complaintraisedby) {
        this.complaintraisedby = complaintraisedby;
    }

    public String getTodmtleaader() {
        return todmtleaader;
    }

    public void setTodmtleaader(String Todmtleaader) {
        this.todmtleaader = Todmtleaader;
    }

    public String getAreaincharge() {
        return areaincharge;
    }

    public void setAreaincharge(String areaincharge) {
        this.areaincharge = areaincharge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
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
