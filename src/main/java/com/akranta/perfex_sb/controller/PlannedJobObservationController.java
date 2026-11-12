package com.akranta.perfex_sb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.PlnjobemplinkRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.SheTlPlannedJobObservation;
import com.akranta.perfex_sb.service.PlannedJobObservationService;

@RestController
@RequestMapping("/api/plnjobemplink")
public class PlannedJobObservationController {  // ← renamed here

    private static final Logger logger = LoggerFactory.getLogger(PlannedJobObservationController.class);  // ← renamed here
    private final PlannedJobObservationService service;  // ← updated here

    public PlannedJobObservationController(PlannedJobObservationService service) {  // ← renamed here
        this.service = service;
    }

    /**
     * Save or Update Plnjobemplink (handles both insert and update)
     * If master.keyid is null/empty -> INSERT
     * If master.keyid exists -> UPDATE
     */
    @PostMapping("/save")
    public ResponseEntity<PlnjobemplinkRequest> savePlnjobemplink(@RequestBody PlnjobemplinkRequest request) {
        try {
            logger.info("Saving/Updating Planned Job Observation with Employee Link data");
            return service.savePlnjobemplink(request);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error saving Plnjobemplink: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Find Planned Job Observation by keyid
     */
    @GetMapping("/find/{keyid}")  // ← new endpoint
    public ResponseEntity<SheTlPlannedJobObservation> findByKeyid(@PathVariable String keyid) {
        try {
            logger.info("Fetching Planned Job Observation with keyid: {}", keyid);
            SheTlPlannedJobObservation result = service.findPlannedJobObservationByKeyid(keyid);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error fetching Planned Job Observation: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

   @GetMapping("/employees/{pjobKeyId}")
public ResponseEntity<List<Map<String, Object>>> getPojEmployee(@PathVariable String pjobKeyId) {
    try {
        logger.info("Fetching employee list for pjobKeyId: {}", pjobKeyId);
        List<Map<String, Object>> result = service.getPojEmployee(pjobKeyId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (Exception e) {
        logger.error("Error fetching employee list: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}



@PutMapping("/update-action-plan")
public ResponseEntity<Map<String, Object>> updateActionPlan(
        @RequestParam String pojKeyid,
        @RequestParam String aplmKeyid) {
    try {
        logger.info("Updating ActionPlan for pojKeyid: {} with aplmKeyid: {}", pojKeyid, aplmKeyid);
        int rowsAffected = service.updateActionPlan(pojKeyid, aplmKeyid);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "ActionPlan updated successfully");
        response.put("rowsAffected", rowsAffected);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (ResourceNotFoundException e) {
        logger.error("Record not found: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (Exception e) {
        logger.error("Error updating ActionPlan: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}




@DeleteMapping("/delete/{keyid}")
public ResponseEntity<Map<String, Object>> deletePlannedJobObservation(@PathVariable String keyid) {
    try {
        logger.info("Deleting PlannedJobObservation for keyid: {}", keyid);
        boolean deleted = service.deletePlannedJobObservation(keyid);

        Map<String, Object> response = Map.of(
            "success", deleted,
            "message", "PlannedJobObservation deleted successfully",
            "deletedId", keyid
        );

        return ResponseEntity.ok(response);
    } catch (ResourceNotFoundException e) {
        logger.error("PlannedJobObservation not found: {}", e.getMessage());
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid keyid provided: {}", keyid);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (Exception e) {
        logger.error("Error deleting PlannedJobObservation: ", e);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "Error deleting PlannedJobObservation: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}




//Delete by employee id:

@DeleteMapping("/delete-detail/{pjelPjobEmployeeid}")
public ResponseEntity<Map<String, Object>> deleteDetail(
        @PathVariable String pjelPjobEmployeeid,
        @RequestParam(required = false, defaultValue = "") String gridId) {
    try {
        logger.info("Deleting PLNJOBEMPLINK detail for pjelPjobEmployeeid: {}", pjelPjobEmployeeid);
        String result = service.deleteDetail(pjelPjobEmployeeid, gridId);

        Map<String, Object> response = Map.of(
            "success", true,
            "message", result,
            "deletedId", pjelPjobEmployeeid
        );

        return ResponseEntity.ok(response);
    } catch (ResourceNotFoundException e) {
        logger.error("PLNJOBEMPLINK detail not found: {}", e.getMessage());
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid pjelPjobEmployeeid provided: {}", pjelPjobEmployeeid);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (Exception e) {
        logger.error("Error deleting PLNJOBEMPLINK detail: ", e);
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "Error deleting PLNJOBEMPLINK detail: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}



// @DeleteMapping("/delete-employee/{pjelPjobEmployeeid}")
// public ResponseEntity<Map<String, Object>> deleteDetail(
//         @PathVariable String pjelPjobEmployeeid,
//         @RequestParam(required = false, defaultValue = "") String gridId) {
//     try {
//         logger.info("Deleting employee link for pjelPjobEmployeeid: {} | gridId: {}",
//                     pjelPjobEmployeeid, gridId);

//         String result = service.deleteDetail(pjelPjobEmployeeid, gridId); // ✅ renamed

//         Map<String, Object> response = Map.of(
//             "success", true,
//             "message", result,
//             "deletedEmployeeId", pjelPjobEmployeeid
//         );

//         return ResponseEntity.ok(response);

//     } catch (ResourceNotFoundException e) {
//         logger.error("Employee link not found: {}", e.getMessage());
//         Map<String, Object> response = Map.of(
//             "success", false,
//             "message", e.getMessage()
//         );
//         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

//     } catch (IllegalArgumentException e) {
//         logger.error("Invalid pjelPjobEmployeeid provided: {}", pjelPjobEmployeeid);
//         Map<String, Object> response = Map.of(
//             "success", false,
//             "message", e.getMessage()
//         );
//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

//     } catch (Exception e) {
//         logger.error("Error deleting employee link: ", e);
//         Map<String, Object> response = Map.of(
//             "success", false,
//             "message", "Error deleting employee link: " + e.getMessage()
//         );
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//     }
// }
}