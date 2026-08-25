package com.akranta.perfex_sb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.EmployeeGroupDto;
import com.akranta.perfex_sb.model.GenTlMomGroupdtl;
import com.akranta.perfex_sb.model.GenTlMomGroupmst;

import com.akranta.perfex_sb.repository.GenTlMomGroupdtlRepository;
import com.akranta.perfex_sb.repository.GenTlMomGroupmstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.EmployeeGroupService;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.transaction.Transactional;

@Service
public class EmployeeGroupServiceImpl implements EmployeeGroupService 
{
    @Autowired
    private GenTlMomGroupmstRepository mstRepository;

    @Autowired
    private GenTlMomGroupdtlRepository dtlRepository;

    private static final Logger logger = LoggerFactory.getLogger(MoMeetingServiceImpl.class);
    // kzn_tl_bestdtl
    private static final String SEQ_IDENTIFIER = "GEN_TL_MOM_GROUPMST";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "MGRM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_DTL = "GEN_TL_MOM_GROUPDTL";
    private static final String PREFIX_DTL = "MGRD";

    
    @Autowired
    private DbActionTemplate dbActionTemplate;

    @Override
    @Transactional
    public EmployeeGroupDto saveEmployeeGroup(EmployeeGroupDto dto) throws Exception {
        
        GenTlMomGroupmst mst = dto.getMaster();
        List<GenTlMomGroupdtl> dtl = dto. getDetails();

        if(!ValidationUtil.isValidKeyId(mst.getKeyid())){

            String newMstKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX,
                    FORMAT_RESET, DATE_FORMAT);
            
            if (newMstKeyId == null || newMstKeyId.trim().isEmpty()) {
                logger.info("Failed To Generate the Key Id", newMstKeyId);
                throw new RuntimeException("Failed to generate Master Key ID");
            }

            logger.info("Generated new Key ID: {} Master Keyid", newMstKeyId);
            mst.setKeyid(newMstKeyId);
        } else {
            EmployeeGroupDto updateDto = new EmployeeGroupDto();
            
            if (mstRepository.existsById(mst.getKeyid())) {

                GenTlMomGroupmst updateMst = mstRepository.save(mst);

                updateDto.setMaster(updateMst);

                logger.info("Successfully updated Mom with Key ID: {}", updateMst.getKeyid());

                List<GenTlMomGroupdtl> resultDetail = new ArrayList<>();

                for (GenTlMomGroupdtl detail : dtl) {
                    logger.info("Generated new Key ID: {} Detail Keyid", detail.getKeyid());
                    if (detail.getKeyid() == null || detail.getKeyid().trim().isEmpty()) {
                        detail.setMgrm_keyid(mst.getKeyid());
                        String newDetailKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                                PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                        if (newDetailKeyid == null || newDetailKeyid.trim().isEmpty()) {
                            logger.info("Failed To Generate the Detail Key Id", newDetailKeyid);
                            throw new RuntimeException("Failed to generate Master Key ID");
                        }

                        logger.info("Generated new Key ID: {} Detail Keyid", newDetailKeyid);
                        detail.setKeyid(newDetailKeyid);
                        GenTlMomGroupdtl value = dtlRepository.save(detail);
                        resultDetail.add(value);

                    } else {
                        // Detail Table Update
                        GenTlMomGroupdtl detailValue = dtlRepository.save(detail);
                        resultDetail.add(detailValue);

                    }

                }
                updateDto.setDetails(resultDetail);
            }
            return updateDto;

        }

        EmployeeGroupDto createDto = new EmployeeGroupDto();
        List<GenTlMomGroupdtl> createDetail = new ArrayList<>();
        GenTlMomGroupmst createEntity = mstRepository.save(mst);
        createDto.setMaster(createEntity);
        if (dtl != null && !dtl.isEmpty()) {
            for (GenTlMomGroupdtl detailValue : dtl) {
                if (detailValue.getKeyid() == null || detailValue.getKeyid().trim().isEmpty()) {
                    String newDetail = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                            PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);

                    if (newDetail == null || newDetail.trim().isEmpty()) {
                        logger.info("Failed To Generate the Detail Key Id", newDetail);
                        throw new RuntimeException("Failed to generate Detail Key ID");
                    }
                    logger.info("Generated new Key ID: {} Detail Keyid", newDetail);
                    detailValue.setKeyid(newDetail);
                    detailValue.setMgrm_keyid(createEntity.getKeyid());
                } else {
                    detailValue.setMgrm_keyid(createEntity.getKeyid());
                    GenTlMomGroupdtl updateDetailResult = dtlRepository.save(detailValue);
                    createDetail.add(updateDetailResult);
                }

                GenTlMomGroupdtl detailResult = dtlRepository.save(detailValue);
                createDetail.add(detailResult);

            }
        }
        createDto.setDetails(createDetail);
        return createDto;
    }

    @Override
    public List<Map<String, Object>> getGrid() 
    {
        return mstRepository.getEmpgroupViewGridData();
    }
    @Override
    public List<Map<String,Object>> createGrid(String functional,String mstKeyId)
    {
        return mstRepository.getEmployees(functional, mstKeyId);
    }

    @Override
    public List<Map<String, Object>> detailGrid(String mgrmKeyId)
     {
        return mstRepository.getGroupEmployees(mgrmKeyId);
    }

    @Override
    public void deleteGrid(String mgrdKeyId)
     {
        int n =  mstRepository.deleteGroupDtl(mgrdKeyId);
        GenTlMomGroupmst gm = new GenTlMomGroupmst();
        
    } 
    @Override
    public GenTlMomGroupmst viewGrid(String mgrmKeyId)
     {
        GenTlMomGroupmst mst = mstRepository.findById(mgrmKeyId).orElse(null);
        return mst;

    }
    
       

   
}
