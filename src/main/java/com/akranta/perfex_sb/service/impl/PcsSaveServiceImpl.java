package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.PcsSaveRequestDto;
import com.akranta.perfex_sb.model.PcsTlLosscapture;
import com.akranta.perfex_sb.repository.PcsSaveRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.PcsSaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
public class PcsSaveServiceImpl implements PcsSaveService {

    private static final Logger logger = LoggerFactory.getLogger(PcsSaveServiceImpl.class);

    private static final String MASTER_SEQ_IDENTIFIER = "PCS_TL_MST";
    private static final int MASTER_KEY_LENGTH = 15;
    private static final String MASTER_PREFIX = "PRL";
    private static final String MASTER_DATE_FORMAT = "YY";
    private static final String MASTER_FORMAT_RESET = "Y";

    private static final String DETAIL_SEQ_IDENTIFIER = "PCS_TL_DTL";
    private static final int DETAIL_KEY_LENGTH = 15;
    private static final String DETAIL_PREFIX = "PDE";
    private static final String DETAIL_DATE_FORMAT = "YY";
    private static final String DETAIL_FORMAT_RESET = "Y";

    private static final String LOSS_REASON_SEQ_IDENTIFIER = "PCS_TL_LOSSREASONLINK";
    private static final int LOSS_REASON_KEY_LENGTH = 15;
    private static final String LOSS_REASON_PREFIX = "QHB";
    private static final String LOSS_REASON_DATE_FORMAT = "YY";
    private static final String LOSS_REASON_FORMAT_RESET = "Y";

    private static final String LOSS_CAPTURE_SEQ_IDENTIFIER = "PCS_TL_LOSSCAPTURE";
    private static final int LOSS_CAPTURE_KEY_LENGTH = 15;
    private static final String LOSS_CAPTURE_PREFIX = "PLOS";
    private static final String LOSS_CAPTURE_DATE_FORMAT = "MMYY";
    private static final String LOSS_CAPTURE_FORMAT_RESET = "Y";

    private static final String LOSS_CAUSE_SEQ_IDENTIFIER = "PCS_TL_LOSSCAUSEMST";
    private static final int LOSS_CAUSE_KEY_LENGTH = 15;
    private static final String LOSS_CAUSE_PREFIX = "PLCS";
    private static final String LOSS_CAUSE_DATE_FORMAT = "YY";
    private static final String LOSS_CAUSE_FORMAT_RESET = "Y";

    private final PcsSaveRepository repository;
    private final DbActionTemplate dbActionTemplate;

