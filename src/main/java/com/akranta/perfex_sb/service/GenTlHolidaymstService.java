package com.akranta.perfex_sb.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.model.GenTlHolidaymst;

public interface GenTlHolidaymstService {
    ResponseEntity<GenTlHolidaymst> saveHoliday(GenTlHolidaymst request) throws Exception;
    GenTlHolidaymst getHoliday(String keyid);
    List<GenTlHolidaymst> getAllHolidays(String factory, String flag, Integer year);
    boolean deleteHoliday(String keyid);
}