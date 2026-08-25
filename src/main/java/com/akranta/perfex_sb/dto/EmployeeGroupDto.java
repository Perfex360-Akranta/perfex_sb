package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.GenTlMomGroupmst;

import java.util.List;

import com.akranta.perfex_sb.model.GenTlMomGroupdtl;

public class EmployeeGroupDto {

    private GenTlMomGroupmst master;
    private List <GenTlMomGroupdtl> details;

  

    public GenTlMomGroupmst getMaster() {
        return master;
    }
    
    public void setMaster(GenTlMomGroupmst master) {
        this.master = master;
    }

    public List<GenTlMomGroupdtl> getDetails() {
        return details;
    }

    public void setDetails(List<GenTlMomGroupdtl> details) {
        this.details = details;
    }

   

    
}
