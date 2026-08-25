package com.akranta.perfex_sb.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.ActionPlanSaveDto;
import com.akranta.perfex_sb.dto.ProjectResponseDto;
import com.akranta.perfex_sb.dto.WorkFlowApprovalSaveDto;
import com.akranta.perfex_sb.dto.WorkFlowSaveDto;
import com.akranta.perfex_sb.model.GenTlActionPlanDtl;
import com.akranta.perfex_sb.model.GenTlActionPlanMst;
import com.akranta.perfex_sb.model.GenTlWorkFlowDtl;
import com.akranta.perfex_sb.model.GenTlWorkFlowInfo;
import com.akranta.perfex_sb.model.GenTlWorkFlowMst;
import com.akranta.perfex_sb.repository.GenTlWorkFlowDtlRepository;
import com.akranta.perfex_sb.repository.GenTlWorkFlowInfoRepository;
import com.akranta.perfex_sb.repository.GenTlWorkFlowMstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.WorkFlowService;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.transaction.Transactional;

@Service
public class WorkFlowServiceImpl implements WorkFlowService {
    
    private static final Logger logger = LoggerFactory.getLogger(FIProjectServiceImpl.class);

    @Autowired
    private DbActionTemplate dbActionTemplate;

    @Autowired
    private GenTlWorkFlowInfoRepository workFlowRepository;

    @Autowired
    private GenTlWorkFlowMstRepository workFlowMstRepository;

    @Autowired
    private GenTlWorkFlowDtlRepository workFlowDtlRepository;



