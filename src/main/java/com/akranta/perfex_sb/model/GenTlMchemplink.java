package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "gen_tl_mchemplink", schema = "public")
@IdClass(GenTlMchemplinkId.class)
public class GenTlMchemplink {
    @Id
    @Column(name = "mcem_machineid", length = 15, nullable = false)
    private String machineid;
@Id
    @Column(name = "mcem_employeeid", length = 15, nullable = false)
    private String employeeid;

    @Column(name = "mcem_tempfield1", length = 100, nullable = false)
    private String tempfield1;

    @Column(name = "mcem_tempfield2", length = 100, nullable = false)
    private String tempfield2;

    @Column(name = "mcem_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "mcem_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "mcem_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "mcem_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
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