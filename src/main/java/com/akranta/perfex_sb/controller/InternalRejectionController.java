package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.internalRejectionMstDtlDto;
import com.akranta.perfex_sb.model.QtmTlIntrejectiondtl;
import com.akranta.perfex_sb.model.QtmTlIntrejectionmst;
import com.akranta.perfex_sb.service.InternalRejectionService;

@RestController
@RequestMapping("/api/internalRejection")
public class InternalRejectionController

{
    @Autowired
    private InternalRejectionService service;

    // private static final Logger logger =
    // LoggerFactory.getLogger(InternalRejectionController.class);

    @PostMapping("/save")
    public ResponseEntity<QtmTlIntrejectiondtl> saveInternalRejection(@RequestBody internalRejectionMstDtlDto dto)
            throws Exception {

        QtmTlIntrejectiondtl dtoResult = service.saveInternalRejection(dto);
        return ResponseEntity.ok(dtoResult);

    }

    @GetMapping("/grid")
    public List<Map<String, Object>> getInternalRejectionMstGrid(@RequestParam("flid") String flid) {

        return service.getInternalRejectionModificationGrid(flid);

    }

    @GetMapping("/getById")
    public QtmTlIntrejectionmst getInternalRejectionMasterData(@RequestParam("id") String id)
            throws Exception {

        return service.getInternalRejectionMasterData(id);

    }

}
