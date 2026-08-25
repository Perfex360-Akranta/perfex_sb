package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@JsonIgnoreProperties(ignoreUnknown = true)
public class EntTlTragcalmstDto {

    @JsonAlias({"txtEtcmKeyid","traCalId","calendarId","keyid","trgCalId","etcm_keyid"})
    private String etcmKeyid;
    @JsonAlias({"cmbEtcmFlid"})
    private String etcmFlid;
    @JsonAlias({"cmbEtcmLocation"})
    private String etcmLocation;
    @JsonAlias({"cmbEtcmDmt"})
    private String etcmDmt;
    @JsonAlias({"cmbEtcmJh"})
    private String etcmJh;
    @JsonAlias({"cmbEtcmTopicid"})
    private String etcmTopicid;
    private LocalDateTime etcmCreatedatetime;
    @JsonAlias({"dteEtcmCreatedDateTime","etcmCreatedDateTime"})
    private String etcmCreatedatetimeText;
    private String etcmRemarks;

    private LocalDateTime etcmCaldate;
    @JsonAlias({"chkEtcmGeneral","cmbEtcmGeneral","etcmGeneral","etcm_general"})
    private String etcmGeneral;
    @JsonAlias({"cmbEtcmUniquepos","cmbEtcmUniquePos","etcmUniqueposition","etcmUniquepos","etcm_uniquepos"})
    private String etcmUniquepos;
    @JsonAlias({"etcmMSD","cmbEtcmMSD","etcmMsd","etcm_msd"})
    private String etcmMsd;
    @JsonAlias({"etcmChkCompleted","chkEtcmChkCompleted","cmbEtcmChkCompleted"})
    private String etcmChkcompleted;
    @JsonAlias({"etcmCompletedDate","dteEtcmCompletedDate"})
 
    private LocalDateTime etcmCompleteddate;
    @JsonAlias({"etcmCompletedBy","cmbEtcmCompletedBy"})
    private String etcmCompletedby;
    @JsonAlias({"txtEtcmMaxDuration","etcmMaxDuration"})
    private Double etcmMaxDuration;
    @JsonAlias({"cmbEtcmFunction"})
    private String etcmFunction;
    @JsonAlias({"cmbEtcmVenue"})
    private String etcmVenue;
    @JsonAlias({"etcmPermittedStrength","txtEtcmPermittedStrength"})
    private Integer etcmPermittedstrength;
    @JsonAlias({"etcmMaterialsReady","cmbEtcmMaterialsReady"})
    private String etcmMaterialready;
    @JsonAlias({"etcmAssessmentReq","cmbEtcmAssessmentReq"})
    private String etcmAssessmentrequired;
    @JsonAlias({"etcmMarksBased","cmbEtcmMarksBased"})
    private String etcmMarkbased;
    @JsonAlias({"etcmFileManagedId","cmbEtcmFileManagedId"})
    private String etcmFilemgnid;
    @JsonAlias({"cmbEtcmAnchoredby"})
    private String etcmAnchoredby;
    @JsonAlias({"cmbEtcmTrainingfunction"})
    private String etcmTrainingfunction;
    @JsonAlias({"cmbEtcmRating"})
    private String etcmRating;
    @JsonAlias({"txtEtcmComments"})
    private String etcmComments;
    @JsonAlias({"cmbEtcmTopiccategory"})
    private String etcmTopiccategory;
    @JsonAlias({"cmbEtcmTempfield6"})
    private String etcmTempfield6;
    private String etcmTempfield7;
    private String etcmTempfield8;
    private String etcmTempfield9;
    private String etcmTempfield10;
    @JsonAlias({"chkEtcmActive"})
    private String etcmActive;
    @JsonAlias({"etcmCreatedBy","cmbEtcmCreatedBy"})
    private String etcmCreatedby;
    @JsonAlias({"etcmCreatedOn"})
    private LocalDateTime etcmCreatedon;
    @JsonAlias({"etcmModifiedOn"})
    private LocalDateTime etcmModifiedon;

    // raw strings (JSP sends custom formats)
    @JsonAlias({"etcmCalendarDate","dteEtcmCalendarDate","cmbEtcmCalendarDate"})
    private String etcmCalendarDateText;

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

