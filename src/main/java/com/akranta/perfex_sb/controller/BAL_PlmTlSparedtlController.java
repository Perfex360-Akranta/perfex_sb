package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.BAL_PlmTlSparedtlService;
import com.akranta.perfex_sb.dto.BAL_PlmTlSparedtlRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/spares-pickup")
public class BAL_PlmTlSparedtlController {

    private static final Logger logger = LoggerFactory.getLogger(BAL_PlmTlSparedtlController.class);
    private final BAL_PlmTlSparedtlService service;

    public BAL_PlmTlSparedtlController(BAL_PlmTlSparedtlService service) {
        this.service = service;
    }

    /**
     * Save or Update Spares Pickup details for a Standard.
     * Per-detail: keyid null/empty -> INSERT, keyid exists -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<BAL_PlmTlSparedtlRequest> saveSparesPickup(@RequestBody BAL_PlmTlSparedtlRequest request) throws Exception {
        logger.info("Saving/Updating Spares Pickup details for standardId: {}", request.getStandardId());
        return service.saveSparesPickup(request);
    }

    @GetMapping("/list/{standardId}")
public ResponseEntity<List<Map<String, Object>>> getSprPickup(@PathVariable String standardId) throws Exception {
    logger.info("Fetching Spares Pickup for standardId: {}", standardId);
    return service.getSprPickup(standardId);
}
}