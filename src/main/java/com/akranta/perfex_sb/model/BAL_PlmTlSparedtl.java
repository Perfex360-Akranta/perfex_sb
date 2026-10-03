package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "bal_plm_tl_sparedtl", schema = "public")
public class BAL_PlmTlSparedtl {

    @Id
    @Column(name = "pspd_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "pspd_standardid", length = 12, nullable = false)
    private String standardid;

    @Column(name = "pspd_spareid", length = 20, nullable = false)
    private String spareid;

    @Column(name = "pspd_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "pspd_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "pspd_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "pspd_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getStandardid() {
        return standardid;
    }

    public void setStandardid(String standardid) {
        this.standardid = standardid;
    }

    public String getSpareid() {
        return spareid;
    }

    public void setSpareid(String spareid) {
        this.spareid = spareid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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