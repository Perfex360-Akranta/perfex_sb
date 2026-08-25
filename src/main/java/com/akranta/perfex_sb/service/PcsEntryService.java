package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.dto.DeleteLossEntryRequestDto;
import com.akranta.perfex_sb.dto.PcsLossCaptureGridRequestDto;
import com.akranta.perfex_sb.dto.PcsLossEntryGridRequestDto;

public interface PcsEntryService {

    /*
     * This will match your old flow:
     * pcsEntryDao.getLossEntryData(gridParams, commonFilter, flid)
     *
     * For Spring Boot we will return List<Map<String,Object>> (easy JSON).
     * In Eclipse ServiceApi you can still convert JSON -> List<String[]>.
     */
   // List<Map<String, Object>> getLossEntryGrid(PcsEntryDto dto);

    String getCurrentShift();
    List<Map<String, Object>> getLossEntryGrid(PcsLossEntryGridRequestDto dto);

    List<Map<String, Object>> getPcsLossCaptureGrid(PcsLossCaptureGridRequestDto dto);

    Map<String, Object> deletePcsLossEntry(DeleteLossEntryRequestDto dto);


    // OPTIONAL: if you prefer explicit params instead of dto
    // List<Map<String, Object>> getLossEntryGrid(String flid, GridParams gridParams, CommonFilter commonFilter);

}
