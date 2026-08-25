package com.akranta.perfex_sb.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.akranta.perfex_sb.dto.DocumentUploadDto;
import com.akranta.perfex_sb.model.DcmTlDocumentManager;
import com.akranta.perfex_sb.repository.DcmTlDocumentManagerRepository;
import com.akranta.perfex_sb.repository.DmtDashboardRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.DocumentManagerService;

@Service
public class DocumentManagerServiceImpl implements DocumentManagerService 
{

     @Value("${document.storage.path}")
    private String storageDirectory;

    
        @Autowired
        private  DcmTlDocumentManagerRepository repo;

         @Autowired
        private DbActionTemplate dbActionTemplate;





   @Override
public void uploadFile(DocumentUploadDto dto) {

    try {

       Path storagePath = Paths.get(storageDirectory,dto.getRefDocType(),dto.getRefDocNo()).toAbsolutePath().normalize();

        Files.createDirectories(storagePath);

        Path targetPath = storagePath
                .resolve(dto.getFile().getOriginalFilename())
                .normalize();

        if (!targetPath.startsWith(storagePath)) {
            throw new SecurityException("Unsupported File Name");
        }

        Files.copy(dto.getFile().getInputStream(),
                   targetPath,
                   StandardCopyOption.REPLACE_EXISTING);
        String filePath = targetPath.toString();

        DcmTlDocumentManager document = new DcmTlDocumentManager();

        document.setRefdocno(dto.getRefDocNo());
        document.setRefdoctype(dto.getRefDocType());


        document.setFilename(dto.getFile().getOriginalFilename());
        document.setDescription(dto.getDescription());
        document.setKeywords(dto.getKeywords());

        document.setBloblength((int) dto.getFile().getSize());
        document.setBlobfile(dto.getFile().getBytes());

        document.setSlno(3);

        document.setCategory(dto.getCategory());
        document.setOwner(dto.getOwner());
        document.setApprovedby(dto.getApprovedBy());
        document.setSubjectarea(dto.getSubjectArea());

        document.setTitle(dto.getTitle());

        document.setPath(filePath); // Path where the file was saved

        document.setType("word");

        document.setCreatedby("EMP00001");

        document.setApprovedby("EMP00001");

        document.setSubjectarea("SUB001");

        document.setTitle("testtitle");

        document.setActive('Y');

        document.setCreatedon(LocalDateTime.now());
        document.setModifiedon(LocalDateTime.now());
        try {
            String newKeyId = dbActionTemplate.getSequenceNumber("DCM_TL_DOCUMENTMANAGER",10,"DMM","YYMM","Y");
            document.setKeyid(newKeyId);
            repo.save(document);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    } catch (IOException e) {
        throw new RuntimeException("File upload failed", e);
    }
}

@Override
public ResponseEntity<byte[]> downloadFile(String keyId) throws IOException {

    DcmTlDocumentManager document = repo.findById(keyId)
            .orElseThrow(() -> new RuntimeException("Document not found"));

            String contentType = Files.probeContentType(Paths.get(document.getFilename()));


    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + document.getFilename() + "\"")
            .contentType(MediaType.parseMediaType(contentType))
            .contentLength(document.getBloblength())
            .body(document.getBlobfile());
}

}
