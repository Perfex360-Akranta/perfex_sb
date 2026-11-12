package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.dto.PlnjobemplinkRequest;
import com.akranta.perfex_sb.model.SheTlPlannedJobObservation;


public interface PlannedJobObservationService {  // ← renamed here

    ResponseEntity<PlnjobemplinkRequest> savePlnjobemplink(PlnjobemplinkRequest request) throws Exception;

    SheTlPlannedJobObservation findPlannedJobObservationByKeyid(String keyid) throws Exception;


    int updateActionPlan(String pojKeyid, String aplmKeyid) throws Exception;

    //List<Object[]> getPojEmployee(String pjobKeyId) throws Exception;
    //List<Map<String, Object>> getPojEmployee(String pjobKeyId) throws Exception;  
    
    List<Map<String, Object>> getPojEmployee(String pjobKeyId) throws Exception; 


    boolean deletePlannedJobObservation(String keyid) throws Exception;


    

// Delete single employee row from grid
// ✅ Service Interface
//String deleteDetail(String pjelPjobEmployeeid, String gridId) throws Exception;

    String deleteDetail(String pjelPjobEmployeeid, String gridId) throws Exception;


}