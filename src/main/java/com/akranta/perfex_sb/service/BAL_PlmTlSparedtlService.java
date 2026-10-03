package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.BAL_PlmTlSparedtlRequest;

public interface BAL_PlmTlSparedtlService {

    ResponseEntity<BAL_PlmTlSparedtlRequest> saveSparesPickup(BAL_PlmTlSparedtlRequest request) throws Exception;

    // NEW
    ResponseEntity<List<Map<String, Object>>> getSprPickup(String standardId) throws Exception;
}