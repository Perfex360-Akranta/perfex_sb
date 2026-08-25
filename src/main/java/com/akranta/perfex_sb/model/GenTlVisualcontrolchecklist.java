package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "GEN_TL_VISUALCONTROLCHECKLIST", schema = "public")
public class GenTlVisualcontrolchecklist {

    @Id
    @Column(name = "vccl_keyid", length = 15, nullable = false)
    private String keyid;


     @Column(name = "vccl_flid", length = 15, nullable = false)
    private String flid;
    

    @Column(name = "vccl_employeeid", length = 10, nullable = false)
    private String employeeid;

    @Column(name = "vccl_date")
    private LocalDateTime date;

    @Column(name = "vccl_title",  length = 500, nullable = false)
    private String title;

    @Column(name = "vccl_approvedby", length = 10, nullable = false)
    private String approvedby;

    @Column(name = "vccl_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column(name = "vccl_tempfield4", length = 1, nullable = false)
    private Character tempfield4;


    @Column(name = "vccl_tempfield5", length = 1, nullable = false)
    private Character tempfield5;

    @Column(name = "vccl_tempfield6", length = 1, nullable = false)
    private Character tempfield6;

    @Column(name = "vccl_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "vccl_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "vccl_createdon", updatable = false)
    private LocalDateTime createdon;

    @Column(name = "vccl_modifiedon")
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

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public Character getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(Character tempfield3) {
        this.tempfield3 = tempfield3;
    }

    public Character getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(Character tempfield4) {
        this.tempfield4 = tempfield4;
    }

    public Character getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(Character tempfield5) {
        this.tempfield5 = tempfield5;
    }

    public Character getTempfield6() {
        return tempfield6;
    }

    public void setTempfield6(Character tempfield6) {
        this.tempfield6 = tempfield6;
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
