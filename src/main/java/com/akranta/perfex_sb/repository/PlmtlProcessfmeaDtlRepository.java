package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.PlmtlProcessfmeaDTL;

import jakarta.transaction.Transactional;

@Repository

public interface PlmtlProcessfmeaDtlRepository extends JpaRepository<PlmtlProcessfmeaDTL, String> {

    @Modifying
    @Query("DELETE FROM PlmtlProcessfmeaDTL p WHERE p.keyid = :keyid")
    void deleteByKeyId(@Param("keyid") String keyid);

   

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE PLM_TL_PROCESSFMEADTL
                SET
                    FMPD_RESEVERITY_KEYID = '{}',
                    FMPD_REOCCURRENCE_KEYID = '{}',
                    FMPD_REDETECTION_KEYID = '{}',
                    FMPD_RERPN = 0,
                    FMPD_REVIEWBY = '{}',
                    FMPD_REDATE = CURRENT_TIMESTAMP
                WHERE FMPD_KEYID = :keyId
            """, nativeQuery = true)
    int updateReviewByKeyId(@Param("keyId") String keyId);

    @Query("SELECT COUNT(p) FROM PlmtlProcessfmeaDTL p WHERE p.fmpm_keyid = :mstKeyId")
    long countByMstKeyId(@Param("mstKeyId") String mstKeyId);

}



 // @Modifying
    // @Transactional
    // @Query(value = """
    // UPDATE TBL_PLM_TL_PROCESSFMEADTL
    // SET
    // FMPD_RESEVERITY_KEYID = :reSeverityKeyId,
    // FMPD_REOCCURRENCE_KEYID = :reOccurrenceKeyId,
    // FMPD_REDETECTION_KEYID = :reDetectionKeyId,
    // FMPD_RERPN = '0',
    // FMPD_REVIEWBY = :reviewBy,
    // FMPD_REDATE = SYSDATE
    // WHERE FMPD_KEYID = :keyid
    // """, nativeQuery = true)
    // int updateReview(
    // @Param("reSeverityKeyId") String reSeverityKeyId,
    // @Param("reOccurrenceKeyId") String reOccurrenceKeyId,
    // @Param("reDetectionKeyId") String reDetectionKeyId,
    // @Param("reviewBy") String reviewBy,
    // @Param("keyid") String keyid);