package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.BAL_BdmTlWhywhyRequest;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.BAL_BdmTlWhywhydtl;
import com.akranta.perfex_sb.model.BAL_BdmTlWhywhymst;
import com.akranta.perfex_sb.repository.BAL_BdmTlWhywhyLinkRepository;
import com.akranta.perfex_sb.repository.BAL_BdmTlWhywhydtlRepository;
import com.akranta.perfex_sb.repository.BAL_BdmTlWhywhymstRepository;
import com.akranta.perfex_sb.service.BAL_BdmTlWhywhyService;
import com.akranta.perfex_sb.service.DbActionTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class BAL_BdmTlWhywhyServiceImpl implements BAL_BdmTlWhywhyService {

    private static final Logger logger = LoggerFactory.getLogger(BAL_BdmTlWhywhyServiceImpl.class);

    /** Legacy placeholder stored for "empty" string columns. */
    private static final String EMPTY = "{}";

    // Master key  -> YY-2609-0002
    private static final String SEQ_MASTER = "BDM_TL_WHYWHYMST";
    private static final int LEN_MASTER = 12;
    private static final String PREFIX_MASTER = "YY";
    private static final String DATEFMT_MASTER = "YYMM";
    private static final String RESET_MASTER = " ";

    // Detail key  -> YYD-26-00001
    private static final String SEQ_DETAIL = "BDM_TL_WHYWHYDTL";
    private static final int LEN_DETAIL = 12;
    private static final String PREFIX_DETAIL = "YYD";
    private static final String DATEFMT_DETAIL = "YY";
    private static final String RESET_DETAIL = "Y";

    // Doc updates key -> DPD260001486
    private static final String SEQ_DOCUPDATES = "GEN_TL_DOCUPDATES";
    private static final int LEN_DOCUPDATES = 12;
    private static final String PREFIX_DOCUPDATES = "DPD";
    private static final String DATEFMT_DOCUPDATES = "YY";
    private static final String RESET_DOCUPDATES = "Y";

    private static final LocalDateTime DEFAULT_PREVDATE = LocalDateTime.of(1801, 1, 1, 0, 0);

    /** Defaults the legacy save wrote for blank columns (taken from the insert log). */
    private static final Map<String, String> STRING_DEFAULTS = Map.of(
            "tempfield1", "-",
            "tempfield2", "-",
            "tempfield3", "-",
            "sparesreplaced", "N",
            "formtype", "N");

    private static final Map<String, Character> CHAR_DEFAULTS = Map.ofEntries(
            Map.entry("isjh", 'X'),
            Map.entry("ispm", 'X'),
            Map.entry("iskk", 'X'),
            Map.entry("isopl", 'X'),
            Map.entry("ispy", 'X'),
            Map.entry("isojt", 'X'),
            Map.entry("issop", 'X'),
            Map.entry("iskzn", 'N'),
            Map.entry("ispokayoke", 'N'),
            Map.entry("iseffective", 'N'),
            Map.entry("ishdpossible", 'N'),
            Map.entry("status", 'P'),
            Map.entry("active", 'Y'));

    /** Never defaulted - must come from the caller. */
    private static final Set<String> NO_DEFAULT = Set.of("keyid", "createdby");

    /** Never overwritten on update. */
    private static final String[] MASTER_NEVER_COPY = {"keyid", "createdby", "createdon", "reportdatetime"};

    private final BAL_BdmTlWhywhymstRepository masterRepository;
    private final BAL_BdmTlWhywhydtlRepository detailRepository;
    private final BAL_BdmTlWhywhyLinkRepository linkRepository;
    private final DbActionTemplate dbActionTemplate;

    public BAL_BdmTlWhywhyServiceImpl(
            BAL_BdmTlWhywhymstRepository masterRepository,
            BAL_BdmTlWhywhydtlRepository detailRepository,
            BAL_BdmTlWhywhyLinkRepository linkRepository,
            DbActionTemplate dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.linkRepository = linkRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    // =====================================================================
    //  SAVE  (insert + update)
    // =====================================================================
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<BAL_BdmTlWhywhyRequest> saveWhyWhy(BAL_BdmTlWhywhyRequest request) throws Exception {

        BAL_BdmTlWhywhymst master = request.getMaster();
        if (master == null) {
            throw new RuntimeException("No Why Why Analysis Master Details");
        }

        boolean isInsertMode = isBlank(master.getKeyid()) || !masterRepository.existsById(master.getKeyid());
        LocalDateTime now = LocalDateTime.now();

        BAL_BdmTlWhywhymst savedMaster = isInsertMode
                ? insertMaster(master, request.getElementId(), now)
                : updateMaster(master, now);

        List<BAL_BdmTlWhywhydtl> savedDetails =
                saveDetails(request.getDetails(), savedMaster, isInsertMode, now);

        // Update the referenced document (BDM / UPM / ...) - legacy: refdoctype switch in create()
        syncReferenceDocument(savedMaster, savedDetails);

        // Legacy insertDocUpdates() - only when the analysis is first created
        if (isInsertMode) {
            insertDocUpdate(savedMaster, now);
        }

        BAL_BdmTlWhywhyRequest result = new BAL_BdmTlWhywhyRequest();
        result.setMaster(savedMaster);
        result.setDetails(savedDetails);
        result.setElementId(request.getElementId());
        result.setFormActionMode(request.getFormActionMode());
        result.setFormMode(request.getFormMode());
        result.setFormHeader(request.getFormHeader());

        logger.info("Why Why analysis {} : {}", isInsertMode ? "created" : "updated", savedMaster.getKeyid());

        return ResponseEntity
                .status(isInsertMode ? HttpStatus.CREATED : HttpStatus.OK)
                .body(result);
    }

    // =====================================================================
    //  MASTER
    // =====================================================================
    private BAL_BdmTlWhywhymst insertMaster(BAL_BdmTlWhywhymst master, String elementId, LocalDateTime now)
            throws Exception {

        if (isBlank(master.getCreatedby())) {
            throw new IllegalArgumentException("Created By is required to save a Why Why analysis");
        }

        applyDefaults(master, true);

        String newKeyid = dbActionTemplate.getSequenceNumber(
                resolveMasterSeqIdentifier(elementId), LEN_MASTER, PREFIX_MASTER, DATEFMT_MASTER, RESET_MASTER);

        if (newKeyid == null || newKeyid.trim().isEmpty()) {
            logger.error("Failed to generate the Why Why Master Key ID");
            throw new RuntimeException("Failed to generate Master Key ID");
        }

        master.setKeyid(newKeyid);
        if (master.getReportdatetime() == null) {
            master.setReportdatetime(now);
        }
        master.setCreatedon(now);
        master.setModifiedon(now);

        logger.info("Generated new Why Why Master Key ID: {}", newKeyid);
        return masterRepository.save(master);
    }

    private BAL_BdmTlWhywhymst updateMaster(BAL_BdmTlWhywhymst master, LocalDateTime now) {

        BAL_BdmTlWhywhymst existing = masterRepository.findById(master.getKeyid())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Why Why analysis not found for keyid: " + master.getKeyid()));

        applyDefaults(master, false);

        // null in the request = "not sent" -> keep what is already stored
        BeanUtils.copyProperties(master, existing, ignoreProperties(master, MASTER_NEVER_COPY));
        existing.setModifiedon(now);

        return masterRepository.save(existing);
    }

    // =====================================================================
    //  DETAIL   (legacy insertDetail)
    // =====================================================================
    private List<BAL_BdmTlWhywhydtl> saveDetails(List<BAL_BdmTlWhywhydtl> details,
                                                 BAL_BdmTlWhywhymst master,
                                                 boolean forceInsert,
                                                 LocalDateTime now) throws Exception {

        List<BAL_BdmTlWhywhydtl> savedDetails = new ArrayList<>();
        if (details == null || details.isEmpty()) {
            return savedDetails;
        }

        int slno = 1;
        for (BAL_BdmTlWhywhydtl detail : details) {

            BAL_BdmTlWhywhydtl target;

            if (forceInsert || isBlank(detail.getKeyid())) {
                // INSERT detail
                String newKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_DETAIL, LEN_DETAIL, PREFIX_DETAIL, DATEFMT_DETAIL, RESET_DETAIL);

                if (newKeyid == null || newKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate Why Why Detail Key ID");
                    throw new RuntimeException("Failed to generate Detail Key ID");
                }

                detail.setKeyid(newKeyid);
                if (isBlank(detail.getCreatedby())) {
                    detail.setCreatedby(master.getCreatedby());
                }
                detail.setCreatedon(now);
                target = detail;
            } else {
                // UPDATE detail
                target = detailRepository.findById(detail.getKeyid())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Why Why detail not found for keyid: " + detail.getKeyid()));
            }

            target.setWwmsKeyid(master.getKeyid());
            target.setSlno(BigDecimal.valueOf(slno++));
            target.setWhy(orEmpty(detail.getWhy()));
            target.setAnswer(orEmpty(detail.getAnswer()));
            target.setAction(orEmpty(detail.getAction()));
            target.setModifiedon(now);

            savedDetails.add(detailRepository.save(target));
        }
        return savedDetails;
    }

    // =====================================================================
    //  REFERENCE DOCUMENT UPDATE   (legacy refdoctype switch in create())
    // =====================================================================
    private void syncReferenceDocument(BAL_BdmTlWhywhymst master, List<BAL_BdmTlWhywhydtl> details) {

        String refDocNo = master.getRefdocno();
        String refDocType = effectiveRefDocType(master);

        if (isBlank(refDocNo) || isBlank(refDocType)) {
            return;
        }

        switch (refDocType) {
            case "BDM" -> updateBreakdownFromWhyWhy(master, details);

            // SQLs for these were not part of the migrated code yet
            case "UPM", "KZN", "CMC", "IMT", "DOC", "TCD", "SFT" ->
                    logger.warn("Reference document update for type {} ({}) is not migrated yet",
                            refDocType, refDocNo);

            default -> logger.debug("No reference document update for type {}", refDocType);
        }
    }

    /** Legacy: BAL_BdmTlMstSql.updateYYSql(...) + getClassification(...) */
    // private void updateBreakdownFromWhyWhy(BAL_BdmTlWhywhymst master, List<BAL_BdmTlWhywhydtl> details) {

    //     // classification lookup (only when a root cause text exists)
    //     String classificationId = null;
    //     if (!isBlank(master.getRootcause())) {
    //         List<String> ids = linkRepository.findClassificationIds(
    //                 master.getRootcause(), validOrNull(master.getTargetpillarid()));
    //         if (!ids.isEmpty()) {
    //             classificationId = ids.get(0);
    //         }
    //     }

    //     // root cause / counter measure come from the LAST why row
    //     String rootCause = null;
    //     String counterMeasure = null;
    //     if (details != null && !details.isEmpty()) {
    //         BAL_BdmTlWhywhydtl last = details.get(details.size() - 1);
    //         rootCause = validOrNull(last.getAnswer());
    //         counterMeasure = validOrNull(last.getAction());
    //     }

    //     int rows = linkRepository.updateBreakdownDetail(
    //             master.getRefdocno(),
    //             master.getKeyid(),
    //             validOrNull(master.getRootcauseid()),
    //             rootCause,
    //             counterMeasure,
    //             classificationId,
    //             validOrNull(master.getCountermeasure()));

    //     logger.info("BAL_BDM_TL_DTL updated for {} : {} row(s)", master.getRefdocno(), rows);
    // }
//     private void updateBreakdownFromWhyWhy(BAL_BdmTlWhywhymst master, List<BAL_BdmTlWhywhydtl> details) {

//     String classificationId = null;
//     if (!isBlank(master.getRootcause())) {
//         List<String> ids = linkRepository.findClassificationIds(
//                 master.getRootcause(), validOrNull(master.getTargetpillarid()));
//         if (!ids.isEmpty()) {
//             classificationId = ids.get(0);
//         }
//     }

//     // root cause conclusion still comes from the last why row
//     String rootCause = null;
//     if (details != null && !details.isEmpty()) {
//         rootCause = validOrNull(details.get(details.size() - 1).getAnswer());
//     }

//     int rows = linkRepository.updateBreakdownDetail(
//             master.getRefdocno(),
//             master.getKeyid(),
//             validOrNull(master.getRootcauseid()),
//             rootCause,
//             validOrNull(master.getCountermeasure()),      // ✅ now goes to bdan_countermeasure
//             classificationId,
//             validOrNull(master.getPreventivemeasure()));  // ✅ now goes to bdan_preventivemeasure

//     logger.info("BAL_BDM_TL_DTL updated for {} : {} row(s)", master.getRefdocno(), rows);
// }
private void updateBreakdownFromWhyWhy(BAL_BdmTlWhywhymst master, List<BAL_BdmTlWhywhydtl> details) {

    String classificationId = null;
    if (!isBlank(master.getRootcause())) {
        List<String> ids = linkRepository.findClassificationIds(
                master.getRootcause(), validOrNull(master.getTargetpillarid()));
        if (!ids.isEmpty()) {
            classificationId = ids.get(0);
        }
    }

    int rows = linkRepository.updateBreakdownDetail(
            master.getRefdocno(),
            master.getKeyid(),
            validOrNull(master.getRootcauseid()),
            validOrNull(master.getRootcause()),           // ✅ from master's Root Cause box, not the last why-answer
            validOrNull(master.getCountermeasure()),
            classificationId,
            validOrNull(master.getPreventivemeasure()));

    logger.info("BAL_BDM_TL_DTL updated for {} : {} row(s)", master.getRefdocno(), rows);
}
    // =====================================================================
    //  DOC UPDATES   (legacy insertDocUpdates)
    // =====================================================================
    private void insertDocUpdate(BAL_BdmTlWhywhymst master, LocalDateTime now) throws Exception {

        String docKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_DOCUPDATES, LEN_DOCUPDATES, PREFIX_DOCUPDATES, DATEFMT_DOCUPDATES, RESET_DOCUPDATES);

        linkRepository.insertDocUpdate(
                docKeyid,
                EMPTY,                                   // feedback id
                orEmpty(effectiveRefDocType(master)),    // ref doc type
                orEmpty(master.getRefdocno()),           // ref doc id
                EMPTY,                                   // update doc type
                EMPTY,                                   // detail id
                master.getCreatedby(),
                now);
    }

    // =====================================================================
    //  HELPERS
    // =====================================================================

    /**
     * Legacy: CommonFunctions.getSeqnoLocationIdentifier(elementId, TBL_BAL_BDM_TL_WHYWHYMST)
     * The legacy log shows the identifier as table name + location code
     * (e.g. BAL_BDM_TL_WHYWHYMSTLCN0000001). Replace the body with your
     * project's equivalent if the YY sequence has to run per location.
     */
    private String resolveMasterSeqIdentifier(String elementId) {
        return SEQ_MASTER;
    }

    /** Legacy: refdocno starting with "TCD" forces the ref doc type to TCD (master column itself is unchanged). */
    private String effectiveRefDocType(BAL_BdmTlWhywhymst master) {
        String refDocNo = master.getRefdocno();
        if (refDocNo != null && refDocNo.startsWith("TCD")) {
            return "TCD";
        }
        return master.getRefdoctype();
    }

    /**
     * Fills the legacy placeholders.
     * insert = true  : null and blank values get the default
     * insert = false : only blank (non-null) values get the default; null = keep stored value
     */
    private void applyDefaults(BAL_BdmTlWhywhymst master, boolean insert) {

        BeanWrapper bw = new BeanWrapperImpl(master);

        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            String name = pd.getName();
            if (pd.getWriteMethod() == null || pd.getReadMethod() == null || NO_DEFAULT.contains(name)) {
                continue;
            }

            Class<?> type = pd.getPropertyType();
            Object value = bw.getPropertyValue(name);

            if (type == String.class) {
                String s = (String) value;
                if ((s == null && insert) || (s != null && isBlank(s))) {
                    bw.setPropertyValue(name, STRING_DEFAULTS.getOrDefault(name, EMPTY));
                }
            } else if (type == Character.class) {
                Character def = CHAR_DEFAULTS.get(name);
                Character c = (Character) value;
                boolean blank = c != null && (c == '\0' || Character.isWhitespace(c));
                if (def != null && ((c == null && insert) || blank)) {
                    bw.setPropertyValue(name, def);
                }
            }
        }

        if (insert) {
            if (master.getDate() == null) {
                master.setDate(LocalDateTime.now());
            }
            if (master.getPrevdate() == null) {
                master.setPrevdate(DEFAULT_PREVDATE);
            }
        }
    }

    /** Property names to skip while copying: fixed ones + every property that is null in the source. */
    private String[] ignoreProperties(Object source, String... fixed) {
        BeanWrapper bw = new BeanWrapperImpl(source);
        Set<String> names = new HashSet<>(Arrays.asList(fixed));
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            if (bw.isReadableProperty(pd.getName()) && bw.getPropertyValue(pd.getName()) == null) {
                names.add(pd.getName());
            }
        }
        return names.toArray(new String[0]);
    }

    /** Legacy: CommonFunctions.isValidKeyId (null / empty / "{}" / "undefined" are not valid). */
    private static boolean isBlank(String value) {
        if (value == null) {
            return true;
        }
        String t = value.trim();
        return t.isEmpty() || t.equals(EMPTY) || t.equalsIgnoreCase("undefined");
    }

    private static String validOrNull(String value) {
        return isBlank(value) ? null : value;
    }

    private static String orEmpty(String value) {
        return isBlank(value) ? EMPTY : value;
    }
    @Override
    public List<String> getYYRefKeyid(String refDocId) throws Exception {
        if (isBlank(refDocId)) {
            logger.warn("getYYRefKeyid called with blank refDocId");
            return List.of();
        }
        return masterRepository.getYYRefKeyid(refDocId);
    }
     @Override
    public BAL_BdmTlWhywhymst selectMasKeyid(String keyid) throws Exception {
        if (isBlank(keyid)) {
            logger.warn("selectMasKeyid called with blank keyid");
            return null;
        }
        return masterRepository.selectMasKeyid(keyid);
    }

    @Override
@Transactional(readOnly = true)
public List<Map<String, Object>> getRootCause(String openMode) throws Exception {
    logger.info("Fetching root cause data for openMode: {}", openMode);
    
    // Handle null or empty openMode
    String mode = (openMode == null || openMode.trim().isEmpty()) ? "BDM" : openMode;
    
    List<Map<String, Object>> result = masterRepository.getRootCauseBySql(mode);
    
    if (result == null || result.isEmpty()) {
        logger.warn("No root cause data found for openMode: {}", mode);
        return new ArrayList<>();
    }
    
    logger.info("Successfully retrieved {} root cause records for openMode: {}", 
                result.size(), mode);
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
}
