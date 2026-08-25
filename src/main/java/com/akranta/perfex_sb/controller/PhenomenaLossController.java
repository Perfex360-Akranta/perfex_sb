package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.PhenomenaComboRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaLossGridRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaLossSaveRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaLossDeleteRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaFactoryMappingSaveRequestDto;
import com.akranta.perfex_sb.service.PhenomenaLossService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pcs/phenomenaLoss")
public class PhenomenaLossController {

    private final PhenomenaLossService service;

    public PhenomenaLossController(PhenomenaLossService service) {
        this.service = service;
    }

    @PostMapping("/grid")
    public Map<String, Object> getGrid(@RequestBody PhenomenaLossGridRequestDto dto) {
        return service.getPhenomenaGrid(dto);
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody PhenomenaLossSaveRequestDto dto) {
        return service.savePhenomenaLoss(dto);
    }

    @PostMapping("/comboText")
    public List<Map<String, Object>> comboText(@RequestBody PhenomenaComboRequestDto dto) {
        return service.getComboTextContent(dto);
    }

    @PostMapping("/factoryGrid")
    public Map<String, Object> factoryGrid(@RequestBody com.akranta.perfex_sb.dto.PhenomenaFactoryGridRequestDto dto) {
        return service.getFactoryGrid(dto);
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody PhenomenaLossDeleteRequestDto dto) {
        return service.deletePhenomenaLoss(dto.getPlpmKeyid());
    }

    @PostMapping("/mapping/save")
    public Map<String, Object> saveMapping(@RequestBody PhenomenaFactoryMappingSaveRequestDto dto) {
        return service.savePhenomenaFactoryMapping(dto);
    }

    @GetMapping("/mapping/validate")
    public Map<String, Object> validateMapping(@RequestParam("phenomenaId") String phenId,
                                               @RequestParam("factoryId") String factoryId) {
        return service.validatePhenomenaLink(phenId, factoryId);
    }
}
