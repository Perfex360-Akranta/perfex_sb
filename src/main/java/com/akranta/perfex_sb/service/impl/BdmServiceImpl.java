package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.BdmDto;
import com.akranta.perfex_sb.dto.BreakdownAllocationMobileDTO;
import com.akranta.perfex_sb.dto.BreakdownBookingMobileDTO;
import com.akranta.perfex_sb.dto.BreakdownCompletionMobileDTO;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.BAL_BdmTlDtl;
import com.akranta.perfex_sb.model.BAL_BdmTlMst;
import com.akranta.perfex_sb.model.BAL_BdmTlMultipleResp;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostactual;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostactualId;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostplan;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostplanId;
import com.akranta.perfex_sb.model.BAL_WomTlOthercostactual;
import com.akranta.perfex_sb.model.BAL_WomTlOthercostactualId;
import com.akranta.perfex_sb.model.BAL_WomTlOthercostplan;
import com.akranta.perfex_sb.model.BAL_WomTlOthercostplanId;
import com.akranta.perfex_sb.model.BAL_WomTlServicecostactual;
import com.akranta.perfex_sb.model.BAL_WomTlServicecostactualId;
import com.akranta.perfex_sb.model.BAL_WomTlServicecostplan;
import com.akranta.perfex_sb.model.BAL_WomTlServicecostplanId;
import com.akranta.perfex_sb.model.BAL_WomTlSparecostactual;
import com.akranta.perfex_sb.model.BAL_WomTlSparecostactualId;
import com.akranta.perfex_sb.model.BAL_WomTlSparecostplan;
import com.akranta.perfex_sb.model.BAL_WomTlSparecostplanId;
import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostactual;
import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostactualId;
import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostplan;
import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostplanId;
import com.akranta.perfex_sb.model.WomTlCommunicationlog;
import com.akranta.perfex_sb.model.WomTlWomst;
import com.akranta.perfex_sb.repository.BAL_BdmTlDtlRepository;
import com.akranta.perfex_sb.repository.BAL_BdmTlMstRepository;
import com.akranta.perfex_sb.repository.BAL_BdmTlMultipleRespRepository;
import com.akranta.perfex_sb.repository.BreakdownBookingMobileRepo;
import com.akranta.perfex_sb.repository.BAL_WomTlManpowercostactualRepository;
import com.akranta.perfex_sb.repository.BAL_WomTlManpowercostplanRepository;
import com.akranta.perfex_sb.repository.BAL_WomTlOthercostactualRepository;
import com.akranta.perfex_sb.repository.BAL_WomTlOthercostplanRepository;
import com.akranta.perfex_sb.repository.BAL_WomTlServicecostactualRepository;
import com.akranta.perfex_sb.repository.BAL_WomTlServicecostplanRepository;
import com.akranta.perfex_sb.repository.BAL_WomTlSparecostactualRepository;
import com.akranta.perfex_sb.repository.BAL_WomTlSparecostplanRepository;
import com.akranta.perfex_sb.repository.BAL_WomTlUtilitycostactualRepository;
import com.akranta.perfex_sb.repository.BAL_WomTlUtilitycostplanRepository;
import com.akranta.perfex_sb.repository.WomTlWomstRepository;
import com.akranta.perfex_sb.repository.wom_tl_communicationlogRepository;
import com.akranta.perfex_sb.service.BdmService;
import com.akranta.perfex_sb.service.DbActionTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


import java.util.List;
import java.util.Map;



@Service
public class BdmServiceImpl implements BdmService {

    private static final Logger logger = LoggerFactory.getLogger(BdmServiceImpl.class);
    private static final LocalDate FUTURE_NULL_DATE = LocalDate.of(2100, 12, 31);
    private static final LocalDate PAST_NULL_DATE   = LocalDate.of(1801, 1, 1);
   

    // ── Sequence identifiers (used by DbActionTemplate) ─────────────────────
    private static final String SEQ_MASTER  = "BAL_BDM_TL_MST";
    private static final String SEQ_DETAIL  = "BAL_BDM_TL_DTL";
    private static final String SEQ_WOM     = "BAL_WOM_TL_WOMST";
     private static final String SEQ_COMM    = "WOM_TL_COMMUNICATIONLOG";

      // added here by priyanka  on 18/09/2026
    private static final String SEQ_MULTI_RESP = "BAL_BDM_TL_MULTIPLE_RESP";
    // end

    private static final int    KEY_LEN_MASTER = 20;
    private static final int    KEY_LEN_DETAIL = 14;
    private static final int    KEY_LEN_WOM    = 15;
     private static final int KEY_LEN_COMM = 11;

    private static final String PREFIX_MASTER = "BDM";
    private static final String PREFIX_DETAIL = "BDA";
    private static final String PREFIX_WOM    = "MW";
    private static final String PREFIX_COMM = "CMC";

    private static final String DATE_FORMAT  = "YY";
    private static final String FORMAT_RESET = "Y";

    // Default machine calendar time (minutes) – matches legacy hardcoded 480
    private static final BigDecimal DEFAULT_CAL_TIME = BigDecimal.valueOf(480);

    // Config key for YY-analysis mandatory flag
    private static final String CONFIG_ISYYMANDATRY = "ISYYMANDATRY";

    private static final String EMPTY_PLACEHOLDER = "{}";
    private static final LocalDateTime PAST_NULL_DATETIME = LocalDateTime.of(1801, 1, 1, 0, 0);
    private static final LocalDateTime FUTURE_NULL_DATETIME = LocalDateTime.of(2100, 12, 31, 0, 0);

    // ── Dependencies ─────────────────────────────────────────────────────────
    private final BAL_BdmTlMstRepository masterRepository;
    private final BAL_BdmTlDtlRepository detailRepository;
    private final WomTlWomstRepository   womRepository;
    private final BAL_WomTlManpowercostplanRepository manpowercostplanRepository; 
    private final BAL_WomTlManpowercostactualRepository manpowercostactualRepository; 
    private final BAL_WomTlSparecostplanRepository sparecostplanRepository;
    private final BAL_WomTlServicecostplanRepository servicecostplanRepository;
    private final BAL_WomTlUtilitycostplanRepository utilitycostplanRepository;
    private final BAL_WomTlOthercostplanRepository othercostplanRepository; 
    private final BAL_WomTlSparecostactualRepository sparecostactualRepository; 
    private final BAL_WomTlServicecostactualRepository servicecostactualRepository;
    private final BAL_WomTlUtilitycostactualRepository   utilitycostactualRepository;
     private final BAL_WomTlOthercostactualRepository     othercostactualRepository;
private final wom_tl_communicationlogRepository communicationRepository;
    private final BreakdownBookingMobileRepo breakdownBookingMobileRepo;
    private final DbActionTemplate       dbActionTemplate;
     // added here by priyanka on 18/09/2026
    private final BAL_BdmTlMultipleRespRepository multiRespRepo;
    // end

     

