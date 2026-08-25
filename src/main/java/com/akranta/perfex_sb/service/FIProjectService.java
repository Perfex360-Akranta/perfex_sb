package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.dto.ProjectCreationDto;
import com.akranta.perfex_sb.dto.ProjectKaizenSaveDto;
import com.akranta.perfex_sb.dto.ProjectKpiSaveDto;
import com.akranta.perfex_sb.dto.ProjectMilestoneSaveDto;
import com.akranta.perfex_sb.dto.ProjectResourceSaveDto;
import com.akranta.perfex_sb.dto.ProjectResponseDto;
import com.akranta.perfex_sb.dto.ProjectUpdationDto;
import com.akranta.perfex_sb.model.KznTlProjMilestoneMst;
import com.akranta.perfex_sb.model.KznTlProjectChecklistLink;
import com.akranta.perfex_sb.model.KznTlProjectKaizenLink;
import com.akranta.perfex_sb.model.KznTlProjectKpiLink;
import com.akranta.perfex_sb.model.KznTlProjectResourceLink;
import com.akranta.perfex_sb.model.KznTlProjectcreationmst;

public interface FIProjectService {
    public ResponseEntity<ProjectCreationDto> create(ProjectCreationDto projectCreationDto);
    public ProjectResponseDto getProjectByKeyid( String keyid);
    public ResponseEntity<KznTlProjectcreationmst> update(ProjectUpdationDto projectUpdationDto);
    public ResponseEntity<KznTlProjectcreationmst> updateMAICStatus(String keyid , String stage);
    public ResponseEntity<String> updateFIPApprovals(String rolename, String refId,
			String nxtrole, String trnscode, Character lstlvl, String flId,
			String roleid,String workstatus);
    public String getWorkFlowStaus(String kznKeyId, String refType,String transCode,String isUpdate,String wfStatus);
    public List<Map<String, Object>> getChecklistByStage( String keyid,String Stage);
    public List<Map<String, Object>> getProjectKpi(String keyid);
    public List<Map<String, Object>> getProjectKaizen( String flid,String masterId);
    public List<Map<String, Object>> getProjectMilestone(String masterId);
    public KznTlProjMilestoneMst getRecallMilestone(String keyid);
    public List<Map<String, Object>> getAllMilestones(String stage,String keyid);
    public ResponseEntity<ProjectMilestoneSaveDto> saveMilestone(ProjectMilestoneSaveDto projectMilestoneSaveDto);
    public ResponseEntity<String> deleteMilestoneDetail(String Keyid);
    public ResponseEntity<String> deleteMilestone(String Keyid);
    public ResponseEntity<List<KznTlProjectKpiLink>> saveKpi(List<ProjectKpiSaveDto> projectKpiSaveDtoList);
    public ResponseEntity<List<KznTlProjectResourceLink>> saveResource(List<ProjectResourceSaveDto> projectResourceSaveDtoList);
    public ResponseEntity<String> deleteResource(String Keyid);
    public ResponseEntity<List<KznTlProjectChecklistLink>> saveChecklist(List<KznTlProjectChecklistLink> checkListLinks);
    public ResponseEntity<List<KznTlProjectKaizenLink>> saveKaizen(List<ProjectKaizenSaveDto> projectKaizenSaveDtoList);
}
