package com.akranta.perfex_sb.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.ProjectPriorityDto;
import com.akranta.perfex_sb.model.KznTlKkprojectprioritydtl;
import com.akranta.perfex_sb.model.KznTlKkprojectprioritymst;
import com.akranta.perfex_sb.repository.KznTlKkprojectprioritydtlRepository;
import com.akranta.perfex_sb.repository.KznTlKkprojectprioritymstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.FIProjectPriorityService;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.transaction.Transactional;

@Service
public class FIProjectPriorityServiceImpl implements FIProjectPriorityService {

private static final Logger logger = LoggerFactory.getLogger(FIProjectPriorityServiceImpl.class);

    @Autowired
     private DbActionTemplate dbActionTemplate;

     @Autowired
    private KznTlKkprojectprioritymstRepository mstRepository;

    @Autowired
    private KznTlKkprojectprioritydtlRepository dtlRepository;

    @Transactional
    public ResponseEntity<ProjectPriorityDto> save(ProjectPriorityDto projectPriorityDto) {
        try {

            List<KznTlKkprojectprioritymst> mstList = projectPriorityDto.getMstList();
            List<KznTlKkprojectprioritydtl> dtlList = projectPriorityDto.getDtlList();
            
             List<KznTlKkprojectprioritymst> newMstList = new ArrayList<>();
            List<KznTlKkprojectprioritydtl> newDtlList = new ArrayList<>();

            int parameterCount = mstRepository.getParmeterCount();
            logger.info("Project Priority Parameter count :{} ", parameterCount);

            int i = 0;
            for(KznTlKkprojectprioritymst  mst : mstList ){

            if (!ValidationUtil.isValidKeyId(mst.getKeyid()) ) {
                
                String newKeyId = dbActionTemplate.getSequenceNumber("KZN_TL_KKPROJECTPRIORITYMST",15,"KKM","YYMMDD","Y");
                mst.setKeyid(newKeyId);
                logger.info("Generated new Key ID: {} for Project Priority Mst", newKeyId);
                KznTlKkprojectprioritymst savedEntity = mstRepository.save(mst);
                //  projectCreationDto.setProjectCreation(savedEntity);
                logger.info("Successfully created Project Priority Mst with Key ID: {}", savedEntity.getKeyid());

            } else {
                // Validate if the provided keyId already exists
                int updateCount = mstRepository.updateProjectPriorityMst(mst.getProjectscore(), mst.getApprovedby(), mst.getRank(), mst.getKeyid());
                logger.info("Successfully Updated Project Priority Mst with Key ID: {} , count :{} ", mst.getKeyid(),updateCount);
            }
            newMstList.add(mst);


            for(int j = 0 ; j < parameterCount; j++ ){
                KznTlKkprojectprioritydtl dtl = dtlList.get(i);
                dtl.setKppm_keyid(mst.getKeyid());
                dtl.setCreatedby(mst.getCreatedby());
                String detailScore = dtlRepository.getDetailScore(dtl.getKppm_keyid(),dtl.getKkpm_keyid());
                if(ValidationUtil.isValidKeyId(dtl.getKppm_keyid()) && ValidationUtil.isValidKeyId(dtl.getKkpm_keyid()) && ValidationUtil.isValidKeyId(detailScore) ){
                    int updateCount = dtlRepository.UpdateDetailScore(dtl.getScore(), dtl.getKppm_keyid(), dtl.getKkpm_keyid());
                    logger.info("Successfully Updated Project Priority Dtl with Key ID: {} , count :{} ", dtl.getKeyid(),updateCount);
                }else{
                    String newDtlKeyId = dbActionTemplate.getSequenceNumber("KZN_TL_KKPROJECTPRIORITYDTL",15,"KKD","YYMMDD","Y");
                    dtl.setKeyid(newDtlKeyId);
                    KznTlKkprojectprioritydtl savedEntity = dtlRepository.save(dtl);
                    logger.info("Successfully created Project Priority Dtl with Key ID: {}", savedEntity.getKeyid());
                }
                i++;
                newDtlList.add(dtl);
            }


            }

            projectPriorityDto.setMstList(newMstList);
            projectPriorityDto.setDtlList(newDtlList);
            

            return ResponseEntity.status(HttpStatus.OK).body(projectPriorityDto);

        } catch (Exception e) {
            logger.error("Error creating FIP Priority  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
    
}
