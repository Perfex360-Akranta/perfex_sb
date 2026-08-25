package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bdm_tl_criticalityassessment", schema = "public")
public class BdmTlCriticalityassessment {

    @Id
    @Column(name = "casm_keyid", length = 16, nullable = false)
    private String keyid;

    @Column(name = "casm_elementid", length = 200, nullable = false)
    private String elementid;

    @Column(name = "casm_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "casm_equipmentid", length = 16, nullable = false)
    private String equipmentid;

    @Column(name = "casm_criteriaid", length = 500, nullable = false)
    private String criteriaid;

    @Column(name = "casm_scores", precision = 38, scale = 2, nullable = false)
    private BigDecimal scores;

    @Column(name = "casm_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "casm_doneby", length = 10, nullable = false)
    private String doneby;

    @Column(name = "casm_remarks", length = 100, nullable = false)
    private String remarks;

    @Column(name = "casm_tempfield6", length = 1, nullable = false)
    private String tempfield6;

    @Column(name = "casm_tempfield7", length = 1, nullable = false)
    private String tempfield7;

    @Column(name = "casm_tempfield8", length = 1, nullable = false)
    private String tempfield8;

    @Column(name = "casm_tempfield9", length = 1, nullable = false)
    private String tempfield9;

    @Column(name = "casm_tempfield10", length = 1, nullable = false)
    private String tempfield10;

    

    @Column(name = "casm_tradeid", length = 15, nullable = false)
    private String tradeid;

    @Column(name = "casm_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "casm_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "casm_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "casm_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "casm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "casm_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "casm_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "casm_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
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

    public String getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(String equipmentid) {
        this.equipmentid = equipmentid;
    }

    public String getCriteriaid() {
        return criteriaid;
    }

    public void setCriteriaid(String criteriaid) {
        this.criteriaid = criteriaid;
    }

    public BigDecimal getScores() {
        return scores;
    }

    public void setScores(BigDecimal scores) {
        this.scores = scores;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDoneby() {
        return doneby;
    }

    public void setDoneby(String doneby) {
        this.doneby = doneby;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getTempfield8() {
        return tempfield8;
    }

    public void setTempfield8(String tempfield8) {
        this.tempfield8 = tempfield8;
    }

    public String getTempfield9() {
        return tempfield9;
    }

    public void setTempfield9(String tempfield9) {
        this.tempfield9 = tempfield9;
    }

    public String getTempfield10() {
        return tempfield10;
    }

    public void setTempfield10(String tempfield10) {
        this.tempfield10 = tempfield10;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
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

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
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
    