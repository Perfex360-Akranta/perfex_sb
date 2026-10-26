package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.model.BAL_PlmTlSparedtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_PlmTlSparedtlRepository extends JpaRepository<BAL_PlmTlSparedtl, String> {

    List<BAL_PlmTlSparedtl> findByStandardid(String standardid);

    // mirrors legacy BAL_PlmTlSparedtlSql.getSparesPickupTbl()
    @Query(value = """
            SELECT 'False'                     AS select_flag,
                   PSPD_SPAREID                AS pspd_spareid,
                   PSPD_KEYID                  AS pspd_keyid,
                   ''                          AS standardid,
                   SPRM_PARTNO                 AS sprm_partno,
                   SPRM_PARTNAME               AS sprm_partname,
                   SPRM_MAKE                   AS sprm_make,
                   SPRM_MODEL                  AS sprm_model,
                   PSPD_QUANTITY                AS pspd_quantity,
                   PSPD_MODIFIEDON             AS pspd_modifiedon,
                   PSPD_CREATEDON              AS pspd_createdon
            FROM GEN_TL_SPARESMST, BAL_PLM_TL_SPAREDTL
            WHERE SPRM_KEYID = PSPD_SPAREID
              AND PSPD_STANDARDID = :standardId
            """, nativeQuery = true)
    List<Map<String, Object>> getSprPickup(@Param("standardId") String standardId);
}