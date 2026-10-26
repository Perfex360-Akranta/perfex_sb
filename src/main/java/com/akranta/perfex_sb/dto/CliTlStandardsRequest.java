package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.CliTlStandards;
import java.util.List;

/**
 * Wrapper for the CLTI multiple-save screen.
 * Header/location fields come once from the form; they get stamped onto
 * every row in "details" before persisting (same as the legacy
 * saveMultipleClit servlet did per JSON row).
 */
public class CliTlStandardsRequest {

    private String factoryId;
    private String sectionId;
    private String cellId;
    private String machineId;
    private String flId;
    private String elementId;

    private List<CliTlStandards> details;

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

    public String getFlId() { return flId; }
    public void setFlId(String flId) { this.flId = flId; }

    public String getElementId() { return elementId; }
    public void setElementId(String elementId) { this.elementId = elementId; }

    public List<CliTlStandards> getDetails() { return details; }
    public void setDetails(List<CliTlStandards> details) { this.details = details; }

    public String getFormActionMode() { return formActionMode; }
    public void setFormActionMode(String formActionMode) { this.formActionMode = formActionMode; }

    public String getFormMode() { return formMode; }
    public void setFormMode(String formMode) { this.formMode = formMode; }

    public String getFormHeader() { return formHeader; }
    public void setFormHeader(String formHeader) { this.formHeader = formHeader; }
}
