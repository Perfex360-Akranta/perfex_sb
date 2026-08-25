package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.FieldAuditSheetmst;
import com.akranta.perfex_sb.model.FieldAuditSheetdtl;
import java.util.List;

public class FieldAuditSheetRequest {
    
    private FieldAuditSheetmst master;
    private List<FieldAuditSheetdtl> details;
    
    private String formActionMode;
    private String formMode;
    private String formHeader;

    // Constructors
    public FieldAuditSheetRequest() {}

    public FieldAuditSheetRequest(FieldAuditSheetmst master, List<FieldAuditSheetdtl> details) {
        this.master = master;
        this.details = details;
    }

    // Getters and Setters
    public FieldAuditSheetmst getMaster() {
        return master;
    }

    public void setMaster(FieldAuditSheetmst master) {
        this.master = master;
    }

    public List<FieldAuditSheetdtl> getDetails() {
        return details;
    }

    public void setDetails(List<FieldAuditSheetdtl> details) {
        this.details = details;
    }

    public String getFormActionMode() {
        return formActionMode;
    }

    public void setFormActionMode(String formActionMode) {
        this.formActionMode = formActionMode;
    }

    public String getFormMode() {
        return formMode;
    }

    public void setFormMode(String formMode) {
        this.formMode = formMode;
    }

    public String getFormHeader() {
        return formHeader;
    }

    public void setFormHeader(String formHeader) {
        this.formHeader = formHeader;
    }
}