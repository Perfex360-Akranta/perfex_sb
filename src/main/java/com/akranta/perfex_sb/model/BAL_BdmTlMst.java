package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bal_bdm_tl_mst", schema = "public")
public class BAL_BdmTlMst {

    @Id
    @Column(name = "bdms_keyid", length = 20, nullable = false)
    private String keyid;

    @Column(name = "bdms_entrydate", nullable = false)
    private LocalDateTime entrydate;

    @Column(name = "bdms_shiftid", length = 6, nullable = false)
    private String shiftid;

    @Column(name = "bdms_factoryid", length = 10, nullable = false)
    private String factoryid;

    @Column(name = "bdms_sectionid", length = 10, nullable = false)
    private String sectionid;

    @Column(name = "bdms_cellid", length = 10, nullable = false)
    private String cellid;

    @Column(name = "bdms_machineid", length = 10, nullable = false)
    private String machineid;

    @Column(name = "bdms_assemblyid", length = 10, nullable = false)
    private String assemblyid;

    @Column(name = "bdms_partlocationid", length = 50, nullable = false)
    private String partlocationid;

    @Column(name = "bdms_alarmdescription", length = 100, nullable = false)
    private String alarmdescription;

    @Column(name = "bdms_bdtype", length = 1, nullable = false)
    private Character bdtype;

    @Column(name = "bdms_reporteddate", nullable = false)
    private LocalDateTime reporteddate;

    @Column(name = "bdms_receiveddate", nullable = false)
    private LocalDateTime receiveddate;

    @Column(name = "bdms_wostarttime", nullable = false)
    private LocalDateTime wostarttime;

    @Column(name = "bdms_woendtime", nullable = false)
    private LocalDateTime woendtime;

    @Column(name = "bdms_breaktime", nullable = false)
    private BigDecimal breaktime;

    @Column(name = "bdms_actualworktime", nullable = false)
    private BigDecimal actualworktime;

    @Column(name = "bdms_downtime", nullable = false)
    private BigDecimal downtime;

    @Column(name = "bdms_prodaccepdate", nullable = false)
    private LocalDateTime prodaccepdate;

    @Column(name = "bdms_bookedphenomena", length = 8, nullable = false)
    private String bookedphenomena;

    @Column(name = "bdms_phenomenadescription", length = 600, nullable = false)
    private String phenomenadescription;

    @Column(name = "bdms_bookedcause", length = 9, nullable = false)
    private String bookedcause;

    @Column(name = "bdms_finalphenomena", length = 15, nullable = false)
    private String finalphenomena;

    @Column(name = "bdms_finalcause", length = 9, nullable = false)
    private String finalcause;

    @Column(name = "bdms_bookedtrade", length = 15, nullable = false)
    private String bookedtrade;

    @Column(name = "bdms_finaltrade", length = 15, nullable = false)
    private String finaltrade;

    @Column(name = "bdms_problemdescription", length = 600, nullable = false)
    private String problemdescription;

    @Column(name = "bdms_isbdlocked", length = 1, nullable = false)
    private Character isbdlocked;

    @Column(name = "bdms_shiftincharge", length = 8, nullable = false)
    private String shiftincharge;

    @Column(name = "bdms_status", length = 1, nullable = false)
    private Character status;

    @Column(name = "bdms_bookedby", length = 8, nullable = false)
    private String bookedby;

    @Column(name = "bdms_remarks", length = 600, nullable = false)
    private String remarks;

    @Column(name = "bdms_bookingtype", length = 3, nullable = false)
    private String bookingtype;

    @Column(name = "bdms_bdrelatedto", length = 3, nullable = false)
    private String bdrelatedto = "MCH";

    @Column(name = "bdms_wno", length = 20, nullable = false)
    private String wno;

    @Column(name = "bdms_spareid", length = 12, nullable = false)
    private String spareid;

    @Column(name = "bdms_priority", length = 1, nullable = false)
    private Character priority;

    @Column(name = "bdms_woallottedflag", length = 1, nullable = false)
    private Character woallottedflag;

    @Column(name = "bdms_wostartflag", length = 1, nullable = false)
    private Character wostartflag;

    @Column(name = "bdms_woendflag", length = 1, nullable = false)
    private Character woendflag;

    @Column(name = "bdms_woprodaccepflag", length = 1, nullable = false)
    private Character woprodaccepflag;

    @Column(name = "bdms_subassemblyid", length = 15, nullable = false)
    private String subassemblyid;

    @Column(name = "bdms_repeatedbdflag", length = 500, nullable = false)
    private String repeatedbdflag;

    @Column(name = "bdms_repeatedbdno", length = 500, nullable = false)
    private String repeatedbdno;

