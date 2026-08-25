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
import com.akranta.perfex_sb.dto.EmployeeDto;
import com.akranta.perfex_sb.dto.RecallEmployeeDto;
import com.akranta.perfex_sb.model.GenTlEmployeedtl;
import com.akranta.perfex_sb.model.GenTlEmployeemst;
import com.akranta.perfex_sb.model.KznTlBestdtl;
import com.akranta.perfex_sb.model.KznTlBestmst;
import com.akranta.perfex_sb.repository.GenTlEmployeeMstRepository;
import com.akranta.perfex_sb.repository.GenTlEmployeedtlRepository;
import com.akranta.perfex_sb.repository.GenTlMomGroupdtlRepository;
import com.akranta.perfex_sb.repository.GenTlMomGroupmstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.EmployeeService;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private GenTlEmployeeMstRepository mstRepository;

    @Autowired
    private GenTlEmployeedtlRepository dtlRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MoMeetingServiceImpl.class);

    private static final String SEQ_IDENTIFIER = "GEN_TL_EMPLOYEEMST";
    private static final int KEY_LENGTH = 8;
    private static final String PREFIX = "BCM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_DTL = "GEN_TL_EMPLOYEEDTL";
    private static final String PREFIX_DTL = "BCM";

    @Override
    @Transactional
    public EmployeeDto saveEmployee(EmployeeDto dto) throws Exception {

        GenTlEmployeemst mst = dto.getMaster();
        List<GenTlEmployeedtl> dtl = dto.getDetails();

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
            EmployeeDto updateDto = new EmployeeDto();

            if (mstRepository.existsById(mst.getKeyid())) {

                GenTlEmployeemst updateMst = mstRepository.save(mst);

                updateDto.setMaster(updateMst);

                logger.info("Successfully updated Mom with Key ID: {}", updateMst.getKeyid());

                List<GenTlEmployeedtl> resultDetail = new ArrayList<>();

                for (GenTlEmployeedtl detail : dtl) {
                    if (detail.getKeyid() == null || detail.getKeyid().trim().isEmpty()) {

                        detail.setKeyid(updateMst.getKeyid());

                    } else {
                        if (detail.getKeyid().equals(mst.getKeyid())) {
                            GenTlEmployeedtl updateDetailResult = dtlRepository.save(detail);
                            resultDetail.add(updateDetailResult);

                        }
                        // detailValue.setEmpd_keyid(createEntity.getKeyid());
                        // GenTlEmployeedtl updateDetailResult = dtlRepository.save(detailValue);
                        // createDetail.add(updateDetailResult);
                    }

                    GenTlEmployeedtl detailResult = dtlRepository.save(detail);
                    resultDetail.add(detailResult);

                }

                updateDto.setDetails(resultDetail);
            }
            return updateDto;
        }

        EmployeeDto createDto = new EmployeeDto();
        List<GenTlEmployeedtl> createDetail = new ArrayList<>();
        GenTlEmployeemst createEntity = mstRepository.save(mst);
        createDto.setMaster(createEntity);
        if (dtl != null && !dtl.isEmpty()) {
            for (GenTlEmployeedtl detailValue : dtl) {
                if (detailValue.getKeyid() == null || detailValue.getKeyid().trim().isEmpty()) {

                    detailValue.setKeyid(createEntity.getKeyid());

                } else {
                    if (detailValue.getKeyid().equals(mst.getKeyid())) {
                        GenTlEmployeedtl updateDetailResult = dtlRepository.save(detailValue);
                        createDetail.add(updateDetailResult);

                    }
                    // detailValue.setEmpd_keyid(createEntity.getKeyid());
                    // GenTlEmployeedtl updateDetailResult = dtlRepository.save(detailValue);
                    // createDetail.add(updateDetailResult);
                }

                GenTlEmployeedtl detailResult = dtlRepository.save(detailValue);
                createDetail.add(detailResult);

            }
        }
        createDto.setDetails(createDetail);
        return createDto;
    }

    @Override
    public RecallEmployeeDto findMstDtlById(String keyId) throws Exception {
        if (!ValidationUtil.isValidKeyId(keyId)) {
            throw new RuntimeException("Invalid Key ID");

        }

        GenTlEmployeemst mst = mstRepository.findById(keyId).orElse(null);
        GenTlEmployeedtl dtl = dtlRepository.findById(keyId).orElse(null);

        RecallEmployeeDto resultDto = new RecallEmployeeDto();
        resultDto.setMaster(mst);
        resultDto.setDetail(dtl);

        return resultDto;

    }
}
