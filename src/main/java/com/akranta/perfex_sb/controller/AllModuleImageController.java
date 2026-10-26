package com.akranta.perfex_sb.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.akranta.perfex_sb.service.AllModuleImageService;

@RestController
@RequestMapping("/api/images")
public class AllModuleImageController {

        private final AllModuleImageService imageService;

        public AllModuleImageController(AllModuleImageService imageService) {
                this.imageService = imageService;
        }

        private static final long MAX_IMAGE_SIZE = 5L * 1024 * 1024;

        private static final Set<String> ALLOWED_TYPES = Set.of(
                        "image/jpeg",
                        "image/png",
                        "image/webp");

        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<?> uploadImage(

                        @RequestParam("file") MultipartFile file,

                        @RequestParam("refKeyId") String refKeyId,

                        @RequestParam("refDocType") String refDocType,

                        @RequestParam("imageType") String imageType

        ) throws IOException {

                if (file.isEmpty()) {
                        return ResponseEntity.badRequest()
                                        .body(Map.of("message", "Image is required"));
                }

                if (file.getSize() > MAX_IMAGE_SIZE) {
                        return ResponseEntity.badRequest()
                                        .body(Map.of("message", "Image exceeds 5 MB"));
                }

                if (!ALLOWED_TYPES.contains(file.getContentType())) {
                        return ResponseEntity.badRequest()
                                        .body(Map.of("message", "Unsupported image type"));
                }

                if (refKeyId.isBlank() || refDocType.isBlank() || imageType.isBlank()) {
                        return ResponseEntity.badRequest()
                                        .body(Map.of("message", "Image reference details are required"));
                }

                byte[] imageBytes = file.getBytes();

                System.out.println("Reference ID: " + refKeyId);
                System.out.println("Document Type: " + refDocType);
                System.out.println("Image Type: " + imageType);
                System.out.println("Filename: " + file.getOriginalFilename());
                System.out.println("Image bytes: " + imageBytes.length);

                imageService.uploadImage(
                                file,
                                refKeyId,
                                refDocType,
                                imageType);

                return ResponseEntity.ok(Map.of(
                                "message", "Image received successfully",
                                "refKeyId", refKeyId,
                                "refDocType", refDocType,
                                "imageType", imageType,
                                "size", imageBytes.length));
        }

        @GetMapping(value = "/view", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
        public ResponseEntity<byte[]> getImage(

                        @RequestParam String refKeyId,
                        @RequestParam String refDocType,
                        @RequestParam String imageType) {

                byte[] imageBytes = imageService.getImage(
                                refKeyId,
                                refDocType,
                                imageType);

              return ResponseEntity.ok()
        .contentType(MediaType.IMAGE_JPEG)
        .contentLength(imageBytes.length)
        .body(imageBytes);
             }
}