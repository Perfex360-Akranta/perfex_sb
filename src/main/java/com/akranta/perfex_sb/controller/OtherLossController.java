package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.OtherLossEntryDto;
import com.akranta.perfex_sb.dto.OtherLossUploadRequest;
import com.akranta.perfex_sb.service.OtherLossService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/pcs/otherLoss")
public class OtherLossController {

    private final OtherLossService service;

    public OtherLossController(OtherLossService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody OtherLossEntryDto dto) {
        return service.saveOrUpdate(dto);
    }

    @PutMapping("/lossValues")
    public Map<String, Object> updateLossValues(@RequestBody List<Map<String, Object>> list) {
        List<OtherLossEntryDto> items = new ArrayList<>();
        if (list != null) {
            for (Map<String, Object> row : list) {
                if (row == null) continue;
                String keyId = getString(row, "olseKeyid");
                if (keyId == null || keyId.isBlank()) {
                    keyId = getString(row, "txtOlseKeyid"); // legacy field name from JSP
                }
                BigDecimal lossVal = parseBigDecimal(row.get("olseLossvalue"));
                if (lossVal == null) {
                    // Sometimes the value arrives as a plain string (or "{}"); try string fallback
                    String asString = getString(row, "olseLossvalue");
                    if (asString != null && !asString.isBlank() && !asString.trim().equals("{}")) {
                        lossVal = parseBigDecimal(asString);
                    }
                }
                if (lossVal == null) {
                    // Legacy name
                    String asString = getString(row, "txtOlseLossvalue");
                    if (asString != null && !asString.isBlank() && !asString.trim().equals("{}")) {
                        lossVal = parseBigDecimal(asString);
                    } else {
                        lossVal = parseBigDecimal(row.get("txtOlseLossvalue"));
                    }
                }
                if (keyId == null || keyId.isBlank() || lossVal == null) continue;
                OtherLossEntryDto dto = new OtherLossEntryDto();
                dto.setOlseKeyid(keyId);
                dto.setOlseLossvalue(lossVal);
                items.add(dto);
            }
        }
        service.updateLossValues(items);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Updated successfully");
        resp.put("rowsUpdated", items.size());
        return resp;
    }

    @DeleteMapping("/{keyId}")
    public void delete(@PathVariable String keyId) {
        service.delete(keyId);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> upload(@RequestPart("file") MultipartFile file,
                                      @RequestPart("flid") String flid,
                                      @RequestPart("elementId") String elementId,
                                      @RequestPart("lossId") String lossId,
                                      @RequestPart("createdBy") String createdBy) {
        OtherLossUploadRequest req = new OtherLossUploadRequest();
        req.setFile(file);
        req.setFlid(flid);
        req.setElementId(elementId);
        req.setLossId(lossId);
        req.setCreatedBy(createdBy);
        return service.uploadExcel(req);
    }

    private String getString(Map<String, Object> map, String key) {
        if (map == null || key == null) return null;
        Object v = map.get(key);
        return v == null ? null : v.toString();
    }

    private BigDecimal parseBigDecimal(Object v) {
        if (v == null) return null;
        // If the value comes as a map (e.g., {text: "22"} or {value: "22"}) try to unwrap it
        if (v instanceof Map<?, ?> map) {
            Object inner = map.get("value");
            if (inner == null) inner = map.get("text");
            if (inner == null && map.size() == 1) {
                inner = map.values().stream().findFirst().orElse(null);
            }
            if (inner != null) return parseBigDecimal(inner);
            return null;
        }
        try {
            return new BigDecimal(v.toString().trim());
        } catch (Exception e) {
            return null;
        }
    }
}
