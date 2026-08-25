package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.VisualControlChecklistDto;
import com.akranta.perfex_sb.dto.VisualControlChecklistdtlDto;
import com.akranta.perfex_sb.model.GenTlVisualcontrolchecklist;
import com.akranta.perfex_sb.model.GenTlVisualcntchecklistdtl;
import com.akranta.perfex_sb.repository.GenTlVisualcontrolchecklistRepository;
import com.akranta.perfex_sb.repository.GenTlVisualcntchecklistdtlRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.VisualControlCheckService;

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
public class VisualControlCheckServiceImpl implements VisualControlCheckService {

    private static final Logger logger = LoggerFactory.getLogger(VisualControlCheckServiceImpl.class);

    private GenTlVisualcontrolchecklistRepository masterRepository;
    private GenTlVisualcntchecklistdtlRepository detailRepository;
    private DbActionTemplate dbActionTemplate;

    // Sequence configuration for Master
    private static final String SEQ_IDENTIFIER_MST = "GEN_TL_VISUALCONTROLCHECKLIST";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX_MST = "VCCL";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    // Sequence configuration for Detail
    private static final String SEQ_IDENTIFIER_DTL = "GEN_TL_VISUALCNTCHECKLISTDTL";
    private static final String PREFIX_DTL = "VCDT";

    public VisualControlCheckServiceImpl(
            GenTlVisualcontrolchecklistRepository masterRepository,
            GenTlVisualcntchecklistdtlRepository detailRepository,
            DbActionTemplate dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<VisualControlChecklistDto> createOrUpdate(VisualControlChecklistDto dto) {
        try {
            if (dto == null) {
                throw new IllegalArgumentException("Visual Control Check data cannot be null");
            }

            GenTlVisualcontrolchecklist master = dto.getGenTlVisualcontrolchecklist();
            List<GenTlVisualcntchecklistdtl> detailList = dto.getGenTlVisualcntchecklistdtl();

            if (master == null) {
                throw new IllegalArgumentException("Master data cannot be null");
            }

            // Handle master record
            master = saveMasterRecord(master);

            // Handle detail records
            // List<GenTlVisualcntchecklistdtl> savedDetailList =
            // saveDetailRecords(detailList, master.getKeyid());
            List<GenTlVisualcntchecklistdtl> savedDetailList = saveDetailRecords(detailList, master.getKeyid(),
                    dto.getVisualControlChecklistdtlDto());

            // Prepare response
            VisualControlChecklistDto response = new VisualControlChecklistDto();
            response.setGenTlVisualcontrolchecklist(master);
            response.setGenTlVisualcntchecklistdtl(savedDetailList);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            logger.error("Validation error creating/updating visual control check: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error creating/updating visual control check: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create/update visual control check: " + e.getMessage(), e);
        }
    }

    // ==========================================
    // PRIVATE HELPER METHODS
    // ==========================================

    private GenTlVisualcontrolchecklist saveMasterRecord(GenTlVisualcontrolchecklist master) {
        if (master.getKeyid() == null || master.getKeyid().trim().isEmpty()) {
            // CREATE - Generate new ID
            try {
                String newKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER_MST, KEY_LENGTH, PREFIX_MST, DATE_FORMAT, FORMAT_RESET);

                if (newKeyid == null || newKeyid.trim().isEmpty()) {
                    throw new IllegalStateException("Failed to generate Master Key ID - sequence returned null");
                }

                master.setKeyid(newKeyid);
                master.setCreatedon(LocalDateTime.now());
                logger.info("Generated new Master Key ID: {}", newKeyid);

            } catch (Exception e) {
                throw new IllegalStateException("Failed to generate Master Key ID: " + e.getMessage(), e);
            }
        } else {
            // UPDATE - Check if exists
            if (masterRepository.existsById(master.getKeyid())) {
                master.setModifiedon(LocalDateTime.now());
                GenTlVisualcontrolchecklist updateEntity = masterRepository.save(master);
                logger.info("Successfully updated Visual Control Check Master with Key ID: {}",
                        updateEntity.getKeyid());
                return updateEntity;
            }
        }

        master.setModifiedon(LocalDateTime.now());

        // Save master (handles both insert and update)
        GenTlVisualcontrolchecklist savedEntity = masterRepository.save(master);
        logger.info("Saved Master with Key ID: {}", savedEntity.getKeyid());

        return savedEntity;
    }

