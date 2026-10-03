package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bal_wom_tl_womst", schema = "public")
public class WomTlWomst {

    @Id
    @Column(name = "woms_keyid", length = 20, nullable = false)
    private String keyid;

    @Column(name = "woms_factoryid", length = 10)
    private String factoryid;

    @Column(name = "woms_sectionid", length = 10, nullable = false)
    private String sectionid;

    @Column(name = "woms_cellid", length = 10)
    private String cellid;

    @Column(name = "woms_machineid", length = 10)
    private String machineid;

    @Column(name = "woms_directentry", length = 1, nullable = false)
    private Character directentry;

    @Column(name = "woms_relatedto", length = 3, nullable = false)
    private String relatedto;

    @Column(name = "woms_loss", length = 30, nullable = false)
    private String loss;

    @Column(name = "woms_mouldid", length = 10, nullable = false)
    private String mouldid;

    @Column(name = "woms_workcenterid", length = 15, nullable = false)
    private String workcenterid;

    @Column(name = "woms_costcenterid", length = 10, nullable = false)
    private String costcenterid;

    @Column(name = "woms_occurreddate", nullable = false)
    private LocalDate occurreddate;

    @Column(name = "woms_shiftid", length = 10)
    private String shiftid;

    @Column(name = "woms_shiftdate", nullable = false)
    private LocalDate shiftdate;

    @Column(name = "woms_priority", length = 1, nullable = false)
    private Character priority;

    @Column(name = "woms_reporteddate", nullable = false)
    private LocalDate reporteddate;

    @Column(name = "woms_reportedby", length = 10)
    private String reportedby;

    @Column(name = "woms_productionstop", length = 1, nullable = false)
    private Character productionstop;

    @Column(name = "woms_machinecondition", length = 10, nullable = false)
    private String machinecondition;

    @Column(name = "woms_activitytype", length = 2, nullable = false)
    private String activitytype;

    @Column(name = "woms_alarmno", length = 10, nullable = false)
    private String alarmno;

    @Column(name = "woms_tradeid", length = 12, nullable = false)
    private String tradeid;

    @Column(name = "woms_assemblyid", length = 10, nullable = false)
    private String assemblyid;

    @Column(name = "woms_subassemblyid", length = 10, nullable = false)
    private String subassemblyid;

    @Column(name = "woms_failuretypeid", length = 20, nullable = false)
    private String failuretypeid;

    @Column(name = "woms_partlocation", length = 100, nullable = false)
    private String partlocation;

    @Column(name = "woms_spareid", length = 12, nullable = false)
    private String spareid;

    @Column(name = "woms_phenomenaid", length = 15, nullable = false)
    private String phenomenaid;

    @Column(name = "woms_causeid", length = 10, nullable = false)
    private String causeid;

    @Column(name = "woms_location", length = 600, nullable = false)
    private String location;

    @Column(name = "woms_problem", length = 600, nullable = false)
    private String problem;

    @Column(name = "woms_bookingremarks", length = 600, nullable = false)
    private String bookingremarks;

    @Column(name = "woms_status", length = 1, nullable = false)
    private Character status;

    @Column(name = "woms_requestapproved", length = 1, nullable = false)
    private Character requestapproved;

    @Column(name = "woms_requestapprovedby", length = 10)
    private String requestapprovedby;

    @Column(name = "woms_requestapproveddate", nullable = false)
    private LocalDate requestapproveddate;

    @Column(name = "woms_requestapprovremarks", length = 600, nullable = false)
    private String requestapprovremarks;

    @Column(name = "woms_accepteddate", nullable = false)
    private LocalDate accepteddate;

    @Column(name = "woms_acceptedflag", length = 1, nullable = false)
    private Character acceptedflag;

    @Column(name = "woms_acceptedby", length = 10)
    private String acceptedby;

    @Column(name = "woms_acceptedremarks", length = 600, nullable = false)
    private String acceptedremarks;

    @Column(name = "woms_productionapproval", length = 1, nullable = false)
    private Character productionapproval;

    @Column(name = "woms_safetypermitsrequried", length = 1, nullable = false)
    private Character safetypermitsrequried;

    @Column(name = "woms_safetypermitid", length = 10, nullable = false)
    private String safetypermitid;

    @Column(name = "woms_safetypermitapproved", length = 1, nullable = false)
    private Character safetypermitapproved;

    @Column(name = "woms_safetypermitcompleted", length = 1, nullable = false)
    private Character safetypermitcompleted;

