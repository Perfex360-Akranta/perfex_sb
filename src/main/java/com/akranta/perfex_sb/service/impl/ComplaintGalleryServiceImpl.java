package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.QtmTlComplaintgallery;
import com.akranta.perfex_sb.repository.QtmTlComplaintgalleryRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.ComplaintGalleryService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.ComplaintGalleryRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ComplaintGalleryServiceImpl implements ComplaintGalleryService {

    private static final Logger logger = LoggerFactory.getLogger(ComplaintGalleryServiceImpl.class);
    
    private final QtmTlComplaintgalleryRepository complaintGalleryRepository;
    private final DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "QTM_TL_COMPLAINTGALLERY";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "CMGA";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    public ComplaintGalleryServiceImpl(
        QtmTlComplaintgalleryRepository complaintGalleryRepository,
        DbActionTemplate dbActionTemplate) {
        this.complaintGalleryRepository = complaintGalleryRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<ComplaintGalleryRequest> saveComplaintGallery(ComplaintGalleryRequest request) throws Exception {
        QtmTlComplaintgallery complaintGallery = request.getComplaintGallery();

        if (complaintGallery == null) {
            throw new RuntimeException("No Complaint Gallery Details provided");
        }

        ComplaintGalleryRequest result = new ComplaintGalleryRequest();

        // CHECK IF INSERT OR UPDATE
        if (complaintGallery.getKeyid() == null || complaintGallery.getKeyid().trim().isEmpty()) {
            // ========== INSERT MODE ==========
            String newKeyid = dbActionTemplate.getSequenceNumber(
                SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET
            );

            if (newKeyid == null || newKeyid.trim().isEmpty()) {
                logger.error("Failed to generate the Complaint Gallery Key ID");
                throw new RuntimeException("Failed to generate Complaint Gallery Key ID");
            }

            complaintGallery.setKeyid(newKeyid);
            
            if (complaintGallery.getCreatedon() == null) {
                complaintGallery.setCreatedon(LocalDateTime.now());
            }
            complaintGallery.setModifiedon(LocalDateTime.now());

            logger.info("Generated new Complaint Gallery Key ID: {}", newKeyid);

            // Save Complaint Gallery
            QtmTlComplaintgallery savedComplaintGallery = complaintGalleryRepository.save(complaintGallery);

            result.setComplaintGallery(savedComplaintGallery);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);

        } else {
            // ========== UPDATE MODE ==========
            QtmTlComplaintgallery existingComplaintGallery = complaintGalleryRepository.findByKeyid(complaintGallery.getKeyid());
            
            if (existingComplaintGallery == null) {
                throw new ResourceNotFoundException("Complaint Gallery not found with keyid: " + complaintGallery.getKeyid());
            }

            // Update all fields
            existingComplaintGallery.setCustomerid(complaintGallery.getCustomerid());
            existingComplaintGallery.setGradeproduct(complaintGallery.getGradeproduct());
            existingComplaintGallery.setCorrectiveaction(complaintGallery.getCorrectiveaction());
            existingComplaintGallery.setPreventiveaction(complaintGallery.getPreventiveaction());
            existingComplaintGallery.setComplaintdescription(complaintGallery.getComplaintdescription());
            existingComplaintGallery.setComplaintdate(complaintGallery.getComplaintdate());
            existingComplaintGallery.setManufacturedate(complaintGallery.getManufacturedate());
            existingComplaintGallery.setGradespecification(complaintGallery.getGradespecification());
            existingComplaintGallery.setFlid(complaintGallery.getFlid());
            existingComplaintGallery.setElementid(complaintGallery.getElementid());
            existingComplaintGallery.setSource(complaintGallery.getSource());
            existingComplaintGallery.setDefectid(complaintGallery.getDefectid());
            existingComplaintGallery.setDefectqty(complaintGallery.getDefectqty());
            existingComplaintGallery.setTempfield1(complaintGallery.getTempfield1());
            existingComplaintGallery.setTempfield2(complaintGallery.getTempfield2());
            existingComplaintGallery.setTempfield3(complaintGallery.getTempfield3());
            existingComplaintGallery.setActive(complaintGallery.getActive());
            existingComplaintGallery.setModifiedon(LocalDateTime.now());

            QtmTlComplaintgallery updatedComplaintGallery = complaintGalleryRepository.save(existingComplaintGallery);
            logger.info("Successfully updated Complaint Gallery with Key ID: {}", updatedComplaintGallery.getKeyid());

            result.setComplaintGallery(updatedComplaintGallery);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ComplaintGalleryRequest getComplaintGalleryData(String keyid) {
        logger.info("Fetching Complaint Gallery data for Key ID: {}", keyid);

        // Validate input
        if (keyid == null || keyid.trim().isEmpty()) {
            throw new IllegalArgumentException("Complaint Gallery Key ID cannot be null or empty");
        }

        // Fetch complaint gallery using native query
        QtmTlComplaintgallery complaintGallery = complaintGalleryRepository.findByKeyid(keyid);
        
        if (complaintGallery == null) {
            throw new ResourceNotFoundException(
                "Complaint Gallery not found for keyid: " + keyid);
        }
        
        logger.info("Successfully retrieved Complaint Gallery record");
        
        ComplaintGalleryRequest response = new ComplaintGalleryRequest(complaintGallery);
        
        return response;
    }

    @Override
@Transactional
public boolean deleteComplaintGallery(String keyid) throws Exception {
    logger.info("Deleting Complaint Gallery record with keyid: {}", keyid);
    
    if (keyid == null || keyid.trim().isEmpty()) {
        throw new IllegalArgumentException("Key ID cannot be null or empty");
    }
    
    // Check if record exists
    QtmTlComplaintgallery existingRecord = complaintGalleryRepository.findByKeyid(keyid);
    if (existingRecord == null) {
        logger.warn("Complaint Gallery record not found with keyid: {}", keyid);
        throw new ResourceNotFoundException("Complaint Gallery record not found with keyid: " + keyid);
    }
    
    int rowsAffected = complaintGalleryRepository.deleteComplaintGallery(keyid);
    
    if (rowsAffected == 0) {
        logger.error("Failed to delete Complaint Gallery record with keyid: {}", keyid);
        throw new RuntimeException("Failed to delete Complaint Gallery record");
    }
    
    logger.info("Successfully deleted Complaint Gallery record with keyid: {}. Rows affected: {}", keyid, rowsAffected);
    return true;
}
}