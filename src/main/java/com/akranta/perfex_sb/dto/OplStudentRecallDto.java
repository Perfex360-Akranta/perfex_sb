package com.akranta.perfex_sb.dto;
public class OplStudentRecallDto {
    private String oplId;      // you pass request.getParameter("oplId")
    private String cellId;     // you pass request.getParameter("cellId")
    private String oplKeyid;   // you pass request.getParameter("hdnoplkeyid")

    public String getOplId() { return oplId; }
    public void setOplId(String oplId) { this.oplId = oplId; }

    public String getCellId() { return cellId; }
    public void setCellId(String cellId) { this.cellId = cellId; }

    public String getOplKeyid() { return oplKeyid; }
    public void setOplKeyid(String oplKeyid) { this.oplKeyid = oplKeyid; }
}
