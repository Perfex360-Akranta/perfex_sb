package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.CriticalProcessRequest;

public interface CriticalProcessService {
    
    /**
     * Save or Update Critical Process (handles both insert and update in single method)
     * If keyid is null/empty -> INSERT
     * If keyid exists -> UPDATE
     */
    ResponseEntity<CriticalProcessRequest> saveCriticalProcess(CriticalProcessRequest request) throws Exception;
    
    /**
     * Get complete Critical Process data including master and details
     */
    CriticalProcessRequest getCompleteCriticalProcessData(String masterKeyid);
    boolean deleteCriticalProcessDetail(String detailId) throws Exception;

    boolean delete(String masterId) throws Exception;
}