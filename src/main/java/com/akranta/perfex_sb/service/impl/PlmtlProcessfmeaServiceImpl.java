package com.akranta.perfex_sb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.PlmtlProcessfmeaDto;
import com.akranta.perfex_sb.dto.ProcessfmeaParamDto;
import com.akranta.perfex_sb.model.PlmtlProcessfmeaDTL;
import com.akranta.perfex_sb.model.PlmtlProcessfmeaMST;
import com.akranta.perfex_sb.repository.PlmEquipmentfmeaMstRepository;
import com.akranta.perfex_sb.repository.PlmtlProcessfmeaDtlRepository;
import com.akranta.perfex_sb.repository.PlmtlProcessfmeaMstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.PlmtlProcessfmeaService;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class PlmtlProcessfmeaServiceImpl implements PlmtlProcessfmeaService {

    private static final Logger logger = LoggerFactory.getLogger(PlmtlProcessfmeaServiceImpl.class);
    @Autowired
    private PlmEquipmentfmeaMstRepository plmEquipmentfmeaMstRepository;
    @Autowired
    private PlmtlProcessfmeaDtlRepository plmtlProcessfmeaDtlRepository;
    @Autowired
    private PlmtlProcessfmeaMstRepository plmtlProcessfmeaMstRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "PLM_TL_PROCESSFMEAMST";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX = "FPM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_DTL = "PLM_TL_PROCESSFMEADTL";
    private static final String PREFIX_DTL = "FPD";

    @Override
    @Transactional
    public ResponseEntity<?> save(PlmtlProcessfmeaDto plmtlProcessfmeaDto) {
        logger.info("ENTERED INTO THE SERVICE");
        if (plmtlProcessfmeaDto == null || plmtlProcessfmeaDto.getPlmtlProcessfmeaMST() == null) {
            return ResponseEntity.badRequest().body("Request body or Master data cannot be null");
        }

        PlmtlProcessfmeaMST mst = plmtlProcessfmeaDto.getPlmtlProcessfmeaMST();
        List<PlmtlProcessfmeaDTL> dtlList = plmtlProcessfmeaDto.getPlmtlProcessfmeaDTL();

        PlmtlProcessfmeaDto resultDto = new PlmtlProcessfmeaDto();
        List<PlmtlProcessfmeaDTL> resultDtlList = new ArrayList<>();

        try {
            if (!ValidationUtil.isValidKeyId(mst.getKeyid())) {
                logger.info("ENTERED INTO THE CREATE");
                String newMstKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX,
                        FORMAT_RESET, DATE_FORMAT);

                if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                    logger.info("Failed To Generate the Key Id");
                    throw new RuntimeException("Failed to generate Master Key ID");
                }

                logger.info("Generated new Key ID: {} Master Keyid", newMstKeyid);
                mst.setKeyid(newMstKeyid);
                mst.setNo(newMstKeyid);
                logger.info("Generated new Key ID: {} Master Keyid", mst.getNo());
                PlmtlProcessfmeaMST savedMst = plmtlProcessfmeaMstRepository.save(mst);
                resultDto.setPlmtlProcessfmeaMST(savedMst);

                if (dtlList != null && !dtlList.isEmpty()) {
                    for (PlmtlProcessfmeaDTL dtl : dtlList) {
                        if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
                            String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                                    PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                            if (newDetailKeyId == null || newDetailKeyId.trim().isEmpty()) {
                                logger.info("Failed To Generate the Detail Key Id");
                                throw new RuntimeException("Failed to generate Detail Key ID");
                            }

                            logger.info("Generated new Key ID: {} Detail Keyid", newDetailKeyId);
                            dtl.setKeyid(newDetailKeyId);
                            dtl.setFmpm_keyid(savedMst.getKeyid());
                            PlmtlProcessfmeaDTL savedDtl = plmtlProcessfmeaDtlRepository.save(dtl);
                            resultDtlList.add(savedDtl);
                        } else {
                            // Even if ID provided, link to new master
                            dtl.setFmpm_keyid(savedMst.getKeyid());
                            PlmtlProcessfmeaDTL savedDtl = plmtlProcessfmeaDtlRepository.save(dtl);
                            resultDtlList.add(savedDtl);
                        }
                    }
                    resultDto.setPlmtlProcessfmeaDTL(resultDtlList);
                }

            } else {
                logger.info("ENTERED INTO THE UPDATE");
                if (plmtlProcessfmeaMstRepository.existsById(mst.getKeyid())) {
                    PlmtlProcessfmeaMST updateMst = plmtlProcessfmeaMstRepository.save(mst);
                    resultDto.setPlmtlProcessfmeaMST(updateMst);

                    if (dtlList != null && !dtlList.isEmpty()) {
                        for (PlmtlProcessfmeaDTL dtl : dtlList) {
                            // Ensure it's linked to this master
                            if (mst.getKeyid().equals(dtl.getFmpm_keyid())) {
                                logger.info("ENTERED INTO THE DETAIL PROCESSING IN MST UPDATE");

                                if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
                                    // New Detail
                                    String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL,
                                            KEY_LENGTH,
                                            PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                                    if (newDetailKeyId == null || newDetailKeyId.trim().isEmpty()) {
                                        logger.info("Failed To Generate the Detail Key Id");
                                        throw new RuntimeException("Failed to generate Detail Key ID");
                                    }

                                    logger.info("Generated new Key ID: {} Detail Keyid", newDetailKeyId);
                                    dtl.setKeyid(newDetailKeyId);
                                    dtl.setFmpm_keyid(updateMst.getKeyid());
                                    PlmtlProcessfmeaDTL savedDtl = plmtlProcessfmeaDtlRepository.save(dtl);
                                    resultDtlList.add(savedDtl);
                                } else {
                                    // Existing Detail
                                    logger.info("ENTERED INTO THE DETAIL UPDATE IN MST UPDATE");
                                    dtl.setFmpm_keyid(updateMst.getKeyid());
                                    PlmtlProcessfmeaDTL updateDetail = plmtlProcessfmeaDtlRepository.save(dtl);
                                    resultDtlList.add(updateDetail);
                                }
                            } else {

                                if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
                                    String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL,
                                            KEY_LENGTH,
                                            PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                                    dtl.setKeyid(newDetailKeyId);
                                }
                                dtl.setFmpm_keyid(updateMst.getKeyid());
                                PlmtlProcessfmeaDTL savedDtl = plmtlProcessfmeaDtlRepository.save(dtl);
                                resultDtlList.add(savedDtl);
                            }
                        }
                        resultDto.setPlmtlProcessfmeaDTL(resultDtlList);
                    }
                } else {

                    PlmtlProcessfmeaMST savedMst = plmtlProcessfmeaMstRepository.save(mst);
                    resultDto.setPlmtlProcessfmeaMST(savedMst);

                    if (dtlList != null && !dtlList.isEmpty()) {
                        for (PlmtlProcessfmeaDTL dtl : dtlList) {
                            if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
                                String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL,
                                        KEY_LENGTH,
                                        PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                                dtl.setKeyid(newDetailKeyId);
                            }
                            dtl.setFmpm_keyid(savedMst.getKeyid());
                            PlmtlProcessfmeaDTL savedDtl = plmtlProcessfmeaDtlRepository.save(dtl);
                            resultDtlList.add(savedDtl);
                        }
                        resultDto.setPlmtlProcessfmeaDTL(resultDtlList);
                    }
                }
            }
            return ResponseEntity.ok(resultDto);

        } catch (Exception e) {
            logger.error("Error Saving", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>>recallFmeaByKeyId(String keyId, String type) {

        try {
             List<Map<String, Object>> result;
          

            if ("equipment".equalsIgnoreCase(type)) {
                 result  = plmEquipmentfmeaMstRepository.getEquipmentFmeaByKeyId(keyId);

            } else if ("process".equalsIgnoreCase(type)) {
                 result  = plmtlProcessfmeaMstRepository.getProcessFmeaByKeyId(keyId);

            } else {
                throw new IllegalArgumentException(
                        "Invalid type: " + type + ". Expected equipment or process");
            }

             if (result == null || result.isEmpty()) {
            throw new RuntimeException("Result is empty");
        }

            logger.info("Successfully retrieved {} record(s) for type={}",
                    result.size(), type);

            return result;

        } catch (Exception e) {
            logger.error("Error recalling FMEA for keyId={} type={}",
                    keyId, type, e);
            throw new RuntimeException("Failed to recall FMEA data", e);
        }
    }

    @Override
    @Transactional
    public void deleteDtls(List<ProcessfmeaParamDto> paramDtoList) throws Exception {
        logger.info("ENTERING deleteDtls with flat DTO list, size={}", paramDtoList.size());
        try {
            if (paramDtoList == null || paramDtoList.isEmpty()) {
                return;

            }

            String mstKeyId = null;

            // Using a Set to track involved master records for a final count check
            java.util.Set<String> mstKeyIds = new java.util.HashSet<>();

            for (ProcessfmeaParamDto dto : paramDtoList) {
                mstKeyId = dto.getMstKeyid();
                String dtlKeyId = dto.getFmpd_keyid();
                String reviewBy = dto.getFmpd_reviewby();

                if (mstKeyId != null && !mstKeyId.trim().isEmpty()) {
                    mstKeyIds.add(mstKeyId);
                }

                // Case: Delete detail record if reviewby is not a valid Key ID
                if (dtlKeyId != null && !ValidationUtil.isValidKeyId(reviewBy)) {
                    logger.info("Deleting DTL record: {}", dtlKeyId);
                    plmtlProcessfmeaDtlRepository.deleteByKeyId(dtlKeyId);
                }
                // CASE 2: UPDATE REVIEW (Reset fields)
                else if (dtlKeyId != null) {
                    logger.info("Updating review (reset) for DTL record: {}", dtlKeyId);
                    plmtlProcessfmeaDtlRepository.updateReviewByKeyId(dtlKeyId);
                }
            }

            if (ValidationUtil.isValidKeyId(mstKeyId)) {

                long cnt = plmtlProcessfmeaDtlRepository.countByMstKeyId(mstKeyId);

                if (cnt == 0) {
                    plmtlProcessfmeaMstRepository.deleteByKeyId(mstKeyId);
                }
            }
        }
        

        catch (Exception e) {
            logger.error("Error in deleteDtls (flat DTO-based)", e);
            throw new Exception(e.getMessage(), e);
        }
    }

}





// Check if master records should be deleted (when no details remain)
        // for (String mstKeyId : mstKeyIds) {
        // long cnt = plmtlProcessfmeaDtlRepository.countByMstKeyId(mstKeyId);
        // logger.info("Remaining DTL count for MST {}: {}", mstKeyId, cnt);
        // if (cnt == 0) {
        // logger.info("Deleting MST record: {}", mstKeyId);
        // plmtlProcessfmeaMstRepository.deleteByKeyId(mstKeyId);
        // }

        
// @Override
// @Transactional
// public PlmtlProcessfmeaMST deleteDtls(PlmtlProcessfmeaMST mst) throws
// Exception {
// logger.info("ENTERING deleteDtls for MST KeyId: {}", mst.getKeyid());
// try {
// List<PlmtlProcessfmeaDTL> dtlList = mst.getPlmtlProcessfmeaDTL();
// if (dtlList != null && !dtlList.isEmpty()) {
// for (PlmtlProcessfmeaDTL dtl : dtlList) {
// dtl.setFmpm_keyid(mst.getKeyid());
// if (!ValidationUtil.isValidKeyId(dtl.getReviewby())) {
// logger.info("Deleting detail record: {}", dtl.getKeyid());
// plmtlProcessfmeaDtlRepository.deleteByKeyId(dtl.getKeyid());
// } else {
// logger.info("Updating review for detail record: {}", dtl.getKeyid());
// plmtlProcessfmeaDtlRepository.updateReview(
// dtl.getReseverity_keyid(),
// dtl.getReoccurrence_keyid(),
// dtl.getRedetection_keyid(),
// dtl.getReviewby(),
// dtl.getKeyid());
// }
// }
// }

// long cnt = plmtlProcessfmeaDtlRepository.countByMstKeyId(mst.getKeyid());
// logger.info("Remaining details count for master {}: {}", mst.getKeyid(),
// cnt);

// if (cnt == 0) {
// logger.info("No details remaining, deleting master record: {}",
// mst.getKeyid());
// plmtlProcessfmeaMstRepository.deleteByKeyId(mst.getKeyid());
// }

// } catch (Exception e) {
// logger.error("Error in deleteDtls", e);
// throw new Exception(e.getMessage());
// }
// return mst;
// }

// @Override
// @Transactional
// public void deleteDtls(List<ProcessfmeaParamDto> paramDtoList) throws
// Exception {

// logger.info("ENTERING deleteDtls with DTO list, size={}",
// paramDtoList.size());

// try {
// for (ProcessfmeaParamDto mstDto : paramDtoList) {

// String mstKeyId = mstDto.getMstKeyid();
// logger.info("Processing MST KeyId: {}", mstKeyId);

// List<ProcessfmeaDtlDto> dtlList = mstDto.getDtlList();

// if (dtlList != null && !dtlList.isEmpty()) {

// for (ProcessfmeaDtlDto dtlDto : dtlList) {

// String dtlKeyId = dtlDto.getKeyid();

// // CASE 1: DELETE DTL
// if (!ValidationUtil.isValidKeyId(dtlDto.getReviewby())) {

// logger.info("Deleting DTL record: {}", dtlKeyId);
// plmtlProcessfmeaDtlRepository.deleteByKeyId(dtlKeyId);

// }
// // CASE 2: UPDATE REVIEW
// else {

// logger.info("Updating review for DTL record: {}", dtlKeyId);
// plmtlProcessfmeaDtlRepository.updateReview(
// dtlDto.getReseverity_keyid(),
// dtlDto.getReoccurrence_keyid(),
// dtlDto.getRedetection_keyid(),
// dtlDto.getReviewby(),
// dtlKeyId);
// }
// }
// }

// // Check remaining DTL count for this MST
// long cnt = plmtlProcessfmeaDtlRepository.countByMstKeyId(mstKeyId);

// logger.info("Remaining DTL count for MST {}: {}", mstKeyId, cnt);

// // Delete MST if no DTL remains
// if (cnt == 0) {
// logger.info("Deleting MST record: {}", mstKeyId);
// plmtlProcessfmeaMstRepository.deleteByKeyId(mstKeyId);
// }
// }

// } catch (Exception e) {
// logger.error("Error in deleteDtls (DTO-based)", e);
// throw new Exception(e.getMessage(), e);
// }
// }