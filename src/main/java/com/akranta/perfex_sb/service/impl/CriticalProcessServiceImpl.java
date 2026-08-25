package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.*;
import com.akranta.perfex_sb.repository.*;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.CriticalProcessService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.CriticalProcessRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CriticalProcessServiceImpl implements CriticalProcessService {

    private static final Logger logger = LoggerFactory.getLogger(CriticalProcessServiceImpl.class);


     @Autowired
    private QtmTlCriticalprocessmstRepository criticalprocessmstRepository;

    private final QtmTlCriticalprocessmstRepository masterRepository;
    private final QtmTlCriticalprocessdtlRepository detailRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER_MASTER = "QTM_TL_CRITICALPROCESSMST";
    private static final String SEQ_IDENTIFIER_DETAIL = "QTM_TL_CRITICALPROCESSDTL";
    
    private static final int KEY_LENGTH_MASTER = 16;
    private static final int KEY_LENGTH_DETAIL = 16;
    
    private static final String PREFIX_MASTER = "CRPP";
    private static final String PREFIX_DETAIL = "CRPD";
    
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    public CriticalProcessServiceImpl(
        QtmTlCriticalprocessmstRepository masterRepository,
        QtmTlCriticalprocessdtlRepository detailRepository,
        DbActionTemplate dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<CriticalProcessRequest> saveCriticalProcess(CriticalProcessRequest request) throws Exception {
        QtmTlCriticalprocessmst master = request.getMaster();
        List<QtmTlCriticalprocessdtl> details = request.getDetails();

        if (master == null) {
            throw new RuntimeException("No Critical Process Master Details");
        }

        CriticalProcessRequest result = new CriticalProcessRequest();

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
            QtmTlCriticalprocessmst savedMaster = masterRepository.save(master);
            List<QtmTlCriticalprocessdtl> savedDetailList = new ArrayList<>();

            // Save Details for INSERT
            if (details != null && !details.isEmpty()) {
                for (QtmTlCriticalprocessdtl detail : details) {
                    detail.setCrpp_keyid(savedMaster.getKeyid());
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

                    QtmTlCriticalprocessdtl savedDetail = detailRepository.save(detail);
                    savedDetailList.add(savedDetail);
                    logger.info("Successfully created Detail with Key: {}", newDetailKeyid);
                }
            }

            result.setMaster(savedMaster);
            result.setDetails(savedDetailList);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);

        } else {
            // ========== UPDATE MODE ==========
            QtmTlCriticalprocessmst existingMaster = masterRepository.findByKeyid(master.getKeyid());
            
            if (existingMaster == null) {
                throw new ResourceNotFoundException("Critical Process not found with keyid: " + master.getKeyid());
            }

            // Update all master fields
            existingMaster.setFlid(master.getFlid());
            existingMaster.setElementid(master.getElementid());
            existingMaster.setDate(master.getDate());
            existingMaster.setParameter(master.getParameter());
            existingMaster.setTempfield1(master.getTempfield1());
            existingMaster.setTempfield2(master.getTempfield2());
            existingMaster.setTempfield3(master.getTempfield3());
            existingMaster.setTempfield4(master.getTempfield4());
            existingMaster.setTempfield5(master.getTempfield5());
            existingMaster.setActive(master.getActive());
            existingMaster.setModifiedon(LocalDateTime.now());

            QtmTlCriticalprocessmst updatedMaster = masterRepository.save(existingMaster);
            logger.info("Successfully updated Master with Key ID: {}", updatedMaster.getKeyid());

            // Handle Details in update mode
            List<QtmTlCriticalprocessdtl> resultDetails = new ArrayList<>();
            if (details != null && !details.isEmpty()) {
                for (QtmTlCriticalprocessdtl detail : details) {
                    if (detail.getKeyid() == null || detail.getKeyid().trim().isEmpty() || 
                        detail.getKeyid().equals("undefined")) {
                        // New detail - INSERT
                        detail.setCrpp_keyid(master.getKeyid());
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
                        
                        QtmTlCriticalprocessdtl savedDetail = detailRepository.save(detail);
                        resultDetails.add(savedDetail);
                        logger.info("Successfully created Detail with Key: {}", newDetailKeyid);
                    } else {
                        // Existing detail - UPDATE
                        // CRITICAL FIX: Fetch existing detail to preserve crpp_keyid
                        QtmTlCriticalprocessdtl existingDetail = detailRepository.findById(detail.getKeyid())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                "Detail not found for keyid: " + detail.getKeyid()));
                        
                        // Update only the fields that should change, preserve crpp_keyid
                        existingDetail.setMethod(detail.getMethod());
                        existingDetail.setUnit(detail.getUnit());
                        existingDetail.setValue(detail.getValue());
                        // existingDetail.setTempfield1(detail.getTempfield1());
                        // existingDetail.setTempfield2(detail.getTempfield2());

                        existingDetail.setMin(detail.getMin());
                        existingDetail.setMax(detail.getMax());

                        existingDetail.setTempfield3(detail.getTempfield3());
                        existingDetail.setTempfield4(detail.getTempfield4());
                        existingDetail.setTempfield5(detail.getTempfield5());
                        existingDetail.setActive(detail.getActive());
                        existingDetail.setModifiedon(LocalDateTime.now());
                        
                        QtmTlCriticalprocessdtl savedDetail = detailRepository.save(existingDetail);
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
    @Transactional(readOnly = true)
    public CriticalProcessRequest getCompleteCriticalProcessData(String masterKeyid) {
        logger.info("Fetching complete Critical Process data for Master Key ID: {}", masterKeyid);

        // Validate input
        if (masterKeyid == null || masterKeyid.trim().isEmpty()) {
            throw new IllegalArgumentException("Master Key ID cannot be null or empty");
        }

        // Fetch master only
        QtmTlCriticalprocessmst master = masterRepository.findByKeyid(masterKeyid);
        
        if (master == null) {
            throw new ResourceNotFoundException(
                "Critical Process not found for keyid: " + masterKeyid);
        }
        
        logger.info("Successfully retrieved Master record");
        
        // Pass null for details as per requirement
        CriticalProcessRequest response = new CriticalProcessRequest(master, null);
        
        return response;
    }
    @Override
@Transactional
public boolean deleteCriticalProcessDetail(String detailId) throws Exception {
    logger.info("Deleting Critical Process detail with keyid: {}", detailId);
    
    if (detailId == null || detailId.trim().isEmpty()) {
        throw new IllegalArgumentException("Detail ID cannot be null or empty");
    }
    
    // Check if detail exists
    if (!detailRepository.existsById(detailId)) {
        logger.warn("Critical Process detail not found with keyid: {}", detailId);
        throw new ResourceNotFoundException("Critical Process detail not found with keyid: " + detailId);
    }
    
    int rowsAffected = detailRepository.deleteCriticalProcessDetail(detailId);
    
    if (rowsAffected == 0) {
        logger.error("Failed to delete Critical Process detail with keyid: {}", detailId);
        throw new RuntimeException("Failed to delete Critical Process detail");
    }
    
    logger.info("Successfully deleted Critical Process detail with keyid: {}. Rows affected: {}", 
                detailId, rowsAffected);
    return true;
}
 @Override
    @Transactional
    public boolean delete(String masterId) throws Exception {
        logger.info("Deleting Critical Process Master record with masterId: {}", masterId);
        
        // Validate input
        if (masterId == null || masterId.trim().isEmpty()) {
            logger.error("Master ID is null or empty");
            throw new IllegalArgumentException("Master ID cannot be null or empty");
        }
        
        // Check if record exists
        QtmTlCriticalprocessmst existingRecord = criticalprocessmstRepository.findByKeyid(masterId);
        if (existingRecord == null) {
            logger.warn("Critical Process Master record not found with masterId: {}", masterId);
            throw new ResourceNotFoundException("Critical Process Master record not found with masterId: " + masterId);
        }
        
        try {
            // Delete detail records first (to respect foreign key constraints)
            int dtlRowsAffected = criticalprocessmstRepository.deleteAllDtl(masterId);
            logger.info("Deleted {} detail records for masterId: {}", dtlRowsAffected, masterId);
            
            // Delete master record
            int mstRowsAffected = criticalprocessmstRepository.deleteMaster(masterId);
            
            if (mstRowsAffected == 0) {
                logger.error("Failed to delete Critical Process Master record with masterId: {}", masterId);
                throw new RuntimeException("Failed to delete Critical Process Master record");
            }
            
            logger.info("Successfully deleted Critical Process Master record with masterId: {}. Master rows affected: {}, Detail rows affected: {}", 
                        masterId, mstRowsAffected, dtlRowsAffected);
            return true;
            
        } catch (Exception e) {
            logger.error("Error occurred while deleting Critical Process Master record with masterId: {}", masterId, e);
            throw new Exception("Error deleting Critical Process Master record: " + e.getMessage(), e);
        }
    }

}