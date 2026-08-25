package com.akranta.perfex_sb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.PlmEquipmentfmeaDTO;
import com.akranta.perfex_sb.dto.EquipmentfmeaParamDTO;
import com.akranta.perfex_sb.service.PlmEquipmentfmeaService;

@RestController
@RequestMapping("/api/plmequipmentfmea")
public class PlmEquipmentfmeaController {

    @Autowired
    private PlmEquipmentfmeaService plmEquipmentfmeaService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PlmEquipmentfmeaDTO plmEquipmentfmeaDTO) {
        return plmEquipmentfmeaService.save(plmEquipmentfmeaDTO);
    }

    @PostMapping("/deleteDtls")
    public ResponseEntity<?> deleteDtls(@RequestBody List<EquipmentfmeaParamDTO> paramDtoList) {
        try {
            plmEquipmentfmeaService.deleteDtls(paramDtoList);
            return ResponseEntity.ok("Detail records processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
