package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "qtm_tl_criticalprocessdtl", schema = "public")
public class QtmTlCriticalprocessdtl {
    
    @Id
    @Column(name = "crpd_keyid", length = 16, nullable = false)
    private String keyid;

    @Column(name = "crpd_crpp_keyid", length = 16, nullable = false)
    private String crpp_keyid;

    @Column(name = "crpd_method", length = 500, nullable = false)
    private String method;

    @Column(name = "crpd_unit", length = 8, nullable = false)
    private String unit;

    @Column(name = "crpd_value", nullable = false)
    private BigDecimal value;

    @Column(name = "crpd_min",  nullable = false)
    private BigDecimal min;

    @Column(name = "crpd_max", nullable = false)
    private BigDecimal max;

    @Column(name = "crpd_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column(name = "crpd_tempfield4", length = 1, nullable = false)
    private Character tempfield4;

    @Column(name = "crpd_tempfield5", length = 1, nullable = false)
    private Character tempfield5;

    @Column(name = "crpd_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "crpd_createdby", length = 15, nullable = false)
    private String createdby;

    @Column(name = "crpd_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "crpd_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getCrpp_keyid() {
        return crpp_keyid;
    }

    public void setCrpp_keyid(String crppKeyid) {
        this.crpp_keyid = crppKeyid;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal Min) {
        this.min = Min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal Max) {
        this.max = Max;
    }

    public Character getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(Character tempfield3) {
        this.tempfield3 = tempfield3;
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
}