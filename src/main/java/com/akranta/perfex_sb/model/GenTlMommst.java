package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

// import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

//import java.math.BigDecimal;
//import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "gen_tl_mommst", schema = "public")

public class GenTlMommst {
    @Id
    @Column(name = "moms_keyid", columnDefinition = "text", nullable = false)
    private String keyid;

    @Column(name = "moms_flid", columnDefinition = "text", nullable = false)
    private String flid;

    @Column(name = "moms_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    @Column(name = "moms_shiftid", columnDefinition = "text", nullable = false)
    // @NotBlank
    // @Size(max = 15)
    private String shiftid;

    @Column(name = "moms_ismeetinghappen", columnDefinition = "CHAR(1)", nullable = false)
    // @NotNull
    private Character ismeetinghappen;

    @Column(name = "moms_safetytalk", columnDefinition = "text", nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private String safetytalk;

    @Column(name = "moms_remarks", columnDefinition = "text", nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private String remarks;

    @Column(name = "moms_meetingno", length = 20, nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private String meetingno;

    @Column(name = "moms_meetingtitle", columnDefinition = "text", nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private String meetingtitle;

    @Column(name = "moms_meetingtype", columnDefinition = "text", nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private String meetingtype;

    @Column(name = "moms_pillarid", columnDefinition = "text", nullable = false)
    // @NotBlank
    // @Size(max = 15)
    private String pillarid;

    @Column(name = "moms_ismessageboard", columnDefinition = "text", nullable = false)
    // @NotNull
    private String ismessageboard;

    @Column(name = "moms_agenda", columnDefinition = "text", nullable = false)
    // @NotNull
    private String agenda;

    @Column(name = "moms_others", columnDefinition = "CHAR(1)", nullable = false)
    // @NotNull
    private Character others;

    @Column(name = "moms_pillargroup", columnDefinition = "text", nullable = false)
    // @NotNull
    private String momsPillargroup;

    @Column(name = "moms_refdocid", columnDefinition = "text", nullable = false)
    // @NotNull
    private String refdocid;

    @Column(name = "moms_refdoctype", columnDefinition = "text", nullable = false)
    // @NotNull
    private String refdoctype;

    @Column(name = "moms_ismailtrig", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    // @NotBlank
    // @Size(max = 500)
    private Character ismail;

    @Column(name = "moms_tempfield1", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private Character tempefield1;

    @Column(name = "moms_tempfield2", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 500)
    private Character tempefield2;

    @Column(name = "moms_tempfield3", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 500)
    private Character tempefield3;

    @Column(name = "moms_active", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 15)
    private Character active;

    @Column(name = "moms_createdby", columnDefinition = "text", nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private String createdby;

    @Column(name = "moms_createdon", nullable = false)

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    // @NotBlank
    // @Size(max = 10)
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "moms_modifiedon", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    // @NotBlank
    // @Size(max = 500)
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    // ============================================
    // EXPLICIT GETTER AND SETTER METHODS
    // ============================================

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getShiftid() {
        return shiftid;
    }

    public void setShiftid(String shiftid) {
        this.shiftid = shiftid;
    }

    public Character getIsmeetinghappen() {
        return ismeetinghappen;
    }

    public void setIsmeetinghappen(Character ismeetinghappen) {
        this.ismeetinghappen = ismeetinghappen;
    }

    public String getSafetytalk() {
        return safetytalk;
    }

    public void setSafetytalk(String safetytalk) {
        this.safetytalk = safetytalk;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMeetingno() {
        return meetingno;
    }

    public void setMeetingno(String meetingno) {
        this.meetingno = meetingno;
    }

    public String getMeetingtitle() {
        return meetingtitle;
    }

    public void setMeetingtitle(String meetingtitle) {
        this.meetingtitle = meetingtitle;
    }

    public String getMeetingtype() {
        return meetingtype;
    }

    public void setMeetingtype(String meetingtype) {
        this.meetingtype = meetingtype;
    }

    public String getPillarid() {
        return pillarid;
    }

    public void setPillarid(String pillarid) {
        this.pillarid = pillarid;
    }

    public String getIsmessageboard() {
        return ismessageboard;
    }

    public void setIsmessageboard(String ismessageboard) {
        this.ismessageboard = ismessageboard;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public Character getOthers() {
        return others;
    }

    public void setOthers(Character others) {
        this.others = others;
    }

    public String getMomsPillargroup() {
        return momsPillargroup;
    }

    public void setMomsPillargroup(String momsPillargroup) {
        this.momsPillargroup = momsPillargroup;
    }

    public String getRefdocid() {
        return refdocid;
    }

    public void setRefdocid(String refdocid) {
        this.refdocid = refdocid;
    }

    public String getRefdoctype() {
        return refdoctype;
    }

    public void setRefdoctype(String refdoctype) {
        this.refdoctype = refdoctype;
    }

    public Character getIsmail() {
        return ismail;
    }

    public void setIsmail(Character ismail) {
        this.ismail = ismail;
    }

    public Character getTempefield1() {
        return tempefield1;
    }

    public void setTempefield1(Character tempefield1) {
        this.tempefield1 = tempefield1;
    }

    public Character getTempefield2() {
        return tempefield2;
    }

    public void setTempefield2(Character tempefield2) {
        this.tempefield2 = tempefield2;
    }

    public Character getTempefield3() {
        return tempefield3;
    }

    public void setTempefield3(Character tempefield3) {
        this.tempefield3 = tempefield3;
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

    @Override
    public String toString() {
        return "GenTlMommst [keyid=" + keyid + ", flid=" + flid + ", date=" + date + ", shiftid=" + shiftid
                + ", ismeetinghappen=" + ismeetinghappen + ", safetytalk=" + safetytalk + ", remarks=" + remarks
                + ", meetingno=" + meetingno + ", meetingtitle=" + meetingtitle + ", meetingtype=" + meetingtype
                + ", pillarid=" + pillarid + ", ismessageboard=" + ismessageboard + ", agenda=" + agenda + ", others="
                + others + ", momsPillargroup=" + momsPillargroup + ", refdocid=" + refdocid + ", refdoctype="
                + refdoctype + ", ismail=" + ismail + ", tempefield1=" + tempefield1 + ", tempefield2=" + tempefield2
                + ", tempefield3=" + tempefield3 + ", active=" + active + ", createdby=" + createdby + ", createdon="
                + createdon + ", modifiedon=" + modifiedon + "]";
    }

}
