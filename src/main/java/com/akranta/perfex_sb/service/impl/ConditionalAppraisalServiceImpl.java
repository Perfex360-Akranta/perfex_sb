package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.*;
import com.akranta.perfex_sb.repository.*;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.ConditionalAppraisalService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.ConditionalAppraisalRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConditionalAppraisalServiceImpl implements ConditionalAppraisalService {

    private static final Logger logger = LoggerFactory.getLogger(ConditionalAppraisalServiceImpl.class);

    private final PlmTlConditionalappraisalmstRepository masterRepository;
    private final PlmTlConditionalappraisalRepository detailRepository;
    private final PlmTlConditionalappraisalmstentryRepository masterEntryRepository;
    private final PlmTlConappraisalentryRepository detailEntryRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER_MASTER = "PLM_TL_CONDITIONALAPPRAISALMST";
    private static final String SEQ_IDENTIFIER_DETAIL = "PLM_TL_CONDITIONALAPPRAISAL";
    private static final String SEQ_IDENTIFIER_MASTER_ENTRY = "PLM_TL_CONAPPRAISALMSTENTRY";
    private static final String SEQ_IDENTIFIER_DETAIL_ENTRY = "PLM_TL_CONAPPRAISALENTRY";
    
    private static final int KEY_LENGTH_MASTER = 10;
    private static final int KEY_LENGTH_DETAIL = 15;
    
    private static final String PREFIX_MASTER = "CDM";
    private static final String PREFIX_DETAIL = "CDA";
    private static final String PREFIX_MASTER_ENTRY = "CDM";
    private static final String PREFIX_DETAIL_ENTRY = "CDA";
    
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    public ConditionalAppraisalServiceImpl(
        PlmTlConditionalappraisalmstRepository masterRepository,
        PlmTlConditionalappraisalRepository detailRepository,
        PlmTlConditionalappraisalmstentryRepository masterEntryRepository,
        PlmTlConappraisalentryRepository detailEntryRepository,
        DbActionTemplate dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.masterEntryRepository = masterEntryRepository;
        this.detailEntryRepository = detailEntryRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<ConditionalAppraisalRequest> saveConditionalAppraisal(ConditionalAppraisalRequest request) throws Exception {
        PlmTlConditionalappraisalmst master = request.getMaster();
        List<PlmTlConditionalappraisal> details = request.getDetails();

        if (master == null) {
            throw new RuntimeException("No Conditional Appraisal Master Details");
        }

        ConditionalAppraisalRequest result = new ConditionalAppraisalRequest();

        // CHECK IF INSERT OR UPDATE
        if (master.getKeyid() == null || master.getKeyid().trim().isEmpty()) {
            // ========== INSERT MODE ==========
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
            PlmTlConditionalappraisalmst savedMaster = masterRepository.save(master);
            List<PlmTlConditionalappraisal> savedDetailList = new ArrayList<>();

            // Save Details for INSERT
            if (details != null && !details.isEmpty()) {
                for (PlmTlConditionalappraisal detail : details) {
                    detail.setCdam_keyid(savedMaster.getKeyid());
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

                    PlmTlConditionalappraisal savedDetail = detailRepository.save(detail);
                    savedDetailList.add(savedDetail);
                    logger.info("Successfully created Detail with Key: {}", newDetailKeyid);
                }
            }

            result.setMaster(savedMaster);
            result.setDetails(savedDetailList);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);

        } else {
            // ========== UPDATE MODE ==========
            PlmTlConditionalappraisalmst existingMaster = masterRepository.findByKeyid(master.getKeyid());
            
            if (existingMaster == null) {
                throw new ResourceNotFoundException("Conditional Appraisal not found with keyid: " + master.getKeyid());
            }

            // Update all master fields
            existingMaster.setFlid(master.getFlid());
            existingMaster.setElementid(master.getElementid());
            existingMaster.setDate(master.getDate());
            existingMaster.setTempfield1(master.getTempfield1());
            existingMaster.setTempfield2(master.getTempfield2());
            existingMaster.setTempfield3(master.getTempfield3());
            existingMaster.setTempfield4(master.getTempfield4());
            existingMaster.setTempfield5(master.getTempfield5());
            existingMaster.setActive(master.getActive());
            existingMaster.setModifiedon(LocalDateTime.now());

            PlmTlConditionalappraisalmst updatedMaster = masterRepository.save(existingMaster);
            logger.info("Successfully updated Master with Key ID: {}", updatedMaster.getKeyid());

            // Handle Details in update mode
            List<PlmTlConditionalappraisal> resultDetails = new ArrayList<>();
            if (details != null && !details.isEmpty()) {
                for (PlmTlConditionalappraisal detail : details) {
                    if (detail.getKeyid() == null || detail.getKeyid().trim().isEmpty() || 
                        detail.getKeyid().equals("undefined")) {
                        // New detail - INSERT
                        detail.setCdam_keyid(master.getKeyid());
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
                        
                        PlmTlConditionalappraisal savedDetail = detailRepository.save(detail);
                        resultDetails.add(savedDetail);
                        logger.info("Successfully created Detail with Key: {}", newDetailKeyid);
                    } else {
                        // Existing detail - UPDATE
                        PlmTlConditionalappraisal existingDetail = detailRepository.findById(detail.getKeyid())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                "Detail not found for keyid: " + detail.getKeyid()));
                        
                        // Update only the fields that should change, preserve cdam_keyid
                        existingDetail.setComponent_type(detail.getComponent_type());
                        existingDetail.setComponentid(detail.getComponentid());
                        existingDetail.setNewcomponent(detail.getNewcomponent());
                        existingDetail.setDimension(detail.getDimension());
                        existingDetail.setCheckingtool(detail.getCheckingtool());
                        existingDetail.setTypeofcheck(detail.getTypeofcheck());
                        existingDetail.setIdealtype(detail.getIdealtype());
                        existingDetail.setIdealminimum(detail.getIdealminimum());
                        existingDetail.setIdealmaximum(detail.getIdealmaximum());
                        existingDetail.setUom(detail.getUom());
                        existingDetail.setIdealcondition(detail.getIdealcondition());
                        existingDetail.setActualcondition(detail.getActualcondition());
                        existingDetail.setActualvalue(detail.getActualvalue());
                        existingDetail.setOknotok(detail.getOknotok());
                        existingDetail.setStatus(detail.getStatus());
                        existingDetail.setActionrequired(detail.getActionrequired());
                        existingDetail.setRefurbishment_status(detail.getRefurbishment_status());
                        existingDetail.setTempfield1(detail.getTempfield1());
                        existingDetail.setTempfield2(detail.getTempfield2());
                        existingDetail.setTempfield3(detail.getTempfield3());
                        existingDetail.setTempfield4(detail.getTempfield4());
                        existingDetail.setTempfield5(detail.getTempfield5());
                        existingDetail.setTempfield6(detail.getTempfield6());
                        existingDetail.setTempfield7(detail.getTempfield7());
                        existingDetail.setActive(detail.getActive());
                        existingDetail.setModifiedon(LocalDateTime.now());
                        
                        PlmTlConditionalappraisal savedDetail = detailRepository.save(existingDetail);
                        resultDetails.add(savedDetail);
                        logger.info("Successfully updated Detail with Key: {}", detail.getKeyid());
                    }
                }
            }

            result.setMaster(updatedMaster);
            result.setDetails(resultDetails);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @Override
@Transactional
public ResponseEntity<ConditionalAppraisalRequest> saveConditionalAppraisalEntry(ConditionalAppraisalRequest request) throws Exception {
    PlmTlConditionalappraisalmstentry masterEntry = request.getMasterEntry();
    List<PlmTlConappraisalentry> detailsEntry = request.getDetailsEntry();

    if (masterEntry == null) {
        throw new RuntimeException("No Conditional Appraisal Master Entry Details");
    }

    ConditionalAppraisalRequest result = new ConditionalAppraisalRequest();

    // ========== CHECK IF MASTER ENTRY EXISTS IN DATABASE ==========
    PlmTlConditionalappraisalmstentry existingMasterEntry = null;
    boolean isMasterEntryUpdate = false;
    
    if (masterEntry.getKeyid() != null && !masterEntry.getKeyid().trim().isEmpty() 
        && !masterEntry.getKeyid().equals("undefined")) {
        // Try to find existing master entry
        existingMasterEntry = masterEntryRepository.findById(masterEntry.getKeyid()).orElse(null);
        isMasterEntryUpdate = (existingMasterEntry != null);
    }

    PlmTlConditionalappraisalmstentry savedMasterEntry;

    if (!isMasterEntryUpdate) {
        // ========== INSERT NEW MASTER ENTRY ==========
        String newMstKeyid = dbActionTemplate.getSequenceNumber(
            SEQ_IDENTIFIER_MASTER_ENTRY, KEY_LENGTH_MASTER, PREFIX_MASTER_ENTRY, DATE_FORMAT, FORMAT_RESET
        );

        if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
            logger.error("Failed to generate the Master Entry Key ID");
            throw new RuntimeException("Failed to generate Master Entry Key ID");
        }

        masterEntry.setKeyid(newMstKeyid);
        if (masterEntry.getCreatedon() == null) {
            masterEntry.setCreatedon(LocalDateTime.now());
        }
        masterEntry.setModifiedon(LocalDateTime.now());

        logger.info("Generated new Master Entry Key ID: {}", newMstKeyid);
        savedMasterEntry = masterEntryRepository.save(masterEntry);
        logger.info("Successfully created Master Entry with Key: {}", newMstKeyid);

    } else {
        // ========== UPDATE EXISTING MASTER ENTRY ==========
        existingMasterEntry.setFlid(masterEntry.getFlid());
        existingMasterEntry.setElementid(masterEntry.getElementid());
        existingMasterEntry.setDate(masterEntry.getDate());
        existingMasterEntry.setTempfield1(masterEntry.getTempfield1());
        existingMasterEntry.setTempfield2(masterEntry.getTempfield2());
        existingMasterEntry.setTempfield3(masterEntry.getTempfield3());
        existingMasterEntry.setTempfield4(masterEntry.getTempfield4());
        existingMasterEntry.setTempfield5(masterEntry.getTempfield5());
        existingMasterEntry.setActive(masterEntry.getActive());
        existingMasterEntry.setModifiedon(LocalDateTime.now());

        savedMasterEntry = masterEntryRepository.save(existingMasterEntry);
        logger.info("Successfully updated Master Entry with Key ID: {}", savedMasterEntry.getKeyid());
    }

    // ========== HANDLE DETAIL ENTRIES ==========
    List<PlmTlConappraisalentry> savedDetailEntryList = new ArrayList<>();

    if (detailsEntry != null && !detailsEntry.isEmpty()) {
        for (PlmTlConappraisalentry detailEntry : detailsEntry) {
            
            // Check if detail entry exists
            PlmTlConappraisalentry existingDetailEntry = null;
            boolean isDetailEntryUpdate = false;
            
            if (detailEntry.getKeyid() != null && !detailEntry.getKeyid().trim().isEmpty() 
                && !detailEntry.getKeyid().equals("undefined")) {
                existingDetailEntry = detailEntryRepository.findById(detailEntry.getKeyid()).orElse(null);
                isDetailEntryUpdate = (existingDetailEntry != null);
            }

            if (!isDetailEntryUpdate) {
                // ========== INSERT NEW DETAIL ENTRY ==========
                detailEntry.setCdam_keyid(savedMasterEntry.getKeyid());
                
                String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER_DETAIL_ENTRY, KEY_LENGTH_DETAIL, PREFIX_DETAIL_ENTRY, DATE_FORMAT, FORMAT_RESET
                );

                if (newDetailKeyid == null || newDetailKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate Detail Entry Key ID");
                    throw new RuntimeException("Failed to generate Detail Entry Key ID");
                }

                detailEntry.setKeyid(newDetailKeyid);
                
                // Ensure createdby is set
                if (detailEntry.getCreatedby() == null || detailEntry.getCreatedby().trim().isEmpty()) {
                    detailEntry.setCreatedby(savedMasterEntry.getCreatedby());
                }
                
                if (detailEntry.getCreatedon() == null) {
                    detailEntry.setCreatedon(LocalDateTime.now());
                }
                detailEntry.setModifiedon(LocalDateTime.now());

                PlmTlConappraisalentry savedDetailEntry = detailEntryRepository.save(detailEntry);
                savedDetailEntryList.add(savedDetailEntry);
                logger.info("Successfully created Detail Entry with Key: {}", newDetailKeyid);
                
            } else {
                // ========== UPDATE EXISTING DETAIL ENTRY ==========
                existingDetailEntry.setComponent_type(detailEntry.getComponent_type());
                existingDetailEntry.setComponentid(detailEntry.getComponentid());
                existingDetailEntry.setNewcomponent(detailEntry.getNewcomponent());
                existingDetailEntry.setDimension(detailEntry.getDimension());
                existingDetailEntry.setCheckingtool(detailEntry.getCheckingtool());
                existingDetailEntry.setTypeofcheck(detailEntry.getTypeofcheck());
                existingDetailEntry.setIdealtype(detailEntry.getIdealtype());
                existingDetailEntry.setIdealminimum(detailEntry.getIdealminimum());
                existingDetailEntry.setIdealmaximum(detailEntry.getIdealmaximum());
                existingDetailEntry.setUom(detailEntry.getUom());
                existingDetailEntry.setIdealcondition(detailEntry.getIdealcondition());
                existingDetailEntry.setActualcondition(detailEntry.getActualcondition());
                existingDetailEntry.setActualvalue(detailEntry.getActualvalue());
                existingDetailEntry.setOknotok(detailEntry.getOknotok());
                existingDetailEntry.setStatus(detailEntry.getStatus());
                existingDetailEntry.setActionrequired(detailEntry.getActionrequired());
                existingDetailEntry.setRefurbishment_status(detailEntry.getRefurbishment_status());
                existingDetailEntry.setCdapkeyid(detailEntry.getCdapkeyid());
                existingDetailEntry.setTempfield2(detailEntry.getTempfield2());
                existingDetailEntry.setTempfield3(detailEntry.getTempfield3());
                existingDetailEntry.setTempfield4(detailEntry.getTempfield4());
                existingDetailEntry.setTempfield5(detailEntry.getTempfield5());
                existingDetailEntry.setTempfield6(detailEntry.getTempfield6());
                existingDetailEntry.setTempfield7(detailEntry.getTempfield7());
                existingDetailEntry.setActive(detailEntry.getActive());
                existingDetailEntry.setModifiedon(LocalDateTime.now());
                
                PlmTlConappraisalentry savedDetailEntry = detailEntryRepository.save(existingDetailEntry);
                savedDetailEntryList.add(savedDetailEntry);
                logger.info("Successfully updated Detail Entry with Key: {}", detailEntry.getKeyid());
            }
        }
    }

    result.setMasterEntry(savedMasterEntry);
    result.setDetailsEntry(savedDetailEntryList);

    HttpStatus status = isMasterEntryUpdate ? HttpStatus.OK : HttpStatus.CREATED;
    return ResponseEntity.status(status).body(result);
}
    @Override
    @Transactional(readOnly = true)
    public ConditionalAppraisalRequest getCompleteConditionalAppraisalData(String masterKeyid) {
        logger.info("Fetching complete Conditional Appraisal data for Master Key ID: {}", masterKeyid);

        // Validate input
        if (masterKeyid == null || masterKeyid.trim().isEmpty()) {
            throw new IllegalArgumentException("Master Key ID cannot be null or empty");
        }

        // Fetch master only
        PlmTlConditionalappraisalmst master = masterRepository.findByKeyid(masterKeyid);
        
        if (master == null) {
            throw new ResourceNotFoundException(
                "Conditional Appraisal not found for keyid: " + masterKeyid);
        }
        
        logger.info("Successfully retrieved Master record");
        
        // Pass null for details as per requirement
        ConditionalAppraisalRequest response = new ConditionalAppraisalRequest(master, null);
        
        return response;
    }

    @Override
    public List<Map<String, Object>> recallConditionalAppraisalDetail(String keyid) {
        logger.info("Recalling Conditional Appraisal detail for Key ID: {}", keyid);
        
        // Validate input
        if (keyid == null || keyid.trim().isEmpty()) {
            throw new IllegalArgumentException("Detail Key ID cannot be null or empty");
        }
        
        List<Map<String, Object>> result = detailRepository.recallConditionalAppraisalDetail(keyid);
        
        if (result == null || result.isEmpty()) {
            logger.warn("No detail found for Key ID: {}", keyid);
            throw new ResourceNotFoundException("Detail not found for keyid: " + keyid);
        }
        
        logger.info("Successfully retrieved detail record for keyid: {}", keyid);
        return result;
    }

   @Override
