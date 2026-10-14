package com.akranta.perfex_sb.service;

import java.util.List;

import com.akranta.perfex_sb.dto.GenmaintenanceBookingMobileDTO;
import com.akranta.perfex_sb.dto.GenmaintenanceCompletionMobileDTO;
import com.akranta.perfex_sb.model.PlmTlGenmaintenance;

public interface PlmTlGenmaintenanceService {

    // Handles BOTH insert and update — keyid null/empty => insert, keyid present => update
    PlmTlGenmaintenance saveGeneralmainteneance(PlmTlGenmaintenance plmTlGenmaintenance) throws Exception;

    PlmTlGenmaintenance saveGeneralmaintenanceBookingMobile(GenmaintenanceBookingMobileDTO request) throws Exception;

    PlmTlGenmaintenance saveGeneralmaintenanceCompletionMobile(GenmaintenanceCompletionMobileDTO request) throws Exception;

    PlmTlGenmaintenance getById(String keyid) throws Exception;

    List<PlmTlGenmaintenance> getAll() throws Exception;

    boolean deleteById(String keyid) throws Exception;

}