package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.FishboneChildSaveRequest;
import com.akranta.perfex_sb.dto.FishboneMasterSaveRequest;
import com.akranta.perfex_sb.dto.FishboneTreeRequest;
import com.akranta.perfex_sb.repository.FishboneRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.FishboneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class FishboneServiceImpl implements FishboneService {

    private static final String MASTER_TABLE = "GEN_TL_FISHBONEMST";
    private static final String DETAIL_TABLE = "GEN_TL_FISHBONEDTL";
    private static final int KEY_LENGTH = 10;
    private static final String MASTER_PREFIX = "FSH";
    private static final String DETAIL_PREFIX = "FSD";
    private static final String DATE_FORMAT = "MMYY";
    private static final String FORMAT_RESET = "Y";
    private static final String ROOT_PARENT_ID = "FB001";
    private static final String[] DEFAULT_CAUSES = {"MAN", "MACHINE", "MATERIAL", "METHOD" ,"INFORMATION"};

    private final FishboneRepository repository;
    private final DbActionTemplate dbActionTemplate;

    public FishboneServiceImpl(FishboneRepository repository, DbActionTemplate dbActionTemplate) {
        this.repository = repository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public Map<String, Object> saveOrUpdateMaster(FishboneMasterSaveRequest request) {
        validateMaster(request);
        applyMasterDefaults(request);

        Map<String, Object> resp = new LinkedHashMap<>();
        boolean isCreate = !hasKey(request.getFismKeyid());

        if (isCreate) {
            String masterKey = generateMasterKey();
            request.setFismKeyid(masterKey);
            repository.insertMaster(request);
            insertDefaultChildren(request);

            if ("WM".equalsIgnoreCase(nullSafe(request.getFismRefdoctype())) && hasText(request.getFismRefdocid())) {
                repository.updateWorkOrderFishboneRef(request.getFismRefdocid(), masterKey);
            }
            resp.put("msg", "Data Saved Successfully");
            resp.put("masterKeyId", masterKey);
            resp.put("defaultChildrenInserted", DEFAULT_CAUSES.length);
        } else {
            repository.updateMaster(request);
            resp.put("msg", "Data Updated Successfully");
            resp.put("masterKeyId", request.getFismKeyid());
        }
        return resp;
    }

    @Override
    @Transactional
    public Map<String, Object> saveOrUpdateChild(FishboneChildSaveRequest request) {
        validateChild(request);

        Map<String, Object> resp = new LinkedHashMap<>();
        boolean editMode = isEditMode(request);

        if (editMode) {
            String targetKey = firstNonEmpty(request.getFisdParentid(), request.getFisdKeyid());
            String newCause = firstNonEmpty(request.getUpdateCause(), request.getFisdCause());
            if (!hasText(targetKey)) {
                throw new IllegalArgumentException("detailKeyId/parentId is required for edit");
            }
            if (!hasText(newCause)) {
                throw new IllegalArgumentException("New cause is required for edit");
            }
            repository.updateDetailCause(targetKey, newCause.trim());
            resp.put("msg", "Data Saved Successfully");
            resp.put("parentId", targetKey);
            resp.put("mode", "edit");
            return resp;
        }

        applyChildDefaults(request);

        if (!hasKey(request.getFisdKeyid())) {
            String detailKey = generateDetailKey();
            request.setFisdKeyid(detailKey);
            repository.insertDetail(request);
            resp.put("msg", "Data Saved Successfully");
            resp.put("detailKeyId", detailKey);
            resp.put("parentId", request.getFisdParentid());
        } else {
            repository.updateDetail(request);
            resp.put("msg", "Data Updated Successfully");
            resp.put("detailKeyId", request.getFisdKeyid());
            resp.put("parentId", request.getFisdParentid());
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<java.util.Map<String, Object>> getFishboneTree(FishboneTreeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is required");
        }

        String id = request.getId();
        String masterId = request.getMasterId();
        String parentId = firstNonEmpty(request.getParentId(), id);
        String problem = hasText(request.getProblemText()) ? request.getProblemText() : "Effect /Problem";

        if (!hasText(masterId)) {
            throw new IllegalArgumentException("masterId is required");
        }

        // Root node for id == "0" or null
        if (!hasText(id) || "0".equals(id)) {
            java.util.Map<String, Object> root = new java.util.LinkedHashMap<>();
            root.put("id", ROOT_PARENT_ID);
            root.put("displayCode", problem);
            root.put("originalId", "1");
            root.put("elementId", "1");
            root.put("elementType", masterId);
            root.put("parentId", "{}");
            root.put("orderNo", 0);
            root.put("levelNo", 0);
            root.put("title", problem);
            root.put("state", "closed");
            root.put("icon", "");
            return java.util.List.of(root);
        }

        if (!hasText(parentId)) {
            throw new IllegalArgumentException("parentId is required when id is not root");
        }

        java.util.List<java.util.Map<String, Object>> rows = repository.findChildren(masterId, parentId);
        java.util.List<java.util.Map<String, Object>> nodes = new java.util.ArrayList<>();
        int idx = 0;
        for (java.util.Map<String, Object> row : rows) {
            java.util.Map<String, Object> n = new java.util.LinkedHashMap<>();
            String keyId = (String) row.get("fisdKeyid");
            String cause = (String) row.get("fisdCause");
            n.put("id", keyId);
            n.put("displayCode", cause);
            n.put("originalId", keyId);
            n.put("elementId", keyId);
            n.put("elementType", masterId);
            n.put("parentId", row.get("fisdParentid"));
            n.put("orderNo", row.get("fisdOrderno"));
            n.put("levelNo", row.get("fisdLevelno"));
            n.put("title", "Fish Bone");
            n.put("state", "closed");
            n.put("icon", "");
            n.put("metadataId", idx++);
            nodes.add(n);
        }
        return nodes;
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<String> searchNode(String searchNode, String originalId) {
        if (!hasText(searchNode) || !hasText(originalId)) {
            return java.util.List.of();
        }
        return repository.searchChildPath(searchNode, originalId);
    }

    @Override
    @Transactional
    public Map<String, Object> deleteChild(String detailKeyId) {
        if (!hasText(detailKeyId)) {
            throw new IllegalArgumentException("detailKeyId is required");
        }
        // delete the node and its immediate children (one level, same as legacy)
        int deletedSelf = repository.deleteDetail(detailKeyId);
        int deletedChildren = repository.deleteChildrenByParent(detailKeyId);

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("msg", deletedSelf > 0 ? "Data Deleted successfully" : "Data Not Deleted");
        resp.put("deletedChildren", deletedChildren);
        resp.put("detailKeyId", detailKeyId);
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public FishboneMasterSaveRequest getMaster(String keyId, String refDocType, String refDocId) {
        if (!hasText(keyId)) {
            throw new IllegalArgumentException("keyId is required");
        }
        FishboneMasterSaveRequest m = repository.findMasterById(keyId);
        if (m == null) {
            throw new IllegalStateException("Fishbone not found for keyId=" + keyId);
        }
        // Overlay refdoc fields if not present, matching servlet behavior
        if (!hasText(m.getFismRefdocid()) && hasText(refDocId)) {
            m.setFismRefdocid(refDocId);
        }
        if (!hasText(m.getFismRefdoctype()) && hasText(refDocType)) {
            m.setFismRefdoctype(refDocType);
        }
        return m;
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<java.util.Map<String, Object>> getReportGrid(String keyId) {
        return repository.findReportGrid(keyId);
    }

    private void validateMaster(FishboneMasterSaveRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is required");
        }
        if (!hasText(request.getFismFlid())) {
            throw new IllegalArgumentException("fismFlid is required");
        }
        if (!hasText(request.getFismTitle())) {
            throw new IllegalArgumentException("fismTitle is required");
        }
        if (!hasText(request.getFismProblem())) {
            throw new IllegalArgumentException("fismProblem is required");
        }
        if (!hasText(request.getFismPreparedby())) {
            throw new IllegalArgumentException("fismPreparedby is required");
        }
        if (!hasText(request.getFismApprovedby())) {
            throw new IllegalArgumentException("fismApprovedby is required");
        }
        if (!hasText(request.getFismCreatedby())) {
            throw new IllegalArgumentException("fismCreatedby is required");
        }
    }

    private void validateChild(FishboneChildSaveRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is required");
        }
        if (!hasText(request.getFisdFismKeyid())) {
            throw new IllegalArgumentException("masterId (fisdFismKeyid) is required");
        }
        if (isEditMode(request)) {
            return;
        }
        if (!hasText(request.getFisdCause())) {
            throw new IllegalArgumentException("fisdCause is required");
        }
        if (!hasText(request.getFisdCreatedby())) {
            throw new IllegalArgumentException("fisdCreatedby is required");
        }
    }

    private void applyMasterDefaults(FishboneMasterSaveRequest request) {
        LocalDateTime now = LocalDateTime.now();
        if (request.getFismPrepareddate() == null) request.setFismPrepareddate(now);
        if (request.getFismApproveddate() == null) request.setFismApproveddate(now);
        if (!hasText(request.getFismStatus())) request.setFismStatus("Y");
        if (!hasText(request.getFismActive())) request.setFismActive("Y");
        if (!hasText(request.getFismTempfield2())) request.setFismTempfield2("-");
        if (!hasText(request.getFismTempfield3())) request.setFismTempfield3("-");
        if (!hasText(request.getFismTempfield4())) request.setFismTempfield4("-");
        if (!hasText(request.getFismTempfield5())) request.setFismTempfield5("-");
        if (request.getFismCreatedon() == null) request.setFismCreatedon(now);
        if (request.getFismModifiedon() == null) request.setFismModifiedon(now);
    }

    private void applyChildDefaults(FishboneChildSaveRequest request) {
        LocalDateTime now = LocalDateTime.now();
        // Derive parentId from legacy flags if missing
        if (!hasText(request.getFisdParentid())) {
            String detlFlag = nullSafe(request.getDetlIdFlag());
            if ("detlid".equalsIgnoreCase(detlFlag) && hasText(request.getDetailId())) {
                request.setFisdParentid(request.getDetailId());
            } else if ("Smelvl".equalsIgnoreCase(detlFlag)) {
                if ("level".equalsIgnoreCase(nullSafe(request.getLevelFlag()))) {
                    request.setFisdParentid(ROOT_PARENT_ID);
                } else if (hasText(request.getFisdParentid())) {
                    // keep existing
                } else {
                    request.setFisdParentid(ROOT_PARENT_ID);
                }
            } else if (hasText(request.getDetailId())) {
                // fallback: use detailId if provided
                request.setFisdParentid(request.getDetailId());
            } else {
                request.setFisdParentid(ROOT_PARENT_ID);
            }
        }
        if (request.getFisdOrderno() == null) request.setFisdOrderno(1);
        if (request.getFisdLevelno() == null) request.setFisdLevelno(1);
        if (!hasText(request.getFisdTempfield1())) request.setFisdTempfield1("-");
        if (!hasText(request.getFisdTempfield2())) request.setFisdTempfield2("-");
        if (!hasText(request.getFisdTempfield3())) request.setFisdTempfield3("-");
        if (!hasText(request.getFisdTempfield4())) request.setFisdTempfield4("-");
        if (!hasText(request.getFisdTempfield5())) request.setFisdTempfield5("-");
        if (!hasText(request.getFisdActive())) request.setFisdActive("Y");
        if (!hasText(request.getFisdRemarks())) request.setFisdRemarks("-");
        if (request.getFisdCreatedon() == null) request.setFisdCreatedon(now);
        if (request.getFisdModifiedon() == null) request.setFisdModifiedon(now);
    }

    private void insertDefaultChildren(FishboneMasterSaveRequest master) {
        for (String cause : DEFAULT_CAUSES) {
            FishboneChildSaveRequest child = new FishboneChildSaveRequest();
            child.setFisdKeyid(generateDetailKey());
            child.setFisdFismKeyid(master.getFismKeyid());
            child.setFisdCause(cause);
            child.setFisdParentid(ROOT_PARENT_ID);
            child.setFisdOrderno(1);
            child.setFisdLevelno(1);
            child.setFisdTempfield1("-");
            child.setFisdTempfield2("-");
            child.setFisdTempfield3("-");
            child.setFisdTempfield4("-");
            child.setFisdTempfield5("-");
            child.setFisdActive("Y");
            child.setFisdCreatedby(master.getFismCreatedby());
            child.setFisdCreatedon(defaultDate(master.getFismCreatedon()));
            child.setFisdModifiedon(defaultDate(master.getFismModifiedon()));
            child.setFisdRemarks("-");
            repository.insertDetail(child);
        }
    }

    private String generateMasterKey() {
        try {
            return dbActionTemplate.getSequenceNumber(MASTER_TABLE, KEY_LENGTH, MASTER_PREFIX, DATE_FORMAT, FORMAT_RESET);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate master key: " + e.getMessage(), e);
        }
    }

    private String generateDetailKey() {
        try {
            return dbActionTemplate.getSequenceNumber(DETAIL_TABLE, KEY_LENGTH, DETAIL_PREFIX, DATE_FORMAT, FORMAT_RESET);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate detail key: " + e.getMessage(), e);
        }
    }

    private boolean isEditMode(FishboneChildSaveRequest request) {
        return request.getEditMode() != null && "EDITVAL".equalsIgnoreCase(request.getEditMode());
    }

    private boolean hasKey(String key) {
        return hasText(key);
    }

    private boolean hasText(String value) {
        if (value == null) return false;
        String v = value.trim();
        if (v.isEmpty()) return false;
        return !v.equals("{}") && !v.equalsIgnoreCase("null");
    }

    private String nullSafe(String value) {
        return value == null ? "" : value;
    }

    private String firstNonEmpty(String a, String b) {
        if (hasText(a)) return a;
        if (hasText(b)) return b;
        return null;
    }

    private LocalDateTime defaultDate(LocalDateTime dateTime) {
        return dateTime == null ? LocalDateTime.now() : dateTime;
    }
}
