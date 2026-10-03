package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "plm_tl_genmaintenance", schema = "public")
public class PlmTlGenmaintenance {

    @Id
    @Column(name = "gmnt_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "gmnt_occureddate", nullable = false)
    private LocalDateTime occureddate;

    @Column(name = "gmnt_shiftdate", nullable = false)
    private LocalDateTime shiftdate;

    @Column(name = "gmnt_bookeddate", nullable = false)
    private LocalDateTime bookeddate;

    @Column(name = "gmnt_receiveddate", nullable = false)
    private LocalDateTime receiveddate;

    @Column(name = "gmnt_allocateddate", nullable = false)
    private LocalDateTime allocateddate;

    @Column(name = "gmnt_wostartdate", nullable = false)
    private LocalDateTime wostartdate;

    @Column(name = "gmnt_woenddate", nullable = false)
    private LocalDateTime woenddate;

    @Column(name = "gmnt_responsetime", nullable = false)
    private BigDecimal responsetime;

    @Column(name = "gmnt_workhours", nullable = false)
    private BigDecimal workhours;

    @Column(name = "gmnt_downtime", nullable = false)
    private BigDecimal downtime;

    @Column(name = "gmnt_refdoctype", length = 6, nullable = false)
    private String refdoctype;

    @Column(name = "gmnt_refdocid", length = 20, nullable = false)
    private String refdocid;

    @Column(name = "gmnt_factoryid", length = 10, nullable = false)
    private String factoryid;

    @Column(name = "gmnt_sectionid", length = 10, nullable = false)
    private String sectionid;

    @Column(name = "gmnt_lineid", length = 12, nullable = false)
    private String lineid;

    @Column(name = "gmnt_machineid", length = 10, nullable = false)
    private String machineid;

    @Column(name = "gmnt_stationid", length = 12, nullable = false)
    private String stationid;

    @Column(name = "gmnt_phenid", length = 10, nullable = false)
    private String phenid;

    @Column(name = "gmnt_rootcauseid", length = 8, nullable = false)
    private String rootcauseid;

    @Column(name = "gmnt_shift", length = 12, nullable = false)
    private String shift;

    @Column(name = "gmnt_trade", length = 15, nullable = false)
    private String trade;

    @Column(name = "gmnt_partlocation", length = 100, nullable = false)
    private String partlocation;

    @Column(name = "gmnt_activitytype", length = 15, nullable = false)
    private String activitytype;

    @Column(name = "gmnt_mchcondition", length = 10, nullable = false)
    private String mchcondition;

    @Column(name = "gmnt_manpowercost", nullable = false)
    private BigDecimal manpowercost;

    @Column(name = "gmnt_contractorcost", nullable = false)
    private BigDecimal contractorcost;

    @Column(name = "gmnt_othercost", nullable = false)
    private BigDecimal othercost;

    @Column(name = "gmnt_sparecost", length = 12, nullable = false)
    private String sparecost;

    @Column(name = "gmnt_problem", length = 500, nullable = false)
    private String problem;

    @Column(name = "gmnt_rootcause", length = 500, nullable = false)
    private String rootcause;

    @Column(name = "gmnt_countermeasure", length = 500, nullable = false)
    private String countermeasure;

    @Column(name = "gmnt_action", length = 500, nullable = false)
    private String action;

    @Column(name = "gmnt_reportedby", length = 12, nullable = false)
    private String reportedby;

    @Column(name = "gmnt_targetdate", nullable = false)
    private LocalDateTime targetdate;

    @Column(name = "gmnt_status", length = 1, nullable = false)
    private String status;

    @Column(name = "gmnt_isyy", length = 1, nullable = false)
    private String isyy;

    @Column(name = "gmnt_yyno", length = 12, nullable = false)
    private String yyno;

    @Column(name = "gmnt_completedby", length = 12, nullable = false)
    private String completedby;

    @Column(name = "gmnt_completeddate", nullable = false)
    private LocalDateTime completeddate;

    @Column(name = "gmnt_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "gmnt_pctrmeasure", length = 500, nullable = false)
    private String pctrmeasure;

    @Column(name = "gmnt_relatedto", length = 3, nullable = false)
    private String relatedto;

    @Column(name = "gmnt_mouldid", length = 10, nullable = false)
    private String mouldid;

    // FIX: DB allows nullable for tempfield1-4 (no NOT NULL constraint)
    @Column(name = "gmnt_tempfield1", length = 1, nullable = true)
    private String tempfield1;

    @Column(name = "gmnt_tempfield2", length = 1, nullable = true)
    private String tempfield2;

    @Column(name = "gmnt_tempfield3", length = 1, nullable = true)
    private String tempfield3;

    @Column(name = "gmnt_tempfield4", length = 1, nullable = true)
    private String tempfield4;

    @Column(name = "gmnt_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "gmnt_tempfield6", length = 1, nullable = false)
    private String tempfield6;

    @Column(name = "gmnt_tempfield7", length = 1, nullable = false)
    private String tempfield7;

    @Column(name = "gmnt_tempfield8", length = 1, nullable = false)
    private String tempfield8;

    @Column(name = "gmnt_tempfield9", length = 1, nullable = false)
    private String tempfield9;

    // FIX: DB defines this as character varying(100), NOT length = 1
    @Column(name = "gmnt_tempfield10", length = 100, nullable = false)
    private String tempfield10;

    @Column(name = "gmnt_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "gmnt_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "gmnt_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "gmnt_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "gmnt_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "gmnt_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    @Column(name = "gmnt_ordertype", length = 50)
    private String ordertype;

    // FIX: DB column is gmnt_erppoststatus (not errp...), length = 10
    @Column(name = "gmnt_erppoststatus", length = 10)
    @JsonProperty("errppostStatus")
    private String erppoststatus;

    // FIX: DB column is gmnt_erpnumber (not errppostno), length = 50
    @Column(name = "gmnt_erpnumber", length = 50)
    @JsonProperty("errppostNo")
    private String erpnumber;

    // FIX: DB column is gmnt_sparesreplaced (not isspares), length = 5
    @Column(name = "gmnt_sparesreplaced", length = 5)
    @JsonProperty("isSpares")
    private String sparesreplaced;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public LocalDateTime getOccureddate() {
        return occureddate;
    }

    public void setOccureddate(LocalDateTime occureddate) {
        this.occureddate = occureddate;
    }

    public LocalDateTime getShiftdate() {
        return shiftdate;
    }

    public void setShiftdate(LocalDateTime shiftdate) {
        this.shiftdate = shiftdate;
    }

    public LocalDateTime getBookeddate() {
        return bookeddate;
    }

    public void setBookeddate(LocalDateTime bookeddate) {
        this.bookeddate = bookeddate;
    }

    public LocalDateTime getReceiveddate() {
        return receiveddate;
    }

    public void setReceiveddate(LocalDateTime receiveddate) {
        this.receiveddate = receiveddate;
    }

    public LocalDateTime getAllocateddate() {
        return allocateddate;
    }

    public void setAllocateddate(LocalDateTime allocateddate) {
        this.allocateddate = allocateddate;
    }

    public LocalDateTime getWostartdate() {
        return wostartdate;
    }

    public void setWostartdate(LocalDateTime wostartdate) {
        this.wostartdate = wostartdate;
    }

    public LocalDateTime getWoenddate() {
        return woenddate;
    }

    public void setWoenddate(LocalDateTime woenddate) {
        this.woenddate = woenddate;
    }

    public BigDecimal getResponsetime() {
        return responsetime;
    }

    public void setResponsetime(BigDecimal responsetime) {
        this.responsetime = responsetime;
    }

    public BigDecimal getWorkhours() {
        return workhours;
    }

    public void setWorkhours(BigDecimal workhours) {
        this.workhours = workhours;
    }

    public BigDecimal getDowntime() {
        return downtime;
    }

    public void setDowntime(BigDecimal downtime) {
        this.downtime = downtime;
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

    public String getLineid() {
        return lineid;
    }

    public void setLineid(String lineid) {
        this.lineid = lineid;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }

    public String getPhenid() {
        return phenid;
    }

    public void setPhenid(String phenid) {
        this.phenid = phenid;
    }

    public String getRootcauseid() {
        return rootcauseid;
    }

    public void setRootcauseid(String rootcauseid) {
        this.rootcauseid = rootcauseid;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getPartlocation() {
        return partlocation;
    }

    public void setPartlocation(String partlocation) {
        this.partlocation = partlocation;
    }

    public String getActivitytype() {
        return activitytype;
    }

    public void setActivitytype(String activitytype) {
        this.activitytype = activitytype;
    }

    public String getMchcondition() {
        return mchcondition;
    }

    public void setMchcondition(String mchcondition) {
        this.mchcondition = mchcondition;
    }

    public BigDecimal getManpowercost() {
        return manpowercost;
    }

    public void setManpowercost(BigDecimal manpowercost) {
        this.manpowercost = manpowercost;
    }

    public BigDecimal getContractorcost() {
        return contractorcost;
    }

    public void setContractorcost(BigDecimal contractorcost) {
        this.contractorcost = contractorcost;
    }

    public BigDecimal getOthercost() {
        return othercost;
    }

    public void setOthercost(BigDecimal othercost) {
        this.othercost = othercost;
    }

    public String getSparecost() {
        return sparecost;
    }

    public void setSparecost(String sparecost) {
        this.sparecost = sparecost;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getReportedby() {
        return reportedby;
    }

    public void setReportedby(String reportedby) {
        this.reportedby = reportedby;
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

    public String getIsyy() {
        return isyy;
    }

    public void setIsyy(String isyy) {
        this.isyy = isyy;
    }

    public String getYyno() {
        return yyno;
    }

    public void setYyno(String yyno) {
        this.yyno = yyno;
    }

    public String getCompletedby() {
        return completedby;
    }

    public void setCompletedby(String completedby) {
        this.completedby = completedby;
    }

    public LocalDateTime getCompleteddate() {
        return completeddate;
    }

    public void setCompleteddate(LocalDateTime completeddate) {
        this.completeddate = completeddate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPctrmeasure() {
        return pctrmeasure;
    }

    public void setPctrmeasure(String pctrmeasure) {
        this.pctrmeasure = pctrmeasure;
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

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getErppoststatus() {
        return erppoststatus;
    }

    public void setErppoststatus(String erppoststatus) {
        this.erppoststatus = erppoststatus;
    }

    public String getErpnumber() {
        return erpnumber;
    }

    public void setErpnumber(String erpnumber) {
        this.erpnumber = erpnumber;
    }

    public String getSparesreplaced() {
        return sparesreplaced;
    }

    public void setSparesreplaced(String sparesreplaced) {
        this.sparesreplaced = sparesreplaced;
    }
}
    