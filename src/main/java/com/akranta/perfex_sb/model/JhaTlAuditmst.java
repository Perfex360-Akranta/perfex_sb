package com.akranta.perfex_sb.model;



import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Entity
@Table(name = "jha_tl_auditmst", schema = "public")
public class JhaTlAuditmst {

    // =========================
    // PRIMARY KEY
    // =========================
    @Id
    @Column(name = "jham_keyid", length = 12, nullable = false)
    private String keyid;

    // =========================
    // AUDIT DETAILS
    // =========================
    @Column(name = "jham_auditdate", nullable = false)
    private LocalDateTime auditdate;

    @Column(name = "jham_auditpillar", length = 5, nullable = false)
    private String auditpillar;

    @Column(name = "jham_audittype", length = 5, nullable = false)
    private String audittype;

    @Column(name = "jham_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "jham_auditteamid", length = 10)
    private String auditteamid;

    @Column(name = "jham_auditorname", length = 550)
    private String auditorname;

    @Column(name = "jham_leadername", length = 550)
    private String leadername;

    @Column(name = "jham_totalpoints")
    private Integer totalpoints;

    @Column(name = "jham_auditortype", length = 1)
    private Character auditortype;

    @Column(name = "jham_nextauditdate")
    private LocalDateTime nextauditdate;

    @Column(name = "jham_nextauditteam", length = 10)
    private String nextauditteam;

    @Column(name = "jham_jhstepid", length = 10)
    private String jhstepid;

    @Column(name = "jham_status", length = 1)
    private Character status;

    @Column(name = "jham_auditupload", length = 2)
    private String auditupload;

    // =========================
    // ELEMENT / TEMP FIELDS
    // =========================


    @Column(name = "jham_tempfield2", length = 10)
    private String tempfield2;

    @Column(name = "jham_tempfield3", length = 10)
    private String tempfield3;

    @Column(name = "jham_tempfield4", length = 10)
    private String tempfield4;

    @Column(name = "jham_tempfield5", length = 10)
    private String tempfield5;

    // =========================
    // AUDIT INFO
    // =========================
    @Column(name = "jham_active", length = 1)
    private Character active;

    @Column(name = "jham_createdby", length = 10)
    private String createdby;

    @CreationTimestamp
    @Column(name = "jham_createdon", updatable = false)
    private LocalDateTime createdon;

    

    @UpdateTimestamp
    @Column(name = "jham_modifiedon")
    private LocalDateTime modifiedon;



    public String getKeyid() {
        return keyid;
    }



    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }



    public LocalDateTime getAuditdate() {
        return auditdate;
    }



    public void setAuditdate(LocalDateTime auditdate) {
        this.auditdate = auditdate;
    }



    public String getAuditpillar() {
        return auditpillar;
    }



    public void setAuditpillar(String auditpillar) {
        this.auditpillar = auditpillar;
    }



    public String getAudittype() {
        return audittype;
    }



    public void setAudittype(String audittype) {
        this.audittype = audittype;
    }



    public String getFlid() {
        return flid;
    }



    public void setFlid(String flid) {
        this.flid = flid;
    }



    public String getAuditteamid() {
        return auditteamid;
    }



    public void setAuditteamid(String auditteamid) {
        this.auditteamid = auditteamid;
    }



    public String getAuditorname() {
        return auditorname;
    }



    public void setAuditorname(String auditorname) {
        this.auditorname = auditorname;
    }



    public String getLeadername() {
        return leadername;
    }



    public void setLeadername(String leadername) {
        this.leadername = leadername;
    }



    public Integer getTotalpoints() {
        return totalpoints;
    }



    public void setTotalpoints(Integer totalpoints) {
        this.totalpoints = totalpoints;
    }



    public Character getAuditortype() {
        return auditortype;
    }



    public void setAuditortype(Character auditortype) {
        this.auditortype = auditortype;
    }



    public LocalDateTime getNextauditdate() {
        return nextauditdate;
    }



    public void setNextauditdate(LocalDateTime nextauditdate) {
        this.nextauditdate = nextauditdate;
    }



    public String getNextauditteam() {
        return nextauditteam;
    }



    public void setNextauditteam(String nextauditteam) {
        this.nextauditteam = nextauditteam;
    }



    public String getJhstepid() {
        return jhstepid;
    }



    public void setJhstepid(String jhstepid) {
        this.jhstepid = jhstepid;
    }



    public Character getStatus() {
        return status;
    }



    public void setStatus(Character status) {
        this.status = status;
    }



    public String getAuditupload() {
        return auditupload;
    }



    public void setAuditupload(String auditupload) {
        this.auditupload = auditupload;
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

   
    // =========================
    // GETTERS & SETTERS
    // =========================

  

   
}

