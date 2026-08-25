package com.akranta.perfex_sb.dto;

public class PcsLossEntryGridRequestDto {
    private String flid;
    private String fromDate; // "01-JAN-1801"
    private String toDate;   // "31-DEC-2100"

    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }

    public String getFromDate() { return fromDate; }
    public void setFromDate(String fromDate) { this.fromDate = fromDate; }

    public String getToDate() { return toDate; }
    public void setToDate(String toDate) { this.toDate = toDate; }
}
