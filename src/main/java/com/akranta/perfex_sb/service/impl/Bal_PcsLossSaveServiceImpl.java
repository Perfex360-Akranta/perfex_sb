package com.akranta.perfex_sb.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.BalPcsLossSaveDto;
import com.akranta.perfex_sb.model.*;
import com.akranta.perfex_sb.repository.*;
import com.akranta.perfex_sb.service.BAL_PcsLossService;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.util.ValidationUtil;


@Service
public class Bal_PcsLossSaveServiceImpl implements BAL_PcsLossService {

    private static final Logger logger = LoggerFactory.getLogger(Bal_PcsLossSaveServiceImpl.class);
    private static final DateTimeFormatter HHMM = DateTimeFormatter.ofPattern("HH:mm");

    // NOTE: confirm against your legacy *Sql constants
    private static final String MASTER_SEQ = "PCS_TL_MST";
    private static final String DETAIL_SEQ = "PCS_TL_DTL";  
    private static final String LOSSCAP_SEQ = "BAL_PCS_TL_LOSSCAPTURE";
    private static final String LINK_SEQ = "PCS_TL_LOSSREASONLINK";

    private final BAL_PcsEntryMstRepository masterRepository;
    private final BAL_PcsEntryDtlRepository detailRepository;
    private final BAL_PcsLossCaptureRepository lossCaptureRepository;
    private final BAL_PcsLossReasonLinkRepository linkRepository;
    private final DbActionTemplate dbActionTemplate;
    private final DbFunctionTempleteRepository fnRepository;
    private final BAL_PcsTlLosscausemstRepository pcsTlLosscausemstRepository;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private DynamicPcsDtlRepository dynamicPcsDtlRepository;
    

