package com.akranta.perfex_sb.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.ClitCalendarEnsureDto;
import com.akranta.perfex_sb.dto.ClitCalendarSaveDto;
import com.akranta.perfex_sb.service.CLTImobileSheduleService;

@RestController
@RequestMapping("/api/clit/mobile/schedule")
public class CLTImobileSheduleController {

    private static final Logger logger = LoggerFactory.getLogger(CLTImobileSheduleController.class);

    private final CLTImobileSheduleService cltiMobileSheduleService;

    public CLTImobileSheduleController(CLTImobileSheduleService cltiMobileSheduleService) {
        this.cltiMobileSheduleService = cltiMobileSheduleService;
    }

    /**
     * POST /api/clit/mobile/schedule/save
     */
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@RequestBody ClitCalendarSaveDto request) {
        logger.info("CLTImobileShedule save called – machineId={}, cellId={}, shiftId={}",
                request != null ? request.getMachineId() : "null",
                request != null ? request.getCellId() : "null",
                request != null ? request.getShiftId() : "null");

        Map<String, Object> stnd = new HashMap<>();
        Map<String, Object> response = new HashMap<>();

        try {
            String result = cltiMobileSheduleService.save(request);
            stnd.put("result", result);
            response.put("stnd", stnd);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid CLIT schedule input: {}", e.getMessage());
            stnd.put("result", "error");
            stnd.put("message", e.getMessage());
            response.put("stnd", stnd);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            logger.error("Error saving CLIT mobile schedule: {}", e.getMessage(), e);
            stnd.put("result", "error");
            stnd.put("message", "Unable to save.");
            response.put("stnd", stnd);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

//     @PostMapping("/clti/calendar/ensure")
// public ResponseEntity<Map<String, Object>> ensureClitCalendar(@RequestBody ClitCalendarEnsureDto request) throws Exception {
//     logger.info("Entered clti calendar ensure");
//     boolean generated = cltiMobileSheduleService.ensureClitCalendar(request);

//     Map<String, Object> result = new HashMap<>();
//     result.put("result", "success");
//     result.put("generated", generated);
//     return ResponseEntity.ok(result);
// }

@PostMapping("/calendar/ensure")
    public ResponseEntity<Map<String, Object>> ensureCalendar(@RequestBody ClitCalendarEnsureDto request) {
        logger.info("CLTImobileShedule calendar ensure called – machineId={}, cellId={}",
                request != null ? request.getMachineId() : "null",
                request != null ? request.getCellId() : "null");

        Map<String, Object> stnd = new HashMap<>();
        Map<String, Object> response = new HashMap<>();

        try {
            boolean generated = cltiMobileSheduleService.ensureClitCalendar(request);
            stnd.put("result", "success");
            stnd.put("generated", generated);
            stnd.put("calendarReady", true);
            response.put("stnd", stnd);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid CLIT calendar input: {}", e.getMessage());
            stnd.put("result", "error");
            stnd.put("message", e.getMessage());
            response.put("stnd", stnd);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            logger.error("Error ensuring CLIT calendar: {}", e.getMessage(), e);
            stnd.put("result", "error");
            stnd.put("message", "Unable to prepare the calendar.");
            response.put("stnd", stnd);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }




} 

