package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

// import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "jha_tl_audittemplate", schema = "public")
public class JhaTlAudittemplate {

    @Id
    @Column(name = "jaut_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "jaut_masterid", length = 12, nullable = false)
    private String masterid;

    @Column(name = "jaut_parametername", length = 255, nullable = false)
    private String parametername;

    @Column(name = "jaut_parameterdescription", length = 500)
    private String parameterdescription;

    @Column(name = "jaut_evidence", length = 1000)
    private String evidence;

    @Column(name = "jaut_maximumpoints")
    private Integer maximumpoints;

    @Column(name = "jaut_reviewptslno", length = 10)
    private String reviewptslno;

    @Column(name = "jaut_criteriaslno", length = 10)
    private String criteriaslno;

    @Column(name = "jaut_tempfield3", length = 500)
    private String tempfield3;

    @Column(name = "jaut_tempfield4", length = 500)
    private String tempfield4;

    @Column(name = "jaut_tempfield5", length = 500)
    private String tempfield5;

    @Column(name = "jaut_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "jaut_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "jaut_createdon", updatable = false )
   // @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "jaut_modifiedon")
    //@UpdateTimestamp
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getMasterid() {
        return masterid;
    }

    public void setMasterid(String masterid) {
        this.masterid = masterid;
    }

    public String getParametername() {
        return parametername;
    }

    public void setParametername(String parametername) {
        this.parametername = parametername;
    }

    public String getParameterdescription() {
        return parameterdescription;
    }

    public void setParameterdescription(String parameterdescription) {
        this.parameterdescription = parameterdescription;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public Integer getMaximumpoints() {
        return maximumpoints;
    }

    public void setMaximumpoints(Integer maximumpoints) {
        this.maximumpoints = maximumpoints;
    }

    public String getReviewptslno() {
        return reviewptslno;
    }

    public void setReviewptslno(String reviewptslno) {
        this.reviewptslno = reviewptslno;
    }

    public String getCriteriaslno() {
        return criteriaslno;
    }

    public void setCriteriaslno(String criteriaslno) {
        this.criteriaslno = criteriaslno;
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