    public Bal_PcsLossSaveServiceImpl(BAL_PcsEntryMstRepository masterRepository,
                                     BAL_PcsEntryDtlRepository detailRepository,
                                     BAL_PcsLossCaptureRepository lossCaptureRepository,
                                     BAL_PcsLossReasonLinkRepository linkRepository,
                                     BAL_PcsTlLosscausemstRepository pcsTlLosscausemstRepository,
                                     DbActionTemplate dbActionTemplate,
                                    DbFunctionTempleteRepository fnRepository) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.lossCaptureRepository = lossCaptureRepository;
        this.linkRepository = linkRepository;
        this.dbActionTemplate = dbActionTemplate;
        this.fnRepository = fnRepository;
        this.pcsTlLosscausemstRepository = pcsTlLosscausemstRepository;
        
    }

    @Override
    @Transactional
    public BAL_PcsLossCapture saveLoss(BalPcsLossSaveDto request) throws Exception {
        if (request == null)
            throw new IllegalArgumentException("Request is null");
        if (request.getMaster() == null)
            throw new IllegalArgumentException("Master payload is required");
        if (request.getDetail() == null)
            throw new IllegalArgumentException("Detail payload is required");
        if (request.getLossCapture() == null)
            throw new IllegalArgumentException("Loss capture payload is required");
        if (request.getLossReasonLink() == null)
            throw new IllegalArgumentException("Loss reason payload is required");
        if (request.getEntryBean() == null)
            request.setEntryBean(new BalPcsLossSaveDto.EntryBean());

        LocalDateTime now = LocalDateTime.now();
        BAL_PcsEntryMst master = request.getMaster();
        BAL_PcsEntryDtl detail = request.getDetail();
        BAL_PcsLossCapture cap = request.getLossCapture();
        BAL_PcsLossReasonLink link = request.getLossReasonLink();
        BigDecimal availHourly = nz(request.getEntryBean().getAvailtimehourly());

        validate(cap);
        applyDefaults(master, detail, cap, link, now);

        // --- 1. Normalise from/to time (06:00 boundary fix, same as legacy) ---
        String fromTime = normaliseFrom(cap.getFromtime());
        String toTime = normaliseTo(cap.getTotime());

        // --- 2. Split the loss across shifts (DB function) ---
        List<String[]> shifts = splitByShift(fromTime, toTime, cap.getLosstime(), master.getFactoryid());
        if (shifts.isEmpty())
            throw new IllegalStateException("No shift found for the given loss time range");

        List<String[]> detailsForRecalc = new ArrayList<>(); // {detailId, calendartime}
        String lastDetailId = null;

         String detailTable = detailRepository.findDetailTable(master.getCellid());
            if (detailTable == null)
                throw new IllegalStateException("No detail table for cell " + master.getCellid());

        // --- 3. One master / detail set per shift ---
        for (String[] shiftRow : shifts) {
            System.out.println("Shift row: " + Arrays.toString(shiftRow));
            String shiftId = shiftRow[0];
            BigDecimal shiftAvailableTime =new BigDecimal(String.valueOf(shiftRow[2]));//new BigDecimal(shiftRow[2]);
            LocalDateTime prodDate = productionDate(cap.getFromtime(), shiftId);
System.out.println("shiftAvailableTime: " + shiftAvailableTime);
            master.setShiftid(shiftId);
            master.setDate(prodDate);
            master.setEntrydate(prodDate);
            
            detail.setCalendartime(shiftAvailableTime);

            String entryDate = master.getEntrydate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.ENGLISH)).toUpperCase(Locale.ENGLISH);

    Integer checkcount = masterRepository.checkExistingMaster(
            entryDate,
            master.getShiftid(),
            master.getSectionid(),
            master.getCellid()
    );

    if (checkcount != null && checkcount > 0) {

        String existingId = masterRepository.findExistingMasterId(
                entryDate,
                master.getShiftid(),
                master.getSectionid(),
                master.getCellid()
        );

        master.setKeyid(existingId);
        master.setModifiedon(now);

        masterRepository.save(master);

        List<String[]> noOfHours = getLossHourNo(shiftId, fromTime, toTime);

int totalNoOfLossHours = noOfHours.size();

for (int j = 0; j < totalNoOfLossHours; j++) {

    String hourNo = noOfHours.get(j)[3];
    String hourAvailTime = noOfHours.get(j)[4];
    BigDecimal productionAvailTime = detail.getProductionavltime();

if (productionAvailTime != null
        && productionAvailTime.compareTo(BigDecimal.valueOf(60)) > 0) {

    hourAvailTime = productionAvailTime.stripTrailingZeros().toPlainString();

} else {
    hourAvailTime = noOfHours.get(j)[4];
}
    String hourStartTime = noOfHours.get(j)[1];
    String hourEndTime = noOfHours.get(j)[2];
System.out.println("noOfHours: " + Arrays.toString(noOfHours.get(j)));
   int lossReasonMinutes = lossReasonMinutes(
            j,
            totalNoOfLossHours,
            hourStartTime,
            hourEndTime,
            fromTime,
            toTime
    );

     BigDecimal plrkMinutes =  cap.getLosstime() != null
        ? new BigDecimal(cap.getLosstime())
        : BigDecimal.ZERO;

    detail.setPlmasterid(master.getKeyid());

    // Machine ID condition
    boolean isShiftLog = "S".equals(master.getLogtype());

    if (isShiftLog) {
        hourNo = "0";
        detail.setMachineid("0");
    } else {
        detail.setMachineid(hourNo);
    }

    // Find existing detail ID
    String dtlId = findMaxDetailId(detailTable,hourNo,detail.getPlmasterid());

    detail.setCalendartime(availHourly);

    int unaccountedTime =
            Integer.parseInt(hourAvailTime) - lossReasonMinutes;

    if (!ValidationUtil.isValidKeyId(dtlId)) {

        // New detail record
        detail.setUnaccountedtime(BigDecimal.valueOf(unaccountedTime));

        detail.setMchavailabletime(BigDecimal.valueOf(unaccountedTime));

        String newDetailId = generate(
                "PCS_TL_DTL",
                15,
                "PDE",
                "YY",
                "Y"
        );

        detail.setPldetailsid(newDetailId);
        dtlId = newDetailId;

        // Insert detail into the dynamic detail table
        dynamicPcsDtlRepository.insert(
                detailTable,
                detail
        );

    } else {

        // Existing detail record
        detail.setPldetailsid(dtlId);

        // Update detail in the dynamic detail table
        dynamicPcsDtlRepository.update(
                detailTable,
                detail
        );
    }

     // Set detail ID on loss capture
    cap.setPldetailsid(dtlId);

    detail.setPldetailsid(dtlId);

    // Set loss-reason-link fields
    link.setMinutes(plrkMinutes);
    link.setCellid(master.getCellid());
    link.setPldetailid(dtlId);
    link.setDate(master.getDate());
    link.setShiftid(master.getShiftid());

    detail.setCalendartime(availHourly);

    
    if (checkcount > 0) {

        
        createLossReasonLink(link,dtlId,link.getLossid(),link.getMinutes() );
        detailsForRecalc.add(new String[] { dtlId, availHourly.toPlainString(), detailTable });

    } else {

        
        updateLossReasonLink(link, dtlId,link.getLossid(),link.getMinutes() );
        detailsForRecalc.add(new String[] { dtlId, availHourly.toPlainString(), detailTable });
    }
}

    }else{
         master.setKeyid(generate(MASTER_SEQ, 15, "PRL", "YY", "Y"));
         master.setCreatedon(now);
         master.setModifiedon(now);

         masterRepository.save(master);

          List<String[]> noOfHours = getLossHourNo(shiftId, fromTime, toTime);

int totalNoOfLossHours = noOfHours.size();

for (int j = 0; j < totalNoOfLossHours; j++) {

    String hourNo = noOfHours.get(j)[3];
    String hourAvailTime = noOfHours.get(j)[4];

    String hourStartTime = noOfHours.get(j)[1];
    String hourEndTime = noOfHours.get(j)[2];

   int lossReasonMinutes = lossReasonMinutes(
            j,
            totalNoOfLossHours,
            hourStartTime,
            hourEndTime,
            fromTime,
            toTime
    );

     BigDecimal plrkMinutes =  cap.getLosstime() != null
        ? new BigDecimal(cap.getLosstime())
        : BigDecimal.ZERO;

    detail.setPlmasterid(master.getKeyid());

    // Find existing detail ID
    String dtlId = findMaxDetailId(detailTable,hourNo,detail.getPlmasterid());
    // Machine ID condition
    boolean isShiftLog = "Y".equals(master.getLogtype());

    if (isShiftLog) {
        hourNo = "0";
        detail.setMachineid("0");
    } else {
        detail.setMachineid(hourNo);
    }

    

    detail.setCalendartime(availHourly);

    int unaccountedTime =
            Integer.parseInt(hourAvailTime) - lossReasonMinutes;

    if (!ValidationUtil.isValidKeyId(dtlId)) {

        // New detail record
        detail.setUnaccountedtime(BigDecimal.valueOf(unaccountedTime));
        detail.setProductionavltime(BigDecimal.valueOf(unaccountedTime));
     
        detail.setMchavailabletime(BigDecimal.valueOf(unaccountedTime));

        String newDetailId = generate("PCS_TL_DTL",15,"PDE","YY","Y");

        detail.setPldetailsid(newDetailId);
        dtlId = newDetailId;

        // Insert detail into the dynamic detail table
        dynamicPcsDtlRepository.insert(
                detailTable,
                detail
        );

    } 
    
    // else {

    //     // Existing detail record
    //     detail.setPldetailsid(dtlId);

    //     // Update detail in the dynamic detail table
    //     dynamicPcsDtlRepository.update(
    //             detailTable,
    //             detail
    //     );
    // }

     // Set detail ID on loss capture
    cap.setPldetailsid(dtlId);

    detail.setPldetailsid(dtlId);

    // Set loss-reason-link fields
    link.setMinutes(plrkMinutes);
    link.setCellid(master.getCellid());
    link.setPldetailid(dtlId);
    link.setDate(master.getDate());
    link.setShiftid(master.getShiftid());

    detail.setCalendartime(availHourly);

    
    if (!ValidationUtil.isValidKeyId(master.getKeyid())) {

        
        createLossReasonLink(link,dtlId,link.getLossid(),link.getMinutes() );
        detailsForRecalc.add(new String[] { dtlId, availHourly.toPlainString(), detailTable });

    } else {

        
        updateLossReasonLink(link, dtlId,link.getLossid(),link.getMinutes() );
        detailsForRecalc.add(new String[] { dtlId, availHourly.toPlainString(), detailTable });
    }
}
    }
}


        // --- 5. Loss capture header ---
        cap.setPldetailsid(isValidKey(cap.getPldetailsid()) ? cap.getPldetailsid() : "-");
        if (!isValidKey(cap.getKeyid())) {
            cap.setKeyid(generate(LOSSCAP_SEQ, 15, "PLOS", "YYMM", "Y"));
            cap.setCreatedon(now);
        } else {
            lossCaptureRepository.findById(cap.getKeyid()).ifPresent(old -> {
                cap.setCreatedon(old.getCreatedon());
                cap.setCreatedby(old.getCreatedby());
            });
        }
        cap.setModifiedon(now);
        lossCaptureRepository.save(cap);

        // --- 6. Recalculate each affected detail row (legacy runBulkSqls) ---
        // for (String[] d : detailsForRecalc) {
        //     recalculate(d[0], new BigDecimal(d[1]), cap.getFlid());
        // }
        for (String[] d : detailsForRecalc) {
            System.out.println("detailsForRecalc: " + Arrays.toString(d));
            System.out.println("cap.getFlid(): " +cap.getFlid());
            recalculate(d[2], d[0], new BigDecimal(d[1]), cap.getFlid());
        }

        logger.info("BAL PCS loss save: capture={} shifts={} detailRows={}",
                cap.getKeyid(), shifts.size(), detailsForRecalc.size());
        return cap;
    }

    // =====================================================================
    // Master
    // =====================================================================
    private String findOrCreateMaster(BAL_PcsEntryMst m, LocalDateTime now) {
        LocalDateTime start = m.getEntrydate().toLocalDate().atStartOfDay();
        List<String> ids = masterRepository.findKeyIds(start, start.plusDays(1),
                m.getShiftid(), m.getSectionid(), m.getCellid());
        if (!ids.isEmpty()) {
            BAL_PcsEntryMst existing = masterRepository.findById(ids.get(0))
                    .orElseThrow(() -> new IllegalStateException("Master not found: " + ids.get(0)));
            existing.setUpdatedby(m.getUpdatedby());
            existing.setUpdateddate(now);
            existing.setModifiedon(now);
            masterRepository.save(existing);
            return existing.getKeyid();
        }
        m.setKeyid(generate(MASTER_SEQ, 15, "PRLM", "YY", "Y"));
        m.setCreatedon(now);
        m.setModifiedon(now);
        masterRepository.save(m);
        return m.getKeyid();
    }

    // =====================================================================
    // Loss reason link
    // =====================================================================
    private void saveLossLink(BAL_PcsLossReasonLink req, BAL_PcsLossCapture cap, BAL_PcsEntryMst m,
                              String detailId, String hourNo, int minutes, LocalDateTime now) {
        BAL_PcsLossReasonLink row = linkRepository
                .findFirstByPldetailidAndLossidAndReasonidOrderByKeyidAsc(detailId, req.getLossid(), req.getReasonid())
                .orElse(null);

        if (row == null) {
            row = new BAL_PcsLossReasonLink();
            org.springframework.beans.BeanUtils.copyProperties(req, row);
            row.setKeyid(generate(LINK_SEQ, 15, "PLRK", "YY", "Y")); // confirm prefix from legacy createLosssql
            row.setCreatedon(now);
        }
        row.setPldetailid(detailId);
        row.setMinutes(BigDecimal.valueOf(minutes));
        row.setHourno(new BigDecimal(hourNo));
        row.setCellid(m.getCellid());
        row.setDate(m.getDate());
        row.setShiftid(m.getShiftid());
        row.setFlid(cap.getFlid());
        row.setModifiedon(now);
        linkRepository.save(row);
    }

    // =====================================================================
    // Time helpers
    // =====================================================================
    /** from 06:00 -> 06:01 so it stays inside the correct shift */
   private String normaliseFrom(LocalDateTime t) {
    if (t.getHour() == 6 && t.getMinute() == 0) t = t.withMinute(1);
    return t.format(HHMM);
}

    /** to 06:00 -> 05:59 */
   private String normaliseTo(LocalDateTime t) {
    if (t.getHour() == 6 && t.getMinute() == 0) t = t.withHour(5).withMinute(59);
    return t.format(HHMM);
}

    /** SFT001 / SFT008 belong to the previous production day (legacy rule). */
 private LocalDateTime productionDate(LocalDateTime calendar, String shiftId) {
    // return calendar.toLocalDate().atStartOfDay();   // TODO apply previous-day rule only after confirming it
    LocalDate productionDate = calendar.toLocalDate();

    if ("SFT001".equals(shiftId)
            || "SFT008".equals(shiftId)) {

        productionDate = productionDate.minusDays(1);
    }

    return productionDate.atStartOfDay();
}
    // =====================================================================
    // TODO: port these from the legacy code
    // =====================================================================

    /** Legacy: dbActionTemplate.processFunctionCalls("PCS_FN_PRODLOSSSHIFTCAL", [from, to, losstime, factoryid]).
     *  Returns one row per shift: [0]=shiftId, [2]=calendar time. */
    private List<String[]> splitByShift(String from, String to, String lossTime, String factoryId) {
    try {
        Map<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, from);
        params.put(2, to);
        params.put(3, lossTime);
        params.put(4, factoryId);

        Map<String, Object> result =
                fnRepository.callMultipeParamFunction("PCS_FN_PRODLOSSSHIFTCAL", params);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("cur");

        List<String[]> out = new ArrayList<>();
        if (rows != null) {
            for (Map<String, Object> r : rows) {
                String[] arr = new String[r.size()];
                int i = 0;
                for (Object v : r.values()) {      // order preserved by LinkedHashMap
                    arr[i++] = v == null ? null : String.valueOf(v);
                }
                out.add(arr);
            }
        }
        return out;
    } catch (SQLException e) {
        throw new IllegalStateException("Shift split failed: " + e.getMessage(), e);
    }
}

    /** TODO port legacy getLossHourNo(shiftId, from, to).
     *  Row layout used: [1]=hour start, [2]=hour end, [3]=hour no, [4]=hour available time. */
    // private List<String[]> getLossHourNo(String shiftId, String from, String to) {
    //     throw new UnsupportedOperationException("Port legacy getLossHourNo");
    // }

    public List<String[]> getLossHourNo(
        String shiftId,
        String dfromtime,
        String dtoTime) {

    try {
        // Get shift start time
        String shiftStart = lossCaptureRepository.findShiftStartTime(shiftId);

        if (shiftStart == null || shiftStart.isBlank()) {
            return Collections.emptyList();
        }

        // Convert HH:mm to HHmm, as in the legacy code
        String fromTime = dfromtime.replace(":", "");
        String toTime = dtoTime.replace(":", "");

        // Get hourly slots from repository
        List<Object[]> hourRows = lossCaptureRepository.findLossHours(
                shiftId,
                shiftStart,
                fromTime,
                toTime
        );

        if (hourRows == null || hourRows.isEmpty()) {
            return Collections.emptyList();
        }

        // Convert List<Object[]> to legacy List<String[]>
        List<String[]> noOfHours = new ArrayList<>();

        for (Object[] row : hourRows) {

            String[] hour = new String[5];

            for (int i = 0; i < row.length; i++) {
                hour[i] = row[i] != null
                        ? row[i].toString()
                        : null;
            }

            noOfHours.add(hour);
        }

        return noOfHours;

    } catch (Exception e) {
        logger.error("Exception in getLossHourNo(): {}", e.getMessage(), e);
        throw new RuntimeException("Unable to get loss hour details", e);
    }
}

    /** TODO port legacy lossReasonMinutes(j, total, hourStart, hourEnd, from, to). */
    // private int lossReasonMinutes(int j, int total, String hourStart, String hourEnd, String from, String to) {
       
    // }

