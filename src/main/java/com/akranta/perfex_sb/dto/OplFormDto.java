package com.akranta.perfex_sb.dto;

public class OplFormDto {

    // UI state flags (from old JSP/Struts style bean)
    private boolean disableChkApprovedBy;
    private boolean disableCmbApprovedBy;
    private boolean disableDteApprovedDate;
    private boolean disableChkpreparedBy;
    private boolean disableCmbDocumentNo;

    // Data fields
    private String responsibility;
    private String resultAreaP;
    private String lesson;

    private String classificationB;
    private String classificationI;
    private String classificationT;

    private String formActionMode;
    private String formMode;
    private String formHeader;

    private boolean oplExcelView;
    private boolean viewMode;

    private String chkUnderstoodOpl;
    private String disableForm;

    private String yyId;
    private String docId;
    private String oplmApprovedid;
    private String oplmwhyhow;

    public OplFormDto() {
    }

    // --- Getters & Setters ---

    public boolean isDisableChkApprovedBy() {
        return disableChkApprovedBy;
    }

    public void setDisableChkApprovedBy(boolean disableChkApprovedBy) {
        this.disableChkApprovedBy = disableChkApprovedBy;
    }

    public boolean isDisableCmbApprovedBy() {
        return disableCmbApprovedBy;
    }

    public void setDisableCmbApprovedBy(boolean disableCmbApprovedBy) {
        this.disableCmbApprovedBy = disableCmbApprovedBy;
    }

    public boolean isDisableDteApprovedDate() {
        return disableDteApprovedDate;
    }

    public void setDisableDteApprovedDate(boolean disableDteApprovedDate) {
        this.disableDteApprovedDate = disableDteApprovedDate;
    }

    public boolean isDisableChkpreparedBy() {
        return disableChkpreparedBy;
    }

    public void setDisableChkpreparedBy(boolean disableChkpreparedBy) {
        this.disableChkpreparedBy = disableChkpreparedBy;
    }

    public boolean isDisableCmbDocumentNo() {
        return disableCmbDocumentNo;
    }

    public void setDisableCmbDocumentNo(boolean disableCmbDocumentNo) {
        this.disableCmbDocumentNo = disableCmbDocumentNo;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getResultAreaP() {
        return resultAreaP;
    }

    public void setResultAreaP(String resultAreaP) {
        this.resultAreaP = resultAreaP;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getClassificationB() {
        return classificationB;
    }

    public void setClassificationB(String classificationB) {
        this.classificationB = classificationB;
    }

    public String getClassificationI() {
        return classificationI;
    }

    public void setClassificationI(String classificationI) {
        this.classificationI = classificationI;
    }

    public String getClassificationT() {
        return classificationT;
    }

    public void setClassificationT(String classificationT) {
        this.classificationT = classificationT;
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

    public String getFormHeader() {
        return formHeader;
    }

    public void setFormHeader(String formHeader) {
        this.formHeader = formHeader;
    }

    public boolean isOplExcelView() {
        return oplExcelView;
    }

    public void setOplExcelView(boolean oplExcelView) {
        this.oplExcelView = oplExcelView;
    }

    public boolean isViewMode() {
        return viewMode;
    }

    public void setViewMode(boolean viewMode) {
        this.viewMode = viewMode;
    }

    public String getChkUnderstoodOpl() {
        return chkUnderstoodOpl;
    }

    public void setChkUnderstoodOpl(String chkUnderstoodOpl) {
        this.chkUnderstoodOpl = chkUnderstoodOpl;
    }

    public String getDisableForm() {
        return disableForm;
    }

    public void setDisableForm(String disableForm) {
        this.disableForm = disableForm;
    }

    public String getYyId() {
        return yyId;
    }

    public void setYyId(String yyId) {
        this.yyId = yyId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getOplmApprovedid() {
        return oplmApprovedid;
    }

    public void setOplmApprovedid(String oplmApprovedid) {
        this.oplmApprovedid = oplmApprovedid;
    }

    public String getOplmwhyhow() {
        return oplmwhyhow;
    }

    public void setOplmwhyhow(String oplmwhyhow) {
        this.oplmwhyhow = oplmwhyhow;
    }
}
