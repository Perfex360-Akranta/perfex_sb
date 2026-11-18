package com.akranta.perfex_sb.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.BalPcsLossSaveDto;
import com.akranta.perfex_sb.model.*;
import com.akranta.perfex_sb.repository.*;
import com.akranta.perfex_sb.service.BAL_PcsLossService;
import com.akranta.perfex_sb.service.DbActionTemplate;


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
    

    public Bal_PcsLossSaveServiceImpl(BAL_PcsEntryMstRepository masterRepository,
                                     BAL_PcsEntryDtlRepository detailRepository,
                                     BAL_PcsLossCaptureRepository lossCaptureRepository,
                                     BAL_PcsLossReasonLinkRepository linkRepository,
                                     DbActionTemplate dbActionTemplate,
                                    DbFunctionTempleteRepository fnRepository) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.lossCaptureRepository = lossCaptureRepository;
        this.linkRepository = linkRepository;
        this.dbActionTemplate = dbActionTemplate;
        this.fnRepository = fnRepository;
       
        
    }

    @Override
    @Transactional
    public BAL_PcsLossCapture saveLoss(BalPcsLossSaveDto request) {
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

        // --- 3. One master / detail set per shift ---
        for (String[] shiftRow : shifts) {
            String shiftId = shiftRow[0];
            LocalDateTime prodDate = productionDate(cap.getFromtime(), shiftId);

            // BAL_PcsEntryMst shiftMaster = new BAL_PcsEntryMst();
            // org.springframework.beans.BeanUtils.copyProperties(master, shiftMaster);
            // shiftMaster.setShiftid(shiftId);
            // shiftMaster.setDate(prodDate);
            // shiftMaster.setEntrydate(prodDate);
            BAL_PcsEntryMst shiftMaster = new BAL_PcsEntryMst();
org.springframework.beans.BeanUtils.copyProperties(master, shiftMaster);
if (!isValidKey(master.getKeyid()))
    throw new IllegalStateException("No production entry for this shift. Save production first, then the loss.");
String masterId = master.getKeyid();

            // String masterId = findOrCreateMaster(shiftMaster, now);
           // String masterId = findOrCreateMaster(shiftMaster, now);

            // --- 4. Hour buckets of this shift ---
            // one detail/link row per shift, using the hour the user selected
            String hourNo = (isBlank(cap.getLosshrno()) || "0".equals(cap.getLosshrno()))
                    ? "1"
                    : cap.getLosshrno();
            int hourAvail = availHourly.intValue();

            // minutes for this shift: DB function row [2] if present, else the entered loss
            // time
            int lossMinutes = Integer.parseInt(cap.getLosstime().trim());

            String machineId = "S".equals(String.valueOf(master.getLogtype())) ? "0" : hourNo;

            // resolve the detail row of the selected hour (don't trust the servlet's MIN(PLDETAILSID))
String dtlId = detailRepository.findDetailIdByHour(
        master.getCellid(), cap.getShiftid(), cap.getDate().toLocalDate(),
        Integer.parseInt(hourNo));
if (!isValidKey(dtlId))
    throw new IllegalStateException(
            "No production entry for hour " + hourNo + ". Save production first, then the loss.");
if (!dtlId.equals(detail.getPldetailsid()))
    logger.warn("Servlet sent detail={} but hour {} resolves to {}; using {}",
            detail.getPldetailsid(), hourNo, dtlId, dtlId);

            // String dtlId = detail.getPldetailsid(); // 472, resolved by the servlet
            // if (!isValidKey(dtlId))
            //     throw new IllegalStateException(
            //             "No production entry for this hour. Save production first, then the loss.");

            String detailTable = detailRepository.findDetailTable(master.getCellid());
            if (detailTable == null)
                throw new IllegalStateException("No detail table for cell " + master.getCellid());

            lastDetailId = dtlId;
            saveLossLink(link, cap, shiftMaster, dtlId, hourNo, lossMinutes, now);
            detailsForRecalc.add(new String[] { dtlId, availHourly.toPlainString(), detailTable });

            // BAL_PcsEntryDtl dtl = detailRepository
            //         .findFirstByMachineidAndPlmasteridOrderByPldetailsidAsc(machineId, masterId)
            //         .orElse(null);

            // if (dtl == null) {
            //     BigDecimal unaccounted = BigDecimal.valueOf(hourAvail - lossMinutes);
            //     dtl = new BAL_PcsEntryDtl();
            //     org.springframework.beans.BeanUtils.copyProperties(detail, dtl);
            //     dtl.setPldetailsid(generate(DETAIL_SEQ, 15, "PDE", "YY", "Y"));
            //     dtl.setPlmasterid(masterId);
            //     dtl.setMachineid(machineId);
            //     dtl.setUnaccountedtime(unaccounted);
            //     dtl.setProductionavltime(unaccounted);
            //     dtl.setMchavailabletime(unaccounted);
            //     dtl.setCalendartime(availHourly);
            //     dtl.setCreatedon(now);
            //     dtl.setModifiedon(now);
            // } else {
            //     dtl.setCalendartime(availHourly);
            //     dtl.setModifiedon(now);
            // }
            // detailRepository.save(dtl);
            // lastDetailId = dtl.getPldetailsid();

            // saveLossLink(link, cap, shiftMaster, dtl.getPldetailsid(), hourNo, lossMinutes, now);
            // detailsForRecalc.add(new String[] { dtl.getPldetailsid(), availHourly.toPlainString() });

            // List<String[]> hours = getLossHourNo(shiftId, fromTime, toTime);
            // int total = hours.size();

            // for (int j = 0; j < total; j++) {
            // String hourNo = hours.get(j)[3];
            // String hourStart = hours.get(j)[1];
            // String hourEnd = hours.get(j)[2];
            // int hourAvail = Integer.parseInt(hours.get(j)[4]);

            // int lossMinutes = lossReasonMinutes(j, total, hourStart, hourEnd, fromTime,
            // toTime);

            // String machineId = "S".equals(String.valueOf(master.getLogtype())) ? "0" :
            // hourNo;

            // // --- detail: update only calendartime if it exists, else insert ---
            // BAL_PcsEntryDtl dtl = detailRepository
            // .findFirstByMachineidAndPlmasteridOrderByPldetailsidAsc(machineId, masterId)
            // .orElse(null);

            // if (dtl == null) {
            // BigDecimal unaccounted = BigDecimal.valueOf(hourAvail - lossMinutes);
            // dtl = new BAL_PcsEntryDtl();
            // org.springframework.beans.BeanUtils.copyProperties(detail, dtl);
            // dtl.setPldetailsid(generate(DETAIL_SEQ, 15, "PDE", "YY", "Y"));
            // dtl.setPlmasterid(masterId);
            // dtl.setMachineid(machineId);
            // dtl.setUnaccountedtime(unaccounted);
            // dtl.setProductionavltime(unaccounted);
            // dtl.setMchavailabletime(unaccounted);
            // dtl.setCalendartime(availHourly);
            // dtl.setCreatedon(now);
            // dtl.setModifiedon(now);
            // detailRepository.save(dtl);
            // } else {
            // // do NOT overwrite production quantities entered on the Production tab
            // dtl.setCalendartime(availHourly);
            // dtl.setModifiedon(now);
            // detailRepository.save(dtl);
            // }
            // lastDetailId = dtl.getPldetailsid();

            // // --- loss reason link: update if exists, else insert ---
            // saveLossLink(link, cap, shiftMaster, dtl.getPldetailsid(), hourNo,
            // lossMinutes, now);

            // detailsForRecalc.add(new String[] { dtl.getPldetailsid(),
            // availHourly.toPlainString() });

        }

        // --- 5. Loss capture header ---
        cap.setPldetailsid(isValidKey(lastDetailId) ? lastDetailId : "-");
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
    return calendar.toLocalDate().atStartOfDay();   // TODO apply previous-day rule only after confirming it
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

    /** TODO port legacy lossReasonMinutes(j, total, hourStart, hourEnd, from, to). */
    private int lossReasonMinutes(int j, int total, String hourStart, String hourEnd, String from, String to) {
        throw new UnsupportedOperationException("Port legacy lossReasonMinutes");
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
}