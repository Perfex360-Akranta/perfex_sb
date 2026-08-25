package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "std_tl_stdworksheetmst", schema = "public")
public class StdTlStdworksheetmst {
    
    @Id
    @Column(name = "stws_keyid", length = 16, nullable = false)
    private String keyid;

    @Column(name = "stws_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "stws_by", length = 8, nullable = false)
    private String by;

    @Column(name = "stws_approvedby", length = 8, nullable = false)
    private String approvedby;

    @Column(name = "stws_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "stws_elementid", length = 500, nullable = false)
    private String elementid;

    @Column(name = "stws_process", length = 100, nullable = false)
    private String process;

    @Column(name = "stws_budgetedtime", length = 100, nullable = false)
    private String budgetedtime;

    @Column(name = "stws_type", length = 1, nullable = false)
    private String type;

    @Column(name = "stws_cycletime", length = 15, nullable = false)
    private String cycletime;

    @Column(name = "stws_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "stws_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "stws_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "stws_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "stws_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "stws_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "stws_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getBudgetedtime() {
        return budgetedtime;
    }

    public void setBudgetedtime(String budgetedtime) {
        this.budgetedtime = budgetedtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCycletime() {
        return cycletime;
    }

    public void setCycletime(String cycletime) {
        this.cycletime = cycletime;
    }

    public String getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(String tempfield3) {
        this.tempfield3 = tempfield3;
    }

    public String getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(String tempfield4) {
        this.tempfield4 = tempfield4;
    }

    public String getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(String tempfield5) {
        this.tempfield5 = tempfield5;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDateTime getCreatedon() {
        return createdon;
    }

    public void setCreatedon(LocalDateTime createdon) {
        this.createdon = createdon;
    }

    public LocalDateTime getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(LocalDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }
}