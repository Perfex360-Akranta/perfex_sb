package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.KznTlProjectKaizenLink;


public class ProjectKaizenSaveDto {
    private KznTlProjectKaizenLink   kaizenLink;
    private String isDelete;
    
    public KznTlProjectKaizenLink getKaizenLink() {
        return kaizenLink;
    }
    public void setKaizenLink(KznTlProjectKaizenLink kaizenLink) {
        this.kaizenLink = kaizenLink;
    }
    public String getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    
}
