package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.PhenomenaComboRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaFactoryGridRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaFactoryLinkItemDto;
import com.akranta.perfex_sb.dto.PhenomenaFactoryMappingSaveRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaLossGridRequestDto;
import com.akranta.perfex_sb.dto.PhenomenaLossSaveRequestDto;
import com.akranta.perfex_sb.repository.PhenomenaLossRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.PhenomenaLossService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PhenomenaLossServiceImpl implements PhenomenaLossService {

    private static final String SEQ_IDENTIFIER = "PCS_TL_LOSSPHENOMENAMST";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX = "PLPM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String FACTORY_SEQ_IDENTIFIER = "PCS_TL_LOSSPHENFACTORYLINK";
    private static final int FACTORY_KEY_LENGTH = 15;
    private static final String FACTORY_PREFIX = "PPFL";
    private static final String FACTORY_DATE_FORMAT = "YY";
    private static final String FACTORY_FORMAT_RESET = "Y";

    private final PhenomenaLossRepository repository;
    private final DbActionTemplate dbActionTemplate;

    public PhenomenaLossServiceImpl(PhenomenaLossRepository repository, DbActionTemplate dbActionTemplate) {
        this.repository = repository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getPhenomenaGrid(PhenomenaLossGridRequestDto dto) {
        if (dto == null) throw new IllegalArgumentException("PhenomenaLossGridRequestDto is null");
        int from = dto.getFromRow() != null ? dto.getFromRow() : 1;
        long total = repository.countPhenomena(dto.getPhenId(), dto.getLossId());
        int to = dto.getToRow() != null ? dto.getToRow() : (int) total;
        if (from < 1) from = 1;
        if (to < from) to = from;

        List<Map<String, Object>> rows = repository.findPhenomena(dto.getPhenId(), dto.getLossId(), from, to);

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("rows", rows);
        resp.put("totalRecords", total);
        resp.put("fromRow", from);
        resp.put("toRow", to);
        return resp;
    }

    @Override
    @Transactional
    public Map<String, Object> savePhenomenaLoss(PhenomenaLossSaveRequestDto dto) {
        if (dto == null) throw new IllegalArgumentException("PhenomenaLossSaveRequestDto is null");
        LocalDateTime now = LocalDateTime.now();

        String key = dto.getPlpmKeyid();
        if (!isValidKey(key)) {
            key = generateKey();
            dto.setPlpmKeyid(key);
        }
        if (dto.getPlpmActive() == null || dto.getPlpmActive().isBlank()) {
            dto.setPlpmActive("Y");
        }
        if (dto.getPlpmTempfield1() == null) dto.setPlpmTempfield1("-");
        if (dto.getPlpmTempfield2() == null) dto.setPlpmTempfield2("-");
        if (dto.getPlpmTempfield3() == null) dto.setPlpmTempfield3("-");
        if (dto.getPlpmCreatedon() == null) dto.setPlpmCreatedon(now);
        if (dto.getPlpmModifiedon() == null) dto.setPlpmModifiedon(now);

        Map<String, Object> values = new LinkedHashMap<>();
        values.put("plpm_keyid", dto.getPlpmKeyid());
        values.put("plpm_name", dto.getPlpmName());
        values.put("plpm_mainloss", dto.getPlpmMainloss());
        values.put("plpm_tempfield1", dto.getPlpmTempfield1());
        values.put("plpm_tempfield2", dto.getPlpmTempfield2());
        values.put("plpm_tempfield3", dto.getPlpmTempfield3());
        values.put("plpm_active", dto.getPlpmActive());
        values.put("plpm_createdby", dto.getPlpmCreatedby());
        values.put("plpm_createdon", dto.getPlpmCreatedon());
        values.put("plpm_modifiedon", dto.getPlpmModifiedon());

        int affected;
        if (isValidKey(dto.getPlpmKeyid()) && dtoExists(dto.getPlpmKeyid())) {
            affected = repository.updatePhenomena(values);
        } else {
            affected = repository.insertPhenomena(values);
        }
        if (affected <= 0) {
            throw new IllegalStateException("No rows affected while saving phenomena loss");
        }

        // factory links (replace existing set if provided)
        if (dto.getFactoryIds() != null) {
            repository.deleteFactoryLinks(dto.getPlpmKeyid());
            for (String factoryId : dto.getFactoryIds()) {
                if (factoryId == null || factoryId.trim().isEmpty()) continue;
                Map<String, Object> link = new LinkedHashMap<>();
                link.put("ppfl_keyid", generateFactoryKey());
                link.put("ppfl_plpm_keyid", dto.getPlpmKeyid());
                link.put("ppfl_factoryid", factoryId);
                link.put("ppfl_tempfield1", "-");
                link.put("ppfl_tempfield2", "-");
                link.put("ppfl_active", "Y");
                link.put("ppfl_createdby", dto.getPlpmCreatedby());
                link.put("ppfl_createdon", dto.getPlpmCreatedon());
                link.put("ppfl_modifiedon", dto.getPlpmModifiedon());
                repository.insertFactoryLink(link);
            }
        }

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("msg", "Saved successfully");
        resp.put("mstKeyid", dto.getPlpmKeyid());
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getComboTextContent(PhenomenaComboRequestDto dto) {
        if (dto == null) throw new IllegalArgumentException("PhenomenaComboRequestDto is null");
        return repository.getComboTextContent(dto.getKeyId(), dto.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getFactoryGrid(PhenomenaFactoryGridRequestDto dto) {
        if (dto == null) throw new IllegalArgumentException("PhenomenaFactoryGridRequestDto is null");
        int from = dto.getFromRow() != null ? dto.getFromRow() : 1;
        long total = repository.countFactoryGrid(dto.getLossId(), dto.getPhenId());
        int to = dto.getToRow() != null ? dto.getToRow() : (int) total;
        if (from < 1) from = 1;
        if (to < from) to = from;

        List<Map<String, Object>> rows = repository.findFactoryGrid(dto.getLossId(), dto.getPhenId(), from, to);
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("rows", rows);
        resp.put("totalRecords", total);
        resp.put("fromRow", from);
        resp.put("toRow", to);
        return resp;
    }

    @Override
    @Transactional
    public Map<String, Object> deletePhenomenaLoss(String plpmKeyid) {
        if (!isValidKey(plpmKeyid)) {
            throw new IllegalArgumentException("plpmKeyid is required");
        }
        int linked = repository.countLinksForPhenomena(plpmKeyid);
        if (linked > 0) {
            throw new IllegalStateException("Cannot delete. This Phenomena is mapped to Functional Location(s) and must be unmapped first.");
        }
        int affected = repository.deletePhenomena(plpmKeyid);
        if (affected <= 0) {
            throw new IllegalStateException("No rows deleted for plpmKeyid=" + plpmKeyid);
        }
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("msg", "Data Deleted Successfully");
        resp.put("mstKeyid", plpmKeyid);
        return resp;
    }

    @Override
    @Transactional
    public Map<String, Object> savePhenomenaFactoryMapping(PhenomenaFactoryMappingSaveRequestDto dto) {
        if (dto == null) throw new IllegalArgumentException("PhenomenaFactoryMappingSaveRequestDto is null");
        LocalDateTime now = LocalDateTime.now();
        if (dto.getLinks() == null || dto.getLinks().isEmpty()) {
            throw new IllegalArgumentException("links are required");
        }
        for (PhenomenaFactoryLinkItemDto item : dto.getLinks()) {
            if (!isValidKey(item.getPpflPlpmKeyid()) || !isValidKey(item.getPpflFactoryid())) {
                throw new IllegalArgumentException("phenomena id and factory id are required for each link");
            }
            boolean deleteFlag = "Y".equalsIgnoreCase(item.getIsDelete());
            if (deleteFlag) {
                int used = repository.countLossCaptureForPhenomenaFactory(item.getPpflPlpmKeyid(), item.getPpflFactoryid());
                if (used > 0) {
                    throw new IllegalStateException(item.getPpflPlpmKeyid() + " Phenomena in " + item.getPpflFactoryid() + " already referred in loss entry");
                }
                repository.deleteFactoryLink(item.getPpflPlpmKeyid(), item.getPpflFactoryid());
            } else {
                Map<String, Object> link = new LinkedHashMap<>();
                link.put("ppfl_keyid", generateFactoryKey());
                link.put("ppfl_plpm_keyid", item.getPpflPlpmKeyid());
                link.put("ppfl_factoryid", item.getPpflFactoryid());
                link.put("ppfl_tempfield1", "-");
                link.put("ppfl_tempfield2", "-");
                link.put("ppfl_active", "Y");
                link.put("ppfl_createdby", dto.getCreatedBy());
                link.put("ppfl_createdon", now);
                link.put("ppfl_modifiedon", now);
                repository.insertFactoryLink(link);
            }
        }
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("msg", "Saved successfully");
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> validatePhenomenaLink(String phenId, String factoryId) {
        if (!isValidKey(phenId) || !isValidKey(factoryId)) {
            throw new IllegalArgumentException("phenomena id and factory id are required");
        }
        int cnt = repository.countLossCaptureForPhenomenaFactory(phenId, factoryId);
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("isValidate", cnt == 0);
        if (cnt > 0) {
            resp.put("msg", "Phenomena already referred in loss entry for this location");
        }
        return resp;
    }

    private boolean dtoExists(String key) {
        // cheap existence check using count
        return repository.countPhenomena(key, null) > 0;
    }

    private boolean isValidKey(String key) {
        return key != null && !key.trim().isEmpty() && !"{}".equals(key) && !"null".equalsIgnoreCase(key);
    }

    private String generateKey() {
        try {
            return dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate sequence for phenomena loss: " + e.getMessage(), e);
        }
    }

    private String generateFactoryKey() {
        try {
            return dbActionTemplate.getSequenceNumber(FACTORY_SEQ_IDENTIFIER, FACTORY_KEY_LENGTH, FACTORY_PREFIX, FACTORY_DATE_FORMAT, FACTORY_FORMAT_RESET);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate sequence for factory link: " + e.getMessage(), e);
        }
    }
}
