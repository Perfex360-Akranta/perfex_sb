package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.ComplaintGalleryService;
import com.akranta.perfex_sb.dto.ComplaintGalleryRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/complaint-gallery")
public class ComplaintGalleryController {

    private static final Logger logger = LoggerFactory.getLogger(ComplaintGalleryController.class);
    private final ComplaintGalleryService service;

    public ComplaintGalleryController(ComplaintGalleryService service) {
        this.service = service;
    }

    /**
     * Save or Update Complaint Gallery (handles both insert and update)
     * If complaintGallery.keyid is null/empty -> INSERT
     * If complaintGallery.keyid exists -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<ComplaintGalleryRequest> saveComplaintGallery(@RequestBody ComplaintGalleryRequest request) {
        try {
            logger.info("Saving/Updating Complaint Gallery");
            return service.saveComplaintGallery(request);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error saving Complaint Gallery: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get Complaint Gallery data by keyid
     */
    @GetMapping("/{keyid}")
    public ResponseEntity<ComplaintGalleryRequest> getComplaintGalleryData(@PathVariable String keyid) {
        try {
            logger.info("Fetching Complaint Gallery data for keyid: {}", keyid);
            
            // Validate path variable
            if (keyid == null || keyid.trim().isEmpty()) {
                logger.error("Invalid keyid provided");
                return ResponseEntity.badRequest().build();
            }
            
            ComplaintGalleryRequest completeData = service.getComplaintGalleryData(keyid);
            
            logger.info("Successfully retrieved data for keyid: {}", keyid);
            return ResponseEntity.ok(completeData);
            
        } catch (ResourceNotFoundException e) {
            logger.error("Complaint Gallery not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (Exception e) {
            logger.error("Error fetching Complaint Gallery data: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    /**
 * Delete Complaint Gallery by keyid
 */
@DeleteMapping("/delete/{keyid}")
public ResponseEntity<Map<String, Object>> deleteComplaintGallery(@PathVariable String keyid) {
    try {
        logger.info("Deleting Complaint Gallery record with keyid: {}", keyid);
        boolean deleted = service.deleteComplaintGallery(keyid);
        
        Map<String, Object> response = Map.of(
            "success", deleted,
            "message", "Complaint Gallery record deleted successfully",
            "deletedId", keyid
        );
        
        return ResponseEntity.ok(response);
    } catch (ResourceNotFoundException e) {
        logger.error("Complaint Gallery record not found: {}", e.getMessage());
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
        logger.error("Error deleting Complaint Gallery record: ", e);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "Error deleting Complaint Gallery record: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
}