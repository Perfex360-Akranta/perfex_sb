package com.akranta.perfex_sb.dto;

public class PhenomenaFactoryLinkItemDto {
    private String ppflPlpmKeyid;
    private String ppflFactoryid;
    private String isDelete; // "Y" or "N"

    public String getPpflPlpmKeyid() {
        return ppflPlpmKeyid;
    }

    public void setPpflPlpmKeyid(String ppflPlpmKeyid) {
        this.ppflPlpmKeyid = ppflPlpmKeyid;
    }

    public String getPpflFactoryid() {
        return ppflFactoryid;
    }

    public void setPpflFactoryid(String ppflFactoryid) {
        this.ppflFactoryid = ppflFactoryid;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
