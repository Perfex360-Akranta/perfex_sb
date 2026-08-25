package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.service.GenTlMachinemstService;
import com.akranta.perfex_sb.dto.GenTlMachinemstRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
//import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.GenTlMachinemst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import java.util.Map;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/machine-master")
public class GenTlMachinemstController {

    private static final Logger logger = LoggerFactory.getLogger(GenTlMachinemstController.class);
    private final GenTlMachinemstService service;

    public GenTlMachinemstController(GenTlMachinemstService service) {
        this.service = service;
    }

   
    @PostMapping("/save")
    public ResponseEntity<GenTlMachinemstRequest> saveMachineMaster(@RequestBody GenTlMachinemstRequest request) throws Exception {
        logger.info("Saving/Updating Machine Master with all related data");
        return service.saveMachineMaster(request);
    }

   @GetMapping("/operators")
    public ResponseEntity<List<Map<String, Object>>> getOperatorData(
            @RequestParam(required = false) String factId) {
        
        logger.info("Getting operator data for factory: {}", factId);
        
        List<Map<String, Object>> operators = service.getOperatorData(factId);
        
        return ResponseEntity.ok(operators);
    }

    @GetMapping("/operator-skills")
    public ResponseEntity<List<Map<String, Object>>> getOperatorSkillData() {
        logger.info("Getting operator skill data");
        
        List<Map<String, Object>> skills = service.getOperatorSkillData();
        
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/machine-teams")
    public ResponseEntity<List<Map<String, Object>>> getMaintenanceTeamDataForMachine(
            @RequestParam(required = false) String machineId) {
        
        logger.info("Getting maintenance team data for machine: {}", machineId);
        
        List<Map<String, Object>> teams = service.getMaintenanceTeamDataForMachine(machineId);
        
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/maintenance-skills")
    public ResponseEntity<List<Map<String, Object>>> getMaintenanceSkillData() {
        logger.info("Getting maintenance skill data");
        
        List<Map<String, Object>> skills = service.getMaintenanceSkillData();
        
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/data")
    public ResponseEntity<List<Map<String, Object>>> getEquipmentData(
            @RequestParam String equipmentNum) {
        
        logger.info("Getting equipment data for: {}", equipmentNum);
        
        List<Map<String, Object>> equipment = service.getEquipmentData(equipmentNum);
        
        return ResponseEntity.ok(equipment);
    }
     @GetMapping("/sub-equipment")
    public ResponseEntity<List<Map<String, Object>>> getSubEquipmentData(
            @RequestParam(required = false) String sectId,
            @RequestParam(required = false) String eqpId) {
        
        logger.info("Getting sub equipment data for sectId: {}, eqpId: {}", sectId, eqpId);
        
        List<Map<String, Object>> subEquipment = service.getSubEquipmentData(sectId, eqpId);
        
        return ResponseEntity.ok(subEquipment);
    }

    @GetMapping("/form-circle")
    public ResponseEntity<List<Map<String, Object>>> getFormCircle(
            @RequestParam String mchId) {
        
        logger.info("Getting form circle data for machine: {}", mchId);
        
        List<Map<String, Object>> circles = service.getFormCircle(mchId);
        
        return ResponseEntity.ok(circles);
    } 

   // In GenTlMachinemstController.java

@GetMapping("/equipment-master/{machineId}")
public ResponseEntity<GenTlMachinemst> getEquipmentMasterById(
        @PathVariable String machineId) {
    
    logger.info("Getting equipment master data for machine ID: {}", machineId);
    
    GenTlMachinemst equipmentMaster = service.getEquipmentMasterById(machineId);
    
    if (equipmentMaster == null) {
        return ResponseEntity.notFound().build();
    }
    
    return ResponseEntity.ok(equipmentMaster);
}

@GetMapping("/recall-operators")
public ResponseEntity<List<Map<String, Object>>> recallOperatorData(
        @RequestParam String machineId) {
    
    logger.info("Recalling operator data for machine ID: {}", machineId);
    
    List<Map<String, Object>> operators = service.recallOperatorData(machineId);
    
    return ResponseEntity.ok(operators);
}

@GetMapping("/recall-operator-skills")
public ResponseEntity<List<Map<String, Object>>> recallOperatorSkillData(
        @RequestParam String machineId) {
    
    logger.info("Recalling operator skill data for machine ID: {}", machineId);
    
    List<Map<String, Object>> operatorSkills = service.recallOperatorSkillData(machineId);
    
    return ResponseEntity.ok(operatorSkills);
}

@GetMapping("/recall-maintenance")
public ResponseEntity<List<Map<String, Object>>> recallMaintenanceData(
        @RequestParam String machineId) {
    
    logger.info("Recalling maintenance data for machine ID: {}", machineId);
    
    List<Map<String, Object>> maintenance = service.recallMaintenanceData(machineId);
    
    return ResponseEntity.ok(maintenance);
}
@GetMapping("/recall-maintenance-skills")
public ResponseEntity<List<Map<String, Object>>> recallMaintenanceSkillData(
        @RequestParam String machineId) {
    
    logger.info("Recalling maintenance skill data for machine ID: {}", machineId);
    
    List<Map<String, Object>> maintenanceSkills = service.recallMaintenanceSkillData(machineId);
    
    return ResponseEntity.ok(maintenanceSkills);
}

@GetMapping("/recall-equipment-parameters")
public ResponseEntity<List<Map<String, Object>>> recallEquipmentParameterData(
        @RequestParam String machineId) {
    
    logger.info("Recalling equipment parameter data for machine ID: {}", machineId);
    
    List<Map<String, Object>> parameters = service.recallEquipmentParameterData(machineId);
    
    return ResponseEntity.ok(parameters);
}

@DeleteMapping("/operator-skill")
public ResponseEntity<Map<String, Object>> deleteOperatorSkill(
        @RequestParam String machineId,
        @RequestParam String skillDescription) {
    
    logger.info("Deleting operator skill for machine: {} with skill: {}", machineId, skillDescription);
    
    try {
        boolean deleted = service.deleteOperatorSkill(machineId, skillDescription);
        
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", deleted);
        response.put("message", "Operator skill deleted successfully");
        
        return ResponseEntity.ok(response);
        
    } catch (ResourceNotFoundException e) {
        logger.error("Resource not found: {}", e.getMessage());
        Map<String, Object> errorResponse = new java.util.HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        
    } catch (Exception e) {
        logger.error("Error deleting operator skill: {}", e.getMessage(), e);
        Map<String, Object> errorResponse = new java.util.HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", "Failed to delete operator skill: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


}

@DeleteMapping("/maintenance-skill")
public ResponseEntity<Map<String, Object>> deleteMaintenanceSkill(
        @RequestParam String machineId,
        @RequestParam String skillDescription) {
    
    logger.info("Deleting maintenance skill for machine: {} with skill: {}", machineId, skillDescription);
    
    try {
        boolean deleted = service.deleteMaintenanceSkill(machineId, skillDescription);
        
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", deleted);
        response.put("message", "Maintenance skill deleted successfully");
        
        return ResponseEntity.ok(response);
        
    } catch (ResourceNotFoundException e) {
        logger.error("Resource not found: {}", e.getMessage());
        Map<String, Object> errorResponse = new java.util.HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        
    } catch (Exception e) {
        logger.error("Error deleting maintenance skill: {}", e.getMessage(), e);
        Map<String, Object> errorResponse = new java.util.HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", "Failed to delete maintenance skill: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

@DeleteMapping("/operator-link")
public ResponseEntity<Map<String, Object>> deleteOperatorMachineLink(
        @RequestParam String machineId,
        @RequestParam String employeeId) {
    
    logger.info("Deleting operator machine link for machine: {} and employee: {}", machineId, employeeId);
    
    try {
        boolean deleted = service.deleteOperatorMachineLink(machineId, employeeId);
        
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", deleted);
        response.put("message", "Operator machine link deleted successfully");
        
        return ResponseEntity.ok(response);
        
    } catch (ResourceNotFoundException e) {
        logger.error("Resource not found: {}", e.getMessage());
        Map<String, Object> errorResponse = new java.util.HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        
    } catch (Exception e) {
        logger.error("Error deleting operator machine link: {}", e.getMessage(), e);
        Map<String, Object> errorResponse = new java.util.HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", "Failed to delete operator machine link: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

@DeleteMapping("/maintenance-team-link")
public ResponseEntity<Map<String, Object>> deleteMaintenanceTeamMachineLink(
        @RequestParam String machineId,
        @RequestParam String maintenanceTeamId) {
    
    logger.info("Deleting maintenance team machine link for machine: {} and team: {}", machineId, maintenanceTeamId);
    
    try {
        boolean deleted = service.deleteMaintenanceTeamMachineLink(machineId, maintenanceTeamId);
        
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", deleted);
        response.put("message", "Maintenance team machine link deleted successfully");
        
        return ResponseEntity.ok(response);
        
    } catch (ResourceNotFoundException e) {
        logger.error("Resource not found: {}", e.getMessage());
        Map<String, Object> errorResponse = new java.util.HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        
    } catch (Exception e) {
        logger.error("Error deleting maintenance team machine link: {}", e.getMessage(), e);
        Map<String, Object> errorResponse = new java.util.HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", "Failed to delete maintenance team machine link: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
}
