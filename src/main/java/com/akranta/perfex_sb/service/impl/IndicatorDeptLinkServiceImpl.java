package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.IndicatorDeptLinkRequest;
import com.akranta.perfex_sb.dto.IndicatorDeptLinkRequest.IndicatorDeptLinkItem;
import com.akranta.perfex_sb.dto.NewIndicatorDeptLinkRequestDto;
import com.akranta.perfex_sb.model.KpiTlIndicatorDeptLink;
import com.akranta.perfex_sb.repository.KpiTlIndicatorDeptLinkRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.IndicatorDeptLinkService;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class IndicatorDeptLinkServiceImpl implements IndicatorDeptLinkService {

    private static final Logger logger = LoggerFactory.getLogger(IndicatorDeptLinkServiceImpl.class);

    @Autowired
    private KpiTlIndicatorDeptLinkRepository repository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "KPI_TL_INDICATOR_DEPT_LINK";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX = "KID";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    @Override
    @Transactional
    public KpiTlIndicatorDeptLink createIndicatorDeptLink(IndicatorDeptLinkRequest request) throws Exception {

        StringBuilder exceptionStr = new StringBuilder();
        KpiTlIndicatorDeptLink resultEntity = new KpiTlIndicatorDeptLink();

        try {

            String val = request.getCreatedBy();
            logger.info("INSIDE THE SERVICE IMPL");

            // LocalDateTime dateTime1 = LocalDateTime.now();

            logger.info("pillCode: {}", request.getPillCode());
            logger.info("drillLevel: {}", request.getDrillLevel());
            logger.info("indicatorId: {}", request.getIndicatorId());
            logger.info("deptId: {}", request.getDeptId());
            logger.info("isIndicatorFactory: {}", request.getIsIndicatorFactory());

            // Get Pillar ID from Pillar Code
            String pillId = repository.getPillarIdByCode(request.getPillCode());

            if (pillId == null || pillId.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid Pillar Code: " + request.getPillCode());
            }

            // ========== GET ENTITY LIST FROM REQUEST ==========
            List<NewIndicatorDeptLinkRequestDto> methodsList = request.getNewIndicatorDeptLinkRequestDto();
            logger.info("INSIDE THE METHOD LIST: {}", methodsList);

            List<KpiTlIndicatorDeptLink> saveList = new ArrayList<>();

            // ============= PROCESS METHODS LIST (Entity List) =============
            if (methodsList != null && !methodsList.isEmpty()) {

                logger.info("INSIDE THE METHOD IF - Processing {} items", methodsList.size());

                for (NewIndicatorDeptLinkRequestDto item : methodsList) {

                    logger.info("***==**getIsDeleteValue={}", item.getIsDelete());

                    // ============= DELETE CASE =============
                    if ("Y".equals(item.getIsDelete())) {

                        logger.info("INSIDE THE IF - DELETE");

                        try {
                            // Convert empty/invalid to null
                            String validIndicatorId = ValidationUtil
                                    .isValidKeyId(item.getKpiTlIndicatorDeptLink().getIndicatorid())
                                            ? item.getKpiTlIndicatorDeptLink().getIndicatorid()
                                            : null;
                            String validDeptId = ValidationUtil
                                    .isValidKeyId(item.getKpiTlIndicatorDeptLink().getDeptid())
                                            ? item.getKpiTlIndicatorDeptLink().getDeptid()
                                            : null;

                            logger.info("Delete Params:");
                            logger.info("pillId      : {}", pillId);
                            logger.info("drillLevel  : {}", request.getDrillLevel());
                            logger.info("indicatorId : {}", validIndicatorId);
                            logger.info("deptId      : {}", validDeptId);

                            int deletedCount = repository.deleteByCompositeKey(
                                    pillId,
                                     validIndicatorId,
                                     validDeptId,
                                    request.getDrillLevel()                                    
                                    );

                            logger.info("Deleted {} records", deletedCount);

                        } catch (DataIntegrityViolationException e) {

                            String indicatorName = repository
                                    .getIndicatorNameById(item.getKpiTlIndicatorDeptLink().getIndicatorid());
                            String fnlnName = repository
                                    .getFunctionalLocationById(item.getKpiTlIndicatorDeptLink().getDeptid());

                            if (ValidationUtil.isValidKeyId(exceptionStr.toString())) {
                                exceptionStr.append(",");
                            }
                            exceptionStr.append(indicatorName)
                                    .append(" Indicator In ")
                                    .append(fnlnName);

                            logger.error("BusinessApplicationException: {}", exceptionStr);
                        }
                    }

                    //new 
                    // ============= INSERT OR UPDATE CASE =============
                    else {

                        logger.info("INSIDE THE ELSE ** INSERT OR UPDATE");

                        item.getKpiTlIndicatorDeptLink().setPillarid(pillId);

                        // Check if record already exists (upsert logic)
                        KpiTlIndicatorDeptLink existing = repository.findByIndicatorIdAndDeptId(
                                item.getKpiTlIndicatorDeptLink().getIndicatorid(),
                                item.getKpiTlIndicatorDeptLink().getDeptid()
                        );

                        if (existing != null) {
                            // UPDATE — reuse existing keyid so JPA does UPDATE not INSERT
                            logger.info("Record exists, updating keyid: {}", existing.getKeyid());
                            item.getKpiTlIndicatorDeptLink().setKeyid(existing.getKeyid());
                            item.getKpiTlIndicatorDeptLink().setCreatedon(existing.getCreatedon());
                            item.getKpiTlIndicatorDeptLink().setCreatedby(existing.getCreatedby());
                            item.getKpiTlIndicatorDeptLink().setModifiedon(LocalDateTime.now());

                             // Force active = Y on reactivation/update unless explicitly deleting
                            item.getKpiTlIndicatorDeptLink().setActive('Y');
                        } else {
                            // INSERT — generate new keyid
                            String newKeyId = dbActionTemplate.getSequenceNumber(
                                    "KPI_TL_INDICATOR_DEPT_LINK", 15, "KID", "YY", "Y");

                            if (newKeyId == null || newKeyId.trim().isEmpty()) {
                                throw new IllegalStateException("Failed to generate Key ID");
                            }

                            logger.info("New record, generated keyid: {}", newKeyId);
                            item.getKpiTlIndicatorDeptLink().setKeyid(newKeyId);
                            item.getKpiTlIndicatorDeptLink().setActive('Y');
                        }

                        saveList.add(item.getKpiTlIndicatorDeptLink());
                    }
                    // ============= INSERT CASE =============
                    // else {

                    //     logger.info("INSIDE THE ELSE ** INSERT");

                    //     // logger.info("Before setting pillId - indicatorid: {}, deptid: {}, createdby:
                    //     // {} depttype :{}",
                    //     // item.getIndicatorid(), item.getDeptid(), item.getCreatedby());

                    //     // Item is already an Entity with values set from Service/Controller
                    //     // Just set pillId and generate keyId
                    //     // item.setPillarid(pillId);
                    //     item.getKpiTlIndicatorDeptLink().setPillarid(pillId);

                    //     String newKeyId = dbActionTemplate.getSequenceNumber(
                    //             "KPI_TL_INDICATOR_DEPT_LINK", 15, "KID", "YY", "Y");

                    //     if (newKeyId == null || newKeyId.trim().isEmpty()) {
                    //         throw new IllegalStateException("Failed to generate Key ID");
                    //     }

                    //     item.getKpiTlIndicatorDeptLink().setKeyid(newKeyId);

                    //     // ========== VERIFY VALUES BEFORE SAVING ==========
                    //     // logger.info("Before save - keyid: {}, indicatorid: {}, deptid: {}, pillarid:
                    //     // {}, createdby: {},depttype :{}",
                    //     // item.getKeyid(), item.getIndicatorid(), item.getDeptid(),
                    //     // item.getPillarid(), item.getCreatedby(),item.getDepttype());

                    //     saveList.add(item.getKpiTlIndicatorDeptLink());

                    //     // logger.debug("create serviceimpl: {} with indicatorid: {} and deptid: {}",
                    //     // item.getKeyid(), item.getIndicatorid(), item.getDeptid());
                    // }
                }

            }
            // ============= NO METHODS LIST (Single Entity) =============
            else {

                logger.info("INSIDE THE ELSE - NO LIST");

                KpiTlIndicatorDeptLink indicatorFactLink = new KpiTlIndicatorDeptLink();

                // indicatorFactLink.setEffectivedate(dateTime1);
                // indicatorFactLink.setInactivedate(LocalDateTime.of(1801, 1, 1, 0, 0)); //
                // Constants.futureNullDate
                // indicatorFactLink.setModifiedon(dateTime1);
                // indicatorFactLink.setCreatedon(dateTime1);
                // indicatorFactLink.setTempfield1('-');
                // indicatorFactLink.setTempfield2('-');
                // indicatorFactLink.setTempfield3('-');
                // indicatorFactLink.setTempfield4('-');
                // indicatorFactLink.setTempfield5('-');
                // indicatorFactLink.setActive('Y');

                // logger.info("val: {}", val);
                // indicatorFactLink.setCreatedby(val);
                // indicatorFactLink.setPillarid(pillId);
                // indicatorFactLink.setDepttype(request.getDrillLevel());
                // indicatorFactLink.setIndicatorid(request.getIndicatorId());
                // indicatorFactLink.setDeptid(request.getDeptId());

                String newKeyId = dbActionTemplate.getSequenceNumber(
                        "KPI_TL_INDICATOR_DEPT_LINK", 15, "KID", "YY", "Y");

                if (newKeyId == null || newKeyId.trim().isEmpty()) {
                    throw new IllegalStateException("Failed to generate Key ID");
                }

                indicatorFactLink.setKeyid(newKeyId);

                // Build exception message
                String indicatorName = repository.getIndicatorNameById(indicatorFactLink.getIndicatorid());
                String fnlnOriginalId = repository.getFnlnOriginalIdById(indicatorFactLink.getDeptid());

                if (ValidationUtil.isValidKeyId(fnlnOriginalId) && fnlnOriginalId.length() >= 3) {
                    String str = fnlnOriginalId.substring(0, 3);

                    if (str.equals("CEL")) {
                        String cellName = repository.getCellNameByOriginalId(fnlnOriginalId);
                        exceptionStr.append(indicatorName).append(" Indicator In ").append(cellName);
                        logger.info("KPI Exception CEL: {}", exceptionStr);

                    } else if (str.equals("SEC")) {
                        String sectName = repository.getSectionNameByOriginalId(fnlnOriginalId);
                        exceptionStr.append(indicatorName).append(" Indicator In ").append(sectName);
                        logger.info("KPI Exception SEC: {}", exceptionStr);

                    } else {
                        String fnlnName = repository.getFunctionalLocationById(indicatorFactLink.getDeptid());
                        exceptionStr.append(indicatorName).append(" Indicator In ").append(fnlnName);
                        logger.info("KPI Exception OTHER: {}", exceptionStr);
                    }
                }

                logger.info("KidlKeyid: {}", indicatorFactLink.getKeyid());

                saveList.add(indicatorFactLink);
                resultEntity = indicatorFactLink;
            }

            // ============= SAVE ALL =============
            if (!saveList.isEmpty()) {
                repository.saveAll(saveList);
                logger.info("Saved {} entities to database", saveList.size());
            }

            // ============= THROW EXCEPTION IF DELETIONS FAILED =============
            if (exceptionStr.length() > 0) {
                throw new Exception(exceptionStr.toString() + " Already Referred In Target/Actual Entry");
            }

            return resultEntity;

        } catch (Exception e) {
            logger.error("Error occurred while creating indicator dept link", e);

            if (exceptionStr.length() > 0) {
                throw new Exception(exceptionStr.toString() + " Already Referred");
            }

            throw new Exception(e.getMessage());
        }
    }

    public String validateKeyIndLink(KpiTlIndicatorDeptLink kpiTlIndicatorDeptLink) throws Exception {

        String isValidate = "";

        try {
            // Prepare parameters for validation
            String indicatorId = ValidationUtil.isValidKeyId(kpiTlIndicatorDeptLink.getIndicatorid())
                    ? kpiTlIndicatorDeptLink.getIndicatorid()
                    : null;

            String deptId = ValidationUtil.isValidKeyId(kpiTlIndicatorDeptLink.getDeptid())
                    ? kpiTlIndicatorDeptLink.getDeptid()
                    : null;

            // Get link count
            Long cnt = repository.getLinkCount(indicatorId, deptId);

            logger.debug("Link count: {}", cnt);

            // If count is 0, validation passes
            if (cnt == 0) {
                logger.debug("Validation passed - no existing links");
                isValidate = "";
            }
            // If count > 0, build error message
            else {
                logger.debug("Validation failed - existing links found: {}", cnt);

                // Get indicator name
                String indicatorName = repository.getIndicatorNameById(
                        kpiTlIndicatorDeptLink.getIndicatorid());

                // Get functional location name
                String fnlnName = repository.getFunctionalLocationById(
                        kpiTlIndicatorDeptLink.getDeptid());

                // Build validation error message
                isValidate = indicatorName + " Indicator In " + fnlnName
                        + " Already Referred In Target/Actual Entry";

                logger.warn("Validation error: {}", isValidate);
            }

            return isValidate;

        } catch (Exception e) {
            logger.error("Error occurred during validation", e);
            throw new Exception("Validation failed: " + e.getMessage());
        }
    }
}
