package com.akranta.perfex_sb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.NextDueDateDTO;
import com.akranta.perfex_sb.dto.BalPlanConfigurationSaveDto; 
import com.akranta.perfex_sb.service.BalPlanConfigurationService;

@RestController
@RequestMapping("/api/BalPlanConfiguration")
public class BalPlanConfigurationController {

    @Autowired
    private BalPlanConfigurationService service;

    @GetMapping("/getnextduedates")
    public List<NextDueDateDTO> getNextDueDates() {
        return service.getNextDueDates();
    }

    //save
   @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody BalPlanConfigurationSaveDto dto) {   // 👈 updated type
        try {
            BalPlanConfigurationSaveDto result = service.savePlanConfiguration(dto);   // 👈 updated type
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

