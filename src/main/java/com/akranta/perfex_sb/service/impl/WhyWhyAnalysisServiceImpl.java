package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.*;
import com.akranta.perfex_sb.repository.*;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.WhyWhyAnalysisService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.WhyWhyRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WhyWhyAnalysisServiceImpl implements WhyWhyAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(WhyWhyAnalysisServiceImpl.class);

    private final BdmTlWhywhymstRepository repository;
    private final BdmTlWhywhydtlRepository detailRepository;
    private final BdmTlYyeffectivemstRepository effectiveMstRepository;
    private final BdmTlYyeffectivedtlRepository effectiveDtlRepository;
    private final BdmTlYydonebymstRepository doneByRepository;
    private final BdmTlYyproblemattbymstRepository problemAttByRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER_MASTER = "BDM_TL_WHYWHYMST";
    private static final String SEQ_IDENTIFIER_DETAIL = "BDM_TL_WHYWHYDTL";
    private static final String SEQ_IDENTIFIER_EFFECTIVE_MASTER = "BDM_TL_YYEFFECTIVEMST";
    private static final String SEQ_IDENTIFIER_EFFECTIVE_DETAIL = "BDM_TL_YYEFFECTIVEDTL";
    private static final String SEQ_IDENTIFIER_DONEBY = "BDM_TL_YYDONEBYMST";
    private static final String SEQ_IDENTIFIER_PROBLEMATTBY = "BDM_TL_YYPROBLEMATTBYMST";
    
    // FIXED: Changed to match old format YYD160000132 (14 chars)
    private static final int KEY_LENGTH_MASTER = 15;
    private static final int KEY_LENGTH_DETAIL = 14;  // Changed from 12 to 14
    
    private static final String PREFIX_MASTER = "YYB";
    private static final String PREFIX_DETAIL = "YYD";
    private static final String PREFIX_EFFECTIVE_MASTER = "YYE";
    private static final String PREFIX_EFFECTIVE_DETAIL = "YYD";
    private static final String PREFIX_DONEBY = "YYB";
    private static final String PREFIX_PROBLEMATTBY = "YYPA";
    
    // FIXED: Use YY format without separator for old format compatibility
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    public WhyWhyAnalysisServiceImpl(
        BdmTlWhywhymstRepository repository, 
        BdmTlWhywhydtlRepository detailRepository,
        BdmTlYyeffectivemstRepository effectiveMstRepository,
        BdmTlYyeffectivedtlRepository effectiveDtlRepository,
        BdmTlYydonebymstRepository doneByRepository,
        BdmTlYyproblemattbymstRepository problemAttByRepository,
        DbActionTemplate dbActionTemplate) {
        this.repository = repository;
        this.detailRepository = detailRepository;
        this.effectiveMstRepository = effectiveMstRepository;
        this.effectiveDtlRepository = effectiveDtlRepository;
        this.doneByRepository = doneByRepository;
        this.problemAttByRepository = problemAttByRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<WhyWhyRequest> saveWhyWhy(WhyWhyRequest request) throws Exception {
        BdmTlWhywhymst master = request.getMaster();
        List<BdmTlWhywhydtl> details = request.getDetails();
        List<BdmTlYydonebymst> doneByList = request.getDoneByList();
        List<BdmTlYyproblemattbymst> problemAttByList = request.getProblemAttendedByList();

        if (master == null) {
            throw new RuntimeException("No WhyWhy Master Details");
        }

        WhyWhyRequest result = new WhyWhyRequest();

        // CHECK IF INSERT OR UPDATE
        if (master.getKeyid() == null || master.getKeyid().trim().isEmpty()) {
            // INSERT MODE
            String newMstKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER_MASTER, KEY_LENGTH_MASTER, PREFIX_MASTER, DATE_FORMAT, FORMAT_RESET
            );

            if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                logger.error("Failed to generate the Master Key ID");
                throw new RuntimeException("Failed to generate Master Key ID");
            }

            master.setKeyid(newMstKeyid);
            if (master.getCreatedon() == null) {
                master.setCreatedon(LocalDateTime.now());
            }
            master.setModifiedon(LocalDateTime.now());
            logger.info("section  ID: {}, trade id:{}", master.getSectionid(),master.getTradeid());
            int ApprovalCount = repository.checkAreaInchargeApprovals(master.getSectionid(),master.getTradeid());
            logger.info("Approval Count  Master Key ID: {}, count:{}", newMstKeyid,ApprovalCount);
            if(ApprovalCount == 0){
                master.setAppStatus("C");
            }
            // FIXED: Set default appstatus based on timespent
          //  if (master.getAppstatus() == null || master.getAppstatus().trim().isEmpty()) {
            //    try {
                  //  float timeSpent = master.getTimespent() != null ? master.getTimespent() : 0;
                    //master.setAppstatus(timeSpent >= 0 ? "P" : "-");
                //} catch (Exception e) {
                  //  master.setAppstatus("P");
              //  }
           // }
            
            // FIXED: Set default approved date if not provided
           // if (master.getApprovedon() == null) {
              //  master.setApprovedon(LocalDate.of(2000, 1, 1));
          //  }

            logger.info("Generated new Master Key ID: {}", newMstKeyid);

      } else {
    // UPDATE MODE
    if (repository.existsById(master.getKeyid())) {
        WhyWhyRequest updateResult = new WhyWhyRequest();
        
        // CRITICAL FIX: Only update master if it contains actual data
        // Check if this is a full master update or just adding child records
        boolean isFullMasterUpdate = master.getFlid() != null || 
                                     master.getDate() != null || 
                                     master.getFactoryid() != null ||
                                     master.getProblem() != null;  // Check key fields
        
        BdmTlWhywhymst existingMaster;
        
        if (isFullMasterUpdate) {
            // FULL MASTER UPDATE
            existingMaster = repository.findById(master.getKeyid())
                .orElseThrow(() -> new ResourceNotFoundException("WhyWhy not found"));

            // Only update fields that are NOT null in the incoming request
            if (master.getDate() != null) existingMaster.setDate(master.getDate());
            if (master.getFactoryid() != null) existingMaster.setFactoryid(master.getFactoryid());
            if (master.getSectionid() != null) existingMaster.setSectionid(master.getSectionid());
            if (master.getLossid() != null) existingMaster.setLossid(master.getLossid());
            if (master.getCellid() != null) existingMaster.setCellid(master.getCellid());
            if (master.getSubcellid() != null) existingMaster.setSubcellid(master.getSubcellid());
            if (master.getMachineid() != null) existingMaster.setMachineid(master.getMachineid());
            if (master.getAssemblyid() != null) existingMaster.setAssemblyid(master.getAssemblyid());
            if (master.getTargetpillarid() != null) existingMaster.setTargetpillarid(master.getTargetpillarid());
            if (master.getRefdoctype() != null) existingMaster.setRefdoctype(master.getRefdoctype());
            if (master.getRefdocno() != null) existingMaster.setRefdocno(master.getRefdocno());
            if (master.getPhenomenaid() != null) existingMaster.setPhenomenaid(master.getPhenomenaid());
            if (master.getFinalaction() != null) existingMaster.setFinalaction(master.getFinalaction());
            if (master.getSparesreplaced() != null) existingMaster.setSparesreplaced(master.getSparesreplaced());
            if (master.getChecksmade() != null) existingMaster.setChecksmade(master.getChecksmade());
            if (master.getSymptombefore() != null) existingMaster.setSymptombefore(master.getSymptombefore());
            if (master.getYoudidnot() != null) existingMaster.setYoudidnot(master.getYoudidnot());
            if (master.getCountermeasureid() != null) existingMaster.setCountermeasureid(master.getCountermeasureid());
            if (master.getCountermeasure() != null) existingMaster.setCountermeasure(master.getCountermeasure());
            if (master.getRootcauseid() != null) existingMaster.setRootcauseid(master.getRootcauseid());
            if (master.getRootcause() != null) existingMaster.setRootcause(master.getRootcause());
            if (master.getIsjh() != null) existingMaster.setIsjh(master.getIsjh());
            if (master.getIspm() != null) existingMaster.setIspm(master.getIspm());
            if (master.getIskk() != null) existingMaster.setIskk(master.getIskk());
            if (master.getIsopl() != null) existingMaster.setIsopl(master.getIsopl());
            if (master.getPreventivemeasureid() != null) existingMaster.setPreventivemeasureid(master.getPreventivemeasureid());
            if (master.getPreventivemeasure() != null) existingMaster.setPreventivemeasure(master.getPreventivemeasure());
            if (master.getMaintinchargeid() != null) existingMaster.setMaintinchargeid(master.getMaintinchargeid());
            if (master.getStatus() != null) existingMaster.setStatus(master.getStatus());
            if (master.getIshdpossible() != null) existingMaster.setIshdpossible(master.getIshdpossible());
            if (master.getPrevdate() != null) existingMaster.setPrevdate(master.getPrevdate());
            if (master.getPreveffectiveness() != null) existingMaster.setPreveffectiveness(master.getPreveffectiveness());
            if (master.getIspy() != null) existingMaster.setIspy(master.getIspy());
            if (master.getIsojt() != null) existingMaster.setIsojt(master.getIsojt());
            if (master.getOjtdesc() != null) existingMaster.setOjtdesc(master.getOjtdesc());
            if (master.getIssop() != null) existingMaster.setIssop(master.getIssop());
            if (master.getSopdesc() != null) existingMaster.setSopdesc(master.getSopdesc());
            if (master.getIskzn() != null) existingMaster.setIskzn(master.getIskzn());
            if (master.getIspokayoke() != null) existingMaster.setIspokayoke(master.getIspokayoke());
            if (master.getFormtype() != null) existingMaster.setFormtype(master.getFormtype());
            if (master.getPokayoke() != null) existingMaster.setPokayoke(master.getPokayoke());
            if (master.getAccidentdesc() != null) existingMaster.setAccidentdesc(master.getAccidentdesc());
            if (master.getAccidentphen() != null) existingMaster.setAccidentphen(master.getAccidentphen());
            if (master.getPrevno() != null) existingMaster.setPrevno(master.getPrevno());
            if (master.getPrevperson() != null) existingMaster.setPrevperson(master.getPrevperson());
            if (master.getIseffective() != null) existingMaster.setIseffective(master.getIseffective());
            if (master.getFlid() != null) existingMaster.setFlid(master.getFlid());  // CRITICAL FIELD
            if (master.getArea() != null) existingMaster.setArea(master.getArea());
            if (master.getProblem() != null) existingMaster.setProblem(master.getProblem());
            if (master.getTimespent() != null) existingMaster.setTimespent(master.getTimespent());
            if (master.getProblemattendby() != null) existingMaster.setProblemattendby(master.getProblemattendby());
            if (master.getWhywhydoneby() != null) existingMaster.setWhywhydoneby(master.getWhywhydoneby());
            if (master.getReportdatetime() != null) existingMaster.setReportdatetime(master.getReportdatetime());
            if (master.getOthercheckpoints() != null) existingMaster.setOthercheckpoints(master.getOthercheckpoints());
            if (master.getSparesid() != null) existingMaster.setSparesid(master.getSparesid());
            if (master.getPillarid() != null) existingMaster.setPillarid(master.getPillarid());
            if (master.getProductid() != null) existingMaster.setProductid(master.getProductid());
            if (master.getActive() != null) existingMaster.setActive(master.getActive());
            if (master.getTradeid() != null) existingMaster.setTradeid(master.getTradeid());
            if (master.getApprRoleid() != null) existingMaster.setApprRoleid(master.getApprRoleid());
            if (master.getApprovedBy() != null) existingMaster.setApprovedBy(master.getApprovedBy());
            
            // if (master.getAppStatus() != null && !master.getAppStatus().trim().isEmpty()) {
            //     existingMaster.setAppStatus(master.getAppStatus());
            // }

            //  if (master.getApprvedOn() != null) {
            //     existingMaster.setApprvedOn(master.getApprvedOn());
            // }
            // if (master.getAppRemarks() != null) {
            //     existingMaster.setAppRemarks(master.getAppRemarks());
            // }
            if ("R".equals(existingMaster.getAppStatus())) {
                 logger.info("section  ID: {}, trade id:{}", master.getSectionid(),master.getTradeid());
            int ApprovalCount = repository.checkAreaInchargeApprovals(master.getSectionid(),master.getTradeid());
            logger.info("Approval Count  Master Key ID: {}, count:{}", master.getKeyid(),ApprovalCount);
            if(ApprovalCount == 0){
                existingMaster.setAppStatus("C");
            }else{
                existingMaster.setAppStatus(master.getAppStatus());
            }
               
            }
            if ("R".equals(existingMaster.getAppStatus())) {
                existingMaster.setApprvedOn(master.getApprvedOn());
            }
            if ("R".equals(existingMaster.getAppStatus())) {
                existingMaster.setAppRemarks(master.getAppRemarks());
            }
            
            existingMaster.setModifiedon(LocalDateTime.now());

            BdmTlWhywhymst updatedMaster = repository.save(existingMaster);
            updateResult.setMaster(updatedMaster);
            logger.info("Successfully updated Master with Key ID: {}", updatedMaster.getKeyid());
        } else {
            // CHILD RECORDS ONLY - Just fetch the existing master, don't update it
            existingMaster = repository.findById(master.getKeyid())
                .orElseThrow(() -> new ResourceNotFoundException("WhyWhy not found"));
            updateResult.setMaster(existingMaster);
            logger.info("Fetched existing Master (no update needed) with Key ID: {}", existingMaster.getKeyid());
        }

        // Handle Details in update mode
        List<BdmTlWhywhydtl> resultDetails = new ArrayList<>();
        if (details != null && !details.isEmpty()) {
            for (BdmTlWhywhydtl detail : details) {
                detail.setWwmsKeyid(master.getKeyid());
                
                if (detail.getKeyid() == null || detail.getKeyid().trim().isEmpty() || 
                    detail.getKeyid().equals("undefined")) {
                    
                    String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET
                    );
                    detail.setKeyid(newDetailKeyid);
                    
                    if (detail.getCreatedby() == null || detail.getCreatedby().trim().isEmpty()) {
                        detail.setCreatedby(existingMaster.getCreatedby());
                    }
                    detail.setCreatedon(LocalDateTime.now());
                    detail.setModifiedon(LocalDateTime.now());
                    
                    logger.info("Creating new detail in update mode - KeyID: {}, Master KeyID: {}", 
                               newDetailKeyid, detail.getWwmsKeyid());
                    
                    BdmTlWhywhydtl savedDetail = detailRepository.save(detail);
                    resultDetails.add(savedDetail);
                } else {
                    detail.setModifiedon(LocalDateTime.now());
                    
                    logger.info("Updating existing detail - KeyID: {}, Master KeyID: {}", 
                               detail.getKeyid(), detail.getWwmsKeyid());
                    
                    BdmTlWhywhydtl savedDetail = detailRepository.save(detail);
                    resultDetails.add(savedDetail);
                }
            }
        }
        updateResult.setDetails(resultDetails);

        // Handle DoneBy in update mode
        List<BdmTlYydonebymst> resultDoneBy = new ArrayList<>();
        if (doneByList != null && !doneByList.isEmpty()) {
            for (BdmTlYydonebymst doneBy : doneByList) {
                // VALIDATE empm_keyid
                if (doneBy.getEmpm_Keyid() == null || doneBy.getEmpm_Keyid().trim().isEmpty()) {
                    logger.error("Employee Key ID (empm_keyid) is null for DoneBy");
                    throw new RuntimeException("Employee Key ID (empm_keyid) is required for Done By");
                }
                
                if (doneBy.getKeyid() == null || doneBy.getKeyid().trim().isEmpty()) {
                    doneBy.setWwms_keyid(master.getKeyid());
                    String newDoneByKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER_DONEBY, KEY_LENGTH_MASTER, PREFIX_DONEBY, DATE_FORMAT, FORMAT_RESET
                    );
                    doneBy.setKeyid(newDoneByKeyid);
                    
                    // Set default values
                    if (doneBy.getTempfield1() == null) doneBy.setTempfield1("-");
                    if (doneBy.getTempfield2() == null) doneBy.setTempfield2("-");
                    if (doneBy.getTempfield3() == null) doneBy.setTempfield3("-");
                    if (doneBy.getActive() == null) doneBy.setActive('Y');
                    if (doneBy.getCreatedby() == null) doneBy.setCreatedby(existingMaster.getCreatedby());
                    
                    doneBy.setCreatedon(LocalDateTime.now());
                    doneBy.setModifiedon(LocalDateTime.now());
                    
                    logger.info("Creating new DoneBy in update mode - KeyID: {}, Employee: {}", 
                               newDoneByKeyid, doneBy.getEmpm_Keyid());
                    
                    BdmTlYydonebymst savedDoneBy = doneByRepository.save(doneBy);
                    resultDoneBy.add(savedDoneBy);
                } else {
                    doneBy.setModifiedon(LocalDateTime.now());
                    BdmTlYydonebymst savedDoneBy = doneByRepository.save(doneBy);
                    resultDoneBy.add(savedDoneBy);
                }
            }
        }
        updateResult.setDoneByList(resultDoneBy);

        // Handle ProblemAttBy in update mode
        List<BdmTlYyproblemattbymst> resultProblemAttBy = new ArrayList<>();
        if (problemAttByList != null && !problemAttByList.isEmpty()) {
            for (BdmTlYyproblemattbymst problemAttBy : problemAttByList) {
                // CRITICAL: Validate empm_keyid
                if (problemAttBy.getEmpm_keyid() == null || problemAttBy.getEmpm_keyid().trim().isEmpty()) {
                    logger.error("Employee Key ID (empm_keyid) is null for ProblemAttBy");
                    throw new RuntimeException("Employee Key ID (empm_keyid) is required for Problem Attended By");
                }
                
                if (problemAttBy.getKeyid() == null || problemAttBy.getKeyid().trim().isEmpty()) {
                    problemAttBy.setWwms_keyid(master.getKeyid());
                    String newProblemAttByKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER_PROBLEMATTBY, KEY_LENGTH_MASTER, PREFIX_PROBLEMATTBY, DATE_FORMAT, FORMAT_RESET
                    );
                    problemAttBy.setKeyid(newProblemAttByKeyid);
                    
                    // Set default values
                    if (problemAttBy.getTempfield1() == null) problemAttBy.setTempfield1("-");
                    if (problemAttBy.getTempfield2() == null) problemAttBy.setTempfield2("-");
                    if (problemAttBy.getTempfield3() == null) problemAttBy.setTempfield3("-");
                    if (problemAttBy.getActive() == null) problemAttBy.setActive('Y');
                    if (problemAttBy.getCreatedby() == null) problemAttBy.setCreatedby(existingMaster.getCreatedby());
                    
                    problemAttBy.setCreatedon(LocalDateTime.now());
                    problemAttBy.setModifiedon(LocalDateTime.now());
                    
                    logger.info("Creating new ProblemAttBy in update mode - KeyID: {}, Employee: {}", 
                               newProblemAttByKeyid, problemAttBy.getEmpm_keyid());
                    
                    BdmTlYyproblemattbymst savedProblemAttBy = problemAttByRepository.save(problemAttBy);
                    resultProblemAttBy.add(savedProblemAttBy);
                } else {
                    problemAttBy.setModifiedon(LocalDateTime.now());
                    BdmTlYyproblemattbymst savedProblemAttBy = problemAttByRepository.save(problemAttBy);
                    resultProblemAttBy.add(savedProblemAttBy);
                }
            }
        }
        updateResult.setProblemAttendedByList(resultProblemAttBy);

        updateResult.setFormActionMode(request.getFormActionMode());
        updateResult.setFormMode(request.getFormMode());
        updateResult.setFormHeader(request.getFormHeader());
        updateResult.setWwmsPillarmode(request.getWwmsPillarmode());
        updateResult.setFormType(request.getFormType());
        updateResult.setWwmsPreveffective(request.getWwmsPreveffective());
        updateResult.setWwmsPrevdat(request.getWwmsPrevdat());

        return ResponseEntity.status(HttpStatus.OK).body(updateResult);
    }
}

        // INSERT MODE - Save Master
        BdmTlWhywhymst savedMaster = repository.save(master);
        List<BdmTlWhywhydtl> savedDetailList = new ArrayList<>();

        // Save Details
        if (details != null && !details.isEmpty()) {
            for (BdmTlWhywhydtl detail : details) {
                detail.setWwmsKeyid(savedMaster.getKeyid());
                String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET
                );

                if (newDetailKeyid == null || newDetailKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate Detail Key ID");
                    throw new RuntimeException("Failed to generate Detail Key ID");
                }

                detail.setKeyid(newDetailKeyid);
                
                // FIXED: Ensure createdby is set
                if (detail.getCreatedby() == null || detail.getCreatedby().trim().isEmpty()) {
                    detail.setCreatedby(savedMaster.getCreatedby());
                }
                
                if (detail.getCreatedon() == null) {
                    detail.setCreatedon(LocalDateTime.now());
                }
                detail.setModifiedon(LocalDateTime.now());
                
                // FIXED: Set default values for null fields
                if (detail.getWhy() == null || detail.getWhy().trim().isEmpty()) {
                    detail.setWhy("{}");
                }
                if (detail.getAnswer() == null || detail.getAnswer().trim().isEmpty()) {
                    detail.setAnswer("{}");
                }
                if (detail.getAction() == null || detail.getAction().trim().isEmpty()) {
                    detail.setAction("{}");
                }

                BdmTlWhywhydtl savedDetail = detailRepository.save(detail);
                savedDetailList.add(savedDetail);
                logger.info("Successfully created Detail with Key: {}", newDetailKeyid);
            }
        }

        // Save DoneBy
        List<BdmTlYydonebymst> savedDoneByList = new ArrayList<>();
        if (doneByList != null && !doneByList.isEmpty()) {
            for (BdmTlYydonebymst doneBy : doneByList) {
                doneBy.setWwms_keyid(savedMaster.getKeyid());
                String newDoneByKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER_DONEBY, KEY_LENGTH_MASTER, PREFIX_DONEBY, DATE_FORMAT, FORMAT_RESET
                );

                if (newDoneByKeyid == null || newDoneByKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate DoneBy Key ID");
                    throw new RuntimeException("Failed to generate DoneBy Key ID");
                }

                doneBy.setKeyid(newDoneByKeyid);
                if (doneBy.getCreatedon() == null) {
                    doneBy.setCreatedon(LocalDateTime.now());
                }
                doneBy.setModifiedon(LocalDateTime.now());

                BdmTlYydonebymst savedDoneBy = doneByRepository.save(doneBy);
                savedDoneByList.add(savedDoneBy);
                logger.info("Successfully created DoneBy with Key: {}", newDoneByKeyid);
            }
        } else if (savedMaster.getWhywhydoneby() != null && 
                   !savedMaster.getWhywhydoneby().trim().isEmpty() && 
                   !savedMaster.getWhywhydoneby().equals("{}")) {
            
            BdmTlYydonebymst doneBy = new BdmTlYydonebymst();
            doneBy.setWwms_keyid(savedMaster.getKeyid());
            doneBy.setEmpm_keyid(savedMaster.getWhywhydoneby());
            doneBy.setTempfield1("-");
            doneBy.setTempfield2("-");
            doneBy.setTempfield3("-");
            doneBy.setActive('Y');
            doneBy.setCreatedby(savedMaster.getCreatedby());
            doneBy.setCreatedon(savedMaster.getCreatedon());
            doneBy.setModifiedon(savedMaster.getModifiedon());
            
            String newDoneByKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER_DONEBY, KEY_LENGTH_MASTER, PREFIX_DONEBY, DATE_FORMAT, FORMAT_RESET
            );
            doneBy.setKeyid(newDoneByKeyid);
            
            BdmTlYydonebymst savedDoneBy = doneByRepository.save(doneBy);
            savedDoneByList.add(savedDoneBy);
        }

        // Save ProblemAttBy
        List<BdmTlYyproblemattbymst> savedProblemAttByList = new ArrayList<>();
        if (problemAttByList != null && !problemAttByList.isEmpty()) {
            for (BdmTlYyproblemattbymst problemAttBy : problemAttByList) {
                problemAttBy.setWwms_keyid(savedMaster.getKeyid());
                String newProblemAttByKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER_PROBLEMATTBY, KEY_LENGTH_MASTER, PREFIX_PROBLEMATTBY, DATE_FORMAT, FORMAT_RESET
                );

                if (newProblemAttByKeyid == null || newProblemAttByKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate ProblemAttBy Key ID");
                    throw new RuntimeException("Failed to generate ProblemAttBy Key ID");
                }

                problemAttBy.setKeyid(newProblemAttByKeyid);
                if (problemAttBy.getCreatedon() == null) {
                    problemAttBy.setCreatedon(LocalDateTime.now());
                }
                problemAttBy.setModifiedon(LocalDateTime.now());

                BdmTlYyproblemattbymst savedProblemAttBy = problemAttByRepository.save(problemAttBy);
                savedProblemAttByList.add(savedProblemAttBy);
                logger.info("Successfully created ProblemAttBy with Key: {}", newProblemAttByKeyid);
            }
        } else if (savedMaster.getProblemattendby() != null && 
                   !savedMaster.getProblemattendby().trim().isEmpty() && 
                   !savedMaster.getProblemattendby().equals("{}")) {
            
            BdmTlYyproblemattbymst problemAttBy = new BdmTlYyproblemattbymst();
            problemAttBy.setWwms_keyid(savedMaster.getKeyid());
            problemAttBy.setEmpm_keyid(savedMaster.getProblemattendby());
            problemAttBy.setTempfield1("-");
            problemAttBy.setTempfield2("-");
            problemAttBy.setTempfield3("-");
            problemAttBy.setActive('Y');
            problemAttBy.setCreatedby(savedMaster.getCreatedby());
            problemAttBy.setCreatedon(savedMaster.getCreatedon());
             problemAttBy.setModifiedon(savedMaster.getModifiedon());
            
            String newProblemAttByKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER_PROBLEMATTBY, KEY_LENGTH_MASTER, PREFIX_PROBLEMATTBY, DATE_FORMAT, FORMAT_RESET
            );
            problemAttBy.setKeyid(newProblemAttByKeyid);
            
            BdmTlYyproblemattbymst savedProblemAttBy = problemAttByRepository.save(problemAttBy);
            savedProblemAttByList.add(savedProblemAttBy);
        }

        result.setMaster(savedMaster);
        result.setDetails(savedDetailList);
        result.setDoneByList(savedDoneByList);
        result.setProblemAttendedByList(savedProblemAttByList);
        result.setFormActionMode(request.getFormActionMode());
        result.setFormMode(request.getFormMode());
        result.setFormHeader(request.getFormHeader());
        result.setWwmsPillarmode(request.getWwmsPillarmode());
        result.setFormType(request.getFormType());
        result.setWwmsPreveffective(request.getWwmsPreveffective());
        result.setWwmsPrevdat(request.getWwmsPrevdat());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

