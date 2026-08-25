package com.akranta.perfex_sb.controller;


import com.akranta.perfex_sb.repository.DbFunctionTempleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("/api/db")
public class DbController {


    @Autowired
    private DbFunctionTempleteRepository fnRepository;



@PostMapping("/callFunction/{functionName}")
    public ResponseEntity<Map<String, Object>> callDbFunction(
            @PathVariable String functionName,
            @RequestBody Map<String, Object> params) throws SQLException {
        Map<String, Object> result = fnRepository.callFunction(functionName, params);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/callMultiParamFunction/{functionName}")
    public ResponseEntity<Map<String, Object>> callMultiParamFunction(
            @PathVariable String functionName,
            @RequestBody Map<Integer, Object> params) throws SQLException {
        Map<String, Object> result = fnRepository.callMultipeParamFunction(functionName, params);
        return ResponseEntity.ok(result);
    }
 
}
