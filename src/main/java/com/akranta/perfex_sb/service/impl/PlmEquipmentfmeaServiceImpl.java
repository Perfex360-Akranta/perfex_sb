package com.akranta.perfex_sb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.PlmEquipmentfmeaDTO;
import com.akranta.perfex_sb.dto.EquipmentfmeaParamDTO;
import com.akranta.perfex_sb.model.PlmtlEquipmentfmeaDTL;
import com.akranta.perfex_sb.model.PlmtlEquipmentfmeaMST;
import com.akranta.perfex_sb.repository.PlmEquipmentfmeaDtlRepository;
import com.akranta.perfex_sb.repository.PlmEquipmentfmeaMstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.PlmEquipmentfmeaService;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class PlmEquipmentfmeaServiceImpl implements PlmEquipmentfmeaService {

    private static final Logger logger = LoggerFactory.getLogger(PlmEquipmentfmeaServiceImpl.class);

    @Autowired
    private PlmEquipmentfmeaDtlRepository plmEquipmentfmeaDtlRepository;

    @Autowired
    private PlmEquipmentfmeaMstRepository plmEquipmentfmeaMstRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "PLM_TL_EQUIPMENTFMEAMST";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX = "FEM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_DTL = "PLM_TL_EQUIPMENTFMEADTL";
    private static final String PREFIX_DTL = "FED";

    @Override
    @Transactional
    public ResponseEntity<?> save(PlmEquipmentfmeaDTO plmEquipmentfmeaDTO) {
        logger.info("ENTERED INTO THE SERVICE");
        if (plmEquipmentfmeaDTO == null || plmEquipmentfmeaDTO.getPlmtlequipmentfmeaMST() == null) {
            return ResponseEntity.badRequest().body("Request body or Master data cannot be null");
        }

        PlmtlEquipmentfmeaMST mst = plmEquipmentfmeaDTO.getPlmtlequipmentfmeaMST();
        List<PlmtlEquipmentfmeaDTL> dtlList = plmEquipmentfmeaDTO.getPlmtlequipmentfmeaDTL();

        PlmEquipmentfmeaDTO resultDto = new PlmEquipmentfmeaDTO();
        List<PlmtlEquipmentfmeaDTL> resultDtlList = new ArrayList<>();

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
                PlmtlEquipmentfmeaMST savedMst = plmEquipmentfmeaMstRepository.save(mst);
                resultDto.setPlmtlequipmentfmeaMST(savedMst);

                if (dtlList != null && !dtlList.isEmpty()) {
                    for (PlmtlEquipmentfmeaDTL dtl : dtlList) {
                        if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
                            String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                                    PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                            if (newDetailKeyId == null || newDetailKeyId.trim().isEmpty()) {
                                logger.info("Failed To Generate the Detail Key Id");
                                throw new RuntimeException("Failed to generate Detail Key ID");
                            }

                            logger.info("Generated new Key ID: {} Detail Keyid", newDetailKeyId);
                            dtl.setKeyid(newDetailKeyId);
                            dtl.setFmeq_keyid(savedMst.getKeyid());
                            PlmtlEquipmentfmeaDTL savedDtl = plmEquipmentfmeaDtlRepository.save(dtl);
                            resultDtlList.add(savedDtl);
                        } else {
                            // Even if ID provided, link to new master
                            dtl.setFmeq_keyid(savedMst.getKeyid());
                            PlmtlEquipmentfmeaDTL savedDtl = plmEquipmentfmeaDtlRepository.save(dtl);
                            resultDtlList.add(savedDtl);
                        }
                    }
                    resultDto.setPlmtlequipmentfmeaDTL(resultDtlList);
                }

            } else {
                logger.info("ENTERED INTO THE UPDATE");
                if (plmEquipmentfmeaMstRepository.existsById(mst.getKeyid())) {
                    PlmtlEquipmentfmeaMST updateMst = plmEquipmentfmeaMstRepository.save(mst);
                    resultDto.setPlmtlequipmentfmeaMST(updateMst);

                    if (dtlList != null && !dtlList.isEmpty()) {
                        for (PlmtlEquipmentfmeaDTL dtl : dtlList) {
                            // Ensure it's linked to this master
                            if (mst.getKeyid().equals(dtl.getFmeq_keyid())) {
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
                                    dtl.setFmeq_keyid(updateMst.getKeyid());
                                    PlmtlEquipmentfmeaDTL savedDtl = plmEquipmentfmeaDtlRepository.save(dtl);
                                    resultDtlList.add(savedDtl);
                                } else {
                                    // Existing Detail
                                    logger.info("ENTERED INTO THE DETAIL UPDATE IN MST UPDATE");
                                    dtl.setFmeq_keyid(updateMst.getKeyid());
                                    PlmtlEquipmentfmeaDTL updateDetail = plmEquipmentfmeaDtlRepository.save(dtl);
                                    resultDtlList.add(updateDetail);
                                }
                            } else {

                                if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
                                    String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL,
                                            KEY_LENGTH,
                                            PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                                    dtl.setKeyid(newDetailKeyId);
                                }
                                dtl.setFmeq_keyid(updateMst.getKeyid());
                                PlmtlEquipmentfmeaDTL savedDtl = plmEquipmentfmeaDtlRepository.save(dtl);
                                resultDtlList.add(savedDtl);
                            }
                        }
                        resultDto.setPlmtlequipmentfmeaDTL(resultDtlList);
                    }
                } else {

                    PlmtlEquipmentfmeaMST savedMst = plmEquipmentfmeaMstRepository.save(mst);
                    resultDto.setPlmtlequipmentfmeaMST(savedMst);

                    if (dtlList != null && !dtlList.isEmpty()) {
                        for (PlmtlEquipmentfmeaDTL dtl : dtlList) {
                            if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
                                String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL,
                                        KEY_LENGTH,
                                        PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                                dtl.setKeyid(newDetailKeyId);
                            }
                            dtl.setFmeq_keyid(savedMst.getKeyid());
                            PlmtlEquipmentfmeaDTL savedDtl = plmEquipmentfmeaDtlRepository.save(dtl);
                            resultDtlList.add(savedDtl);
                        }
                        resultDto.setPlmtlequipmentfmeaDTL(resultDtlList);
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
    public ResponseEntity<?> update(PlmEquipmentfmeaDTO plmEquipmentfmeaDTO) {
        return save(plmEquipmentfmeaDTO);
    }

    @Override
    public ResponseEntity<?> getAll(PlmEquipmentfmeaDTO plmEquipmentfmeaDTO) {
        try {
            List<PlmtlEquipmentfmeaMST> list = plmEquipmentfmeaMstRepository.findAll();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            logger.error("Error Getting All", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public void deleteDtls(List<EquipmentfmeaParamDTO> paramDtoList) throws Exception {
        logger.info("ENTERING deleteDtls with flat DTO list, size={}", paramDtoList.size());
        try {
            if (paramDtoList == null || paramDtoList.isEmpty()) {
                return;
            }

            String mstKeyId = null;

            for (EquipmentfmeaParamDTO dto : paramDtoList) {
                mstKeyId = dto.getFmeq_keyid();
                String dtlKeyId = dto.getFmed_keyid();
                String reviewBy = dto.getFmed_reviewby();

                // Case: Delete detail record if reviewby is not a valid Key ID
                if (dtlKeyId != null && !ValidationUtil.isValidKeyId(reviewBy)) {
                    logger.info("Deleting DTL record: {}", dtlKeyId);
                    plmEquipmentfmeaDtlRepository.deleteByKeyId(dtlKeyId);
                }
                // CASE 2: UPDATE REVIEW (Reset fields)
                else if (dtlKeyId != null) {
                    logger.info("Updating review (reset) for DTL record: {}", dtlKeyId);
                    plmEquipmentfmeaDtlRepository.updateReviewByKeyId(dtlKeyId);
                }
            }

            if (ValidationUtil.isValidKeyId(mstKeyId)) {
                long cnt = plmEquipmentfmeaDtlRepository.countByMstKeyId(mstKeyId);
                if (cnt == 0) {
                    plmEquipmentfmeaMstRepository.deleteByKeyId(mstKeyId);
                }
            }
        } catch (Exception e) {
            logger.error("Error in deleteDtls (flat DTO-based)", e);
            throw new Exception(e.getMessage(), e);
        }
    }

}
