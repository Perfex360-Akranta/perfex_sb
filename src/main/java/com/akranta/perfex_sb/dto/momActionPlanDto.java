package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.GenTlActionPlanDtl;
import com.akranta.perfex_sb.model.GenTlActionPlanMst;
import com.akranta.perfex_sb.model.GenTlMommst;

public class momActionPlanDto {
    GenTlActionPlanMst actionPlanMst;
    GenTlActionPlanDtl actionPlanDtl;
    List<GenTlActionPlanDtl> actionPlanDtls;
    GenTlMommst genTlMommst;
    String actionPlanMstId;
    String actionPlanDetailId;
    String rowId;

    public GenTlActionPlanMst getActionPlanMst() {
        return actionPlanMst;
    }

    public void setActionPlanMst(GenTlActionPlanMst actionPlanMst) {
        this.actionPlanMst = actionPlanMst;
    }

    public List<GenTlActionPlanDtl> getActionPlanDtls() {
        return actionPlanDtls;
    }

    public void setActionPlanDtls(List<GenTlActionPlanDtl> actionPlanDtls) {
        this.actionPlanDtls = actionPlanDtls;
    }

    public GenTlMommst getGenTlMommst() {
        return genTlMommst;
    }

    public void setGenTlMommst(GenTlMommst genTlMommst) {
        this.genTlMommst = genTlMommst;
    }

    public GenTlActionPlanDtl getActionPlanDtl() {
        return actionPlanDtl;
    }

    public void setActionPlanDtl(GenTlActionPlanDtl actionPlanDtl) {
        this.actionPlanDtl = actionPlanDtl;
    }

    public String getActionPlanMstId() {
        return actionPlanMstId;
    }

    public void setActionPlanMstId(String actionPlanMstId) {
        this.actionPlanMstId = actionPlanMstId;
    }

    public String getActionPlanDetailId() {
        return actionPlanDetailId;
    }

    public void setActionPlanDetailId(String actionPlanDetailId) {
        this.actionPlanDetailId = actionPlanDetailId;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    

}
