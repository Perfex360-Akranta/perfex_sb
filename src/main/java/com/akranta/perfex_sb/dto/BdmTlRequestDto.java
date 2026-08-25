package com.akranta.perfex_sb.dto;

import java.math.BigDecimal;

import com.akranta.perfex_sb.model.BdmTlCriticalityassessment;

public class  BdmTlRequestDto{

    // Master table: bdm_tl_criticalityassessment
    private BdmTlCriticalityassessment master;

    // Detail table: bdm_tl_mchrankskillhistory
    private BigDecimal TotalRating;

    // Default constructor
    public BdmTlRequestDto() {
    }

    // Parameterized constructor
    public BdmTlRequestDto(
            BdmTlCriticalityassessment master,
            BigDecimal TotalRating) {
        this.master = master;
        this.TotalRating = TotalRating;
    }

    // -------- Getters & Setters --------

    public BdmTlCriticalityassessment getMaster() {
        return master;
    }

    public void setMaster(BdmTlCriticalityassessment master) {
        this.master = master;
    }

    public BigDecimal getTotalRating() {
        return TotalRating;
    }

    public void setTotalRating(BigDecimal TotalRating) {
        this.TotalRating = TotalRating;
    }
}