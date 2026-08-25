package com.akranta.perfex_sb.dto;

import java.util.List;

public class EmployeeAttendanceRequest {
    private String cutoff;
    private String max;
    private String typ;
    private String topicid;
    private String locnid;
    private String flid;
    private String assess;
    private List<EntTlTtgCalEmpatScoreDto> scores;

    public String getCutoff() { return cutoff; }
    public void setCutoff(String cutoff) { this.cutoff = cutoff; }

    public String getMax() { return max; }
    public void setMax(String max) { this.max = max; }

    public String getTyp() { return typ; }
    public void setTyp(String typ) { this.typ = typ; }

    public String getTopicid() { return topicid; }
    public void setTopicid(String topicid) { this.topicid = topicid; }

    public String getLocnid() { return locnid; }
    public void setLocnid(String locnid) { this.locnid = locnid; }

    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }

    public String getAssess() { return assess; }
    public void setAssess(String assess) { this.assess = assess; }

    public List<EntTlTtgCalEmpatScoreDto> getScores() { return scores; }
    public void setScores(List<EntTlTtgCalEmpatScoreDto> scores) { this.scores = scores; }
}
