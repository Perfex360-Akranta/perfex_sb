package com.akranta.perfex_sb.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.dto.ActionPlanCompDto;
import com.akranta.perfex_sb.dto.ActionPlanSaveDto;

public interface ActionPlanService {
    ResponseEntity<ActionPlanSaveDto>  save(ActionPlanSaveDto actionPlanSaveDto);
    public void saveCompletion(List<ActionPlanCompDto> actionPlanCompDto);
    public void deleteActionplan(String keyid , String masterKeyid);
}