    public BdmServiceImpl(BAL_BdmTlMstRepository masterRepository,
                          BAL_BdmTlDtlRepository detailRepository,
                          WomTlWomstRepository   womRepository,
                          BAL_WomTlManpowercostplanRepository manpowercostplanRepository,
                           BAL_WomTlManpowercostactualRepository manpowercostactualRepository,
                           BAL_WomTlSparecostplanRepository sparecostplanRepository,
                           BAL_WomTlServicecostplanRepository servicecostplanRepository,
                            BAL_WomTlUtilitycostplanRepository utilitycostplanRepository,
                            BAL_WomTlOthercostplanRepository othercostplanRepository, 
                            BAL_WomTlSparecostactualRepository sparecostactualRepository,
                            BAL_WomTlServicecostactualRepository servicecostactualRepository,
                            BAL_WomTlUtilitycostactualRepository utilitycostactualRepository,
                              BAL_WomTlOthercostactualRepository othercostactualRepository,
                              BAL_BdmTlMultipleRespRepository multiRespRepo,    // added by priyanka on 18/09/2026   
                              wom_tl_communicationlogRepository communicationRepository,
                              BreakdownBookingMobileRepo breakdownBookingMobileRepo,
                                DbActionTemplate       dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.manpowercostactualRepository = manpowercostactualRepository; 
        this.womRepository    = womRepository;
        this.manpowercostplanRepository = manpowercostplanRepository; 
         this.sparecostplanRepository = sparecostplanRepository;
         this.servicecostplanRepository = servicecostplanRepository;
         this.utilitycostplanRepository = utilitycostplanRepository;
         this.othercostplanRepository = othercostplanRepository;
         this.sparecostactualRepository = sparecostactualRepository; 
         this.servicecostactualRepository = servicecostactualRepository;
         this.utilitycostactualRepository = utilitycostactualRepository;
         this.othercostactualRepository = othercostactualRepository;
         this.communicationRepository = communicationRepository;
         // added here by priyanka on 18/09/2026                        
        this.multiRespRepo    = multiRespRepo;
        //end
         this.breakdownBookingMobileRepo = breakdownBookingMobileRepo;

        this.dbActionTemplate = dbActionTemplate;
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  SINGLE SAVE METHOD  –  INSERT when keyid is blank, UPDATE otherwise
    // ═════════════════════════════════════════════════════════════════════════
    @Override
    @Transactional
    public ResponseEntity<BdmDto> saveBdm(BdmDto request) throws Exception {

        BAL_BdmTlMst master = request.getMaster();
        BAL_BdmTlDtl detail = request.getDetail();

        if (master == null) {
            throw new RuntimeException("BDM Master data is required");
        }

        // ── Decide INSERT vs UPDATE ──────────────────────────────────────────
        boolean isInsert = (master.getKeyid() == null || master.getKeyid().trim().isEmpty());

        if (isInsert) {
            return doInsert(master, detail, request);
        } else {
            return doUpdate(master, detail, request);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<BdmDto> saveBreakdownBooking(BreakdownBookingMobileDTO request) throws Exception {
        if (request == null) {
            throw new IllegalArgumentException("Booking request is required");
        }
        if (isBlankOrPlaceholder(request.getMachineid()) && isBlankOrPlaceholder(request.getFlid())) {
            throw new IllegalArgumentException("machineid or flid is required");
        }
        if (isBlankOrPlaceholder(request.getProblemDescription())) {
            throw new IllegalArgumentException("Problem Description is required");
        }
        if (isBlankOrPlaceholder(request.getPriority())) {
            throw new IllegalArgumentException("Priority is required");
        }
        if (isBlankOrPlaceholder(request.getAssemblyid())) {
            throw new IllegalArgumentException("Assembly / Station is required");
        }
        if (isBlankOrPlaceholder(request.getBookedby())) {
            throw new IllegalArgumentException("Booked By is required");
        }

        Map<String, Object> hierarchy = breakdownBookingMobileRepo.findEquipmentHierarchy(
                blankToNull(request.getMachineid()),
                blankToNull(request.getFlid()));

        if (hierarchy == null || hierarchy.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Equipment hierarchy not found in gen_vw_fnln1 for machineid="
                            + request.getMachineid() + ", flid=" + request.getFlid());
        }

        String factoryid = requiredHierarchyValue(hierarchy, "factoryid");
        String sectionid = requiredHierarchyValue(hierarchy, "sectionid");
        String cellid = placeholderIfBlank(asString(hierarchy.get("cellid")));
        String machineid = requiredHierarchyValue(hierarchy, "machineid");
        String elementid = requiredHierarchyValue(hierarchy, "elementid");
        String flid = requiredHierarchyValue(hierarchy, "flid");

        LocalDateTime occurredOn = parseOccurredOn(request.getOccurredOn());
        LocalDateTime now = LocalDateTime.now();
        String bookedby = request.getBookedby().trim();
        String shiftid = breakdownBookingMobileRepo.findCurrentShiftId(factoryid);
        if (isBlankOrPlaceholder(shiftid)) {
            shiftid = EMPTY_PLACEHOLDER;
        }

        BAL_BdmTlMst master = new BAL_BdmTlMst();
        master.setKeyid(blankToNull(request.getKeyid()));
        master.setEntrydate(occurredOn.toLocalDate().atStartOfDay());
        master.setShiftid(shiftid);
        master.setFactoryid(factoryid);
        master.setSectionid(sectionid);
        master.setCellid(cellid);
        master.setMachineid(machineid);
        master.setAssemblyid(request.getAssemblyid().trim());
        master.setPartlocationid(EMPTY_PLACEHOLDER);
        master.setAlarmdescription(EMPTY_PLACEHOLDER);
        master.setBdtype('E');
        master.setReporteddate(occurredOn);
        master.setReceiveddate(now);
        master.setWostarttime(PAST_NULL_DATETIME);
        master.setWoendtime(FUTURE_NULL_DATETIME);
        master.setBreaktime(BigDecimal.ZERO);
        master.setActualworktime(BigDecimal.ZERO);
        master.setDowntime(BigDecimal.ZERO);
        master.setProdaccepdate(FUTURE_NULL_DATETIME);
        master.setBookedphenomena(EMPTY_PLACEHOLDER);
        master.setPhenomenadescription(EMPTY_PLACEHOLDER);
        master.setBookedcause(EMPTY_PLACEHOLDER);
        master.setFinalphenomena(EMPTY_PLACEHOLDER);
        master.setFinalcause(EMPTY_PLACEHOLDER);
        master.setBookedtrade(EMPTY_PLACEHOLDER);
        master.setFinaltrade(EMPTY_PLACEHOLDER);
        master.setProblemdescription(request.getProblemDescription().trim());
        master.setIsbdlocked('X');
        master.setShiftincharge(EMPTY_PLACEHOLDER);
        master.setStatus('X');
        master.setBookedby(bookedby);
        master.setRemarks(EMPTY_PLACEHOLDER);
        master.setBookingtype("ONL");
        master.setBdrelatedto("MCH");
        master.setWno(EMPTY_PLACEHOLDER);
        master.setSpareid(EMPTY_PLACEHOLDER);
        master.setPriority(firstChar(request.getPriority(), 'X'));
        master.setWoallottedflag('N');
        master.setWostartflag('N');
        master.setWoendflag('N');
        master.setWoprodaccepflag('N');
        master.setSubassemblyid(placeholderIfBlank(request.getSubassemblyid()));
        master.setRepeatedbdflag("N");
        master.setRepeatedbdno(EMPTY_PLACEHOLDER);
        master.setRelatedto("MCH");
        master.setMould(EMPTY_PLACEHOLDER);
        master.setElementid(elementid);
        master.setFlid(flid);
        master.setProcessid(EMPTY_PLACEHOLDER);
        master.setIsstandby('N');
        master.setStandbyequipment(EMPTY_PLACEHOLDER);
        master.setBreakdowntime(BigDecimal.ZERO);
        master.setProductionstop('-');
        master.setImmediateaction(EMPTY_PLACEHOLDER);
        master.setCompleteddate(FUTURE_NULL_DATETIME);
        master.setProblemreason("-");
        master.setActivity('B');
        master.setOtherphenomena(EMPTY_PLACEHOLDER);
        master.setTempfield7('-');
        master.setActive('Y');
        master.setCreatedby(bookedby);
        master.setCreatedon(now);
        master.setModifiedon(now);

        BAL_BdmTlDtl detail = buildBookingStubDetail(bookedby, now);

        BdmDto bdmDto = new BdmDto();
        bdmDto.setMaster(master);
        bdmDto.setDetail(detail);
        bdmDto.setFormMode("MOBILE");
        bdmDto.setFormActionMode("BOOKING");

        logger.info("Mobile booking save – factoryid={}, sectionid={}, cellid={}, machineid={}, flid={}, elementid={}",
                factoryid, sectionid, cellid, machineid, flid, elementid);

        return saveBdm(bdmDto);
    }

    @Override
    @Transactional
    public ResponseEntity<BdmDto> saveBreakdownAllocation(BreakdownAllocationMobileDTO request) throws Exception {
        if (request == null) {
            throw new IllegalArgumentException("Allocation request is required");
        }
        if (isBlankOrPlaceholder(request.getKeyid())) {
            throw new IllegalArgumentException("BDM keyid is required");
        }
        if (isBlankOrPlaceholder(request.getSectionid())) {
            throw new IllegalArgumentException("Maint. Section / Trade is required");
        }
        if (isBlankOrPlaceholder(request.getStartdate())) {
            throw new IllegalArgumentException("Start date is required");
        }
        if (isBlankOrPlaceholder(request.getEnddate())) {
            throw new IllegalArgumentException("End date is required");
        }

        String keyid = request.getKeyid().trim();
        String tradeid = request.getSectionid().trim();
        LocalDateTime startDate = parseOccurredOn(request.getStartdate());
        LocalDateTime endDate = parseOccurredOn(request.getEnddate());

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date/time must be after start date/time");
        }

        LocalDateTime now = LocalDateTime.now();

        BAL_BdmTlMst master = new BAL_BdmTlMst();
        master.setKeyid(keyid);
        master.setBookedtrade(tradeid);
        master.setFinaltrade(tradeid);
        master.setWostarttime(startDate);
        master.setWoendtime(endDate);
        master.setStatus('X');
        master.setWoallottedflag('Y');
        master.setWostartflag('N');
        master.setWoendflag('N');
        master.setWoprodaccepflag('N');
        master.setReceiveddate(now);
        master.setModifiedon(now);

        BAL_BdmTlDtl detail = null;
        List<BAL_BdmTlDtl> details = detailRepository.findByBdmsKeyidNative(keyid);
        if (details != null && !details.isEmpty()) {
            detail = details.get(0);
            detail.setTradeid(tradeid);
            detail.setModifiedon(now);
        }

        BdmDto bdmDto = new BdmDto();
        bdmDto.setMaster(master);
        bdmDto.setDetail(detail);
        bdmDto.setFormMode("MOBILE");
        bdmDto.setFormActionMode("ALLOCATION");

        logger.info("Mobile allocation save – keyid={}, tradeid={}, start={}, end={}",
                keyid, tradeid, startDate, endDate);

        return saveBdm(bdmDto);
    }

    @Override
    @Transactional
    public ResponseEntity<BdmDto> saveBreakdownCompletion(BreakdownCompletionMobileDTO request) throws Exception {
        if (request == null) {
            throw new IllegalArgumentException("Completion request is required");
        }
        if (isBlankOrPlaceholder(request.getKeyid())) {
            throw new IllegalArgumentException("BDM keyid is required");
        }
        if (isBlankOrPlaceholder(request.getCompletedby())) {
            throw new IllegalArgumentException("Completed By is required");
        }
        if (isBlankOrPlaceholder(request.getCompleteddate())) {
            throw new IllegalArgumentException("Completed date is required");
        }

        String keyid = request.getKeyid().trim();
        String completedby = request.getCompletedby().trim();
        LocalDateTime completedDate = parseOccurredOn(request.getCompleteddate());
        LocalDateTime now = LocalDateTime.now();

        BAL_BdmTlMst master = new BAL_BdmTlMst();
        master.setKeyid(keyid);
        master.setStatus('C');
        master.setWoendflag('Y');
        master.setWostartflag('Y');
        master.setWoprodaccepflag('Y');
        master.setCompleteddate(completedDate);
        master.setProdaccepdate(completedDate);
        master.setModifiedon(now);

        BAL_BdmTlDtl detail = null;
        List<BAL_BdmTlDtl> details = detailRepository.findByBdmsKeyidNative(keyid);
        if (details != null && !details.isEmpty()) {
            detail = details.get(0);
            detail.setCompletedby(completedby);
            detail.setModifiedon(now);
        }

        BdmDto bdmDto = new BdmDto();
        bdmDto.setMaster(master);
        bdmDto.setDetail(detail);
        bdmDto.setFormMode("MOBILE");
        bdmDto.setFormActionMode("COMPLETION");

        logger.info("Mobile completion save – keyid={}, completedby={}, completeddate={}",
                keyid, completedby, completedDate);

        return saveBdm(bdmDto);
    }

    private BAL_BdmTlDtl buildBookingStubDetail(String bookedby, LocalDateTime now) {
        BAL_BdmTlDtl detail = new BAL_BdmTlDtl();
        detail.setFinalphenomena(EMPTY_PLACEHOLDER);
        detail.setFinalcause(EMPTY_PLACEHOLDER);
        detail.setFinalaction(EMPTY_PLACEHOLDER);
        detail.setTradeid(EMPTY_PLACEHOLDER);
        detail.setCountermeasure(EMPTY_PLACEHOLDER);
        detail.setWwrequired('N');
        detail.setWwno(EMPTY_PLACEHOLDER);
        detail.setRootcause(EMPTY_PLACEHOLDER);
        detail.setPreventivemeasure(EMPTY_PLACEHOLDER);
        detail.setRootcauseid(EMPTY_PLACEHOLDER);
        detail.setCountermeasureid(EMPTY_PLACEHOLDER);
        detail.setPreventivemeasureid(EMPTY_PLACEHOLDER);
        detail.setBreakdowntime(BigDecimal.ZERO);
        detail.setWorktime(BigDecimal.ZERO);
        detail.setClassificationid(EMPTY_PLACEHOLDER);
        detail.setCategoryid(EMPTY_PLACEHOLDER);
        detail.setIssparesreplaced('N');
        detail.setAlarmno(EMPTY_PLACEHOLDER);
        detail.setManpowercost(BigDecimal.ZERO);
        detail.setContractorcost(BigDecimal.ZERO);
        detail.setSparescost(BigDecimal.ZERO);
        detail.setOthercost(BigDecimal.ZERO);
        detail.setStatus('X');
        detail.setRemarks(EMPTY_PLACEHOLDER);
        detail.setActiontakenby(EMPTY_PLACEHOLDER);
        detail.setCompletedby(EMPTY_PLACEHOLDER);
        detail.setCostcentre(EMPTY_PLACEHOLDER);
        detail.setErppoststatus('X');
        detail.setProblemseverity("N");
        detail.setFailuretype(EMPTY_PLACEHOLDER);
        detail.setIsapproved('X');
        detail.setApproverdby(EMPTY_PLACEHOLDER);
        detail.setErpnumber("0");
        detail.setOtherfailuretype(EMPTY_PLACEHOLDER);
        detail.setActive('Y');
        detail.setCreatedby(bookedby);
        detail.setCreatedon(now);
        detail.setModifiedon(now);
        return detail;
    }

    private String requiredHierarchyValue(Map<String, Object> hierarchy, String column) {
        String value = asString(hierarchy.get(column));
        if (isBlankOrPlaceholder(value)) {
            throw new ResourceNotFoundException(
                    "Equipment hierarchy is missing " + column + " in gen_vw_fnln1");
        }
        return value;
    }

    private String asString(Object value) {
        return value == null ? null : value.toString().trim();
    }

    private String blankToNull(String value) {
        return isBlankOrPlaceholder(value) ? null : value.trim();
    }

    private String placeholderIfBlank(String value) {
        return isBlankOrPlaceholder(value) ? EMPTY_PLACEHOLDER : value.trim();
    }

    private Character firstChar(String value, char fallback) {
        if (isBlankOrPlaceholder(value)) {
            return fallback;
        }
        return value.trim().charAt(0);
    }

    private LocalDateTime parseOccurredOn(String occurredOn) {
        if (isBlankOrPlaceholder(occurredOn)) {
            return LocalDateTime.now();
        }
        String value = occurredOn.trim();
        DateTimeFormatter[] formatters = new DateTimeFormatter[] {
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        };
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
                // try next pattern
            }
        }
        try {
            return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid occurredOn date: " + occurredOn);
        }
    }

    // =========================================================================
    //  INSERT PATH
    // =========================================================================
    private ResponseEntity<BdmDto> doInsert(BAL_BdmTlMst master,
                                                  BAL_BdmTlDtl detail,
                                                  BdmDto   request) throws Exception {

        // ── 1. Apply ISYYMANDATRY config logic (mirrors legacy) ──────────────
        if (detail != null && detail.getErppoststatus() != null
                && detail.getErppoststatus() == 'C') {

            String yyMand = masterRepository.getConfigValue(CONFIG_ISYYMANDATRY);
            if ("Y".equals(yyMand)) {
                detail.setErppoststatus('W');
                logger.info("ISYYMANDATRY=Y: overriding erppoststatus from C to W");
            }
        }

        // ── 2. Generate BDM Master key ───────────────────────────────────────
        String masterKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_MASTER, KEY_LEN_MASTER, PREFIX_MASTER, DATE_FORMAT, FORMAT_RESET);

        if (masterKeyid == null || masterKeyid.trim().isEmpty()) {
            throw new RuntimeException("Failed to generate BDM Master Key ID");
        }
        master.setKeyid(masterKeyid);
        logger.info("Generated BDM Master Key ID: {}", masterKeyid);

        // ── 3. Work Order: insert new WOM record ─────────────────────────────
        //WomTlWomst wom = buildWorkOrder(master);
        WomTlWomst wom = buildWorkOrder(master, detail);


        String womKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_WOM, KEY_LEN_WOM, PREFIX_WOM, DATE_FORMAT, FORMAT_RESET);

        if (womKeyid == null || womKeyid.trim().isEmpty()) {
            throw new RuntimeException("Failed to generate WOM Key ID");
        }
        wom.setKeyid(womKeyid);
        master.setWno(womKeyid);   // bdms_wno <- woms_keyid
        logger.info("Generated WOM Key ID: {}", womKeyid);

        WomTlWomst savedWom = womRepository.save(wom);
        logger.info("Saved WOM Work Order: {}", womKeyid);

        // ── 4. Save BDM Master ───────────────────────────────────────────────
        if (master.getCreatedon() == null) {
            master.setCreatedon(LocalDateTime.now());
        }
        master.setModifiedon(LocalDateTime.now());
        master.setActive('Y');

        BAL_BdmTlMst savedMaster = masterRepository.save(master);
        logger.info("Saved BDM Master: {}", masterKeyid);

        // ── 5. Save BDM Detail + Machine Calendar Time ───────────────────────
        BAL_BdmTlDtl savedDetail = null;
        if (detail != null) {

            // Generate detail key
            String detailKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_DETAIL, KEY_LEN_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET);

            if (detailKeyid == null || detailKeyid.trim().isEmpty()) {
                throw new RuntimeException("Failed to generate BDM Detail Key ID");
            }
            detail.setKeyid(detailKeyid);
            detail.setBdms_keyid(masterKeyid);

            if (detail.getCreatedby() == null || detail.getCreatedby().trim().isEmpty()) {
                detail.setCreatedby(savedMaster.getCreatedby());
            }
            if (detail.getCreatedon() == null) {
                detail.setCreatedon(LocalDateTime.now());
            }
            detail.setModifiedon(LocalDateTime.now());
            detail.setActive('Y');

            savedDetail = detailRepository.save(detail);
            logger.info("Saved BDM Detail: {}", detailKeyid);

            // ── 5a. Insert MACHINECALTIME (native query, mirrors legacy SQL) ──
            womRepository.insertMachineCalTime(
                    savedMaster.getFactoryid(),
                    savedMaster.getSectionid(),
                    savedMaster.getCellid(),
                    savedMaster.getMachineid(),
                    savedMaster.getShiftid(),
                    savedMaster.getEntrydate(),   // bdms_entrydate -> mctm_shiftdate
                    DEFAULT_CAL_TIME
            );
            logger.info("Inserted MACHINECALTIME for master: {}", masterKeyid);
        }

        // ── 6. Build and return response ─────────────────────────────────────
        BdmDto result = buildResponse(savedMaster, savedDetail, savedWom, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // =========================================================================
 // =========================================================================
    //  UPDATE PATH
    // =========================================================================
    private ResponseEntity<BdmDto> doUpdate(BAL_BdmTlMst master,
                                                  BAL_BdmTlDtl detail,
                                                  BdmDto   request) throws Exception {

        String masterKeyid = master.getKeyid();

        // ── 1. Load existing master ──────────────────────────────────────────
        BAL_BdmTlMst existingMaster = masterRepository.findById(masterKeyid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "BDM Master not found for keyid: " + masterKeyid));

        // ── 2. Merge only non-null fields (same style as WhyWhy impl) ────────
        mergeMaster(existingMaster, master);
        existingMaster.setModifiedon(LocalDateTime.now());

        BAL_BdmTlMst savedMaster = masterRepository.save(existingMaster);
        logger.info("Updated BDM Master: {}", masterKeyid);

        // ── 3. Work Order update (if wno already exists) ─────────────────────
        WomTlWomst savedWom = null;
        String existingWno = savedMaster.getWno();

        // FIX: "{}" is used elsewhere in this class as a placeholder for "empty",
        // so it must be treated the same as null/blank here — otherwise we try
        // womRepository.findById("{}") and blow up with ResourceNotFoundException.
        if (!isBlankOrPlaceholder(existingWno)) {

            WomTlWomst existingWom = womRepository.findById(existingWno)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "WOM Work Order not found for keyid: " + existingWno));

            mergeWorkOrder(existingWom, savedMaster);
            existingWom.setModifiedon(LocalDateTime.now());

            savedWom = womRepository.save(existingWom);
            logger.info("Updated WOM Work Order: {}", existingWno);

        } else {
            // Edge case: wno missing on an existing BDM – insert a new WOM
            logger.warn("BDM {} has no wno (value='{}') – inserting a new WOM Work Order",
                    masterKeyid, existingWno);

            WomTlWomst newWom = buildWorkOrder(savedMaster, null);

            String womKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_WOM, KEY_LEN_WOM, PREFIX_WOM, DATE_FORMAT, FORMAT_RESET);

            if (womKeyid == null || womKeyid.trim().isEmpty()) {
                throw new RuntimeException("Failed to generate WOM Key ID during update fallback");
            }
            newWom.setKeyid(womKeyid);
            savedMaster.setWno(womKeyid);
            masterRepository.save(savedMaster);

            savedWom = womRepository.save(newWom);
            logger.info("Inserted fallback WOM Work Order: {}", womKeyid);
        }

        // ── 4. Update / insert BDM Detail ────────────────────────────────────
        BAL_BdmTlDtl savedDetail = null;
        if (detail != null) {

            boolean detailIsNew = (detail.getKeyid() == null
                    || detail.getKeyid().trim().isEmpty()
                    || "undefined".equals(detail.getKeyid()));

            if (detailIsNew) {
                // Detail not yet saved for this master – insert it
                String detailKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_DETAIL, KEY_LEN_DETAIL, PREFIX_DETAIL, DATE_FORMAT, FORMAT_RESET);

                if (detailKeyid == null || detailKeyid.trim().isEmpty()) {
                    throw new RuntimeException("Failed to generate BDM Detail Key ID during update");
                }
                detail.setKeyid(detailKeyid);
                detail.setBdms_keyid(masterKeyid);

                if (detail.getCreatedby() == null || detail.getCreatedby().trim().isEmpty()) {
                    detail.setCreatedby(savedMaster.getCreatedby());
                }
                detail.setCreatedon(LocalDateTime.now());
                detail.setModifiedon(LocalDateTime.now());
                detail.setActive('Y');

                savedDetail = detailRepository.save(detail);
                logger.info("Inserted new BDM Detail during update: {}", detailKeyid);

            } else {
                // Update existing detail
                detail.setBdms_keyid(masterKeyid);
                detail.setModifiedon(LocalDateTime.now());
                savedDetail = detailRepository.save(detail);
                logger.info("Updated BDM Detail: {}", detail.getKeyid());
            }
        }

        // ── 5. Build and return response ─────────────────────────────────────
        BdmDto result = buildResponse(savedMaster, savedDetail, savedWom, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // =========================================================================
    //  HELPER : treat null, empty string, and the "{}" placeholder as blank
    // =========================================================================
    private boolean isBlankOrPlaceholder(String value) {
        return value == null
                || value.trim().isEmpty()
                || value.trim().equals("{}")
                || "undefined".equalsIgnoreCase(value.trim());
    }

   private WomTlWomst buildWorkOrder(BAL_BdmTlMst master, BAL_BdmTlDtl detail) {
    WomTlWomst wom = new WomTlWomst();

    // ── Fields mapped from BDM master ────────────────────────────────────
    wom.setFactoryid(master.getFactoryid());
    wom.setSectionid(master.getSectionid());
    wom.setCellid(master.getCellid());
    wom.setMachineid(master.getMachineid());
    wom.setShiftid(master.getShiftid());

    LocalDate entryLocalDate = null;
    if (master.getEntrydate() != null) {
        entryLocalDate = master.getEntrydate().toLocalDate();
        wom.setOccurreddate(entryLocalDate);
        wom.setShiftdate(entryLocalDate);
        wom.setReporteddate(entryLocalDate);
    } else {
        wom.setOccurreddate(FUTURE_NULL_DATE);
        wom.setShiftdate(FUTURE_NULL_DATE);
        wom.setReporteddate(FUTURE_NULL_DATE);
    }

    LocalDate refDate = (entryLocalDate != null) ? entryLocalDate : FUTURE_NULL_DATE;

    // ── Guard helper: treat 1801-01-01 and 2100-12-31 as null sentinels ──
    // Reported date
    LocalDate reportedDate = null;
    if (master.getReporteddate() != null) {
        LocalDate d = master.getReporteddate().toLocalDate();
        if (!d.equals(PAST_NULL_DATE) && !d.equals(FUTURE_NULL_DATE)) {
            reportedDate = d;
        }
    }
    if (reportedDate != null) {
        wom.setReporteddate(reportedDate);
    }

    // WO start time
    LocalDate woStartDate = null;
    if (master.getWostarttime() != null) {
        LocalDate d = master.getWostarttime().toLocalDate();
        if (!d.equals(PAST_NULL_DATE) && !d.equals(FUTURE_NULL_DATE)) {
            woStartDate = d;
        }
    }

    // WO end time
    LocalDate woEndDate = null;
    if (master.getWoendtime() != null) {
        LocalDate d = master.getWoendtime().toLocalDate();
        if (!d.equals(PAST_NULL_DATE) && !d.equals(FUTURE_NULL_DATE)) {
            woEndDate = d;
        }
    }

    // Prod accept date
    LocalDate prodAccepDate = null;
    if (master.getProdaccepdate() != null) {
        LocalDate d = master.getProdaccepdate().toLocalDate();
        if (!d.equals(PAST_NULL_DATE) && !d.equals(FUTURE_NULL_DATE)) {
            prodAccepDate = d;
        }
    }

    // Received date
    LocalDate receivedDate = null;
    if (master.getReceiveddate() != null) {
        LocalDate d = master.getReceiveddate().toLocalDate();
        if (!d.equals(PAST_NULL_DATE) && !d.equals(FUTURE_NULL_DATE)) {
            receivedDate = d;
        }
    }

    // ── Core identity fields ──────────────────────────────────────────────
    wom.setReportedby(master.getBookedby() != null ? master.getBookedby() : "-");

    // Legacy uses finalphenomena / finalcause / finaltrade for WOM
    wom.setPhenomenaid(master.getFinalphenomena() != null
            && !master.getFinalphenomena().equals("{}")
            ? master.getFinalphenomena() : "-");

    wom.setCauseid(master.getFinalcause() != null
            && !master.getFinalcause().equals("{}")
            ? master.getFinalcause() : "-");

    wom.setTradeid(master.getFinaltrade() != null
            && !master.getFinaltrade().equals("{}")
            ? master.getFinaltrade()
            : (master.getBookedtrade() != null ? master.getBookedtrade() : "-"));

    // Legacy uses problemdescription for woms_problem
    wom.setProblem(master.getProblemdescription() != null
            && !master.getProblemdescription().equals("{}")
            ? master.getProblemdescription() : "-");

    wom.setAssemblyid(master.getAssemblyid() != null
            && !master.getAssemblyid().equals("{}") ? master.getAssemblyid() : "-");

    wom.setSubassemblyid(master.getSubassemblyid() != null
            && !master.getSubassemblyid().equals("{}") ? master.getSubassemblyid() : "-");

    wom.setPartlocation(master.getPartlocationid() != null
            && !master.getPartlocationid().equals("{}") ? master.getPartlocationid() : "-");

    wom.setSpareid(master.getSpareid() != null
            && !master.getSpareid().equals("{}") ? master.getSpareid() : "-");

    wom.setElementid(master.getElementid() != null
            && !master.getElementid().equals("{}") ? master.getElementid() : "-");

    wom.setFlid(master.getFlid() != null
            && !master.getFlid().equals("{}") ? master.getFlid() : "-");

    wom.setProcessid(master.getProcessid() != null
            && !master.getProcessid().equals("{}") ? master.getProcessid() : "-");

    wom.setAlarmno(master.getAlarmdescription() != null
            && !master.getAlarmdescription().equals("{}") ? master.getAlarmdescription() : "-");

    wom.setBookingremarks(master.getRemarks() != null
            && !master.getRemarks().equals("{}") ? master.getRemarks() : "-");

    // Character fields – 'X' as sentinel
    wom.setProductionstop(master.getProductionstop() != null ? master.getProductionstop() : 'X');
    wom.setPriority(master.getPriority() != null ? master.getPriority() : 'X');

    // relatedto – bdrelatedto for WOM (String)
    wom.setRelatedto(master.getBdrelatedto() != null
            && !master.getBdrelatedto().equals("{}") ? master.getBdrelatedto() : "X");

    // activityid = BDM master keyid
    wom.setActivityid(master.getKeyid() != null ? master.getKeyid() : "-");

    wom.setRefdoctype("BD");
    wom.setRefdocid(master.getKeyid() != null ? master.getKeyid() : "-");

    // ── Flags ─────────────────────────────────────────────────────────────
    wom.setDirectentry('Y');
    wom.setActivitytype("BD");    // String in entity
    wom.setFinalactivitytype('B'); // Character in entity

    // ── Request approval ──────────────────────────────────────────────────
    wom.setRequestapproved('A');
    wom.setRequestapprovedby(master.getBookedby() != null ? master.getBookedby() : "-");
    wom.setRequestapproveddate(refDate);
    wom.setRequestapprovremarks("-");

    // ── Accepted ──────────────────────────────────────────────────────────
    wom.setAcceptedflag('Y');
    wom.setAccepteddate(refDate);
    wom.setAcceptedby(master.getBookedby() != null ? master.getBookedby() : "-");
    wom.setAcceptedremarks("-");

    // ── Safety permit ─────────────────────────────────────────────────────
    wom.setProductionapproval('N');
    wom.setSafetypermitsrequried('N');
    wom.setSafetypermitapproved('X');
    wom.setSafetypermitcompleted('X');
    wom.setSafetypermitsignoff('X');
    wom.setSafetypermitid("-");

    // ── Reschedule ────────────────────────────────────────────────────────
    wom.setRescheduleflag('A');
    wom.setRescheduledate(refDate);
    wom.setRescheduledstflag('N');
    wom.setRescheduledendflag('N');
    wom.setRescheduleby("-");
    wom.setRescheduleremarks("-");
    wom.setRescheduledremarks("-");
    wom.setReschedulestartdate(PAST_NULL_DATE);   // legacy: passNullDate = 1801-01-01
    wom.setRescheduleenddate(FUTURE_NULL_DATE);   // legacy: futureNullDate = 2100-12-31

    // ── Allotment ─────────────────────────────────────────────────────────
    wom.setAllottedflag(master.getWoallottedflag() != null ? master.getWoallottedflag() : 'N');
    wom.setAllotteddate(receivedDate != null ? receivedDate : refDate);
    wom.setAllottedto("-");
    wom.setAllottedremarks("-");
    wom.setAllottedsource("-");
    wom.setAllottedsupplier("-");

    // ── Proposed dates ────────────────────────────────────────────────────
    wom.setProposedstflag(master.getWostartflag() != null ? master.getWostartflag() : 'N');
    wom.setProposedendflag(master.getWoendflag() != null ? master.getWoendflag() : 'N');
    wom.setProposeddtacceptflag('X');
    wom.setProposedstartdate(woStartDate != null ? woStartDate : refDate);
    wom.setProposedenddate(woEndDate != null ? woEndDate : FUTURE_NULL_DATE);

    // ── Work execution dates ──────────────────────────────────────────────
    wom.setProductionstartflag(master.getWoprodaccepflag() != null ? master.getWoprodaccepflag() : 'N');
    wom.setProductionstartdate(prodAccepDate != null ? prodAccepDate : refDate);

    wom.setWorkstartflag(master.getWostartflag() != null ? master.getWostartflag() : 'N');
    wom.setWorkstartdate(woStartDate != null ? woStartDate : PAST_NULL_DATE);

    wom.setWorkendflag(master.getWoendflag() != null ? master.getWoendflag() : 'N');
    wom.setWorkenddate(woEndDate != null ? woEndDate : FUTURE_NULL_DATE);

    wom.setWoapprovalflag(master.getWoendflag() != null ? master.getWoendflag() : 'N');
    wom.setWoapprovaldate(woEndDate != null ? woEndDate : PAST_NULL_DATE);

    wom.setMachinereleaseflag(master.getWoendflag() != null ? master.getWoendflag() : 'N');
    wom.setMachinereleaseddate(woEndDate != null ? woEndDate : PAST_NULL_DATE);

    wom.setExceptedreturndate(PAST_NULL_DATE);

    // ── Status logic from detail (mirrors legacy fillWorkOrder) ───────────
    if (detail != null && detail.getErppoststatus() != null) {
        char erpStatus = detail.getErppoststatus();
        if (erpStatus == 'C') {
            wom.setStatus('C');
            wom.setFinalstatus("COMPLETED");
        } else {
            wom.setStatus('L');
            wom.setFinalstatus("ALLOTTED");
        }
        // failuretypeid from detail
        wom.setFailuretypeid(detail.getFailuretype() != null
                && !detail.getFailuretype().equals("{}")
                ? detail.getFailuretype() : "-");
        // doneby / allottedto / woapprovalby / machinereleaseby from detail completedby
        String completedBy = (detail.getCompletedby() != null
                && !detail.getCompletedby().equals("{}"))
                ? detail.getCompletedby() : "-";
        wom.setAllottedto(completedBy);
        wom.setDoneby(completedBy);
        wom.setWoapprovalby(completedBy);
        wom.setMachinereleaseby(completedBy);
    } else {
        wom.setStatus('X');
        wom.setFinalstatus("-");
        wom.setFailuretypeid("-");
        wom.setAllottedto("-");
        wom.setDoneby("-");
        wom.setWoapprovalby("-");
        wom.setMachinereleaseby("-");
    }

    // ── NOT NULL string defaults ──────────────────────────────────────────
    wom.setLoss("-");
    wom.setMouldid(master.getMould() != null
            && !master.getMould().equals("{}") ? master.getMould() : "-");
    wom.setWorkcenterid("-");
    wom.setCostcenterid("-");
    wom.setMachinecondition("X");
    wom.setLocation("-");
    wom.setProductionby("-");
    wom.setProductionremarks("-");
    wom.setSentforrepairflag('X');
    wom.setSentrepairid("-");
    wom.setSentto("-");
    wom.setRepairremarks("-");
    wom.setRemarks("-");
    wom.setJobopeningid("-");
    wom.setOrderno("-");
    wom.setPwdmwono("-");
    wom.setRequiredstart("-");
    wom.setRequiredend("-");
    wom.setPlannergroup("-");
    wom.setDepartmentid("-");
    wom.setTempfield1("-");
    wom.setIntorextequip('N');
    wom.setIntorextequipdesc("-");
    wom.setStandbyadditionalinfo("-");
    wom.setStandbyremarks("-");

    // ── Numeric NOT NULL defaults ─────────────────────────────────────────
    wom.setMaintpriority(BigDecimal.ZERO);
    wom.setNumofactivities(BigDecimal.ZERO);

    // ── Audit fields ──────────────────────────────────────────────────────
    wom.setCreatedby(master.getCreatedby() != null ? master.getCreatedby() : "-");
    wom.setModifiedby(master.getCreatedby() != null ? master.getCreatedby() : "-");
    wom.setCreatedon(LocalDateTime.now());
    wom.setModifiedon(LocalDateTime.now());
    wom.setActive('Y');

    return wom;
}
    // =========================================================================
    //  HELPER : Merge incoming master fields onto existing entity (null-safe)
    // =========================================================================
    private void mergeMaster(BAL_BdmTlMst existing, BAL_BdmTlMst incoming) {
        if (incoming.getEntrydate()            != null) existing.setEntrydate(incoming.getEntrydate());
        if (incoming.getShiftid()              != null) existing.setShiftid(incoming.getShiftid());
        if (incoming.getFactoryid()            != null) existing.setFactoryid(incoming.getFactoryid());
        if (incoming.getSectionid()            != null) existing.setSectionid(incoming.getSectionid());
        if (incoming.getCellid()               != null) existing.setCellid(incoming.getCellid());
        if (incoming.getMachineid()            != null) existing.setMachineid(incoming.getMachineid());
        if (incoming.getAssemblyid()           != null) existing.setAssemblyid(incoming.getAssemblyid());
        if (incoming.getSubassemblyid()        != null) existing.setSubassemblyid(incoming.getSubassemblyid());
        if (incoming.getPartlocationid()       != null) existing.setPartlocationid(incoming.getPartlocationid());
        if (incoming.getAlarmdescription()     != null) existing.setAlarmdescription(incoming.getAlarmdescription());
        if (incoming.getBdtype()               != null) existing.setBdtype(incoming.getBdtype());
        if (incoming.getReporteddate()         != null) existing.setReporteddate(incoming.getReporteddate());
        if (incoming.getReceiveddate()         != null) existing.setReceiveddate(incoming.getReceiveddate());
        if (incoming.getWostarttime()          != null) existing.setWostarttime(incoming.getWostarttime());
        if (incoming.getWoendtime()            != null) existing.setWoendtime(incoming.getWoendtime());
        if (incoming.getBreaktime()            != null) existing.setBreaktime(incoming.getBreaktime());
        if (incoming.getActualworktime()       != null) existing.setActualworktime(incoming.getActualworktime());
        if (incoming.getDowntime()             != null) existing.setDowntime(incoming.getDowntime());
        if (incoming.getProdaccepdate()        != null) existing.setProdaccepdate(incoming.getProdaccepdate());
        if (incoming.getBookedphenomena()      != null) existing.setBookedphenomena(incoming.getBookedphenomena());
        if (incoming.getPhenomenadescription() != null) existing.setPhenomenadescription(incoming.getPhenomenadescription());
        if (incoming.getBookedcause()          != null) existing.setBookedcause(incoming.getBookedcause());
        if (incoming.getFinalphenomena()       != null) existing.setFinalphenomena(incoming.getFinalphenomena());
        if (incoming.getFinalcause()           != null) existing.setFinalcause(incoming.getFinalcause());
        if (incoming.getBookedtrade()          != null) existing.setBookedtrade(incoming.getBookedtrade());
        if (incoming.getFinaltrade()           != null) existing.setFinaltrade(incoming.getFinaltrade());
        if (incoming.getProblemdescription()   != null) existing.setProblemdescription(incoming.getProblemdescription());
        if (incoming.getIsbdlocked()           != null) existing.setIsbdlocked(incoming.getIsbdlocked());
        if (incoming.getShiftincharge()        != null) existing.setShiftincharge(incoming.getShiftincharge());
        if (incoming.getStatus()               != null) existing.setStatus(incoming.getStatus());
        if (incoming.getBookedby()             != null) existing.setBookedby(incoming.getBookedby());
        if (incoming.getRemarks()              != null) existing.setRemarks(incoming.getRemarks());
        if (incoming.getBookingtype()          != null) existing.setBookingtype(incoming.getBookingtype());
        if (incoming.getBdrelatedto()          != null) existing.setBdrelatedto(incoming.getBdrelatedto());
        // if (incoming.getWno()                  != null) existing.setWno(incoming.getWno());
        if (incoming.getWno() != null && !isBlankOrPlaceholder(incoming.getWno()))
    existing.setWno(incoming.getWno());
        if (incoming.getSpareid()              != null) existing.setSpareid(incoming.getSpareid());
        if (incoming.getPriority()             != null) existing.setPriority(incoming.getPriority());
        if (incoming.getWoallottedflag()       != null) existing.setWoallottedflag(incoming.getWoallottedflag());
        if (incoming.getWostartflag()          != null) existing.setWostartflag(incoming.getWostartflag());
        if (incoming.getWoendflag()            != null) existing.setWoendflag(incoming.getWoendflag());
        if (incoming.getWoprodaccepflag()      != null) existing.setWoprodaccepflag(incoming.getWoprodaccepflag());
        if (incoming.getRepeatedbdflag()       != null) existing.setRepeatedbdflag(incoming.getRepeatedbdflag());
        if (incoming.getRepeatedbdno()         != null) existing.setRepeatedbdno(incoming.getRepeatedbdno());
        if (incoming.getRelatedto()            != null) existing.setRelatedto(incoming.getRelatedto());
        if (incoming.getMould()                != null) existing.setMould(incoming.getMould());
        if (incoming.getElementid()            != null) existing.setElementid(incoming.getElementid());
        if (incoming.getFlid()                 != null) existing.setFlid(incoming.getFlid());
        if (incoming.getProcessid()            != null) existing.setProcessid(incoming.getProcessid());
        if (incoming.getIsstandby()            != null) existing.setIsstandby(incoming.getIsstandby());
        if (incoming.getStandbyequipment()     != null) existing.setStandbyequipment(incoming.getStandbyequipment());
        if (incoming.getBreakdowntime()        != null) existing.setBreakdowntime(incoming.getBreakdowntime());
        if (incoming.getProductionstop()       != null) existing.setProductionstop(incoming.getProductionstop());
        if (incoming.getImmediateaction()      != null) existing.setImmediateaction(incoming.getImmediateaction());
        if (incoming.getCompleteddate()        != null) existing.setCompleteddate(incoming.getCompleteddate());
        if (incoming.getProblemreason()        != null) existing.setProblemreason(incoming.getProblemreason());
        if (incoming.getActivity()             != null) existing.setActivity(incoming.getActivity());
        if (incoming.getOtherphenomena()       != null) existing.setOtherphenomena(incoming.getOtherphenomena());
        if (incoming.getTempfield7()           != null) existing.setTempfield7(incoming.getTempfield7());
        if (incoming.getActive()               != null) existing.setActive(incoming.getActive());
    }

    // =========================================================================
    //  HELPER : Merge updated BDM master fields back into existing WOM entity
    // =========================================================================
    private void mergeWorkOrder(WomTlWomst existingWom, BAL_BdmTlMst savedMaster) {
        existingWom.setFactoryid(savedMaster.getFactoryid());
        existingWom.setSectionid(savedMaster.getSectionid());
        existingWom.setCellid(savedMaster.getCellid());
        existingWom.setMachineid(savedMaster.getMachineid());
        existingWom.setShiftid(savedMaster.getShiftid());

        if (savedMaster.getEntrydate() != null) {
            existingWom.setOccurreddate(savedMaster.getEntrydate().toLocalDate());
            existingWom.setShiftdate(savedMaster.getEntrydate().toLocalDate());
            existingWom.setReporteddate(savedMaster.getEntrydate().toLocalDate());
        }

        existingWom.setTradeid(savedMaster.getBookedtrade());
        existingWom.setAssemblyid(savedMaster.getAssemblyid());
        existingWom.setSubassemblyid(savedMaster.getSubassemblyid());
        existingWom.setPartlocation(savedMaster.getPartlocationid());
        existingWom.setSpareid(savedMaster.getSpareid());
        existingWom.setPhenomenaid(savedMaster.getBookedphenomena());
        existingWom.setCauseid(savedMaster.getBookedcause());
        existingWom.setProblem(savedMaster.getProblemreason());
        existingWom.setBookingremarks(savedMaster.getRemarks());
        existingWom.setStatus(savedMaster.getStatus());
        existingWom.setPriority(savedMaster.getPriority());
        existingWom.setElementid(savedMaster.getElementid());
        existingWom.setFlid(savedMaster.getFlid());
        existingWom.setProcessid(savedMaster.getProcessid());
        existingWom.setProductionstop(savedMaster.getProductionstop());
        existingWom.setRelatedto(savedMaster.getBdrelatedto());

        if (savedMaster.getWoallottedflag() != null) {
            existingWom.setAllottedflag(savedMaster.getWoallottedflag());
        }
        if (savedMaster.getWoallottedflag() != null && savedMaster.getWoallottedflag() == 'Y') {
            if (savedMaster.getBookedby() != null && !isBlankOrPlaceholder(savedMaster.getBookedby())) {
                existingWom.setAllottedto(savedMaster.getBookedby());
            }
            existingWom.setAllotteddate(LocalDate.now());
            existingWom.setProposedstflag('Y');
            existingWom.setProposedendflag('Y');
            if (savedMaster.getWostarttime() != null) {
                existingWom.setProposedstartdate(savedMaster.getWostarttime().toLocalDate());
            }
            if (savedMaster.getWoendtime() != null) {
                existingWom.setProposedenddate(savedMaster.getWoendtime().toLocalDate());
            }
        }
        if (savedMaster.getWoendflag() != null && savedMaster.getWoendflag() == 'Y') {
            existingWom.setStatus('C');
            existingWom.setFinalstatus("COMPLETED");
            existingWom.setWorkendflag('Y');
            if (savedMaster.getCompleteddate() != null) {
                existingWom.setWorkenddate(savedMaster.getCompleteddate().toLocalDate());
            } else if (savedMaster.getWoendtime() != null) {
                existingWom.setWorkenddate(savedMaster.getWoendtime().toLocalDate());
            }
            existingWom.setWoapprovalflag('Y');
        }
    }

    // =========================================================================
    //  HELPER : Assemble the response DTO
    // =========================================================================
    private BdmDto buildResponse(BAL_BdmTlMst master,
                                      BAL_BdmTlDtl detail,
                                      WomTlWomst   wom,
                                      BdmDto   original) {
        BdmDto result = new BdmDto();
        result.setMaster(master);
        result.setDetail(detail);
        result.setWomWorkOrder(wom);
        result.setFormActionMode(original.getFormActionMode());
        result.setFormMode(original.getFormMode());
        return result;
    }
    // ─── BdmServiceImpl.java  (add this method inside the existing class) ─────

@Override
public List<Map<String, Object>> getGridSummary(String woId) throws Exception {

    if (woId == null || woId.trim().isEmpty()) {
        throw new IllegalArgumentException("woId is required");
    }

    List<Map<String, Object>> result = womRepository.getGridSummary(woId);
    logger.info("Fetched cost grid summary for woId: {}", woId);
    return result;
}

// ─── BdmServiceImpl.java (add this method inside the existing class) ──────

@Override
public List<Map<String, Object>> getGridEstimate(String formName, String woId) throws Exception {

    if (woId == null || woId.trim().isEmpty()) {
        throw new IllegalArgumentException("woId is required");
    }
    if (formName == null || formName.trim().isEmpty()) {
        throw new IllegalArgumentException("formName is required");
    }

    List<Map<String, Object>> result;

    switch (formName) {
        case "empCost":
            result = womRepository.getEmpCostEstimate(woId);
            break;
        case "contractorCost":
            result = womRepository.getContractorCostEstimate(woId);
            break;
        case "spareCost":
            result = womRepository.getSpareCostEstimate(woId);
            break;
        case "serviceCost":
            result = womRepository.getServiceCostEstimate(woId);
            break;
        case "utilityCost":
            result = womRepository.getUtilityCostEstimate(woId);
            break;
        case "otherCost":
            result = womRepository.getOtherCostEstimate(woId);
            break;
        default:
            throw new IllegalArgumentException("Unknown formName: " + formName);
    }

    logger.info("Fetched grid estimate – formName: {}, woId: {}, rows: {}",
            formName, woId, result.size());
    return result;
}

// ─── BdmServiceImpl.java (add this method inside the existing class) ──────

@Override
public List<Map<String, Object>> getPageTotal(String formName, String formType, String woId) throws Exception {

    if (woId == null || woId.trim().isEmpty()) {
        throw new IllegalArgumentException("woId is required");
    }
    if (formName == null || formType == null) {
        throw new IllegalArgumentException("formName and formType are required");
    }

    List<Map<String, Object>> result;

    if ("Estimate".equals(formType)) {
        switch (formName) {
            case "empCost":         result = womRepository.getEmpCostEstimatePageTotal(woId); break;
            case "contractorCost":  result = womRepository.getContractorCostEstimatePageTotal(woId); break;
            case "spareCost":       result = womRepository.getSpareCostEstimatePageTotal(woId); break;
            case "serviceCost":     result = womRepository.getServiceCostEstimatePageTotal(woId); break;
            case "utilityCost":     result = womRepository.getUtilityCostEstimatePageTotal(woId); break;
            case "otherCost":       result = womRepository.getOtherCostEstimatePageTotal(woId); break;
            default: throw new IllegalArgumentException("Unknown formName: " + formName);
        }
    } else if ("Actual".equals(formType)) {
        switch (formName) {
            case "empCost":         result = womRepository.getEmpCostActualPageTotal(woId); break;
            case "contractorCost":  result = womRepository.getContractorCostActualPageTotal(woId); break;
            case "spareCost":       result = womRepository.getSpareCostActualPageTotal(woId); break;
            case "serviceCost":     result = womRepository.getServiceCostActualPageTotal(woId); break;
            case "utilityCost":     result = womRepository.getUtilityCostActualPageTotal(woId); break;
            case "otherCost":       result = womRepository.getOtherCostActualPageTotal(woId); break;
            default: throw new IllegalArgumentException("Unknown formName: " + formName);
        }
    } else {
        throw new IllegalArgumentException("Unknown formType: " + formType);
    }

    logger.info("Fetched page total – formType: {}, formName: {}, woId: {}", formType, formName, woId);
    return result;
}

// ─── BdmServiceImpl.java (add this method inside the existing class) ──────

@Override
public List<Map<String, Object>> getGridActual(String formName, String woId) throws Exception {

    if (woId == null || woId.trim().isEmpty()) {
        throw new IllegalArgumentException("woId is required");
    }
    if (formName == null || formName.trim().isEmpty()) {
        throw new IllegalArgumentException("formName is required");
    }

    List<Map<String, Object>> result;

    switch (formName) {
        case "empCost":
            result = womRepository.getEmpCostActual(woId);
            break;
        case "contractorCost":
            result = womRepository.getContractorCostActual(woId);
            break;
        case "spareCost":
            result = womRepository.getSpareCostActual(woId);
            break;
        case "serviceCost":
            result = womRepository.getServiceCostActual(woId);
            break;
        case "utilityCost":
            result = womRepository.getUtilityCostActual(woId);
            break;
        case "otherCost":
            result = womRepository.getOtherCostActual(woId);
            break;
        default:
            throw new IllegalArgumentException("Unknown formName: " + formName);
    }

    logger.info("Fetched grid actual – formName: {}, woId: {}, rows: {}", formName, woId, result.size());
    return result;
}

// =========================================================================
//  SINGLE SAVE METHOD for manpower cost – INSERT when no row matches the
//  composite key (woid+manpowerid+skillid), UPDATE otherwise.
//  Mirrors the saveBdm() insert/update pattern above.
// =========================================================================
@Override
@Transactional
public BAL_WomTlManpowercostplan saveManpowerCost(BAL_WomTlManpowercostplan request) throws Exception {

    if (request == null) {
        throw new IllegalArgumentException("Manpower cost data is required");
    }
    if (request.getWoid() == null || request.getWoid().trim().isEmpty()) {
        throw new IllegalArgumentException("woid is required");
    }
    if (request.getManpowerid() == null || request.getManpowerid().trim().isEmpty()) {
        throw new IllegalArgumentException("manpowerid is required");
    }
    if (request.getSkillid() == null || request.getSkillid().trim().isEmpty()) {
        throw new IllegalArgumentException("skillid is required");
    }

    BAL_WomTlManpowercostplanId id = new BAL_WomTlManpowercostplanId(
            request.getWoid(), request.getManpowerid(), request.getSkillid());

    boolean isUpdate = manpowercostplanRepository.existsById(id);

    if (isUpdate) {
        BAL_WomTlManpowercostplan existing = manpowercostplanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Manpower cost record not found for woid=" + request.getWoid()
                                + ", manpowerid=" + request.getManpowerid()
                                + ", skillid=" + request.getSkillid()));

        mergeManpowerCost(existing, request);
        existing.setModifiedon(LocalDateTime.now());

        BAL_WomTlManpowercostplan saved = manpowercostplanRepository.save(existing);
        logger.info("Updated manpower cost plan: woid={}, manpowerid={}, skillid={}",
                request.getWoid(), request.getManpowerid(), request.getSkillid());
        return saved;

    } else {
        request.setId(id);
        if (request.getCreatedon() == null) {
            request.setCreatedon(LocalDateTime.now());
        }
        request.setModifiedon(LocalDateTime.now());
        if (request.getActive() == null) {
            request.setActive('Y');
        }

        BAL_WomTlManpowercostplan saved = manpowercostplanRepository.save(request);
        logger.info("Inserted manpower cost plan: woid={}, manpowerid={}, skillid={}",
                request.getWoid(), request.getManpowerid(), request.getSkillid());
        return saved;
    }
}

