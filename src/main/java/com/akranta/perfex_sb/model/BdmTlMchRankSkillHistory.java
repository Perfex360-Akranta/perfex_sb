package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bdm_tl_mchrankskillhistory", schema = "public")
public class BdmTlMchRankSkillHistory {

    @Id
    @Column(name = "mrsh_keyid", length = 16, nullable = false)
    private String mrshKeyid;

    @Column(name = "mrsh_equipmentid", length = 16, nullable = false)
    private String mrshEquipmentid;

    @Column(name = "mrsh_ratings", nullable = false)
    private BigDecimal mrshRatings;

    @Column(name = "mrsh_rank", length = 19, nullable = false)
    private String mrshRank;

    @Column(name = "mrsh_date", nullable = false)
    private LocalDateTime mrshDate;

    @Column(name = "mrsh_tempfield1", length = 1, nullable = false)
    private Character mrshTempfield1;

    @Column(name = "mrsh_createdby", length = 8, nullable = false)
    private String createdBy;

    @Column(name = "mrsh_active", length = 1, nullable = false)
    private Character mrshActive;

    @Column(name = "mrsh_createdon", nullable = false)
    private LocalDateTime mrshCreatedon;

    @Column(name = "mrsh_modifiedon", nullable = false)
    private LocalDateTime mrshModifiedon;

    public String getMrshKeyid() {
        return mrshKeyid;
    }

    public void setMrshKeyid(String mrshKeyid) {
        this.mrshKeyid = mrshKeyid;
    }

    public String getMrshEquipmentid() {
        return mrshEquipmentid;
    }

    public void setMrshEquipmentid(String mrshEquipmentid) {
        this.mrshEquipmentid = mrshEquipmentid;
    }

    public BigDecimal getMrshRatings() {
        return mrshRatings;
    }

    public void setMrshRatings(BigDecimal mrshRatings) {
        this.mrshRatings = mrshRatings;
    }

    public String getMrshRank() {
        return mrshRank;
    }

    public void setMrshRank(String mrshRank) {
        this.mrshRank = mrshRank;
    }

    public LocalDateTime getMrshDate() {
        return mrshDate;
    }

    public void setMrshDate(LocalDateTime mrshDate) {
        this.mrshDate = mrshDate;
    }

    public Character getMrshTempfield1() {
        return mrshTempfield1;
    }

    public void setMrshTempfield1(Character mrshTempfield1) {
        this.mrshTempfield1 = mrshTempfield1;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Character getMrshActive() {
        return mrshActive;
    }

    public void setMrshActive(Character mrshActive) {
        this.mrshActive = mrshActive;
    }

    public LocalDateTime getMrshCreatedon() {
        return mrshCreatedon;
    }

    public void setMrshCreatedon(LocalDateTime mrshCreatedon) {
        this.mrshCreatedon = mrshCreatedon;
    }

    public LocalDateTime getMrshModifiedon() {
        return mrshModifiedon;
    }

    public void setMrshModifiedon(LocalDateTime mrshModifiedon) {
        this.mrshModifiedon = mrshModifiedon;
    }

    // getters and setters
    
}