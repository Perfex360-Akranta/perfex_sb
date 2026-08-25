package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name="KZN_TL_MST",schema="public")
public class KznTlMst {
    @Id
    @Column(name="kznm_keyid", length=20,	nullable=false)
private String keyid; 
@Column(name = "kznm_date", 	nullable = false, length = 0)
private LocalDateTime date;
@Column(name = "kznm_factoryid", length = 10,	nullable = false)
private String factoryid;
@Column(name = "kznm_tpmpillarid", length = 15,	nullable = false)
private String tpmpillarid;
@Column(name = "kznm_sectionid", length = 10,	nullable = false)
private String sectionid;
@Column(name = "kznm_cellid", length = 10,	nullable = false)
private String cellid;
@Column(name = "kznm_machineid", length = 10,	nullable = false)
private String machineid;
@Column(name = "kznm_lossid", length = 15,	nullable = false)
private String lossid;
@Column(name = "kznm_resultarea", length = 10,	nullable = false)
private String resultarea;
@Column(name = "kznm_teammembers", length = 4000,	nullable = false)
private String teammembers;
@Column(name = "kznm_theme", length = 600,	nullable = false)
private String theme;
@Column(name = "kznm_themecategoryid", length = 35,	nullable = false)
private String themecategoryid;
@Column(name = "kznm_benchmark", length = 30,	nullable = false)
private String benchmark;
@Column(name = "kznm_target", length = 30,	nullable = false)
private String target;
@Column(name = "kznm_startdate", 	nullable = false, length = 0)
private LocalDateTime startdate;
@Column(name = "kznm_enddate", 	nullable = false,length = 0)
private LocalDateTime enddate;

@Column(name = "kznm_presentproblem", length = 4000,	nullable = false)
private String presentproblem;
@Column(name = "kznm_presentimage", length = 98,	nullable = false)
private String presentimage;
@Column(name = "kznm_wwms_keyid", length = 20,	nullable = false)
private String wwms_keyid;
@Column(name = "kznm_rootcause", length = 600,	nullable = false)
private String rootcause;
@Column(name = "kznm_idea", length = 4000,	nullable = false)
private String idea;
@Column(name = "kznm_countermeasure", length = 4000,	nullable = false)
private String countermeasure;
@Column(name = "kznm_afterimage", length = 100,	nullable = false)
private String afterimage;
@Column(name = "kznm_resultdescription", length = 4000,	nullable = false)
private String resultdescription;
@Column(name = "kznm_resultimage", length = 100,	nullable = false)
private  String resultimage;
@Column(name = "kznm_benefits", length = 4000,	nullable = false)
private String benefits;
@Column(name = "kznm_benefitsimage", length = 100,	nullable = false)
private String benefitsimage;
@Column(name = "kznm_isprovidingchanging", length = 1,	nullable = false)
private Character isprovidingchanging;
@Column(name = "kznm_reversibleirreversible", length = 1,	nullable = false)
private Character reversibleirreversible;
@Column(name = "kznm_kaizenlink", length = 1,	nullable = false)
private Character kaizenlink;
@Column(name = "kznm_kaizenlinktype", length = 3,	nullable = false)
private String kaizenlinktype;
@Column(name = "kznm_assemblyid", length = 9,	nullable = false)
private String assemblyid;
@Column(name = "kznm_phenomenaid", length = 8,	nullable = false)
private String phenomenaid;
@Column(name = "kznm_causeid", length = 9,	nullable = false)
private String causeid;
@Column(name = "kznm_materialcost", length = 75,	nullable = false)
private String materialcost;
@Column(name = "kznm_labourcost", length = 75,	nullable = false)
private String labourcost;
@Column(name = "kznm_howtosustain", length = 250,	nullable = false)
private String howtosustain;
@Column(name = "kznm_additionaldetails", length = 250,	nullable = false)
private String additionaldetails;
@Column(name = "kznm_additionalimage", length = 100,	nullable = false)
private String additionalimage;
@Column(name = "kznm_ishdpossible", length = 1,	nullable = false)
private Character ishdpossible;
@Column(name = "kznm_noofhds", length = 10,	nullable = false)
private String noofhds;
@Column(name = "kznm_preparedid", length = 20,	nullable = false)
private String preparedid;
@Column(name = "kznm_prepareddate", 	nullable = false,length =0)
private LocalDateTime prepareddate;
@Column(name = "kznm_approvedid", length = 20,	nullable = false)
private String approvedid;
@Column(name = "kznm_approveddate", 	nullable = false,length = 0)
private LocalDateTime approveddate;
@Column(name = "kznm_isworequired", length = 1,	nullable = false)
private Character isworequired;
@Column(name = "kznm_refdoctype", length = 30,	nullable = false)
private String refdoctype;
@Column(name = "kznm_refdocno", length = 20,	nullable = false)
private String refdocno;
@Column(name = "kznm_status", length = 1,	nullable = false)
private Character status;
@Column(name = "kznm_woid", length = 20,	nullable = false)
private String woid;
@Column(name = "kznm_wofeedbackid", length = 150,	nullable = false)
private String wofeedbackid;
@Column(name = "kznm_completeddate", 	nullable = false,length = 0)
private LocalDateTime completeddate;
@Column(name = "kznm_completedid", length = 20,	nullable = false)
private String completedid;
@Column(name = "kznm_remarks", length = 4000,	nullable = false)
private String remarks;
@Column(name = "kznm_operations", length = 250,	nullable = false)
private String operations;
@Column(name = "kznm_whattosustain", length = 500,	nullable = false)
private String whattosustain;
@Column(name = "kznm_sustainfreq", length = 250,	nullable = false)
private String sustainfreq;
@Column(name = "kznm_totalcost", length = 75,	nullable = false)
private String totalcost;
@Column(name = "kznm_circleid", length = 20,	nullable = false)
private String circleid;
@Column(name = "kznm_departmentid", length = 20,	nullable = false)
private String departmentid;
@Column(name = "kznm_costcentreid", length = 50,	nullable = false)
private String costcentreid;
@Column(name = "kznm_istpmkzn", length = 1,	nullable = false)
private String istpmkzn;
@Column(name = "kznm_materialno", length = 20,	nullable = false)
private String materialno;
@Column(name = "kznm_isworthformp", length = 1,	nullable = false)
private Character isworthformp;
@Column(name = "kznm_creationflag", length = 1,	nullable = false)
private Character creationflag;
@Column(name = "kznm_relatedto", length = 3,	nullable = false)
private String relatedto;
@Column(name = "kznm_mouldid", length = 10,	nullable = false)
private String mouldid;
@Column(name = "kznm_elementid", length = 250,	nullable = false)
private String elementid;
@Column(name = "kznm_flid", length = 12,	nullable = false)
private String flid;
@Column(name = "kznm_utiliseforfuture", length = 1,	nullable = false)
private Character utiliseforfuture;
@Column(name = "kznm_resultareasec", length = 10,	nullable = false)
private String resultareasec;
@Column(name = "kznm_ideagroupindividual", length = 1,	nullable = false)
private Character ideagroupindividual;
@Column(name = "kznm_kzbnkeyid", length = 15,	nullable = false)
private String kzbnkeyid;
@Column(name = "kznm_benefittype", length = 5,	nullable = false)
private String benefittype;
@Column(name = "kznm_benefitvalue", length = 25,	nullable = false)
private String benefitvalue;
@Column(name = "kznm_costperhour", 	nullable = false)
private BigDecimal costperhour;
@Column(name = "kznm_costperequipment",	nullable = false)
private BigDecimal costperequipment;
@Column(name = "kznm_verifyamount", length = 10,	nullable = false)
private String verifyamount;
@Column(name = "kznm_aprov_level", length = 150,	nullable = false)
private String aprovLevel;
@Column(name = "kznm_analysis", length = 4000,	nullable = false)
private String analysis;
@Column(name = "kznm_iswhywhy", length = 1,	nullable = false)
private Character isWhywhy;
@Column(name = "kznm_fiprequired", length = 1,	nullable = false)
private Character fipRequired;
@Column(name = "kznm_fipnumber", length = 30,	nullable = false)
private String fipNumber;
@Column(name = "kznm_kaizenupload", length = 1,	nullable = false)
private String kaizenupload;
@Column(name = "kznm_kpiid", length = 10,	nullable = false)
private String kpiid;
@Column(name = "kznm_activitypillarid", length = 10,	nullable = false)
private String activitypillarid;
@Column(name = "kznm_active", length = 1,	nullable = false)
private Character active;
@Column(name = "kznm_createdby", length = 8,	nullable = false)
private String createdby;
@Column(name = "kznm_createdon", length = 0,	nullable = false)
@CreationTimestamp
private LocalDateTime createdon;
@Column(name = "kznm_modifiedon", length = 0,	nullable = false)
@UpdateTimestamp
private LocalDateTime modifiedon;
@Column(name = "kznm_industry4", length = 1,	nullable = false)
private Character industry4;
@Column(name = "kznm_industrycategory", length = 30,	nullable = false)
private String industrycategory;
@Column(name = "kznm_suggbyespname", length = 350,	nullable = false)
private String espNames;
@Column(name = "kznm_csmvalue", length = 10,	nullable = false)
private String csmValue;
@Column(name = "kznm_icoe", length = 10,	nullable = false)
private String icoe;
@Column(name = "kznm_pcoe", length = 10,	nullable = false)
private String pcoe;
@Column(name = "kznm_fip", length = 3,	nullable = false)
private String fip;

public String getKeyid() {
    return keyid;
}
public void setKeyid(String kznmKeyid) {
    this.keyid = kznmKeyid;
}
public LocalDateTime getDate() {
    return date;
}
public void setDate(LocalDateTime date) {
    this.date = date;
}
public String getFactoryid() {
    return factoryid;
}
public void setFactoryid(String factoryid) {
    this.factoryid = factoryid;
}
public String getTpmpillarid() { 
    return tpmpillarid;
}
public void setTpmpillarid(String tpmpillarid) {
    this.tpmpillarid = tpmpillarid;
}
public String getSectionid() {
    return sectionid;
}
public void setSectionid(String sectionid) {
    this.sectionid = sectionid;
}
public String getCellid() {
    return cellid;
}
public void setCellid(String cellid) {
    this.cellid = cellid;
}
public String getMachineid() {
    return machineid;
}
public void setMachineid(String machineid) {
    this.machineid = machineid;
}
public String getLossid() {
    return lossid;
}
public void setLossid(String lossid) {
    this.lossid = lossid;
}
public String getResultarea() {
    return resultarea;
}
public void setResultarea(String resultarea) {
    this.resultarea = resultarea;
}
public String getTeammembers() {
    return teammembers;
}
public void setTeammembers(String teammembers) {
    this.teammembers = teammembers;
}
public String getTheme() {
    return theme;
}
public void setTheme(String theme) {
    this.theme = theme;
}
public String getThemecategoryid() {
    return themecategoryid;
}
public void setThemecategoryid(String themecategoryid) {
    this.themecategoryid = themecategoryid;
}
public String getBenchmark() {
    return benchmark;
}
public void setBenchmark(String benchmark) {
    this.benchmark = benchmark;
}
public String getTarget() {
    return target;
}
public void setTarget(String target) {
    this.target = target;
}
public LocalDateTime getStartdate() {
    return startdate;
}
public void setStartdate(LocalDateTime startdate) {
    this.startdate = startdate;
}
public LocalDateTime getEnddate() {
    return enddate;
}
public void setEnddate(LocalDateTime enddate) {
    this.enddate = enddate;
}
public String getPresentproblem() {
    return presentproblem;
}
public void setPresentproblem(String presentproblem) {
    this.presentproblem = presentproblem;
}
public String getPresentimage() {
    return presentimage;
}
public void setPresentimage(String presentimage) {
    this.presentimage = presentimage;
}
public String getWwms_keyid() {
    return wwms_keyid;
}
public void setWwms_keyid(String wwms_keyid) {
    this.wwms_keyid = wwms_keyid;
}
public String getRootcause() {
    return rootcause;
}
public void setRootcause(String rootcause) {
    this.rootcause = rootcause;
}
public String getIdea() {
    return idea;
}
public void setIdea(String idea) {
    this.idea = idea;
}
public String getCountermeasure() {
    return countermeasure;
}
public void setCountermeasure(String countermeasure) {
    this.countermeasure = countermeasure;
}
public String getAfterimage() {
    return afterimage;
}
public void setAfterimage(String afterimage) {
    this.afterimage = afterimage;
}
public String getResultdescription() {
    return resultdescription;
}
public void setResultdescription(String resultdescription) {
    this.resultdescription = resultdescription;
}
public String getResultimage() {
    return resultimage;
}
public void setResultimage(String resultimage) {
    this.resultimage = resultimage;
}
public String getBenefits() {
    return benefits;
}
public void setBenefits(String benefits) {
    this.benefits = benefits;
}
public String getBenefitsimage() {
    return benefitsimage;
}
public void setBenefitsimage(String benefitsimage) {
    this.benefitsimage = benefitsimage;
}
public Character getIsprovidingchanging() {
    return isprovidingchanging;
}
public void setIsprovidingchanging(Character isprovidingchanging) {
    this.isprovidingchanging = isprovidingchanging;
}
public Character getReversibleirreversible() {
    return reversibleirreversible;
}
public void setReversibleirreversible(Character reversibleirreversible) {
    this.reversibleirreversible = reversibleirreversible;
}
public Character getKaizenlink() {
    return kaizenlink;
}
public void setKaizenlink(Character kaizenlink) {
    this.kaizenlink = kaizenlink;
}
public String getKaizenlinktype() {
    return kaizenlinktype;
}
public void setKaizenlinktype(String kaizenlinktype) {
    this.kaizenlinktype = kaizenlinktype;
}
public String getAssemblyid() {
    return assemblyid;
}
public void setAssemblyid(String assemblyid) {
    this.assemblyid = assemblyid;
}
public String getPhenomenaid() {
    return phenomenaid;
}
public void setPhenomenaid(String phenomenaid) {
    this.phenomenaid = phenomenaid;
}
public String getCauseid() {
    return causeid;
}
public void setCauseid(String causeid) {
    this.causeid = causeid;
}
public String getMaterialcost() {
    return materialcost;
}
public void setMaterialcost(String materialcost) {
    this.materialcost = materialcost;
}
public String getLabourcost() {
    return labourcost;
}
public void setLabourcost(String labourcost) {
    this.labourcost = labourcost;
}
public String getHowtosustain() {
    return howtosustain;
}
public void setHowtosustain(String howtosustain) {
    this.howtosustain = howtosustain;
}
public String getAdditionaldetails() {
    return additionaldetails;
}
public void setAdditionaldetails(String additionaldetails) {
    this.additionaldetails = additionaldetails;
}
public String getAdditionalimage() {
    return additionalimage;
}
public void setAdditionalimage(String additionalimage) {
    this.additionalimage = additionalimage;
}
public Character getIshdpossible() {
    return ishdpossible;
}
public void setIshdpossible(Character ishdpossible) {
    this.ishdpossible = ishdpossible;
}
public String getNoofhds() {
    return noofhds;
}
public void setNoofhds(String noofhds) {
    this.noofhds = noofhds;
}
public String getPreparedid() {
    return preparedid;
}
public void setPreparedid(String preparedid) {
    this.preparedid = preparedid;
}
public LocalDateTime getPrepareddate() {
    return prepareddate;
}
public void setPrepareddate(LocalDateTime prepareddate) {
    this.prepareddate = prepareddate;
}
public String getApprovedid() {
    return approvedid;
}
public void setApprovedid(String approvedid) {
    this.approvedid = approvedid;
}
public LocalDateTime getApproveddate() {
    return approveddate;
}
public void setApproveddate(LocalDateTime approveddate) {
    this.approveddate = approveddate;
}
public Character getIsworequired() {
    return isworequired;
}
public void setIsworequired(Character isworequired) {
    this.isworequired = isworequired;
}
public String getRefdoctype() {
    return refdoctype;
}
public void setRefdoctype(String refdoctype) {
    this.refdoctype = refdoctype;
}
public String getRefdocno() {
    return refdocno;
}
public void setRefdocno(String refdocno) {
    this.refdocno = refdocno;
}
public Character getStatus() {
    return status;
}
public void setStatus(Character status) {
    this.status = status;
}
public String getWoid() {
    return woid;
}
public void setWoid(String woid) {
    this.woid = woid;
}
public String getWofeedbackid() {
    return wofeedbackid;
}
public void setWofeedbackid(String wofeedbackid) {
    this.wofeedbackid = wofeedbackid;
}
public LocalDateTime getCompleteddate() {
    return completeddate;
}
public void setCompleteddate(LocalDateTime completeddate) {
    this.completeddate = completeddate;
}
public String getCompletedid() {
    return completedid;
}
public void setCompletedid(String completedid) {
    this.completedid = completedid;
}
public String getRemarks() {
    return remarks;
}
public void setRemarks(String remarks) {
    this.remarks = remarks;
}
public String getOperations() {
    return operations;
}
public void setOperations(String operations) {
    this.operations = operations;
}
public String getWhattosustain() {
    return whattosustain;
}
public void setWhattosustain(String whattosustain) {
    this.whattosustain = whattosustain;
}
public String getSustainfreq() {
    return sustainfreq;
}
public void setSustainfreq(String sustainfreq) {
    this.sustainfreq = sustainfreq;
}
public String getTotalcost() {
    return totalcost;
}
public void setTotalcost(String totalcost) {
    this.totalcost = totalcost;
}
public String getCircleid() {
    return circleid;
}
public void setCircleid(String circleid) {
    this.circleid = circleid;
}
public String getDepartmentid() {
    return departmentid;
}
public void setDepartmentid(String departmentid) {
    this.departmentid = departmentid;
}
public String getCostcentreid() {
    return costcentreid;
}
public void setCostcentreid(String costcentreid) {
    this.costcentreid = costcentreid;
}
public String getIstpmkzn() {
    return istpmkzn;
}
public void setIstpmkzn(String istpmkzn) {
    this.istpmkzn = istpmkzn;
}
public String getMaterialno() {
    return materialno;
}
public void setMaterialno(String materialno) {
    this.materialno = materialno;
}
public Character getIsworthformp() {
    return isworthformp;
}
public void setIsworthformp(Character isworthformp) {
    this.isworthformp = isworthformp;
}
public Character getCreationflag() {
    return creationflag;
}
public void setCreationflag(Character creationflag) {
    this.creationflag = creationflag;
}
public String getRelatedto() {
    return relatedto;
}
public void setRelatedto(String relatedto) {
    this.relatedto = relatedto;
}
public String getMouldid() {
    return mouldid;
}
public void setMouldid(String mouldid) {
    this.mouldid = mouldid;
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
public Character getUtiliseforfuture() {
    return utiliseforfuture;
}
public void setUtiliseforfuture(Character utiliseforfuture) {
    this.utiliseforfuture = utiliseforfuture;
}
public String getResultareasec() {
    return resultareasec;
}
public void setResultareasec(String resultareasec) {
    this.resultareasec = resultareasec;
}
public Character getIdeagroupindividual() {
    return ideagroupindividual;
}
public void setIdeagroupindividual(Character ideagroupindividual) {
    this.ideagroupindividual = ideagroupindividual;
}
public String getKzbnkeyid() {
    return kzbnkeyid;
}
public void setKzbnkeyid(String kzbnkeyid) {
    this.kzbnkeyid = kzbnkeyid;
}
public String getBenefittype() {
    return benefittype;
}
public void setBenefittype(String benefittype) {
    this.benefittype = benefittype;
}
public String getBenefitvalue() {
    return benefitvalue;
}
public void setBenefitvalue(String benefitvalue) {
    this.benefitvalue = benefitvalue;
}
public BigDecimal getCostperhour() {
    return costperhour;
}
public void setCostperhour(BigDecimal costperhour) {
    this.costperhour = costperhour;
}
public BigDecimal getCostperequipment() {
    return costperequipment;
}
public void setCostperequipment(BigDecimal costperequipment) {
    this.costperequipment = costperequipment;
}
public String getVerifyamount() {
    return verifyamount;
}
public void setVerifyamount(String verifyamount) {
    this.verifyamount = verifyamount;
}
public String getAprovLevel() {
    return aprovLevel;
}
public void setAprovLevel(String aprovLevel) {
    this.aprovLevel = aprovLevel;
}
public String getAnalysis() {
    return analysis;
}
public void setAnalysis(String analysis) {
    this.analysis = analysis;
}
public Character getIsWhywhy() {
    return isWhywhy;
}
public void setIsWhywhy(Character isWhywhy) {
    this.isWhywhy = isWhywhy;
}
public Character getFipRequired() {
    return fipRequired;
}
public void setFipRequired(Character fipRequired) {
    this.fipRequired = fipRequired;
}
public String getFipNumber() {
    return fipNumber;
}
public void setFipNumber(String fipNumber) {
    this.fipNumber = fipNumber;
}
public String getKaizenupload() {
    return kaizenupload;
}
public void setKaizenupload(String kaizenupload) {
    this.kaizenupload = kaizenupload;
}
public String getKpiid() {
    return kpiid;
}
public void setKpiid(String kpiid) {
    this.kpiid = kpiid;
}
public String getActivitypillarid() {
    return activitypillarid;
}
public void setActivitypillarid(String activitypillarid) {
    this.activitypillarid = activitypillarid;
}
public Character getActive() {
    return active;
}
public void setActive(Character active) {
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
public Character getIndustry4() {
    return industry4;
}
public void setIndustry4(Character industry4) {
    this.industry4 = industry4;
}
public String getIndustrycategory() {
    return industrycategory;
}
public void setIndustrycategory(String industrycategory) {
    this.industrycategory = industrycategory;
}
public String getEspNames() {
    return espNames;
}
public void setEspNames(String espNames) {
    this.espNames = espNames;
}
public String getCsmValue() {
    return csmValue;
}
public void setCsmValue(String csmValue) {
    this.csmValue = csmValue;
}
public String getIcoe() {
    return icoe;
}
public void setIcoe(String icoe) {
    this.icoe = icoe;
}
public String getPcoe() {
    return pcoe;
}
public void setPcoe(String pcoe) {
    this.pcoe = pcoe;
}
public String getFip() {
    return fip;
}
public void setFip(String fip) {
    this.fip = fip;
}
}