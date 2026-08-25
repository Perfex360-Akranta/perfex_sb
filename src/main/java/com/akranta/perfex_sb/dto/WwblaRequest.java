package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.BdmTlWwblamst;
import com.akranta.perfex_sb.model.BdmTlWwbladtl;
import java.util.List;

public class WwblaRequest {
    
    private BdmTlWwblamst master;
    private List<BdmTlWwbladtl> details;

    public BdmTlWwblamst getMaster() {
        return master;
    }

    public void setMaster(BdmTlWwblamst master) {
        this.master = master;
    }

    public List<BdmTlWwbladtl> getDetails() {
        return details;
    }

    public void setDetails(List<BdmTlWwbladtl> details) {
        this.details = details;
    }
}