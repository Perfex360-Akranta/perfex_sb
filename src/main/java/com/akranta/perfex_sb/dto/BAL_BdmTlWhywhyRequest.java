package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.BAL_BdmTlWhywhydtl;
import com.akranta.perfex_sb.model.BAL_BdmTlWhywhymst;

import java.util.ArrayList;
import java.util.List;

public class BAL_BdmTlWhywhyRequest {

    private BAL_BdmTlWhywhymst master;
    private List<BAL_BdmTlWhywhydtl> details = new ArrayList<>();

    private String formActionMode;
    private String formMode;
    private String formHeader;

    /** Element/location id used to build the master sequence identifier (not persisted). */
    private String elementId;

    public BAL_BdmTlWhywhyRequest() {
    }

    public BAL_BdmTlWhywhyRequest(BAL_BdmTlWhywhymst master, List<BAL_BdmTlWhywhydtl> details) {
        this.master = master;
        this.details = details;
    }

    public BAL_BdmTlWhywhymst getMaster() {
        return master;
    }

    public void setMaster(BAL_BdmTlWhywhymst master) {
        this.master = master;
    }

    public List<BAL_BdmTlWhywhydtl> getDetails() {
        return details;
    }

    public void setDetails(List<BAL_BdmTlWhywhydtl> details) {
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

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }
}
