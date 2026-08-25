package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.ComplaintGalleryRequest;

public interface ComplaintGalleryService {
    
    /**
     * Save or Update Complaint Gallery (handles both insert and update in single method)
     * If keyid is null/empty -> INSERT
     * If keyid exists -> UPDATE
     */
    ResponseEntity<ComplaintGalleryRequest> saveComplaintGallery(ComplaintGalleryRequest request) throws Exception;
    
    /**
     * Get Complaint Gallery data by keyid
     */
    ComplaintGalleryRequest getComplaintGalleryData(String keyid);

     boolean deleteComplaintGallery(String keyid) throws Exception;
}