//     private int lossReasonMinutes(
//         LocalDateTime hourStart,
//         LocalDateTime hourEnd,
//         LocalDateTime lossStart,
//         LocalDateTime lossEnd) {

//     LocalDateTime actualStart =
//             hourStart.isAfter(lossStart) ? hourStart : lossStart;

//     LocalDateTime actualEnd =
//             hourEnd.isBefore(lossEnd) ? hourEnd : lossEnd;

//     if (!actualEnd.isAfter(actualStart)) {
//         return 0;
//     }

//     return (int) Duration.between(actualStart, actualEnd).toMinutes();
// }

private int lossReasonMinutes(int j,int totalNoOfLossHours,String hourStartTime,String hourEndTime,String dfromtime, String dtoTime) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    LocalTime lossFrom = LocalTime.parse(dfromtime, formatter);
    LocalTime lossTo = LocalTime.parse(dtoTime, formatter);

    LocalTime hourStart = LocalTime.parse(
            hourStartTime, formatter);

    LocalTime hourEnd = LocalTime.parse(
            hourEndTime, formatter);

    int minutes;

    if (j == 0) {

        if (totalNoOfLossHours != 1) {
            minutes = (int) Duration.between(lossFrom, hourEnd).toMinutes();
        } else {
            minutes = (int) Duration.between(lossFrom, lossTo).toMinutes();
        }

    } else if (j == totalNoOfLossHours - 1) {

        minutes = (int) Duration.between(hourStart, lossTo).toMinutes();

    } else {

        minutes = (int) Duration.between(hourStart, hourEnd).toMinutes();
    }

    if (minutes < 0) {
        minutes += 1440;
    }

    return minutes;
}


