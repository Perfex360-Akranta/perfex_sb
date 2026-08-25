package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.EmpListDto;
import com.akranta.perfex_sb.dto.saveMultipleSkillDto;

import com.akranta.perfex_sb.service.SkillIndexGridEntryService;

@RestController
@RequestMapping("/api/skillAssessment/multiple")
public class SkillIndexGridEntryController {
    @Autowired
    private SkillIndexGridEntryService service;

    @PostMapping("/getEmpList")
    public ResponseEntity<List<Map<String, Object>>> getEmpList(@RequestBody EmpListDto empListDto) {
        List<Map<String, Object>> result = service.getEmpListMultiple(empListDto);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/save")
    public ResponseEntity<saveMultipleSkillDto> saveMiltipleSkillAssessment(@RequestBody saveMultipleSkillDto dto)
            throws Exception {
        saveMultipleSkillDto result = service.saveMiltipleSkillAssessment(dto);
        return ResponseEntity.ok(result);
    }

}
