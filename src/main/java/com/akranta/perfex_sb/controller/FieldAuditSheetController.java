package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.FieldAuditSheetService;
import com.akranta.perfex_sb.dto.FieldAuditSheetRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/field-audit-sheet")
public class FieldAuditSheetController {

    private static final Logger logger = LoggerFactory.getLogger(FieldAuditSheetController.class);
    private final FieldAuditSheetService service;

    public FieldAuditSheetController(FieldAuditSheetService service) {
        this.service = service;
    }

    /**
     * Save or Update Field Audit Sheet (handles both insert and update)
     * If master.keyid is null/empty -> INSERT
     * If master.keyid exists -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<FieldAuditSheetRequest> saveFieldAuditSheet(@RequestBody FieldAuditSheetRequest request) throws Exception {
        logger.info("Saving/Updating Field Audit Sheet with all related data");
        return service.saveFieldAuditSheet(request);
    }

    /**
     * Get complete Field Audit Sheet data with all details
     */
    @GetMapping("/complete/{masterKeyid}")
    public ResponseEntity<FieldAuditSheetRequest> getCompleteFieldAuditSheetData(@PathVariable String masterKeyid) {
        logger.info("Fetching complete Field Audit Sheet data for keyid: {}", masterKeyid);
        FieldAuditSheetRequest completeData = service.getCompleteFieldAuditSheetData(masterKeyid);
        return ResponseEntity.ok(completeData);
    }

    /**
     * Delete Field Audit Sheet detail
     */
    @DeleteMapping("/delete-detail/{detailId}")
    public ResponseEntity<Map<String, Object>> deleteFieldAuditSheetDetail(@PathVariable String detailId) {
        try {
            logger.info("Deleting Field Audit Sheet detail with keyid: {}", detailId);
            boolean deleted = service.deleteFieldAuditSheetDetail(detailId);
            
            Map<String, Object> response = Map.of(
                "success", deleted,
                "message", "Field Audit Sheet detail deleted successfully",
                "deletedId", detailId
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Field Audit Sheet detail not found: {}", e.getMessage());
            Map<String, Object> response = Map.of(
                "success", false,
                "message", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid detailId provided: {}", detailId);
            Map<String, Object> response = Map.of(
                "success", false,
                "message", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            logger.error("Error deleting Field Audit Sheet detail: ", e);
            Map<String, Object> response = Map.of(
                "success", false,
                "message", "Error deleting Field Audit Sheet detail: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}