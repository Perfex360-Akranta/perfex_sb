package com.akranta.perfex_sb.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.model.GenTlOtgCapture;
import com.akranta.perfex_sb.repository.GenTlOtgCaptureRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.OtgCaptureService;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.transaction.Transactional;

@Service
public class OtgCaptureServiceImpl implements OtgCaptureService {

    private static final Logger logger =
            LoggerFactory.getLogger(OtgCaptureServiceImpl.class);

    @Autowired
    private GenTlOtgCaptureRepository otgRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "OTG";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    @Override
    @Transactional
    public ResponseEntity<GenTlOtgCapture> save(
            GenTlOtgCapture otgCapture) {

        try {
            if (otgCapture == null) {
                return ResponseEntity.badRequest().build();
            }

            boolean isNew =
                    !ValidationUtil.isValidKeyId(otgCapture.getKeyid());

            if (isNew) {
                if (otgCapture.getCreatedby() == null
                        || otgCapture.getCreatedby().isBlank()) {
                    return ResponseEntity.badRequest().build();
                }

                String newKeyId = dbActionTemplate.getSequenceNumber(
                        "GEN_TL_OTGCAPTURE",
                        KEY_LENGTH,
                        PREFIX,
                        DATE_FORMAT,
                        FORMAT_RESET);

                otgCapture.setKeyid(newKeyId);

                GenTlOtgCapture saved =
                        otgRepository.save(otgCapture);

                logger.info(
                        "OTG Capture created successfully. Key ID: {}",
                        saved.getKeyid());

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(saved);
            }

            // Update only when the record already exists.
            GenTlOtgCapture existing = otgRepository.findById(
                    otgCapture.getKeyid()).orElse(null);

            if (existing == null) {
                return ResponseEntity.notFound().build();
            }

            // Update editable fields while preserving creation details.
            existing.setTitle(otgCapture.getTitle());
            existing.setDescription(otgCapture.getDescription());
            existing.setRemarks(otgCapture.getRemarks());
            existing.setFlid(otgCapture.getFlid());
            existing.setEmpmKeyid(otgCapture.getEmpmKeyid());
            existing.setEntrydate(otgCapture.getEntrydate());
            existing.setPriority(otgCapture.getPriority());
            existing.setStatus(otgCapture.getStatus());
            existing.setType(otgCapture.getType());

            existing.setTempfield1(otgCapture.getTempfield1());
            existing.setTempfield2(otgCapture.getTempfield2());
            existing.setTempfield3(otgCapture.getTempfield3());
            existing.setTempfield4(otgCapture.getTempfield4());
            existing.setTempfield5(otgCapture.getTempfield5());
            existing.setTempfield6(otgCapture.getTempfield6());
            existing.setTempfield7(otgCapture.getTempfield7());
            existing.setTempfield8(otgCapture.getTempfield8());
            existing.setTempfield9(otgCapture.getTempfield9());
            existing.setTempfield10(otgCapture.getTempfield10());

            existing.setModifiedby(otgCapture.getModifiedby());

            GenTlOtgCapture updated =
                    otgRepository.save(existing);

            logger.info(
                    "OTG Capture updated successfully. Key ID: {}",
                    updated.getKeyid());

            return ResponseEntity.ok(updated);

        } catch (Exception e) {
            logger.error("Error saving OTG Capture", e);

            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public GenTlOtgCapture findByKeyid(String keyid) {
        return otgRepository.findById(keyid).orElse(null);
    }

    @Override
    public List<GenTlOtgCapture> findAll() {
        return otgRepository.findAll();
    }

    @Override
    public List<GenTlOtgCapture> findByFlid(String flid) {
        return otgRepository.findByFlid(flid);
    }

    @Override
    public List<GenTlOtgCapture> findByStatus(String status) {
        return otgRepository.findByStatus(status);
    }

    @Override
    public Map<String, Object> findOtgCaptureDetails(String keyid) {
        return otgRepository.findOtgCaptureDetails(keyid);
    }

    @Override
    @Transactional
    public void delete(String keyid) {
        if (!otgRepository.existsById(keyid)) {
            throw new IllegalArgumentException(
                    "OTG Capture not found: " + keyid);
        }

        otgRepository.deleteById(keyid);

        logger.info("OTG Capture deleted. Key ID: {}", keyid);
    }
}