package com.akranta.perfex_sb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.BalWorespMstSaveDto;
import com.akranta.perfex_sb.service.BalWorespService;

@RestController
@RequestMapping("/api/BalWoresponsibility")
public class BalWorespController {

    @Autowired
    private BalWorespService service;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody BalWorespMstSaveDto dto) {
        try {
            BalWorespMstSaveDto result = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //update
    @PostMapping("/update")
public ResponseEntity<?> update(@RequestBody BalWorespMstSaveDto dto) {
    try {
        BalWorespMstSaveDto result = service.update(dto);
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
}