    // // In VisualControlCheckServiceImpl.java - saveDetailRecords method
    // private List<GenTlVisualcntchecklistdtl> saveDetailRecords(
    // List<GenTlVisualcntchecklistdtl> detailList, String masterKeyId) {

    // List<GenTlVisualcntchecklistdtl> savedDetailList = new ArrayList<>();

    // if (detailList == null || detailList.isEmpty()) {
    // logger.info("No detail records to save for master: {}", masterKeyId);
    // return savedDetailList;
    // }

    // for (GenTlVisualcntchecklistdtl detail : detailList) {
    // detail.setVccl_keyid(masterKeyId); // ✅ Set foreign key to master

    // if (detail.getKeyid() == null || detail.getKeyid().trim().isEmpty()) {
    // // CREATE - Generate new ID
    // try {
    // String newDtlKeyid = dbActionTemplate.getSequenceNumber(
    // SEQ_IDENTIFIER_DTL, KEY_LENGTH, PREFIX_DTL, DATE_FORMAT, FORMAT_RESET);

    // if (newDtlKeyid == null || newDtlKeyid.trim().isEmpty()) {
    // throw new IllegalStateException("Failed to generate Detail Key ID - sequence
    // returned null");
    // }

    // detail.setKeyid(newDtlKeyid);
    // detail.setCreatedon(LocalDateTime.now());
    // logger.info("Generated new Detail Key ID: {}", newDtlKeyid);

    // } catch (Exception e) {
    // throw new IllegalStateException("Failed to generate Detail Key ID: " +
    // e.getMessage(), e);
    // }
    // } else {
    // // UPDATE - Check if exists
    // if (detailRepository.existsById(detail.getKeyid())) {
    // detail.setModifiedon(LocalDateTime.now());
    // GenTlVisualcntchecklistdtl updateDtlEntity = detailRepository.save(detail);
    // logger.info("Successfully updated Visual Control Check Detail with Key ID:
    // {}",
    // updateDtlEntity.getKeyid());
    // savedDetailList.add(updateDtlEntity);
    // continue;
    // }
    // }

    // detail.setModifiedon(LocalDateTime.now());

    // // Save detail (handles both insert and update)
    // GenTlVisualcntchecklistdtl savedDtlEntity = detailRepository.save(detail);
    // savedDetailList.add(savedDtlEntity);
    // }

    // logger.info("Saved {} detail records for master: {}", savedDetailList.size(),
    // masterKeyId);
    // return savedDetailList;
    // }

