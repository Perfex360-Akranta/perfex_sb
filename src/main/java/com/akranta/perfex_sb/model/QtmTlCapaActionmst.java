package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "qtm_tl_capactionmst", schema = "public")
public class QtmTlCapaActionmst {

    @Id
    @Column(name = "qcpa_keyid", length = 14, nullable = false)
    private String keyid;

    @Column(name = "qcpa_qcap_keyid", length = 14)
    private String masterid;

    @Column(name = "qcpa_correctiveaction", length = 500)
    private String correctiveaction;

    @Column(name = "qcpa_rootcause", length = 500)
    private String rootcause;

    @Column(name = "qcpa_preventiveaction", length = 500)
    private String preventiveaction;

    @Column(name = "qcpa_4mcondition", length = 10)
    private String fourmcondition;

    @Column(name = "qcpa_defecttype", length = 1)
    private String defecttype;

    @Column(name = "qcpa_responsibleid", length = 12)
    private String responsibleid;

    @Column(name = "qcpa_targetdate")
    private LocalDateTime targetdate;

    @Column(name = "qcpa_status", length = 1)
    private String status;

    @Column(name = "qcpa_completedon")
    private LocalDate completedon;

    @Column(name = "qcpa_actionplanid", length = 12)
    private String actionplanid;

    @Column(name = "qcpa_whywhyid", length = 12)
    private String whywhwyid;

    @Column(name = "qcpa_suggestionid", length = 12)
    private String suggestionid;

    @Column(name = "qcpa_tempfield1", length = 1)
    private String tempfield1;

    @Column(name = "qcpa_tempfield2", length = 1)
    private String tempfield2;

    @Column(name = "qcpa_tempfield3", length = 1)
    private String tempfield3;

    @Column(name = "qcpa_tempfield4", length = 1)
    private String tempfield4;

    @Column(name = "qcpa_tempfield5", length = 1)
    private String tempfield5;

    @Column(name = "qcpa_tempfield6", length = 1)
    private String tempfield6;

    @Column(name = "qcpa_tempfield7", length = 1)
    private String tempfield7;

    @Column(name = "qcpa_tempfield8", length = 1)
    private String tempfield8;

    @Column(name = "qcpa_tempfield9", length = 1)
    private String tempfield9;

    @Column(name = "qcpa_tempfield10", length = 1)
    private String tempfield10;

    @Column(name = "qcpa_active", length = 1)
    private String active;

    @Column(name = "qcpa_createdby", length = 12)
    private String createdby;

    @Column(name = "qcpa_createdon")
    private LocalDateTime createdon;

    @Column(name = "qcpa_modifiedon")
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getMasterid() {
        return masterid;
    }

    public void setMasterid(String masterid) {
        this.masterid = masterid;
    }

    public String getCorrectiveaction() {
        return correctiveaction;
    }

    public void setCorrectiveaction(String correctiveaction) {
        this.correctiveaction = correctiveaction;
    }

    public String getRootcause() {
        return rootcause;
    }

    public void setRootcause(String rootcause) {
        this.rootcause = rootcause;
    }

    public String getPreventiveaction() {
        return preventiveaction;
    }

    public void setPreventiveaction(String preventiveaction) {
        this.preventiveaction = preventiveaction;
    }

    public String getFourmcondition() {
        return fourmcondition;
    }

    public void setFourmcondition(String fourmcondition) {
        this.fourmcondition = fourmcondition;
    }

    public String getDefecttype() {
        return defecttype;
    }

    public void setDefecttype(String defecttype) {
        this.defecttype = defecttype;
    }

    public String getResponsibleid() {
        return responsibleid;
    }

    public void setResponsibleid(String responsibleid) {
        this.responsibleid = responsibleid;
    }

    public LocalDateTime getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(LocalDateTime targetdate) {
        this.targetdate = targetdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCompletedon() {
        return completedon;
    }

    public void setCompletedon(LocalDate completedon) {
        this.completedon = completedon;
    }

    public String getActionplanid() {
        return actionplanid;
    }

    public void setActionplanid(String actionplanid) {
        this.actionplanid = actionplanid;
    }

    public String getWhywhwyid() {
        return whywhwyid;
    }

    public void setWhywhwyid(String whywhwyid) {
        this.whywhwyid = whywhwyid;
    }

    public String getSuggestionid() {
        return suggestionid;
    }

    public void setSuggestionid(String suggestionid) {
        this.suggestionid = suggestionid;
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

    public String getTempfield6() {
        return tempfield6;
    }

    public void setTempfield6(String tempfield6) {
        this.tempfield6 = tempfield6;
    }

    public String getTempfield7() {
        return tempfield7;
    }

    public void setTempfield7(String tempfield7) {
        this.tempfield7 = tempfield7;
    }

    public String getTempfield8() {
        return tempfield8;
    }

    public void setTempfield8(String tempfield8) {
        this.tempfield8 = tempfield8;
    }

    public String getTempfield9() {
        return tempfield9;
    }

    public void setTempfield9(String tempfield9) {
        this.tempfield9 = tempfield9;
    }

    public String getTempfield10() {
        return tempfield10;
    }

    public void setTempfield10(String tempfield10) {
        this.tempfield10 = tempfield10;
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

}