// =========================================================================
//  HELPER : Merge incoming manpower cost fields onto existing entity
// =========================================================================
private void mergeManpowerCost(BAL_WomTlManpowercostplan existing, BAL_WomTlManpowercostplan incoming) {
    if (incoming.getDoctype()     != null) existing.setDoctype(incoming.getDoctype());
    if (incoming.getNormalmins()  != null) existing.setNormalmins(incoming.getNormalmins());
    if (incoming.getHolidaymins() != null) existing.setHolidaymins(incoming.getHolidaymins());
    if (incoming.getOthermins()   != null) existing.setOthermins(incoming.getOthermins());
    if (incoming.getNormalcost()  != null) existing.setNormalcost(incoming.getNormalcost());
    if (incoming.getHolidaycost() != null) existing.setHolidaycost(incoming.getHolidaycost());
    if (incoming.getOthercost()   != null) existing.setOthercost(incoming.getOthercost());
    if (incoming.getTotalvalue()  != null) existing.setTotalvalue(incoming.getTotalvalue());
    if (incoming.getNoofhelpers() != null) existing.setNoofhelpers(incoming.getNoofhelpers());
    if (incoming.getSkillflag()   != null) existing.setSkillflag(incoming.getSkillflag());
    if (incoming.getDate()        != null) existing.setDate(incoming.getDate());
    if (incoming.getActivity()    != null) existing.setActivity(incoming.getActivity());
    if (incoming.getRemarks()     != null) existing.setRemarks(incoming.getRemarks());
    if (incoming.getTempfield1()  != null) existing.setTempfield1(incoming.getTempfield1());
    if (incoming.getTempfield2()  != null) existing.setTempfield2(incoming.getTempfield2());
    if (incoming.getTempfield3()  != null) existing.setTempfield3(incoming.getTempfield3());
    if (incoming.getTempfield4()  != null) existing.setTempfield4(incoming.getTempfield4());
    if (incoming.getTempfield5()  != null) existing.setTempfield5(incoming.getTempfield5());
    if (incoming.getActive()      != null) existing.setActive(incoming.getActive());
}
@Override
@Transactional
public BAL_WomTlManpowercostactual saveManpowerCostActual(BAL_WomTlManpowercostactual request) throws Exception {

    if (request == null) {
        throw new IllegalArgumentException("Manpower cost actual data is required");
    }
    if (request.getMaintwoid() == null || request.getMaintwoid().trim().isEmpty()) {
        throw new IllegalArgumentException("maintwoid is required");
    }
    if (request.getManpowerid() == null || request.getManpowerid().trim().isEmpty()) {
        throw new IllegalArgumentException("manpowerid is required");
    }
    if (request.getSkillid() == null || request.getSkillid().trim().isEmpty()) {
        throw new IllegalArgumentException("skillid is required");
    }

    BAL_WomTlManpowercostactualId id = new BAL_WomTlManpowercostactualId(
            request.getMaintwoid(), request.getManpowerid(), request.getSkillid());

    boolean isUpdate = manpowercostactualRepository.existsById(id);

    if (isUpdate) {
        BAL_WomTlManpowercostactual existing = manpowercostactualRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Manpower cost actual record not found for maintwoid=" + request.getMaintwoid()
                                + ", manpowerid=" + request.getManpowerid()
                                + ", skillid=" + request.getSkillid()));

        mergeManpowerCostActual(existing, request);
        existing.setModifiedon(LocalDateTime.now());

        BAL_WomTlManpowercostactual saved = manpowercostactualRepository.save(existing);
        logger.info("Updated manpower cost actual: maintwoid={}, manpowerid={}, skillid={}",
                request.getMaintwoid(), request.getManpowerid(), request.getSkillid());
        return saved;

    } else {
        request.setId(id);
        if (request.getCreatedon() == null) {
            request.setCreatedon(LocalDateTime.now());
        }
        request.setModifiedon(LocalDateTime.now());

        BAL_WomTlManpowercostactual saved = manpowercostactualRepository.save(request);
        logger.info("Inserted manpower cost actual: maintwoid={}, manpowerid={}, skillid={}",
                request.getMaintwoid(), request.getManpowerid(), request.getSkillid());
        return saved;
    }
}

