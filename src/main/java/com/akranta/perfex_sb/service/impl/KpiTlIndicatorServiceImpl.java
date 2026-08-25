package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.KpiIndicatorDto;
import com.akranta.perfex_sb.model.KpiTlActual;
import com.akranta.perfex_sb.model.KpiTlIndicator;
import com.akranta.perfex_sb.model.KpiTlKpiRemarks;
import com.akranta.perfex_sb.repository.KpiTlActualRepository;
import com.akranta.perfex_sb.repository.KpiTlIndicatorRepository;
import com.akranta.perfex_sb.repository.KpiTlKpiRemarksRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.KPIService;
import com.akranta.perfex_sb.util.ValidationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KpiTlIndicatorServiceImpl implements KPIService {

    private static final Logger logger = LoggerFactory.getLogger(KpiTlIndicatorServiceImpl.class);

    // private final KpiTlIndicatorRepository repository;
    // private final DbActionTemplate dbActionTemplate;
    @Autowired
    private KpiTlIndicatorRepository repository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    @Autowired
    private KpiTlActualRepository kpiTlActualRepository;

    @Autowired
    private KpiTlKpiRemarksRepository kpiTlKpiRemarksRepository;

    private static final String SEQ_IDENTIFIER = "KPI_TL_INDICATOR";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX = "KIN";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_ACT = "KPI_TL_ACTUAL";
    // private static final int KEY_LENGTH = 15;
    private static final String PREFIX_ACT = "KAU";
    // private static final String DATE_FORMAT = "YY";
    // private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_RM = "KPI_TL_KPIREMARKS";
    // private static final int KEY_LENGTH = 15;
    private static final String PREFIX_RM = "KPR";

    // public KpiTlIndicatorServiceImpl(
    // KpiTlIndicatorRepository repository,
    // DbActionTemplate dbActionTemplate) {
    // this.repository = repository;
    // this.dbActionTemplate = dbActionTemplate;
    // }

    public List<Map<String, Object>> getAllKeyIndicators(KpiIndicatorDto kpiIndicatorDto) {

        // Extract parameters from DTO
        String parentId = ValidationUtil.isValidKeyId(kpiIndicatorDto.getParentid()) ? kpiIndicatorDto.getParentid()
                : null;
        String pillarId = ValidationUtil.isValidKeyId(kpiIndicatorDto.getPillarid()) ? kpiIndicatorDto.getPillarid()
                : null;
        String keyId = ValidationUtil.isValidKeyId(kpiIndicatorDto.getKeyid()) ? kpiIndicatorDto.getKeyid() : null;
        String location = ValidationUtil.isValidKeyId(kpiIndicatorDto.getLocation()) ? kpiIndicatorDto.getLocation()
                : null;
        // String type = ValidationUtil.isValidKeyId(kpiIndicatorDto.getTempfield3()) ?
        // kpiIndicatorDto.getTempfield3() : null;

        logger.info("Fetching KPI indicators - parentId: {}, pillarId: {}, keyId: {}, location: {}",
                parentId, pillarId, keyId, location);

        return repository.getAllkeyInd(parentId, pillarId, keyId, location);
    }

    public List<Map<String, Object>> getAllKeyIndicatorsKkValue(KpiIndicatorDto kpiIndicatorDto) {

        String parentId = ValidationUtil.isValidKeyId(kpiIndicatorDto.getParentid()) ? kpiIndicatorDto.getParentid()
                : null;
        String pillarId = ValidationUtil.isValidKeyId(kpiIndicatorDto.getPillarid()) ? kpiIndicatorDto.getPillarid()
                : null;
        String keyId = ValidationUtil.isValidKeyId(kpiIndicatorDto.getKeyid()) ? kpiIndicatorDto.getKeyid() : null;
        String location = ValidationUtil.isValidKeyId(kpiIndicatorDto.getLocation()) ? kpiIndicatorDto.getLocation()
                : null;
        String type = ValidationUtil.isValidKeyId(kpiIndicatorDto.getTempfield3()) ? kpiIndicatorDto.getTempfield3()
                : null;

        logger.info("Fetching KPI indicators - parentId: {}, pillarId: {}, keyId: {}, location: {}",
                parentId, pillarId, keyId, location, type);

        return repository.getAllkeyIndvalue(parentId, pillarId, keyId, location, type);
    }

    public int deleteByKeyId(KpiTlIndicator kpiTlIndicator) {
        String keyid = kpiTlIndicator.getKeyid();
        return repository.deletebykeyid(keyid);
    }

    // @Override
    // public String validateDelkeyIndLevel(String parentId, String pillarId, String
    // keyId, String location)
    // throws Exception {
    // int menuLevel = 0;
    // String validate = "Not Valid";

    // // Get all indicators based on parameters
    // List<KpiTlIndicator> indicatorList = repository.getAllkeyInd(parentId,
    // pillarId, keyId, location);

    // menuLevel = indicatorList != null ? indicatorList.size() : 0;
    // System.out.println("menuLevel: " + menuLevel);

    // // If no child records found, it's valid to delete
    // if (menuLevel <= 0) {
    // validate = "Valid";
    // }

    // return validate;
    // }

    @Override
    public String getByPillCode(String pillcode) {
        return repository.getTpmpKeyId(pillcode);
    }

    @Override
    public String getflId(String flId) {
        return repository.getflId(flId);
    }

    @Override
    @Transactional
    public KpiTlIndicator createIndicator(KpiTlIndicator kpiTlIndicator) {

        try {

            logger.info("KPI VALUES {}", kpiTlIndicator.getDescription());
            logger.info("KPI VALUES {}", kpiTlIndicator.getInputentry());
            logger.info("KPI VALUES {}", kpiTlIndicator.getDescription());
            logger.info("KPI VALUES {}", kpiTlIndicator.getDescription());
            // if (kpiTlIndicator == null) {
            // throw new IllegalArgumentException("KPI Indicator data cannot be null");
            // }
            // Set indicator name from description
            // kpiTlIndicator.setIndicatorname(kpiTlIndicator.getDescription());

            // String description = kpiTlIndicator.getIndicatorname();
            // if (description == null || description.trim().isEmpty() ||
            // "{}".equals(description.trim())) {
            // throw new IllegalArgumentException("Description cannot be empty or null");
            // }

            // Generate new key ID (no condition check before generation)
            String newKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER,
                    KEY_LENGTH,
                    PREFIX,
                    DATE_FORMAT,
                    FORMAT_RESET);

            if (newKeyid == null || newKeyid.trim().isEmpty()) {
                throw new IllegalStateException("Failed to generate Key ID - sequence returned null");
            }

            kpiTlIndicator.setKeyid(newKeyid);
            logger.info("Generated new Key ID: {}", newKeyid);

            // If parentid is not valid or equals "1", set it equal to keyid (root level)
            if (!ValidationUtil.isValidKeyId(kpiTlIndicator.getParentid())
                    || "1".equals(kpiTlIndicator.getParentid())) {
                kpiTlIndicator.setParentid(newKeyid);
                logger.info("Setting parentId = keyId for root level");
            } else {
                // Update parent's ischild flag
                repository.updateParentIsChild(kpiTlIndicator.getParentid());
                logger.info("Updated parent ischild flag for keyid: {}", kpiTlIndicator.getParentid());

                // Check if target is needed
                if ("Y".equals(kpiTlIndicator.getTargetneed())) {
                    List<String> searchNodeList = getSearchNode("", kpiTlIndicator.getParentid());

                    // if (searchNodeList != null && !searchNodeList.isEmpty()) {
                    // String parentId = searchNodeList.get(0);
                    // logger.info("Original parentId: {}", parentId);

                    // // Remove first character (substring from index 1)
                    // parentId = parentId.substring(1);
                    // logger.info("Processed parentId: {}", parentId);

                    // // Split by "/"
                    // String[] searchNodes = parentId.split("/");
                    // logger.info("searchNodes length: {}", searchNodes.length);

                    // // Update target need for each node in the path
                    // for (String keyid : searchNodes) {
                    // if (isValidKeyId(keyid)) {
                    // logger.info("Updating target need for keyid: {}", keyid);
                    // repository.updateTargetNeed(keyid);
                    // }
                    // }
                    // }

                    if (searchNodeList != null && searchNodeList.size() > 0) {
                        String parentId = searchNodeList.get(0);
                        logger.info("Original parentId: {}", parentId);

                        // Remove first character (substring from index 1 to length)
                        parentId = parentId.substring(1, parentId.length());
                        logger.info("Processed parentId: {}", parentId);

                        // Split by "/"
                        String[] searchNodes = parentId.split("/");
                        logger.info("searchNodes length: {}", searchNodes.length);

                        // Update target need for each node in the path
                        for (int i = 0; i < searchNodes.length; i++) {
                            String keyid = searchNodes[i];
                            if (isValidKeyId(keyid)) {
                                logger.info("Updating target need for keyid: {}", keyid);
                                repository.updateTargetNeed(keyid);
                            }
                        }
                    }
                }
            }

            // Calculate level and sortno
            logger.info("Sort No {}", kpiTlIndicator.getSortno());
            kpiTlIndicator = getLevelSortNo(kpiTlIndicator);

            // Save the entity
            KpiTlIndicator savedEntity = repository.save(kpiTlIndicator);
            logger.info("Saved KPI Indicator with Key ID: {}", savedEntity.getKeyid());

            return savedEntity;

        } catch (IllegalArgumentException e) {
            logger.error("Validation error creating/updating KPI Indicator: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error creating/updating KPI Indicator: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create/update KPI Indicator: " + e.getMessage(), e);
        }
    }

    /**
     * Get search node child paths
     * Single method combining SQL generation and execution
     */
    private List<String> getSearchNode(String searchNode, String originalId) {
        try {
            logger.info("Fetching search nodes - searchNode: {}, originalId: {}", searchNode, originalId);

            List<String> childPaths = repository.getSearchNodeChildPaths(searchNode, originalId);

            logger.info("Found {} child paths", childPaths.size());
            return childPaths;

        } catch (Exception e) {
            logger.error("Error fetching search node: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch search node: " + e.getMessage(), e);
        }
    }

    /**
     * Validate key ID
     */
    private boolean isValidKeyId(String keyId) {
        return keyId != null && !keyId.trim().isEmpty() && !keyId.equalsIgnoreCase("null");
    }

    /**
     * Calculate and set level number and sort number
     * Matches legacy Java logic exactly
     */
    private KpiTlIndicator getLevelSortNo(KpiTlIndicator kpiTlIndicator) {
        try {
            String sortNo = "";

            // Check if keyid equals parentid (root level)
            if (kpiTlIndicator.getKeyid().equals(kpiTlIndicator.getParentid())) {
                // Set level to 1 if not already "1"
                if (kpiTlIndicator.getLevelno() == null || kpiTlIndicator.getLevelno() != 1) {
                    kpiTlIndicator.setLevelno(1);
                }

                // IMPORTANT: Only recalculate sortno if it contains a "."
                if (kpiTlIndicator.getSortno() != null &&
                        kpiTlIndicator.getSortno().indexOf(".") > 0) {
                    sortNo = getSortNo(kpiTlIndicator);
                    kpiTlIndicator.setSortno(sortNo);
                }
            }

            // // ROOT LEVEL (keyid == parentid)
            // if (kpiTlIndicator.getKeyid().equals(kpiTlIndicator.getParentid())) {

            // // Always force level = 1
            // kpiTlIndicator.setLevelno(1);

            // // ALWAYS generate sortno if missing
            // if (kpiTlIndicator.getSortno() == null ||
            // kpiTlIndicator.getSortno().isEmpty()) {
            // String sortNo1 = getSortNo(kpiTlIndicator); // count + 1
            // kpiTlIndicator.setSortno(sortNo1);
            // }
            // }
            else {
                // Child level - ELSE block
                logger.info("Processing child level for keyid: {}", kpiTlIndicator.getKeyid());
                logger.info("Processing child level for parentid: {}", kpiTlIndicator.getParentid());

                // Get parent's sort number FIRST
                sortNo = getParentSortNo(kpiTlIndicator);

                // Increment level number if currently 1
                if (kpiTlIndicator.getLevelno() != null && kpiTlIndicator.getLevelno() == 1) {
                    kpiTlIndicator.setLevelno(kpiTlIndicator.getLevelno() + 1);
                }

                // IMPORTANT: Only recalculate sortno if it doesn't already start with parent
                // sortno
                if (kpiTlIndicator.getSortno() == null ||
                        kpiTlIndicator.getSortno().indexOf(sortNo + ".") <= 0) {
                    sortNo = getSortNo(kpiTlIndicator);
                    kpiTlIndicator.setSortno(sortNo);
                }
            }

            logger.info("LevelNo: {}", kpiTlIndicator.getLevelno());
            logger.info("SortNo: {}", kpiTlIndicator.getSortno());

            return kpiTlIndicator;

        } catch (Exception e) {
            logger.error("Error in getLevelSortNo: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to calculate level and sort number: " + e.getMessage(), e);
        }
    }

    /**
     * Get sort number based on level
     * Matches legacy getSortNo exactly
     */
    private String getSortNo(KpiTlIndicator kpiTlIndicator) {
        try {
            String sortNo;

            if (kpiTlIndicator.getLevelno() == 1) {
                // Level 1: Get count + 1
                Integer count = repository.getSortNoForLevel1(kpiTlIndicator.getLevelno());
                sortNo = String.valueOf(count);
                logger.info("Level 1111 SortNo: {}", sortNo);
            } else {
                // Child level: Get parent sortno + . + count
                sortNo = repository.getSortNoForChildLevel(kpiTlIndicator.getParentid());
                logger.info("Child level SortNo : {}", sortNo);
            }

            return sortNo;

        } catch (Exception e) {
            logger.error("Error getting sort number: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get sort number: " + e.getMessage(), e);
        }
    }

    @Override
    public String getSortNo2(KpiTlIndicator kpiTlIndicator) {
        try {
            String sortNo;

            if (kpiTlIndicator.getLevelno() == 1) {
                // Level 1: Get count + 1
                Integer count = repository.getSortNoForLevel1(kpiTlIndicator.getLevelno());
                sortNo = String.valueOf(count);
                logger.info("Level 1111 SortNo: {}", sortNo);
            } else {
                // Child level: Get parent sortno + . + count
                sortNo = repository.getSortNoForChildLevel(kpiTlIndicator.getParentid());
                logger.info("Child level SortNo : {}", sortNo);
            }

            return sortNo;

        } catch (Exception e) {
            logger.error("Error getting sort number: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get sort number: " + e.getMessage(), e);
        }
    }

    /**
     * Get parent's sort number
     * Matches legacy getParentSortNo exactly
     */
    private String getParentSortNo(KpiTlIndicator kpiTlIndicator) {
        try {
            String sortNo = repository.getParentSortNo(kpiTlIndicator.getParentid());
            logger.info("Parent SortNo: {}", sortNo);
            return sortNo != null ? sortNo : "";

        } catch (Exception e) {
            logger.error("Error getting parent sort number: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get parent sort number: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public KpiTlIndicator updateIndicator(KpiTlIndicator kpiTlIndicator) {
        try {
            // Validate that keyid exists (required for update)
            if (!ValidationUtil.isValidKeyId(kpiTlIndicator.getKeyid())) {
                throw new IllegalArgumentException("Valid Key ID is required for update");
            }

            // Store keyid in a final variable for use in lambda
            final String keyid = kpiTlIndicator.getKeyid();

            // Verify the record exists
            KpiTlIndicator existingIndicator = repository.findById(keyid)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "KPI Indicator not found with keyid: " + keyid));

            logger.info("Updating KPI Indicator with Key ID: {}", kpiTlIndicator.getKeyid());
            logger.info("KPI VALUES {}", kpiTlIndicator.getDescription());
            logger.info("KPI VALUES {}", kpiTlIndicator.getInputentry());

            // Set indicator name from description
            kpiTlIndicator.setIndicatorname(kpiTlIndicator.getDescription());

            // Handle target need updates (same logic as create)
            if ("Y".equals(kpiTlIndicator.getTargetneed())) {
                List<String> searchNodeList = getSearchNode("", kpiTlIndicator.getParentid());

                if (searchNodeList != null && searchNodeList.size() > 0) {
                    String parentId = searchNodeList.get(0);
                    logger.info("Original parentId: {}", parentId);

                    // Remove first character (substring from index 1 to length)
                    parentId = parentId.substring(1, parentId.length());
                    logger.info("Processed parentId: {}", parentId);

                    // Split by "/"
                    String[] searchNodes = parentId.split("/");
                    logger.info("searchNodes length: {}", searchNodes.length);

                    // Update target need for each node in the path
                    for (int i = 0; i < searchNodes.length; i++) {
                        String nodeKeyid = searchNodes[i];
                        if (isValidKeyId(nodeKeyid)) {
                            logger.info("Updating target need for keyid: {}", nodeKeyid);
                            repository.updateTargetNeed(nodeKeyid);
                        }
                    }
                }
            }

            // Calculate level and sortno
            logger.info("Sort No {}", kpiTlIndicator.getSortno());
            kpiTlIndicator = getLevelSortNo(kpiTlIndicator);

            // Save the updated entity
            KpiTlIndicator updatedEntity = repository.save(kpiTlIndicator);
            logger.info("Updated KPI Indicator with Key ID: {}", updatedEntity.getKeyid());

            return updatedEntity;

        } catch (IllegalArgumentException e) {
            logger.error("Validation error updating KPI Indicator: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error updating KPI Indicator: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update KPI Indicator: " + e.getMessage(), e);
        }
    }

    // TARGET SETTING 22222222222222222222

    // public List<KpiTlActual> getByKeyId(String keyid, String indicatorid) {
    // return kpiTlActualRepository.findActuals(keyid, indicatorid);
    // }

    // Service
    // public KpiTlActual getByModel(KpiTlActual kpiTlActual) {
    // String keyid = kpiTlActual.getKeyid();
    // String indicatorid = kpiTlActual.getIndicatorid();

    // logger.info("KPI VALUES {}", kpiTlActual.getKeyid());
    // logger.info("KPI VALUES {}", kpiTlActual.getIndicatorid());

    // if (kpiTlActual.getIndicatorid().isEmpty()) {
    // kpiTlActual.setIndicatorid(null);
    // }

    // List<KpiTlActual> actuals = kpiTlActualRepository.findActuals(keyid,
    // indicatorid);
    // logger.info("actuals value {} logger");

    // return actuals.isEmpty() ? null : actuals.get(0);
    // }

    // public KpiTlActual getByModel(KpiTlActual kpiTlActual) {
    // String keyid = kpiTlActual.getKeyid();
    // String indicatorid = kpiTlActual.getIndicatorid();

    // logger.info("KPI VALUES keyid: {}", keyid);
    // logger.info("KPI VALUES indicatorid: {}", indicatorid);

    // List<KpiTlActual> actuals = kpiTlActualRepository.findActuals(keyid,
    // indicatorid);
    // return actuals.isEmpty() ? null : actuals.get(0);
    // }

    @Override
    @Transactional
    public List<KpiTlActual> createordelete(List<KpiTlActual> list) {

        try {

            if (list == null || list.isEmpty()) {
                return list;
            }

            List<KpiTlActual> saveList = new ArrayList<>();
            List<KpiTlActual> deleteList = new ArrayList<>();

            for (KpiTlActual entity : list) {

                KpiTlActual oldKpiTlActual = kpiTlActualRepository.findActuals(
                        entity.getKeyid(),
                        null);

                logger.info("oldKpiTlActual keyid : {}", entity.getKeyid());
                logger.info("oldKpiTlActual Indicatorid: {}", entity.getIndicatorid());
                logger.info("oldKpiTlActual Benchmarkvalue: {}", entity.getBenchmarkvalue());
                logger.info("oldKpiTlActual Pillarid: {}", entity.getPillarid());
                logger.info("oldKpiTlActual Calendaryear: {}", entity.getCalendaryear());

                // KpiTlActual newKpiTlActual = new KpiTlActual();
                // entity.setKeyid(oldKpiTlActual.getKeyid());
                // entity.setIndicatorid(oldKpiTlActual.getIndicatorid());

                if (ValidationUtil.isValidKeyId(entity.getKeyid())) {
                    entity.setMonthyear(oldKpiTlActual.getMonthyear());
                    entity.setCreatedon(oldKpiTlActual.getCreatedon());
                    entity.setCreatedby(oldKpiTlActual.getCreatedby());
                }

                // ACTIVE = Y
                // if ("Y".equals(entity.getActive())) {
                if (Character.valueOf('Y').equals(entity.getActive())) {

                    // INSERT
                    if (entity.getKeyid() == null || entity.getKeyid().trim().isEmpty()) {

                        String newMstKeyid = dbActionTemplate.getSequenceNumber(
                                SEQ_IDENTIFIER_ACT, KEY_LENGTH, PREFIX_ACT, DATE_FORMAT, FORMAT_RESET);

                        // String newMstKeyid = dbActionTemplate.getSequenceNumber(
                        // "kpi_tl_actual", // replaced TableNames
                        // 10,
                        // "KAU",
                        // null,
                        // null);

                        if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                            throw new IllegalStateException("Failed to generate Key ID");
                        }

                        entity.setKeyid(newMstKeyid);
                    }

                    saveList.add(entity);
                }

                // ACTIVE = N
                else if (Character.valueOf('N').equals(entity.getActive())) {

                    if (entity.getKeyid() != null &&
                            kpiTlActualRepository.existsById(entity.getKeyid())) {

                        deleteList.add(entity);
                    }
                }
            }

            if (!saveList.isEmpty()) {
                kpiTlActualRepository.saveAll(saveList);
            }

            if (!deleteList.isEmpty()) {
                kpiTlActualRepository.deleteAll(deleteList);
            }

            List<KpiTlActual> result = new ArrayList<>();
            result.addAll(saveList);
            result.addAll(deleteList);
            return result;

        } catch (Exception e) {

            // Only log internally
            logger.error("Error occurred while processing KPI Actual", e);

            return list; // silently return
        }
    }

    // public List<KpiTlKpiRemarks> getbykeyid(String keyid)
    // {
    // return kpiTlKpiRemarksRepository.getbykprmkeyid(keyid);
    // }

    @Override
    @Transactional
    public List<KpiTlKpiRemarks> createordeleteremarks(List<KpiTlKpiRemarks> list) {

        try {

            if (list == null || list.isEmpty()) {
                return list;
            }

            List<KpiTlKpiRemarks> saveList = new ArrayList<>();
            List<KpiTlKpiRemarks> deleteList = new ArrayList<>();

            for (KpiTlKpiRemarks entity : list) {

                KpiTlKpiRemarks oldKpiTlKpiRemarks = kpiTlKpiRemarksRepository.getbykprmkeyid(
                        entity.getKeyid());

                if (ValidationUtil.isValidKeyId(entity.getKeyid())) {
                    entity.setCreatedon(oldKpiTlKpiRemarks.getCreatedon());
                    entity.setCreatedby(oldKpiTlKpiRemarks.getCreatedby());
                }

                // ACTIVE = Y
                // if ("Y".equals(entity.getActive())) {
                if (Character.valueOf('Y').equals(entity.getActive())) {

                    // INSERT
                    if (entity.getKeyid() == null || entity.getKeyid().trim().isEmpty()) {

                        String newMstKeyid = dbActionTemplate.getSequenceNumber(
                                SEQ_IDENTIFIER_RM, KEY_LENGTH, PREFIX_RM, DATE_FORMAT, FORMAT_RESET);

                        if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                            throw new IllegalStateException("Failed to generate Key ID");
                        }

                        entity.setKeyid(newMstKeyid);
                    }

                    saveList.add(entity);
                }

                // ACTIVE = N
                else if (Character.valueOf('N').equals(entity.getActive())) {

                    if (entity.getKeyid() != null &&
                            kpiTlKpiRemarksRepository.existsById(entity.getKeyid())) {

                        deleteList.add(entity);
                    }
                }
            }

            if (!saveList.isEmpty()) {
                kpiTlKpiRemarksRepository.saveAll(saveList);
            }

            if (!deleteList.isEmpty()) {
                kpiTlKpiRemarksRepository.deleteAll(deleteList);
            }

            List<KpiTlKpiRemarks> result = new ArrayList<>();
            result.addAll(saveList);
            result.addAll(deleteList);
            return result;

        } catch (Exception e) {

            // Only log internally
            logger.error("Error occurred while processing KPI Remarks", e);

            return list; // silently return
        }
    }

    // @Override
    // public List<Map<String, Object>> getDeviationListif(String flid, String year,
    // String frequency, String currDate) {
    // return kpiTlActualRepository.getDeviationListif(flid, year, frequency,
    // currDate);
    // }

    @Override
    public List<Map<String, Object>> getKPIDeviationCount(String flid, BigDecimal year, String frequency,
            String currDate, String currMonthYear) {
        if ("D".equals(frequency)) {
            // For Daily frequency - uses DD-Mon-yyyy format
            logger.info("Fetching KPI Deviation Count for Daily frequency - flid: {}, year: {}, currDate: {}", flid,
                    year, currDate);
            return kpiTlActualRepository.getDeviationListif(flid, year, frequency, currDate);
        } else {
            // For other frequencies (Weekly, Monthly, etc.) - uses Mon-yyyy format
            logger.info("Fetching KPI Deviation Count for {} frequency - flid: {}, year: {}, currMonthYear: {}",
                    frequency, flid, year, currMonthYear);
            return kpiTlActualRepository.getDeviationListelse(flid, year, frequency, currMonthYear);
        }
    }
    // @Override
    // @Transactional
    // public KpiTlIndicator updateIndicator(KpiTlIndicator kpiTlIndicator) {
    // try {
    // // Validate that keyid exists (required for update)
    // if (!ValidationUtil.isValidKeyId(kpiTlIndicator.getKeyid())) {
    // throw new IllegalArgumentException("Valid Key ID is required for update");
    // }

    // // Verify the record exists
    // KpiTlIndicator existingIndicator =
    // repository.findById(kpiTlIndicator.getKeyid())
    // .orElseThrow(() -> new IllegalArgumentException(
    // "KPI Indicator not found with keyid: " + kpiTlIndicator.getKeyid()));

    // logger.info("Updating KPI Indicator with Key ID: {}",
    // kpiTlIndicator.getKeyid());
    // logger.info("KPI VALUES {}", kpiTlIndicator.getDescription());
    // logger.info("KPI VALUES {}", kpiTlIndicator.getInputentry());

    // // Set indicator name from description
    // kpiTlIndicator.setIndicatorname(kpiTlIndicator.getDescription());

    // // Handle target need updates (same logic as create)
    // if ("Y".equals(kpiTlIndicator.getTargetneed())) {
    // List<String> searchNodeList = getSearchNode("",
    // kpiTlIndicator.getParentid());

    // if (searchNodeList != null && searchNodeList.size() > 0) {
    // String parentId = searchNodeList.get(0);
    // logger.info("Original parentId: {}", parentId);

    // // Remove first character (substring from index 1 to length)
    // parentId = parentId.substring(1, parentId.length());
    // logger.info("Processed parentId: {}", parentId);

    // // Split by "/"
    // String[] searchNodes = parentId.split("/");
    // logger.info("searchNodes length: {}", searchNodes.length);

    // // Update target need for each node in the path
    // for (int i = 0; i < searchNodes.length; i++) {
    // String keyid = searchNodes[i];
    // if (isValidKeyId(keyid)) {
    // logger.info("Updating target need for keyid: {}", keyid);
    // repository.updateTargetNeed(keyid);
    // }
    // }
    // }
    // }

    // // Calculate level and sortno
    // logger.info("Sort No {}", kpiTlIndicator.getSortno());
    // kpiTlIndicator = getLevelSortNo(kpiTlIndicator);

    // // Save the updated entity
    // KpiTlIndicator updatedEntity = repository.save(kpiTlIndicator);
    // logger.info("Updated KPI Indicator with Key ID: {}",
    // updatedEntity.getKeyid());

    // return updatedEntity;

    // } catch (IllegalArgumentException e) {
    // logger.error("Validation error updating KPI Indicator: {}", e.getMessage());
    // throw e;
    // } catch (Exception e) {
    // logger.error("Error updating KPI Indicator: {}", e.getMessage(), e);
    // throw new RuntimeException("Failed to update KPI Indicator: " +
    // e.getMessage(), e);
    // }
    // }

    /**
     * Calculate and set level number and sort number
     */
    // private KpiTlIndicator getLevelSortNo(KpiTlIndicator kpiTlIndicator) {
    // try {
    // String sortNo;

    // // Check if keyid equals parentid (root level)
    // if (kpiTlIndicator.getKeyid().equals(kpiTlIndicator.getParentid())) {
    // // Set level to 1 for root
    // kpiTlIndicator.setLevelno(1);

    // // Calculate sortno for level 1
    // sortNo = getSortNo(kpiTlIndicator);
    // kpiTlIndicator.setSortno(sortNo);

    // logger.info("Root level - LevelNo: {}, SortNo: {}",
    // kpiTlIndicator.getLevelno(), kpiTlIndicator.getSortno());

    // } else {
    // // Child level
    // logger.info("Processing child level for keyid: {}",
    // kpiTlIndicator.getKeyid());

    // // Get parent's sort number
    // String parentSortNo = getParentSortNo(kpiTlIndicator);

    // // Calculate level number based on parent
    // KpiTlIndicator parent =
    // repository.findById(kpiTlIndicator.getParentid()).orElse(null);
    // if (parent != null) {
    // kpiTlIndicator.setLevelno(parent.getLevelno() + 1);
    // } else {
    // kpiTlIndicator.setLevelno(2); // Default to 2 if parent not found
    // }

    // // Calculate sortno for children
    // sortNo = getSortNo(kpiTlIndicator);
    // kpiTlIndicator.setSortno(sortNo);

    // logger.info("Child level - LevelNo: {}, SortNo: {}",
    // kpiTlIndicator.getLevelno(), kpiTlIndicator.getSortno());
    // }

    // return kpiTlIndicator;

    // } catch (Exception e) {
    // logger.error("Error in getLevelSortNo: {}", e.getMessage(), e);
    // throw new RuntimeException("Failed to calculate level and sort number: " +
    // e.getMessage(), e);
    // }
    // }

    // /**
    // * Get sort number based on level
    // */
    // private String getSortNo(KpiTlIndicator kpiTlIndicator) {
    // try {
    // String sortNo;

    // if (kpiTlIndicator.getLevelno() == 1) {
    // // Level 1: Get count + 1
    // Integer count = repository.getSortNoForLevel1(kpiTlIndicator.getLevelno());
    // sortNo = String.valueOf(count);
    // logger.info("Level 1 SortNo: {}", sortNo);
    // } else {
    // // Child level: Get parent sortno + . + count
    // sortNo = repository.getSortNoForChildLevel(kpiTlIndicator.getParentid());

    // // Handle null case
    // if (sortNo == null || sortNo.trim().isEmpty()) {
    // logger.warn("Parent sortno is null, calculating default");
    // // Get parent's sortno first
    // String parentSortNo =
    // repository.getParentSortNo(kpiTlIndicator.getParentid());
    // if (parentSortNo == null || parentSortNo.trim().isEmpty()) {
    // parentSortNo = "1"; // Default if parent has no sortno
    // }
    // // Get count of children
    // Integer childCount = repository.getChildCount(kpiTlIndicator.getParentid());
    // sortNo = parentSortNo + "." + childCount;
    // }

    // logger.info("Child level SortNo: {}", sortNo);
    // }

    // return sortNo;

    // } catch (Exception e) {
    // logger.error("Error getting sort number: {}", e.getMessage(), e);
    // throw new RuntimeException("Failed to get sort number: " + e.getMessage(),
    // e);
    // }
    // }

    // /**
    // * Get parent's sort number
    // */
    // private String getParentSortNo(KpiTlIndicator kpiTlIndicator) {
    // try {
    // String sortNo = repository.getParentSortNo(kpiTlIndicator.getParentid());
    // logger.info("Parent SortNo: {}", sortNo);
    // return sortNo != null ? sortNo : "";

    // } catch (Exception e) {
    // logger.error("Error getting parent sort number: {}", e.getMessage(), e);
    // throw new RuntimeException("Failed to get parent sort number: " +
    // e.getMessage(), e);
    // }
    // }

    @Override
    public int getkeyIndLevel(String Keyid) throws Exception {
        int menuLevel = 0;

        try {
            // Get list of levels from repository
            List<Integer> resultList = repository.getKeyIndicatorLevels(Keyid);

            logger.debug("SQL result for getkeyIndLevel: {}", resultList);

            // Loop through results - last value wins (same as Java code)
            for (Integer level : resultList) {
                menuLevel = level;
            }

        } catch (Exception e) {
            logger.error("Error in getkeyIndLevel: {}", e.getMessage());
            throw e;
        }

        return menuLevel;
    }

    /**
     * Gets the configured key indicator level from configuration table
     */
    @Override
    public int getConfigkeyIndLevel() throws Exception {
        int configLevel = 0;

        try {
            // Get list of config levels from repository+
            List<Integer> resultList = repository.getConfigkeyIndLevels();

            logger.debug("SQL result for getConfigkeyIndLevel: {}", resultList);

            // Loop through results - last value wins
            for (Integer level : resultList) {
                configLevel = level;
            }

        } catch (Exception e) {
            logger.error("Error in getConfigkeyIndLevel: {}", e.getMessage());
            throw e;
        }

        return configLevel;
    }

    @Override
    public String getEntProgStartMonth() {
        String startMonth = repository.getEntProgStartMonth();
        return startMonth;
    }

    @Override
    public KpiTlIndicator indiactor(String Keyid, String indicatorName, String parentId) {

        logger.info("Fetching Keyid: {}, indicatorName: {}, parentId: {}",
                Keyid, indicatorName, parentId);

        KpiTlIndicator indicator = repository.findIndicators(Keyid, indicatorName, parentId);
        return indicator;
    }



    //   @Override
    // public List<String[]> getElementId(String loginflid, String empId, String loginElementid, Integer loginlevel) 
    //         throws Exception {
    //     try {
    //         logger.info("Getting element IDs for empId: {}, loginlevel: {}, loginflid: {}", 
    //                    empId, loginlevel, loginflid);
            
    //         // Validate loginflid
    //       //  String validLoginflid = UIUtils.isValidKeyId(loginflid) ? loginflid : null;

    //          String validLoginflid = ValidationUtil.isValidKeyId(loginflid) ? loginflid : null;
            
            
    //         // Get data from repository
    //         List<Map<String, Object>> results = kpiTlActualRepository.getElementId(validLoginflid, empId, loginlevel);
            
    //         // Convert Map to String[]
    //         List<String[]> userDatas = new ArrayList<>();
    //         for (Map<String, Object> row : results) {
    //             String[] stringRow = new String[5]; // 5 columns
                
    //             // Map columns by name
    //             stringRow[0] = row.get("FNLN_ELEMENTID") != null ? row.get("FNLN_ELEMENTID").toString() : null;
    //             stringRow[1] = row.get("FNLN_KEYID") != null ? row.get("FNLN_KEYID").toString() : null;
    //             stringRow[2] = row.get("ROLE_LEVEL") != null ? row.get("ROLE_LEVEL").toString() : null;
    //             stringRow[3] = row.get("ROLE_NAME") != null ? row.get("ROLE_NAME").toString() : null;
    //             stringRow[4] = row.get("ROLE_KEYID") != null ? row.get("ROLE_KEYID").toString() : null;
                
    //             userDatas.add(stringRow);
    //         }
            
    //         logger.info("Found {} element records", userDatas.size());
    //         return userDatas;
            
    //     } catch (Exception e) {
    //         logger.error("Error getting element IDs: {}", e.getMessage(), e);
    //         throw new Exception("Failed to get element IDs: " + e.getMessage(), e);
    //     }
    // }

 @Override
    public List<Map<String, Object>> getElementIdAsMap(String loginflid, Integer loginlevel, 
                                                        String loginElementid, String empId) 
            throws Exception {
        try {
            logger.info("Getting element IDs for empId: {}, loginlevel: {}, loginflid: {}", 
                       empId, loginlevel, loginflid);
            
           String validLoginflid = ValidationUtil.isValidKeyId(loginflid) ? loginflid : null;
            
            // Get data from repository
            List<Map<String, Object>> elementIds = kpiTlActualRepository.getElementId(validLoginflid, empId, loginlevel);
            
            logger.info("Found {} element records", elementIds.size());
            return elementIds;
            
        } catch (Exception e) {
            logger.error("Error getting element IDs: {}", e.getMessage(), e);
            throw new Exception("Failed to get element IDs: " + e.getMessage(), e);
        }
    }
}