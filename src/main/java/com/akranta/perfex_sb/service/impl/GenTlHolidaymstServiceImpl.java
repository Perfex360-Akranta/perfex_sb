package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.GenTlHolidaymst;
import com.akranta.perfex_sb.repository.GenTlHolidaymstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.GenTlHolidaymstService;

@Service
public class GenTlHolidaymstServiceImpl implements GenTlHolidaymstService {

    private static final Logger logger = LoggerFactory.getLogger(GenTlHolidaymstServiceImpl.class);

    private final GenTlHolidaymstRepository holidaymstRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "GEN_TL_HOLIDAYMST";
    private static final int KEY_LENGTH = 9;
    private static final String PREFIX = "HOL";
    private static final String DATE_FORMAT = "";
    private static final String FORMAT_RESET = "";

    public GenTlHolidaymstServiceImpl(
        GenTlHolidaymstRepository holidaymstRepository,
        DbActionTemplate dbActionTemplate) {
        this.holidaymstRepository = holidaymstRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
@Transactional
public ResponseEntity<GenTlHolidaymst> saveHoliday(GenTlHolidaymst request) throws Exception {

    if (request == null) {
        throw new RuntimeException("No Holiday Details");
    }

    // ---- VALIDATION (do this before touching the DB) ----
    if (request.getName() == null || request.getName().trim().isEmpty()) {
        throw new RuntimeException("Holiday Name is required");
    }
    if (request.getDate() == null) {
        throw new RuntimeException("Holiday Date is required");
    }
    if (request.getFactory() == null || request.getFactory().trim().isEmpty()) {
        throw new RuntimeException("Unit/Factory is required");
    }

    try {
        if (request.getKeyid() == null || request.getKeyid().trim().isEmpty()) {
            // ---- INSERT MODE ----
            String newKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET
            );

            if (newKeyid == null || newKeyid.trim().isEmpty()) {
                logger.error("Failed to generate the Holiday Key ID");
                throw new RuntimeException("Failed to generate Holiday Key ID");
            }

            request.setKeyid(newKeyid);
            request.setCreatedon(LocalDateTime.now());
            request.setModifiedon(LocalDateTime.now());

            // audit fix: createdby was never being set before
            if (request.getCreatedby() == null || request.getCreatedby().trim().isEmpty()) {
                request.setCreatedby("SYSTEM"); // replace with logged-in user id from context
            }

            // sensible defaults instead of letting nulls hit the DB
            if (request.getHolidayflag() == null || request.getHolidayflag().trim().isEmpty()) {
                request.setHolidayflag("W");
            }
            if (request.getDescription() == null) {
                request.setDescription("");
            }

            GenTlHolidaymst savedHoliday = holidaymstRepository.save(request);
            logger.info("Successfully created Holiday with Key ID: {}", newKeyid);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedHoliday);

        } else {
            // ---- UPDATE MODE ----
            GenTlHolidaymst existingHoliday = holidaymstRepository.findById(request.getKeyid())
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found for keyid: " + request.getKeyid()));

            existingHoliday.setName(request.getName());
            existingHoliday.setDate(request.getDate());
            existingHoliday.setDescription(request.getDescription());
            existingHoliday.setFactory(request.getFactory());
            existingHoliday.setHolidayflag(request.getHolidayflag());
            // createdby / createdon deliberately untouched on update — preserve original audit values
            existingHoliday.setModifiedon(LocalDateTime.now());

            GenTlHolidaymst updatedHoliday = holidaymstRepository.save(existingHoliday);
            logger.info("Successfully updated Holiday with Key ID: {}", updatedHoliday.getKeyid());

            return ResponseEntity.status(HttpStatus.OK).body(updatedHoliday);
        }
    } catch (ResourceNotFoundException e) {
        throw e; // let the controller's own NOT_FOUND handling deal with it
    } catch (Exception e) {
        // catches DB-level failures (e.g. bad date/format hitting Postgres) with a clear message
        // instead of a raw stack trace like the one in your log
        logger.error("Failed to save Holiday: {}", e.getMessage(), e);
        throw new RuntimeException("Failed to save Holiday: " + e.getMessage(), e);
    }
}

    @Override
    @Transactional(readOnly = true)
    public GenTlHolidaymst getHoliday(String keyid) {
        logger.info("Fetching Holiday for Key ID: {}", keyid);
        return holidaymstRepository.findById(keyid)
            .orElseThrow(() -> new ResourceNotFoundException("Holiday not found for keyid: " + keyid));
    }

   @Override
@Transactional(readOnly = true)
public List<GenTlHolidaymst> getAllHolidays(String factory, String flag, Integer year) {
    if (factory != null && !factory.trim().isEmpty() && year != null) {
        java.time.LocalDateTime startDate = java.time.LocalDate.of(year, 1, 1).atStartOfDay();
        java.time.LocalDateTime endDate = java.time.LocalDate.of(year + 1, 1, 1).atStartOfDay();
        return holidaymstRepository.findByYearFlagAndFactory(startDate, endDate, flag, factory);
    } else if (year != null) {
        java.time.LocalDateTime startDate = java.time.LocalDate.of(year, 1, 1).atStartOfDay();
        java.time.LocalDateTime endDate = java.time.LocalDate.of(year + 1, 1, 1).atStartOfDay();
        return holidaymstRepository.findByYearAndFlag(startDate, endDate, flag);
    } else if (factory != null && !factory.trim().isEmpty()) {
        return holidaymstRepository.findByFactoryAndHolidayflag(factory, flag);
    }
    return holidaymstRepository.findAll();
}

    @Override
    @Transactional
    public boolean deleteHoliday(String keyid) {
        GenTlHolidaymst existingHoliday = holidaymstRepository.findById(keyid)
            .orElseThrow(() -> new ResourceNotFoundException("Holiday not found for keyid: " + keyid));
        holidaymstRepository.delete(existingHoliday);
        logger.info("Successfully deleted Holiday with Key ID: {}", keyid);
        return true;
    }
}