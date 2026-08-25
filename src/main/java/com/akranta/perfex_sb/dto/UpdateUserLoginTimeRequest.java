package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

public class UpdateUserLoginTimeRequest {
    private String keyid;
    private LocalDateTime lastlogindate;

    // getters and setters
    public String getKeyid() { return keyid; }
    public void setKeyid(String keyid) { this.keyid = keyid; }

    public LocalDateTime getLastlogindate() { return lastlogindate; }
    public void setLastlogindate(LocalDateTime lastlogindate) { this.lastlogindate = lastlogindate; }
}