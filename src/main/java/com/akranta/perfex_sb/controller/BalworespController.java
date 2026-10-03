
package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.BalworespService;
import com.akranta.perfex_sb.dto.WorespRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/woresp")
public class BalworespController {

    private static final Logger logger = LoggerFactory.getLogger(BalworespController.class);
    private final BalworespService service;

    public BalworespController(BalworespService service) {
        this.service = service;
    }

    
    @PostMapping("/save")
    public ResponseEntity<WorespRequest> saveWoresp(@RequestBody WorespRequest request) throws Exception {
        logger.info("Saving/Updating Work Order Responsibility with all related data");
        return service.saveWoresp(request);
    }

   
    @GetMapping("/complete/{masterKeyid}")
    public ResponseEntity<WorespRequest> getCompleteWorespData(@PathVariable String masterKeyid) {
        logger.info("Fetching complete Work Order Responsibility data for keyid: {}", masterKeyid);
        WorespRequest completeData = service.getCompleteWorespData(masterKeyid);
        return ResponseEntity.ok(completeData);
    }

   
    @DeleteMapping("/delete-detail/{detailId}")
    public ResponseEntity<Map<String, Object>> deleteWorespDetail(@PathVariable String detailId) {
        try {
            logger.info("Deleting Work Order Responsibility detail with keyid: {}", detailId);
            boolean deleted = service.deleteWorespDetail(detailId);

            Map<String, Object> response = Map.of(
                    "success", deleted,
                    "message", "Work Order Responsibility detail deleted successfully",
                    "deletedId", detailId
            );

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Work Order Responsibility detail not found: {}", e.getMessage());
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
            logger.error("Error deleting Work Order Responsibility detail: ", e);
            Map<String, Object> response = Map.of(
                    "success", false,
                    "message", "Error deleting Work Order Responsibility detail: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}