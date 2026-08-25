package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.ConditionalAppraisalService;
import com.akranta.perfex_sb.dto.ConditionalAppraisalRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
//import com.akranta.perfex_sb.model.PlmTlConditionalappraisalmstentry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/api/conditional-appraisal")
public class ConditionalAppraisalController {

    private static final Logger logger = LoggerFactory.getLogger(ConditionalAppraisalController.class);
    private final ConditionalAppraisalService service;

    public ConditionalAppraisalController(ConditionalAppraisalService service) {
        this.service = service;
    }

    /**
     * Save or Update Conditional Appraisal (handles both insert and update)
     * If master.keyid is null/empty -> INSERT
     * If master.keyid exists -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<ConditionalAppraisalRequest> saveConditionalAppraisal(@RequestBody ConditionalAppraisalRequest request) {
        try {
            logger.info("Saving/Updating Conditional Appraisal with all related data");
            return service.saveConditionalAppraisal(request);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error saving Conditional Appraisal: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get complete Conditional Appraisal data with master and details
     */
    @GetMapping("/complete/{masterKeyid}")
    public ResponseEntity<ConditionalAppraisalRequest> getCompleteConditionalAppraisalData(@PathVariable String masterKeyid) {
        try {
            logger.info("Fetching complete Conditional Appraisal data for keyid: {}", masterKeyid);
            
            // Validate path variable
            if (masterKeyid == null || masterKeyid.trim().isEmpty()) {
                logger.error("Invalid masterKeyid provided");
                return ResponseEntity.badRequest().build();
            }
            
            ConditionalAppraisalRequest completeData = service.getCompleteConditionalAppraisalData(masterKeyid);
            
            logger.info("Successfully retrieved data for keyid: {}", masterKeyid);
            return ResponseEntity.ok(completeData);
            
        } catch (ResourceNotFoundException e) {
            logger.error("Conditional Appraisal not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (Exception e) {
            logger.error("Error fetching Conditional Appraisal data: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
 @GetMapping("/recall-detail/{keyid}")
public ResponseEntity<List<Map<String, Object>>> recallConditionalAppraisalDetail(@PathVariable String keyid) {
    try {
        logger.info("Recalling Conditional Appraisal detail for keyid: {}", keyid);
        
        if (keyid == null || keyid.trim().isEmpty()) {
            logger.error("Invalid keyid provided");
            return ResponseEntity.badRequest().build();
        }
        
        List<Map<String, Object>> detail = service.recallConditionalAppraisalDetail(keyid);
        
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


@PostMapping("/save-entry")
public ResponseEntity<ConditionalAppraisalRequest> saveConditionalAppraisalEntry(
        @RequestBody ConditionalAppraisalRequest request) throws Exception {
    logger.info("Saving/Updating Conditional Appraisal Entry with all related data");
    return service.saveConditionalAppraisalEntry(request);
}

/**
 * Check if update exists
 */
@PostMapping("/check-update")
public ResponseEntity<?> checkUpdate(@RequestBody Map<String, String> requestData) {
    try {
        logger.info("Checking update for conditional appraisal entry");
        
        String flid = requestData.get("flid");
        String date = requestData.get("date");
        String forGrid = requestData.get("forGrid");
        String cdapkeyid = requestData.get("cdapkeyid");
        
        Long count = service.checkUpdate(flid, date, forGrid, cdapkeyid);
        
        Map<String, Object> response = Map.of(
            "count", count,
            "exists", count > 0
        );
        
        return ResponseEntity.ok(count);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (Exception e) {
        logger.error("Error checking update: ", e);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "Error checking update: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

@DeleteMapping("/delete-detail/{keyid}")
public ResponseEntity<Map<String, Object>> deleteConditionalAppraisalDetail(@PathVariable String keyid) {
    try {
        logger.info("Deleting Conditional Appraisal detail with keyid: {}", keyid);
        boolean deleted = service.deleteConditionalAppraisalDetail(keyid);
        
        Map<String, Object> response = Map.of(
            "success", deleted,
            "message", "Conditional Appraisal detail deleted successfully",
            "deletedId", keyid
        );
        
        return ResponseEntity.ok(response);
    } catch (ResourceNotFoundException e) {
        logger.error("Conditional Appraisal detail not found: {}", e.getMessage());
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
        logger.error("Error deleting Conditional Appraisal detail: ", e);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "Error deleting Conditional Appraisal detail: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

/**
 * Delete Conditional Appraisal Master and all its details
 */
@DeleteMapping("/delete-master/{keyid}")
public ResponseEntity<Map<String, Object>> deleteConditionalAppraisalMaster(@PathVariable String keyid) {
    try {
        logger.info("Deleting Conditional Appraisal master with keyid: {}", keyid);
        boolean deleted = service.deleteConditionalAppraisalMaster(keyid);
        
        Map<String, Object> response = Map.of(
            "success", deleted,
            "message", "Conditional Appraisal master and all details deleted successfully",
            "deletedId", keyid
        );
        
        return ResponseEntity.ok(response);
    } catch (ResourceNotFoundException e) {
        logger.error("Conditional Appraisal master not found: {}", e.getMessage());
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
        logger.error("Error deleting Conditional Appraisal master: ", e);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "Error deleting Conditional Appraisal master: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
    
}