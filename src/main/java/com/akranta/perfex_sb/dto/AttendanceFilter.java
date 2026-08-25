package com.akranta.perfex_sb.dto;

public class AttendanceFilter {
    private String key;       // training calendar id
    private String flid;      // functional location
    private String lossId;    // location
    private String topicid;
    private String chkExternal; // created by user

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }

    public String getLossId() { return lossId; }
    public void setLossId(String lossId) { this.lossId = lossId; }

    public String getTopicid() { return topicid; }
    public void setTopicid(String topicid) { this.topicid = topicid; }

    public String getChkExternal() { return chkExternal; }
    public void setChkExternal(String chkExternal) { this.chkExternal = chkExternal; }
}
