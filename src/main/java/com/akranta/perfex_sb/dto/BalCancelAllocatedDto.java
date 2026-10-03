package com.akranta.perfex_sb.dto;

import java.util.List;

public class BalCancelAllocatedDto 
{
       private String workOrderNo;
    private List<String> cancelWoIds;
    private Integer noOfActivities;
    public String getWorkOrderNo() {
        return workOrderNo;
    }
    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }
    public List<String> getCancelWoIds() {
        return cancelWoIds;
    }
    public void setCancelWoIds(List<String> cancelWoIds) {
        this.cancelWoIds = cancelWoIds;
    }
    public Integer getNoOfActivities() {
        return noOfActivities;
    }
    public void setNoOfActivities(Integer noOfActivities) {
        this.noOfActivities = noOfActivities;
    }

    


}