    @Column(name = "woms_safetypermitsignoff", length = 1, nullable = false)
    private Character safetypermitsignoff;

    @Column(name = "woms_rescheduleflag", length = 1, nullable = false)
    private Character rescheduleflag;

    @Column(name = "woms_rescheduledate", nullable = false)
    private LocalDate rescheduledate;

    @Column(name = "woms_rescheduledremarks", length = 600, nullable = false)
    private String rescheduledremarks;

    @Column(name = "woms_allottedflag", length = 1, nullable = false)
    private Character allottedflag;

    @Column(name = "woms_allotteddate", nullable = false)
    private LocalDate allotteddate;

    @Column(name = "woms_allottedto", length = 10, nullable = false)
    private String allottedto;

    @Column(name = "woms_allottedremarks", length = 600, nullable = false)
    private String allottedremarks;

    @Column(name = "woms_proposedstflag", length = 1, nullable = false)
    private Character proposedstflag;

    @Column(name = "woms_proposedendflag", length = 1, nullable = false)
    private Character proposedendflag;

    @Column(name = "woms_proposedstartdate", nullable = false)
    private LocalDate proposedstartdate;

    @Column(name = "woms_proposedenddate", nullable = false)
    private LocalDate proposedenddate;

    @Column(name = "woms_proposeddtacceptflag", length = 1, nullable = false)
    private Character proposeddtacceptflag;

    @Column(name = "woms_rescheduledstflag", length = 1, nullable = false)
    private Character rescheduledstflag;

    @Column(name = "woms_rescheduledendflag", length = 1, nullable = false)
    private Character rescheduledendflag;

    @Column(name = "woms_rescheduleby", length = 10, nullable = false)
    private String rescheduleby;

    @Column(name = "woms_reschedulestartdate", nullable = false)
    private LocalDate reschedulestartdate;

    @Column(name = "woms_rescheduleenddate", nullable = false)
    private LocalDate rescheduleenddate;

    @Column(name = "woms_rescheduleremarks", length = 600, nullable = false)
    private String rescheduleremarks;

    @Column(name = "woms_productionstartflag", length = 1, nullable = false)
    private Character productionstartflag;

    @Column(name = "woms_productionstartdate", nullable = false)
    private LocalDate productionstartdate;

    @Column(name = "woms_productionby", length = 10, nullable = false)
    private String productionby;

    @Column(name = "woms_productionremarks", length = 600, nullable = false)
    private String productionremarks;

    @Column(name = "woms_workstartflag", length = 1, nullable = false)
    private Character workstartflag;

    @Column(name = "woms_workstartdate", nullable = false)
    private LocalDate workstartdate;

    @Column(name = "woms_workendflag", length = 1, nullable = false)
    private Character workendflag;

    @Column(name = "woms_workenddate", nullable = false)
    private LocalDate workenddate;

    @Column(name = "woms_doneby", length = 10, nullable = false)
    private String doneby;

    @Column(name = "woms_finalactivitytype", length = 1, nullable = false)
    private Character finalactivitytype;

    @Column(name = "woms_activityid", length = 15, nullable = false)
    private String activityid;

    @Column(name = "woms_woapprovalflag", length = 1, nullable = false)
    private Character woapprovalflag;

    @Column(name = "woms_woapprovalby", length = 10, nullable = false)
    private String woapprovalby;

    @Column(name = "woms_woapprovaldate", nullable = false)
    private LocalDate woapprovaldate;

    @Column(name = "woms_machinereleaseflag", length = 1, nullable = false)
    private Character machinereleaseflag;

    @Column(name = "woms_machinereleaseddate", nullable = false)
    private LocalDate machinereleaseddate;

    @Column(name = "woms_machinereleaseby", length = 10, nullable = false)
    private String machinereleaseby;

    @Column(name = "woms_intorextequip", length = 1, nullable = false)
    private Character intorextequip;

    @Column(name = "woms_intorextequipdesc", length = 600, nullable = false)
    private String intorextequipdesc;

    @Column(name = "woms_standbyadditionalinfo", length = 600, nullable = false)
    private String standbyadditionalinfo;

    @Column(name = "woms_standbyremarks", length = 600, nullable = false)
    private String standbyremarks;

    @Column(name = "woms_sentforrepairflag", length = 1, nullable = false)
    private Character sentforrepairflag;

    @Column(name = "woms_sentrepairid", length = 15, nullable = false)
    private String sentrepairid;

    @Column(name = "woms_sentto", length = 10, nullable = false)
    private String sentto;

