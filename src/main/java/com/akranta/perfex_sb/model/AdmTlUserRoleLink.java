package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "adm_tl_user_role_link",schema = "public")
public class AdmTlUserRoleLink 
{
    @Id
    @Column(name = "arul_userid", length = 8, nullable = false)
    private String userid;
    
    @Column(name = "arul_roleid", length = 12, nullable = false)
    private String roleid;
    
    @Column(name = "arul_active", length = 1, columnDefinition = "CHAR(1)",nullable = false)
    private Character active;
    
    @Column(name = "arul_createdby", length = 8, nullable = false)
    private String createdby;
    
    @Column(name = "arul_createdon", nullable = false)
    private LocalDateTime createdon;
    
    @Column(name = "arul_modifiedon",nullable = false)
    private LocalDateTime modifiedon;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
