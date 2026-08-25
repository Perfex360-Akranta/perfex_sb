package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ent_tl_trgcalempatscore", schema = "public")
public class EntTlTtgCalEmpatScore {

    @Id
    @Column(name = "etca_keyid", length = 15, nullable = false)
    private String etcaKeyid;

    @Column(name = "etca_etcm_keyid", length = 15, nullable = false)
    private String etcaEtcmKeyid;

    @Column(name = "etca_etce_empm_keyid", length = 15, nullable = false)
    private String etcaEtceEmpmKeyid;

    @Column(name = "etca_etce_keyid", length = 15, nullable = false)
    private String etcaEtceKeyid;

    @Column(name = "etca_assessmentcom", columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcaAssessmentCom;

    @Column(name = "etca_maxmarks", nullable = false)
    private BigDecimal etcaMaxMarks;

    @Column(name = "etca_cutoff", nullable = false)
    private BigDecimal etcaCutOff;

    @Column(name = "etca_type",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcaType;

    @Column(name = "etca_dept", length = 20, nullable = false)
    private String etcaDept;

    @Column(name = "etca_prsentabsent",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcaPrsentAbsent;

    @Column(name = "etca_attdate", nullable = false)
    private LocalDateTime etcaAttDate;

    @Column(name = "etca_score", nullable = false)
    private BigDecimal etcaScore;

    @Column(name = "etca_result",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcaResult;

    @Column(name = "etca_remarks", length = 500, nullable = false)
    private String etcaRemarks;

    @Column(name = "etca_scoredate", nullable = false)
    private LocalDateTime etcaScoreDate;

    @Column(name = "etca_filemgnid", length = 10, nullable = false)
    private String etcaFilemgnid;

    @Column(name = "etca_tempfield1", length = 1, nullable = false)
    private String etcaTempfield1;

    @Column(name = "etca_tempfield2", length = 1, nullable = false)
    private String etcaTempfield2;

    @Column(name = "etca_tempfield3", length = 1, nullable = false)
    private String etcaTempfield3;

    @Column(name = "etca_tempfield4", length = 1, nullable = false)
    private String etcaTempfield4;

    @Column(name = "etca_tempfield5", length = 1, nullable = false)
    private String etcaTempfield5;

    @Column(name = "etca_createdby", length = 8, nullable = false)
    private String etcaCreatedby;

    @Column(name = "etca_active", columnDefinition = "char(1)",length = 1, nullable = false)
    private Character etcaActive;

    @Column(name = "etca_createdon", nullable = false)
    private LocalDateTime etcaCreatedon;

    @Column(name = "etca_modifiedon", nullable = false)
    private LocalDateTime etcaModifiedon;

    public String getEtcaKeyid() { return etcaKeyid; }
    public void setEtcaKeyid(String etcaKeyid) { this.etcaKeyid = etcaKeyid; }

    public String getEtcaEtcmKeyid() { return etcaEtcmKeyid; }
    public void setEtcaEtcmKeyid(String etcaEtcmKeyid) { this.etcaEtcmKeyid = etcaEtcmKeyid; }

    public String getEtcaEtceEmpmKeyid() { return etcaEtceEmpmKeyid; }
    public void setEtcaEtceEmpmKeyid(String etcaEtceEmpmKeyid) { this.etcaEtceEmpmKeyid = etcaEtceEmpmKeyid; }

    public String getEtcaEtceKeyid() { return etcaEtceKeyid; }
    public void setEtcaEtceKeyid(String etcaEtceKeyid) { this.etcaEtceKeyid = etcaEtceKeyid; }

    public Character getEtcaAssessmentCom() { return etcaAssessmentCom; }
    public void setEtcaAssessmentCom(Character etcaAssessmentCom) { this.etcaAssessmentCom = etcaAssessmentCom; }

    public BigDecimal getEtcaMaxMarks() { return etcaMaxMarks; }
    public void setEtcaMaxMarks(BigDecimal etcaMaxMarks) { this.etcaMaxMarks = etcaMaxMarks; }

    public BigDecimal getEtcaCutOff() { return etcaCutOff; }
    public void setEtcaCutOff(BigDecimal etcaCutOff) { this.etcaCutOff = etcaCutOff; }

    public Character getEtcaType() { return etcaType; }
    public void setEtcaType(Character etcaType) { this.etcaType = etcaType; }

    public String getEtcaDept() { return etcaDept; }
    public void setEtcaDept(String etcaDept) { this.etcaDept = etcaDept; }

    public Character getEtcaPrsentAbsent() { return etcaPrsentAbsent; }
    public void setEtcaPrsentAbsent(Character etcaPrsentAbsent) { this.etcaPrsentAbsent = etcaPrsentAbsent; }

    public LocalDateTime getEtcaAttDate() { return etcaAttDate; }
    public void setEtcaAttDate(LocalDateTime etcaAttDate) { this.etcaAttDate = etcaAttDate; }

    public BigDecimal getEtcaScore() { return etcaScore; }
    public void setEtcaScore(BigDecimal etcaScore) { this.etcaScore = etcaScore; }

    public Character getEtcaResult() { return etcaResult; }
    public void setEtcaResult(Character etcaResult) { this.etcaResult = etcaResult; }

    public String getEtcaRemarks() { return etcaRemarks; }
    public void setEtcaRemarks(String etcaRemarks) { this.etcaRemarks = etcaRemarks; }

    public LocalDateTime getEtcaScoreDate() { return etcaScoreDate; }
    public void setEtcaScoreDate(LocalDateTime etcaScoreDate) { this.etcaScoreDate = etcaScoreDate; }

    public String getEtcaFilemgnid() { return etcaFilemgnid; }
    public void setEtcaFilemgnid(String etcaFilemgnid) { this.etcaFilemgnid = etcaFilemgnid; }

    public String getEtcaTempfield1() { return etcaTempfield1; }
    public void setEtcaTempfield1(String etcaTempfield1) { this.etcaTempfield1 = etcaTempfield1; }

    public String getEtcaTempfield2() { return etcaTempfield2; }
    public void setEtcaTempfield2(String etcaTempfield2) { this.etcaTempfield2 = etcaTempfield2; }

    public String getEtcaTempfield3() { return etcaTempfield3; }
    public void setEtcaTempfield3(String etcaTempfield3) { this.etcaTempfield3 = etcaTempfield3; }

    public String getEtcaTempfield4() { return etcaTempfield4; }
    public void setEtcaTempfield4(String etcaTempfield4) { this.etcaTempfield4 = etcaTempfield4; }

    public String getEtcaTempfield5() { return etcaTempfield5; }
    public void setEtcaTempfield5(String etcaTempfield5) { this.etcaTempfield5 = etcaTempfield5; }

    public String getEtcaCreatedby() { return etcaCreatedby; }
    public void setEtcaCreatedby(String etcaCreatedby) { this.etcaCreatedby = etcaCreatedby; }

    public Character getEtcaActive() { return etcaActive; }
    public void setEtcaActive(Character etcaActive) { this.etcaActive = etcaActive; }

    public LocalDateTime getEtcaCreatedon() { return etcaCreatedon; }
    public void setEtcaCreatedon(LocalDateTime etcaCreatedon) { this.etcaCreatedon = etcaCreatedon; }

    public LocalDateTime getEtcaModifiedon() { return etcaModifiedon; }
    public void setEtcaModifiedon(LocalDateTime etcaModifiedon) { this.etcaModifiedon = etcaModifiedon; }
}
