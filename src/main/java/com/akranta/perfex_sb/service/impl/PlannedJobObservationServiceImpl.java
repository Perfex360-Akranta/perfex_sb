package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.PlnjobemplinkRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.SheTlPlannedJobObservation;
import com.akranta.perfex_sb.model.SheTlPlnjobemplink;
import com.akranta.perfex_sb.repository.SheTlPlannedJobObservationRepository;
import com.akranta.perfex_sb.repository.SheTlPlnjobemplinkRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.PlannedJobObservationService;

@Service
public class PlannedJobObservationServiceImpl implements PlannedJobObservationService {  // ← updated here

    private static final Logger logger = LoggerFactory.getLogger(PlannedJobObservationServiceImpl.class);

    private final SheTlPlannedJobObservationRepository masterRepository;
    private final SheTlPlnjobemplinkRepository detailRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER_MASTER = "SHE_TL_PLANNEDJOBOBSERVATION";
    private static final String SEQ_IDENTIFIER_DETAIL = "SHE_TL_PLNJOBEMPLINK";

    private static final int KEY_LENGTH_MASTER = 15;
    private static final int KEY_LENGTH_DETAIL = 15;

    private static final String PREFIX_MASTER = "PJOB";
    private static final String PREFIX_DETAIL = "PJEL";

    private static final String DATE_FORMAT = "YY";

    private static final String FORMAT_RESET = "Y";

    public PlannedJobObservationServiceImpl(
            SheTlPlannedJobObservationRepository masterRepository,
            SheTlPlnjobemplinkRepository detailRepository,
            DbActionTemplate dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

 @Override
@Transactional
public ResponseEntity<PlnjobemplinkRequest> savePlnjobemplink(PlnjobemplinkRequest request) throws Exception {

    SheTlPlannedJobObservation master = request.getMaster();
    List<SheTlPlnjobemplink> details = request.getDetails();

    if (master == null) {
        throw new RuntimeException("No Planned Job Observation Master Details");
    }

    PlnjobemplinkRequest result = new PlnjobemplinkRequest();

    // ========== INSERT MODE ==========
    if (master.getKeyid() == null || master.getKeyid().trim().isEmpty()) {

        String newMstKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER_MASTER, KEY_LENGTH_MASTER, PREFIX_MASTER, DATE_FORMAT, FORMAT_RESET);

        if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
            logger.error("Failed to generate Master Key ID");
            throw new RuntimeException("Failed to generate Master Key ID");
        }

        master.setKeyid(newMstKeyid);
        if (master.getCreatedon() == null) {
            master.setCreatedon(LocalDateTime.now());
        }
        master.setModifiedon(LocalDateTime.now());

        logger.info("Generated new Master Key ID: {}", newMstKeyid);

        SheTlPlannedJobObservation savedMaster = masterRepository.save(master);
        List<SheTlPlnjobemplink> savedDetailList = new ArrayList<>();

        if (details != null && !details.isEmpty()) {
            for (SheTlPlnjobemplink detail : details) {
                detail.setPjob_keyid(savedMaster.getKeyid());

                String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET);

                if (newDetailKeyid == null || newDetailKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate Detail Key ID");
                    throw new RuntimeException("Failed to generate Detail Key ID");
                }

                detail.setKeyid(newDetailKeyid);

                if (detail.getCreatedby() == null || detail.getCreatedby().trim().isEmpty()) {
                    detail.setCreatedby(savedMaster.getCreatedby());
                }
                if (detail.getCreatedon() == null) {
                    detail.setCreatedon(LocalDateTime.now());
                }
                detail.setModifiedon(LocalDateTime.now());

                SheTlPlnjobemplink savedDetail = detailRepository.save(detail);
                savedDetailList.add(savedDetail);
                logger.info("Successfully created Detail with Key: {}", newDetailKeyid);
            }
        }
        
