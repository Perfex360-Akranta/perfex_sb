package com.akranta.perfex_sb.dto;

public class DeleteLossEntryRequestDto {
    private String plrkKeyid;
    private String pldetailsid;
    private String sectId;

    public String getPlrkKeyid() {
        return plrkKeyid;
    }

    public void setPlrkKeyid(String plrkKeyid) {
        this.plrkKeyid = plrkKeyid;
    }

    public String getPldetailsid() {
        return pldetailsid;
    }

    public void setPldetailsid(String pldetailsid) {
        this.pldetailsid = pldetailsid;
    }

    public String getSectId() {
        return sectId;
    }

    public void setSectId(String sectId) {
        this.sectId = sectId;
    }
}