// =========================================================================
//  HELPER : Merge incoming manpower cost actual fields onto existing entity
// =========================================================================
private void mergeManpowerCostActual(BAL_WomTlManpowercostactual existing, BAL_WomTlManpowercostactual incoming) {
    if (incoming.getDoctype()     != null) existing.setDoctype(incoming.getDoctype());
    if (incoming.getNormalwt()    != null) existing.setNormalwt(incoming.getNormalwt());
    if (incoming.getHolidaywt()   != null) existing.setHolidaywt(incoming.getHolidaywt());
    if (incoming.getOtherwt()     != null) existing.setOtherwt(incoming.getOtherwt());
    if (incoming.getNormalrate()  != null) existing.setNormalrate(incoming.getNormalrate());
    if (incoming.getHolidayrate() != null) existing.setHolidayrate(incoming.getHolidayrate());
    if (incoming.getOtherrate()   != null) existing.setOtherrate(incoming.getOtherrate());
    if (incoming.getTotalvalue()  != null) existing.setTotalvalue(incoming.getTotalvalue());
    if (incoming.getNoofhelpers() != null) existing.setNoofhelpers(incoming.getNoofhelpers());
    if (incoming.getSkillflag()   != null) existing.setSkillflag(incoming.getSkillflag());
    if (incoming.getDate()        != null) existing.setDate(incoming.getDate());
    if (incoming.getActivity()    != null) existing.setActivity(incoming.getActivity());
    if (incoming.getRemarks()     != null) existing.setRemarks(incoming.getRemarks());
    if (incoming.getTempfield1()  != null) existing.setTempfield1(incoming.getTempfield1());
    if (incoming.getTempfield2()  != null) existing.setTempfield2(incoming.getTempfield2());
    if (incoming.getTempfield3()  != null) existing.setTempfield3(incoming.getTempfield3());
    if (incoming.getTempfield4()  != null) existing.setTempfield4(incoming.getTempfield4());
    if (incoming.getTempfield5()  != null) existing.setTempfield5(incoming.getTempfield5());
}
// =========================================================================
//  SINGLE SAVE METHOD for spare cost estimate – INSERT when no row matches
//  the composite key (woid+requestedby+sparesid), UPDATE otherwise.
// =========================================================================
@Override
@Transactional
public BAL_WomTlSparecostplan saveSpareCost(BAL_WomTlSparecostplan request) throws Exception {

    if (request == null) {
        throw new IllegalArgumentException("Spare cost data is required");
    }
    if (request.getWoid() == null || request.getWoid().trim().isEmpty()) {
        throw new IllegalArgumentException("woid is required");
    }
    if (request.getRequestedby() == null || request.getRequestedby().trim().isEmpty()) {
        throw new IllegalArgumentException("requestedby is required");
    }
    if (request.getSparesid() == null || request.getSparesid().trim().isEmpty()) {
        throw new IllegalArgumentException("sparesid is required");
    }

    BAL_WomTlSparecostplanId id = new BAL_WomTlSparecostplanId(
            request.getWoid(), request.getRequestedby(), request.getSparesid());

    boolean isUpdate = sparecostplanRepository.existsById(id);

    if (isUpdate) {
        BAL_WomTlSparecostplan existing = sparecostplanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Spare cost record not found for woid=" + request.getWoid()
                                + ", requestedby=" + request.getRequestedby()
                                + ", sparesid=" + request.getSparesid()));

        mergeSpareCost(existing, request);
        existing.setModifiedon(LocalDateTime.now());

        BAL_WomTlSparecostplan saved = sparecostplanRepository.save(existing);
        logger.info("Updated spare cost estimate: woid={}, requestedby={}, sparesid={}",
                request.getWoid(), request.getRequestedby(), request.getSparesid());
        return saved;

    } else {
        request.setId(id);
        if (request.getCreatedon() == null) {
            request.setCreatedon(LocalDateTime.now());
        }
        request.setModifiedon(LocalDateTime.now());
        if (request.getActive() == null) {
            request.setActive('Y');
        }

        BAL_WomTlSparecostplan saved = sparecostplanRepository.save(request);
        logger.info("Inserted spare cost estimate: woid={}, requestedby={}, sparesid={}",
                request.getWoid(), request.getRequestedby(), request.getSparesid());
        return saved;
    }
}

