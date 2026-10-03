package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.BAL_PlmTlSparedtl;
import com.akranta.perfex_sb.repository.BAL_PlmTlSparedtlRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.BAL_PlmTlSparedtlService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.BAL_PlmTlSparedtlRequest;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BAL_PlmTlSparedtlServiceImpl implements BAL_PlmTlSparedtlService {

    private static final Logger logger = LoggerFactory.getLogger(BAL_PlmTlSparedtlServiceImpl.class);

    private final BAL_PlmTlSparedtlRepository repository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "PLM_TL_SPAREDTL";
    private static final int KEY_LENGTH = 12;
    private static final String PREFIX = "PMS";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    public BAL_PlmTlSparedtlServiceImpl(
        BAL_PlmTlSparedtlRepository repository,
        DbActionTemplate dbActionTemplate) {
        this.repository = repository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<BAL_PlmTlSparedtlRequest> saveSparesPickup(BAL_PlmTlSparedtlRequest request) throws Exception {

        List<BAL_PlmTlSparedtl> details = request.getDetails();
        String standardId = request.getStandardId();

        if (standardId == null || standardId.trim().isEmpty()) {
            throw new RuntimeException("Standard Id is required to save Spares Pickup");
        }
        if (details == null || details.isEmpty()) {
            throw new RuntimeException("No Spares Pickup Details");
        }

        BAL_PlmTlSparedtlRequest result = new BAL_PlmTlSparedtlRequest();
        List<BAL_PlmTlSparedtl> savedDetailList = new ArrayList<>();

        for (BAL_PlmTlSparedtl detail : details) {

            boolean isInsertMode = detail.getKeyid() == null ||
                                  detail.getKeyid().trim().isEmpty() ||
                                  detail.getKeyid().equals("{}") ||
                                  detail.getKeyid().equals("undefined") ||
                                  !repository.existsById(detail.getKeyid());

            if (isInsertMode) {
                // INSERT MODE
                String newKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET
                );

                if (newKeyid == null || newKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate the Spares Pickup Key ID");
                    throw new RuntimeException("Failed to generate Spares Pickup Key ID");
                }

                detail.setKeyid(newKeyid);
                detail.setStandardid(standardId);

                if (detail.getCreatedby() == null || detail.getCreatedby().trim().isEmpty()) {
                    detail.setCreatedby(request.getCreatedBy());
                }
                if (detail.getCreatedon() == null) {
                    detail.setCreatedon(LocalDateTime.now());
                }
                detail.setModifiedon(LocalDateTime.now());

                BAL_PlmTlSparedtl savedDetail = repository.save(detail);
                savedDetailList.add(savedDetail);
                logger.info("Successfully created Spares Pickup Detail with Key: {}", newKeyid);

            } else {
                // UPDATE MODE
                BAL_PlmTlSparedtl existingDetail = repository.findById(detail.getKeyid())
                    .orElseThrow(() -> new ResourceNotFoundException("Spares Pickup Detail not found"));

                existingDetail.setStandardid(standardId);
                existingDetail.setSpareid(detail.getSpareid());
                existingDetail.setQuantity(detail.getQuantity());
                existingDetail.setModifiedon(LocalDateTime.now());

                BAL_PlmTlSparedtl savedDetail = repository.save(existingDetail);
                savedDetailList.add(savedDetail);
                logger.info("Successfully updated Spares Pickup Detail with Key: {}", existingDetail.getKeyid());
            }
        }

        result.setStandardId(standardId);
        result.setDetails(savedDetailList);
        result.setFormActionMode(request.getFormActionMode());
        result.setFormMode(request.getFormMode());
        result.setFormHeader(request.getFormHeader());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
@Transactional(readOnly = true)
public ResponseEntity<List<Map<String, Object>>> getSprPickup(String standardId) throws Exception {
    logger.info("Fetching Spares Pickup data for standardId: {}", standardId);

    List<Map<String, Object>> sprPkupList = repository.getSprPickup(standardId);

    logger.info("Fetched {} Spares Pickup row(s) for standardId: {}", sprPkupList.size(), standardId);
    return ResponseEntity.status(HttpStatus.OK).body(sprPkupList);
}
}