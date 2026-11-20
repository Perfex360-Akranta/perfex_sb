package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.OcrFormRequest;
import com.akranta.perfex_sb.dto.OcrFormResponse;
import com.akranta.perfex_sb.service.FormOcrService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Full-form OCR. Separate from OcrController so the single-field flow is untouched. */
@RestController
@RequestMapping("/api/ocr")
public class FormOcrController {

    private final FormOcrService formOcrService;

    public FormOcrController(FormOcrService formOcrService) {
        this.formOcrService = formOcrService;
    }

    @PostMapping(value = "/form", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OcrFormResponse> form(@RequestBody OcrFormRequest request) {
        try {
            return ResponseEntity.ok(formOcrService.recognizeForm(request));
        } catch (Exception e) {
            return ResponseEntity.ok(OcrFormResponse.fail("Form OCR unavailable"));
        }
    }
}
