package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.BalPlmTlSpareconsumed;
import com.akranta.perfex_sb.model.BalPlmTlSparecostactual;

public class BalWorkOrderDetailsDto 
{
    private String wofbStarttime;
    private String wofbEndtime;
    private String wofbProdStarttime;
    private String wofbObservation;

    private String pmCalendarId;
    private String fromMonth;
    private String assmbleyId;
    private String wOmstId;
    private String pmstdId;

    private String directSave;
    private String jobtype;
    private String plannedduration;
    private String wofbAction;
    private String wofbDuration;
    private String wksmWodetailid;

    private String beanWofbCompletedby;
    private String formActionMode;
    private String formMode;
    private String actionmode;
    private String disableForm;

    private String upcWorkOrderNo;
    private String upcWorkdetailid;
    private String description;
    private String allotedTo;
    private String modeModify;

    private BalPlmTlSpareconsumed spareConsumed;
    private BalPlmTlSparecostactual spareCostActual;

    private String dbLocation;
    private String wofbMachinetkeovrtime;

    /** CBM DATA **/

    private String currentReading;
    private String adjustReading;
    private String nextdueDate;
    private String minmumReading;
    private String maximumReading;

    private String genwCellid;
    private String genwFactoryid;
    private String genwSectionid;
    private String genwMachineid;
    private String genwFlid;
    private String genwElementid;
    private String wogenTradeid;
    private String genwLocationid;
    private String wogenAssemblyid;
   
   
    public String getWofbStarttime() {
        return wofbStarttime;
    }
    public void setWofbStarttime(String wofbStarttime) {
        this.wofbStarttime = wofbStarttime;
    }
    public String getWofbEndtime() {
        return wofbEndtime;
    }
    public void setWofbEndtime(String wofbEndtime) {
        this.wofbEndtime = wofbEndtime;
    }
    public String getWofbProdStarttime() {
        return wofbProdStarttime;
    }
    public void setWofbProdStarttime(String wofbProdStarttime) {
        this.wofbProdStarttime = wofbProdStarttime;
    }
    public String getWofbObservation() {
        return wofbObservation;
    }
    public void setWofbObservation(String wofbObservation) {
        this.wofbObservation = wofbObservation;
    }
    public String getPmCalendarId() {
        return pmCalendarId;
    }
    public void setPmCalendarId(String pmCalendarId) {
        this.pmCalendarId = pmCalendarId;
    }
    public String getFromMonth() {
        return fromMonth;
    }
    public void setFromMonth(String fromMonth) {
        this.fromMonth = fromMonth;
    }
    public String getAssmbleyId() {
        return assmbleyId;
    }
    public void setAssmbleyId(String assmbleyId) {
        this.assmbleyId = assmbleyId;
    }
    public String getwOmstId() {
        return wOmstId;
    }
    public void setwOmstId(String wOmstId) {
        this.wOmstId = wOmstId;
    }
    public String getPmstdId() {
        return pmstdId;
    }
    public void setPmstdId(String pmstdId) {
        this.pmstdId = pmstdId;
    }
    public String getDirectSave() {
        return directSave;
    }
    public void setDirectSave(String directSave) {
        this.directSave = directSave;
    }
    public String getJobtype() {
        return jobtype;
    }
    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }
    public String getPlannedduration() {
        return plannedduration;
    }
    public void setPlannedduration(String plannedduration) {
        this.plannedduration = plannedduration;
    }
    public String getWofbAction() {
        return wofbAction;
    }
    public void setWofbAction(String wofbAction) {
        this.wofbAction = wofbAction;
    }
    public String getWofbDuration() {
        return wofbDuration;
    }
    public void setWofbDuration(String wofbDuration) {
        this.wofbDuration = wofbDuration;
    }
    public String getWksmWodetailid() {
        return wksmWodetailid;
    }
    public void setWksmWodetailid(String wksmWodetailid) {
        this.wksmWodetailid = wksmWodetailid;
    }
    public String getBeanWofbCompletedby() {
        return beanWofbCompletedby;
    }
    public void setBeanWofbCompletedby(String beanWofbCompletedby) {
        this.beanWofbCompletedby = beanWofbCompletedby;
    }
    public String getFormActionMode() {
        return formActionMode;
    }
    public void setFormActionMode(String formActionMode) {
        this.formActionMode = formActionMode;
    }
    public String getFormMode() {
        return formMode;
    }
    public void setFormMode(String formMode) {
        this.formMode = formMode;
    }
    public String getActionmode() {
        return actionmode;
    }
    public void setActionmode(String actionmode) {
        this.actionmode = actionmode;
    }
    public String getDisableForm() {
        return disableForm;
    }
    public void setDisableForm(String disableForm) {
        this.disableForm = disableForm;
    }
    public String getUpcWorkOrderNo() {
        return upcWorkOrderNo;
    }
    public void setUpcWorkOrderNo(String upcWorkOrderNo) {
        this.upcWorkOrderNo = upcWorkOrderNo;
    }
    public String getUpcWorkdetailid() {
        return upcWorkdetailid;
    }
    public void setUpcWorkdetailid(String upcWorkdetailid) {
        this.upcWorkdetailid = upcWorkdetailid;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAllotedTo() {
        return allotedTo;
    }
    public void setAllotedTo(String allotedTo) {
        this.allotedTo = allotedTo;
    }
    public String getModeModify() {
        return modeModify;
    }
    public void setModeModify(String modeModify) {
        this.modeModify = modeModify;
    }
    public BalPlmTlSpareconsumed getSpareConsumed() {
        return spareConsumed;
    }
    public void setSpareConsumed(BalPlmTlSpareconsumed spareConsumed) {
        this.spareConsumed = spareConsumed;
    }
    public BalPlmTlSparecostactual getSpareCostActual() {
        return spareCostActual;
    }
    public void setSpareCostActual(BalPlmTlSparecostactual spareCostActual) {
        this.spareCostActual = spareCostActual;
    }
    public String getDbLocation() {
        return dbLocation;
    }
    public void setDbLocation(String dbLocation) {
        this.dbLocation = dbLocation;
    }
    public String getWofbMachinetkeovrtime() {
        return wofbMachinetkeovrtime;
    }
    public void setWofbMachinetkeovrtime(String wofbMachinetkeovrtime) {
        this.wofbMachinetkeovrtime = wofbMachinetkeovrtime;
    }
    public String getCurrentReading() {
        return currentReading;
    }
    public void setCurrentReading(String currentReading) {
        this.currentReading = currentReading;
    }
    public String getAdjustReading() {
        return adjustReading;
    }
    public void setAdjustReading(String adjustReading) {
        this.adjustReading = adjustReading;
    }
    public String getNextdueDate() {
        return nextdueDate;
    }
    public void setNextdueDate(String nextdueDate) {
        this.nextdueDate = nextdueDate;
    }
    public String getMinmumReading() {
        return minmumReading;
    }
    public void setMinmumReading(String minmumReading) {
        this.minmumReading = minmumReading;
    }
    public String getMaximumReading() {
        return maximumReading;
    }
    public void setMaximumReading(String maximumReading) {
        this.maximumReading = maximumReading;
    }
    public String getGenwCellid() {
        return genwCellid;
    }
    public void setGenwCellid(String genwCellid) {
        this.genwCellid = genwCellid;
    }
    public String getGenwFactoryid() {
        return genwFactoryid;
    }
    public void setGenwFactoryid(String genwFactoryid) {
        this.genwFactoryid = genwFactoryid;
    }
    public String getGenwSectionid() {
        return genwSectionid;
    }
    public void setGenwSectionid(String genwSectionid) {
        this.genwSectionid = genwSectionid;
    }
    public String getGenwMachineid() {
        return genwMachineid;
    }
    public void setGenwMachineid(String genwMachineid) {
        this.genwMachineid = genwMachineid;
    }
    public String getGenwFlid() {
        return genwFlid;
    }
    public void setGenwFlid(String genwFlid) {
        this.genwFlid = genwFlid;
    }
    public String getGenwElementid() {
        return genwElementid;
    }
    public void setGenwElementid(String genwElementid) {
        this.genwElementid = genwElementid;
    }
    public String getWogenTradeid() {
        return wogenTradeid;
    }
    public void setWogenTradeid(String wogenTradeid) {
        this.wogenTradeid = wogenTradeid;
    }
    public String getGenwLocationid() {
        return genwLocationid;
    }
    public void setGenwLocationid(String genwLocationid) {
        this.genwLocationid = genwLocationid;
    }
    public String getWogenAssemblyid() {
        return wogenAssemblyid;
    }
    public void setWogenAssemblyid(String wogenAssemblyid) {
        this.wogenAssemblyid = wogenAssemblyid;
    }



    /** END CBM DATA **/

    

}
