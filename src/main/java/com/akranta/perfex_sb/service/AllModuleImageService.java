package com.akranta.perfex_sb.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface AllModuleImageService {

       public void uploadImage(MultipartFile file,String refKeyId,String refDocType,String imageType) throws IOException;

       byte[] getImage(
    String refKeyId,
    String refDocType,
    String imageType
);

}
