package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.LopcEntryMst;

public class LopcEntryRequest {
    
    private LopcEntryMst master;

    public LopcEntryRequest() {
    }

    public LopcEntryRequest(LopcEntryMst master) {
        this.master = master;
    }

    public LopcEntryMst getMaster() {
        return master;
    }

    public void setMaster(LopcEntryMst master) {
        this.master = master;
    }
}