package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.dto.EmpListDto;
import com.akranta.perfex_sb.dto.getSkillIndexRadarChartDto;
import com.akranta.perfex_sb.dto.saveSkillDto;


public interface SkillIndexReviewPointService {

    saveSkillDto saveSkillAssessment(saveSkillDto dto) throws Exception;

    List<Map<String, Object>> getEmpList(EmpListDto empListDto);

    List<Map<String, Object>> getSkillIndexRadarChart(getSkillIndexRadarChartDto requestDto);

}
