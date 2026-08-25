package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "gen_tl_employeemst", schema = "public")
public class GenTlEmployeemst {

    @Id
    @NotNull
    @Size(max = 15)
    @Column(name = "empm_keyid", length = 15, nullable = false)
    private String keyid;

    @NotNull
    @Size(max = 50)
    @Column(name = "empm_name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(max = 30)
    @Column(name = "empm_code", length = 30, nullable = false, unique = true)
    private String code;

    @NotNull
    @Size(max = 1)

    @Column(name = "empm_employeetype", length = 1, nullable = false)
    private String employeetype;

    @NotNull
    @Size(max = 30)
    @Column(name = "empm_employeenumber", length = 30, nullable = false)
    private String employeenumber;

    @NotNull
    @Column(name = "empm_joineddate", nullable = false)
    private LocalDateTime joineddate;

    @Size(max = 6)
    @Column(name = "empm_departmentid", length = 6, nullable = false)
    private String departmentid;

    @Size(max = 15)
    @Column(name = "empm_designationid", length = 15, nullable = false)
    private String designationid;

    @Size(max = 10)
    @Column(name = "empm_factoryid", length = 10, nullable = false)
    private String factoryid;

    @Size(max = 1)

    @Column(name = "empm_isshiftincharge", length = 1, nullable = false)
    private String isshiftincharge;

    @Size(max = 10)
    @Column(name = "empm_sectionid", length = 10, nullable = false)
    private String sectionid;

    @Size(max = 10)

    @Column(name = "empm_iscellmanager", length = 10, nullable = false)
    private String iscellmanager;

    @Size(max = 10)
    @Column(name = "empm_cellid", length = 10, nullable = false)
    private String cellid;

    @Size(max = 15)
    @Column(name = "empm_tradeid", length = 15, nullable = false)
    private String tradeid;

    @Size(max = 20)
    @Column(name = "empm_extensionphone", length = 20, nullable = false)
    private String extensionphone;

    @Size(max = 1)

    @Column(name = "empm_gender", length = 1, nullable = false)
    private String gender;

    @Size(max = 30)
    @Column(name = "empm_mobile", length = 30, nullable = false)
    private String mobile;

    @Size(max = 100)

    @Column(name = "empm_email", length = 100, nullable = false)
    private String email;

    @Size(max = 200)
    @Column(name = "empm_personalinfo", length = 200, nullable = false)
    private String personalinfo;

    @Size(max = 200)
    @Column(name = "empm_remarks", length = 200, nullable = false)
    private String remarks;

    @Size(max = 10)
    @Column(name = "empm_issectionmanager", length = 10, nullable = false)
    private String issectionmanager;

    @Size(max = 100)
    @Column(name = "empm_skillcategory", length = 100, nullable = false)
    private String skillcategory;

    @Size(max = 10)
    @Column(name = "empm_gradeid", length = 10, nullable = false)
    private String gradeid;

    @Size(max = 1)

    @Column(name = "empm_isoperator", length = 1, nullable = false)
    private String isoperator;

    @Size(max = 100)
    @Column(name = "empm_company", length = 100, nullable = false)
    private String company;

    @Size(max = 15)
    @Column(name = "empm_roleid", length = 15, nullable = false)
    private String roleid;

    @Size(max = 10)
    @Column(name = "empm_sbuid", length = 10, nullable = false)
    private String sbuid;

    @Size(max = 1)

    @Column(name = "empm_enableemail", length = 1, nullable = false)
    private String embmenablemail;

    @Size(max = 10)
    @Column(name = "empm_location", length = 10, nullable = false)
    private String location;

    @Size(max = 1)

    @Column(name = "empm_active", length = 1, nullable = false)
    private String active = "Y";

    @Size(max = 8)
    @Column(name = "empm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "empm_createdon", nullable = false)
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "empm_modifiedon", nullable = false)
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Constructors
    public GenTlEmployeemst() {
    }

    public GenTlEmployeemst(String keyid, String name, String code, String employeetype,
            String employeenumber, LocalDateTime joineddate, String departmentid,
            String designationid, String factoryid, String isshiftincharge,
            String sectionid, String iscellmanager, String cellid, String tradeid,
            String extensionphone, String gender, String mobile, String email,
            String personalinfo, String remarks, String issectionmanager,
            String skillcategory, String gradeid, String isoperator, String company,
            String roleid, String sbuid, String embmenablemail, String location,
            String active, String createdby, LocalDateTime createdon, LocalDateTime modifiedon) {
        this.keyid = keyid;
        this.name = name;
        this.code = code;
        this.employeetype = employeetype;
        this.employeenumber = employeenumber;
        this.joineddate = joineddate;
        this.departmentid = departmentid;
        this.designationid = designationid;
        this.factoryid = factoryid;
        this.isshiftincharge = isshiftincharge;
        this.sectionid = sectionid;
        this.iscellmanager = iscellmanager;
        this.cellid = cellid;
        this.tradeid = tradeid;
        this.extensionphone = extensionphone;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
        this.personalinfo = personalinfo;
        this.remarks = remarks;
        this.issectionmanager = issectionmanager;
        this.skillcategory = skillcategory;
        this.gradeid = gradeid;
        this.isoperator = isoperator;
        this.company = company;
        this.roleid = roleid;
        this.sbuid = sbuid;
        this.embmenablemail = embmenablemail;
        this.location = location;
        this.active = active;
        this.createdby = createdby;
        this.createdon = createdon;
        this.modifiedon = modifiedon;
    }

    // Getters and Setters
    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmployeetype() {
        return employeetype;
    }

    public void setEmployeetype(String employeetype) {
        this.employeetype = employeetype;
    }

    public String getEmployeenumber() {
        return employeenumber;
    }

    public void setEmployeenumber(String employeenumber) {
        this.employeenumber = employeenumber;
    }

    public LocalDateTime getJoineddate() {
        return joineddate;
    }

    public void setJoineddate(LocalDateTime joineddate) {
        this.joineddate = joineddate;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getDesignationid() {
        return designationid;
    }

    public void setDesignationid(String designationid) {
        this.designationid = designationid;
    }

    public String getFactoryid() {
        return factoryid;
    }

    public void setFactoryid(String factoryid) {
        this.factoryid = factoryid;
    }

    public String getIsshiftincharge() {
        return isshiftincharge;
    }

    public void setIsshiftincharge(String isshiftincharge) {
        this.isshiftincharge = isshiftincharge;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getIscellmanager() {
        return iscellmanager;
    }

    public void setIscellmanager(String iscellmanager) {
        this.iscellmanager = iscellmanager;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getExtensionphone() {
        return extensionphone;
    }

    public void setExtensionphone(String extensionphone) {
        this.extensionphone = extensionphone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalinfo() {
        return personalinfo;
    }

    public void setPersonalinfo(String personalinfo) {
        this.personalinfo = personalinfo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIssectionmanager() {
        return issectionmanager;
    }

    public void setIssectionmanager(String issectionmanager) {
        this.issectionmanager = issectionmanager;
    }

    public String getSkillcategory() {
        return skillcategory;
    }

    public void setSkillcategory(String skillcategory) {
        this.skillcategory = skillcategory;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public String getIsoperator() {
        return isoperator;
    }

    public void setIsoperator(String isoperator) {
        this.isoperator = isoperator;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getSbuid() {
        return sbuid;
    }

    public void setSbuid(String sbuid) {
        this.sbuid = sbuid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    @Override
    public String toString() {
        return "GenTlEmployeemst{" +
                "keyid='" + keyid + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", employeetype='" + employeetype + '\'' +
                ", employeenumber='" + employeenumber + '\'' +
                ", departmentid='" + departmentid + '\'' +
                ", designationid='" + designationid + '\'' +
                ", active='" + active + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenTlEmployeemst))
            return false;
        GenTlEmployeemst that = (GenTlEmployeemst) o;
        return keyid != null ? keyid.equals(that.keyid) : that.keyid == null;
    }

    @Override
    public int hashCode() {
        return keyid != null ? keyid.hashCode() : 0;
    }

    public String getEmbmenablemail() {
        return embmenablemail;
    }

    public void setEmbmenablemail(String embmenablemail) {
        this.embmenablemail = embmenablemail;
    }
}
