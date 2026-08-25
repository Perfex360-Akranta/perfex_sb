package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.UpstreamdefectmstSaveDto;
import com.akranta.perfex_sb.model.UpstreamdefectDet;
import com.akranta.perfex_sb.model.Upstreamdefectmst;
import com.akranta.perfex_sb.repository.UpstreamdefectDetRepository;
import com.akranta.perfex_sb.repository.UpstreamdefectmstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.UpstreamdefectmstService;
import jakarta.transaction.Transactional;

@Service
public class UpstreamdefectmstServiceImpl implements UpstreamdefectmstService {

     private static final Logger logger = LoggerFactory.getLogger(UpstreamdefectmstServiceImpl.class);

     @Autowired
     private UpstreamdefectmstRepository mstRepository;

     @Autowired
     private UpstreamdefectDetRepository detRepository;

     @Autowired
     private DbActionTemplate dbActionTemplate;

     private static final String SEQ_IDENTIFIER = "TBL_GEN_TL_UPSTREAMDEFECT_MST";
     private static final int KEY_LENGTH = 15;
     private static final String PREFIX = "UPSM";
     private static final String DATE_FORMAT = "YY";
     private static final String FORMAT_RESET = "Y";

     private static final String SEQ_IDENTIFIER_DTL = "TBL_GEN_TL_UPSTREAMDEFECT_DET";
     private static final String PREFIX_DTL = "UPSD";

     @Override
     @Transactional
     public UpstreamdefectmstSaveDto createorupdateUpstreamdefectmst(
               UpstreamdefectmstSaveDto upstreamdefectmstSaveDto) {
          try {
               if (upstreamdefectmstSaveDto == null) {
                    throw new IllegalArgumentException("Defectmst should not be null");

               }
               Upstreamdefectmst upstreamdefectmst = upstreamdefectmstSaveDto.getUpstreamdefectmst();
               UpstreamdefectDet upstreamdefectDet = upstreamdefectmstSaveDto.getUpstreamdefectDet();
               // List<UpstreamdefectDet> UpstreamdefectDet =
               // upstreamdefectmstSaveDto.getUpstreamdefectDet();

               if (upstreamdefectmst == null) {
                    throw new IllegalArgumentException("Upstreamdefect master id should not be null");
               }

               upstreamdefectmst = saveMasterRecord(upstreamdefectmst);

               // List<UpstreamdefectDet> savedDtlList = saveDetailRecord(UpstreamdefectDet,
               // upstreamdefectmst.getKeyid());

               UpstreamdefectDet detail = saveDetailRecord(upstreamdefectDet, upstreamdefectmst.getKeyid());

               UpstreamdefectmstSaveDto response = new UpstreamdefectmstSaveDto();
               response.setUpstreamdefectmst(upstreamdefectmst);
               response.setUpstreamdefectDet(detail);

               return response;
          } catch (IllegalArgumentException e) {
               logger.error("Validation error creating/updating upstream: {}", e.getMessage());
               throw e;
          }
     }

     private Upstreamdefectmst saveMasterRecord(Upstreamdefectmst upstreamdefectmst) {
          if (upstreamdefectmst.getKeyid() == null || upstreamdefectmst.getKeyid().trim().isEmpty()) {
               // Generate new ID for insert
               try {
                    String newMstKeyid = dbActionTemplate.getSequenceNumber(
                              SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET);

                    if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                         throw new IllegalStateException("Failed to generate Master Key ID - sequence returned null");
                    }

                    upstreamdefectmst.setKeyid(newMstKeyid);
                    upstreamdefectmst.setCreatedon(LocalDateTime.now());
                    logger.info("Generated new Master Key ID: {}", newMstKeyid);

               } catch (Exception e) {
                    throw new IllegalStateException("Failed to generate Master Key ID: " + e.getMessage(), e);
               }
          } else {
               // UPDATE - else block added
               if (mstRepository.existsById(upstreamdefectmst.getKeyid())) {
                    upstreamdefectmst.setModifiedon(LocalDateTime.now());
                    Upstreamdefectmst updateEntity = mstRepository.save(upstreamdefectmst);
                    logger.info("Successfully updated Audit with Key ID: {}", updateEntity.getKeyid());
                    return updateEntity;
               }
          }

          upstreamdefectmst.setModifiedon(LocalDateTime.now());

          // Save master (handles both insert and update)
          Upstreamdefectmst savedEntity = mstRepository.save(upstreamdefectmst);
          logger.info("Saved Master with Key ID: {}", savedEntity.getKeyid());