public String findMaxDetailId(
        String detailTableName,
        String machineId,
        String masterId) {

    // Validate the dynamic table identifier before using it in SQL.
    if (detailTableName == null ||
            !detailTableName.matches("[a-zA-Z0-9_\\.]+")) {
        throw new IllegalArgumentException("Invalid detail table name");
    }

    String sql = """
        SELECT MAX(PLDETAILSID)
        FROM %s
        WHERE MACHINEID = :machineId
          AND PLMASTERID = :masterId
        """.formatted(detailTableName);

    MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("machineId", machineId)
            .addValue("masterId", masterId);

    return namedParameterJdbcTemplate.queryForObject(
            sql, params, String.class);
}

    /** TODO port legacy runBulkSqls(detailTable, detailId, calendartime, flid). */
    // private void recalculate(String detailId, BigDecimal calendartime, String flid) {
    //     detailRepository.flush();
    //     lossCaptureRepository.flush();
    //     linkRepository.flush();
    //     logger.debug("recalculate detail={} calendartime={} flid={}", detailId, calendartime, flid);
    // }

    //01nov

    private void recalculate(String tbl, String dtlId, BigDecimal avail, String flid) {
    detailRepository.flush(); lossCaptureRepository.flush(); linkRepository.flush();
    Integer r = detailRepository.recalculate(tbl, dtlId, avail.toPlainString(), flid);
    if (r == null || r < 0) {
        String why = r == null ? "no result"
                   : r == -2 ? "ROP exceeds the MAXROP limit"
                   : r == -1 ? "save_pcs_tl_day failed" : "unknown";
        throw new IllegalStateException("Recalculation failed: " + r + " (" + why + ") detail=" + dtlId);
    }
}

