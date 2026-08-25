package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "adm_tl_usercustompages", schema = "public")
public class AdmTlUsercustompages {

    @Id
    @Column(name = "uscp_keyid", length = 20, nullable = false)
    private String keyid;

    @Column(name = "uscp_usrm_keyid", length = 20, nullable = false)
    private String usrmKeyid;

    @Column(name = "uscp_pageuri", length = 100, nullable = false)
    private String pageuri;

    @Column(name = "uscp_params", length = 500, nullable = false)
    private String params;

    @Column(name = "uscp_formheader", length = 100, nullable = false)
    private String formheader;

    @Column(name = "uscp_displayorder", nullable = false)
    private Integer displayorder;

    @Column(name = "uscp_tempfield1", length = 1, nullable = false)
    private Character tempfield1;

    @Column(name = "uscp_tempfield2", length = 1, nullable = false)
    private Character tempfield2;

    @Column(name = "uscp_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column(name = "uscp_tempfield4", length = 1, nullable = false)
    private Character tempfield4;

    @Column(name = "uscp_tempfield5", length = 1, nullable = false)
    private Character tempfield5;

    @Column(name = "uscp_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "uscp_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "uscp_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "uscp_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    /* Getters and Setters */

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getUsrmKeyid() {
        return usrmKeyid;
    }

    public void setUsrmKeyid(String usrmKeyid) {
        this.usrmKeyid = usrmKeyid;
    }

    public String getPageuri() {
        return pageuri;
    }

    public void setPageuri(String pageuri) {
        this.pageuri = pageuri;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getFormheader() {
        return formheader;
    }

    public void setFormheader(String formheader) {
        this.formheader = formheader;
    }

    public Integer getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(Integer displayorder) {
        this.displayorder = displayorder;
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
