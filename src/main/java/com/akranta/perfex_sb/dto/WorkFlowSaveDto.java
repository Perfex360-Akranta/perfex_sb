package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.GenTlWorkFlowDtl;
import com.akranta.perfex_sb.model.GenTlWorkFlowMst;

public class WorkFlowSaveDto {
    
    private GenTlWorkFlowMst workFlowMst;
    private List<GenTlWorkFlowDtl> workFlowDtls;
    
    public GenTlWorkFlowMst getWorkFlowMst() {
        return workFlowMst;
    }
    public void setWorkFlowMst(GenTlWorkFlowMst workFlowMst) {
        this.workFlowMst = workFlowMst;
    }
    public List<GenTlWorkFlowDtl> getWorkFlowDtls() {
        return workFlowDtls;
    }
    public void setWorkFlowDtls(List<GenTlWorkFlowDtl> workFlowDtls) {
        this.workFlowDtls = workFlowDtls;
    }

    
}
