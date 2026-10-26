package com.akranta.perfex_sb.dto;

public class CliStandardsDtlRequest {

    private String clisKeyid;   // parent BAL_CLI_TL_STANDARDS key (stored in CLISDR_KEYID)
    private String howIcon;
    private String userId;

    public String getClisKeyid() { return clisKeyid; }
    public void setClisKeyid(String clisKeyid) { this.clisKeyid = clisKeyid; }

    public String getHowIcon() { return howIcon; }
    public void setHowIcon(String howIcon) { this.howIcon = howIcon; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}