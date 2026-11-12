package com.akranta.perfex_sb.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.AssignResponsibilityDto;
import com.akranta.perfex_sb.dto.IncidentApprovalDto;
import com.akranta.perfex_sb.dto.IncidentClosureDto;
import com.akranta.perfex_sb.dto.IncidentVerifyDto;
import com.akranta.perfex_sb.dto.SheTlIncidentEmployeenDto;
import com.akranta.perfex_sb.dto.UpdateIncidentDto;
import com.akranta.perfex_sb.model.GenTlMommst;
import com.akranta.perfex_sb.model.SheTlIncidentEmployeenew;
import com.akranta.perfex_sb.model.SheTlIncidentmstnew;
import com.akranta.perfex_sb.service.EHSAccidentRptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/ehsacc")
public class EHSAccidentRptController {

    @Autowired
    private EHSAccidentRptService EHSAccidentRptService;

    private static final Logger logger = LoggerFactory.getLogger(EHSAccidentRptController.class);

    @PostMapping("/save")
    public ResponseEntity<SheTlIncidentmstnew> save(@RequestBody SheTlIncidentmstnew sheTlIncidentmstnew)
         throws Exception {
        logger.info("Entered");
        logger.info("Time",sheTlIncidentmstnew.getOccurred_date());
        SheTlIncidentmstnew result = EHSAccidentRptService.save(sheTlIncidentmstnew);
        return ResponseEntity.ok(result);
        
    }
    

      @PostMapping("/saveEmployee")
    public ResponseEntity<SheTlIncidentEmployeenew> save(@RequestBody SheTlIncidentEmployeenDto SheTlIncidentEmployeenDto)
         throws Exception {
        SheTlIncidentEmployeenew result = EHSAccidentRptService.save(SheTlIncidentEmployeenDto);
        return ResponseEntity.ok(result);
        
    }

    @GetMapping("/getById")
    public ResponseEntity<SheTlIncidentmstnew> getIncidentmstnewById(@RequestParam("keyId") String keyId) {

        
        SheTlIncidentmstnew result = EHSAccidentRptService.getSheTlIncidentmstnewById(keyId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateIncident(@RequestBody UpdateIncidentDto incident) {

        int result = EHSAccidentRptService.updateIncident(incident);

        if(result > 0){
            return ResponseEntity.ok("Incident updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Update failed");
        }
    }

    @PostMapping("/assign-responsibility")
    public ResponseEntity<String> assignResponsibility(@RequestBody AssignResponsibilityDto dto) {

        int rowsUpdated = EHSAccidentRptService.assignResponsibility(dto);

        if (rowsUpdated > 0) {
            return ResponseEntity.ok("Responsibility assigned successfully.");
        } else {
            return ResponseEntity.badRequest().body("No records were updated.");
        }
    }


    @PostMapping("/incident-closure")
    public ResponseEntity<String> updateIncidentClosure(@RequestBody IncidentClosureDto dto) {

    int rowsUpdated = EHSAccidentRptService.updateIncidentClosure(dto);

    if (rowsUpdated > 0) {
        return ResponseEntity.ok("Incident closure updated successfully.");
    } else {
        return ResponseEntity.badRequest().body("No records were updated.");
    }
}

    @GetMapping("/keyid")
    public ResponseEntity<List<Map<String, Object>>> getActionPlanIds(@PathVariable String keyid) {

        List<Map<String, Object>> result = EHSAccidentRptService.getActionPlanIds(keyid);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/delete/{masterRefId}")
    
    public String DeleteIncident(@PathVariable String masterRefId) {
        return EHSAccidentRptService.DeleteIncident(masterRefId);
    }


    @PutMapping("/verify")
    public ResponseEntity<String> verifyIncident(@RequestBody IncidentVerifyDto incidentVerifyDTO) {

        String response = EHSAccidentRptService.verifyIncident(incidentVerifyDTO);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/approve")
    public ResponseEntity<String> approveIncident(@RequestBody IncidentApprovalDto dto) {
        boolean success = EHSAccidentRptService.approveIncident(dto);
        if (success) {
            return ResponseEntity.ok("Incident approved successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to approve incident");
        }
    }

    
    
}