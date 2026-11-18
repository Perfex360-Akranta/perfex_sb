package com.akranta.perfex_sb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.BAL_PcsTlCycletimemstSaveDto;
import com.akranta.perfex_sb.model.BAL_PcsTlCycletimemst;
import com.akranta.perfex_sb.service.BAL_PcsTlCycletimemstService;
import java.util.Collections;

@RestController
@RequestMapping("/api/cycletime")
public class BAL_PcsTlCycletimemstController {

    @Autowired
    private BAL_PcsTlCycletimemstService service;

   
   @PostMapping("/createorupdate")
public ResponseEntity<BAL_PcsTlCycletimemst> createorupdate(@RequestBody BAL_PcsTlCycletimemst rec) {

   

    BAL_PcsTlCycletimemstSaveDto dto = new BAL_PcsTlCycletimemstSaveDto();
    dto.setCreatedby(rec.getCreatedby());
    dto.setCycletimemstList(Collections.singletonList(rec));

    BAL_PcsTlCycletimemstSaveDto result = service.createorupdateCycletime(dto);
    return ResponseEntity.ok(result.getCycletimemstList().get(0));
}
    

  @PostMapping("/delete/{keyid}")
public void deleteByKeyid(@PathVariable String keyid) throws Exception {
    service.deleteByKeyid(keyid);
}
}