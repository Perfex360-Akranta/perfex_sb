package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.TradeRoleLinkRequestDto;
import com.akranta.perfex_sb.service.GenTlTradeRoleLinkService;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/trade-role-link")
public class GenTlTradeRoleLinkController {

    private static final Logger logger = LoggerFactory.getLogger(GenTlTradeRoleLinkController.class);

    @Autowired
    private GenTlTradeRoleLinkService service;

    @PostMapping("/save")
    public ResponseEntity<TradeRoleLinkRequestDto> save(@RequestBody TradeRoleLinkRequestDto request) {
    //public ResponseEntity<?> save(@RequestBody TradeRoleLinkRequestDto request) {
        try {
            logger.info("Entered into Controller - Springboot");
            ResponseEntity<TradeRoleLinkRequestDto> result = service.saveTradeRoleLink(request);
            return result;

        }
                
        catch (Exception e) {
            logger.error("Error saving Trade-Role Link: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Error-Message", e.getMessage())
                    .build();
            //return ResponseEntity
                //.status(HttpStatus.CONFLICT)             
                //.body(Map.of("tpmException", e.getMessage()));        
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<TradeRoleLinkRequestDto> delete(@RequestBody TradeRoleLinkRequestDto request) {
        try {
            logger.info("Entered into Controller - Springboot");
            ResponseEntity<TradeRoleLinkRequestDto> result = service.deleteTradeRoleLink(request);
            return result;

        } catch (Exception e) {
            logger.error("Error deleting Trade-Role Link: {}", e.getMessage(), e);
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}