// =========================================================================
//  HELPER : Merge incoming spare cost fields onto existing entity
// =========================================================================
private void mergeSpareCost(BAL_WomTlSparecostplan existing, BAL_WomTlSparecostplan incoming) {
    if (incoming.getDoctype()    != null) existing.setDoctype(incoming.getDoctype());
    if (incoming.getQuantity()   != null) existing.setQuantity(incoming.getQuantity());
    if (incoming.getRate()       != null) existing.setRate(incoming.getRate());
    if (incoming.getValue()      != null) existing.setValue(incoming.getValue());
    if (incoming.getRefdocno()   != null) existing.setRefdocno(incoming.getRefdocno());
    if (incoming.getDate()       != null) existing.setDate(incoming.getDate());
    if (incoming.getTempfield1() != null) existing.setTempfield1(incoming.getTempfield1());
    if (incoming.getTempfield2() != null) existing.setTempfield2(incoming.getTempfield2());
    if (incoming.getTempfield3() != null) existing.setTempfield3(incoming.getTempfield3());
    if (incoming.getActive()     != null) existing.setActive(incoming.getActive());
}
// =========================================================================
//  SINGLE SAVE METHOD for service cost estimate – INSERT when no row
//  matches the composite key (woid+serviceid), UPDATE otherwise.
//  NOTE: this table has no "active" column.
// =========================================================================
@Override
@Transactional
public BAL_WomTlServicecostplan saveServiceCost(BAL_WomTlServicecostplan request) throws Exception {

    if (request == null) {
        throw new IllegalArgumentException("Service cost data is required");
    }
    if (request.getWoid() == null || request.getWoid().trim().isEmpty()) {
        throw new IllegalArgumentException("woid is required");
    }
    if (request.getServiceid() == null || request.getServiceid().trim().isEmpty()) {
        throw new IllegalArgumentException("serviceid is required");
    }

    BAL_WomTlServicecostplanId id = new BAL_WomTlServicecostplanId(
            request.getWoid(), request.getServiceid());

    boolean isUpdate = servicecostplanRepository.existsById(id);

    if (isUpdate) {
        BAL_WomTlServicecostplan existing = servicecostplanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Service cost record not found for woid=" + request.getWoid()
                                + ", serviceid=" + request.getServiceid()));

        mergeServiceCost(existing, request);
        existing.setModifiedon(LocalDateTime.now());

        BAL_WomTlServicecostplan saved = servicecostplanRepository.save(existing);
        logger.info("Updated service cost estimate: woid={}, serviceid={}",
                request.getWoid(), request.getServiceid());
        return saved;

    } else {
        request.setId(id);
        if (request.getCreatedon() == null) {
            request.setCreatedon(LocalDateTime.now());
        }
        request.setModifiedon(LocalDateTime.now());

        BAL_WomTlServicecostplan saved = servicecostplanRepository.save(request);
        logger.info("Inserted service cost estimate: woid={}, serviceid={}",
                request.getWoid(), request.getServiceid());
        return saved;
    }
}

