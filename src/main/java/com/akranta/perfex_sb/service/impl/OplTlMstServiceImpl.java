package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.OplTlMst;
import com.akranta.perfex_sb.model.BdmTlYycountermeasurelink;
import com.akranta.perfex_sb.model.GenTlDocupdates;
import com.akranta.perfex_sb.repository.OplTlMstRepository;
import com.akranta.perfex_sb.repository.BdmTlYycountermeasurelinkRepository;
import com.akranta.perfex_sb.repository.GenTlDocupdatesRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.OplTlMstService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
////import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import org.springframework.transaction.annotation.Transactional;

@Service
public class OplTlMstServiceImpl implements OplTlMstService {

    private static final Logger logger = LoggerFactory.getLogger(OplTlMstServiceImpl.class);

    private final OplTlMstRepository repository;
    private final DbActionTemplate dbActionTemplate;

    // ✅ NEW repos (for your 3-param save)
    private final BdmTlYycountermeasurelinkRepository yyLinkRepository;
    private final GenTlDocupdatesRepository docupdatesRepository;

    // ✅ REQUIRED PARAMETERS (as you instructed)
    private static final String SEQ_IDENTIFIER = "OPL_TL_MSTLCN0000001";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX = "OPL";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    // ✅ YY LINK sequence settings (matches your Eclipse DAO values)
    private static final String YYCM_SEQ_IDENTIFIER = "BDM_TL_YYCOUNTERMEASURELINKLCN0000001";
    private static final int YYCM_KEY_LENGTH = 11;
    private static final String YYCM_PREFIX = "YCM";
    private static final String YYCM_DATE_FORMAT = "YYMM";
    private static final String YYCM_FORMAT_RESET = "Y";

    public OplTlMstServiceImpl(
            OplTlMstRepository repository,
            DbActionTemplate dbActionTemplate,
            BdmTlYycountermeasurelinkRepository yyLinkRepository,
            GenTlDocupdatesRepository docupdatesRepository
    ) {
        this.repository = repository;
        this.dbActionTemplate = dbActionTemplate;
        this.yyLinkRepository = yyLinkRepository;
        this.docupdatesRepository = docupdatesRepository;
    }

    // ---------------------------
    // your existing CRUD methods
    // (keep them as you already have)
    // ---------------------------

    @Override
    public OplTlMst create(OplTlMst opl) {
        // (your current create 그대로)
        // ...
        if (opl == null) throw new IllegalArgumentException("OPL object is null");

        if (opl.getCreatedon() == null) opl.setCreatedon(LocalDateTime.now());
        if (opl.getModifiedon() == null) opl.setModifiedon(LocalDateTime.now());

        String keyid = opl.getKeyid();
        boolean needsNewKey = keyid == null || keyid.trim().isEmpty() || keyid.equals("{}") || keyid.equals("null");

        if (needsNewKey) {
            try {
                String newKeyId = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET
                );
                if (newKeyId == null || newKeyId.trim().isEmpty()) {
                    throw new IllegalStateException("Failed to generate Key ID - sequence returned null");
                }
                opl.setKeyid(newKeyId);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to generate Key ID: " + e.getMessage(), e);
            }
        } else {
            if (repository.existsById(keyid)) {
                throw new IllegalArgumentException("OPL already exists with keyid: " + keyid);
            }
        }

