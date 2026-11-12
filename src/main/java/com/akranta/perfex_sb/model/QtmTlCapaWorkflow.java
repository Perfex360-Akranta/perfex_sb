package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "qtm_tl_capaworkflow", schema = "public")
public class QtmTlCapaWorkflow {

    @Id
    @Column(name = "qcwf_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "qcwf_caparefid", length = 14, nullable = false)
    private String caparefid;

    @Column(name = "qcwf_roleid", length = 15, nullable = false)
    private String roleid;

    @Column(name = "qcwf_approvedby", length = 15, nullable = false)
    private String approvedby;

    @Column(name = "qcwf_status", length = 15, nullable = false)
    private String status;

    @Column(name = "qcwf_date")
    private LocalDateTime date;

    @Column(name = "qcwf_remarks", length = 100)
    private String remarks;

    @Column(name = "qcwf_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "qcwf_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "qcwf_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "qcwf_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "qcwf_active", length = 1, nullable = false)
    private String active;

    @Column(name = "qcwf_createdby", length = 12, nullable = false)
    private String createdby;

    @Column(name = "qcwf_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "qcwf_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getCaparefid() {
        return caparefid;
    }

    public void setCaparefid(String caparefid) {
        this.caparefid = caparefid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
