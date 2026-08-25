package com.akranta.perfex_sb.controller;

//import com.akranta.perfex_sb.dto.PcsEntryDto;
import com.akranta.perfex_sb.dto.PcsLossCaptureGridRequestDto;
import com.akranta.perfex_sb.dto.PcsLossEntryGridRequestDto;
import com.akranta.perfex_sb.dto.DeleteLossEntryRequestDto;
import com.akranta.perfex_sb.service.PcsEntryService;

//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pcs")
public class PcsEntryController {

    private final PcsEntryService pcsEntryService;

    public PcsEntryController(PcsEntryService pcsEntryService) {
        this.pcsEntryService = pcsEntryService;
    }

    /*
     * This endpoint is meant to replace:
     * pcsEntryServiceApi.getLossEntryData(gridParams, commonFilter, flid)
     *
     * Request body can be your existing CommonFilter + GridParams wrapper DTO.
     * For now we keep it simple: accept a DTO with needed params (you can expand later).
     */

    // @PostMapping("/lossEntry/grid")
    // public ResponseEntity<List<Map<String, Object>>> getLossEntryGrid(@RequestBody PcsEntryDto dto) {

    //     // NOTE:
    //     // You can change return type later to List<String[]> if you want to match your old Eclipse flow.
    //     // In Spring Boot, returning List<Map<String,Object>> is easiest for JSON grid.

    //     List<Map<String, Object>> result = service.getLossEntryGrid(dto);

    //     return ResponseEntity.ok(result);
    // }
    

    @PostMapping("/lossEntryGrid")
public List<Map<String,Object>> lossEntryGrid(@RequestBody PcsLossEntryGridRequestDto dto) {
    return pcsEntryService.getLossEntryGrid(dto);
}

    @PostMapping("/pcsLossCaptureGrid")
    public List<Map<String, Object>> pcsLossCaptureGrid(@RequestBody PcsLossCaptureGridRequestDto dto) {
        return pcsEntryService.getPcsLossCaptureGrid(dto);
    }

    @PostMapping("/pcsLossEntry/delete")
    public Map<String, Object> deletePcsLossEntry(@RequestBody DeleteLossEntryRequestDto dto) {
        return pcsEntryService.deletePcsLossEntry(dto);
    }

}
