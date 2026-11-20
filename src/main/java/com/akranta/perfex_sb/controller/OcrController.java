package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.OcrHandwritingRequest;
import com.akranta.perfex_sb.dto.OcrHandwritingResponse;
import com.akranta.perfex_sb.service.OcrService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    private final OcrService ocrService;

    public OcrController(OcrService ocrService) {
        this.ocrService = ocrService;
    }

    @PostMapping(value = "/handwriting", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OcrHandwritingResponse> handwriting(@RequestBody OcrHandwritingRequest request) {
        try {
            return ResponseEntity.ok(ocrService.recognizeHandwriting(request));
        } catch (Exception e) {
            return ResponseEntity.ok(OcrHandwritingResponse.fail("OCR unavailable"));
        }
    }
}
