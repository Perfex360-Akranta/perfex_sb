package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "kzn_tl_kkprojectprioritydtl", schema = "public")
public class KznTlKkprojectprioritydtl {
    @Id
    @Column(name = "kppd_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "kppd_kppm_keyid", length = 15, nullable = false)
    private String kppm_keyid;

    @Column(name = "kppd_kkpm_keyid", length = 15, nullable = false)
    private String kkpm_keyid;

    @Column(name = "kppd_score", nullable = false)
    private BigDecimal score;

    @Column(name = "kppd_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "kppd_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "kppd_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "kppd_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "kppd_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "kppd_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "kppd_active", columnDefinition = "char(1)", nullable = false)
    private Character active;

    @Column(name = "kppd_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kppd_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getKppm_keyid() {
        return kppm_keyid;
    }

    public void setKppm_keyid(String kppm_keyid) {
        this.kppm_keyid = kppm_keyid;
    }

    public String getKkpm_keyid() {
        return kkpm_keyid;
    }

    public void setKkpm_keyid(String kkpm_keyid) {
        this.kkpm_keyid = kkpm_keyid;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
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
