package com.akranta.perfex_sb.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.IndicatorDto;
import com.akranta.perfex_sb.dto.KpiIndicatorDto;
import com.akranta.perfex_sb.dto.kpiElementIdRequestDto;
import com.akranta.perfex_sb.model.KpiTlActual;
import com.akranta.perfex_sb.model.KpiTlIndicator;
import com.akranta.perfex_sb.model.KpiTlKpiRemarks;
import com.akranta.perfex_sb.service.KPIService;



@RestController
@RequestMapping("/api/kpi/indicator")
public class KPIController {

    private static final Logger logger = LoggerFactory.getLogger(KPIController.class);

    @Autowired
    private KPIService kpiService;

    @PostMapping
    public ResponseEntity<KpiTlIndicator> create(@RequestBody KpiTlIndicator kpiTlIndicator) {
        KpiTlIndicator savedIndicator = kpiService.createIndicator(kpiTlIndicator);
        return ResponseEntity.ok(savedIndicator);
    }

    @PostMapping("/update")
    public ResponseEntity<KpiTlIndicator> update(@RequestBody KpiTlIndicator kpiTlIndicator) {
        KpiTlIndicator savedIndicator = kpiService.updateIndicator(kpiTlIndicator);
        return ResponseEntity.ok(savedIndicator);
    }

    @PostMapping("/getAllkeyInd")
    public List<Map<String, Object>> getAllkeyInds(
            @RequestBody KpiIndicatorDto kpiIndicatorDto) {

        logger.info("Received request to get all KPI indicators");

        List<Map<String, Object>> result = kpiService.getAllKeyIndicators(kpiIndicatorDto);

        return result;
    }

    @PostMapping("/getAllkeyIndkkvalue")
    public List<Map<String, Object>> getKpiTlIndicatorKkValues(
            @RequestBody KpiIndicatorDto kpiIndicatorDto) {

        logger.info("Received request to get all KPI indicators");

        List<Map<String, Object>> result = kpiService.getAllKeyIndicatorsKkValue(kpiIndicatorDto);

        return result;
    }

    // delete by key id
    @DeleteMapping("/deletekpitlindicator/{kink_keyid}")
    public ResponseEntity<String> deletebykeyid(@PathVariable("kink_keyid") KpiTlIndicator kpiTlIndicator) {

        int count = kpiService.deleteByKeyId(kpiTlIndicator);

        if (count > 0) {
            return ResponseEntity.ok("Deleted sucessfully");
        } else {
            return ResponseEntity.ok("Not Deleted");
        }
    }
    // @GetMapping("/validateDelkeyIndLevel")
    // public ResponseEntity<String> validateDelkeyIndLevel(
    // @RequestParam(required = false) String parentId,
    // @RequestParam(required = false) String pillarId,
    // @RequestParam(required = false) String keyId,
    // @RequestParam(required = false) String location) throws Exception {

    // String result = kpiService.validateDelkeyIndLevel(parentId, pillarId, keyId,
    // location);
    // return ResponseEntity.ok(result);
    // }

    @PostMapping("/findbypillcode/{pillCode}")
    public String getByPillCode(@PathVariable String pillCode) {
        return kpiService.getByPillCode(pillCode);
    }

    @PostMapping("/findbyflid/{flid}")
    public String getLocation(@PathVariable String flid) {
        return kpiService.getflId(flid);
    }

    // TAGET SETTING 22222

    // @PostMapping("/findbykeyid_indicatorid")
    // public ResponseEntity<List<KpiTlActual>> getByKeyId(@RequestParam("keyid")
    // String keyid,
    // @RequestParam("indicatorid") String indicatorid) {
    // List<KpiTlActual> result = kpiService.getByKeyId(keyid, indicatorid);
    // return ResponseEntity.ok(result);
    // }

    // @PostMapping("/findbykeyid_indicatorid")
    // public ResponseEntity<List<KpiTlActual>> getByKeyId(
    // @RequestParam(required = false) String keyid,
    // @RequestParam(required = false) String indicatorid) {

