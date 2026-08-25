package com.akranta.perfex_sb.dto;

import org.springframework.web.multipart.MultipartFile;

public class DocumentUploadDto {
       private MultipartFile file;
    private String refDocNo;
    private String refDocType;
    private String description;
    private String keywords;
    private String category;
    private String owner;
    private String approvedBy;
    private String subjectArea;
    private String title;
    private String createdBy;
    
    
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    public String getRefDocNo() {
        return refDocNo;
    }
    public void setRefDocNo(String refDocNo) {
        this.refDocNo = refDocNo;
    }
    public String getRefDocType() {
        return refDocType;
    }
    public void setRefDocType(String refDocType) {
        this.refDocType = refDocType;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getKeywords() {
        return keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getApprovedBy() {
        return approvedBy;
    }
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
    public String getSubjectArea() {
        return subjectArea;
    }
    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    


}
