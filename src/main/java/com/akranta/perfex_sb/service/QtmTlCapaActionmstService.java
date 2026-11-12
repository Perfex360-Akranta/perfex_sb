package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.QtmTlCapaActionmstDTO;

public interface QtmTlCapaActionmstService {
    QtmTlCapaActionmstDTO save(QtmTlCapaActionmstDTO dto) throws Exception;

    String getFnlnKeyIdByOriginalId(String targetJH);

    void deleteCapaActionPlanDetail(String qcpaKeyid) throws Exception;
}