    // if (keyid == null || indicatorid == null) {
    // return ResponseEntity.badRequest().build();
    // }

    // List<KpiTlActual> actuals = kpiService.getByKeyId(keyid, indicatorid);
    // return ResponseEntity.ok(actuals);
    // }

    // @PostMapping("/selectbymodel")
    // public ResponseEntity<KpiTlActual> selectByModel(@RequestBody KpiTlActual
    // kpiTlActual) {

    // logger.info("KPI VALUES {}", kpiTlActual.getKeyid());
    // logger.info("KPI VALUES {}", kpiTlActual.getIndicatorid());
    // KpiTlActual result = kpiService.getByModel(kpiTlActual);

    // if (result == null) {
    // return ResponseEntity.notFound().build();
    // }

    // return ResponseEntity.ok(result);
    // }

    // @PostMapping("/selectbymodel")
    // public ResponseEntity<KpiTlActual> selectByModel(@RequestBody KpiTlActual
    // kpiTlActual) {
    // KpiTlActual result = kpiService.getByModel(kpiTlActual);

    // if (result == null) {
    // return ResponseEntity.notFound().build();
    // }

    // return ResponseEntity.ok(result);
    // }

    // @GetMapping("/findbypillcode/{pillCode}")
    // public String getByPillCode(@PathVariable String pillCode) {
    // return service.getByPillCode(pillCode);
    // }

    @PostMapping("/createordeletekpiactual")
    public ResponseEntity<List<KpiTlActual>> createOrDelete(
            @RequestBody List<KpiTlActual> requestList) {

        List<KpiTlActual> response = kpiService.createordelete(requestList);

        return ResponseEntity.ok(response);
    }

