package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;


public class EntTlTrgCalSessionDto {

    private String etcsKeyid;
    @JsonAlias({"etcmKeyid","keyid","traCalId","trgCalId","txtEtcmKeyid","etcm_keyid"})
    private String etcsEtcmKeyid;
    private String etcsEtcmFlid;
    private String etcsName;
  
    private LocalDateTime etcsSessiondate;

    private LocalDateTime etcsFromdate;

    private LocalDateTime etcsTilldate;

    private LocalDateTime etcsDateadd;
    private String etcsTempfield1;
    private String etcsTempfield2;
    private String etcsTempfield3;
    private String etcsTempfield4;
    private String etcsTempfield5;
    private String etcsActive;

    private LocalDateTime etcsCreatedon;

    private LocalDateTime etcsModifiedon;

    // raw string fallbacks (JSP sends dd-MMM-yyyy HH:mm)
    @JsonAlias({"etcsSessionDate"})
    private String etcsSessionDateText;

    @JsonAlias({"etcsFromDate"})
    private String etcsFromDateText;

    @JsonAlias({"etcsTillDate"})
    private String etcsTillDateText;

    @JsonAlias({"etcsDateAdd"})
    private String etcsDateAddText;

    // UI may send separate time fields (e.g., sessionFromTime / sessionTillTime)
    @JsonAlias({"sessionFromTime"})
    private String sessionFromTime;

    @JsonAlias({"sessionTillTime"})
    private String sessionTillTime;

    public String getEtcsSessionDateText() {
        return etcsSessionDateText;
    }

    public void setEtcsSessionDateText(String etcsSessionDateText) {
        this.etcsSessionDateText = etcsSessionDateText;
    }

    public String getEtcsFromDateText() {
        return etcsFromDateText;
    }

    public void setEtcsFromDateText(String etcsFromDateText) {
        this.etcsFromDateText = etcsFromDateText;
    }

    public String getEtcsTillDateText() {
        return etcsTillDateText;
    }

    public void setEtcsTillDateText(String etcsTillDateText) {
        this.etcsTillDateText = etcsTillDateText;
    }

    public String getEtcsDateAddText() {
        return etcsDateAddText;
    }

    public void setEtcsDateAddText(String etcsDateAddText) {
        this.etcsDateAddText = etcsDateAddText;
    }

    public String getSessionFromTime() { return sessionFromTime; }
    public void setSessionFromTime(String sessionFromTime) { this.sessionFromTime = sessionFromTime; }

    public String getSessionTillTime() { return sessionTillTime; }
    public void setSessionTillTime(String sessionTillTime) { this.sessionTillTime = sessionTillTime; }

    public String getEtcsKeyid() { return etcsKeyid; }
    public void setEtcsKeyid(String etcsKeyid) { this.etcsKeyid = etcsKeyid; }

    public String getEtcsEtcmKeyid() { return etcsEtcmKeyid; }
    public void setEtcsEtcmKeyid(String etcsEtcmKeyid) { this.etcsEtcmKeyid = etcsEtcmKeyid; }

    public String getEtcsEtcmFlid() { return etcsEtcmFlid; }
    public void setEtcsEtcmFlid(String etcsEtcmFlid) { this.etcsEtcmFlid = etcsEtcmFlid; }

    public String getEtcsName() { return etcsName; }
    public void setEtcsName(String etcsName) { this.etcsName = etcsName; }

    public LocalDateTime getEtcsSessiondate() { return etcsSessiondate; }
    public void setEtcsSessiondate(LocalDateTime etcsSessiondate) { this.etcsSessiondate = etcsSessiondate; }

    public LocalDateTime getEtcsFromdate() { return etcsFromdate; }
    public void setEtcsFromdate(LocalDateTime etcsFromdate) { this.etcsFromdate = etcsFromdate; }

    public LocalDateTime getEtcsTilldate() { return etcsTilldate; }
    public void setEtcsTilldate(LocalDateTime etcsTilldate) { this.etcsTilldate = etcsTilldate; }

    public LocalDateTime getEtcsDateadd() { return etcsDateadd; }
    public void setEtcsDateadd(LocalDateTime etcsDateadd) { this.etcsDateadd = etcsDateadd; }

    public String getEtcsTempfield1() { return etcsTempfield1; }
    public void setEtcsTempfield1(String etcsTempfield1) { this.etcsTempfield1 = etcsTempfield1; }

    public String getEtcsTempfield2() { return etcsTempfield2; }
    public void setEtcsTempfield2(String etcsTempfield2) { this.etcsTempfield2 = etcsTempfield2; }

    public String getEtcsTempfield3() { return etcsTempfield3; }
    public void setEtcsTempfield3(String etcsTempfield3) { this.etcsTempfield3 = etcsTempfield3; }

    public String getEtcsTempfield4() { return etcsTempfield4; }
    public void setEtcsTempfield4(String etcsTempfield4) { this.etcsTempfield4 = etcsTempfield4; }

    public String getEtcsTempfield5() { return etcsTempfield5; }
    public void setEtcsTempfield5(String etcsTempfield5) { this.etcsTempfield5 = etcsTempfield5; }

    public String getEtcsActive() { return etcsActive; }
    public void setEtcsActive(String etcsActive) { this.etcsActive = etcsActive; }

    public LocalDateTime getEtcsCreatedon() { return etcsCreatedon; }
    public void setEtcsCreatedon(LocalDateTime etcsCreatedon) { this.etcsCreatedon = etcsCreatedon; }

    public LocalDateTime getEtcsModifiedon() { return etcsModifiedon; }
    public void setEtcsModifiedon(LocalDateTime etcsModifiedon) { this.etcsModifiedon = etcsModifiedon; }
}