// =========================================================================
//  HELPER : Merge incoming service cost fields onto existing entity
// =========================================================================
private void mergeServiceCost(BAL_WomTlServicecostplan existing, BAL_WomTlServicecostplan incoming) {
    if (incoming.getDoctype()        != null) existing.setDoctype(incoming.getDoctype());
    if (incoming.getBillno()         != null) existing.setBillno(incoming.getBillno());
    if (incoming.getBillvalue()      != null) existing.setBillvalue(incoming.getBillvalue());
    if (incoming.getBilldateflag()   != null) existing.setBilldateflag(incoming.getBilldateflag());
    if (incoming.getBilldate()       != null) existing.setBilldate(incoming.getBilldate());
    if (incoming.getJobdescription() != null) existing.setJobdescription(incoming.getJobdescription());
    if (incoming.getRemarks()        != null) existing.setRemarks(incoming.getRemarks());
    if (incoming.getTempfield1()     != null) existing.setTempfield1(incoming.getTempfield1());
    if (incoming.getTempfield2()     != null) existing.setTempfield2(incoming.getTempfield2());
    if (incoming.getTempfield3()     != null) existing.setTempfield3(incoming.getTempfield3());
    if (incoming.getTempfield4()     != null) existing.setTempfield4(incoming.getTempfield4());
    if (incoming.getTempfield5()     != null) existing.setTempfield5(incoming.getTempfield5());
}
// =========================================================================
//  SINGLE SAVE METHOD for utility cost estimate – INSERT when no row
//  matches the composite key (wokeyid+requestedby+utilitymstid), UPDATE
//  otherwise. NOTE: this table has no "active" column.
// =========================================================================
@Override
@Transactional
public BAL_WomTlUtilitycostplan saveUtilityCost(BAL_WomTlUtilitycostplan request) throws Exception {

    if (request == null) {
        throw new IllegalArgumentException("Utility cost data is required");
    }
    if (request.getWokeyid() == null || request.getWokeyid().trim().isEmpty()) {
        throw new IllegalArgumentException("wokeyid is required");
    }
    if (request.getRequestedby() == null || request.getRequestedby().trim().isEmpty()) {
        throw new IllegalArgumentException("requestedby is required");
    }
    if (request.getUtilitymstid() == null || request.getUtilitymstid().trim().isEmpty()) {
        throw new IllegalArgumentException("utilitymstid is required");
    }

    BAL_WomTlUtilitycostplanId id = new BAL_WomTlUtilitycostplanId(
            request.getWokeyid(), request.getRequestedby(), request.getUtilitymstid());

    boolean isUpdate = utilitycostplanRepository.existsById(id);

    if (isUpdate) {
        BAL_WomTlUtilitycostplan existing = utilitycostplanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Utility cost record not found for wokeyid=" + request.getWokeyid()
                                + ", requestedby=" + request.getRequestedby()
                                + ", utilitymstid=" + request.getUtilitymstid()));

        mergeUtilityCost(existing, request);
        existing.setModifiedon(LocalDateTime.now());

        BAL_WomTlUtilitycostplan saved = utilitycostplanRepository.save(existing);
        logger.info("Updated utility cost estimate: wokeyid={}, requestedby={}, utilitymstid={}",
                request.getWokeyid(), request.getRequestedby(), request.getUtilitymstid());
        return saved;

    } else {
        request.setId(id);
        if (request.getCreatedon() == null) {
            request.setCreatedon(LocalDateTime.now());
        }
        request.setModifiedon(LocalDateTime.now());

        BAL_WomTlUtilitycostplan saved = utilitycostplanRepository.save(request);
        logger.info("Inserted utility cost estimate: wokeyid={}, requestedby={}, utilitymstid={}",
                request.getWokeyid(), request.getRequestedby(), request.getUtilitymstid());
        return saved;
    }
}

// =========================================================================
//  HELPER : Merge incoming utility cost fields onto existing entity
// =========================================================================
private void mergeUtilityCost(BAL_WomTlUtilitycostplan existing, BAL_WomTlUtilitycostplan incoming) {
    if (incoming.getDoctype()    != null) existing.setDoctype(incoming.getDoctype());
    if (incoming.getQuantity()   != null) existing.setQuantity(incoming.getQuantity());
    if (incoming.getMinutes()    != null) existing.setMinutes(incoming.getMinutes());
    if (incoming.getCost()       != null) existing.setCost(incoming.getCost());
    if (incoming.getTotalvalue() != null) existing.setTotalvalue(incoming.getTotalvalue());
    if (incoming.getRemarks()    != null) existing.setRemarks(incoming.getRemarks());
    if (incoming.getDate()       != null) existing.setDate(incoming.getDate());
    if (incoming.getTempfield2() != null) existing.setTempfield2(incoming.getTempfield2());
    if (incoming.getTempfield3() != null) existing.setTempfield3(incoming.getTempfield3());
    if (incoming.getTempfield4() != null) existing.setTempfield4(incoming.getTempfield4());
    if (incoming.getTempfield5() != null) existing.setTempfield5(incoming.getTempfield5());
}
// =========================================================================
//  SINGLE SAVE METHOD for other cost estimate – INSERT when no row
//  matches the composite key (woid+requestedby+othercostmstid), UPDATE
//  otherwise. NOTE: this table has no "active" column.
// =========================================================================
@Override
@Transactional
public BAL_WomTlOthercostplan saveOtherCost(BAL_WomTlOthercostplan request) throws Exception {

    if (request == null) {
        throw new IllegalArgumentException("Other cost data is required");
    }
    if (request.getWoid() == null || request.getWoid().trim().isEmpty()) {
        throw new IllegalArgumentException("woid is required");
    }
    if (request.getRequestedby() == null || request.getRequestedby().trim().isEmpty()) {
        throw new IllegalArgumentException("requestedby is required");
    }
    if (request.getOthercostmstid() == null || request.getOthercostmstid().trim().isEmpty()) {
        throw new IllegalArgumentException("othercostmstid is required");
    }

    BAL_WomTlOthercostplanId id = new BAL_WomTlOthercostplanId(
            request.getWoid(), request.getRequestedby(), request.getOthercostmstid());

    boolean isUpdate = othercostplanRepository.existsById(id);

    if (isUpdate) {
        BAL_WomTlOthercostplan existing = othercostplanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Other cost record not found for woid=" + request.getWoid()
                                + ", requestedby=" + request.getRequestedby()
                                + ", othercostmstid=" + request.getOthercostmstid()));

        mergeOtherCost(existing, request);
        existing.setModifiedon(LocalDateTime.now());

        BAL_WomTlOthercostplan saved = othercostplanRepository.save(existing);
        logger.info("Updated other cost estimate: woid={}, requestedby={}, othercostmstid={}",
                request.getWoid(), request.getRequestedby(), request.getOthercostmstid());
        return saved;

    } else {
        request.setId(id);
        if (request.getCreatedon() == null) {
            request.setCreatedon(LocalDateTime.now());
        }
        request.setModifiedon(LocalDateTime.now());

        BAL_WomTlOthercostplan saved = othercostplanRepository.save(request);
        logger.info("Inserted other cost estimate: woid={}, requestedby={}, othercostmstid={}",
                request.getWoid(), request.getRequestedby(), request.getOthercostmstid());
        return saved;
    }
}

