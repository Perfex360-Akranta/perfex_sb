package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.BalPcsLossSaveDto;
import com.akranta.perfex_sb.model.BAL_PcsLossCapture;
import com.akranta.perfex_sb.service.BAL_PcsLossService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pcsentry")
public class BAL_PcsLossSaveController {

    private final BAL_PcsLossService balPcsLossService;

    public BAL_PcsLossSaveController(BAL_PcsLossService balPcsLossService) {
        this.balPcsLossService = balPcsLossService;
    }

    @PostMapping("/saveLoss")
    public ResponseEntity<BAL_PcsLossCapture> saveLossEntry(@RequestBody BalPcsLossSaveDto request) throws Exception {
        BAL_PcsLossCapture saved = balPcsLossService.saveLoss(request);
        return ResponseEntity.ok(saved);
    }
}