package com.akranta.perfex_sb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.UpstreamdefectmstSaveDto;
import com.akranta.perfex_sb.model.Upstreamdefectmst;
import com.akranta.perfex_sb.service.UpstreamdefectmstService;

@RestController
@RequestMapping("/api/upstreamdefect")

public class UpstreamdefectController {

    @Autowired
    private UpstreamdefectmstService service;

    @PostMapping("/createorupdate")
    public ResponseEntity<UpstreamdefectmstSaveDto> create(
            @RequestBody UpstreamdefectmstSaveDto upstreamdefectmstSaveDto) {
        UpstreamdefectmstSaveDto result = service.createorupdateUpstreamdefectmst(upstreamdefectmstSaveDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/getelementid")
    public ResponseEntity<List<Map<String, Object>>> getElementId(
            @RequestParam(required = false) String loginflid,
            @RequestParam double loginlevel,
            @RequestParam(required = false) String loginElementid,
            @RequestParam String empId) {

        List<Map<String, Object>> result = service.getElementId(loginflid, loginlevel, loginElementid, empId);
        return ResponseEntity.ok(result);

    }

    // @GetMapping("/find/{keyid}")
    // public Upstreamdefectmst getbyUpsmid(@PathVariable String keyid, @RequestBody Upstreamdefectmst upstreamdefectmst) {
    //     return service.getbyUpsmId(keyid);
    // }

    @GetMapping("/findmst/{keyid}")
    public Upstreamdefectmst getbyUpsmid(
            @PathVariable("keyid") String keyid) {

        return service.getbyUpsmId(keyid);
    }

    @GetMapping("/finddet/{keyid}")
    public List<Map<String,Object>> getbyUpsdittkeyid(@PathVariable("keyid") String keyid)
    {
         return service.getbyUpstreamdefectkeyid(keyid);
    }

    //delete records
    @DeleteMapping("/delete/{upsmKeyId}")
    public ResponseEntity<?> deleteNewUpstreamDefect(
            @PathVariable String upsmKeyId) {

        try {
            service.deleteNewUpstreamDefect(upsmKeyId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Upstream defect deleted successfully");
            response.put("upsmKeyId", upsmKeyId);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Failed to delete upstream defect");
            error.put("error", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error);
        }
    }

    //delete detail table by delete keyid
    @DeleteMapping("deletedt/{upsdKeyid}")
    public String delete(@PathVariable String upsdKeyid) {
        service.deleteNewUpstreamDefectDetails(upsdKeyid);
        return "Deleted successfully";
    }

}
