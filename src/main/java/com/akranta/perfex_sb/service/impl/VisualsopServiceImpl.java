package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.model.Upstreamdefectmst;
import com.akranta.perfex_sb.model.Visualsopdtl;
import com.akranta.perfex_sb.model.Visualsopmst;
import com.akranta.perfex_sb.repository.VisualsopdtlRepository;
import com.akranta.perfex_sb.repository.VisualsopmstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.VisualsopService;
// import com.akranta.perfex_sb.service.list;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class VisualsopServiceImpl implements VisualsopService {

     private static final Logger logger = LoggerFactory.getLogger(VisualsopServiceImpl.class);

     @Autowired
     private VisualsopmstRepository mstRepository;

     @Autowired
     private VisualsopdtlRepository dlRepository;

     @Autowired
     private DbActionTemplate dbActionTemplate;

     private static final String SEQ_IDENTIFIER = "JHA_TL_VISUALSOPMST";
     private static final int KEY_LENGTH = 15;
     private static final String PREFIX = "VSB";
     private static final String DATE_FORMAT = "YY";
     private static final String FORMAT_RESET = "Y";

     private static final String SEQ_IDENTIFIER_DTL = "JHA_TL_VISUALSOPDTL";
     private static final String PREFIX_DTL = "VSDT";

     // create or update for masster table

     @Override
     @Transactional
     public Visualsopmst createorupdateVisualsopmst(Visualsopmst visualsopmst) {
          if (visualsopmst.getKeyid() == null || visualsopmst.getKeyid().trim().isEmpty()) {
               // Generate new ID for insert
               try {
                    String newMstKeyid = dbActionTemplate.getSequenceNumber(
                              SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET);

                    if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                         throw new IllegalStateException("Failed to generate Master Key ID - sequence returned null");
                    }

                    visualsopmst.setKeyid(newMstKeyid);
                    visualsopmst.setCreatedon(LocalDateTime.now());
                    logger.info("Generated new Master Key ID: {}", newMstKeyid);

               } catch (Exception e) {
                    throw new IllegalStateException("Failed to generate Master Key ID: " + e.getMessage(), e);
               }
          } else {
               // UPDATE - else block added
               if (mstRepository.existsById(visualsopmst.getKeyid())) {
                    visualsopmst.setModifiedon(LocalDateTime.now());
                    Visualsopmst updateEntity = mstRepository.save(visualsopmst);
                    logger.info("Successfully updated Audit with Key ID: {}", updateEntity.getKeyid());
                    return updateEntity;
               }
          }

          visualsopmst.setModifiedon(LocalDateTime.now());

          // Save master (handles both insert and update)
          Visualsopmst savedEntity = mstRepository.save(visualsopmst);
          logger.info("Saved Master with Key ID: {}", savedEntity.getKeyid());

          return savedEntity;
     }

     // create or update for detail table

     public Visualsopdtl createorupdateVisualsopdtl(Visualsopdtl visualsopdtl, String masterkeyId) {
          long count = dlRepository.checkDuplicate(visualsopdtl.getInstruction(), visualsopdtl.getVsom_keyid(),
                    visualsopdtl.getKeypoint());

          if (count > 0) {
               logger.info("duplication");
          }
          if (visualsopdtl.getKeyid() == null || visualsopdtl.getKeyid().trim().isEmpty() && count > 0) {

               try {
                    String newMstKeyid = dbActionTemplate.getSequenceNumber(
                              SEQ_IDENTIFIER_DTL, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET);

                    if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                         throw new IllegalStateException("Failed to generate Master Key ID - sequence returned null");
                    }

                    visualsopdtl.setKeyid(newMstKeyid);
                    visualsopdtl.setVsom_keyid(masterkeyId);
                    visualsopdtl.setCreatedon(LocalDateTime.now());
                    logger.info("Generated new Master Key ID: {}", newMstKeyid);

               } catch (Exception e) {
                    throw new IllegalStateException("Failed to generate Master Key ID: " + e.getMessage(), e);
               }
          } else {
               // UPDATE - else block added
               if (dlRepository.existsById(visualsopdtl.getKeyid())) {
                    visualsopdtl.setModifiedon(LocalDateTime.now());
                    Visualsopdtl updateEntity = dlRepository.save(visualsopdtl);
                    logger.info("Successfully updated Audit with Key ID: {}", updateEntity.getKeyid());
                    return updateEntity;
               }
          }

          visualsopdtl.setModifiedon(LocalDateTime.now());

          // Save master (handles both insert and update)
          Visualsopdtl savedEntity = dlRepository.save(visualsopdtl);
          logger.info("Saved Master with Key ID: {}", savedEntity.getKeyid());

          return savedEntity;
     }

     @Override
     public Visualsopdtl getByKeyid(String keyid) {
          return dlRepository.findById(keyid)
                    .orElseThrow(() -> new RuntimeException(
                              "Visualsopdtl not found for keyid: " + keyid));
     }

     @Override
     @Transactional
     public void deleteBydetailKeyId(String keyid) {

          Visualsopdtl visualsopdtl = dlRepository.findById(keyid)
                    .orElseThrow(() -> new EntityNotFoundException(
                              "Visual SOP Detail not found for keyid: " + keyid));

          // ✅ Null check for imgtoolused
          if (visualsopdtl.getImgtoolused() != null && !visualsopdtl.getImgtoolused().isBlank()) {
               dlRepository.deleteByRefKeyId(keyid);
          }

          // ✅ Delete main record
          dlRepository.deleteByKeyId(keyid);
     }

    
     @Override
     public Visualsopmst getByKeyidMst(String keyid)
     {
          return mstRepository.findByKeyid(keyid);
     }


     //fetching details from the detail record
     public List<Map<String,Object>> getdetails(String keyid)
     {
          logger.info("Key Id {}",keyid);
          if(!ValidationUtil.isValidKeyId(keyid))
               {
                    keyid = null;
               }
         return dlRepository.getVisualSopDetails(keyid);
     }

     // @Override
     // @Transactional
     // public void deleteBydetailKeyId(Visualsopdtl visualsopdtl)
     // {
     // if(visualsopdtl.getImgtoolused()!=null)
     // {
     // dlRepository.deleteByRefKeyId(visualsopdtl.getKeyid());
     // }

     // dlRepository.deleteByKeyId(visualsopdtl.getKeyid());

     // }

     // @Override
     // @Transactional
     // public void deleteById(String keyid) {

     // int rows = dlRepository.deleteByKeyId(keyid);

     // if (rows == 0) {
     // throw new RuntimeException(
     // "Record not found for keyid: " + keyid);
     // }
     // }

     // @Override
     // @Transactional
     // public void deletebyImage(String keyId)
     // {
     // int rows = dlRepository.deleteByRefKeyId(keyId);

     // if (rows == 0)
     // {
     // throw new RuntimeException("Record not for keyid:" + keyId);
     // }
     // }

     // deleting both tables by using maaster key id
     @Override
    @Transactional
    public Visualsopmst delete(Visualsopmst visualsopmst) throws Exception {

        try {

            String keyId = visualsopmst.getKeyid();

            // Step 1 → Delete Detail Records
            dlRepository.deletebymasterkeyid(keyId);

            // Step 2 → Delete Master Record
            mstRepository.deleteByKeyId(keyId);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return visualsopmst;
    }

}
