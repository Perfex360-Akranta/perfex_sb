package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.dto.BestKaizenRecallDto;
import com.akranta.perfex_sb.dto.BestKaizenmsdtlDto;
import com.akranta.perfex_sb.model.KznTlBestmst;

public interface BestKaizenService {
    BestKaizenmsdtlDto saveBestKaizen(BestKaizenmsdtlDto dto) throws Exception;

    List<Map<String, Object>> selectData(BestKaizenRecallDto dto);

    KznTlBestmst getById(String keyId) throws Exception;

    public void deleteBestKaizen(String keyId) throws Exception;

}
