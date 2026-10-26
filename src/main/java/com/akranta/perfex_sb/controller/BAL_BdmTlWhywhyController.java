package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.BAL_BdmTlWhywhyRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.BAL_BdmTlWhywhymst;
import com.akranta.perfex_sb.service.BAL_BdmTlWhywhyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/bdm/whywhy")
public class BAL_BdmTlWhywhyController {
 private static final Logger logger = LoggerFactory.getLogger(BAL_BdmTlWhywhyController.class);
    private final BAL_BdmTlWhywhyService whyWhyService;

    public BAL_BdmTlWhywhyController(BAL_BdmTlWhywhyService whyWhyService) {
        this.whyWhyService = whyWhyService;
    }

    /**
     * Single endpoint for insert + update.
     * master.keyid empty / not found -> INSERT (201), existing keyid -> UPDATE (200)
     */
    @PostMapping("/save")
    public ResponseEntity<BAL_BdmTlWhywhyRequest> saveWhyWhy(@RequestBody BAL_BdmTlWhywhyRequest request)
            throws Exception {
        return whyWhyService.saveWhyWhy(request);
    }

    @GetMapping("/refkeyid/{refDocId}")
    public ResponseEntity<List<String>> getYYRefKeyid(@PathVariable String refDocId) throws Exception {
        return ResponseEntity.ok(whyWhyService.getYYRefKeyid(refDocId));
    }
        @GetMapping("/master/{keyid}")
    public ResponseEntity<BAL_BdmTlWhywhymst> selectMasKeyid(@PathVariable String keyid) throws Exception {
        logger.info("Fetching Why Why master for keyid: {}", keyid);

        BAL_BdmTlWhywhymst master = whyWhyService.selectMasKeyid(keyid);

        if (master == null) {
            logger.warn("Why Why master not found for keyid: {}", keyid);
            return ResponseEntity.notFound().build();
        }

        logger.info("Why Why master fetched successfully for keyid: {}", keyid);
        return ResponseEntity.ok(master);
    }

    @GetMapping("/root-cause")
public ResponseEntity<List<Map<String, Object>>> getRootCause(
        @RequestParam(required = false) String openMode) {
    try {
        logger.info("Fetching root cause data for openMode: {}", openMode);
        
        List<Map<String, Object>> result = whyWhyService.getRootCause(openMode);
        
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
@DeleteMapping("/delete-detail/{detailId}")
public ResponseEntity<Map<String, Object>> deleteWhyWhyDetail(@PathVariable String detailId) {
    try {
        logger.info("Deleting WhyWhy detail with keyid: {}", detailId);
        boolean deleted = whyWhyService.deleteWhyWhyDetail(detailId);
        
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

@GetMapping("/analysis")
public ResponseEntity<List<Map<String, Object>>> getAnalysis(@RequestParam (required = false) String masdetkeyid) {
    try {
        logger.info("Fetching analysis data for masdetkeyid: {}", masdetkeyid);
        List<Map<String, Object>> result = whyWhyService.getAnalysis(masdetkeyid);
        return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid masdetkeyid provided: {}", masdetkeyid);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (Exception e) {
        logger.error("Error fetching analysis data: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

}
