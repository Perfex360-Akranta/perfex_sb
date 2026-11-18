// package com.akranta.perfex_sb.service.impl;

// import java.math.BigDecimal;
// import java.sql.SQLException;
// import java.time.LocalDateTime;
// import java.util.LinkedHashMap;
// import java.util.List;
// import java.util.Map;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.BeanUtils;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.akranta.perfex_sb.dto.BalPcsEntrySaveDto;
// import com.akranta.perfex_sb.model.BAL_PcsEntryDtl;
// import com.akranta.perfex_sb.model.BAL_PcsEntryMst;
// import com.akranta.perfex_sb.model.BAL_PcsEntryProductiondata;
// import com.akranta.perfex_sb.repository.BAL_PcsEntryDtlRepository;
// import com.akranta.perfex_sb.repository.BAL_PcsEntryMstRepository;
// import com.akranta.perfex_sb.repository.BAL_PcsEntryProductiondataRepository;
// import com.akranta.perfex_sb.service.BAL_PcsEntryService;
// import com.akranta.perfex_sb.service.DbActionTemplate;
// import com.akranta.perfex_sb.repository.DbFunctionTempleteRepository;


// @Service
// public class BalPcsEntrySaveServiceImpl implements BAL_PcsEntryService {

    

//     private static final Logger logger = LoggerFactory.getLogger(BalPcsEntrySaveServiceImpl.class);

//     // NOTE: use the same values as BAL_PcsTlMstSql.TBL_BAL_PCS_TL_MST etc. in your legacy code
//     private static final String MASTER_SEQ_IDENTIFIER = "BAL_PCS_TL_MST";
//     private static final int MASTER_KEY_LENGTH = 15;
//     private static final String MASTER_PREFIX = "PRL"; // legacy had "\tPRLM" (stray tab)
//     private static final String MASTER_DATE_FORMAT = "YY";
//     private static final String MASTER_FORMAT_RESET = "Y";

//     private static final String DETAIL_SEQ_IDENTIFIER = "BAL_PCS_TL_DTL";
//     private static final int DETAIL_KEY_LENGTH = 15;
//     private static final String DETAIL_PREFIX = "PDE";
//     private static final String DETAIL_DATE_FORMAT = "YY";
//     private static final String DETAIL_FORMAT_RESET = "Y";

//     private static final String PRODUCTION_SEQ_IDENTIFIER = "BAL_PCS_TL_PRODUCTIONDATA";
//     private static final int PRODUCTION_KEY_LENGTH = 15;
//     private static final String PRODUCTION_PREFIX = "PRL"; // legacy used "PPRD" in one branch
//     private static final String PRODUCTION_DATE_FORMAT = "YY";
//     private static final String PRODUCTION_FORMAT_RESET = "Y";

//     private final BAL_PcsEntryMstRepository masterRepository;
//     private final BAL_PcsEntryDtlRepository detailRepository;
//     private final BAL_PcsEntryProductiondataRepository productionRepository;
//     private final DbActionTemplate dbActionTemplate;
//     private final DbFunctionTempleteRepository fnRepository;

//     public BalPcsEntrySaveServiceImpl(BAL_PcsEntryMstRepository masterRepository,
//                                       BAL_PcsEntryDtlRepository detailRepository,
//                                       BAL_PcsEntryProductiondataRepository productionRepository,
//                                       DbActionTemplate dbActionTemplate,
//                                     DbFunctionTempleteRepository fnRepository) {
//         this.masterRepository = masterRepository;
//         this.detailRepository = detailRepository;
//         this.productionRepository = productionRepository;
//         this.dbActionTemplate = dbActionTemplate;
//         this.fnRepository = fnRepository;
//     }

//     /** Everything one save needs, so helper methods don't take 8 parameters. */
//     private record Ctx(BAL_PcsEntryMst master,
//                        BAL_PcsEntryDtl detail,
//                        BAL_PcsEntryProductiondata prod,
//                        BalPcsEntrySaveDto.EntryBean bean,
//                        LocalDateTime now,
//                        boolean hourlyNoPlan,
//                        boolean shiftNoPlan,
//                        int noPlanDuration) {
//     }

//     @Override
//     @Transactional
//     public BAL_PcsEntryProductiondata createNew(BalPcsEntrySaveDto request) {
//         if (request == null)
//             throw new IllegalArgumentException("Request is null");
//         if (request.getMaster() == null)
//             throw new IllegalArgumentException("Master payload is required");
//         if (request.getDetail() == null)
//             throw new IllegalArgumentException("Detail payload is required");
//         if (request.getProductionData() == null)
//             throw new IllegalArgumentException("Production data payload is required");
//         if (request.getEntryBean() == null)
//             request.setEntryBean(new BalPcsEntrySaveDto.EntryBean());

//         LocalDateTime now = LocalDateTime.now();
//         BAL_PcsEntryMst master = request.getMaster();
//         BAL_PcsEntryDtl detail = request.getDetail();
//         BAL_PcsEntryProductiondata prod = request.getProductionData();
//         BalPcsEntrySaveDto.EntryBean bean = request.getEntryBean();

//         // --- Apply all defaults BEFORE any lookup ---
//         applyDefaults(master, detail, prod, now);

//         boolean hourlyNoPlan = "Y".equals(prod.getHourlynoplan());
//         boolean shiftNoPlan = "Y".equals(prod.getShiftnoplan());
//         int noPlanDuration = prod.getNoplanduration() == null ? 0 : prod.getNoplanduration();
//         Ctx ctx = new Ctx(master, detail, prod, bean, now, hourlyNoPlan, shiftNoPlan, noPlanDuration);

//         // --- Master: one per (entrydate, shift, section, cell) ---
//         String existingMasterId = findExistingMasterId(master);

//         BAL_PcsEntryProductiondata result;
//         if (existingMasterId != null) {
//             result = handleExistingMaster(ctx, existingMasterId);
//         } else {
//             result = handleNewMaster(ctx);
//         }

//         // Legacy: if (!shiftNoplan) runBulkSqls(...)
//         if (!shiftNoPlan && isValidKey(detail.getPldetailsid())) {
//             recalculate(detail.getPldetailsid(), detail.getCalendartime(), master.getFlid());
//         }

//         logger.info("BAL PCS createNew [{}]: master={} detail={} prod={} hourlyNoPlan={} shiftNoPlan={}",
//                 existingMasterId != null ? "EXISTING_MASTER" : "NEW_MASTER",
//                 master.getKeyid(), detail.getPldetailsid(), result.getKeyid(), hourlyNoPlan, shiftNoPlan);
//         return result;
//     }

//     // =====================================================================
//     // Master already exists for (date, shift, section, cell)
//     // =====================================================================
//     private BAL_PcsEntryProductiondata handleExistingMaster(Ctx c, String masterId) {
//         BAL_PcsEntryMst master = c.master();
//         BAL_PcsEntryDtl detail = c.detail();
//         BAL_PcsEntryProductiondata prod = c.prod();

//         // Legacy ran a full UPDATE of the master from the request. Here only the audit
//         // columns are touched so created* / approval columns are not overwritten.
//         BAL_PcsEntryMst existing = masterRepository.findById(masterId)
//                 .orElseThrow(() -> new IllegalStateException("Master " + masterId + " not found"));
//         if (!isBlank(master.getUpdatedby()))
//             existing.setUpdatedby(master.getUpdatedby());
//         existing.setUpdateddate(c.now());
//         existing.setModifiedon(c.now());
//         masterRepository.save(existing);

//         master.setKeyid(masterId);
//         detail.setPlmasterid(masterId);

//         // --- Resolve existing detail row ---
//         String dtlId = null;
// if (isValidKey(detail.getPldetailsid())) {
//     dtlId = detailRepository.findById(detail.getPldetailsid())
//             .filter(d -> masterId.equals(d.getPlmasterid()))
//             .map(BAL_PcsEntryDtl::getPldetailsid)
//             .orElse(null);
// }
// if (!isValidKey(dtlId)) {
//     dtlId = detailRepository
//             .findFirstByMachineidAndProductidAndPlmasteridAndActualcycletimeOrderByPldetailsidAsc(
//                     detail.getMachineid(), detail.getProductid(), masterId, detail.getActualcycletime())
//             .map(BAL_PcsEntryDtl::getPldetailsid).orElse(null);
// }

//         if (!isValidKey(dtlId) && isValidKey(prod.getProductid()) && c.noPlanDuration() != 0) {
//             dtlId = findDetailIdByMachineAndMaster(detail.getMachineid(), masterId);
//         }

//         if (!isValidKey(dtlId) && c.hourlyNoPlan()) {
//             dtlId = findDetailIdByMachineAndMaster(detail.getMachineid(), masterId);
//         }

