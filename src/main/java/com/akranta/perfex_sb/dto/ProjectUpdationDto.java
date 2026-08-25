package com.akranta.perfex_sb.dto;


import com.akranta.perfex_sb.model.KznTlProjectcreationmst;

public class ProjectUpdationDto {
    private KznTlProjectcreationmst projectCreation;
    private String mode;

    public KznTlProjectcreationmst getProjectCreation() {
        return projectCreation;
    }
    public void setProjectCreation(KznTlProjectcreationmst projectCreation) {
        this.projectCreation = projectCreation;
    }
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
   
  
   


}
