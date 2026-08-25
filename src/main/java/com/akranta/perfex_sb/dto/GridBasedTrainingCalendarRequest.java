package com.akranta.perfex_sb.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GridBasedTrainingCalendarRequest {

    @JsonAlias({"fliId", "flid"})
    private String flid;

    @JsonAlias({"locaionId", "locationId"})
    private String locationId;

    @JsonAlias({"sectionId", "dmt"})
    private String sectionId;

    @JsonAlias({"cellId", "jh"})
    private String cellId;

    @JsonAlias({"TrnCalId", "calendarId", "traCalId"})
    private String calendarId;

    @JsonAlias({"createdBy"})
    private String createdBy;

    @JsonAlias({"calendarDetails", "abnormalitydetails"})
    private List<EntTlTragcalmstDto> calendarDetails;

    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }

    public String getLocationId() { return locationId; }
    public void setLocationId(String locationId) { this.locationId = locationId; }

    public String getSectionId() { return sectionId; }
    public void setSectionId(String sectionId) { this.sectionId = sectionId; }

    public String getCellId() { return cellId; }
    public void setCellId(String cellId) { this.cellId = cellId; }

    public String getCalendarId() { return calendarId; }
    public void setCalendarId(String calendarId) { this.calendarId = calendarId; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public List<EntTlTragcalmstDto> getCalendarDetails() { return calendarDetails; }
    public void setCalendarDetails(List<EntTlTragcalmstDto> calendarDetails) { this.calendarDetails = calendarDetails; }
}
