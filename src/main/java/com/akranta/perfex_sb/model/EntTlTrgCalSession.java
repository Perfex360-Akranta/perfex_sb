package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ent_tl_trgcalsession", schema = "public")
public class EntTlTrgCalSession {

    @Id
    @Column(name = "etcs_keyid", length = 15, nullable = false)
    private String etcsKeyid;

    @Column(name = "etcs_etcm_keyid", length = 15, nullable = false)
    private String etcsEtcmKeyid;

    @Column(name = "etcs_etcm_flid", length = 12, nullable = false)
    private String etcsEtcmFlid;

    @Column(name = "etcs_name", length = 25, nullable = false)
    private String etcsName;

    @Column(name = "etcs_sessiondate", nullable = false)
    private LocalDateTime etcsSessiondate;

    @Column(name = "etcs_fromdate", nullable = false)
    private LocalDateTime etcsFromdate;

    @Column(name = "etcs_tilldate", nullable = false)
    private LocalDateTime etcsTilldate;

    @Column(name = "etcs_dateadd", nullable = false)
    private LocalDateTime etcsDateadd;

    @Column(name = "etcs_tempfield1", length = 1, nullable = false)
    private String etcsTempfield1;

    @Column(name = "etcs_tempfield2", length = 1, nullable = false)
    private String etcsTempfield2;

    @Column(name = "etcs_tempfield3", length = 1, nullable = false)
    private String etcsTempfield3;

    @Column(name = "etcs_tempfield4", length = 1, nullable = false)
    private String etcsTempfield4;

    @Column(name = "etcs_tempfield5", length = 1, nullable = false)
    private String etcsTempfield5;

    @Column(name = "etcs_active",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcsActive;

    @Column(name = "etcs_createdon", nullable = false)
    private LocalDateTime etcsCreatedon;

    @Column(name = "etcs_modifiedon", nullable = false)
    private LocalDateTime etcsModifiedon;

    public String getEtcsKeyid() { return etcsKeyid; }
    public void setEtcsKeyid(String etcsKeyid) { this.etcsKeyid = etcsKeyid; }

    public String getEtcsEtcmKeyid() { return etcsEtcmKeyid; }
    public void setEtcsEtcmKeyid(String etcsEtcmKeyid) { this.etcsEtcmKeyid = etcsEtcmKeyid; }

    public String getEtcsEtcmFlid() { return etcsEtcmFlid; }
    public void setEtcsEtcmFlid(String etcsEtcmFlid) { this.etcsEtcmFlid = etcsEtcmFlid; }

    public String getEtcsName() { return etcsName; }
    public void setEtcsName(String etcsName) { this.etcsName = etcsName; }

    public LocalDateTime getEtcsSessiondate() { return etcsSessiondate; }
    public void setEtcsSessiondate(LocalDateTime etcsSessiondate) { this.etcsSessiondate = etcsSessiondate; }

    public LocalDateTime getEtcsFromdate() { return etcsFromdate; }
    public void setEtcsFromdate(LocalDateTime etcsFromdate) { this.etcsFromdate = etcsFromdate; }

    public LocalDateTime getEtcsTilldate() { return etcsTilldate; }
    public void setEtcsTilldate(LocalDateTime etcsTilldate) { this.etcsTilldate = etcsTilldate; }

    public LocalDateTime getEtcsDateadd() { return etcsDateadd; }
    public void setEtcsDateadd(LocalDateTime etcsDateadd) { this.etcsDateadd = etcsDateadd; }

    public String getEtcsTempfield1() { return etcsTempfield1; }
    public void setEtcsTempfield1(String etcsTempfield1) { this.etcsTempfield1 = etcsTempfield1; }

    public String getEtcsTempfield2() { return etcsTempfield2; }
    public void setEtcsTempfield2(String etcsTempfield2) { this.etcsTempfield2 = etcsTempfield2; }

    public String getEtcsTempfield3() { return etcsTempfield3; }
    public void setEtcsTempfield3(String etcsTempfield3) { this.etcsTempfield3 = etcsTempfield3; }

    public String getEtcsTempfield4() { return etcsTempfield4; }
    public void setEtcsTempfield4(String etcsTempfield4) { this.etcsTempfield4 = etcsTempfield4; }

    public String getEtcsTempfield5() { return etcsTempfield5; }
    public void setEtcsTempfield5(String etcsTempfield5) { this.etcsTempfield5 = etcsTempfield5; }

    public Character getEtcsActive() { return etcsActive; }
    public void setEtcsActive(Character etcsActive) { this.etcsActive = etcsActive; }

    public LocalDateTime getEtcsCreatedon() { return etcsCreatedon; }
    public void setEtcsCreatedon(LocalDateTime etcsCreatedon) { this.etcsCreatedon = etcsCreatedon; }

    public LocalDateTime getEtcsModifiedon() { return etcsModifiedon; }
    public void setEtcsModifiedon(LocalDateTime etcsModifiedon) { this.etcsModifiedon = etcsModifiedon; }
}
