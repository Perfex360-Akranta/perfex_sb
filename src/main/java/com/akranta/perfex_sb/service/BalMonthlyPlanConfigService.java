package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.akranta.perfex_sb.dto.BalAllWoGenDataMobileDto;
import com.akranta.perfex_sb.dto.BalCancelAllocatedDto;
import com.akranta.perfex_sb.dto.BalGetScheduleDto;
import com.akranta.perfex_sb.dto.BalSaveObservationsDto;
import com.akranta.perfex_sb.dto.BalSaveWorkOrderDetailsDto;
import com.akranta.perfex_sb.dto.BalUpdateAllocatedDto;
import com.akranta.perfex_sb.dto.BalUpdateWorkOrderDetailsDto;
import com.akranta.perfex_sb.model.BalPlmTlCalendar;

public interface BalMonthlyPlanConfigService  {

    


     String saveGDWorkOrderDetails(BalSaveWorkOrderDetailsDto model) throws Exception;

     public void saveObservations(@RequestBody BalSaveObservationsDto observationsDto) throws Exception;

       public List<Map<String,Object>> getAllWoGenDataMobile(@RequestBody BalAllWoGenDataMobileDto dto);

        public List<Map<String,Object>> getSchedule(@RequestBody BalGetScheduleDto dto);

         public String updateGDWorkOrderDetails(@RequestBody BalUpdateWorkOrderDetailsDto model) throws Exception;

         public String updateAllocated(@RequestBody BalUpdateAllocatedDto dto);

          public String cancelAllocated(@RequestBody BalCancelAllocatedDto dto);
    
}
