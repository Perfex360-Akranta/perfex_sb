package com.akranta.perfex_sb.service.impl;

//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.JhaTlAuditParameterDto;
import com.akranta.perfex_sb.dto.JhaTlAuditTemplateDto;
//import com.akranta.perfex_sb.dto.JhaTlAudituploadDto;
//import com.akranta.perfex_sb.model.JhaTlAuditmst;
import com.akranta.perfex_sb.model.JhaTlAuditparameter;
import com.akranta.perfex_sb.model.JhaTlAudittemplate;
import com.akranta.perfex_sb.model.JhaTlTemplatelevellink;
import com.akranta.perfex_sb.repository.JhaTlAuditParameterRepository;
//import com.akranta.perfex_sb.repository.JhaTlAuditmstRepository;
import com.akranta.perfex_sb.repository.JhaTlAudittemplateRepository;
import com.akranta.perfex_sb.repository.JhaTlTemplatelevellinkRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.JhaTlAuditParameterService;

@Service
public class JhaTlAuditParameterServiceImpl implements JhaTlAuditParameterService {

    @Autowired
    private JhaTlAudittemplateRepository repository;

    @Autowired
private JhaTlTemplatelevellinkRepository templateLevelRepository;


    @Autowired
    private JhaTlAuditParameterRepository parameterrepositotry;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "JHA_TL_AUDITTEMPLATE";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "JHT";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    @Override
    public List<JhaTlAuditParameterDto> getJhAuditParameterGrid(String templateId) {
        List<Object[]> results = repository.findAuditParametersByMasterId(templateId);
         //List<Object[]> results = repository.findAuditParametersByMasterId(templateId);
        List<JhaTlAuditParameterDto> dtoList = new ArrayList<>();

        for (Object[] row : results) {
            JhaTlAuditParameterDto dto = new JhaTlAuditParameterDto();
            dto.setJautKeyid(row[0] != null ? row[0].toString() : "");
            dto.setJautReviewptslno(row[1] != null ? row[1].toString() : "");
            dto.setParamname(row[2] != null ? row[2].toString() : "");
            dto.setJautCriteriaslno(row[3] != null ? row[3].toString() : "");
            dto.setParamdesc(row[4] != null ? row[4].toString() : "");
            dto.setEvidence(row[5] != null ? row[5].toString() : "");
            dto.setMaxpoint(row[6] != null ? (Integer) row[6] : null);
            dto.setDelet("");
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public JhaTlAuditparameter getParameterByKeyid(String keyid) {
        try {
            // logger.info("Fetching audit by keyid: {}", keyid);

            if (!isValidKeyId(keyid)) {
                throw new IllegalArgumentException("Invalid keyid provided");
            }

            // Find master record by keyid
            JhaTlAuditparameter jhalAuditparameter = parameterrepositotry.findById(keyid).orElse(null);
            // .orElseThrow(() -> new RuntimeException("Audit not found with keyid: " +
            // keyid));

            // Build response DTO
            // JhaTlAuditParameterDto result = new JhaTlAuditParameterDto();

            return jhalAuditparameter;

        } catch (IllegalArgumentException e) {
            // logger.error("Validation error fetching audit: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // logger.error("Error fetching audit by keyid {}: {}", keyid, e.getMessage(),
            // e);
            throw new RuntimeException("Failed to fetch audit: " + e.getMessage(), e);
        }
    }

    /************************************************************************************* */

    @Transactional
    public JhaTlAuditTemplateDto createOrUpdateTemplate(JhaTlAuditTemplateDto jhaTlAuditTemplateDto) {
        try {
            if (jhaTlAuditTemplateDto == null) {
                throw new IllegalArgumentException("Audit data cannot be null");
            }

            JhaTlAudittemplate jhaTlAudittemplate = jhaTlAuditTemplateDto.getJhaTlAudittemplate();

            if (jhaTlAudittemplate == null) {
                throw new IllegalArgumentException("Audit master data cannot be null");
            }

            // Handle master record
           JhaTlAudittemplate jhaTlAudittemplateResult = saveTemplateRecord(jhaTlAudittemplate);

            // Prepare response
            JhaTlAuditTemplateDto response = new JhaTlAuditTemplateDto();
            response.setJhaTlAudittemplate(jhaTlAudittemplateResult);

            return response;

        } catch (IllegalArgumentException e) {
            // logger.error("Validation error creating/updating audit: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // logger.error("Error creating/updating audit: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create/update audit: " + e.getMessage(), e);
        }
    }

    // ==========================================
    // PRIVATE HELPER METHODS
    // ==========================================

    private JhaTlAudittemplate saveTemplateRecord(JhaTlAudittemplate jhaTlAudittemplate) {
        if (jhaTlAudittemplate.getKeyid() == null || jhaTlAudittemplate.getKeyid().trim().isEmpty()) {
            // Generate new ID for insert
            try {
                String newMstKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET);

                if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                    throw new IllegalStateException("Failed to generate Master Key ID - sequence returned null");
                }

                jhaTlAudittemplate.setKeyid(newMstKeyid);
                jhaTlAudittemplate.setCreatedon(LocalDateTime.now());
                // logger.info("Generated new Master Key ID: {}", newMstKeyid);

            } catch (Exception e) {
                throw new IllegalStateException("Failed to generate Master Key ID: " + e.getMessage(), e);
            }
        } else {
            // UPDATE - else block added
            if (repository.existsById(jhaTlAudittemplate.getKeyid())) {
                jhaTlAudittemplate.setModifiedon(LocalDateTime.now());
                JhaTlAudittemplate updateEntity = repository.save(jhaTlAudittemplate);
                // logger.info("Successfully updated Audit with Key ID: {}",
                // updateEntity.getKeyid());
                return updateEntity;
            }
        }

        jhaTlAudittemplate.setModifiedon(LocalDateTime.now());

        // Save master (handles both insert and update)
        JhaTlAudittemplate savedEntity = repository.save(jhaTlAudittemplate);
        // logger.info("Saved Master with Key ID: {}", savedEntity.getKeyid());

        return savedEntity;
    }

