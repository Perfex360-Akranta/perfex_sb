package com.akranta.perfex_sb.controller;



import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.WorkFlowApprovalSaveDto;
import com.akranta.perfex_sb.dto.WorkFlowSaveDto;
import com.akranta.perfex_sb.model.GenTlWorkFlowMst;
import com.akranta.perfex_sb.service.WorkFlowService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/workflow")
public class WorkFlowController {

    @Autowired
    private WorkFlowService  wfService;

    @PostMapping("/approval/save")
    public ResponseEntity<String> saveApproval(@RequestBody WorkFlowApprovalSaveDto workFlowApprovalSaveDto  ) {
        return wfService.saveApproval(workFlowApprovalSaveDto);
    }

    @GetMapping("/mst/{keyid}")
    public GenTlWorkFlowMst getWorkFlowMst(@PathVariable String keyid) {
        return wfService.getWorkFlowMst(keyid);
    }

    @PostMapping("/mst/save")
    public ResponseEntity<WorkFlowSaveDto> saveWorkFlow(@RequestBody WorkFlowSaveDto workFlowSaveDto  ) {
        return wfService.saveWorkFlow(workFlowSaveDto);
    }

    @DeleteMapping("/dtl/{keyids}")
    public ResponseEntity<String> deleteWorkFlowDtl(@PathVariable String keyids) {

        List<String> ids = Arrays.asList(keyids.split(","));
        String msg = wfService.deleteWorkFlowDtl(ids);
        return ResponseEntity.ok(msg);
    }
    
    @DeleteMapping("/mst/{keyid}")
    public ResponseEntity<String> deleteWorkFlowMst(@PathVariable String keyid) {
        String msg = wfService.deleteAllWorkFlow(keyid);
        return ResponseEntity.ok(msg);
    }



   
    
    
}
