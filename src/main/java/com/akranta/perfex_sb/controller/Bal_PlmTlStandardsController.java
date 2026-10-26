package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.Bal_PlmTlStandardsService;
import com.akranta.perfex_sb.dto.PlmTlStandardsRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/plm-tl-standards")
public class Bal_PlmTlStandardsController {

    private static final Logger logger = LoggerFactory.getLogger(Bal_PlmTlStandardsController.class);
    private final Bal_PlmTlStandardsService service;

    public Bal_PlmTlStandardsController(Bal_PlmTlStandardsService service) {
        this.service = service;
    }

   
    @PostMapping("/save")
    public ResponseEntity<PlmTlStandardsRequest> savePlmTlStandards(@RequestBody PlmTlStandardsRequest request) throws Exception {
        logger.info("Saving/Updating PM Standard multiple rows");
        return service.savePlmTlStandards(request);
    }

    @DeleteMapping("/delete/{keyid}")
    public ResponseEntity<Void> deletePlmTlStandards(@PathVariable String keyid) throws Exception {
        logger.info("Deleting PM Standard with Key ID: {}", keyid);
        return service.deletePlmTlStandards(keyid);
    }

    @GetMapping("/cbm/{pmStandardId}")
public ResponseEntity<List<Map<String, Object>>> getCBM(@PathVariable String pmStandardId) throws Exception {
    logger.info("Fetching CBM zone data for PM Standard Key ID: {}", pmStandardId);
    return service.getCBM(pmStandardId);
}
}