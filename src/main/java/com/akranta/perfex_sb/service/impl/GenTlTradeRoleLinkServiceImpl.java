package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.TradeRoleLinkRequestDto;
import com.akranta.perfex_sb.repository.GenTlTradeRoleLinkRepository;
import com.akranta.perfex_sb.service.GenTlTradeRoleLinkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GenTlTradeRoleLinkServiceImpl implements GenTlTradeRoleLinkService {

    @Autowired
    private GenTlTradeRoleLinkRepository repository;

    @Override
    public ResponseEntity<TradeRoleLinkRequestDto> saveTradeRoleLink(TradeRoleLinkRequestDto request) throws Exception {

        String tradeId = request.getTradeId();
        String roleId = request.getRoleId();
        String createdBy = request.getCreatedBy();

        if (tradeId == null || tradeId.trim().isEmpty()
                || roleId == null || roleId.trim().isEmpty()) {
            throw new Exception("Trade and Role are required");
        }

        String existingRoleId = repository.findOtherActiveRoleForTrade(tradeId, roleId);
        if (existingRoleId != null && !existingRoleId.trim().isEmpty()) {
            throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        "This Trade is already linked to a Role. A Trade can be linked to only one Role."
    );
        }

        String existingActive = repository.findActiveFlag(tradeId, roleId);

        if (existingActive == null || existingActive.trim().isEmpty()) {
            repository.insertTradeRoleLink(tradeId, roleId, createdBy);

        } else if ("Y".equals(existingActive.trim())) {
             throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        "This Trade-Role combination already exists."
    );

        } else {
            repository.reactivateTradeRoleLink(tradeId, roleId);
        }

        return ResponseEntity.ok(request);
    }

    @Override
    public ResponseEntity<TradeRoleLinkRequestDto> deleteTradeRoleLink(TradeRoleLinkRequestDto request) throws Exception {

        String tradeId = request.getTradeId();
        String roleId = request.getRoleId();

        if (tradeId == null || tradeId.trim().isEmpty()
                || roleId == null || roleId.trim().isEmpty()) {
            throw new Exception("Trade and Role are required");
        }
        // added by priyanka 
        int rowsAffected = repository.deactivateTradeRoleLink(tradeId, roleId);

        if (rowsAffected == 0) {
            throw new Exception("This Trade-Role link does not exist");
        }
        // end 

        repository.deactivateTradeRoleLink(tradeId, roleId);

        return ResponseEntity.ok(request);
    }
}
