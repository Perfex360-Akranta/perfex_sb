package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "she_tl_plannedjobobservation", schema = "public")
public class SheTlPlannedJobObservation {

    @Id
    @Column(name = "pjob_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "pjob_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "pjob_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "pjob_employeeid", length = 1000, nullable = false)
    private String employeeid;

    @Column(name = "pjob_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "pjob_location_description", length = 100, nullable = false)
    private String location_description;

    @Column(name = "pjob_task_observed", length = 500, nullable = false)
    private String task_observed;

    @Column(name = "pjob_type", length = 1, nullable = false)
    private Character type;

    @Column(name = "pjob_notification", length = 1, nullable = false)
    private Character notification;

    @Column(name = "pjob_reasonid", length = 10, nullable = false)
    private String reasonid;

    @Column(name = "pjob_is_property_damage", length = 1, nullable = false)
    private Character is_property_damage;

    @Column(name = "pjob_is_efficient", length = 1, nullable = false)
    private Character is_efficient;

    @Column(name = "pjob_is_comply", length = 1, nullable = false)
    private Character is_comply;

    @Column(name = "pjob_is_detrimental", length = 1, nullable = false)
    private Character is_detrimental;

    @Column(name = "pjob_corrections", length = 500, nullable = false)
    private String corrections;

    @Column(name = "pjob_deviations", length = 500, nullable = false)
    private String deviations;

    @Column(name = "pjob_is_complemented", length = 1, nullable = false)
    private Character is_complemented;

    @Column(name = "pjob_is_follow_up", length = 1, nullable = false)
    private Character is_follow_up;

    @Column(name = "pjob_standard_procedure", length = 500, nullable = false)
    private String standard_procedure;

    @Column(name = "pjob_manager", length = 10, nullable = false)
    private String manager;

    @Column(name = "pjob_follow_up_action", length = 500, nullable = false)
    private String follow_up_action;

    @Column(name = "pjob_follow_up_date", nullable = false)
    private LocalDateTime follow_up_date;
    
    @Column(name = "pjob_follow_up_done", length = 10, nullable = false)
    private String follow_up_done;

    @Column(name = "pjob_practicecond", length = 50, nullable = false)
    private String practicecond;

    @Column(name = "pjob_followup_pjono", length = 12, nullable = false)
    private String followuppjono;

    @Column(name = "pjob_followup_taskobserved", length = 500, nullable = false)
    private String folowuptaskobserved;

    @Column(name = "pjob_sopno", length = 50, nullable = false)
    private String sopno;

    @Column(name = "pjob_tempfield5", length = 18, nullable = false)
    private String tempfield5;

    @Column(name = "pjob_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "pjob_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "pjob_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "pjob_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    @Column(name = "pjob_actionplan_id", length = 20)
    private String actionplan_id;

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

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation_description() {
        return location_description;
    }

    public void setLocation_description(String location_description) {
        this.location_description = location_description;
    }

    public String getTask_observed() {
        return task_observed;
    }

    public void setTask_observed(String task_observed) {
        this.task_observed = task_observed;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    public Character getNotification() {
        return notification;
    }

    public void setNotification(Character notification) {
        this.notification = notification;
    }

    public String getReasonid() {
        return reasonid;
    }

    public void setReasonid(String reasonid) {
        this.reasonid = reasonid;
    }

    public Character getIs_property_damage() {
        return is_property_damage;
    }

    public void setIs_property_damage(Character is_property_damage) {
        this.is_property_damage = is_property_damage;
    }

    public Character getIs_efficient() {
        return is_efficient;
    }

    public void setIs_efficient(Character is_efficient) {
        this.is_efficient = is_efficient;
    }

    public Character getIs_comply() {
        return is_comply;
    }

    public void setIs_comply(Character is_comply) {
        this.is_comply = is_comply;
    }

    public Character getIs_detrimental() {
        return is_detrimental;
    }

    public void setIs_detrimental(Character is_detrimental) {
        this.is_detrimental = is_detrimental;
    }

    public String getCorrections() {
        return corrections;
    }

    public void setCorrections(String corrections) {
        this.corrections = corrections;
    }

    public String getDeviations() {
        return deviations;
    }

    public void setDeviations(String deviations) {
        this.deviations = deviations;
    }

    public Character getIs_complemented() {
        return is_complemented;
    }

    public void setIs_complemented(Character is_complemented) {
        this.is_complemented = is_complemented;
    }

    public Character getIs_follow_up() {
        return is_follow_up;
    }

    public void setIs_follow_up(Character is_follow_up) {
        this.is_follow_up = is_follow_up;
    }

    public String getStandard_procedure() {
        return standard_procedure;
    }

    public void setStandard_procedure(String standard_procedure) {
        this.standard_procedure = standard_procedure;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getFollow_up_action() {
        return follow_up_action;
    }

    public void setFollow_up_action(String follow_up_action) {
        this.follow_up_action = follow_up_action;
    }

    public LocalDateTime getFollow_up_date() {
        return follow_up_date;
    }

    public void setFollow_up_date(LocalDateTime follow_up_date) {
        this.follow_up_date = follow_up_date;
    }

    public String getFollow_up_done() {
        return follow_up_done;
    }

    public void setFollow_up_done(String follow_up_done) {
        this.follow_up_done = follow_up_done;
    }

    public String getPracticecond() {
        return practicecond;
    }

    public void setPracticecond(String practicecond) {
        this.practicecond = practicecond;
    }

    public String getFollowuppjono() {
        return followuppjono;
    }

    public void setFollowuppjono(String followuppjono) {
        this.followuppjono = followuppjono;
    }

    public String getFolowuptaskobserved() {
        return folowuptaskobserved;
    }

    public void setFolowuptaskobserved(String folowuptaskobserved) {
        this.folowuptaskobserved = folowuptaskobserved;
    }

    public String getSopno() {
        return sopno;
    }

    public void setSopno(String sopno) {
        this.sopno = sopno;
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

    public String getActionplan_id() {
        return actionplan_id;
    }

    public void setActionplan_id(String actionplan_id) {
        this.actionplan_id = actionplan_id;
    }
}