public Long checkUpdate(String flid, String date, String forGrid, String cdapkeyid) throws Exception {
    logger.info("Checking update for FLID: {}, Date: {}, forGrid: {}", flid, date, forGrid);
    
    // Validate input
    if (flid == null || flid.trim().isEmpty()) {
        throw new IllegalArgumentException("FLID cannot be null or empty");
    }
    
    if (date == null || date.trim().isEmpty()) {
        throw new IllegalArgumentException("Date cannot be null or empty");
    }
    
    // Convert String date to LocalDateTime
    LocalDateTime dateTime;
    try {
        // Adjust the pattern based on your date format
        // Example formats:
        // "yyyy-MM-dd'T'HH:mm:ss" for ISO format
        // "yyyy-MM-dd HH:mm:ss" for standard datetime
        // "yyyy-MM-dd" for date only
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        dateTime = LocalDateTime.parse(date, formatter);
    } catch (DateTimeParseException e) {
        logger.error("Invalid date format: {}", date, e);
        throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd'T'HH:mm:ss");
    }
    
    Long count;
    
    if ("N".equals(forGrid)) {
        // Validate cdapkeyid for join query
        if (cdapkeyid == null || cdapkeyid.trim().isEmpty()) {
            throw new IllegalArgumentException("CDAPKEYID is required when forGrid is 'N'");
        }
        
        count = masterEntryRepository.checkUpdateWithJoin(flid, dateTime, cdapkeyid);
        logger.info("Check update with join - Count: {}", count);
    } else {
        count = masterEntryRepository.checkUpdateWithoutJoin(flid, dateTime);
        logger.info("Check update without join - Count: {}", count);
    }
    
    return count;
}
@Override
@Transactional
public boolean deleteConditionalAppraisalDetail(String keyid) throws Exception {
    logger.info("Deleting Conditional Appraisal detail with keyid: {}", keyid);
    
    if (keyid == null || keyid.trim().isEmpty()) {
        throw new IllegalArgumentException("Detail Key ID cannot be null or empty");
    }
    
    // Check if detail exists
    if (!detailRepository.existsById(keyid)) {
        logger.warn("Conditional Appraisal detail not found with keyid: {}", keyid);
        throw new ResourceNotFoundException("Conditional Appraisal detail not found with keyid: " + keyid);
    }
    
    int rowsAffected = detailRepository.deleteConditionalAppraisalDetail(keyid);
    
    if (rowsAffected == 0) {
        logger.error("Failed to delete Conditional Appraisal detail with keyid: {}", keyid);
        throw new RuntimeException("Failed to delete Conditional Appraisal detail");
    }
    
    logger.info("Successfully deleted Conditional Appraisal detail with keyid: {}. Rows affected: {}", keyid, rowsAffected);
    return true;
}

