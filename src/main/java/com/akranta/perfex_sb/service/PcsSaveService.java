package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.PcsSaveRequestDto;
import com.akranta.perfex_sb.model.PcsTlLosscapture;

public interface PcsSaveService {

    PcsTlLosscapture save(PcsSaveRequestDto request);
}
