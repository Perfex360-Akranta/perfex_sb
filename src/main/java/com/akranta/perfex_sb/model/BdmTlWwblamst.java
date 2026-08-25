package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "bdm_tl_wwblamst", schema = "public")
public class BdmTlWwblamst {
    
    @Id
    @Column(name = "wwbl_keyid", length = 10, nullable = false)
    private String keyid;

    @Column(name = "wwbl_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "wwbl_preparedby", length = 10, nullable = false)
    private String preparedby;

    @Column(name = "wwbl_prepareddate", nullable = false)
    private LocalDateTime prepareddate;

    @Column(name = "wwbl_problem", length = 1000, nullable = false)
    private String problem;

    @Column(name = "wwbl_phenomena", length = 1000, nullable = false)
    private String phenomena;

    @Column(name = "wwbl_mechanism", length = 1000, nullable = false)
    private String mechanism;

    @Column(name = "wwbl_lopcid", length = 50, nullable = false)
    private String lopcid;

    @Column(name = "wwbl_lopcempid", length = 50, nullable = false)
    private String lopcempid;

    @Column(name = "wwbl_lopcyn", length = 1, nullable = false)
    private String lopcyn;

    @Column(name = "wwbl_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "wwbl_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "wwbl_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "wwbl_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    @Column(name = "wwbl_investigation", length = 1)
    private String wwblinvestigation;

    

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }

    public LocalDateTime getPrepareddate() {
        return prepareddate;
    }

    public void setPrepareddate(LocalDateTime prepareddate) {
        this.prepareddate = prepareddate;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getPhenomena() {
        return phenomena;
    }

    public void setPhenomena(String phenomena) {
        this.phenomena = phenomena;
    }

    public String getMechanism() {
        return mechanism;
    }

    public void setMechanism(String mechanism) {
        this.mechanism = mechanism;
    }

    public String getLopcid() {
        return lopcid;
    }

    public void setLopcid(String lopcid) {
        this.lopcid = lopcid;
    }

    public String getLopcempid() {
        return lopcempid;
    }

    public void setLopcempid(String lopcempid) {
        this.lopcempid = lopcempid;
    }

    public String getLopcyn() {
        return lopcyn;
    }

    public void setLopcyn(String lopcyn) {
        this.lopcyn = lopcyn;
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

    public String getWwblinvestigation() {
        return wwblinvestigation;
    }

    public void setWwblinvestigation(String wwblinvestigation) {
        this.wwblinvestigation = wwblinvestigation;
    }
}