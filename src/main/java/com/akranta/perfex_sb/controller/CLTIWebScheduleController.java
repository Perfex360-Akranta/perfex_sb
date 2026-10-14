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

import com.akranta.perfex_sb.dto.ClitCalendarSaveDto;
import com.akranta.perfex_sb.service.CLTIWebScheduleService;

@RestController
@RequestMapping("/api/clit/web/schedule")
public class CLTIWebScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(CLTIWebScheduleController.class);

    private final CLTIWebScheduleService cltiWebScheduleService;

    public CLTIWebScheduleController(CLTIWebScheduleService cltiWebScheduleService) {
        this.cltiWebScheduleService = cltiWebScheduleService;
    }

    /**
     * POST /api/clit/web/schedule/save
     */
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@RequestBody ClitCalendarSaveDto request) {
        logger.info("CLTIWebSchedule save called – machineId={}, cellId={}, shiftId={}",
                request != null ? request.getMachineId() : "null",
                request != null ? request.getCellId() : "null",
                request != null ? request.getShiftId() : "null");

        Map<String, Object> stnd = new HashMap<>();
        Map<String, Object> response = new HashMap<>();

        try {
            String result = cltiWebScheduleService.save(request);
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
            logger.error("Error saving CLIT web schedule: {}", e.getMessage(), e);
            stnd.put("result", "error");
            stnd.put("message", "Unable to save.");
            response.put("stnd", stnd);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}