// =========================================================================
//  HELPER : Merge incoming other cost fields onto existing entity
// =========================================================================
private void mergeOtherCost(BAL_WomTlOthercostplan existing, BAL_WomTlOthercostplan incoming) {
    if (incoming.getDoctype()    != null) existing.setDoctype(incoming.getDoctype());
    if (incoming.getAmount()     != null) existing.setAmount(incoming.getAmount());
    if (incoming.getRemarks()    != null) existing.setRemarks(incoming.getRemarks());
    if (incoming.getDate()       != null) existing.setDate(incoming.getDate());
    if (incoming.getTempfield2() != null) existing.setTempfield2(incoming.getTempfield2());
    if (incoming.getTempfield3() != null) existing.setTempfield3(incoming.getTempfield3());
    if (incoming.getTempfield4() != null) existing.setTempfield4(incoming.getTempfield4());
    if (incoming.getTempfield5() != null) existing.setTempfield5(incoming.getTempfield5());
}
// =========================================================================
//  SINGLE SAVE METHOD for spare cost ACTUAL – INSERT when no row matches
//  the composite key (woid+requestedby+sparesid), UPDATE otherwise.
//  NOTE: this table has no "active" column.
// =========================================================================
@Override
@Transactional
public BAL_WomTlSparecostactual saveSpareCostActual(BAL_WomTlSparecostactual request) throws Exception {

    if (request == null) {
        throw new IllegalArgumentException("Spare cost actual data is required");
    }
    if (request.getWoid() == null || request.getWoid().trim().isEmpty()) {
        throw new IllegalArgumentException("woid is required");
    }
    if (request.getRequestedby() == null || request.getRequestedby().trim().isEmpty()) {
        throw new IllegalArgumentException("requestedby is required");
    }
    if (request.getSparesid() == null || request.getSparesid().trim().isEmpty()) {
        throw new IllegalArgumentException("sparesid is required");
    }

    BAL_WomTlSparecostactualId id = new BAL_WomTlSparecostactualId(
            request.getWoid(), request.getRequestedby(), request.getSparesid());

    boolean isUpdate = sparecostactualRepository.existsById(id);

    if (isUpdate) {
        BAL_WomTlSparecostactual existing = sparecostactualRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Spare cost actual record not found for woid=" + request.getWoid()
                                + ", requestedby=" + request.getRequestedby()
                                + ", sparesid=" + request.getSparesid()));

        mergeSpareCostActual(existing, request);
        existing.setModifiedon(LocalDateTime.now());

        BAL_WomTlSparecostactual saved = sparecostactualRepository.save(existing);
        logger.info("Updated spare cost actual: woid={}, requestedby={}, sparesid={}",
                request.getWoid(), request.getRequestedby(), request.getSparesid());
        return saved;

    } else {
        request.setId(id);
        if (request.getCreatedon() == null) {
            request.setCreatedon(LocalDateTime.now());
        }
        request.setModifiedon(LocalDateTime.now());

        BAL_WomTlSparecostactual saved = sparecostactualRepository.save(request);
        logger.info("Inserted spare cost actual: woid={}, requestedby={}, sparesid={}",
                request.getWoid(), request.getRequestedby(), request.getSparesid());
        return saved;
    }
}

// =========================================================================
//  HELPER : Merge incoming spare cost actual fields onto existing entity
// =========================================================================
private void mergeSpareCostActual(BAL_WomTlSparecostactual existing, BAL_WomTlSparecostactual incoming) {
    if (incoming.getSitreference() != null) existing.setSitreference(incoming.getSitreference());
    if (incoming.getQuantity()     != null) existing.setQuantity(incoming.getQuantity());
    if (incoming.getRate()         != null) existing.setRate(incoming.getRate());
    if (incoming.getValue()        != null) existing.setValue(incoming.getValue());
    if (incoming.getRefdocno()     != null) existing.setRefdocno(incoming.getRefdocno());
    if (incoming.getDoctype()      != null) existing.setDoctype(incoming.getDoctype());
    if (incoming.getDate()         != null) existing.setDate(incoming.getDate());
    if (incoming.getTempfield1()   != null) existing.setTempfield1(incoming.getTempfield1());
    if (incoming.getTempfield2()   != null) existing.setTempfield2(incoming.getTempfield2());
}
// =========================================================================
//  SINGLE SAVE METHOD for service cost ACTUAL – INSERT when no row matches
//  the composite key (woid+serviceid), UPDATE otherwise.
//  NOTE: this table has no "active" column.
// =========================================================================
@Override
@Transactional
public BAL_WomTlServicecostactual saveServiceCostActual(BAL_WomTlServicecostactual request) throws Exception {

    if (request == null) {
        throw new IllegalArgumentException("Service cost actual data is required");
    }
    if (request.getWoid() == null || request.getWoid().trim().isEmpty()) {
        throw new IllegalArgumentException("woid is required");
    }
    if (request.getServiceid() == null || request.getServiceid().trim().isEmpty()) {
        throw new IllegalArgumentException("serviceid is required");
    }

    BAL_WomTlServicecostactualId id = new BAL_WomTlServicecostactualId(
            request.getWoid(), request.getServiceid());

    boolean isUpdate = servicecostactualRepository.existsById(id);

    if (isUpdate) {
        BAL_WomTlServicecostactual existing = servicecostactualRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Service cost actual record not found for woid=" + request.getWoid()
                                + ", serviceid=" + request.getServiceid()));

        mergeServiceCostActual(existing, request);
        existing.setModifiedon(LocalDateTime.now());

        BAL_WomTlServicecostactual saved = servicecostactualRepository.save(existing);
        logger.info("Updated service cost actual: woid={}, serviceid={}",
                request.getWoid(), request.getServiceid());
        return saved;

    } else {
        request.setId(id);
        if (request.getCreatedon() == null) {
            request.setCreatedon(LocalDateTime.now());
        }
        request.setModifiedon(LocalDateTime.now());

        BAL_WomTlServicecostactual saved = servicecostactualRepository.save(request);
        logger.info("Inserted service cost actual: woid={}, serviceid={}",
                request.getWoid(), request.getServiceid());
        return saved;
    }
}

// =========================================================================
//  HELPER : Merge incoming service cost actual fields onto existing entity
// =========================================================================
private void mergeServiceCostActual(BAL_WomTlServicecostactual existing, BAL_WomTlServicecostactual incoming) {
    if (incoming.getDoctype()        != null) existing.setDoctype(incoming.getDoctype());
    if (incoming.getBillno()         != null) existing.setBillno(incoming.getBillno());
    if (incoming.getBillvalue()      != null) existing.setBillvalue(incoming.getBillvalue());
    if (incoming.getBilldateflag()   != null) existing.setBilldateflag(incoming.getBilldateflag());
    if (incoming.getBilldate()       != null) existing.setBilldate(incoming.getBilldate());
    if (incoming.getJobdescription() != null) existing.setJobdescription(incoming.getJobdescription());
    if (incoming.getRemarks()        != null) existing.setRemarks(incoming.getRemarks());
    if (incoming.getTempfield1()     != null) existing.setTempfield1(incoming.getTempfield1());
    if (incoming.getTempfield2()     != null) existing.setTempfield2(incoming.getTempfield2());
    if (incoming.getTempfield3()     != null) existing.setTempfield3(incoming.getTempfield3());
    if (incoming.getTempfield4()     != null) existing.setTempfield4(incoming.getTempfield4());
    if (incoming.getTempfield5()     != null) existing.setTempfield5(incoming.getTempfield5());
}
// =========================================================================
//  SINGLE SAVE METHOD for utility cost ACTUAL – INSERT when no row matches
//  the composite key (wokeyid+requestedby+utilitymstid), UPDATE otherwise.
//  NOTE: this table has no "active" column.
// =========================================================================
@Override
@Transactional
public BAL_WomTlUtilitycostactual saveUtilityCostActual(BAL_WomTlUtilitycostactual request) throws Exception {

    if (request == null) {
        throw new IllegalArgumentException("Utility cost actual data is required");
    }
    if (request.getWokeyid() == null || request.getWokeyid().trim().isEmpty()) {
        throw new IllegalArgumentException("wokeyid is required");
    }
    if (request.getRequestedby() == null || request.getRequestedby().trim().isEmpty()) {
        throw new IllegalArgumentException("requestedby is required");
    }
    if (request.getUtilitymstid() == null || request.getUtilitymstid().trim().isEmpty()) {
        throw new IllegalArgumentException("utilitymstid is required");
    }

    BAL_WomTlUtilitycostactualId id = new BAL_WomTlUtilitycostactualId(
            request.getWokeyid(), request.getRequestedby(), request.getUtilitymstid());

    boolean isUpdate = utilitycostactualRepository.existsById(id);

    if (isUpdate) {
        BAL_WomTlUtilitycostactual existing = utilitycostactualRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Utility cost actual record not found for wokeyid=" + request.getWokeyid()
                                + ", requestedby=" + request.getRequestedby()
                                + ", utilitymstid=" + request.getUtilitymstid()));

        mergeUtilityCostActual(existing, request);
        existing.setModifiedon(LocalDateTime.now());

        BAL_WomTlUtilitycostactual saved = utilitycostactualRepository.save(existing);
        logger.info("Updated utility cost actual: wokeyid={}, requestedby={}, utilitymstid={}",
                request.getWokeyid(), request.getRequestedby(), request.getUtilitymstid());
        return saved;

    } else {
        request.setId(id);
        if (request.getCreatedon() == null) {
            request.setCreatedon(LocalDateTime.now());
        }
        request.setModifiedon(LocalDateTime.now());

        BAL_WomTlUtilitycostactual saved = utilitycostactualRepository.save(request);
        logger.info("Inserted utility cost actual: wokeyid={}, requestedby={}, utilitymstid={}",
                request.getWokeyid(), request.getRequestedby(), request.getUtilitymstid());
        return saved;
    }
}

// =========================================================================
//  HELPER : Merge incoming utility cost actual fields onto existing entity
// =========================================================================
private void mergeUtilityCostActual(BAL_WomTlUtilitycostactual existing, BAL_WomTlUtilitycostactual incoming) {
    if (incoming.getDoctype()    != null) existing.setDoctype(incoming.getDoctype());
    if (incoming.getQuantity()   != null) existing.setQuantity(incoming.getQuantity());
    if (incoming.getMinutes()    != null) existing.setMinutes(incoming.getMinutes());
    if (incoming.getCost()       != null) existing.setCost(incoming.getCost());
    if (incoming.getTotalvalue() != null) existing.setTotalvalue(incoming.getTotalvalue());
    if (incoming.getRemarks()    != null) existing.setRemarks(incoming.getRemarks());
    if (incoming.getDate()       != null) existing.setDate(incoming.getDate());
    if (incoming.getTempfield2() != null) existing.setTempfield2(incoming.getTempfield2());
    if (incoming.getTempfield3() != null) existing.setTempfield3(incoming.getTempfield3());
    if (incoming.getTempfield4() != null) existing.setTempfield4(incoming.getTempfield4());
    if (incoming.getTempfield5() != null) existing.setTempfield5(incoming.getTempfield5());
}
    @Override
    @Transactional
    public BAL_WomTlOthercostactual saveOtherCostActual(BAL_WomTlOthercostactual request) throws Exception {
        if (request == null) throw new IllegalArgumentException("Other cost actual data is required");
        if (isBlank(request.getWoid())) throw new IllegalArgumentException("woid is required");
        if (isBlank(request.getRequestedby())) throw new IllegalArgumentException("requestedby is required");
        if (isBlank(request.getOthercostmstid())) throw new IllegalArgumentException("othercostmstid is required");

        BAL_WomTlOthercostactualId id = new BAL_WomTlOthercostactualId(
                request.getWoid(), request.getRequestedby(), request.getOthercostmstid());

        if (othercostactualRepository.existsById(id)) {
            BAL_WomTlOthercostactual existing = othercostactualRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Other cost actual record not found for woid="
                            + request.getWoid() + ", requestedby=" + request.getRequestedby()
                            + ", othercostmstid=" + request.getOthercostmstid()));
            mergeOtherCostActual(existing, request);
            existing.setModifiedon(LocalDateTime.now());
            BAL_WomTlOthercostactual saved = othercostactualRepository.save(existing);
            logger.info("Updated other cost actual: woid={}, requestedby={}, othercostmstid={}",
                    request.getWoid(), request.getRequestedby(), request.getOthercostmstid());
            return saved;
        } else {
            request.setId(id);
            if (request.getCreatedon() == null) request.setCreatedon(LocalDateTime.now());
            request.setModifiedon(LocalDateTime.now());
            BAL_WomTlOthercostactual saved = othercostactualRepository.save(request);
            logger.info("Inserted other cost actual: woid={}, requestedby={}, othercostmstid={}",
                    request.getWoid(), request.getRequestedby(), request.getOthercostmstid());
            return saved;
        }
    }

    private void mergeOtherCostActual(BAL_WomTlOthercostactual existing, BAL_WomTlOthercostactual incoming) {
        if (incoming.getDoctype()    != null) existing.setDoctype(incoming.getDoctype());
        if (incoming.getAmount()     != null) existing.setAmount(incoming.getAmount());
        if (incoming.getRemarks()    != null) existing.setRemarks(incoming.getRemarks());
        if (incoming.getDate()       != null) existing.setDate(incoming.getDate());
        if (incoming.getTempfield1() != null) existing.setTempfield1(incoming.getTempfield1());
        if (incoming.getTempfield2() != null) existing.setTempfield2(incoming.getTempfield2());
        if (incoming.getTempfield3() != null) existing.setTempfield3(incoming.getTempfield3());
        if (incoming.getTempfield4() != null) existing.setTempfield4(incoming.getTempfield4());
    }
