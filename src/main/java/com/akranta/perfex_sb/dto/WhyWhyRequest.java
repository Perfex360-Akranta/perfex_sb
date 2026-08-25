package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.*;
import java.time.LocalDate;
import java.util.List;

public class WhyWhyRequest {
    
    private BdmTlWhywhymst master;
    private List<BdmTlWhywhydtl> details;
    
    // Change this to a List
    private List<BdmTlYyeffectivemst> effectiveMaster;
    private List<BdmTlYyeffectivedtl> effectiveDetails;
    
    private List<BdmTlYydonebymst> doneByList;
    private List<BdmTlYyproblemattbymst> problemAttendedByList;
    
    private String formActionMode;
    private String formMode;
    private String formHeader;
    private String wwmsPillarmode;
    private String formType;
    private String wwmsPreveffective;
    private LocalDate wwmsPrevdat;

    // Constructors
    public WhyWhyRequest() {}

    public WhyWhyRequest(BdmTlWhywhymst master, List<BdmTlWhywhydtl> details) {
        this.master = master;
        this.details = details;
    }

    public WhyWhyRequest(BdmTlWhywhymst master, 
                        List<BdmTlWhywhydtl> details,
                        BdmTlYyeffectivemst effectiveMaster,
                        List<BdmTlYyeffectivedtl> effectiveDetails,
                        List<BdmTlYydonebymst> doneByList,
                        List<BdmTlYyproblemattbymst> problemAttendedByList) {
        this.master = master;
        this.details = details;
        // Convert single object to list
        if (effectiveMaster != null) {
            this.effectiveMaster = List.of(effectiveMaster);
        }
        this.effectiveDetails = effectiveDetails;
        this.doneByList = doneByList;
        this.problemAttendedByList = problemAttendedByList;
    }

    // Getters and Setters
    public BdmTlWhywhymst getMaster() {
        return master;
    }

    public void setMaster(BdmTlWhywhymst master) {
        this.master = master;
    }

    public List<BdmTlWhywhydtl> getDetails() {
        return details;
    }

    public void setDetails(List<BdmTlWhywhydtl> details) {
        this.details = details;
    }

    // Changed to return List
    public List<BdmTlYyeffectivemst> getEffectiveMaster() {
        return effectiveMaster;
    }

    public void setEffectiveMaster(List<BdmTlYyeffectivemst> effectiveMaster) {
        this.effectiveMaster = effectiveMaster;
    }

    public List<BdmTlYyeffectivedtl> getEffectiveDetails() {
        return effectiveDetails;
    }

    public void setEffectiveDetails(List<BdmTlYyeffectivedtl> effectiveDetails) {
        this.effectiveDetails = effectiveDetails;
    }

    public List<BdmTlYydonebymst> getDoneByList() {
        return doneByList;
    }

    public void setDoneByList(List<BdmTlYydonebymst> doneByList) {
        this.doneByList = doneByList;
    }

    public List<BdmTlYyproblemattbymst> getProblemAttendedByList() {
        return problemAttendedByList;
    }

    public void setProblemAttendedByList(List<BdmTlYyproblemattbymst> problemAttendedByList) {
        this.problemAttendedByList = problemAttendedByList;
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

    public String getWwmsPillarmode() {
        return wwmsPillarmode;
    }

    public void setWwmsPillarmode(String wwmsPillarmode) {
        this.wwmsPillarmode = wwmsPillarmode;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getWwmsPreveffective() {
        return wwmsPreveffective;
    }

    public void setWwmsPreveffective(String wwmsPreveffective) {
        this.wwmsPreveffective = wwmsPreveffective;
    }

    public LocalDate getWwmsPrevdat() {
        return wwmsPrevdat;
    }

    public void setWwmsPrevdat(LocalDate wwmsPrevdat) {
        this.wwmsPrevdat = wwmsPrevdat;
    }
}