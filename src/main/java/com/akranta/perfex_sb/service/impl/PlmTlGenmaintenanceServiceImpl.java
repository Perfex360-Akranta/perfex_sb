package com.akranta.perfex_sb.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.GenmaintenanceBookingMobileDTO;
import com.akranta.perfex_sb.dto.GenmaintenanceCompletionMobileDTO;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.PlmTlGenmaintenance;
import com.akranta.perfex_sb.model.WomTlWomst;
import com.akranta.perfex_sb.repository.BreakdownBookingMobileRepo;
import com.akranta.perfex_sb.repository.PlmTlGenmaintenanceRepository;
import com.akranta.perfex_sb.repository.WomTlWomstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.PlmTlGenmaintenanceService;

@Service
public class PlmTlGenmaintenanceServiceImpl implements PlmTlGenmaintenanceService {

    private static final Logger logger = LoggerFactory.getLogger(PlmTlGenmaintenanceServiceImpl.class);

    // ── Sentinel dates (mirrors BDM convention) ───────────────────────────────
    private static final LocalDate FUTURE_NULL_DATE = LocalDate.of(2100, 12, 31);
    private static final LocalDate PAST_NULL_DATE   = LocalDate.of(1801, 1, 1);

    // ── Sequence identifiers ──────────────────────────────────────────────────
    private static final String SEQ_IDENTIFIER     = "PLM_TL_GENMAINTENANCE";
    private static final String SEQ_WOM            = "WOM_TL_WOMST";

    private static final int    KEY_LENGTH         = 11;
    private static final int    KEY_LEN_WOM        = 15;

    private static final String PREFIX             = "GMN";
    private static final String PREFIX_WOM         = "MW";

    private static final String DATE_FORMAT        = "YYMM";
    private static final String FORMAT_RESET       = "Y";

    // WOM sequence uses null for date-format / reset (matches legacy getSequenceNumber call)
    private static final String WOM_DATE_FORMAT    = null;
    private static final String WOM_FORMAT_RESET   = null;

    // Default machine calendar time (minutes) – matches legacy hardcoded 480
    private static final BigDecimal DEFAULT_CAL_TIME = BigDecimal.valueOf(480);

    private static final String EMPTY_PLACEHOLDER = "{}";
    private static final LocalDateTime PAST_NULL_DATETIME = LocalDateTime.of(1801, 1, 1, 0, 0);

    // ── Dependencies ──────────────────────────────────────────────────────────
    private final PlmTlGenmaintenanceRepository plmTlGenmaintenanceRepository;
    private final WomTlWomstRepository          womRepository;
    private final BreakdownBookingMobileRepo    breakdownBookingMobileRepo;
    private final DbActionTemplate              dbActionTemplate;

    public PlmTlGenmaintenanceServiceImpl(
            PlmTlGenmaintenanceRepository plmTlGenmaintenanceRepository,
            WomTlWomstRepository womRepository,
            BreakdownBookingMobileRepo breakdownBookingMobileRepo,
            DbActionTemplate dbActionTemplate) {
        this.plmTlGenmaintenanceRepository = plmTlGenmaintenanceRepository;
        this.womRepository                 = womRepository;
        this.breakdownBookingMobileRepo    = breakdownBookingMobileRepo;
        this.dbActionTemplate              = dbActionTemplate;
    }

    // =========================================================================
    //  SINGLE SAVE METHOD – INSERT when keyid is blank, UPDATE otherwise
    //  Mirrors legacy: create() / update() in BAL_PlmTlGenmaintenanceDao
    // =========================================================================
    @Override
    @Transactional
    public PlmTlGenmaintenance saveGeneralmainteneance(PlmTlGenmaintenance plmTlGenmaintenance) throws Exception {

        if (plmTlGenmaintenance == null) {
            throw new IllegalArgumentException("No General Maintenance Details");
        }

        boolean isInsert = (plmTlGenmaintenance.getKeyid() == null
                || plmTlGenmaintenance.getKeyid().trim().isEmpty());

        if (isInsert) {
            return doInsert(plmTlGenmaintenance);
        } else {
            return doUpdate(plmTlGenmaintenance);
        }
    }

