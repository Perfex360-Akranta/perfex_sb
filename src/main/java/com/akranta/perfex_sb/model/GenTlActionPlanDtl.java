package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "gen_tl_actionplandtl", schema = "public")
public class GenTlActionPlanDtl {

    @Id
    @Column(name = "apld_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "apld_aplm_keyid", length = 15, nullable = false)
    private String aplm_keyid;

    @Column(name = "apld_tradeid", length = 11, nullable = false)
    private String tradeid;

    @Column(name = "apld_actionplan", length = 500, nullable = false)
    private String actionplan;

    @Column(name = "apld_howtodo", length = 500, nullable = false)
    private String howtodo;

    @Column(name = "apld_responsibility", length = 8, nullable = false)
    private String responsibility;

    @Column(name = "apld_targetdate", nullable = false)
    private LocalDateTime targetdate;

    @Column(name = "apld_status", columnDefinition = "char(1)", nullable = false)
    private Character status;

    @Column(name = "apld_compleatedon", nullable = false)
    private LocalDateTime compleatedon;

    @Column(name = "apld_completedby", length = 8, nullable = false)
    private String completedby;

    @Column(name = "apld_countermeasure", length = 500, nullable = false)
    private String countermeasure;

    @Column(name = "apld_remarks", length = 500, nullable = false)
    private String remarks;

    @Column(name = "apld_others", columnDefinition = "char(1)", nullable = false)
    private Character others;

    @Column(name = "apld_tempfiled2", columnDefinition = "char(1)", nullable = false)
    private Character tempfiled2;

    @Column(name = "apld_tempfiled3", columnDefinition = "char(1)", nullable = false)
    private Character tempfiled3;

    @Column(name = "apld_tempfiled4", columnDefinition = "char(1)", nullable = false)
    private Character tempfiled4;

    @Column(name = "apld_tempfiled5", columnDefinition = "char(1)", nullable = false)
    private Character tempfiled5;

    @Column(name = "apld_active", columnDefinition = "char(1)", nullable = false)
    private Character active;

    @Column(name = "apld_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "apld_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "apld_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getAplm_keyid() {
        return aplm_keyid;
    }

    public void setAplm_keyid(String aplm_keyid) {
        this.aplm_keyid = aplm_keyid;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getActionplan() {
        return actionplan;
    }

    public void setActionplan(String actionplan) {
        this.actionplan = actionplan;
    }

    public String getHowtodo() {
        return howtodo;
    }

    public void setHowtodo(String howtodo) {
        this.howtodo = howtodo;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public LocalDateTime getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(LocalDateTime targetdate) {
        this.targetdate = targetdate;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public LocalDateTime getCompleatedon() {
        return compleatedon;
    }

    public void setCompleatedon(LocalDateTime compleatedon) {
        this.compleatedon = compleatedon;
    }

    public String getCompletedby() {
        return completedby;
    }

    public void setCompletedby(String completedby) {
        this.completedby = completedby;
    }

    public String getCountermeasure() {
        return countermeasure;
    }

    public void setCountermeasure(String countermeasure) {
        this.countermeasure = countermeasure;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Character getOthers() {
        return others;
    }

    public void setOthers(Character others) {
        this.others = others;
    }

    public Character getTempfiled2() {
        return tempfiled2;
    }

    public void setTempfiled2(Character tempfiled2) {
        this.tempfiled2 = tempfiled2;
    }

    public Character getTempfiled3() {
        return tempfiled3;
    }

    public void setTempfiled3(Character tempfiled3) {
        this.tempfiled3 = tempfiled3;
    }

    public Character getTempfiled4() {
        return tempfiled4;
    }

    public void setTempfiled4(Character tempfiled4) {
        this.tempfiled4 = tempfiled4;
    }

    public Character getTempfiled5() {
        return tempfiled5;
    }

    public void setTempfiled5(Character tempfiled5) {
        this.tempfiled5 = tempfiled5;
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