//     private void recalculate(String detailTable, String detailId, BigDecimal avail, String flid) {
//     detailRepository.flush(); lossCaptureRepository.flush(); linkRepository.flush();
//     Integer r = detailRepository.recalculate(detailTable, detailId, avail.toPlainString(), flid);
//     if (r == null || r < 0) throw new IllegalStateException("Recalculation failed: " + r);
// }

    // =====================================================================
    // Validation / defaults
    // =====================================================================
    private void validate(BAL_PcsLossCapture cap) {
        if (cap.getFromtime() == null || cap.getTotime() == null)
            throw new IllegalArgumentException("From time and to time are required");
        if (!cap.getTotime().isAfter(cap.getFromtime()))
            throw new IllegalArgumentException("To time must be after from time");
        if (isBlank(cap.getLosstime()))
            throw new IllegalArgumentException("Loss time is required");
        if (isBlank(cap.getLossid()))
            throw new IllegalArgumentException("Loss id is required");
    }

    private void applyDefaults(BAL_PcsEntryMst master, BAL_PcsEntryDtl detail, BAL_PcsLossCapture cap,
                               BAL_PcsLossReasonLink link, LocalDateTime now) {
        if (isBlank(master.getCellid())) throw new IllegalArgumentException("Cell id is required");
        if (isBlank(master.getSectionid())) throw new IllegalArgumentException("Section id is required");
        if (isBlank(detail.getCellid())) detail.setCellid(master.getCellid());

        // master NOT NULL columns (same fallbacks as production save)
        String by = !isBlank(master.getCreatedby()) ? master.getCreatedby() : cap.getCreatedby();
        master.setCreatedby(orDash(by));
        master.setEntryby(orDash(master.getEntryby() != null ? master.getEntryby() : by));
        master.setUpdatedby(orDash(master.getUpdatedby() != null ? master.getUpdatedby() : by));
        master.setFactoryid(orDash(master.getFactoryid()));
        master.setApprovedby(orDash(master.getApprovedby()));
        master.setShiftincharge(orDash(master.getShiftincharge()));
        master.setSubgroupid(orDash(master.getSubgroupid()));
        master.setTimeorqty(orDash(master.getTimeorqty()));
        master.setCompletedflag(orDash(master.getCompletedflag()));
        master.setTempfield2(orDash(master.getTempfield2()));
        master.setElementid(orDash(master.getElementid()));
        master.setMachineid(orDash(master.getMachineid()));
        master.setFlid(orDash(!isBlank(master.getFlid()) ? master.getFlid() : cap.getFlid()));
        if (master.getUpdateddate() == null) master.setUpdateddate(now);
        if (master.getApprovedate() == null) master.setApprovedate(now);
        if (master.getPrlm_apporvedflag() == null) master.setPrlm_apporvedflag('N');
        if (master.getLogtype() == null) master.setLogtype('-');
        if (master.getIsnoplan() == null) master.setIsnoplan('N');
        if (master.getActive() == null) master.setActive('Y');
        if (master.getCreatedon() == null) master.setCreatedon(now);
        if (master.getModifiedon() == null) master.setModifiedon(now);

        // detail NOT NULL columns
        if (detail.getOperators() == null) detail.setOperators(BigDecimal.ZERO);
        if (detail.getCompletedflag() == null) detail.setCompletedflag('N');
        detail.setRemarks(orDash(detail.getRemarks()));
        detail.setModelchangepart(orDash(detail.getModelchangepart()));
        if (detail.getActive() == null) detail.setActive('Y');
        if (isBlank(detail.getCreatedby())) detail.setCreatedby(master.getCreatedby());
        if (detail.getCreatedon() == null) detail.setCreatedon(now);
        if (detail.getModifiedon() == null) detail.setModifiedon(now);

        // loss capture NOT NULL columns
        if (cap.getDate() == null) cap.setDate(cap.getFromtime().toLocalDate().atStartOfDay());
        if (isBlank(cap.getShiftid())) cap.setShiftid(orDash(master.getShiftid()));
        if (isBlank(cap.getFlid())) cap.setFlid(master.getFlid());
        cap.setLossreason(orDash(cap.getLossreason()));
        cap.setTradeid(orDash(cap.getTradeid()));
        cap.setLossdescription(orDash(cap.getLossdescription()));
        cap.setLossremarks(orDash(cap.getLossremarks()));
        cap.setLosshrno(isBlank(cap.getLosshrno()) ? "0" : cap.getLosshrno());
        cap.setMachineid(orDash(cap.getMachineid()));
        cap.setPldetailsid(orDash(cap.getPldetailsid()));
        if (cap.getProdImpact() == null) cap.setProdImpact('N');
        if (cap.getProdImpQty() == null) cap.setProdImpQty(BigDecimal.ZERO);
        if (cap.getActive() == null) cap.setActive('Y');
        cap.setCreatedby(orDash(!isBlank(cap.getCreatedby()) ? cap.getCreatedby() : master.getCreatedby()));
        if (cap.getCreatedon() == null) cap.setCreatedon(now);
        if (cap.getModifiedon() == null) cap.setModifiedon(now);

        // link NOT NULL columns
        link.setElementid(orDash(link.getElementid()));
        if (link.getActive() == null) link.setActive('Y');
        link.setCreatedby(orDash(!isBlank(link.getCreatedby()) ? link.getCreatedby() : master.getCreatedby()));
        if (link.getCreatedon() == null) link.setCreatedon(now);
        if (link.getModifiedon() == null) link.setModifiedon(now);
        if (isBlank(link.getLossid())) link.setLossid(cap.getLossid());
        if (isBlank(link.getReasonid())) link.setReasonid(cap.getLossreason());
    }

   private String generate(String seqId, int len, String prefix, String dateFmt, String reset) {
    try {
        String key = dbActionTemplate.getSequenceNumber(seqId, len, prefix.trim(), dateFmt, reset);
        return key == null ? null : key.trim();
    } catch (Exception e) {
        throw new IllegalStateException("Failed to generate sequence " + seqId + ": " + e.getMessage(), e);
    }
}

    private boolean isValidKey(String key) {
        return !isBlank(key) && !"{}".equals(key) && !"<**>".equals(key) && !"-".equals(key)
                && !"null".equalsIgnoreCase(key) && !"undefined".equalsIgnoreCase(key)
                && !"false".equalsIgnoreCase(key);
    }

    private boolean isBlank(String v) {
    return v == null || v.trim().isEmpty() || "{}".equals(v.trim()) || "<**>".equals(v.trim());
}
    private String orDash(String v) { return isBlank(v) ? "-" : v; }
    private BigDecimal nz(BigDecimal v) { return v == null ? BigDecimal.ZERO : v; }


    public String getColName(String parameterCode) {

    if (parameterCode == null) {
        return null;
    }

    return switch (parameterCode) {

        case "MCHAVLTIME" -> "MCHAVAILABLETIME";
        case "PLANNEDQTY", "SPEEDVARIATION" -> "PLANNEDQTY";
        case "PRODTIME" -> "PRODUCTIONTIME";
        case "UNREPORTED" -> "UNACCOUNTEDTIME";
        case "NOPLAN" -> "NOPLANINMINS";
        case "NUMPRODUCTS" -> "NOOFPRODUCTS";
        case "PARTNO" -> "PARTNO";
        case "PARTNAME" -> "PARTNAME";
        case "PRODUCTMODEL" -> "PRODUCTMODEL";
        case "CYCLETIME" -> "CYCLETIME";
        case "NUMOPERATOR" -> "OPERATORS";
        case "OPNNO" -> "OPERATIONNO";
        case "OPNDESC" -> "OPERATIONDESCRIPTION";
        case "WNO" -> "WNO";
        case "WEIGHT" -> "WEIGHT";
        case "RAWMATERIALTYPE" -> "RAWMATERIALTYPE";
        case "PRODUCEDQTY" -> "PRODUCEDQTY";

        case "INSPECTEDQTY" -> "INSPECTEDQTY";
        case "DEFECTNREWORK" -> "DEFECTSANDREWORKLOSS_ML";
        case "REJECTEDQTY" -> "REJECTEDQTY";
        case "REWORKQTY" -> "REWORKQTY";
        case "QAACCEPTEDQTY" -> "QAACCEPTEDQTY";
        case "DEFECTPREVPROC" -> "DEFECTPREVPROC";
        case "DEFECTTIME" -> "DEFECTTIME_SL";
        case "MRNMT" -> "MRNMT";
        case "REPROCESS" -> "REPROCESSING";
        case "PRODUCTIONLOSS" -> "PRODUCTIONLOSSES";

        case "EF" -> "EQUIPMENTFAILURE_ML";
        case "EFE" -> "EF_ELECTRICAL";
        case "EFL" -> "EF_ELECTRONICS";
        case "EFM" -> "EF_MECHANICAL";
        case "EFJ" -> "EF_JIGFIXTURELOSS";
        case "PEFL", "UEFL", "PROCESSFAILURE" -> parameterCode;

        case "SETUPANDADJ" -> "SETUPANDADJUSTMENT_ML";
        case "MODELCHANGEPART" -> "MODELCHANGEPART";
        case "MODELCHANGE" -> "MODELCHANGE";
        case "MODELADJUSTMENT" -> "MODELADJUSTMENT";
        case "PARTMODEL" -> "PARTMODEL";

        case "TOOLCHANGELOSS" -> "TOOLCHANGELOSS_ML";
        case "STARTUPLOSS" -> "STARTUPLOSS_ML";
        case "MS" -> "MINORSTOPPAGELOSS_ML";
        case "SPEEDLOSS" -> "SPEEDLOSS_ML";
        case "SHUTDOWNLOSS" -> "SHUTDOWNLOSS_ML";
        case "MANAGEMENTLOSS" -> "MANAGEMENTLOSS_ML";

        case "COMMONUTILITY" -> "COMMONUTILITYLOSS_SL";
        case "MOTIONLOSS" -> "OPERATINGMOTIONLOSS_ML";
        case "LINEORGLOSS" -> "LINEORGANISATIONLOSS_ML";
        case "LOGISTICSLOSS" -> "LOGISTICSLOSS_ML";
        case "MEASUREMENTLOSS" -> "MEASURINGANDADJLOSS_ML";
        case "DIETOOLJIGLOSS" -> "DIETOOLANDJIGLOSS_ML";
        case "ENERGYLOSS" -> "ENERGYLOSS_ML";
        case "YIELDLOSS" -> "YIELDLOSS_ML";

        case "RESULTS" -> "RESULTS";
        case "LOADINGTIME" -> "LOADINGTIME";
        case "PRODAVLTIME" -> "PRODUCTIONAVLTIME";
        case "EFFPRODMINS" -> "EFFECTIVEPRODMINS";
        case "ROA" -> "ROA";
        case "ROP", "ROQ" -> "ROQ";
        case "OEE" -> "OEEREMARKS";
        case "REMARKS" -> "REMARKS";

        default -> null;
    };
}


