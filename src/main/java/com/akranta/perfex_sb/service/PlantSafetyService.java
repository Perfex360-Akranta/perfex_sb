package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.PlantSafetyRequest;
import com.akranta.perfex_sb.model.SheTlPlantSafetydtl;
import com.akranta.perfex_sb.model.SheTlPlantSafetymst;

import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;


public interface PlantSafetyService {

    /**
     * Save or Update Plant Safety (handles both insert and update in single method)
     * If master.keyid is null/empty -> INSERT
     * If master.keyid exists       -> UPDATE
     */
    ResponseEntity<PlantSafetyRequest> savePlantSafety(PlantSafetyRequest request) throws Exception;
    ResponseEntity<SheTlPlantSafetymst> getPlantSafetyByKeyid(String keyid);
    String getDetailValue(String keyid);
    List<Map<String, Object>> getDetailBySpsmKeyid(String keyid);
    
     String getResponse(String modifiedid);

    String getObser(String modifiedid);

    String getMaster(String modifiedid);

    String getResponseRemarks(String modifiedid);
    String singleResponsiblty(String detailkeyid, String responsevalue, String targetdate,
        SheTlPlantSafetymst sheTlPlantSafetymst, SheTlPlantSafetydtl sheTlPlantSafetydtl);
       // throws BusinessApplicationExceptions, Exception;
       String verifyUpdate(String dtlid, String verid, String verifydate);
       String resUpdate(String dtlid, String resid, String targetdate);
       String checkDeleteList(String detailkeyid, String apmdelete);

       String updateVerification(String detailkeyid, String responsevalue, String targetdate,
                           String verifystatus, String remarks);
String updateActionClosure(String detailkeyid, String responsevalue, String targetdate,
                           String revisedate, String status, String correctiveaction,
                           String remarks, String abnKeyid) throws Exception;
}