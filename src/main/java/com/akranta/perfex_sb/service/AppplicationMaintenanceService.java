package com.akranta.perfex_sb.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.dto.AppMainAbnDto;
import com.akranta.perfex_sb.dto.AppMainActPlanDto;

public interface AppplicationMaintenanceService {

    List<Map<String, Object>> getEmployeeList(String keyId);

    public void updateEmployeUserInactive(String keyId, String remarks) throws Exception;

    public List<Map<String, Object>> getEmployeeLocation(String keyId, String location);

    public void updateEmployeeLocation(String keyId, String location) throws Exception;

    public void updateEmployeeActive(String keyId, String remarks, String validTill) throws Exception;

    public String getFlid(String keyId);

    void deleteAbnormality(String abnKeyId);

    void deleteSuggestion(String kznKeyId);

    void deleteKaizen(String kznKeyId);

    public void deleteActionPlan(String actPlanKeyId);

    public void deleteWhyWhy(String whywhyKeyId);

    public void deleteTrgCalendar(String whywhyKeyId);

    void deleteLoss(String lossKeyId);

    void abnormalityClosure(AppMainAbnDto dto);
    
    void abnormalityClosure(List<AppMainAbnDto> dtoList);

    void actionPlanClosure(AppMainActPlanDto dto);

    void kaizenDateChange(String kznKeyId, LocalDateTime kznDate);

    void fipProjectDateChange(String fipKeyId, LocalDateTime kznDate);

}
