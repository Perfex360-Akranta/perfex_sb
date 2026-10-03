package com.akranta.perfex_sb.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.akranta.perfex_sb.model.BalPlmTlSpareconsumed;
import com.akranta.perfex_sb.model.BalPlmTlSparecostactual;
import com.akranta.perfex_sb.model.BalPlmTlWoFeedBack;

public class BalWorkOrderFeedbackDto {

    private BalPlmTlWoFeedBack feedback;

    private BalPlmTlSpareconsumed spareconsumed;

    private BalPlmTlSparecostactual sparecostactual;

    private LocalDateTime obsvTargetDate; 
    private String obsvResponsibility;
     private BigDecimal cbmReading;
      private LocalDateTime cbmNextDueDate;
       private BigDecimal cbmMinReading; 
       
       private BigDecimal cbmMaxReading; 
       private BigDecimal cbmAdjustedReading;
        private String pmstandId;
         private String pmCalendarId;


    public BalPlmTlWoFeedBack getFeedback() {
        return feedback;
    }

    public void setFeedback(BalPlmTlWoFeedBack feedback) {
        this.feedback = feedback;
    }

    public BalPlmTlSpareconsumed getSpareconsumed() {
        return spareconsumed;
    }

    public void setSpareconsumed(BalPlmTlSpareconsumed spareconsumed) {
        this.spareconsumed = spareconsumed;
    }

    public BalPlmTlSparecostactual getSparecostactual() {
        return sparecostactual;
    }

    public void setSparecostactual(
            BalPlmTlSparecostactual sparecostactual) {
        this.sparecostactual = sparecostactual;
    }

    public LocalDateTime getObsvTargetDate() {
        return obsvTargetDate;
    }

    public void setObsvTargetDate(LocalDateTime obsvTargetDate) {
        this.obsvTargetDate = obsvTargetDate;
    }

    public String getObsvResponsibility() {
        return obsvResponsibility;
    }

    public void setObsvResponsibility(String obsvResponsibility) {
        this.obsvResponsibility = obsvResponsibility;
    }

    public BigDecimal getCbmReading() {
        return cbmReading;
    }

    public void setCbmReading(BigDecimal cbmReading) {
        this.cbmReading = cbmReading;
    }

    public LocalDateTime getCbmNextDueDate() {
        return cbmNextDueDate;
    }

    public void setCbmNextDueDate(LocalDateTime cbmNextDueDate) {
        this.cbmNextDueDate = cbmNextDueDate;
    }

    public BigDecimal getCbmMinReading() {
        return cbmMinReading;
    }

    public void setCbmMinReading(BigDecimal cbmMinReading) {
        this.cbmMinReading = cbmMinReading;
    }

    public BigDecimal getCbmMaxReading() {
        return cbmMaxReading;
    }

    public void setCbmMaxReading(BigDecimal cbmMaxReading) {
        this.cbmMaxReading = cbmMaxReading;
    }

    public BigDecimal getCbmAdjustedReading() {
        return cbmAdjustedReading;
    }

    public void setCbmAdjustedReading(BigDecimal cbmAdjustedReading) {
        this.cbmAdjustedReading = cbmAdjustedReading;
    }

    public String getPmstandId() {
        return pmstandId;
    }

    public void setPmstandId(String pmstandId) {
        this.pmstandId = pmstandId;
    }

    public String getPmCalendarId() {
        return pmCalendarId;
    }

    public void setPmCalendarId(String pmCalendarId) {
        this.pmCalendarId = pmCalendarId;
    }

    
}