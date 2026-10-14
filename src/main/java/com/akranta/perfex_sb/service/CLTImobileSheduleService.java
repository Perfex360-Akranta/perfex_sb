package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.ClitCalendarEnsureDto;
import com.akranta.perfex_sb.dto.ClitCalendarSaveDto;

public interface CLTImobileSheduleService {

    public String save(ClitCalendarSaveDto dto) throws Exception;
    public boolean ensureClitCalendar(ClitCalendarEnsureDto dto) throws Exception;
}