@Override
@Transactional
public ResponseEntity<WhyWhyRequest> saveEffectiveness(WhyWhyRequest request) throws Exception {
    String masterKeyid = null;
    
    // First priority: Check master object
    if (request.getMaster() != null && request.getMaster().getKeyid() != null) {
        masterKeyid = request.getMaster().getKeyid();
    }
    
    // Second priority: Check effectiveMaster list for wwms_keyid
    if ((masterKeyid == null || masterKeyid.trim().isEmpty() || "null".equals(masterKeyid)) && 
        request.getEffectiveMaster() != null && 
        !request.getEffectiveMaster().isEmpty()) {
        
        BdmTlYyeffectivemst firstEffective = request.getEffectiveMaster().get(0);
        if (firstEffective.getWwms_keyid() != null && 
            !firstEffective.getWwms_keyid().trim().isEmpty() &&
            !"null".equals(firstEffective.getWwms_keyid()) &&
            !"{}".equals(firstEffective.getWwms_keyid())) {
            masterKeyid = firstEffective.getWwms_keyid();
        } 
    }
    
    if (masterKeyid == null || masterKeyid.trim().isEmpty() || "null".equals(masterKeyid)) {
        throw new RuntimeException("Master Key ID is required for effectiveness");
    }

    // Try to find the master
    BdmTlWhywhymst master = null;
    try {
        master = repository.findById(masterKeyid).orElse(null);
    } catch (Exception e) {
        logger.warn("WhyWhy master not found with keyid: {}, continuing anyway", masterKeyid);
    }

    List<BdmTlYyeffectivemst> effectiveMasterList = request.getEffectiveMaster();
    List<BdmTlYyeffectivedtl> effectiveDetails = request.getEffectiveDetails();

    if (effectiveMasterList == null || effectiveMasterList.isEmpty()) {
        throw new RuntimeException("No Effectiveness Master Details");
    }

    WhyWhyRequest result = new WhyWhyRequest();
    List<BdmTlYyeffectivemst> savedEffectiveMasters = new ArrayList<>();
    List<BdmTlYyeffectivedtl> allSavedDetails = new ArrayList<>();

    // Process each effectiveness master record
    for (BdmTlYyeffectivemst effectiveMaster : effectiveMasterList) {
        effectiveMaster.setWwms_keyid(masterKeyid);
        
        // Check if this specific record has a keyid (UPDATE) or not (INSERT)
        boolean isUpdate = effectiveMaster.getKeyid() != null && 
                          !effectiveMaster.getKeyid().trim().isEmpty() &&
                          !"null".equals(effectiveMaster.getKeyid());

        if (isUpdate) {
            // UPDATE MODE - Update existing record by its keyid
            BdmTlYyeffectivemst existing = effectiveMstRepository.findById(effectiveMaster.getKeyid())
                .orElseThrow(() -> new ResourceNotFoundException("Effectiveness not found with keyid: " + effectiveMaster.getKeyid()));

            // Update all fields
            existing.setFlid(effectiveMaster.getFlid());
            existing.setRefdocid(effectiveMaster.getRefdocid());
            existing.setRefdoctype(effectiveMaster.getRefdoctype());
            existing.setEffectivedate(effectiveMaster.getEffectivedate());
            existing.setTempfield1(effectiveMaster.getTempfield1());
            existing.setTempfield2(effectiveMaster.getTempfield2());
            existing.setTempfield3(effectiveMaster.getTempfield3());
            existing.setTempfield4(effectiveMaster.getTempfield4());
            existing.setTempfield5(effectiveMaster.getTempfield5());
            existing.setTempfield6(effectiveMaster.getTempfield6());
            existing.setTempfield7(effectiveMaster.getTempfield7());
            existing.setActive(effectiveMaster.getActive());
            existing.setModifiedon(LocalDateTime.now());
            existing.setWwms_keyid(masterKeyid);

            BdmTlYyeffectivemst updatedEffective = effectiveMstRepository.save(existing);
            savedEffectiveMasters.add(updatedEffective);

            // Handle details for this updated master
            if (effectiveDetails != null && !effectiveDetails.isEmpty()) {
                // Match details by comparing their yyef_keyid with this master's keyid
                // or if yyef_keyid is empty/null/"{}", treat them as belonging to this master
                List<BdmTlYyeffectivedtl> relevantDetails = effectiveDetails.stream()
                    .filter(detail -> {
                        String detailParentId = detail.getYyefKeyid();
                        // Check if detail belongs to this master
                        return effectiveMaster.getKeyid().equals(detailParentId) ||
                               detailParentId == null || 
                               detailParentId.trim().isEmpty() ||
                               "null".equals(detailParentId) ||
                               "{}".equals(detailParentId);
                    })
                    .collect(Collectors.toList());

                for (BdmTlYyeffectivedtl detail : relevantDetails) {
                    // IMPORTANT: Set the correct parent reference
                    detail.setYyefKeyid(effectiveMaster.getKeyid());
                    
                    boolean isDetailUpdate = detail.getKeyid() != null && 
                                            !detail.getKeyid().trim().isEmpty() &&
                                            !"null".equals(detail.getKeyid());
                    
                    if (isDetailUpdate) {
                        // Update existing detail
                        BdmTlYyeffectivedtl existingDetail = effectiveDtlRepository
                            .findById(detail.getKeyid())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                "Effectiveness detail not found with keyid: " + detail.getKeyid()));
                        
                        // Update fields
                        existingDetail.setYyefKeyid(effectiveMaster.getKeyid());
                        existingDetail.setCountermesid(detail.getCountermesid());
                        existingDetail.setCountermestype(detail.getCountermestype());
                       // existingDetail.setEmpm_keyid(detail.getEmpm_keyid());
                        existingDetail.setCountermesdate(detail.getCountermesdate());
                        existingDetail.setEffectiveid(detail.getEffectiveid());
                        existingDetail.setTempfield1(detail.getTempfield1());
                        existingDetail.setTempfield2(detail.getTempfield2());
                        existingDetail.setTempfield3(detail.getTempfield3());
                        existingDetail.setTempfield4(detail.getTempfield4());
                        existingDetail.setTempfield5(detail.getTempfield5());
                        existingDetail.setTempfield6(detail.getTempfield6());
                        existingDetail.setTempfield7(detail.getTempfield7());
                        existingDetail.setActive(detail.getActive());
                        existingDetail.setModifiedon(LocalDateTime.now());
                        
                        BdmTlYyeffectivedtl savedDetail = effectiveDtlRepository.save(existingDetail);
                        allSavedDetails.add(savedDetail);
                    } else {
                        // Insert new detail
                        String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                            SEQ_IDENTIFIER_EFFECTIVE_DETAIL, KEY_LENGTH_DETAIL, 
                            PREFIX_EFFECTIVE_DETAIL, DATE_FORMAT, FORMAT_RESET
                        );
                        
                        if (newDetailKeyid == null || newDetailKeyid.trim().isEmpty()) {
                            throw new RuntimeException("Failed to generate Effectiveness Detail Key ID");
                        }
                        
                        detail.setKeyid(newDetailKeyid);
                        detail.setYyefKeyid(effectiveMaster.getKeyid());
                        detail.setCreatedon(LocalDateTime.now());
                        detail.setModifiedon(LocalDateTime.now());
                        
                        BdmTlYyeffectivedtl savedDetail = effectiveDtlRepository.save(detail);
                        allSavedDetails.add(savedDetail);
                    }
                }
            }

        } else {
            // INSERT MODE - Create new record
            String newEffectiveKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER_EFFECTIVE_MASTER, KEY_LENGTH_DETAIL, PREFIX_EFFECTIVE_MASTER, DATE_FORMAT, FORMAT_RESET
            );

            if (newEffectiveKeyid == null || newEffectiveKeyid.trim().isEmpty()) {
                throw new RuntimeException("Failed to generate Effectiveness Master Key ID");
            }

            effectiveMaster.setKeyid(newEffectiveKeyid);
            effectiveMaster.setWwms_keyid(masterKeyid);
            if (effectiveMaster.getCreatedon() == null) {
                effectiveMaster.setCreatedon(LocalDateTime.now());
            }
            effectiveMaster.setModifiedon(LocalDateTime.now());

            BdmTlYyeffectivemst savedEffective = effectiveMstRepository.save(effectiveMaster);
            savedEffectiveMasters.add(savedEffective);

            // Handle details for this new master
            if (effectiveDetails != null && !effectiveDetails.isEmpty()) {
                // Filter details that belong to this master (or have no parent yet)
                List<BdmTlYyeffectivedtl> relevantDetails = effectiveDetails.stream()
                    .filter(detail -> {
                        String detailParentId = detail.getYyefKeyid();
                        // Accept unlinked details or details explicitly linked to this master
                        return detailParentId == null || 
                               detailParentId.trim().isEmpty() ||
                               "null".equals(detailParentId) ||
                               "{}".equals(detailParentId) ||
                               newEffectiveKeyid.equals(detailParentId);
                    })
                    .collect(Collectors.toList());

                for (BdmTlYyeffectivedtl detail : relevantDetails) {
                    detail.setYyefKeyid(newEffectiveKeyid);
                    String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER_EFFECTIVE_DETAIL, KEY_LENGTH_DETAIL, PREFIX_EFFECTIVE_DETAIL, DATE_FORMAT, FORMAT_RESET
                    );

                    if (newDetailKeyid == null || newDetailKeyid.trim().isEmpty()) {
                        throw new RuntimeException("Failed to generate Effectiveness Detail Key ID");
                    }

                    detail.setKeyid(newDetailKeyid);
                    if (detail.getCreatedon() == null) {
                        detail.setCreatedon(LocalDateTime.now());
                    }
                    detail.setModifiedon(LocalDateTime.now());

                    BdmTlYyeffectivedtl savedDetail = effectiveDtlRepository.save(detail);
                    allSavedDetails.add(savedDetail);
                }
            }
        }
    }

    result.setMaster(master);
    result.setEffectiveMaster(savedEffectiveMasters);
    result.setEffectiveDetails(allSavedDetails);

    return ResponseEntity.status(savedEffectiveMasters.size() > 0 ? HttpStatus.CREATED : HttpStatus.OK).body(result);
}
    @Override
    @Transactional(readOnly = true) 
    public WhyWhyRequest getCompleteWhyWhyData(String masterKeyid) {
        logger.info("Fetching complete WhyWhy data for Master Key ID: {}", masterKeyid);

        BdmTlWhywhymst master = repository.findById(masterKeyid)
                .orElseThrow(() -> new ResourceNotFoundException("WhyWhy not found for keyid: " + masterKeyid));
        
        List<BdmTlWhywhydtl> details = detailRepository.findByWwmsKeyid(masterKeyid);
        List<BdmTlYydonebymst> doneByList = doneByRepository.findByWwmsKeyid(masterKeyid);
        List<BdmTlYyproblemattbymst> problemAttByList = problemAttByRepository.findByWwmsKeyid(masterKeyid);
        
        BdmTlYyeffectivemst effectiveMaster = null;
        List<BdmTlYyeffectivedtl> effectiveDetails = new ArrayList<>();
        
        try {
            List<BdmTlYyeffectivemst> effectiveMasters = effectiveMstRepository.findByWwms_keyid(masterKeyid);
            if (!effectiveMasters.isEmpty()) {
                effectiveMaster = effectiveMasters.get(0);
                effectiveDetails = effectiveDtlRepository.findByYyef_keyid(effectiveMaster.getKeyid());
            }
        } catch (Exception e) {
            logger.warn("No Effective Master found for WhyWhy: {}", masterKeyid);
        }
        
        WhyWhyRequest response = new WhyWhyRequest(
            master, details, effectiveMaster, effectiveDetails, doneByList, problemAttByList
        );
        
        logger.info("Successfully retrieved complete WhyWhy data for Master: {}", masterKeyid);
        return response;
    }

   @Override
