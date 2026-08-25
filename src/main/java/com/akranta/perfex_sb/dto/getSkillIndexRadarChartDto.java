package com.akranta.perfex_sb.dto;

import java.util.List;

public class getSkillIndexRadarChartDto {
    String fromDate;
    String flid;
    String uniquePosId;
    String empmKeyIds;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getUniquePosId() {
        return uniquePosId;
    }

    public void setUniquePosId(String uniquePosId) {
        this.uniquePosId = uniquePosId;
    }

    public String getEmpmKeyIds() {
        return empmKeyIds;
    }

    public void setEmpmKeyIds(String empmKeyIds) {
        this.empmKeyIds = empmKeyIds;
    }

}
