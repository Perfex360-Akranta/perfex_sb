package com.akranta.perfex_sb.dto;

import java.time.LocalDate;

public class BalWoCbmReadingDto {

    private String pmstdid;
    private String pmcalendarid;
    private String inspectionid;
    private String uomid;
    private String currentReading;
    private String adjustReading;
    private String minimumReading;
    private String maximumReading;
    private LocalDate nextDueDate;
    private String zoneCondition;

    public String getPmstdid() { return pmstdid; }
    public void setPmstdid(String pmstdid) { this.pmstdid = pmstdid; }

    public String getPmcalendarid() { return pmcalendarid; }
    public void setPmcalendarid(String pmcalendarid) { this.pmcalendarid = pmcalendarid; }

    public String getInspectionid() { return inspectionid; }
    public void setInspectionid(String inspectionid) { this.inspectionid = inspectionid; }

    public String getUomid() { return uomid; }
    public void setUomid(String uomid) { this.uomid = uomid; }

    public String getCurrentReading() { return currentReading; }
    public void setCurrentReading(String currentReading) { this.currentReading = currentReading; }

    public String getAdjustReading() { return adjustReading; }
    public void setAdjustReading(String adjustReading) { this.adjustReading = adjustReading; }

    public String getMinimumReading() { return minimumReading; }
    public void setMinimumReading(String minimumReading) { this.minimumReading = minimumReading; }

    public String getMaximumReading() { return maximumReading; }
    public void setMaximumReading(String maximumReading) { this.maximumReading = maximumReading; }

    public LocalDate getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(LocalDate nextDueDate) { this.nextDueDate = nextDueDate; }

    public String getZoneCondition() { return zoneCondition; }
    public void setZoneCondition(String zoneCondition) { this.zoneCondition = zoneCondition; }
}