    // create and update or delete in remarks table
    @PostMapping("/createordeletekpiremarks")
    public ResponseEntity<List<KpiTlKpiRemarks>> createordeleteremarks(@RequestBody List<KpiTlKpiRemarks> list) {
        List<KpiTlKpiRemarks> result = kpiService.createordeleteremarks(list);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/deviationcount")
    public ResponseEntity<List<Map<String, Object>>> getKPIDeviationCount(
            @RequestParam String flid,
            @RequestParam BigDecimal year,
            @RequestParam String frequency,
            @RequestParam String currDate,
            @RequestParam String currMonthYear) {
        List<Map<String, Object>> result = kpiService.getKPIDeviationCount(flid, year, frequency, currDate,
                currMonthYear);
        return ResponseEntity.ok(result);
    }



     @PostMapping("/getSortNo")
    public ResponseEntity<String> getSortNo(@RequestBody KpiTlIndicator kpiTlIndicator) {
        
            String sortNo = kpiService.getSortNo2(kpiTlIndicator);
            return ResponseEntity.ok(sortNo);
    }

    // @GetMapping("/deviation-list")
    // public ResponseEntity<List<Map<String, Object>>> getDeviationList(
    // @RequestParam String flid,
    // @RequestParam String year,
    // @RequestParam String frequency,
    // @RequestParam String currDate) {
    // List<Map<String, Object>> deviationList = kpiService.getDeviationListif(flid,
    // year, frequency, currDate);
    // return ResponseEntity.ok(deviationList);
    // }

    // @GetMapping("/kpiremarks/{kprm_keyid}")
    // public ResponseEntity<List<KpiTlKpiRemarks>>
    // getbykeyid(@PathVariable("kprm_keyid") String keyid)
    // {
    // List<KpiTlKpiRemarks> result = kpiService.getbykeyid(keyid);
    // return ResponseEntity.ok(result);
    // }

    // @PostMapping
    // public ResponseEntity<?> createOrUpdateIndicator(@RequestBody KpiTlIndicator
    // kpiTlIndicator) {
    // try {
    // logger.info("Received request to create/update KPI Indicator with keyid: {}",
    // kpiTlIndicator.getKeyid());

    // // Validate input
    // // if (kpiTlIndicator == null) {
    // // return ResponseEntity.badRequest().body("KPI Indicator data cannot be
    // null");
    // // }

    // // Call service method
    // KpiTlIndicator savedIndicator =
    // kpiService.createOrUpdateIndicator(kpiTlIndicator);

    // logger.info("Successfully created/updated KPI Indicator with keyid: {}",
    // savedIndicator.getKeyid());

    // return ResponseEntity.ok(savedIndicator);

    // } catch (IllegalArgumentException e) {
    // logger.error("Validation error: {}", e.getMessage());
    // return ResponseEntity.badRequest().body(e.getMessage());
    // } catch (Exception e) {
    // logger.error("Error creating/updating KPI Indicator: {}", e.getMessage(), e);
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("Failed to create/update KPI Indicator: " + e.getMessage());
    // }
    // }



    //   @GetMapping("/get-key-ind-level")
    // public ResponseEntity<Integer> getkeyIndLevel(@RequestBody KpiTlIndicator kpiTlIndicator) 
    //         throws Exception {
        
    //     int menuLevel = kpiService.getkeyIndLevel(kpiTlIndicator);
        
    //     return ResponseEntity.ok(menuLevel);
    // }

    @GetMapping("/get-key-ind-level")
public ResponseEntity<Integer> getkeyIndLevel(
        @RequestParam("keyId") String keyId) throws Exception {

    int level = kpiService.getkeyIndLevel(keyId);
    return ResponseEntity.ok(level);
}

    
    @PostMapping("/get-config-key-ind-level")
    public ResponseEntity<Integer> getConfigkeyIndLevel() throws Exception {
        
        int configLevel = kpiService.getConfigkeyIndLevel();
        
        return ResponseEntity.ok(configLevel);
    }


    @PostMapping("/StartMonth")
    public ResponseEntity<String> getEntProgStartMonth() throws Exception {
        String configLevel = kpiService.getEntProgStartMonth();
        return ResponseEntity.ok(configLevel);

    }


      @PostMapping("/indiactorss")
    public ResponseEntity<KpiTlIndicator> indiactor(@RequestBody IndicatorDto KpiTlIndicator) throws Exception {
        String indcatorname = KpiTlIndicator.getIndicatorname();
        String KEYID = KpiTlIndicator.getKeyid();
        String Parentid = KpiTlIndicator.getParentid();
        KpiTlIndicator ind = kpiService.indiactor( KEYID,indcatorname,Parentid);
        return ResponseEntity.ok(ind);

    }


  

    //   @PostMapping("/getElementId")
    // public ResponseEntity<?> getElementId(@RequestBody kpiElementIdRequestDto request) {
    //     try {
    //         System.out.println("==== getElementId API called ======");
    //         System.out.println("Request - loginflid: " + request.getLoginflid() + 
    //                          ", loginlevel: " + request.getLoginlevel() + 
    //                          ", empId: " + request.getEmpId());
            
    //         List<String[]> elementIds = kpiService.getElementId(
    //             request.getLoginflid(),
    //             request.getEmpId(),                
    //             request.getLoginElementid(),
    //             request.getLoginlevel()
                
    //         );
            
    //         System.out.println("Found " + elementIds.size() + " records");
    //         return ResponseEntity.ok(elementIds);
            
    //     } catch (Exception e) {
    //         System.err.println("Error in getElementId: " + e.getMessage());
    //         Map<String, String> error = new HashMap<>();
    //         error.put("error", e.getMessage());
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    //     }
    // }


    @PostMapping("/getElementId")
    public ResponseEntity<?> getElementId(@RequestBody kpiElementIdRequestDto request) {
        try {
            System.out.println("==== getElementId API called ======");
            System.out.println("Request - loginflid: " + request.getLoginflid() + 
                             ", loginlevel: " + request.getLoginlevel() + 
                             ", empId: " + request.getEmpId());
            
            List<Map<String, Object>> elementIds = kpiService.getElementIdAsMap(
                request.getLoginflid(),
                request.getLoginlevel(),
                request.getLoginElementid(),
                request.getEmpId()
                
            );
            
            System.out.println("Found " + elementIds.size() + " records");
            return ResponseEntity.ok(elementIds);
            
        } catch (Exception e) {
            System.err.println("Error in getElementId: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}