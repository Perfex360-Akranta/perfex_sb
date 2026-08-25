package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "gen_tl_employeedtl", schema = "public")
public class GenTlEmployeedtl {

    @Id
    @NotNull
    @Size(max = 15)
    @Column(name = "empd_keyid", length = 15, nullable = false)
    private String keyid;

    @NotNull
    @Column(name = "empd_birthdate", nullable = false)
    private LocalDateTime birthdate;

    @NotNull
    @Size(max = 200)
    @Column(name = "empd_address", length = 200, nullable = false)
    private String address;

    @NotNull
    @Size(max = 8)
    @Column(name = "empd_cityid", length = 8, nullable = false)
    private String cityid;

    @NotNull
    @Size(max = 20)
    @Column(name = "empd_stateid", length = 20, nullable = false)
    private String stateid;

    @NotNull
    @Size(max = 8)
    @Column(name = "empd_countryid", length = 8, nullable = false)
    private String countryid;

    @NotNull
    @Size(max = 30)
    @Column(name = "empd_phone", length = 30, nullable = false)
    private String phone;

    @NotNull
    @Size(max = 500)
    @Column(name = "empd_image", length = 500, nullable = false)
    private String image;

    @NotNull
    @Size(max = 50)
    @Column(name = "empd_remarks", length = 50, nullable = false)
    private String remarks;

    @NotNull
    @Column(name = "empd_currentexperience", nullable = false)
    private int currentexperience;

    @NotNull
    @Column(name = "empd_otherexperience", nullable = false)
    private int otherexperience;

    @NotNull
    @Column(name = "empd_totalexperience", nullable = false)
    private int totalexperience;

    @NotNull
    @Size(max = 15)
    @Column(name = "empd_qualification", length = 15, nullable = false)
    private String qualification;

    @NotNull
    @Size(max = 10)
    @Column(name = "empd_discipline", length = 10, nullable = false)
    private String discipline;

    @NotNull

    @Column(name = "empd_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @NotNull
    @Size(max = 8)
    @Column(name = "empd_createdby", length = 8, nullable = false)
    private String createdby;

    @NotNull
    @Column(name = "empd_createdon", nullable = false)
    private LocalDateTime createdon = LocalDateTime.now();

    @NotNull
    @Column(name = "empd_modifiedon", nullable = false)
    private LocalDateTime modifiedon = LocalDateTime.now();

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getStateid() {
        return stateid;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getCurrentexperience() {
        return currentexperience;
    }

    public void setCurrentexperience(int currentexperience) {
        this.currentexperience = currentexperience;
    }

    public int getOtherexperience() {
        return otherexperience;
    }

    public void setOtherexperience(int otherexperience) {
        this.otherexperience = otherexperience;
    }

    public int getTotalexperience() {
        return totalexperience;
    }

    public void setTotalexperience(int totalexperience) {
        this.totalexperience = totalexperience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
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
