package com.akranta.perfex_sb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.ClitCalendarSaveDto;
import com.akranta.perfex_sb.repository.CLTIWebScheduleRepository;
import com.akranta.perfex_sb.service.CLTIWebScheduleService;

@Service
public class CLTIWebScheduleServiceImpl implements CLTIWebScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(CLTIWebScheduleServiceImpl.class);

    private final CLTIWebScheduleRepository cltiWebScheduleRepository;

    public CLTIWebScheduleServiceImpl(CLTIWebScheduleRepository cltiWebScheduleRepository) {
        this.cltiWebScheduleRepository = cltiWebScheduleRepository;
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

        if (dto.getCellId() == null || dto.getCellId().trim().isEmpty()) {
            throw new IllegalArgumentException("cellId is required");
        }
        if (dto.getMachineId() == null || dto.getMachineId().trim().isEmpty()) {
            throw new IllegalArgumentException("machineId is required");
        }
        if (dto.getShiftId() == null || dto.getShiftId().trim().isEmpty()) {
            throw new IllegalArgumentException("shiftId is required");
        }

        int updatedRows = cltiWebScheduleRepository.save(
                dto.getActivity(),
                dto.getCellId(),
                dto.getMachineId(),
                dto.getShiftId(),
                createdBy,
                dto.getStatus(),
                dto.getObservation(),
                dto.getTagClass());

        logger.info("CLIT calendar (web) update — rows affected: {}", updatedRows);

        return updatedRows > 0 ? "success" : "no records updated";
    }
}