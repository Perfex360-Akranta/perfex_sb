package com.akranta.perfex_sb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.model.BAL_PcsTlEnablelosscapture;
import com.akranta.perfex_sb.service.BAL_PcsTlEnablelosscaptureService;

@RestController
@RequestMapping("/api/BAL_PcsTlEnablelosscapture")
public class BAL_PcsTlEnablelosscaptureController {

    @Autowired
    private BAL_PcsTlEnablelosscaptureService service;

    @PostMapping ("/createorupdate")
    public ResponseEntity<List<BAL_PcsTlEnablelosscapture>> create (@RequestBody List<BAL_PcsTlEnablelosscapture> Bal_PcsTlEnablelosscapture){
       List<BAL_PcsTlEnablelosscapture> result = service.createorupdate(Bal_PcsTlEnablelosscapture);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


}
