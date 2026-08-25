package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

// import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

//import java.math.BigDecimal;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "gen_tl_momattendance", schema = "public")

public class GenTlMomattendance {
    @Id
    @Column(name = "moma_keyid", length = 25, nullable = false)
    private String keyid;

    @Column(name = "moma_moms_keyid", length = 25, nullable = false)
    private String moms_keyid;

    @Column(name = "moma_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "moma_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    // @NotBlank
    // @Size(max = 15)
    private LocalDateTime date;

    @Column(name = "moma_employeeid", length = 15, nullable = false)
    // @NotNull
    private String employeeid;

    @Column(name = "moma_attandance", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private Character attandance;

    @Column(name = "moma_tempfield1", length = 1, nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private String tempfield1;

    @Column(name = "moma_tempfield2", length = 1, nullable = false)
    // @NotBlank
    // @Size(max = 500)
    private String tempfield2;

    @Column(name = "moma_tempfield3", length = 1, nullable = false)
    // @NotBlank
    // @Size(max = 500)
    private String tempfield3;

    @Column(name = "moma_tempfield4", length = 1, nullable = false)
    // @NotBlank
    // @Size(max = 500)
    private String tempfield4;

    @Column(name = "moma_tempfield5", length = 1, nullable = false)
    // @NotBlank
    // @Size(max = 500)
    private String tempfield5;

    @Column(name = "moma_active", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 15)
    private Character active;

    @Column(name = "moma_createdby", length = 15, nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private String createdby;

    @Column(name = "moma_createdon", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreationTimestamp
    // @NotBlank
    // @Size(max = 10)
    private LocalDateTime createdon;

    @Column(name = "moma_modifiedon", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @UpdateTimestamp
    // @NotBlank
    // @Size(max = 500)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getMoms_keyid() {
        return moms_keyid;
    }

    public void setMoms_keyid(String moms_keyid) {
        this.moms_keyid = moms_keyid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public Character getAttandance() {
        return attandance;
    }

    public void setAttandance(Character attandance) {
        this.attandance = attandance;
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
