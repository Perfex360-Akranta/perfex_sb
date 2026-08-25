package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bdm_tl_wwbladtl", schema = "public")
public class BdmTlWwbladtl {
    
    @Id
    @Column(name = "wwbd_keyid", length = 10, nullable = false)
    private String keyid;

    @Column(name = "wwbd_wwbl_keyid", length = 10, nullable = false)
    private String wwbl_keyid;

    @Column(name = "wwbd_phenomena_factor", length = 500, nullable = false)
    private String phenomena_factor;

    @Column(name = "wwbd_verification", length = 1)
    private Character verification;

    @Column(name = "wwbd_parentid", length = 10)
    private String parentid;

    @Column(name = "wwbd_orderno")
    private BigDecimal orderno;

    @Column(name = "wwbd_levelno")
    private BigDecimal levelno;

    @Column(name = "wwbd_islastfactor", length = 1)
    private Character islastfactor;

    @Column(name = "wwbd_countermeasure", length = 1000)
    private String countermeasure;

    @Column(name = "wwbd_skilltype", length = 1)
    private Character skilltype;

    @Column(name = "wwbd_responsibility", length = 100)
    private String responsiblity;

    @Column(name = "wwbd_targetdate")
    private LocalDateTime targetdate;

    @Column(name = "wwbd_status", length = 1, nullable = false)
    private String status;

    @Column(name = "wwbd_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "wwbd_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "wwbd_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "wwbd_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    @Column(name = "wwbd_reoccur", length = 4000)
    private String reoccur;

    @Column(name = "wwbd_actiontaken", length = 500)
    private String actiontaken;

    @Column(name = "wwbd_completedby", length = 14)
    private String completedby;

    @Column(name = "wwbd_completeddate")
    private LocalDateTime completedon;

    @Column(name = "wwbd_remarks", length = 200)
    private String remarks;

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getWwbl_keyid() {
        return wwbl_keyid;
    }

    public void setWwbl_keyid(String wwbl_keyid) {
        this.wwbl_keyid = wwbl_keyid;
    }

    public String getPhenomena_factor() {
        return phenomena_factor;
    }

    public void setPhenomena_factor(String phenomena_factor) {
        this.phenomena_factor = phenomena_factor;
    }

    public Character getVerification() {
        return verification;
    }

    public void setVerification(Character verification) {
        this.verification = verification;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public BigDecimal getOrderno() {
        return orderno;
    }

    public void setOrderno(BigDecimal orderno) {
        this.orderno = orderno;
    }

    public BigDecimal getLevelno() {
        return levelno;
    }

    public void setLevelno(BigDecimal levelno) {
        this.levelno = levelno;
    }

    public Character getIslastfactor() {
        return islastfactor;
    }

    public void setIslastfactor(Character islastfactor) {
        this.islastfactor = islastfactor;
    }

    public String getCountermeasure() {
        return countermeasure;
    }

    public void setCountermeasure(String countermeasure) {
        this.countermeasure = countermeasure;
    }

    public Character getSkilltype() {
        return skilltype;
    }

    public void setSkilltype(Character skilltype) {
        this.skilltype = skilltype;
    }

    public String getResponsiblity() {
        return responsiblity;
    }

    public void setResponsiblity(String responsiblity) {
        this.responsiblity = responsiblity;
    }

    public LocalDateTime getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(LocalDateTime targetdate) {
        this.targetdate = targetdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getReoccur() {
        return reoccur;
    }

    public void setReoccur(String reoccur) {
        this.reoccur = reoccur;
    }

    public String getActiontaken() {
        return actiontaken;
    }

    public void setActiontaken(String actiontaken) {
        this.actiontaken = actiontaken;
    }

    public String getCompletedby() {
        return completedby;
    }

    public void setCompletedby(String completedby) {
        this.completedby = completedby;
    }

    public LocalDateTime getCompletedon() {
        return completedon;
    }

    public void setCompletedon(LocalDateTime completeddate) {
        this.completedon = completeddate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}