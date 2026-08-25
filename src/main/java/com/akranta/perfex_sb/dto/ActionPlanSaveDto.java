package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.GenTlActionPlanMst;

import java.util.List;

import com.akranta.perfex_sb.model.GenTlActionPlanDtl;

public class ActionPlanSaveDto {
    private GenTlActionPlanMst actionPlanMst;
    private List<GenTlActionPlanDtl> actionPlanDtls;

    public GenTlActionPlanMst getActionPlanMst() {
        return actionPlanMst;
    }
    public void setActionPlanMst(GenTlActionPlanMst actionPlanMst) {
        this.actionPlanMst = actionPlanMst;
    }
    public List<GenTlActionPlanDtl> getActionPlanDtls() {
        return actionPlanDtls;
    }
    public void setActionPlanDtls(List<GenTlActionPlanDtl> actionplanDtls) {
        this.actionPlanDtls = actionplanDtls;
    }

    
}
