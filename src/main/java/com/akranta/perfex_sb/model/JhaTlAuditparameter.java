package com.akranta.perfex_sb.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "JHA_TL_AUDITPARAMETER", schema = "public")

public class JhaTlAuditparameter {

 
    @Id
    @Column(name = "jhap_keyid", length = 10, nullable = false)
    private String keyid;

     @Column(name = "jhap_templatename", length = 200, nullable = false)
    private String templatename;

         @Column(name = "jhap_templatecode", length = 10, nullable = false)
    private String templatecode;

         @Column(name = "jhap_auditpillar", length = 5, nullable = false)
    private String auditpillar;

         @Column(name = "jhap_audittype", length = 5, nullable = false)
    private String audittype;


          @Column(name = "jhap_auditlevel", length = 6, nullable = false)
    private String auditlevel;


     @Column(name = "jhap_evidence", length = 1, nullable = false)
    private Character evidence;

     @Column(name = "jhap_remarks", length = 500, nullable = false)
    private String remarks;

     @Column(name = "jhap_revisionno", length = 20, nullable = false)
    private String revisionno;

    @Column(name = "jhap_revisiondate")
    private LocalDateTime revisiondate;

    @Column(name = "jhap_criteriamax", length = 10, nullable = false)
    private String criteriamax;


    @Column(name = "jhap_tempfield2", length = 2, nullable = false)
    private String tempfield2;


    @Column(name = "jhap_tempfield3", length = 2, nullable = false)
    private String tempfield3;
    
    @Column(name = "jhap_tempfield4", length = 2, nullable = false)
    private String tempfield4;

    
    @Column(name = "jhap_tempfield5", length = 2, nullable = false)
    private String tempfield5;

     @Column(name = "jhap_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "jhap_createdby", length = 10, nullable = false)
    private String createdby;

    
    @Column(name = "jhap_createdon",  updatable = false)
    private LocalDateTime createdon;

    
    @Column(name = "jhap_modifiedon")
    private LocalDateTime modifiedon;


    public String getKeyid() {
        return keyid;
    }


    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }


    public String getTemplatename() {
        return templatename;
    }


    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }


    public String getTemplatecode() {
        return templatecode;
    }


    public void setTemplatecode(String templatecode) {
        this.templatecode = templatecode;
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


    public String getAuditlevel() {
        return auditlevel;
    }


    public void setAuditlevel(String auditlevel) {
        this.auditlevel = auditlevel;
    }


    public Character getEvidence() {
        return evidence;
    }


    public void setEvidence(Character evidence) {
        this.evidence = evidence;
    }


    public String getRemarks() {
        return remarks;
    }


    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getRevisionno() {
        return revisionno;
    }


    public void setRevisionno(String revisionno) {
        this.revisionno = revisionno;
    }


    public LocalDateTime getRevisiondate() {
        return revisiondate;
    }


    public void setRevisiondate(LocalDateTime revisiondate) {
        this.revisiondate = revisiondate;
    }


    public String getCriteriamax() {
        return criteriamax;
    }


    public void setCriteriamax(String criteriamax) {
        this.criteriamax = criteriamax;
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
