package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.SheTlPlantSafetymst;
import com.akranta.perfex_sb.model.SheTlPlantSafetydtl;
import java.util.List;

public class PlantSafetyRequest {

    private SheTlPlantSafetymst master;
    private List<SheTlPlantSafetydtl> details;

    private String detailkeyid;
    private String responsevalue;
    private String targetdate;

    // Verification fields
    private String verifystatus;
    private String remarks;

    // Action closure fields
    private String revisedate;
    private String status;
    private String correctiveaction;
    private String abnKeyid;

    public PlantSafetyRequest() {}

    public PlantSafetyRequest(SheTlPlantSafetymst master, List<SheTlPlantSafetydtl> details) {
        this.master = master;
        this.details = details;
    }

    public SheTlPlantSafetymst getMaster() { return master; }
    public void setMaster(SheTlPlantSafetymst master) { this.master = master; }

    public List<SheTlPlantSafetydtl> getDetails() { return details; }
    public void setDetails(List<SheTlPlantSafetydtl> details) { this.details = details; }

    public String getDetailkeyid() { return detailkeyid; }
    public void setDetailkeyid(String detailkeyid) { this.detailkeyid = detailkeyid; }

    public String getResponsevalue() { return responsevalue; }
    public void setResponsevalue(String responsevalue) { this.responsevalue = responsevalue; }

    public String getTargetdate() { return targetdate; }
    public void setTargetdate(String targetdate) { this.targetdate = targetdate; }

    public String getVerifystatus() { return verifystatus; }
    public void setVerifystatus(String verifystatus) { this.verifystatus = verifystatus; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    // Action closure getters/setters
    public String getRevisedate() { return revisedate; }
    public void setRevisedate(String revisedate) { this.revisedate = revisedate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCorrectiveaction() { return correctiveaction; }
    public void setCorrectiveaction(String correctiveaction) { this.correctiveaction = correctiveaction; }

    public String getAbnKeyid() { return abnKeyid; }
    public void setAbnKeyid(String abnKeyid) { this.abnKeyid = abnKeyid; }
}