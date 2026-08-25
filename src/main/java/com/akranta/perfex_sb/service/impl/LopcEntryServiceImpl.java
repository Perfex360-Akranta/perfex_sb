package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.LopcEntryMst;
import com.akranta.perfex_sb.repository.LopcEntryMstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.LopcEntryService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.LopcEntryRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;


@Service
public class LopcEntryServiceImpl implements LopcEntryService {

    private static final Logger logger = LoggerFactory.getLogger(LopcEntryServiceImpl.class);

    private final LopcEntryMstRepository repository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER_MASTER = "GEN_TL_LOPCENTRYMST";
    private static final int KEY_LENGTH_MASTER = 15;
    private static final String PREFIX_MASTER = "LOPC";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    public LopcEntryServiceImpl(
        LopcEntryMstRepository repository,
        DbActionTemplate dbActionTemplate) {
        this.repository = repository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<LopcEntryRequest> saveLopcEntry(LopcEntryRequest request) throws Exception {
        LopcEntryMst master = request.getMaster();

        if (master == null) {
            throw new RuntimeException("No LOPC Entry Master Details");
        }

        LopcEntryRequest result = new LopcEntryRequest();

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

            // Set default values for tempfields if not provided
            if (master.getTempfield1() == null || master.getTempfield1().trim().isEmpty()) {
                master.setTempfield1("-");
            }
            if (master.getTempfield2() == null || master.getTempfield2().trim().isEmpty()) {
                master.setTempfield2("-");
            }
            if (master.getTempfield3() == null || master.getTempfield3().trim().isEmpty()) {
                master.setTempfield3("-");
            }
            if (master.getTempfield4() == null || master.getTempfield4().trim().isEmpty()) {
                master.setTempfield4("-");
            }
            if (master.getTempfield5() == null || master.getTempfield5().trim().isEmpty()) {
                master.setTempfield5("-");
            }

if (master.getActive() == null) {
    master.setActive('Y');  
}

            logger.info("Generated new Master Key ID: {}", newMstKeyid);

            // Save the new record
            LopcEntryMst savedMaster = repository.save(master);
            result.setMaster(savedMaster);
            
            logger.info("Successfully created LOPC Entry with Key ID: {}", savedMaster.getKeyid());
            return ResponseEntity.status(HttpStatus.CREATED).body(result);

        } else {
            // UPDATE MODE
            if (repository.existsById(master.getKeyid())) {
                LopcEntryMst existingMaster = repository.findById(master.getKeyid())
                    .orElseThrow(() -> new ResourceNotFoundException("LOPC Entry not found"));

                // Update all master fields
                existingMaster.setLopccategoryid(master.getLopccategoryid());
                existingMaster.setOccurrencedatetime(master.getOccurrencedatetime());
                existingMaster.setFnlid(master.getFnlid());
                existingMaster.setEmployeeid(master.getEmployeeid());
                existingMaster.setLopcdesc(master.getLopcdesc());
                existingMaster.setIdentifiedby(master.getIdentifiedby());
                existingMaster.setPrepareddatetime(master.getPrepareddatetime());
                existingMaster.setTempfield1(master.getTempfield1());
                existingMaster.setTempfield2(master.getTempfield2());
                existingMaster.setTempfield3(master.getTempfield3());
                existingMaster.setTempfield4(master.getTempfield4());
                existingMaster.setTempfield5(master.getTempfield5());
                existingMaster.setActive(master.getActive());
                existingMaster.setModifiedon(LocalDateTime.now());

                LopcEntryMst updatedMaster = repository.save(existingMaster);
                result.setMaster(updatedMaster);
                
                logger.info("Successfully updated LOPC Entry with Key ID: {}", updatedMaster.getKeyid());
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                throw new ResourceNotFoundException("LOPC Entry not found with keyid: " + master.getKeyid());
            }
        }
    }
 @Override
@Transactional(readOnly = true)
public LopcEntryRequest getLopcEntryData(String keyid) {
    logger.info("Fetching LOPC Entry data for Key ID: {}", keyid);

    LopcEntryMst master = repository.findByKeyid(keyid);
    
    if (master == null) {
        throw new ResourceNotFoundException("LOPC Entry not found for keyid: " + keyid);
    }
    
    LopcEntryRequest response = new LopcEntryRequest();
    response.setMaster(master);
    
    logger.info("Successfully retrieved LOPC Entry data for Key ID: {}", keyid);
    return response;
}
 @Override
    @Transactional
    public void updateLopcActionClosure(
            String keyid, 
            String completedBy, 
            String status, 
            String completedDate, 
            String correctiveAction, 
            String remarks) throws Exception {
        
        logger.info("Updating LOPC Action Closure for keyid: {}", keyid);
        
        // Get the foreign key (WWBL_KEYID) from the detail record
        List<Map<String, Object>> wwblKeyidResult = repository.getWwblKeyidByDetailKeyid(keyid);
        
        if (wwblKeyidResult == null || wwblKeyidResult.isEmpty()) {
            logger.error("Foreign key WWBD_WWBL_KEYID not found for WWBD_KEYID: {}", keyid);
            throw new Exception("Foreign key WWBD_WWBL_KEYID not found for WWBD_KEYID: " + keyid);
        }
        
        String wwblKeyid = (String) wwblKeyidResult.get(0).get("wwbl_keyid");
        
        if (wwblKeyid == null || wwblKeyid.isEmpty()) {
            logger.error("Foreign key WWBD_WWBL_KEYID is null or empty for WWBD_KEYID: {}", keyid);
            throw new Exception("Foreign key WWBD_WWBL_KEYID is null or empty for WWBD_KEYID: " + keyid);
        }
        
        logger.info("Retrieved wwblKeyid: {}", wwblKeyid);
        
        // Update all detail records with the same foreign key
        int updatedRecords = repository.updateLopcActionClosure(
                correctiveAction, 
                completedBy, 
                completedDate, 
                status, 
                remarks, 
                wwblKeyid
        );
        
        logger.info("Updated {} detail records for wwblKeyid: {}", updatedRecords, wwblKeyid);
        
        // If status is 'C' (Completed), check if all records are completed
        if ("C".equals(status)) {
            checkAndUpdateMasterInvestigation(wwblKeyid);
        }
        
        logger.info("Successfully completed LOPC Action Closure update for keyid: {}", keyid);
    }

