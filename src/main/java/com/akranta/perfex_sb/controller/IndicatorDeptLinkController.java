package com.akranta.perfex_sb.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.IndicatorDeptLinkRequest;
import com.akranta.perfex_sb.model.KpiTlIndicatorDeptLink;
import com.akranta.perfex_sb.service.IndicatorDeptLinkService;

@RestController
@RequestMapping("/api/kpi/indicatordeptlink")
public class IndicatorDeptLinkController {

    private static final Logger logger = LoggerFactory.getLogger(IndicatorDeptLinkController.class);

    @Autowired
    private IndicatorDeptLinkService service;

     @PostMapping("/create")
    public ResponseEntity<?> createIndicatorDeptLink(@RequestBody IndicatorDeptLinkRequest request) {
        try {
            KpiTlIndicatorDeptLink result = service.createIndicatorDeptLink(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // @PostMapping("/create")
    // public ResponseEntity<KpiTlIndicatorDeptLink> createIndicatorDeptLink(
    //         @RequestBody IndicatorDeptLinkRequest request) {

    //     try {
    //         // ========== CONVERT DTO LIST TO ENTITY LIST (BASIC FIELDS ONLY) ==========
    //         if (request.getMethodPillarFactlink() != null && !request.getMethodPillarFactlink().isEmpty()) {

    //             List<KpiTlIndicatorDeptLink> entityList = new ArrayList<>();

    //             for (IndicatorDeptLinkItem item : request.getMethodPillarFactlink()) {
    //                 KpiTlIndicatorDeptLink entity = new KpiTlIndicatorDeptLink();

    //                 // Only copy basic fields from DTO
    //                 // entity.setIndicatorid(item.getIndicatorid());
    //                 // entity.setDeptid(item.getDeptid());
    //                 // entity.setIsDelete(item.getIsDelete());

    //                 entityList.add(entity);

    //                 logger.info("Created entity - indicatorid: {}, deptid: {}, isDelete: {}",
    //                         entity.getIndicatorid(), entity.getDeptid(), entity.getIsDelete());
    //             }

    //             // Set the entity list in request
    //             request.setKpiTlIndicatorDeptLink(entityList);

    //             logger.info("Set {} entities in request", entityList.size());
    //         }

    //         // Call service
    //         KpiTlIndicatorDeptLink result = service.createIndicatorDeptLink(request);

    //         return ResponseEntity.ok(result);

    //     } catch (Exception e) {
    //         logger.error("Error creating indicator dept link", e);
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }

    @PostMapping("/validate")
    public ResponseEntity<String> validateKeyIndLink(
            @RequestBody KpiTlIndicatorDeptLink kpiTlIndicatorDeptLink) {
        
        try {
            String validationMessage = service.validateKeyIndLink(kpiTlIndicatorDeptLink);
            
            if (validationMessage.isEmpty()) {
                return ResponseEntity.ok("");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationMessage);
            }
                
        } catch (Exception e) {
            logger.error("Error validating indicator dept link", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    //  @PostMapping("/validate")
    // public ResponseEntity<String> validateKey(
    //         @RequestBody KpiTlIndicatorDeptLink kpiTlIndicatorDeptLink) {
        
    //     try {
    //         String validationMessage = service.validateKeyIndLink(kpiTlIndicatorDeptLink);
            
    //         if (validationMessage.isEmpty()) {
    //             return ResponseEntity.ok("");
    //         } else {
    //             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationMessage);
    //         }
                
    //     } catch (Exception e) {
    //         logger.error("Error validating indicator dept link", e);
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    //     }
    // }


    // @PostMapping("/create")
    // public ResponseEntity<KpiTlIndicatorDeptLink> createIndicatorDeptLink(
    // @RequestBody <IndicatorDeptLinkRequest> request) {

    // @PostMapping("/create")
    // public ResponseEntity<KpiTlIndicatorDeptLink> createIndicatorDeptLink(
    // @RequestBody IndicatorDeptLinkRequest request) {

    // try {
    // // Create a model object (can be empty or populated from request if needed)
    // KpiTlIndicatorDeptLink kpiTlIndicatorDeptLink = new KpiTlIndicatorDeptLink();
    // kpiTlIndicatorDeptLink.setCreatedby(request.getCreatedBy());

    // // KpiTlIndicatorDeptLink result = service.createIndicatorDeptLink(
    // // kpiTlIndicatorDeptLink,
    // // request
    // // );
    // KpiTlIndicatorDeptLink result = service.createIndicatorDeptLink(request);

    // return ResponseEntity.ok(result);

    // } catch (Exception e) {
    // logger.error("Error creating indicator dept link", e);
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    // }
    // }

    // @PostMapping("/create")
    // public ResponseEntity<KpiTlIndicatorDeptLink> createIndicatorDeptLink(
    // @RequestBody IndicatorDeptLinkRequest kpiTlIndicatorDeptLink) {

    // try {
    // KpiTlIndicatorDeptLink result =
    // service.createIndicatorDeptLink(kpiTlIndicatorDeptLink);
    // return ResponseEntity.ok(result);

    // } catch (Exception e) {
    // logger.error("Error creating indicator dept link", e);
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    // }
    // }
}