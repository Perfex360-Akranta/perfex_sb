package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.PlantSafetyRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.SheTlPlantSafetydtl;
import com.akranta.perfex_sb.model.SheTlPlantSafetymst;
import com.akranta.perfex_sb.repository.SheTlPlantSafetydtlRepository;
import com.akranta.perfex_sb.repository.SheTlPlantSafetymstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.PlantSafetyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Map;


@Service
public class PlantSafetyServiceImpl implements PlantSafetyService {

    private static final Logger logger = LoggerFactory.getLogger(PlantSafetyServiceImpl.class);

    private final SheTlPlantSafetymstRepository masterRepository;
    private final SheTlPlantSafetydtlRepository detailRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER_MASTER = "SHE_TL_PLANTSAFETYMST";
    private static final String SEQ_IDENTIFIER_DETAIL = "SHE_TL_PLANTSAFETYDTL";

    private static final int    KEY_LENGTH_MASTER = 18;
    private static final int    KEY_LENGTH_DETAIL = 12;

    private static final String PREFIX_MASTER = "SPSM";
    private static final String PREFIX_DETAIL = "SPSD";

    private static final String DATE_FORMAT   = "YY";
    private static final String FORMAT_RESET  = "Y";

    public PlantSafetyServiceImpl(
            SheTlPlantSafetymstRepository masterRepository,
            SheTlPlantSafetydtlRepository detailRepository,
            DbActionTemplate dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<PlantSafetyRequest> savePlantSafety(PlantSafetyRequest request) throws Exception {

        SheTlPlantSafetymst master = request.getMaster();
        List<SheTlPlantSafetydtl> details = request.getDetails();

        if (master == null) {
            throw new IllegalArgumentException("Plant Safety master details cannot be null");
        }

        PlantSafetyRequest result = new PlantSafetyRequest();

        // ─────────────────────────────────────────────
        // INSERT
        // ─────────────────────────────────────────────
        if (master.getKeyid() == null || master.getKeyid().trim().isEmpty()) {

            String newMstKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER_MASTER, KEY_LENGTH_MASTER, PREFIX_MASTER, DATE_FORMAT, FORMAT_RESET);

            if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                throw new RuntimeException("Failed to generate Master Key ID");
            }

            master.setKeyid(newMstKeyid);
            if (master.getCreatedOn() == null) {
                master.setCreatedOn(LocalDateTime.now());
            }
            master.setModifiedOn(LocalDateTime.now());

            logger.info("Generated new Master Key ID: {}", newMstKeyid);

            SheTlPlantSafetymst savedMaster = masterRepository.save(master);
            List<SheTlPlantSafetydtl> savedDetails = new ArrayList<>();

            if (details != null && !details.isEmpty()) {
                for (SheTlPlantSafetydtl detail : details) {

                    String newDtlKeyid = dbActionTemplate.getSequenceNumber(
                            SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET);

                    if (newDtlKeyid == null || newDtlKeyid.trim().isEmpty()) {
                        throw new RuntimeException("Failed to generate Detail Key ID");
                    }

                    detail.setKeyid(newDtlKeyid);
                    detail.setSpsd_keyid(savedMaster.getKeyid());   // FK -> master

                    if (detail.getCreatedBy() == null || detail.getCreatedBy().trim().isEmpty()) {
                        detail.setCreatedBy(savedMaster.getCreatedby());
                    }
                    if (detail.getCreatedOn() == null) {
                        detail.setCreatedOn(LocalDateTime.now());
                    }
                    detail.setModifiedOn(LocalDateTime.now());

                    savedDetails.add(detailRepository.save(detail));
                    logger.info("Inserted Detail Key: {}", newDtlKeyid);
                }
            }

            result.setMaster(savedMaster);
            result.setDetails(savedDetails);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }

        // ─────────────────────────────────────────────
        // UPDATE
        // ─────────────────────────────────────────────
        SheTlPlantSafetymst existingMaster = masterRepository.findByKeyid(master.getKeyid());
        if (existingMaster == null) {
            throw new ResourceNotFoundException(
                    "Plant Safety record not found with keyid: " + master.getKeyid());
        }

