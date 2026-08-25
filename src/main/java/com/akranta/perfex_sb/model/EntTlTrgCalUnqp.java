package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ent_tl_trgcalunqp", schema = "public")
public class EntTlTrgCalUnqp {

    @Id
    @Column(name = "etcu_keyid", length = 15, nullable = false)
    private String etcuKeyid;

    @Column(name = "etcu_etcm_keyid", length = 15, nullable = false)
    private String etcuEtcmKeyid;

    @Column(name = "etcu_role_keyid", length = 10, nullable = false)
    private String etcuRoleKeyid;

    @Column(name = "etcu_roledmt", length = 12, nullable = false)
    private String etcuRoledmt;

    @Column(name = "etcu_rolejh", length = 12, nullable = false)
    private String etcuRolejh;

    @Column(name = "etcu_dateadd", nullable = false)
    private LocalDateTime etcuDateadd;

    @Column(name = "etcu_tempfield1", length = 1, nullable = false)
    private String etcuTempfield1;

    @Column(name = "etcu_tempfield2", length = 1, nullable = false)
    private String etcuTempfield2;

    @Column(name = "etcu_tempfield3", length = 1, nullable = false)
    private String etcuTempfield3;

    @Column(name = "etcu_tempfield4", length = 1, nullable = false)
    private String etcuTempfield4;

    @Column(name = "etcu_tempfield5", length = 1, nullable = false)
    private String etcuTempfield5;

    @Column(name = "etcu_createdby", length = 8, nullable = false)
    private String etcuCreatedby;

    @Column(name = "etcu_active",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcuActive;

    @Column(name = "etcu_createdon", nullable = false)
    private LocalDateTime etcuCreatedon;

    @Column(name = "etcu_modifiedon", nullable = false)
    private LocalDateTime etcuModifiedon;

    public String getEtcuKeyid() { return etcuKeyid; }
    public void setEtcuKeyid(String etcuKeyid) { this.etcuKeyid = etcuKeyid; }

    public String getEtcuEtcmKeyid() { return etcuEtcmKeyid; }
    public void setEtcuEtcmKeyid(String etcuEtcmKeyid) { this.etcuEtcmKeyid = etcuEtcmKeyid; }

    public String getEtcuRoleKeyid() { return etcuRoleKeyid; }
    public void setEtcuRoleKeyid(String etcuRoleKeyid) { this.etcuRoleKeyid = etcuRoleKeyid; }

    public String getEtcuRoledmt() { return etcuRoledmt; }
    public void setEtcuRoledmt(String etcuRoledmt) { this.etcuRoledmt = etcuRoledmt; }

    public String getEtcuRolejh() { return etcuRolejh; }
    public void setEtcuRolejh(String etcuRolejh) { this.etcuRolejh = etcuRolejh; }

    public LocalDateTime getEtcuDateadd() { return etcuDateadd; }
    public void setEtcuDateadd(LocalDateTime etcuDateadd) { this.etcuDateadd = etcuDateadd; }

    public String getEtcuTempfield1() { return etcuTempfield1; }
    public void setEtcuTempfield1(String etcuTempfield1) { this.etcuTempfield1 = etcuTempfield1; }

    public String getEtcuTempfield2() { return etcuTempfield2; }
    public void setEtcuTempfield2(String etcuTempfield2) { this.etcuTempfield2 = etcuTempfield2; }

    public String getEtcuTempfield3() { return etcuTempfield3; }
    public void setEtcuTempfield3(String etcuTempfield3) { this.etcuTempfield3 = etcuTempfield3; }

    public String getEtcuTempfield4() { return etcuTempfield4; }
    public void setEtcuTempfield4(String etcuTempfield4) { this.etcuTempfield4 = etcuTempfield4; }

    public String getEtcuTempfield5() { return etcuTempfield5; }
    public void setEtcuTempfield5(String etcuTempfield5) { this.etcuTempfield5 = etcuTempfield5; }

    public String getEtcuCreatedby() { return etcuCreatedby; }
    public void setEtcuCreatedby(String etcuCreatedby) { this.etcuCreatedby = etcuCreatedby; }

    public Character getEtcuActive() { return etcuActive; }
    public void setEtcuActive(Character etcuActive) { this.etcuActive = etcuActive; }

    public LocalDateTime getEtcuCreatedon() { return etcuCreatedon; }
    public void setEtcuCreatedon(LocalDateTime etcuCreatedon) { this.etcuCreatedon = etcuCreatedon; }

    public LocalDateTime getEtcuModifiedon() { return etcuModifiedon; }
    public void setEtcuModifiedon(LocalDateTime etcuModifiedon) { this.etcuModifiedon = etcuModifiedon; }
}