    @Column(name = "woms_exceptedreturndate", nullable = false)
    private LocalDate exceptedreturndate;

    @Column(name = "woms_repairremarks", length = 600, nullable = false)
    private String repairremarks;

    @Column(name = "woms_remarks", length = 600, nullable = false)
    private String remarks;

    @Column(name = "woms_jobopeningid", length = 20, nullable = false)
    private String jobopeningid;

    @Column(name = "woms_finalstatus", length = 100, nullable = false)
    private String finalstatus;

    @Column(name = "woms_orderno", length = 10, nullable = false)
    private String orderno;

    @Column(name = "woms_maintpriority", nullable = false)
    private BigDecimal maintpriority;

    @Column(name = "woms_allottedsource", length = 20, nullable = false)
    private String allottedsource;

    @Column(name = "woms_allottedsupplier", length = 20, nullable = false)
    private String allottedsupplier;

    @Column(name = "woms_numofactivities", nullable = false)
    private BigDecimal numofactivities;

    @Column(name = "woms_pwdm_wono", length = 20, nullable = false)
    private String pwdmwono;

    @Column(name = "woms_refdoctype", length = 3, nullable = false)
    private String refdoctype;

    @Column(name = "woms_refdocid", length = 20, nullable = false)
    private String refdocid;

    @Column(name = "woms_processid", length = 15, nullable = false)
    private String processid;

    @Column(name = "woms_requiredstart", length = 12, nullable = false)
    private String requiredstart;

    @Column(name = "woms_requiredend", length = 12, nullable = false)
    private String requiredend;

    @Column(name = "woms_plannergroup", length = 2, nullable = false)
    private String plannergroup;

    @Column(name = "woms_departmentid", length = 2, nullable = false)
    private String departmentid;

    @Column(name = "woms_tempfield1", length = 2, nullable = false)
    private String tempfield1;

    @Column(name = "woms_modifiedby", length = 10, nullable = false)
    private String modifiedby;

