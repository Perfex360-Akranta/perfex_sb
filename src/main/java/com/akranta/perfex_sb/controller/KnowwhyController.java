package com.akranta.perfex_sb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.KnowWhySaveDto;
import com.akranta.perfex_sb.model.QtmTlKnowwhymst;
import com.akranta.perfex_sb.service.KnowwhyService;
//import com.akranta.perfex_sb.service.impl.KnowwhyServiceImpl;

@RestController
@RequestMapping("/api/knowWhy")
public class KnowwhyController {
    @Autowired
    private KnowwhyService service;

    private static final Logger logger = LoggerFactory.getLogger(KnowwhyController.class);

    @PostMapping("/save")
    public ResponseEntity<KnowWhySaveDto> saveKnowWhy(@RequestBody KnowWhySaveDto dto) throws Exception {

        KnowWhySaveDto result = service.saveKnowWhy(dto);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/getKnowWhy")
    public ResponseEntity<QtmTlKnowwhymst> getKnowWhy(@RequestParam("keyId") String keyid) throws Exception {

        QtmTlKnowwhymst result = service.getKnowWhy(keyid);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/saveKnowWhyApproval")
    public ResponseEntity<String> saveKnowWhyApproval(@RequestParam("appKeyid") String keyid) throws Exception {

        String result = service.saveKnowWhyApproval(keyid);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/deleteKnowWhy")
    public ResponseEntity<QtmTlKnowwhymst> DeleteKnowWhy(@RequestBody QtmTlKnowwhymst qtmTlKnowwhymst)
            throws Exception {
        logger.info("Entered into Controller {}");

        QtmTlKnowwhymst result = service.DeleteKnowWhy(qtmTlKnowwhymst);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/saveKnowWhyMst")
    public ResponseEntity<QtmTlKnowwhymst> saveKnowWhyMst(@RequestBody QtmTlKnowwhymst mst) throws Exception {

        QtmTlKnowwhymst result = service.saveKnowWhyMst(mst);
        return ResponseEntity.ok(result);

    }

}
