package com.akranta.perfex_sb.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.model.KznTlKaizenBankMst;
import com.akranta.perfex_sb.repository.KaizenBankMstRepository;
import com.akranta.perfex_sb.service.KaizenBankService;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.util.ValidationUtil;

import io.micrometer.common.lang.NonNull;
import jakarta.transaction.Transactional;

@Service
public class KaizenBankServiceImpl implements KaizenBankService {

    @Autowired
    private KaizenBankMstRepository kaizenBankRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KaizenBankServiceImpl.class);

    private static final String SEQ_IDENTIFIER = "KZN_TL_KAIZENBANKMST";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "KZBN";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    @Override
    public KznTlKaizenBankMst save(KznTlKaizenBankMst model) throws Exception {
        if (!ValidationUtil.isValidKeyId(model.getKeyid())) {
            String newKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX,
                    FORMAT_RESET, DATE_FORMAT);

            if (newKeyId == null || newKeyId.trim().isEmpty()) {
                logger.info("Failed To Generate the Key Id");
                throw new RuntimeException("Failed to generate Master Key ID");
            }

            logger.info("Generated new Key ID: {}", newKeyId);
            model.setKeyid(newKeyId);
        } else {
            if (kaizenBankRepository.existsById(model.getKeyid())) {
                return kaizenBankRepository.save(model);
            }
        }
        return kaizenBankRepository.save(model);
    }

    public KznTlKaizenBankMst findById(String id) {
        return kaizenBankRepository.findById(id).orElse(null);
    }

    @Override
    public String selectKznData(String keyId) {
        return kaizenBankRepository.selectKznData(keyId);
    }

   
    @Override
    @Transactional
    public String updateKaizenWorkflowStatus(
            String keyId,
            String status,
            String kaizen,
            String acrejby,
            BigDecimal implementCost,
            LocalDateTime targetDate,
            String mocRequired,
            String responsibility,
            String verifyRemarks,
            String mocitem
    ) {

        int updatedRows = kaizenBankRepository.updateKaizenNative(
                status,
                kaizen,
                acrejby,
                implementCost != null ? implementCost : BigDecimal.ZERO,
                targetDate,
                mocRequired,
                responsibility,
                verifyRemarks,
                mocitem,
                keyId
        );

        if (updatedRows == 0) {
            return "No record updated";
        }

        kaizenBankRepository.deleteApprovalByDocNo(keyId);

        return "SUCCESS";
    }

    @Override
    public List<KznTlKaizenBankMst> multipleSave(List<KznTlKaizenBankMst> mltSuggs) {

        String TABLEIDENTIFIER = "KZN_TL_KAIZENBANKMST";
        int KEY_LENGTH = 10;
        String PREFIX = "KZBN";
        String DATE_FORMAT = "YY";
        String FORMAT_RESET = "Y";

        for (KznTlKaizenBankMst multSgg : mltSuggs) {

            if (multSgg.getKeyid() == null || multSgg.getKeyid().isBlank()) {

                try {
                    String keyId = dbActionTemplate.getSequenceNumber(
                            TABLEIDENTIFIER,
                            KEY_LENGTH,
                            PREFIX,
                            DATE_FORMAT,
                            FORMAT_RESET
                    );
                    multSgg.setKeyid(keyId);
                } catch (Exception e) {
                    throw new RuntimeException("Key generation failed", e);
                }
            }
        }

        return kaizenBankRepository.saveAll(mltSuggs);
    }

    @Override
    public List<Map<String, Object>> findCategoryRecall(String keyid) throws Exception {
        return kaizenBankRepository.findCategoryRecall(keyid);
    }

    @Override
    public String deleteSuggestionById( String keyId) 
    {

         kaizenBankRepository.deleteById(keyId);
         return "Deleted";
        
    }
}


   

