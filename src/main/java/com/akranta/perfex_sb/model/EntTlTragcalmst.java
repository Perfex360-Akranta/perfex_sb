package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ent_tl_trgcalmst", schema = "public")
public class EntTlTragcalmst {

    @Id
    @Column(name = "etcm_keyid", length = 15, nullable = false)
    private String etcmKeyid;

    @Column(name = "etcm_flid", length = 12, nullable = false)
    private String etcmFlid;

    @Column(name = "etcm_location", length = 12, nullable = false)
    private String etcmLocation;

    @Column(name = "etcm_dmt", length = 12, nullable = false)
    private String etcmDmt;

    @Column(name = "etcm_jh", length = 12, nullable = false)
    private String etcmJh;

    @Column(name = "etcm_topicid", length = 12, nullable = false)
    private String etcmTopicid;

    @Column(name = "etcm_createdatetime", nullable = false)
    private LocalDateTime etcmCreatedatetime;

    @Column(name = "etcm_remarks", length = 500, nullable = false)
    private String etcmRemarks;

    @Column(name = "etcm_caldate", nullable = false)
    private LocalDateTime etcmCaldate;

    @Column(name = "etcm_general",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcmGeneral;

    @Column(name = "etcm_uniquepos",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcmUniquepos;

    @Column(name = "etcm_msd",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcmMsd;

    @Column(name = "etcm_chkcompleted",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcmChkcompleted;

    @Column(name = "etcm_completeddate", nullable = false)
    private LocalDateTime etcmCompleteddate;

    @Column(name = "etcm_completedby", length = 8, nullable = false)
    private String etcmCompletedby;

    @Column(name = "etcm_max_duration", nullable = false)
    private BigDecimal etcmMaxDuration;

    @Column(name = "etcm_function", length = 20, nullable = false)
    private String etcmFunction;

    @Column(name = "etcm_venue", length = 8, nullable = false)
    private String etcmVenue;

    @Column(name = "etcm_permittedstrength", nullable = false)
    private BigDecimal etcmPermittedstrength;

    @Column(name = "etcm_materialready",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcmMaterialready;

    @Column(name = "etcm_assessmentrequired",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcmAssessmentrequired;

    @Column(name = "etcm_markbased",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcmMarkbased;

    @Column(name = "etcm_filemgnid", length = 10, nullable = false)
    private String etcmFilemgnid;

    @Column(name = "etcm_anchoredby", length = 12, nullable = false)
    private String etcmAnchoredby;

    @Column(name = "etcm_trainingfunction", length = 15, nullable = false)
    private String etcmTrainingfunction;

    @Column(name = "etcm_rating", length = 1)
    private String etcmRating;

    @Column(name = "etcm_comments", length = 400, nullable = false)
    private String etcmComments;

    @Column(name = "etcm_topiccategory", length = 14, nullable = false)
    private String etcmTopiccategory;

    @Column(name = "etcm_tempfield6", length = 3, nullable = false)
    private String etcmTempfield6;

    @Column(name = "etcm_tempfield7", length = 1, nullable = false)
    private String etcmTempfield7;

    @Column(name = "etcm_tempfield8", length = 1, nullable = false)
    private String etcmTempfield8;

    @Column(name = "etcm_tempfield9", length = 1, nullable = false)
    private String etcmTempfield9;

    @Column(name = "etcm_tempfield10", length = 1, nullable = false)
    private String etcmTempfield10;

    @Column(name = "etcm_active",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character etcmActive;

    @Column(name = "etcm_createdby", length = 8, nullable = false)
    private String etcmCreatedby;

    @Column(name = "etcm_createdon", nullable = false)
    private LocalDateTime etcmCreatedon;

    @Column(name = "etcm_modifiedon", nullable = false)
    private LocalDateTime etcmModifiedon;

    public String getEtcmKeyid() { return etcmKeyid; }
    public void setEtcmKeyid(String etcmKeyid) { this.etcmKeyid = etcmKeyid; }

    public String getEtcmFlid() { return etcmFlid; }
    public void setEtcmFlid(String etcmFlid) { this.etcmFlid = etcmFlid; }

    public String getEtcmLocation() { return etcmLocation; }
    public void setEtcmLocation(String etcmLocation) { this.etcmLocation = etcmLocation; }

    public String getEtcmDmt() { return etcmDmt; }
    public void setEtcmDmt(String etcmDmt) { this.etcmDmt = etcmDmt; }

    public String getEtcmJh() { return etcmJh; }
    public void setEtcmJh(String etcmJh) { this.etcmJh = etcmJh; }

    public String getEtcmTopicid() { return etcmTopicid; }
    public void setEtcmTopicid(String etcmTopicid) { this.etcmTopicid = etcmTopicid; }

    public LocalDateTime getEtcmCreatedatetime() { return etcmCreatedatetime; }
    public void setEtcmCreatedatetime(LocalDateTime etcmCreatedatetime) { this.etcmCreatedatetime = etcmCreatedatetime; }

    public String getEtcmRemarks() { return etcmRemarks; }
    public void setEtcmRemarks(String etcmRemarks) { this.etcmRemarks = etcmRemarks; }

    public LocalDateTime getEtcmCaldate() { return etcmCaldate; }
    public void setEtcmCaldate(LocalDateTime etcmCaldate) { this.etcmCaldate = etcmCaldate; }

    public Character getEtcmGeneral() { return etcmGeneral; }
    public void setEtcmGeneral(Character etcmGeneral) { this.etcmGeneral = etcmGeneral; }

    public Character getEtcmUniquepos() { return etcmUniquepos; }
    public void setEtcmUniquepos(Character etcmUniquepos) { this.etcmUniquepos = etcmUniquepos; }

    public Character getEtcmMsd() { return etcmMsd; }
    public void setEtcmMsd(Character etcmMsd) { this.etcmMsd = etcmMsd; }

    public Character getEtcmChkcompleted() { return etcmChkcompleted; }
    public void setEtcmChkcompleted(Character etcmChkcompleted) { this.etcmChkcompleted = etcmChkcompleted; }

    public LocalDateTime getEtcmCompleteddate() { return etcmCompleteddate; }
    public void setEtcmCompleteddate(LocalDateTime etcmCompleteddate) { this.etcmCompleteddate = etcmCompleteddate; }

    public String getEtcmCompletedby() { return etcmCompletedby; }
    public void setEtcmCompletedby(String etcmCompletedby) { this.etcmCompletedby = etcmCompletedby; }

    public BigDecimal getEtcmMaxDuration() { return etcmMaxDuration; }
    public void setEtcmMaxDuration(BigDecimal etcmMaxDuration) { this.etcmMaxDuration = etcmMaxDuration; }

    public String getEtcmFunction() { return etcmFunction; }
    public void setEtcmFunction(String etcmFunction) { this.etcmFunction = etcmFunction; }

    public String getEtcmVenue() { return etcmVenue; }
    public void setEtcmVenue(String etcmVenue) { this.etcmVenue = etcmVenue; }

    public BigDecimal getEtcmPermittedstrength() { return etcmPermittedstrength; }
    public void setEtcmPermittedstrength(BigDecimal etcmPermittedstrength) { this.etcmPermittedstrength = etcmPermittedstrength; }

    public Character getEtcmMaterialready() { return etcmMaterialready; }
    public void setEtcmMaterialready(Character etcmMaterialready) { this.etcmMaterialready = etcmMaterialready; }

    public Character getEtcmAssessmentrequired() { return etcmAssessmentrequired; }
    public void setEtcmAssessmentrequired(Character etcmAssessmentrequired) { this.etcmAssessmentrequired = etcmAssessmentrequired; }

    public Character getEtcmMarkbased() { return etcmMarkbased; }
    public void setEtcmMarkbased(Character etcmMarkbased) { this.etcmMarkbased = etcmMarkbased; }

    public String getEtcmFilemgnid() { return etcmFilemgnid; }
    public void setEtcmFilemgnid(String etcmFilemgnid) { this.etcmFilemgnid = etcmFilemgnid; }

    public String getEtcmAnchoredby() { return etcmAnchoredby; }
    public void setEtcmAnchoredby(String etcmAnchoredby) { this.etcmAnchoredby = etcmAnchoredby; }

    public String getEtcmTrainingfunction() { return etcmTrainingfunction; }
    public void setEtcmTrainingfunction(String etcmTrainingfunction) { this.etcmTrainingfunction = etcmTrainingfunction; }

    public String getEtcmRating() { return etcmRating; }
    public void setEtcmRating(String etcmRating) { this.etcmRating = etcmRating; }

    public String getEtcmComments() { return etcmComments; }
    public void setEtcmComments(String etcmComments) { this.etcmComments = etcmComments; }

    public String getEtcmTopiccategory() { return etcmTopiccategory; }
    public void setEtcmTopiccategory(String etcmTopiccategory) { this.etcmTopiccategory = etcmTopiccategory; }

    public String getEtcmTempfield6() { return etcmTempfield6; }
    public void setEtcmTempfield6(String etcmTempfield6) { this.etcmTempfield6 = etcmTempfield6; }

    public String getEtcmTempfield7() { return etcmTempfield7; }
    public void setEtcmTempfield7(String etcmTempfield7) { this.etcmTempfield7 = etcmTempfield7; }

    public String getEtcmTempfield8() { return etcmTempfield8; }
    public void setEtcmTempfield8(String etcmTempfield8) { this.etcmTempfield8 = etcmTempfield8; }

    public String getEtcmTempfield9() { return etcmTempfield9; }
    public void setEtcmTempfield9(String etcmTempfield9) { this.etcmTempfield9 = etcmTempfield9; }

    public String getEtcmTempfield10() { return etcmTempfield10; }
    public void setEtcmTempfield10(String etcmTempfield10) { this.etcmTempfield10 = etcmTempfield10; }

    public Character getEtcmActive() { return etcmActive; }
    public void setEtcmActive(Character etcmActive) { this.etcmActive = etcmActive; }

    public String getEtcmCreatedby() { return etcmCreatedby; }
    public void setEtcmCreatedby(String etcmCreatedby) { this.etcmCreatedby = etcmCreatedby; }

    public LocalDateTime getEtcmCreatedon() { return etcmCreatedon; }
    public void setEtcmCreatedon(LocalDateTime etcmCreatedon) { this.etcmCreatedon = etcmCreatedon; }

    public LocalDateTime getEtcmModifiedon() { return etcmModifiedon; }
    public void setEtcmModifiedon(LocalDateTime etcmModifiedon) { this.etcmModifiedon = etcmModifiedon; }
}
