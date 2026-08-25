package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.dto.EmpListDto;
import com.akranta.perfex_sb.dto.saveMultipleSkillDto;


public interface SkillIndexGridEntryService {
    List<Map<String, Object>> getEmpListMultiple(EmpListDto empListDto);

    saveMultipleSkillDto saveMiltipleSkillAssessment(saveMultipleSkillDto dto) throws Exception;

}
