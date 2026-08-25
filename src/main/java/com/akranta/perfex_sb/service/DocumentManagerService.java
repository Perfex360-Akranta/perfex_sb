package com.akranta.perfex_sb.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.akranta.perfex_sb.dto.DocumentUploadDto;

public interface DocumentManagerService 
{

     public void uploadFile(DocumentUploadDto dto);


        ResponseEntity<byte[]> downloadFile(String keyId) throws IOException;
}