//         // Loss-entry case: the detail row already exists but has no product yet
//         if (!isValidKey(dtlId) && !c.hourlyNoPlan() && c.noPlanDuration() == 0) {
//             String dtlProductId = detailRepository
//                     .findFirstByMachineidAndPlmasteridOrderByPldetailsidAsc(detail.getMachineid(), masterId)
//                     .map(BAL_PcsEntryDtl::getProductid).orElse(null);
//             if (!isValidKey(dtlProductId)) {
//                 dtlId = findDetailIdByMachineAndMaster(detail.getMachineid(), masterId);
//                 if (isValidKey(dtlId)) {
//                     // Legacy inserted a fresh production row for this existing detail
//                     prod.setKeyid(null);
//                     prod.setHourlynoplan("N");
//                 }
//             }
//         }

//         // --- Persist ---
//         if (isValidKey(dtlId)) {
//             updateExistingDetailAndProduction(c, dtlId, masterId);
//             return prod;
//         }
//         if (c.shiftNoPlan()) {
//             return createShiftNoPlanRows(c, masterId, false);
//         }
//         if (c.hourlyNoPlan()) {
//             createHourlyNoPlanRow(c, masterId);
//             return prod;
//         }
//         // hourly = N and shift = N
//         createRegularRow(c, masterId, true);
//         return prod;
//     }

//     // =====================================================================
//     // No master yet -> create master + detail + production data
//     // =====================================================================
//     private BAL_PcsEntryProductiondata handleNewMaster(Ctx c) {
//         BAL_PcsEntryMst master = c.master();

//         if (c.hourlyNoPlan()) {
//             String masterId = insertMaster(master);
//             createHourlyNoPlanRow(c, masterId);
//             return c.prod();
//         }
//         if (c.shiftNoPlan()) {
//             String masterId = insertMaster(master);
//             return createShiftNoPlanRows(c, masterId, true);
//         }
//         if (c.bean().getAddrowtoGrid() == null) {
//             String masterId = insertMaster(master);
//             // Legacy only set calendartime for this branch (not the other hourly times)
//             createRegularRow(c, masterId, false);
//             return c.prod();
//         }

//         // Legacy silently saved nothing here.
//         logger.warn("BAL PCS createNew: nothing saved (new master, no-plan flags N, addrowtoGrid={})",
//                 c.bean().getAddrowtoGrid());
//         return c.prod();
//     }

//     // =====================================================================
//     // Row builders
//     // =====================================================================

//     /** Hourly no-plan: detail with loading time = available - noplan duration. */
//     private void createHourlyNoPlanRow(Ctx c, String masterId) {
//         BAL_PcsEntryDtl detail = c.detail();
//         BalPcsEntrySaveDto.EntryBean bean = c.bean();

//         BigDecimal avail = nz(bean.getAvailtimehourly());
//         BigDecimal loading = avail.subtract(BigDecimal.valueOf(c.noPlanDuration()));

//         detail.setLoadingtime(loading);
//         detail.setCalendartime(avail);
//         detail.setMchavailabletime(avail);
//         detail.setProductionavltime(loading);
//         detail.setUnaccountedtime(nz(bean.getUnreptimehourly()));
//         detail.setNoplaninmins(BigDecimal.valueOf(c.noPlanDuration()));

//         insertDetail(detail, masterId);

//         BAL_PcsEntryProductiondata prod = c.prod();
//         prod.setKeyid(null);
//         prod.setHourlynoplan("Y");
//         persistProduction(prod, detail.getPldetailsid(), masterId, c.now());
//     }

//     /**
//      * Normal entry (hourly = N, shift = N).
//      * @param fullHourlyTimes true  -> existing-master legacy branch (sets all five time columns)
//      *                        false -> new-master legacy branch (sets calendartime only)
//      */
//     private void createRegularRow(Ctx c, String masterId, boolean fullHourlyTimes) {
//         BAL_PcsEntryDtl detail = c.detail();
//         BalPcsEntrySaveDto.EntryBean bean = c.bean();
//         BigDecimal avail = nz(bean.getAvailtimehourly());

//         if (fullHourlyTimes) {
//             detail.setUnaccountedtime(nz(bean.getUnreptimehourly()));
//             detail.setProductiontime(nz(bean.getProdtimehourly()));
//             detail.setProductionavltime(avail);
//             detail.setMchavailabletime(avail);
//         }
//         detail.setCalendartime(avail);

//         insertDetail(detail, masterId);

//         BAL_PcsEntryProductiondata prod = c.prod();
//         prod.setKeyid(null);
//         persistProduction(prod, detail.getPldetailsid(), masterId, c.now());
//     }

//     /**
//      * Shift no-plan: one detail + one production row per hour of the shift.
//      * A NEW entity is built for every hour (legacy re-used one mutable bean).
//      *
//      * @param recalcPerRow legacy only ran runBulkSqls per hour when the master was new
//      * @return the last production row saved (legacy returned the mutated bean = last hour)
//      */
//     private BAL_PcsEntryProductiondata createShiftNoPlanRows(Ctx c, String masterId, boolean recalcPerRow) {
//         if (c.bean().getSftDuration() == null)
//             throw new IllegalArgumentException("Shift duration (sftDuration) is required for shift no-plan");

//         int fullMinutes = c.bean().getSftDuration();
//         int hours = (int) Math.ceil(fullMinutes / 60.0);
//         BAL_PcsEntryProductiondata last = c.prod();

//         for (int i = 1; i <= hours; i++) {
//             int calendar = 60;
//             if (i == hours) {
//                 int remainder = fullMinutes % 60;
//                 if (remainder != 0)
//                     calendar = remainder;
//             }
//             BigDecimal cal = BigDecimal.valueOf(calendar);

//             BAL_PcsEntryDtl row = new BAL_PcsEntryDtl();
//             BeanUtils.copyProperties(c.detail(), row);
//             row.setCalendartime(cal);
//             row.setUnaccountedtime(BigDecimal.ZERO);
//             row.setProductiontime(BigDecimal.ZERO);
//             row.setNoplaninmins(cal);
//             row.setProductionavltime(cal);
//             row.setMchavailabletime(cal);
//             row.setMachineid(String.valueOf(i));
//             insertDetail(row, masterId);

//             BAL_PcsEntryProductiondata prodRow = new BAL_PcsEntryProductiondata();
//             BeanUtils.copyProperties(c.prod(), prodRow);
//             prodRow.setKeyid(null);
//             prodRow.setHour(i);
//             prodRow.setShiftnoplan("Y");
//             persistProduction(prodRow, row.getPldetailsid(), masterId, c.now());

//             if (recalcPerRow) {
//                 recalculate(row.getPldetailsid(), row.getCalendartime(), c.master().getFlid());
//             }
//             last = prodRow;
//         }
//         return last;
//     }

//     /** Detail row already exists -> update its production columns and the production data row. */
//     private void updateExistingDetailAndProduction(Ctx c, String dtlId, String masterId) {
//         BAL_PcsEntryDtl req = c.detail();
//         BalPcsEntrySaveDto.EntryBean bean = c.bean();
//         BigDecimal avail = nz(bean.getAvailtimehourly());

//         // Load + modify so the loss01..loss122 columns already on the row are NOT wiped.
//         BAL_PcsEntryDtl existing = detailRepository.findById(dtlId)
//                 .orElseThrow(() -> new IllegalStateException("Detail " + dtlId + " not found"));

//         existing.setUnaccountedtime(nz(bean.getUnreptimehourly()));
//         existing.setProductiontime(nz(bean.getProdtimehourly()));
//         existing.setProductionavltime(avail);
//         existing.setMchavailabletime(avail);
//         existing.setCalendartime(avail);

//         // Production figures from the request (adjust to match BAL_PcsTlDtlSql.getUpdatePcsProductionSql)
//         if (req.getProducedqty() != null) existing.setProducedqty(req.getProducedqty());
//         if (req.getRejectedqty() != null) existing.setRejectedqty(req.getRejectedqty());
//         if (req.getReworkqty() != null) existing.setReworkqty(req.getReworkqty());
//         if (req.getActualcycletime() != null) existing.setActualcycletime(req.getActualcycletime());
//         existing.setModifiedon(c.now());
//         detailRepository.save(existing);

//         // keep request detail in sync for the post-save recalculation
//         req.setPldetailsid(dtlId);
//         req.setCalendartime(avail);

//         BAL_PcsEntryProductiondata prod = c.prod();
// if (!isValidKey(prod.getKeyid())) {
//     productionRepository.findByDetailIdNative(dtlId).ifPresent(old -> {
//         prod.setKeyid(old.getKeyid());
//         prod.setHour(old.getHour());
//     });
// }
// persistProduction(prod, dtlId, masterId, c.now());

