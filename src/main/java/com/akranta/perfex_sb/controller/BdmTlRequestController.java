package com.akranta.perfex_sb.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.BdmTlRequestDto;
import com.akranta.perfex_sb.dto.DeleteCriteriaDto;
import com.akranta.perfex_sb.model.BdmTlCriticalityassessment;
import com.akranta.perfex_sb.service.BdmTlRequestService;

@RestController
@RequestMapping("/api/bdmTlRequest")
public class BdmTlRequestController {

    @Autowired
    private BdmTlRequestService bdmTlRequestService;

    @PostMapping("/save")
    public ResponseEntity<List<BdmTlCriticalityassessment>> saveWorksheetRequest(
            @RequestBody List<BdmTlRequestDto> request) throws Exception {

        List<BdmTlCriticalityassessment> saved =
                bdmTlRequestService.saveWorksheetRequest(request);

        return ResponseEntity.ok(saved);

        
    }
    @GetMapping("/flid/{parentFlid}")
    public ResponseEntity<String> getFlid(@PathVariable String parentFlid) {
        try {
            //logger.info("Fetching FLID for parent FLID: {}", parentFlid);
            String flid = bdmTlRequestService.getFlid(parentFlid);
            
            return ResponseEntity.ok(flid);
        } catch (IllegalArgumentException e) {
            //logger.error("Invalid parent FLID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            //logger.error("Error fetching FLID: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching FLID: " + e.getMessage());
        }
    }
   

   @GetMapping("/remarks")
public ResponseEntity<?> getCriticalityAssessmentRemarks(
        @RequestParam String flid,
        @RequestParam String equipmentId) {
    try {
        String remarks = bdmTlRequestService.getCriticalityAssessmentRemarks(flid, equipmentId);
        
        // Return JSON object instead of plain string
        Map<String, String> response = new HashMap<>();
        response.put("remarks", remarks);
        
        return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    } catch (Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "Error fetching remarks: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
@GetMapping("/criteria-key")
    public ResponseEntity<Map<String, String>> getCriteriaKeyId(
            @RequestParam String flId,
            @RequestParam BigDecimal totalPoints,
            @RequestParam String equipmentId,
            @RequestParam(required = false) String tradeId) {
        try {
           // logger.info("Fetching criteria key ID for flId: {}, totalPoints: {}, equipmentId: {}, tradeId: {}", 
                      // flId, totalPoints, equipmentId, tradeId);
            
            String criteriaKeyId = bdmTlRequestService.getCriteriaKeyId(
                    flId, totalPoints, equipmentId, tradeId);
            
            Map<String, String> response = new HashMap<>();
            response.put("criteriaKeyId", criteriaKeyId);
            
            if (criteriaKeyId == null || criteriaKeyId.isEmpty()) {
                response.put("message", "No criteria key ID found for the given parameters");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
           // logger.error("Error fetching criteria key ID: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error fetching criteria key ID: " + e.getMessage()));
        }
    }



//     @DeleteMapping("/delete-criteria")
// public ResponseEntity<Map<String, Object>> deleteCriteriaList(
//         @RequestBody List<String> keyIds) {  // Single declaration
    
//     if (keyIds == null || keyIds.isEmpty()) {
//         Map<String, Object> response = Map.of(
//             "success", false,
//             "message", "Request body is required and must contain at least one key ID"
//         );
//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//     }
        
//         try {
//           //  logger.info("Deleting criteria records with keyIds: {}", keyIds);
            
//             int deletedCount = bdmTlRequestService.deleteCriteriaList(keyIds);
            
//             Map<String, Object> response = Map.of(
//                 "success", true,
//                 "message", "Criteria records deleted successfully",
//                 "deletedCount", deletedCount,
//                 "deletedIds", keyIds
//             );
            
//             return ResponseEntity.ok(response);
            
//         } catch (IllegalArgumentException e) {
//            // logger.error("Invalid key IDs provided: {}", e.getMessage());
//             Map<String, Object> response = Map.of(
//                 "success", false,
//                 "message", e.getMessage()
//             );
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
//         } catch (Exception e) {
//            // logger.error("Error deleting criteria records: ", e);
//             Map<String, Object> response = Map.of(
//                 "success", false,
//                 "message", "Error deleting criteria records: " + e.getMessage()
//             );
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//         }
//     }


    
    @PostMapping("/delete-criteria")
public ResponseEntity<Map<String, Object>> deleteCriteria(
        @RequestBody DeleteCriteriaDto deleteCriteriaDto) {  // Single declaration
    
            List<String> keyIds = deleteCriteriaDto.getCriteriaDeleteList();

    if (keyIds == null || keyIds.isEmpty()) {
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "Request body is required and must contain at least one key ID"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
        
        try {
          //  logger.info("Deleting criteria records with keyIds: {}", keyIds);
            
            int deletedCount = bdmTlRequestService.deleteCriteriaList(keyIds);
            
            Map<String, Object> response = Map.of(
                "success", true,
                "message", "Criteria records deleted successfully",
                "deletedCount", deletedCount,
                "deletedIds", keyIds
            );
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
           // logger.error("Invalid key IDs provided: {}", e.getMessage());
            Map<String, Object> response = Map.of(
                "success", false,
                "message", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (Exception e) {
           // logger.error("Error deleting criteria records: ", e);
            Map<String, Object> response = Map.of(
                "success", false,
                "message", "Error deleting criteria records: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    
}
