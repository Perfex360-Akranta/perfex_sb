package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.StdTlStdworksheetmst;
import com.akranta.perfex_sb.model.StdTlStdworksheetdtl;
import java.util.List;

public class WorksheetRequest {
    
    private StdTlStdworksheetmst master;
    private List<StdTlStdworksheetdtl> details;

    public WorksheetRequest() {
    }

    public WorksheetRequest(StdTlStdworksheetmst master, List<StdTlStdworksheetdtl> details) {
        this.master = master;
        this.details = details;
    }

    public StdTlStdworksheetmst getMaster() {
        return master;
    }

    public void setMaster(StdTlStdworksheetmst master) {
        this.master = master;
    }

    public List<StdTlStdworksheetdtl> getDetails() {
        return details;
    }

    public void setDetails(List<StdTlStdworksheetdtl> details) {
        this.details = details;
    }
}