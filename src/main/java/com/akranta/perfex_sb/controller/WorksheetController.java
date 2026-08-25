package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.WorksheetService;
import com.akranta.perfex_sb.dto.WorksheetRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worksheet")
public class WorksheetController {

    private static final Logger logger = LoggerFactory.getLogger(WorksheetController.class);
    private final WorksheetService service;

    public WorksheetController(WorksheetService service) {
        this.service = service;
    }

    /**
     * Save or Update Worksheet (handles both insert and update)
     * If master.keyid is null/empty -> INSERT
     * If master.keyid exists -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<WorksheetRequest> saveWorksheet(@RequestBody WorksheetRequest request) {
        try {
            logger.info("Saving/Updating Worksheet with all related data");
            return service.saveWorksheet(request);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error saving Worksheet: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get complete Worksheet data with master and details
     */
    @GetMapping("/complete/{masterKeyid}")
    public ResponseEntity<WorksheetRequest> getCompleteWorksheetData(@PathVariable String masterKeyid) {
        try {
            logger.info("Fetching complete Worksheet data for keyid: {}", masterKeyid);
            WorksheetRequest completeData = service.getCompleteWorksheetData(masterKeyid);
            return ResponseEntity.ok(completeData);
        } catch (ResourceNotFoundException e) {
            logger.error("Worksheet not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error fetching Worksheet data: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}