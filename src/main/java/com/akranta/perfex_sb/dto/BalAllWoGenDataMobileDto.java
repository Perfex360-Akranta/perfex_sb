package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

public class BalAllWoGenDataMobileDto 
{
    String machineId;
    LocalDateTime fromDate;;
    LocalDateTime toDate;
    public String getMachineId() {
        return machineId;
    }
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
    public LocalDateTime getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }
    public LocalDateTime getToDate() {
        return toDate;
    }
    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    

}
