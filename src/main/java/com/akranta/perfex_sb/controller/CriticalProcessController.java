package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.CriticalProcessService;
import com.akranta.perfex_sb.dto.CriticalProcessRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/api/critical-process")
public class CriticalProcessController {

    
    private static final Logger logger = LoggerFactory.getLogger(CriticalProcessController.class);
    private final CriticalProcessService service;
    
    

    public CriticalProcessController(CriticalProcessService service) {
        this.service = service;
    }

    /**
     * Save or Update Critical Process (handles both insert and update)
     * If master.keyid is null/empty -> INSERT
     * If master.keyid exists -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<CriticalProcessRequest> saveCriticalProcess(@RequestBody CriticalProcessRequest request) {
        try {
            logger.info("Saving/Updating Critical Process with all related data");
            return service.saveCriticalProcess(request);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error saving Critical Process: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get complete Critical Process data with master and details
     */
    @GetMapping("/complete/{masterKeyid}")
public ResponseEntity<CriticalProcessRequest> getCompleteCriticalProcessData(@PathVariable String masterKeyid) {
    try {
        logger.info("Fetching complete Critical Process data for keyid: {}", masterKeyid);
        
        // Validate path variable
        if (masterKeyid == null || masterKeyid.trim().isEmpty()) {
            logger.error("Invalid masterKeyid provided");
            return ResponseEntity.badRequest().build();
        }
        
        CriticalProcessRequest completeData = service.getCompleteCriticalProcessData(masterKeyid);
        
        logger.info("Successfully retrieved data for keyid: {}", masterKeyid);
        return ResponseEntity.ok(completeData);
        
    } catch (ResourceNotFoundException e) {
        logger.error("Critical Process not found: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
        
    } catch (Exception e) {
        logger.error("Error fetching Critical Process data: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}


@DeleteMapping("/delete-detail/{detailId}")
    public ResponseEntity<Map<String, Object>> deleteCriticalProcessDetail(@PathVariable String detailId) {
        try {
            logger.info("Deleting Critical Process detail with keyid: {}", detailId);
            boolean deleted = service.deleteCriticalProcessDetail(detailId);
            
            Map<String, Object> response = Map.of(
                "success", deleted,
                "message", "Critical Process detail deleted successfully",
                "deletedId", detailId
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Critical Process detail not found: {}", e.getMessage());
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
            logger.error("Error deleting Critical Process detail: ", e);
            Map<String, Object> response = Map.of(
                "success", false,
                "message", "Error deleting Critical Process detail: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete/{masterId}")
public ResponseEntity<Map<String, Object>> deleteCriticalProcess(@PathVariable String masterId) {
    try {
        logger.info("Deleting Critical Process record with masterId: {}", masterId);
        boolean deleted = service.delete(masterId);  // Changed from CriticalProcessService.delete to service.delete
        
        Map<String, Object> response = Map.of(
            "success", deleted,
            "message", "Critical Process record deleted successfully",
            "deletedId", masterId
        );
        
        return ResponseEntity.ok(response);
        
    } catch (ResourceNotFoundException e) {
        logger.error("Critical Process record not found: {}", e.getMessage());
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        
    } catch (IllegalArgumentException e) {
        logger.error("Invalid masterId provided: {}", masterId);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        
    } catch (Exception e) {
        logger.error("Error deleting Critical Process record: ", e);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "Error deleting Critical Process record: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
}