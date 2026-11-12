package com.akranta.perfex_sb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.PlantSafetyRequest;
import com.akranta.perfex_sb.model.SheTlPlantSafetydtl;
import com.akranta.perfex_sb.model.SheTlPlantSafetymst;
import com.akranta.perfex_sb.service.PlantSafetyService;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/plant-safety")
public class PlantSafetyController {

    private static final Logger logger = LoggerFactory.getLogger(PlantSafetyController.class);

    private final PlantSafetyService service;

    public PlantSafetyController(PlantSafetyService service) {
        this.service = service;
    }

    /**
     * POST /api/plant-safety/save
     * master.keyid null/empty -> INSERT
     * master.keyid present    -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<PlantSafetyRequest> savePlantSafety(@RequestBody PlantSafetyRequest request) {
        try {
            logger.info("Saving/Updating Plant Safety record");
            return service.savePlantSafety(request);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error saving Plant Safety: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{keyid}")
public ResponseEntity<SheTlPlantSafetymst> getPlantSafetyByKeyid(@PathVariable String keyid) {
    try {
        return service.getPlantSafetyByKeyid(keyid);
    } catch (Exception e) {
        logger.error("Error fetching Plant Safety by keyid {}: {}", keyid, e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
@GetMapping("/detail-value/{keyid}")
public ResponseEntity<String> getDetailValue(@PathVariable String keyid) {
    try {
        return ResponseEntity.ok(service.getDetailValue(keyid));
    } catch (Exception e) {
        logger.error("Error fetching detail value for keyid {}: {}", keyid, e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
@GetMapping("/detail/{keyid}")
public ResponseEntity<List<Map<String, Object>>> getDetailBySpsmKeyid(@PathVariable String keyid) {
    try {
        return ResponseEntity.ok(service.getDetailBySpsmKeyid(keyid));
    } catch (Exception e) {
        logger.error("Error fetching details for keyid {}: {}", keyid, e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

 @GetMapping("/responsibility/{keyid}")
    public ResponseEntity<String> getResponse(@PathVariable String keyid) {
        try {
            logger.info("Fetching responsibility for detail keyid: {}", keyid);
            return ResponseEntity.ok(service.getResponse(keyid));
        } catch (Exception e) {
            logger.error("Error fetching responsibility for keyid {}: {}", keyid, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // GET - Observations by detail keyid
    // ─────────────────────────────────────────────────────────────
    @GetMapping("/observations/{keyid}")
    public ResponseEntity<String> getObservations(@PathVariable String keyid) {
        try {
            logger.info("Fetching observations for detail keyid: {}", keyid);
            return ResponseEntity.ok(service.getObser(keyid));
        } catch (Exception e) {
            logger.error("Error fetching observations for keyid {}: {}", keyid, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // GET - Master keyid by detail keyid
    // ─────────────────────────────────────────────────────────────
    @GetMapping("/master-keyid/{keyid}")
    public ResponseEntity<String> getMasterKeyid(@PathVariable String keyid) {
        try {
            logger.info("Fetching master keyid for detail keyid: {}", keyid);
            return ResponseEntity.ok(service.getMaster(keyid));
        } catch (Exception e) {
            logger.error("Error fetching master keyid for detail keyid {}: {}", keyid, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // GET - Response remarks by detail keyid
    // ─────────────────────────────────────────────────────────────
    @GetMapping("/response-remarks/{keyid}")
    public ResponseEntity<String> getResponseRemarks(@PathVariable String keyid) {
        try {
            logger.info("Fetching response remarks for detail keyid: {}", keyid);
            return ResponseEntity.ok(service.getResponseRemarks(keyid));
        } catch (Exception e) {
            logger.error("Error fetching response remarks for keyid {}: {}", keyid, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

   @PostMapping("/single-responsibility")
public ResponseEntity<String> singleResponsiblty(
        @RequestBody PlantSafetyRequest request) {  // ✅ only @RequestBody, no @RequestParam
    try {
        logger.info("Updating single responsibility for detailkeyid: {}", request.getDetailkeyid());

        String result = service.singleResponsiblty(
                request.getDetailkeyid(),
                request.getResponsevalue(),
                request.getTargetdate(),
                request.getMaster(),
                request.getDetails().get(0)  // first detail item
        );
        return ResponseEntity.ok(result);

    } catch (Exception e) {
        logger.error("Error updating responsibility: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
@PostMapping("/verify-update")
public ResponseEntity<String> verifyUpdate(@RequestBody PlantSafetyRequest request) {
    try {
        logger.info("Updating verify fields for dtlid: {}", request.getDetailkeyid());
        String result = service.verifyUpdate(
                request.getDetailkeyid(),
                request.getResponsevalue(),   // maps to verid
                request.getTargetdate()       // maps to verifydate
        );
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        logger.error("Error updating verify fields: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@PostMapping("/res-update")
public ResponseEntity<String> resUpdate(@RequestBody PlantSafetyRequest request) {
    try {
        logger.info("Updating responsibility for dtlid: {}", request.getDetailkeyid());
        String result = service.resUpdate(
                request.getDetailkeyid(),
                request.getResponsevalue(),   // maps to resid
                request.getTargetdate()       // maps to targetdate
        );
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        logger.error("Error updating responsibility: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@PostMapping("/check-delete")
public ResponseEntity<String> checkDeleteList(
        @RequestParam String detailkeyid,
        @RequestParam(required = false) String apmdelete) {
    try {
        logger.info("Deleting detail keyid: {}, apmdelete: {}", detailkeyid, apmdelete);
        String result = service.checkDeleteList(detailkeyid, apmdelete);
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        logger.error("Error deleting records: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}


@PostMapping("/update-verification")
public ResponseEntity<String> updateVerification(@RequestBody PlantSafetyRequest request) {
    try {
        logger.info("Updating verification for detailkeyid: {}", request.getDetailkeyid());
        String result = service.updateVerification(
                request.getDetailkeyid(),
                request.getResponsevalue(),
                request.getTargetdate(),
                request.getVerifystatus(),
                request.getRemarks()
        );
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        logger.error("Error updating verification: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}


@PostMapping("/update-action-closure")
public ResponseEntity<String> updateActionClosure(@RequestBody PlantSafetyRequest request) {
    try {
        logger.info("Updating action closure for detailkeyid: {}", request.getDetailkeyid());
        String result = service.updateActionClosure(
                request.getDetailkeyid(),
                request.getResponsevalue(),
                request.getTargetdate(),
                request.getRevisedate(),
                request.getStatus(),
                request.getCorrectiveaction(),
                request.getRemarks(),
                request.getAbnKeyid()
        );
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        logger.error("Error updating action closure: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
}