package com.akranta.perfex_sb.dto;


import com.akranta.perfex_sb.model.KznTlProjectResourceLink;

public class ProjectResourceSaveDto {
    private KznTlProjectResourceLink resourceLink;
    private String isDelete;

    public KznTlProjectResourceLink getResourceLink() {
        return resourceLink;
    }
    public void setResourceLink(KznTlProjectResourceLink resourceLink) {
        this.resourceLink = resourceLink;
    }
    public String getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    
}
