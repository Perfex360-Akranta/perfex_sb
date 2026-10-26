package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.BalPlmTlCbmwocompdtl;
import com.akranta.perfex_sb.model.BalPlmTlMultipleResp;
import com.akranta.perfex_sb.model.BalPlmTlObservations;
import com.akranta.perfex_sb.model.BalPlmTlSpareconsumed;
import com.akranta.perfex_sb.model.BalPlmTlSparecostactual;
import com.akranta.perfex_sb.model.BalPlmTlWoFeedBack;

public class BalSaveWorkOrderDetailsDto 
{

    List<BalWorkOrderFeedbackDto> feedbackList;


   // BalPlmTlWoFeedBack feedBackResponsibility;

    List<BalWorkOrderDetailsListDto> workOrderDetailsLstBean;

    List<BalPlmTlSpareconsumed> spareconsumed;

    List<BalPlmTlSparecostactual> sparecostactual;

    List<BalPlmTlMultipleResp> multipleResps;

    BalPlmTlObservations observations;

    BalPlmTlCbmwocompdtl cbmwocompdtl;

    private String pmStdId;

    

    
    public List<BalWorkOrderFeedbackDto> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<BalWorkOrderFeedbackDto> feedbackList) {
        this.feedbackList = feedbackList;
    }

    // public BalPlmTlWoFeedBack getFeedBackResponsibility() {
    //     return feedBackResponsibility;
    // }

    // public void setFeedBackResponsibility(BalPlmTlWoFeedBack feedBackResponsibility) {
    //     this.feedBackResponsibility = feedBackResponsibility;
    // }

    public List<BalWorkOrderDetailsListDto> getWorkOrderDetailsLstBean() {
        return workOrderDetailsLstBean;
    }

    public void setWorkOrderDetailsLstBean(List<BalWorkOrderDetailsListDto> workOrderDetailsLstBean) {
        this.workOrderDetailsLstBean = workOrderDetailsLstBean;
    }
    
    

    public List<BalPlmTlSpareconsumed> getSpareconsumed() {
        return spareconsumed;
    }

    public void setSpareconsumed(List<BalPlmTlSpareconsumed> spareconsumed) {
        this.spareconsumed = spareconsumed;
    }

    public List<BalPlmTlSparecostactual> getSparecostactual() {
        return sparecostactual;
    }

    public void setSparecostactual(List<BalPlmTlSparecostactual> sparecostactual) {
        this.sparecostactual = sparecostactual;
    }

    public List<BalPlmTlMultipleResp> getMultipleResps() {
        return multipleResps;
    }

    public void setMultipleResps(List<BalPlmTlMultipleResp> multipleResps) {
        this.multipleResps = multipleResps;
    }

    public BalPlmTlObservations getObservations() {
        return observations;
    }

    public void setObservations(BalPlmTlObservations observations) {
        this.observations = observations;
    }

    public BalPlmTlCbmwocompdtl getCbmwocompdtl() {
        return cbmwocompdtl;
    }

    public void setCbmwocompdtl(BalPlmTlCbmwocompdtl cbmwocompdtl) {
        this.cbmwocompdtl = cbmwocompdtl;
    }

    public String getPmStdId() {
        return pmStdId;
    }

    public void setPmStdId(String pmStdId) {
        this.pmStdId = pmStdId;
    }

    


}
