package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.GenTlMachinemstRequest;
import com.akranta.perfex_sb.model.GenTlMachinemst;

import java.util.List;
import java.util.Map;


public interface GenTlMachinemstService {
    
   
    ResponseEntity<GenTlMachinemstRequest> saveMachineMaster(GenTlMachinemstRequest request) throws Exception;
    List<Map<String, Object>> getOperatorData(String factId);
    List<Map<String, Object>> getOperatorSkillData();
    List<Map<String, Object>> getMaintenanceTeamDataForMachine(String machineId);
    List<Map<String, Object>> getMaintenanceSkillData();
    List<Map<String, Object>> getEquipmentData(String equipmentNum);
    List<Map<String, Object>> getSubEquipmentData(String sectId, String eqpId);
     List<Map<String, Object>> getFormCircle(String mchId);
GenTlMachinemst getEquipmentMasterById(String machineId);


List<Map<String, Object>> recallOperatorData(String machineId);
List<Map<String, Object>> recallOperatorSkillData(String machineId);
List<Map<String, Object>> recallMaintenanceData(String machineId);
List<Map<String, Object>> recallMaintenanceSkillData(String machineId);
List<Map<String, Object>> recallEquipmentParameterData(String machineId);

boolean deleteOperatorSkill(String machineId, String skillDescription) throws Exception;

boolean deleteMaintenanceSkill(String machineId, String skillDescription) throws Exception;
boolean deleteOperatorMachineLink(String machineId, String employeeId) throws Exception;

boolean deleteMaintenanceTeamMachineLink(String machineId, String maintenanceTeamId) throws Exception;
    
    
   
}
