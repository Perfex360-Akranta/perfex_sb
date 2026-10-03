
package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.WorespRequest;

public interface BalworespService {

  
    ResponseEntity<WorespRequest> saveWoresp(WorespRequest request) throws Exception;

    
    WorespRequest getCompleteWorespData(String masterKeyid);

    
    boolean deleteWorespDetail(String detailId) throws Exception;
}