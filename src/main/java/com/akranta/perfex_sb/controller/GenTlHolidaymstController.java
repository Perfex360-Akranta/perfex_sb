package com.akranta.perfex_sb.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.GenTlHolidaymst;
import com.akranta.perfex_sb.service.GenTlHolidaymstService;

@RestController
@RequestMapping("/api/holiday")
public class GenTlHolidaymstController {

    private static final Logger logger = LoggerFactory.getLogger(GenTlHolidaymstController.class);
    private final GenTlHolidaymstService service;

    public GenTlHolidaymstController(GenTlHolidaymstService service) {
        this.service = service;
    }

    /**
     * Save or Update Holiday (handles both insert and update)
     * If keyid is null/empty -> INSERT
     * If keyid exists -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveHoliday(@RequestBody GenTlHolidaymst request) {
        logger.info("Saving/Updating Holiday");
        try {
            return service.saveHoliday(request);
        } catch (ResourceNotFoundException e) {
            logger.error("Holiday not found on update: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("success", false, "message", e.getMessage()));
        } catch (RuntimeException e) {
            // validation failures and wrapped DB errors from the service land here
            logger.error("Error saving Holiday: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error saving Holiday: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", false, "message", "Unexpected error: " + e.getMessage()));
        }
    }

    /**
     * Get Holiday by keyid
     */
    @GetMapping("/{keyid}")
    public ResponseEntity<?> getHoliday(@PathVariable String keyid) {
        logger.info("Fetching Holiday for keyid: {}", keyid);
        try {
            GenTlHolidaymst holiday = service.getHoliday(keyid);
            return ResponseEntity.ok(holiday);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    /**
     * Get all Holidays, optionally filtered by factory/unit, flag (H/W) and year
     */
    @GetMapping
    public ResponseEntity<List<GenTlHolidaymst>> getAllHolidays(
            @RequestParam(required = false) String factory,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) Integer year) {
        logger.info("Fetching Holidays - factory: {}, flag: {}, year: {}", factory, flag, year);
        List<GenTlHolidaymst> holidays = service.getAllHolidays(factory, flag, year);
        return ResponseEntity.ok(holidays);
    }

    /**
     * Delete Holiday
     */
    @DeleteMapping("/{keyid}")
    public ResponseEntity<Map<String, Object>> deleteHoliday(@PathVariable String keyid) {
        try {
            logger.info("Deleting Holiday with keyid: {}", keyid);
            boolean deleted = service.deleteHoliday(keyid);
            return ResponseEntity.ok(Map.of(
                "success", deleted,
                "message", "Holiday deleted successfully",
                "deletedId", keyid
            ));
        } catch (ResourceNotFoundException e) {
            logger.error("Holiday not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error deleting Holiday: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", false, "message", "Error deleting Holiday: " + e.getMessage()));
        }
    }
}