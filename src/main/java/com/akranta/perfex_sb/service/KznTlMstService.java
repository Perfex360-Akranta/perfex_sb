package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.dto.updateThemeCatageryDto;
import com.akranta.perfex_sb.model.GenTlWorkFlowInfo;
import com.akranta.perfex_sb.model.KznTlMst;

public interface KznTlMstService {

    ResponseEntity<KznTlMst> create(KznTlMst kznTlMst);

    KznTlMst getKaizenListById(String keyId);

    List<Map<String, Object>> findAll(String flid);

    KznTlMst updateKaizen(KznTlMst kznTlMst);

    KznTlMst getByKeyid(String keyid);

    String findKeyid(String keyId);

    List<GenTlWorkFlowInfo> saveSimpliAppval(List<GenTlWorkFlowInfo> wrkFlw);

    List<updateThemeCatageryDto> updateThemeCatogery(List<updateThemeCatageryDto> updateDto);
}
