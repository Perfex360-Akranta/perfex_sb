package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.PlmtlEquipmentfmeaMST;

import jakarta.transaction.Transactional;

@Repository
public interface PlmEquipmentfmeaMstRepository extends JpaRepository<PlmtlEquipmentfmeaMST, String> {

        // @Query(value = "SELECT FMEQ_KEYID, " +
        //                 "FMEQ_FLID, " +
        //                 "TO_CHAR(FMEQ_DATE, 'DD-Mon-YYYY') AS FMEQ_DATE, " +
        //                 "FMEQ_NO, " +
        //                 "FMEQ_PREPAREDBY, " +
        //                 "FMEQ_CORETEAM, " +
        //                 "FMEQ_EQUIPID, " +
        //                 "FMEQ_SUPEQUIPID " +
        //                 "FROM PLM_TL_EQUIPMENTFMEAMST " +
        //                 "WHERE FMEQ_KEYID = :keyId", nativeQuery = true)
        // List<Map<String, Object>> getEquipmentFmeaByKeyId(@Param("keyId") String keyId);


        @Query(value = """
    SELECT
        '1' AS FMEQ_KEYID,
        '2' AS FMEQ_FLID,
        '3' AS FMEQ_DATE,
        '4' AS FMEQ_NO,
        '5' AS FMEQ_PREPAREDBY,
        '6' AS FMEQ_CORETEAM,
        '7' AS FMEQ_EQUIPID,
        '8' AS FMEQ_SUPEQUIPID
    UNION ALL
    SELECT
        FMEQ_KEYID  AS FMEQ_KEYID,
        FMEQ_FLID AS FMEQ_FLID,
        TO_CHAR(FMEQ_DATE, 'DD-Mon-YYYY') AS FMEQ_DATE,
        FMEQ_NO AS FMEQ_NO,
        FMEQ_PREPAREDBY AS FMEQ_PREPAREDBY,
        COALESCE(NULLIF(FMEQ_CORETEAM, '{}'), '') AS FMEQ_CORETEAM,
        COALESCE(NULLIF(FMEQ_EQUIPID, '{}'), '') AS FMEQ_EQUIPID,
        COALESCE(NULLIF(FMEQ_SUPEQUIPID, '{}'), '') AS FMEQ_SUPEQUIPID
    FROM PLM_TL_EQUIPMENTFMEAMST
    WHERE FMEQ_KEYID = :keyId
    """, nativeQuery = true)
List<Map<String, Object>> getEquipmentFmeaByKeyId(@Param("keyId") String keyId);

        @Modifying
        @Transactional
        @Query(value = "DELETE FROM PLM_TL_EQUIPMENTFMEAMST WHERE FMEQ_KEYID = :keyid", nativeQuery = true)
        void deleteByKeyId(@Param("keyid") String keyid);

}