private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
   // =========================================================================
//  DELETE : manpower cost estimate  –  key: woid+manpowerid+skillid
// =========================================================================
@Override
@Transactional
public int deleteManpowerCost(String woid, String manpowerid, String skillid) throws Exception {
    if (isBlank(woid)) throw new IllegalArgumentException("woid is required");
    if (isBlank(manpowerid)) throw new IllegalArgumentException("manpowerid is required");
    if (isBlank(skillid)) throw new IllegalArgumentException("skillid is required");

    int rowsDeleted = manpowercostplanRepository.deleteByCompositeKey(woid, manpowerid, skillid);

    if (rowsDeleted == 0) {
        throw new ResourceNotFoundException("Manpower cost record not found for woid=" + woid
                + ", manpowerid=" + manpowerid + ", skillid=" + skillid);
    }

    logger.info("Deleted manpower cost plan: woid={}, manpowerid={}, skillid={}", woid, manpowerid, skillid);
    return rowsDeleted;
} 
// =========================================================================
//  DELETE : manpower cost actual  –  key: maintwoid+manpowerid+skillid
// =========================================================================
@Override
@Transactional
public int deleteManpowerCostActual(String maintwoid, String manpowerid, String skillid) throws Exception {
    if (isBlank(maintwoid)) throw new IllegalArgumentException("maintwoid is required");
    if (isBlank(manpowerid)) throw new IllegalArgumentException("manpowerid is required");
    if (isBlank(skillid)) throw new IllegalArgumentException("skillid is required");

    int rowsDeleted = manpowercostactualRepository.deleteByCompositeKey(maintwoid, manpowerid, skillid);

    if (rowsDeleted == 0) {
        throw new ResourceNotFoundException("Manpower cost actual record not found for maintwoid=" + maintwoid
                + ", manpowerid=" + manpowerid + ", skillid=" + skillid);
    }

    logger.info("Deleted manpower cost actual: maintwoid={}, manpowerid={}, skillid={}", maintwoid, manpowerid, skillid);
    return rowsDeleted;
}
// =========================================================================
//  DELETE : spare cost estimate  –  key: woid+requestedby+sparesid
// =========================================================================
@Override
@Transactional
public int deleteSpareCost(String woid, String requestedby, String sparesid) throws Exception {
    if (isBlank(woid)) throw new IllegalArgumentException("woid is required");
    if (isBlank(requestedby)) throw new IllegalArgumentException("requestedby is required");
    if (isBlank(sparesid)) throw new IllegalArgumentException("sparesid is required");

    int rowsDeleted = sparecostplanRepository.deleteByCompositeKey(woid, requestedby, sparesid);

    if (rowsDeleted == 0) {
        throw new ResourceNotFoundException("Spare cost record not found for woid=" + woid
                + ", requestedby=" + requestedby + ", sparesid=" + sparesid);
    }

    logger.info("Deleted spare cost estimate: woid={}, requestedby={}, sparesid={}", woid, requestedby, sparesid);
    return rowsDeleted;
}
// =========================================================================
//  DELETE : spare cost actual  –  key: woid+requestedby+sparesid
// =========================================================================
@Override
@Transactional
public int deleteSpareCostActual(String woid, String requestedby, String sparesid) throws Exception {
    if (isBlank(woid)) throw new IllegalArgumentException("woid is required");
    if (isBlank(requestedby)) throw new IllegalArgumentException("requestedby is required");
    if (isBlank(sparesid)) throw new IllegalArgumentException("sparesid is required");

    int rowsDeleted = sparecostactualRepository.deleteByCompositeKey(woid, requestedby, sparesid);

    if (rowsDeleted == 0) {
        throw new ResourceNotFoundException("Spare cost actual record not found for woid=" + woid
                + ", requestedby=" + requestedby + ", sparesid=" + sparesid);
    }

    logger.info("Deleted spare cost actual: woid={}, requestedby={}, sparesid={}", woid, requestedby, sparesid);
    return rowsDeleted;
}
// =========================================================================
//  DELETE : service cost estimate  –  key: woid+serviceid
// =========================================================================
@Override
@Transactional
public int deleteServiceCost(String woid, String serviceid) throws Exception {
    if (isBlank(woid)) throw new IllegalArgumentException("woid is required");
    if (isBlank(serviceid)) throw new IllegalArgumentException("serviceid is required");

    int rowsDeleted = servicecostplanRepository.deleteByCompositeKey(woid, serviceid);

    if (rowsDeleted == 0) {
        throw new ResourceNotFoundException("Service cost record not found for woid=" + woid
                + ", serviceid=" + serviceid);
    }

    logger.info("Deleted service cost estimate: woid={}, serviceid={}", woid, serviceid);
    return rowsDeleted;
}

// =========================================================================
//  DELETE : service cost actual  –  key: woid+serviceid
// =========================================================================
@Override
@Transactional
public int deleteServiceCostActual(String woid, String serviceid) throws Exception {
    if (isBlank(woid)) throw new IllegalArgumentException("woid is required");
    if (isBlank(serviceid)) throw new IllegalArgumentException("serviceid is required");

    int rowsDeleted = servicecostactualRepository.deleteByCompositeKey(woid, serviceid);

    if (rowsDeleted == 0) {
        throw new ResourceNotFoundException("Service cost actual record not found for woid=" + woid
                + ", serviceid=" + serviceid);
    }

    logger.info("Deleted service cost actual: woid={}, serviceid={}", woid, serviceid);
    return rowsDeleted;
}
// =========================================================================
//  DELETE : utility cost estimate  –  key: wokeyid+requestedby+utilitymstid
// =========================================================================
@Override
@Transactional
public int deleteUtilityCost(String wokeyid, String requestedby, String utilitymstid) throws Exception {
    if (isBlank(wokeyid)) throw new IllegalArgumentException("wokeyid is required");
    if (isBlank(requestedby)) throw new IllegalArgumentException("requestedby is required");
    if (isBlank(utilitymstid)) throw new IllegalArgumentException("utilitymstid is required");

    int rowsDeleted = utilitycostplanRepository.deleteByCompositeKey(wokeyid, requestedby, utilitymstid);

    if (rowsDeleted == 0) {
        throw new ResourceNotFoundException("Utility cost record not found for wokeyid=" + wokeyid
                + ", requestedby=" + requestedby + ", utilitymstid=" + utilitymstid);
    }

    logger.info("Deleted utility cost estimate: wokeyid={}, requestedby={}, utilitymstid={}",
            wokeyid, requestedby, utilitymstid);
    return rowsDeleted;
}
// =========================================================================
//  DELETE : utility cost actual  –  key: wokeyid+requestedby+utilitymstid
// =========================================================================
@Override
@Transactional
public int deleteUtilityCostActual(String wokeyid, String requestedby, String utilitymstid) throws Exception {
    if (isBlank(wokeyid)) throw new IllegalArgumentException("wokeyid is required");
    if (isBlank(requestedby)) throw new IllegalArgumentException("requestedby is required");
    if (isBlank(utilitymstid)) throw new IllegalArgumentException("utilitymstid is required");

    int rowsDeleted = utilitycostactualRepository.deleteByCompositeKey(wokeyid, requestedby, utilitymstid);

    if (rowsDeleted == 0) {
        throw new ResourceNotFoundException("Utility cost actual record not found for wokeyid=" + wokeyid
                + ", requestedby=" + requestedby + ", utilitymstid=" + utilitymstid);
    }

    logger.info("Deleted utility cost actual: wokeyid={}, requestedby={}, utilitymstid={}",
            wokeyid, requestedby, utilitymstid);
    return rowsDeleted;
}

// =========================================================================
//  DELETE : other cost estimate  –  key: woid+requestedby+othercostmstid
// =========================================================================
@Override
@Transactional
public int deleteOtherCost(String woid, String requestedby, String othercostmstid) throws Exception {
    if (isBlank(woid)) throw new IllegalArgumentException("woid is required");
    if (isBlank(requestedby)) throw new IllegalArgumentException("requestedby is required");
    if (isBlank(othercostmstid)) throw new IllegalArgumentException("othercostmstid is required");

    int rowsDeleted = othercostplanRepository.deleteByCompositeKey(woid, requestedby, othercostmstid);

    if (rowsDeleted == 0) {
        throw new ResourceNotFoundException("Other cost record not found for woid=" + woid
                + ", requestedby=" + requestedby + ", othercostmstid=" + othercostmstid);
    }

    logger.info("Deleted other cost estimate: woid={}, requestedby={}, othercostmstid={}",
            woid, requestedby, othercostmstid);
    return rowsDeleted;
}

// =========================================================================
//  DELETE : other cost actual  –  key: woid+requestedby+othercostmstid
// =========================================================================
@Override
@Transactional
public int deleteOtherCostActual(String woid, String requestedby, String othercostmstid) throws Exception {
    if (isBlank(woid)) throw new IllegalArgumentException("woid is required");
    if (isBlank(requestedby)) throw new IllegalArgumentException("requestedby is required");
    if (isBlank(othercostmstid)) throw new IllegalArgumentException("othercostmstid is required");

    int rowsDeleted = othercostactualRepository.deleteByCompositeKey(woid, requestedby, othercostmstid);

    if (rowsDeleted == 0) {
        throw new ResourceNotFoundException("Other cost actual record not found for woid=" + woid
                + ", requestedby=" + requestedby + ", othercostmstid=" + othercostmstid);
    }

    logger.info("Deleted other cost actual: woid={}, requestedby={}, othercostmstid={}",
            woid, requestedby, othercostmstid);
    return rowsDeleted;
}

    @Override
    public BAL_BdmTlMst getBdmMaster(String keyid) throws Exception {
        if (keyid == null || keyid.trim().isEmpty()) {
            throw new IllegalArgumentException("keyid is required");
        }
        BAL_BdmTlMst master = masterRepository.findByKeyidNative(keyid);
        if (master == null) {
            throw new ResourceNotFoundException("BDM Master not found for keyid: " + keyid);
        }
        logger.info("Fetched BDM Master for keyid: {}", keyid);
        return master;
    }

    @Override
    public BAL_BdmTlDtl getBdmDetailByMasterKeyid(String bdmsKeyid) throws Exception {
        if (bdmsKeyid == null || bdmsKeyid.trim().isEmpty()) {
            throw new IllegalArgumentException("bdmsKeyid is required");
        }
        List<BAL_BdmTlDtl> details = detailRepository.findByBdmsKeyidNative(bdmsKeyid);
        if (details == null || details.isEmpty()) {
            throw new ResourceNotFoundException("BDM Detail not found for bdms_keyid: " + bdmsKeyid);
        }
        logger.info("Fetched BDM Detail for bdms_keyid: {}", bdmsKeyid);
        return details.get(0);
    }

    @Override
    public WomTlWomst getWorkOrder(String keyid) throws Exception {
        if (keyid == null || keyid.trim().isEmpty()) {
            throw new IllegalArgumentException("keyid is required");
        }
        WomTlWomst wom = womRepository.findByKeyidNative(keyid);
        if (wom == null) {
            throw new ResourceNotFoundException("WOM Work Order not found for keyid: " + keyid);
        }
        logger.info("Fetched WOM Work Order for keyid: {}", keyid);
        return wom;
    }
    @Transactional
    public ResponseEntity<WomTlCommunicationlog> saveCommTxt(WomTlCommunicationlog log) throws Exception
    {
        try{
           // logger.debug("saveCommTxt - keyid:{}",log.WomTlCommunicationlog.getkeyid());
            logger.debug("saveCommTxt - keyid:{}", log.getKeyid());
            boolean isInsert = (log.getKeyid() == null || log.getKeyid().trim().isEmpty());
            if(isInsert)
            {
                String newKeyId = dbActionTemplate.getSequenceNumber("WOM_TL_COMMUNICATIONLOG",11,"CMC","MMYY","Y");

                if(newKeyId == null || newKeyId.trim().isEmpty())
                {
                    throw new RuntimeException("Failed to generate Communication Log Key ID");
                }
                //generate key id
                 log.setKeyid(newKeyId);
            logger.debug("Generated KeyId: {}", newKeyId);
            //set aduit fields
            log.setCreatedon(LocalDateTime.now());
            log.setModifiedon(LocalDateTime.now());
            log.setActive('Y');
            //save
            WomTlCommunicationlog saved = communicationRepository.save(log);
            logger.debug("Inserted Communication Log: {}", newKeyId);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
            }
            else
            {
                log.setModifiedon(LocalDateTime.now());
                //save and update if key id exist
                 WomTlCommunicationlog saved = communicationRepository.save(log);
            logger.debug("Updated Communication Log: {}", log.getKeyid());

            return ResponseEntity.status(HttpStatus.OK).body(saved);
            }
        }
            catch (Exception e)
             {
        e.printStackTrace();
        throw e;
            }
        }

        @Override
    public List<Map<String, Object>> getCommText(String bdId) throws Exception {
        try {
            logger.debug("getCommText - bdId:{}", bdId);
            List<Map<String, Object>> result = communicationRepository.getCommText(bdId);
            logger.debug("getCommText - rows fetched:{}", result.size());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
