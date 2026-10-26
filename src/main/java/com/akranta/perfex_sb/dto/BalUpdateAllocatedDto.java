package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.BalPlmTlMultipleResp;
import com.akranta.perfex_sb.model.BalPlmTlWoFeedBack;

public class BalUpdateAllocatedDto 
{
    String workorderno; 
    String updateWoId;
	String allotedtocombo;
    BalPlmTlWoFeedBack newPlmTlWofeedback;
    List<BalPlmTlMultipleResp> multipleResps;
     
    public String getWorkorderno() {
        return workorderno;
    }
    public void setWorkorderno(String workorderno) {
        this.workorderno = workorderno;
    }
    public String getUpdateWoId() {
        return updateWoId;
    }
    public void setUpdateWoId(String updateWoId) {
        this.updateWoId = updateWoId;
    }
    public String getAllotedtocombo() {
        return allotedtocombo;
    }
    public void setAllotedtocombo(String allotedtocombo) {
        this.allotedtocombo = allotedtocombo;
    }
    public BalPlmTlWoFeedBack getNewPlmTlWofeedback() {
        return newPlmTlWofeedback;
    }
    public void setNewPlmTlWofeedback(BalPlmTlWoFeedBack newPlmTlWofeedback) {
        this.newPlmTlWofeedback = newPlmTlWofeedback;
    }
    public List<BalPlmTlMultipleResp> getMultipleResps() {
        return multipleResps;
    }
    public void setMultipleResps(List<BalPlmTlMultipleResp> multipleResps) {
        this.multipleResps = multipleResps;
    }


    
    
    
}
