package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.dto.PillarRoleLinkRequestDto;

public interface GenTlWhyWhyPillarRoleLinkService {

    ResponseEntity<PillarRoleLinkRequestDto> savePillarRoleLink(PillarRoleLinkRequestDto request) throws Exception;

    ResponseEntity<PillarRoleLinkRequestDto> deletePillarRoleLink(PillarRoleLinkRequestDto request) throws Exception;
}
