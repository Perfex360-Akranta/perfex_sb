package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.OcrHandwritingRequest;
import com.akranta.perfex_sb.dto.OcrHandwritingResponse;

public interface OcrService {
    OcrHandwritingResponse recognizeHandwriting(OcrHandwritingRequest request);
}
