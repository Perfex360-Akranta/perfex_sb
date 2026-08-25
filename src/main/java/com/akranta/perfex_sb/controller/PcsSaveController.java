package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.PcsSaveRequestDto;
import com.akranta.perfex_sb.model.PcsTlLosscapture;
import com.akranta.perfex_sb.service.PcsSaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pcs")
public class PcsSaveController {

    private final PcsSaveService pcsSaveService;

    public PcsSaveController(PcsSaveService pcsSaveService) {
        this.pcsSaveService = pcsSaveService;
    }

    @PostMapping("/lossCapture")
    public ResponseEntity<PcsTlLosscapture> saveLossCapture(@RequestBody PcsSaveRequestDto request) {
        PcsTlLosscapture saved = pcsSaveService.save(request);
        return ResponseEntity.ok(saved);
    }
}
