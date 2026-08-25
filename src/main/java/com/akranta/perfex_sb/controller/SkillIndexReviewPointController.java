package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.EmpListDto;
import com.akranta.perfex_sb.dto.getSkillIndexRadarChartDto;
import com.akranta.perfex_sb.dto.saveSkillDto;

import com.akranta.perfex_sb.service.SkillIndexReviewPointService;

@RestController
@RequestMapping("/api/skillAssessment/single")
public class SkillIndexReviewPointController {
    @Autowired
    private SkillIndexReviewPointService service;

    @PostMapping("/save")
    public ResponseEntity<saveSkillDto> saveSkillAssessment(@RequestBody saveSkillDto dto)
            throws Exception {
        saveSkillDto result = service.saveSkillAssessment(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/getEmpList")
    public ResponseEntity<List<Map<String, Object>>> getEmpList(@RequestBody EmpListDto empListDto) {
        List<Map<String, Object>> result = service.getEmpList(empListDto);
        return ResponseEntity.ok(result);

    }


    @PostMapping("/getSkillIndexRadarChart")
    public ResponseEntity<List<Map<String, Object>>> getSkillIndexRadarChart(@RequestBody getSkillIndexRadarChartDto requestDto) {
        List<Map<String, Object>> result = service.getSkillIndexRadarChart(requestDto);
        return ResponseEntity.ok(result);

    }

}
