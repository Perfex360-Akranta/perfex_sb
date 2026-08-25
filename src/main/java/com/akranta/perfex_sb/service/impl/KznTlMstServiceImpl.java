package com.akranta.perfex_sb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.akranta.perfex_sb.controller.KznTlMstController;
import com.akranta.perfex_sb.dto.updateThemeCatageryDto;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.GenTlWorkFlowInfo;
import com.akranta.perfex_sb.model.KznTlMst;
import com.akranta.perfex_sb.repository.GenTlWorkFlowInfoRepository;
import com.akranta.perfex_sb.repository.KznTlMstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.KznTlMstService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class KznTlMstServiceImpl implements KznTlMstService {
    private static final Logger logger = LoggerFactory.getLogger(KznTlMstController.class);

    @Autowired
    private KznTlMstRepository repository;

    @Autowired
    private GenTlWorkFlowInfoRepository WrkFlwrepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "KZN";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String TABLEIDENTIFIER = "GEN_TL_WORKFLOW_INFO";
    private static final int WRK_KEY_LENGTH = 10;
    private static final String WRK_PREFIX = "WF";
    private static final String WRK_DATE_FORMAT = "YY";
    private static final String WRK_FORMAT_RESET = "Y";

    public ResponseEntity<KznTlMst> create(@RequestBody KznTlMst kznTlMst) {
        try {
            String elementId = kznTlMst.getElementid();
            String location = null;
            String seqIdentfr = "KZN_TL_MST";

            if (elementId != null && elementId.length() > 10) {
                location = elementId.substring(11, 21);
                seqIdentfr += location;
            }

            if (kznTlMst.getKeyid() == null || kznTlMst.getKeyid().trim().isEmpty()) {
                String newKeyId = dbActionTemplate.getSequenceNumber(seqIdentfr, KEY_LENGTH, PREFIX, DATE_FORMAT,
                        FORMAT_RESET);
                kznTlMst.setKeyid(newKeyId);
                logger.info("Generated new Key ID: {} for Kaizen", newKeyId);
            } else {
                if (repository.existsById(kznTlMst.getKeyid())) {
                    KznTlMst updateEntity = repository.save(kznTlMst);
                    logger.info("Kaizen Successfully updated : {}", updateEntity.getKeyid());

                    return ResponseEntity.status(HttpStatus.OK).body(updateEntity);
                } else {
                    new RuntimeException("Kaizen not found: " + kznTlMst.getKeyid());
                }
            }

            // Save the entity
            KznTlMst savedEntity = repository.save(kznTlMst);
            logger.info("Successfully created Kaizen  Key ID: {}", savedEntity.getKeyid());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);

        } catch (Exception e) {
            logger.error("Error creating Kaizen: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    public KznTlMst getKaizenListById(String keyid) {
        return repository.findById(keyid).orElseThrow(() -> new RuntimeException("Kaizen not found: " + keyid));
    }

    public Object findAll() {
        // throw new UnsupportedOperationException("Unimplemented method 'findAll'");
        return repository.findAll();

    }

    /*
     * public List<Map<String, Object>> findAll(String flid) {
     * List<Map<String, Object>> result = repository.findAll(flid);
     * if (result == null || result.isEmpty()) {
     * throw new RuntimeException("Result is empty");
     * }
     * return result;
     * }
     */
    public KznTlMst updateKaizen(@RequestBody KznTlMst kznTlMst) {

        // KznTlMst entity = repository.findById(dto.getKeyid()).orElseThrow(() ->
        // new RuntimeException("Kaizen not found: " + dto.getKeyid()));
        KznTlMst updateEntity = null;
        if (repository.existsById(kznTlMst.getKeyid())) {
            updateEntity = repository.save(kznTlMst);
            logger.info("Successfully updated i: {}", updateEntity.getKeyid());
        }
        return updateEntity;

    }

    @Override
    public KznTlMst getByKeyid(String keyid) {
        return repository.findById(keyid)
                .orElseThrow(() -> new ResourceNotFoundException("Kaizen not found for keyid: " + keyid));
    }

    @Override
    public List<Map<String, Object>> findAll(String flid) {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public String findKeyid(String keyId) {
        return repository.findKeyid(keyId);
    }

    public List<GenTlWorkFlowInfo> saveSimpliAppval(
            List<GenTlWorkFlowInfo> approvals) {

        for (GenTlWorkFlowInfo wrkFlw : approvals) {

            /* ------------------- INSERT OR UPDATE ------------------- */

            if (wrkFlw.getKeyid() == null || wrkFlw.getKeyid().isBlank()) {

                String keyId = null;
                try {
                    keyId = dbActionTemplate.getSequenceNumber(
                            TABLEIDENTIFIER,
                            WRK_KEY_LENGTH,
                            WRK_PREFIX,
                            WRK_DATE_FORMAT,
                            WRK_FORMAT_RESET);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                wrkFlw.setKeyid(keyId);
            }
        }

        List<GenTlWorkFlowInfo> savedList = WrkFlwrepository.saveAll(approvals); // INSERT

        for (GenTlWorkFlowInfo wf : savedList) {

            // Rework logic
            if (wf.getStatus() != null && wf.getStatus() == 'E') {
                String ref_id = wf.getRef_id();
                String wrml_keyid = wf.getWrml_keyid();
                String ref_type = wf.getRef_type();
                WrkFlwrepository.updateReject(
                        ref_id,
                        wrml_keyid,
                        ref_type);
            } else {
                refreshInbox(wf);
            }

            // Kaizen status update
            if (wf.getRef_type() != null && wf.getRef_type().length() > 2) {
                String refPrefix = wf.getRef_type().substring(0, 3);
                if ("KZN".equals(refPrefix)) {

                    Character kaizenStatus = null;
                    String nextRoleName = "-";
                    String lastLevel = "Y";

                    if (wf.getStatus() == 'A' && "Y".equals(lastLevel)) {
                        kaizenStatus = 'C';
                    } else if (wf.getStatus() == 'A') {
                        kaizenStatus = 'A';
                    } else if (wf.getStatus() == 'E') {
                        kaizenStatus = 'E';
                        nextRoleName = "REWORK";
                    } else if (wf.getStatus() == 'R') {
                        kaizenStatus = 'R';
                    }
                    String ref_id = wf.getRef_id();
                    WrkFlwrepository.updateKZNStatus(kaizenStatus, nextRoleName, ref_id);
                }
            }
        }

        return savedList;
    }

    private int refreshInbox(GenTlWorkFlowInfo wf) {
        int updaterRow = 0;
        if (wf.getEmployee_id() != null) {
            updaterRow = WrkFlwrepository.updateAdmApproval(wf.getEmployee_id(), wf.getRef_id());
            // if (updaterRow == 0) {
            // throw new RuntimeException("No record found for = " + wf.getRef_id());
            // }
        } else {
            WrkFlwrepository.deleteAdmApproval(wf.getRef_id());
        }
        return updaterRow;
    }

    /* ------------------- KAIZEN UPDATE ------------------- */

    public void updateKaizenStatus(List<GenTlWorkFlowInfo> workflowList, String refType) {

        if (!"KZN".equals(refType)) {
            return; // only Kaizen
        }

        for (GenTlWorkFlowInfo wf : workflowList) {

            String wfStatus = String.valueOf(wf.getStatus()); // A / E / R
            Character kaizenStatus = '-';
            String nextRoleName = "-";
            String lastLevel = "Y";

            /* ---------- STATUS MAPPING ---------- */

            if ("A".equals(wfStatus) && "Y".equals(lastLevel)) {
                kaizenStatus = 'C';
            } else if ("A".equals(wfStatus)) {
                kaizenStatus = 'A';
            } else if ("E".equals(wfStatus)) {
                kaizenStatus = 'E';
                nextRoleName = "REWORK";
            } else if ("R".equals(wfStatus)) {
                kaizenStatus = 'R';
            } else {
                continue;
            }

            /* ---------- UPDATE KAIZEN ---------- */

            WrkFlwrepository.updateKZNStatus(kaizenStatus, nextRoleName, wf.getRef_id());
        }
    }

    @Override
    public List<updateThemeCatageryDto> updateThemeCatogery(List<updateThemeCatageryDto> updateDto) {
        for (updateThemeCatageryDto dto : updateDto) {
            String kznmKeyId = dto.getKznmKeyId();
            String kznmResult = dto.getKznmResultArea();
            String kznmThemeCatogeryId = dto.getKznmThemeCatogeryId();

            int result = repository.updateThemeNative(kznmThemeCatogeryId, kznmResult, kznmKeyId);

            if (result > 0) {
                logger.info("Updated");
            }

        }
        return updateDto;
    }

    // @Override
    // public List<GenTlWorkFlowInfo> saveSimpliAppval(List<GenTlWorkFlowInfo>
    // wrkFlw) {

    // String tableIdentifier="GEN_TL_WORKFLOW_INFO";
    // for (GenTlWorkFlowInfo wf : wrkFlw) {

    // if (wf.getKeyid() == null || wf.getKeyid().isBlank()) {
    // String keyId = null;
    // try {
    // keyId = dbActionTemplate.getSequenceNumber(
    // tableIdentifier,
    // WRK_KEY_LENGTH,
    // WRK_PREFIX,
    // WRK_DATE_FORMAT,
    // WRK_FORMAT_RESET
    // );
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // wf.setKeyid(keyId);
    // logger.info("Generated WF Key: {}", keyId);
    // }
    // }

    // return WrkFlwrepository.saveAll(wrkFlw);

    // }

}
