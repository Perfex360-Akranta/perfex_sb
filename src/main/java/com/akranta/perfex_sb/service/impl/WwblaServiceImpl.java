package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.BdmTlWwblamst;
import com.akranta.perfex_sb.model.BdmTlWwbladtl;
import com.akranta.perfex_sb.repository.WwblaMstRepository;
import com.akranta.perfex_sb.repository.WwblaDtlRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.WwblaService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.WwblaRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.List;
import java.util.Map;

@Service
public class WwblaServiceImpl implements WwblaService {

    private static final Logger logger = LoggerFactory.getLogger(WwblaServiceImpl.class);

    private final WwblaMstRepository repository;
    private final WwblaDtlRepository detailRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER_MASTER = "BDM_TL_WWBLAMST";
    private static final int KEY_LENGTH_MASTER = 10;
    private static final String PREFIX_MASTER = "WWBL";
    
    private static final String SEQ_IDENTIFIER_DETAIL = "BDM_TL_WWBLADTL";
    private static final int KEY_LENGTH_DETAIL = 10;
    private static final String PREFIX_DETAIL = "WWBD";
    
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    public WwblaServiceImpl(
        WwblaMstRepository repository,
        WwblaDtlRepository detailRepository,
        DbActionTemplate dbActionTemplate) {
        this.repository = repository;
        this.detailRepository = detailRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

   @Override
@Transactional
public ResponseEntity<WwblaRequest> saveWwbla(WwblaRequest request) throws Exception {
    BdmTlWwblamst master = request.getMaster();

    if (master == null) {
        throw new RuntimeException("No Wwbla Master Details");
    }

    WwblaRequest result = new WwblaRequest();

    // CHECK IF INSERT OR UPDATE
    // Treat null, empty, or "-" as INSERT mode
    if (master.getKeyid() == null || 
        master.getKeyid().trim().isEmpty() || 
        master.getKeyid().trim().equals("-")) {
        
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

        if (master.getActive() == null) {
    master.setActive('Y');  
}

        logger.info("Generated new Master Key ID: {}", newMstKeyid);

        // Save the new record
        BdmTlWwblamst savedMaster = repository.save(master);
        result.setMaster(savedMaster);
        
        logger.info("Successfully created Wwbla Master Entry with Key ID: {}", savedMaster.getKeyid());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    } else {
        // UPDATE MODE
        if (repository.existsById(master.getKeyid())) {
            BdmTlWwblamst existingMaster = repository.findById(master.getKeyid())
                .orElseThrow(() -> new ResourceNotFoundException("Wwbla Master Entry not found"));

            // Update all master fields
            existingMaster.setFlid(master.getFlid());
            existingMaster.setPreparedby(master.getPreparedby());
            existingMaster.setPrepareddate(master.getPrepareddate());
            existingMaster.setProblem(master.getProblem());
            existingMaster.setPhenomena(master.getPhenomena());
            existingMaster.setMechanism(master.getMechanism());
            existingMaster.setLopcid(master.getLopcid());
            existingMaster.setLopcempid(master.getLopcempid());
            existingMaster.setLopcyn(master.getLopcyn());
            existingMaster.setActive(master.getActive());
            existingMaster.setWwblinvestigation(master.getWwblinvestigation());
            existingMaster.setModifiedon(LocalDateTime.now());

            BdmTlWwblamst updatedMaster = repository.save(existingMaster);
            result.setMaster(updatedMaster);
            
            logger.info("Successfully updated Wwbla Master Entry with Key ID: {}", updatedMaster.getKeyid());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            throw new ResourceNotFoundException("Wwbla Master Entry not found with keyid: " + master.getKeyid());
        }
    }
}
    @Override
    @Transactional
    public ResponseEntity<WwblaRequest> saveWwblaDetail(WwblaRequest request) throws Exception {
        List<BdmTlWwbladtl> details = request.getDetails();

        if (details == null || details.isEmpty()) {
            throw new RuntimeException("No Wwbla Detail Details");
        }

        WwblaRequest result = new WwblaRequest();
        List<BdmTlWwbladtl> savedDetails = new ArrayList<>();

        // INSERT MODE ONLY FOR DETAILS
        for (BdmTlWwbladtl detail : details) {
            if (detail.getKeyid() != null && !detail.getKeyid().trim().isEmpty()) {
                throw new IllegalArgumentException("Detail Key ID should be null or empty for insert operation");
            }

            String newDtlKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET
            );

            if (newDtlKeyid == null || newDtlKeyid.trim().isEmpty()) {
                logger.error("Failed to generate the Detail Key ID");
                throw new RuntimeException("Failed to generate Detail Key ID");
            }

            detail.setKeyid(newDtlKeyid);
            
            if (detail.getCreatedon() == null) {
                detail.setCreatedon(LocalDateTime.now());
            }
            detail.setModifiedon(LocalDateTime.now());

            if (detail.getActive() == null) {
    detail.setActive('Y'); 
            }

            logger.info("Generated new Detail Key ID: {}", newDtlKeyid);
            
            BdmTlWwbladtl savedDetail = detailRepository.save(detail);
            savedDetails.add(savedDetail);
            
            logger.info("Successfully created Wwbla Detail Entry with Key ID: {}", savedDetail.getKeyid());
        }

        result.setDetails(savedDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
@Transactional(readOnly = true)
public List<Map<String, Object>> recallWwblaDetail(String keyid) {
    logger.info("Recalling Wwbla detail for Key ID: {}", keyid);
    
    // Validate input
    if (keyid == null || keyid.trim().isEmpty()) {
        throw new IllegalArgumentException("Detail Key ID cannot be null or empty");
    }
    
    List<Map<String, Object>> result = detailRepository.recallWwblaDetail(keyid);
    
    if (result == null || result.isEmpty()) {
        logger.warn("No detail found for Key ID: {}", keyid);
        throw new ResourceNotFoundException("Detail not found for keyid: " + keyid);
    }
    
    logger.info("Successfully retrieved detail record for keyid: {}", keyid);
    return result;
}

@Override
@Transactional(readOnly = true)
public List<Map<String, Object>> getWwblaValues(
        String masterKeyid,
        String parentId,
        String detailKeyid) throws Exception {
    
    logger.info("Getting Wwbla values - masterKeyid: {}, parentId: {}, detailKeyid: {}", 
                masterKeyid, parentId, detailKeyid);
    
    try {
        // Determine if we should exclude self-reference
        boolean excludeSelfReference = (detailKeyid != null && !detailKeyid.trim().isEmpty());
        
        // Call the repository method
        List<Map<String, Object>> result = detailRepository.getWwblaValues(
                masterKeyid,
                parentId,
                excludeSelfReference
        );
        
        if (result == null || result.isEmpty()) {
            logger.warn("No Wwbla values found for the given criteria");
            return new ArrayList<>();
        }
        
        logger.info("Successfully retrieved {} Wwbla value records", result.size());
        return result;
        
    } catch (Exception e) {
        logger.error("Error retrieving Wwbla values: {}", e.getMessage(), e);
        throw new Exception("Failed to retrieve Wwbla values: " + e.getMessage());
    }
}
@Override
    @Transactional
    public boolean deleteWwblaChildEntry(String keyid) throws Exception {
        logger.info("Deleting WWBLA child entry and its descendants with keyid: {}", keyid);
        
        if (keyid == null || keyid.trim().isEmpty()) {
            throw new IllegalArgumentException("Key ID cannot be null or empty");
        }
        
        // Check if detail exists
        if (!detailRepository.existsById(keyid)) {
            logger.warn("WWBLA detail not found with keyid: {}", keyid);
            throw new ResourceNotFoundException("WWBLA detail not found with keyid: " + keyid);
        }
        
        // First, delete all child entries where this keyid is the parent
        int childRowsAffected = detailRepository.deleteByParentId(keyid);
        logger.info("Deleted {} child entries with parent ID: {}", childRowsAffected, keyid);
        
        // Then, delete the entry itself
        int rowsAffected = detailRepository.deleteByKeyid(keyid);
        
        if (rowsAffected == 0) {
            logger.error("Failed to delete WWBLA detail with keyid: {}", keyid);
            throw new RuntimeException("Failed to delete WWBLA detail");
        }
        
        logger.info("Successfully deleted WWBLA detail with keyid: {}. Total rows affected: {}", 
                    keyid, (childRowsAffected + rowsAffected));
        return true;
    }


   
}