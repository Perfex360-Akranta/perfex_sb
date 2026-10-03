
package com.akranta.perfex_sb.controller;

//import com.akranta.perfex_sb.exception.WorkOrderResponsibilityException;
import com.akranta.perfex_sb.model.BalPlanconfiguration;
import com.akranta.perfex_sb.service.BalPlanconfigurationService;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/plan-configuration")
public class BalPlanconfigurationController {

    private static final Logger logger = LoggerFactory.getLogger(BalPlanconfigurationController.class);
    private final BalPlanconfigurationService service;

    public BalPlanconfigurationController(BalPlanconfigurationService service) {
        this.service = service;
    }

   
    // @PostMapping("/create")
    // public ResponseEntity<BalPlanconfiguration> create(@RequestBody BalPlanconfiguration planconfiguration) {
    //     logger.info("Creating plan configuration for machineid={}, level={}",
    //             planconfiguration.getMachineid(), planconfiguration.getLevel());
    //     BalPlanconfiguration created = service.create(planconfiguration);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(created);
    // }
//Plan Configuration-Swetha
    @PostMapping("/create")
public ResponseEntity<?> create(
        @RequestBody BalPlanconfiguration planconfiguration) {

    logger.info("Creating plan configuration for machineid={}, level={}",
            planconfiguration.getMachineid(),
            planconfiguration.getLevel());

    try {
        ResponseEntity<?> created = service.create(planconfiguration);
        return created;
        //return ResponseEntity.status(HttpStatus.CREATED).body(created);

    } catch (ResponseStatusException e) {

      return ResponseEntity
                .status(e.getStatusCode())
                .build();
    }
}
}