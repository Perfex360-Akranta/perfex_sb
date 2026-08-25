package com.akranta.perfex_sb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.KnowWhySaveDto;
import com.akranta.perfex_sb.model.QtmTlKnowwhydtl;
import com.akranta.perfex_sb.model.QtmTlKnowwhymst;
import com.akranta.perfex_sb.repository.QtmTlKnowwhydtlRepository;
import com.akranta.perfex_sb.repository.QtmTlKnowwhymstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.KnowwhyService;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class KnowwhyServiceImpl implements KnowwhyService {

    @Autowired
    private QtmTlKnowwhymstRepository mstRepository;

    @Autowired
    private QtmTlKnowwhydtlRepository dtlRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KnowwhyServiceImpl.class);

    private static final String SEQ_IDENTIFIER = "QTM_TL_KNOWWHYMST";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "KNWM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_DTL = "QTM_TL_KNOWWHYDTL";
    private static final String PREFIX_DTL = "KNWD";

    @Transactional
    public KnowWhySaveDto saveKnowWhy(KnowWhySaveDto dto) throws Exception {
        QtmTlKnowwhymst mst = dto.getQtmTlKnowwhymst();
        QtmTlKnowwhydtl dtl = dto.getQtmTlKnowwhydtl();

        if (!ValidationUtil.isValidKeyId(mst.getKeyid())) {

            String newMstKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX,
                    FORMAT_RESET, DATE_FORMAT);

            if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                logger.info("Failed To Generate the Key Id", newMstKeyid);
                throw new RuntimeException("Failed to generate Master Key ID");
            }

            logger.info("Generated new Key ID: {} Master Keyid", newMstKeyid);
            mst.setKeyid(newMstKeyid);

        } else {
            if (mstRepository.existsById(mst.getKeyid())) {
                QtmTlKnowwhymst updateMst = mstRepository.save(mst);
                KnowWhySaveDto updateDto = new KnowWhySaveDto();
                updateDto.setQtmTlKnowwhymst(updateMst);
                if (dtl != null) {

                    if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
                        String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                                PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                        if (newDetailKeyId == null || newDetailKeyId.trim().isEmpty()) {
                            logger.info("Failed To Generate the Detail Key Id", newDetailKeyId);
                            throw new RuntimeException("Failed to generate Master Key ID");
                        }

                        logger.info("Generated new Key ID: {} Detail Keyid", newDetailKeyId);
                        dtl.setKeyid(newDetailKeyId);
                        dtl.setKnwm_keyid(updateMst.getKeyid());
                        QtmTlKnowwhydtl value = dtlRepository.save(dtl);
                        updateDto.setQtmTlKnowwhydtl(value);
                    } else {
                        dtl.setKnwm_keyid(updateMst.getKeyid());
                        QtmTlKnowwhydtl updateDetail = dtlRepository.save(dtl);
                        updateDto.setQtmTlKnowwhydtl(updateDetail);
                    }

                }
                return updateDto;
            }
        }
        KnowWhySaveDto resultDto = new KnowWhySaveDto();
        QtmTlKnowwhymst saveMst = mstRepository.save(mst);
        resultDto.setQtmTlKnowwhymst(saveMst);
        dtl.setKnwm_keyid(saveMst.getKeyid());
        if (dtl != null) {

            if (!ValidationUtil.isValidKeyId(dtl.getKeyid())) {
                String newDetailKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                        PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                if (newDetailKeyId == null || newDetailKeyId.trim().isEmpty()) {
                    logger.info("Failed To Generate the Detail Key Id", newDetailKeyId);
                    throw new RuntimeException("Failed to generate Create Master Key ID");
                }

                logger.info("Generated new Key ID: {} Detail Keyid", newDetailKeyId);
                dtl.setKeyid(newDetailKeyId);
                dtl.setKnwm_keyid(saveMst.getKeyid());
                QtmTlKnowwhydtl value = dtlRepository.save(dtl);
                resultDto.setQtmTlKnowwhydtl(value);
            } else {
                dtl.setKnwm_keyid(saveMst.getKeyid());
                QtmTlKnowwhydtl updateDetail = dtlRepository.save(dtl);
                resultDto.setQtmTlKnowwhydtl(updateDetail);
            }

        }

        return resultDto;

    }

    @Override
    public QtmTlKnowwhymst getKnowWhy(String keyid) throws Exception {
        if (!ValidationUtil.isValidKeyId(keyid)) {
            throw new RuntimeException("No valid key Id");
        }

        QtmTlKnowwhymst result = mstRepository.findById(keyid).orElse(null);
        return result;

    }

    @Override
    public String saveKnowWhyApproval(String keyid) {
        if (!ValidationUtil.isValidKeyId(keyid)) {
            throw new RuntimeException("Invalid KeyID");
        }
        int result = mstRepository.insertApprovalHistory(keyid);
        String rowsInsertedStr = String.valueOf(result);
        return rowsInsertedStr;
    }

    @Override
    @Transactional
    public QtmTlKnowwhymst DeleteKnowWhy(QtmTlKnowwhymst qtmTlKnowwhymst) {

        if (qtmTlKnowwhymst == null || !ValidationUtil.isValidKeyId(qtmTlKnowwhymst.getKeyid())) {
            throw new RuntimeException("Invalid Key Id");

        }
        String keyId = qtmTlKnowwhymst.getKeyid();
        dtlRepository.deleteByMasterKeyId(keyId);
        mstRepository.deleteById(keyId);

        boolean stillExists = mstRepository.existsById(keyId);
        if (stillExists) {
            throw new RuntimeException("Failed to delete KnowWhy MST for keyId: " + keyId);
        }

        logger.info("KnowWhy deleted successfully. MST={}, DTL={}", 1, keyId);

        return qtmTlKnowwhymst; // returning deleted object (snapshot)

    }

    @Override
    public QtmTlKnowwhymst saveKnowWhyMst(QtmTlKnowwhymst mst) throws Exception {
        QtmTlKnowwhymst result = new QtmTlKnowwhymst();

        if (!ValidationUtil.isValidKeyId(mst.getKeyid())) {
            String elementId = mst.getElementid();

            String location = null;
            String seqIdentfr = "QTM_TL_KNOWWHYMST";

            if (elementId != null && elementId.length() > 10) {
                location = elementId.substring(11, 21); /* location id starts from 11 */
                seqIdentfr += location;
            }

            String newMstKeyid = dbActionTemplate.getSequenceNumber(
                    seqIdentfr, 10, "KNW",
                    "", "");

            if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                logger.info("Failed To Generate the Key Id", newMstKeyid);
                throw new RuntimeException("Failed to generate Master Key ID");
            }

            logger.info("Generated new Key ID: {} Master Keyid", newMstKeyid);
            mst.setKeyid(newMstKeyid);

            result = mstRepository.save(mst);

        } else {
            if (mstRepository.existsById(mst.getKeyid())) {

                result = mstRepository.save(mst);
            }
        }
        return result;
    }

}
