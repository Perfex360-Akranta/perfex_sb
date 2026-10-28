package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.GenTlOtgCapture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface GenTlOtgCaptureRepository
        extends JpaRepository<GenTlOtgCapture, String> {

    GenTlOtgCapture findByKeyid(String keyid);

    List<GenTlOtgCapture> findByFlid(String flid);

    List<GenTlOtgCapture> findByEmpmKeyid(String empmKeyid);

    List<GenTlOtgCapture> findByStatus(String status);

    @Query(value = """
        SELECT
            otgc_keyid AS keyid,
            otgc_title AS title,
            otgc_description AS description,
            otgc_remarks AS remarks,
            otgc_flid AS flid,
            otgc_empm_keyid AS empmKeyid,
            otgc_entrydate AS entrydate,
            otgc_priority AS priority,
            otgc_status AS status,
            otgc_type AS type,
            otgc_createdby AS createdby,
            otgc_createddate AS createddate,
            otgc_modifiedby AS modifiedby,
            otgc_modifieddate AS modifieddate
        FROM gen_tl_otgcapture
        WHERE otgc_keyid = :keyid
        """, nativeQuery = true)
    Map<String, Object> findOtgCaptureDetails(
            @Param("keyid") String keyid);
}