@Transactional
public BdmTlWhywhymst updateApproval(BdmTlWhywhymst whywhymst) throws Exception {
    logger.info("Updating approval fields for WhyWhy keyid: {}", whywhymst.getKeyid());
    
    // Validate required fields
    if (whywhymst.getKeyid() == null || whywhymst.getKeyid().trim().isEmpty()) {
        throw new IllegalArgumentException("Key ID is required for approval update");
    }
    
    // Check if record exists
    if (!repository.existsById(whywhymst.getKeyid())) {
        throw new ResourceNotFoundException("WhyWhy record not found with keyid: " + whywhymst.getKeyid());
    }
    
    // Set current date for approvedon if not provided
    LocalDateTime approvedOnDate = whywhymst.getApprvedOn() != null 
        ? whywhymst.getApprvedOn() 
        : LocalDateTime.now();
    
    // Set current date for modifiedon
    LocalDateTime modifiedOnDate = LocalDateTime.now();
    
    // No conversion needed - use LocalDateTime directly
    
    // Set default values for null fields
    String appRoleId = whywhymst.getApprRoleid() != null ? whywhymst.getApprRoleid() : "";
    String approvedBy = whywhymst.getApprovedBy() != null ? whywhymst.getApprovedBy() : "";
   
   String appStatus = whywhymst.getAppStatus();
if (appStatus == null || appStatus.trim().isEmpty()) {
    throw new IllegalArgumentException("App status is required");
}
    String appRemarks = whywhymst.getAppRemarks() != null ? whywhymst.getAppRemarks() : "";
    
    // Execute native query update - pass LocalDateTime directly
    int rowsAffected = repository.updateApprovalFields(
        whywhymst.getKeyid(),
        appRoleId,
        approvedBy,
        approvedOnDate,      // Pass LocalDateTime directly
        appStatus,
        appRemarks,
        modifiedOnDate       // Pass LocalDateTime directly
    );
    
    if (rowsAffected == 0) {
        throw new RuntimeException("Failed to update approval fields - no rows affected");
    }
    
    logger.info("Successfully updated approval fields for keyid: {}. Rows affected: {}", 
                whywhymst.getKeyid(), rowsAffected);
    
    // Fetch and return the updated record
    return repository.findById(whywhymst.getKeyid())
        .orElseThrow(() -> new ResourceNotFoundException("Failed to retrieve updated record"));
}
@Override
@Transactional
public BdmTlWhywhymst updateApprovalAI(BdmTlWhywhymst whywhymst) throws Exception {
    logger.info("Updating approval fields for WhyWhy keyid: {}", whywhymst.getKeyid());
    
    // Validate required fields
    if (whywhymst.getKeyid() == null || whywhymst.getKeyid().trim().isEmpty()) {
        throw new IllegalArgumentException("Key ID is required for approval update");
    }
    
    // Check if record exists
    if (!repository.existsById(whywhymst.getKeyid())) {
        throw new ResourceNotFoundException("WhyWhy record not found with keyid: " + whywhymst.getKeyid());
    }
    
    // Set current date for approvedon if not provided
    LocalDateTime approvedOnDate = whywhymst.getApprvedOn() != null 
        ? whywhymst.getApprvedOn() 
        : LocalDateTime.now();
    
    // Set current date for modifiedon
    LocalDateTime modifiedOnDate = LocalDateTime.now();
    
    // No conversion needed - use LocalDateTime directly
    
    // Set default values for null fields
    String appRoleId = whywhymst.getApprRoleid() != null ? whywhymst.getApprRoleid() : "";
    String approvedBy = whywhymst.getApprovedBy() != null ? whywhymst.getApprovedBy() : "";
   
   String appStatus = whywhymst.getAppStatus();
   Character iscobd = whywhymst.getIscobd();
   BigDecimal cobdvalue = whywhymst.getCobdvalue();
   BigDecimal cobdhours = whywhymst.getCobdhours();
if (appStatus == null || appStatus.trim().isEmpty()) {
    throw new IllegalArgumentException("App status is required");
}
    String appRemarks = whywhymst.getAppRemarks() != null ? whywhymst.getAppRemarks() : "";
    
    // Execute native query update - pass LocalDateTime directly
    int rowsAffected = repository.updateApprovalAIFields(
        whywhymst.getKeyid(),
        appRoleId,
        approvedBy,
        approvedOnDate,      // Pass LocalDateTime directly
        appStatus,
        appRemarks,
        modifiedOnDate ,      // Pass LocalDateTime directly
        iscobd,
        cobdvalue,
        cobdhours
    );
    
    if (rowsAffected == 0) {
        throw new RuntimeException("Failed to update approval fields - no rows affected");
    }
    
    logger.info("Successfully updated approval fields for keyid: {}. Rows affected: {}", 
                whywhymst.getKeyid(), rowsAffected);
    
    // Fetch and return the updated record
    return repository.findById(whywhymst.getKeyid())
        .orElseThrow(() -> new ResourceNotFoundException("Failed to retrieve updated record"));
}
 @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getSpentTime(String keyId) throws Exception {
        logger.info("Fetching spent time for WhyWhy Key ID: {}", keyId);
        
        if (keyId == null || keyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Key ID cannot be null or empty");
        }
        
        List<Map<String, Object>> result = repository.getSpentTimeByKeyId(keyId);
        
        if (result == null || result.isEmpty()) {
            logger.warn("No spent time data found for Key ID: {}", keyId);
            throw new ResourceNotFoundException("No data found for keyId: " + keyId);
        }
        
        logger.info("Successfully retrieved spent time data for Key ID: {}", keyId);
        return result;
    }

    @Override
