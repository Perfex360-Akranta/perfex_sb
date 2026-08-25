package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.WwblaRequest;

import java.util.List;
import java.util.Map;


public interface WwblaService {
    
    
    ResponseEntity<WwblaRequest> saveWwbla(WwblaRequest request) throws Exception;

    
    ResponseEntity<WwblaRequest> saveWwblaDetail(WwblaRequest request) throws Exception;

    List<Map<String, Object>> recallWwblaDetail(String keyid);

    List<Map<String, Object>> getWwblaValues(
            String masterKeyid,
            String parentId,
            String detailKeyid
    ) throws Exception;

    boolean deleteWwblaChildEntry(String keyid) throws Exception;

    
}