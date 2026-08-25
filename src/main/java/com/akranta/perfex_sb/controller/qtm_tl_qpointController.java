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

import com.akranta.perfex_sb.model.QpointModel;
import com.akranta.perfex_sb.service.qtm_tl_qpointService;
import com.akranta.perfex_sb.dto.qtm_tl_qpointDto;

@RestController
@RequestMapping("/api/qtm_tl_qpoint")
public class qtm_tl_qpointController {

    @Autowired
    private qtm_tl_qpointService service;

    @GetMapping("/all")
    public List<QpointModel> getAll() {
        return service.getAll();
    }

    @GetMapping("/{keyid}")
    public QpointModel getById(@PathVariable String keyid) {
        return service.getById(keyid);
    }

    @DeleteMapping("/{keyid}")
    public QpointModel deleteById(@PathVariable String keyid) {
        return service.deleteById(keyid);
    }

    @PostMapping("/save")
    public ResponseEntity<qtm_tl_qpointDto> save(@RequestBody qtm_tl_qpointDto dto) throws Exception {
        qtm_tl_qpointDto result = service.saveQpoint(dto);
        return ResponseEntity.ok(result);

    }

}
