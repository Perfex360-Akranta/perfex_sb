package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "qtm_tl_knowwhydtl", schema = "public")
public class QtmTlKnowwhydtl {

    @Column(name = "knwd_keyid", length = 15, nullable = false)
    @Id
    private String keyid;

    @Column(name = "knwd_knwm_keyid", length = 15, nullable = false)
    private String knwm_keyid;

    @Column(name = "knwd_possiblecauses", length = 500, nullable = false)
    private String possiblecauses;

    @Column(name = "knwd_knowwhy", length = 500, nullable = false)
    private String knowwhy;

    @Column(name = "knwd_solution", length = 500, nullable = false)
    private String solution;

    @Column(name = "knwd_normalcondition", length = 500, nullable = false)
    private String normalcondition;

    @Column(name = "knwd_sustenanceaction", length = 500, nullable = false)
    private String sustenanceaction;

    @Column(name = "knwd_tempfield2", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield2;

    @Column(name = "knwd_tempfield3", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield3;

    @Column(name = "knwd_tempfield4", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield4;

    @Column(name = "knwd_tempfield5", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield5;

    @Column(name = "knwd_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "knwd_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "knwd_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "knwd_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getKnwm_keyid() {
        return knwm_keyid;
    }

    public void setKnwm_keyid(String knwm_keyid) {
        this.knwm_keyid = knwm_keyid;
    }

    public String getPossiblecauses() {
        return possiblecauses;
    }

    public void setPossiblecauses(String possiblecauses) {
        this.possiblecauses = possiblecauses;
    }

    public String getKnowwhy() {
        return knowwhy;
    }

    public void setKnowwhy(String knowwhy) {
        this.knowwhy = knowwhy;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getNormalcondition() {
        return normalcondition;
    }

    public void setNormalcondition(String normalcondition) {
        this.normalcondition = normalcondition;
    }

    public String getSustenanceaction() {
        return sustenanceaction;
    }

    public void setSustenanceaction(String sustenanceaction) {
        this.sustenanceaction = sustenanceaction;
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
