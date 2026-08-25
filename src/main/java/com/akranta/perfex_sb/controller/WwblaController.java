package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.WwblaService;
import com.akranta.perfex_sb.dto.WwblaRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/wwbla")
public class WwblaController {

    private static final Logger logger = LoggerFactory.getLogger(WwblaController.class);
    private final WwblaService service;

    public WwblaController(WwblaService service) {
        this.service = service;
    }

    /**
     * Save or Update Wwbla Master Entry (handles both insert and update)
     * If master.keyid is null/empty -> INSERT
     * If master.keyid exists -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<WwblaRequest> saveWwbla(@RequestBody WwblaRequest request) {
        try {
            logger.info("Saving/Updating Wwbla Master Entry");
            return service.saveWwbla(request);
        } catch (ResourceNotFoundException e) {
            logger.error("Wwbla Master Entry not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error saving Wwbla Master Entry: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Save Wwbla Detail Entry (INSERT only)
     * detail.keyid must be null/empty
     */
    @PostMapping("/save/detail")
    public ResponseEntity<WwblaRequest> saveWwblaDetail(@RequestBody WwblaRequest request) {
        try {
            logger.info("Creating Wwbla Detail Entry");
            return service.saveWwblaDetail(request);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error saving Wwbla Detail Entry: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    /**
 * Recall Wwbla detail by keyid
 */
@GetMapping("/recall-detail/{keyid}")
public ResponseEntity<List<Map<String, Object>>> recallWwblaDetail(@PathVariable String keyid) {
    try {
        logger.info("Recalling Wwbla detail for keyid: {}", keyid);
        
        if (keyid == null || keyid.trim().isEmpty()) {
            logger.error("Invalid keyid provided");
            return ResponseEntity.badRequest().build();
        }
        
        List<Map<String, Object>> detail = service.recallWwblaDetail(keyid);
        
        logger.info("Successfully recalled detail for keyid: {}", keyid);
        return ResponseEntity.ok(detail);
        
    } catch (ResourceNotFoundException e) {
        logger.error("Detail not found: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
        
    } catch (Exception e) {
        logger.error("Error recalling detail: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

/**
 * Get Wwbla values with filtering
 */
@GetMapping("/values")
public ResponseEntity<List<Map<String, Object>>> getWwblaValues(
        @RequestParam(required = false) String masterKeyid,
        @RequestParam(required = false) String parentId,
        @RequestParam(required = false) String detailKeyid) {
    try {
        logger.info("Getting Wwbla values with filters - masterKeyid: {}, parentId: {}, detailKeyid: {}", 
                    masterKeyid, parentId, detailKeyid);
        
        List<Map<String, Object>> values = service.getWwblaValues(masterKeyid, parentId, detailKeyid);
        
        logger.info("Successfully retrieved {} Wwbla values", values.size());
        return ResponseEntity.ok(values);
        
    } catch (Exception e) {
        logger.error("Error getting Wwbla values: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@DeleteMapping("/delete-detail/{keyid}")
    public ResponseEntity<Map<String, Object>> deleteWwblaChildEntry(@PathVariable String keyid) {
        try {
            logger.info("Deleting WWBLA detail with keyid: {}", keyid);
            boolean deleted = service.deleteWwblaChildEntry(keyid);
            
            Map<String, Object> response = Map.of(
                "success", deleted,
                "message", "WWBLA detail and its children deleted successfully",
                "deletedId", keyid
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            logger.error("WWBLA detail not found: {}", e.getMessage());
            Map<String, Object> response = Map.of(
                "success", false,
                "message", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid keyid provided: {}", keyid);
            Map<String, Object> response = Map.of(
                "success", false,
                "message", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            logger.error("Error deleting WWBLA detail: ", e);
            Map<String, Object> response = Map.of(
                "success", false,
                "message", "Error deleting WWBLA detail: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    

}