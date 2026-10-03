package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bal_plm_tl_cbmstdcadtl", schema = "public")
public class BalPlmTlCbmstdcadtl {

    @Id
    @Column(name = "cmdt_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "cmdt_inspectionid", length = 10, nullable = false)
    private String inspectionid;

    @Column(name = "cmdt_zoneid", length = 100, nullable = false)
    private String zoneid;

    @Column(name = "cmdt_zonecolor", length = 25, nullable = false)
    private String zonecolor;

    @Column(name = "cmdt_desirablereading", nullable = false)
    private java.math.BigDecimal desirablereading;

    @Column(name = "cmdt_upperlimit", nullable = false)
    private java.math.BigDecimal upperlimit;

    @Column(name = "cmdt_lowerlimit", nullable = false)
    private java.math.BigDecimal lowerlimit;

    @Column(name = "cmdt_correctiveaction", length = 500, nullable = false)
    private String correctiveaction;

    @Column(name = "cmdt_cbmcondition", columnDefinition = "char(1)", length = 1, nullable = false)
    private Character cbmcondition;

    @Column(name = "cmdt_pmstandardid", length = 12, nullable = false)
    private String pmstandardid;

    @Column(name = "cmdt_uomid", length = 8, nullable = false)
    private String uomid;

    @Column(name = "cmdt_measuringmethod", length = 500, nullable = false)
    private String measuringmethod;

    @Column(name = "cmdt_active", columnDefinition = "char(1)", length = 1, nullable = false)
    private Character active;

    @Column(name = "cmdt_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "cmdt_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "cmdt_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getInspectionid() {
        return inspectionid;
    }

    public void setInspectionid(String inspectionid) {
        this.inspectionid = inspectionid;
    }

    public String getZoneid() {
        return zoneid;
    }

    public void setZoneid(String zoneid) {
        this.zoneid = zoneid;
    }

    public String getZonecolor() {
        return zonecolor;
    }

    public void setZonecolor(String zonecolor) {
        this.zonecolor = zonecolor;
    }

    public java.math.BigDecimal getDesirablereading() {
        return desirablereading;
    }

    public void setDesirablereading(java.math.BigDecimal desirablereading) {
        this.desirablereading = desirablereading;
    }

    public java.math.BigDecimal getUpperlimit() {
        return upperlimit;
    }

    public void setUpperlimit(java.math.BigDecimal upperlimit) {
        this.upperlimit = upperlimit;
    }

    public java.math.BigDecimal getLowerlimit() {
        return lowerlimit;
    }

    public void setLowerlimit(java.math.BigDecimal lowerlimit) {
        this.lowerlimit = lowerlimit;
    }

    public String getCorrectiveaction() {
        return correctiveaction;
    }

    public void setCorrectiveaction(String correctiveaction) {
        this.correctiveaction = correctiveaction;
    }

    public Character getCbmcondition() {
        return cbmcondition;
    }

    public void setCbmcondition(Character cbmcondition) {
        this.cbmcondition = cbmcondition;
    }

    public String getPmstandardid() {
        return pmstandardid;
    }

    public void setPmstandardid(String pmstandardid) {
        this.pmstandardid = pmstandardid;
    }

    public String getUomid() {
        return uomid;
    }

    public void setUomid(String uomid) {
        this.uomid = uomid;
    }

    public String getMeasuringmethod() {
        return measuringmethod;
    }

    public void setMeasuringmethod(String measuringmethod) {
        this.measuringmethod = measuringmethod;
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

    