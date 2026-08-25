package com.akranta.perfex_sb.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public class TrainingCalendarRequest {

    private EntTlTragcalmstDto master;
    private EntTlTrgCalSessionDto session;
    private EntTlTrgFacultyDto faculty;
    private List<EntTlTrgCalUnqpDto> uniquePositions;
    @JsonAlias({"traCalId", "calendarId", "etcmKeyid", "keyid", "trgCalId", "txtEtcmKeyid", "etcm_keyid"})
    private String calendarId;

    public EntTlTragcalmstDto getMaster() {
        return master;
    }

    public void setMaster(EntTlTragcalmstDto master) {
        this.master = master;
    }

    public EntTlTrgCalSessionDto getSession() {
        return session;
    }

    public void setSession(EntTlTrgCalSessionDto session) {
        this.session = session;
    }

    public EntTlTrgFacultyDto getFaculty() {
        return faculty;
    }

    public void setFaculty(EntTlTrgFacultyDto faculty) {
        this.faculty = faculty;
    }

    public List<EntTlTrgCalUnqpDto> getUniquePositions() {
        return uniquePositions;
    }

    public void setUniquePositions(List<EntTlTrgCalUnqpDto> uniquePositions) {
        this.uniquePositions = uniquePositions;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }
}
