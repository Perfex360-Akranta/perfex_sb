package com.akranta.perfex_sb.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "she_tl_incidentmstnew", schema = "public")
public class SheTlIncidentmstnew {

    @Id
    @Column(name = "sinm_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "sinm_elementid", length = 250)
    private String elementid;

    @Column(name = "sinm_flid", length = 12)
    private String flid;

    @Column(name = "sinm_locn_keyid", length = 10)
    private String locn_keyid;

    @Column(name = "sinm_fact_keyid", length = 10)
    private String fact_keyid;

    @Column(name = "sinm_sect_keyid", length = 10)
    private String sect_keyid;

    @Column(name = "sinm_cell_keyid", length = 10)
    private String cell_keyid;

    @Column(name = "sinm_mach_keyid", length = 10)
    private String mach_keyid;

    @Column(name = "sinm_department", length = 100)
    private String department;

    @Column(name = "sinm_division", length = 100)
    private String division;

    @Column(name = "sinm_area", length = 200)
    private String area;

    @Column(name = "sinm_accident_type", length = 20)
    private String accident_type;

    @Column(name = "sinm_accident_desc", length = 500)
    private String accident_desc;

    @Column(name = "sinm_immediate_action", length = 500)
    private String immediate_action;

    @Column(name = "sinm_rptrecommendations", length = 500)
    private String rptrecommendations;

    @Column(name = "sinm_lossproperty", length = 100)
    private String lossproperty;

    @Column(name = "sinm_directcost", length = 30)
    private String directcost;

    @Column(name = "sinm_noofemployees")
    private Integer noofemployees;

    @Column(name = "sinm_occurred_date")
    private LocalDateTime occurred_date;

    @Column(name = "sinm_occurred_shift", length = 6)
    private String occurred_shift;

    @Column(name = "sinm_reported_date")
    private LocalDateTime reported_date;

    @Column(name = "sinm_reported_shift", length = 6)
    private String reported_shift;

    @Column(name = "sinm_reported_by", length = 8)
    private String reported_by;

    @Column(name = "sinm_witness1", length = 500)
    private String witness1;

    @Column(name = "sinm_witness2", length = 500)
    private String witness2;

    @Column(name = "sinm_exact_desc", length = 500)
    private String exact_desc;

    @Column(name = "sinm_exact_type", length = 20)
    private String exact_type;

    @Column(name = "sinm_investigation", length = 500)
    private String investigation;

    @Column(name = "sinm_investrmrks", length = 500)
    private String invstremarks;

    @Column(name = "sinm_phenomena", length = 500)
    private String phenomena;

    @Column(name = "sinm_whywhyflag", length = 1)
    private String whywhyflag;

    @Column(name = "sinm_whywhyno", length = 30)
    private String whywhyno;

    @Column(name = "sinm_rootcause", length = 500)
    private String rootcause;

    @Column(name = "sinm_countermeasure", length = 500)
    private String countermeasure;

    @Column(name = "sinm_rootcausedueto", length = 1)
    private String rootcausedueto;

    @Column(name = "sinm_idea", length = 500)
    private String idea;

    @Column(name = "sinm_analysed_by", length = 8)
    private String analysed_by;

    @Column(name = "sinm_analysed_date")
    private LocalDateTime analysed_date;

    @Column(name = "sinm_analysed_shift", length = 30)
    private String analysed_shift;

    @Column(name = "sinm_invrptid", length = 20)
    private String invrptid;

    @Column(name = "sinm_invrptby", length = 8)
    private String invrptby;

    @Column(name = "sinm_investrptrmrks", length = 500)
    private String invrptremarks;

    @Column(name = "sinm_invrptdate")
    private LocalDateTime invrptdate;

    @Column(name = "sinm_carecommended", length = 500)
    private String carecommended;

    @Column(name = "sinm_responsibility", length = 8)
    private String responsibility;

    @Column(name = "sinm_targetdate")
    private LocalDateTime targetdate;

    @Column(name = "sinm_responsermrks", length = 500)
    private String responseremarks;

    @Column(name = "sinm_actionplanno", length = 12)
    private String actionplanno;

    @Column(name = "sinm_status", length = 1)
    private String status;

    @Column(name = "sinm_reviseddate")
    private LocalDateTime reviseddate;

    @Column(name = "sinm_actiontaken", length = 500)
    private String actiontaken;

    @Column(name = "sinm_completedby", length = 8)
    private String completedby;

    @Column(name = "sinm_datecompleted")
    private LocalDateTime datecompleted;

    @Column(name = "sinm_completermrks", length = 500)
    private String completeremarks;

    @Column(name = "sinm_verifiedstatus", length = 1)
    private String verifiedstatus;

    @Column(name = "sinm_verifiedby", length = 8)
    private String verifiedby;

    @Column(name = "sinm_dateverified")
    private LocalDateTime dateverified;

    @Column(name = "sinm_verifyrmrks", length = 500)
    private String verifyremarks;

    @Column(name = "sinm_approvalstatus", length = 1)
    private String approvalstatus;

    @Column(name = "sinm_approvalby", length = 8)
    private String approvalby;

    @Column(name = "sinm_approvaldate")
    private LocalDateTime approvaldate;

    @Column(name = "sinm_approvalrmrks", length = 500)
    private String approvalremarks;

    @Column(name = "sinm_tempfield1", length = 4)
    private String tempfield1;

    @Column(name = "sinm_tempfield2", length = 20)
    private String tempfield2;

    @Column(name = "sinm_tempfield3", length = 20)
    private String tempfield3;

    @Column(name = "sinm_tempfield4", length = 20)
    private String tempfield4;

    @Column(name = "sinm_tempfield5", length = 100)
    private String tempfield5;

    @Column(name = "sinm_tempfield6", length = 1)
    private String tempfield6;

    @Column(name = "sinm_tempfield7", length = 1)
    private String tempfield7;

    @Column(name = "sinm_active", length = 1)
    private String active;

    @Column(name = "sinm_createdby", length = 8)
    private String createdby;

    @Column(name = "sinm_modifiedby", length = 8)
    private String modifiedby;

    @CreationTimestamp
    @Column(name = "sinm_createdon")
    private LocalDateTime createdon;

    @UpdateTimestamp
    @Column(name = "sinm_modifiedon")
    private LocalDateTime modifiedon;

   

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getLocn_keyid() {
        return locn_keyid;
    }

    public void setLocn_keyid(String locn_keyid) {
        this.locn_keyid = locn_keyid;
    }

    public String getFact_keyid() {
        return fact_keyid;
    }

    public void setFact_keyid(String fact_keyid) {
        this.fact_keyid = fact_keyid;
    }

    public String getSect_keyid() {
        return sect_keyid;
    }

    public void setSect_keyid(String sect_keyid) {
        this.sect_keyid = sect_keyid;
    }

    public String getCell_keyid() {
        return cell_keyid;
    }

    public void setCell_keyid(String cell_keyid) {
        this.cell_keyid = cell_keyid;
    }

    public String getMach_keyid() {
        return mach_keyid;
    }

    public void setMach_keyid(String mach_keyid) {
        this.mach_keyid = mach_keyid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAccident_type() {
        return accident_type;
    }

    public void setAccident_type(String accident_type) {
        this.accident_type = accident_type;
    }

    public String getAccident_desc() {
        return accident_desc;
    }

    public void setAccident_desc(String accident_desc) {
        this.accident_desc = accident_desc;
    }

    public String getImmediate_action() {
        return immediate_action;
    }

    public void setImmediate_action(String immediate_action) {
        this.immediate_action = immediate_action;
    }

    public String getRptrecommendations() {
        return rptrecommendations;
    }

    public void setRptrecommendations(String rptrecommendations) {
        this.rptrecommendations = rptrecommendations;
    }

    public String getLossproperty() {
        return lossproperty;
    }

    public void setLossproperty(String lossproperty) {
        this.lossproperty = lossproperty;
    }

    public String getDirectcost() {
        return directcost;
    }

    public void setDirectcost(String directcost) {
        this.directcost = directcost;
    }

    public Integer getNoofemployees() {
        return noofemployees;
    }

    public void setNoofemployees(Integer noofemployees) {
        this.noofemployees = noofemployees;
    }

    public LocalDateTime getOccurred_date() {
        return occurred_date;
    }

    public void setOccurred_date(LocalDateTime occurred_date) {
        this.occurred_date = occurred_date;
    }

    public String getOccurred_shift() {
        return occurred_shift;
    }

    public void setOccurred_shift(String occurred_shift) {
        this.occurred_shift = occurred_shift;
    }

    public LocalDateTime getReported_date() {
        return reported_date;
    }

    public void setReported_date(LocalDateTime reported_date) {
        this.reported_date = reported_date;
    }

    public String getReported_shift() {
        return reported_shift;
    }

    public void setReported_shift(String reported_shift) {
        this.reported_shift = reported_shift;
    }

    public String getReported_by() {
        return reported_by;
    }

    public void setReported_by(String reported_by) {
        this.reported_by = reported_by;
    }

    public String getWitness1() {
        return witness1;
    }

    public void setWitness1(String witness1) {
        this.witness1 = witness1;
    }

    public String getWitness2() {
        return witness2;
    }

    public void setWitness2(String witness2) {
        this.witness2 = witness2;
    }

    public String getExact_desc() {
        return exact_desc;
    }

    public void setExact_desc(String exact_desc) {
        this.exact_desc = exact_desc;
    }

    public String getExact_type() {
        return exact_type;
    }

    public void setExact_type(String exact_type) {
        this.exact_type = exact_type;
    }

    public String getInvestigation() {
        return investigation;
    }

    public void setInvestigation(String investigation) {
        this.investigation = investigation;
    }

    public String getInvstremarks() {
        return invstremarks;
    }

    public void setInvstremarks(String invstremarks) {
        this.invstremarks = invstremarks;
    }

    public String getPhenomena() {
        return phenomena;
    }

    public void setPhenomena(String phenomena) {
        this.phenomena = phenomena;
    }

    public String getWhywhyflag() {
        return whywhyflag;
    }

    public void setWhywhyflag(String whywhyflag) {
        this.whywhyflag = whywhyflag;
    }

    public String getWhywhyno() {
        return whywhyno;
    }

    public void setWhywhyno(String whywhyno) {
        this.whywhyno = whywhyno;
    }

    public String getRootcause() {
        return rootcause;
    }

    public void setRootcause(String rootcause) {
        this.rootcause = rootcause;
    }

    public String getCountermeasure() {
        return countermeasure;
    }

    public void setCountermeasure(String countermeasure) {
        this.countermeasure = countermeasure;
    }

    public String getRootcausedueto() {
        return rootcausedueto;
    }

    public void setRootcausedueto(String rootcausedueto) {
        this.rootcausedueto = rootcausedueto;
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getAnalysed_by() {
        return analysed_by;
    }

    public void setAnalysed_by(String analysed_by) {
        this.analysed_by = analysed_by;
    }

    public LocalDateTime getAnalysed_date() {
        return analysed_date;
    }

    public void setAnalysed_date(LocalDateTime analysed_date) {
        this.analysed_date = analysed_date;
    }

    public String getAnalysed_shift() {
        return analysed_shift;
    }

    public void setAnalysed_shift(String analysed_shift) {
        this.analysed_shift = analysed_shift;
    }

    public String getInvrptid() {
        return invrptid;
    }

    public void setInvrptid(String invrptid) {
        this.invrptid = invrptid;
    }

    public String getInvrptby() {
        return invrptby;
    }

    public void setInvrptby(String invrptby) {
        this.invrptby = invrptby;
    }

    public String getInvrptremarks() {
        return invrptremarks;
    }

    public void setInvrptremarks(String invrptremarks) {
        this.invrptremarks = invrptremarks;
    }

    public LocalDateTime getInvrptdate() {
        return invrptdate;
    }

    public void setInvrptdate(LocalDateTime invrptdate) {
        this.invrptdate = invrptdate;
    }

    public String getCarecommended() {
        return carecommended;
    }

    public void setCarecommended(String carecommended) {
        this.carecommended = carecommended;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public LocalDateTime getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(LocalDateTime targetdate) {
        this.targetdate = targetdate;
    }

    public String getResponseremarks() {
        return responseremarks;
    }

    public void setResponseremarks(String responseremarks) {
        this.responseremarks = responseremarks;
    }

    public String getActionplanno() {
        return actionplanno;
    }

    public void setActionplanno(String actionplanno) {
        this.actionplanno = actionplanno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getReviseddate() {
        return reviseddate;
    }

    public void setReviseddate(LocalDateTime reviseddate) {
        this.reviseddate = reviseddate;
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

    public LocalDateTime getDatecompleted() {
        return datecompleted;
    }

    public void setDatecompleted(LocalDateTime datecompleted) {
        this.datecompleted = datecompleted;
    }

    public String getCompleteremarks() {
        return completeremarks;
    }

    public void setCompleteremarks(String completeremarks) {
        this.completeremarks = completeremarks;
    }

    public String getVerifiedstatus() {
        return verifiedstatus;
    }

    public void setVerifiedstatus(String verifiedstatus) {
        this.verifiedstatus = verifiedstatus;
    }

    public String getVerifiedby() {
        return verifiedby;
    }

    public void setVerifiedby(String verifiedby) {
        this.verifiedby = verifiedby;
    }

    public LocalDateTime getDateverified() {
        return dateverified;
    }

    public void setDateverified(LocalDateTime dateverified) {
        this.dateverified = dateverified;
    }

    public String getVerifyremarks() {
        return verifyremarks;
    }

    public void setVerifyremarks(String verifyremarks) {
        this.verifyremarks = verifyremarks;
    }

    public String getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public String getApprovalby() {
        return approvalby;
    }

    public void setApprovalby(String approvalby) {
        this.approvalby = approvalby;
    }

    public LocalDateTime getApprovaldate() {
        return approvaldate;
    }

    public void setApprovaldate(LocalDateTime approvaldate) {
        this.approvaldate = approvaldate;
    }

    public String getApprovalremarks() {
        return approvalremarks;
    }

    public void setApprovalremarks(String approvalremarks) {
        this.approvalremarks = approvalremarks;
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

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
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