package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.JHAuidtParameterDto;
import com.akranta.perfex_sb.dto.JhaTlAuditTemplateGridDto;
import com.akranta.perfex_sb.dto.JhaTlAuditmstAndDtlDto;
import com.akranta.perfex_sb.dto.JhauditDto;
import com.akranta.perfex_sb.model.JhaTlAuditmst;
import com.akranta.perfex_sb.service.JhAuditService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jhaudit")
public class JhAuditController {

    private static final Logger logger = LoggerFactory.getLogger(JhAuditController.class);

    private final JhAuditService service;

    public JhAuditController(JhAuditService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<JhaTlAuditmstAndDtlDto>> getAllAudits() {
        List<JhaTlAuditmstAndDtlDto> audits = service.getAllAudits();
        return ResponseEntity.ok(audits);
    }

    // // NEW: Get audit by keyid
    // @GetMapping("/{keyid}")
    // public ResponseEntity<JhaTlAuditmstAndDtlDto> getAuditByKeyid(@PathVariable
    // String keyid) {
    // JhaTlAuditmstAndDtlDto audit = service.getAuditByKeyid(keyid);
    // return ResponseEntity.ok(audit);
    // }

    // @GetMapping("/{keyid}")
    // public ResponseEntity<List<JhaTlAuditmst>> getAuditByKeyid(
    // @PathVariable String keyid,
    // @RequestParam(required = false) String flid,
    // @RequestParam(required = false) String auditteamid,
    // @RequestParam(required = false) String audittype,
    // @RequestParam(required = false) String auditpillar,
    // @RequestParam(required = false) String jhstepid,
    // @RequestParam(required = false) String auditdate,
    // @RequestParam(required = false) String auditortype) {

    // List<JhaTlAuditmst> audits = service.getAuditByKeyid(keyid, flid,
    // auditteamid,
    // audittype, auditpillar,
    // jhstepid, auditdate, auditortype);
    // return ResponseEntity.ok(audits);
    // }

    // @PostMapping("/search")
    // public ResponseEntity<JhaTlAuditmst> getAuditByKeyid(@RequestBody
    // JhaTlAuditmst jhaTlAuditmst) {

    // List<JhaTlAuditmst> audits = service.getAuditByKeyid(jhaTlAuditmst);

    // logger.info("Fetching audit size : {}, jhamKeyid: ", audits.size() );
    // if(audits.size() == 1){
    // return ResponseEntity.ok(audits.get(0));
    // }
    // return ResponseEntity.ok(jhaTlAuditmst);
    // }

    @PostMapping("/search")
    public ResponseEntity<JhaTlAuditmst> getAuditByKeyid(@RequestBody JhauditDto jhauditDto) {

        List<JhaTlAuditmst> audits = service.getAuditByKeyid(jhauditDto);

        logger.info("Fetching audit size : {}, jhamKeyid: ", audits.size());

        JhaTlAuditmst jhaTlAuditmst = new JhaTlAuditmst();
        if (audits.size() == 1) {
            return ResponseEntity.ok(audits.get(0));
        }
        return ResponseEntity.ok(jhaTlAuditmst);
    }

    @PostMapping
    public ResponseEntity<JhaTlAuditmstAndDtlDto> create(@RequestBody JhaTlAuditmstAndDtlDto jhaTlAuditmstAndDtlDto) {
        JhaTlAuditmstAndDtlDto result = service.createOrUpdateAudit(jhaTlAuditmstAndDtlDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/template/grid")
    public ResponseEntity<List<JhaTlAuditTemplateGridDto>> getAuditTemplateGrid(
            @RequestParam(required = false) String templateId,
            @RequestParam(required = false) String jhamKeyid) {

        List<JhaTlAuditTemplateGridDto> result = service.getAuditTemplateGrid(templateId, jhamKeyid);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/parameter")
    public ResponseEntity<JHAuidtParameterDto> saveParameter(@RequestBody JHAuidtParameterDto jhauidtParameterDto) {
        try {
            logger.info("Entered into Controller - Save Parameter");
            ResponseEntity<JHAuidtParameterDto> result = service.saveParameter(jhauidtParameterDto);
            return result;

        } catch (Exception e) {
            logger.error("Error saving Parameter: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    // @GetMapping("/existing")
    // public ResponseEntity<JhaTlAuditmst> getExisting(
    // @RequestParam String templateId,
    // @RequestParam String flId,
    // @RequestParam(required = false) String date,
    // @RequestParam String auditType,
    // @RequestParam String stepId) throws Exception {

    // JhaTlAuditmst result = service.getExistingkeyid(
    // templateId, flId, date, auditType, stepId);

    // return ResponseEntity.ok(result);
    // }

    @GetMapping("/existing")
    public ResponseEntity<JhaTlAuditmst> getExisting(
            @RequestParam String templateId,
            @RequestParam String flId,
            @RequestParam(required = false) String date,
            @RequestParam String auditType,
            @RequestParam String stepId) throws Exception {

        // Clean stepId - treat "-" as null
        if (stepId != null && (stepId.equals("-") || stepId.trim().isEmpty())) {
            stepId = null;
        }

        JhaTlAuditmst result = service.getExistingkeyid(
                templateId, flId, date, auditType, stepId);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/templatedelete/{parameterId}")
    public ResponseEntity<String> deleteAuditTemplate(@PathVariable String parameterId) {
        try {
            service.deleteAuditTemplate(parameterId);
            return ResponseEntity.ok("Audit template deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting audit template: " + e.getMessage());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getAuditCount(@RequestParam String flId) {
        try {
            Long count = service.getAuditCount(flId);
            return ResponseEntity.ok(count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // @GetMapping("/unassignedcount")
    // public ResponseEntity<String> getUnassignedCount(@RequestParam String flid) {
    // String count = service.getUnassignedAuditTeamsCount(flid);
    // return ResponseEntity.ok(count);
    // }

    @GetMapping("/unassignedcount")
    public ResponseEntity<String> getUnassignedCount(
            @RequestParam(required = false) String flid) {

        if (flid == null || flid.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("flid parameter is required");
        }

        String count = service.getUnassignedAuditTeamsCount(flid);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/auditlevel/current")
    public ResponseEntity<String> getAuditLevelCurrent(@RequestParam String jhTemplateId) {
        try {
            String keyId = service.getAuditLevelCurrent(jhTemplateId);
            return ResponseEntity.ok(keyId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/minimumpoints")
    public ResponseEntity<Integer> getMinimumPointsss(
            @RequestParam String auditLevel,
            @RequestParam String auditTemplate) {

        try {
            Integer minPoints = service.getMinimumPoints(auditTemplate, auditLevel);
            return ResponseEntity.ok(minPoints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(0);
        }
    }

    //delete the all records in detail and master

   @DeleteMapping("/delete/{masterId}")
    public ResponseEntity<String> deleteAudit(@PathVariable String masterId) {
        try {

            service.deleteAudit(masterId);

            return ResponseEntity.ok("Audit deleted successfully");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting audit: " + e.getMessage());
        }
    }


//   @DeleteMapping("/delete")
// public ResponseEntity<String> delete(@RequestBody JhaTlAuditmstAndDtlDto dto) throws Exception {
//     service.delete(dto);
//     return ResponseEntity.ok("Deleted Successfully");
// }

 

}