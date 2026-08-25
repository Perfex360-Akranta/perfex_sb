package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.FieldAuditSheetRequest;

public interface FieldAuditSheetService {
    
    /**
     * Save or Update Field Audit Sheet (handles both insert and update in single method)
     * If keyid is null/empty -> INSERT
     * If keyid exists -> UPDATE
     */
    ResponseEntity<FieldAuditSheetRequest> saveFieldAuditSheet(FieldAuditSheetRequest request) throws Exception;
    
    /**
     * Get complete Field Audit Sheet data including all details
     */
    FieldAuditSheetRequest getCompleteFieldAuditSheetData(String masterKeyid);
    
    /**
     * Delete Field Audit Sheet detail
     */
    boolean deleteFieldAuditSheetDetail(String detailId) throws Exception;
}