//     }

//     // =====================================================================
//     // Persistence helpers
//     // =====================================================================

//     private String insertMaster(BAL_PcsEntryMst master) {
//         master.setKeyid(generateSequence(MASTER_SEQ_IDENTIFIER, MASTER_KEY_LENGTH, MASTER_PREFIX,
//                 MASTER_DATE_FORMAT, MASTER_FORMAT_RESET));
//         masterRepository.save(master);
//         return master.getKeyid();
//     }

//     private void insertDetail(BAL_PcsEntryDtl detail, String masterId) {
//         detail.setPlmasterid(masterId);
//         detail.setPldetailsid(generateSequence(DETAIL_SEQ_IDENTIFIER, DETAIL_KEY_LENGTH, DETAIL_PREFIX,
//                 DETAIL_DATE_FORMAT, DETAIL_FORMAT_RESET));
//         detailRepository.save(detail);
//     }

//     /** Insert (key blank) or update (key present) the production data row. */
//     private void persistProduction(BAL_PcsEntryProductiondata prod, String detailId, String masterId,
//                                    LocalDateTime now) {
//         if (!isValidKey(prod.getKeyid())) {
//             prod.setKeyid(generateSequence(PRODUCTION_SEQ_IDENTIFIER, PRODUCTION_KEY_LENGTH, PRODUCTION_PREFIX,
//                     PRODUCTION_DATE_FORMAT, PRODUCTION_FORMAT_RESET));
//         } else {
//             // update: keep original created* values
//             productionRepository.findById(prod.getKeyid()).ifPresent(old -> {
//                 prod.setCreatedon(old.getCreatedon());
//                 prod.setCreatedby(old.getCreatedby());
//             });
//         }
//         prod.setTempfield1(detailId);
//         prod.setTempfield2(masterId);
//         prod.setModifiedon(now);
//         productionRepository.save(prod);
//     }

//     private String findExistingMasterId(BAL_PcsEntryMst master) {
//         LocalDateTime start = master.getEntrydate().toLocalDate().atStartOfDay();
//         List<String> ids = masterRepository.findKeyIds(start, start.plusDays(1),
//                 master.getShiftid(), master.getSectionid(), master.getCellid());
//         return ids.isEmpty() ? null : ids.get(0);
//     }

//     private String findDetailIdByMachineAndMaster(String machineId, String masterId) {
//         return detailRepository.findFirstByMachineidAndPlmasteridOrderByPldetailsidAsc(machineId, masterId)
//                 .map(BAL_PcsEntryDtl::getPldetailsid).orElse(null);
//     }

//     /**
//      * Equivalent of legacy runBulkSqls(...) AFTER the SQL list ran.
//      * Rows are flushed first so anything reading the DB sees them.
//      * TODO: call the same recalculation your runBulkSqls performs
//      *       (stored function / loss + OEE recompute) using detailId, calendartime, flid.
//      */

// //     private void recalculate(String detailId, BigDecimal calendartime, String flid) {
// //     detailRepository.flush();
// //     productionRepository.flush();

// //     String cycle = calendartime == null ? "0" : calendartime.toPlainString();

// //     Map<Integer, Object> p = new LinkedHashMap<>();
// //     p.put(1, "BAL_PCS_TL_DTL");   // table the entity writes to
// //     p.put(2, detailId);
// //     p.put(3, cycle);
// //     p.put(4, "Y");
// //     p.put(5, "N");
// //     p.put(6, flid);

// //     try {
// //         Map<String, Object> res = fnRepository.callMultipeParamFunction("bal_pcs_fn_updatepldtl", p);
// //         logger.info("bal_pcs_fn_updatepldtl result={}", res);   // check the real shape here
// //         Object first = (res == null || res.isEmpty()) ? null : res.values().iterator().next();
// //         int rc = first == null ? -1 : Integer.parseInt(String.valueOf(first).trim());

// //         if (rc == -1) throw new IllegalStateException("Data not saved");
// //        if (rc == -2) {
// //     String tolStr = detailRepository.findConfigValue("MAXROP");
// //     throw new IllegalStateException("ROP is greater than " + (Integer.parseInt(tolStr.trim()) + 100) + "%");
// // }
// //        if (rc == -3) {
// //     String unrep = detailRepository.findConfigValue("MAXUNREPORTED").trim();
// //     if (Double.parseDouble(cycle) > Integer.parseInt(unrep)) unrep = cycle;
// //     throw new IllegalStateException("Unreported time exceeds " + unrep + " Mins");
// // }
// //     } catch (IllegalStateException e) {
// //         throw e;                                   // our own messages pass through untouched
// //     } catch (Exception e) {
// //         throw new IllegalStateException("bal_pcs_fn_updatepldtl failed: " + e.getMessage(), e);
// //     }
// // }

// private void recalculate(String detailId, BigDecimal calendartime, String flid) {
//     detailRepository.flush();
//     productionRepository.flush();

//     String cycle = calendartime == null ? "0" : calendartime.toPlainString();
//     Integer rc = detailRepository.updatePlDtl("BAL_PCS_TL_DTL", detailId, cycle, flid);

//     if (rc == null || rc < 0)
//         logger.warn("bal_pcs_fn_updatepldtl returned {} for detail={}", rc, detailId);
//     else
//         logger.info("bal_pcs_fn_updatepldtl detail={} rc={}", detailId, rc);
// }
   

//     // =====================================================================
//     // Defaults / validation
//     // =====================================================================
//     private void applyDefaults(BAL_PcsEntryMst master, BAL_PcsEntryDtl detail,
//                                BAL_PcsEntryProductiondata prod, LocalDateTime now) {
//         // values used for lookup
//         if (master.getEntrydate() == null)
//             master.setEntrydate(prod.getDate() != null ? prod.getDate() : now);
//         if (master.getDate() == null)
//             master.setDate(master.getEntrydate());
//         if (isBlank(master.getShiftid()))
//             master.setShiftid(prod.getShiftid());
//         if (isBlank(master.getFlid()))
//             master.setFlid(prod.getFlid());
//         if (isBlank(master.getCellid()))
//             master.setCellid(!isBlank(detail.getCellid()) ? detail.getCellid() : prod.getCellid());
//         if (isBlank(detail.getCellid()))
//             detail.setCellid(master.getCellid());
//         if (isBlank(master.getMachineid()))
//             master.setMachineid(detail.getMachineid());
//         if (isBlank(detail.getMachineid()))
//             detail.setMachineid(master.getMachineid());

//         if (isBlank(master.getShiftid()))
//             throw new IllegalArgumentException("Shift id is required");
//         if (isBlank(master.getSectionid()))
//             throw new IllegalArgumentException("Section id is required");
//         if (isBlank(master.getCellid()))
//             throw new IllegalArgumentException("Cell id is required");

//         // production-data flags (legacy NPE'd when null)
//         if (isBlank(prod.getShiftnoplan()))
//             prod.setShiftnoplan("N");
//         if (isBlank(prod.getHourlynoplan()))
//             prod.setHourlynoplan("N");
//         if (prod.getNoplanduration() == null)
//             prod.setNoplanduration(0);

//         // master: NOT NULL columns. Adjust the fallback values to what your DB expects.
//         String createdBy = !isBlank(master.getCreatedby()) ? master.getCreatedby()
//                 : (!isBlank(master.getEntryby()) ? master.getEntryby() : prod.getCreatedby());
//         master.setCreatedby(orDash(createdBy));
//         master.setEntryby(orDash(master.getEntryby() != null ? master.getEntryby() : createdBy));
//         master.setUpdatedby(orDash(master.getUpdatedby() != null ? master.getUpdatedby() : createdBy));
//         master.setFactoryid(orDash(master.getFactoryid()));
//         master.setApprovedby(orDash(master.getApprovedby()));
//         master.setShiftincharge(orDash(master.getShiftincharge()));
//         master.setSubgroupid(orDash(master.getSubgroupid()));
//         master.setTimeorqty(orDash(master.getTimeorqty()));
//         master.setCompletedflag(orDash(master.getCompletedflag()));
//         master.setTempfield2(orDash(master.getTempfield2()));
//         master.setElementid(orDash(master.getElementid()));
//         master.setMachineid(orDash(master.getMachineid()));
//         if (master.getUpdateddate() == null) master.setUpdateddate(now);
//         if (master.getApprovedate() == null) master.setApprovedate(now);
//         if (master.getPrlm_apporvedflag() == null) master.setPrlm_apporvedflag('N');
//         if (master.getLogtype() == null) master.setLogtype('-');
//         if (master.getIsnoplan() == null)
//             master.setIsnoplan(("Y".equals(prod.getShiftnoplan()) || "Y".equals(prod.getHourlynoplan())) ? 'Y' : 'N');
//         if (master.getActive() == null) master.setActive('Y');
//         if (master.getCreatedon() == null) master.setCreatedon(now);
//         if (master.getModifiedon() == null) master.setModifiedon(now);

