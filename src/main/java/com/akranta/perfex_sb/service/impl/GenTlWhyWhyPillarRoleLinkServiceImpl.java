package com.akranta.perfex_sb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.akranta.perfex_sb.dto.PillarRoleLinkRequestDto;
import com.akranta.perfex_sb.repository.GenTlWhyWhyPillarRoleLinkRepository;
import com.akranta.perfex_sb.service.GenTlWhyWhyPillarRoleLinkService;

@Service
public class GenTlWhyWhyPillarRoleLinkServiceImpl implements GenTlWhyWhyPillarRoleLinkService {

    @Autowired
    private GenTlWhyWhyPillarRoleLinkRepository repository;

    @Override
    public ResponseEntity<PillarRoleLinkRequestDto> savePillarRoleLink(PillarRoleLinkRequestDto request) throws Exception {

        String pillarId  = request.getPillarId();
        String roleId    = request.getRoleId();
        String createdBy = request.getCreatedBy();

        if (pillarId == null || pillarId.trim().isEmpty()
                || roleId == null || roleId.trim().isEmpty()) {
            throw new Exception("Pillar and Role are required");
        }

        String existingActive = repository.findActiveFlag(pillarId, roleId);

        if (existingActive == null || existingActive.trim().isEmpty()) {

            String pillarCode = repository.findPillarCode(pillarId);
            String roleCode   = repository.findRoleCode(roleId);
            String roleName   = repository.findRoleName(roleId);
            String newKeyid   = repository.getNextSequence();

            repository.insertPillarRoleLink(newKeyid, pillarId, pillarCode, roleId, roleCode, roleName, createdBy);

        } else if ("Y".equals(existingActive.trim())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This Pillar-Role combination already exists."
            );

        } else {
            repository.reactivatePillarRoleLink(pillarId, roleId);
        }

        return ResponseEntity.ok(request);
    }

    @Override
public ResponseEntity<PillarRoleLinkRequestDto> deletePillarRoleLink(PillarRoleLinkRequestDto request) throws Exception {

    String pillarId = request.getPillarId();
    String roleId    = request.getRoleId();

    if (pillarId == null || pillarId.trim().isEmpty()
            || roleId == null || roleId.trim().isEmpty()) {
        throw new Exception("Pillar and Role are required");
    }

    int rowsAffected = repository.deactivatePillarRoleLink(pillarId, roleId);

    if (rowsAffected == 0) {
        throw new Exception("This Pillar-Role link does not exist");
    }

    return ResponseEntity.ok(request);
}
}

    