public void createLossReasonLink(
        BAL_PcsLossReasonLink link,
        String detailId,
        String lossId,
        BigDecimal lossValue) throws Exception {

    // 1. Get dynamic detail table
    // String detailTableName = getDetailTable(
    //         link.getCellid(),
    //         link.getDate()
    // );
    String detailTableName = detailRepository.findDetailTable(link.getCellid());
 
    // 2. Read loss configuration
    String lossMapId = masterRepository.findLossMapField(lossId);

    String parameterCode = masterRepository.findParameterCode(lossId);

    String lossColName = getColName(parameterCode);
//     if (lossColName == null || lossColName.isBlank()) {
//     throw new IllegalArgumentException(
//             "No loss column mapping found for parameter code: " + parameterCode
//     );
// }

    // 3. Insert/update loss cause master
    // Use the existing cause ID to check whether it exists.
    boolean  causeExists = pcsTlLosscausemstRepository.existsById(link.getReasonid());
    BAL_PcsTlLosscausemst pcsTlLosscausemst = new BAL_PcsTlLosscausemst();
    if (pcsTlLosscausemst.getDescription() == null)
        pcsTlLosscausemst.setDescription("{}");

    if (pcsTlLosscausemst.getPhenomenaid() == null)
        pcsTlLosscausemst.setPhenomenaid("{}");

    if (pcsTlLosscausemst.getTempfield2() == null)
        pcsTlLosscausemst.setTempfield2("{}");

    if (pcsTlLosscausemst.getTempfield3() == null)
        pcsTlLosscausemst.setTempfield3("{}");

     // If it does not exist, generate a new ID and insert.
    // If it exists, update its description.
    if (!causeExists) {

        // Generate new cause master ID
        pcsTlLosscausemst.setKeyid(generate("PCS_TL_LOSSCAUSEMST", 15, "PLRK", "YY", "Y") );

        pcsTlLosscausemst.setPhenomenaid(link.getReasonid());

        pcsTlLosscausemst.setDescription(link.getCauseid());

        // Replace cause text with generated cause key
        link.setCauseid(pcsTlLosscausemst.getKeyid());

        pcsTlLosscausemstRepository.save(pcsTlLosscausemst);

    } else {

        // Existing cause
        pcsTlLosscausemst.setKeyid(link.getCauseid());

        pcsTlLosscausemstRepository.save(pcsTlLosscausemst);
    }

   

    // 4. Generate loss-reason link ID
    // Use your existing sequence-number generation method.
     link.setKeyid(generate(LINK_SEQ, 15, "PLRK", "YY", "Y"));

    // 5. Check duplicate loss link
    long count = linkRepository.countExistingLossLink(
            link.getLossid(),
            link.getReasonid(),
            link.getCauseid(),
            link.getRootcauseid(),
            detailId,
            link.getKeyid()
    );

    if (count > 0) {
        throw new RuntimeException("LOSSEXIST,");
    }

    // 6. Save loss-reason link
    linkRepository.saveAndFlush(link);

    // 7. Update dynamic detail table
    dynamicPcsDtlRepository.updateLossValue(
            detailTableName,
            detailId,
            lossId,
            lossMapId,
            lossColName
    );

    // 8. Delete and reinsert ancillary-time records
}