    @Column(name = "woms_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "woms_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "woms_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "woms_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "woms_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "woms_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
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

    public Character getDirectentry() {
        return directentry;
    }

    public void setDirectentry(Character directentry) {
        this.directentry = directentry;
    }

    public String getRelatedto() {
        return relatedto;
    }

    public void setRelatedto(String relatedto) {
        this.relatedto = relatedto;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getMouldid() {
        return mouldid;
    }

    public void setMouldid(String mouldid) {
        this.mouldid = mouldid;
    }

    public String getWorkcenterid() {
        return workcenterid;
    }

    public void setWorkcenterid(String workcenterid) {
        this.workcenterid = workcenterid;
    }

    public String getCostcenterid() {
        return costcenterid;
    }

    public void setCostcenterid(String costcenterid) {
        this.costcenterid = costcenterid;
    }

    public LocalDate getOccurreddate() {
        return occurreddate;
    }

    public void setOccurreddate(LocalDate occurreddate) {
        this.occurreddate = occurreddate;
    }

    public String getShiftid() {
        return shiftid;
    }

    public void setShiftid(String shiftid) {
        this.shiftid = shiftid;
    }

    public LocalDate getShiftdate() {
        return shiftdate;
    }

    public void setShiftdate(LocalDate shiftdate) {
        this.shiftdate = shiftdate;
    }

    public Character getPriority() {
        return priority;
    }

    public void setPriority(Character priority) {
        this.priority = priority;
    }

    public LocalDate getReporteddate() {
        return reporteddate;
    }

    public void setReporteddate(LocalDate reporteddate) {
        this.reporteddate = reporteddate;
    }

    public String getReportedby() {
        return reportedby;
    }

    public void setReportedby(String reportedby) {
        this.reportedby = reportedby;
    }

    public Character getProductionstop() {
        return productionstop;
    }

    public void setProductionstop(Character productionstop) {
        this.productionstop = productionstop;
    }

    public String getMachinecondition() {
        return machinecondition;
    }

    public void setMachinecondition(String machinecondition) {
        this.machinecondition = machinecondition;
    }

    public String getActivitytype() {
        return activitytype;
    }

    public void setActivitytype(String activitytype) {
        this.activitytype = activitytype;
    }

    public String getAlarmno() {
        return alarmno;
    }

    public void setAlarmno(String alarmno) {
        this.alarmno = alarmno;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getAssemblyid() {
        return assemblyid;
    }

    public void setAssemblyid(String assemblyid) {
        this.assemblyid = assemblyid;
    }

    public String getSubassemblyid() {
        return subassemblyid;
    }

    public void setSubassemblyid(String subassemblyid) {
        this.subassemblyid = subassemblyid;
    }

    public String getFailuretypeid() {
        return failuretypeid;
    }

    public void setFailuretypeid(String failuretypeid) {
        this.failuretypeid = failuretypeid;
    }

    public String getPartlocation() {
        return partlocation;
    }

    public void setPartlocation(String partlocation) {
        this.partlocation = partlocation;
    }

    public String getSpareid() {
        return spareid;
    }

    public void setSpareid(String spareid) {
        this.spareid = spareid;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getBookingremarks() {
        return bookingremarks;
    }

    public void setBookingremarks(String bookingremarks) {
        this.bookingremarks = bookingremarks;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Character getRequestapproved() {
        return requestapproved;
    }

    public void setRequestapproved(Character requestapproved) {
        this.requestapproved = requestapproved;
    }

    public String getRequestapprovedby() {
        return requestapprovedby;
    }

    public void setRequestapprovedby(String requestapprovedby) {
        this.requestapprovedby = requestapprovedby;
    }

    public LocalDate getRequestapproveddate() {
        return requestapproveddate;
    }

    public void setRequestapproveddate(LocalDate requestapproveddate) {
        this.requestapproveddate = requestapproveddate;
    }

    public String getRequestapprovremarks() {
        return requestapprovremarks;
    }

    public void setRequestapprovremarks(String requestapprovremarks) {
        this.requestapprovremarks = requestapprovremarks;
    }

    public LocalDate getAccepteddate() {
        return accepteddate;
    }

    public void setAccepteddate(LocalDate accepteddate) {
        this.accepteddate = accepteddate;
    }

    public Character getAcceptedflag() {
        return acceptedflag;
    }

    public void setAcceptedflag(Character acceptedflag) {
        this.acceptedflag = acceptedflag;
    }

    public String getAcceptedby() {
        return acceptedby;
    }

    public void setAcceptedby(String acceptedby) {
        this.acceptedby = acceptedby;
    }

    public String getAcceptedremarks() {
        return acceptedremarks;
    }

    public void setAcceptedremarks(String acceptedremarks) {
        this.acceptedremarks = acceptedremarks;
    }

    public Character getProductionapproval() {
        return productionapproval;
    }

    public void setProductionapproval(Character productionapproval) {
        this.productionapproval = productionapproval;
    }

    public Character getSafetypermitsrequried() {
        return safetypermitsrequried;
    }

    public void setSafetypermitsrequried(Character safetypermitsrequried) {
        this.safetypermitsrequried = safetypermitsrequried;
    }

    public String getSafetypermitid() {
        return safetypermitid;
    }

    public void setSafetypermitid(String safetypermitid) {
        this.safetypermitid = safetypermitid;
    }

    public Character getSafetypermitapproved() {
        return safetypermitapproved;
    }

    public void setSafetypermitapproved(Character safetypermitapproved) {
        this.safetypermitapproved = safetypermitapproved;
    }

    public Character getSafetypermitcompleted() {
        return safetypermitcompleted;
    }

    public void setSafetypermitcompleted(Character safetypermitcompleted) {
        this.safetypermitcompleted = safetypermitcompleted;
    }

    public Character getSafetypermitsignoff() {
        return safetypermitsignoff;
    }

    public void setSafetypermitsignoff(Character safetypermitsignoff) {
        this.safetypermitsignoff = safetypermitsignoff;
    }

    public Character getRescheduleflag() {
        return rescheduleflag;
    }

    public void setRescheduleflag(Character rescheduleflag) {
        this.rescheduleflag = rescheduleflag;
    }

    public LocalDate getRescheduledate() {
        return rescheduledate;
    }

    public void setRescheduledate(LocalDate rescheduledate) {
        this.rescheduledate = rescheduledate;
    }

    public String getRescheduledremarks() {
        return rescheduledremarks;
    }

    public void setRescheduledremarks(String rescheduledremarks) {
        this.rescheduledremarks = rescheduledremarks;
    }

    public Character getAllottedflag() {
        return allottedflag;
    }

    public void setAllottedflag(Character allottedflag) {
        this.allottedflag = allottedflag;
    }

    public LocalDate getAllotteddate() {
        return allotteddate;
    }

    public void setAllotteddate(LocalDate allotteddate) {
        this.allotteddate = allotteddate;
    }

    public String getAllottedto() {
        return allottedto;
    }

    public void setAllottedto(String allottedto) {
        this.allottedto = allottedto;
    }

    public String getAllottedremarks() {
        return allottedremarks;
    }

    public void setAllottedremarks(String allottedremarks) {
        this.allottedremarks = allottedremarks;
    }

    public Character getProposedstflag() {
        return proposedstflag;
    }

    public void setProposedstflag(Character proposedstflag) {
        this.proposedstflag = proposedstflag;
    }

    public Character getProposedendflag() {
        return proposedendflag;
    }

    public void setProposedendflag(Character proposedendflag) {
        this.proposedendflag = proposedendflag;
    }

    public LocalDate getProposedstartdate() {
        return proposedstartdate;
    }

    public void setProposedstartdate(LocalDate proposedstartdate) {
        this.proposedstartdate = proposedstartdate;
    }

    public LocalDate getProposedenddate() {
        return proposedenddate;
    }

    public void setProposedenddate(LocalDate proposedenddate) {
        this.proposedenddate = proposedenddate;
    }

    public Character getProposeddtacceptflag() {
        return proposeddtacceptflag;
    }

    public void setProposeddtacceptflag(Character proposeddtacceptflag) {
        this.proposeddtacceptflag = proposeddtacceptflag;
    }

    public Character getRescheduledstflag() {
        return rescheduledstflag;
    }

    public void setRescheduledstflag(Character rescheduledstflag) {
        this.rescheduledstflag = rescheduledstflag;
    }

    public Character getRescheduledendflag() {
        return rescheduledendflag;
    }

    public void setRescheduledendflag(Character rescheduledendflag) {
        this.rescheduledendflag = rescheduledendflag;
    }

    public String getRescheduleby() {
        return rescheduleby;
    }

    public void setRescheduleby(String rescheduleby) {
        this.rescheduleby = rescheduleby;
    }

    public LocalDate getReschedulestartdate() {
        return reschedulestartdate;
    }

    public void setReschedulestartdate(LocalDate reschedulestartdate) {
        this.reschedulestartdate = reschedulestartdate;
    }

    public LocalDate getRescheduleenddate() {
        return rescheduleenddate;
    }

    public void setRescheduleenddate(LocalDate rescheduleenddate) {
        this.rescheduleenddate = rescheduleenddate;
    }

    public String getRescheduleremarks() {
        return rescheduleremarks;
    }

    public void setRescheduleremarks(String rescheduleremarks) {
        this.rescheduleremarks = rescheduleremarks;
    }

    public Character getProductionstartflag() {
        return productionstartflag;
    }

    public void setProductionstartflag(Character productionstartflag) {
        this.productionstartflag = productionstartflag;
    }

    public LocalDate getProductionstartdate() {
        return productionstartdate;
    }

    public void setProductionstartdate(LocalDate productionstartdate) {
        this.productionstartdate = productionstartdate;
    }

    public String getProductionby() {
        return productionby;
    }

    public void setProductionby(String productionby) {
        this.productionby = productionby;
    }

    public String getProductionremarks() {
        return productionremarks;
    }

    public void setProductionremarks(String productionremarks) {
        this.productionremarks = productionremarks;
    }

    public Character getWorkstartflag() {
        return workstartflag;
    }

    public void setWorkstartflag(Character workstartflag) {
        this.workstartflag = workstartflag;
    }

    public LocalDate getWorkstartdate() {
        return workstartdate;
    }

    public void setWorkstartdate(LocalDate workstartdate) {
        this.workstartdate = workstartdate;
    }

    public Character getWorkendflag() {
        return workendflag;
    }

    public void setWorkendflag(Character workendflag) {
        this.workendflag = workendflag;
    }

    public LocalDate getWorkenddate() {
        return workenddate;
    }

    public void setWorkenddate(LocalDate workenddate) {
        this.workenddate = workenddate;
    }

    public String getDoneby() {
        return doneby;
    }

    public void setDoneby(String doneby) {
        this.doneby = doneby;
    }

    public Character getFinalactivitytype() {
        return finalactivitytype;
    }

    public void setFinalactivitytype(Character finalactivitytype) {
        this.finalactivitytype = finalactivitytype;
    }

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public Character getWoapprovalflag() {
        return woapprovalflag;
    }

    public void setWoapprovalflag(Character woapprovalflag) {
        this.woapprovalflag = woapprovalflag;
    }

    public String getWoapprovalby() {
        return woapprovalby;
    }

    public void setWoapprovalby(String woapprovalby) {
        this.woapprovalby = woapprovalby;
    }

    public LocalDate getWoapprovaldate() {
        return woapprovaldate;
    }

    public void setWoapprovaldate(LocalDate woapprovaldate) {
        this.woapprovaldate = woapprovaldate;
    }

    public Character getMachinereleaseflag() {
        return machinereleaseflag;
    }

    public void setMachinereleaseflag(Character machinereleaseflag) {
        this.machinereleaseflag = machinereleaseflag;
    }

    public LocalDate getMachinereleaseddate() {
        return machinereleaseddate;
    }

    public void setMachinereleaseddate(LocalDate machinereleaseddate) {
        this.machinereleaseddate = machinereleaseddate;
    }

    public String getMachinereleaseby() {
        return machinereleaseby;
    }

    public void setMachinereleaseby(String machinereleaseby) {
        this.machinereleaseby = machinereleaseby;
    }

    public Character getIntorextequip() {
        return intorextequip;
    }

    public void setIntorextequip(Character intorextequip) {
        this.intorextequip = intorextequip;
    }

    public String getIntorextequipdesc() {
        return intorextequipdesc;
    }

    public void setIntorextequipdesc(String intorextequipdesc) {
        this.intorextequipdesc = intorextequipdesc;
    }

    public String getStandbyadditionalinfo() {
        return standbyadditionalinfo;
    }

    public void setStandbyadditionalinfo(String standbyadditionalinfo) {
        this.standbyadditionalinfo = standbyadditionalinfo;
    }

    public String getStandbyremarks() {
        return standbyremarks;
    }

    public void setStandbyremarks(String standbyremarks) {
        this.standbyremarks = standbyremarks;
    }

    public Character getSentforrepairflag() {
        return sentforrepairflag;
    }

    public void setSentforrepairflag(Character sentforrepairflag) {
        this.sentforrepairflag = sentforrepairflag;
    }

    public String getSentrepairid() {
        return sentrepairid;
    }

    public void setSentrepairid(String sentrepairid) {
        this.sentrepairid = sentrepairid;
    }

    public String getSentto() {
        return sentto;
    }

    public void setSentto(String sentto) {
        this.sentto = sentto;
    }

    public LocalDate getExceptedreturndate() {
        return exceptedreturndate;
    }

    public void setExceptedreturndate(LocalDate exceptedreturndate) {
        this.exceptedreturndate = exceptedreturndate;
    }

    public String getRepairremarks() {
        return repairremarks;
    }

    public void setRepairremarks(String repairremarks) {
        this.repairremarks = repairremarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getJobopeningid() {
        return jobopeningid;
    }

    public void setJobopeningid(String jobopeningid) {
        this.jobopeningid = jobopeningid;
    }

    public String getFinalstatus() {
        return finalstatus;
    }

    public void setFinalstatus(String finalstatus) {
        this.finalstatus = finalstatus;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public BigDecimal getMaintpriority() {
        return maintpriority;
    }

    public void setMaintpriority(BigDecimal maintpriority) {
        this.maintpriority = maintpriority;
    }

    public String getAllottedsource() {
        return allottedsource;
    }

    public void setAllottedsource(String allottedsource) {
        this.allottedsource = allottedsource;
    }

    public String getAllottedsupplier() {
        return allottedsupplier;
    }

    public void setAllottedsupplier(String allottedsupplier) {
        this.allottedsupplier = allottedsupplier;
    }

    public BigDecimal getNumofactivities() {
        return numofactivities;
    }

    public void setNumofactivities(BigDecimal numofactivities) {
        this.numofactivities = numofactivities;
    }

    public String getPwdmwono() {
        return pwdmwono;
    }

    public void setPwdmwono(String pwdmwono) {
        this.pwdmwono = pwdmwono;
    }

    public String getRefdoctype() {
        return refdoctype;
    }

    public void setRefdoctype(String refdoctype) {
        this.refdoctype = refdoctype;
    }

    public String getRefdocid() {
        return refdocid;
    }

    public void setRefdocid(String refdocid) {
        this.refdocid = refdocid;
    }

    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }

    public String getRequiredstart() {
        return requiredstart;
    }

    public void setRequiredstart(String requiredstart) {
        this.requiredstart = requiredstart;
    }

    public String getRequiredend() {
        return requiredend;
    }

    public void setRequiredend(String requiredend) {
        this.requiredend = requiredend;
    }

    public String getPlannergroup() {
        return plannergroup;
    }

    public void setPlannergroup(String plannergroup) {
        this.plannergroup = plannergroup;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(String tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
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
}