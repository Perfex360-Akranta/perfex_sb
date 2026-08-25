package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_tl_usermst", schema = "public")
public class AdmTlUsermst {

    @Id
    @Column(name = "usrm_keyid", length = 10, nullable = false)
    private String keyid;

    @NotNull
    @Column(name = "usrm_userpin", nullable = false)
    private Integer userpin;

    @NotNull
    @Size(max = 50)
    @Column(name = "usrm_username", length = 50, nullable = false)
    private String username;

    @NotNull
    @Size(max = 15)
    @Column(name = "usrm_ccno", length = 15, nullable = false, unique = true)
    private String ccno;

    @NotNull
    @Size(max = 30)
    @Column(name = "usrm_loginid", length = 30, nullable = false)
    private String loginid;

    @NotNull
    @Size(max = 30)
    @Column(name = "usrm_password", length = 30, nullable = false)
    private String password;

    @NotNull
    @Size(max = 30)
    @Column(name = "usrm_defaultpassword", length = 30, nullable = false)
    private String defaultpassword;

    @NotNull
    @Size(max = 8)
    @Column(name = "usrm_securitypolicyid", length = 8, nullable = false)
    private String securitypolicyid;

    @NotNull
    @Size(max = 50)
    @Column(name = "usrm_designationid", length = 50, nullable = false)
    private String designationid;

    @NotNull
    @Size(max = 10)
    @Column(name = "usrm_departmentid", length = 10, nullable = false)
    private String departmentid;

    @NotNull
    @Column(name = "usrm_extensionphone", nullable = false)
    private Integer extensionphone;

    @NotNull
    @Column(name = "usrm_lastpwdchanged", nullable = false)
    private LocalDateTime lastpwdchanged;

    @NotNull
    @Column(name = "usrm_lastlogindate", nullable = false)
    private LocalDateTime lastlogindate;

    @NotNull
    @Size(max = 1)
    @Column(name = "usrm_isuserlocked", length = 1, nullable = false)
    private String isuserlocked;

    @NotNull
    @Size(max = 1)
    @Column(name = "usrm_isactive", length = 1, nullable = false)
    private String isactive;

    @NotNull
    @Size(max = 1)
    @Column(name = "usrm_isadministartor", length = 1, nullable = false)
    private String isadministartor;

    @NotNull
    @Size(max = 1)
    @Column(name = "usrm_ispwdlockenabled", length = 1, nullable = false)
    private String ispwdlockenabled;

    @NotNull
    @Size(max = 1)
    @Column(name = "usrm_istemplateuser", length = 1, nullable = false)
    private String istemplateuser;

    @NotNull
    @Size(max = 250)
    @Column(name = "usrm_remarks", length = 250, nullable = false)
    private String remarks;

    @NotNull
    @Column(name = "usrm_loginattempt", nullable = false)
    private Integer loginatempt;

    @NotNull
    @Size(max = 1)
    @Column(name = "usrm_isvalidityreq", length = 1, nullable = false)
    private String isvalidityreq;

    @NotNull
    @Column(name = "usrm_validfrom", nullable = false)
    private LocalDateTime validfrom;

    @NotNull
    @Column(name = "usrm_validtill", nullable = false)
    private LocalDateTime validtill;

    @NotNull
    @Size(max = 10)
    @Column(name = "usrm_createdby", length = 10, nullable = false)
    private String createdby;

    @NotNull
    @Column(name = "usrm_createdon", nullable = false)
    private LocalDateTime createdon;

