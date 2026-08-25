package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.dto.PhenomenaLossGridRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaLossSaveRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaComboRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaFactoryGridRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaFactoryMappingSaveRequestDto;

public interface PhenomenaLossService {
    Map<String, Object> getPhenomenaGrid(PhenomenaLossGridRequestDto dto);
    Map<String, Object> savePhenomenaLoss(PhenomenaLossSaveRequestDto dto);
    List<Map<String, Object>> getComboTextContent(PhenomenaComboRequestDto dto);
    Map<String, Object> getFactoryGrid(PhenomenaFactoryGridRequestDto dto);
    Map<String, Object> deletePhenomenaLoss(String plpmKeyid);
    Map<String, Object> savePhenomenaFactoryMapping(PhenomenaFactoryMappingSaveRequestDto dto);
    Map<String, Object> validatePhenomenaLink(String phenId, String factoryId);
}
