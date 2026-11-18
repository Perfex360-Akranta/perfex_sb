package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.BAL_PcsTlCycletimemstSaveDto;
import com.akranta.perfex_sb.model.BAL_PcsTlCycletimemst;

public interface BAL_PcsTlCycletimemstService {

    public BAL_PcsTlCycletimemstSaveDto createorupdateCycletime(BAL_PcsTlCycletimemstSaveDto dto);

   void deleteByKeyid(String keyid) throws Exception;
}