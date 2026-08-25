package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ent_tl_trgfaculty", schema = "public")
public class EntTlTrgFaculty {

    @Id
    @Column(name = "etcf_keyid", length = 15, nullable = false)
    private String etcfKeyid;

    @Column(name = "etcf_etcm_keyid", length = 15, nullable = false)
    private String etcfEtcmKeyid;

    @Column(name = "etcf_etcm_flid", length = 12, nullable = false)
    private String etcfEtcmFlid;

    @Column(name = "etcf_facultyid", length = 10, nullable = false)
    private String etcfFacultyid;

    @Column(name = "etcf_facultytype",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcfFacultytype;

    @Column(name = "etcf_dateadd", nullable = false)
    private LocalDateTime etcfDateadd;

    @Column(name = "etcf_tempfield1", length = 1, nullable = false)
    private String etcfTempfield1;

    @Column(name = "etcf_tempfield2", length = 1, nullable = false)
    private String etcfTempfield2;

    @Column(name = "etcf_tempfield3", length = 1, nullable = false)
    private String etcfTempfield3;

    @Column(name = "etcf_tempfield4", length = 1, nullable = false)
    private String etcfTempfield4;

    @Column(name = "etcf_tempfield5", length = 1, nullable = false)
    private String etcfTempfield5;

    @Column(name = "etcf_active",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcfActive;

    @Column(name = "etcf_createdby", length = 8, nullable = false)
    private String etcfCreatedby;

    @Column(name = "etcf_createdon", nullable = false)
    private LocalDateTime etcfCreatedon;

    @Column(name = "etcf_modifiedon", nullable = false)
    private LocalDateTime etcfModifiedon;

    public String getEtcfKeyid() { return etcfKeyid; }
    public void setEtcfKeyid(String etcfKeyid) { this.etcfKeyid = etcfKeyid; }

    public String getEtcfEtcmKeyid() { return etcfEtcmKeyid; }
    public void setEtcfEtcmKeyid(String etcfEtcmKeyid) { this.etcfEtcmKeyid = etcfEtcmKeyid; }

    public String getEtcfEtcmFlid() { return etcfEtcmFlid; }
    public void setEtcfEtcmFlid(String etcfEtcmFlid) { this.etcfEtcmFlid = etcfEtcmFlid; }

    public String getEtcfFacultyid() { return etcfFacultyid; }
    public void setEtcfFacultyid(String etcfFacultyid) { this.etcfFacultyid = etcfFacultyid; }

    public Character getEtcfFacultytype() { return etcfFacultytype; }
    public void setEtcfFacultytype(Character etcfFacultytype) { this.etcfFacultytype = etcfFacultytype; }

    public LocalDateTime getEtcfDateadd() { return etcfDateadd; }
    public void setEtcfDateadd(LocalDateTime etcfDateadd) { this.etcfDateadd = etcfDateadd; }

    public String getEtcfTempfield1() { return etcfTempfield1; }
    public void setEtcfTempfield1(String etcfTempfield1) { this.etcfTempfield1 = etcfTempfield1; }

    public String getEtcfTempfield2() { return etcfTempfield2; }
    public void setEtcfTempfield2(String etcfTempfield2) { this.etcfTempfield2 = etcfTempfield2; }

    public String getEtcfTempfield3() { return etcfTempfield3; }
    public void setEtcfTempfield3(String etcfTempfield3) { this.etcfTempfield3 = etcfTempfield3; }

    public String getEtcfTempfield4() { return etcfTempfield4; }
    public void setEtcfTempfield4(String etcfTempfield4) { this.etcfTempfield4 = etcfTempfield4; }

    public String getEtcfTempfield5() { return etcfTempfield5; }
    public void setEtcfTempfield5(String etcfTempfield5) { this.etcfTempfield5 = etcfTempfield5; }

    public Character getEtcfActive() { return etcfActive; }
    public void setEtcfActive(Character etcfActive) { this.etcfActive = etcfActive; }

    public String getEtcfCreatedby() { return etcfCreatedby; }
    public void setEtcfCreatedby(String etcfCreatedby) { this.etcfCreatedby = etcfCreatedby; }

    public LocalDateTime getEtcfCreatedon() { return etcfCreatedon; }
    public void setEtcfCreatedon(LocalDateTime etcfCreatedon) { this.etcfCreatedon = etcfCreatedon; }

    public LocalDateTime getEtcfModifiedon() { return etcfModifiedon; }
    public void setEtcfModifiedon(LocalDateTime etcfModifiedon) { this.etcfModifiedon = etcfModifiedon; }
}
