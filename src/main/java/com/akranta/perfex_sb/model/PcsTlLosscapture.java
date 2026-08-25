package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pcs_tl_losscapture", schema = "public")
public class PcsTlLosscapture {

    @Id
    @Column(name = "plos_keyid", length = 15, nullable = false)
    private String plosKeyid;

    @Column(name = "plos_flid", length = 15, nullable = false)
    private String plosFlid;

    @Column(name = "plos_date", nullable = false)
    private LocalDateTime plosDate;

    @Column(name = "plos_shiftid", length = 8, nullable = false)
    private String plosShiftid;

    @Column(name = "plos_fromtime", nullable = false)
    private LocalDateTime plosFromtime;

    @Column(name = "plos_totime", nullable = false)
    private LocalDateTime plosTotime;

    @Column(name = "plos_losstime", length = 10, nullable = false)
    private String plosLosstime;

    @Column(name = "plos_lossreason", length = 15, nullable = false)
    private String plosLossreason;

    @Column(name = "plos_lossid", length = 15, nullable = false)
    private String plosLossid;

    @Column(name = "plos_tradeid", length = 15, nullable = false)
    private String plosTradeid;

    @Column(name = "plos_prod_impact", length = 1, nullable = false)
    private Character plosProdImpact;

    @Column(name = "plos_prod_imp_qty", nullable = false)
    private BigDecimal plosProdImpQty;

    @Column(name = "plos_lossdescription", length = 500, nullable = false)
    private String plosLossdescription;

    @Column(name = "plos_pldetailsid", length = 15, nullable = false)
    private String plosPldetailsid;

    @Column(name = "plos_equipment", length = 10, nullable = false)
    private String plosEquipment;

    @Column(name = "plos_detectedby", length = 15, nullable = false)
    private String plosDetectedby;

    @Column(name = "plos_tempfield4", length = 3, nullable = false)
    private String plosTempfield4;

    @Column(name = "plos_tempfield5", length = 3, nullable = false)
    private String plosTempfield5;

    @Column(name = "plos_active", length = 1, nullable = false)
    private Character plosActive;

    @Column(name = "plos_createdby", length = 8, nullable = false)
    private String plosCreatedby;

    @Column(name = "plos_createdon", nullable = false)
    private LocalDateTime plosCreatedon;

    @Column(name = "plos_modifiedon", nullable = false)
    private LocalDateTime plosModifiedon;

    // -------- getters & setters --------

    public String getPlosKeyid() { return plosKeyid; }
    public void setPlosKeyid(String plosKeyid) { this.plosKeyid = plosKeyid; }

    public String getPlosFlid() { return plosFlid; }
    public void setPlosFlid(String plosFlid) { this.plosFlid = plosFlid; }

    public LocalDateTime getPlosDate() { return plosDate; }
    public void setPlosDate(LocalDateTime plosDate) { this.plosDate = plosDate; }

    public String getPlosShiftid() { return plosShiftid; }
    public void setPlosShiftid(String plosShiftid) { this.plosShiftid = plosShiftid; }

    public LocalDateTime getPlosFromtime() { return plosFromtime; }
    public void setPlosFromtime(LocalDateTime plosFromtime) { this.plosFromtime = plosFromtime; }

    public LocalDateTime getPlosTotime() { return plosTotime; }
    public void setPlosTotime(LocalDateTime plosTotime) { this.plosTotime = plosTotime; }

    public String getPlosLosstime() { return plosLosstime; }
    public void setPlosLosstime(String plosLosstime) { this.plosLosstime = plosLosstime; }

    public String getPlosLossreason() { return plosLossreason; }
    public void setPlosLossreason(String plosLossreason) { this.plosLossreason = plosLossreason; }

    public String getPlosLossid() { return plosLossid; }
    public void setPlosLossid(String plosLossid) { this.plosLossid = plosLossid; }

    public String getPlosTradeid() { return plosTradeid; }
    public void setPlosTradeid(String plosTradeid) { this.plosTradeid = plosTradeid; }

    public Character getPlosProdImpact() { return plosProdImpact; }
    public void setPlosProdImpact(Character plosProdImpact) { this.plosProdImpact = plosProdImpact; }

    public BigDecimal getPlosProdImpQty() { return plosProdImpQty; }
    public void setPlosProdImpQty(BigDecimal plosProdImpQty) { this.plosProdImpQty = plosProdImpQty; }

    public String getPlosLossdescription() { return plosLossdescription; }
    public void setPlosLossdescription(String plosLossdescription) { this.plosLossdescription = plosLossdescription; }

    public String getPlosPldetailsid() { return plosPldetailsid; }
    public void setPlosPldetailsid(String plosPldetailsid) { this.plosPldetailsid = plosPldetailsid; }

    public String getPlosEquipment() { return plosEquipment; }
    public void setPlosEquipment(String plosEquipment) { this.plosEquipment = plosEquipment; }

    public String getPlosDetectedby() { return plosDetectedby; }
    public void setPlosDetectedby(String plosDetectedby) { this.plosDetectedby = plosDetectedby; }

    public String getPlosTempfield4() { return plosTempfield4; }
    public void setPlosTempfield4(String plosTempfield4) { this.plosTempfield4 = plosTempfield4; }

    public String getPlosTempfield5() { return plosTempfield5; }
    public void setPlosTempfield5(String plosTempfield5) { this.plosTempfield5 = plosTempfield5; }

    public Character getPlosActive() { return plosActive; }
    public void setPlosActive(Character plosActive) { this.plosActive = plosActive; }

    public String getPlosCreatedby() { return plosCreatedby; }
    public void setPlosCreatedby(String plosCreatedby) { this.plosCreatedby = plosCreatedby; }

    public LocalDateTime getPlosCreatedon() { return plosCreatedon; }
    public void setPlosCreatedon(LocalDateTime plosCreatedon) { this.plosCreatedon = plosCreatedon; }

    public LocalDateTime getPlosModifiedon() { return plosModifiedon; }
    public void setPlosModifiedon(LocalDateTime plosModifiedon) { this.plosModifiedon = plosModifiedon; }
}