public List<Map<String, Object>> getYyDoneby(String masterKeyid) throws Exception {
    logger.info("Fetching YY Done By data for Master Key ID: {}", masterKeyid);
    
    // if (masterKeyid == null || masterKeyid.isBlank()) {
    //     logger.info("Master Key ID is empty – fetching all YY Done By records");
    //     return doneByRepository.getYyDonebyByMasterKeyid();
    // }
    logger.info("Master Key ID is empty – fetching all YY Done By records {} ",masterKeyid);
    List<Map<String, Object>> result =
            doneByRepository.getYyDonebyByMasterKeyid(masterKeyid);

    if (result.isEmpty()) {
        logger.warn("No YY Done By data found for Master Key ID: {}", masterKeyid);
    }

    logger.info("Successfully retrieved {} YY Done By records for Master Key ID: {}",
            result.size(), masterKeyid);

    return result;
}

@Override
public List<Map<String, Object>> getProbAttby(String masterKeyid) throws Exception {
    logger.info("Fetching Problem Attended By data for Master Key ID: {}", masterKeyid);
    
    logger.info("Master Key ID provided: {}", masterKeyid);
    
    List<Map<String, Object>> result = 
            problemAttByRepository.getProbAttbyByMasterKeyid(masterKeyid);
    
    if (result.isEmpty()) {
        logger.warn("No Problem Attended By data found for Master Key ID: {}", masterKeyid);
    }
    
    logger.info("Successfully retrieved {} Problem Attended By records for Master Key ID: {}", 
                result.size(), masterKeyid);
    
    return result;
}
@Override

