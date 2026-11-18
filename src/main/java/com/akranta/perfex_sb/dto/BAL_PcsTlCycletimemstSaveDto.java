package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.BAL_PcsTlCycletimemst;

public class BAL_PcsTlCycletimemstSaveDto {

    private String createdby;
 
    private List<BAL_PcsTlCycletimemst> cycletimemstList;
 
    public String getCreatedby() {
        return createdby;
    }
 
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
 
    public List<BAL_PcsTlCycletimemst> getCycletimemstList() {
        return cycletimemstList;
    }
 
    public void setCycletimemstList(List<BAL_PcsTlCycletimemst> cycletimemstList) {
        this.cycletimemstList = cycletimemstList;
    }

}