    // private boolean isValidKeyId(String keyId) {
    //     return keyId != null && !keyId.trim().isEmpty() && !keyId.equalsIgnoreCase("null");
    // }
    private boolean isValidKeyId(String keyId) {
    if (keyId == null) return false;

    keyId = keyId.trim();
    if (keyId.isEmpty() || keyId.equalsIgnoreCase("null")) return false;

    // 🚫 Reject pillar codes
    if (keyId.equalsIgnoreCase("GEN")) return false;

    // ✅ Accept only real system keys
    return keyId.startsWith("JH");
}




//     @Override
// public List<JhaTlTemplatelevellink> getAuditLevels(
//         String templateId,
//         String jhStepId) {

//     String validTemplateId =
//             isValidKeyId(templateId) ? templateId : null;

//     String validJhStepId =
//             isValidKeyId(jhStepId) ? jhStepId : null;

//     return templateLevelRepository.findAuditLevels(
//             validTemplateId,
//             validJhStepId
//     );
// }


@Override
    public List<JhaTlTemplatelevellink> getAuditLevels(String templateId, String flId,String jhStepId) {
        // Pass parameters as-is (empty string or null will be ignored by query)
        return templateLevelRepository.findAuditLevels(
            templateId == null ? "" : templateId,
            jhStepId == null ? "" : jhStepId
        );
    }


    @Override
public Integer getMinimumMarks(String auditLevel, String auditTemplate) {
    try {
        if (!isValidKeyId(auditLevel) || !isValidKeyId(auditTemplate)) {
            return 0;
        }
        
        Integer minPoints = templateLevelRepository.findMinimumPoints(auditLevel, auditTemplate);
        return minPoints != null ? minPoints : 0;
        
    } catch (Exception e) {
        // logger.error("Error getting minimum marks: {}", e.getMessage());
        return 0;
    }
}








}




