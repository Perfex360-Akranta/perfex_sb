package com.akranta.perfex_sb.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * Payload for POST /api/workorders/completion/{woDetailId} - the equivalent
 * of AbnCompletionDto for this module. Consolidates what the legacy servlet
 * spread across saveWODetails() (first completion), saveGDWODetails() (grid
 * completion + CBM fields), and the hdnMultiresp JSON blob.
 */
public class BalWoCompletionDto {

    private String machineId;
    private String pmstdId;
    private String pmcalendarId;
    private String assemblyId;
    private LocalDate fromMonth;
    private String completedBy;
    private String observation;
    private String duration;

    /** true => editing an already-completed WO (legacy modeselected / modifyCompWo path) */
    private boolean modify;

    /** present only when the work order's job type is CBM */
    private BalWoCbmReadingDto cbmReading;

    private List<BalWoSpareConsumedDto> spares;

    /** employee IDs to tag against this completion (legacy PlmTlMultipleResp) */
    private List<String> responsibleEmployeeIds;

    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }

    public String getPmstdId() { return pmstdId; }
    public void setPmstdId(String pmstdId) { this.pmstdId = pmstdId; }

    public String getPmcalendarId() { return pmcalendarId; }
    public void setPmcalendarId(String pmcalendarId) { this.pmcalendarId = pmcalendarId; }

    public String getAssemblyId() { return assemblyId; }
    public void setAssemblyId(String assemblyId) { this.assemblyId = assemblyId; }

    public LocalDate getFromMonth() { return fromMonth; }
    public void setFromMonth(LocalDate fromMonth) { this.fromMonth = fromMonth; }

    public String getCompletedBy() { return completedBy; }
    public void setCompletedBy(String completedBy) { this.completedBy = completedBy; }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public boolean isModify() { return modify; }
    public void setModify(boolean modify) { this.modify = modify; }

    public BalWoCbmReadingDto getCbmReading() { return cbmReading; }
    public void setCbmReading(BalWoCbmReadingDto cbmReading) { this.cbmReading = cbmReading; }

    public List<BalWoSpareConsumedDto> getSpares() { return spares; }
    public void setSpares(List<BalWoSpareConsumedDto> spares) { this.spares = spares; }

    public List<String> getResponsibleEmployeeIds() { return responsibleEmployeeIds; }
    public void setResponsibleEmployeeIds(List<String> responsibleEmployeeIds) { this.responsibleEmployeeIds = responsibleEmployeeIds; }
}
