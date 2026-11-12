// DTO: PlnjobemplinkRequest.java
package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.SheTlPlannedJobObservation;
import com.akranta.perfex_sb.model.SheTlPlnjobemplink;

public class PlnjobemplinkRequest {

    private SheTlPlannedJobObservation master;
    private List<SheTlPlnjobemplink> details;

    public PlnjobemplinkRequest() {}

    public PlnjobemplinkRequest(SheTlPlannedJobObservation master, List<SheTlPlnjobemplink> details) {
        this.master = master;
        this.details = details;
    }

    public SheTlPlannedJobObservation getMaster() { return master; }
    public void setMaster(SheTlPlannedJobObservation master) { this.master = master; }

    public List<SheTlPlnjobemplink> getDetails() { return details; }
    public void setDetails(List<SheTlPlnjobemplink> details) { this.details = details; }


    
}