package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BalPlmTlCbmstdcadtl;

public interface BalPlmTlCbmstdcadtlRepository extends JpaRepository<BalPlmTlCbmstdcadtl, String> {

    List<BalPlmTlCbmstdcadtl> findByPmstandardid(String pmstandardid);
    void deleteByPmstandardid(String pmstandardid);

    // mirrors legacy BAL_PlmTlStandardsSql.getCBMTbl() -- LEFT JOIN so all
    // three zones (GREEN/YELLOW/RED) always come back even if no CBM row
    // exists yet for a given zone + pmStandardId combination
    @Query(value = """
            SELECT Z.ZONM_KEYID                                                                 AS zonm_keyid,
                   C.CMDT_INSPECTIONID                                                           AS cmdt_inspectionid,
                   REPLACE(REPLACE(Z.ZONM_NAME, '<*', ''), '*>', '')                              AS zonm_name,
                   C.CMDT_ZONECOLOR                                                               AS cmdt_zonecolor,
                   C.CMDT_LOWERLIMIT                                                              AS cmdt_lowerlimit,
                   C.CMDT_UPPERLIMIT                                                              AS cmdt_upperlimit,
                   C.CMDT_DESIRABLEREADING                                                        AS cmdt_desirablereading,
                   REPLACE(REPLACE(C.CMDT_CORRECTIVEACTION, '<*', ''), '*>', '')                  AS cmdt_correctiveaction,
                   Z.ZONM_CORRECTIVECONDITION                                                     AS zonm_correctivecondition,
                   '' 						                                                        AS column10,
                   REPLACE(REPLACE(REPLACE(C.CMDT_MEASURINGMETHOD, '<*', ''), '*>', ''), '{}', '') AS cmdt_measuringmethod,
                   C.CMDT_PMSTANDARDID                                                            AS cmdt_pmstandardid,
                   C.CMDT_UOMID                                                                   AS cmdt_uomid,
                   C.CMDT_KEYID                                                                   AS cmdt_keyid
            FROM BAL_PLM_TL_ZONEMST Z
            LEFT JOIN BAL_PLM_TL_CBMSTDCADTL C
              ON C.CMDT_ZONEID = Z.ZONM_KEYID
             AND C.CMDT_PMSTANDARDID = :pmStandardId
            ORDER BY Z.ZONM_CODE
            """, nativeQuery = true)
    List<Map<String, Object>> getCBM(@Param("pmStandardId") String pmStandardId);
}