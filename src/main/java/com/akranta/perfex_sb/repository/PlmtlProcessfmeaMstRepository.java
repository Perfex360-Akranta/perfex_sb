package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.akranta.perfex_sb.model.PlmtlProcessfmeaMST;

import jakarta.transaction.Transactional;

@Repository
public interface PlmtlProcessfmeaMstRepository extends JpaRepository<PlmtlProcessfmeaMST, String> {

    // @Query(value = "SELECT FMPM_KEYID, " +
    // "FMPM_FLID, " +
    // "TO_CHAR(FMPM_DATE, 'DD-Mon-YYYY') AS FMPM_DATE, " +
    // "FMPM_NO, " +
    // "FMPM_PREPAREDBY, " +
    // "COALESCE(FMPM_CORETEAM, '') AS FMPM_CORETEAM, " +
    // "FMPM_PROCESSID, " +
    // "FMPM_SUPPROCESSID " +
    // "FROM PLM_TL_PROCESSFMEAMST " +
    // "WHERE FMPM_KEYID = :keyId", nativeQuery = true)

    // @Query(value = "SELECT FMPM_KEYID, " +
    //         "FMPM_FLID, " +
    //         "TO_CHAR(FMPM_DATE, 'DD-Mon-YYYY') AS FMPM_DATE, " +
    //         "FMPM_NO, " +
    //         "FMPM_PREPAREDBY, " +
    //         "FMPM_CORETEAM, " +
    //         "FMPM_PROCESSID, " +
    //         "FMPM_SUPPROCESSID " +
    //         "FROM PLM_TL_PROCESSFMEAMST " +
    //         "WHERE FMPM_KEYID = :keyId", nativeQuery = true)
    //  List<Map<String, Object>> getProcessFmeaByKeyId(@Param("keyId") String keyId);


    // @Query(value ="SELECT FMPM_KEYID, " +
    //         "FMPM_FLID, " +
    //         "TO_CHAR(FMPM_DATE, 'DD-Mon-YYYY') AS FMPM_DATE, " +
    //         "FMPM_NO, " +
    //         "FMPM_PREPAREDBY, " +
    //         "FMPM_CORETEAM, " +
    //         "FMPM_PROCESSID, " +
    //         "FMPM_SUPPROCESSID " +
    //         "FROM PLM_TL_PROCESSFMEAMST " +
    //         "WHERE FMPM_KEYID = :keyId", nativeQuery = true)

    // // String getProcessFmeaByKeyId(@Param("keyId") String keyId);

    //  List<Map<String, Object>> getProcessFmeaByKeyId(@Param("keyId") String keyId);


    @Query(value = """
    SELECT
        '1' AS FMPM_KEYID,
        '2' AS FMPM_FLID,
        '3' AS FMPM_DATE,
        '4' AS FMPM_NO,
        '5' AS FMPM_PREPAREDBY,
        '6' AS FMPM_CORETEAM,
        '7' AS FMPM_PROCESSID,
        '8' AS FMPM_SUPPROCESSID
    UNION ALL
    SELECT
        FMPM_KEYID AS FMPM_KEYID,
        FMPM_FLID AS FMPM_FLID,
        TO_CHAR(FMPM_DATE, 'DD-Mon-YYYY') AS FMPM_DATE,
        FMPM_NO AS FMPM_NO,
        FMPM_PREPAREDBY AS FMPM_PREPAREDBY,        
        COALESCE(NULLIF(FMPM_CORETEAM, '{}'), '') AS FMPM_CORETEAM,
        FMPM_PROCESSID AS FMPM_PROCESSID,
        FMPM_SUPPROCESSID AS FMPM_SUPPROCESSID
    FROM PLM_TL_PROCESSFMEAMST
    WHERE FMPM_KEYID = :keyId
    """, nativeQuery = true)
 List<Map<String, Object>> getProcessFmeaByKeyId(@Param("keyId") String keyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM PLM_TL_PROCESSFMEAMST WHERE FMPM_KEYID = :keyid", nativeQuery = true)
    void deleteByKeyId(@Param("keyid") String keyid);
}
