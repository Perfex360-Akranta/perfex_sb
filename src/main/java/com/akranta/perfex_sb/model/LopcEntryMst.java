package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "gen_tl_lopcentrymst", schema = "public")
public class LopcEntryMst {
    
    @Id
    @Column(name = "loem_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "loem_lopccategoryid", length = 15, nullable = false)
    private String lopccategoryid;

    @Column(name = "loem_occurrencedatetime", nullable = false)
    private LocalDateTime occurrencedatetime;

    @Column(name = "loem_flnid", length = 15, nullable = false)
    private String fnlid;

    @Column(name = "loem_employeeid", length = 15, nullable = false)
    private String employeeid;

    @Column(name = "loem_descnearmiss", length = 400, nullable = false)
    private String lopcdesc;

    @Column(name = "loem_identifiedby", length = 15, nullable = false)
    private String identifiedby;

    @Column(name = "loem_prepareddatetime", nullable = false)
    private LocalDateTime prepareddatetime;

    @Column(name = "loem_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "loem_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "loem_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "loem_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "loem_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "loem_active", length = 1, nullable = false)
    private Character active ;

    @Column(name = "loem_createdby", length = 15, nullable = false)
    private String createdby;

    @Column(name = "loem_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "loem_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getLopccategoryid() {
        return lopccategoryid;
    }

    public void setLopccategoryid(String lopccategoryid) {
        this.lopccategoryid = lopccategoryid;
    }

    public LocalDateTime getOccurrencedatetime() {
        return occurrencedatetime;
    }

    public void setOccurrencedatetime(LocalDateTime occurrencedatetime) {
        this.occurrencedatetime = occurrencedatetime;
    }

    public String getFnlid() {
        return fnlid;
    }

    public void setFnlid(String fnlid) {
        this.fnlid = fnlid;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getLopcdesc() {
        return lopcdesc;
    }

    public void setLopcdesc(String lopcdesc) {
        this.lopcdesc = lopcdesc;
    }

    public String getIdentifiedby() {
        return identifiedby;
    }

    public void setIdentifiedby(String identifiedby) {
        this.identifiedby = identifiedby;
    }

    public LocalDateTime getPrepareddatetime() {
        return prepareddatetime;
    }

    public void setPrepareddatetime(LocalDateTime prepareddatetime) {
        this.prepareddatetime = prepareddatetime;
    }

    public String getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(String tempfield1) {
        this.tempfield1 = tempfield1;
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

    public Character getActive() {
        return active ='Y';
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