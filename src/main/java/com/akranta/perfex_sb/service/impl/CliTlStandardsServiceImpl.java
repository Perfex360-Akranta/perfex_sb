package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.CliTlStandards;
import com.akranta.perfex_sb.repository.CliTlStandardsRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.CliTlStandardsService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.CliTlStandardsRequest;

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
public class CliTlStandardsServiceImpl implements CliTlStandardsService {

    private static final Logger logger = LoggerFactory.getLogger(CliTlStandardsServiceImpl.class);

    private final CliTlStandardsRepository cliTlStandardsRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "CLI_TL_STANDARDS";
    private static final int KEY_LENGTH = 12;
    private static final String PREFIX = "CL";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    public CliTlStandardsServiceImpl(
            CliTlStandardsRepository cliTlStandardsRepository,
            DbActionTemplate dbActionTemplate) {
        this.cliTlStandardsRepository = cliTlStandardsRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

@Override
@Transactional
public ResponseEntity<CliTlStandardsRequest> saveCliTlStandards(CliTlStandardsRequest request) throws Exception {

    List<CliTlStandards> details = request.getDetails();
    if (details == null || details.isEmpty()) {
        throw new RuntimeException("No CLI Standard rows to save");
    }

    CliTlStandardsRequest result = new CliTlStandardsRequest();
    List<CliTlStandards> savedList = new ArrayList<>();

    for (CliTlStandards row : details) {

        // Only overwrite with the DTO's header-level value when it's actually
        // present (multi-row screen always sends these). Otherwise keep
        // whatever the row already carries (single-row save path).
        row.setFactoryid(request.getFactoryId() != null ? request.getFactoryId() : row.getFactoryid());
        row.setSectionid(request.getSectionId() != null ? request.getSectionId() : row.getSectionid());
        row.setCellid(request.getCellId() != null ? request.getCellId() : row.getCellid());
        row.setMachineid(request.getMachineId() != null ? request.getMachineId() : row.getMachineid());
        row.setFlid(request.getFlId() != null ? request.getFlId() : row.getFlid());
        row.setElementid(request.getElementId() != null ? request.getElementId() : row.getElementid());

        String keyid = row.getKeyid();
        boolean isInsertMode = keyid == null ||
                keyid.trim().isEmpty() ||
                keyid.equals("{}") ||
                keyid.equals("undefined") ||
                !cliTlStandardsRepository.existsById(keyid);

        if (isInsertMode) {
            savedList.add(insertRow(row));
        } else {
            savedList.add(updateRow(row));
        }
    }

    result.setDetails(savedList);
    result.setFactoryId(request.getFactoryId());
    result.setSectionId(request.getSectionId());
    result.setCellId(request.getCellId());
    result.setMachineId(request.getMachineId());
    result.setFlId(request.getFlId());
    result.setElementId(request.getElementId());
    result.setFormActionMode(request.getFormActionMode());
    result.setFormMode(request.getFormMode());
    result.setFormHeader(request.getFormHeader());

    return ResponseEntity.status(HttpStatus.OK).body(result);
}

    private CliTlStandards insertRow(CliTlStandards row) throws Exception {
        String newKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET
        );

        if (newKeyid == null || newKeyid.trim().isEmpty()) {
            logger.error("Failed to generate the CLI Standard Key ID");
            throw new RuntimeException("Failed to generate CLI Standard Key ID");
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

        CliTlStandards saved = cliTlStandardsRepository.save(row);
        logger.info("Generated new CLI Standard Key ID: {}", newKeyid);
        return saved;
    }

    private CliTlStandards updateRow(CliTlStandards row) {
        CliTlStandards existing = cliTlStandardsRepository.findById(row.getKeyid())
                .orElseThrow(() -> new ResourceNotFoundException("CLI Standard not found"));

        existing.setFactoryid(row.getFactoryid());
        existing.setSectionid(row.getSectionid());
        existing.setCellid(row.getCellid());
        existing.setMachineid(row.getMachineid());
        existing.setFlid(row.getFlid());
        existing.setElementid(row.getElementid());
        existing.setAssemblyid(row.getAssemblyid());
        existing.setPhenomenaid(row.getPhenomenaid());
        existing.setCauseid(row.getCauseid());
        existing.setJhid(row.getJhid());
        existing.setTradeid(row.getTradeid());
        existing.setShiftid(row.getShiftid());
        existing.setEffectivedate(row.getEffectivedate());
        existing.setRefdoctype(row.getRefdoctype());
        existing.setRefdocno(row.getRefdocno());
        existing.setActivitytype(row.getActivitytype());
        existing.setHowmuchduration(row.getHowmuchduration());
        existing.setCorrectiveaction(row.getCorrectiveaction());
        existing.setWhatactivity(row.getWhatactivity());
        existing.setWherelocation(row.getWherelocation());
        existing.setStandard(row.getStandard());
        existing.setWhyifnotdone(row.getWhyifnotdone());
        existing.setFrequencyunit(row.getFrequencyunit());
        existing.setFrequency(row.getFrequency());
        existing.setResponsibilityid(row.getResponsibilityid());
        existing.setResponsibilitydesgid(row.getResponsibilitydesgid());
        existing.setIstoolsreq(row.getIstoolsreq());
        existing.setHowmethod(row.getHowmethod());
        existing.setStartdate(row.getStartdate());
        existing.setStartweekno(row.getStartweekno());
        existing.setLastdonedate(row.getLastdonedate());
        existing.setLastweekno(row.getLastweekno());
        existing.setNextduedate(row.getNextduedate());
        existing.setNextdueweekno(row.getNextdueweekno());
        existing.setMonthweekno(row.getMonthweekno());
        existing.setPreparedbyid(row.getPreparedbyid());
        existing.setActive(row.getActive() != null ? row.getActive() : existing.getActive());
        existing.setModifiedon(LocalDateTime.now());

        CliTlStandards saved = cliTlStandardsRepository.save(existing);
        logger.info("Successfully updated CLI Standard with Key ID: {}", saved.getKeyid());
        return saved;
    }

   @Override
@Transactional
public ResponseEntity<Void> deleteCliTlStandards(String keyid) throws Exception {
    if (keyid == null || keyid.trim().isEmpty()) {
        throw new RuntimeException("keyid is required to delete CLI Standard");
    }
    if (!cliTlStandardsRepository.existsById(keyid)) {
        throw new ResourceNotFoundException("CLI Standard not found for keyid: " + keyid);
    }

    cliTlStandardsRepository.deleteById(keyid);
    logger.info("Deleted CLI Standard with Key ID: {}", keyid);

    return ResponseEntity.noContent().build();
}
}