public List<Map<String, Object>> getAnalysis(String masdetkeyid) throws Exception {
    logger.info("Fetching analysis data for Master Key ID: {}", masdetkeyid);
    
   // if (masdetkeyid == null || masdetkeyid.trim().isEmpty()) {
       // throw new IllegalArgumentException("Master Key ID cannot be null or empty");
   // }
    
    List<Map<String, Object>> result = detailRepository.getAnalysisData(masdetkeyid);
    
    if (result == null || result.isEmpty()) {
        logger.warn("No analysis data found for Master Key ID: {}", masdetkeyid);
        // Return header row only if no data found
        return result;
    }
    
    logger.info("Successfully retrieved {} analysis records for Master Key ID: {}", 
                result.size(), masdetkeyid);
    return result;
}
@Override
@Transactional
public boolean deleteWhyWhyDetail(String detailId) throws Exception {
    logger.info("Deleting WhyWhy detail with keyid: {}", detailId);
    
    if (detailId == null || detailId.trim().isEmpty()) {
        throw new IllegalArgumentException("Detail ID cannot be null or empty");
    }
    
    // Check if detail exists
    if (!detailRepository.existsById(detailId)) {
        logger.warn("WhyWhy detail not found with keyid: {}", detailId);
        throw new ResourceNotFoundException("WhyWhy detail not found with keyid: " + detailId);
    }
    
    int rowsAffected = detailRepository.deleteWhyWhyDetail(detailId);
    
    if (rowsAffected == 0) {
        logger.error("Failed to delete WhyWhy detail with keyid: {}", detailId);
        throw new RuntimeException("Failed to delete WhyWhy detail");
    }
    
    logger.info("Successfully deleted WhyWhy detail with keyid: {}. Rows affected: {}", detailId, rowsAffected);
    return true;
}
// Add this method to WhyWhyAnalysisServiceImpl class

