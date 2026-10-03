
package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.model.BalPlanconfiguration;

public interface BalPlanconfigurationService {

    
    ResponseEntity<?> create(BalPlanconfiguration planconfiguration);
}