package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.PlmTlStandardsRequest;
import java.util.List;
import java.util.Map;

public interface Bal_PlmTlStandardsService {

    ResponseEntity<PlmTlStandardsRequest> savePlmTlStandards(PlmTlStandardsRequest request) throws Exception;
    ResponseEntity<Void> deletePlmTlStandards(String keyid) throws Exception;
     ResponseEntity<List<Map<String, Object>>> getCBM(String pmStandardId) throws Exception;

}