public void updateLossReasonLink(
        BAL_PcsLossReasonLink link,
        String detailId,
        String lossId,
        BigDecimal lossValue) throws Exception {

    // 1. Get dynamic detail table
    // String detailTableName = getDetailTable(
    //         link.getCellid(),
    //         link.getDate()
    // );
    String detailTableName = detailRepository.findDetailTable(link.getCellid());
 
    // 2. Read loss configuration
    String lossMapId = masterRepository.findLossMapField(lossId);

    String parameterCode = masterRepository.findParameterCode(lossId);

    String lossColName = getColName(parameterCode);
//     if (lossColName == null || lossColName.isBlank()) {
//     throw new IllegalArgumentException(
//             "No loss column mapping found for parameter code: " + parameterCode
//     );
// }

   

   

    // 5. Check duplicate loss link
    long count = linkRepository.countExistingLossLink(
            link.getLossid(),
            link.getReasonid(),
            link.getCauseid(),
            link.getRootcauseid(),
            detailId,
            link.getKeyid()
    );

    if (count > 0) {
        throw new RuntimeException("LOSSEXIST,");
    }

    // 6. Save loss-reason link
    linkRepository.saveAndFlush(link);

    // 7. Update dynamic detail table
    dynamicPcsDtlRepository.updateLossValue(
            detailTableName,
            detailId,
            lossId,
            lossMapId,
            lossColName
    );

    // 8. Delete and reinsert ancillary-time records
}

}