    /**
 * Save detail records with index-based dtlKeyid matching and DELETE support
 * The dtlKeyid in beanDto represents the index position (1-based) of which detail's keyid to return
 */

    
private List<GenTlVisualcntchecklistdtl> saveDetailRecords(
        List<GenTlVisualcntchecklistdtl> detailList, String masterKeyId, VisualControlChecklistdtlDto beanDto) {
    
    List<GenTlVisualcntchecklistdtl> savedDetailList = new ArrayList<>();

    if (detailList == null || detailList.isEmpty()) {
        logger.info("No detail records to save for master: {}", masterKeyId);
        return savedDetailList;
    }

    boolean setDtlKeyid = false;
    
    for (int i = 0; i < detailList.size(); i++) {
        GenTlVisualcntchecklistdtl detail = detailList.get(i);
        
        // Set foreign key to master
        detail.setVccl_keyid(masterKeyId);

        // CONDITION 1: UPDATE - has keyid, vccd_keyid, and criteriaval
        if (detail.getKeyid() != null && !detail.getKeyid().trim().isEmpty() 
                && detail.getVccd_keyid() != null && !detail.getVccd_keyid().trim().isEmpty()
              //  && detail.getCriteriaval() != null && !detail.getCriteriaval().trim().isEmpty())
                && detail.getCriteriaval() != null&& !Character.isWhitespace(detail.getCriteriaval()))
 {
            
            // UPDATE existing record
            detail.setModifiedon(LocalDateTime.now());
            GenTlVisualcntchecklistdtl updateDtlEntity = detailRepository.save(detail);
            logger.info("Successfully updated Visual Control Check Detail with Key ID: {}", 
                    updateDtlEntity.getKeyid());
            savedDetailList.add(updateDtlEntity);
            
            // Check if this is the record to return keyid for (only if beanDto is not null)
            if (beanDto != null && beanDto.getDtlKeyid() != null 
                    && !beanDto.getDtlKeyid().equals("null") 
                    && !setDtlKeyid) {
                try {
                    int requestedIndex = Integer.parseInt(beanDto.getDtlKeyid());
                    if (requestedIndex == (i + 1)) {
                        beanDto.setDtlKeyid(detail.getKeyid());
                        setDtlKeyid = true;
                        logger.info("Set dtlKeyid in bean to: {} for index position: {}", 
                                detail.getKeyid(), requestedIndex);
                    }
                } catch (NumberFormatException e) {
                    logger.warn("dtlKeyid is not a valid number: {}", beanDto.getDtlKeyid());
                }
            }
        } 
        // CONDITION 2: DELETE - has keyid, but vccd_keyid is null/empty, and has criteriaval
        else if (detail.getKeyid() != null && !detail.getKeyid().trim().isEmpty()
                && (detail.getVccd_keyid() == null || detail.getVccd_keyid().trim().isEmpty())
               // && detail.getCriteriaval() != null && !detail.getCriteriaval().trim().isEmpty()) 
               && detail.getCriteriaval() != null&& !Character.isWhitespace(detail.getCriteriaval())){
            
            // DELETE record by keyid
            try {
                detailRepository.deleteById(detail.getKeyid());
                logger.info("Successfully deleted Visual Control Check Detail with Key ID: {}", 
                        detail.getKeyid());
            } catch (Exception e) {
                logger.error("Failed to delete detail with Key ID: {}", detail.getKeyid(), e);
                throw new RuntimeException("Failed to delete detail record: " + e.getMessage(), e);
            }
            // Don't add to saved list as it's deleted
            continue;
            
        } 
        // CONDITION 3: INSERT - new record
        else {
            // Skip if vccd_keyid is null or empty (invalid record)
            if (detail.getVccd_keyid() == null || detail.getVccd_keyid().trim().isEmpty()) {
                logger.warn("Skipping detail record at index {} - vccd_keyid is null or empty", i);
                continue;
            }
            
            // CREATE - Generate new ID
            try {
                String newDtlKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER_DTL, KEY_LENGTH, PREFIX_DTL, DATE_FORMAT, FORMAT_RESET);

                if (newDtlKeyid == null || newDtlKeyid.trim().isEmpty()) {
                    throw new IllegalStateException("Failed to generate Detail Key ID - sequence returned null");
                }

                detail.setKeyid(newDtlKeyid);
                detail.setCreatedon(LocalDateTime.now());
                detail.setModifiedon(LocalDateTime.now());
                
                // Save detail
                GenTlVisualcntchecklistdtl savedDtlEntity = detailRepository.save(detail);
                savedDetailList.add(savedDtlEntity);
                
                logger.info("Generated and saved new Detail Key ID: {} at index: {}", newDtlKeyid, i);

                // Check if this is the record to return keyid for (only if beanDto is not null)
                if (beanDto != null && beanDto.getDtlKeyid() != null 
                        && !beanDto.getDtlKeyid().equals("null") 
                        && !setDtlKeyid) {
                    try {
                        int requestedIndex = Integer.parseInt(beanDto.getDtlKeyid());
                        if (requestedIndex == (i + 1)) {
                            beanDto.setDtlKeyid(detail.getKeyid());
                            setDtlKeyid = true;
                            logger.info("Set dtlKeyid in bean to: {} for index position: {}", 
                                    detail.getKeyid(), requestedIndex);
                        }
                    } catch (NumberFormatException e) {
                        logger.warn("dtlKeyid is not a valid number: {}", beanDto.getDtlKeyid());
                    }
                }

            } catch (Exception e) {
                logger.error("Failed to generate or save detail Key ID at index: {}", i, e);
                throw new IllegalStateException("Failed to generate Detail Key ID: " + e.getMessage(), e);
            }
        }
    }

