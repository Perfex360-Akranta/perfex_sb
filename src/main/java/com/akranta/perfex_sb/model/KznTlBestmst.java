package com.akranta.perfex_sb.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "kzn_tl_bestmst", schema = "public")
public class KznTlBestmst {
    @Column(name = "kzbm_keyid", length = 15, nullable = false)
    @Id
    private String keyid;

    @Column(name = "kzbm_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "kzbm_employeeid", length = 10, nullable = false)
    private String employeeid;

    @Column(name = "kzbm_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "kzbm_month", length = 10, nullable = false)
    private String month;

    @Column(name = "kzbm_level", length = 30, nullable = false)
    private String level;

    @Column(name = "kzbm_tempfield1", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield1;

    @Column(name = "kzbm_tempfield2", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield2;

    @Column(name = "kzbm_tempfield3", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield3;

    @Column(name = "kzbm_tempfield4", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield4;

    @Column(name = "kzbm_tempfield5", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield5;

    @Column(name = "kzbm_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "kzbm_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "kzbm_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kzbm_modifiedon", nullable = false)
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

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
