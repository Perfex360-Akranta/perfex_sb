package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.BAL_PlmTlStandards;
import com.akranta.perfex_sb.model.BalPlmTlCbmstdcadtl;
import com.akranta.perfex_sb.repository.PlmTlStandardsRepository;
import com.akranta.perfex_sb.repository.BalPlmTlCbmstdcadtlRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.Bal_PlmTlStandardsService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.PlmTlStandardsRequest;
import com.akranta.perfex_sb.dto.PlmTlStandardsDetailDTO;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;   // already imported


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Bal_PlmTlStandardsServiceImpl implements Bal_PlmTlStandardsService {

    private static final Logger logger = LoggerFactory.getLogger(Bal_PlmTlStandardsServiceImpl.class);

    private final PlmTlStandardsRepository plmTlStandardsRepository;
    private final BalPlmTlCbmstdcadtlRepository plmTlCbmstdcadtlRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "PLM_TL_STANDARDS";
    private static final int KEY_LENGTH = 12;
    private static final String PREFIX = "PMD";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    // CBM key-gen settings — mirrors the legacy DAO's
    // dbActionTemplate.getSequenceNumber(TBL_PLM_TL_CBMSTDCADTL, 12, "PCC", "MMYY", "Y")
    private static final String CBM_SEQ_IDENTIFIER = "PLM_TL_CBMSTDCADTL";
    private static final int CBM_KEY_LENGTH = 12;
    private static final String CBM_PREFIX = "PCC";
    private static final String CBM_DATE_FORMAT = "YY";
    private static final String CBM_FORMAT_RESET = "Y";

    public Bal_PlmTlStandardsServiceImpl(
            PlmTlStandardsRepository plmTlStandardsRepository,
            BalPlmTlCbmstdcadtlRepository plmTlCbmstdcadtlRepository,
            DbActionTemplate dbActionTemplate) {
        this.plmTlStandardsRepository = plmTlStandardsRepository;
        this.plmTlCbmstdcadtlRepository = plmTlCbmstdcadtlRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<PlmTlStandardsRequest> savePlmTlStandards(PlmTlStandardsRequest request) throws Exception {

        List<PlmTlStandardsDetailDTO> details = request.getDetails();
        if (details == null || details.isEmpty()) {
            throw new RuntimeException("No PM Standard rows to save");
        }

        PlmTlStandardsRequest result = new PlmTlStandardsRequest();
        List<PlmTlStandardsDetailDTO> savedList = new ArrayList<>();

        for (PlmTlStandardsDetailDTO detailDto : details) {

            BAL_PlmTlStandards row = detailDto.getStandard();
            if (row == null) {
                throw new RuntimeException("PM Standard row data is missing in one of the detail entries");
            }

            row.setFactoryid(request.getFactoryId() != null ? request.getFactoryId() : row.getFactoryid());
            row.setSectionid(request.getSectionId() != null ? request.getSectionId() : row.getSectionid());
            row.setCellid(request.getCellId() != null ? request.getCellId() : row.getCellid());
            row.setMachineid(request.getMachineId() != null ? request.getMachineId() : row.getMachineid());
            row.setLocationid(request.getLocationId() != null ? request.getLocationId() : row.getLocationid());
            row.setFlid(request.getFlId() != null ? request.getFlId() : row.getFlid());

            String keyid = row.getKeyid();
            boolean isInsertMode = keyid == null ||
                    keyid.trim().isEmpty() ||
                    keyid.equals("{}") ||
                    keyid.equals("undefined") ||
                    !plmTlStandardsRepository.existsById(keyid);

            PlmTlStandardsDetailDTO savedDetail;
            if (isInsertMode) {
                savedDetail = insertRow(row, detailDto.getCbmData());
            } else {
                savedDetail = updateRow(row, detailDto.getCbmData());
            }
            savedList.add(savedDetail);
        }

        result.setDetails(savedList);
        result.setFactoryId(request.getFactoryId());
        result.setSectionId(request.getSectionId());
        result.setCellId(request.getCellId());
        result.setMachineId(request.getMachineId());
        result.setLocationId(request.getLocationId());
        result.setFlId(request.getFlId());
        result.setFormActionMode(request.getFormActionMode());
        result.setFormMode(request.getFormMode());
        result.setFormHeader(request.getFormHeader());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private PlmTlStandardsDetailDTO insertRow(BAL_PlmTlStandards row, List<BalPlmTlCbmstdcadtl> cbmData) throws Exception {
        String newKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET
        );

        if (newKeyid == null || newKeyid.trim().isEmpty()) {
            logger.error("Failed to generate the PM Standard Key ID");
            throw new RuntimeException("Failed to generate PM Standard Key ID");
        }

        row.setKeyid(newKeyid);
        if (row.getActive() == null) {
            row.setActive('Y');
        }
        if (row.getWogenflag() == null) {
            row.setWogenflag('N');
        }
        if (row.getCreatedon() == null) {
            row.setCreatedon(LocalDateTime.now());
        }
        row.setModifiedon(LocalDateTime.now());

        BAL_PlmTlStandards saved = plmTlStandardsRepository.save(row);
        logger.info("Generated new PM Standard Key ID: {}", newKeyid);

        List<BalPlmTlCbmstdcadtl> savedCbm = upsertCbmData(cbmData, saved.getKeyid(), saved.getCreatedby());

        PlmTlStandardsDetailDTO result = new PlmTlStandardsDetailDTO();
        result.setStandard(saved);
        result.setCbmData(savedCbm);
        return result;
    }

    private PlmTlStandardsDetailDTO updateRow(BAL_PlmTlStandards row, List<BalPlmTlCbmstdcadtl> cbmData) throws Exception {
        BAL_PlmTlStandards existing = plmTlStandardsRepository.findById(row.getKeyid())
                .orElseThrow(() -> new ResourceNotFoundException("PM Standard not found"));

        existing.setFactoryid(row.getFactoryid());
        existing.setSectionid(row.getSectionid());
        existing.setCellid(row.getCellid());
        existing.setMachineid(row.getMachineid());
        existing.setAssemblyid(row.getAssemblyid());
        existing.setSubassemblyid(row.getSubassemblyid());
        existing.setEqpgroupid(row.getEqpgroupid());
        existing.setSource(row.getSource());
        existing.setSupplierid(row.getSupplierid());
        existing.setTradeid(row.getTradeid());
        existing.setFrequency(row.getFrequency());
        existing.setFrequencyunit(row.getFrequencyunit());
        existing.setUomid(row.getUomid());
        existing.setHowmethod(row.getHowmethod());
        existing.setDuration(row.getDuration());
        existing.setActivitytype(row.getActivitytype());
        existing.setActivitysubtype(row.getActivitysubtype());
        existing.setBomid(row.getBomid());
        existing.setLocation(row.getLocation());
        existing.setActivity(row.getActivity());
        existing.setStandard(row.getStandard());
        existing.setMachinecondition(row.getMachinecondition());
        existing.setPlanconfigstatus(row.getPlanconfigstatus());
        existing.setIssparesreq(row.getIssparesreq());
        existing.setIstoolsreq(row.getIstoolsreq());
        existing.setRefdoctype(row.getRefdoctype());
        existing.setRefdocno(row.getRefdocno());
        existing.setPreparedbyid(row.getPreparedbyid());
        existing.setFormatno(row.getFormatno());
        existing.setEffectivedate(row.getEffectivedate());
        existing.setWogenflag(row.getWogenflag());
        existing.setPhenomenaid(row.getPhenomenaid());
        existing.setCauseid(row.getCauseid());
        existing.setRoutenumber(row.getRoutenumber());
        existing.setIncludeinshutdownmaint(row.getIncludeinshutdownmaint());
        existing.setGroupno(row.getGroupno());
        existing.setResultifnotdone(row.getResultifnotdone());
        existing.setCorrectiveaction(row.getCorrectiveaction());
        existing.setIssftpermitreq(row.getIssftpermitreq());
        existing.setSafetyinstruction(row.getSafetyinstruction());
        existing.setInactivateddate(row.getInactivateddate());
        existing.setMonthweekno(row.getMonthweekno());
        existing.setRelatedto(row.getRelatedto());
        existing.setMouldid(row.getMouldid());
        existing.setLocationid(row.getLocationid());
        existing.setFlid(row.getFlid());
        existing.setElementid(row.getElementid());
        existing.setMaxvalue(row.getMaxvalue());
        existing.setMinvalue(row.getMinvalue());
        existing.setTarget(row.getTarget());
        existing.setTempfield8(row.getTempfield8());
        existing.setTempfield9(row.getTempfield9());
        existing.setTempfield10(row.getTempfield10());
        existing.setActive(row.getActive() != null ? row.getActive() : existing.getActive());
        existing.setModifiedon(LocalDateTime.now());

        BAL_PlmTlStandards saved = plmTlStandardsRepository.save(existing);
        logger.info("Successfully updated PM Standard with Key ID: {}", saved.getKeyid());

        String createdby = row.getCreatedby() != null ? row.getCreatedby() : existing.getCreatedby();
        List<BalPlmTlCbmstdcadtl> savedCbm = upsertCbmData(cbmData, saved.getKeyid(), createdby);

        PlmTlStandardsDetailDTO result = new PlmTlStandardsDetailDTO();
        result.setStandard(saved);
        result.setCbmData(savedCbm);
        return result;
    }

    /**
     * Inserts or updates each CBM row present in the incoming list. Mirrors the
     * legacy DAO's behavior exactly: no delete logic at all. If cbmList is null
     * or empty, existing CBM rows for this PM Standard are left untouched.
     */
    private List<BalPlmTlCbmstdcadtl> upsertCbmData(List<BalPlmTlCbmstdcadtl> cbmList, String pmStandardId, String createdby) throws Exception {
        if (cbmList == null || cbmList.isEmpty()) {
            // Nothing sent this time — leave whatever's already saved as-is,
            // same as the legacy code (which simply skips the whole block).
            return plmTlCbmstdcadtlRepository.findByPmstandardid(pmStandardId);
        }

        List<BalPlmTlCbmstdcadtl> savedRows = new ArrayList<>();

        for (BalPlmTlCbmstdcadtl cbm : cbmList) {
            String cbmKeyid = cbm.getKeyid();
            boolean isInsertMode = cbmKeyid == null ||
                    cbmKeyid.trim().isEmpty() ||
                    cbmKeyid.equals("{}") ||
                    cbmKeyid.equals("undefined") ||
                    !plmTlCbmstdcadtlRepository.existsById(cbmKeyid);

            BalPlmTlCbmstdcadtl toSave;
            if (isInsertMode) {
                toSave = insertCbmRow(cbm, pmStandardId, createdby);
            } else {
                toSave = updateCbmRow(cbm, pmStandardId);
            }
            savedRows.add(toSave);
        }

        logger.info("Saved {} CBM row(s) for PM Standard {}", savedRows.size(), pmStandardId);
        return savedRows;
    }

    private BalPlmTlCbmstdcadtl insertCbmRow(BalPlmTlCbmstdcadtl cbm, String pmStandardId, String createdby) throws Exception {
        String cbmKeyid = dbActionTemplate.getSequenceNumber(
                CBM_SEQ_IDENTIFIER, CBM_KEY_LENGTH, CBM_PREFIX, CBM_DATE_FORMAT, CBM_FORMAT_RESET
        );
        if (cbmKeyid == null || cbmKeyid.trim().isEmpty()) {
            logger.error("Failed to generate the CBM Key ID");
            throw new RuntimeException("Failed to generate CBM Key ID");
        }
        cbm.setKeyid(cbmKeyid);
        cbm.setPmstandardid(pmStandardId);
        if (cbm.getActive() == null) {
            cbm.setActive('Y');
        }
        cbm.setCreatedby(createdby);
        cbm.setCreatedon(LocalDateTime.now());
        cbm.setModifiedon(LocalDateTime.now());
        return plmTlCbmstdcadtlRepository.save(cbm);
    }

    private BalPlmTlCbmstdcadtl updateCbmRow(BalPlmTlCbmstdcadtl cbm, String pmStandardId) throws Exception {
        BalPlmTlCbmstdcadtl existing = plmTlCbmstdcadtlRepository.findById(cbm.getKeyid())
                .orElseThrow(() -> new ResourceNotFoundException("CBM row not found for keyid: " + cbm.getKeyid()));

        existing.setPmstandardid(pmStandardId);
        existing.setInspectionid(cbm.getInspectionid());
        existing.setZoneid(cbm.getZoneid());
        existing.setZonecolor(cbm.getZonecolor());
        existing.setDesirablereading(cbm.getDesirablereading());
        existing.setUpperlimit(cbm.getUpperlimit());
        existing.setLowerlimit(cbm.getLowerlimit());
        existing.setCorrectiveaction(cbm.getCorrectiveaction());
        existing.setCbmcondition(cbm.getCbmcondition());
        existing.setUomid(cbm.getUomid());
        existing.setMeasuringmethod(cbm.getMeasuringmethod());
        existing.setActive(cbm.getActive() != null ? cbm.getActive() : existing.getActive());
        existing.setModifiedon(LocalDateTime.now());

        return plmTlCbmstdcadtlRepository.save(existing);
    }

    // @Override
    // @Transactional
    // public ResponseEntity<Void> deletePlmTlStandards(String keyid) throws Exception {
    //     if (keyid == null || keyid.trim().isEmpty()) {
    //         throw new RuntimeException("keyid is required to delete PM Standard");
    //     }
    //     if (!plmTlStandardsRepository.existsById(keyid)) {
    //         throw new ResourceNotFoundException("PM Standard not found for keyid: " + keyid);
    //     }

    //     plmTlStandardsRepository.deleteById(keyid);
    //     logger.info("Deleted PM Standard with Key ID: {}", keyid);

    //     return ResponseEntity.noContent().build();
    // }

    @Override
@Transactional
public ResponseEntity<Void> deletePlmTlStandards(String keyid) throws Exception {
    if (keyid == null || keyid.trim().isEmpty()) {
        throw new RuntimeException("keyid is required to delete PM Standard");
    }
    if (!plmTlStandardsRepository.existsById(keyid)) {
        throw new ResourceNotFoundException("PM Standard not found for keyid: " + keyid);
    }

    // 1. delete child CBM rows first (cmdt_pmstandardid = keyid)
    List<BalPlmTlCbmstdcadtl> cbmRows = plmTlCbmstdcadtlRepository.findByPmstandardid(keyid);
    if (cbmRows != null && !cbmRows.isEmpty()) {
        plmTlCbmstdcadtlRepository.deleteAll(cbmRows);
        plmTlCbmstdcadtlRepository.flush();   // make sure children are gone before the parent delete
        logger.info("Deleted {} CBM row(s) for PM Standard {}", cbmRows.size(), keyid);
    }

    // 2. then delete the PM Standard itself
    plmTlStandardsRepository.deleteById(keyid);
    logger.info("Deleted PM Standard with Key ID: {}", keyid);

    return ResponseEntity.noContent().build();
}

    @Override
//@Transactional(readOnly = true)
public ResponseEntity<List<Map<String, Object>>> getCBM(String pmStandardId) throws Exception {
    logger.info("Fetching CBM zone data for PM Standard: {}", pmStandardId);

    // mirrors legacy DAO's isValidKeyId() guard -- pass null through when the
    // incoming id isn't a real value, so the LEFT JOIN falls back to
    // "zones only, no CBM data attached"
    String effectiveId = (pmStandardId == null
            || pmStandardId.trim().isEmpty()
            || pmStandardId.equals("{}")
            || pmStandardId.equals("undefined"))
            ? null
            : pmStandardId;

    List<Map<String, Object>> cbmList = plmTlCbmstdcadtlRepository.getCBM(effectiveId);

    logger.info("Fetched {} CBM zone row(s) for PM Standard: {}", cbmList.size(), pmStandardId);
    return ResponseEntity.status(HttpStatus.OK).body(cbmList);
}
}