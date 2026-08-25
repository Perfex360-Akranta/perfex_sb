package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.BestKaizenRecallDto;
import com.akranta.perfex_sb.dto.BestKaizenmsdtlDto;
import com.akranta.perfex_sb.model.KznTlBestmst;
import com.akranta.perfex_sb.service.BestKaizenService;

@RestController
@RequestMapping("/api/bestKaizen")
public class BestKaizenController {
    @Autowired
    private BestKaizenService service;

    @PostMapping("/save")
    public ResponseEntity<BestKaizenmsdtlDto> saveBestKaizen(@RequestBody BestKaizenmsdtlDto dto) throws Exception {
        BestKaizenmsdtlDto result = service.saveBestKaizen(dto);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/getData")
    public List<Map<String, Object>> selectData(@RequestBody BestKaizenRecallDto dto) {

        List<Map<String, Object>> result = service.selectData(dto);
        return result;
    }

    @GetMapping("/getById")
    public ResponseEntity<KznTlBestmst> getById(@RequestParam("keyId") String keyId) throws Exception {

        KznTlBestmst mst = service.getById(keyId);
        return ResponseEntity.ok(mst);

    }

    @DeleteMapping("/delete")
    public void deleteBestKaizen(@RequestParam("keyId") String keyId) throws Exception {
        service.deleteBestKaizen(keyId);

    }

}