    @Column(name = "bdms_relatedto", length = 3, nullable = false)
    private String relatedto;

    @Column(name = "bdms_mould", length = 30, nullable = false)
    private String mould;

    @Column(name = "bdms_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "bdms_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "bdms_processid", length = 20, nullable = false)
    private String processid;

    @Column(name = "bdms_isstandby", length = 1, nullable = false)
    private Character isstandby;

    @Column(name = "bdms_standbyequipment", length = 15, nullable = false)
    private String standbyequipment;

    @Column(name = "bdms_breakdowntime", nullable = false)
    private BigDecimal breakdowntime;

    @Column(name = "bdms_productionstop", length = 1, nullable = false)
    private Character productionstop;

    @Column(name = "bdms_immediateaction", length = 500, nullable = false)
    private String immediateaction;

    @Column(name = "bdms_completeddate", nullable = false)
    private LocalDateTime completeddate;

    @Column(name = "bdms_problemreason", length = 500, nullable = false)
    private String problemreason;

    @Column(name = "bdms_activity", length = 1, nullable = false)
    private Character activity;

    @Column(name = "bdms_otherphenomena", length = 150, nullable = false)
    private String otherphenomena;

    @Column(name = "bdms_tempfield7", length = 1, nullable = false)
    private Character tempfield7;