    @Transactional
    public ResponseEntity<String> saveApproval(WorkFlowApprovalSaveDto workFlowApprovalSaveDto) {
        try {

            GenTlWorkFlowInfo workFlowInfo = workFlowApprovalSaveDto.getWorkFlowInfo();
          logger.info(" Workflow id : {}, RefType : {} , Status : {} ", workFlowInfo.getKeyid(),workFlowInfo.getRef_type(),workFlowInfo.getStatus());
            if( ! ValidationUtil.isValidKeyId(workFlowInfo.getKeyid())){

                if(workFlowInfo.getStatus().equals('A') && workFlowApprovalSaveDto.getLastLevel().equals('Y')){

                    if(workFlowInfo.getRef_type().equals("PRODE")){
                        String menuLinkId = workFlowRepository.getMenuLinkKeyid("FIPRODEF");
                        String detailId = workFlowRepository.getDetailKeyid("FIPRODEF", workFlowInfo.getRole_id());
                        workFlowInfo.setWrml_keyid(menuLinkId);
                        workFlowInfo.setWrkd_keyid(detailId);
                        logger.info("   menuLinkId : {}, detailId : {} , TransactionCode : FIPRODEF ", menuLinkId,detailId);
                    }else if(workFlowInfo.getRef_type().equals("PRODEGE5L")){
                        String menuLinkId = workFlowRepository.getMenuLinkKeyid("FIPRODEFGE5L");
                        String detailId = workFlowRepository.getDetailKeyid("FIPRODEFGE5L", workFlowInfo.getRole_id());
                        workFlowInfo.setWrml_keyid(menuLinkId);
                        workFlowInfo.setWrkd_keyid(detailId);
                        logger.info("   menuLinkId : {}, detailId : {} , TransactionCode : FIPRODEFGE5L ", menuLinkId,detailId);
                    }else if(workFlowInfo.getRef_type().equals("PRODEGE1C")){   
                        String menuLinkId = workFlowRepository.getMenuLinkKeyid("FIPRODEFGE1C");
                        String detailId = workFlowRepository.getDetailKeyid("FIPRODEFGE1C", workFlowInfo.getRole_id());
                        workFlowInfo.setWrml_keyid(menuLinkId);
                        workFlowInfo.setWrkd_keyid(detailId);
                        logger.info("   menuLinkId : {}, detailId : {} , TransactionCode : FIPRODEFGE1C ", menuLinkId,detailId);
                    }else if(workFlowInfo.getRef_type().equals("PROCL")){   
                        String menuLinkId = workFlowRepository.getMenuLinkKeyid("FIPROCLO");
                        String detailId = workFlowRepository.getDetailKeyid("FIPROCLO", workFlowInfo.getRole_id());
                        workFlowInfo.setWrml_keyid(menuLinkId);
                        workFlowInfo.setWrkd_keyid(detailId);
                        logger.info("   menuLinkId : {}, detailId : {} , TransactionCode : FIPROCLO ", menuLinkId,detailId);
                    }else if(workFlowInfo.getRef_type().equals("PROCLGE1C")){   
                        String menuLinkId = workFlowRepository.getMenuLinkKeyid("FIPROCLOGE1C");
                        String detailId = workFlowRepository.getDetailKeyid("FIPROCLOGE1C", workFlowInfo.getRole_id());
                        workFlowInfo.setWrml_keyid(menuLinkId);
                        workFlowInfo.setWrkd_keyid(detailId);
                        logger.info("   menuLinkId : {}, detailId : {} , TransactionCode : FIPROCLOGE1C ", menuLinkId,detailId);
                    }else if(workFlowInfo.getRef_type().equals("PROCLGE5L")){   
                        String menuLinkId = workFlowRepository.getMenuLinkKeyid("FIPROCLOGE5L");
                        String detailId = workFlowRepository.getDetailKeyid("FIPROCLOGE5L", workFlowInfo.getRole_id());
                        workFlowInfo.setWrml_keyid(menuLinkId);
                        workFlowInfo.setWrkd_keyid(detailId);
                        logger.info("   menuLinkId : {}, detailId : {} , TransactionCode : FIPROCLOGE5L ", menuLinkId,detailId);
                    }

                    logger.info(" LAST Approval  menuLinkId : {}, detailId : {}  ", workFlowInfo.getWrml_keyid(),workFlowInfo.getWrkd_keyid());
                    String newKeyId = dbActionTemplate.getSequenceNumber("GEN_TL_WORKFLOW_INFO",10,"WF","","");
                    workFlowInfo.setKeyid(newKeyId);
                    logger.info("Generated new Key ID: {} for WorkFlow Info At LastLevel  ", newKeyId);
                    workFlowInfo = workFlowRepository.save(workFlowInfo);
                    logger.info("Successfully created WorkFlow Approval with Key ID: {}", workFlowInfo.getKeyid());
                }else{
                    logger.info(" Approval  menuLinkId : {}, detailId : {} ,  ", workFlowInfo.getWrml_keyid(),workFlowInfo.getWrkd_keyid());
                    String newKeyId = dbActionTemplate.getSequenceNumber("GEN_TL_WORKFLOW_INFO",10,"WF","","");
                    workFlowInfo.setKeyid(newKeyId);
                    logger.info("Generated new Key ID: {} for WorkFlow Info   ", newKeyId);
                    workFlowInfo = workFlowRepository.save(workFlowInfo);
                    logger.info("Successfully created WorkFlow Approval with Key ID: {}", workFlowInfo.getKeyid());
                }

                if(workFlowInfo.getStatus().equals('E')){
                    int updateCount = workFlowRepository.updateReworkStatus(workFlowInfo.getRef_id(), workFlowInfo.getRef_type());
                    int deleteCount = workFlowRepository.deleteAdmApproval(workFlowInfo.getRef_id());
                    logger.info("  Approval  Rework count : {}, delete Count : {}  ",updateCount,deleteCount);
                }else{
                    if( ValidationUtil.isValidKeyId(workFlowApprovalSaveDto.getNextEmpId())){
                        int updateCount = workFlowRepository.updateAdmApproval(workFlowApprovalSaveDto.getNextEmpId(),workFlowInfo.getRef_id());
                        logger.info(" Next  Approval  : {},  Count : {}  ",workFlowApprovalSaveDto.getNextEmpId(),updateCount);
                    }else{
                        int deleteCount = workFlowRepository.deleteAdmApproval(workFlowInfo.getRef_id());
                        logger.info("   Approval  delete Count : {}  ",deleteCount);
                    }
                }

            }else{
                    workFlowInfo = workFlowRepository.save(workFlowInfo);

                if(workFlowInfo.getStatus().equals('E')){
                    int updateCount = workFlowRepository.updateReworkStatus(workFlowInfo.getRef_id(), workFlowInfo.getRef_type());
                    int deleteCount = workFlowRepository.deleteAdmApproval(workFlowInfo.getRef_id());
                    logger.info(" Update  Approval  Rework count : {}, delete Count : {}  ",updateCount,deleteCount);
                }else{
                    if( ValidationUtil.isValidKeyId(workFlowApprovalSaveDto.getNextEmpId())){
                        int updateCount = workFlowRepository.updateAdmApproval(workFlowApprovalSaveDto.getNextEmpId(),workFlowInfo.getRef_id());
                        logger.info(" Update Next  Approval  : {},  Count : {}  ",workFlowApprovalSaveDto.getNextEmpId(),updateCount);
                    }else{
                        int deleteCount = workFlowRepository.deleteAdmApproval(workFlowInfo.getRef_id());
                        logger.info(" Update  Approval  delete Count : {}  ",deleteCount);
                    }
                }
            }


            if( ValidationUtil.isValidKeyId(workFlowInfo.getRef_type()) && workFlowInfo.getRef_type().length() > 2){
                String refType = workFlowInfo.getRef_type().substring(0, 3);
                logger.info(" REF TYPE : {}  ",refType);
                if(refType.equals("KZN") || refType.equals("OPL")){
                    updateOPLKaizen(workFlowApprovalSaveDto, refType);
                }
            }

            

            return ResponseEntity.status(HttpStatus.CREATED).body("Approved");

        } catch (Exception e) {
            logger.error("Error creating Approvals  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    public void updateOPLKaizen(WorkFlowApprovalSaveDto workFlowApprovalSaveDto , String refType){
        Character wfStatus= workFlowApprovalSaveDto.getWorkFlowInfo().getStatus(); 
		Character status = null;
        String nextRoleName = workFlowApprovalSaveDto.getNextRoleName();

        if( wfStatus.equals('A') || wfStatus.equals('E') || wfStatus.equals('R')){

            if(wfStatus.equals('A') && workFlowApprovalSaveDto.getLastLevel().equals('Y')){
                status = 'C';
            }else if(wfStatus.equals('A') ){
                status = 'A';
            }else if(wfStatus.equals('E') ){
                status = 'E';
                nextRoleName = "REWORK";
            }else if(wfStatus.equals('R') ){
                status = 'R';
            }
            if(!ValidationUtil.isValidKeyId(nextRoleName)){
                nextRoleName = "-";
            }

            logger.info("   refType : {}, nextRoleName : {} , status : {} ", refType,nextRoleName,status);
              int kznCount =0;
              int oplCount = 0;      
            if(refType.equals("KZN")){
                 kznCount = workFlowRepository.updateKZNStatus(status, nextRoleName, workFlowApprovalSaveDto.getWorkFlowInfo().getRef_id());
            }else if(refType.equals("OPL")){
                 oplCount = workFlowRepository.updateOPLStatus(status, nextRoleName, workFlowApprovalSaveDto.getWorkFlowInfo().getRef_id());
            }
            logger.info("   refType : {}, KZN COUNT  : {} , OPL COUNT : {} ", refType,kznCount,oplCount);
                    
        }
    }

    public GenTlWorkFlowMst getWorkFlowMst( String keyid) {
        return workFlowMstRepository.findById(keyid).orElseThrow(() ->
                    new RuntimeException("WorkFlow Mst not found: " + keyid));
    }


    @Transactional
    public ResponseEntity<WorkFlowSaveDto> saveWorkFlow(WorkFlowSaveDto workFlowSaveDto) {
        try {

            GenTlWorkFlowMst workFlowMst = workFlowSaveDto.getWorkFlowMst();
            

            List<GenTlWorkFlowDtl> workFlowDtls = new ArrayList<>(); 
           
            if (!ValidationUtil.isValidKeyId(workFlowMst.getKeyid()) ) {
                
                String newKeyId = dbActionTemplate.getSequenceNumber("GEN_TL_WORKFLOWMST",10,"QTM","","");
                workFlowMst.setKeyid(newKeyId);
                logger.info("Generated new Key ID: {} for WorkFlow Mst", newKeyId);
                GenTlWorkFlowMst savedEntity = workFlowMstRepository.save(workFlowMst);
                 workFlowSaveDto.setWorkFlowMst(savedEntity);
                logger.info("Successfully created WorkFlow  Mst with Key ID: {}", savedEntity.getKeyid());

            } else {
               
                if (workFlowMstRepository.existsById(workFlowMst.getKeyid())) {
                    GenTlWorkFlowMst updateEntity = workFlowMstRepository.save(workFlowMst);
                    logger.info("Successfully updated WorkFlow Mst with Key ID: {}", updateEntity.getKeyid());

                }else{
                     new RuntimeException("WorkFlow  Mst not found: " + workFlowMst.getKeyid());
                }
            }

            for (GenTlWorkFlowDtl workFlowDtl : workFlowSaveDto.getWorkFlowDtls() ){
                  workFlowDtl.setWrkm_keyid(workFlowMst.getKeyid());
                 if (!ValidationUtil.isValidKeyId(workFlowDtl.getKeyid()) ) {
                    String newKeyId = dbActionTemplate.getSequenceNumber("GEN_TL_WORKFLOWDTL",10,"WFD","","");
                    workFlowDtl.setKeyid(newKeyId);
                    logger.info("Generated new Key ID: {} for WorkFlow Detail ", newKeyId);
                    GenTlWorkFlowDtl savedEntity = workFlowDtlRepository.save(workFlowDtl);
                    workFlowDtls.add(savedEntity);
                    logger.info("Successfully created WorkFlow Detail with Key ID: {}", savedEntity.getKeyid());
                 }else {
                // Validate if the provided keyId already exists
                if (workFlowDtlRepository.existsById(workFlowDtl.getKeyid())) {
                    GenTlWorkFlowDtl updateEntity = workFlowDtlRepository.save(workFlowDtl);
                    workFlowDtls.add(updateEntity);
                    logger.info("Successfully updated WorkFlow Detail with Key ID: {}", updateEntity.getKeyid());

                   // return ResponseEntity.status(HttpStatus.OK).body(actionPlanSaveDto);
                }else{
                     new RuntimeException("WorkFlow Detail not found: " + workFlowDtl.getKeyid());
                }
            }


            }
            workFlowSaveDto.setWorkFlowDtls(workFlowDtls);

            return ResponseEntity.status(HttpStatus.CREATED).body(workFlowSaveDto);

        } catch (Exception e) {
            logger.error("Error creating WorkFlow : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }


    @Transactional
    public String deleteWorkFlowDtl( List<String> keyids) {
        int count = workFlowDtlRepository.deleteWorkFlowDtl(keyids);
        String msg = "Delete Count :"+ count;
        logger.info("Successfully deleted WorkFlow Detail count: {}", count);
        return msg;
    }

    @Transactional
    public String deleteAllWorkFlow( String keyid) {
        int dtlCount = workFlowDtlRepository.deleteAllWorkFlowDtl(keyid);
        int mstCount = workFlowMstRepository.deleteWorkFlowMst(keyid);
        String msg = "Deleted Detail Count :"+ dtlCount +" , Master Count :"+mstCount;
        logger.info("Successfully deleted WorkFlow  Detail count: {} , Master Count : {}", dtlCount,mstCount);
        return msg;
    }


}
