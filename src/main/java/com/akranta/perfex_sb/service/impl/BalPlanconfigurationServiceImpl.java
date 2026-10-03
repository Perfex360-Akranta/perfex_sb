
package com.akranta.perfex_sb.service.impl;

//import com.akranta.perfex_sb.exception.WorkOrderResponsibilityException;
import com.akranta.perfex_sb.model.BAL_PlmTlStandards;
import com.akranta.perfex_sb.model.BalPlanconfiguration;
import com.akranta.perfex_sb.model.BalPlmTlCalendar;
import com.akranta.perfex_sb.repository.BalPlanConfigurationProcedures;
import com.akranta.perfex_sb.repository.BalPlanconfigurationRepository;
import com.akranta.perfex_sb.repository.BalPlmTlCalendarRepository;
import com.akranta.perfex_sb.repository.BalPlmTlStandardsRepository;
import com.akranta.perfex_sb.repository.BalworespmstRepository;
import com.akranta.perfex_sb.repository.EntTlSkillIndexScoreRepo;
import com.akranta.perfex_sb.service.BalPlanconfigurationService;
import com.akranta.perfex_sb.service.DbActionTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
public class BalPlanconfigurationServiceImpl implements BalPlanconfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(BalPlanconfigurationServiceImpl.class);

    private final BalPlanconfigurationRepository planconfigurationRepository;
    private final BalPlmTlStandardsRepository standardsRepository;
    private final BalPlmTlCalendarRepository calendarRepository;
    private final BalworespmstRepository worespmstRepository;
    private final DbActionTemplate dbActionTemplate;
    private final DataSource dataSource;
    
    @Autowired
    private BalPlanConfigurationProcedures planConfigProcedures;

    private static final String SEQ_IDENTIFIER = "BAL_PLM_TL_PLANCONFIGURATION";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX = "PPC";
    private static final String DATE_FORMAT = "MMYY";

    public BalPlanconfigurationServiceImpl(
            BalPlanconfigurationRepository planconfigurationRepository,
            BalPlmTlStandardsRepository standardsRepository,
            BalPlmTlCalendarRepository calendarRepository,
            BalworespmstRepository worespmstRepository,
            DbActionTemplate dbActionTemplate,
            DataSource dataSource) {
        this.planconfigurationRepository = planconfigurationRepository;
        this.standardsRepository = standardsRepository;
        this.calendarRepository = calendarRepository;
        this.worespmstRepository = worespmstRepository;
        this.dbActionTemplate = dbActionTemplate;
        this.dataSource = dataSource;
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(BalPlanconfiguration planconfiguration) {

        boolean isUpdate = planconfiguration.getKeyid() != null
                && planconfigurationRepository.existsById(planconfiguration.getKeyid());

        if (isUpdate) {

            return ResponseEntity.ok(planconfigurationRepository.save(planconfiguration));
        }

        // delete the existing configuration for this machine at the given level.

        if ("A".equals(planconfiguration.getLevel())) {
            planconfigurationRepository.deleteAssemblyLevelConfig(planconfiguration.getMachineid());
        } else if ("M".equals(planconfiguration.getLevel())) {
            planconfigurationRepository.deleteMachineLevelConfig(planconfiguration.getMachineid());
        }
        logger.debug("Cleared existing plan configuration for machineid={}, level={}",
                planconfiguration.getMachineid(), planconfiguration.getLevel());

        // generate the new primary key, same prefix/format as the original DAO
        try {
            planconfiguration.setKeyid(
                    dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, null));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate sequence number for: " + SEQ_IDENTIFIER, e);
        }

        // insert the new master row
        BalPlanconfiguration saved = planconfigurationRepository.save(planconfiguration);
        logger.info("Created plan configuration with keyid={}", saved.getKeyid());

        // fetch the distinct frequency units configured for this machine in STANDARDS
        List<Object[]> frequencies;
        try {
            frequencies = standardsRepository.findDistinctFrequenciesByMachineid(saved.getMachineid());
        } catch (Exception e) {
            // Original DAO swallowed this exception silently too; logging instead of
            // swallowing it completely so failures aren't invisible.
            logger.warn("Could not fetch frequencies for machineid={} : {}", saved.getMachineid(), e.getMessage());
            frequencies = List.of();
        }

        // for each frequency unit, resolve which date field the user filled in,
        // then push that effective date into every matching STANDARDS row.
        for (Object[] row : frequencies) {
            Character freqUnit = (Character) row[0];
            Double freq = (Double) row[1];

            if (freqUnit == null) {
                continue;
            }

            LocalDateTime effectiveDate = resolveEffectiveDate(saved, freqUnit, freq);

            List<BAL_PlmTlStandards> matching = standardsRepository.findMatchingStandards(
                    saved.getMachineid(),
                    freqUnit,
                    isValid(saved.getAssemblyid()) ? saved.getAssemblyid() : null,
                    (freqUnit == 'Y' && freq != null) ? freq : null);

            for (BAL_PlmTlStandards standard : matching) {
                standard.setEffectivedate(effectiveDate);
                standard.setMonthweekno(saved.getWeekno());
                standard.setModifiedon(LocalDateTime.now());
                standard.setPlanconfigstatus("Y");
            }
            standardsRepository.saveAll(matching);
        }

        // deactivate previously generated calendar entries for this machine,
        // same filters as the original UPDATE CALENDAR statement.
        // List<BalPlmTlCalendar> activeEntries = calendarRepository.findActiveEntriesToDeactivate(
        //         saved.getMachineid(),
        //         isValid(saved.getAssemblyid()) ? saved.getAssemblyid() : null);

        // for (BalPlmTlCalendar entry : activeEntries) {
        //     entry.setActive("N");
        //     entry.setModifiedon(LocalDateTime.now());
        // }
        // calendarRepository.saveAll(activeEntries);
        
        //Swetha CHanged - here-18-Sep
        
        int updatedCalendarCount = calendarRepository.deactivateCalendar(saved.getMachineid(), saved.getAssemblyid());

         logger.info("Updated Calendar count {}",updatedCalendarCount);

        // regenerate the PM calendar, then validate a Work Order Responsibility

        try {
            boolean standardsExist = standardsRepository.existsByMachineid(saved.getMachineid());
            if (standardsExist && saved.getYearly() != null) {

                String year = String.valueOf(saved.getYearly().getYear());
                String fromMonth = saved.getYearly().getMonth()
                        .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                        .toUpperCase();

                logger.debug(
                        "Generating PM calendar: year={}, factoryId={}, sectionId={}, cellId={}, machineId={}, assemblyId={}, frequency={}, fromMonth={}",
                        year,
                        saved.getFactoryid(),
                        saved.getSectionid(),
                        saved.getCellid(),
                        saved.getMachineid(),
                        saved.getAssemblyid(),
                        saved.getFrequency(),
                        fromMonth);

                generateCalendar(
                        year,
                        saved.getFactoryid(),
                        saved.getSectionid(),
                        saved.getCellid(),
                        saved.getMachineid(),
                        saved.getAssemblyid(),
                        saved.getFrequency(),
                        fromMonth);
            }

            long woRespCount = worespmstRepository.countByPwrmmachineid(saved.getMachineid());

            if (woRespCount == 0) {
                return ResponseEntity
                        .status(HttpStatus.METHOD_NOT_ALLOWED).body("");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while regenerating calendar / validating work order responsibility: {}",
                    e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }

        return ResponseEntity.ok(saved);
    }

   

    private LocalDateTime resolveEffectiveDate(BalPlanconfiguration master, char freqUnit, Double freq) {
        if (freqUnit == 'W' || freqUnit == 'F' || freqUnit == 'M') {
            return master.getMonthly();
        } else if (freqUnit == 'Q') {
            return master.getQuarterly();
        } else if (freqUnit == 'H') {
            return master.getHalfyearly();
        } else if (freqUnit == 'Y' && freq != null) {
            switch (freq.intValue()) {
                case 2:
                    return master.getYearly2();
                case 3:
                    return master.getYearly3();
                case 4:
                    return master.getYearly4();
                case 5:
                    return master.getYearly5();
                case 6:
                    return master.getYearly6();
                case 7:
                    return master.getYearly7();
                case 8:
                    return master.getYearly8();
                case 9:
                    return master.getYearly9();
                case 10:
                    return master.getYearly10();
                default:
                    return master.getYearly();
            }
        }

        return master.getMonthly();
    }

    private boolean isValid(String value) {
        return value != null && !value.trim().isEmpty() && !value.equals("{}");
    }

    private void generateCalendar(String year, String factoryId, String sectionId, String cellId,
            String machineId, String assemblyId, String frequency, String fromMonth)
            throws Exception {
        logger.debug("Generating PM calendar for machineId={}, year={}, fromMonth={}", machineId, year, fromMonth);

        try {
            //Swetha Changed 
           planConfigProcedures.generateCalendar(year, factoryId, sectionId, cellId, machineId, assemblyId, frequency, fromMonth);

        } catch (Exception sqlException) {
            logger.error("SQL Exception while generating calendar: {}", sqlException.getMessage(), sqlException);
            throw new Exception("Calendar Generation SQL Error: " + sqlException.getMessage());
        } finally {

        }
    }
}