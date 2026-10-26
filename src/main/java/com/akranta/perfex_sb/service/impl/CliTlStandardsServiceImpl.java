package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.BAL_CliStandardsDtl;
import com.akranta.perfex_sb.model.CliTlStandards;
import com.akranta.perfex_sb.repository.CliStandardsDtlRepository;
import com.akranta.perfex_sb.repository.CliTlStandardsRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.CliTlStandardsService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.CliTlStandardsRequest;
import com.akranta.perfex_sb.dto.CliStandardsDtlRequest;
import com.akranta.perfex_sb.repository.CliTlStandardsProcedures;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.LocalDate;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CliTlStandardsServiceImpl implements CliTlStandardsService {

    private static final Logger logger = LoggerFactory.getLogger(CliTlStandardsServiceImpl.class);

    private final CliTlStandardsRepository cliTlStandardsRepository;
    private final CliStandardsDtlRepository cliStandardsDtlRepository;
    private final CliTlStandardsProcedures cliTlStandardsProcedures;

    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "CLI_TL_STANDARDS";
    private static final int KEY_LENGTH = 12;
    private static final String PREFIX = "CL";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    // Detail (how-icon) key settings - same values as legacy saveClitHowIcon
    private static final String DTL_SEQ_IDENTIFIER = "BAL_CLI_TL_STANDARDS_DTL";
    private static final int DTL_KEY_LENGTH = 11;
    private static final String DTL_PREFIX = "CLD";

    public CliTlStandardsServiceImpl(
            CliTlStandardsRepository cliTlStandardsRepository,
            CliStandardsDtlRepository cliStandardsDtlRepository,
             CliTlStandardsProcedures cliTlStandardsProcedures,
            DbActionTemplate dbActionTemplate) {
        this.cliTlStandardsRepository = cliTlStandardsRepository;
        this.cliStandardsDtlRepository = cliStandardsDtlRepository;
        this.cliTlStandardsProcedures = cliTlStandardsProcedures;
        this.dbActionTemplate = dbActionTemplate;
    }

    // @Override
    // @Transactional
    // public ResponseEntity<CliTlStandardsRequest> saveCliTlStandards(CliTlStandardsRequest request) throws Exception {

    //     List<CliTlStandards> details = request.getDetails();
    //     if (details == null || details.isEmpty()) {
    //         throw new RuntimeException("No CLI Standard rows to save");
    //     }

    //     CliTlStandardsRequest result = new CliTlStandardsRequest();
    //     List<CliTlStandards> savedList = new ArrayList<>();

    //     for (CliTlStandards row : details) {

    //         // Only overwrite with the DTO's header-level value when it's actually
    //         // present (multi-row screen always sends these). Otherwise keep
    //         // whatever the row already carries (single-row save path).
    //         row.setFactoryid(request.getFactoryId() != null ? request.getFactoryId() : row.getFactoryid());
    //         row.setSectionid(request.getSectionId() != null ? request.getSectionId() : row.getSectionid());
    //         row.setCellid(request.getCellId() != null ? request.getCellId() : row.getCellid());
    //         row.setMachineid(request.getMachineId() != null ? request.getMachineId() : row.getMachineid());
    //         row.setFlid(request.getFlId() != null ? request.getFlId() : row.getFlid());
    //         row.setElementid(request.getElementId() != null ? request.getElementId() : row.getElementid());

    //         String keyid = row.getKeyid();
    //         boolean isInsertMode = keyid == null ||
    //                 keyid.trim().isEmpty() ||
    //                 keyid.equals("{}") ||
    //                 keyid.equals("undefined") ||
    //                 !cliTlStandardsRepository.existsById(keyid);

    //         if (isInsertMode) {
    //             savedList.add(insertRow(row));
    //         } else {
    //             savedList.add(updateRow(row));
    //         }
    //     }

    //     result.setDetails(savedList);
    //     result.setFactoryId(request.getFactoryId());
    //     result.setSectionId(request.getSectionId());
    //     result.setCellId(request.getCellId());
    //     result.setMachineId(request.getMachineId());
    //     result.setFlId(request.getFlId());
    //     result.setElementId(request.getElementId());
    //     result.setFormActionMode(request.getFormActionMode());
    //     result.setFormMode(request.getFormMode());
    //     result.setFormHeader(request.getFormHeader());

    //     return ResponseEntity.status(HttpStatus.OK).body(result);
    // }
    //mano
    @Override
@Transactional
public ResponseEntity<CliTlStandardsRequest> saveCliTlStandards(CliTlStandardsRequest request) throws Exception {

    logger.info("saveCliTlStandards v2 - calendar call enabled");

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

        CliTlStandards saved;
        try {
            if (isInsertMode) {
                saved = insertRow(row);
            } else {
                saved = updateRow(row);
            }
            logger.info("CLI Standard row saved: keyid={}", saved.getKeyid());

            generateCalendarForStandard(saved);

            savedList.add(saved);
        } catch (Exception e) {
            logger.error("Saving CLI Standard row failed (insertMode={}): {}", isInsertMode, e.getMessage(), e);
            throw e;
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
    //mano
  private void generateCalendarForStandard(CliTlStandards saved) throws Exception {

    logger.info("generateCalendarForStandard: keyid={}, startdate={}", saved.getKeyid(), saved.getStartdate());

    String startDate = formatStartDate(saved.getStartdate());
    if (startDate == null) {
        logger.warn("Skipping calendar generation for CLI Standard {} - start date missing", saved.getKeyid());
        return;
    }

    try {
        logger.info("Generating CLI calendar: keyid={}, startDate={}, checkForUpdate=Y",
                saved.getKeyid(), startDate);

        String retval = cliTlStandardsProcedures.generateCalendar(saved.getKeyid(), startDate, "Y");

        logger.info("CLI calendar generated for standard {} (retval={})", saved.getKeyid(), retval);

    } catch (Exception e) {
        logger.error("CLI calendar generation failed for standard {}: {}", saved.getKeyid(), e.getMessage(), e);
        throw new Exception("Calendar Generation SQL Error: " + e.getMessage(), e);
    }
}


private String formatStartDate(Object startdate) {
    if (startdate == null) {
        return null;
    }
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    if (startdate instanceof LocalDateTime) {
        return ((LocalDateTime) startdate).toLocalDate().format(fmt);
    }
    if (startdate instanceof LocalDate) {
        return ((LocalDate) startdate).format(fmt);
    }
    if (startdate instanceof java.util.Date) {
        return new java.sql.Date(((java.util.Date) startdate).getTime()).toLocalDate().format(fmt);
    }
    String s = startdate.toString().trim();
    return s.isEmpty() ? null : s;   // a String is passed through unchanged
}
           @Override
    @Transactional
    public ResponseEntity<Void> deleteCliTlStandards(String keyid) throws Exception {
        if (keyid == null || keyid.trim().isEmpty()) {
            throw new RuntimeException("keyid is required to delete CLI Standard");
        }
        keyid = keyid.trim();

        if (!cliTlStandardsRepository.existsById(keyid)) {
            throw new ResourceNotFoundException("CLI Standard not found for keyid: " + keyid);
        }

        // 1. Delete calendar rows that reference this standard (fk_clca_clirefid)
        int calendarRows = cliTlStandardsRepository.deleteCalendarByStandardKeyid(keyid);
        logger.info("Deleted {} calendar row(s) for CLI Standard Key ID: {}", calendarRows, keyid);

        // 2. Delete the detail row (BAL_CLI_TL_STANDARDS_DTL), if one exists
        BAL_CliStandardsDtl dtl = cliStandardsDtlRepository.findByStandardsKeyid(keyid);
        if (dtl != null) {
            cliStandardsDtlRepository.delete(dtl);
            logger.info("Deleted CLI Standard detail {} for CLI Standard Key ID: {}", dtl.getKeyid(), keyid);
        }

        // 3. Then delete the parent standard
        cliTlStandardsRepository.deleteById(keyid);
        logger.info("Deleted CLI Standard with Key ID: {}", keyid);

        return ResponseEntity.noContent().build();
    }
    
       // ------------------------------------------------------------------
    // Detail save: BAL_CLI_TL_STANDARDS_DTL (replaces legacy saveClitHowIcon)
    // ------------------------------------------------------------------
    @Override
    @Transactional
    public ResponseEntity<BAL_CliStandardsDtl> saveClitHowIcon(CliStandardsDtlRequest request) throws Exception {

        String howIcon = request.getHowIcon();
        String clisKeyid = request.getClisKeyid();

        if (howIcon == null || howIcon.trim().isEmpty()) {
            return ResponseEntity.noContent().build(); // nothing selected on this row
        }
        if (clisKeyid == null || clisKeyid.trim().isEmpty()) {
            return ResponseEntity.noContent().build(); // safety guard
        }

        howIcon = howIcon.trim();
        clisKeyid = clisKeyid.trim();
        String userId = request.getUserId() == null ? "" : request.getUserId().trim();

        if (!cliTlStandardsRepository.existsById(clisKeyid)) {
            throw new ResourceNotFoundException("CLI Standard not found for keyid: " + clisKeyid);
        }

        BAL_CliStandardsDtl dtl = cliStandardsDtlRepository.findByStandardsKeyid(clisKeyid);

        if (dtl != null) {
            // UPDATE
            dtl.setHowicn(howIcon);
            dtl.setModifiedon(LocalDateTime.now());
            logger.info("Updating how-icon for CLI Standard Key ID: {}", clisKeyid);
        } else {
            // INSERT - generate key the same way every other module does
            String newDtlKeyid = dbActionTemplate.getSequenceNumber(
                    DTL_SEQ_IDENTIFIER, DTL_KEY_LENGTH, DTL_PREFIX, DATE_FORMAT, FORMAT_RESET);

            if (newDtlKeyid == null || newDtlKeyid.trim().isEmpty()) {
                logger.error("Failed to generate the CLI Standard Detail Key ID");
                throw new RuntimeException("Failed to generate CLI Standard Detail Key ID");
            }

            dtl = new BAL_CliStandardsDtl();
            dtl.setKeyid(newDtlKeyid);
            dtl.setStandardsKeyid(clisKeyid);
            dtl.setHowicn(howIcon);
            dtl.setCreatedby(userId);
            dtl.setCreatedon(LocalDateTime.now());
            dtl.setModifiedon(LocalDateTime.now());
            logger.info("Inserting how-icon detail {} for CLI Standard Key ID: {}", newDtlKeyid, clisKeyid);
        }

        BAL_CliStandardsDtl saved = cliStandardsDtlRepository.save(dtl);

        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }
}