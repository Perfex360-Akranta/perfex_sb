package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.WhyWhyAnalysisService;
import com.akranta.perfex_sb.dto.WhyWhyRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.BdmTlWhywhymst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/whywhy")
public class WhywhyReportController {

    private static final Logger logger = LoggerFactory.getLogger(WhywhyReportController.class);
    private final WhyWhyAnalysisService service;

    public WhywhyReportController(WhyWhyAnalysisService service) {
        this.service = service;
    }

    
    @PostMapping("/save")
    public ResponseEntity<WhyWhyRequest> saveWhyWhy(@RequestBody WhyWhyRequest request) throws Exception {
        logger.info("Saving/Updating WhyWhy with all related data");
        return service.saveWhyWhy(request);
    }

   
    @PostMapping("/save-effectiveness")
    public ResponseEntity<WhyWhyRequest> saveEffectiveness(@RequestBody WhyWhyRequest request) throws Exception {
        logger.info("Saving/Updating Effectiveness data");
        return service.saveEffectiveness(request);
    }

    
    @GetMapping("/complete/{maskeyid}")
    public ResponseEntity<WhyWhyRequest> getCompleteWhyWhyData(@PathVariable String maskeyid) {
        logger.info("Fetching complete WhyWhy data for keyid: {}", maskeyid);
        WhyWhyRequest completeData = service.getCompleteWhyWhyData(maskeyid);
        return ResponseEntity.ok(completeData);
    }
    @PostMapping("/update-approval")
public ResponseEntity<BdmTlWhywhymst> updateApproval(@RequestBody BdmTlWhywhymst whywhymst) {
    logger.info("Updating approval for WhyWhy keyid: {}", whywhymst.getKeyid());
    
    try {
        BdmTlWhywhymst updated = service.updateApproval(whywhymst);
        logger.info("Successfully updated approval for keyid: {}", updated.getKeyid());
        return ResponseEntity.ok(updated);
    } catch (ResourceNotFoundException e) {
        logger.error("WhyWhy record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error updating approval: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@PostMapping("/update-approvalAI")
public ResponseEntity<BdmTlWhywhymst> updateApprovalAI(@RequestBody BdmTlWhywhymst whywhymst) {
    logger.info("Updating approval for WhyWhy keyid: {}", whywhymst.getKeyid());
    
    try {
        BdmTlWhywhymst updated = service.updateApprovalAI(whywhymst);
        logger.info("Successfully updated approval for keyid: {}", updated.getKeyid());
        return ResponseEntity.ok(updated);
    } catch (ResourceNotFoundException e) {
        logger.error("WhyWhy record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error updating approval: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
 @GetMapping("/spent-time/{keyId}")
    public ResponseEntity<List<Map<String, Object>>> getSpentTime(@PathVariable String keyId) {
        try {
            logger.info("Fetching spent time for keyId: {}", keyId);
            List<Map<String, Object>> result = service.getSpentTime(keyId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid keyId provided: {}", keyId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error fetching spent time: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
 * Get YY Done By data for a master keyid
 */
@GetMapping("/yy-doneby")
public ResponseEntity<List<Map<String, Object>>> getYyDoneby(@RequestParam(required = false) String masterKeyid) {
    try {
        logger.info("Fetching YY Done By data for masterKeyid: {}", masterKeyid);
        List<Map<String, Object>> result = service.getYyDoneby(masterKeyid);
        return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid masterKeyid provided: {}", masterKeyid);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (Exception e) {
        logger.error("Error fetching YY Done By data: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

/**
 * Get Problem Attended By data for a master keyid
 */
@GetMapping("/prob-attby")
public ResponseEntity<List<Map<String, Object>>> getProbAttby(@RequestParam(required = false) String masterKeyid) {
    try {
        logger.info("Fetching Problem Attended By data for masterKeyid: {}", masterKeyid);
        List<Map<String, Object>> result = service.getProbAttby(masterKeyid);
        return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid masterKeyid provided: {}", masterKeyid);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (Exception e) {
        logger.error("Error fetching Problem Attended By data: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@GetMapping("/analysis")
public ResponseEntity<List<Map<String, Object>>> getAnalysis(@RequestParam (required = false) String masdetkeyid) {
    try {
        logger.info("Fetching analysis data for masdetkeyid: {}", masdetkeyid);
        List<Map<String, Object>> result = service.getAnalysis(masdetkeyid);
        return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid masdetkeyid provided: {}", masdetkeyid);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (Exception e) {
        logger.error("Error fetching analysis data: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
@DeleteMapping("/delete-detail/{detailId}")
public ResponseEntity<Map<String, Object>> deleteWhyWhyDetail(@PathVariable String detailId) {
    try {
        logger.info("Deleting WhyWhy detail with keyid: {}", detailId);
        boolean deleted = service.deleteWhyWhyDetail(detailId);
        
        Map<String, Object> response = Map.of(
            "success", deleted,
            "message", "WhyWhy detail deleted successfully",
            "deletedId", detailId
        );
        
        return ResponseEntity.ok(response);
    } catch (ResourceNotFoundException e) {
        logger.error("WhyWhy detail not found: {}", e.getMessage());
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid detailId provided: {}", detailId);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (Exception e) {
        logger.error("Error deleting WhyWhy detail: ", e);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "Error deleting WhyWhy detail: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

@DeleteMapping("/delete-prob-attby/{keyId}")
public ResponseEntity<Map<String, Object>> deleteProblemAttBy(@PathVariable String keyId) {
    try {
        logger.info("Deleting Problem Attended By record with keyid: {}", keyId);
        boolean deleted = service.deleteProblemAttBy(keyId);
        
        Map<String, Object> response = Map.of(
            "success", deleted,
            "message", "Problem Attended By record deleted successfully",
            "deletedId", keyId
        );
        
        return ResponseEntity.ok(response);
    } catch (ResourceNotFoundException e) {
        logger.error("Problem Attended By record not found: {}", e.getMessage());
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid keyId provided: {}", keyId);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (Exception e) {
        logger.error("Error deleting Problem Attended By record: ", e);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "Error deleting Problem Attended By record: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

/**
 * Delete YY Done By record
 */
@DeleteMapping("/delete-yy-doneby/{keyId}")
public ResponseEntity<Map<String, Object>> deleteYyDoneby(@PathVariable String keyId) {
    try {
        logger.info("Deleting YY Done By record with keyid: {}", keyId);
        boolean deleted = service.deleteYyDoneby(keyId);
        
        Map<String, Object> response = Map.of(
            "success", deleted,
            "message", "YY Done By record deleted successfully",
            "deletedId", keyId
        );
        
        return ResponseEntity.ok(response);
    } catch (ResourceNotFoundException e) {
        logger.error("YY Done By record not found: {}", e.getMessage());
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid keyId provided: {}", keyId);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (Exception e) {
        logger.error("Error deleting YY Done By record: ", e);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "Error deleting YY Done By record: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

@GetMapping("/counter-measure")
public ResponseEntity<List<Map<String, Object>>> getCounterMeasure(
        @RequestParam String yyno) {
    try {
        logger.info("Fetching counter measure data for yyno: {}", yyno);
        
        // Validate yyno parameter
        if (yyno == null || yyno.trim().isEmpty()) {
            logger.error("yyno parameter is null or empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ArrayList<>());
        }
        
        List<Map<String, Object>> result = service.getCounterMeasure(yyno);
        
        // Return empty array instead of error if no data found
        if (result == null || result.isEmpty()) {
            logger.warn("No counter measure data found for yyno: {}", yyno);
            return ResponseEntity.ok(new ArrayList<>());
        }
        
        return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid yyno provided: {}", yyno, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ArrayList<>());
    } catch (Exception e) {
        logger.error("Error fetching counter measure data: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ArrayList<>());
    }
}


/**
 * Get Root Cause data by openMode
 */
@GetMapping("/root-cause")
public ResponseEntity<List<Map<String, Object>>> getRootCause(
        @RequestParam(required = false) String openMode) {
    try {
        logger.info("Fetching root cause data for openMode: {}", openMode);
        
        List<Map<String, Object>> result = service.getRootCause(openMode);
        
        if (result == null || result.isEmpty()) {
            logger.warn("No root cause data found for openMode: {}", openMode);
            return ResponseEntity.ok(new ArrayList<>());
        }
        
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        logger.error("Error fetching root cause data: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ArrayList<>());
    }
}

}