package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="wom_tl_communicationlog", schema = "public")
public class WomTlCommunicationlog {

    @Id
    @Column(name = "wcml_keyid", length = 11, nullable = false)
    private String keyid;

    @Column(name="wcml_wonumber", length = 25, nullable = false)
    private String wonumber;

    @Column(name="wcml_date", nullable = false)
    private LocalDateTime date;

    @Column(name="wcml_communicationtext", length = 600, nullable = false)
    private String communicationtext;

    @Column(name="wcml_level", length = 25, nullable = false)
    private String level;

    @Column(name="wcml_enteredby", length=15, nullable = false)
    private String enteredby;

    @Column(name="wcml_displayorderno", nullable = false)
    private int displayorderno;

    @Column(name="wcml_active", length=1, nullable = false)
    private char active;

    @Column(name="wcml_createdby", length=10, nullable = false)
    private String createdby;

    @Column(name = "wcml_createdon",nullable = false)
    private LocalDateTime createdon;

    @Column(name="wcml_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getWonumber() {
        return wonumber;
    }

    public void setWonumber(String wonumber) {
        this.wonumber = wonumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCommunicationtext() {
        return communicationtext;
    }

    public void setCommunicationtext(String communicationtext) {
        this.communicationtext = communicationtext;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEnteredby() {
        return enteredby;
    }

    public void setEnteredby(String enteredby) {
        this.enteredby = enteredby;
    }

    public int getDisplayorderno() {
        return displayorderno;
    }

    public void setDisplayorderno(int displayorderno) {
        this.displayorderno = displayorderno;
    }

    public char getActive() {
        return active;
    }

    public void setActive(char active) {
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