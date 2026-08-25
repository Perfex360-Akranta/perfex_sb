package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.QtmTlComplaintgallery;
//import java.util.List;

public class ComplaintGalleryRequest {
    
    private QtmTlComplaintgallery complaintGallery;
    
    // Constructors
    public ComplaintGalleryRequest() {
    }
    
    public ComplaintGalleryRequest(QtmTlComplaintgallery complaintGallery) {
        this.complaintGallery = complaintGallery;
    }
    
    // Getters and Setters
    public QtmTlComplaintgallery getComplaintGallery() {
        return complaintGallery;
    }
    
    public void setComplaintGallery(QtmTlComplaintgallery complaintGallery) {
        this.complaintGallery = complaintGallery;
    }
}