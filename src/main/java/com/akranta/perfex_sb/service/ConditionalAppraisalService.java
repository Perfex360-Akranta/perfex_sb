package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.ConditionalAppraisalRequest;
//import com.akranta.perfex_sb.model.PlmTlConditionalappraisalmstentry;

import java.util.Map;
import java.util.List;

public interface ConditionalAppraisalService {
    
    ResponseEntity<ConditionalAppraisalRequest> saveConditionalAppraisal(ConditionalAppraisalRequest request) throws Exception;
    
    ConditionalAppraisalRequest getCompleteConditionalAppraisalData(String masterKeyid);
    
    List<Map<String, Object>> recallConditionalAppraisalDetail(String keyid);
    
   
    ResponseEntity<ConditionalAppraisalRequest> saveConditionalAppraisalEntry(ConditionalAppraisalRequest request) throws Exception;
   Long checkUpdate(String flid, String date, String forGrid, String cdapkeyid) throws Exception;
    boolean deleteConditionalAppraisalDetail(String keyid) throws Exception;

    boolean deleteConditionalAppraisalMaster(String masterKeyid) throws Exception;
}