    logger.info("Processed {} detail records for master: {} (saved/updated: {})", 
            detailList.size(), masterKeyId, savedDetailList.size());
    return savedDetailList;
}

    // /**
    // * Save detail records with index-based dtlKeyid matching and DELETE support
    // * The dtlKeyid in beanDto represents the index position (1-based) of which
    // detail's keyid to return
    // */
    // private List<GenTlVisualcntchecklistdtl> saveDetailRecords(
    // List<GenTlVisualcntchecklistdtl> detailList, String masterKeyId,
    // VisualControlChecklistdtlDto beanDto) {

    // List<GenTlVisualcntchecklistdtl> savedDetailList = new ArrayList<>();

    // if (detailList == null || detailList.isEmpty()) {
    // logger.info("No detail records to save for master: {}", masterKeyId);
    // return savedDetailList;
    // }

    // boolean setDtlKeyid = false;

    // for (int i = 0; i < detailList.size(); i++) {
    // GenTlVisualcntchecklistdtl detail = detailList.get(i);

    // // Set foreign key to master
    // detail.setVccl_keyid(masterKeyId);

    // // CONDITION 1: UPDATE - has keyid, vccd_keyid, and criteriaval
    // if (detail.getKeyid() != null && !detail.getKeyid().trim().isEmpty()
    // && detail.getVccd_keyid() != null && !detail.getVccd_keyid().trim().isEmpty()
    // && detail.getCriteriaval() != null &&
    // !detail.getCriteriaval().trim().isEmpty()) {

    // // UPDATE existing record
    // detail.setModifiedon(LocalDateTime.now());
    // GenTlVisualcntchecklistdtl updateDtlEntity = detailRepository.save(detail);
    // logger.info("Successfully updated Visual Control Check Detail with Key ID:
    // {}",
    // updateDtlEntity.getKeyid());
    // savedDetailList.add(updateDtlEntity);

    // // Check if this is the record to return keyid for
    // if (beanDto != null && beanDto.getDtlKeyid() != null
    // && !beanDto.getDtlKeyid().equals("null")
    // && !setDtlKeyid) {
    // try {
    // int requestedIndex = Integer.parseInt(beanDto.getDtlKeyid());
    // if (requestedIndex == (i + 1)) {
    // beanDto.setDtlKeyid(detail.getKeyid());
    // setDtlKeyid = true;
    // logger.info("Set dtlKeyid in bean to: {} for index position: {}",
    // detail.getKeyid(), requestedIndex);
    // }
    // } catch (NumberFormatException e) {
    // logger.warn("dtlKeyid is not a valid number: {}", beanDto.getDtlKeyid());
    // }
    // }
    // }
    // // CONDITION 2: DELETE - has keyid, but vccd_keyid is null/empty, and has
    // criteriaval
    // else if (detail.getKeyid() != null && !detail.getKeyid().trim().isEmpty()
    // && (detail.getVccd_keyid() == null ||
    // detail.getVccd_keyid().trim().isEmpty())
    // && detail.getCriteriaval() != null &&
    // !detail.getCriteriaval().trim().isEmpty()) {

    // // DELETE record by keyid
    // try {
    // detailRepository.deleteById(detail.getKeyid());
    // logger.info("Successfully deleted Visual Control Check Detail with Key ID:
    // {}",
    // detail.getKeyid());
    // } catch (Exception e) {
    // logger.error("Failed to delete detail with Key ID: {}", detail.getKeyid(),
    // e);
    // throw new RuntimeException("Failed to delete detail record: " +
    // e.getMessage(), e);
    // }
    // // Don't add to saved list as it's deleted
    // continue;

    // }
    // // CONDITION 3: INSERT - new record
    // else {
    // // Skip if vccd_keyid is null or empty (invalid record)
    // if (detail.getVccd_keyid() == null ||
    // detail.getVccd_keyid().trim().isEmpty()) {
    // logger.warn("Skipping detail record at index {} - vccd_keyid is null or
    // empty", i);
    // continue;
    // }

    // // CREATE - Generate new ID
    // try {
    // String newDtlKeyid = dbActionTemplate.getSequenceNumber(
    // SEQ_IDENTIFIER_DTL, KEY_LENGTH, PREFIX_DTL, DATE_FORMAT, FORMAT_RESET);

    // if (newDtlKeyid == null || newDtlKeyid.trim().isEmpty()) {
    // throw new IllegalStateException("Failed to generate Detail Key ID - sequence
    // returned null");
    // }

    // detail.setKeyid(newDtlKeyid);
    // detail.setCreatedon(LocalDateTime.now());
    // detail.setModifiedon(LocalDateTime.now());

    // // Save detail
    // GenTlVisualcntchecklistdtl savedDtlEntity = detailRepository.save(detail);
    // savedDetailList.add(savedDtlEntity);

    // logger.info("Generated and saved new Detail Key ID: {} at index: {}",
    // newDtlKeyid, i);

    // // Check if this is the record to return keyid for
    // if (beanDto != null && beanDto.getDtlKeyid() != null
    // && !beanDto.getDtlKeyid().equals("null")
    // && !setDtlKeyid) {
    // try {
    // int requestedIndex = Integer.parseInt(beanDto.getDtlKeyid());
    // if (requestedIndex == (i + 1)) {
    // beanDto.setDtlKeyid(detail.getKeyid());
    // setDtlKeyid = true;
    // logger.info("Set dtlKeyid in bean to: {} for index position: {}",
    // detail.getKeyid(), requestedIndex);
    // }
    // } catch (NumberFormatException e) {
    // logger.warn("dtlKeyid is not a valid number: {}", beanDto.getDtlKeyid());
    // }
    // }

    // } catch (Exception e) {
    // logger.error("Failed to generate or save detail Key ID at index: {}", i, e);
    // throw new IllegalStateException("Failed to generate Detail Key ID: " +
    // e.getMessage(), e);
    // }
    // }
    // }

    // logger.info("Processed {} detail records for master: {} (saved/updated: {})",
    // detailList.size(), masterKeyId, savedDetailList.size());
    // return savedDetailList;
    // }

    @Override
    public Object[] getByKeyidNative(String keyid) {
        logger.info("Fetching Visual Control Checklist by keyid (native): {}", keyid);
        return masterRepository.findByKeyidAsArray(keyid);
    }

    //delete the records
     public ResponseEntity<String> delete(GenTlVisualcontrolchecklist genTlVisualcontrolchecklist) throws Exception
    {
        String vccl_keyid = genTlVisualcontrolchecklist.getKeyid();

       int abnExists = detailRepository.checkAbnormalityExists(vccl_keyid);

       if(abnExists > 0)
       {
        //throw new Exception("Data Not Deleted - Abnormality Exists");
        return ResponseEntity.status(202).body("Data Not Deleted - Abnormality Exists");
       //logger.info("Deletion not allowed");
       }

       detailRepository.deleteChecklistDetails(vccl_keyid);

       masterRepository.deleteChecklistMaster(vccl_keyid);

       return ResponseEntity.status(HttpStatus.OK).body("Data  Deleted Successfully") ;


    }

    
}