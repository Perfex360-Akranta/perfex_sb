package com.akranta.perfex_sb.controller;



//import com.akranta.perfex_sb.dto.JhaTlAuditmstAndDtlDto;
import com.akranta.perfex_sb.dto.JhaTlAudituploadDto;

import com.akranta.perfex_sb.service.JhDmtAuditUploadService;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/jhauditupload")
public class JhDmtAuditUploadController {

    // private static final Logger logger = LoggerFactory.getLogger(JhAuditController.class);

    private final JhDmtAuditUploadService service;

    public JhDmtAuditUploadController(JhDmtAuditUploadService service) {
        this.service =  service;
    }

    // @GetMapping("/getAll")
    // public ResponseEntity<List<JhaTlAudituploadDto>> getAllAudits() {
    //     List<JhaTlAudituploadDto> audits = service.getAllAudits();
    //     return ResponseEntity.ok(audits);
    // }

       // NEW: Get audit by keyid
    @GetMapping("/{keyid}")
    public ResponseEntity<JhaTlAudituploadDto> getAuditByKeyid(@PathVariable String keyid) {
        JhaTlAudituploadDto audit = service.getAuditByKeyid(keyid);
        return ResponseEntity.ok(audit);
    }


    @PostMapping
    public ResponseEntity<JhaTlAudituploadDto> create(@RequestBody JhaTlAudituploadDto jhaTlAudituploadDto) {
        JhaTlAudituploadDto result = service.createOrUpdateAuditupload(jhaTlAudituploadDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

   
}