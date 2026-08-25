package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ENT_TL_SKILLINDEXASSESSDTL", schema = "public")
public class EntTlSkillindexassessdtl {
    @Column(name = "siad_keyid", length = 15, nullable = false)
    @Id
    private String keyid;

    @Column(name = "siad_siam_keyid", length = 15, nullable = false)
    private String siam_keyid;

    @Column(name = "siad_empm_keyid", length = 15, nullable = false)
    private String empm_keyid;

    @Column(name = "siad_reviewid", length = 15, nullable = false)
    private String reviewid;

    @Column(name = "siad_score", nullable = false)
    private BigDecimal score;

    @Column(name = "siad_criteriaid", length = 15, nullable = false)
    private String criteriaid;

    @Column(name = "siad_total", nullable = false)
    private BigDecimal total;

       @Column(name = "siad_reviewdate", nullable = false)
    private LocalDateTime reviewdate;

    @Column(name = "siad_review_half",length = 20, nullable = false)
    private String reviewhalf;

    @Column(name = "siad_tempfiled5", length = 1, columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfiled5;

    @Column(name = "siad_active", length = 1, columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "siad_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "siad_createdon", length = 15, nullable = false)
    private LocalDateTime createdon;

    @Column(name = "siad_modifiedon", length = 15, nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getSiam_keyid() {
        return siam_keyid;
    }

    public void setSiam_keyid(String siam_keyid) {
        this.siam_keyid = siam_keyid;
    }

    public String getEmpm_keyid() {
        return empm_keyid;
    }

    public void setEmpm_keyid(String empm_keyid) {
        this.empm_keyid = empm_keyid;
    }

    public String getReviewid() {
        return reviewid;
    }

    public void setReviewid(String reviewid) {
        this.reviewid = reviewid;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getCriteriaid() {
        return criteriaid;
    }

    public void setCriteriaid(String criteriaid) {
        this.criteriaid = criteriaid;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getReviewdate() {
        return reviewdate;
    }

    public void setReviewdate(LocalDateTime reviewdate) {
        this.reviewdate = reviewdate;
    }

    public String getReviewhalf() {
        return reviewhalf;
    }

    public void setReviewhalf(String reviewhalf) {
        this.reviewhalf = reviewhalf;
    }

    public Character getTempfiled5() {
        return tempfiled5;
    }

    public void setTempfiled5(Character tempfiled5) {
        this.tempfiled5 = tempfiled5;
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
