package com.akranta.perfex_sb.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.ClitCalendarEnsureDto;
import com.akranta.perfex_sb.dto.ClitCalendarSaveDto;
import com.akranta.perfex_sb.repository.CLTImobileSheduleRepository;
import com.akranta.perfex_sb.service.CLTImobileSheduleService;

@Service
public class CLTImobileSheduleServiceImpl implements CLTImobileSheduleService {

    private static final Logger logger = LoggerFactory.getLogger(CLTImobileSheduleServiceImpl.class);

    private final CLTImobileSheduleRepository cltiMobileSheduleRepository;

    public CLTImobileSheduleServiceImpl(CLTImobileSheduleRepository cltiMobileSheduleRepository) {
        this.cltiMobileSheduleRepository = cltiMobileSheduleRepository;
    }

    @Override
    @Transactional
    public String save(ClitCalendarSaveDto dto) throws Exception {

        if (dto == null || dto.getActivity() == null || dto.getActivity().isEmpty()) {
            throw new IllegalArgumentException("At least one activity id is required");
        }

        String createdBy = dto.getCreatedBy();

        if (createdBy == null || createdBy.trim().isEmpty()) {
            throw new IllegalArgumentException("createdBy is required");
        }

        int updatedRows = cltiMobileSheduleRepository.save(
                dto.getActivity(),
                dto.getShiftId(),
                createdBy,
                dto.getStatus(),
                dto.getObservation(),
                dto.getTagClass());

        logger.info("CLIT calendar update — rows affected: {}", updatedRows);

        return updatedRows > 0 ? "success" : "no records updated";
    }

    @Override
    @Transactional
    public boolean ensureClitCalendar(ClitCalendarEnsureDto dto) throws Exception {

        if (dto == null
                || isBlank(dto.getFactoryId()) || isBlank(dto.getSectionId())
                || isBlank(dto.getCellId()) || isBlank(dto.getMachineId())) {
            throw new IllegalArgumentException("factoryId, sectionId, cellId and machineId are required");
        }

        String monthYear = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM-yyyy", Locale.ENGLISH)); // Sep-2026
        String fromDate = "01-" + monthYear; // 01-Sep-2026

        // same as web: checkMachineExist
        boolean checkMachineExist = cltiMobileSheduleRepository.calendarExists(
                monthYear, dto.getFactoryId(), dto.getSectionId(), dto.getCellId(), dto.getMachineId());
        logger.info("checkMachineExist : {}", checkMachineExist);

        if (!checkMachineExist) {
            cltiMobileSheduleRepository.runCalendarProcedure(
                    fromDate, dto.getFactoryId(), dto.getSectionId(), dto.getCellId(), dto.getMachineId());
            logger.info("CLIT calendar generated for machineId={}, month={}", dto.getMachineId(), monthYear);
        }

        return checkMachineExist;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}