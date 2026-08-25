package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ent_tl_trgcalemp", schema = "public")
public class EntTlTrgCalEmp {

    @Id
    @Column(name = "etce_keyid", length = 15, nullable = false)
    private String etceKeyid;

    @Column(name = "etce_etcm_keyid", length = 15, nullable = false)
    private String etceEtcmKeyid;

    @Column(name = "etce_etcs_keyid", length = 15, nullable = false)
    private String etceEtcsKeyid;

    @Column(name = "etce_empm_keyid", length = 8, nullable = false)
    private String etceEmpmKeyid;

    @Column(name = "etce_dateadd", nullable = false)
    private LocalDateTime etceDateadd;

    @Column(name = "etce_role_keyid", length = 8, nullable = false)
    private String etceRoleKeyid;

    @Column(name = "etce_roledmt", length = 12, nullable = false)
    private String etceRoleDmt;

    @Column(name = "etce_rolejh", length = 12, nullable = false)
    private String etceRoleJh;

    @Column(name = "etce_tempfield1", length = 1, nullable = false)
    private String etceTempfield1;

    @Column(name = "etce_tempfield2", length = 1, nullable = false)
    private String etceTempfield2;

    @Column(name = "etce_tempfield3", length = 1, nullable = false)
    private String etceTempfield3;

    @Column(name = "etce_tempfield4", length = 1, nullable = false)
    private String etceTempfield4;

    @Column(name = "etce_tempfield5", length = 1, nullable = false)
    private String etceTempfield5;

    @Column(name = "etce_createdby", length = 8, nullable = false)
    private String etceCreatedby;
 
    @Column(name = "etce_active", columnDefinition = "char(1)", nullable = false)
    private Character etceActive;

    @Column(name = "etce_createdon", nullable = false)
    private LocalDateTime etceCreatedon;

    @Column(name = "etce_modifiedon", nullable = false)
    private LocalDateTime etceModifiedon;

    public String getEtceKeyid() { return etceKeyid; }
    public void setEtceKeyid(String etceKeyid) { this.etceKeyid = etceKeyid; }

    public String getEtceEtcmKeyid() { return etceEtcmKeyid; }
    public void setEtceEtcmKeyid(String etceEtcmKeyid) { this.etceEtcmKeyid = etceEtcmKeyid; }

    public String getEtceEtcsKeyid() { return etceEtcsKeyid; }
    public void setEtceEtcsKeyid(String etceEtcsKeyid) { this.etceEtcsKeyid = etceEtcsKeyid; }

    public String getEtceEmpmKeyid() { return etceEmpmKeyid; }
    public void setEtceEmpmKeyid(String etceEmpmKeyid) { this.etceEmpmKeyid = etceEmpmKeyid; }

    public LocalDateTime getEtceDateadd() { return etceDateadd; }
    public void setEtceDateadd(LocalDateTime etceDateadd) { this.etceDateadd = etceDateadd; }

    public String getEtceRoleKeyid() { return etceRoleKeyid; }
    public void setEtceRoleKeyid(String etceRoleKeyid) { this.etceRoleKeyid = etceRoleKeyid; }

    public String getEtceRoleDmt() { return etceRoleDmt; }
    public void setEtceRoleDmt(String etceRoleDmt) { this.etceRoleDmt = etceRoleDmt; }

    public String getEtceRoleJh() { return etceRoleJh; }
    public void setEtceRoleJh(String etceRoleJh) { this.etceRoleJh = etceRoleJh; }

    public String getEtceTempfield1() { return etceTempfield1; }
    public void setEtceTempfield1(String etceTempfield1) { this.etceTempfield1 = etceTempfield1; }

    public String getEtceTempfield2() { return etceTempfield2; }
    public void setEtceTempfield2(String etceTempfield2) { this.etceTempfield2 = etceTempfield2; }

    public String getEtceTempfield3() { return etceTempfield3; }
    public void setEtceTempfield3(String etceTempfield3) { this.etceTempfield3 = etceTempfield3; }

    public String getEtceTempfield4() { return etceTempfield4; }
    public void setEtceTempfield4(String etceTempfield4) { this.etceTempfield4 = etceTempfield4; }

    public String getEtceTempfield5() { return etceTempfield5; }
    public void setEtceTempfield5(String etceTempfield5) { this.etceTempfield5 = etceTempfield5; }

    public String getEtceCreatedby() { return etceCreatedby; }
    public void setEtceCreatedby(String etceCreatedby) { this.etceCreatedby = etceCreatedby; }

    public Character getEtceActive() { return etceActive; }
    public void setEtceActive(Character etceActive) { this.etceActive = etceActive; }

    public LocalDateTime getEtceCreatedon() { return etceCreatedon; }
    public void setEtceCreatedon(LocalDateTime etceCreatedon) { this.etceCreatedon = etceCreatedon; }

    public LocalDateTime getEtceModifiedon() { return etceModifiedon; }
    public void setEtceModifiedon(LocalDateTime etceModifiedon) { this.etceModifiedon = etceModifiedon; }
}
