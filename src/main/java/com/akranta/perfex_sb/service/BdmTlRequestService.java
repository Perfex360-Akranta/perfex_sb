package com.akranta.perfex_sb.service;

import java.math.BigDecimal;
import java.util.List;

import com.akranta.perfex_sb.dto.BdmTlRequestDto;
import com.akranta.perfex_sb.model.BdmTlCriticalityassessment;

public interface BdmTlRequestService {

    // Save or update Master + Details
   public List<BdmTlCriticalityassessment> saveWorksheetRequest(
            List<BdmTlRequestDto> requestList) throws Exception;
    String getFlid(String parentFlid) throws Exception;
    String getCriticalityAssessmentRemarks(String flid, String equipmentId) throws Exception;

   String getCriteriaKeyId( String flId, BigDecimal totalPoints, String equipmentId, String tradeId);

   int deleteCriteriaList(List<String> keyIds) throws Exception;
}
