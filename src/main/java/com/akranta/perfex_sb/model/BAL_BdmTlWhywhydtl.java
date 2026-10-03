package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bal_bdm_tl_whywhydtl", schema = "public")
public class BAL_BdmTlWhywhydtl {

    @Id
    @Column(name = "wwdt_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "wwdt_wwms_keyid", length = 12)
    private String wwmsKeyid;

    @Column(name = "wwdt_slno")
    private BigDecimal slno;

    @Column(name = "wwdt_why", length = 500)
    private String why;

    @Column(name = "wwdt_answer", length = 500)
    private String answer;

    @Column(name = "wwdt_action", length = 500)
    private String action;

    @Column(name = "wwdt_createdby", length = 8)
    private String createdby;

    @Column(name = "wwdt_createdon")
    private LocalDateTime createdon;

    @Column(name = "wwdt_modifiedon")
    private LocalDateTime modifiedon;

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getWwmsKeyid() {
        return wwmsKeyid;
    }

    public void setWwmsKeyid(String wwmsKeyid) {
        this.wwmsKeyid = wwmsKeyid;
    }

    public BigDecimal getSlno() {
        return slno;
    }

    public void setSlno(BigDecimal slno) {
        this.slno = slno;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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