        result.setMaster(savedMaster);
        result.setDetails(savedDetailList);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    } else {
        // ========== UPDATE MODE ==========
        SheTlPlannedJobObservation existingMaster = masterRepository.findByKeyid(master.getKeyid());

        if (existingMaster == null) {
            throw new ResourceNotFoundException(
                    "Planned Job Observation not found with keyid: " + master.getKeyid());
        }

        existingMaster.setFlid(master.getFlid());
        existingMaster.setElementid(master.getElementid());
        existingMaster.setEmployeeid(master.getEmployeeid());
        existingMaster.setDate(master.getDate());
        existingMaster.setLocation_description(master.getLocation_description());
        existingMaster.setTask_observed(master.getTask_observed());
        existingMaster.setType(master.getType());
        existingMaster.setNotification(master.getNotification());
        existingMaster.setReasonid(master.getReasonid());
        existingMaster.setIs_property_damage(master.getIs_property_damage());
        existingMaster.setIs_efficient(master.getIs_efficient());
        existingMaster.setIs_comply(master.getIs_comply());
        existingMaster.setIs_detrimental(master.getIs_detrimental());
        existingMaster.setCorrections(master.getCorrections());
        existingMaster.setDeviations(master.getDeviations());
        existingMaster.setIs_complemented(master.getIs_complemented());
        existingMaster.setIs_follow_up(master.getIs_follow_up());
        existingMaster.setStandard_procedure(master.getStandard_procedure());
        existingMaster.setManager(master.getManager());
        existingMaster.setFollow_up_action(master.getFollow_up_action());
        existingMaster.setFollow_up_date(master.getFollow_up_date());
        existingMaster.setFollow_up_done(master.getFollow_up_done());
        existingMaster.setPracticecond(master.getPracticecond());
        existingMaster.setFollowuppjono(master.getFollowuppjono());
        existingMaster.setFolowuptaskobserved(master.getFolowuptaskobserved());
        existingMaster.setSopno(master.getSopno());
        existingMaster.setTempfield5(master.getTempfield5());
        existingMaster.setActive(master.getActive());
        existingMaster.setActionplan_id(master.getActionplan_id());
        existingMaster.setModifiedon(LocalDateTime.now());

        SheTlPlannedJobObservation updatedMaster = masterRepository.save(existingMaster);
        logger.info("Successfully updated Master with Key ID: {}", updatedMaster.getKeyid());

        List<SheTlPlnjobemplink> resultDetails = new ArrayList<>();

        if (details != null && !details.isEmpty()) {
            for (SheTlPlnjobemplink detail : details) {

                boolean isNewDetail = detail.getKeyid() == null
                        || detail.getKeyid().trim().isEmpty()
                        || detail.getKeyid().equals("undefined");

                if (isNewDetail) {
                    // Check if a record already exists for this pjob + employee combination
                    Optional<SheTlPlnjobemplink> existingByCompositeKey = detailRepository
                            .findByPjobKeyidAndEmployeeId(master.getKeyid(), detail.getPjob_employeeid());

                    if (existingByCompositeKey.isPresent()) {
                        // ---- UPDATE the existing record instead of inserting ----
                        SheTlPlnjobemplink existingDetail = existingByCompositeKey.get();

                        existingDetail.setPjob_employeeid(detail.getPjob_employeeid());
                        existingDetail.setTempfield1(detail.getTempfield1());
                        existingDetail.setTempfield2(detail.getTempfield2());
                        existingDetail.setTempfield3(detail.getTempfield3());
                        existingDetail.setTempfield4(detail.getTempfield4());
                        existingDetail.setActive(detail.getActive());
                        existingDetail.setModifiedon(LocalDateTime.now());

                        SheTlPlnjobemplink savedDetail = detailRepository.save(existingDetail);
                        resultDetails.add(savedDetail);
                        logger.info("Updated existing Detail (by composite key) with Key: {}", existingDetail.getKeyid());

                    } else {
                        // ---- INSERT genuinely new record ----
                        detail.setPjob_keyid(master.getKeyid());

                        String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                                SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET);

                        if (newDetailKeyid == null || newDetailKeyid.trim().isEmpty()) {
                            logger.error("Failed to generate Detail Key ID");
                            throw new RuntimeException("Failed to generate Detail Key ID");
                        }

                        detail.setKeyid(newDetailKeyid);

                        if (detail.getCreatedby() == null || detail.getCreatedby().trim().isEmpty()) {
                            detail.setCreatedby(master.getCreatedby());
                        }
                        detail.setCreatedon(LocalDateTime.now());
                        detail.setModifiedon(LocalDateTime.now());

                        SheTlPlnjobemplink savedDetail = detailRepository.save(detail);
                        resultDetails.add(savedDetail);
                        logger.info("Successfully created new Detail with Key: {}", newDetailKeyid);
                    }

                } else {
                    // ---- keyid is present — standard update by primary key ----
                    SheTlPlnjobemplink existingDetail = detailRepository.findById(detail.getKeyid())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Plnjobemplink detail not found for keyid: " + detail.getKeyid()));

                    existingDetail.setPjob_employeeid(detail.getPjob_employeeid());
                    existingDetail.setTempfield1(detail.getTempfield1());
                    existingDetail.setTempfield2(detail.getTempfield2());
                    existingDetail.setTempfield3(detail.getTempfield3());
                    existingDetail.setTempfield4(detail.getTempfield4());
                    existingDetail.setActive(detail.getActive());
                    existingDetail.setModifiedon(LocalDateTime.now());

                    SheTlPlnjobemplink savedDetail = detailRepository.save(existingDetail);
                    resultDetails.add(savedDetail);
                    logger.info("Successfully updated Detail with Key: {}", detail.getKeyid());
                }
            }
        }

        result.setMaster(updatedMaster);
        result.setDetails(resultDetails);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

    
    // ========== FIND BY KEYID ==========
    @Override
    public SheTlPlannedJobObservation findPlannedJobObservationByKeyid(String keyid) throws Exception {
        try {
            SheTlPlannedJobObservation result = masterRepository.findByKeyid(keyid);
            if (result == null) {
                throw new ResourceNotFoundException("No record found for keyid: " + keyid);
            }
            logger.info("Successfully fetched PlannedJobObservation with keyid: {}", keyid);
            return result;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error fetching PlannedJobObservation with keyid: {}", keyid, e);
            throw new Exception("Error fetching PlannedJobObservation with keyid: " + keyid, e);
        }
    }


    
   @Override
