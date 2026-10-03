package com.akranta.perfex_sb.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PLM_TL_WODTL")

public class BalPlmTlWoDtl {

    @Id
    @Column(name = "PWDD_WODETAILID", length = 20, nullable = false)
    private String woDetailId;

    @Column(name = "PWDD_WOMASTERID", length = 20, nullable = false)
    private String woMasterId;

    @Column(name = "PWDD_CALENDARID", length = 12, nullable = false)
    private String calendarId;

    @Column(name = "PWDD_PMSTANDARDID", length = 15, nullable = false)
    private String pmStandardId;

    @Column(name = "PWDD_TARGETWEEKNO", nullable = false)
    private Integer targetWeekNo;

    @Column(name = "PWDD_TARGETDATE", nullable = false)
    private Timestamp targetDate;

    @Column(name = "PWDD_STATUS", length = 1, nullable = false)
    private String status;

    @Column(name = "PWDD_PRINTFLAG", length = 1, nullable = false)
    private String printFlag;

    @Column(name = "PWDD_FEEDBACKID", length = 14, nullable = false)
    private String feedbackId;

    @Column(name = "PWDD_COMPLETEDBY", length = 8, nullable = false)
    private String completedBy;

    @Column(name = "PWDD_COMPLETEDDATE", nullable = false)
    private Timestamp completedDate;

    @Column(name = "PWDD_ISREALLOCATED", length = 1, nullable = false)
    private String isReallocated;

    @Column(name = "PWDD_REALLOCATEDTO", length = 8, nullable = false)
    private String reallocatedTo;

    @Column(name = "PWDD_REALLOCATEDDATE", nullable = false)
    private Timestamp reallocatedDate;

    @Column(name = "PWDD_REALLOCATEDID", length = 12, nullable = false)
    private String reallocatedId;

    @Column(name = "PWDD_CREATEDBY", length = 8, nullable = false)
    private String createdBy;

    @Column(name = "PWDD_CREATEDON", nullable = false)
    private Timestamp createdOn;

    @Column(name = "PWDD_MODIFIEDON", nullable = false)
    private Timestamp modifiedOn;

    public String getWoDetailId() {
        return woDetailId;
    }

    public void setWoDetailId(String woDetailId) {
        this.woDetailId = woDetailId;
    }

    public String getWoMasterId() {
        return woMasterId;
    }

    public void setWoMasterId(String woMasterId) {
        this.woMasterId = woMasterId;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getPmStandardId() {
        return pmStandardId;
    }

    public void setPmStandardId(String pmStandardId) {
        this.pmStandardId = pmStandardId;
    }

    public Integer getTargetWeekNo() {
        return targetWeekNo;
    }

    public void setTargetWeekNo(Integer targetWeekNo) {
        this.targetWeekNo = targetWeekNo;
    }

    public Timestamp getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Timestamp targetDate) {
        this.targetDate = targetDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrintFlag() {
        return printFlag;
    }

    public void setPrintFlag(String printFlag) {
        this.printFlag = printFlag;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public Timestamp getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Timestamp completedDate) {
        this.completedDate = completedDate;
    }

    public String getIsReallocated() {
        return isReallocated;
    }

    public void setIsReallocated(String isReallocated) {
        this.isReallocated = isReallocated;
    }

    public String getReallocatedTo() {
        return reallocatedTo;
    }

    public void setReallocatedTo(String reallocatedTo) {
        this.reallocatedTo = reallocatedTo;
    }

    public Timestamp getReallocatedDate() {
        return reallocatedDate;
    }

    public void setReallocatedDate(Timestamp reallocatedDate) {
        this.reallocatedDate = reallocatedDate;
    }

    public String getReallocatedId() {
        return reallocatedId;
    }

    public void setReallocatedId(String reallocatedId) {
        this.reallocatedId = reallocatedId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public void setWodtStatus(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setWodtStatus'");
    }
}