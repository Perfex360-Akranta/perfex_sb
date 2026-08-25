package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "gen_tl_workflow_info", schema = "public")
public class GenTlWorkFlowInfo {
    @Id
    @Column(name = "wrin_keyid", length = 10, nullable = false)
    private String keyid;

    @Column(name = "wrin_wrml_keyid", length = 10, nullable = false)
    private String wrml_keyid;

    @Column(name = "wrin_ref_id", length = 25, nullable = false)
    private String ref_id;

    @Column(name = "wrin_ref_type", length = 10, nullable = false)
    private String ref_type;

    @Column(name = "wrin_role_id", length = 10, nullable = false)
    private String role_id;

    @Column(name = "wrin_status",columnDefinition = "char(1)", nullable = false)
    private Character status;

    @Column(name = "wrin_employee_id", length = 10, nullable = false)
    private String employee_id;

    @Column(name = "wrin_date")
    private LocalDateTime date;

    @Column(name = "wrin_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "wrin_wrkd_keyid", length = 15, nullable = false)
    private String wrkd_keyid;

    @Column(name = "wrin_tempfield2", length = 15, nullable = false)
    private String tempfield2;

    @Column(name = "wrin_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "wrin_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "wrin_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "wrin_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "wrin_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "wrin_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getWrml_keyid() {
        return wrml_keyid;
    }

    public void setWrml_keyid(String wrml_keyid) {
        this.wrml_keyid = wrml_keyid;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }

    public String getRef_type() {
        return ref_type;
    }

    public void setRef_type(String ref_type) {
        this.ref_type = ref_type;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
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

    public String getWrkd_keyid() {
        return wrkd_keyid;
    }

    public void setWrkd_keyid(String wrkd_keyid) {
        this.wrkd_keyid = wrkd_keyid;
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