public List<Map<String, Object>> getPojEmployee(String pjobKeyId) throws Exception {
    try {
        List<Map<String, Object>> result = detailRepository.getPojEmployee(pjobKeyId);
        logger.info("Successfully fetched employee list for pjobKeyId: {}", pjobKeyId);
        return result;
    } catch (Exception e) {
        logger.error("Error fetching employee list for pjobKeyId: {}", pjobKeyId, e);
        throw new Exception("Error fetching employee list for pjobKeyId: " + pjobKeyId, e);
    }
}


@Override
public int updateActionPlan(String pojKeyid, String aplmKeyid) throws Exception {
    try {
        int rowsAffected = ((PlannedJobObservationService) masterRepository).updateActionPlan(pojKeyid, aplmKeyid);
        if (rowsAffected == 0) {
            throw new ResourceNotFoundException("No record found for pojKeyid: " + pojKeyid);
        }
        logger.info("Successfully updated ActionPlan for pojKeyid: {} with aplmKeyid: {}", pojKeyid, aplmKeyid);
        return rowsAffected;
    } catch (ResourceNotFoundException e) {
        throw e;
    } catch (Exception e) {
        logger.error("Error updating ActionPlan for pojKeyid: {} with aplmKeyid: {}", pojKeyid, aplmKeyid, e);
        throw new Exception("Error updating ActionPlan for pojKeyid: " + pojKeyid, e);
    }
}



