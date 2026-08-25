package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.EmpListDto;
import com.akranta.perfex_sb.dto.saveMultipleSkillDto;

import com.akranta.perfex_sb.model.EntTlSkillindexassessdtl;
import com.akranta.perfex_sb.model.EntTlSkillindexassessmst;
import com.akranta.perfex_sb.repository.EntTlSkillIndexScoreRepo;
import com.akranta.perfex_sb.repository.EntTlSkillindexassessdtlRepository;
import com.akranta.perfex_sb.repository.EntTlSkillindexassessmstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.SkillIndexGridEntryService;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class SkillIndexGridEntryServiceImpl implements SkillIndexGridEntryService {

    @Autowired
    private EntTlSkillindexassessmstRepository mstRepository;

    @Autowired
    private EntTlSkillindexassessdtlRepository dtlRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    @Autowired
    private EntTlSkillIndexScoreRepo repo;

    private static final Logger logger = LoggerFactory.getLogger(SkillIndexGridEntryServiceImpl.class);

    private static final String SEQ_IDENTIFIER = "ENT_TL_SKILLINDEXASSESSMST";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "SIAM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_DTL = "ENT_TL_SKILLINDEXASSESSDTL";
    private static final String PREFIX_DTL = "SIAD";

    @Override
    public List<Map<String, Object>> getEmpListMultiple(EmpListDto empListDto) {

        String flid = empListDto.getFlid();
        String reviewDate = empListDto.getStartDate();
        String uniqPosId = empListDto.getUniqPosid();

        String lastDoneDate = mstRepository.getLastDoneDate(flid, uniqPosId);

        if (!ValidationUtil.isValidKeyId(reviewDate)) {
            reviewDate = lastDoneDate;
        }
        List<Map<String, Object>> finalList = mstRepository.getAllEmployeesMultiple(flid, uniqPosId);
        if (finalList == null) {
            throw new RuntimeException("The List is Empty");
        }
        return finalList;

    }

@Override
    @Transactional
    public saveMultipleSkillDto saveMiltipleSkillAssessment(saveMultipleSkillDto dto) throws Exception {

        List<EntTlSkillindexassessmst> masterList = dto.getEntTlSkillindexassessmsts();
        List<EntTlSkillindexassessdtl> detailList = dto.getEntTlSkillindexassessdtls();
        saveMultipleSkillDto result = new saveMultipleSkillDto();
     Long ASSESSED_REMOVED_COUNT ;
        // Update count
        Long empCount = mstRepository.getEmployeeCount(masterList.get(0).getFlid(), masterList.get(0).getUniqueposid());

        List<EntTlSkillindexassessdtl> detailsResult = new ArrayList<>();
        List<EntTlSkillindexassessmst> masterResult = new ArrayList<>();

        for (EntTlSkillindexassessmst mst : masterList) {

            String uniqPosId = mst.getUniqueposid();
            String flid = mst.getFlid();
            LocalDateTime reviewDate = mst.getReviewdate();
           
            logger.info("Checking Valid Detail Key Id {} {} {}", uniqPosId, flid, reviewDate);

            String masterKeyId = mstRepository.findSiamMasterKeyId(reviewDate, uniqPosId, flid);

            // masterKeyId = mst.getKeyid();//- CHANGE HERE
            if (dtlRepository.existsCurrentHalfYear(mst.getKeyid(), detailList.get(0).getReviewhalf())) {
                  masterKeyId = mst.getKeyid();
                 
            }else{
                mst.setKeyid(null);
            }

            // if (ValidationUtil.isValidKeyId(masterKeyId)) {
            //     mst.setKeyid(masterKeyId); // existing record
            // } else {
            //     mst.setKeyid(null); // force NEW insert
            // }

            logger.info("Checking Valid Master Key Id {}", masterKeyId);

            if (!ValidationUtil.isValidKeyId(mst.getKeyid())) {
                String newMstKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX,
                        FORMAT_RESET, DATE_FORMAT);

                if (newMstKeyId == null || newMstKeyId.trim().isEmpty()) {
                    logger.info("Failed To Generate the Key Id", newMstKeyId);
                    throw new RuntimeException("Failed to generate Master Key ID");
                }

                 mst.setTempfiled1(empCount.toString());// Set count

                logger.info("Generated new Key ID: {} Master Keyid", newMstKeyId);
                mst.setKeyid(newMstKeyId);
                EntTlSkillindexassessmst savedMst = mstRepository.save(mst);
                masterResult.add(savedMst);

            } else {
                logger.info("Entered int Master Update");

                ASSESSED_REMOVED_COUNT = mstRepository.getActiveEmployeeCount(mst.getFlid(), mst.getUniqueposid(),mst.getKeyid());
            logger.info("Employee Count : {},{} ", empCount,ASSESSED_REMOVED_COUNT);
            empCount = empCount + ASSESSED_REMOVED_COUNT ;
            
			// NEED TO ADD HERE - SWETHA
             mst.setTempfiled1(empCount.toString());
				// NEED TO ADD HERE - SWETHA
                LocalDateTime existingReviewDate = mstRepository.getReviewDate(mst.getKeyid());
                mst.setReviewdate(existingReviewDate);
                EntTlSkillindexassessmst updateEntity = mstRepository.save(mst);
                masterResult.add(updateEntity);
            }
            result.setEntTlSkillindexassessmsts(masterResult);
        }

        int i = 0;
        int mastNo = 0;
        String mastKeyId = "0";
        String prevEmpKeyid = "";

        if (masterList.size() >= 0) {
            mastKeyId = masterList.get(0).getKeyid();
        }

        detailList.sort(
                Comparator.comparing(EntTlSkillindexassessdtl::getEmpm_keyid)
                        .thenComparing(EntTlSkillindexassessdtl::getReviewid));

        for (EntTlSkillindexassessdtl detail : detailList) {
            logger.info("Entering into Detail ");

            if ((i % (detailList.size() / masterList.size())) == 0) {
                mastKeyId = masterList.get(mastNo).getKeyid();
                mastNo = mastNo + 1;
            }
            detail.setSiam_keyid(mastKeyId);
            i = i + 1;

            String empmKeyid = detail.getEmpm_keyid();
            String reviewId = detail.getReviewid();
            String siamKeyId = detail.getSiam_keyid();

            logger.info("Checking Valid Detail Key Id {} {} {},{}", siamKeyId, empmKeyid, reviewId,prevEmpKeyid);

            if(!prevEmpKeyid.equals(empmKeyid)){
            String InactiveFlid = dtlRepository.inactivateExistingAssessmentFlid(
                        detail.getEmpm_keyid(),
                        detail.getReviewhalf(),
                        masterResult.get(0).getFlid(),
                        masterResult.get(0).getUniqueposid());
                    logger.info("Existing Skill Flid: {}", InactiveFlid);
                 int inactiveSkillCount = dtlRepository.inactivateExistingAssessment(
                        detail.getEmpm_keyid(),
                        detail.getReviewhalf(),
                        masterResult.get(0).getFlid(),
                        masterResult.get(0).getUniqueposid());
                logger.info("inactiveSkillCount: {}", inactiveSkillCount);
                if(inactiveSkillCount > 0){
                 
                    int updateSkillCount = dtlRepository.updateSkillIndexCountTempField(InactiveFlid,detail.getReviewhalf());
                    logger.info("Updated Skill Count: {}", updateSkillCount);
                }
                 prevEmpKeyid = empmKeyid;
            }

            String detailKeyId = dtlRepository.findDetailKeyId(siamKeyId, empmKeyid, reviewId);

            logger.info("Checking Valid Detail Key Id {}", detailKeyId);

            if (!ValidationUtil.isValidKeyId(detailKeyId)) {
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
                //         masterResult.get(0).getFlid(),
                //         masterResult.get(0).getUniqueposid());
                //  int updateSkillCount = dtlRepository.updateSkillIndexCountTempField(masterResult.get(0).getFlid(),detail.getReviewhalf());

                //  logger.info("Updated Skill Count", updateSkillCount);

                EntTlSkillindexassessdtl savedDtl = dtlRepository.save(detail);
                detailsResult.add(savedDtl);

            } else {
                detail.setKeyid(detailKeyId);
                EntTlSkillindexassessdtl updateDtl = dtlRepository.save(detail);
                detailsResult.add(updateDtl);
            }
        }
        dtlRepository.updateTotal(masterList.get(0).getKeyid());
        String saidSiamKeyid = masterList.get(0).getKeyid();
        String siamFlid = masterList.get(0).getFlid();
        String siamUniquePosId = masterList.get(0).getUniqueposid();
        LocalDateTime siamReviewDate = masterList.get(0).getReviewdate();
        dtlRepository.deleteEmployeesWithZeroScore(siamFlid, siamUniquePosId, siamReviewDate, saidSiamKeyid);
        result.setEntTlSkillindexassessdtls(detailsResult);

        // dtlRepository.deleteEmployeesWithZeroScore(siamFlid, siamUniquePosId,
        // siamReviewDate,saidSiamKeyid);

        result.setEntTlSkillindexassessdtls(detailsResult);
        for (EntTlSkillindexassessmst mst : masterResult) {
            String siamKeyid = mst.getKeyid();
            String flid = mst.getFlid();
            LocalDateTime reviewDate = mst.getReviewdate();
            String uniqueposid = mst.getUniqueposid();
            String createdBy = mst.getCreatedby();

            logger.info("Calling sp_insert_skill_score_count for siamKeyid: {}", siamKeyid);
            repo.callMultipleSkillScoreCountProcedure(
                    siamKeyid,
                    flid,
                    reviewDate,
                    // uniqueposid,
                    createdBy);
            logger.info("sp_insert_skill_score_count completed for siamKeyid: {}", siamKeyid);
        }

        return result;

    }
}
