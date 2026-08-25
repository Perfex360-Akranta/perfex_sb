package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
//import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "qtm_tl_criticalprocessmst", schema = "public")
public class QtmTlCriticalprocessmst {
    
    @Id
    @Column(name = "crpp_keyid", length = 16, nullable = false)
    private String keyid;

    @Column(name = "crpp_flid", length = 15, nullable = false)
    private String flid;

    @Column(name = "crpp_elementid", length = 200, nullable = false)
    private String elementid;

    @Column(name = "crpp_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "crpp_parameter", length = 500, nullable = false)
    private String parameter;

    @Column(name = "crpp_tempfield1", length = 1, nullable = false)
    private Character tempfield1;

    @Column(name = "crpp_tempfield2", length = 1, nullable = false)
    private Character tempfield2;

    @Column(name = "crpp_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column(name = "crpp_tempfield4", length = 1, nullable = false)
    private Character tempfield4;

    @Column(name = "crpp_tempfield5", length = 1, nullable = false)
    private Character tempfield5;

    @Column(name = "crpp_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "crpp_createdby", length = 16, nullable = false)
    private String createdby;

    @Column(name = "crpp_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "crpp_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

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

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Character getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(Character tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public Character getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(Character tempfield2) {
        this.tempfield2 = tempfield2;
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