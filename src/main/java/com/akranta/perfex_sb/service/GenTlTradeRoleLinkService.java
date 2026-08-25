package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.dto.TradeRoleLinkRequestDto;

public interface GenTlTradeRoleLinkService {

    ResponseEntity<TradeRoleLinkRequestDto> saveTradeRoleLink(TradeRoleLinkRequestDto request) throws Exception;

    ResponseEntity<TradeRoleLinkRequestDto> deleteTradeRoleLink(TradeRoleLinkRequestDto request) throws Exception;

    
    
}