    @Override
    @Transactional
    public PlmTlGenmaintenance saveGeneralmaintenanceBookingMobile(GenmaintenanceBookingMobileDTO request)
            throws Exception {
        if (request == null) {
            throw new IllegalArgumentException("Booking request is required");
        }
        if (isBlankOrPlaceholder(request.getMachineid()) && isBlankOrPlaceholder(request.getFlid())) {
            throw new IllegalArgumentException("machineid or flid is required");
        }
        if (isBlankOrPlaceholder(request.getShift())) {
            throw new IllegalArgumentException("Shift is required");
        }
        if (isBlankOrPlaceholder(request.getActivitytype())) {
            throw new IllegalArgumentException("Activity Type is required");
        }
        if (isBlankOrPlaceholder(request.getTrade())) {
            throw new IllegalArgumentException("Maintenance Section is required");
        }
        if (isBlankOrPlaceholder(request.getIsSpares())) {
            throw new IllegalArgumentException("Spare Replaced is required");
        }
        if (isBlankOrPlaceholder(request.getProblem())) {
            throw new IllegalArgumentException("Problem is required");
        }
        if (isBlankOrPlaceholder(request.getPriority())) {
            throw new IllegalArgumentException("Priority is required");
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
        String lineid = placeholderIfBlank(asString(hierarchy.get("cellid")));
        String machineid = requiredHierarchyValue(hierarchy, "machineid");
        String elementid = requiredHierarchyValue(hierarchy, "elementid");
        String flid = requiredHierarchyValue(hierarchy, "flid");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime occurredOn = resolveOccurredDateTime(request.getOccureddate(), request.getOccurredTime(), now);
        LocalDateTime shiftDate = parseDateTimeOrStartOfDay(request.getShiftdate(), occurredOn.toLocalDate().atStartOfDay());
        LocalDateTime targetDate = parseDateTimeOrStartOfDay(request.getTargetdate(), now.toLocalDate().atStartOfDay());
        String bookedby = request.getBookedby().trim();

        PlmTlGenmaintenance gmn = new PlmTlGenmaintenance();
        gmn.setKeyid(blankToNull(request.getKeyid()));
        gmn.setOccureddate(occurredOn);
        gmn.setShiftdate(shiftDate.toLocalDate().atStartOfDay());
        gmn.setBookeddate(now.toLocalDate().atStartOfDay());
        gmn.setReceiveddate(now);
        gmn.setAllocateddate(now);
        gmn.setWostartdate(PAST_NULL_DATETIME);
        gmn.setWoenddate(PAST_NULL_DATETIME);
        gmn.setResponsetime(toDecimal(request.getResponsetime()));
        gmn.setWorkhours(toDecimal(request.getWorkhours()));
        gmn.setDowntime(toDecimal(request.getDowntime()));
        gmn.setRefdoctype(EMPTY_PLACEHOLDER);
        gmn.setRefdocid(EMPTY_PLACEHOLDER);
        gmn.setFactoryid(factoryid);
        gmn.setSectionid(sectionid);
        gmn.setLineid(lineid);
        gmn.setMachineid(machineid);
        gmn.setStationid(placeholderIfBlank(request.getStationid()));
        gmn.setPhenid(EMPTY_PLACEHOLDER);
        gmn.setRootcauseid(EMPTY_PLACEHOLDER);
        gmn.setShift(request.getShift().trim());
        gmn.setTrade(request.getTrade().trim());
        gmn.setPartlocation(placeholderIfBlank(request.getPartlocation()));
        gmn.setActivitytype(request.getActivitytype().trim());
        gmn.setMchcondition(placeholderIfBlank(request.getMchcondition()));
        gmn.setManpowercost(BigDecimal.ZERO);
        gmn.setContractorcost(BigDecimal.ZERO);
        gmn.setOthercost(BigDecimal.ZERO);
        gmn.setSparecost(EMPTY_PLACEHOLDER);
        gmn.setProblem(request.getProblem().trim());
        gmn.setRootcause(placeholderIfBlank(request.getRootcause()));
        gmn.setCountermeasure(placeholderIfBlank(request.getCountermeasure()));
        gmn.setAction(placeholderIfBlank(request.getAction()));
        gmn.setReportedby(bookedby);
        gmn.setTargetdate(targetDate.toLocalDate().atStartOfDay());
        gmn.setStatus(isBlankOrPlaceholder(request.getStatus()) ? "P" : request.getStatus().trim());
        gmn.setIsyy("Y");
        gmn.setYyno("0");
        gmn.setCompletedby(bookedby);
        gmn.setCompleteddate(now);
        gmn.setRemarks(EMPTY_PLACEHOLDER);
        gmn.setPctrmeasure(EMPTY_PLACEHOLDER);
        gmn.setRelatedto("MCH");
        gmn.setMouldid(EMPTY_PLACEHOLDER);
        gmn.setOrdertype("-");
        gmn.setErppoststatus("X");
        gmn.setErpnumber("0");
        gmn.setSparesreplaced(request.getIsSpares().trim());
        gmn.setTempfield1("-");
        gmn.setTempfield2("-");
        gmn.setTempfield3("-");
        gmn.setTempfield4("-");
        gmn.setTempfield5("-");
        gmn.setTempfield6("-");
        gmn.setTempfield7("-");
        gmn.setTempfield8("-");
        gmn.setTempfield9("-");
        gmn.setTempfield10("-");
        gmn.setElementid(elementid);
        gmn.setFlid(flid);
        gmn.setActive('Y');
        gmn.setCreatedby(bookedby);
        gmn.setCreatedon(now);
        gmn.setModifiedon(now);

        logger.info("Mobile GM booking save – factoryid={}, sectionid={}, lineid={}, machineid={}, flid={}, elementid={}",
                factoryid, sectionid, lineid, machineid, flid, elementid);

        PlmTlGenmaintenance saved = saveGeneralmainteneance(gmn);
        applyMobilePriorityToWorkOrder(saved, request.getPriority());
        return saved;
    }

    @Override
    @Transactional
    public PlmTlGenmaintenance saveGeneralmaintenanceCompletionMobile(GenmaintenanceCompletionMobileDTO request)
            throws Exception {
        if (request == null) {
            throw new IllegalArgumentException("Completion request is required");
        }
        if (isBlankOrPlaceholder(request.getKeyid())) {
            throw new IllegalArgumentException("General Maintenance keyid is required");
        }
        if (isBlankOrPlaceholder(request.getCompletedby())) {
            throw new IllegalArgumentException("Completed By is required");
        }
        if (isBlankOrPlaceholder(request.getWostartdate())) {
            throw new IllegalArgumentException("Work Start is required");
        }
        if (isBlankOrPlaceholder(request.getWoenddate())) {
            throw new IllegalArgumentException("Work End is required");
        }

        String keyid = request.getKeyid().trim();
        PlmTlGenmaintenance existing = plmTlGenmaintenanceRepository.findByKeyid(keyid);
        if (existing == null) {
            throw new ResourceNotFoundException("General Maintenance not found with keyid: " + keyid);
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime workStart = parseFlexibleDateTime(request.getWostartdate());
        LocalDateTime workEnd = parseFlexibleDateTime(request.getWoenddate());
        if (workStart == null || workEnd == null) {
            throw new IllegalArgumentException("Work Start and Work End must be valid date/time values");
        }
        if (workEnd.isBefore(workStart)) {
            throw new IllegalArgumentException("Work End must be after Work Start");
        }

        LocalDateTime completedDate = parseFlexibleDateTime(request.getCompleteddate());
        if (completedDate == null) {
            completedDate = workEnd;
        }

        long downtimeMinutes = Math.max(0, Duration.between(workStart, workEnd).toMinutes());

        existing.setWostartdate(workStart);
        existing.setWoenddate(workEnd);
        existing.setCompletedby(request.getCompletedby().trim());
        existing.setCompleteddate(completedDate);
        existing.setRemarks(placeholderIfBlank(request.getRemarks()));
        existing.setStatus("C");
        existing.setWorkhours(BigDecimal.valueOf(downtimeMinutes));
        existing.setDowntime(BigDecimal.valueOf(downtimeMinutes));
        existing.setModifiedon(now);

        logger.info("Mobile GM completion save – keyid={}, completedby={}, workStart={}, workEnd={}",
                keyid, request.getCompletedby(), workStart, workEnd);

        PlmTlGenmaintenance saved = saveGeneralmainteneance(existing);
        applyMobileCompletionToWorkOrder(saved, request.getCompletedby().trim(), workStart, workEnd);
        return saved;
    }

    // =========================================================================
    //  INSERT PATH
    //  Legacy order: generate GMN keyid → WOM insert/update → GMN master insert
    //  NOTE: legacy create() checks if refdocid is already valid – if so,
    //        it UPDATES the existing WOM instead of inserting a new one.
    // =========================================================================
    private PlmTlGenmaintenance doInsert(PlmTlGenmaintenance gmn) throws Exception {

        // ── 1. Generate GMN Master key ────────────────────────────────────────
        String newKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET);

        if (newKeyid == null || newKeyid.trim().isEmpty()) {
            logger.error("Failed to generate General Maintenance Key ID");
            throw new RuntimeException("Failed to generate General Maintenance Key ID");
        }
        gmn.setKeyid(newKeyid);
        logger.info("Generated new General Maintenance Key ID: {}", newKeyid);

        // ── 2. WOM: insert or update depending on whether refdocid is present ─
        //  Mirrors legacy: if (FilterCondSql.isValidKeyId(gmntRefdocid)) → updateWorkOrder
        //                  else → insertWorkOrder
        WomTlWomst savedWom;
        if (isValidKeyId(gmn.getRefdocid())) {

            // Existing WOM – update it
            logger.info("GMN insert: refdocid {} already set – updating existing WOM", gmn.getRefdocid());
            WomTlWomst existingWom = womRepository.findById(gmn.getRefdocid())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "WOM Work Order not found for refdocid: " + gmn.getRefdocid()));

            fillWorkOrder(existingWom, gmn);
            existingWom.setModifiedon(LocalDateTime.now());
            savedWom = womRepository.save(existingWom);
            logger.info("Updated existing WOM Work Order: {}", gmn.getRefdocid());

        } else {

            // New WOM – insert
            WomTlWomst newWom = buildWorkOrder(gmn);

            String womKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_WOM, KEY_LEN_WOM, PREFIX_WOM, WOM_DATE_FORMAT, WOM_FORMAT_RESET);

