package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.BdmDto;
import com.akranta.perfex_sb.dto.EmpCostDto;
import com.akranta.perfex_sb.model.BAL_BdmTlDtl;
import com.akranta.perfex_sb.model.BAL_BdmTlMst;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostactual;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostplan;
import com.akranta.perfex_sb.model.BAL_WomTlOthercostactual;
import com.akranta.perfex_sb.model.BAL_WomTlOthercostplan;
import com.akranta.perfex_sb.model.BAL_WomTlServicecostactual;
import com.akranta.perfex_sb.model.BAL_WomTlServicecostplan;
import com.akranta.perfex_sb.model.BAL_WomTlSparecostactual;
import com.akranta.perfex_sb.model.BAL_WomTlSparecostplan;
import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostactual;
import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostplan;
import com.akranta.perfex_sb.model.WomTlCommunicationlog;
import com.akranta.perfex_sb.model.WomTlWomst;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BdmService {

    
    ResponseEntity<BdmDto> saveBdm(BdmDto request) throws Exception;
     List<Map<String, Object>> getGridSummary(String woId) throws Exception;
    

List<Map<String, Object>> getGridEstimate(String formName, String woId) throws Exception;
List<Map<String, Object>> getPageTotal(String formName, String formType, String woId) throws Exception;
List<Map<String, Object>> getGridActual(String formName, String woId) throws Exception;
BAL_WomTlManpowercostplan saveManpowerCost(BAL_WomTlManpowercostplan request) throws Exception;
BAL_WomTlManpowercostactual saveManpowerCostActual(BAL_WomTlManpowercostactual request) throws Exception;
BAL_WomTlSparecostplan saveSpareCost(BAL_WomTlSparecostplan request) throws Exception;
BAL_WomTlServicecostplan saveServiceCost(BAL_WomTlServicecostplan request) throws Exception;
BAL_WomTlUtilitycostplan saveUtilityCost(BAL_WomTlUtilitycostplan request) throws Exception;
BAL_WomTlOthercostplan saveOtherCost(BAL_WomTlOthercostplan request) throws Exception;
BAL_WomTlSparecostactual saveSpareCostActual(BAL_WomTlSparecostactual request) throws Exception;
BAL_WomTlServicecostactual saveServiceCostActual(BAL_WomTlServicecostactual request) throws Exception;
BAL_WomTlUtilitycostactual saveUtilityCostActual(BAL_WomTlUtilitycostactual request) throws Exception;
 BAL_WomTlOthercostactual saveOtherCostActual(BAL_WomTlOthercostactual request) throws Exception;
 int deleteManpowerCost(String woid, String manpowerid, String skillid) throws Exception;
 int deleteManpowerCostActual(String maintwoid, String manpowerid, String skillid) throws Exception;
 int deleteSpareCost(String woid, String requestedby, String sparesid) throws Exception;
 int deleteSpareCostActual(String woid, String requestedby, String sparesid) throws Exception;
 int deleteServiceCost(String woid, String serviceid) throws Exception;
int deleteServiceCostActual(String woid, String serviceid) throws Exception;
int deleteUtilityCost(String wokeyid, String requestedby, String utilitymstid) throws Exception;
int deleteUtilityCostActual(String wokeyid, String requestedby, String utilitymstid) throws Exception;
int deleteOtherCost(String woid, String requestedby, String othercostmstid) throws Exception;
int deleteOtherCostActual(String woid, String requestedby, String othercostmstid) throws Exception;
    BAL_BdmTlMst getBdmMaster(String keyid) throws Exception;

    BAL_BdmTlDtl getBdmDetailByMasterKeyid(String bdmsKeyid) throws Exception;

    WomTlWomst getWorkOrder(String keyid) throws Exception;
    ResponseEntity<WomTlCommunicationlog> saveCommTxt(
            WomTlCommunicationlog log) throws Exception;
            List<Map<String, Object>> getCommText(String bdId) throws Exception;

}
