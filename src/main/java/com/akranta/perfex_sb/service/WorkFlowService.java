package com.akranta.perfex_sb.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.dto.WorkFlowApprovalSaveDto;
import com.akranta.perfex_sb.dto.WorkFlowSaveDto;
import com.akranta.perfex_sb.model.GenTlWorkFlowMst;

public interface WorkFlowService {

    public ResponseEntity<String> saveApproval(WorkFlowApprovalSaveDto workFlowApprovalSaveDto);

    public GenTlWorkFlowMst getWorkFlowMst( String keyid);

    public ResponseEntity<WorkFlowSaveDto> saveWorkFlow(WorkFlowSaveDto workFlowSaveDto);

    public String deleteWorkFlowDtl( List<String> keyids);

    public String deleteAllWorkFlow( String keyid);
}
