package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.FishboneChildSaveRequest;
import com.akranta.perfex_sb.dto.FishboneMasterSaveRequest;
import com.akranta.perfex_sb.dto.FishboneTreeRequest;
import com.akranta.perfex_sb.service.FishboneService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/fishbone")
public class FishboneController {

    private final FishboneService service;

    public FishboneController(FishboneService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public Map<String, Object> saveMaster(@RequestBody FishboneMasterSaveRequest request) {
        return service.saveOrUpdateMaster(request);
    }

    @PostMapping("/child/save")
    public Map<String, Object> saveChild(@RequestBody FishboneChildSaveRequest request) {
        return service.saveOrUpdateChild(request);
    }

    @PostMapping("/tree")
    public java.util.List<java.util.Map<String, Object>> getTree(@RequestBody FishboneTreeRequest request) {
        return service.getFishboneTree(request);
    }

    @PostMapping("/searchNode")
    public java.util.List<String> searchNode(@RequestBody java.util.Map<String, String> payload) {
        String searchNode = payload.get("searchNode");
        String originalId = payload.get("originalId");
        return service.searchNode(searchNode, originalId);
    }

    @PostMapping("/child/delete")
    public Map<String, Object> deleteChild(@RequestBody java.util.Map<String, String> payload) {
        String detailKeyId = payload.get("detailKeyId");
        return service.deleteChild(detailKeyId);
    }

    @PostMapping("/master/get")
    public com.akranta.perfex_sb.dto.FishboneMasterSaveRequest getMaster(@RequestBody java.util.Map<String, String> payload) {
        String keyId = payload.get("keyId");
        String refDocType = payload.get("refDocType");
        String refDocId = payload.get("refDocId");
        return service.getMaster(keyId, refDocType, refDocId);
    }

    @PostMapping("/report/grid")
    public java.util.List<java.util.Map<String, Object>> getReportGrid(@RequestBody java.util.Map<String, String> payload) {
        String keyId = payload.get("keyId");
        return service.getReportGrid(keyId);
    }
}
