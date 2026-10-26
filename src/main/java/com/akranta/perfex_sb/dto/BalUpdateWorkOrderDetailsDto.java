package com.akranta.perfex_sb.dto;

import java.time.LocalDateTime;

public class BalUpdateWorkOrderDetailsDto
 {
    String woDetailId;
    String completedBy;
    LocalDateTime completedDate;
    public String getWoDetailId() {
        return woDetailId;
    }
    public void setWoDetailId(String woDetailId) {
        this.woDetailId = woDetailId;
    }
    public String getCompletedBy() {
        return completedBy;
    }
    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }
    public LocalDateTime getCompletedDate() {
        return completedDate;
    }
    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }


    
    
}
