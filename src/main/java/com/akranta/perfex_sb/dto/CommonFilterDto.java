package com.akranta.perfex_sb.dto;

public class CommonFilterDto {
    private String cellId;
    private String sectionId;
    private String sbuId;
    private String pbuId;
    private String locationId;
    private String companyId;

    //Abnormality Dropdowns
    private String abnormalityTypeId;
    private String abnmOthers;
   
   
    public String getCellId() {
        return cellId;
    }
    public void setCellId(String cellId) {
        this.cellId = cellId;
    }
    public String getSectionId() {
        return sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public String getSbuId() {
        return sbuId;
    }
    public void setSbuId(String sbuId) {
        this.sbuId = sbuId;
    }
    public String getPbuId() {
        return pbuId;
    }
    public void setPbuId(String pbuId) {
        this.pbuId = pbuId;
    }
    public String getLocationId() {
        return locationId;
    }
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

     //Abnormality Dropdowns
    public String getAbnormalityTypeId() {
        return abnormalityTypeId;
    }
    public void setAbnormalityTypeId(String abnormalityTypeId) {
        this.abnormalityTypeId = abnormalityTypeId;
    }
    public String getAbnmOthers() {
        return abnmOthers;
    }
    public void setAbnmOthers(String abnmOthers) {
        this.abnmOthers = abnmOthers;
    }



   
    
    
}