    public PcsSaveServiceImpl(PcsSaveRepository repository, DbActionTemplate dbActionTemplate) {
        this.repository = repository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public PcsTlLosscapture save(PcsSaveRequestDto request) {
        if (request == null) throw new IllegalArgumentException("Request is null");
        if (request.getMaster() == null) throw new IllegalArgumentException("Master payload is required");
        if (request.getDetail() == null) throw new IllegalArgumentException("Detail payload is required");
        if (request.getLossCapture() == null) throw new IllegalArgumentException("Loss capture payload is required");
        if (request.getLossReasonLink() == null) throw new IllegalArgumentException("Loss reason link payload is required");

        LocalDateTime now = LocalDateTime.now();
        PcsSaveRequestDto.PcsMasterDto master = request.getMaster();
        PcsSaveRequestDto.PcsDetailDto detail = request.getDetail();
        PcsSaveRequestDto.PcsLossReasonLinkDto lossReason = request.getLossReasonLink();
        PcsSaveRequestDto.PcsLossCaptureDto lossCapture = request.getLossCapture();

        // --- Apply all defaults BEFORE resolveDetailTable ---
        if (master.getPrlmEntrydate() == null) {
            if (lossCapture.getPlosDate() != null) {
                master.setPrlmEntrydate(lossCapture.getPlosDate());
            } else if (lossCapture.getPlosFromtime() != null) {
                master.setPrlmEntrydate(lossCapture.getPlosFromtime());
            } else if (lossCapture.getPlosTotime() != null) {
                master.setPrlmEntrydate(lossCapture.getPlosTotime());
            }
        }
        if (master.getPrlmDate() == null && master.getPrlmEntrydate() != null) {
            master.setPrlmDate(master.getPrlmEntrydate());
        }
        if (lossCapture.getPlosDate() == null && master.getPrlmEntrydate() != null) {
            lossCapture.setPlosDate(master.getPrlmEntrydate());
        }
        if (isBlank(master.getPrlmShiftid())) master.setPrlmShiftid(lossCapture.getPlosShiftid());
        if (isBlank(master.getPrlmFlid())) master.setPrlmFlid(lossCapture.getPlosFlid());
        if (isBlank(master.getPrlmEntryby())) master.setPrlmEntryby(lossCapture.getPlosCreatedby());
        if (isBlank(master.getPrlmUpdatedby())) master.setPrlmUpdatedby(lossCapture.getPlosCreatedby());
        if (isBlank(master.getPrlmCreatedby())) master.setPrlmCreatedby(lossCapture.getPlosCreatedby());

        // Section from cell â€” must be populated before resolveDetailTable
        if (isBlank(master.getPrlmCellid()) && !isBlank(detail.getCellid())) {
            master.setPrlmCellid(detail.getCellid());
        }
        if (isBlank(detail.getCellid()) && !isBlank(master.getPrlmCellid())) {
            detail.setCellid(master.getPrlmCellid());
        }
        String cellIdForSection = !isBlank(detail.getCellid()) ? detail.getCellid() : master.getPrlmCellid();
        if (isBlank(master.getPrlmSectionid()) && !isBlank(cellIdForSection)) {
            master.setPrlmSectionid(repository.findSectionIdByCellId(cellIdForSection));
        }

        if (master.getPrlmCreatedon() == null) master.setPrlmCreatedon(now);
        if (master.getPrlmModifiedon() == null) master.setPrlmModifiedon(now);
        if (isBlank(master.getPrlmActive())) master.setPrlmActive("Y");

        if (detail.getCreatedon() == null) detail.setCreatedon(now);
        if (detail.getModifiedon() == null) detail.setModifiedon(now);
        if (isBlank(detail.getActive())) detail.setActive("Y");

        if (lossReason.getPlrkCreatedon() == null) lossReason.setPlrkCreatedon(now);
        if (lossReason.getPlrkModifiedon() == null) lossReason.setPlrkModifiedon(now);
        if (isBlank(lossReason.getPlrkActive())) lossReason.setPlrkActive("Y");

        if (lossCapture.getPlosCreatedon() == null) lossCapture.setPlosCreatedon(now);
        if (lossCapture.getPlosModifiedon() == null) lossCapture.setPlosModifiedon(now);
        if (isBlank(lossCapture.getPlosActive())) lossCapture.setPlosActive("Y");

        boolean isUpdateEntry = isValidKey(lossCapture.getPlosKeyid())
                && repository.lossCaptureExists(lossCapture.getPlosKeyid());
        String detailTable = null;
        boolean shouldInsertDetail = false;

        if (isUpdateEntry) {
            String linkedDetailId = repository.findDetailIdByLossCaptureId(lossCapture.getPlosKeyid());
            if (!isValidKey(linkedDetailId)) {
                logger.warn("Loss capture {} has no linked detail id; treating request as NEW.", lossCapture.getPlosKeyid());
                isUpdateEntry = false;
            } else {
                String linkedDetailTable = repository.findDetailTableByDetailId(linkedDetailId);
                if (isBlank(linkedDetailTable)) {
                    logger.warn("Linked detail {} for loss capture {} not found in any detail table; treating request as NEW.",
                            linkedDetailId, lossCapture.getPlosKeyid());
                    isUpdateEntry = false;
                } else {
                    detailTable = linkedDetailTable;
                    detail.setPldetailsid(linkedDetailId);
                }
            }
        }

        boolean isNewEntry = !isUpdateEntry;
        if (isNewEntry) {
            detailTable = resolveDetailTable(request);
            // Always allocate fresh keys for create flow even if stale keys come from frontend.
            detail.setPldetailsid(null);
            lossReason.setPlrkKeyid(null);
            lossCapture.setPlosKeyid(null);
        }

        // --- Master: one per (flid, shift, entrydate) ---
        // DB is the single source of truth: ignore any master key the client sends
        String existingMasterId = repository.findExistingMasterId(
                master.getPrlmEntrydate(),
                master.getPrlmShiftid(),
                master.getPrlmFlid()
        );

        if (existingMasterId != null) {
            master.setPrlmKeyid(existingMasterId);
            master.setPrlmModifiedon(now);
        } else {
            master.setPrlmKeyid(null);
            ensureKey(master, MASTER_SEQ_IDENTIFIER, MASTER_KEY_LENGTH, MASTER_PREFIX, MASTER_DATE_FORMAT, MASTER_FORMAT_RESET);
        }

        // --- Detail: one per master ---
        if (isNewEntry && isValidKey(master.getPrlmKeyid())) {
            String existingDetailId = repository.findExistingDetailIdByMasterId(detailTable, master.getPrlmKeyid());
            if (!isValidKey(existingDetailId)) {
                String existingDetailTable = repository.findDetailTableByMasterId(master.getPrlmKeyid());
                if (!isBlank(existingDetailTable)) {
                    detailTable = existingDetailTable;
                    existingDetailId = repository.findExistingDetailIdByMasterId(detailTable, master.getPrlmKeyid());
                }
            }
            if (isValidKey(existingDetailId)) {
                detail.setPldetailsid(existingDetailId);
            }
        }

        shouldInsertDetail = !isValidKey(detail.getPldetailsid());
        if (shouldInsertDetail) {
            ensureKey(detail, DETAIL_SEQ_IDENTIFIER, DETAIL_KEY_LENGTH, DETAIL_PREFIX, DETAIL_DATE_FORMAT, DETAIL_FORMAT_RESET);
        }

        // Generate keys for anything still missing
        ensureKey(lossReason, LOSS_REASON_SEQ_IDENTIFIER, LOSS_REASON_KEY_LENGTH, LOSS_REASON_PREFIX, LOSS_REASON_DATE_FORMAT, LOSS_REASON_FORMAT_RESET);
        ensureKey(lossCapture, LOSS_CAPTURE_SEQ_IDENTIFIER, LOSS_CAPTURE_KEY_LENGTH, LOSS_CAPTURE_PREFIX, LOSS_CAPTURE_DATE_FORMAT, LOSS_CAPTURE_FORMAT_RESET);

        // Sync master ID into detail and loss reason link
        detail.setPlmasterid(master.getPrlmKeyid());
        lossReason.setPlrkPldetailid(detail.getPldetailsid());
        lossCapture.setPlosPldetailsid(detail.getPldetailsid());

        logger.info("PCS save [{}]: master={} detail={} table={} detailPersist={} flid={} shift={} date={}",
                isNewEntry ? "NEW" : "UPDATE",
                master.getPrlmKeyid(), detail.getPldetailsid(), detailTable, shouldInsertDetail ? "INSERT" : "UPDATE",
                master.getPrlmFlid(), master.getPrlmShiftid(), master.getPrlmEntrydate());

        // --- Master upsert ---
        if (existingMasterId != null) {
            int cnt = repository.updateMaster(master);
            requireAffected(cnt, "pcs_tl_mst update", master.getPrlmKeyid());
        } else {
            int cnt = repository.insertMaster(master);
            requireAffected(cnt, "pcs_tl_mst insert", master.getPrlmKeyid());
        }

        // --- Detail persist ---
        if (shouldInsertDetail) {
            int cnt = repository.insertDetail(detailTable, detail);
            requireAffected(cnt, detailTable + " insert", detail.getPldetailsid());
        } else {
            int cnt = repository.updateDetail(detailTable, detail);
            requireAffected(cnt, detailTable + " update", detail.getPldetailsid());
        }

        // Update loss value columns on the detail row
        repository.updateLossColumns(detailTable, detail.getPldetailsid(), detail.getLosses());

        // --- Loss cause (ensure exists before inserting reason link) ---
        if (isBlank(lossReason.getPlrkCauseid()) && !isBlank(lossReason.getPlrkReasonid())) {
            String newCauseId = generateSequence(LOSS_CAUSE_SEQ_IDENTIFIER, LOSS_CAUSE_KEY_LENGTH, LOSS_CAUSE_PREFIX, LOSS_CAUSE_DATE_FORMAT, LOSS_CAUSE_FORMAT_RESET);
            lossReason.setPlrkCauseid(newCauseId);
            if (!repository.lossCauseExists(newCauseId)) {
                int cnt = repository.insertLossCause(newCauseId, lossReason.getPlrkReasonid(), "-");
                requireAffected(cnt, "pcs_tl_losscausemst insert", newCauseId);
            }
        } else if (!isBlank(lossReason.getPlrkCauseid()) && !repository.lossCauseExists(lossReason.getPlrkCauseid())) {
            int cnt = repository.insertLossCause(lossReason.getPlrkCauseid(), lossReason.getPlrkReasonid(), "-");
            requireAffected(cnt, "pcs_tl_losscausemst insert", lossReason.getPlrkCauseid());
        }

        // --- Loss reason link upsert ---
        if (!isValidKey(lossReason.getPlrkKeyid()) || !repository.lossReasonExists(lossReason.getPlrkKeyid())) {
            int cnt = repository.insertLossReason(lossReason);
            requireAffected(cnt, "pcs_tl_lossreasonlink insert", lossReason.getPlrkKeyid());
        } else {
            int cnt = repository.updateLossReason(lossReason);
            requireAffected(cnt, "pcs_tl_lossreasonlink update", lossReason.getPlrkKeyid());
        }

        // Ancillary time cleanup then loss column sync
        repository.deleteAncilliaryTime(detail.getPldetailsid(), lossReason.getPlrkLossid());
        syncLossColumn(detailTable, detail, lossReason);

        // --- Loss capture persist ---
        if (isNewEntry) {
            int cnt = repository.insertLossCapture(lossCapture);
            requireAffected(cnt, "pcs_tl_losscapture insert", lossCapture.getPlosKeyid());
        } else {
            int cnt = repository.updateLossCapture(lossCapture);
            requireAffected(cnt, "pcs_tl_losscapture update", lossCapture.getPlosKeyid());
        }

        // Build response model
        PcsTlLosscapture response = new PcsTlLosscapture();
        response.setPlosKeyid(lossCapture.getPlosKeyid());
        response.setPlosFlid(lossCapture.getPlosFlid());
        response.setPlosDate(lossCapture.getPlosDate());
        response.setPlosShiftid(lossCapture.getPlosShiftid());
        response.setPlosFromtime(lossCapture.getPlosFromtime());
        response.setPlosTotime(lossCapture.getPlosTotime());
        response.setPlosLosstime(lossCapture.getPlosLosstime());
        response.setPlosLossreason(lossCapture.getPlosLossreason());
        response.setPlosLossid(lossCapture.getPlosLossid());
        response.setPlosTradeid(lossCapture.getPlosTradeid());
        response.setPlosProdImpact(toCharFlag(lossCapture.getPlosProdImpact()));
        response.setPlosProdImpQty(lossCapture.getPlosProdImpQty());
        response.setPlosLossdescription(lossCapture.getPlosLossdescription());
        response.setPlosPldetailsid(lossCapture.getPlosPldetailsid());
        response.setPlosEquipment(lossCapture.getPlosEquipment());
        response.setPlosDetectedby(lossCapture.getPlosDetectedby());
        response.setPlosTempfield4(lossCapture.getPlosTempfield4());
        response.setPlosTempfield5(lossCapture.getPlosTempfield5());
        response.setPlosActive(toCharFlag(lossCapture.getPlosActive()));
        response.setPlosCreatedby(lossCapture.getPlosCreatedby());
        response.setPlosCreatedon(lossCapture.getPlosCreatedon());
        response.setPlosModifiedon(lossCapture.getPlosModifiedon());
        return response;
    }

    private String resolveDetailTable(PcsSaveRequestDto request) {
        PcsSaveRequestDto.PcsMasterDto master = request.getMaster();
        String sectionId = master.getPrlmSectionid();
        if (isBlank(sectionId)) {
            throw new IllegalArgumentException("Section id is required to resolve detail table");
        }
        String baseName = repository.findSectionCode(sectionId);
        if (isBlank(baseName)) {
            throw new IllegalStateException("Section code not found for section id " + sectionId);
        }
        String tableName = ("pcs_tl_" + baseName)
                .trim()
                .replace("-", "_")
                .replace(" ", "")
                .replace("&", "")
                .replace(",", "")
                .replace("/", "")
                .toLowerCase();

        if (master.getPrlmEntrydate() != null) {
            Integer diff = repository.diffFromTpmStart(master.getPrlmEntrydate());
            if (diff != null && diff < 0) {
                return "pcs_tl_dtl";
            }
        }

        if (!repository.tableExists(tableName)) {
            repository.cloneDetailTable(tableName);
            repository.addPrimaryKey(tableName);
            repository.addForeignKey(tableName);
        }
        return tableName;
    }

    private void ensureKey(Object dto, String seqId, int keyLen, String prefix, String dateFmt, String formatReset) {
        try {
            if (dto instanceof PcsSaveRequestDto.PcsMasterDto m) {
                if (!isValidKey(m.getPrlmKeyid())) {
                    m.setPrlmKeyid(dbActionTemplate.getSequenceNumber(seqId, keyLen, prefix, dateFmt, formatReset));
                }
            } else if (dto instanceof PcsSaveRequestDto.PcsDetailDto d) {
                if (!isValidKey(d.getPldetailsid())) {
                    d.setPldetailsid(dbActionTemplate.getSequenceNumber(seqId, keyLen, prefix, dateFmt, formatReset));
                }
            } else if (dto instanceof PcsSaveRequestDto.PcsLossReasonLinkDto l) {
                if (!isValidKey(l.getPlrkKeyid())) {
                    l.setPlrkKeyid(dbActionTemplate.getSequenceNumber(seqId, keyLen, prefix, dateFmt, formatReset));
                }
            } else if (dto instanceof PcsSaveRequestDto.PcsLossCaptureDto c) {
                if (!isValidKey(c.getPlosKeyid())) {
                    c.setPlosKeyid(dbActionTemplate.getSequenceNumber(seqId, keyLen, prefix, dateFmt, formatReset));
                }
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to generate sequence: " + ex.getMessage(), ex);
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
        return val == null || val.trim().isEmpty();
    }

    private Character toCharFlag(String val) {
        if (isBlank(val)) return null;
        String trimmed = val.trim();
        return trimmed.isEmpty() ? null : Character.toUpperCase(trimmed.charAt(0));
    }

    private String generateSequence(String seqId, int keyLen, String prefix, String dateFmt, String formatReset) {
        try {
            return dbActionTemplate.getSequenceNumber(seqId, keyLen, prefix, dateFmt, formatReset);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate sequence " + seqId + ": " + e.getMessage(), e);
        }
    }

    private void syncLossColumn(String detailTable, PcsSaveRequestDto.PcsDetailDto detail, PcsSaveRequestDto.PcsLossReasonLinkDto lossReason) {
        if (detail == null || lossReason == null) return;
        if (isBlank(detail.getPldetailsid()) || isBlank(lossReason.getPlrkLossid())) return;

        String col = resolveLossColumnName(detail, lossReason);
        if (col == null) {
            logger.warn("No loss column resolved for detailId={} lossId={}", detail.getPldetailsid(), lossReason.getPlrkLossid());
            return;
        }

        try {
            BigDecimal total = repository.sumLossMinutes(detail.getPldetailsid(), lossReason.getPlrkLossid());
            int cnt = repository.updateSingleLossColumn(detailTable, detail.getPldetailsid(), col, total);
            if (cnt <= 0) {
                logger.warn("Skipping loss column sync for detailId={} table={} column={}", detail.getPldetailsid(), detailTable, col);
            }
        } catch (Exception ex) {
            // Do not roll back the whole save if a single derived loss column cannot be synced.
            logger.warn("Loss column sync failed for detailId={} table={} column={} reason={}",
                    detail.getPldetailsid(), detailTable, col, ex.getMessage());
        }
    }

    private String resolveLossColumnName(PcsSaveRequestDto.PcsDetailDto detail, PcsSaveRequestDto.PcsLossReasonLinkDto lossReason) {
        Map<String, BigDecimal> losses = detail.getLosses();
        if (losses == null || losses.isEmpty()) return null;

        if (losses.size() == 1) {
            return normalizeLossKey(losses.keySet().iterator().next());
        }

        BigDecimal minutes = lossReason.getPlrkMinutes();
        if (minutes != null) {
            for (Map.Entry<String, BigDecimal> e : losses.entrySet()) {
                if (minutes.compareTo(e.getValue() == null ? BigDecimal.ZERO : e.getValue()) == 0) {
                    return normalizeLossKey(e.getKey());
                }
            }
        }

        return losses.keySet().stream()
                .filter(Objects::nonNull)
                .sorted((a, b) -> {
                    boolean aMatch = a.matches("loss\\d{2}");
                    boolean bMatch = b.matches("loss\\d{2}");
                    if (aMatch && bMatch) return a.compareTo(b);
                    if (aMatch) return -1;
                    if (bMatch) return 1;
                    return a.compareTo(b);
                })
                .map(this::normalizeLossKey)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private String normalizeLossKey(String key) {
        if (key == null) return null;
        String k = key.trim().toLowerCase();
        if (!k.matches("^[a-z0-9_]+$")) return null;
        return k;
    }

    private void requireAffected(int count, String context, String key) {
        if (count <= 0) {
            throw new IllegalStateException("Expected row change for " + context + " (" + key + ") but got " + count);
        }
    }
}
