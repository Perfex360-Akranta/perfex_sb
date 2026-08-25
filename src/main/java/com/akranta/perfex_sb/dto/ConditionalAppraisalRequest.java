package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.PlmTlConditionalappraisalmst;
import com.akranta.perfex_sb.model.PlmTlConditionalappraisal;
import com.akranta.perfex_sb.model.PlmTlConditionalappraisalmstentry;
import com.akranta.perfex_sb.model.PlmTlConappraisalentry;

import java.util.List;

public class ConditionalAppraisalRequest {
    
    private PlmTlConditionalappraisalmst master;
    private List<PlmTlConditionalappraisal> details;
    private PlmTlConditionalappraisalmstentry masterEntry;
    private List<PlmTlConappraisalentry> detailsEntry;

    public ConditionalAppraisalRequest() {
    }

    public ConditionalAppraisalRequest(PlmTlConditionalappraisalmst master, List<PlmTlConditionalappraisal> details) {
        this.master = master;
        this.details = details;
    }

    public PlmTlConditionalappraisalmst getMaster() {
        return master;
    }

    public void setMaster(PlmTlConditionalappraisalmst master) {
        this.master = master;
    }

    public List<PlmTlConditionalappraisal> getDetails() {
        return details;
    }

    public void setDetails(List<PlmTlConditionalappraisal> details) {
        this.details = details;
    }

    public PlmTlConditionalappraisalmstentry getMasterEntry() {
        return masterEntry;
    }

    public void setMasterEntry(PlmTlConditionalappraisalmstentry masterEntry) {
        this.masterEntry = masterEntry;
    }

    public List<PlmTlConappraisalentry> getDetailsEntry() {
        return detailsEntry;
    }

    public void setDetailsEntry(List<PlmTlConappraisalentry> detailsEntry) {
        this.detailsEntry = detailsEntry;
    }
}