@Override
@Transactional
public boolean deleteProblemAttBy(String keyId) throws Exception {
    logger.info("Deleting Problem Attended By record with keyid: {}", keyId);
    
    if (keyId == null || keyId.trim().isEmpty()) {
        throw new IllegalArgumentException("Key ID cannot be null or empty");
    }
    
    // Check if record exists
    if (!problemAttByRepository.existsById(keyId)) {
        logger.warn("Problem Attended By record not found with keyid: {}", keyId);
        throw new ResourceNotFoundException("Problem Attended By record not found with keyid: " + keyId);
    }
    
    int rowsAffected = problemAttByRepository.deleteProblemAttBy(keyId);
    
    if (rowsAffected == 0) {
        logger.error("Failed to delete Problem Attended By record with keyid: {}", keyId);
        throw new RuntimeException("Failed to delete Problem Attended By record");
    }
    
    logger.info("Successfully deleted Problem Attended By record with keyid: {}. Rows affected: {}", keyId, rowsAffected);
    return true;
}
@Override
@Transactional
public boolean deleteYyDoneby(String keyId) throws Exception {
    logger.info("Deleting YY Done By record with keyid: {}", keyId);
    
    if (keyId == null || keyId.trim().isEmpty()) {
        throw new IllegalArgumentException("Key ID cannot be null or empty");
    }
    
    // Check if record exists
    if (!doneByRepository.existsById(keyId)) {
        logger.warn("YY Done By record not found with keyid: {}", keyId);
        throw new ResourceNotFoundException("YY Done By record not found with keyid: " + keyId);
    }
    
    int rowsAffected = doneByRepository.deleteYyDoneby(keyId);
    
    if (rowsAffected == 0) {
        logger.error("Failed to delete YY Done By record with keyid: {}", keyId);
        throw new RuntimeException("Failed to delete YY Done By record");
    }
    
    logger.info("Successfully deleted YY Done By record with keyid: {}. Rows affected: {}", keyId, rowsAffected);
    return true;
}

