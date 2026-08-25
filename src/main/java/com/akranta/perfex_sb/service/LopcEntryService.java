package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.LopcEntryRequest;
import com.akranta.perfex_sb.model.LopcEntryMst;
import java.util.List;
import java.util.Map;


public interface LopcEntryService {
    
    /**
     * Save or Update LOPC Entry (handles both insert and update in single method)
     * If keyid is null/empty -> INSERT
     * If keyid exists -> UPDATE
     */
    ResponseEntity<LopcEntryRequest> saveLopcEntry(LopcEntryRequest request) throws Exception;

    LopcEntryRequest getLopcEntryData(String keyid);
    void updateLopcActionClosure(
            String keyid, 
            String completedBy, 
            String status, 
            String completedDate, 
            String correctiveAction, 
            String remarks) throws Exception;

    /**
     * Get all detail records for a master key
     */
    List<Map<String, Object>> getDetailRecords(String wwblKeyid);
}