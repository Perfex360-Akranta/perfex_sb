package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.BAL_BdmTlWhywhyRequest;
import com.akranta.perfex_sb.model.BAL_BdmTlWhywhymst;

import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface BAL_BdmTlWhywhyService {

   
    ResponseEntity<BAL_BdmTlWhywhyRequest> saveWhyWhy(BAL_BdmTlWhywhyRequest request) throws Exception;
    List<String> getYYRefKeyid(String refDocId) throws Exception;
    BAL_BdmTlWhywhymst selectMasKeyid(String keyid) throws Exception;
    List<Map<String, Object>> getRootCause(String openMode) throws Exception;
    boolean deleteWhyWhyDetail(String detailId) throws Exception;
    List<Map<String, Object>> getAnalysis(String masdetkeyid) throws Exception;
}
