package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "she_tl_plantsafetydtl", schema = "public")
public class SheTlPlantSafetydtl {

    @Id
    @Column(name = "spsd_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "spsd_spsm_keyid", length = 12)
    private String spsd_keyid;

    @Column(name = "spsd_observations", length = 500)
    private String observe;

    @Column(name = "spsd_type", length = 3)
    private String type;

    @Column(name = "spsd_relatedto", length = 3)
    private String relatedto;

    @Column(name = "spsd_reccomaction", length = 2000)
    private String recomaction;

    @Column(name = "spsd_correcaction", length = 2000)
    private String correcaction;

    @Column(name = "spsd_prevaction", length = 2000)
    private String preventiveaction;

    @Column(name = "spsd_responsibility", length = 12)
    private String responsibilty;

    @Column(name = "spsd_targetdate")
    private LocalDateTime targetdate;

    @Column(name = "spsd_respremarks", length = 1000)
    private String reponseremarks;

    @Column(name = "spsd_status", length = 1)
    private String status;

    @Column(name = "spsd_actiontaken", length = 2000)
    private String actiontaken;

    @Column(name = "spsd_completedby", length = 12)
    private String completedby;

    @Column(name = "spsd_completeddate")
    private LocalDateTime completedate;

    @Column(name = "spsd_completeremarks", length = 1000)
    private String completeremarks;

    @Column(name = "spsd_verified", length = 1)
    private String verified;

    @Column(name = "spsd_verifiedby", length = 12)
    private String verifiedby;

    @Column(name = "spsd_verifieddate")
    private LocalDateTime verifieddate;

    @Column(name = "spsd_verifyremarks", length = 1000)
    private String verifeidremarks;

    @Column(name = "spsd_tempfield1", length = 10)
    private String tempfield1;

    @Column(name = "spsd_tempfield2", length = 10)
    private String tempfield2;

    @Column(name = "spsd_tempfield3", length = 10)
    private String tempfield3;

    @Column(name = "spsd_tempfield4", length = 10)
    private String tempfield4;

    @Column(name = "spsd_tempfield5", length = 10)
    private String tempfield5;

    @Column(name = "spsd_active", length = 1)
    private String active;

    @Column(name = "spsd_createdby", length = 12)
    private String createdBy;

    @Column(name = "spsd_createdon")
    private LocalDateTime createdOn;

    @Column(name = "spsd_modifiedon")
    private LocalDateTime modifiedOn;

    @Column(name = "spsd_reviseddate")
    private LocalDateTime reviseddate;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getSpsd_keyid() {
        return spsd_keyid;
    }

    public void setSpsd_keyid(String spsd_keyid) {
        this.spsd_keyid = spsd_keyid;
    }

    public String getObserve() {
        return observe;
    }

    public void setObserve(String observe) {
        this.observe = observe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelatedto() {
        return relatedto;
    }

    public void setRelatedto(String relatedto) {
        this.relatedto = relatedto;
    }

    public String getRecomaction() {
        return recomaction;
    }

    public void setRecomaction(String recomaction) {
        this.recomaction = recomaction;
    }

    public String getCorrecaction() {
        return correcaction;
    }

    public void setCorrecaction(String correcaction) {
        this.correcaction = correcaction;
    }

    public String getPreventiveaction() {
        return preventiveaction;
    }

    public void setPreventiveaction(String preventiveaction) {
        this.preventiveaction = preventiveaction;
    }

    public String getResponsibilty() {
        return responsibilty;
    }

    public void setResponsibilty(String responsibilty) {
        this.responsibilty = responsibilty;
    }

    public LocalDateTime getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(LocalDateTime targetdate) {
        this.targetdate = targetdate;
    }

    public String getReponseremarks() {
        return reponseremarks;
    }

    public void setReponseremarks(String reponseremarks) {
        this.reponseremarks = reponseremarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActiontaken() {
        return actiontaken;
    }

    public void setActiontaken(String actiontaken) {
        this.actiontaken = actiontaken;
    }

    public String getCompletedby() {
        return completedby;
    }

    public void setCompletedby(String completedby) {
        this.completedby = completedby;
    }

    public LocalDateTime getCompletedate() {
        return completedate;
    }

    public void setCompletedate(LocalDateTime completedate) {
        this.completedate = completedate;
    }

    public String getCompleteremarks() {
        return completeremarks;
    }

    public void setCompleteremarks(String completeremarks) {
        this.completeremarks = completeremarks;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getVerifiedby() {
        return verifiedby;
    }

    public void setVerifiedby(String verifiedby) {
        this.verifiedby = verifiedby;
    }

    public LocalDateTime getVerifieddate() {
        return verifieddate;
    }

    public void setVerifieddate(LocalDateTime verifieddate) {
        this.verifieddate = verifieddate;
    }

    public String getVerifeidremarks() {
        return verifeidremarks;
    }

    public void setVerifeidremarks(String verifeidremarks) {
        this.verifeidremarks = verifeidremarks;
    }

    public String getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(String tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public String getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(String tempfield2) {
        this.tempfield2 = tempfield2;
    }

    public String getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(String tempfield3) {
        this.tempfield3 = tempfield3;
    }

    public String getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(String tempfield4) {
        this.tempfield4 = tempfield4;
    }

    public String getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(String tempfield5) {
        this.tempfield5 = tempfield5;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public LocalDateTime getReviseddate() {
        return reviseddate;
    }

    public void setReviseddate(LocalDateTime reviseddate) {
        this.reviseddate = reviseddate;
    }

   
    
}