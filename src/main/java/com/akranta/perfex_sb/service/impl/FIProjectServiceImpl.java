package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.akranta.perfex_sb.dto.ActionPlanSaveDto;
import com.akranta.perfex_sb.dto.ProjectCreationDto;
import com.akranta.perfex_sb.dto.ProjectKaizenSaveDto;
import com.akranta.perfex_sb.dto.ProjectKpiSaveDto;
import com.akranta.perfex_sb.dto.ProjectMilestoneSaveDto;
import com.akranta.perfex_sb.dto.ProjectResourceSaveDto;
import com.akranta.perfex_sb.dto.ProjectResponseDto;
import com.akranta.perfex_sb.dto.ProjectUpdationDto;
import com.akranta.perfex_sb.model.GenTlActionPlanDtl;
import com.akranta.perfex_sb.model.GenTlActionPlanMst;
import com.akranta.perfex_sb.model.GenTlWorkFlowInfo;
import com.akranta.perfex_sb.model.KkTlFipapprovalslist;
import com.akranta.perfex_sb.model.KznTlProjMilestoneDtl;
import com.akranta.perfex_sb.model.KznTlProjMilestoneMst;
import com.akranta.perfex_sb.model.KznTlProjectChecklistLink;
import com.akranta.perfex_sb.model.KznTlProjectKaizenLink;
import com.akranta.perfex_sb.model.KznTlProjectKpiLink;
import com.akranta.perfex_sb.model.KznTlProjectResourceLink;
import com.akranta.perfex_sb.model.KznTlProjectcreationmst;
import com.akranta.perfex_sb.repository.GenTlWorkFlowInfoRepository;
import com.akranta.perfex_sb.repository.KkTlFipapprovalslistRepository;
import com.akranta.perfex_sb.repository.KznTlProjMilestoneDtlRepository;
import com.akranta.perfex_sb.repository.KznTlProjMilestoneMstRepository;
import com.akranta.perfex_sb.repository.KznTlProjectChecklistLinkRepository;
import com.akranta.perfex_sb.repository.KznTlProjectKaizenLinkRepository;
import com.akranta.perfex_sb.repository.KznTlProjectKpiLinkRepository;
import com.akranta.perfex_sb.repository.KznTlProjectResourceLinkRepository;
import com.akranta.perfex_sb.repository.KznTlProjectcreationmstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.FIProjectService;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.transaction.Transactional;

@Service
public class FIProjectServiceImpl implements FIProjectService {
    private static final Logger logger = LoggerFactory.getLogger(FIProjectServiceImpl.class);

    @Autowired
     private DbActionTemplate dbActionTemplate;

     @Autowired
    private KznTlProjectcreationmstRepository projectRepository;

    @Autowired
    private KznTlProjectResourceLinkRepository resourceLinkRepository;

    @Autowired
    private GenTlWorkFlowInfoRepository workFlowRepository;

    @Autowired 
    private KznTlProjectChecklistLinkRepository checlistLinkRepository;

    @Autowired 
    private KznTlProjectKpiLinkRepository kpiLinkRepository;

    @Autowired
    private KkTlFipapprovalslistRepository fipapprovalslistRepository;

    @Autowired
    private KznTlProjectKaizenLinkRepository  kaizenLinkRepository;

    @Autowired
    private KznTlProjMilestoneMstRepository  milestoneMstRepository;

    @Autowired
    private KznTlProjMilestoneDtlRepository milestoneDtlRepository;


    private static final int KEY_LENGTH = 15;
    private static final String PREFIX = "FI";
    private static final String DATE_FORMAT = "YYMMDD";
    private static final String FORMAT_RESET = "Y";


