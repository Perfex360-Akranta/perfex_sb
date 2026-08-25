package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "jha_tl_fieldauditsheetmst", schema = "public")
public class FieldAuditSheetmst {
    
    @Id
    @Column(name = "fasm_keyid", length = 14, nullable = false)
    private String keyid;

    @Column(name = "fasm_flid", length = 14, nullable = false)
    private String flid;

    @Column(name = "fasm_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "fasm_jobdesc", length = 500, nullable = false)
    private String jobdesc;

    @Column(name = "fasm_shift", length = 12, nullable = false)
    private String shift;

    @Column(name = "fasm_serprovider", length = 12, nullable = false)
    private String serprovider;

    @Column(name = "fasm_violations", length = 500, nullable = false)
    private String violations;

    @Column(name = "fasm_evaluatedby", length = 12, nullable = false)
    private String evaluatedby;

    @Column(name = "fasm_noofesp", nullable = false)
    private Integer noofesp;

    @Column(name = "fasm_donedmt", length = 12, nullable = false)
    private String donedmt;

    @Column(name = "fasm_donejh", length = 12, nullable = false)
    private String donejh;

    @Column(name = "fasm_tradeid", length = 15, nullable = false)
    private String tradeid;

    @Column(name = "fasm_tempfield1", length = 14, nullable = false)
    private String tempfield1;

    @Column(name = "fasm_tempfield2", length = 14, nullable = false)
    private String tempfield2;

    @Column(name = "fasm_tempfield3", length = 14, nullable = false)
    private String tempfield3;

    @Column(name = "fasm_tempfield4", length = 14, nullable = false)
    private String tempfield4;

    @Column(name = "fasm_tempfield5", length = 14, nullable = false)
    private String tempfield5;

    @Column(name = "fasm_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "fasm_createdby", length = 12, nullable = false)
    private String createdby;

    @Column(name = "fasm_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "fasm_modifiedon", nullable = false)
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getJobdesc() {
        return jobdesc;
    }

    public void setJobdesc(String jobdesc) {
        this.jobdesc = jobdesc;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getSerprovider() {
        return serprovider;
    }

    public void setSerprovider(String serprovider) {
        this.serprovider = serprovider;
    }

    public String getViolations() {
        return violations;
    }

    public void setViolations(String violations) {
        this.violations = violations;
    }

    public String getEvaluatedby() {
        return evaluatedby;
    }

    public void setEvaluatedby(String evaluatedby) {
        this.evaluatedby = evaluatedby;
    }

    public Integer getNoofesp() {
        return noofesp;
    }

    public void setNoofesp(Integer noofesp) {
        this.noofesp = noofesp;
    }

    public String getDonedmt() {
        return donedmt;
    }

    public void setDonedmt(String donedmt) {
        this.donedmt = donedmt;
    }

    public String getDonejh() {
        return donejh;
    }

    public void setDonejh(String donejh) {
        this.donejh = donejh;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
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