        // Update master fields
        existingMaster.setFlid(master.getFlid());
        existingMaster.setSafetydate(master.getSafetydate());
        existingMaster.setDoneby(master.getDoneby());
        existingMaster.setSafetytype(master.getSafetytype());
        existingMaster.setMembers(master.getMembers());
        existingMaster.setRemarks(master.getRemarks());
        existingMaster.setTempfield1(master.getTempfield1());
        existingMaster.setTempfield2(master.getTempfield2());
        existingMaster.setTempfield3(master.getTempfield3());
        existingMaster.setTempfield4(master.getTempfield4());
        existingMaster.setTempfield5(master.getTempfield5());
        existingMaster.setActive(master.getActive());
        existingMaster.setModifiedOn(LocalDateTime.now());

        SheTlPlantSafetymst updatedMaster = masterRepository.save(existingMaster);
        logger.info("Updated Master Key ID: {}", updatedMaster.getKeyid());

        List<SheTlPlantSafetydtl> resultDetails = new ArrayList<>();

        if (details != null && !details.isEmpty()) {
            for (SheTlPlantSafetydtl detail : details) {

                boolean isNew = detail.getKeyid() == null
                        || detail.getKeyid().trim().isEmpty()
                        || detail.getKeyid().equals("undefined");

                if (isNew) {
                    // New detail row -> INSERT
                    String newDtlKeyid = dbActionTemplate.getSequenceNumber(
                            SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET);

                    if (newDtlKeyid == null || newDtlKeyid.trim().isEmpty()) {
                        throw new RuntimeException("Failed to generate Detail Key ID");
                    }

                    detail.setKeyid(newDtlKeyid);
                    detail.setSpsd_keyid(updatedMaster.getKeyid());

                    if (detail.getCreatedBy() == null || detail.getCreatedBy().trim().isEmpty()) {
                        detail.setCreatedBy(updatedMaster.getCreatedby());
                    }
                    detail.setCreatedOn(LocalDateTime.now());
                    detail.setModifiedOn(LocalDateTime.now());

                    resultDetails.add(detailRepository.save(detail));
                    logger.info("Inserted new Detail Key (during update): {}", newDtlKeyid);

                } else {
                    // Existing detail row -> UPDATE
                    SheTlPlantSafetydtl existing = detailRepository.findById(detail.getKeyid())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Detail not found for keyid: " + detail.getKeyid()));

                    existing.setObserve(detail.getObserve());
                    existing.setType(detail.getType());
                    existing.setRelatedto(detail.getRelatedto());
                    existing.setRecomaction(detail.getRecomaction());
                    existing.setCorrecaction(detail.getCorrecaction());
                    existing.setPreventiveaction(detail.getPreventiveaction());
                    existing.setResponsibilty(detail.getResponsibilty());
                    existing.setTargetdate(detail.getTargetdate());
                    existing.setReponseremarks(detail.getReponseremarks());
                    existing.setStatus(detail.getStatus());
                    existing.setActiontaken(detail.getActiontaken());
                    existing.setCompletedby(detail.getCompletedby());
                    existing.setCompletedate(detail.getCompletedate());
                    existing.setCompleteremarks(detail.getCompleteremarks());
                    existing.setVerified(detail.getVerified());
                    existing.setVerifiedby(detail.getVerifiedby());
                    existing.setVerifieddate(detail.getVerifieddate());
                    existing.setVerifeidremarks(detail.getVerifeidremarks());
                    existing.setTempfield1(detail.getTempfield1());
                    existing.setTempfield2(detail.getTempfield2());
                    existing.setTempfield3(detail.getTempfield3());
                    existing.setTempfield4(detail.getTempfield4());
                    existing.setTempfield5(detail.getTempfield5());
                    existing.setActive(detail.getActive());
                    existing.setModifiedOn(LocalDateTime.now());

                    resultDetails.add(detailRepository.save(existing));
                    logger.info("Updated Detail Key: {}", detail.getKeyid());
                }
            }
        }

