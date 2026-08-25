package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.JhaTlAuditParameterDto;
import com.akranta.perfex_sb.dto.JhaTlAuditTemplateDto;
// import com.akranta.perfex_sb.dto.JhaTlAudituploadDto;
import com.akranta.perfex_sb.model.JhaTlAuditparameter;
import com.akranta.perfex_sb.model.JhaTlTemplatelevellink;
//import com.akranta.perfex_sb.model.JhaTlAudittemplate;
import com.akranta.perfex_sb.service.JhaTlAuditParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auditparametr")

public class JhAuditParameterController {

    @Autowired
    private JhaTlAuditParameterService service;

    @GetMapping("/{keyid}")
    public ResponseEntity<JhaTlAuditparameter> getParameterByKeyid(@PathVariable String keyid) {
        JhaTlAuditparameter audit = service.getParameterByKeyid(keyid);
        return ResponseEntity.ok(audit);
    }

    @GetMapping("/parameters")
    public ResponseEntity<?> getAuditParameters(@RequestParam String templateId) {

        if (templateId == null || templateId.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Template ID is required");
        }

        try {
            List<JhaTlAuditParameterDto> result = service.getJhAuditParameterGrid(templateId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching audit parameters: " + e.getMessage());
        }
    }

    @GetMapping("/parameters/{templateId}")
    public ResponseEntity<?> getAuditParametersByPath(@PathVariable String templateId) {

        if (templateId == null || templateId.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Template ID is required");
        }

        try {
            List<JhaTlAuditParameterDto> result = service.getJhAuditParameterGrid(templateId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching audit parameters: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<JhaTlAuditTemplateDto> create(@RequestBody JhaTlAuditTemplateDto jhaTlAuditTemplateDto) {
        JhaTlAuditTemplateDto result = service.createOrUpdateTemplate(jhaTlAuditTemplateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // @GetMapping("/auditlevels")
    // public ResponseEntity<?> getAuditLevels(
    // @RequestParam(required = false) String templateId,
    // @RequestParam(required = false) String jhStepId) {

    // try {
    // return ResponseEntity.ok(
    // service.getAuditLevels(templateId, jhStepId)
    // );
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("Error fetching audit levels: " + e.getMessage());
    // }
    // }

    @GetMapping("/auditlevels")
    public ResponseEntity<List<JhaTlTemplatelevellink>> getAuditLevels(
            @RequestParam(required = false) String templateId,
            @RequestParam(required = false) String flId,
            @RequestParam(required = false) String jhStepId) {

        List<JhaTlTemplatelevellink> result = service.getAuditLevels(templateId, flId, jhStepId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/minimummarks")
    public ResponseEntity<Integer> getMinimumMarks(
            @RequestParam String auditLevel,
            @RequestParam String auditTemplate) {

        try {
            Integer minMarks = service.getMinimumMarks(auditLevel, auditTemplate);
            return ResponseEntity.ok(minMarks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(0);
        }
    }

}