    public String getEtcmCreatedatetimeText() { return etcmCreatedatetimeText; }
    public void setEtcmCreatedatetimeText(String etcmCreatedatetimeText) { this.etcmCreatedatetimeText = etcmCreatedatetimeText; }

    public String getEtcmRemarks() { return etcmRemarks; }
    public void setEtcmRemarks(String etcmRemarks) { this.etcmRemarks = etcmRemarks; }

    public LocalDateTime getEtcmCaldate() { return etcmCaldate; }
    public void setEtcmCaldate(LocalDateTime etcmCaldate) { this.etcmCaldate = etcmCaldate; }

    public String getEtcmGeneral() { return etcmGeneral; }
    public void setEtcmGeneral(String etcmGeneral) { this.etcmGeneral = etcmGeneral; }

    public String getEtcmUniquepos() { return etcmUniquepos; }
    public void setEtcmUniquepos(String etcmUniquepos) { this.etcmUniquepos = etcmUniquepos; }

    public String getEtcmMsd() { return etcmMsd; }
    public void setEtcmMsd(String etcmMsd) { this.etcmMsd = etcmMsd; }

    public String getEtcmChkcompleted() { return etcmChkcompleted; }
    public void setEtcmChkcompleted(String etcmChkcompleted) { this.etcmChkcompleted = etcmChkcompleted; }

    public LocalDateTime getEtcmCompleteddate() { return etcmCompleteddate; }
    public void setEtcmCompleteddate(LocalDateTime etcmCompleteddate) { this.etcmCompleteddate = etcmCompleteddate; }

    public String getEtcmCompletedby() { return etcmCompletedby; }
    public void setEtcmCompletedby(String etcmCompletedby) { this.etcmCompletedby = etcmCompletedby; }

    public Double getEtcmMaxDuration() { return etcmMaxDuration; }
    public void setEtcmMaxDuration(Double etcmMaxDuration) { this.etcmMaxDuration = etcmMaxDuration; }

    public String getEtcmFunction() { return etcmFunction; }
    public void setEtcmFunction(String etcmFunction) { this.etcmFunction = etcmFunction; }

    public String getEtcmVenue() { return etcmVenue; }
    public void setEtcmVenue(String etcmVenue) { this.etcmVenue = etcmVenue; }

    public Integer getEtcmPermittedstrength() { return etcmPermittedstrength; }
    public void setEtcmPermittedstrength(Integer etcmPermittedstrength) { this.etcmPermittedstrength = etcmPermittedstrength; }

    public String getEtcmMaterialready() { return etcmMaterialready; }
    public void setEtcmMaterialready(String etcmMaterialready) { this.etcmMaterialready = etcmMaterialready; }

    public String getEtcmAssessmentrequired() { return etcmAssessmentrequired; }
    public void setEtcmAssessmentrequired(String etcmAssessmentrequired) { this.etcmAssessmentrequired = etcmAssessmentrequired; }

    public String getEtcmMarkbased() { return etcmMarkbased; }
    public void setEtcmMarkbased(String etcmMarkbased) { this.etcmMarkbased = etcmMarkbased; }

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

    public String getEtcmActive() { return etcmActive; }
    public void setEtcmActive(String etcmActive) { this.etcmActive = etcmActive; }

    public String getEtcmCreatedby() { return etcmCreatedby; }
    public void setEtcmCreatedby(String etcmCreatedby) { this.etcmCreatedby = etcmCreatedby; }

    public LocalDateTime getEtcmCreatedon() { return etcmCreatedon; }
    public void setEtcmCreatedon(LocalDateTime etcmCreatedon) { this.etcmCreatedon = etcmCreatedon; }

    public LocalDateTime getEtcmModifiedon() { return etcmModifiedon; }
    public void setEtcmModifiedon(LocalDateTime etcmModifiedon) { this.etcmModifiedon = etcmModifiedon; }

    public String getEtcmCalendarDateText() { return etcmCalendarDateText; }
    public void setEtcmCalendarDateText(String etcmCalendarDateText) { this.etcmCalendarDateText = etcmCalendarDateText; }
}
