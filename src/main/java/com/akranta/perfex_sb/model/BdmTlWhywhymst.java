package com.akranta.perfex_sb.model;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//import java.math.BigDecimal;
//import java.time.LocalDate;
@Entity
@Table(name = "bdm_tl_whywhymst", schema = "public")

public class BdmTlWhywhymst {
   @Id
    @Column(name = "wwms_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "wwms_date")
    private LocalDateTime date;

    @Column(name = "wwms_factoryid", length = 10)
    private String factoryid;

    @Column(name = "wwms_sectionid", length = 10)
    private String sectionid;

    @Column(name = "wwms_lossid", length = 15)
    private String lossid;

    @Column(name = "wwms_cellid", length = 10)
    private String cellid;

    @Column(name = "wwms_subcellid", length = 10)
    private String subcellid;

    @Column(name = "wwms_machineid", length = 10)
    private String machineid;

    @Column(name = "wwms_assemblyid", length = 15)
    private String assemblyid;

    @Column(name = "wwms_targetpillarid", length = 15)
    private String targetpillarid;

    @Column(name = "wwms_refdoctype", length = 6)
    private String refdoctype;

    @Column(name = "wwms_refdocno", length = 30)
    private String refdocno;

    @Column(name = "wwms_phenomenaid", length = 10)
    private String phenomenaid;

    @Column(name = "wwms_finalaction", length = 500)
    private String finalaction;

    @Column(name = "wwms_sparesreplaced", length = 1)
    private String sparesreplaced;

    @Column(name = "wwms_checksmade", length = 500)
    private String checksmade;

    @Column(name = "wwms_symptombefore", length = 500)
    private String symptombefore;

    @Column(name = "wwms_youdidnot", length = 500)
    private String youdidnot;

    @Column(name = "wwms_countermeasureid", length = 8)
    private String countermeasureid;

    @Column(name = "wwms_countermeasure", length = 500)
    private String countermeasure;

    @Column(name = "wwms_rootcauseid", length = 8)
    private String rootcauseid;

    @Column(name = "wwms_rootcause", length = 500)
    private String rootcause;

    @Column(name = "wwms_isjh", length = 1)
    private Character isjh;

    @Column(name = "wwms_ispm", length = 1)
    private Character ispm;

    @Column(name = "wwms_iskk", length = 1)
    private Character iskk;

    @Column(name = "wwms_isopl", length = 1)
    private Character isopl;

    @Column(name = "wwms_preventivemeasureid", length = 8)
    private String preventivemeasureid;

    @Column(name = "wwms_preventivemeasure", length = 500)
    private String preventivemeasure;

    @Column(name = "wwms_maintinchargeid", length = 8)
    private String maintinchargeid;

    @Column(name = "wwms_status", length = 1)
    private Character status;

   @Column(name = "wwms_ishdpossible", nullable = false)
private Character ishdpossible = 'N';
    @Column(name = "wwms_prevdate", nullable = false)
    @CreationTimestamp
private LocalDateTime prevdate ;

 

    @Column(name = "wwms_preveffectiveness", length = 100, nullable = false)
    private String preveffectiveness ="N";

    @Column(name = "wwms_ispy", length = 1, nullable = false)
    private Character ispy='N';

    @Column(name = "wwms_isojt", length = 1, nullable = false)
    private Character isojt='N';

    @Column(name = "wwms_ojtdesc", length = 500, nullable = false)
    private String ojtdesc="N";

    @Column(name = "wwms_issop", length = 1, nullable = false)
    private Character issop='N';

    @Column(name = "wwms_sopdesc", length = 500, nullable = false)
    private String sopdesc="N";

    @Column(name = "wwms_iskzn", length = 1)
    private Character iskzn;

    @Column(name = "wwms_ispokayoke", length = 1)
    private Character ispokayoke;

    @Column(name = "wwms_formtype", length = 10)
    private String formtype;

    @Column(name = "wwms_pokayoke", length = 30)
    private String pokayoke;

    @Column(name = "wwms_accidentdesc", length = 500, nullable = false)
    private String accidentdesc="N";

    @Column(name = "wwms_accidentphen", length = 100)
    private String accidentphen;

    @Column(name = "wwms_prevno", length = 100)
    private String prevno;

    @Column(name = "wwms_prevperson", length = 30)
    private String prevperson;

    @Column(name = "wwms_iseffective", length = 1)
    private Character iseffective;

    @Column(name = "wwms_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "wwms_area", length = 600, nullable = false)
    private String area;

    @Column(name = "wwms_problem", length = 600, nullable = false)
    private String problem;

    @Column(name = "wwms_timespent")
    private BigDecimal timespent;

    @Column(name = "wwms_problemattendby", length = 12, nullable = false)
    private String problemattendby;

    @Column(name = "wwms_whywhydoneby", length = 12, nullable = false)
    private String whywhydoneby;

    @Column(name = "wwms_reportdatetime", nullable = false)
    private LocalDateTime reportdatetime = LocalDateTime.now();

    @Column(name = "wwms_othercheckpoints", length = 600, nullable = false)
    private String othercheckpoints="N";

    @Column(name = "wwms_sparesid", length = 12, nullable = false)
    private String sparesid ="N";

    @Column(name = "wwms_pillarid", length = 12, nullable = false)
    private String pillarid="N";

    @Column(name = "wwms_productid", length = 12, nullable = false)
    private String productid="N";

    @Column(name = "wwms_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "wwms_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "wwms_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "wwms_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    @Column(name = "wwms_tradeid", length = 20,nullable = false)
    private String tradeid="N";

    @Column(name = "wwms_approleid", length = 20)
    private String apprRoleid;

    @Column(name = "wwms_approvedby", length = 20)
    private String approvedBy;

    @Column(name = "wwms_approvedon")
    private LocalDateTime apprvedOn;

    @Column(name = "wwms_appstatus", length = 20)
    private String appStatus;

    @Column(name = "wwms_appremarks", length = 500)
    private String appRemarks;

     @Column(name = "wwms_iscobd", length = 1, nullable = false)
    private Character iscobd ;

    @Column(name = "wwms_cobdvalue")
    private BigDecimal cobdvalue;

    @Column(name = "wwms_cobdhours")
    private BigDecimal cobdhours;


    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
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

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getLossid() {
        return lossid;
    }

    public void setLossid(String lossid) {
        this.lossid = lossid;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getSubcellid() {
        return subcellid;
    }

    public void setSubcellid(String subcellid) {
        this.subcellid = subcellid;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public String getAssemblyid() {
        return assemblyid;
    }

    public void setAssemblyid(String assemblyid) {
        this.assemblyid = assemblyid;
    }

    public String getTargetpillarid() {
        return targetpillarid;
    }

    public void setTargetpillarid(String targetpillarid) {
        this.targetpillarid = targetpillarid;
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

    public String getPhenomenaid() {
        return phenomenaid;
    }

    public void setPhenomenaid(String phenomenaid) {
        this.phenomenaid = phenomenaid;
    }

    public String getFinalaction() {
        return finalaction;
    }

    public void setFinalaction(String finalaction) {
        this.finalaction = finalaction;
    }

    public String getSparesreplaced() {
        return sparesreplaced;
    }

    public void setSparesreplaced(String sparesreplaced) {
        this.sparesreplaced = sparesreplaced;
    }

    public String getChecksmade() {
        return checksmade;
    }

    public void setChecksmade(String checksmade) {
        this.checksmade = checksmade;
    }

    public String getSymptombefore() {
        return symptombefore;
    }

    public void setSymptombefore(String symptombefore) {
        this.symptombefore = symptombefore;
    }

    public String getYoudidnot() {
        return youdidnot;
    }

    public void setYoudidnot(String youdidnot) {
        this.youdidnot = youdidnot;
    }

    public String getCountermeasureid() {
        return countermeasureid;
    }

    public void setCountermeasureid(String countermeasureid) {
        this.countermeasureid = countermeasureid;
    }

    public String getCountermeasure() {
        return countermeasure;
    }

    public void setCountermeasure(String countermeasure) {
        this.countermeasure = countermeasure;
    }

    public String getRootcauseid() {
        return rootcauseid;
    }

    public void setRootcauseid(String rootcauseid) {
        this.rootcauseid = rootcauseid;
    }

    public String getRootcause() {
        return rootcause;
    }

    public void setRootcause(String rootcause) {
        this.rootcause = rootcause;
    }

    public Character getIsjh() {
        return isjh;
    }

    public void setIsjh(Character isjh) {
        this.isjh = isjh;
    }

    public Character getIspm() {
        return ispm;
    }

    public void setIspm(Character ispm) {
        this.ispm = ispm;
    }

    public Character getIskk() {
        return iskk;
    }

    public void setIskk(Character iskk) {
        this.iskk = iskk;
    }

    public Character getIsopl() {
        return isopl;
    }

    public void setIsopl(Character isopl) {
        this.isopl = isopl;
    }

    public String getPreventivemeasureid() {
        return preventivemeasureid;
    }

    public void setPreventivemeasureid(String preventivemeasureid) {
        this.preventivemeasureid = preventivemeasureid;
    }

    public String getPreventivemeasure() {
        return preventivemeasure;
    }

    public void setPreventivemeasure(String preventivemeasure) {
        this.preventivemeasure = preventivemeasure;
    }

    public String getMaintinchargeid() {
        return maintinchargeid;
    }

    public void setMaintinchargeid(String maintinchargeid) {
        this.maintinchargeid = maintinchargeid;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Character getIshdpossible() {
        return ishdpossible;
    }

    public void setIshdpossible(Character ishdpossible) {
        this.ishdpossible = ishdpossible;
    }

    public LocalDateTime getPrevdate() {
        return prevdate;
    }

    public void setPrevdate(LocalDateTime prevdate) {
        this.prevdate = prevdate;
    }

    public String getPreveffectiveness() {
        return preveffectiveness;
    }

    public void setPreveffectiveness(String preveffectiveness) {
        this.preveffectiveness = preveffectiveness;
    }

    public Character getIspy() {
        return ispy;
    }

    public void setIspy(Character ispy) {
        this.ispy = ispy;
    }

    public Character getIsojt() {
        return isojt;
    }

    public void setIsojt(Character isojt) {
        this.isojt = isojt;
    }

    public String getOjtdesc() {
        return ojtdesc;
    }

    public void setOjtdesc(String ojtdesc) {
        this.ojtdesc = ojtdesc;
    }

    public Character getIssop() {
        return issop;
    }

    public void setIssop(Character issop) {
        this.issop = issop;
    }

    public String getSopdesc() {
        return sopdesc;
    }

    public void setSopdesc(String sopdesc) {
        this.sopdesc = sopdesc;
    }

    public Character getIskzn() {
        return iskzn;
    }

    public void setIskzn(Character iskzn) {
        this.iskzn = iskzn;
    }

    public Character getIspokayoke() {
        return ispokayoke;
    }

    public void setIspokayoke(Character ispokayoke) {
        this.ispokayoke = ispokayoke;
    }

    public String getFormtype() {
        return formtype;
    }

    public void setFormtype(String formtype) {
        this.formtype = formtype;
    }

    public String getPokayoke() {
        return pokayoke;
    }

    public void setPokayoke(String pokayoke) {
        this.pokayoke = pokayoke;
    }

    public String getAccidentdesc() {
        return accidentdesc;
    }

    public void setAccidentdesc(String accidentdesc) {
        this.accidentdesc = accidentdesc;
    }

    public String getAccidentphen() {
        return accidentphen;
    }

    public void setAccidentphen(String accidentphen) {
        this.accidentphen = accidentphen;
    }

    public String getPrevno() {
        return prevno;
    }

    public void setPrevno(String prevno) {
        this.prevno = prevno;
    }

    public String getPrevperson() {
        return prevperson;
    }

    public void setPrevperson(String prevperson) {
        this.prevperson = prevperson;
    }

    public Character getIseffective() {
        return iseffective;
    }

    public void setIseffective(Character iseffective) {
        this.iseffective = iseffective;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public BigDecimal getTimespent() {
        return timespent;
    }

    public void setTimespent(BigDecimal timespent) {
        this.timespent = timespent;
    }

    public String getProblemattendby() {
        return problemattendby;
    }

    public void setProblemattendby(String problemattendby) {
        this.problemattendby = problemattendby;
    }

    public String getWhywhydoneby() {
        return whywhydoneby;
    }

    public void setWhywhydoneby(String whywhydoneby) {
        this.whywhydoneby = whywhydoneby;
    }

    public LocalDateTime getReportdatetime() {
        return reportdatetime;
    }

    public void setReportdatetime(LocalDateTime reportdatetime) {
        this.reportdatetime = reportdatetime;
    }

    public String getOthercheckpoints() {
        return othercheckpoints;
    }

    public void setOthercheckpoints(String othercheckpoints) {
        this.othercheckpoints = othercheckpoints;
    }

    public String getSparesid() {
        return sparesid;
    }

    public void setSparesid(String sparesid) {
        this.sparesid = sparesid;
    }

    public String getPillarid() {
        return pillarid;
    }

    public void setPillarid(String pillarid) {
        this.pillarid = pillarid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
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

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getApprRoleid() {
        return apprRoleid;
    }

    public void setApprRoleid(String apprRoleid) {
        this.apprRoleid = apprRoleid;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getApprvedOn() {
        return apprvedOn;
    }

    public void setApprvedOn(LocalDateTime apprvedOn) {
        this.apprvedOn = apprvedOn;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getAppRemarks() {
        return appRemarks;
    }

    public void setAppRemarks(String appRemarks) {
        this.appRemarks = appRemarks;
    }

    public Character getIscobd() {
        return iscobd;
    }

    public void setIscobd(Character iscobd) {
        this.iscobd = iscobd;
    }


    public BigDecimal getCobdvalue() {
        return cobdvalue;
    }

    public void setCobdValue(BigDecimal cobdvalue) {
        this.cobdvalue = cobdvalue;
    }

    
    public BigDecimal getCobdhours() {
        return cobdhours;
    }

    public void setCobdhours(BigDecimal cobdhours) {
        this.cobdhours = cobdhours;
    }










    
   
   
}
