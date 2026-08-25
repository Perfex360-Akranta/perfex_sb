package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.dto.PlmtlProcessfmeaDto;
import com.akranta.perfex_sb.dto.ProcessfmeaParamDto;


public interface PlmtlProcessfmeaService {
    public ResponseEntity<?> save(PlmtlProcessfmeaDto plmtlProcessfmeaDto);
    

    // List<String> recallFmeaByKeyId(String keyId, String type);
    //List<String> recallFmeaByKeyId(String keyId, String type);

     public List<Map<String, Object>>recallFmeaByKeyId(String keyId, String type);


    void deleteDtls(List<ProcessfmeaParamDto> paramDtoList) throws Exception;


    // PlmtlProcessfmeaMST deleteDtls(PlmtlProcessfmeaMST mst) throws Exception;


    
   

   
}
