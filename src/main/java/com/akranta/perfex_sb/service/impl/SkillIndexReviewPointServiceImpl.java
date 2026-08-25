package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.EmpListDto;
import com.akranta.perfex_sb.dto.getSkillIndexRadarChartDto;
import com.akranta.perfex_sb.dto.saveSkillDto;
// import com.akranta.perfex_sb.model.EntTlSkillIndexEmpCount;
import com.akranta.perfex_sb.model.EntTlSkillindexassessdtl;
import com.akranta.perfex_sb.model.EntTlSkillindexassessmst;

import com.akranta.perfex_sb.repository.EntTlSkillIndexScoreRepo;
import com.akranta.perfex_sb.repository.EntTlSkillindexassessdtlRepository;
import com.akranta.perfex_sb.repository.EntTlSkillindexassessmstRepository;

import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.SkillIndexReviewPointService;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class SkillIndexReviewPointServiceImpl implements SkillIndexReviewPointService {

    @Autowired
    private EntTlSkillindexassessmstRepository mstRepository;

    @Autowired
    private EntTlSkillindexassessdtlRepository dtlRepository;

    @Autowired
    private EntTlSkillIndexScoreRepo repo;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KnowwhyServiceImpl.class);

    private static final String SEQ_IDENTIFIER = "ENT_TL_SKILLINDEXASSESSMST";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "SIAM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_BCK = "ENT_TL_SKILL_IDX_DMTJHEMP";
    private static final String PREFIX_BCK = "SDJE";
    private static final String DATE_FORMAT_BCK = "MMYY";

    private static final String SEQ_IDENTIFIER_DTL = "ENT_TL_SKILLINDEXASSESSDTL";
    private static final String PREFIX_DTL = "SIAD";

    // @Override
    // @Transactional
    // public saveSkillDto saveSkillAssessment(saveSkillDto dto) throws Exception {

    //     EntTlSkillindexassessmst mst = dto.getSkillAssessmentmstList();
    //     List<EntTlSkillindexassessdtl> dtls = dto.getSkillindexassessdtlsList();
    //     saveSkillDto result = new saveSkillDto();
    //     List<EntTlSkillindexassessdtl> detailsResult = new ArrayList<>();

    //     // Update total Employee count
    //     Long empCount = mstRepository.getEmployeeCount(mst.getFlid(), mst.getUniqueposid());
    //     mst.setTempfiled1(empCount.toString());

    //     if (!ValidationUtil.isValidKeyId(mst.getKeyid())) {
    //         String newMstKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX,
    //                 FORMAT_RESET, DATE_FORMAT);

    //         if (newMstKeyId == null || newMstKeyId.trim().isEmpty()) {
    //             logger.info("Failed To Generate the Key Id", newMstKeyId);
    //             throw new RuntimeException("Failed to generate Master Key ID");
    //         }

    //         logger.info("Generated new Key ID: {} Master Keyid", newMstKeyId);
    //         mst.setKeyid(newMstKeyId);
    //         EntTlSkillindexassessmst savedMst = mstRepository.save(mst);
    //         result.setSkillAssessmentmstList(savedMst);

    //     } else {
    //          if (dtlRepository.existsCurrentHalfYear(mst.getKeyid(), dtls.get(0).getReviewhalf())) {
    //             EntTlSkillindexassessmst updateEntity = mstRepository.save(mst);
    //             result.setSkillAssessmentmstList(updateEntity);
    //         }
    //         // EntTlSkillindexassessmst updateEntity = mstRepository.save(mst);
    //         // result.setSkillAssessmentmstList(updateEntity);
    //     }

    //     for (EntTlSkillindexassessdtl detail : dtls) {
    //         logger.info("Entering into Detail ");

    //         String masterKeyId = mst.getKeyid();
    //         detail.setSiam_keyid(masterKeyId);

    //         String empmKeyid = detail.getEmpm_keyid();
    //         String reviewId = detail.getReviewid();
    //         logger.info("Checking Valid Detail Key Id {} {} {}", masterKeyId, empmKeyid, reviewId);

    //         String detailKeyId = dtlRepository.findDetailKeyId(masterKeyId, empmKeyid, reviewId);
    //         logger.info("Checking Valid Detail Key Id {}", detailKeyId);
    //         if (ValidationUtil.isValidKeyId(detailKeyId)) {
    //             dtlRepository.deleteExistingDetail(masterKeyId, empmKeyid, reviewId);
    //         }
    //         String afterDeleteDtlKeyId = dtlRepository.findDetailKeyId(masterKeyId, empmKeyid, reviewId);
    //         if (!ValidationUtil.isValidKeyId(afterDeleteDtlKeyId)) {
    //             String newDetail = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH, PREFIX_DTL,
    //                     FORMAT_RESET, DATE_FORMAT);

    //             if (newDetail == null || newDetail.trim().isEmpty()) {
    //                 logger.info("Failed To Generate the Key Id", newDetail);
    //                 throw new RuntimeException("Failed to generate Master Key ID");
    //             }

    //             logger.info("Generated new Key ID: {} dETAIL Keyid", newDetail);
    //             detail.setKeyid(newDetail);
    //             EntTlSkillindexassessdtl savedDtl = dtlRepository.save(detail);
    //             detailsResult.add(savedDtl);

    //         } else {
    //             EntTlSkillindexassessdtl updateDtl = dtlRepository.save(detail);
    //             detailsResult.add(updateDtl);
    //         }
    //         result.setSkillindexassessdtlsList(detailsResult);
    //     }

    //     // saveSkillIndexAssessmentScoreCountBackup(result);
    //     // saveSkillIndexDmtjhemp(mst.getCreatedby());
    //     dtlRepository.updateTotal(result.getSkillAssessmentmstList().getKeyid());

    //     String siamKeyid = result.getSkillAssessmentmstList().getKeyid();
    //     String flid = mst.getFlid();
    //     LocalDateTime reviewDate = mst.getReviewdate();
    //     String uniqueposid = mst.getUniqueposid();
    //     String createdBy = mst.getCreatedby();

    //     logger.info("Calling sp_insert_skill_score_count for siamKeyid: {}", siamKeyid);
    //     repo.callSkillScoreCountProcedure(siamKeyid, flid, reviewDate, createdBy);
    //     logger.info("sp_insert_skill_score_count executed successfully for siamKeyid: {}", siamKeyid);
    //     return result;

    // }
    @Override
    @Transactional
    public saveSkillDto saveSkillAssessment(saveSkillDto dto) throws Exception {

        EntTlSkillindexassessmst mst = dto.getSkillAssessmentmstList();
        List<EntTlSkillindexassessdtl> dtls = dto.getSkillindexassessdtlsList();
        saveSkillDto result = new saveSkillDto();
        List<EntTlSkillindexassessdtl> detailsResult = new ArrayList<>();
       Long ASSESSED_REMOVED_COUNT ;
        // Update total Employee count
        Long empCount = mstRepository.getEmployeeCount(mst.getFlid(), mst.getUniqueposid());
        

        if (!ValidationUtil.isValidKeyId(mst.getKeyid())) {
            String newMstKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX,
                    FORMAT_RESET, DATE_FORMAT);

            if (newMstKeyId == null || newMstKeyId.trim().isEmpty()) {
                logger.info("Failed To Generate the Key Id", newMstKeyId);
                throw new RuntimeException("Failed to generate Master Key ID");
            }
           mst.setTempfiled1(empCount.toString());
            logger.info("Generated new Key ID: {} Master Keyid", newMstKeyId);
            mst.setKeyid(newMstKeyId);
            EntTlSkillindexassessmst savedMst = mstRepository.save(mst);
            result.setSkillAssessmentmstList(savedMst);

        } else {
			
            ASSESSED_REMOVED_COUNT = mstRepository.getActiveEmployeeCount(mst.getFlid(), mst.getUniqueposid(),mst.getKeyid());
            logger.info("Employee Count : {},{} ", empCount,ASSESSED_REMOVED_COUNT);
            empCount = empCount + ASSESSED_REMOVED_COUNT ;
            
			// NEED TO ADD HERE - SWETHA
             mst.setTempfiled1(empCount.toString());
            LocalDateTime existingReviewDate = mstRepository.getReviewDate(mst.getKeyid());
            mst.setReviewdate(existingReviewDate);

            EntTlSkillindexassessmst updateEntity = mstRepository.save(mst);
            result.setSkillAssessmentmstList(updateEntity);

            // EntTlSkillindexassessmst updateEntity = mstRepository.save(mst);
            // result.setSkillAssessmentmstList(updateEntity);
        }

        String prevEmpKeyid = "";

        for (EntTlSkillindexassessdtl detail : dtls) {
            logger.info("Entering into Detail ");

            String masterKeyId = mst.getKeyid();
            detail.setSiam_keyid(masterKeyId);

            String empmKeyid = detail.getEmpm_keyid();
            String reviewId = detail.getReviewid();
            //logger.info("Checking Valid Detail Key Id {} {} {}", masterKeyId, empmKeyid, reviewId);
logger.info("Checking Valid Detail Key Id {} {} {},{}", masterKeyId, empmKeyid, reviewId,prevEmpKeyid);

            if(!prevEmpKeyid.equals(empmKeyid)){

                String InactiveFlid = dtlRepository.inactivateExistingAssessmentFlid(
                        detail.getEmpm_keyid(),
                        detail.getReviewhalf(),
                        mst.getFlid(),
                        mst.getUniqueposid());
                    logger.info("Existing Skill Flid: {}", InactiveFlid);

                int inactiveSkillCount = dtlRepository.inactivateExistingAssessment(
                        detail.getEmpm_keyid(),
                        detail.getReviewhalf(),
                        mst.getFlid(),
                        mst.getUniqueposid());
                logger.info("inactiveSkillCount: {}", inactiveSkillCount);
                if(inactiveSkillCount > 0){
                    
                    int updateSkillCount = dtlRepository.updateSkillIndexCountTempField(InactiveFlid,detail.getReviewhalf());
                    logger.info("Updated Skill Count: {}", updateSkillCount);
                }
                 

                 
                 prevEmpKeyid = empmKeyid;
            }
            String detailKeyId = dtlRepository.findDetailKeyId(masterKeyId, empmKeyid, reviewId);
            logger.info("Checking Valid Detail Key Id {}", detailKeyId);
            if (ValidationUtil.isValidKeyId(detailKeyId)) {
                dtlRepository.deleteExistingDetail(masterKeyId, empmKeyid, reviewId);
            }
            String afterDeleteDtlKeyId = dtlRepository.findDetailKeyId(masterKeyId, empmKeyid, reviewId);
            if (!ValidationUtil.isValidKeyId(afterDeleteDtlKeyId)) {
                String newDetail = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH, PREFIX_DTL,
                        FORMAT_RESET, DATE_FORMAT);

                if (newDetail == null || newDetail.trim().isEmpty()) {
                    logger.info("Failed To Generate the Key Id", newDetail);
                    throw new RuntimeException("Failed to generate Master Key ID");
                }

                logger.info("Generated new Key ID: {} dETAIL Keyid", newDetail);
                detail.setKeyid(newDetail);

                // NEED TO ADD HERE - SWETHA

                // dtlRepository.inactivateExistingAssessment(
                //         detail.getEmpm_keyid(),
                //         detail.getReviewhalf(),
                //         mst.getFlid(),
                //         mst.getUniqueposid());

                EntTlSkillindexassessdtl savedDtl = dtlRepository.save(detail);
                detailsResult.add(savedDtl);

            } else {
                // if (!ValidationUtil.isValidKeyId(detail.getSiam_keyid())) {
                // detail.setSiam_keyid(result.getSkillAssessmentmstList().getKeyid());
                // }
                EntTlSkillindexassessdtl updateDtl = dtlRepository.save(detail);
                detailsResult.add(updateDtl);
            }
            result.setSkillindexassessdtlsList(detailsResult);
        }

        // saveSkillIndexAssessmentScoreCountBackup(result);
        // saveSkillIndexDmtjhemp(mst.getCreatedby());
        dtlRepository.updateTotal(result.getSkillAssessmentmstList().getKeyid());

        String siamKeyid = result.getSkillAssessmentmstList().getKeyid();
        String flid = mst.getFlid();
        LocalDateTime reviewDate = mst.getReviewdate();
        String uniqueposid = mst.getUniqueposid();
        String createdBy = mst.getCreatedby();

        logger.info("Calling sp_insert_skill_score_count for siamKeyid: {}", siamKeyid);
        repo.callSkillScoreCountProcedure(siamKeyid, flid, reviewDate, createdBy);
        logger.info("sp_insert_skill_score_count executed successfully for siamKeyid: {}", siamKeyid);
        return result;

    }



    @Override
    public List<Map<String, Object>> getEmpList(EmpListDto empListDto) {

        String flid = empListDto.getFlid();
        String reviewDate = empListDto.getStartDate();
        String uniqPosId = empListDto.getUniqPosid();

        String lastDoneDate = mstRepository.getLastDoneDate(flid, uniqPosId);

        if (!ValidationUtil.isValidKeyId(reviewDate)) {
            reviewDate = lastDoneDate;
        }
        List<Map<String, Object>> finalList = mstRepository.getAllEmployees(flid, uniqPosId, reviewDate);
        if (finalList == null) {
            throw new RuntimeException("The List is Empty");
        }
        return finalList;

    }

    @Override
    public List<Map<String, Object>> getSkillIndexRadarChart(getSkillIndexRadarChartDto requestDto) {
        String fromDate = requestDto.getFromDate();
        String flid = requestDto.getFlid();
        String uniqPosId = requestDto.getUniquePosId();
        String empIds = requestDto.getEmpmKeyIds();

        return mstRepository.getSkillIndexRadarChart(fromDate, flid, uniqPosId, empIds);
    }

}
