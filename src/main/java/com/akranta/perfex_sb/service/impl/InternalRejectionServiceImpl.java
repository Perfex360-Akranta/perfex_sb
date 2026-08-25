package com.akranta.perfex_sb.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.IntRejEntryDto;
import com.akranta.perfex_sb.dto.internalRejectionMstDtlDto;
import com.akranta.perfex_sb.model.QtmTlIntrejectiondtl;
import com.akranta.perfex_sb.model.QtmTlIntrejectionmst;
import com.akranta.perfex_sb.repository.QtmTlIntrejectiondtlRepository;
import com.akranta.perfex_sb.repository.QtmTlIntrejectionmstlRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.InternalRejectionService;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.transaction.Transactional;

@Service
public class InternalRejectionServiceImpl implements InternalRejectionService

{
    @Autowired
    private QtmTlIntrejectionmstlRepository masterRepository;

    @Autowired
    private QtmTlIntrejectiondtlRepository detailRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final Logger logger = LoggerFactory.getLogger(InternalRejectionServiceImpl.class);

    private static final String SEQ_IDENTIFIER_MST = "QTM_TL_INTREJECTIONMST";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX_MST = "QTM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_DTL = "QTM_TL_INTREJECTIONDTL";
    private static final String PREFIX_DTL = "QTD";

    @Override
    @Transactional
    public QtmTlIntrejectiondtl saveInternalRejection(internalRejectionMstDtlDto dto) throws Exception {

        QtmTlIntrejectionmst qtmTlIntrejectionmst = dto.getQtmTlIntrejectionmst();
        QtmTlIntrejectiondtl qtmTlIntrejectiondtl = dto.getQtmTlIntrejectiondtl();
        IntRejEntryDto entryDto = dto.getIntRejEntryDto();
        String existMstKeyId = null;
        String existDtlKeyId = null;
        if (entryDto != null) {
            existMstKeyId = entryDto.getQirmKeyid();
            existDtlKeyId = entryDto.getQirdKeyid();
        }

        if (!ValidationUtil.isValidKeyId(existMstKeyId)) {
            logger.info("INSERTING THE MASTER TABLE DATA");
            String newMstKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_MST, KEY_LENGTH, PREFIX_MST,
                    FORMAT_RESET, DATE_FORMAT);

            if (newMstKeyId == null || newMstKeyId.trim().isEmpty()) {
                logger.info("Failed To Generate the Key Id", newMstKeyId);
                throw new RuntimeException("Failed to generate Master Key ID");
            }
            logger.info("Generated new MASTER Key ID: {} ", newMstKeyId);
            qtmTlIntrejectionmst.setKeyid(newMstKeyId);
            qtmTlIntrejectionmst.setParentmasterid(newMstKeyId);
            qtmTlIntrejectionmst.setLinkmasterid(newMstKeyId);
            qtmTlIntrejectionmst.setReferencekeyid(newMstKeyId);

            masterRepository.save(qtmTlIntrejectionmst);
        } else {
            String parentId = masterRepository.findParentMasterIdByKeyId(existMstKeyId);
            String linkId = masterRepository.findLinkMasterIdByKeyId(existMstKeyId);
            qtmTlIntrejectionmst.setKeyid(existMstKeyId);

            if (!ValidationUtil.isValidKeyId(parentId)) {
                qtmTlIntrejectionmst.setParentmasterid(qtmTlIntrejectionmst.getKeyid());
            } else {
                qtmTlIntrejectionmst.setParentmasterid(parentId);
            }
            if (!ValidationUtil.isValidKeyId(linkId)) {
                qtmTlIntrejectionmst.setLinkmasterid(qtmTlIntrejectionmst.getKeyid());
            } else {
                qtmTlIntrejectionmst.setLinkmasterid(linkId);
            }
            qtmTlIntrejectionmst.setLinkmasterid(qtmTlIntrejectionmst.getKeyid());
            qtmTlIntrejectionmst.setReferencekeyid(qtmTlIntrejectionmst.getKeyid());

            masterRepository.save(qtmTlIntrejectionmst);
        }

        if (ValidationUtil.isValidKeyId(qtmTlIntrejectionmst.getMachineid())) {
            qtmTlIntrejectiondtl.setTempfield5(qtmTlIntrejectionmst.getMachineid());
        }

        qtmTlIntrejectiondtl.setMasterid(qtmTlIntrejectionmst.getKeyid());
        qtmTlIntrejectiondtl.setReferenceid(qtmTlIntrejectionmst.getKeyid());
        QtmTlIntrejectiondtl resultDetail = new QtmTlIntrejectiondtl();
        if (!ValidationUtil.isValidKeyId(existDtlKeyId)) {
            String newDtlKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH, PREFIX_DTL,
                    FORMAT_RESET, DATE_FORMAT);

            if (newDtlKeyId == null || newDtlKeyId.trim().isEmpty()) {
                logger.info("Failed To Generate the Key Id", newDtlKeyId);
                throw new RuntimeException("Failed to generate Master Key ID");
            }
            qtmTlIntrejectiondtl.setKeyid(newDtlKeyId);
            logger.info("Generated new DETAIL Key ID: {} ", newDtlKeyId);
            resultDetail = detailRepository.save(qtmTlIntrejectiondtl);

        } else {
            qtmTlIntrejectiondtl.setKeyid(existDtlKeyId);
            resultDetail = detailRepository.save(qtmTlIntrejectiondtl);
        }

        return resultDetail;

    }

    public List<Map<String, Object>> getInternalRejectionModificationGrid(String flid) {
        List<Map<String, Object>> result = masterRepository.getInternalRejectionMstGrid(flid);
        if (result == null || result.isEmpty()) {
            throw new RuntimeException("Result is empty");
        }
        return result;
    }

    @Override
    public QtmTlIntrejectionmst getInternalRejectionMasterData(String id) throws Exception {
        if (!ValidationUtil.isValidKeyId(id)) {
            throw new Exception("No Id Value");

        }
        return masterRepository.findById(id).orElse(null);
    }

}
