package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.CliTlStandardsService;
import com.akranta.perfex_sb.dto.CliTlStandardsRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cli-tl-standards")
public class CliTlStandardsController {

    private static final Logger logger = LoggerFactory.getLogger(CliTlStandardsController.class);
    private final CliTlStandardsService service;

    public CliTlStandardsController(CliTlStandardsService service) {
        this.service = service;
    }

    /**
     * Save or Update multiple CLI Standard rows in one call.
     * Row keyid null/empty -> INSERT, keyid exists -> UPDATE.
     * (image / GEN_TL_DOCUPDATES handling excluded)
     */
    @PostMapping("/save")
    public ResponseEntity<CliTlStandardsRequest> saveCliTlStandards(@RequestBody CliTlStandardsRequest request) throws Exception {
        logger.info("Saving/Updating CLI Standard multiple rows");
        return service.saveCliTlStandards(request);
    }
    @DeleteMapping("/delete/{keyid}")
public ResponseEntity<Void> deleteCliTlStandards(@PathVariable String keyid) throws Exception {
    logger.info("Deleting CLI Standard with Key ID: {}", keyid);
    return service.deleteCliTlStandards(keyid);
}
}
