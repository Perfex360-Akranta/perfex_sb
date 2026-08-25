package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.*;
import com.akranta.perfex_sb.repository.*;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.WorksheetService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.WorksheetRequest;

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
public class WorksheetServiceImpl implements WorksheetService {

    private static final Logger logger = LoggerFactory.getLogger(WorksheetServiceImpl.class);

    private final StdTlStdworksheetmstRepository masterRepository;
    private final StdTlStdworksheetdtlRepository detailRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER_MASTER = "STD_TL_STDWORKSHEETMST";
    private static final String SEQ_IDENTIFIER_DETAIL = "STD_TL_STDWORKSHEETDTL";
    
    private static final int KEY_LENGTH_MASTER = 16;
    private static final int KEY_LENGTH_DETAIL = 16;
    
    private static final String PREFIX_MASTER = "STWS";
    private static final String PREFIX_DETAIL = "STWD";
    
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    public WorksheetServiceImpl(
        StdTlStdworksheetmstRepository masterRepository,
        StdTlStdworksheetdtlRepository detailRepository,
        DbActionTemplate dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<WorksheetRequest> saveWorksheet(WorksheetRequest request) throws Exception {
        StdTlStdworksheetmst master = request.getMaster();
        List<StdTlStdworksheetdtl> details = request.getDetails();

        if (master == null) {
            throw new RuntimeException("No Worksheet Master Details");
        }

        WorksheetRequest result = new WorksheetRequest();

        // CHECK IF INSERT OR UPDATE
        if (master.getKeyid() == null || master.getKeyid().trim().isEmpty()) {
            // INSERT MODE
            String newMstKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER_MASTER, KEY_LENGTH_MASTER, PREFIX_MASTER, DATE_FORMAT, FORMAT_RESET
            );

            if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                logger.error("Failed to generate the Master Key ID");
                throw new RuntimeException("Failed to generate Master Key ID");
            }

            master.setKeyid(newMstKeyid);
            if (master.getCreatedon() == null) {
                master.setCreatedon(LocalDateTime.now());
            }
            master.setModifiedon(LocalDateTime.now());

            logger.info("Generated new Master Key ID: {}", newMstKeyid);

        } else {
            // UPDATE MODE
            if (masterRepository.existsById(master.getKeyid())) {
                WorksheetRequest updateResult = new WorksheetRequest();
                
                StdTlStdworksheetmst existingMaster = masterRepository.findById(master.getKeyid())
                    .orElseThrow(() -> new ResourceNotFoundException("Worksheet not found"));

                // Update all master fields
                existingMaster.setDate(master.getDate());
                existingMaster.setBy(master.getBy());
                existingMaster.setApprovedby(master.getApprovedby());
                existingMaster.setFlid(master.getFlid());
                existingMaster.setElementid(master.getElementid());
                existingMaster.setProcess(master.getProcess());
                existingMaster.setBudgetedtime(master.getBudgetedtime());
                existingMaster.setType(master.getType());
                existingMaster.setCycletime(master.getCycletime());
                existingMaster.setTempfield3(master.getTempfield3());
                existingMaster.setTempfield4(master.getTempfield4());
                existingMaster.setTempfield5(master.getTempfield5());
                existingMaster.setActive(master.getActive());
                existingMaster.setModifiedon(LocalDateTime.now());

                StdTlStdworksheetmst updatedMaster = masterRepository.save(existingMaster);
                updateResult.setMaster(updatedMaster);
                logger.info("Successfully updated Master with Key ID: {}", updatedMaster.getKeyid());

                // Handle Details in update mode
                List<StdTlStdworksheetdtl> resultDetails = new ArrayList<>();
                if (details != null && !details.isEmpty()) {
                    for (StdTlStdworksheetdtl detail : details) {
                        if (detail.getKeyid() == null || detail.getKeyid().trim().isEmpty() || 
                            detail.getKeyid().equals("undefined")) {
                            // New detail - INSERT
                            detail.setStws_keyid(master.getKeyid());
                            String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                                SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET
                            );
                            detail.setKeyid(newDetailKeyid);
                            
                            // Set createdby from master if not set
                            if (detail.getCreatedby() == null || detail.getCreatedby().trim().isEmpty()) {
                                detail.setCreatedby(master.getCreatedby());
                            }
                            detail.setCreatedon(LocalDateTime.now());
                            detail.setModifiedon(LocalDateTime.now());
                            
                            StdTlStdworksheetdtl savedDetail = detailRepository.save(detail);
                            resultDetails.add(savedDetail);
                            logger.info("Successfully created Detail with Key: {}", newDetailKeyid);
                        } else {
                            // Existing detail - UPDATE
                            detail.setModifiedon(LocalDateTime.now());
                            StdTlStdworksheetdtl savedDetail = detailRepository.save(detail);
                            resultDetails.add(savedDetail);
                            logger.info("Successfully updated Detail with Key: {}", detail.getKeyid());
                        }
                    }
                }
                updateResult.setDetails(resultDetails);

                return ResponseEntity.status(HttpStatus.OK).body(updateResult);
            }
        }

        // INSERT MODE - Save Master
        StdTlStdworksheetmst savedMaster = masterRepository.save(master);
        List<StdTlStdworksheetdtl> savedDetailList = new ArrayList<>();

        // Save Details
        if (details != null && !details.isEmpty()) {
            for (StdTlStdworksheetdtl detail : details) {
                detail.setStws_keyid(savedMaster.getKeyid());
                String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET
                );

                if (newDetailKeyid == null || newDetailKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate Detail Key ID");
                    throw new RuntimeException("Failed to generate Detail Key ID");
                }

                detail.setKeyid(newDetailKeyid);
                
                // Ensure createdby is set
                if (detail.getCreatedby() == null || detail.getCreatedby().trim().isEmpty()) {
                    detail.setCreatedby(savedMaster.getCreatedby());
                }
                
                if (detail.getCreatedon() == null) {
                    detail.setCreatedon(LocalDateTime.now());
                }
                detail.setModifiedon(LocalDateTime.now());

                StdTlStdworksheetdtl savedDetail = detailRepository.save(detail);
                savedDetailList.add(savedDetail);
                logger.info("Successfully created Detail with Key: {}", newDetailKeyid);
            }
        }

        result.setMaster(savedMaster);
        result.setDetails(savedDetailList);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    @Transactional(readOnly = true)
    public WorksheetRequest getCompleteWorksheetData(String masterKeyid) {
        logger.info("Fetching complete Worksheet data for Master Key ID: {}", masterKeyid);

        StdTlStdworksheetmst master = masterRepository.findById(masterKeyid)
                .orElseThrow(() -> new ResourceNotFoundException("Worksheet not found for keyid: " + masterKeyid));
        
        List<StdTlStdworksheetdtl> details = detailRepository.findByStwsKeyid(masterKeyid);
        
        WorksheetRequest response = new WorksheetRequest(master, details);
        
        logger.info("Successfully retrieved complete Worksheet data for Master: {}", masterKeyid);
        return response;
    }
}