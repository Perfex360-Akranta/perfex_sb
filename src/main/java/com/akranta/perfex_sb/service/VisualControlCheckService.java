package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.VisualControlChecklistDto;
import com.akranta.perfex_sb.model.GenTlVisualcontrolchecklist;

import org.springframework.http.ResponseEntity;

public interface VisualControlCheckService {
    ResponseEntity<VisualControlChecklistDto> createOrUpdate(VisualControlChecklistDto dto);

    Object[] getByKeyidNative(String keyid);

    public ResponseEntity<String> delete(GenTlVisualcontrolchecklist genTlVisualcontrolchecklist) throws Exception;
    

       

}