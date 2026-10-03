package com.akranta.perfex_sb.dto;

import java.util.List;

public class PlmTlStandardsRequest {

    private String factoryId;
    private String sectionId;
    private String cellId;
    private String machineId;
    private String locationId;
    private String flId;

    private List<PlmTlStandardsDetailDTO> details;

    private String formActionMode;
    private String formMode;
    private String formHeader;

    public String getFactoryId() { return factoryId; }
    public void setFactoryId(String factoryId) { this.factoryId = factoryId; }

    public String getSectionId() { return sectionId; }
    public void setSectionId(String sectionId) { this.sectionId = sectionId; }

    public String getCellId() { return cellId; }
    public void setCellId(String cellId) { this.cellId = cellId; }

    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }

    public String getLocationId() { return locationId; }
    public void setLocationId(String locationId) { this.locationId = locationId; }

    public String getFlId() { return flId; }
    public void setFlId(String flId) { this.flId = flId; }

    public List<PlmTlStandardsDetailDTO> getDetails() { return details; }
    public void setDetails(List<PlmTlStandardsDetailDTO> details) { this.details = details; }

    public String getFormActionMode() { return formActionMode; }
    public void setFormActionMode(String formActionMode) { this.formActionMode = formActionMode; }

    public String getFormMode() { return formMode; }
    public void setFormMode(String formMode) { this.formMode = formMode; }

    public String getFormHeader() { return formHeader; }
    public void setFormHeader(String formHeader) { this.formHeader = formHeader; }
}