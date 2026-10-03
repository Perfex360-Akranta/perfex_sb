package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.BalAllWoGenDataMobileDto;
import com.akranta.perfex_sb.dto.BalCancelAllocatedDto;
import com.akranta.perfex_sb.dto.BalGetScheduleDto;
import com.akranta.perfex_sb.dto.BalSaveObservationsDto;
import com.akranta.perfex_sb.dto.BalSaveWorkOrderDetailsDto;
import com.akranta.perfex_sb.dto.BalUpdateAllocatedDto;
import com.akranta.perfex_sb.dto.BalUpdateWorkOrderDetailsDto;
import com.akranta.perfex_sb.model.BalPlmTlCalendar;
import com.akranta.perfex_sb.service.BalMonthlyPlanConfigService;

@RestController
@RequestMapping("/api/workOrder")
public class BalMonthlyPlanConfigController 
{
    @Autowired
    private BalMonthlyPlanConfigService service;

    private static final Logger logger = LoggerFactory.getLogger(BalMonthlyPlanConfigController.class);


    @PostMapping("/saveWodGrd")
    public ResponseEntity<String> saveGDWorkOrderDetails(@RequestBody BalSaveWorkOrderDetailsDto model) throws Exception
    {
        logger.info("Entered");

        String result = service.saveGDWorkOrderDetails(model);
        return ResponseEntity.ok(result);
    }

       @PostMapping("/modifyCompWo")
    public ResponseEntity<String> updateGDWorkOrderDetails(@RequestBody BalUpdateWorkOrderDetailsDto model) throws Exception
    {
        logger.info("Entered");

        String result = service.updateGDWorkOrderDetails(model);
        return ResponseEntity.ok(result);
    }




    @PostMapping("/saveObservations")
    public void saveObservations(@RequestBody BalSaveObservationsDto observationsDto)throws Exception
    {
        service.saveObservations(observationsDto);


    }
    @PostMapping("/getAllWoGenDataMobile")
    public ResponseEntity<List<Map<String,Object>>> getAllWoGenDataMobile(@RequestBody BalAllWoGenDataMobileDto dto)
    {
        List<Map<String,Object>> result = service.getAllWoGenDataMobile(dto);
        return ResponseEntity.ok(result);


        
    }

     @PostMapping("/getSchedule")
    public ResponseEntity<List<Map<String,Object>>> getSchedule(@RequestBody BalGetScheduleDto dto)
    {
        List<Map<String,Object>> result = service.getSchedule(dto);
        return ResponseEntity.ok(result);


        
    }

         @PostMapping("/updateAllocated")
    public ResponseEntity<String> updateAllocated(@RequestBody BalUpdateAllocatedDto dto)
    {
        String result = service.updateAllocated(dto);
        return ResponseEntity.ok(result);


        
    }

          @PostMapping("/cancelAllocated")
    public ResponseEntity<String> cancelAllocated(@RequestBody BalCancelAllocatedDto dto)
    {
        String result = service.cancelAllocated(dto);
        return ResponseEntity.ok(result);


        
    }



    




}
