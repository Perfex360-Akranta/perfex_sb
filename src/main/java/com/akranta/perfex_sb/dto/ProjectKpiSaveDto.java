package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.KznTlProjectKpiLink;

public class ProjectKpiSaveDto {
    
    private KznTlProjectKpiLink   kpiLink;
    private String IsDelete;
    
    public KznTlProjectKpiLink getKpiLink() {
        return kpiLink;
    }
    public void setKpiLink(KznTlProjectKpiLink kpiLink) {
        this.kpiLink = kpiLink;
    }
    public String getIsDelete() {
        return IsDelete;
    }
    public void setIsDelete(String isDelete) {
        IsDelete = isDelete;
    }

    

}
