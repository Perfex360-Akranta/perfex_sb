package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.akranta.perfex_sb.dto.ActionPlanCompDto;
import com.akranta.perfex_sb.dto.ActionPlanSaveDto;
import com.akranta.perfex_sb.service.ActionPlanService;

@RestController
@RequestMapping("/api/actionplan")
public class ActionPlanController {
    
    @Autowired
    private ActionPlanService actionPlanService;


    @PostMapping
    public ResponseEntity<ActionPlanSaveDto> save(@RequestBody ActionPlanSaveDto actionPlanSaveDto) {

        return actionPlanService.save(actionPlanSaveDto);
    }

    @PostMapping("/compSave")
    public ResponseEntity<String> saveCompletion(@RequestBody List<ActionPlanCompDto> actionPlanSaveDtoList) {

        
         actionPlanService.saveCompletion(actionPlanSaveDtoList);

        return  ResponseEntity.status(HttpStatus.OK).body("Updated");
    }


    @PostMapping("/delete")
    public ResponseEntity<String> deleteActionPlan(@RequestBody Map<String, String> req) {

        
        actionPlanService.deleteActionplan(req.get("keyid"), req.get("masterKeyid"));

        return  ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }
}
