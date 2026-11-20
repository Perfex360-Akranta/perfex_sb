package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.OcrFormRequest;
import com.akranta.perfex_sb.dto.OcrFormResponse;

public interface FormOcrService {
    OcrFormResponse recognizeForm(OcrFormRequest request);
}
