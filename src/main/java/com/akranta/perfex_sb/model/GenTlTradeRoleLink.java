package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "gen_tl_trade_role_link", schema = "public")
public class GenTlTradeRoleLink {

    @Id
    @Column(name = "gtrl_tradeid", length = 12, nullable = false)
    private String tradeid;

    @Id
    @Column(name = "gtrl_roleid", length = 12, nullable = false)
    private String roleid;

    @Column(name = "gtrl_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "gtrl_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "gtrl_createdon", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "gtrl_modifiedon", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
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