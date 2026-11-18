package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.model.Bal_pcs_tl_rejectionbreakupdata;
import com.akranta.perfex_sb.repository.Bal_pcs_tl_rejectionbreakupdataRepository;
import com.akranta.perfex_sb.service.Bal_pcs_tl_rejectionbreakupdataService;
import com.akranta.perfex_sb.service.DbActionTemplate;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class Bal_pcs_tl_rejectionbreakupdataServiceImpl implements Bal_pcs_tl_rejectionbreakupdataService {

    private static final Logger logger = LoggerFactory.getLogger(Bal_pcs_tl_rejectionbreakupdataService.class);

    @Autowired
    private Bal_pcs_tl_rejectionbreakupdataRepository repository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final String MASTER_SEQ_IDENTIFIER = "BAL_PCS_TL_REJECTIONBREAKUPDATA";
    private static final int MASTER_KEY_LENGTH = 15;
    private static final String MASTER_PREFIX = "PRBD"; // legacy "\tPRLM"
    private static final String MASTER_DATE_FORMAT = "YY";
    private static final String MASTER_FORMAT_RESET = "Y";

    @Override
    @Transactional
    public List<Bal_pcs_tl_rejectionbreakupdata> saveOrUpdate(String pprdKeyid, String createdby,
            List<Bal_pcs_tl_rejectionbreakupdata> rows) {

        if (isBlank(pprdKeyid)) {
            throw new IllegalArgumentException("Save production data first (production key is empty)");
        }
        if (rows == null || rows.isEmpty()) {
            throw new IllegalArgumentException("No rejection rows to save");
        }

        List<Bal_pcs_tl_rejectionbreakupdata> saved = new ArrayList<>();

        for (Bal_pcs_tl_rejectionbreakupdata row : rows) {

            row.setPprd_keyid(pprdKeyid);
            // validate(row);
            applyDefaults(row);

            boolean isNew = isBlank(row.getKeyid()) || !repository.existsByKeyid(row.getKeyid());

            if (isNew) {
                // INSERT
                String newKeyid;
                try {
                    newKeyid = dbActionTemplate.getSequenceNumber(
                            MASTER_SEQ_IDENTIFIER, MASTER_KEY_LENGTH, MASTER_PREFIX,
                            MASTER_DATE_FORMAT, MASTER_FORMAT_RESET);
                } catch (Exception e) {
                    throw new IllegalStateException("Failed to generate Key ID: " + e.getMessage(), e);
                }
                if (isBlank(newKeyid)) {
                    throw new IllegalStateException("Failed to generate Key ID - sequence returned null");
                }
                row.setKeyid(newKeyid);
                row.setActive('Y');
                row.setCreatedby(createdby);
                row.setCreatedon(LocalDateTime.now());
                logger.info("Generated new Rejection Key ID: {}", newKeyid);
            } else {
                // UPDATE: keep original created info
                Bal_pcs_tl_rejectionbreakupdata existing = repository.findByKeyid(row.getKeyid())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Rejection record not found for keyid: " + row.getKeyid()));
                row.setCreatedby(existing.getCreatedby());
                row.setCreatedon(existing.getCreatedon());
                row.setActive(existing.getActive());
            }

            row.setModifiedon(LocalDateTime.now());
            Bal_pcs_tl_rejectionbreakupdata savedRow = repository.save(row);
            logger.info("{} Rejection with Key ID: {}", isNew ? "Created" : "Updated", savedRow.getKeyid());
            saved.add(savedRow);
        }
        return saved;
    }

    // columns are NOT NULL, so fill blanks with "-" as the legacy code did
    private void applyDefaults(Bal_pcs_tl_rejectionbreakupdata r) {
        if (isBlank(r.getPhenomena()))
            r.setPhenomena("-");
        if (isBlank(r.getEquipment()))
            r.setEquipment("-");
        if (isBlank(r.getRejectionreason()))
            r.setRejectionreason("-");
        if (isBlank(r.getDefect()))
            r.setDefect("-");
        r.setTempfield1("-");
        r.setTempfield2("-");
        r.setTempfield3("-");
        r.setTempfield4("-");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    //delete
    @Override
@Transactional
public void deleteByKeyid(String keyid) {

    if (isBlank(keyid)) {
        throw new IllegalArgumentException("Rejection key id is required for delete");
    }

    int rows = repository.deleteByKeyId(keyid);

    if (rows == 0) {
        throw new EntityNotFoundException("Rejection record not found for keyid: " + keyid);
    }

    logger.info("Deleted Rejection with Key ID: {}", keyid);
}

}
