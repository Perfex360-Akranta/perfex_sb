package com.akranta.perfex_sb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.BestKaizenRecallDto;
import com.akranta.perfex_sb.dto.BestKaizenmsdtlDto;

import com.akranta.perfex_sb.model.KznTlBestdtl;
import com.akranta.perfex_sb.model.KznTlBestmst;

import com.akranta.perfex_sb.repository.KznTlBestdtlRepository;
import com.akranta.perfex_sb.repository.KznTlBestmstRepository;

import com.akranta.perfex_sb.service.BestKaizenService;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.transaction.Transactional;

@Service
public class BestKaizenServiceImpl implements BestKaizenService {

    @Autowired
    private KznTlBestmstRepository mstRepository;

    @Autowired
    private KznTlBestdtlRepository detailRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MoMeetingServiceImpl.class);
    // kzn_tl_bestdtl
    private static final String SEQ_IDENTIFIER = "KZN_TL_BESTMST";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "KZBM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_DTL = "KZN_TL_BESTDTL";
    private static final String PREFIX_DTL = "KZBD";

    @Override
    @Transactional
    public BestKaizenmsdtlDto saveBestKaizen(BestKaizenmsdtlDto dto) throws Exception {

        KznTlBestmst mst = dto.getKznTlBestmst();
        List<KznTlBestdtl> dtls = dto.getBestdtls();
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
            BestKaizenmsdtlDto updateDto = new BestKaizenmsdtlDto();
            if (mstRepository.existsById(mst.getKeyid())) {

                KznTlBestmst updateMst = mstRepository.save(mst);

                updateDto.setKznTlBestmst(updateMst);

                logger.info("Successfully updated Mom with Key ID: {}", updateMst.getKeyid());
                
                List<String> keyIds = dto.getKeyIds();

                if (keyIds != null && !keyIds.isEmpty()) 
                {
                    detailRepository.deleteAllById(keyIds);
                }

                List<KznTlBestdtl> resultDetail = new ArrayList<>();

                for (KznTlBestdtl detail : dtls) 
                {
                    logger.info("Generated new Key ID: {} Detail Keyid", detail.getKeyid());
                    if (detail.getKeyid() == null || detail.getKeyid().trim().isEmpty()) 
                    {
                        detail.setKzbm_keyid(mst.getKeyid());
                        String newDetailKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                                PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                        if (newDetailKeyid == null || newDetailKeyid.trim().isEmpty()) 
                        {
                            logger.info("Failed To Generate the Detail Key Id", newDetailKeyid);
                            throw new RuntimeException("Failed to generate Master Key ID");
                        }

                        logger.info("Generated new Key ID: {} Detail Keyid", newDetailKeyid);
                        detail.setKeyid(newDetailKeyid);
                        KznTlBestdtl value = detailRepository.save(detail);
                        resultDetail.add(value);

                    } 
                    else 
                    {
                        // Detail Table Update
                        KznTlBestdtl detailValue = detailRepository.save(detail);
                        resultDetail.add(detailValue);

                    }

                }
                updateDto.setBestdtls(resultDetail);
            }
            return updateDto;

        }

        BestKaizenmsdtlDto createDto = new BestKaizenmsdtlDto();
        List<KznTlBestdtl> createDetail = new ArrayList<>();
        KznTlBestmst createEntity = mstRepository.save(mst);
        createDto.setKznTlBestmst(createEntity);
        if (dtls != null && !dtls.isEmpty()) {
            for (KznTlBestdtl detailValue : dtls) {
                if (detailValue.getKeyid() == null || detailValue.getKeyid().trim().isEmpty()) {
                    String newDetail = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                            PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);

                    if (newDetail == null || newDetail.trim().isEmpty()) {
                        logger.info("Failed To Generate the Detail Key Id", newDetail);
                        throw new RuntimeException("Failed to generate Detail Key ID");
                    }
                    logger.info("Generated new Key ID: {} Detail Keyid", newDetail);
                    detailValue.setKeyid(newDetail);
                    detailValue.setKzbm_keyid(createEntity.getKeyid());
                } else {
                    detailValue.setKzbm_keyid(createEntity.getKeyid());
                    KznTlBestdtl updateDetailResult = detailRepository.save(detailValue);
                    createDetail.add(updateDetailResult);
                }

                KznTlBestdtl detailResult = detailRepository.save(detailValue);
                createDetail.add(detailResult);

            }
        }
        createDto.setBestdtls(createDetail);
        return createDto;
    }

    @Override
    public List<Map<String, Object>> selectData(BestKaizenRecallDto dto) {
        String flid = dto.getFlid();
        String fromMonth = dto.getFromMonth();
        String kznBankType = dto.getKznBankType();

        List<Map<String, Object>> result = mstRepository.selectData(flid, fromMonth, kznBankType);
        return result;
    }

    public KznTlBestmst getById(String keyId) throws Exception {
        if (!ValidationUtil.isValidKeyId(keyId)) {
            throw new Exception("Invalid KeyId");
        }
        KznTlBestmst mst = mstRepository.findById(keyId).orElse(null);
        return mst;

    }

    @Override
    public void deleteBestKaizen(String keyId) throws Exception {
        if (!ValidationUtil.isValidKeyId(keyId)) {
            throw new Exception("Invalid KeyId");
        }
        detailRepository.deleteByKzbm_keyid(keyId);
        mstRepository.deleteById(keyId);

    }

}
