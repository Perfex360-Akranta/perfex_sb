package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.dto.internalRejectionMstDtlDto;
import com.akranta.perfex_sb.model.QtmTlIntrejectiondtl;
import com.akranta.perfex_sb.model.QtmTlIntrejectionmst;

public interface InternalRejectionService {

    QtmTlIntrejectiondtl saveInternalRejection(internalRejectionMstDtlDto dto) throws Exception;

    List<Map<String, Object>> getInternalRejectionModificationGrid(String flid);

    QtmTlIntrejectionmst getInternalRejectionMasterData(String id) throws Exception;

}
