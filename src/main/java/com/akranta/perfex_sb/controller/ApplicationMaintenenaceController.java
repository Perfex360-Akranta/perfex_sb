package com.akranta.perfex_sb.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.AppMainAbnDto;
import com.akranta.perfex_sb.dto.AppMainActPlanDto;
import com.akranta.perfex_sb.service.AppplicationMaintenanceService;

@RestController
@RequestMapping("/api/appMaintenance")
public class ApplicationMaintenenaceController {

    @Autowired
    private AppplicationMaintenanceService service;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationMaintenenaceController.class);

    @GetMapping("/getEmployeeList")
    public ResponseEntity<List<Map<String, Object>>> getEmployeeList(
            @RequestParam(value = "keyId", required = false) String keyId) {

        logger.info("Enterent into AP");
        List<Map<String, Object>> result = service.getEmployeeList(keyId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/updateUserEmp")
    public void updateUserAndEmployee(
            @RequestParam(value = "keyId", required = false) String keyId,
            @RequestParam(value = "remarks", required = false) String remarks) throws Exception {

        logger.info("Enterent into AP update");
        service.updateEmployeUserInactive(keyId, remarks);

    }

    @GetMapping("/getEmpLocation")
    public ResponseEntity<List<Map<String, Object>>> getEmpLocation(
            @RequestParam(value = "keyId", required = false) String keyId,
            @RequestParam(value = "location", required = false) String location) throws Exception {

        logger.info("Enterent into AP update");
        List<Map<String, Object>> result = service.getEmployeeLocation(keyId, location);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/locationTransfer")
    public void locationTransfer(
            @RequestParam(value = "keyId", required = false) String keyId,
            @RequestParam(value = "location", required = false) String location) throws Exception {

        logger.info("Enterent into AP update");
        service.updateEmployeeLocation(keyId, location);

    }

    @GetMapping("/employeeActive")
    public void activateEmployee(
            @RequestParam(value = "keyId", required = false) String keyId,
            @RequestParam(value = "ValidTill", required = false) String ValidTill,
            @RequestParam(value = "remarks", required = false) String remarks) throws Exception {

        logger.info("Enterent into AP update");
        service.updateEmployeeActive(keyId, remarks, ValidTill);

    }

    // ------------------------------ABNORMALITY
    // DELETE---------------------------------------------------------------//

    @GetMapping("/getFlid")
    public String getFlid(@RequestParam(value = "keyId", required = false) String flid) {

        return service.getFlid(flid);

    }

    @GetMapping("/deleteAbnormality")
    public void deleteAbnormality(@RequestParam(value = "keyId", required = false) String abnKeyId) {

        service.deleteAbnormality(abnKeyId);

    }

    // --------------------------------------SUGGESSTION DELETE
    // ----------------------------------------------------//

    @GetMapping("/deleteSuggestion")
    public void deleteSuggestion(@RequestParam(value = "keyId", required = false) String kznKeyId) {

        service.deleteSuggestion(kznKeyId);

    }

    // --------------------------------------KAIZEN DELETE
    // ----------------------------------------------------//

    @GetMapping("/deleteKaizen")
    public void deleteKaizen(@RequestParam(value = "keyId", required = false) String kznKeyId) {

        service.deleteKaizen(kznKeyId);

    }

    // --------------------------------------ACTION PLAN DELETE
    // ----------------------------------------------------//

    @GetMapping("/deleteActionPlan")
    public void deleteActionPlan(@RequestParam(value = "keyId", required = false) String actPlanId) {

        service.deleteActionPlan(actPlanId);

    }

    // --------------------------------------WHY WHY DELETE
    // ----------------------------------------------------//

    @GetMapping("/deleteWhyWhy")
    public void deleteWhyWhy(@RequestParam(value = "keyId", required = false) String whywhyKeyId) {

        service.deleteWhyWhy(whywhyKeyId);

    }

    // --------------------------------------DELETE TRAINING CALENDAR
    // ----------------------------------------------------//
    @GetMapping("/deleteTrgCalendar")
    public void deleteTrainingCalendar(@RequestParam(value = "keyId", required = false) String TrgKeyId) {

        service.deleteTrgCalendar(TrgKeyId);

    }

    // --------------------------------------DELETE LOSS
    // ----------------------------------------------------//
    @GetMapping("/deleteLossEntry")
    public void deleteLossEntry(@RequestParam(value = "keyId", required = false) String lossKeyId) {

        service.deleteLoss(lossKeyId);

    }

    // @PostMapping("/abnClosure1")
    // public void abnormalityClosure(@RequestBody AppMainAbnDto dto) {

    //     service.abnormalityClosure(dto);

    // }

    @PostMapping("/abnClosure")
public void abnormalityClosure(@RequestBody List<AppMainAbnDto> dtoList) {
    service.abnormalityClosure(dtoList);
}

    @PostMapping("/actionPlanClosure")
    public void actPlanClosure(@RequestBody AppMainActPlanDto dto) {

        service.actionPlanClosure(dto);

    }

    @GetMapping("/kaizenDateChange")
    public void kaizenDateChange(
            @RequestParam(value = "keyId", required = false) String kznKeyId,
            @RequestParam(value = "date", required = false) LocalDateTime kznDate) throws Exception {

        service.kaizenDateChange(kznKeyId, kznDate);

    }

    @GetMapping("/fipDateChange")
    public void fipDateChange(
            @RequestParam(value = "keyId", required = false) String fipKeyId,
            @RequestParam(value = "date", required = false) LocalDateTime kznDate) throws Exception {

        service.fipProjectDateChange(fipKeyId, kznDate);

    }

}