          return savedEntity;
     }

     // one master one detail
     private UpstreamdefectDet saveDetailRecord(UpstreamdefectDet upstreamdefectDet, String masterKeyId) 
     {
          if (upstreamdefectDet.getKeyid() == null || upstreamdefectDet.getKeyid().trim().isEmpty()) 
               {
               // Generate new ID for insert
                try {
                String newDtlKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER_DTL, KEY_LENGTH, PREFIX_DTL, DATE_FORMAT, FORMAT_RESET);

                    if (newDtlKeyid == null || newDtlKeyid.trim().isEmpty()) {
                         throw new IllegalStateException("Failed to generate Master Key ID - sequence returned null");
                    }

                    upstreamdefectDet.setKeyid(newDtlKeyid);
                    upstreamdefectDet.setCreatedon(LocalDateTime.now());
                    upstreamdefectDet.setUpsm_keyid(masterKeyId);
                    

                    logger.info("Generated new Master Key ID: {}", newDtlKeyid);

               } catch (Exception e) 
               {
                    throw new IllegalStateException("Failed to generate Master Key ID: " + e.getMessage(), e);
               }
          } 
          else
               {
               // UPDATE - else block added
               // if (mstRepository.existsById(upstreamdefectDet.getKeyid())) 
               // CORRECT
if (detRepository.existsById(upstreamdefectDet.getKeyid())) 
                    {
                    upstreamdefectDet.setModifiedon(LocalDateTime.now());
                    upstreamdefectDet.setUpsm_keyid(masterKeyId);
                    UpstreamdefectDet updateEntity = detRepository.save(upstreamdefectDet);
                    logger.info("Successfully updated Audit with Key ID: {}", updateEntity.getKeyid());
                    return updateEntity;
               }
          }

          upstreamdefectDet.setModifiedon(LocalDateTime.now());

          // Save master (handles both insert and update)
          UpstreamdefectDet savedEntity = detRepository.save(upstreamdefectDet);
          logger.info("Saved Master with Key ID: {}", savedEntity.getKeyid());

          return savedEntity;
     }

     // private List<UpstreamdefectDet> saveDetailRecord(List<UpstreamdefectDet>
     // upstreamdefectDetlList, String masterKeyId) {
     // List<UpstreamdefectDet> savedDtlList = new ArrayList<>();

     // if (upstreamdefectDetlList == null || upstreamdefectDetlList.isEmpty()) {
     // logger.info("No detail records to save for master: {}", masterKeyId);
     // return savedDtlList;
     // }

     // for (UpstreamdefectDet upstreamdefectDet : upstreamdefectDetlList) {
     // upstreamdefectDet.setUpsm_keyid(masterKeyId);

     // if (upstreamdefectDet.getKeyid() == null ||
     // upstreamdefectDet.getKeyid().trim().isEmpty()) {
     // // Generate new ID for insert
     // try {
     // String newDtlKeyid = dbActionTemplate.getSequenceNumber(
     // SEQ_IDENTIFIER_DTL, KEY_LENGTH, PREFIX_DTL, DATE_FORMAT, FORMAT_RESET);

     // if (newDtlKeyid == null || newDtlKeyid.trim().isEmpty()) {
     // throw new IllegalStateException("Failed to generate Detail Key ID - sequence
     // returned null");
     // }

     // upstreamdefectDet.setKeyid(newDtlKeyid);
     // upstreamdefectDet.setCreatedon(LocalDateTime.now());
     // logger.info("Generated new Detail Key ID: {}", newDtlKeyid);
     // } catch (Exception e) {
     // throw new IllegalStateException("Failed to generate Detail Key ID: " +
     // e.getMessage(), e);
     // }
     // } else {
     // // UPDATE - else block added
     // if (detRepository.existsById(upstreamdefectDet.getKeyid())) {
     // upstreamdefectDet.setModifiedon(LocalDateTime.now());
     // UpstreamdefectDet updateDtlEntity = detRepository.save(upstreamdefectDet);
     // logger.info("Successfully updated Audit Detail with Key ID: {}",
     // updateDtlEntity.getKeyid());
     // savedDtlList.add(updateDtlEntity);
     // continue; // Skip the rest of the loop iteration
     // }
     // }

     // upstreamdefectDet.setModifiedon(LocalDateTime.now());

     // // Save detail (handles both insert and update)
     // UpstreamdefectDet savedDtlEntity = detRepository.save(upstreamdefectDet);
     // savedDtlList.add(savedDtlEntity);
     // }

     // logger.info("Saved {} detail records for master: {}", savedDtlList.size(),
     // masterKeyId);
     // return savedDtlList;
     // }

     @Override
     public List<Map<String, Object>> getElementId(String loginflid, double loginlevel,
               String loginElementid, String empId) {

          logger.info("=== GET ELEMENT ID ===");
          logger.info("loginflid: {}", loginflid);
          logger.info("loginlevel: {}", loginlevel);
          logger.info("loginElementid: {}", loginElementid);
          logger.info("empId: {}", empId);

          List<Object[]> resultList = mstRepository.getElementIdNative(loginflid, loginlevel, empId);

          // Convert Object[] to Map<String, Object>
          List<Map<String, Object>> response = new ArrayList<>();
          for (Object[] row : resultList) {
               Map<String, Object> map = new HashMap<>();
               map.put("fnlnElementid", row[0] != null ? row[0].toString() : "");
               map.put("fnlnKeyid", row[1] != null ? row[1].toString() : "");
               map.put("roleLevel", row[2] != null ? row[2].toString() : "");
               map.put("roleName", row[3] != null ? row[3].toString() : "");
               map.put("roleKeyid", row[4] != null ? row[4].toString() : "");
               response.add(map);
          }

          logger.info("Found {} element records", response.size());

          return response;
     }

     @Override
     public Upstreamdefectmst getbyUpsmId(String keyid) {
          return mstRepository.findById(keyid).orElse(null);
     }

     // public UpstreamdefectDet getbyUpstreamdefectkeyid (String keyid)

     @Override
     public List<Map<String, Object>> getbyUpstreamdefectkeyid(String keyid) {
          return detRepository.recall(keyid);
     }

     // delete the detail and master table
     @Transactional
     public void deleteNewUpstreamDefect(String upsmKeyId) {

          // 1️⃣ Delete child records first
          detRepository.deleteByMasterKey(upsmKeyId);

          // 2️⃣ Delete master record
          mstRepository.deleteMasterByKey(upsmKeyId);
     }

     //delete the detail table by delete keyid
     @Transactional
    public void deleteNewUpstreamDefectDetails(String upsdKeyid) {
        detRepository.deleteByUpsdKeyid(upsdKeyid);
    }

}