        result.setMaster(updatedMaster);
        result.setDetails(resultDetails);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @Override
public ResponseEntity<SheTlPlantSafetymst> getPlantSafetyByKeyid(String keyid) {
    SheTlPlantSafetymst master = masterRepository.findByKeyid(keyid);
    if (master == null) {
        throw new ResourceNotFoundException("Plant Safety record not found with keyid: " + keyid);
    }
    return ResponseEntity.ok(master);
}
@Override
public String getDetailValue(String keyid) {
    return masterRepository.findSpsdKeyidBySpsmKeyid(keyid);
}
@Override
public List<Map<String, Object>> getDetailBySpsmKeyid(String keyid) {
    return detailRepository.findBySpsmKeyid(keyid);
}
@Override
public String getResponse(String modifiedid) {
    return detailRepository.findResponsibilityByKeyid(modifiedid);
}

@Override
public String getObser(String modifiedid) {
    return detailRepository.findObservationsByKeyid(modifiedid);
}

@Override
public String getMaster(String modifiedid) {
    return detailRepository.findMasterKeyidByKeyid(modifiedid);
}

@Override
public String getResponseRemarks(String modifiedid) {
    return detailRepository.findRespRemarksByKeyid(modifiedid);
}
@Override
@Transactional
public String singleResponsiblty(String detailkeyid, String responsevalue, String targetdate,
        SheTlPlantSafetymst sheTlPlantSafetymst, SheTlPlantSafetydtl sheTlPlantSafetydtl) {

    System.out.println("PSD000000671");

    if (sheTlPlantSafetydtl.getReponseremarks() == null 
            || sheTlPlantSafetydtl.getReponseremarks().length() == 0) {
        sheTlPlantSafetydtl.setReponseremarks("-");
    }

    if (detailkeyid != null && detailkeyid.length() != 0) {

        // ✅ Convert String to LocalDateTime (handles both "4-Mar-2026" and "04-Mar-2026")
        LocalDateTime parsedDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
            parsedDate = LocalDate.parse(targetdate, formatter).atStartOfDay();
        } catch (Exception e) {
            logger.warn("Date parse failed, using current time. Raw value: {}", targetdate);
            parsedDate = LocalDateTime.now();
        }

        detailRepository.updateResponsibility(
                detailkeyid,
                responsevalue,
                parsedDate,  // ✅ LocalDateTime instead of String
                sheTlPlantSafetydtl.getReponseremarks()
        );
        System.out.println("Updated responsibility for keyid: " + detailkeyid);
    }

    return "SUCCESS";
}

@Override
@Transactional
public String verifyUpdate(String dtlid, String verid, String verifydate) {

    LocalDateTime parsedDate;
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        parsedDate = LocalDate.parse(verifydate, formatter).atStartOfDay();
    } catch (Exception e) {
        logger.warn("Date parse failed, using current time. Raw value: {}", verifydate);
        parsedDate = LocalDateTime.now();
    }

    detailRepository.updateVerify(dtlid, verid, parsedDate);
    logger.info("Updated verify fields for keyid: {}", dtlid);

    return "SUCCESS";
}

@Override
@Transactional
public String resUpdate(String dtlid, String resid, String targetdate) {

    LocalDateTime parsedDate;
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        parsedDate = LocalDate.parse(targetdate, formatter).atStartOfDay();
    } catch (Exception e) {
        logger.warn("Date parse failed, using current time. Raw value: {}", targetdate);
        parsedDate = LocalDateTime.now();
    }

    detailRepository.resUpdate(dtlid, resid, parsedDate);
    logger.info("Updated responsibility for keyid: {}", dtlid);

    return "SUCCESS";
}