    @NotNull
    @Column(name = "usrm_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    // Default constructor
    public AdmTlUsermst() {
    }

    // Constructor with all fields
    public AdmTlUsermst(String keyid, Integer userpin, String username, String ccno,
            String loginid, String password, String defaultpassword,
            String securitypolicyid, String designationid, String departmentid,
            Integer extensionphone, LocalDateTime lastpwdchanged, LocalDateTime lastlogindate,
            String isuserlocked, String isactive, String isadministartor,
            String ispwdlockenabled, String istemplateuser, String remarks,
            Integer loginatempt, String isvalidityreq, LocalDateTime validfrom,
            LocalDateTime validtill, String createdby, LocalDateTime createdon,
            LocalDateTime modifiedon) {
        this.keyid = keyid;
        this.userpin = userpin;
        this.username = username;
        this.ccno = ccno;
        this.loginid = loginid;
        this.password = password;
        this.defaultpassword = defaultpassword;
        this.securitypolicyid = securitypolicyid;
        this.designationid = designationid;
        this.departmentid = departmentid;
        this.extensionphone = extensionphone;
        this.lastpwdchanged = lastpwdchanged;
        this.lastlogindate = lastlogindate;
        this.isuserlocked = isuserlocked;
        this.isactive = isactive;
        this.isadministartor = isadministartor;
        this.ispwdlockenabled = ispwdlockenabled;
        this.istemplateuser = istemplateuser;
        this.remarks = remarks;
        this.loginatempt = loginatempt;
        this.isvalidityreq = isvalidityreq;
        this.validfrom = validfrom;
        this.validtill = validtill;
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

    public Integer getUserpin() {
        return userpin;
    }

    public void setUserpin(Integer userpin) {
        this.userpin = userpin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCcno() {
        return ccno;
    }

    public void setCcno(String ccno) {
        this.ccno = ccno;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDefaultpassword() {
        return defaultpassword;
    }

    public void setDefaultpassword(String defaultpassword) {
        this.defaultpassword = defaultpassword;
    }

    public String getSecuritypolicyid() {
        return securitypolicyid;
    }

    public void setSecuritypolicyid(String securitypolicyid) {
        this.securitypolicyid = securitypolicyid;
    }

    public String getDesignationid() {
        return designationid;
    }

    public void setDesignationid(String designationid) {
        this.designationid = designationid;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public Integer getExtensionphone() {
        return extensionphone;
    }

    public void setExtensionphone(Integer extensionphone) {
        this.extensionphone = extensionphone;
    }

    public LocalDateTime getLastpwdchanged() {
        return lastpwdchanged;
    }

    public void setLastpwdchanged(LocalDateTime lastpwdchanged) {
        this.lastpwdchanged = lastpwdchanged;
    }

    public LocalDateTime getLastlogindate() {
        return lastlogindate;
    }

    public void setLastlogindate(LocalDateTime lastlogindate) {
        this.lastlogindate = lastlogindate;
    }

    public String getIsuserlocked() {
        return isuserlocked;
    }

    public void setIsuserlocked(String isuserlocked) {
        this.isuserlocked = isuserlocked;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getIsadministartor() {
        return isadministartor;
    }

    public void setIsadministartor(String isadministartor) {
        this.isadministartor = isadministartor;
    }

    public String getIspwdlockenabled() {
        return ispwdlockenabled;
    }

    public void setIspwdlockenabled(String ispwdlockenabled) {
        this.ispwdlockenabled = ispwdlockenabled;
    }

    public String getIstemplateuser() {
        return istemplateuser;
    }

    public void setIstemplateuser(String istemplateuser) {
        this.istemplateuser = istemplateuser;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getLoginatempt() {
        return loginatempt;
    }

    public void setLoginatempt(Integer loginatempt) {
        this.loginatempt = loginatempt;
    }

    public String getIsvalidityreq() {
        return isvalidityreq;
    }

    public void setIsvalidityreq(String isvalidityreq) {
        this.isvalidityreq = isvalidityreq;
    }

    public LocalDateTime getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(LocalDateTime validfrom) {
        this.validfrom = validfrom;
    }

    public LocalDateTime getValidtill() {
        return validtill;
    }

    public void setValidtill(LocalDateTime validtill) {
        this.validtill = validtill;
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
        return "AdmTlUsermst{" +
                "keyid='" + keyid + '\'' +
                ", userpin=" + userpin +
                ", username='" + username + '\'' +
                ", ccno='" + ccno + '\'' +
                ", loginid='" + loginid + '\'' +
                ", designationid='" + designationid + '\'' +
                ", departmentid='" + departmentid + '\'' +
                ", isactive='" + isactive + '\'' +
                ", isadministartor='" + isadministartor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AdmTlUsermst))
            return false;
        AdmTlUsermst that = (AdmTlUsermst) o;
        return keyid != null ? keyid.equals(that.keyid) : that.keyid == null;
    }

    @Override
    public int hashCode() {
        return keyid != null ? keyid.hashCode() : 0;
    }

    public String getIsExpired() {
        if (!"Y".equalsIgnoreCase(isvalidityreq)) {
            return "N";
        }

        LocalDateTime today = LocalDateTime.now();

        if (validfrom != null && validfrom.isAfter(today)) {
            return "S"; // Scheduled
        }

        if (validtill != null && validtill.isBefore(today)) {
            return "E"; // Expired
        }

        return "N"; // Valid
    }
}