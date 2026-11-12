package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

public class UpdateIncidentDto {
    private String keyid;
    private String exact_desc;             
    private String exact_type;             
    private String analysed_by;            
    private LocalDateTime analysed_date;   
    private String analysed_shift;         
    private String rptRecommendations;    
    private String investigation;         
    private String invstremarks;  
    private String rootcausedueto;        

    // Getters and Setters
    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getExact_desc() {
        return exact_desc;
    }

    public void setExact_desc(String exact_desc) {
        this.exact_desc = exact_desc;
    }

    public String getExact_type() {
        return exact_type;
    }

    public void setExact_type(String exact_type) {
        this.exact_type = exact_type;
    }

    public String getAnalysed_by() {
        return analysed_by;
    }

    public void setAnalysed_by(String analysed_by) {
        this.analysed_by = analysed_by;
    }

    public LocalDateTime getAnalysed_date() {
        return analysed_date;
    }

    public void setAnalysed_date(LocalDateTime analysed_date) {
        this.analysed_date = analysed_date;
    }

    public String getAnalysed_shift() {
        return analysed_shift;
    }

    public void setAnalysed_shift(String analysed_shift) {
        this.analysed_shift = analysed_shift;
    }

    public String getRptrecommendations() {
        return rptRecommendations;
    }

    public void setRptRecommendations(String rptRecommendations) {
        this.rptRecommendations = rptRecommendations;
    }

    public String getInvestigation() {
        return investigation;
    }

    public void setInvestigation(String investigation) {
        this.investigation = investigation;
    }

    public String getInvstremarks() {
        return invstremarks;
    }

    public void setInvstremarks(String invstremarks) {
        this.invstremarks = invstremarks;
    }

    public String getRootcausedueto() {
        return rootcausedueto;
    }

    public void setRootcausedueto(String rootcausedueto) {
        this.rootcausedueto = rootcausedueto;
    }
}
