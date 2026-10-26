package com.akranta.perfex_sb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.PlmTlGenmaintenance;
import com.akranta.perfex_sb.service.PlmTlGenmaintenanceService;

@RestController
@RequestMapping("/api/genmaintenance")
public class PlmTlGenmaintenanceController {

    private static final Logger logger = LoggerFactory.getLogger(PlmTlGenmaintenanceController.class);

    private final PlmTlGenmaintenanceService plmTlGenmaintenanceService;

    public PlmTlGenmaintenanceController(PlmTlGenmaintenanceService plmTlGenmaintenanceService) {
        this.plmTlGenmaintenanceService = plmTlGenmaintenanceService;
    }

    /**
     * SAVE - Handles BOTH insert and update (like PlannedJobObservationController /save)
     * If plmTlGenmaintenance.keyid is null/empty -> INSERT
     * If plmTlGenmaintenance.keyid exists -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<PlmTlGenmaintenance> saveGeneralmainteneance(@RequestBody PlmTlGenmaintenance plmTlGenmaintenance) {
        try {
            logger.info("Saving/Updating General Maintenance record");
            PlmTlGenmaintenance saved = plmTlGenmaintenanceService.saveGeneralmainteneance(plmTlGenmaintenance);
            HttpStatus status = (plmTlGenmaintenance.getKeyid() == null
                    || plmTlGenmaintenance.getKeyid().trim().isEmpty())
                    ? HttpStatus.CREATED
                    : HttpStatus.OK;
            return ResponseEntity.status(status).body(saved);
        } catch (ResourceNotFoundException e) {
            logger.error("Record not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error saving General Maintenance: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * FIND BY KEYID
     */
    
    @GetMapping("/{keyid}")
    public ResponseEntity<PlmTlGenmaintenance> getById(@PathVariable String keyid) {
        try {
            logger.info("Fetching General Maintenance with keyid: {}", keyid);
            PlmTlGenmaintenance result = plmTlGenmaintenanceService.getById(keyid);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (ResourceNotFoundException e) {
            logger.error("Record not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error fetching General Maintenance: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET ALL
     */
    @GetMapping
    public ResponseEntity<List<PlmTlGenmaintenance>> getAll() {
        try {
            logger.info("Fetching all General Maintenance records");
            List<PlmTlGenmaintenance> result = plmTlGenmaintenanceService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            logger.error("Error fetching General Maintenance list: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE by keyid
     */
    @DeleteMapping("/delete/{keyid}")
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable String keyid) {
        try {
            logger.info("Deleting General Maintenance for keyid: {}", keyid);
            boolean deleted = plmTlGenmaintenanceService.deleteById(keyid);

            Map<String, Object> response = new HashMap<>();
            response.put("success", deleted);
            response.put("message", "General Maintenance deleted successfully");
            response.put("deletedId", keyid);

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            logger.error("General Maintenance not found: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid keyid provided: {}", keyid);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            logger.error("Error deleting General Maintenance: ", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error deleting General Maintenance: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}