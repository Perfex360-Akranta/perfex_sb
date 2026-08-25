package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.*;
import java.util.List;

public class GenTlMachinemstRequest {
    
    // Main machine master entity
    private GenTlMachinemst master;
    
    // Related entities
    private List<GenTlMchemplink> operatorGrid;
    private List<GenTlMchmaintteamlink> maintenanceGrid;
    private List<GenTlMachineskillmst> operatorSkillGrid;
    private List<GenTlMachineskillmst> maintenanceSkillGrid;
   // private List<GenTlMchparameterlink> equipmentParameterGrid;
    private List<GenTlMchsubmchlink> subEquipmentGrid;
    private GenTlFunctionallocn functionalLocation;
    
    // Form control fields
    private String formActionMode;
    private String formMode;
    private String formHeader;
    private String formType;

    // Constructors
    public GenTlMachinemstRequest() {}

    public GenTlMachinemstRequest(GenTlMachinemst master) {
        this.master = master;
    }

    public GenTlMachinemstRequest(GenTlMachinemst master,
                                 List<GenTlMchemplink> operatorGrid,
                                 List<GenTlMchmaintteamlink> maintenanceGrid,
                                 List<GenTlMachineskillmst> operatorSkillGrid,
                                 List<GenTlMachineskillmst> maintenanceSkillGrid,
                                // List<GenTlMchparameterlink> equipmentParameterGrid,
                                 List<GenTlMchsubmchlink> subEquipmentGrid,
                                 GenTlFunctionallocn functionalLocation) {
        this.master = master;
        this.operatorGrid = operatorGrid;
        this.maintenanceGrid = maintenanceGrid;
        this.operatorSkillGrid = operatorSkillGrid;
        this.maintenanceSkillGrid = maintenanceSkillGrid;
       // this.equipmentParameterGrid = equipmentParameterGrid;
        this.subEquipmentGrid = subEquipmentGrid;
        this.functionalLocation = functionalLocation;
    }

    // Getters and Setters
    public GenTlMachinemst getMaster() {
        return master;
    }

    public void setMaster(GenTlMachinemst master) {
        this.master = master;
    }

    public List<GenTlMchemplink> getOperatorGrid() {
        return operatorGrid;
    }

    public void setOperatorGrid(List<GenTlMchemplink> operatorGrid) {
        this.operatorGrid = operatorGrid;
    }

    public List<GenTlMchmaintteamlink> getMaintenanceGrid() {
        return maintenanceGrid;
    }

    public void setMaintenanceGrid(List<GenTlMchmaintteamlink> maintenanceGrid) {
        this.maintenanceGrid = maintenanceGrid;
    }

    public List<GenTlMachineskillmst> getOperatorSkillGrid() {
        return operatorSkillGrid;
    }

    public void setOperatorSkillGrid(List<GenTlMachineskillmst> operatorSkillGrid) {
        this.operatorSkillGrid = operatorSkillGrid;
    }

    public List<GenTlMachineskillmst> getMaintenanceSkillGrid() {
        return maintenanceSkillGrid;
    }

    public void setMaintenanceSkillGrid(List<GenTlMachineskillmst> maintenanceSkillGrid) {
        this.maintenanceSkillGrid = maintenanceSkillGrid;
    }

    //public List<GenTlMchparameterlink> getEquipmentParameterGrid() {
    //    return equipmentParameterGrid;
    //}

   // public void setEquipmentParameterGrid(List<GenTlMchparameterlink> equipmentParameterGrid) {
   //     this.equipmentParameterGrid = equipmentParameterGrid;
   // }

    public List<GenTlMchsubmchlink> getSubEquipmentGrid() {
        return subEquipmentGrid;
    }

    public void setSubEquipmentGrid(List<GenTlMchsubmchlink> subEquipmentGrid) {
        this.subEquipmentGrid = subEquipmentGrid;
    }

    public GenTlFunctionallocn getFunctionalLocation() {
        return functionalLocation;
    }

    public void setFunctionalLocation(GenTlFunctionallocn functionalLocation) {
        this.functionalLocation = functionalLocation;
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
}