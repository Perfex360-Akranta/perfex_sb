package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.GenTlWorkFlowInfo;
import com.akranta.perfex_sb.model.KznTlProjectResourceLink;
import com.akranta.perfex_sb.model.KznTlProjectcreationmst;

public class ProjectCreationDto {
    
    private KznTlProjectcreationmst projectCreation;
    private List<KznTlProjectResourceLink> resourceLinkList;
    private String elementId;
    private GenTlWorkFlowInfo workFlow;

    public KznTlProjectcreationmst getProjectCreation() {
        return projectCreation;
    }
    public void setProjectCreation(KznTlProjectcreationmst projectCreation) {
        this.projectCreation = projectCreation;
    }
    public List<KznTlProjectResourceLink> getResourceLinkList() {
        return resourceLinkList;
    }
    public void setResourceLinkList(List<KznTlProjectResourceLink> resourceLinkList) {
        this.resourceLinkList = resourceLinkList;
    }
    public String getElementId() {
        return elementId;
    }
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }
    public GenTlWorkFlowInfo getWorkFlow() {
        return workFlow;
    }
    public void setWorkFlow(GenTlWorkFlowInfo workFlow) {
        this.workFlow = workFlow;
    }

    

    
}
