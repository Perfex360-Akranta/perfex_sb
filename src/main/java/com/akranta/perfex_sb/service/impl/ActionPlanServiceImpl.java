package com.akranta.perfex_sb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.ActionPlanCompDto;
import com.akranta.perfex_sb.dto.ActionPlanSaveDto;
import com.akranta.perfex_sb.model.GenTlActionPlanDtl;
import com.akranta.perfex_sb.model.GenTlActionPlanMst;
import com.akranta.perfex_sb.repository.GenTlActionplandtlRepository;
import com.akranta.perfex_sb.repository.GenTlActionplanmstRepository;
import com.akranta.perfex_sb.service.ActionPlanService;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.transaction.Transactional;

@Service
public class ActionPlanServiceImpl implements ActionPlanService {
  
    
    private static final Logger logger = LoggerFactory.getLogger(ActionPlanServiceImpl.class);

    @Autowired
     private DbActionTemplate dbActionTemplate;

     @Autowired
    private GenTlActionplanmstRepository mstRepository;

    @Autowired
    private GenTlActionplandtlRepository dtlRepository;

    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "APB";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";


    @Transactional
    public ResponseEntity<ActionPlanSaveDto> save(ActionPlanSaveDto actionPlanSaveDto) {
        try {

            GenTlActionPlanMst actionPlanMst = actionPlanSaveDto.getActionPlanMst();
            String elementId = actionPlanMst.getElementid(); 
            String location = null;
	 	    String seqIdentfr = "GEN_TL_ACTIONPLANMST";

            List<GenTlActionPlanDtl> actionPlanDtls = new ArrayList<>(); 


	 	if( elementId != null && elementId.length() > 10  ){
	 		location = elementId.substring(11, 21); /* location id starts from 11  */
	 		seqIdentfr += location;
	 	}

            // Check if keyId is null or empty
            if (!ValidationUtil.isValidKeyId(actionPlanMst.getKeyid()) ) {
                
                String newKeyId = dbActionTemplate.getSequenceNumber(seqIdentfr,KEY_LENGTH,PREFIX,DATE_FORMAT,FORMAT_RESET);
                actionPlanMst.setKeyid(newKeyId);
                logger.info("Generated new Key ID: {} for Action plan Mst", newKeyId);
                GenTlActionPlanMst savedEntity = mstRepository.save(actionPlanMst);
                 actionPlanSaveDto.setActionPlanMst(savedEntity);
                logger.info("Successfully created Action Plan Mst with Key ID: {}", savedEntity.getKeyid());

            } else {
                // Validate if the provided keyId already exists
                if (mstRepository.existsById(actionPlanMst.getKeyid())) {
                    GenTlActionPlanMst updateEntity = mstRepository.save(actionPlanMst);
                    logger.info("Successfully updated abnormality with Key ID: {}", updateEntity.getKeyid());

                   // return ResponseEntity.status(HttpStatus.OK).body(actionPlanSaveDto);
                }else{
                     new RuntimeException("Action Plan Mst not found: " + actionPlanMst.getKeyid());
                }
            }

            for (GenTlActionPlanDtl actionPlanDtl : actionPlanSaveDto.getActionPlanDtls() ){
                  actionPlanDtl.setAplm_keyid(actionPlanMst.getKeyid());
                 if (!ValidationUtil.isValidKeyId(actionPlanDtl.getKeyid()) ) {
                    String newKeyId = dbActionTemplate.getSequenceNumber("GEN_TL_ACTIONPLANDTL",KEY_LENGTH,"APLD","","");
                    actionPlanDtl.setKeyid(newKeyId);
                    logger.info("Generated new Key ID: {} for Action plan Detail ", newKeyId);
                    GenTlActionPlanDtl savedEntity = dtlRepository.save(actionPlanDtl);
                    actionPlanDtls.add(savedEntity);
                    logger.info("Successfully created Action Plan Detail with Key ID: {}", savedEntity.getKeyid());
                 }else {
                // Validate if the provided keyId already exists
                if (dtlRepository.existsById(actionPlanDtl.getKeyid())) {
                    GenTlActionPlanDtl updateEntity = dtlRepository.save(actionPlanDtl);
                    actionPlanDtls.add(updateEntity);
                    logger.info("Successfully updated abnormality with Key ID: {}", updateEntity.getKeyid());

                   // return ResponseEntity.status(HttpStatus.OK).body(actionPlanSaveDto);
                }else{
                     new RuntimeException("Action Plan Detail not found: " + actionPlanDtl.getKeyid());
                }
            }


            }
            actionPlanSaveDto.setActionPlanDtls(actionPlanDtls);

            int mstcount =  mstRepository.UpdateActionPlanMSt(actionPlanMst.getKeyid());
            logger.info("Action Plan mst updated with Key ID: {} , count : {}", actionPlanMst.getKeyid(),mstcount);
            if(actionPlanMst.getRefdoctype().equals("JHA")){
                int JHcount =   mstRepository.UpdateJHAuditDTl(actionPlanMst.getKeyid(),actionPlanMst.getRemarks(),actionPlanMst.getDetailrefid());
                logger.info("Action Plan JHAudit with Key ID: {},count : {}", actionPlanMst.getKeyid(),JHcount);
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(actionPlanSaveDto);

        } catch (Exception e) {
            logger.error("Error creating Action Plan : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }


    @Transactional
    public void saveCompletion(List<ActionPlanCompDto> actionPlanCompDto) {
        try {

            // List<GenTlActionPlanDtl> actionPlanDtls = new ArrayList<>(); 


            for (ActionPlanCompDto actionPlanDtl : actionPlanCompDto ){
                 // actionPlanDtl.setAplm_keyid(actionPlanMst.getKeyid());
                  GenTlActionPlanDtl exitActionPlanDtl = dtlRepository.findById(actionPlanDtl.getKeyid()).orElseThrow(() ->
                    new RuntimeException("Action Plan Dtl not found: " + actionPlanDtl.getKeyid()));;

                    exitActionPlanDtl.setCompleatedon(actionPlanDtl.getCompleatedon());
                    exitActionPlanDtl.setCompletedby(actionPlanDtl.getCompletedby());
                    exitActionPlanDtl.setResponsibility(actionPlanDtl.getResponsibility());
                    exitActionPlanDtl.setCountermeasure(actionPlanDtl.getCountermeasure());
                    exitActionPlanDtl.setRemarks(actionPlanDtl.getRemarks());
                    exitActionPlanDtl.setTargetdate(actionPlanDtl.getTargetdate());
                    exitActionPlanDtl.setStatus(actionPlanDtl.getStatus());

                    GenTlActionPlanDtl updateActionPlanDtl = dtlRepository.save(exitActionPlanDtl);

                    if(updateActionPlanDtl.getStatus().equals('C')){
                      int mstcount =  mstRepository.UpdateActionPlanMSt(updateActionPlanDtl.getAplm_keyid());
                      int JHcount =   mstRepository.UpdateJHAuditDTl(updateActionPlanDtl.getAplm_keyid());
                      int MocStatus = mstRepository.UpdateMOCRecommendation(updateActionPlanDtl.getAplm_keyid());
                      int MocPssrStatus = mstRepository.UpdateMOCPSSRecommendation(updateActionPlanDtl.getAplm_keyid());
                      logger.info("Action Plan mst updated with Key ID: {} , count : {}", updateActionPlanDtl.getAplm_keyid(),mstcount);
                      logger.info("Action Plan JHAudit with Key ID: {},count : {}", updateActionPlanDtl.getAplm_keyid(),JHcount);
                      logger.info("Action Plan MOC with Key ID: {},count : {}", updateActionPlanDtl.getAplm_keyid(),MocStatus);
                       logger.info("PSSR Plan JHAudit with Key ID: {},count : {}", updateActionPlanDtl.getAplm_keyid(),MocPssrStatus);
                    }

            }

        } catch (Exception e) {
            logger.error("Error Updating Action Plan : {}", e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteActionplan(String keyid , String masterKeyid) {
        try {
            int dtlcount =   dtlRepository.DeleteActionPlanDtl(keyid);
            int mstcount =  mstRepository.DeleteActionPlanMSt(masterKeyid);
            logger.info("Action Plan dtl deleted with Key ID: {} , count : {}",keyid ,dtlcount);
            logger.info("Action Plan mst deleted with Key ID: {} , count : {}",masterKeyid ,mstcount);    
        } catch (Exception e) {
            logger.error("Error Deleting Action Plan : {}", e.getMessage(), e);
        }
    }


}
