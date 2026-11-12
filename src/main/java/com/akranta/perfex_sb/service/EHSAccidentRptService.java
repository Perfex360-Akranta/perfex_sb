package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.dto.AssignResponsibilityDto;
import com.akranta.perfex_sb.dto.IncidentApprovalDto;
import com.akranta.perfex_sb.dto.IncidentClosureDto;
import com.akranta.perfex_sb.dto.IncidentVerifyDto;
import com.akranta.perfex_sb.dto.SheTlIncidentEmployeenDto;
import com.akranta.perfex_sb.dto.UpdateIncidentDto;
import com.akranta.perfex_sb.model.SheTlIncidentEmployeenew;
import com.akranta.perfex_sb.model.SheTlIncidentmstnew;

public interface EHSAccidentRptService {

    SheTlIncidentmstnew save(SheTlIncidentmstnew SheTlIncidentmstnew) throws Exception;
    SheTlIncidentEmployeenew save(SheTlIncidentEmployeenDto SheTlIncidentEmployeenDto) throws Exception;
    
    SheTlIncidentmstnew getSheTlIncidentmstnewById(String KeyId);

    int updateIncident(UpdateIncidentDto incidentDto);

    int assignResponsibility(AssignResponsibilityDto dto);

    int updateIncidentClosure(IncidentClosureDto dto);

    List<Map<String, Object>> getActionPlanIds(String keyid);
   
   
    String DeleteIncident(String masterRefId);

    String verifyIncident(IncidentVerifyDto incidentVerifyDto);
   

    boolean approveIncident(IncidentApprovalDto incidentApprovalDtodto);

} 
