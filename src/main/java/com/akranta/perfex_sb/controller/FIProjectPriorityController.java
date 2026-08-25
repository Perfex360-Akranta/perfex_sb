package com.akranta.perfex_sb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.akranta.perfex_sb.dto.ProjectPriorityDto;
import com.akranta.perfex_sb.service.FIProjectPriorityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/fipp")
public class FIProjectPriorityController {
    
    @Autowired
    private FIProjectPriorityService ppService;


    
    @PostMapping
    public ResponseEntity<ProjectPriorityDto> save(@RequestBody ProjectPriorityDto projectPriorityDto){
        return ppService.save(projectPriorityDto);
    }
}
