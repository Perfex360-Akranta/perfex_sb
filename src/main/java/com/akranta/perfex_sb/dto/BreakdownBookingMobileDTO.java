package com.akranta.perfex_sb.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class BreakdownBookingMobileDTO {

    private String keyid;
    private String machineid;
    private String flid;

    @JsonAlias({ "problemdescription" })
    private String problemDescription;

    private String priority;

    @JsonAlias({ "occurredon" })
    private String occurredOn;

    @JsonAlias({ "assemblyStation" })
    private String assemblyid;

    @JsonAlias({ "subStation" })
    private String subassemblyid;

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

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(String occurredOn) {
        this.occurredOn = occurredOn;
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
