    package com.akranta.perfex_sb.controller;



    import java.io.IOException;

import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RequestPart;
    import org.springframework.web.bind.annotation.RestController;
    import org.springframework.web.multipart.MultipartFile;

    import com.akranta.perfex_sb.dto.DocumentUploadDto;
    import com.akranta.perfex_sb.service.DocumentManagerService;

    @RestController
    @RequestMapping("/api/document/")
    public class DocumentManagerController 
    {
        @Autowired
        private DocumentManagerService service;

        private static final Logger logger = LoggerFactory.getLogger(DocumentManagerController.class);


        @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public void uploadFile( @ModelAttribute DocumentUploadDto dto)
        {
            try {
                logger.info("Inside upload controller");
                service.uploadFile(dto);
                
            } catch (Exception e) {
            logger.info("Exception During Upload");
            e.printStackTrace();
            }
        }


        @GetMapping("/download/{keyId}")
public ResponseEntity<byte[]> downloadFile(@PathVariable String keyId) throws IOException {
    return service.downloadFile(keyId);
}
    }