// ✅ AFTER (correct order + correct method names):
@Override
@Transactional(rollbackFor = Exception.class)
public boolean deletePlannedJobObservation(String keyid) throws Exception {
    logger.info("Deleting PlannedJobObservation record with keyid: {}", keyid);

    if (keyid == null || keyid.trim().isEmpty()) {
        throw new IllegalArgumentException("Keyid cannot be null or empty");
    }

    SheTlPlannedJobObservation existingRecord = masterRepository.findByKeyid(keyid);
    if (existingRecord == null) {
        throw new ResourceNotFoundException(
            "PlannedJobObservation not found with keyid: " + keyid);
    }

    // ✅ Step 1: Delete DETAIL records first (child before parent)
    int dtlRowsAffected = detailRepository.deleteByPjobKeyid(keyid);
    logger.info("Deleted {} detail records for keyid: {}", dtlRowsAffected, keyid);

    // ✅ Step 2: Delete MASTER record
    int mstRowsAffected = masterRepository.deleteByPjobKeyid(keyid);
    logger.info("Deleted {} master records for keyid: {}", mstRowsAffected, keyid);

    if (mstRowsAffected == 0) {
        throw new RuntimeException(
            "Failed to delete master record for keyid: " + keyid);
    }

    logger.info("Successfully deleted PlannedJobObservation for keyid: {}", keyid);
    return true;
}





//DELETE employee id:

@Override
@Transactional(rollbackFor = Exception.class)
public String deleteDetail(String pjelPjobEmployeeid, String gridId) throws Exception {
    logger.info("Deleting PLNJOBEMPLINK detail record with pjelPjobEmployeeid: {}", pjelPjobEmployeeid);

    if (pjelPjobEmployeeid == null || pjelPjobEmployeeid.trim().isEmpty()) {
        throw new IllegalArgumentException("pjelPjobEmployeeid cannot be null or empty");
    }

    logger.info("gridId: {}", gridId);

    int rowsAffected = detailRepository.deleteByPjelPjobEmployeeid(pjelPjobEmployeeid);
    logger.info("Deleted {} detail records for pjelPjobEmployeeid: {}", rowsAffected, pjelPjobEmployeeid);

    if (rowsAffected == 0) {
        logger.warn("No records found to delete for pjelPjobEmployeeid: {}", pjelPjobEmployeeid);
        return "Data Not Deleted";
    }

    logger.info("Successfully deleted PLNJOBEMPLINK detail for pjelPjobEmployeeid: {}", pjelPjobEmployeeid);
    return "Data Deleted Successfully";
}





// ========== DELETE SINGLE EMPLOYEE LINK (Grid Row Delete) ==========
//@Override
//@Transactional(rollbackFor = Exception.class)
// public String deleteDetail(String pjelPjobEmployeeid, String gridId) throws Exception {
//     logger.info("Deleting employee link | pjelPjobEmployeeid: {} | gridId: {}",
//                 pjelPjobEmployeeid, gridId);

//     if (pjelPjobEmployeeid == null
//             || pjelPjobEmployeeid.trim().isEmpty()
//             || pjelPjobEmployeeid.equalsIgnoreCase("false")
//             || pjelPjobEmployeeid.equalsIgnoreCase("undefined")
//             || pjelPjobEmployeeid.equalsIgnoreCase("null")) {

//         logger.warn("Invalid pjelPjobEmployeeid received: {}", pjelPjobEmployeeid);
//         throw new IllegalArgumentException(
//             "Invalid pjelPjobEmployeeid received: '" + pjelPjobEmployeeid + "'. " +
//             "Please pass the actual employee ID.");
//     }

//     // ✅ Check if record exists
//     Optional<SheTlPlnjobemplink> existing = detailRepository
//             .findByPjelPjobEmployeeid(pjelPjobEmployeeid);

//     if (existing.isEmpty()) {
//         throw new ResourceNotFoundException(
//                 "Employee link not found for pjelPjobEmployeeid: " + pjelPjobEmployeeid);
//     }                                          // ← only ONE } here, no extra brace

//     // ✅ Delete
//     int rowsDeleted = detailRepository.deleteByPjelPjobEmployeeid(pjelPjobEmployeeid);
//     logger.info("Deleted {} employee link record(s) for pjelPjobEmployeeid: {}",
//                 rowsDeleted, pjelPjobEmployeeid);

//     if (rowsDeleted == 0) {
//         throw new RuntimeException(
//                 "Failed to delete employee link for pjelPjobEmployeeid: " + pjelPjobEmployeeid);
//     }

//     logger.info("Successfully deleted employee link for pjelPjobEmployeeid: {}",
//                 pjelPjobEmployeeid);
//     return "Data Deleted Successfully";
// }
}