package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.FieldAuditSheetmst;
import com.akranta.perfex_sb.model.FieldAuditSheetdtl;
import com.akranta.perfex_sb.repository.FieldAuditSheetmstRepository;
import com.akranta.perfex_sb.repository.FieldAuditSheetdtlRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.FieldAuditSheetService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.FieldAuditSheetRequest;

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
public class FieldAuditSheetServiceImpl implements FieldAuditSheetService {

    private static final Logger logger = LoggerFactory.getLogger(FieldAuditSheetServiceImpl.class);

    private final FieldAuditSheetmstRepository masterRepository;
    private final FieldAuditSheetdtlRepository detailRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER_MASTER = "JHA_TL_FIELDAUDITSHEETMST";
    private static final String SEQ_IDENTIFIER_DETAIL = "JHA_TL_FIELDAUDITSHEETDTL";
    
    private static final int KEY_LENGTH_MASTER = 14;
    private static final int KEY_LENGTH_DETAIL = 14;
    
    private static final String PREFIX_MASTER = "FASM";
    private static final String PREFIX_DETAIL = "FASD";
    
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    public FieldAuditSheetServiceImpl(
        FieldAuditSheetmstRepository masterRepository,
        FieldAuditSheetdtlRepository detailRepository,
        DbActionTemplate dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

   @Override
@Transactional
public ResponseEntity<FieldAuditSheetRequest> saveFieldAuditSheet(FieldAuditSheetRequest request) throws Exception {
    FieldAuditSheetmst master = request.getMaster();
    List<FieldAuditSheetdtl> details = request.getDetails();

    if (master == null) {
        throw new RuntimeException("No Field Audit Sheet Master Details");
    }

    FieldAuditSheetRequest result = new FieldAuditSheetRequest();

    // CHECK IF INSERT OR UPDATE
    String keyid = master.getKeyid();
    boolean isInsertMode = keyid == null || 
                          keyid.trim().isEmpty() || 
                          keyid.equals("{}") || 
                          keyid.equals("undefined") ||
                          !masterRepository.existsById(keyid);
    
    if (isInsertMode) {
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
        
        // Save Master
        FieldAuditSheetmst savedMaster = masterRepository.save(master);
        List<FieldAuditSheetdtl> savedDetailList = new ArrayList<>();

        // Save Details
        if (details != null && !details.isEmpty()) {
            for (FieldAuditSheetdtl detail : details) {
                detail.setMasterid(savedMaster.getKeyid());
                String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET
                );

                if (newDetailKeyid == null || newDetailKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate Detail Key ID");
                    throw new RuntimeException("Failed to generate Detail Key ID");
                }

                detail.setKeyid(newDetailKeyid);
                
                if (detail.getCreatedby() == null || detail.getCreatedby().trim().isEmpty()) {
                    detail.setCreatedby(savedMaster.getCreatedby());
                }
                
                if (detail.getCreatedon() == null) {
                    detail.setCreatedon(LocalDateTime.now());
                }
                detail.setModifiedon(LocalDateTime.now());

                FieldAuditSheetdtl savedDetail = detailRepository.save(detail);
                savedDetailList.add(savedDetail);
                logger.info("Successfully created Detail with Key: {}", newDetailKeyid);
            }
        }

        result.setMaster(savedMaster);
        result.setDetails(savedDetailList);
        result.setFormActionMode(request.getFormActionMode());
        result.setFormMode(request.getFormMode());
        result.setFormHeader(request.getFormHeader());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    } else {
        // UPDATE MODE
        FieldAuditSheetRequest updateResult = new FieldAuditSheetRequest();
        
        FieldAuditSheetmst existingMaster = masterRepository.findById(master.getKeyid())
            .orElseThrow(() -> new ResourceNotFoundException("Field Audit Sheet not found"));

        // Update all master fields
        existingMaster.setFlid(master.getFlid());
        existingMaster.setDate(master.getDate());
        existingMaster.setJobdesc(master.getJobdesc());
        existingMaster.setShift(master.getShift());
        existingMaster.setSerprovider(master.getSerprovider());
        existingMaster.setViolations(master.getViolations());
        existingMaster.setEvaluatedby(master.getEvaluatedby());
        existingMaster.setNoofesp(master.getNoofesp());
        existingMaster.setDonedmt(master.getDonedmt());
        existingMaster.setDonejh(master.getDonejh());
        existingMaster.setTradeid(master.getTradeid());
        existingMaster.setTempfield1(master.getTempfield1());
        existingMaster.setTempfield2(master.getTempfield2());
        existingMaster.setTempfield3(master.getTempfield3());
        existingMaster.setTempfield4(master.getTempfield4());
        existingMaster.setTempfield5(master.getTempfield5());
        existingMaster.setActive(master.getActive());
        existingMaster.setModifiedon(LocalDateTime.now());

        FieldAuditSheetmst updatedMaster = masterRepository.save(existingMaster);
        updateResult.setMaster(updatedMaster);
        logger.info("Successfully updated Master with Key ID: {}", updatedMaster.getKeyid());

        // Handle Details in update mode
        List<FieldAuditSheetdtl> resultDetails = new ArrayList<>();
        if (details != null && !details.isEmpty()) {
            for (FieldAuditSheetdtl detail : details) {
                if (detail.getKeyid() == null || detail.getKeyid().trim().isEmpty() || 
                    detail.getKeyid().equals("undefined") || detail.getKeyid().equals("{}")) {
                    detail.setMasterid(master.getKeyid());
                    String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET
                    );
                    detail.setKeyid(newDetailKeyid);
                    
                    if (detail.getCreatedby() == null || detail.getCreatedby().trim().isEmpty()) {
                        detail.setCreatedby(master.getCreatedby());
                    }
                    detail.setCreatedon(LocalDateTime.now());
                    detail.setModifiedon(LocalDateTime.now());
                    FieldAuditSheetdtl savedDetail = detailRepository.save(detail);
                    resultDetails.add(savedDetail);
                } else {
                    // Update existing detail
                    FieldAuditSheetdtl existingDetail = detailRepository.findById(detail.getKeyid())
                        .orElseThrow(() -> new ResourceNotFoundException("Detail not found"));
                    
                    existingDetail.setMasterid(master.getKeyid());
                    existingDetail.setEspid(detail.getEspid());
                    existingDetail.setPpeid(detail.getPpeid());
                    existingDetail.setPpecondition(detail.getPpecondition());
                    existingDetail.setTools(detail.getTools());
                    existingDetail.setWorkpermitsafety(detail.getWorkpermitsafety());
                    existingDetail.setKnowledge(detail.getKnowledge());
                    existingDetail.setRemarks(detail.getRemarks());
                    existingDetail.setEspothers(detail.getEspothers());
                    existingDetail.setTempfield2(detail.getTempfield2());
                    existingDetail.setTempfield3(detail.getTempfield3());
                    existingDetail.setTempfield4(detail.getTempfield4());
                    existingDetail.setTempfield5(detail.getTempfield5());
                    existingDetail.setActive(detail.getActive());
                    existingDetail.setModifiedon(LocalDateTime.now());
                    
                    FieldAuditSheetdtl savedDetail = detailRepository.save(existingDetail);
                    resultDetails.add(savedDetail);
                }
            }
        }
        updateResult.setDetails(resultDetails);

        updateResult.setFormActionMode(request.getFormActionMode());
        updateResult.setFormMode(request.getFormMode());
        updateResult.setFormHeader(request.getFormHeader());

        return ResponseEntity.status(HttpStatus.OK).body(updateResult);
    }
}
    @Override
    @Transactional(readOnly = true) 
    public FieldAuditSheetRequest getCompleteFieldAuditSheetData(String masterKeyid) {
        logger.info("Fetching complete Field Audit Sheet data for Master Key ID: {}", masterKeyid);

        FieldAuditSheetmst master = masterRepository.findById(masterKeyid)
                .orElseThrow(() -> new ResourceNotFoundException("Field Audit Sheet not found for keyid: " + masterKeyid));
        
        List<FieldAuditSheetdtl> details = detailRepository.findByMasterid(masterKeyid);
        
        FieldAuditSheetRequest response = new FieldAuditSheetRequest(master, details);
        
        logger.info("Successfully retrieved complete Field Audit Sheet data for Master: {}", masterKeyid);
        return response;
    }

    @Override
    @Transactional
    public boolean deleteFieldAuditSheetDetail(String detailId) throws Exception {
        logger.info("Deleting Field Audit Sheet detail with keyid: {}", detailId);
        
        if (detailId == null || detailId.trim().isEmpty()) {
            throw new IllegalArgumentException("Detail ID cannot be null or empty");
        }
        
        if (!detailRepository.existsById(detailId)) {
            logger.warn("Field Audit Sheet detail not found with keyid: {}", detailId);
            throw new ResourceNotFoundException("Field Audit Sheet detail not found with keyid: " + detailId);
        }
        
        detailRepository.deleteById(detailId);
        
        logger.info("Successfully deleted Field Audit Sheet detail with keyid: {}", detailId);
        return true;
    }
}