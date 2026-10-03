package com.akranta.perfex_sb.dto;

public class NextDueDateDTO {
    private String freq;
    private String nextDueDate;

    public NextDueDateDTO(String freq, String nextDueDate) {
        this.freq = freq;
        this.nextDueDate = nextDueDate;
    }

    public String getFreq() { return freq; }
    public void setFreq(String freq) { this.freq = freq; }

    public String getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(String nextDueDate) { this.nextDueDate = nextDueDate; }

}
