package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.FishboneChildSaveRequest;
import com.akranta.perfex_sb.dto.FishboneMasterSaveRequest;

import java.util.Map;

public interface FishboneService {

    Map<String, Object> saveOrUpdateMaster(FishboneMasterSaveRequest request);

    Map<String, Object> saveOrUpdateChild(FishboneChildSaveRequest request);

    java.util.List<java.util.Map<String, Object>> getFishboneTree(com.akranta.perfex_sb.dto.FishboneTreeRequest request);

    java.util.List<String> searchNode(String searchNode, String originalId);

    Map<String, Object> deleteChild(String detailKeyId);

    com.akranta.perfex_sb.dto.FishboneMasterSaveRequest getMaster(String keyId, String refDocType, String refDocId);

    java.util.List<java.util.Map<String, Object>> getReportGrid(String keyId);
}