@Override
@Transactional
public String checkDeleteList(String detailkeyid, String apmdelete) {

    // Step 1: Delete plant safety detail rows
    if (detailkeyid != null && !detailkeyid.trim().isEmpty()) {
        String[] ids = detailkeyid.replaceAll("[\"']", "").split(",");
        for (String id : ids) {
            String trimmed = id.trim();
            if (!trimmed.isEmpty()) {
                detailRepository.deleteByDetailKeyid(trimmed);
                logger.info("Deleted PlantSafety detail for keyid: {}", trimmed);
            }
        }
    }

    // Step 2: Delete specific action plan detail row(s) by apld_keyid
    if (apmdelete != null && !apmdelete.trim().isEmpty()) {
        String[] apmIds = apmdelete.replaceAll("[\"']", "").split(",");
        for (String id : apmIds) {
            String trimmed = id.trim();
            if (!trimmed.isEmpty()) {
                detailRepository.deleteByApldKeyid(trimmed);
                logger.info("Deleted ActionPlan detail for keyid: {}", trimmed);
            }
        }
    }

    // Step 3: Delete ALL remaining child rows of the master, then the master itself
    if (detailkeyid != null && !detailkeyid.trim().isEmpty()) {
        String[] refIds = detailkeyid.replaceAll("[\"']", "").split(",");
        for (String id : refIds) {
            String trimmed = id.trim();
            if (!trimmed.isEmpty()) {
                // Must delete ALL children before deleting master
                detailRepository.deleteActionPlanDtlByDetailRefid(trimmed);
                logger.info("Deleted remaining ActionPlan details for detailrefid: {}", trimmed);

                detailRepository.deleteByDetailRefid(trimmed);
                logger.info("Deleted ActionPlan master for detailrefid: {}", trimmed);
            }
        }
    }

    return "SUCCESS";
}
@Override
@Transactional
public String updateVerification(String detailkeyid, String responsevalue, String targetdate,
                                  String verifystatus, String remarks) {

    if (remarks == null || remarks.length() == 0) {
        remarks = "-";
    }

    if ("Y".equals(verifystatus)) {

        LocalDateTime parsedDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
            parsedDate = LocalDate.parse(targetdate, formatter).atStartOfDay();
        } catch (Exception e) {
            logger.warn("Date parse failed, using current time. Raw value: {}", targetdate);
            parsedDate = LocalDateTime.now();
        }

        detailRepository.updateVerification(detailkeyid, responsevalue, parsedDate, verifystatus, remarks);
        logger.info("Updated verification for keyid: {}", detailkeyid);
    }

    return "SUCCESS";
}

@Override
@Transactional
public String updateActionClosure(String detailkeyid, String responsevalue, String targetdate,
                                   String revisedate, String status, String correctiveaction,
                                   String remarks, String abnKeyid) throws Exception {

    if (remarks == null || remarks.length() == 0) {
        remarks = "-";
    }

    if ("C".equals(status)) {

        LocalDateTime parsedTargetDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
            parsedTargetDate = LocalDate.parse(targetdate, formatter).atStartOfDay();
        } catch (Exception e) {
            logger.warn("Date parse failed, using current time. Raw value: {}", targetdate);
            parsedTargetDate = LocalDateTime.now();
        }

        detailRepository.updateActionClosure(
                detailkeyid, responsevalue, parsedTargetDate, status, remarks, correctiveaction);
        logger.info("Updated action closure for keyid: {}", detailkeyid);

        if (abnKeyid != null && !abnKeyid.trim().isEmpty() && !"X".equals(abnKeyid)) {
            detailRepository.updateAbnormalityClosure(
                    abnKeyid, responsevalue, parsedTargetDate, remarks);
            logger.info("Updated abnormality closure for abnKeyid: {}", abnKeyid);
        }

    } else {

        LocalDateTime parsedReviseDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
            parsedReviseDate = LocalDate.parse(revisedate, formatter).atStartOfDay();
        } catch (Exception e) {
            logger.warn("Revise date parse failed, using current time. Raw value: {}", revisedate);
            parsedReviseDate = LocalDateTime.now();
        }

        detailRepository.updateActionClosureStatus(detailkeyid, status, parsedReviseDate, remarks);
        logger.info("Updated action closure status for keyid: {}", detailkeyid);
    }

    return "SUCCESS";
}
}