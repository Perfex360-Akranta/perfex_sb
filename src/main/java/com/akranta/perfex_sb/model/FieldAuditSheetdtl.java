package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "jha_tl_fieldauditsheetdtl", schema = "public")
public class FieldAuditSheetdtl {
    
    @Id
    @Column(name = "fasd_keyid", length = 14, nullable = false)
    private String keyid;

    @Column(name = "fasd_fasm_keyid", length = 14, nullable = false)
    private String masterid;

    @Column(name = "fasd_espid", length = 12, nullable = false)
    private String espid;

    @Column(name = "fasd_ppeid", length = 15, nullable = false)
    private String ppeid;

    @Column(name = "fasd_ppecondition", length = 15, nullable = false)
    private String ppecondition;

    @Column(name = "fasd_tools", length = 15, nullable = false)
    private String tools;

    @Column(name = "fasd_workpermitsafety", length = 15, nullable = false)
    private String workpermitsafety;

    @Column(name = "fasd_knowledge", length = 15, nullable = false)
    private String knowledge;

    @Column(name = "fasd_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "fasd_tempfield1", length = 150, nullable = false)
    private String espothers;

    @Column(name = "fasd_tempfield2", length = 14, nullable = false)
    private String tempfield2;

    @Column(name = "fasd_tempfield3", length = 14, nullable = false)
    private String tempfield3;

    @Column(name = "fasd_tempfield4", length = 14, nullable = false)
    private String tempfield4;

    @Column(name = "fasd_tempfield5", length = 14, nullable = false)
    private String tempfield5;

    @Column(name = "fasd_active", length = 5, nullable = false)
    private String active = "Y" ;

    @Column(name = "fasd_createdby", length = 12, nullable = false)
    private String createdby;

    @Column(name = "fasd_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "fasd_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getMasterid() {
        return masterid;
    }

    public void setMasterid(String masterid) {
        this.masterid = masterid;
    }

    public String getEspid() {
        return espid;
    }

    public void setEspid(String espid) {
        this.espid = espid;
    }

    public String getPpeid() {
        return ppeid;
    }

    public void setPpeid(String ppeid) {
        this.ppeid = ppeid;
    }

    public String getPpecondition() {
        return ppecondition;
    }

    public void setPpecondition(String ppecondition) {
        this.ppecondition = ppecondition;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public String getWorkpermitsafety() {
        return workpermitsafety;
    }

    public void setWorkpermitsafety(String workpermitsafety) {
        this.workpermitsafety = workpermitsafety;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEspothers() {
        return espothers;
    }

    public void setEspothers(String espothers) {
        this.espothers = espothers;
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