//         // detail: NOT NULL columns
//         if (detail.getOperators() == null) detail.setOperators(BigDecimal.ZERO);
//         if (detail.getCompletedflag() == null) detail.setCompletedflag('N');
//         detail.setRemarks(orDash(detail.getRemarks()));
//         detail.setModelchangepart(orDash(detail.getModelchangepart()));
//         if (detail.getActive() == null) detail.setActive('Y');
//         if (isBlank(detail.getCreatedby())) detail.setCreatedby(master.getCreatedby());
//         if (detail.getCreatedon() == null) detail.setCreatedon(now);
//         if (detail.getModifiedon() == null) detail.setModifiedon(now);

//         // production data: NOT NULL columns
//         if (isBlank(prod.getFlid())) prod.setFlid(master.getFlid());
//         if (isBlank(prod.getCellid())) prod.setCellid(master.getCellid());
//         if (prod.getDate() == null) prod.setDate(master.getEntrydate());
//         if (isBlank(prod.getShiftid())) prod.setShiftid(master.getShiftid());
//         if (prod.getHour() == null) prod.setHour(1);
//         prod.setProductid(orDash(prod.getProductid()));
//         prod.setProductmodel(orDash(prod.getProductmodel()));
//         prod.setManpower(orDash(prod.getManpower()));
//         if (prod.getCycletime() == null) prod.setCycletime(0);
//         if (prod.getProducedqty() == null) prod.setProducedqty(0);
//         if (prod.getRejectedqty() == null) prod.setRejectedqty(0);
//         if (prod.getReworkqty() == null) prod.setReworkqty(0);
//         prod.setRemarks(orDash(prod.getRemarks()));
//         prod.setTempfield1(orDash(prod.getTempfield1()));
//         prod.setTempfield2(orDash(prod.getTempfield2()));
//         prod.setTempfield3(orDash(prod.getTempfield3()));
//         if (prod.getActive() == null) prod.setActive('Y');
//         if (isBlank(prod.getCreatedby())) prod.setCreatedby(master.getCreatedby());
//         if (prod.getCreatedon() == null) prod.setCreatedon(now);
//         if (prod.getModifiedon() == null) prod.setModifiedon(now);
//     }

//     // =====================================================================
//     // Small utilities
//     // =====================================================================
//     private String generateSequence(String seqId, int keyLen, String prefix, String dateFmt, String formatReset) {
//         try {
//             return dbActionTemplate.getSequenceNumber(seqId, keyLen, prefix.trim(), dateFmt, formatReset).trim();
//         } catch (Exception e) {
//             throw new IllegalStateException("Failed to generate sequence " + seqId + ": " + e.getMessage(), e);
//         }
//     }

//     private boolean isValidKey(String key) {
//         return !isBlank(key)
//                 && !"{}".equals(key)
//                 && !"<**>".equals(key)
//                 && !"-".equals(key)
//                 && !"null".equalsIgnoreCase(key)
//                 && !"undefined".equalsIgnoreCase(key);
//     }

//     private boolean isBlank(String val) {
//         return val == null || val.trim().isEmpty();
//     }

//     private String orDash(String val) {
//         return isBlank(val) ? "-" : val;
//     }

//     private BigDecimal nz(BigDecimal val) {
//         return val == null ? BigDecimal.ZERO : val;
//     }
// }


//28 oct 2026

package com.akranta.perfex_sb.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.BalPcsEntrySaveDto;
import com.akranta.perfex_sb.model.BAL_PcsEntryDtl;
import com.akranta.perfex_sb.model.BAL_PcsEntryMst;
import com.akranta.perfex_sb.model.BAL_PcsEntryProductiondata;
import com.akranta.perfex_sb.repository.BAL_PcsEntryDtlRepository;
import com.akranta.perfex_sb.repository.BAL_PcsEntryMstRepository;
import com.akranta.perfex_sb.repository.BAL_PcsEntryProductiondataRepository;
import com.akranta.perfex_sb.service.BAL_PcsEntryService;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.repository.DbFunctionTempleteRepository;

import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * Port of legacy BAL_PcsTlProductiondataDaoImpl.createNew(...).
 *
 * Differences from the legacy DAO that are deliberate:
 *  - the whole save is one @Transactional unit; return codes -1/-2/-3 of the recalculation function
 *    throw IllegalStateException so everything rolls back (legacy rolled back, then swallowed the error)
 *  - a request that carries pldetailsid is resolved by that id first (API update path)
 *  - master created* columns are kept on update
 */
@Service
public class BalPcsEntrySaveServiceImpl implements BAL_PcsEntryService {

    private static final Logger logger = LoggerFactory.getLogger(BalPcsEntrySaveServiceImpl.class);

    private static final String MASTER_SEQ_IDENTIFIER = "PCS_TL_MST";
    private static final int MASTER_KEY_LENGTH = 15;
    private static final String MASTER_PREFIX = "PRLM";      // legacy "\tPRLM"
    private static final String MASTER_DATE_FORMAT = "YY";
    private static final String MASTER_FORMAT_RESET = "Y";

    private static final String DETAIL_SEQ_IDENTIFIER = "PCS_TL_DTL";
    private static final int DETAIL_KEY_LENGTH = 15;
    private static final String DETAIL_PREFIX = "PDE";
    private static final String DETAIL_DATE_FORMAT = "YY";
    private static final String DETAIL_FORMAT_RESET = "Y";

    private static final String PRODUCTION_SEQ_IDENTIFIER = "BAL_PCS_TL_PRODUCTIONDATA";
    private static final int PRODUCTION_KEY_LENGTH = 15;
    private static final String PRODUCTION_PREFIX = "PRL";
    private static final String PRODUCTION_PREFIX_NEW_SHIFT_NOPLAN = "PPRD"; // legacy: new master + shift no-plan
    private static final String PRODUCTION_DATE_FORMAT = "YY";
    private static final String PRODUCTION_FORMAT_RESET = "Y";

    private static final String BASE_DETAIL_TABLE = "PCS_TL_DTL";

    /** Columns that exist on the entity but were NOT part of legacy BAL_PcsTlDtlSql.pcs_DbFields. */
    private static final Set<String> DTL_INSERT_SKIP = Set.of(
            "actualruntime_old", "netutilisationtime_old", "plantavltime_old", "ae", "au", "oe",
            "pfprocessfailureloss", "ancillaryutilityloss", "mgmloadingtime", "mgmproductiontime",
            "mgmoe", "mgmroa", "mgmrop", "mgmroq", "mgmoee", "loss113");

    private static final List<Field> DTL_FIELDS = buildDtlFields();

    private static List<Field> buildDtlFields() {
        List<Field> list = new ArrayList<>();
        for (Field f : BAL_PcsEntryDtl.class.getDeclaredFields()) {
            Column col = f.getAnnotation(Column.class);
            if (col == null || DTL_INSERT_SKIP.contains(col.name().toLowerCase()))
                continue;
            f.setAccessible(true);
            list.add(f);
        }
        return list;
    }

    @PersistenceContext
    private EntityManager em;

    private final BAL_PcsEntryMstRepository masterRepository;
    private final BAL_PcsEntryDtlRepository detailRepository;
    private final BAL_PcsEntryProductiondataRepository productionRepository;
    private final DbActionTemplate dbActionTemplate;
    private final DbFunctionTempleteRepository fnRepository;

