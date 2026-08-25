package com.akranta.perfex_sb.controller;

//import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.akranta.perfex_sb.dto.PillarRoleLinkRequestDto;
import com.akranta.perfex_sb.service.GenTlTradeRoleLinkService;
import com.akranta.perfex_sb.service.GenTlWhyWhyPillarRoleLinkService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/pillar-role-link")
public class GenTlWhyWhyPillarRoleLinkController {

    private static final Logger logger = LoggerFactory.getLogger(GenTlWhyWhyPillarRoleLinkController.class);

    @Autowired
    private GenTlWhyWhyPillarRoleLinkService service;

    @PostMapping("/save")
    public ResponseEntity<PillarRoleLinkRequestDto> save(@RequestBody PillarRoleLinkRequestDto request) {
        try {
            logger.info("Entered into Controller - Springboot");
            ResponseEntity<PillarRoleLinkRequestDto> result = service.savePillarRoleLink(request);
            return result;

        } catch (Exception e) {
            logger.error("Error saving Pillar-Role Link: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PostMapping("/delete")
public ResponseEntity<PillarRoleLinkRequestDto> delete(@RequestBody PillarRoleLinkRequestDto request) {
    try {
        logger.info("Entered into Controller - Springboot");
        ResponseEntity<PillarRoleLinkRequestDto> result = service.deletePillarRoleLink(request);
        return result;

    } catch (Exception e) {
        logger.error("Error deleting Pillar-Role Link: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .header("Error-Message", e.getMessage())
                .build();
    }
}
}