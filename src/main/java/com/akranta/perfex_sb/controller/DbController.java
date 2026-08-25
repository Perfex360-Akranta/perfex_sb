package com.akranta.perfex_sb.controller;


import com.akranta.perfex_sb.dto.ExcelExportRequest;
import com.akranta.perfex_sb.repository.DbFunctionTempleteRepository;

import com.akranta.perfex_sb.util.ExcelUtils;

import jakarta.servlet.http.HttpServletResponse;

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


    @Autowired
    private ExcelUtils utils;

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
 
    @PostMapping("/excel")
    public void exportExcel(@RequestBody ExcelExportRequest req, HttpServletResponse response) throws Exception {

        String ext = req.getFormat().equalsIgnoreCase("xlsx") ? ".xlsx" : ".xls";
        String contentType = req.getFormat().equalsIgnoreCase("xlsx")
                ? "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                : "application/vnd.ms-excel";

        response.setContentType(contentType);
        response.setHeader("Content-Disposition",
                "attachment; filename=" + req.getFileName() + ext);

        utils.exportToResponse(req, response.getOutputStream());
        response.getOutputStream().flush();
    }
}
