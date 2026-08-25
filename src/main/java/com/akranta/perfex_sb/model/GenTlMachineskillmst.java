package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "gen_tl_machineskillmst", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_gen_tl_machineskillmst", 
                           columnNames = {"mskm_machineid", "mskm_skilldescription"})
       })
public class GenTlMachineskillmst {
    
    @Id
    @Column(name = "mskm_machineid", length = 15, nullable = false)
    private String machineid;

    @Column(name = "mskm_skilldescription", length = 600, nullable = false)
    private String skilldescription;

    @Column(name = "mskm_skillfordepartment", length = 1, nullable = false)
    private Character skillfordepartment;

    @Column(name = "mskm_tempfield1", length = 100, nullable = false)
    private String tempfield1;

    @Column(name = "mskm_tempfield2", length = 100, nullable = false)
    private String tempfield2;

    @Column(name = "mskm_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "mskm_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "mskm_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "mskm_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public String getSkilldescription() {
        return skilldescription;
    }

    public void setSkilldescription(String skilldescription) {
        this.skilldescription = skilldescription;
    }

    public Character getSkillfordepartment() {
        return skillfordepartment;
    }

    public void setSkillfordepartment(Character skillfordepartment) {
        this.skillfordepartment = skillfordepartment;
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