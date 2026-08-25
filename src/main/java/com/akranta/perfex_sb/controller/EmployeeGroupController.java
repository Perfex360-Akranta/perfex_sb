package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.EmployeeGroupDto;
import com.akranta.perfex_sb.model.GenTlMomGroupmst;
import com.akranta.perfex_sb.service.EmployeeGroupService;

@RestController
@RequestMapping("/api/employeeGroup")
public class EmployeeGroupController 
{
    @Autowired
    private EmployeeGroupService service;
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeGroupController.class);

    @PostMapping("/save")
    public ResponseEntity<EmployeeGroupDto> saveEmployeeGroup(@RequestBody EmployeeGroupDto dto) throws Exception
    {
        EmployeeGroupDto result = service.saveEmployeeGroup(dto);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/grid")
    public ResponseEntity<List<Map<String,Object>>> getEmpgroupViewGridData()
    {
        List<Map<String,Object>> result = service.getGrid();
        return ResponseEntity.ok(result);

    }

    @GetMapping("/createGrid")
    public ResponseEntity<List<Map<String,Object>>>getEmployees (@RequestParam("flid") String functional,@RequestParam(value = "keyId", required = false) String mstKeyId)
    {
        List<Map<String,Object>> result = service.createGrid(functional,mstKeyId);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/detailGrid")
    public ResponseEntity<List<Map<String,Object>>>getGroupEmployees(@RequestParam ("keyId")String mstKeyId) 
    {
        logger.info("Entered into details grid creation");
        List<Map<String,Object>>result = service.detailGrid(mstKeyId);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/deleteGrid")
    public void deleteGroupDtl(@RequestParam ("keyId") String mgrdKeyid)
    {
       service.deleteGrid(mgrdKeyid);
    }
    
    @GetMapping("/viewGrid")
    public ResponseEntity<GenTlMomGroupmst> getGroupMstByKeyId(@RequestParam ("keyId") String mgrmKeyId)
     {
       GenTlMomGroupmst result = service.viewGrid(mgrmKeyId);
        return ResponseEntity.ok(result);
    }
    
    
    
}
