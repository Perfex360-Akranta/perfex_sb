package com.akranta.perfex_sb.dto;

/** Replaces pmMultipleResp_getData.mpce's getMultipleReposibility() rows. */
public class BalWoResponsibilityDto {

    private String employeeId;
    private String employeeName;
    private boolean assigned;

    public BalWoResponsibilityDto() {}

    public BalWoResponsibilityDto(String employeeId, String employeeName, boolean assigned) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.assigned = assigned;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public boolean isAssigned() { return assigned; }
    public void setAssigned(boolean assigned) { this.assigned = assigned; }
}
