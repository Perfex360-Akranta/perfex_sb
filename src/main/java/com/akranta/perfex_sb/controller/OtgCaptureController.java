package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.akranta.perfex_sb.model.GenTlOtgCapture;
import com.akranta.perfex_sb.model.GenTlOtgRouter;
import com.akranta.perfex_sb.service.OtgCaptureService;

@RestController
@RequestMapping("/api/otgcapture")
public class OtgCaptureController {

    @Autowired
    private OtgCaptureService otgCaptureService;

    // Insert new record or update existing record
    @PostMapping
    public ResponseEntity<GenTlOtgCapture> save(
            @RequestBody GenTlOtgCapture otgCapture) {

        return otgCaptureService.save(otgCapture);
    }

    // Fetch one record
    @GetMapping("/{keyid}")
    public ResponseEntity<GenTlOtgCapture> findByKeyid(
            @PathVariable String keyid) {

        GenTlOtgCapture record =
                otgCaptureService.findByKeyid(keyid);

        if (record == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(record);
    }

    // Fetch all records
    @GetMapping
    public ResponseEntity<List<GenTlOtgCapture>> findAll() {
        return ResponseEntity.ok(
                otgCaptureService.findAll());
    }

    // Fetch records by functional location
    @GetMapping("/flid/{flid}")
    public ResponseEntity<List<GenTlOtgCapture>> findByFlid(
            @PathVariable String flid) {

        return ResponseEntity.ok(
                otgCaptureService.findByFlid(flid));
    }

    // Fetch records by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<GenTlOtgCapture>> findByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                otgCaptureService.findByStatus(status));
    }

    // Fetch selected details as a map
    @GetMapping("/details/{keyid}")
    public ResponseEntity<Map<String, Object>> findDetails(
            @PathVariable String keyid) {

        Map<String, Object> details =
                otgCaptureService.findOtgCaptureDetails(keyid);

        if (details == null || details.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(details);
    }

    // Delete record
    @DeleteMapping("/{keyid}")
    public ResponseEntity<String> delete(
            @PathVariable String keyid) {

        try {
            otgCaptureService.delete(keyid);

            return ResponseEntity.ok("OTG Capture deleted");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    //mano (save for router )
     @PostMapping("/router")
    public ResponseEntity<GenTlOtgRouter> saveRouter(
            @RequestBody GenTlOtgRouter otgRouter) {

        return otgCaptureService.saveRouter(otgRouter);
    }
}