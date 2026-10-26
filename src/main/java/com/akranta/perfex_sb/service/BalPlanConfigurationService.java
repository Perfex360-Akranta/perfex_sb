package com.akranta.perfex_sb.service;

import java.util.List;
import com.akranta.perfex_sb.dto.NextDueDateDTO;
import com.akranta.perfex_sb.dto.BalPlanConfigurationSaveDto;  

public interface BalPlanConfigurationService {
    List<NextDueDateDTO> getNextDueDates();
    BalPlanConfigurationSaveDto savePlanConfiguration(BalPlanConfigurationSaveDto dto) throws Exception; 
}
