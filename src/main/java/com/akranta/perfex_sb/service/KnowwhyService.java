package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.KnowWhySaveDto;
import com.akranta.perfex_sb.model.QtmTlKnowwhymst;

public interface KnowwhyService {
    KnowWhySaveDto saveKnowWhy(KnowWhySaveDto dto) throws Exception;

    QtmTlKnowwhymst getKnowWhy(String keyid) throws Exception;

    String saveKnowWhyApproval(String keyid);

    QtmTlKnowwhymst DeleteKnowWhy(QtmTlKnowwhymst qtmTlKnowwhymst);

    QtmTlKnowwhymst saveKnowWhyMst(QtmTlKnowwhymst mst) throws Exception;

}
