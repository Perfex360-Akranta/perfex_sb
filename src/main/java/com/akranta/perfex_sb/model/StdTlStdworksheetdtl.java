package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "std_tl_stdworksheetdtl", schema = "public")
public class StdTlStdworksheetdtl {
    
    @Id
    @Column(name = "stwd_keyid", length = 16, nullable = false)
    private String keyid;

    @Column(name = "stwd_stws_keyid", length = 16, nullable = false)
    private String stws_keyid;

    @Column(name = "stwd_majorsteps", length = 200, nullable = false)
    private String majorsteps;

    @Column(name = "stwd_typeofmanpower", length = 2, nullable = false)
    private String typeofmanpower;

    @Column(name = "stwd_mantime", length = 50, nullable = false)
    private String mantime;

    @Column(name = "stwd_processtime", length = 50, nullable = false)
    private String processtime;

    @Column(name = "stwd_waittime", length = 50, nullable = false)
    private String waittime;

    @Column(name = "stwd_traveltime", length = 50, nullable = false)
    private String traveltime;

    @Column(name = "stwd_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "stwd_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "stwd_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "stwd_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "stwd_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "stwd_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "stwd_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "stwd_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "stwd_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getStws_keyid() {
        return stws_keyid;
    }

    public void setStws_keyid(String stwsKeyid) {
        this.stws_keyid = stwsKeyid;
    }

    public String getMajorsteps() {
        return majorsteps;
    }

    public void setMajorsteps(String majorsteps) {
        this.majorsteps = majorsteps;
    }

    public String getTypeofmanpower() {
        return typeofmanpower;
    }

    public void setTypeofmanpower(String typeofmanpower) {
        this.typeofmanpower = typeofmanpower;
    }

    public String getMantime() {
        return mantime;
    }

    public void setMantime(String mantime) {
        this.mantime = mantime;
    }

    public String getProcesstime() {
        return processtime;
    }

    public void setProcesstime(String processtime) {
        this.processtime = processtime;
    }

    public String getWaittime() {
        return waittime;
    }

    public void setWaittime(String waittime) {
        this.waittime = waittime;
    }

    public String getTraveltime() {
        return traveltime;
    }

    public void setTraveltime(String traveltime) {
        this.traveltime = traveltime;
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

    public String getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(String tempfield5) {
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