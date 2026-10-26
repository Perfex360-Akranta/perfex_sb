package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.BalWorespMstSaveDto;

public interface BalWorespService {
    BalWorespMstSaveDto create(BalWorespMstSaveDto dto) throws Exception;
    BalWorespMstSaveDto update(BalWorespMstSaveDto dto) throws Exception;
}