    public BalPcsEntrySaveServiceImpl(BAL_PcsEntryMstRepository masterRepository,
                                      BAL_PcsEntryDtlRepository detailRepository,
                                      BAL_PcsEntryProductiondataRepository productionRepository,
                                      DbActionTemplate dbActionTemplate,
                                      DbFunctionTempleteRepository fnRepository) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.productionRepository = productionRepository;
        this.dbActionTemplate = dbActionTemplate;
        this.fnRepository = fnRepository;
    }

    /** Everything one save needs. {@code table} = legacy detailTableName. */
    private record Ctx(BAL_PcsEntryMst master,
                       BAL_PcsEntryDtl detail,
                       BAL_PcsEntryProductiondata prod,
                       BalPcsEntrySaveDto.EntryBean bean,
                       LocalDateTime now,
                       boolean hourlyNoPlan,
                       boolean shiftNoPlan,
                       int noPlanDuration,
                       String table) {
    }

    @Override
    @Transactional
    public BAL_PcsEntryProductiondata createNew(BalPcsEntrySaveDto request) {
        if (request == null)
            throw new IllegalArgumentException("Request is null");
        if (request.getMaster() == null)
            throw new IllegalArgumentException("Master payload is required");
        if (request.getDetail() == null)
            throw new IllegalArgumentException("Detail payload is required");
        if (request.getProductionData() == null)
            throw new IllegalArgumentException("Production data payload is required");
        if (request.getEntryBean() == null)
            request.setEntryBean(new BalPcsEntrySaveDto.EntryBean());

        LocalDateTime now = LocalDateTime.now();
        BAL_PcsEntryMst master = request.getMaster();
        BAL_PcsEntryDtl detail = request.getDetail();
        BAL_PcsEntryProductiondata prod = request.getProductionData();
        BalPcsEntrySaveDto.EntryBean bean = request.getEntryBean();

        applyDefaults(master, detail, prod, now);

        boolean hourlyNoPlan = "Y".equals(prod.getHourlynoplan());
        boolean shiftNoPlan = "Y".equals(prod.getShiftnoplan());
        int noPlanDuration = prod.getNoplanduration() == null ? 0 : prod.getNoplanduration();

        // legacy: String detailTableName = getDetailTableName(cellId, entryDate)
       // String table = getDetailTableName(master.getCellid(), master.getEntrydate().toLocalDate());

       String table = getDetailTableName(master.getCellid(), master.getEntrydate());
logger.info("SAVE table={} masterCell={} detailCell={} prodCell={} entryDate={}",
        table, master.getCellid(), detail.getCellid(), prod.getCellid(), master.getEntrydate());

        Ctx ctx = new Ctx(master, detail, prod, bean, now, hourlyNoPlan, shiftNoPlan, noPlanDuration, table);

        String existingMasterId = findExistingMasterId(master);

        BAL_PcsEntryProductiondata result;
        if (existingMasterId != null) {
            result = handleExistingMaster(ctx, existingMasterId);
        } else {
            result = handleNewMaster(ctx);
        }

        // legacy: if (!shiftNoplan) runBulkSqls(...)
        if (!shiftNoPlan && isValidKey(detail.getPldetailsid())) {
            recalculate(table, detail.getPldetailsid(), detail.getCalendartime(), master.getFlid());
        }

        logger.info("BAL PCS createNew [{}]: table={} master={} detail={} prod={} hourlyNoPlan={} shiftNoPlan={}",
                existingMasterId != null ? "EXISTING_MASTER" : "NEW_MASTER", table,
                master.getKeyid(), detail.getPldetailsid(), result.getKeyid(), hourlyNoPlan, shiftNoPlan);
        return result;
    }

    // =====================================================================
    // Master already exists for (date, shift, section, cell)
    // =====================================================================
    private BAL_PcsEntryProductiondata handleExistingMaster(Ctx c, String masterId) {
        BAL_PcsEntryMst master = c.master();
        BAL_PcsEntryDtl detail = c.detail();
        BAL_PcsEntryProductiondata prod = c.prod();
        String t = c.table();

        // legacy: BAL_PcsTlMstSql.getUpdateSql(...) = full update of the master from the request
        BAL_PcsEntryMst existing = masterRepository.findById(masterId)
                .orElseThrow(() -> new IllegalStateException("Master " + masterId + " not found"));
        master.setKeyid(masterId);
        master.setCreatedby(existing.getCreatedby());
        master.setCreatedon(existing.getCreatedon());
        masterRepository.save(master);

        detail.setPlmasterid(masterId);
        String machine = str(detail.getMachineid());

        // --- Resolve existing detail row (legacy lookups, in legacy order) ---
        String dtlId = null;

        // API addition: explicit detail id from the request (update path)
        if (isValidKey(detail.getPldetailsid())) {
            dtlId = firstString("SELECT PLDETAILSID FROM " + t + " WHERE PLDETAILSID = ?1 AND PLMASTERID = ?2",
                    detail.getPldetailsid(), masterId);
        }

        // legacy 1: MACHINEID + PRODUCTID + PLMASTERID + ACTUALCYCLETIME
        if (!isValidKey(dtlId) && detail.getActualcycletime() != null) {
            dtlId = firstString("SELECT PLDETAILSID FROM " + t
                    + " WHERE MACHINEID = ?1 AND PRODUCTID = ?2 AND PLMASTERID = ?3 AND ACTUALCYCLETIME = ?4"
                    + " ORDER BY PLDETAILSID LIMIT 1",
                    machine, str(detail.getProductid()), masterId, detail.getActualcycletime());
        }

        // legacy 2: valid product and noplanduration != 0
        if (!isValidKey(dtlId) && isValidKey(prod.getProductid()) && c.noPlanDuration() != 0) {
            dtlId = findDetailIdByMachineAndMaster(t, machine, masterId);
        }

        // legacy 3: hourly no-plan
        if (!isValidKey(dtlId) && c.hourlyNoPlan()) {
            dtlId = findDetailIdByMachineAndMaster(t, machine, masterId);
        }

        // legacy 4: loss entry (not hourly no-plan, duration 0): detail exists without product
        if (!isValidKey(dtlId) && !c.hourlyNoPlan() && c.noPlanDuration() == 0) {
            String dtlProductId = firstString("SELECT PRODUCTID FROM " + t
                    + " WHERE MACHINEID = ?1 AND PLMASTERID = ?2 ORDER BY PLDETAILSID LIMIT 1", machine, masterId);
            if (!isValidKey(dtlProductId)) {
                dtlId = findDetailIdByMachineAndMaster(t, machine, masterId);
                if (isValidKey(dtlId)) {
                    // legacy inserted a NEW production row (new key) for this detail
                    prod.setKeyid(generateSequence(PRODUCTION_SEQ_IDENTIFIER, PRODUCTION_KEY_LENGTH,
                            PRODUCTION_PREFIX, PRODUCTION_DATE_FORMAT, PRODUCTION_FORMAT_RESET));
                    prod.setHourlynoplan("N");
                }
            }
        }

        // --- Persist (legacy if / else-if chain) ---
        if (!isValidKey(dtlId) && c.shiftNoPlan()) {
            return createShiftNoPlanRows(c, masterId, PRODUCTION_PREFIX, false);
        }
        if (!isValidKey(dtlId) && "Y".equals(prod.getHourlynoplan())) {
            createHourlyNoPlanRow(c, masterId);
            return prod;
        }
        if (!isValidKey(dtlId) && "N".equals(prod.getHourlynoplan()) && "N".equals(prod.getShiftnoplan())) {
            createRegularRow(c, masterId, true);
            return prod;
        }
        updateExistingDetailAndProduction(c, dtlId, masterId);
        return prod;
    }

    // =====================================================================
    // No master yet
    // =====================================================================
    private BAL_PcsEntryProductiondata handleNewMaster(Ctx c) {
        BAL_PcsEntryMst master = c.master();

        if (c.hourlyNoPlan()) {
            String masterId = insertMaster(master);
            createHourlyNoPlanRow(c, masterId);
            return c.prod();
        }
        if (c.shiftNoPlan()) {
            String masterId = insertMaster(master);
            return createShiftNoPlanRows(c, masterId, PRODUCTION_PREFIX_NEW_SHIFT_NOPLAN, true);
        }
        if (c.bean().getAddrowtoGrid() == null) {
            String masterId = insertMaster(master);
            createRegularRow(c, masterId, false);
            return c.prod();
        }

        logger.warn("BAL PCS createNew: nothing saved (new master, no-plan flags N, addrowtoGrid={})",
                c.bean().getAddrowtoGrid());
        return c.prod();
    }

    // =====================================================================
    // Row builders
    // =====================================================================

    /** Hourly no-plan: loading = available - noplan duration. */
    private void createHourlyNoPlanRow(Ctx c, String masterId) {
        BAL_PcsEntryDtl detail = c.detail();
        BalPcsEntrySaveDto.EntryBean bean = c.bean();

        BigDecimal avail = nz(bean.getAvailtimehourly());
        BigDecimal loading = avail.subtract(BigDecimal.valueOf(c.noPlanDuration()));

        detail.setLoadingtime(loading);
        detail.setCalendartime(avail);
        detail.setMchavailabletime(avail);
        detail.setUnaccountedtime(nz(bean.getUnreptimehourly()));
        detail.setProductionavltime(loading);
        detail.setNoplaninmins(BigDecimal.valueOf(c.noPlanDuration()));

        insertDetail(c.table(), detail, masterId);

        BAL_PcsEntryProductiondata prod = c.prod();
        prod.setKeyid(null);
        prod.setHourlynoplan("Y");
        persistProduction(prod, detail.getPldetailsid(), masterId, c.now(), PRODUCTION_PREFIX);
    }

    /**
     * Normal entry (hourly = N, shift = N).
     * @param fullHourlyTimes true = existing-master legacy branch, false = new-master branch (calendartime only)
     */
    private void createRegularRow(Ctx c, String masterId, boolean fullHourlyTimes) {
        BAL_PcsEntryDtl detail = c.detail();
        BalPcsEntrySaveDto.EntryBean bean = c.bean();
        BigDecimal avail = nz(bean.getAvailtimehourly());

        if (fullHourlyTimes) {
            detail.setUnaccountedtime(nz(bean.getUnreptimehourly()));
            detail.setProductiontime(nz(bean.getProdtimehourly()));
            detail.setProductionavltime(avail);
            detail.setMchavailabletime(avail);
        }
        detail.setCalendartime(avail);

        insertDetail(c.table(), detail, masterId);

        BAL_PcsEntryProductiondata prod = c.prod();
        prod.setKeyid(null);
        persistProduction(prod, detail.getPldetailsid(), masterId, c.now(), PRODUCTION_PREFIX);
    }

    /**
     * Shift no-plan: one detail + one production row per hour.
     * @param prodPrefix   PRL (existing master) / PPRD (new master), as in legacy
     * @param recalcPerRow legacy ran runBulkSqls per hour only when the master was new
     */
    private BAL_PcsEntryProductiondata createShiftNoPlanRows(Ctx c, String masterId, String prodPrefix,
                                                              boolean recalcPerRow) {
        if (c.bean().getSftDuration() == null)
            throw new IllegalArgumentException("Shift duration (sftDuration) is required for shift no-plan");

        int fullMinutes = c.bean().getSftDuration();
        int hours = (int) Math.ceil(fullMinutes / 60.0);
        BAL_PcsEntryProductiondata last = c.prod();

        //elumalai(05Nov26)
        List<BAL_PcsEntryDtl> created = new ArrayList<>();

        for (int i = 1; i <= hours; i++) {
            int calendar = 60;
            if (i == hours) {
                int remainder = fullMinutes % 60;
                if (remainder != 0)
                    calendar = remainder;
            }
            BigDecimal cal = BigDecimal.valueOf(calendar);

            BAL_PcsEntryDtl row = new BAL_PcsEntryDtl();
            BeanUtils.copyProperties(c.detail(), row);
            row.setCalendartime(cal);
            row.setUnaccountedtime(BigDecimal.ZERO);
            row.setProductiontime(BigDecimal.ZERO);
            row.setNoplaninmins(cal);
            row.setProductionavltime(cal);
            row.setMchavailabletime(cal);
            row.setMachineid(String.valueOf(i));
            insertDetail(c.table(), row, masterId);

            BAL_PcsEntryProductiondata prodRow = new BAL_PcsEntryProductiondata();
            BeanUtils.copyProperties(c.prod(), prodRow);
            prodRow.setKeyid(null);
            prodRow.setHour(i);
            prodRow.setShiftnoplan("Y");
            persistProduction(prodRow, row.getPldetailsid(), masterId, c.now(), prodPrefix);
            
            
            created.add(row);
        }

        //elumalai(05Nov26)
        if (recalcPerRow) {
        em.flush();
        for (BAL_PcsEntryDtl r : created) {
            recalculate(c.table(), r.getPldetailsid(), r.getCalendartime(), c.master().getFlid());
        }
    }
        //      if (recalcPerRow) {
        //          recalculate(c.table(), row.getPldetailsid(), row.getCalendartime(), c.master().getFlid());
        //      }
        //     last = prodRow;
        // }
        return last;
    }

    /**
     * Detail exists -> legacy BAL_PcsTlDtlSql.getUpdatePcsProductionSql (only these columns:
     * CALENDARTIME, LOADINGTIME, REMARKS, CAVITYAVAILABLE, BACKLOGQTY, WNO, RAWMATERIALTYPE, WEIGHT,
     * TRIMMINGQTY, EXPANSIONQTY) + full update of the production data row.
     * Quantities / times are left to bal_pcs_fn_updatepldtl, as in legacy.
     */
    private void updateExistingDetailAndProduction(Ctx c, String dtlId, String masterId) {
        BAL_PcsEntryDtl d = c.detail();
        BalPcsEntrySaveDto.EntryBean bean = c.bean();
        BigDecimal avail = nz(bean.getAvailtimehourly());

        d.setUnaccountedtime(nz(bean.getUnreptimehourly()));
        d.setProductiontime(nz(bean.getProdtimehourly()));
        d.setProductionavltime(avail);
        d.setMchavailabletime(avail);
        d.setCalendartime(avail);
        d.setPldetailsid(dtlId);

        em.flush();
        Query q = em.createNativeQuery("UPDATE " + c.table() + " SET CALENDARTIME = ?1, LOADINGTIME = ?2, "
                + "REMARKS = ?3, CAVITYAVAILABLE = ?4, BACKLOGQTY = ?5, WNO = ?6, RAWMATERIALTYPE = ?7, "
                + "WEIGHT = ?8, TRIMMINGQTY = ?9, EXPANSIONQTY = ?10 WHERE PLDETAILSID = ?11");
        q.setParameter(1, nz(d.getCalendartime()));
        q.setParameter(2, nz(d.getLoadingtime()));
        q.setParameter(3, str(d.getRemarks()));
        q.setParameter(4, nz(d.getCavityavailable()));
        q.setParameter(5, nz(d.getBacklogqty()));
        q.setParameter(6, str(d.getWno()));
        q.setParameter(7, str(d.getRawmaterialtype()));
        q.setParameter(8, nz(d.getWeight()));
        q.setParameter(9, nz(d.getTrimmingqty()));
        q.setParameter(10, nz(d.getExpansionqty()));
        q.setParameter(11, dtlId);
        q.executeUpdate();

        BAL_PcsEntryProductiondata prod = c.prod();
        if (!isValidKey(prod.getKeyid())) {
            // client did not send the production key -> find the row of this detail
            productionRepository.findByDetailIdNative(dtlId).ifPresent(old -> {
                prod.setKeyid(old.getKeyid());
                prod.setHour(old.getHour());
            });
        }
        persistProduction(prod, dtlId, masterId, c.now(), PRODUCTION_PREFIX);
    }

    // =====================================================================
    // Persistence helpers
    // =====================================================================

    private String insertMaster(BAL_PcsEntryMst master) {
        master.setKeyid(generateSequence(MASTER_SEQ_IDENTIFIER, MASTER_KEY_LENGTH, MASTER_PREFIX,
                MASTER_DATE_FORMAT, MASTER_FORMAT_RESET));
        masterRepository.save(master);
        return master.getKeyid();
    }

    /** Insert into the (per-cell) detail table. Null numerics -> 0, null strings -> '' like legacy SqlUtils. */
    private void insertDetail(String table, BAL_PcsEntryDtl detail, String masterId) {
        detail.setPlmasterid(masterId);
        detail.setPldetailsid(generateSequence(DETAIL_SEQ_IDENTIFIER, DETAIL_KEY_LENGTH, DETAIL_PREFIX,
                DETAIL_DATE_FORMAT, DETAIL_FORMAT_RESET));

        em.flush(); // master row must be visible before the FK insert

        StringBuilder cols = new StringBuilder();
        StringBuilder vals = new StringBuilder();
        List<Object> params = new ArrayList<>();
        try {
            for (Field f : DTL_FIELDS) {
                Object v = f.get(detail);
                if (v == null) {
                    if (f.getType() == BigDecimal.class) v = BigDecimal.ZERO;
                    else if (f.getType() == String.class) v = "";
                    else continue; // null Character / date -> DB default
                }
                if (v instanceof Character) v = v.toString();
                if (cols.length() > 0) { cols.append(','); vals.append(','); }
                cols.append(f.getAnnotation(Column.class).name());
                params.add(v);
                vals.append('?').append(params.size());
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot read detail entity: " + e.getMessage(), e);
        }
        Query q = em.createNativeQuery("INSERT INTO " + table + " (" + cols + ") VALUES (" + vals + ")");
        for (int i = 0; i < params.size(); i++)
            q.setParameter(i + 1, params.get(i));
        q.executeUpdate();
    }

    /** Insert (key blank) or update (key present) the production data row. */
    private void persistProduction(BAL_PcsEntryProductiondata prod, String detailId, String masterId,
                                   LocalDateTime now, String keyPrefix) {
        if (!isValidKey(prod.getKeyid())) {
            prod.setKeyid(generateSequence(PRODUCTION_SEQ_IDENTIFIER, PRODUCTION_KEY_LENGTH, keyPrefix,
                    PRODUCTION_DATE_FORMAT, PRODUCTION_FORMAT_RESET));
        } else {
            productionRepository.findById(prod.getKeyid()).ifPresent(old -> {
                prod.setCreatedon(old.getCreatedon());
                prod.setCreatedby(old.getCreatedby());
            });
        }
        prod.setTempfield1(detailId);
        prod.setTempfield2(masterId);
        prod.setModifiedon(now);
        productionRepository.save(prod);
    }

    private String findExistingMasterId(BAL_PcsEntryMst master) {
        LocalDateTime start = master.getEntrydate().toLocalDate().atStartOfDay();
        List<String> ids = masterRepository.findKeyIds(start, start.plusDays(1),
                master.getShiftid(), master.getSectionid(), master.getCellid());
        return ids.isEmpty() ? null : ids.get(0);
    }

    private String findDetailIdByMachineAndMaster(String table, String machineId, String masterId) {
        return firstString("SELECT PLDETAILSID FROM " + table
                + " WHERE MACHINEID = ?1 AND PLMASTERID = ?2 ORDER BY PLDETAILSID LIMIT 1", machineId, masterId);
    }

    // =====================================================================
    // Per-cell detail table (legacy getDetailTableName)
    // =====================================================================
    private String getDetailTableName(String cellId, LocalDateTime  entryDate) {
        String code = firstString("SELECT CELL_CODE FROM GEN_TL_CELLMST WHERE CELL_KEYID = ?1", cellId);
        if (code == null)
            throw new IllegalArgumentException("Cell not found: " + cellId);

        String name = ("PCS_TL_" + code).trim()
                .replace("-", "_").replace(" ", "")
                .replace("&", "").replace(",", "").replace("/", "");
        if (!name.matches("[A-Za-z0-9_]+"))
            throw new IllegalArgumentException("Cell code '" + code + "' gives an invalid table name " + name);

        String found = firstString("SELECT tablename FROM pg_tables WHERE tablename = ?1", name.toLowerCase());
        if (found == null) {
            // WHERE 1=0: BAL_PCS_TL_DTL now holds data, copy structure only
            String pk = name.substring("PCS_TL_".length());
            ddl("CREATE TABLE " + name + " AS SELECT * FROM " + BASE_DETAIL_TABLE + " WHERE 1 = 0");
            ddl("ALTER TABLE " + name + " ADD CONSTRAINT PK_PCS_TL_DTL_" + pk + " PRIMARY KEY (PLDETAILSID)");
            ddl("ALTER TABLE " + name + " ADD CONSTRAINT FK_PLMASTERID_" + pk
                    + " FOREIGN KEY (PLMASTERID) REFERENCES BAL_PCS_TL_MST (PRLM_KEYID)");
        }

        // entry before TPMSTARTDATE -> base table
        String diff = firstString("SELECT (CAST(CAST(?1 AS timestamp) AS date) - CAST(CNFM_FROMDATE AS date)) "
        + "FROM ADM_TL_CONFIGURATIONMST WHERE CNFM_CODE = 'TPMSTARTDATE'", entryDate);
logger.info("TPMSTARTDATE diff={} -> {}", diff, (diff != null && Integer.parseInt(diff) < 0) ? BASE_DETAIL_TABLE : name);
if (diff != null && Integer.parseInt(diff) < 0)
    return BASE_DETAIL_TABLE;
return name;
        // String diff = firstString("SELECT (CAST(?1 AS date) - CAST(CNFM_FROMDATE AS date)) "
        //         + "FROM ADM_TL_CONFIGURATIONMST WHERE CNFM_CODE = 'TPMSTARTDATE'", entryDate);
        // if (diff != null && Integer.parseInt(diff) < 0)
        //     return BASE_DETAIL_TABLE;
        // return name;
    }

    private void ddl(String sql) {
        logger.info("DDL: {}", sql);
        em.createNativeQuery(sql).executeUpdate();
    }

    // =====================================================================
    // Recalculation (legacy runBulkSqls -> bal_pcs_fn_updatepldtl + return code handling)
    // =====================================================================
    private void recalculate(String table, String detailId, BigDecimal calendartime, String flid) {
        em.flush();

        String cycle = calendartime == null ? "0" : calendartime.stripTrailingZeros().toPlainString();
        Integer rc = detailRepository.updatePlDtl(table, detailId, cycle, flid);
        logger.info("bal_pcs_fn_updatepldtl table={} detail={} rc={}", table, detailId, rc);

        if (rc == null)
            return;
        if (rc == -1)
            throw new IllegalStateException("DATA NOT SAVED");
        if (rc == -2) {
            String tol = detailRepository.findConfigValue("MAXROP");
            int tolval = Integer.parseInt(tol.trim()) + 100;
            throw new IllegalStateException("ROP is greater than " + tolval + "%");
        }
        if (rc == -3) {
            String unrep = detailRepository.findConfigValue("MAXUNREPORTED").trim();
            if (isValidKey(unrep) && isValidKey(cycle) && Double.parseDouble(cycle) > Integer.parseInt(unrep))
                unrep = cycle;
            throw new IllegalStateException("Unreported time exceeds " + unrep + " Mins");
        }
    }

    // =====================================================================
    // Defaults / validation
    // =====================================================================
    private void applyDefaults(BAL_PcsEntryMst master, BAL_PcsEntryDtl detail,
                               BAL_PcsEntryProductiondata prod, LocalDateTime now) {
        if (master.getEntrydate() == null)
            master.setEntrydate(prod.getDate() != null ? prod.getDate() : now);
        if (master.getDate() == null)
            master.setDate(master.getEntrydate());
        if (isBlank(master.getShiftid()))
            master.setShiftid(prod.getShiftid());
        if (isBlank(master.getFlid()))
            master.setFlid(prod.getFlid());
        if (isBlank(master.getCellid()))
            master.setCellid(!isBlank(detail.getCellid()) ? detail.getCellid() : prod.getCellid());
        if (isBlank(detail.getCellid()))
            detail.setCellid(master.getCellid());
        if (isBlank(master.getMachineid()))
            master.setMachineid(detail.getMachineid());
        if (isBlank(detail.getMachineid()))
            detail.setMachineid(master.getMachineid());

        if (isBlank(master.getShiftid()))
            throw new IllegalArgumentException("Shift id is required");
        if (isBlank(master.getSectionid()))
            throw new IllegalArgumentException("Section id is required");
        if (isBlank(master.getCellid()))
            throw new IllegalArgumentException("Cell id is required");

        if (isBlank(prod.getShiftnoplan()))
            prod.setShiftnoplan("N");
        if (isBlank(prod.getHourlynoplan()))
            prod.setHourlynoplan("N");
        if (prod.getNoplanduration() == null)
            prod.setNoplanduration(0);

        String createdBy = !isBlank(master.getCreatedby()) ? master.getCreatedby()
                : (!isBlank(master.getEntryby()) ? master.getEntryby() : prod.getCreatedby());
        master.setCreatedby(orDash(createdBy));
        master.setEntryby(orDash(master.getEntryby() != null ? master.getEntryby() : createdBy));
        master.setUpdatedby(orDash(master.getUpdatedby() != null ? master.getUpdatedby() : createdBy));
        master.setFactoryid(orDash(master.getFactoryid()));
        master.setApprovedby(orDash(master.getApprovedby()));
        master.setShiftincharge(orDash(master.getShiftincharge()));
        master.setSubgroupid(orDash(master.getSubgroupid()));
        master.setTimeorqty(orDash(master.getTimeorqty()));
        master.setCompletedflag(orDash(master.getCompletedflag()));
        master.setTempfield2(orDash(master.getTempfield2()));
        master.setElementid(orDash(master.getElementid()));
        master.setMachineid(orDash(master.getMachineid()));
        if (master.getUpdateddate() == null) master.setUpdateddate(now);
        if (master.getApprovedate() == null) master.setApprovedate(now);
        if (master.getPrlm_apporvedflag() == null) master.setPrlm_apporvedflag('N');
        if (master.getLogtype() == null) master.setLogtype('-');
        if (master.getIsnoplan() == null)
            master.setIsnoplan(("Y".equals(prod.getShiftnoplan()) || "Y".equals(prod.getHourlynoplan())) ? 'Y' : 'N');
        if (master.getActive() == null) master.setActive('Y');
        if (master.getCreatedon() == null) master.setCreatedon(now);
        if (master.getModifiedon() == null) master.setModifiedon(now);

        if (detail.getOperators() == null) detail.setOperators(BigDecimal.ZERO);
        if (detail.getCompletedflag() == null) detail.setCompletedflag('N');
        detail.setRemarks(orDash(detail.getRemarks()));
        detail.setModelchangepart(orDash(detail.getModelchangepart()));
        if (detail.getActive() == null) detail.setActive('Y');
        if (isBlank(detail.getCreatedby())) detail.setCreatedby(master.getCreatedby());
        if (detail.getCreatedon() == null) detail.setCreatedon(now);
        if (detail.getModifiedon() == null) detail.setModifiedon(now);

        if (isBlank(prod.getFlid())) prod.setFlid(master.getFlid());
        if (isBlank(prod.getCellid())) prod.setCellid(master.getCellid());
        if (prod.getDate() == null) prod.setDate(master.getEntrydate());
        if (isBlank(prod.getShiftid())) prod.setShiftid(master.getShiftid());
        if (prod.getHour() == null) prod.setHour(1);
        prod.setProductid(orDash(prod.getProductid()));
        prod.setProductmodel(orDash(prod.getProductmodel()));
        prod.setManpower(orDash(prod.getManpower()));
        if (prod.getCycletime() == null) prod.setCycletime(0);
        if (prod.getProducedqty() == null) prod.setProducedqty(0);
        if (prod.getRejectedqty() == null) prod.setRejectedqty(0);
        if (prod.getReworkqty() == null) prod.setReworkqty(0);
        prod.setRemarks(orDash(prod.getRemarks()));
        prod.setTempfield1(orDash(prod.getTempfield1()));
        prod.setTempfield2(orDash(prod.getTempfield2()));
        prod.setTempfield3(orDash(prod.getTempfield3()));
        if (prod.getActive() == null) prod.setActive('Y');
        if (isBlank(prod.getCreatedby())) prod.setCreatedby(master.getCreatedby());
        if (prod.getCreatedon() == null) prod.setCreatedon(now);
        if (prod.getModifiedon() == null) prod.setModifiedon(now);
    }

    // =====================================================================
    // Small utilities
    // =====================================================================
    private String firstString(String sql, Object... params) {
        Query q = em.createNativeQuery(sql);
        for (int i = 0; i < params.length; i++)
            q.setParameter(i + 1, params[i]);
        List<?> rows = q.getResultList();
        if (rows.isEmpty() || rows.get(0) == null)
            return null;
        return rows.get(0).toString().trim();
    }

    private String generateSequence(String seqId, int keyLen, String prefix, String dateFmt, String formatReset) {
        try {
            return dbActionTemplate.getSequenceNumber(seqId, keyLen, prefix.trim(), dateFmt, formatReset).trim();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate sequence " + seqId + ": " + e.getMessage(), e);
        }
    }

    private boolean isValidKey(String key) {
        return !isBlank(key)
                && !"{}".equals(key)
                && !"<**>".equals(key)
                && !"-".equals(key)
                && !"null".equalsIgnoreCase(key)
                && !"undefined".equalsIgnoreCase(key);
    }

    private boolean isBlank(String val) {
    return val == null || val.trim().isEmpty() || "{}".equals(val.trim());
}
    // private boolean isBlank(String val) {
    //     return val == null || val.trim().isEmpty();
    // }

    private String orDash(String val) {
        return isBlank(val) ? "-" : val;
    }

    private String str(String val) {
        return val == null ? "" : val;
    }

    private BigDecimal nz(BigDecimal val) {
        return val == null ? BigDecimal.ZERO : val;
    }


    //to get date cell product
    @Override
@Transactional(readOnly = true)
public List<String[]> getLastShiftProduct(String factId, String cellId, String entryDate) {

    if (isBlank(factId) || isBlank(cellId) || isBlank(entryDate)) {
        throw new IllegalArgumentException("factId, cellId and entryDate are required");
    }

    List<String[]> result = new ArrayList<>();
    for (Object[] r : masterRepository.findLastShiftProduct(factId, cellId, entryDate)) {
        String[] out = new String[r.length];
        for (int i = 0; i < r.length; i++)
            out[i] = r[i] == null ? "" : r[i].toString().trim();
        result.add(out);
    }

    logger.info("getLastShiftProduct cell={} fact={} date={} rows={}", cellId, factId, entryDate, result.size());
    return result;
}

//to get the shift starttime and endtime
@Override
@Transactional(readOnly = true)
public List<String[]> getShiftStarEndTime(String shiftId) {

    if (isBlank(shiftId)) {
        throw new IllegalArgumentException("shiftId is required");
    }

    List<String[]> result = new ArrayList<>();
    for (Object[] r : masterRepository.findShiftStartEndTime(shiftId)) {
        String[] out = new String[r.length];
        for (int i = 0; i < r.length; i++)
            out[i] = r[i] == null ? "" : r[i].toString().trim();
        result.add(out);
    }

    logger.info("getShiftStarEndTime shift={} rows={}", shiftId, result.size());
    return result;
}   

//to get holiday
@Override
@Transactional(readOnly = true)
public List<String[]> getHolidayDates(String factId, String entryDate) {

    if (isBlank(factId) || isBlank(entryDate)) {
        throw new IllegalArgumentException("factId and entryDate are required");
    }

    List<String[]> result = new ArrayList<>();
    for (Object[] r : masterRepository.findHolidayDates(factId, entryDate)) {
        String[] out = new String[r.length];
        for (int i = 0; i < r.length; i++)
            out[i] = r[i] == null ? "" : r[i].toString().trim();
        result.add(out);
    }

    logger.info("getHolidayDates fact={} date={} rows={}", factId, entryDate, result.size());
    return result;
}

//getAllTimeforHour
@Override
@Transactional(readOnly = true)
public List<String[]> getAllTimeforHour(String cellId, String shiftId, String entryDate,
                                        String hourId, String shiftWise) {

    if (!isValidKey(cellId) || !isValidKey(shiftId) || !isValidKey(entryDate)) {
        throw new IllegalArgumentException("cellId, shiftId and entryDate are required");
    }

    List<String> masterIds = masterRepository.findMasterIdByDateShiftCell(entryDate, shiftId, cellId);
    if (masterIds.isEmpty() || !isValidKey(masterIds.get(0))) {
        return new ArrayList<>(); // legacy returned null
    }
    String plMasterId = masterIds.get(0).trim();

    List<Object[]> rows = "Y".equals(shiftWise)
            ? masterRepository.sumTimeShiftWise(plMasterId)
            : masterRepository.sumTimeForHour(plMasterId, hourId);

    List<String[]> result = new ArrayList<>();
    for (Object[] r : rows) {
        String[] out = new String[r.length];
        for (int i = 0; i < r.length; i++)
            out[i] = r[i] == null ? "" : r[i].toString().trim();
        result.add(out);
    }
    return result;
}

//to get cycle time
@Override
@Transactional(readOnly = true)
public List<String[]> getCycletime(String productId) {

    if (isBlank(productId)) {
        throw new IllegalArgumentException("productId is required");
    }

    List<String[]> result = new ArrayList<>();
    for (Object[] r : masterRepository.findCycleTime(productId)) {
        String[] out = new String[r.length];
        for (int i = 0; i < r.length; i++)
            out[i] = r[i] == null ? "" : r[i].toString().trim();
        result.add(out);
    }

    logger.info("getCycletime product={} rows={}", productId, result.size());
    return result;
}

//getShiftTotaltime
@Override
@Transactional(readOnly = true)
public int getShiftTotaltime(String startdate, String enddate, String shiftId) {

    if (isBlank(startdate) || isBlank(enddate) || isBlank(shiftId)) {
        throw new IllegalArgumentException("startdate, enddate and shiftId are required");
    }

    // break time from GEN_TL_SHIFTMST
    List<Object> rows = masterRepository.findShiftBreakTime(shiftId);
    if (rows.isEmpty() || rows.get(0) == null) {
        throw new IllegalArgumentException("Shift not found or break time missing: " + shiftId);
    }
    int breakTime = new BigDecimal(rows.get(0).toString().trim()).intValue(); // also handles 30.0

    // same format rules as legacy
    String start = startdate.trim();
    String end = enddate.trim();
    java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat(
            start.length() <= 5 ? "HH:mm"
            : start.length() <= 8 ? "HH:mm:ss"
            : "dd-MMM-yyyy HH:mm",
            java.util.Locale.ENGLISH);

    java.util.Date date1;
    java.util.Date date2;
    try {
        date1 = fmt.parse(start);
        date2 = fmt.parse(end);
    } catch (java.text.ParseException e) {
        throw new IllegalArgumentException("Invalid date/time format: " + e.getMessage(), e);
    }

    int difInMinutes = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60));
    difInMinutes = difInMinutes - breakTime;

    logger.info("getShiftTotaltime shift={} start={} end={} break={} total={}",
            shiftId, start, end, breakTime, difInMinutes);
    return difInMinutes;
}
}