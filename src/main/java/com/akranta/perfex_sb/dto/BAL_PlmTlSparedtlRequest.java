package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.BAL_PlmTlSparedtl;
import java.util.List;

public class BAL_PlmTlSparedtlRequest {

    private String standardId;
    private List<BAL_PlmTlSparedtl> details;
    private String formActionMode;
    private String formMode;
    private String formHeader;
    private String createdBy;

    public String getStandardId() { return standardId; }
    public void setStandardId(String standardId) { this.standardId = standardId; }

    public List<BAL_PlmTlSparedtl> getDetails() { return details; }
    public void setDetails(List<BAL_PlmTlSparedtl> details) { this.details = details; }

    public String getFormActionMode() { return formActionMode; }
    public void setFormActionMode(String formActionMode) { this.formActionMode = formActionMode; }

    public String getFormMode() { return formMode; }
    public void setFormMode(String formMode) { this.formMode = formMode; }

    public String getFormHeader() { return formHeader; }
    public void setFormHeader(String formHeader) { this.formHeader = formHeader; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}