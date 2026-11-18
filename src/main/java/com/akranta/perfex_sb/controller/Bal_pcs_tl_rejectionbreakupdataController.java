package com.akranta.perfex_sb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.model.Bal_pcs_tl_rejectionbreakupdata;
import com.akranta.perfex_sb.service.Bal_pcs_tl_rejectionbreakupdataService;

@RestController 
@RequestMapping("/api/rejection")
public class Bal_pcs_tl_rejectionbreakupdataController {

    @Autowired
    private Bal_pcs_tl_rejectionbreakupdataService service;

    @PostMapping("/saveorupdate")
    public ResponseEntity<List<Bal_pcs_tl_rejectionbreakupdata>> saveOrUpdate(
            @RequestParam("pprdKeyid") String pprdKeyid,
            @RequestParam("createdby") String createdby,
            @RequestBody List<Bal_pcs_tl_rejectionbreakupdata> rows) {

        List<Bal_pcs_tl_rejectionbreakupdata> result = service.saveOrUpdate(pprdKeyid, createdby, rows);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/delete/{keyid}")
    public ResponseEntity<String> delete(@PathVariable("keyid") String keyid) {
        service.deleteByKeyid(keyid);
        return ResponseEntity.ok("Deleted successfully");
    }



}
