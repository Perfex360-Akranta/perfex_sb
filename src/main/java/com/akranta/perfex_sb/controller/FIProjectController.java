package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
import com.akranta.perfex_sb.service.FIProjectService;











@RestController
@RequestMapping("/api/fip")
public class FIProjectController {
    
    @Autowired
    private FIProjectService  fipService;


    @GetMapping("/{keyid}")
    public ProjectResponseDto getProjectByKeyid( @PathVariable String keyid) {
        return fipService.getProjectByKeyid(keyid);
    }
    
   @PostMapping("/resourse/save")
    public ResponseEntity<List<KznTlProjectResourceLink>> saveResource(@RequestBody  List<ProjectResourceSaveDto> projectResourceSaveDtoList) {
        return fipService.saveResource(projectResourceSaveDtoList);
    }

    @PostMapping("/checklist/save")
    public ResponseEntity<List<KznTlProjectChecklistLink>> saveChecklist(@RequestBody List<KznTlProjectChecklistLink> checkListLinks) {
        
        return fipService.saveChecklist(checkListLinks);
    }
    

    @PostMapping
    public ResponseEntity<ProjectCreationDto> create(@RequestBody ProjectCreationDto projectCreationDto) {
        return fipService.create(projectCreationDto);
    }



    @PostMapping("/update")
    public ResponseEntity<KznTlProjectcreationmst> update(@RequestBody ProjectUpdationDto projectUpdationDto) {
        return fipService.update(projectUpdationDto);
    }

    @PostMapping("/update/maic")
    public ResponseEntity<KznTlProjectcreationmst> updateMAICStatus(@RequestBody Map<String, String> req) {
        return fipService.updateMAICStatus(req.get("keyid"), req.get("stage"));
    }

    @PostMapping("/update/fipapproval")
    public ResponseEntity<String> updateFIPApprovals( @RequestBody Map<String, String> req ){

        String rolename = req.get("rolename");
        String refId = req.get("refId");
		String nxtrole = req.get("nxtrole");
        String trnscode = req.get("trnscode");
        Character lstlvl = req.get("lstlvl").charAt(0);
        String flId = req.get("flId");
		String roleid = req.get("roleid");
        String workstatus = req.get("status");
        return fipService.updateFIPApprovals(rolename,refId,nxtrole,trnscode,lstlvl, flId,roleid,workstatus);

    }

    // public String getWorkFlowStaus(String kznKeyId, String refType,String transCode,String isUpdate,String wfStatus)

    @PostMapping("/update/fipworkflowstatus")
    public String getWorkFlowStaus( @RequestBody Map<String, String> req ){

        
        String kznKeyId = req.get("kznKeyId");
		String refType = req.get("refType");
        String transCode = req.get("transCode");
        String isUpdate = req.get("isUpdate");
		String wfStatus = req.get("wfStatus");
        
        return fipService.getWorkFlowStaus(kznKeyId,refType,transCode,isUpdate,wfStatus);

    }
    

    @PostMapping("/checklist")
    public List<Map<String, Object>> getChecklistByStage(@RequestBody Map<String, String> req){
        return fipService.getChecklistByStage(req.get("projectId"), req.get("stage"));
    }

    
    @PostMapping("/kaizen")
    public List<Map<String, Object>> getProjectKaizen(@RequestBody Map<String, String> req){
        return fipService.getProjectKaizen(req.get("flid"), req.get("projectId"));
    }
    

    @GetMapping("/kpi/{keyid}")
    public List<Map<String, Object>> getProjectKpi(@PathVariable String keyid) {
        return fipService.getProjectKpi(keyid);
    }

    @PostMapping("/kpi/save")
   public ResponseEntity<List<KznTlProjectKpiLink>> saveKpi(@RequestBody List<ProjectKpiSaveDto> projectKpiSaveDtoList){    
        return fipService.saveKpi(projectKpiSaveDtoList);
    }


    @DeleteMapping("/resourse/{Keyid}")
    public ResponseEntity<String> deleteResource(@PathVariable String Keyid) {
        return fipService.deleteResource(Keyid);
    }

    @DeleteMapping("/milestone/dtl/{Keyid}")
    public ResponseEntity<String> deleteMilestoneDetail(@PathVariable String Keyid) {
        return fipService.deleteMilestoneDetail(Keyid);
    }

    @DeleteMapping("/milestone/mst/{Keyid}")
    public ResponseEntity<String> deleteMilestone(@PathVariable String Keyid) {
        return fipService.deleteMilestone(Keyid);
    }

    @PostMapping("/kaizen/save")
    public ResponseEntity<List<KznTlProjectKaizenLink>> saveKaizen(@RequestBody List<ProjectKaizenSaveDto> projectKaizenSaveDtoList) {
        return fipService.saveKaizen(projectKaizenSaveDtoList);
    }

    @GetMapping("/milestone/{Keyid}")
    public List<Map<String, Object>> getProjectMilestone(@PathVariable String Keyid) {
        return fipService.getProjectMilestone(Keyid);
    }

    @GetMapping("/milestone/recall/{Keyid}")
    public KznTlProjMilestoneMst getRecallMilestone(@PathVariable String Keyid) {
        return fipService.getRecallMilestone(Keyid);
    }

    @PostMapping("/milestone/all")
    public List<Map<String, Object>> getAllMilestones(@RequestBody Map<String, String> req) {   
        return fipService.getAllMilestones(req.get("stage"), req.get("keyid"));
    }

    @PostMapping("/milestone/save")
     public ResponseEntity<ProjectMilestoneSaveDto> saveMilestone(@RequestBody ProjectMilestoneSaveDto projectMilestoneSaveDto){
        
        return fipService.saveMilestone(projectMilestoneSaveDto);
    }
    
    
    
    
    

}