@Override
@Transactional
public boolean deleteConditionalAppraisalMaster(String masterKeyid) throws Exception {
    logger.info("Deleting Conditional Appraisal master and all details with keyid: {}", masterKeyid);
    
    if (masterKeyid == null || masterKeyid.trim().isEmpty()) {
        throw new IllegalArgumentException("Master Key ID cannot be null or empty");
    }
    
    // Check if master exists
    PlmTlConditionalappraisalmst master = masterRepository.findByKeyid(masterKeyid);
    if (master == null) {
        logger.warn("Conditional Appraisal master not found with keyid: {}", masterKeyid);
        throw new ResourceNotFoundException("Conditional Appraisal master not found with keyid: " + masterKeyid);
    }
    
    try {
        // First delete all associated details
        int detailsDeleted = detailRepository.deleteAllDetailsByMasterKeyid(masterKeyid);
        logger.info("Deleted {} detail records for master keyid: {}", detailsDeleted, masterKeyid);
        
        // Then delete the master
        int masterDeleted = masterRepository.deleteByKeyid(masterKeyid);
        
        if (masterDeleted == 0) {
            logger.error("Failed to delete Conditional Appraisal master with keyid: {}", masterKeyid);
            throw new RuntimeException("Failed to delete Conditional Appraisal master");
        }
        
        logger.info("Successfully deleted Conditional Appraisal master with keyid: {}. Details deleted: {}, Master rows affected: {}", 
                    masterKeyid, detailsDeleted, masterDeleted);
        return true;
        
    } catch (Exception e) {
        logger.error("Error deleting Conditional Appraisal master: {}", e.getMessage(), e);
        throw new Exception("Failed to delete Conditional Appraisal master: " + e.getMessage());
    }
}

}