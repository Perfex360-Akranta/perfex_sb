package com.akranta.perfex_sb.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.BdmTlRequestDto;
import com.akranta.perfex_sb.model.BdmTlCriticalityassessment;
import com.akranta.perfex_sb.model.BdmTlMchRankSkillHistory;
import com.akranta.perfex_sb.repository.BdmTlCriticalityassessmentRepository;
import com.akranta.perfex_sb.repository.BdmTlMchRankSkillHistoryRepository;
import com.akranta.perfex_sb.service.BdmTlRequestService;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class BdmTlRequestServiceImpl implements BdmTlRequestService {

    @Autowired
    private BdmTlCriticalityassessmentRepository mstRepo;

    @Autowired
    private BdmTlMchRankSkillHistoryRepository dtlRepo;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final String MST_SEQ = "BDM_TL_CRITICALITYASSESSMENT";
    private static final String DTL_SEQ = "BDM_TL_MCHRANKSKILLHISTORY";

    @Transactional
    @Override
    public List<BdmTlCriticalityassessment> saveWorksheetRequest(List<BdmTlRequestDto> requestList) throws Exception {

        List<BdmTlCriticalityassessment> savedMasters = new ArrayList<>();
        
        for (BdmTlRequestDto request : requestList) 
        {

            /* ------------ MASTER ------------ */
            BdmTlCriticalityassessment mst = request.getMaster();
            BigDecimal totalRating = request.getTotalRating();
            BdmTlMchRankSkillHistory dtl = new BdmTlMchRankSkillHistory();  
            
            if (!ValidationUtil.isValidKeyId(mst.getKeyid())) 
            {
                // CREATE MODE
                String mstKey = dbActionTemplate.getSequenceNumber(
                        MST_SEQ, 
                        16, 
                        "CASM",
                        "Y", 
                        "Y");
                mst.setKeyid(mstKey);
                mst.setCreatedon(LocalDateTime.now());
            
                mst.setModifiedon(LocalDateTime.now());
                mst.setActive('Y');
                BdmTlCriticalityassessment savedMst  = mstRepo.save(mst);
                savedMasters.add(savedMst);
                LocalDateTime date1 = LocalDateTime.now();
                String equipmentId = mst.getEquipmentid();

                int deleted = dtlRepo.deleteByEquipmentIdAndDate(equipmentId,date1);
                String dtlKeyId = dbActionTemplate.getSequenceNumber(
                            DTL_SEQ, 
                            8, 
                            "MRS",
                            "", 
                            "Y");

                String flid = mst.getFlid();
                String tradeId = mst.getTradeid();
                String createdBy = mst.getCreatedby();
                

                String rank = mstRepo.getCriteriaKeyId(flid, totalRating, equipmentId, tradeId);

                
                dtl.setMrshKeyid(dtlKeyId);
                dtl.setMrshEquipmentid(mst.getEquipmentid());
                dtl.setMrshRatings(totalRating);
                dtl.setMrshRank(rank);
                dtl.setMrshDate(LocalDateTime.now());   
                dtl.setMrshTempfield1('-');
                dtl.setCreatedBy("USR0001");
                dtl.setMrshActive('Y');
                dtl.setMrshCreatedon(date1);
                dtl.setMrshModifiedon(date1);

                dtlRepo.save(dtl);
            } 
            else {
                // UPDATE MODE
                if (!mstRepo.existsById(mst.getKeyid())) {
                    throw new RuntimeException("Master not found: " + mst.getKeyid());
                }

                mst.setModifiedon(LocalDateTime.now());
                mst.setActive('Y');
                BdmTlCriticalityassessment savedMst  = mstRepo.save(mst);
                savedMasters.add(savedMst);
                
                LocalDateTime date1 = LocalDateTime.now();
                String equipmentId = mst.getEquipmentid();

                // Delete existing detail records
                int deleted = dtlRepo.deleteByEquipmentIdAndDate(equipmentId, date1);
                
                // Generate new detail key
                String dtlKeyId = dbActionTemplate.getSequenceNumber(
                            DTL_SEQ, 
                            8, 
                            "MRS",
                            "", 
                            "Y");

                String flid = mst.getFlid();
                String tradeId = mst.getTradeid();
                String createdBy = mst.getCreatedby();
                
                String rank = mstRepo.getCriteriaKeyId(flid, totalRating, equipmentId, tradeId);

                // Insert new detail record
                dtl.setMrshKeyid(dtlKeyId);
                dtl.setMrshEquipmentid(mst.getEquipmentid());
                dtl.setMrshRatings(totalRating);
                dtl.setMrshRank(rank);
                dtl.setMrshDate(LocalDateTime.now());   
                dtl.setMrshTempfield1('-');
                dtl.setCreatedBy("USR0001");
                dtl.setMrshActive('Y');
                dtl.setMrshCreatedon(date1);
                dtl.setMrshModifiedon(date1);

                dtlRepo.save(dtl);
            }
        }
        
        return savedMasters;
    }

      @Override
    @Transactional(readOnly = true)
    public String getCriteriaKeyId(String flId, BigDecimal totalPoints, String equipmentId, String tradeId) {
        //logger.info("Fetching criteria key ID for flId: {}, totalPoints: {}, equipmentId: {}, tradeId: {}", 
                    //flId, totalPoints, equipmentId, tradeId);
        
        String criteriaKeyId = mstRepo.getCriteriaKeyId(flId, totalPoints, equipmentId, tradeId);
        
        if (criteriaKeyId == null || criteriaKeyId.isEmpty()) {
           // logger.warn("No criteria key ID found for the given parameters");
            return null;
        }
        
        //logger.info("Successfully retrieved criteria key ID: {}", criteriaKeyId);
        return criteriaKeyId;
    }
     



    @Override
    @Transactional(readOnly = true)
    public String getFlid(String parentFlid) throws Exception {
        if (parentFlid == null || parentFlid.trim().isEmpty()) {
            throw new IllegalArgumentException("Parent FLID cannot be null or empty");
        }
        
        String flid = mstRepo.getFlidByParent(parentFlid);
        
        if (flid == null || flid.trim().isEmpty()) {
            throw new Exception("FLID not found for parent FLID: " + parentFlid);
        }
        
        return flid;
    }
    @Override
    @Transactional(readOnly = true)
    public String getCriticalityAssessmentRemarks(String flid, String equipmentId) throws Exception {
        //logger.info("Getting criticality assessment remarks for FLID: {} and Equipment ID: {}", flid, equipmentId);
        
        if (flid == null || flid.trim().isEmpty()) {
            throw new IllegalArgumentException("FLID cannot be null or empty");
        }
        
        if (equipmentId == null || equipmentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Equipment ID cannot be null or empty");
        }
        
        String remarks = mstRepo.getCriticalityAssessmentRemarks(flid, equipmentId);
        
       // logger.info("Retrieved remarks: {}", remarks);
        
        // Note: remarks can be null or empty - this is valid if no remarks exist
        return remarks != null ? remarks : "";
    }


    @Transactional
    @Override
    public int deleteCriteriaList(List<String> keyIds) throws Exception {
        //logger.info("Deleting criteria records with keyIds: {}", keyIds);
        
        if (keyIds == null || keyIds.isEmpty()) {
            throw new IllegalArgumentException("Key ID list cannot be null or empty");
        }
        
        // Validate each key ID
        for (String keyId : keyIds) {
            if (keyId == null || keyId.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid key ID in list: " + keyId);
            }
        }
        
        int rowsAffected = mstRepo.deleteCriteriaByKeyIds(keyIds);
        
       // logger.info("Successfully deleted {} criteria records", rowsAffected);
        return rowsAffected;
    }

    
}