package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.BAL_BdmTlMst;
import com.akranta.perfex_sb.model.BAL_BdmTlDtl;
import com.akranta.perfex_sb.model.WomTlWomst;

public class BdmDto {

    private BAL_BdmTlMst master;
    private BAL_BdmTlDtl detail;
    private WomTlWomst womWorkOrder;   // populated after save; sent back in response
    private String formActionMode;
    private String formMode;

    public BAL_BdmTlMst getMaster() { return master; }
    public void setMaster(BAL_BdmTlMst master) { this.master = master; }

    public BAL_BdmTlDtl getDetail() { return detail; }
    public void setDetail(BAL_BdmTlDtl detail) { this.detail = detail; }

    public WomTlWomst getWomWorkOrder() { return womWorkOrder; }
    public void setWomWorkOrder(WomTlWomst womWorkOrder) { this.womWorkOrder = womWorkOrder; }

    public String getFormActionMode() { return formActionMode; }
    public void setFormActionMode(String formActionMode) { this.formActionMode = formActionMode; }

    public String getFormMode() { return formMode; }
    public void setFormMode(String formMode) { this.formMode = formMode; }
}
