package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.WhyWhyRequest;
import com.akranta.perfex_sb.model.BdmTlWhywhymst;
import java.util.List;
import java.util.Map;

public interface WhyWhyAnalysisService {
    
    /**
     * Save or Update WhyWhy (handles both insert and update in single method)
     * If keyid is null/empty -> INSERT
     * If keyid exists -> UPDATE
     */
    ResponseEntity<WhyWhyRequest> saveWhyWhy(WhyWhyRequest request) throws Exception;
    
   
    ResponseEntity<WhyWhyRequest> saveEffectiveness(WhyWhyRequest request) throws Exception;
    
    /**
     * Get complete WhyWhy data including all relationships
     */
    WhyWhyRequest getCompleteWhyWhyData(String masterKeyid);
    BdmTlWhywhymst updateApproval(BdmTlWhywhymst whywhymst) throws Exception;
    BdmTlWhywhymst updateApprovalAI(BdmTlWhywhymst whywhymst) throws Exception;
     List<Map<String, Object>> getSpentTime(String keyId) throws Exception;
     List<Map<String, Object>> getYyDoneby(String masterKeyid) throws Exception;
     List<Map<String, Object>> getProbAttby(String masterKeyid) throws Exception;
     List<Map<String, Object>> getAnalysis(String masdetkeyid) throws Exception;
boolean deleteWhyWhyDetail(String detailId) throws Exception;
boolean deleteProblemAttBy(String keyId) throws Exception;
boolean deleteYyDoneby(String keyId) throws Exception;
List<Map<String, Object>> getRootCause(String openMode) throws Exception;

List<Map<String, Object>> getCounterMeasure(String yyno) throws Exception;
}