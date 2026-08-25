package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.OtherLossEntryDto;
import com.akranta.perfex_sb.dto.OtherLossUploadRequest;
//import org.springframework.web.multipart.MultipartFile;

//import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OtherLossService {
    Map<String, Object> saveOrUpdate(OtherLossEntryDto dto);
    void updateLossValues(List<OtherLossEntryDto> items);
    void delete(String keyId);
    Map<String, Object> uploadExcel(OtherLossUploadRequest req);
}
