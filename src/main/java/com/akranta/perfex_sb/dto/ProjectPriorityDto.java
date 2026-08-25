package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.KznTlKkprojectprioritydtl;
import com.akranta.perfex_sb.model.KznTlKkprojectprioritymst;

public class ProjectPriorityDto {
    
    private List<KznTlKkprojectprioritymst> mstList;
    private List<KznTlKkprojectprioritydtl> dtlList;
    
    public List<KznTlKkprojectprioritymst> getMstList() {
        return mstList;
    }
    public void setMstList(List<KznTlKkprojectprioritymst> mstList) {
        this.mstList = mstList;
    }
    public List<KznTlKkprojectprioritydtl> getDtlList() {
        return dtlList;
    }
    public void setDtlList(List<KznTlKkprojectprioritydtl> dtlList) {
        this.dtlList = dtlList;
    }
    
}
