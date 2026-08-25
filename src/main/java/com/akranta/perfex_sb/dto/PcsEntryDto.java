package com.akranta.perfex_sb.dto;

public class PcsEntryDto {

    private String formActionMode;
    private String formMode;
    private String formHeader;
    private String formType;
    private String lossId;
    private String lossValue;
    private String plmasterid;
    private String pldetailsid;
    private String ptwokeyid;
    private String plrkKeyid;
    private String plosKeyid;
    private String isOpenMsr;
    private String oldTime;

    public PcsEntryDto() {
        this.formMode = "CREATE";
    }

    public String getFormActionMode() {
        return formActionMode;
    }

    public void setFormActionMode(String formActionMode) {
        this.formActionMode = formActionMode;
    }

    public String getFormMode() {
        return formMode;
    }

    public void setFormMode(String formMode) {
        this.formMode = formMode;
    }

    public String getFormHeader() {
        return formHeader;
    }

    public void setFormHeader(String formHeader) {
        this.formHeader = formHeader;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getLossId() {
        return lossId;
    }

    public void setLossId(String lossId) {
        this.lossId = lossId;
    }

    public String getLossValue() {
        return lossValue;
    }

    public void setLossValue(String lossValue) {
        this.lossValue = lossValue;
    }

    public String getPlmasterid() {
        return plmasterid;
    }

    public void setPlmasterid(String plmasterid) {
        this.plmasterid = plmasterid;
    }

    public String getPldetailsid() {
        return pldetailsid;
    }

    public void setPldetailsid(String pldetailsid) {
        this.pldetailsid = pldetailsid;
    }

    public String getPtwokeyid() {
        return ptwokeyid;
    }

    public void setPtwokeyid(String ptwokeyid) {
        this.ptwokeyid = ptwokeyid;
    }

    public String getPlrkKeyid() {
        return plrkKeyid;
    }

    public void setPlrkKeyid(String plrkKeyid) {
        this.plrkKeyid = plrkKeyid;
    }

    public String getPlosKeyid() {
        return plosKeyid;
    }

    public void setPlosKeyid(String plosKeyid) {
        this.plosKeyid = plosKeyid;
    }

    public String getIsOpenMsr() {
        return isOpenMsr;
    }

    public void setIsOpenMsr(String isOpenMsr) {
        this.isOpenMsr = isOpenMsr;
    }

    public String getOldTime() {
        return oldTime;
    }

    public void setOldTime(String oldTime) {
        this.oldTime = oldTime;
    }
}