        return repository.save(opl);
    }

    @Override
    public OplTlMst getByKeyid(String keyid) {
        return repository.findById(keyid)
                .orElseThrow(() -> new ResourceNotFoundException("OPL not found for keyid: " + keyid));
    }

    @Override
    public List<OplTlMst> getAll() {
        return repository.findAll();
    }

    @Override
    public OplTlMst update(String keyid, OplTlMst opl) {
        // (your current update 그대로)
        // ...
        OplTlMst existing = repository.findById(keyid)
                .orElseThrow(() -> new ResourceNotFoundException("OPL not found for keyid: " + keyid));

        existing.setKeyid(keyid);

        existing.setDate(opl.getDate());
        existing.setFactoryid(opl.getFactoryid());
        existing.setTpmpillarid(opl.getTpmpillarid());
        existing.setSectionid(opl.getSectionid());
        existing.setCellid(opl.getCellid());
        existing.setMachineid(opl.getMachineid());

        existing.setTheme(opl.getTheme());
        existing.setThemecategoryid(opl.getThemecategoryid());
        existing.setClassification(opl.getClassification());
        existing.setClassdescription(opl.getClassdescription());
        existing.setBenefit(opl.getBenefit());

        existing.setType(opl.getType());
        existing.setTradeid(opl.getTradeid());
        existing.setPresentcondition(opl.getPresentcondition());
        existing.setPresentimage(opl.getPresentimage());
        existing.setAftercondition(opl.getAftercondition());
        existing.setAfterimage(opl.getAfterimage());
        existing.setLesson(opl.getLesson());

        existing.setPreparedid(opl.getPreparedid());
        existing.setPrepareddate(opl.getPrepareddate());
        existing.setApprovedid(opl.getApprovedid());
        existing.setApproveddate(opl.getApproveddate());

        existing.setStatus(opl.getStatus());
        existing.setRefdoctype(opl.getRefdoctype());
        existing.setRefdocno(opl.getRefdocno());
        existing.setRemarks(opl.getRemarks());
        existing.setRelatedto(opl.getRelatedto());

        existing.setDepartmentmanager(opl.getDepartmentmanager());
        existing.setSectionmanager(opl.getSectionmanager());
        existing.setGroupleader(opl.getGroupleader());

        existing.setRequestflag(opl.getRequestflag());
        existing.setRelated(opl.getRelated());
        existing.setMouldid(opl.getMouldid());

        existing.setIsok(opl.getIsok());
        existing.setIspresent(opl.getIspresent());

        existing.setElementid(opl.getElementid());
        existing.setFlid(opl.getFlid());
        existing.setProcess(opl.getProcess());

        existing.setIsupload(opl.getIsupload());
        existing.setUtiliseforfuture(opl.getUtiliseforfuture());
        existing.setMpworthy(opl.getMpworthy());
        existing.setAprovLevel(opl.getAprovLevel());

        existing.setIsgeneral(opl.getIsgeneral());
        existing.setOplupload(opl.getOplupload());
        existing.setTempfield4(opl.getTempfield4());
        existing.setTempfield5(opl.getTempfield5());

        existing.setActive(opl.getActive());
        existing.setCreatedby(opl.getCreatedby());

        existing.setModifiedon(LocalDateTime.now());

        return repository.save(existing);
    }

    @Override
    public void delete(String keyid) {
        if (!repository.existsById(keyid)) {
            throw new ResourceNotFoundException("OPL not found for keyid: " + keyid);
        }
        repository.deleteById(keyid);
    }

    // ✅ NEW: 3-parameter SAVE (OPL + YY LINK + DOCUPDATES)
    @Override
    @Transactional
    public OplTlMst save(OplTlMst oplTlMst, BdmTlYycountermeasurelink link, GenTlDocupdates doc) {

        if (oplTlMst == null) {
            throw new IllegalArgumentException("oplTlMst is null");
        }

        // 1) Save OPL (create or update)
        String keyid = oplTlMst.getKeyid();
        boolean hasValidKey = keyid != null && !keyid.trim().isEmpty() && !keyid.equals("{}") && !keyid.equals("null");

        OplTlMst savedOpl;
        if (hasValidKey && repository.existsById(keyid)) {
            savedOpl = update(keyid, oplTlMst);
        } else {
            savedOpl = create(oplTlMst);
        }

        String oplKey = savedOpl.getKeyid();

        // 2) YY CounterMeasure Link logic (delete old + insert new)
        if (link != null) {
            link.setYycmCountermsrid(oplKey);
            link.setYycmRefdoctype("OPL");

            Character yyActive = toCharFlag(link.getYycmActive());
            if (yyActive == null) yyActive = 'Y';
            link.setYycmActive(yyActive);
            if (link.getYycmCreatedon() == null) {
                link.setYycmCreatedon(LocalDateTime.now());
            }
            link.setYycmModifiedon(LocalDateTime.now());

            // delete existing links for same oplKey + OPL
            yyLinkRepository.deleteByYycmCountermsridAndYycmRefdoctype(oplKey, "OPL");

            // generate yycm_keyid if missing (like Eclipse DAO)
            String yycmKey = link.getYycmKeyid();
            boolean needsYycmKey = (yycmKey == null || yycmKey.trim().isEmpty() || yycmKey.equals("{}") || yycmKey.equals("null"));
            if (needsYycmKey) {
                try {
                    String newYycmKey = dbActionTemplate.getSequenceNumber(
                            YYCM_SEQ_IDENTIFIER, YYCM_KEY_LENGTH, YYCM_PREFIX, YYCM_DATE_FORMAT, YYCM_FORMAT_RESET
                    );
                    if (newYycmKey == null || newYycmKey.trim().isEmpty()) {
                        throw new IllegalStateException("Failed to generate YYCM keyid - sequence returned null");
                    }
                    link.setYycmKeyid(newYycmKey);
                } catch (Exception e) {
                    throw new IllegalStateException("Failed to generate YYCM keyid: " + e.getMessage(), e);
                }
            }

            yyLinkRepository.save(link);
        }

        // 3) DocUpdates logic (set detailid = oplKey)
        if (doc != null) {
            doc.setDcupDetailid(oplKey);

            if (doc.getDcupCreatedon() == null) {
                doc.setDcupCreatedon(LocalDate.now());
            }
            doc.setDcupModifiedon(LocalDate.now());

            // If key is provided, save() will update if exists, insert if not.
            // If you want STRICT UPDATE only, tell me — I’ll change this to “if not exists -> throw”.
            docupdatesRepository.save(doc);
        }

        return savedOpl;
    }



       @Override
    public List<Map<String, Object>> recallStudents(String oplId, String cellId, String oplKeyid) {
        List<Map<String, Object>> result = repository.recallOplStudents(oplId, cellId, oplKeyid);
        if (result == null || result.isEmpty()) {
            throw new RuntimeException("Result is empty");
        }
        return result;
    }

    /**
     * Convert a String/Character flag to a single uppercase Character (or null if blank).
     */
    private Character toCharFlag(Object val) {
        if (val == null) return null;
        if (val instanceof Character c) {
            return Character.isWhitespace(c) ? null : Character.toUpperCase(c);
        }
        String s = val.toString();
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        return Character.toUpperCase(s.charAt(0));
    }

}
