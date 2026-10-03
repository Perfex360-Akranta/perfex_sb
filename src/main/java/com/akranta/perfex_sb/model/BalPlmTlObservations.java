package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bal_plm_tl_observations")
public class BalPlmTlObservations {

    @Id
    @Column(name = "obsv_keyid", length = 10)
    private String keyid;

    @Column(name = "obsv_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "obsv_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "obsv_refid", length = 25, nullable = false)
    private String refid;

    @Column(name = "obsv_observation", length = 800, nullable = false)
    private String observation;

    @Column(name = "obsv_targetdate", nullable = false)
    private LocalDateTime targetdate;

    @Column(name = "obsv_foundby", length = 10, nullable = false)
    private String foundby;

    @Column(name = "obsv_status", length = 1, nullable = false)
    private String status;

    @Column(name = "obsv_responsibility", length = 8, nullable = false)
    private String responsibility;

    @Column(name = "obsv_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "obsv_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "obsv_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "obsv_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "obsv_active", length = 1, nullable = false)
    private String active;

    @Column(name = "obsv_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "obsv_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "obsv_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getRefid() {
        return refid;
    }

    public void setRefid(String refid) {
        this.refid = refid;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDateTime getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(LocalDateTime targetdate) {
        this.targetdate = targetdate;
    }

    public String getFoundby() {
        return foundby;
    }

    public void setFoundby(String foundby) {
        this.foundby = foundby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
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