@Override
@Transactional(readOnly = true)
public List<Map<String, Object>> getRootCause(String openMode) throws Exception {
    logger.info("Fetching root cause data for openMode: {}", openMode);
    
    // Handle null or empty openMode
    String mode = (openMode == null || openMode.trim().isEmpty()) ? "BDM" : openMode;
    
    List<Map<String, Object>> result = repository.getRootCauseBySql(mode);
    
    if (result == null || result.isEmpty()) {
        logger.warn("No root cause data found for openMode: {}", mode);
        return new ArrayList<>();
    }
    
    logger.info("Successfully retrieved {} root cause records for openMode: {}", 
                result.size(), mode);
    return result;
}

@Override
@Transactional(readOnly = true)
public List<Map<String, Object>> getCounterMeasure(String yyno) throws Exception {
    logger.info("Fetching counter measure data for YY No: {}", yyno);
    
    if (yyno == null || yyno.trim().isEmpty()) {
        throw new IllegalArgumentException("YY No cannot be null or empty");
    }
    
    List<Map<String, Object>> result = repository.getCounterMeasureData(yyno);
    
    if (result == null || result.isEmpty()) {
        logger.warn("No counter measure data found for YY No: {}", yyno);
        // Return empty list or header row as needed
        return result;
    }
    
    logger.info("Successfully retrieved {} counter measure records for YY No: {}", 
                result.size(), yyno);
    return result;
}
}