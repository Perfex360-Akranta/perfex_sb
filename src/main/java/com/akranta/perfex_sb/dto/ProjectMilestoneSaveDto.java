package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.KznTlProjMilestoneDtl;
import com.akranta.perfex_sb.model.KznTlProjMilestoneMst;

public class ProjectMilestoneSaveDto {
    
    private KznTlProjMilestoneMst milestoneMst;
    private List<KznTlProjMilestoneDtl> milestoneDtlList;
    public KznTlProjMilestoneMst getMilestoneMst() {
        return milestoneMst;
    }
    public void setMilestoneMst(KznTlProjMilestoneMst milestoneMst) {
        this.milestoneMst = milestoneMst;
    }
    public List<KznTlProjMilestoneDtl> getMilestoneDtlList() {
        return milestoneDtlList;
    }
    public void setMilestoneDtlList(List<KznTlProjMilestoneDtl> milestoneDtlList) {
        this.milestoneDtlList = milestoneDtlList;
    }

    
}
