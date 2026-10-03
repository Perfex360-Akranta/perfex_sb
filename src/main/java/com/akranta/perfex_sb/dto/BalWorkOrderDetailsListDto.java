package com.akranta.perfex_sb.dto;

import java.io.Serializable;

import com.akranta.perfex_sb.model.BalPlmTlSpareconsumed;
import com.akranta.perfex_sb.model.BalPlmTlSparecostactual;


public class BalWorkOrderDetailsListDto 
{




    private String wofbStarttime;
    private String wofbEndtime;
    private String wofbProdStarttime;

    private String wofbObservation;
    private String wofbRemarks;

    private String obsvResponsibility;
    private String obsvTargetDate;

    private String pmCalendarId;
    private String fromMonth;
    private String assmbleyId;
    private String WOmstId;
    private String pmstandId;
    private String machineId;

    private String jobtype;
    private String plannedduration;

    private String WofbAction;
    private String WofbDuration;
    private String WksmWodetailid;

    private String fromDate;
    private String WofbCompletedby;

    private String cbmReading;
    private String cbmNextDueDate;
    private String cbmMinReading;
    private String cbmMaxReading;
    private String cbmAdjustedReading;

    private BalPlmTlSpareconsumed spareConsumed;
    private BalPlmTlSparecostactual spareCostActual;
    public String getWofbStarttime() {
        return wofbStarttime;
    }
    public void setWofbStarttime(String wofbStarttime) {
        this.wofbStarttime = wofbStarttime;
    }
    public String getWofbEndtime() {
        return wofbEndtime;
    }
    public void setWofbEndtime(String wofbEndtime) {
        this.wofbEndtime = wofbEndtime;
    }
    public String getWofbProdStarttime() {
        return wofbProdStarttime;
    }
    public void setWofbProdStarttime(String wofbProdStarttime) {
        this.wofbProdStarttime = wofbProdStarttime;
    }
    public String getWofbObservation() {
        return wofbObservation;
    }
    public void setWofbObservation(String wofbObservation) {
        this.wofbObservation = wofbObservation;
    }
    public String getWofbRemarks() {
        return wofbRemarks;
    }
    public void setWofbRemarks(String wofbRemarks) {
        this.wofbRemarks = wofbRemarks;
    }
    public String getObsvResponsibility() {
        return obsvResponsibility;
    }
    public void setObsvResponsibility(String obsvResponsibility) {
        this.obsvResponsibility = obsvResponsibility;
    }
    public String getObsvTargetDate() {
        return obsvTargetDate;
    }
    public void setObsvTargetDate(String obsvTargetDate) {
        this.obsvTargetDate = obsvTargetDate;
    }
    public String getPmCalendarId() {
        return pmCalendarId;
    }
    public void setPmCalendarId(String pmCalendarId) {
        this.pmCalendarId = pmCalendarId;
    }
    public String getFromMonth() {
        return fromMonth;
    }
    public void setFromMonth(String fromMonth) {
        this.fromMonth = fromMonth;
    }
    public String getAssmbleyId() {
        return assmbleyId;
    }
    public void setAssmbleyId(String assmbleyId) {
        this.assmbleyId = assmbleyId;
    }
    public String getWOmstId() {
        return WOmstId;
    }
    public void setWOmstId(String wOmstId) {
        WOmstId = wOmstId;
    }
    public String getPmstandId() {
        return pmstandId;
    }
    public void setPmstandId(String pmstandId) {
        this.pmstandId = pmstandId;
    }
    public String getMachineId() {
        return machineId;
    }
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
    public String getJobtype() {
        return jobtype;
    }
    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }
    public String getPlannedduration() {
        return plannedduration;
    }
    public void setPlannedduration(String plannedduration) {
        this.plannedduration = plannedduration;
    }
    public String getWofbAction() {
        return WofbAction;
    }
    public void setWofbAction(String wofbAction) {
        WofbAction = wofbAction;
    }
    public String getWofbDuration() {
        return WofbDuration;
    }
    public void setWofbDuration(String wofbDuration) {
        WofbDuration = wofbDuration;
    }
    public String getWksmWodetailid() {
        return WksmWodetailid;
    }
    public void setWksmWodetailid(String wksmWodetailid) {
        WksmWodetailid = wksmWodetailid;
    }
    public String getFromDate() {
        return fromDate;
    }
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    public String getWofbCompletedby() {
        return WofbCompletedby;
    }
    public void setWofbCompletedby(String wofbCompletedby) {
        WofbCompletedby = wofbCompletedby;
    }
    public String getCbmReading() {
        return cbmReading;
    }
    public void setCbmReading(String cbmReading) {
        this.cbmReading = cbmReading;
    }
    public String getCbmNextDueDate() {
        return cbmNextDueDate;
    }
    public void setCbmNextDueDate(String cbmNextDueDate) {
        this.cbmNextDueDate = cbmNextDueDate;
    }
    public String getCbmMinReading() {
        return cbmMinReading;
    }
    public void setCbmMinReading(String cbmMinReading) {
        this.cbmMinReading = cbmMinReading;
    }
    public String getCbmMaxReading() {
        return cbmMaxReading;
    }
    public void setCbmMaxReading(String cbmMaxReading) {
        this.cbmMaxReading = cbmMaxReading;
    }
    public String getCbmAdjustedReading() {
        return cbmAdjustedReading;
    }
    public void setCbmAdjustedReading(String cbmAdjustedReading) {
        this.cbmAdjustedReading = cbmAdjustedReading;
    }
    public BalPlmTlSpareconsumed getSpareConsumed() {
        return spareConsumed;
    }
    public void setSpareConsumed(BalPlmTlSpareconsumed spareConsumed) {
        this.spareConsumed = spareConsumed;
    }
    public BalPlmTlSparecostactual getSpareCostActual() {
        return spareCostActual;
    }
    public void setSpareCostActual(BalPlmTlSparecostactual spareCostActual) {
        this.spareCostActual = spareCostActual;
    }

    









}
