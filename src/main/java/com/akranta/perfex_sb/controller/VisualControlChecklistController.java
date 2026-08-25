package com.akranta.perfex_sb.controller;


import com.akranta.perfex_sb.dto.VisualControlChecklistDto;
import com.akranta.perfex_sb.model.GenTlVisualcontrolchecklist;
import com.akranta.perfex_sb.service.VisualControlCheckService;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visualcontrol")
public class VisualControlChecklistController {

    private static final Logger logger = LoggerFactory.getLogger(VisualControlChecklistController.class);

    private final VisualControlCheckService service;

    public VisualControlChecklistController(VisualControlCheckService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<VisualControlChecklistDto> createOrUpdate(
            @RequestBody VisualControlChecklistDto dto) {
        return service.createOrUpdate(dto);
    }

    @GetMapping("/native/{keyid}")
public ResponseEntity<?> getByKeyidNative(@PathVariable String keyid) {
    try {
        logger.info("Fetching Visual Control Checklist by keyid (native): {}", keyid);
        Object[] result = service.getByKeyidNative(keyid);
        
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", true, "message", "Record not found for keyid: " + keyid));
        }
        
        return ResponseEntity.ok(result);
    } catch (Exception e) {
       logger.error("Error fetching record", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", true, "message", "Failed to fetch record: " + e.getMessage()));
    }
}

//deleteing the records
 @DeleteMapping("/delete/{keyId}")
public ResponseEntity<?> deleteChecklist(@PathVariable String keyId) {

    try {

        GenTlVisualcontrolchecklist checklist = new GenTlVisualcontrolchecklist();
        checklist.setKeyid(keyId);

        return service.delete(checklist);

    } 
    catch (Exception e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    

}

}

