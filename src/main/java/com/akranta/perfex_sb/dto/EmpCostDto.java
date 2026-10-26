package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.BAL_WomTlManpowercostplan;

public class EmpCostDto {

    private BAL_WomTlManpowercostplan record;
    private String formActionMode;
    private String formMode;

    public BAL_WomTlManpowercostplan getRecord() { return record; }
    public void setRecord(BAL_WomTlManpowercostplan record) { this.record = record; }

    public String getFormActionMode() { return formActionMode; }
    public void setFormActionMode(String formActionMode) { this.formActionMode = formActionMode; }

    public String getFormMode() { return formMode; }
    public void setFormMode(String formMode) { this.formMode = formMode; }
}