    @Column(name = "bdms_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "bdms_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "bdms_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "bdms_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public LocalDateTime getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(LocalDateTime entrydate) {
        this.entrydate = entrydate;
    }

    public String getShiftid() {
        return shiftid;
    }

    public void setShiftid(String shiftid) {
        this.shiftid = shiftid;
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

    public String getAssemblyid() {
        return assemblyid;
    }

    public void setAssemblyid(String assemblyid) {
        this.assemblyid = assemblyid;
    }

    public String getPartlocationid() {
        return partlocationid;
    }

    public void setPartlocationid(String partlocationid) {
        this.partlocationid = partlocationid;
    }

    public String getAlarmdescription() {
        return alarmdescription;
    }

    public void setAlarmdescription(String alarmdescription) {
        this.alarmdescription = alarmdescription;
    }

    public Character getBdtype() {
        return bdtype;
    }

    public void setBdtype(Character bdtype) {
        this.bdtype = bdtype;
    }

    public LocalDateTime getReporteddate() {
        return reporteddate;
    }

    public void setReporteddate(LocalDateTime reporteddate) {
        this.reporteddate = reporteddate;
    }

    public LocalDateTime getReceiveddate() {
        return receiveddate;
    }

    public void setReceiveddate(LocalDateTime receiveddate) {
        this.receiveddate = receiveddate;
    }

    public LocalDateTime getWostarttime() {
        return wostarttime;
    }

    public void setWostarttime(LocalDateTime wostarttime) {
        this.wostarttime = wostarttime;
    }

    public LocalDateTime getWoendtime() {
        return woendtime;
    }

    public void setWoendtime(LocalDateTime woendtime) {
        this.woendtime = woendtime;
    }

    public BigDecimal getBreaktime() {
        return breaktime;
    }

    public void setBreaktime(BigDecimal breaktime) {
        this.breaktime = breaktime;
    }

    public BigDecimal getActualworktime() {
        return actualworktime;
    }

    public void setActualworktime(BigDecimal actualworktime) {
        this.actualworktime = actualworktime;
    }

    public BigDecimal getDowntime() {
        return downtime;
    }

    public void setDowntime(BigDecimal downtime) {
        this.downtime = downtime;
    }

    public LocalDateTime getProdaccepdate() {
        return prodaccepdate;
    }

    public void setProdaccepdate(LocalDateTime prodaccepdate) {
        this.prodaccepdate = prodaccepdate;
    }

    public String getBookedphenomena() {
        return bookedphenomena;
    }

    public void setBookedphenomena(String bookedphenomena) {
        this.bookedphenomena = bookedphenomena;
    }

    public String getPhenomenadescription() {
        return phenomenadescription;
    }

    public void setPhenomenadescription(String phenomenadescription) {
        this.phenomenadescription = phenomenadescription;
    }

    public String getBookedcause() {
        return bookedcause;
    }

    public void setBookedcause(String bookedcause) {
        this.bookedcause = bookedcause;
    }

    public String getFinalphenomena() {
        return finalphenomena;
    }

    public void setFinalphenomena(String finalphenomena) {
        this.finalphenomena = finalphenomena;
    }

    public String getFinalcause() {
        return finalcause;
    }

    public void setFinalcause(String finalcause) {
        this.finalcause = finalcause;
    }

    public String getBookedtrade() {
        return bookedtrade;
    }

    public void setBookedtrade(String bookedtrade) {
        this.bookedtrade = bookedtrade;
    }

    public String getFinaltrade() {
        return finaltrade;
    }

    public void setFinaltrade(String finaltrade) {
        this.finaltrade = finaltrade;
    }

    public String getProblemdescription() {
        return problemdescription;
    }

    public void setProblemdescription(String problemdescription) {
        this.problemdescription = problemdescription;
    }

    public Character getIsbdlocked() {
        return isbdlocked;
    }

    public void setIsbdlocked(Character isbdlocked) {
        this.isbdlocked = isbdlocked;
    }

    public String getShiftincharge() {
        return shiftincharge;
    }

    public void setShiftincharge(String shiftincharge) {
        this.shiftincharge = shiftincharge;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getBookedby() {
        return bookedby;
    }

    public void setBookedby(String bookedby) {
        this.bookedby = bookedby;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBookingtype() {
        return bookingtype;
    }

    public void setBookingtype(String bookingtype) {
        this.bookingtype = bookingtype;
    }

    public String getBdrelatedto() {
        return bdrelatedto;
    }

    public void setBdrelatedto(String bdrelatedto) {
        this.bdrelatedto = bdrelatedto;
    }

    public String getWno() {
        return wno;
    }

    public void setWno(String wno) {
        this.wno = wno;
    }

    public String getSpareid() {
        return spareid;
    }

    public void setSpareid(String spareid) {
        this.spareid = spareid;
    }

    public Character getPriority() {
        return priority;
    }

    public void setPriority(Character priority) {
        this.priority = priority;
    }

    public Character getWoallottedflag() {
        return woallottedflag;
    }

    public void setWoallottedflag(Character woallottedflag) {
        this.woallottedflag = woallottedflag;
    }

    public Character getWostartflag() {
        return wostartflag;
    }

    public void setWostartflag(Character wostartflag) {
        this.wostartflag = wostartflag;
    }

    public Character getWoendflag() {
        return woendflag;
    }

    public void setWoendflag(Character woendflag) {
        this.woendflag = woendflag;
    }

    public Character getWoprodaccepflag() {
        return woprodaccepflag;
    }

    public void setWoprodaccepflag(Character woprodaccepflag) {
        this.woprodaccepflag = woprodaccepflag;
    }

    public String getSubassemblyid() {
        return subassemblyid;
    }

    public void setSubassemblyid(String subassemblyid) {
        this.subassemblyid = subassemblyid;
    }

    public String getRepeatedbdflag() {
        return repeatedbdflag;
    }

    public void setRepeatedbdflag(String repeatedbdflag) {
        this.repeatedbdflag = repeatedbdflag;
    }

    public String getRepeatedbdno() {
        return repeatedbdno;
    }

    public void setRepeatedbdno(String repeatedbdno) {
        this.repeatedbdno = repeatedbdno;
    }

    public String getRelatedto() {
        return relatedto;
    }

    public void setRelatedto(String relatedto) {
        this.relatedto = relatedto;
    }

    public String getMould() {
        return mould;
    }

    public void setMould(String mould) {
        this.mould = mould;
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

    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }

    public Character getIsstandby() {
        return isstandby;
    }

    public void setIsstandby(Character isstandby) {
        this.isstandby = isstandby;
    }

    public String getStandbyequipment() {
        return standbyequipment;
    }

    public void setStandbyequipment(String standbyequipment) {
        this.standbyequipment = standbyequipment;
    }

    public BigDecimal getBreakdowntime() {
        return breakdowntime;
    }

    public void setBreakdowntime(BigDecimal breakdowntime) {
        this.breakdowntime = breakdowntime;
    }

    public Character getProductionstop() {
        return productionstop;
    }

    public void setProductionstop(Character productionstop) {
        this.productionstop = productionstop;
    }

    public String getImmediateaction() {
        return immediateaction;
    }

    public void setImmediateaction(String immediateaction) {
        this.immediateaction = immediateaction;
    }

    public LocalDateTime getCompleteddate() {
        return completeddate;
    }

    public void setCompleteddate(LocalDateTime completeddate) {
        this.completeddate = completeddate;
    }

    public String getProblemreason() {
        return problemreason;
    }

    public void setProblemreason(String problemreason) {
        this.problemreason = problemreason;
    }

    public Character getActivity() {
        return activity;
    }

    public void setActivity(Character activity) {
        this.activity = activity;
    }

    public String getOtherphenomena() {
        return otherphenomena;
    }

    public void setOtherphenomena(String otherphenomena) {
        this.otherphenomena = otherphenomena;
    }

    public Character getTempfield7() {
        return tempfield7;
    }

    public void setTempfield7(Character tempfield7) {
        this.tempfield7 = tempfield7;
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