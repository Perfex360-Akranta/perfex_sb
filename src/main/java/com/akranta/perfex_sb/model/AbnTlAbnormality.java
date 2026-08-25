package com.akranta.perfex_sb.model;
import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "abn_tl_abnormality", schema = "public")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class AbnTlAbnormality {
    @Id
    @Column(name = "abnm_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "abnm_date", nullable = false)
//    @NotNull
    private LocalDateTime date;

    @Column(name = "abnm_refdoctype", length = 15, nullable = false)
//    @NotBlank
//    @Size(max = 15)
    private String refdoctype;

    @Column(name = "abnm_refdocid", length = 15, nullable = false)
//    @NotBlank
//    @Size(max = 15)
    private String refdocid;

    @Column(name = "abnm_detectiondate", nullable = false)
//    @NotNull
    private LocalDateTime detectiondate;

    @Column(name = "abnm_detectedby", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String detectedby;

    @Column(name = "abnm_equipmentid", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String equipmentid;

    @Column(name = "abnm_sectionid", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String sectionid;

    @Column(name = "abnm_cellid", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String cellid;

    @Column(name = "abnm_assemblyid", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String assemblyid;

    @Column(name = "abnm_shiftid", length = 6, nullable = false)
//    @NotBlank
//    @Size(max = 6)
    private String shiftid;

    @Column(name = "abnm_tradeid", length = 15, nullable = false)
//    @NotBlank
//    @Size(max = 15)
    private String tradeid;

    @Column(name = "abnm_woreceiveddate", nullable = false)
//    @NotNull
    private LocalDateTime woreceiveddate;

    @Column(name = "abnm_responsetime", nullable = false)
//    @NotNull
    private BigDecimal responsetime;

    @Column(name = "abnm_worktime", nullable = false)
//    @NotNull
    private BigDecimal worktime;

    @Column(name = "abnm_wostarttime", nullable = false)
//    @NotNull
    private LocalDateTime wostarttime;

    @Column(name = "abnm_woendtime", nullable = false)
//    @NotNull
    private LocalDateTime woendtime;

    @Column(name = "abnm_downtime", nullable = false)
//    @NotNull
    private BigDecimal downtime;

    @Column(name = "abnm_description", length = 500, nullable = false)
//    @NotBlank
//    @Size(max = 500)
    private String description;

    @Column(name = "abnm_typeid", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String typeid;

    @Column(name = "abnm_whyabnhappened", length = 500, nullable = false)
//    @NotBlank
//    @Size(max = 500)
    private String whyabnhappened;

    @Column(name = "abnm_whatcause", length = 500, nullable = false)
//    @NotBlank
//    @Size(max = 500)
    private String whatcause;

    @Column(name = "abnm_tagclassid", length = 15, nullable = false)
//    @NotBlank
//    @Size(max = 15)
    private String tagclassid;

    @Column(name = "abnm_categoryid", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String categoryid;

    @Column(name = "abnm_impactid", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String impactid;

    @Column(name = "abnm_countermeasure", length = 500, nullable = false)
//    @NotBlank
//    @Size(max = 500)
    private String countermeasure;

    @Column(name = "abnm_preventivemeasure", length = 500, nullable = false)
//    @NotBlank
//    @Size(max = 500)
    private String preventivemeasure;

    @Column(name = "abnm_status", length = 1, nullable = false)
//    @NotBlank
//    @Pattern(regexp = "[PCWDXI]", message = "Status must be P, C, W, D, X, or I")
    private String status;

    @Column(name = "abnm_targetdate", nullable = false)
//    @NotNull
    private LocalDateTime targetdate;

    @Column(name = "abnm_targetremarks", length = 500, nullable = false)
//    @NotBlank
//    @Size(max = 500)
    private String targetremarks;

    @Column(name = "abnm_completedby", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String completedby;

    @Column(name = "abnm_womasterid", length = 20, nullable = false)
//    @NotBlank
//    @Size(max = 20)
    private String womasterid;

    @Column(name = "abnm_wodetailid", length = 14, nullable = false)
//    @NotBlank
//    @Size(max = 14)
    private String wodetailid;

    @Column(name = "abnm_feedbackid", length = 14, nullable = false)
//    @NotBlank
//    @Size(max = 14)
    private String feedbackid;

    @Column(name = "abnm_feedbackdate", nullable = false)
//    @NotNull
    private LocalDateTime feedbackdate;

    @Column(name = "abnm_remarks", length = 500, nullable = false)
//    @NotBlank
//    @Size(max = 500)
    private String remarks;

    @Column(name = "abnm_blockdiagramref", length = 500, nullable = false)
//    @NotBlank
//    @Size(max = 500)
    private String blockdiagramref;

    @Column(name = "abnm_revisionno", length = 50, nullable = false)
//    @NotBlank
//    @Size(max = 50)
    private String revisionno;

    @Column(name = "abnm_priority", length = 50, nullable = false)
//    @NotBlank
//    @Size(max = 50)
    private String priority;

    @Column(name = "abnm_detailedesc", length = 500, nullable = false)
//    @NotBlank
//    @Size(max = 500)
    private String detailedesc;

    @Column(name = "abnm_subtype", length = 15, nullable = false)
//    @NotBlank
//    @Size(max = 15)
    private String subtype;

    @Column(name = "abnm_contaminant", length = 500, nullable = false)
//    @NotBlank
//    @Size(max = 500)
    private String contaminant;

    @Column(name = "abnm_mode", length = 500, nullable = false)
//    @NotBlank
//    @Size(max = 500)
    private String mode;

    @Column(name = "abnm_factoryid", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String factoryid;

    @Column(name = "abnm_pillar", length = 3, nullable = false)
//    @NotBlank
//    @Size(max = 3)
    private String pillar;

    @Column(name = "abnm_safetypatrol", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String safetypatrol;

    @Column(name = "abnm_relatedto", length = 3, nullable = false)
//    @NotBlank
//    @Size(max = 3)
    private String relatedto;

    @Column(name = "abnm_mould", length = 30, nullable = false)
//    @NotBlank
//    @Size(max = 30)
    private String mould;

    @Column(name = "abnm_flid", length = 12, nullable = false)
//    @NotBlank
//    @Size(max = 12)
    private String flid;

    @Column(name = "abnm_elementid", length = 250, nullable = false)
//    @NotBlank
//    @Size(max = 250)
    private String elementid;

    @Column(name = "abnm_pillarid", length = 12, nullable = false)
//    @NotBlank
//    @Size(max = 12)
    private String pillarid;

    @Column(name = "abnm_repeatedabn", length = 12, nullable = false)
//    @NotBlank
//    @Size(max = 12)
    private String repeatedabn;

    @Column(name = "abnm_afeemid", length = 12, nullable = false)
//    @NotBlank
//    @Size(max = 12)
    private String afeemid;

    @Column(name = "abnm_effectivedate", nullable = false)
//    @NotNull
    private LocalDateTime effectivedate = LocalDateTime.now();

    @Column(name = "abnm_notifysap", length = 1, nullable = false)
//    @NotBlank
//    @Pattern(regexp = "[YN]", message = "Notify SAP must be Y or N")
    private String notifysap = "N";

    @Column(name = "abnm_shutdownmaint", length = 1, nullable = false)
//    @NotBlank
//    @Pattern(regexp = "[YN]", message = "Shutdown maintenance must be Y or N")
    private String shutdownmaint = "N";

    @Column(name = "abnm_tentativedate", nullable = false)
//    @NotNull
    private LocalDateTime tentativedate = LocalDateTime.now();

    @Column(name = "abnm_shutdownid", length = 12, nullable = false)
//    @NotBlank
//    @Size(max = 12)
    private String shutdownid;

    @Column(name = "abnm_accecptrequired", length = 5, nullable = false)
//    @NotBlank
//    @Size(max = 5)
    private String accecpatncerequired = "N";

    @Column(name = "abnm_accecptdate", nullable = false)
//    @NotNull
    private LocalDateTime accecptdate = LocalDateTime.now();

    @Column(name = "abnm_accecpted", length = 1, nullable = false)
//    @NotBlank
//    @Pattern(regexp = "[YN]", message = "Accepted must be Y or N")
    private String accecpted = "N";

    @Column(name = "abnm_others", length = 5, nullable = false)
//    @NotBlank
//    @Size(max = 5)
    private String others;

    @Column(name = "abnm_repotheres", length = 5, nullable = false)
//    @NotBlank
//    @Size(max = 5)
    private String repotheres;

    @Column(name = "abnm_responsibleid", length = 10, nullable = false)
//    @NotBlank
//    @Size(max = 10)
    private String responsibleid;

    @Column(name = "abnm_multipleabn", length = 1, nullable = false)
//    @NotBlank
//    @Pattern(regexp = "[YN]", message = "Multiple abnormality must be Y or N")
    private String multipleabn;

    // Temporary fields
    @Column(name = "abnm_tempfield4", length = 5, nullable = false)
//    @NotBlank
//    @Size(max = 5)
    private String tempfield4;

    @Column(name = "abnm_tempfield5", length = 5, nullable = false)
//    @NotBlank
//    @Size(max = 5)
    private String tempfield5;

    @Column(name = "abnm_tempfield6", length = 5, nullable = false)
//    @NotBlank
//    @Size(max = 5)
    private String tempfield6;

    @Column(name = "abnm_tempfield7", length = 5, nullable = false)
//    @NotBlank
//    @Size(max = 5)
    private String tempfield7;

    @Column(name = "abnm_tempfield8", length = 5, nullable = false)
//    @NotBlank
//    @Size(max = 5)
    private String tempfield8;

    @Column(name = "abnm_tempfield9", length = 5, nullable = false)
//    @NotBlank
//    @Size(max = 5)
    private String tempfield9;

    @Column(name = "abnm_tempfield10", length = 5, nullable = false)
//    @NotBlank
//    @Size(max = 5)
    private String tempfield10;

    @Column(name = "abnm_active", length = 5, nullable = false)
//    @NotBlank
//    @Size(max = 5)
    private String active;

    @Column(name = "abnm_createdby", length = 8, nullable = false)
//    @NotBlank
//    @Size(max = 8)
    private String createdby;

    @Column(name = "abnm_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "abnm_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    // Enum for Status values
    public enum StatusType {
        P, C, W, D, X, I
    }

    // Enum for Yes/No fields
    public enum YesNoType {
        Y, N
    }

    // ============================================
    // EXPLICIT GETTER AND SETTER METHODS
    // ============================================

    // Key ID
    public String getKeyid() { return keyid; }
    public void setKeyid(String keyid) { this.keyid = keyid; }

    // Date
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    // Reference Document Type
    public String getRefdoctype() { return refdoctype; }
    public void setRefdoctype(String refDocType) { this.refdoctype = refDocType; }

    // Reference Document ID
    public String getRefdocid() { return refdocid; }
    public void setRefdocid(String refdocid) { this.refdocid = refdocid; }

    // Detection Date
    public LocalDateTime getDetectiondate() { return detectiondate; }
    public void setDetectiondate(LocalDateTime detectiondate) { this.detectiondate = detectiondate; }

    // Detected By
    public String getDetectedby() { return detectedby; }
    public void setDetectedby(String detectedby) { this.detectedby = detectedby; }

    // Equipment ID
    public String getEquipmentid() { return equipmentid; }
    public void setEquipmentid(String equipmentid) { this.equipmentid = equipmentid; }

    // Section ID
    public String getSectionid() { return sectionid; }
    public void setSectionid(String sectionid) { this.sectionid = sectionid; }

    // Cell ID
    public String getCellid() { return cellid; }
    public void setCellid(String cellid) { this.cellid = cellid; }

    // Assembly ID
    public String getAssemblyid() { return assemblyid; }
    public void setAssemblyid(String assemblyid) { this.assemblyid = assemblyid; }

    // Shift ID
    public String getShiftid() { return shiftid; }
    public void setShiftid(String shiftid) { this.shiftid = shiftid; }

    // Trade ID
    public String getTradeid() { return tradeid; }
    public void setTradeid(String tradeid) { this.tradeid = tradeid; }

    // Work Order Received Date
    public LocalDateTime getWoreceiveddate() { return woreceiveddate; }
    public void setWoreceiveddate(LocalDateTime woreceiveddate) { this.woreceiveddate = woreceiveddate; }

    // Response Time
    public BigDecimal getResponsetime() { return responsetime; }
    public void setResponsetime(BigDecimal responsetime) { this.responsetime = responsetime; }

    // Work Time
    public BigDecimal getWorktime() { return worktime; }
    public void setWorkTime(BigDecimal worktime) { this.worktime = worktime; }

    // Work Order Start Time
    public LocalDateTime getWostarttime() { return wostarttime; }
    public void setWostarttime(LocalDateTime wostarttime) { this.wostarttime = wostarttime; }

    // Work Order End Time
    public LocalDateTime getWoendtime() { return woendtime; }
    public void setWoendtime(LocalDateTime woendtime) { this.woendtime = woendtime; }

    // Downtime
    public BigDecimal getDowntime() { return downtime; }
    public void setDowntime(BigDecimal downtime) { this.downtime = downtime; }

    // Description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Type ID
    public String getTypeid() { return typeid; }
    public void setTypeid(String typeid) { this.typeid = typeid; }

    // Why Abnormality Happened
    public String getWhyabnhappened() { return whyabnhappened; }
    public void setWhyabnhappened(String whyabnhappened) { this.whyabnhappened = whyabnhappened; }

    // What Cause
    public String getWhatcause() { return whatcause; }
    public void setWhatcause(String whatcause) { this.whatcause = whatcause; }

    // Tag Class ID
    public String getTagclassid() { return tagclassid; }
    public void setTagclassid(String tagclassid) { this.tagclassid = tagclassid; }

    // Category ID
    public String getCategoryid() { return categoryid; }
    public void setCategoryid(String categoryid) { this.categoryid = categoryid; }

    // Impact ID
    public String getImpactid() { return impactid; }
    public void setImpactid(String impactid) { this.impactid = impactid; }

    // Counter Measure
    public String getCountermeasure() { return countermeasure; }
    public void setCountermeasure(String countermeasure) { this.countermeasure = countermeasure; }

    // Preventive Measure
    public String getPreventivemeasure() { return preventivemeasure; }
    public void setPreventivemeasure(String preventivemeasure) { this.preventivemeasure = preventivemeasure; }

    // Status
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Target Date
    public LocalDateTime getTargetdate() { return targetdate; }
    public void setTargetdate(LocalDateTime targetdate) { this.targetdate = targetdate; }

    // Target Remarks
    public String getTargetremarks() { return targetremarks; }
    public void setTargetremarks(String targetremarks) { this.targetremarks = targetremarks; }

    // Completed By
    public String getCompletedby() { return completedby; }
    public void setCompletedby(String completedby) { this.completedby = completedby; }

    // Work Order Master ID
    public String getWomasterid() { return womasterid; }
    public void setWomasterid(String womasterid) { this.womasterid = womasterid; }

    // Work Order Detail ID
    public String getWodetailid() { return wodetailid; }
    public void setWodetailid(String wodetailid) { this.wodetailid = wodetailid; }

    // Feedback ID
    public String getFeedbackid() { return feedbackid; }
    public void setFeedbackid(String feedbackid) { this.feedbackid = feedbackid; }

    // Feedback Date
    public LocalDateTime getFeedbackdate() { return feedbackdate; }
    public void setFeedbackdate(LocalDateTime feedbackdate) { this.feedbackdate = feedbackdate; }

    // Remarks
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    // Block Diagram Reference
    public String getBlockdiagramref() { return blockdiagramref; }
    public void setBlockdiagramref(String blockdiagramref) { this.blockdiagramref = blockdiagramref; }

    // Revision Number
    public String getRevisionno() { return revisionno; }
    public void setRevisionno(String revisionno) { this.revisionno = revisionno; }

    // Priority
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    // Detail Description
    public String getDetailedesc() { return detailedesc; }
    public void setDetailedesc(String detailedesc) { this.detailedesc = detailedesc; }

    // Sub Type
    public String getSubtype() { return subtype; }
    public void setSubtype(String subtype) { this.subtype = subtype; }

    // Contaminant
    public String getContaminant() { return contaminant; }
    public void setContaminant(String contaminant) { this.contaminant = contaminant; }

    // Mode
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    // Factory ID
    public String getFactoryid() { return factoryid; }
    public void setFactoryid(String factoryid) { this.factoryid = factoryid; }

    // Pillar
    public String getPillar() { return pillar; }
    public void setPillar(String pillar) { this.pillar = pillar; }

    // Safety Patrol
    public String getSafetypatrol() { return safetypatrol; }
    public void setSafetypatrol(String safetypatrol) { this.safetypatrol = safetypatrol; }

    // Related To
    public String getRelatedto() { return relatedto; }
    public void setRelatedto(String relatedto) { this.relatedto = relatedto; }

    // Mould
    public String getMould() { return mould; }
    public void setMould(String mould) { this.mould = mould; }

    // FL ID
    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }

    // Element ID
    public String getElementid() { return elementid; }
    public void setElementid(String elementid) { this.elementid = elementid; }

    // Pillar ID
    public String getPillarid() { return pillarid; }
    public void setPillarid(String pillarid) { this.pillarid = pillarid; }

    // Repeated Abnormality
    public String getRepeatedabn() { return repeatedabn; }
    public void setRepeatedabn(String repeatedabn) { this.repeatedabn = repeatedabn; }

    // AFEEM ID
    public String getAfeemid() { return afeemid; }
    public void setAfeemid(String afeemid) { this.afeemid = afeemid; }

    // Effective Date
    public LocalDateTime getEffectivedate() { return effectivedate; }
    public void setEffectivedate(LocalDateTime effectivedate) { this.effectivedate = effectivedate; }

    // Notify SAP
    public String getNotifysap() { return notifysap; }
    public void setNotifysap(String notifysap) { this.notifysap = notifysap; }

    // Shutdown Maintenance
    public String getShutdownmaint() { return shutdownmaint; }
    public void setShutdownmaint(String shutdownmaint) { this.shutdownmaint = shutdownmaint; }

    // Tentative Date
    public LocalDateTime getTentativedate() { return tentativedate; }
    public void setTentativedate(LocalDateTime tentativedate) { this.tentativedate = tentativedate; }

    // Shutdown ID
    public String getShutdownid() { return shutdownid; }
    public void setShutdownid(String shutdownid) { this.shutdownid = shutdownid; }

    // Accept Required
    public String getAccecpatncerequired() { return accecpatncerequired; }
    public void setAccecpatncerequired(String accecpatncerequired) { this.accecpatncerequired = accecpatncerequired; }

    // Accept Date
    public LocalDateTime getAccecptdate() { return accecptdate; }
    public void setAccecptdate(LocalDateTime accecptdate) { this.accecptdate = accecptdate; }

    // Accepted
    public String getAccecpted() { return accecpted; }
    public void setAccecpted(String accecpted) { this.accecpted = accecpted; }

    // Others
    public String getOthers() { return others; }
    public void setOthers(String others) { this.others = others; }

    // Repo Theres
    public String getRepotheres() { return repotheres; }
    public void setRepotheres(String repotheres) { this.repotheres = repotheres; }

    // Responsible ID
    public String getResponsibleid() { return responsibleid; }
    public void setResponsibleid(String responsibleid) { this.responsibleid = responsibleid; }

    // Multiple Abnormality
    public String getMultipleabn() { return multipleabn; }
    public void setMultipleabn(String multipleabn) { this.multipleabn = multipleabn; }

    // Temporary Fields
    public String getTempfield4() { return tempfield4; }
    public void setTempfield4(String tempfield4) { this.tempfield4 = tempfield4; }

    public String getTempfield5() { return tempfield5; }
    public void setTempfield5(String tempfield5) { this.tempfield5 = tempfield5; }

    public String getTempfield6() { return tempfield6; }
    public void setTempfield6(String tempfield6) { this.tempfield6 = tempfield6; }

    public String getTempfield7() { return tempfield7; }
    public void setTempfield7(String tempfield7) { this.tempfield7 = tempfield7; }

    public String getTempfield8() { return tempfield8; }
    public void setTempfield8(String tempfield8) { this.tempfield8 = tempfield8; }

    public String getTempfield9() { return tempfield9; }
    public void setTempfield9(String tempfield9) { this.tempfield9 = tempfield9; }

    public String getTempfield10() { return tempfield10; }
    public void setTempfield10(String tempfield10) { this.tempfield10 = tempfield10; }

    // Active
    public String getActive() { return active; }
    public void setActive(String active) { this.active = active; }

    // Created By
    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    // Created On
    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    // Modified On
    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) { this.modifiedon = modifiedon; }
}
