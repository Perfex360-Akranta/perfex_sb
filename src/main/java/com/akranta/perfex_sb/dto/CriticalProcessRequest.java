package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.QtmTlCriticalprocessmst;
import com.akranta.perfex_sb.model.QtmTlCriticalprocessdtl;
import java.util.List;

public class CriticalProcessRequest {
    
    private QtmTlCriticalprocessmst master;
    private List<QtmTlCriticalprocessdtl> details;
    
    // Constructors
    public CriticalProcessRequest() {
    }
    
    public CriticalProcessRequest(QtmTlCriticalprocessmst master, List<QtmTlCriticalprocessdtl> details) {
        this.master = master;
        this.details = details;
    }
    
    // Getters and Setters
    public QtmTlCriticalprocessmst getMaster() {
        return master;
    }
    
    public void setMaster(QtmTlCriticalprocessmst master) {
        this.master = master;
    }
    
    public List<QtmTlCriticalprocessdtl> getDetails() {
        return details;
    }
    
    public void setDetails(List<QtmTlCriticalprocessdtl> details) {
        this.details = details;
    }
}