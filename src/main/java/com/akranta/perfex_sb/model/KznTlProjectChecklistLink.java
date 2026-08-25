package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "kzn_tl_project_checklist_link", schema = "public")
public class KznTlProjectChecklistLink {

    @Id
    @Column(name = "pcll_keyid", length = 10, nullable = false)
    private String keyid;

    @Column(name = "pcll_projectid", length = 15, nullable = false)
    private String projectid;

    @Column(name = "pcll_checklistid", length = 8, nullable = false)
    private String checklistid;

    @Column(name = "pcll_include",columnDefinition = "char(1)", nullable = false)
    private Character include;

    @Column(name = "pcll_verifiedby", length = 8, nullable = false)
    private String verifiedby;

    @Column(name = "pcll_verifiedstatus",columnDefinition = "char(1)", nullable = false)
    private Character verifiedstatus;

    @Column(name = "pcll_tempfield1",columnDefinition = "char(1)", nullable = false)
    private Character tempfield1;

    @Column(name = "pcll_tempfield2",columnDefinition = "char(1)", nullable = false)
    private Character tempfield2;

    @Column(name = "pcll_tempfield3",columnDefinition = "char(1)", nullable = false)
    private Character tempfield3;

    @Column(name = "pcll_tempfield4",columnDefinition = "char(1)", nullable = false)
    private Character tempfield4;

    @Column(name = "pcll_tempfield5",columnDefinition = "char(1)", nullable = false)
    private Character tempfield5;

    @Column(name = "pcll_active",columnDefinition = "char(1)", nullable = false)
    private Character active;

    @Column(name = "pcll_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "pcll_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "pcll_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getChecklistid() {
        return checklistid;
    }

    public void setChecklistid(String checklistid) {
        this.checklistid = checklistid;
    }

    public Character getInclude() {
        return include;
    }

    public void setInclude(Character include) {
        this.include = include;
    }

    public String getVerifiedby() {
        return verifiedby;
    }

    public void setVerifiedby(String verifiedby) {
        this.verifiedby = verifiedby;
    }

    public Character getVerifiedstatus() {
        return verifiedstatus;
    }

    public void setVerifiedstatus(Character verifiedstatus) {
        this.verifiedstatus = verifiedstatus;
    }

    public Character getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(Character tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public Character getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(Character tempfield2) {
        this.tempfield2 = tempfield2;
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
