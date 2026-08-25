package com.akranta.perfex_sb.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.akranta.perfex_sb.controller.AbnTlAbnormalityController;
import com.akranta.perfex_sb.dto.AbnAllocationDto;
import com.akranta.perfex_sb.dto.AbnCompletionDto;
import com.akranta.perfex_sb.model.AbnTlAbnormality;
import com.akranta.perfex_sb.repository.AbnTlAbnormalityRepository;
import com.akranta.perfex_sb.util.Constants;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AbnTlAbnormalityService {

    private static final Logger logger = LoggerFactory.getLogger(AbnTlAbnormalityController.class);


    @Autowired
    private AbnTlAbnormalityRepository repository;


     @Autowired
     private DbActionTemplate dbActionTemplate;

    //  private static final String SEQ_IDENTIFIER = "ABN_TL_ABNORMALITYLCN0000001";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "ABB";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    public ResponseEntity<AbnTlAbnormality> create(@RequestBody AbnTlAbnormality abnTlAbnormality) {
        try {
            String elementId = abnTlAbnormality.getElementid();
            String location = null;
	 	    String seqIdentfr = "ABN_TL_ABNORMALITY";

	 	if( elementId != null && elementId.length() > 10  ){
	 		location = elementId.substring(11, 21); /* location id starts from 11  */
	 		seqIdentfr += location;
	 	}

            // Check if keyId is null or empty
            if (!ValidationUtil.isValidKeyId(abnTlAbnormality.getKeyid())) {
                // Generate new keyId using sequence function
                String newKeyId = dbActionTemplate.getSequenceNumber(seqIdentfr,KEY_LENGTH,PREFIX,DATE_FORMAT,FORMAT_RESET);
                abnTlAbnormality.setKeyid(newKeyId);
                logger.info("Generated new Key ID: {} for abnormality", newKeyId);
            } else {
                // Validate if the provided keyId already exists
                if (repository.existsById(abnTlAbnormality.getKeyid())) {
                    repository.insertABNHISTORY(abnTlAbnormality.getKeyid());
                    AbnTlAbnormality updateEntity = repository.save(abnTlAbnormality);
                    logger.info("Successfully updated abnormality with Key ID: {}", updateEntity.getKeyid());

                    return ResponseEntity.status(HttpStatus.OK).body(updateEntity);
                }else{
                     new RuntimeException("Abnormality not found: " + abnTlAbnormality.getKeyid());
                }
            }



            // Save the entity
            AbnTlAbnormality savedEntity = repository.save(abnTlAbnormality);
            logger.info("Successfully created abnormality with Key ID: {}", savedEntity.getKeyid());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);

        } catch (Exception e) {
            logger.error("Error creating abnormality: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }


    public ResponseEntity<List<AbnTlAbnormality>> saveMultipleAbn(List<AbnTlAbnormality> abns) {
        try {
            List<AbnTlAbnormality> updatedList = new ArrayList<>();

           for (AbnTlAbnormality abnTlAbnormality : abns) {
            String elementId = abnTlAbnormality.getElementid();
            String location = null;
	 	    String seqIdentfr = "ABN_TL_ABNORMALITY";

	 	if( elementId != null && elementId.length() > 10  ){
	 		location = elementId.substring(11, 21); /* location id starts from 11  */
	 		seqIdentfr += location;
	 	}

            if (abnTlAbnormality.getKeyid() == null || abnTlAbnormality.getKeyid().trim().isEmpty()) {
                // Generate new keyId using sequence function
                String newKeyId = dbActionTemplate.getSequenceNumber(seqIdentfr,KEY_LENGTH,PREFIX,DATE_FORMAT,FORMAT_RESET);
                abnTlAbnormality.setKeyid(newKeyId);
                logger.info("Generated new Key ID: {} for abnormality", newKeyId);
            } else {
                // Validate if the provided keyId already exists
                if (repository.existsById(abnTlAbnormality.getKeyid())) {
                    // AbnTlAbnormality updateEntity = repository.save(abnTlAbnormality);
                    logger.info("Successfully updated abnormality with Key ID: {}", abnTlAbnormality.getKeyid());

                    // return ResponseEntity.status(HttpStatus.OK).body(updateEntity);
                }else{
                     new RuntimeException("Abnormality not found: " + abnTlAbnormality.getKeyid());
                }
            }


            updatedList.add(abnTlAbnormality);
        }
            List<AbnTlAbnormality> saved = repository.saveAll(updatedList);

        return ResponseEntity.status(HttpStatus.OK).body(saved);

        } catch (Exception e) {
            logger.error("Error creating abnormality: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }


  
    public AbnTlAbnormality getAbnormality(String keyid) {
        return repository.findById(keyid).orElseThrow(() ->
                    new RuntimeException("Abnormality not found: " + keyid));
    }

    public List<Map<String, Object>>  getMultipleAbnormality(List<String> keyids) {
        return repository.findMultipleAbn(keyids);
    }

    @Transactional
    public List<AbnTlAbnormality> updateAbnormalityAllocation(@RequestBody List<AbnAllocationDto> abnList) {
   
       List<AbnTlAbnormality> updatedList = new ArrayList<>();

        for (AbnAllocationDto abn : abnList) {

            AbnTlAbnormality entity = repository.findById(abn.getKeyid())
                .orElseThrow(() ->
                    new RuntimeException("Abnormality not found: " + abn.getKeyid()));

            // UPDATE FIELDS
            entity.setResponsibleid(abn.getResponsibleid());
            if(ValidationUtil.isValidKeyId(abn.getTradeid())){
               entity.setTradeid(abn.getTradeid());
            }
            if(abn.getEffectivedate()!= null){
                entity.setEffectivedate(abn.getEffectivedate());
            }else{
                entity.setEffectivedate(Constants.futureNullDate);
            }
            

            AbnTlAbnormality saved = repository.save(entity);
            updatedList.add(saved);

            logger.info("Updated abnormality KeyID: {}", saved.getKeyid());
        }

        return updatedList;
     
}

public AbnTlAbnormality updateAbnormality(@RequestBody AbnCompletionDto dto) {
    
    AbnTlAbnormality entity = repository.findById(dto.getKeyid()).orElseThrow(() ->
                    new RuntimeException("Abnormality not found: " + dto.getKeyid()));;
    

    entity.setStatus(dto.getStatus());
    entity.setRemarks(dto.getRemarks());
    entity.setCountermeasure(dto.getCountermeasure());
    entity.setCompletedby(dto.getCompletedby());
    entity.setWoendtime(dto.getWoendtime());

    AbnTlAbnormality updateEntity = repository.save(entity);
    logger.info("Successfully updated abnormality with Key ID: {}", updateEntity.getKeyid());

    return updateEntity;
     
}

}
