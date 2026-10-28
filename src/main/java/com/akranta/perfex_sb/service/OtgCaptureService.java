package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.model.GenTlOtgCapture;
import com.akranta.perfex_sb.model.GenTlOtgRouter;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface OtgCaptureService {

    ResponseEntity<GenTlOtgCapture> save(
            GenTlOtgCapture otgCapture);

    GenTlOtgCapture findByKeyid(String keyid);

    List<GenTlOtgCapture> findAll();

    List<GenTlOtgCapture> findByFlid(String flid);

    List<GenTlOtgCapture> findByStatus(String status);

    Map<String, Object> findOtgCaptureDetails(String keyid);

    void delete(String keyid);
    //mano for router 
     ResponseEntity<GenTlOtgRouter> saveRouter(GenTlOtgRouter otgRouter);
}