    @Transactional
    public ResponseEntity<ProjectCreationDto> create(ProjectCreationDto projectCreationDto) {
        try {

            KznTlProjectcreationmst projectCreation = projectCreationDto.getProjectCreation();
            String elementId = projectCreationDto.getElementId(); 
            String location = null;
	 	    String seqIdentfr = "KZN_TL_PROJECTCREATIONMST";

            List<KznTlProjectResourceLink> resourceLinks = new ArrayList<>(); 


	 	if( elementId != null && elementId.length() > 10  ){
	 		location = elementId.substring(11, 21); /* location id starts from 11  */
	 		seqIdentfr += location;
	 	}

            // Check if keyId is null or empty
            if (!ValidationUtil.isValidKeyId(projectCreation.getKeyid()) ) {
                
                String newKeyId = dbActionTemplate.getSequenceNumber(seqIdentfr,KEY_LENGTH,PREFIX,DATE_FORMAT,FORMAT_RESET);
                String no = newKeyId.substring(9);
                projectCreation.setKeyid(newKeyId);
                projectCreation.setProjectno(no);
                logger.info("Generated new Key ID: {} for Project Creation Mst", newKeyId);
                KznTlProjectcreationmst savedEntity = projectRepository.save(projectCreation);
                 projectCreationDto.setProjectCreation(savedEntity);
                logger.info("Successfully created Project Creation Mst with Key ID: {}", savedEntity.getKeyid());

            } else {
                // Validate if the provided keyId already exists
                if (projectRepository.existsById(projectCreation.getKeyid())) {
                    KznTlProjectcreationmst updateEntity = projectRepository.save(projectCreation);
                    logger.info("Successfully updated abnormality with Key ID: {}", updateEntity.getKeyid());

                   // return ResponseEntity.status(HttpStatus.OK).body(actionPlanSaveDto);
                }else{
                     new RuntimeException("Project Creation Mst not found: " + projectCreation.getKeyid());
                }
            }

            for (KznTlProjectResourceLink resourceLink : projectCreationDto.getResourceLinkList() ){
                  resourceLink.setKzpm_keyid(projectCreation.getKeyid());
                 if (!ValidationUtil.isValidKeyId(resourceLink.getKeyid()) ) {
                    String newKeyId = dbActionTemplate.getSequenceNumber("KZN_TL_PROJECT_RESOURCE_LINK",KEY_LENGTH,"KPR","YYMMDD","Y");
                    resourceLink.setKeyid(newKeyId);
                    logger.info("Generated new Key ID: {} for Resource Link ", newKeyId);
                    KznTlProjectResourceLink savedEntity = resourceLinkRepository.save(resourceLink);
                    resourceLinks.add(savedEntity);
                    logger.info("Successfully created Resource Link  with Key ID: {}", resourceLink.getKeyid());
                 }else {
                //      new RuntimeException("Resource Link not found: " + actionPlanDtl.getKeyid());
                
            }


            }
            projectCreationDto.setResourceLinkList(resourceLinks);

            if (projectCreationDto.getWorkFlow()!=null){

                GenTlWorkFlowInfo workFlow = projectCreationDto.getWorkFlow();
                String roleId = projectRepository.getRoleKeyid("PROJECTLEAD");
                if(ValidationUtil.isValidKeyId(roleId)){
                    workFlow.setRole_id(roleId);
                }
                String newKeyId = dbActionTemplate.getSequenceNumber("GEN_TL_WORKFLOW_INFO",10,"WF",null,null);
                workFlow.setKeyid(newKeyId);
                workFlow.setRef_id(projectCreation.getKeyid());
                logger.info("Generated new Key ID: {} for WorkFlow Info  ", newKeyId);
                GenTlWorkFlowInfo savedEntity = workFlowRepository.save(workFlow);
                projectCreationDto.setWorkFlow(savedEntity);

            }

            return ResponseEntity.status(HttpStatus.CREATED).body(projectCreationDto);

        } catch (Exception e) {
            logger.error("Error creating FIP  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    public ProjectResponseDto getProjectByKeyid( String keyid) {
        return projectRepository.getProjectById(keyid);
    }


    @Transactional
    public ResponseEntity<KznTlProjectcreationmst> update(ProjectUpdationDto projectUpdationDto) {
        try {

            KznTlProjectcreationmst projectCreation = projectUpdationDto.getProjectCreation();

            // Check if keyId is null or empty
            if (ValidationUtil.isValidKeyId(projectCreation.getKeyid()) ) {
                if (projectRepository.existsById(projectCreation.getKeyid())) {
                    KznTlProjectcreationmst updateEntity = projectRepository.save(projectCreation);
                    logger.info("Successfully updated Project with Key ID: {}", updateEntity.getKeyid());

                   // return ResponseEntity.status(HttpStatus.OK).body(actionPlanSaveDto);
                }else{
                     new RuntimeException("Project Keyid not found: " + projectCreation.getKeyid());
                }
            } else {
               new RuntimeException("Project Keyid is InValid: " + projectCreation.getKeyid());  
            }

            String benval = projectCreation.getBenefits();;
			int amount=Integer.parseInt(ValidationUtil.isValidKeyId(benval)? benval : "0" );
			String trnsCode=null;
            logger.info(" Project : {}, benifit Amount : {}", projectCreation.getKeyid(),amount);
			
			if(amount>=500000 && amount<10000000){
				trnsCode="FIPRODEFGE5L";
			}
			else if(amount>=10000000 ){
				trnsCode="FIPRODEFGE1C";
			}
			else{
				trnsCode="FIPRODEF";
			}
            KkTlFipapprovalslist fipapprovalslist = new KkTlFipapprovalslist();
                fipapprovalslist.setProjectno(projectCreation.getKeyid());
                fipapprovalslist.setProjecttype("FIP");
                fipapprovalslist.setStage(trnsCode);
                fipapprovalslist.setNextapproval("AROL0002");
                fipapprovalslist.setIslastapproval('N');
                fipapprovalslist.setLastapprovalby("AROL0059");
                fipapprovalslist.setLastapprovaldate(LocalDateTime.now());
                fipapprovalslist.setFnlnid(projectCreation.getFlid());
                fipapprovalslist.setMenulevelstatus('P');
                fipapprovalslist.setCurrentstatus('P');


            if("define".equals(projectUpdationDto.getMode())){
                int wfCount = workFlowRepository.UpdateWorkFlowStatus(projectCreation.getKeyid(),projectCreation.getCreatedby() );
                logger.info(" Project WorkFlow Updated with Key ID: {},count : {}", projectCreation.getKeyid(),wfCount);
               
                fipapprovalslist = fipapprovalslistRepository.save(fipapprovalslist);
                logger.info(" Project Approval inserted for PBU with Key ID: {},stage : {},Lastapprovalby : {}", projectCreation.getKeyid(),trnsCode,fipapprovalslist.getLastapprovalby());

                projectCreation.setDefinestage("P");

                projectCreation = projectRepository.save(projectCreation);
                
            }else if("finance".equals(projectUpdationDto.getMode())){

                int updatecurrentcount = fipapprovalslistRepository.UpdateFipApprovalCurrentStatus(projectCreation.getKeyid(), trnsCode);
                logger.info(" Project Approval Current Status Updated with Key ID: {},count : {}", projectCreation.getKeyid(),updatecurrentcount);
                int updatemenucount = fipapprovalslistRepository.UpdateFipApprovalMenuLevelStatus(projectCreation.getKeyid(), trnsCode);
                logger.info(" Project  Approval MenuLevel Status Updated with Key ID: {},count : {}", projectCreation.getKeyid(),updatemenucount);

                fipapprovalslist.setNextapproval("AROL0060");
                fipapprovalslist.setLastapprovalby("AROL0061");
                fipapprovalslist = fipapprovalslistRepository.save(fipapprovalslist);
                logger.info(" Project Approval inserted in Finance with Key ID: {},stage : {},Lastapprovalby : {}", projectCreation.getKeyid(),trnsCode,fipapprovalslist.getLastapprovalby());

            }
            return ResponseEntity.status(HttpStatus.OK).body(projectCreation);

        } catch (Exception e) {
            logger.error("Error creating FIP  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }


    @Transactional
    public ResponseEntity<KznTlProjectcreationmst> updateMAICStatus(String keyid , String stage) {
        try {

            KznTlProjectcreationmst projectCreation = projectRepository.findById(keyid).orElseThrow(() ->
                    new RuntimeException("Project not found: " + keyid));

            if (stage.equals("M")) {
                projectCreation.setMeasurestage('C');
                projectCreation.setMeasurecompleteddate(LocalDateTime.now());
            }else if (stage.equals("A")) {
                projectCreation.setAnalysestage('C');
                projectCreation.setAnalysecompleteddate(LocalDateTime.now());
            }else if (stage.equals("I")) {
                projectCreation.setImprovestage('C');
                projectCreation.setImprovecompleteddate(LocalDateTime.now());
            }else if (stage.equals("C")) {
                projectCreation.setControlstage('C');
                projectCreation.setControlcompleteddate(LocalDateTime.now());
            }

            KznTlProjectcreationmst updateEntity = projectRepository.save(projectCreation);
            logger.info("Successfully updated PROJECT MAIC STATUS with Key ID: {}", updateEntity.getKeyid());

            return ResponseEntity.status(HttpStatus.OK).body(projectCreation);

        } catch (Exception e) {
            logger.error("Error UPDATING FIP  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

     @Transactional
    public ResponseEntity<String> updateFIPApprovals(String rolename, String refId,
			String nxtrole, String trnscode, Character lstlvl, String flId,
			String roleid,String workstatus) {
        try {
            Character status=null;

            int count = fipapprovalslistRepository.getFipApprovalCount(refId,trnscode);
        
            if(workstatus.equals("A"))
			{
			if(trnscode.equals("FIPRODEF")||trnscode.equals("FIPRODEFGE1C")||trnscode.equals("FIPRODEFGE5L")||trnscode.equals("FIPROCLO")||trnscode.equals("FIPROCLOGE5L")||trnscode.equals("FIPROCLOGE1C"))
			{
                
                logger.info("FIP Approval Count  with Key ID: {}, Transaction : {} ,count : {}",refId,trnscode,count);

                if(count>=1){
                    int statusCount = fipapprovalslistRepository.UpdateFipApprovalCurrentStatus(refId, trnscode);
                    logger.info("FIP Approval Current Status  with Key ID: {}, Transaction : {},count : {}",refId,trnscode,statusCount);
                }

                if(lstlvl.equals('Y'))
                {
				    logger.info(" In side the Last level Y   ");
				    status='C';
			    }
			    else{
				    logger.info(" In side the Last level N");
				    status='P';
			    }

                int statusCount = fipapprovalslistRepository.UpdateFipApprovalMenuLevelStatus(refId, trnscode);
                logger.info("FIP Approval MenuLevel Status  with Key ID: {}, Transaction : {},count : {}",refId,trnscode,statusCount);


                KkTlFipapprovalslist fipapprovalslist = new KkTlFipapprovalslist();
                fipapprovalslist.setProjectno(refId);
                fipapprovalslist.setProjecttype("FIP");
                fipapprovalslist.setStage(trnscode);
                fipapprovalslist.setNextapproval(nxtrole);
                fipapprovalslist.setIslastapproval(lstlvl);
                fipapprovalslist.setLastapprovalby(roleid);
                fipapprovalslist.setLastapprovaldate(LocalDateTime.now());
                fipapprovalslist.setFnlnid(flId);
                fipapprovalslist.setMenulevelstatus(status);
                fipapprovalslist.setCurrentstatus(status);

                fipapprovalslist = fipapprovalslistRepository.save(fipapprovalslist);
                logger.info(" FIP Approval inserted  with Key ID: {},stage : {},Lastapprovalby : {}",refId,trnscode,fipapprovalslist.getLastapprovalby());


            if(lstlvl.equals('N'))
			{
				if(trnscode.equals("FIPRODEF")||trnscode.equals("FIPRODEFGE1C")||trnscode.equals("FIPRODEFGE5L"))
				{
                    int dcount = projectRepository.UpdateDefineStage(refId);
                    logger.info("FIP Approval Define Stage Update with Key ID: {}, Transaction : {},count : {}",refId,trnscode,dcount);
				}
            }else{

                if(trnscode.equals("FIPRODEF")||trnscode.equals("FIPRODEFGE1C")||trnscode.equals("FIPRODEFGE5L")){
				System.out.println(" IN side the Else Statement");
                int dcount = projectRepository.UpdateDefineCompleteStage(refId);
                int mcount = projectRepository.UpdateMeasureStage(refId);
                logger.info("FIP Approval Define  Stage Complete  Update with Key ID: {}, Transaction : {}, Define count : {}, Measure count : {}",refId,trnscode,dcount,mcount);
				}
				else if(trnscode.equals("FIPROCLO")||trnscode.equals("FIPROCLOGE1C")||trnscode.equals("FIPROCLOGE5L")){
					
					int ccount = projectRepository.UpdateClosureCompleteStage(refId);
                    logger.info("FIP Approval Closure  Stage Complete  Update with Key ID: {}, Transaction : {}, Closure count : {}",refId,trnscode,ccount);
					
				}
            }
        }


    }

            
            return ResponseEntity.status(HttpStatus.OK).body("Approved");

        } catch (Exception e) {
            logger.error("Error UPDATING FIP  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }


    @Transactional
    public String getWorkFlowStaus(String kznKeyId, String refType,String transCode,String isUpdate,String wfStatus){

        
		Character wfnewstatus = 'P';
		String dmaicStage="";

        int status = workFlowRepository.getWorkFlowStatusCount(kznKeyId,refType,transCode);
        logger.info("FIP Approvaled Count  with Key ID: {}, Transaction : {} ,count : {}",kznKeyId,transCode,status);


        if(wfStatus.equals("R")){
			wfnewstatus='R';
		}
		else if(wfStatus.equals("E")){
			wfnewstatus='E';
		}

        if(isUpdate.equals("Y")){

            if(status == 1){
            logger.info("FIP Approval  before 1 Update with Key ID: {}, Transaction : {}",kznKeyId,transCode);
                if (transCode.equals("FIPRODEF") ||transCode.equals("FIPRODEFGE1C")||transCode.equals("FIPRODEFGE5L") ){
                   logger.info("FIP Approval Define  Stage before Update with Key ID: {}, Transaction : {}",kznKeyId,transCode);
                    int dcount = projectRepository.UpdateDefineCompleteStage(kznKeyId);
                    logger.info("FIP Approval Define  Stage Complete  Update with Key ID: {}, Transaction : {}, Define count : {}",kznKeyId,transCode,dcount);
                    int mCount = projectRepository.UpdateMeasureCompleteStage(kznKeyId);
                    logger.info("FIP Approval Measure  Stage Complete  Update with Key ID: {}, Transaction : {}, Define count : {}",kznKeyId,transCode,mCount);
                    int aCount = projectRepository.UpdateAnalyseCompleteStage(kznKeyId);
                    logger.info("FIP Approval Analyse  Stage Complete  Update with Key ID: {}, Transaction : {}, Define count : {}",kznKeyId,transCode,aCount);
                    int iCount = projectRepository.UpdateImproveCompleteStage(kznKeyId);
                    logger.info("FIP Approval Improve  Stage Complete  Update with Key ID: {}, Transaction : {}, Define count : {}",kznKeyId,transCode,iCount);
                    int cCount = projectRepository.UpdateControlCompleteStage(kznKeyId);
                    logger.info("FIP Approval Control  Stage Complete  Update with Key ID: {}, Transaction : {}, Define count : {}",kznKeyId,transCode,cCount);
                }else if(transCode.equals("FIPROMEA") ){
					int mCount = projectRepository.UpdateMeasureCompleteStage(kznKeyId);
                    logger.info("FIP Approval Measure  Stage Complete  Update with Key ID: {}, Transaction : {}, Define count : {}",kznKeyId,transCode,mCount);
                    
				}
				else if(transCode.equals("FIPROANA") ){
					int aCount = projectRepository.UpdateAnalyseCompleteStage(kznKeyId);
                    logger.info("FIP Approval Analyse  Stage Complete  Update with Key ID: {}, Transaction : {}, Define count : {}",kznKeyId,transCode,aCount);
                    
				}
				else if(transCode.equals("FIPROIMP") ){
					int iCount = projectRepository.UpdateImproveCompleteStage(kznKeyId);
                    logger.info("FIP Approval Improve  Stage Complete  Update with Key ID: {}, Transaction : {}, Define count : {}",kznKeyId,transCode,iCount);
                    
				}
				else if(transCode.equals("FIPROCON") ){
					int cCount = projectRepository.UpdateControlCompleteStage(kznKeyId);
                    logger.info("FIP Approval Control  Stage Complete  Update with Key ID: {}, Transaction : {}, Define count : {}",kznKeyId,transCode,cCount);
                }
				else if(transCode.equals("FIPROCLO")||transCode.equals("FIPROCLOGE5L")||transCode.equals("FIPROCLOGE1C") ){
					//System.out.println("UPDATE SQL :"+sql);
					int ccount = projectRepository.UpdateClosureCompleteStage(kznKeyId);
                    logger.info("FIP Approval Closure  Stage Complete  Update with Key ID: {}, Transaction : {}, Closure count : {}",kznKeyId,transCode,ccount);
					
				}
            }else if (status == 0 && ! wfnewstatus.equals('P') ){				
				if (transCode.equals("FIPRODEF") ||transCode.equals("FIPRODEFGE1C")||transCode.equals("FIPRODEFGE5L") ){	
					int dcount = projectRepository.UpdateDefineStageWithStatus(kznKeyId,wfnewstatus);
                    logger.info("FIP Approval Define  Stage with Status  Update with Key ID: {}, Transaction : {}, Define count : {}",kznKeyId,transCode,dcount);
                    
				}else {
					if(wfStatus.equals("A")){
						wfnewstatus='L';
					}
					if(transCode.equals("FIPROMEA") ){
						dmaicStage="M";
					}
					else if(transCode.equals("FIPROANA") ){
						dmaicStage="A";
					}
					else if(transCode.equals("FIPROIMP") ){
						dmaicStage="I";
					}
					else if(transCode.equals("FIPROCON") ){
						dmaicStage="C";
					}
					else if (transCode.equals("FIPROCLO")||transCode.equals("FIPROCLOGE5L")||transCode.equals("FIPROCLOGE1C") ){
						if(wfStatus.equals("A")){
							wfnewstatus='P';
						}
						dmaicStage="X";
                        int cCount = projectRepository.UpdateClosureStageWithStatus(kznKeyId,wfnewstatus);
                        logger.info("FIP Approval Control  Stage with Status  Update with Key ID: {}, Transaction : {}, Define count : {}",kznKeyId,transCode,cCount);
                
					}
				}
            }

        }
                return wfStatus;
            }


    public List<Map<String, Object>> getChecklistByStage( String keyid,String Stage) {
        return checlistLinkRepository.getChecklistByStage(keyid,Stage);
    }

    public List<Map<String, Object>> getProjectKpi(String keyid) {
        return kpiLinkRepository.getProjectKpi(keyid);
    }

     public List<Map<String, Object>> getProjectKaizen( String flid,String masterId) {
        return kaizenLinkRepository.getProjectKaizen(flid,masterId);
    }

    public List<Map<String, Object>> getProjectMilestone(String masterId) {
        return milestoneMstRepository.getProjectMileStones(masterId);
    }
    public KznTlProjMilestoneMst getRecallMilestone(String keyid) {
        return milestoneMstRepository.findById(keyid).orElseThrow(() ->
                    new RuntimeException("Mile Stone Mst not found: " + keyid));
    }

    public List<Map<String, Object>> getAllMilestones(String stage,String keyid) {
        return milestoneMstRepository.getProjectAllMileStones(stage,keyid);
    }


    @Transactional
    public ResponseEntity<List<KznTlProjectKpiLink>> saveKpi(List<ProjectKpiSaveDto> projectKpiSaveDtoList) {
        try {


            List<KznTlProjectKpiLink> KpiLinkList = new ArrayList<>();
            for(ProjectKpiSaveDto kpiDto : projectKpiSaveDtoList ){
                KznTlProjectKpiLink kpiLink = kpiDto.getKpiLink();

                if (!ValidationUtil.isValidKeyId(kpiLink.getKeyid()) ) {
                    String newKeyId = dbActionTemplate.getSequenceNumber("KZN_TL_PROJECT_KPI_LINK",15,"KPK","YYMMDD","Y");
                    kpiLink.setKeyid(newKeyId);
                    logger.info("Generated new Key ID: {} for KPI Link ", newKeyId);
                    KznTlProjectKpiLink savedEntity = kpiLinkRepository.save(kpiLink);
                    KpiLinkList.add(savedEntity);
                    logger.info("Successfully created KPI Link  with Key ID: {}", savedEntity.getKeyid());
                
            } else {
                if (kpiDto.getIsDelete().equals("Y")) {
                    int deleteCount = kpiLinkRepository.DeleteProjectKpi(kpiLink.getKeyid());
                    logger.info("Successfully Deleted  Kpi link with Key ID: {} , count : {}", kpiLink.getKeyid(),deleteCount);

                }else{
                    if (kpiLinkRepository.existsById(kpiLink.getKeyid())) {
                    KznTlProjectKpiLink updateEntity = kpiLinkRepository.save(kpiLink);
                    logger.info("Successfully updated Kpi link with Key ID: {}", updateEntity.getKeyid());
                    KpiLinkList.add(updateEntity);
                }else{
                     new RuntimeException(" kpi link Keyid not found: " + kpiLink.getKeyid());
                }
                }
                 
            }
            }
            
            return ResponseEntity.status(HttpStatus.OK).body(KpiLinkList);

        } catch (Exception e) {
            logger.error("Error Saving Kpi Link  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @Transactional
    public ResponseEntity<List<KznTlProjectResourceLink>> saveResource(List<ProjectResourceSaveDto> projectResourceSaveDtoList) {
        try {


            List<KznTlProjectResourceLink> resourceLinkList = new ArrayList<>();
            for(ProjectResourceSaveDto resourceDto : projectResourceSaveDtoList ){
                KznTlProjectResourceLink resourceLink = resourceDto.getResourceLink();

                if (!ValidationUtil.isValidKeyId(resourceLink.getKeyid()) ) {
                    String newKeyId = dbActionTemplate.getSequenceNumber("KZN_TL_PROJECT_RESOURCE_LINK",15,"KPR","YYMMDD","Y");
                    resourceLink.setKeyid(newKeyId);
                    logger.info("Generated new Key ID: {} for Resource Link ", newKeyId);
                    KznTlProjectResourceLink savedEntity = resourceLinkRepository.save(resourceLink);
                    resourceLinkList.add(savedEntity);
                    logger.info("Successfully created Resource Link  with Key ID: {}", savedEntity.getKeyid());
                
            } else {
                if (resourceDto.getIsDelete().equals("Y")) {
                    int deleteCount = resourceLinkRepository.DeleteProjectResource(resourceLink.getKeyid());
                    logger.info("Successfully Deleted  Resource link with Key ID: {} , count : {}", resourceLink.getKeyid(),deleteCount);

                }else{
                    if (resourceLinkRepository.existsById(resourceLink.getKeyid())) {
                    KznTlProjectResourceLink updateEntity = resourceLinkRepository.save(resourceLink);
                    logger.info("Successfully updated Resource link with Key ID: {}", updateEntity.getKeyid());
                    resourceLinkList.add(updateEntity);
                }else{
                    // new RuntimeException(" Resource link Keyid not found: " + resourceLink.getKeyid());
                    logger.info(" ERROR  Resource link Keyid not found: {}", resourceLink.getKeyid());
                }
                }
                 
            }
            }
            
            return ResponseEntity.status(HttpStatus.OK).body(resourceLinkList);

        } catch (Exception e) {
            logger.error("Error saving  Resourse  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }


    @Transactional
    public ResponseEntity<String> deleteResource(String Keyid) {
        try {
        String Msg ="";
          if (!ValidationUtil.isValidKeyId(Keyid) ) {
                logger.info("Resource link Keyid  is InValid: {}", Keyid);
                Msg = "Resource link Keyid  is InValid: " + Keyid;
                
            } else {
                
                    if (resourceLinkRepository.existsById(Keyid)) {
                    resourceLinkRepository.deleteById(Keyid);
                    logger.info("Successfully deleted Resource link with Key ID: {}", Keyid);
                    Msg = "Successfully deleted Resource link with Key ID: " + Keyid;
                }else{
                    logger.info(" ERROR  Resource link Keyid not found: {}", Keyid);
                    Msg = " ERROR  Resource link Keyid not found:  " + Keyid;
                }
                
                 
            }
            
            
            return ResponseEntity.status(HttpStatus.OK).body(Msg);

        } catch (Exception e) {
            logger.error("Error Deleting  Resourse  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }


    @Transactional
    public ResponseEntity<List<KznTlProjectChecklistLink>> saveChecklist(List<KznTlProjectChecklistLink> checkListLinks) {
        try {


            List<KznTlProjectChecklistLink> checkListLinkList = new ArrayList<>();
            for(KznTlProjectChecklistLink checkListLink : checkListLinks ){
                

                if (!ValidationUtil.isValidKeyId(checkListLink.getKeyid()) ) {
                    String newKeyId = dbActionTemplate.getSequenceNumber("KZN_TL_PROJECT_CHECKLIST_LINK",15,"PCL","","");
                    checkListLink.setKeyid(newKeyId);
                    logger.info("Generated new Key ID: {} for Resource Link ", newKeyId);
                    KznTlProjectChecklistLink savedEntity = checlistLinkRepository.save(checkListLink);
                    checkListLinkList.add(savedEntity);
                    logger.info("Successfully created CheckList Link  with Key ID: {}", savedEntity.getKeyid());
                
            } else {
                
                if (checlistLinkRepository.existsById(checkListLink.getKeyid())) {
                    KznTlProjectChecklistLink updateEntity = checlistLinkRepository.save(checkListLink);
                    logger.info("Successfully updated CheckList link with Key ID: {}", updateEntity.getKeyid());
                    checkListLinkList.add(updateEntity);
                }else{
                    // new RuntimeException(" Resource link Keyid not found: " + resourceLink.getKeyid());
                    logger.info(" ERROR  CheckList link Keyid not found: {}", checkListLink.getKeyid());
                }
                
                 
            }
            }
            
            return ResponseEntity.status(HttpStatus.OK).body(checkListLinkList);

        } catch (Exception e) {
            logger.error("Error saving CheckList  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }


    @Transactional
    public ResponseEntity<List<KznTlProjectKaizenLink>> saveKaizen(List<ProjectKaizenSaveDto> projectKaizenSaveDtoList) {
        try {


            List<KznTlProjectKaizenLink> kaizenLinkList = new ArrayList<>();
            for(ProjectKaizenSaveDto kaizenDto : projectKaizenSaveDtoList ){
                KznTlProjectKaizenLink kaizenLink =kaizenDto.getKaizenLink() ;

                if (!ValidationUtil.isValidKeyId(kaizenLink.getKeyid()) ) {
                    String newKeyId = dbActionTemplate.getSequenceNumber("KZN_TL_PROJECT_KAIZEN_LINK",15,"KKL","YYMMDD","Y");
                    kaizenLink.setKeyid(newKeyId);
                    logger.info("Generated new Key ID: {} for KAIZEN Link ", newKeyId);
                    KznTlProjectKaizenLink savedEntity = kaizenLinkRepository.save(kaizenLink);
                    kaizenLinkList.add(savedEntity);
                    logger.info("Successfully created KAIZEN Link  with Key ID: {}", savedEntity.getKeyid());
                
            } else {
                if (kaizenDto.getIsDelete().equals("Y")) {
                    int deleteCount = kaizenLinkRepository.DeleteProjectKaizen(kaizenLink.getKeyid());
                    logger.info("Successfully Deleted  Kaizen link with Key ID: {} , count : {}", kaizenLink.getKeyid(),deleteCount);

                }else{
                    if (kaizenLinkRepository.existsById(kaizenLink.getKeyid())) {
                    KznTlProjectKaizenLink updateEntity = kaizenLinkRepository.save(kaizenLink);
                    logger.info("Successfully updated Kaizen link with Key ID: {}", updateEntity.getKeyid());
                    kaizenLinkList.add(updateEntity);
                }else{
                     new RuntimeException(" kaizen link Keyid not found: " + kaizenLink.getKeyid());
                }
                }
                 
            }
            }
            
            return ResponseEntity.status(HttpStatus.OK).body(kaizenLinkList);

        } catch (Exception e) {
            logger.error("Error Saving Kpi Link  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @Transactional
    public ResponseEntity<ProjectMilestoneSaveDto> saveMilestone(ProjectMilestoneSaveDto projectMilestoneSaveDto) {
        try {

            KznTlProjMilestoneMst milestoneMst = projectMilestoneSaveDto.getMilestoneMst();
            
            List<KznTlProjMilestoneDtl> milestoneDtls = new ArrayList<>(); 

            // Check if keyId is null or empty
            if (!ValidationUtil.isValidKeyId(milestoneMst.getKeyid()) ) {
                
                String newKeyId = dbActionTemplate.getSequenceNumber("KZN_TL_PROJ_MILESTONE_MST",15,"KMM","YYMMDD","Y");
                milestoneMst.setKeyid(newKeyId);
                logger.info("Generated new Key ID: {} for Action plan Mst", newKeyId);
                KznTlProjMilestoneMst savedEntity = milestoneMstRepository.save(milestoneMst);
                projectMilestoneSaveDto.setMilestoneMst(savedEntity);
                logger.info("Successfully created MileStone Mst with Key ID: {}", savedEntity.getKeyid());

            } else {
                // Validate if the provided keyId already exists
                if (milestoneMstRepository.existsById(milestoneMst.getKeyid())) {
                    KznTlProjMilestoneMst updateEntity = milestoneMstRepository.save(milestoneMst);
                    logger.info("Successfully updated MileStone Mst with Key ID: {}", updateEntity.getKeyid());

                   // return ResponseEntity.status(HttpStatus.OK).body(actionPlanSaveDto);
                }else{
                     new RuntimeException("MileStone Mst not found: " + milestoneMst.getKeyid());
                }
            }

            for (KznTlProjMilestoneDtl milestoneDtl : projectMilestoneSaveDto.getMilestoneDtlList() ){
                  milestoneDtl.setKmmm_keyid(milestoneMst.getKeyid());
                 if (!ValidationUtil.isValidKeyId(milestoneDtl.getKeyid()) ) {
                    String newKeyId = dbActionTemplate.getSequenceNumber("KZN_TL_PROJ_MILESTONE_DTL",15,"KMD","YYMMDD","Y");
                    milestoneDtl.setKeyid(newKeyId);
                    logger.info("Generated new Key ID: {} for MileStone Detail ", newKeyId);
                    KznTlProjMilestoneDtl savedEntity = milestoneDtlRepository.save(milestoneDtl);
                    milestoneDtls.add(savedEntity);
                    logger.info("Successfully created MileStone Detail with Key ID: {}", savedEntity.getKeyid());
                 }else {
                // Validate if the provided keyId already exists
                if (milestoneDtlRepository.existsById(milestoneDtl.getKeyid())) {
                    LocalDateTime existTargetDate = milestoneDtlRepository.getExistingTarget(milestoneDtl.getKeyid());
                    if(!milestoneDtl.getTargetdate().equals(existTargetDate)){
                        int insertcount = milestoneDtlRepository.insertintoDtlHis(milestoneDtl.getKeyid());
                        logger.info("Successfully inserted MileStone Detail in history with Key ID: {} , count : {} ", milestoneDtl.getKeyid(),insertcount);
                    }
                    KznTlProjMilestoneDtl updateEntity = milestoneDtlRepository.save(milestoneDtl);
                    milestoneDtls.add(updateEntity);
                    logger.info("Successfully updated MileStone Dtl with Key ID: {}", updateEntity.getKeyid());
                    int mastercount = milestoneDtlRepository.updateMaster(milestoneMst.getKeyid());
                    logger.info("Successfully update master status MileStone mst with Key ID: {} , count : {} ", milestoneDtl.getKeyid(),mastercount);

                   // return ResponseEntity.status(HttpStatus.OK).body(actionPlanSaveDto);
                }else{
                     new RuntimeException("MileStone Detail not found: " + milestoneDtl.getKeyid());
                }
            }


            }
            projectMilestoneSaveDto.setMilestoneDtlList(milestoneDtls);

            return ResponseEntity.status(HttpStatus.CREATED).body(projectMilestoneSaveDto);

        } catch (Exception e) {
            logger.error("Error saving Milestones : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }


    @Transactional
    public ResponseEntity<String> deleteMilestone(String Keyid) {
        try {
        String Msg ="";
          if (!ValidationUtil.isValidKeyId(Keyid) ) {
                logger.info("MileStone Mst Keyid  is InValid: {}", Keyid);
                Msg = "MileStone Mst Keyid  is InValid: " + Keyid;
                
            } else {
                
                    if (milestoneMstRepository.existsById(Keyid)) {
                        int dtlCount =  milestoneDtlRepository.deleteAllDetailMilestone(Keyid);
                        int mstCount =  milestoneMstRepository.deleteMasterMilestone(Keyid);
                    logger.info("Successfully deleted MileStone with Key ID: {} , Dtl count : {} , MST Count : {} ", Keyid,dtlCount,mstCount);
                    Msg = "Successfully deleted MileStone with Key ID: " + Keyid +" , Dtl count : "+dtlCount+" , MST Count :"+mstCount;
                }else{
                    logger.info(" ERROR  MileStone Keyid not found: {}", Keyid);
                    Msg = " ERROR  MileStone Keyid not found:  " + Keyid;
                }
                
                 
            }
            
            
            return ResponseEntity.status(HttpStatus.OK).body(Msg);

        } catch (Exception e) {
            logger.error("Error Deleting  Milestone  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @Transactional
    public ResponseEntity<String> deleteMilestoneDetail(String Keyid) {
        try {
        String Msg ="";
          if (!ValidationUtil.isValidKeyId(Keyid) ) {
                logger.info("MileStone Dtl Keyid  is InValid: {}", Keyid);
                Msg = "MileStone Dtl Keyid  is InValid: " + Keyid;
                
            } else {
                
                    if (milestoneDtlRepository.existsById(Keyid)) {
                        int dtlCount =  milestoneDtlRepository.deleteDetailMilestone(Keyid);
                    logger.info("Successfully deleted MileStone with Key ID: {} , Dtl count : {} ", Keyid,dtlCount);
                    Msg = "Successfully deleted MileStone with Key ID: " + Keyid +" , Dtl count : "+dtlCount;
                }else{
                    logger.info(" ERROR  MileStone Dtl Keyid not found: {}", Keyid);
                    Msg = " ERROR  MileStone Dtl Keyid not found:  " + Keyid;
                }
                
                 
            }
            
            
            return ResponseEntity.status(HttpStatus.OK).body(Msg);

        } catch (Exception e) {
            logger.error("Error Deleting  Milestone Dtl  : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}