    /**
     * Check pending records and update master investigation status if all completed
     */
    private void checkAndUpdateMasterInvestigation(String wwblKeyid) {
        logger.info("Checking pending records for wwblKeyid: {}", wwblKeyid);
        
        List<Map<String, Object>> pendingCountResult = repository.countPendingRecords(wwblKeyid);
        
        int pendingCount = 0;
        if (pendingCountResult != null && !pendingCountResult.isEmpty()) {
            Object countObj = pendingCountResult.get(0).get("pending_count");
            if (countObj instanceof Number) {
                pendingCount = ((Number) countObj).intValue();
            } else if (countObj instanceof String) {
                pendingCount = Integer.parseInt((String) countObj);
            }
        }
        
        logger.info("Pending count for wwblKeyid {}: {}", wwblKeyid, pendingCount);
        
        // If no pending records, update master table to 'C'
        if (pendingCount == 0) {
            int updatedMaster = repository.updateMasterInvestigationStatus(wwblKeyid);
            logger.info("Master investigation status updated to Completed for wwblKeyid: {}. Rows affected: {}", 
                        wwblKeyid, updatedMaster);
        } else {
            logger.info("Still {} pending records for wwblKeyid: {}. Master not updated.", 
                       pendingCount, wwblKeyid);
        }
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getDetailRecords(String wwblKeyid) {
        logger.info("Fetching detail records for wwblKeyid: {}", wwblKeyid);
        
        List<Map<String, Object>> detailRecords = repository.getDetailRecordsByMasterKeyid(wwblKeyid);
        
        logger.info("Retrieved {} detail records for wwblKeyid: {}", detailRecords.size(), wwblKeyid);
        
        return detailRecords;
    }

}