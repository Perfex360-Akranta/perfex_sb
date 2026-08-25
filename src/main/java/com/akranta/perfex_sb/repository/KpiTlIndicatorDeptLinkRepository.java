package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.akranta.perfex_sb.model.KpiTlIndicatorDeptLink;

@Repository
public interface KpiTlIndicatorDeptLinkRepository extends JpaRepository<KpiTlIndicatorDeptLink, String> {

        // Get Pillar ID by Pillar Code
        @Query(value = "SELECT TPMP_KEYID FROM GEN_TL_TPMPILLARMST WHERE TPMP_CODE = :pillCode", nativeQuery = true)
        String getPillarIdByCode(@Param("pillCode") String pillCode);

        // Get Indicator Name by Indicator ID
        @Query(value = "SELECT KINK_INDICATORNAME FROM KPI_TL_INDICATOR WHERE KINK_KEYID = :indicatorId", nativeQuery = true)
        String getIndicatorNameById(@Param("indicatorId") String indicatorId);

        // Get Functional Location by FNLN Key ID
        @Query(value = "SELECT FUNCTIONALLOC FROM GEN_VW_FNLN WHERE FNLN_KEYID = :fnlnKeyId", nativeQuery = true)
        String getFunctionalLocationById(@Param("fnlnKeyId") String fnlnKeyId);

        // Get FNLN Original ID
        @Query(value = "SELECT FNLN_ORIGINALID FROM GEN_VW_FNLN WHERE FNLN_KEYID = :fnlnKeyId", nativeQuery = true)
        String getFnlnOriginalIdById(@Param("fnlnKeyId") String fnlnKeyId);

        // Get Cell Name
        @Query(value = "SELECT CELL_NAME FROM GEN_VW_FNLN WHERE FNLN_ORIGINALID = :originalId", nativeQuery = true)
        String getCellNameByOriginalId(@Param("originalId") String originalId);

        // Get Section Name
        @Query(value = "SELECT SECT_NAME FROM GEN_VW_FNLN WHERE FNLN_ORIGINALID = :originalId", nativeQuery = true)
        String getSectionNameByOriginalId(@Param("originalId") String originalId);

        //new 
       // Find existing record by indicatorid + deptid 
        @Query(value = "SELECT * FROM public.kpi_tl_indicator_dept_link WHERE kidl_indicatorid = :indicatorId AND kidl_deptid = :deptId", nativeQuery = true)
        KpiTlIndicatorDeptLink findByIndicatorIdAndDeptId(
                        @Param("indicatorId") String indicatorId,
                        @Param("deptId") String deptId);

        // Delete by composite key
        // Delete by composite key with optional indicatorId and deptId
        @Modifying
        @Query(value = "DELETE FROM KPI_TL_INDICATOR_DEPT_LINK WHERE " +
                        "kidl_pillarid = :pillarId AND " +
                        "kidl_depttype = :drillLevel " +
                        "AND kidl_indicatorid = :indicatorId " +
                        "AND kidl_deptid = :deptId", nativeQuery = true)
        int deleteByCompositeKey(
                        @Param("pillarId") String pillarId,
                        @Param("indicatorId") String indicatorId,
                        @Param("deptId") String deptId,
                        @Param("drillLevel") String drillLevel);

        // @Query(value = """
        // SELECT COUNT(*)
        // FROM KPI_TL_ACTUAL
        // WHERE
        // (:indicatorId IS NULL OR
        // (
        // KAUK_INDICATORID = :indicatorId
        // OR KAUK_INDICATORID IN (
        // SELECT KINK_KEYID
        // FROM KPI_TL_INDICATOR
        // WHERE KINK_PARENTID = :indicatorId
        // )
        // )
        // )
        // AND (:deptId IS NULL OR KAUK_DEPTID = :deptId)
        // """, nativeQuery = true)
        // Long getLinkCount(
        // @Param("indicatorId") String indicatorId,
        // @Param("deptId") String deptId);

        @Query(value = """
                        SELECT COUNT(*)
                        FROM KPI_TL_ACTUAL
                        WHERE 1=1
                          AND (
                                :indicatorId IS NULL
                                OR KAUK_INDICATORID = :indicatorId
                                OR KAUK_INDICATORID IN (
                                    SELECT KINK_KEYID
                                    FROM KPI_TL_INDICATOR
                                    WHERE KINK_PARENTID = :indicatorId
                                )
                              )
                          AND (:deptId IS NULL OR KAUK_DEPTID = :deptId)
                        """, nativeQuery = true)
        Long getLinkCount(
                        @Param("indicatorId") String indicatorId,
                        @Param("deptId") String deptId);

        

        
}
