package com.akranta.perfex_sb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.model.GenTIControlandresponseplan;
import com.akranta.perfex_sb.service.GenTIControlandresponseplanService;

@RestController
@RequestMapping("/api/genticontrolandresponseplan")
public class GenTIControlandresponseplanController {
    @Autowired
    private GenTIControlandresponseplanService service;

    @PostMapping("/save")
    public ResponseEntity<GenTIControlandresponseplan> saveGenTIControlandresponseplan(@RequestBody GenTIControlandresponseplan model) throws Exception {
        GenTIControlandresponseplan result = service.saveGenTIControlandresponseplan(model);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<GenTIControlandresponseplan>> getAll() throws Exception {
        List<GenTIControlandresponseplan> result = service.getAll();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getById/{keyid}")
    public ResponseEntity<GenTIControlandresponseplan> getById(@PathVariable String keyid) throws Exception {
        GenTIControlandresponseplan result = service.getById(keyid);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/deleteById/{keyid}")
    public ResponseEntity<GenTIControlandresponseplan> deleteById(@PathVariable String keyid) throws Exception {
        GenTIControlandresponseplan result = service.deleteById(keyid);
        return ResponseEntity.ok(result);
    }

}
