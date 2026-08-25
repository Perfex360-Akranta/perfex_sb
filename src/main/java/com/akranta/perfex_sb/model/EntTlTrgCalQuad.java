package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ent_tl_trgcalquad", schema = "public")
public class EntTlTrgCalQuad {

    @Id
    @Column(name = "etcq_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "etcq_empm_keyid", length = 15, nullable = false)
    private String empm_keyid;

    @Column(name = "etcq_empm_roleid", length = 10, nullable = false)
    private String empm_roleid;

    @Column(name = "etcq_topicid", length = 12, nullable = false)
    private String empm_topicid;

    @Column(name = "etcq_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "etcq_location", length = 12, nullable = false)
    private String location;

    @Column(name = "etcq_dmt", length = 12, nullable = false)
    private String dmt;

    @Column(name = "etcq_jh", length = 12, nullable = false)
    private String jh;

    @Column(name = "etcq_currentlevel", nullable = false)
    private BigDecimal currentlevel;

    @Column(name = "etcq_currentleveldate", nullable = false)
    private LocalDateTime currleveldate;

    @Column(name = "etcq_l1pass",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character l1pass;

    @Column(name = "etcq_l1date", nullable = false)
    private LocalDateTime l1date;

    @Column(name = "etcq_l1_trgcalid", length = 15, nullable = false)
    private String l1trgcalid;

    @Column(name = "etcq_l1remarks", length = 500, nullable = false)
    private String l1remarks;

    @Column(name = "etcq_l2pass",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character l2pass;

    @Column(name = "etcq_l2date", nullable = false)
    private LocalDateTime l2date;

    @Column(name = "etcq_l2_trgcalid", length = 15, nullable = false)
    private String l2trgcalid;

    @Column(name = "etcq_l2remarks", length = 500, nullable = false)
    private String l2remarks;

    @Column(name = "etcq_l3pass",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character l3pass;

    @Column(name = "etcq_l3date", nullable = false)
    private LocalDateTime l3date;

    @Column(name = "etcq_l3_updby", length = 8, nullable = false)
    private String l3updby;

    @Column(name = "etcq_l3remarks", length = 500, nullable = false)
    private String l3remarks;

    @Column(name = "etcq_l4pass",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character l4pass;

    @Column(name = "etcq_l4date", nullable = false)
    private LocalDateTime l4date;

    @Column(name = "etcq_l4_updby", length = 8, nullable = false)
    private String l4updby;

    @Column(name = "etcq_l4remarks", length = 500, nullable = false)
    private String l4remarks;

    @Column(name = "etcq_tempfield1", length = 1, nullable = false)
    private String tempfield1;

    @Column(name = "etcq_tempfield2", length = 1, nullable = false)
    private String tempfield2;

    @Column(name = "etcq_tempfield3", length = 1, nullable = false)
    private String tempfield3;

    @Column(name = "etcq_tempfield4", length = 1, nullable = false)
    private String tempfield4;

    @Column(name = "etcq_tempfield5", length = 1, nullable = false)
    private String tempfield5;

    @Column(name = "etcq_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "etcq_active",columnDefinition = "char(1)", length = 1, nullable = false)
    private Character active;

    @Column(name = "etcq_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "etcq_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getEmpm_keyid() {
        return empm_keyid;
    }

    public void setEmpm_keyid(String empm_keyid) {
        this.empm_keyid = empm_keyid;
    }

    public String getEmpm_roleid() {
        return empm_roleid;
    }

    public void setEmpm_roleid(String empm_roleid) {
        this.empm_roleid = empm_roleid;
    }

    public String getEmpm_topicid() {
        return empm_topicid;
    }

    public void setEmpm_topicid(String empm_topicid) {
        this.empm_topicid = empm_topicid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDmt() {
        return dmt;
    }

    public void setDmt(String dmt) {
        this.dmt = dmt;
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh;
    }

    public BigDecimal getCurrentlevel() {
        return currentlevel;
    }

    public void setCurrentlevel(BigDecimal currentlevel) {
        this.currentlevel = currentlevel;
    }

    public LocalDateTime getCurrleveldate() {
        return currleveldate;
    }

    public void setCurrleveldate(LocalDateTime currleveldate) {
        this.currleveldate = currleveldate;
    }

    public Character getL1pass() {
        return l1pass;
    }

    public void setL1pass(Character l1pass) {
        this.l1pass = l1pass;
    }

    public LocalDateTime getL1date() {
        return l1date;
    }

    public void setL1date(LocalDateTime l1date) {
        this.l1date = l1date;
    }

    public String getL1trgcalid() {
        return l1trgcalid;
    }

    public void setL1trgcalid(String l1trgcalid) {
        this.l1trgcalid = l1trgcalid;
    }

    public String getL1remarks() {
        return l1remarks;
    }

    public void setL1remarks(String l1remarks) {
        this.l1remarks = l1remarks;
    }

    public Character getL2pass() {
        return l2pass;
    }

    public void setL2pass(Character l2pass) {
        this.l2pass = l2pass;
    }

    public LocalDateTime getL2date() {
        return l2date;
    }

    public void setL2date(LocalDateTime l2date) {
        this.l2date = l2date;
    }

    public String getL2trgcalid() {
        return l2trgcalid;
    }

    public void setL2trgcalid(String l2trgcalid) {
        this.l2trgcalid = l2trgcalid;
    }

    public String getL2remarks() {
        return l2remarks;
    }

    public void setL2remarks(String l2remarks) {
        this.l2remarks = l2remarks;
    }

    public Character getL3pass() {
        return l3pass;
    }

    public void setL3pass(Character l3pass) {
        this.l3pass = l3pass;
    }

    public LocalDateTime getL3date() {
        return l3date;
    }

    public void setL3date(LocalDateTime l3date) {
        this.l3date = l3date;
    }

    public String getL3updby() {
        return l3updby;
    }

    public void setL3updby(String l3updby) {
        this.l3updby = l3updby;
    }

    public String getL3remarks() {
        return l3remarks;
    }

    public void setL3remarks(String l3remarks) {
        this.l3remarks = l3remarks;
    }

    public Character getL4pass() {
        return l4pass;
    }

    public void setL4pass(Character l4pass) {
        this.l4pass = l4pass;
    }

    public LocalDateTime getL4date() {
        return l4date;
    }

    public void setL4date(LocalDateTime l4date) {
        this.l4date = l4date;
    }

    public String getL4updby() {
        return l4updby;
    }

    public void setL4updby(String l4updby) {
        this.l4updby = l4updby;
    }

    public String getL4remarks() {
        return l4remarks;
    }

    public void setL4remarks(String l4remarks) {
        this.l4remarks = l4remarks;
    }

    public String getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(String tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public String getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(String tempfield2) {
        this.tempfield2 = tempfield2;
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

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
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