package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.dto.ProjectPriorityDto;

public interface FIProjectPriorityService {
    public ResponseEntity<ProjectPriorityDto> save(ProjectPriorityDto projectPriorityDto);
}
