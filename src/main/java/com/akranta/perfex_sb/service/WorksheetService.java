package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.WorksheetRequest;

public interface WorksheetService {
    
    /**
     * Save or Update Worksheet (handles both insert and update in single method)
     * If keyid is null/empty -> INSERT
     * If keyid exists -> UPDATE
     */
    ResponseEntity<WorksheetRequest> saveWorksheet(WorksheetRequest request) throws Exception;
    
    /**
     * Get complete Worksheet data including master and details
     */
    WorksheetRequest getCompleteWorksheetData(String masterKeyid);
}