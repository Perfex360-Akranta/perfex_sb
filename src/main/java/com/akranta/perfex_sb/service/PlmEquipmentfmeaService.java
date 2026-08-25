package com.akranta.perfex_sb.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.PlmEquipmentfmeaDTO;
import com.akranta.perfex_sb.dto.EquipmentfmeaParamDTO;

public interface PlmEquipmentfmeaService {
    public ResponseEntity<?> save(PlmEquipmentfmeaDTO plmEquipmentfmeaDTO);

    ResponseEntity<?> update(PlmEquipmentfmeaDTO plmEquipmentfmeaDTO);

    ResponseEntity<?> getAll(PlmEquipmentfmeaDTO plmEquipmentfmeaDTO);

    void deleteDtls(List<EquipmentfmeaParamDTO> paramDtoList) throws Exception;

}




// List<String> recallFmeaByKeyId(String keyId, String type);
