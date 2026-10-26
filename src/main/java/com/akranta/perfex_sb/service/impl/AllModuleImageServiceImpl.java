package com.akranta.perfex_sb.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.akranta.perfex_sb.model.GenTlAllModuleImage;
import com.akranta.perfex_sb.repository.AllModuleImageRepo;
import com.akranta.perfex_sb.service.AllModuleImageService;

@Service
public class AllModuleImageServiceImpl implements AllModuleImageService {

    private final AllModuleImageRepo repository;

    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp");

    public AllModuleImageServiceImpl(AllModuleImageRepo repository) {
        this.repository = repository;
    }

    @Override

    @Transactional(rollbackFor = Exception.class)
    public void uploadImage(
            MultipartFile file,
            String refKeyId,
            String refDocType,
            String imageType) throws IOException {

        // 1. Validate the image
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image is required");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image must be smaller than 5 MB");
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only JPG, PNG and WebP images are allowed");
        }

        if (isBlank(refKeyId) || isBlank(refDocType) || isBlank(imageType)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image reference details are required");
        }

        // 2. Convert MultipartFile to byte[]
        byte[] imageBytes = file.getBytes();

        // 3. Create the model
        GenTlAllModuleImage image = new GenTlAllModuleImage();

        image.setRefkeyid(refKeyId.trim());
        image.setRefdoctype(refDocType.trim());
        image.setImagetype(imageType.trim());

        image.setBlobimage(imageBytes);
        image.setBloblength(imageBytes.length);

        // Server-generated filename
        image.setFilename(
                file.getOriginalFilename());

        image.setTempfield1("");
        image.setTempfield2("");

        // 4. Replace the existing image
        repository.deleteImage(
                image.getRefkeyid(),
                image.getRefdoctype(),
                image.getImagetype());

        // 5. Insert the new image
        repository.insertImage(image);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] getImage(
            String refKeyId,
            String refDocType,
            String imageType) {

        if (refKeyId == null || refKeyId.isBlank()
                || refDocType == null || refDocType.isBlank()
                || imageType == null || imageType.isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image reference details are required");
        }

        byte[] image = repository.getImage(
                refKeyId.trim(),
                refDocType.trim(),
                imageType.trim());

        if (image == null || image.length == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Image not found");
        }

        return image;
    }

    public static String generateNumber() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        return LocalDateTime.now().format(formatter);
    }

}
