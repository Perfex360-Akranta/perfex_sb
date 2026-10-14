package com.akranta.perfex_sb.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class GenmaintenanceBookingMobileDTO {

    private String keyid;

    @JsonAlias({ "gmntMachineid" })
    private String machineid;

    @JsonAlias({ "gmntFlid" })
    private String flid;

    @JsonAlias({ "gmntShiftdate" })
    private String shiftdate;

    @JsonAlias({ "gmntShift" })
    private String shift;

    @JsonAlias({ "gmntOccureddate", "occurredOn" })
    private String occureddate;

    @JsonAlias({ "occurredTime" })
    private String occurredTime;

    @JsonAlias({ "gmntStationid", "assemblyStation" })
    private String stationid;

    @JsonAlias({ "gmntPartlocation" })
    private String partlocation;

    @JsonAlias({ "gmntActivitytype" })
    private String activitytype;

    @JsonAlias({ "gmntTrade" })
    private String trade;

    @JsonAlias({ "gmntMchcondition" })
    private String mchcondition;

    @JsonAlias({ "isSpares", "gmntSparesreplaced" })
    private String isSpares;

    @JsonAlias({ "gmntProblem" })
    private String problem;

    @JsonAlias({ "gmntAction" })
    private String action;

    @JsonAlias({ "gmntRootcause" })
    private String rootcause;

    @JsonAlias({ "gmntCountermeasure" })
    private String countermeasure;

    @JsonAlias({ "gmntStatus" })
    private String status;

    @JsonAlias({ "gmntTargetdate" })
    private String targetdate;

    @JsonAlias({ "cbobdmsPriority", "gmntPriority" })
    private String priority;

    @JsonAlias({ "gmntWorkhours" })
    private String workhours;

    @JsonAlias({ "gmntDowntime" })
    private String downtime;

    @JsonAlias({ "gmntResponsetime" })
    private String responsetime;

    private String bookedby;
    private String roleid;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getShiftdate() {
        return shiftdate;
    }

    public void setShiftdate(String shiftdate) {
        this.shiftdate = shiftdate;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getOccureddate() {
        return occureddate;
    }

    public void setOccureddate(String occureddate) {
        this.occureddate = occureddate;
    }

    public String getOccurredTime() {
        return occurredTime;
    }

    public void setOccurredTime(String occurredTime) {
        this.occurredTime = occurredTime;
    }

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
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

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getMchcondition() {
        return mchcondition;
    }

    public void setMchcondition(String mchcondition) {
        this.mchcondition = mchcondition;
    }

    public String getIsSpares() {
        return isSpares;
    }

    public void setIsSpares(String isSpares) {
        this.isSpares = isSpares;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(String targetdate) {
        this.targetdate = targetdate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getWorkhours() {
        return workhours;
    }

    public void setWorkhours(String workhours) {
        this.workhours = workhours;
    }

    public String getDowntime() {
        return downtime;
    }

    public void setDowntime(String downtime) {
        this.downtime = downtime;
    }

    public String getResponsetime() {
        return responsetime;
    }

    public void setResponsetime(String responsetime) {
        this.responsetime = responsetime;
    }

    public String getBookedby() {
        return bookedby;
    }

    public void setBookedby(String bookedby) {
        this.bookedby = bookedby;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
}
