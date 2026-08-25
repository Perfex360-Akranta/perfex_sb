package com.akranta.perfex_sb.service.impl;

//import com.akranta.perfex_sb.dto.JhaTlAuditmstAndDtlDto;
import com.akranta.perfex_sb.dto.JhaTlAudituploadDto;
//import com.akranta.perfex_sb.model.JhaTlAuditdtl;
import com.akranta.perfex_sb.model.JhaTlAuditmst;
import com.akranta.perfex_sb.repository.JhaTlAuditmstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.JhDmtAuditUploadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import java.util.List;
import java.time.LocalDateTime;

@Service
public class JhDmtAuditUploadServiceimpl implements JhDmtAuditUploadService {

    private static final Logger logger = LoggerFactory.getLogger(JhAuditServiceImpl.class);

    private final JhaTlAuditmstRepository repository;   
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "JHA_TL_AUDITMST";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX = "JHM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

   
    public JhDmtAuditUploadServiceimpl(
            JhaTlAuditmstRepository repository,
            DbActionTemplate dbActionTemplate) {
        this.repository = repository;       
        this.dbActionTemplate = dbActionTemplate;
    }


    
    @Transactional
    public JhaTlAudituploadDto createOrUpdateAuditupload(JhaTlAudituploadDto jhaTlAudituploadDto) {
        try {
            if (jhaTlAudituploadDto == null) {
                throw new IllegalArgumentException("Audit data cannot be null");
            }

            JhaTlAuditmst jhaTlAuditmst = jhaTlAudituploadDto.getJhaTlAuditmst();
            

            if (jhaTlAuditmst == null) {
                throw new IllegalArgumentException("Audit master data cannot be null");
            }

            // Handle master record
            jhaTlAuditmst = saveMasterRecord(jhaTlAuditmst);
            
            
            // Prepare response
            JhaTlAudituploadDto response = new JhaTlAudituploadDto();
            response.setJhaTlAuditmst(jhaTlAuditmst);
          

            return response;
            
        } catch (IllegalArgumentException e) {
            logger.error("Validation error creating/updating audit: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error creating/updating audit: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create/update audit: " + e.getMessage(), e);
        }
    }


    // ==========================================
    // PRIVATE HELPER METHODS
    // ==========================================

    private JhaTlAuditmst saveMasterRecord(JhaTlAuditmst jhaTlAuditmst) {
        if (jhaTlAuditmst.getKeyid() == null || jhaTlAuditmst.getKeyid().trim().isEmpty()) {
            // Generate new ID for insert
            try {
                String newMstKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET);
                
                if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                    throw new IllegalStateException("Failed to generate Master Key ID - sequence returned null");
                }
                
                jhaTlAuditmst.setKeyid(newMstKeyid);
                jhaTlAuditmst.setCreatedon(LocalDateTime.now());
                logger.info("Generated new Master Key ID: {}", newMstKeyid);
                
            } catch (Exception e) {
                throw new IllegalStateException("Failed to generate Master Key ID: " + e.getMessage(), e);
            }
        } else {
            // UPDATE - else block added
            if (repository.existsById(jhaTlAuditmst.getKeyid())) {
                jhaTlAuditmst.setModifiedon(LocalDateTime.now());
                JhaTlAuditmst updateEntity = repository.save(jhaTlAuditmst);
                logger.info("Successfully updated Audit with Key ID: {}", updateEntity.getKeyid());
                return updateEntity;
            }
        }
        
        jhaTlAuditmst.setModifiedon(LocalDateTime.now());

        // Save master (handles both insert and update)
        JhaTlAuditmst savedEntity = repository.save(jhaTlAuditmst);
        logger.info("Saved Master with Key ID: {}", savedEntity.getKeyid());
        
        return savedEntity;
    }



     @Override
public JhaTlAudituploadDto getAuditByKeyid(String keyid) {
    try {
        logger.info("Fetching audit by keyid: {}", keyid);
        
        if (!isValidKeyId(keyid)) {
            throw new IllegalArgumentException("Invalid keyid provided");
        }
        
        // Find master record by keyid
        JhaTlAuditmst jhaTlAuditmst = repository.findById(keyid)
                .orElseThrow(() -> new RuntimeException("Audit not found with keyid: " + keyid));
        
        // Find associated detail records
       // List<JhaTlAuditdtl> jhaTlAuditdtl = detailRepository.findByJhauditmasterid(keyid);
        
        // Build response DTO
        JhaTlAudituploadDto result = new JhaTlAudituploadDto();
        result.setJhaTlAuditmst(jhaTlAuditmst);
        //result.setJhaTlAuditdtl(jhaTlAuditdtl);
        
       // logger.info("Successfully retrieved audit with {} detail records", jhaTlAuditdtl.size());
        return result;
        
    } catch (IllegalArgumentException e) {
        logger.error("Validation error fetching audit: {}", e.getMessage());
        throw e;
    } catch (Exception e) {
        logger.error("Error fetching audit by keyid {}: {}", keyid, e.getMessage(), e);
        throw new RuntimeException("Failed to fetch audit: " + e.getMessage(), e);
    }
}



    private boolean isValidKeyId(String keyId) {
        return keyId != null && !keyId.trim().isEmpty() && !keyId.equalsIgnoreCase("null");
    }
}