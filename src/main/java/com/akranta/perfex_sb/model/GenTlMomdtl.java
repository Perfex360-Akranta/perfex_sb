package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

// import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

//import java.math.BigDecimal;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "gen_tl_momdtl", schema = "public")

public class GenTlMomdtl {
    @Id
    @Column(name = "momd_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "momd_moms_keyid", length = 15, nullable = false)
    private String momskeyid;

    @Column(name = "momd_discussion_type", length = 100, nullable = false)
    private String discussiontype;

    @Column(name = "momd_discussion_details", length = 500, nullable = false)
    // @NotBlank
    // @Size(max = 15)
    private String discussiondetails;

    @Column(name = "momd_actionplan_id", length = 15, nullable = false)
    // @NotNull
    private String actionplanid;

    @Column(name = "momd_remarks", length = 500, nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private String remarks;

    @Column(name = "momd_pillar", length = 35, nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private String pillar;

    @Column(name = "momd_tempfield1", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private Character tempfield1;

    @Column(name = "momd_tempfield2", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 500)
    private Character tempfield2;

    @Column(name = "momd_tempfield3", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 500)
    private Character tempfield3;

    @Column(name = "momd_tempfield4", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 500)
    private Character tempfield4;

    @Column(name = "momd_tempfield5", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 500)
    private Character tempfield5;

    @Column(name = "momd_active", columnDefinition = "CHAR(1)", nullable = false)
    // @NotBlank
    // @Size(max = 15)
    private Character active;

    @Column(name = "momd_createdby", length = 10, nullable = false)
    // @NotBlank
    // @Size(max = 10)
    private String createdby;

    @Column(name = "momd_createdon", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    // @NotBlank
    // @Size(max = 10)
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "momd_modifiedon", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    // @NotBlank
    // @Size(max = 500)
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getMomskeyid() {
        return momskeyid;
    }

    public void setMomskeyid(String momskeyid) {
        this.momskeyid = momskeyid;
    }

    public String getDiscussiontype() {
        return discussiontype;
    }

    public void setDiscussiontype(String discussiontype) {
        this.discussiontype = discussiontype;
    }

    public String getDiscussiondetails() {
        return discussiondetails;
    }

    public void setDiscussiondetails(String discussiondetails) {
        this.discussiondetails = discussiondetails;
    }

    public String getActionplanid() {
        return actionplanid;
    }

    public void setActionplanid(String actionplanid) {
        this.actionplanid = actionplanid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPillar() {
        return pillar;
    }

    public void setPillar(String pillar) {
        this.pillar = pillar;
    }

    public Character getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(Character tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public Character getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(Character tempfield2) {
        this.tempfield2 = tempfield2;
    }

    public Character getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(Character tempfield3) {
        this.tempfield3 = tempfield3;
    }

    public Character getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(Character tempfield4) {
        this.tempfield4 = tempfield4;
    }

    public Character getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(Character tempfield5) {
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

    @Override
    public String toString() {
        return "GenTlMomdtl [keyid=" + keyid + ", momskeyid=" + momskeyid + ", discussiontype=" + discussiontype
                + ", discussiondetails=" + discussiondetails + ", actionplanid=" + actionplanid + ", remarks=" + remarks
                + ",'']";
    }

    // ============================================
    // EXPLICIT GETTER AND SETTER METHODS
    // ============================================

}