            if (womKeyid == null || womKeyid.trim().isEmpty()) {
                throw new RuntimeException("Failed to generate WOM Key ID");
            }
            newWom.setKeyid(womKeyid);

            // Legacy: plmTlGenmaintenance.setGmntRefdocid(womTlWomst.getWomsKeyid())
            gmn.setRefdocid(womKeyid);

            // MSR overlap check (mirrors legacy isMSRExist guard)
            checkMsrOverlap(newWom);

            savedWom = womRepository.save(newWom);
            logger.info("Inserted new WOM Work Order: {}", womKeyid);
        }

        // ── 3. Save GMN Master ────────────────────────────────────────────────
        if (gmn.getCreatedon() == null) {
            gmn.setCreatedon(LocalDateTime.now());
        }
        gmn.setModifiedon(LocalDateTime.now());

        PlmTlGenmaintenance savedRecord = plmTlGenmaintenanceRepository.save(gmn);
        logger.info("Successfully created General Maintenance with Key ID: {}", savedRecord.getKeyid());

        return savedRecord;
    }

    // =========================================================================
    //  UPDATE PATH
    //  Legacy order: update WOM → update GMN master
    // =========================================================================
    private PlmTlGenmaintenance doUpdate(PlmTlGenmaintenance gmn) throws Exception {

        // ── 1. Load existing GMN Master ───────────────────────────────────────
        PlmTlGenmaintenance existingRecord = plmTlGenmaintenanceRepository.findByKeyid(gmn.getKeyid());
        if (existingRecord == null) {
            throw new ResourceNotFoundException(
                    "General Maintenance not found with keyid: " + gmn.getKeyid());
        }

        // ── 2. Update WOM (if refdocid exists) ───────────────────────────────
        String refdocid = existingRecord.getRefdocid();
        if (isValidKeyId(refdocid)) {

            WomTlWomst existingWom = womRepository.findById(refdocid)
                    .orElse(null);

            if (existingWom != null) {
                fillWorkOrder(existingWom, gmn);
                existingWom.setModifiedon(LocalDateTime.now());

                // MSR overlap check (mirrors legacy updateWorkOrder guard)
                checkMsrOverlap(existingWom);

                womRepository.save(existingWom);
                logger.info("Updated WOM Work Order: {}", refdocid);
            } else {
                logger.warn("WOM {} not found during GMN update – skipping WOM update", refdocid);
            }

        } else {
            // Edge case: GMN exists but has no WOM – insert a new one
            logger.warn("GMN {} has no refdocid – inserting a new WOM Work Order", gmn.getKeyid());
            WomTlWomst newWom = buildWorkOrder(gmn);

            String womKeyid = dbActionTemplate.getSequenceNumber(
                    SEQ_WOM, KEY_LEN_WOM, PREFIX_WOM, WOM_DATE_FORMAT, WOM_FORMAT_RESET);

            if (womKeyid == null || womKeyid.trim().isEmpty()) {
                throw new RuntimeException("Failed to generate WOM Key ID during update fallback");
            }
            newWom.setKeyid(womKeyid);
            checkMsrOverlap(newWom);

            womRepository.save(newWom);
            gmn.setRefdocid(womKeyid);
            existingRecord.setRefdocid(womKeyid);
            logger.info("Inserted fallback WOM Work Order: {}", womKeyid);
        }

        // ── 3. Merge fields onto existing GMN master ──────────────────────────
        mergeGmn(existingRecord, gmn);
        existingRecord.setModifiedon(LocalDateTime.now());

        PlmTlGenmaintenance updatedRecord = plmTlGenmaintenanceRepository.save(existingRecord);
        logger.info("Successfully updated General Maintenance with Key ID: {}", updatedRecord.getKeyid());

        return updatedRecord;
    }

    // =========================================================================
    //  BUILD WORK ORDER – full field population for INSERT (new WOM)
    //  Mirrors legacy fillWorkOrderInsert(womTlWomst, plmTlGenmaintenance)
    // =========================================================================
    private WomTlWomst buildWorkOrder(PlmTlGenmaintenance gmn) {
        WomTlWomst wom = new WomTlWomst();
        fillWorkOrder(wom, gmn);

        // Audit fields (only set on INSERT)
        wom.setCreatedby(gmn.getCreatedby() != null ? gmn.getCreatedby() : "-");
        wom.setCreatedon(LocalDateTime.now());

        wom.setRefdoctype("GM");   // General Maintenance document type
        wom.setActivitytype("GM");
        wom.setFinalactivitytype('G');
        wom.setDirectentry('Y');

        // Defaults that only apply at creation
        wom.setRequestapproved('A');
        wom.setRequestapprovedby(gmn.getCreatedby() != null ? gmn.getCreatedby() : "-");
        wom.setRequestapprovremarks("-");

        wom.setAcceptedflag('Y');
        wom.setAcceptedby(gmn.getCreatedby() != null ? gmn.getCreatedby() : "-");
        wom.setAcceptedremarks("-");

        wom.setProductionapproval('N');
        wom.setSafetypermitsrequried('N');
        wom.setSafetypermitapproved('X');
        wom.setSafetypermitcompleted('X');
        wom.setSafetypermitsignoff('X');
        wom.setSafetypermitid("-");

        wom.setRescheduleflag('A');
        wom.setRescheduledstflag('N');
        wom.setRescheduledendflag('N');
        wom.setRescheduleby("-");
        wom.setRescheduleremarks("-");
        wom.setRescheduledremarks("-");
        // FIX 1: gmn dates are LocalDateTime – use resolveDateTime()
        LocalDate reschDate = resolveDateTime(gmn.getOccureddate());
        wom.setRescheduledate(reschDate != null ? reschDate : FUTURE_NULL_DATE);
        wom.setReschedulestartdate(PAST_NULL_DATE);
        wom.setRescheduleenddate(FUTURE_NULL_DATE);

        wom.setActive('Y');
        return wom;
    }

    // =========================================================================
    //  FILL WORK ORDER – fields updated on both INSERT and UPDATE
    //  Mirrors legacy fillWorkOrder(womTlWomst, plmTlGenmaintenance)
    // =========================================================================
    private void fillWorkOrder(WomTlWomst wom, PlmTlGenmaintenance gmn) {

        // ── Location / machine identity ───────────────────────────────────────
        wom.setFactoryid(gmn.getFactoryid());
        wom.setSectionid(gmn.getSectionid());
        wom.setCellid(gmn.getLineid());        // gmnt_lineid → woms_cellid
        wom.setMachineid(gmn.getMachineid());
        wom.setShiftid(gmn.getShift());        // gmnt_shift → woms_shiftid

        // ── Dates ─────────────────────────────────────────────────────────────
        // FIX 1: gmn dates are LocalDateTime – extract LocalDate via resolveDateTime()
        LocalDate occuredDate   = resolveDateTime(gmn.getOccureddate());
        LocalDate woStart       = resolveDateTime(gmn.getWostartdate());
        LocalDate woEnd         = resolveDateTime(gmn.getWoenddate());
        LocalDate receivedDate  = resolveDateTime(gmn.getReceiveddate());
        LocalDate allocatedDate = resolveDateTime(gmn.getAllocateddate());
        LocalDate shiftDate     = resolveDateTime(gmn.getShiftdate());
        LocalDate bookedDate    = resolveDateTime(gmn.getBookeddate());

        LocalDate refDate = occuredDate != null ? occuredDate : FUTURE_NULL_DATE;

        wom.setOccurreddate(occuredDate != null ? occuredDate : FUTURE_NULL_DATE);
        wom.setShiftdate(shiftDate != null ? shiftDate : FUTURE_NULL_DATE);
        wom.setReporteddate(bookedDate != null ? bookedDate : FUTURE_NULL_DATE);

        wom.setProposedstartdate(woStart != null ? woStart : refDate);
        wom.setProposedenddate(woEnd != null ? woEnd : FUTURE_NULL_DATE);
        wom.setWorkstartdate(woStart != null ? woStart : PAST_NULL_DATE);
        wom.setWorkenddate(woEnd != null ? woEnd : FUTURE_NULL_DATE);
        wom.setWoapprovaldate(woEnd != null ? woEnd : PAST_NULL_DATE);
        wom.setMachinereleaseddate(woEnd != null ? woEnd : PAST_NULL_DATE);

        // Received / allotted
        wom.setAllotteddate(receivedDate != null ? receivedDate : refDate);

        // Allocated / accepted / request approved dates
        wom.setRequestapproveddate(refDate);
        wom.setAccepteddate(allocatedDate != null ? allocatedDate : refDate);
        wom.setProductionstartdate(refDate);

        wom.setExceptedreturndate(PAST_NULL_DATE);

        // ── Core identity fields ──────────────────────────────────────────────
        wom.setReportedby(gmn.getReportedby() != null ? gmn.getReportedby() : "-");

        wom.setPhenomenaid(nvl(gmn.getPhenid(), "-"));
        wom.setCauseid(nvl(gmn.getRootcauseid(), "-"));
        wom.setTradeid(nvl(gmn.getTrade(), "-"));
        wom.setProblem(nvl(gmn.getProblem(), "-"));
        wom.setPartlocation(nvl(gmn.getPartlocation(), "-"));

        // refdocid for WOM points back to the GMN keyid
        wom.setRefdocid(gmn.getKeyid() != null ? gmn.getKeyid() : "-");
        wom.setActivityid(gmn.getKeyid() != null ? gmn.getKeyid() : "-");

        // ── Status ────────────────────────────────────────────────────────────
        // FIX 2: gmn.getStatus() is String; wom.setStatus() expects Character
        if (gmn.getStatus() != null && !gmn.getStatus().isEmpty()) {
            char statusChar = gmn.getStatus().charAt(0);
            wom.setStatus(statusChar);
            wom.setFinalstatus(statusChar == 'C' ? "COMPLETED" : "ALLOTTED");
        } else {
            wom.setStatus('X');
            wom.setFinalstatus("-");
        }

        // ── Flags ─────────────────────────────────────────────────────────────
        wom.setAllottedflag('N');
        wom.setProposedstflag('N');
        wom.setProposedendflag('N');
        wom.setProposeddtacceptflag('X');
        wom.setProductionstartflag('N');
        wom.setWorkstartflag('N');
        wom.setWorkendflag('N');
        wom.setWoapprovalflag('N');
        wom.setMachinereleaseflag('N');
        wom.setSentforrepairflag('X');

        // Completion fields
        // FIX 2: WomTlWomst has NO completedby field – map gmn.completedby to the
        // correct WOM completion fields: doneby, woapprovalby, machinereleaseby, allottedto
        String completedBy = nvl(gmn.getCompletedby(), "-");
        wom.setDoneby(completedBy);
        wom.setWoapprovalby(completedBy);
        wom.setMachinereleaseby(completedBy);
        wom.setAllottedto(completedBy);
        wom.setAllottedremarks("-");
        wom.setAllottedsource("-");
        wom.setAllottedsupplier("-");

        // Remarks / miscellaneous
        wom.setBookingremarks(nvl(gmn.getRemarks(), "-"));
        wom.setFailuretypeid("-");
        wom.setLoss("-");
        wom.setMouldid(nvl(gmn.getMouldid(), "-"));
        wom.setWorkcenterid("-");
        wom.setCostcenterid("-");
        // FIX: gmn.getMchcondition() is already String – pass directly
        wom.setMachinecondition(nvl(gmn.getMchcondition(), "X"));
        wom.setLocation("-");
        wom.setProductionby("-");
        wom.setProductionremarks("-");
        wom.setSentrepairid("-");
        wom.setSentto("-");
        wom.setRepairremarks("-");
        wom.setRemarks(nvl(gmn.getRemarks(), "-"));
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
        wom.setElementid(nvl(gmn.getElementid(), "-"));
        wom.setFlid(nvl(gmn.getFlid(), "-"));
        wom.setRelatedto(nvl(gmn.getRelatedto(), "X"));
        wom.setProductionstop('N');
        wom.setPriority('X');
        // FIX: These NOT NULL WOM fields have no GMN equivalent – use safe defaults
        wom.setAlarmno("-");
        wom.setAssemblyid("-");
        wom.setSubassemblyid("-");
        wom.setSpareid("-");
        wom.setProcessid("-");

        // FIX 3: WomTlWomst has NO responsetime/workhours/downtime columns – removed
        // FIX 4: PlmTlGenmaintenance has no modifiedby field – fall back to createdby
        wom.setMaintpriority(BigDecimal.ZERO);
        wom.setNumofactivities(BigDecimal.ZERO);
        wom.setModifiedby(gmn.getCreatedby() != null ? gmn.getCreatedby() : "-");
        wom.setModifiedon(LocalDateTime.now());
    }

    // =========================================================================
    //  HELPER: MSR overlap check
    //  Mirrors legacy: isMSRExist(womTlWomst) – throws if overlap found
    //  TODO: Add isMSRExist(@Param machineid, @Param shiftid, @Param shiftdate)
    //        query to WomTlWomstRepository and uncomment the block below.
    // =========================================================================
    private void checkMsrOverlap(WomTlWomst wom) throws Exception {
        // List<Object[]> overlapFlag = womRepository.isMSRExist(
        //         wom.getMachineid(), wom.getShiftid(), wom.getShiftdate());
        // if (overlapFlag != null && !overlapFlag.isEmpty()) {
        //     throw new RuntimeException("msrOverlap,");
        // }
        logger.debug("MSR overlap check placeholder – enable once isMSRExist is in repository");
    }

    // =========================================================================
    //  HELPER: Merge all updatable fields from incoming onto existing GMN entity
    // =========================================================================
    private void mergeGmn(PlmTlGenmaintenance existing, PlmTlGenmaintenance incoming) {
        if (incoming.getOccureddate()      != null) existing.setOccureddate(incoming.getOccureddate());
        if (incoming.getShiftdate()        != null) existing.setShiftdate(incoming.getShiftdate());
        if (incoming.getBookeddate()       != null) existing.setBookeddate(incoming.getBookeddate());
        if (incoming.getReceiveddate()     != null) existing.setReceiveddate(incoming.getReceiveddate());
        if (incoming.getAllocateddate()    != null) existing.setAllocateddate(incoming.getAllocateddate());
        if (incoming.getWostartdate()      != null) existing.setWostartdate(incoming.getWostartdate());
        if (incoming.getWoenddate()        != null) existing.setWoenddate(incoming.getWoenddate());
        if (incoming.getResponsetime()     != null) existing.setResponsetime(incoming.getResponsetime());
        if (incoming.getWorkhours()        != null) existing.setWorkhours(incoming.getWorkhours());
        if (incoming.getDowntime()         != null) existing.setDowntime(incoming.getDowntime());
        if (incoming.getRefdoctype()       != null) existing.setRefdoctype(incoming.getRefdoctype());
        if (incoming.getRefdocid()         != null) existing.setRefdocid(incoming.getRefdocid());
        if (incoming.getFactoryid()        != null) existing.setFactoryid(incoming.getFactoryid());
        if (incoming.getSectionid()        != null) existing.setSectionid(incoming.getSectionid());
        if (incoming.getLineid()           != null) existing.setLineid(incoming.getLineid());
        if (incoming.getMachineid()        != null) existing.setMachineid(incoming.getMachineid());
        if (incoming.getStationid()        != null) existing.setStationid(incoming.getStationid());
        if (incoming.getPhenid()           != null) existing.setPhenid(incoming.getPhenid());
        if (incoming.getRootcauseid()      != null) existing.setRootcauseid(incoming.getRootcauseid());
        if (incoming.getShift()            != null) existing.setShift(incoming.getShift());
        if (incoming.getTrade()            != null) existing.setTrade(incoming.getTrade());
        if (incoming.getPartlocation()     != null) existing.setPartlocation(incoming.getPartlocation());
        if (incoming.getActivitytype()     != null) existing.setActivitytype(incoming.getActivitytype());
        if (incoming.getMchcondition()     != null) existing.setMchcondition(incoming.getMchcondition());
        if (incoming.getManpowercost()     != null) existing.setManpowercost(incoming.getManpowercost());
        if (incoming.getContractorcost()   != null) existing.setContractorcost(incoming.getContractorcost());
        if (incoming.getOthercost()        != null) existing.setOthercost(incoming.getOthercost());
        if (incoming.getSparecost()        != null) existing.setSparecost(incoming.getSparecost());
        if (incoming.getProblem()          != null) existing.setProblem(incoming.getProblem());
        if (incoming.getRootcause()        != null) existing.setRootcause(incoming.getRootcause());
        if (incoming.getCountermeasure()   != null) existing.setCountermeasure(incoming.getCountermeasure());
        if (incoming.getAction()           != null) existing.setAction(incoming.getAction());
        if (incoming.getReportedby()       != null) existing.setReportedby(incoming.getReportedby());
        if (incoming.getTargetdate()       != null) existing.setTargetdate(incoming.getTargetdate());
        if (incoming.getStatus()           != null) existing.setStatus(incoming.getStatus());
        if (incoming.getIsyy()             != null) existing.setIsyy(incoming.getIsyy());
        if (incoming.getYyno()             != null) existing.setYyno(incoming.getYyno());
        if (incoming.getCompletedby()      != null) existing.setCompletedby(incoming.getCompletedby());
        if (incoming.getCompleteddate()    != null) existing.setCompleteddate(incoming.getCompleteddate());
        if (incoming.getRemarks()          != null) existing.setRemarks(incoming.getRemarks());
        if (incoming.getPctrmeasure()      != null) existing.setPctrmeasure(incoming.getPctrmeasure());
        if (incoming.getRelatedto()        != null) existing.setRelatedto(incoming.getRelatedto());
        if (incoming.getMouldid()          != null) existing.setMouldid(incoming.getMouldid());
        if (incoming.getTempfield1()       != null) existing.setTempfield1(incoming.getTempfield1());
        if (incoming.getTempfield2()       != null) existing.setTempfield2(incoming.getTempfield2());
        if (incoming.getTempfield3()       != null) existing.setTempfield3(incoming.getTempfield3());
        if (incoming.getTempfield4()       != null) existing.setTempfield4(incoming.getTempfield4());
        if (incoming.getTempfield5()       != null) existing.setTempfield5(incoming.getTempfield5());
        if (incoming.getTempfield6()       != null) existing.setTempfield6(incoming.getTempfield6());
        if (incoming.getTempfield7()       != null) existing.setTempfield7(incoming.getTempfield7());
        if (incoming.getTempfield8()       != null) existing.setTempfield8(incoming.getTempfield8());
        if (incoming.getTempfield9()       != null) existing.setTempfield9(incoming.getTempfield9());
        if (incoming.getTempfield10()      != null) existing.setTempfield10(incoming.getTempfield10());
        if (incoming.getElementid()        != null) existing.setElementid(incoming.getElementid());
        if (incoming.getFlid()             != null) existing.setFlid(incoming.getFlid());
        if (incoming.getActive()           != null) existing.setActive(incoming.getActive());
        if (incoming.getOrdertype()        != null) existing.setOrdertype(incoming.getOrdertype());
        if (incoming.getErppoststatus()    != null) existing.setErppoststatus(incoming.getErppoststatus());
        if (incoming.getErpnumber()        != null) existing.setErpnumber(incoming.getErpnumber());
        if (incoming.getSparesreplaced()   != null) existing.setSparesreplaced(incoming.getSparesreplaced());
    }

    // =========================================================================
    //  FIND BY KEYID
    // =========================================================================
    @Override
    public PlmTlGenmaintenance getById(String keyid) throws Exception {
        try {
            PlmTlGenmaintenance result = plmTlGenmaintenanceRepository.findByKeyid(keyid);
            if (result == null) {
                throw new ResourceNotFoundException("No record found for keyid: " + keyid);
            }
            logger.info("Successfully fetched General Maintenance with keyid: {}", keyid);
            return result;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error fetching General Maintenance with keyid: {}", keyid, e);
            throw new Exception("Error fetching General Maintenance with keyid: " + keyid, e);
        }
    }

    // =========================================================================
    //  GET ALL (active only)
    // =========================================================================
    @Override
    public List<PlmTlGenmaintenance> getAll() throws Exception {
        try {
            List<PlmTlGenmaintenance> result = plmTlGenmaintenanceRepository.findAllActive();
            logger.info("Successfully fetched {} active General Maintenance records", result.size());
            return result;
        } catch (Exception e) {
            logger.error("Error fetching General Maintenance list", e);
            throw new Exception("Error fetching General Maintenance list", e);
        }
    }

    // =========================================================================
    //  SOFT DELETE
    // =========================================================================
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(String keyid) throws Exception {
        logger.info("Soft-deleting General Maintenance record with keyid: {}", keyid);

        if (keyid == null || keyid.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyid cannot be null or empty");
        }

        PlmTlGenmaintenance existingRecord = plmTlGenmaintenanceRepository.findByKeyid(keyid);
        if (existingRecord == null) {
            throw new ResourceNotFoundException("General Maintenance not found with keyid: " + keyid);
        }

        int updated = plmTlGenmaintenanceRepository.softDeleteByKeyid(keyid);
        if (updated == 0) {
            throw new Exception("Soft delete failed for keyid: " + keyid);
        }

        logger.info("Successfully soft-deleted General Maintenance for keyid: {}", keyid);
        return true;
    }

    // =========================================================================
    //  PRIVATE UTILITIES
    // =========================================================================

    /**
     * Mirrors legacy FilterCondSql.isValidKeyId():
     * a keyid is "valid" when it is non-null, not blank, and not the "{}" sentinel
     * that the legacy system uses to represent a null/empty value.
     */
    private boolean isValidKeyId(String keyid) {
        return keyid != null && !keyid.trim().isEmpty() && !"{}".equals(keyid.trim());
    }

    /**
     * FIX 1: PlmTlGenmaintenance stores ALL dates as LocalDateTime (not LocalDate).
     * This method extracts the LocalDate part and returns null for sentinel dates
     * (1801-01-01, 2100-12-31) so calling code can apply a safe fallback.
     */
    private LocalDate resolveDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        LocalDate date = dateTime.toLocalDate();
        if (date.equals(PAST_NULL_DATE) || date.equals(FUTURE_NULL_DATE)) return null;
        return date;
    }

    /**
     * Null-or-empty guard for String fields: returns fallback when value is null or "{}".
     */
    private String nvl(String value, String fallback) {
        if (value == null || value.trim().isEmpty() || "{}".equals(value)) {
            return fallback;
        }
        return value;
    }

    private void applyMobilePriorityToWorkOrder(PlmTlGenmaintenance saved, String priority) {
        if (saved == null || !isValidKeyId(saved.getRefdocid()) || isBlankOrPlaceholder(priority)) {
            return;
        }
        womRepository.findById(saved.getRefdocid()).ifPresent(wom -> {
            wom.setPriority(priority.trim().charAt(0));
            wom.setModifiedon(LocalDateTime.now());
            womRepository.save(wom);
        });
    }

    private void applyMobileCompletionToWorkOrder(PlmTlGenmaintenance saved, String completedby,
            LocalDateTime workStart, LocalDateTime workEnd) {
        if (saved == null || !isValidKeyId(saved.getRefdocid())) {
            return;
        }
        womRepository.findById(saved.getRefdocid()).ifPresent(wom -> {
            wom.setStatus('C');
            wom.setFinalstatus("COMPLETED");
            wom.setWorkstartdate(workStart.toLocalDate());
            wom.setWorkenddate(workEnd.toLocalDate());
            wom.setWorkstartflag('Y');
            wom.setWorkendflag('Y');
            wom.setDoneby(completedby);
            wom.setWoapprovalby(completedby);
            wom.setMachinereleaseby(completedby);
            wom.setModifiedon(LocalDateTime.now());
            womRepository.save(wom);
        });
    }

    private LocalDateTime resolveOccurredDateTime(String occurredDate, String occurredTime, LocalDateTime fallback) {
        LocalTime timePart = parseTimeOnly(occurredTime);
        LocalDateTime fromDate = parseFlexibleDateTime(occurredDate);
        LocalDateTime fromTime = timePart == null ? parseFlexibleDateTime(occurredTime) : null;

        if (fromDate != null && timePart != null) {
            return fromDate.toLocalDate().atTime(timePart);
        }
        if (fromTime != null && fromTime.toLocalTime() != LocalTime.MIDNIGHT) {
            return fromTime;
        }
        if (fromDate != null && fromDate.toLocalTime() != LocalTime.MIDNIGHT) {
            return fromDate;
        }
        if (fromDate != null) {
            return timePart != null ? fromDate.toLocalDate().atTime(timePart) : fromDate;
        }
        if (fromTime != null) {
            return fromTime;
        }
        return fallback;
    }

    private LocalDateTime parseDateTimeOrStartOfDay(String value, LocalDateTime fallback) {
        LocalDateTime parsed = parseFlexibleDateTime(value);
        return parsed != null ? parsed : fallback;
    }

    private LocalDateTime parseFlexibleDateTime(String value) {
        if (isBlankOrPlaceholder(value)) {
            return null;
        }
        String trimmed = value.trim();
        if (parseTimeOnly(trimmed) != null) {
            return null;
        }
        DateTimeFormatter[] formatters = new DateTimeFormatter[] {
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
                DateTimeFormatter.ofPattern("d-MMM-yyyy HH:mm", Locale.ENGLISH),
                DateTimeFormatter.ofPattern("d-MMM-yyyy HH:mm:ss", Locale.ENGLISH),
                DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.ENGLISH)
        };
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(trimmed, formatter);
            } catch (DateTimeParseException ignored) {
                // try next pattern
            }
        }
        try {
            return LocalDate.parse(trimmed, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
        } catch (DateTimeParseException ignored) {
            // try desktop / card formats
        }
        try {
            return LocalDate.parse(trimmed, DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.ENGLISH)).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date: " + value);
        }
    }

    private LocalTime parseTimeOnly(String value) {
        if (isBlankOrPlaceholder(value)) {
            return null;
        }
        String trimmed = value.trim();
        DateTimeFormatter[] formatters = new DateTimeFormatter[] {
                DateTimeFormatter.ofPattern("HH:mm:ss"),
                DateTimeFormatter.ofPattern("HH:mm")
        };
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalTime.parse(trimmed, formatter);
            } catch (DateTimeParseException ignored) {
                // try next pattern
            }
        }
        return null;
    }

    private BigDecimal toDecimal(String value) {
        if (isBlankOrPlaceholder(value)) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
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

    private boolean isBlankOrPlaceholder(String value) {
        return value == null || value.trim().isEmpty() || "{}".equals(value.trim());
    }
}