package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "kzn_tl_kkprojectprioritymst", schema = "public")
public class KznTlKkprojectprioritymst {
    @Id
    @Column(name = "kppm_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "kppm_kzpm_keyid", length = 15, nullable = false)
    private String kzpm_keyid;

    @Column(name = "kppm_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "kppm_approvedby", length = 15, nullable = false)
    private String approvedby;

    @Column(name = "kppm_projectscore", nullable = false)
    private BigDecimal projectscore;

    @Column(name = "kppm_rank", length = 2, nullable = false)
    private String rank;

    @Column(name = "kppm_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "kppm_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "kppm_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "kppm_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "kppm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "kppm_active", columnDefinition = "char(1)", nullable = false)
    private Character active;

    @Column(name = "kppm_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kppm_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getKzpm_keyid() {
        return kzpm_keyid;
    }

    public void setKzpm_keyid(String kzpm_keyid) {
        this.kzpm_keyid = kzpm_keyid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public BigDecimal getProjectscore() {
        return projectscore;
    }

    public void setProjectscore(BigDecimal projectscore) {
        this.projectscore = projectscore;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
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

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
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
