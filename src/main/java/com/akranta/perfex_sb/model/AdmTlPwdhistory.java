package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ADM_TL_PWDHISTORY", schema = "public")
public class AdmTlPwdhistory {
    @Column(name = "pwdh_keyid", length = 8, nullable = false)
    @Id
    private String keyid;

    @Column(name = "pwdh_userid", length = 8, nullable = false)
    private String userid;

    @Column(name = "pwdh_passwordno", nullable = false)
    private BigDecimal passwordno;

    @Column(name = "pwdh_password", length = 30, nullable = false)
    private String password;

    @Column(name = "pwdh_lastpwdchangedon", nullable = false)
    private LocalDateTime lastpwdchangedon;

    @Column(name = "pwdh_active", nullable = false)
    private Character active;

    @Column(name = "pwdh_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "pwdh_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "pwdh_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public BigDecimal getPasswordno() {
        return passwordno;
    }

    public void setPasswordno(BigDecimal passwordno) {
        this.passwordno = passwordno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastpwdchangedon() {
        return lastpwdchangedon;
    }

    public void setLastpwdchangedon(LocalDateTime lastpwdchangedon) {
        this.lastpwdchangedon = lastpwdchangedon;
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
