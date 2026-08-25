package com.akranta.perfex_sb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.DeleteLossEntryRequestDto;
import com.akranta.perfex_sb.dto.PcsLossEntryGridRequestDto;
import com.akranta.perfex_sb.dto.PcsLossCaptureGridRequestDto;
import com.akranta.perfex_sb.repository.PcsEntryJdbcRepository;
import com.akranta.perfex_sb.repository.PcsEntryRepository;
import com.akranta.perfex_sb.service.PcsEntryService;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class PcsEntryServiceImpl implements PcsEntryService {

    private final PcsEntryRepository repository;
    private final PcsEntryJdbcRepository jdbcRepository;

    public PcsEntryServiceImpl(PcsEntryRepository repository, PcsEntryJdbcRepository jdbcRepository) {
        this.repository = repository;
        this.jdbcRepository = jdbcRepository;
    }


     @Override
    @Transactional(readOnly = true)
    public String getCurrentShift( ) {

       List<String> shifts = repository.getCurrentShift1();
       if (shifts != null && !shifts.isEmpty()
            && shifts.get(0) != null
            && !shifts.get(0).trim().isEmpty()) {
        return shifts.get(0);
       }
      shifts = repository.getCurrentShift2();
      if (shifts != null && !shifts.isEmpty()
            && shifts.get(0) != null
            && !shifts.get(0).trim().isEmpty()) {
        return shifts.get(0); 
        }

    return "";
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getLossEntryGrid(PcsLossEntryGridRequestDto dto) {

        if (dto == null) throw new IllegalArgumentException("PcsLossEntryGridRequestDto is null");

        return repository.getLossEntryGrid(dto.getFlid(), dto.getFromDate(), dto.getToDate());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getPcsLossCaptureGrid(PcsLossCaptureGridRequestDto dto) {
        if (dto == null) throw new IllegalArgumentException("PcsLossCaptureGridRequestDto is null");
        return repository.getPcsLossCaptureGrid(dto.getFlid(), dto.getFromDate(), dto.getToDate(), dto.getShiftId());
    }

    @Override
    @Transactional
    public Map<String, Object> deletePcsLossEntry(DeleteLossEntryRequestDto dto) {
        if (dto == null) throw new IllegalArgumentException("DeleteLossEntryRequestDto is null");
        String lossCaptureId = dto.getPlrkKeyid();
        if (!ValidationUtil.isValidKeyId(lossCaptureId)) {
            throw new IllegalArgumentException("Valid loss capture key is required");
        }

        String linkedDetailId = jdbcRepository.findDetailIdByLossCaptureId(lossCaptureId);
        if (!ValidationUtil.isValidKeyId(linkedDetailId)) {
            throw new IllegalStateException("Loss capture not found for keyId=" + lossCaptureId);
        }

        if (ValidationUtil.isValidKeyId(dto.getPldetailsid())
                && !dto.getPldetailsid().equals(linkedDetailId)) {
            throw new IllegalArgumentException(
                    "Selected loss capture does not belong to pldetailsid=" + dto.getPldetailsid());
        }

        int deleted = jdbcRepository.deleteLossCapture(lossCaptureId);
        if (deleted <= 0) {
            throw new IllegalStateException("No loss capture row deleted for keyId=" + lossCaptureId);
        }

        Map<String, Object> resp = new java.util.LinkedHashMap<>();
        resp.put("msg", "Data Deleted Successfully");
        resp.put("keyId", lossCaptureId);
        return resp;
    }
}
