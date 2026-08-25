package com.akranta.perfex_sb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.model.EntTlTrgCalQuad;
import com.akranta.perfex_sb.service.EntTlTragcalquadService;

@RestController
@RequestMapping("/api/Quadrant")
public class EntTlTragcalquadController {
    
    @Autowired
    private EntTlTragcalquadService service;
    
    @PostMapping("/updatelevel")
    public ResponseEntity<Map<String, Object>> updateAssessmentLevel(
            @RequestBody Map<String, String> request) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            String updateList = request.get("updateList");
            String userid = request.get("userid");
            
            // Validate input
            if (updateList == null || updateList.isEmpty()) {
                response.put("success", false);
                response.put("message", "UpdateList cannot be empty");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            
            if (userid == null || userid.isEmpty()) {
                response.put("success", false);
                response.put("message", "Userid cannot be empty");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            
            EntTlTrgCalQuad result = service.createAssmLevel(updateList, userid);
            
            response.put("success", true);
            response.put("message", "Level updated successfully");
            response.put("data", result);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update level: " + e.getMessage());
            response.put("data", null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}