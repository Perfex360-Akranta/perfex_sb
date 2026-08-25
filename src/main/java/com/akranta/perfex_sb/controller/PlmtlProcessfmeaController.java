package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.PlmtlProcessfmeaDto;
import com.akranta.perfex_sb.dto.ProcessfmeaParamDto;
import com.akranta.perfex_sb.service.PlmtlProcessfmeaService;

@RestController
@RequestMapping("/api/plmtlprocessfmea")
public class PlmtlProcessfmeaController {

    @Autowired
    private PlmtlProcessfmeaService plmtlProcessfmeaService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PlmtlProcessfmeaDto plmtlProcessfmeaDto) {
        return plmtlProcessfmeaService.save(plmtlProcessfmeaDto);
    }

    @GetMapping("/recall/{keyId}/{type}")
    public  List<Map<String, Object>> recallFmea(
            @PathVariable String keyId,
            @PathVariable String type) {

        return plmtlProcessfmeaService.recallFmeaByKeyId(keyId, type);
    }

    @PostMapping("/deleteDtls")
    public ResponseEntity<?> deleteDtls(@RequestBody List<ProcessfmeaParamDto> paramDtoList) {
        try {
            plmtlProcessfmeaService.deleteDtls(paramDtoList);
            return ResponseEntity.ok("Detail records processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
