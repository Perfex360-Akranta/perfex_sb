package com.akranta.perfex_sb.service;

import org.springframework.http.ResponseEntity;
import com.akranta.perfex_sb.dto.CliTlStandardsRequest;

public interface CliTlStandardsService {

    /**
     * Save or Update the CLTI (CLI Standard) multiple rows in a single call.
     * Per row in details:
     *  - keyid null/empty/"{}"/"undefined" -> INSERT (new key generated)
     *  - keyid exists -> UPDATE
     * Image handling and GEN_TL_DOCUPDATES sync are intentionally left out.
     */
    ResponseEntity<CliTlStandardsRequest> saveCliTlStandards(CliTlStandardsRequest request) throws Exception;
    ResponseEntity<Void> deleteCliTlStandards(String keyid) throws Exception;

}
