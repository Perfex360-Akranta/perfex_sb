package com.akranta.perfex_sb.controller;


import com.akranta.perfex_sb.dto.AbnAllocationDto;
import com.akranta.perfex_sb.dto.AbnCompletionDto;
import com.akranta.perfex_sb.model.AbnTlAbnormality;
import com.akranta.perfex_sb.repository.AbnTlAbnormalityRepository;
// import com.akranta.perfex_sb.repository.DbFunctionTempleteRepository;
// import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.AbnTlAbnormalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import java.sql.SQLException;
// import java.time.LocalDate;
import java.util.List;
// import java.util.Map;
import java.util.Map;

@RestController
@RequestMapping("/api/abnormality")
public class AbnTlAbnormalityController {

    // private static final Logger logger = LoggerFactory.getLogger(AbnTlAbnormalityController.class);


    @Autowired
    private AbnTlAbnormalityRepository repository;

    @Autowired
    private AbnTlAbnormalityService abnService;

  


    @GetMapping
    public List<AbnTlAbnormality> getAllAbnormalities() {
        return repository.findAll();
    }

    @GetMapping("/{keyId}")
    public AbnTlAbnormality getAbnormality(@PathVariable String keyId) {
        return abnService.getAbnormality(keyId);
    }

   

    @PostMapping
    public ResponseEntity<AbnTlAbnormality> create(@RequestBody AbnTlAbnormality abnTlAbnormality) {

        return abnService.create(abnTlAbnormality);
    }

     @PostMapping(value = "/multipleAbn", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getMultipleAbnormality(@RequestBody List<String> keyIds) {
        return abnService.getMultipleAbnormality(keyIds);
    }


     @PostMapping("/multipleAbnSave")
    public ResponseEntity<List<AbnTlAbnormality>> saveMultipleAbnormality(@RequestBody List<AbnTlAbnormality> abns) {
        return abnService.saveMultipleAbn(abns);
    }

     @PostMapping("/updateAllocation")
 public ResponseEntity<?> updateAbnormalityAllocation(@RequestBody List<AbnAllocationDto> dto) {
   
    List<AbnTlAbnormality> updateEntity = abnService.updateAbnormalityAllocation(dto);
    
    return ResponseEntity.status(HttpStatus.OK).body(updateEntity);
    
    
}

    @PostMapping("/update")
public ResponseEntity<?> updateAbnormality(@RequestBody AbnCompletionDto dto) {
   
    AbnTlAbnormality updateEntity = abnService.updateAbnormality(dto); 

    return ResponseEntity.status(HttpStatus.OK).body(updateEntity);
     
}



    @DeleteMapping("/{keyId}")
    public ResponseEntity<Void> deleteAbnormality(@PathVariable String keyId) {
        try {
            if (repository.existsById(keyId)) {
                repository.deleteById(keyId);
                return ResponseEntity.noContent().build();
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
