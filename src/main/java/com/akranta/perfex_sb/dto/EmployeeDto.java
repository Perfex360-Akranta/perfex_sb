package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.GenTlEmployeedtl;
import com.akranta.perfex_sb.model.GenTlEmployeemst;
import com.akranta.perfex_sb.model.GenTlMomGroupdtl;
import com.akranta.perfex_sb.model.GenTlMomGroupmst;

public class EmployeeDto {

    private GenTlEmployeemst master;
    private List <GenTlEmployeedtl> details;
    
    public GenTlEmployeemst getMaster() {
        return master;
    }
    
    public void setMaster(GenTlEmployeemst master) {
        this.master = master;
    }

    public List<GenTlEmployeedtl> getDetails() {
        return details;
    }

    public void setDetails(List<GenTlEmployeedtl> details) {
        this.details = details;
    }

       
}

