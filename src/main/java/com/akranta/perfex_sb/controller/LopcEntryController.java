package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.LopcEntryService;
import com.akranta.perfex_sb.dto.LopcEntryRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lopc-entry")
public class LopcEntryController {

    private static final Logger logger = LoggerFactory.getLogger(LopcEntryController.class);
    private final LopcEntryService service;

    public LopcEntryController(LopcEntryService service) {
        this.service = service;
    }

    /**
     * Save or Update LOPC Entry (handles both insert and update)
     * If master.keyid is null/empty -> INSERT
     * If master.keyid exists -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<LopcEntryRequest> saveLopcEntry(@RequestBody LopcEntryRequest request) {
        try {
            logger.info("Saving/Updating LOPC Entry");
            return service.saveLopcEntry(request);
        } catch (ResourceNotFoundException e) {
            logger.error("LOPC Entry not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error saving LOPC Entry: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
 @GetMapping("/get/{keyid}")
    public ResponseEntity<LopcEntryRequest> getLopcEntry(@PathVariable String keyid) {
        try {
            logger.info("Fetching LOPC Entry with keyid: {}", keyid);
            LopcEntryRequest result = service.getLopcEntryData(keyid);
            return ResponseEntity.ok(result);
        } catch (ResourceNotFoundException e) {
            logger.error("LOPC Entry not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error fetching LOPC Entry: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
@PutMapping("/update-closure/{keyid}")
    public ResponseEntity<Map<String, String>> updateLopcActionClosure(
            @PathVariable String keyid,
            @RequestBody Map<String, String> closureData) {
        try {
            logger.info("Updating LOPC Action Closure for keyid: {}", keyid);
            
            String completedBy = closureData.get("completedBy");
            String status = closureData.get("status");
            String completedDate = closureData.get("completedDate");
            String correctiveAction = closureData.get("correctiveAction");
            String remarks = closureData.get("remarks");
            
            // Validate required fields
            if (status == null || status.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Status is required"));
            }
            
            service.updateLopcActionClosure(
                    keyid, 
                    completedBy, 
                    status, 
                    completedDate, 
                    correctiveAction, 
                    remarks
            );
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "LOPC Action Closure updated successfully");
            response.put("keyid", keyid);
            response.put("status", status);
            
            return ResponseEntity.ok(response);
            
        } catch (ResourceNotFoundException e) {
            logger.error("LOPC Entry not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating LOPC Action Closure: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error updating LOPC Action Closure: " + e.getMessage()));
        }
    }

    /**
     * Get all detail records for a master key
     * GET /api/lopc-entry/detail-records/{wwblKeyid}
     */
    @GetMapping("/detail-records/{wwblKeyid}")
    public ResponseEntity<List<Map<String, Object>>> getDetailRecords(@PathVariable String wwblKeyid) {
        try {
            logger.info("Fetching detail records for wwblKeyid: {}", wwblKeyid);
            List<Map<String, Object>> detailRecords = service.getDetailRecords(wwblKeyid);
            return ResponseEntity.ok(detailRecords);
        } catch (Exception e) {
            logger.error("Error fetching detail records: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}