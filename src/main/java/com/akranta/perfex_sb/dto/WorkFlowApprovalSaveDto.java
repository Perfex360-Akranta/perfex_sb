package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.GenTlWorkFlowInfo;

public class WorkFlowApprovalSaveDto {

    private GenTlWorkFlowInfo  workFlowInfo;
    private Character lastLevel;
    private String nextRoleName;
    private String nextRoleId;
    private String nextEmpId;

    public GenTlWorkFlowInfo getWorkFlowInfo() {
        return workFlowInfo;
    }
    public void setWorkFlowInfo(GenTlWorkFlowInfo workFlowInfo) {
        this.workFlowInfo = workFlowInfo;
    }
    public Character getLastLevel() {
        return lastLevel;
    }
    public void setLastLevel(Character lastLevel) {
        this.lastLevel = lastLevel;
    }
    public String getNextRoleName() {
        return nextRoleName;
    }
    public void setNextRoleName(String nextRoleName) {
        this.nextRoleName = nextRoleName;
    }
    public String getNextRoleId() {
        return nextRoleId;
    }
    public void setNextRoleId(String nextRoleId) {
        this.nextRoleId = nextRoleId;
    }
    public String getNextEmpId() {
        return nextEmpId;
    }
    public void setNextEmpId(String nextEmpId) {
        this.nextEmpId = nextEmpId;
    }

    
    
}
