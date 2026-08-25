package com.akranta.perfex_sb.controller;

import java.util.List;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.akranta.perfex_sb.dto.updateThemeCatageryDto;
import com.akranta.perfex_sb.model.GenTlWorkFlowInfo;
import com.akranta.perfex_sb.model.KznTlMst;
import com.akranta.perfex_sb.repository.KznTlMstRepository;
import com.akranta.perfex_sb.service.KznTlMstService;

@RestController
@RequestMapping("/api/kaizen")
public class KznTlMstController {

    @Autowired
    private KznTlMstService kznTlMstService;

    @Autowired
    private KznTlMstRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(KznTlMstController.class);

    @PostMapping
    public ResponseEntity<KznTlMst> create(@RequestBody KznTlMst kznTlMst) {
        return kznTlMstService.create(kznTlMst);
    }

    @GetMapping
    public List<KznTlMst> findAll() {

        return repository.findAll();
    }
    // @GetMapping("path")
    // public String findKeyid(@RequestParam String keyid) {

    // return repository.findKeyid(keyid);
    // }

    // @GetMapping("/grid")
    // public List<Map<String, Object>> findAll(@RequestParam("flid") String flid) {

    // return kznTlMstService.findAll(flid);

    // }

    @GetMapping("/{keyId}")

    public ResponseEntity<KznTlMst> getById(@PathVariable("keyId") String keyId) {

        return ResponseEntity.ok(kznTlMstService.getByKeyid(keyId));
    }

    @GetMapping("/find/{keyId}")

    public String findKeyid(@PathVariable("keyId") String keyId) {
        logger.info("Key Id {}", keyId);
        return repository.findKeyid(keyId);
    }
    // @PutMapping("/{keyid}")
    // public ResponseEntity<KznTlMst> update(
    // @PathVariable String keyid,
    // @RequestBody KznTlMst data) {

    // return ResponseEntity.ok(kznTlMstService.updateKaizen(keyid, data));
    // }

    @PutMapping("/update")
    // @PostMapping("/update")
    public ResponseEntity<KznTlMst> update(@RequestBody KznTlMst kznTlMst) {

        KznTlMst updateEntity = kznTlMstService.updateKaizen(kznTlMst);

        return ResponseEntity.status(HttpStatus.OK).body(updateEntity);

    }

    @PostMapping("/simplAppSave")
    public ResponseEntity<List<GenTlWorkFlowInfo>> saveSimpliAppval(
            @RequestBody List<GenTlWorkFlowInfo> wrkFlw) {

        List<GenTlWorkFlowInfo> saved = kznTlMstService.saveSimpliAppval(wrkFlw);

        return ResponseEntity.ok(saved);
    }

    @PostMapping("/updateThemeCatogery")
    public ResponseEntity<List<updateThemeCatageryDto>> updateThemeCatogery(
            @RequestBody List<updateThemeCatageryDto> updateDtos) {
        List<updateThemeCatageryDto> result = kznTlMstService.updateThemeCatogery(updateDtos);
        return ResponseEntity.ok(result);

    }

}
