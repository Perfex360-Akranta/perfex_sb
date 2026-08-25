package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.dto.KpiIndicatorDto;
import com.akranta.perfex_sb.model.KpiTlIndicator;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface KpiTlIndicatorRepository extends JpaRepository<KpiTlIndicator, String> {

    // Update parent's ischild flag
    @Modifying
    @Query(value = "UPDATE kpi_tl_indicator SET kink_ischild = 'N' WHERE kink_keyid = :parentId", nativeQuery = true)
    void updateParentIsChild(@Param("parentId") String parentId);

    // // Get search node - returns list of child paths
    // @Query(value = "SELECT CHILDPATH FROM kpi_vw_keyperInchildpath " +
    // "WHERE (:searchNode IS NULL OR :searchNode = '' OR NAME = :searchNode) " +
    // "AND (:originalId IS NULL OR :originalId = '' OR KEYID = :originalId)",
    // nativeQuery = true)
    // List<String> getSearchNodeChildPaths(@Param("searchNode") String searchNode,
    // @Param("originalId") String originalId);

    @Query(value = "SELECT CHILDPATH FROM kpi_vw_keyperInchildpath WHERE 1=1 " +
            "AND (:searchNode IS NULL OR :searchNode = '' OR :searchNode = 'null' OR NAME = :searchNode) " +
            "AND (:originalId IS NULL OR :originalId = '' OR :originalId = 'null' OR KEYID = :originalId)", nativeQuery = true)
    List<String> getSearchNodeChildPaths(@Param("searchNode") String searchNode,
            @Param("originalId") String originalId);

    @Modifying
    @Query(value = "UPDATE kpi_tl_indicator SET kink_targetneed = 'Y' WHERE kink_keyid = :keyid", nativeQuery = true)
    void updateTargetNeed(@Param("keyid") String keyid);

    @Query(value = """
                SELECT COUNT(*) + 1 AS lastCount
                FROM KPI_TL_INDICATOR
                WHERE 1 = 1
                  AND KINK_LEVELNO = :levelNo
            """, nativeQuery = true)
    Integer getSortNoForLevel1(@Param("levelNo") Integer levelNo);

    // Get sort number for child levels
    // @Query(value = "SELECT a.sortno || '.' || b.lastCount " +
    // "FROM (SELECT kink_sortno AS sortno FROM kpi_tl_indicator " +
    // " WHERE kink_keyid = :parentId) a, " +
    // " (SELECT COUNT(*) + 1 AS lastCount FROM kpi_tl_indicator " +
    // " WHERE kink_parentid <> kink_keyid AND kink_parentid = :parentId) b",
    // nativeQuery = true)
    // String getSortNoForChildLevel(@Param("parentId") String parentId);

    // Get sort number for child levels
    // @Query(value = "SELECT a.sortno || '.' || b.lastCount " +
    // "FROM (SELECT kink_sortno AS sortno FROM kpi_tl_indicator " +
    // " WHERE kink_keyid = :parentId) a " +
    // "CROSS JOIN (SELECT COUNT(*) + 1 AS lastCount FROM kpi_tl_indicator " +
    // " WHERE kink_parentid <> kink_keyid AND kink_parentid = :parentId) b",
    // nativeQuery = true)
    // String getSortNoForChildLevel(@Param("parentId") String parentId);

    @Query(value = """
            SELECT a.sortno || '.' || b.lastCount
            FROM (
                SELECT KINK_SORTNO AS sortno
                FROM KPI_TL_INDICATOR
                WHERE 1 = 1
                  AND KINK_KEYID = :parentId
            ) a,
            (
                SELECT COUNT(*) + 1 AS lastCount
                FROM KPI_TL_INDICATOR
                WHERE 1 = 1
                  AND KINK_PARENTID <> KINK_KEYID
                  AND KINK_PARENTID = :parentId
            ) b
            """, nativeQuery = true)
    String getSortNoForChildLevel(@Param("parentId") String parentId);

    // @Query(
    // value = """
    // SELECT COALESCE(a.sortno, '0') || '.' || b.lastCount
    // FROM (
    // SELECT kink_sortno AS sortno
    // FROM kpi_tl_indicator
    // WHERE kink_keyid = :parentId
    // ) a
    // CROSS JOIN (
    // SELECT COUNT(*) + 1 AS lastCount
    // FROM kpi_tl_indicator
    // WHERE kink_parentid = :parentId
    // AND kink_parentid <> kink_keyid
    // ) b
    // """,
    // nativeQuery = true
    // )

    // // Get count of children for a parent
    // @Query(value = "SELECT COUNT(*) + 1 FROM kpi_tl_indicator " +
    // "WHERE kink_parentid <> kink_keyid AND kink_parentid = :parentId",
    // nativeQuery = true)
    // Integer getChildCount(@Param("parentId") String parentId);

    // Get parent sort number
    @Query(value = " SELECT kink_sortno FROM kpi_tl_indicator WHERE kink_keyid = :parentId ", nativeQuery = true)
    String getParentSortNo(@Param("parentId") String parentId);

    @Query(value = "Select TPMP_KEYID from gen_tl_tpmpillarmst where TPMP_CODE = :pillCode", nativeQuery = true)
    String getTpmpKeyId(@Param("pillCode") String pillCode);

    @Query(value = " select LOCN_KEYID from gen_vw_fnln where FNLN_KEYID = :flId ", nativeQuery = true)
    String getflId(@Param("flId") String flId);

    // @Query(value = "SELECT KINK_KEYID, KINK_INDICATORNAME, KINK_INDICATORCODE,
    // KINK_DESCRIPTION, KINK_PARENTID, KINK_LEVELNO, KINK_SORTNO, KINK_ISCHILD,
    // KINK_INPUTTYPE, KINK_INPUTENTRY, KINK_IDENTIFIER, KINK_MANUALCALCTYPE,
    // KINK_UOMID, KINK_FREQUENCY, KINK_EXCELNAME, KINK_DEPT_KEYID, KINK_COSTAREA,
    // KINK_TARGETNEED, KINK_PILLARID, CHILDPATH FROM KPI_TL_INDICATOR,
    // KPI_VW_KEYPERINCHILDPATH WHERE 1=1 AND KINK_KEYID <> KINK_PARENTID AND KEYID
    // = KINK_KEYID AND KINK_ACTIVE = 'Y' AND (:parentId IS NULL OR KINK_PARENTID =
    // :parentId) AND (:pillarId IS NULL OR KINK_PILLARID = :pillarId) AND (:keyId
    // IS NULL OR KINK_KEYID = :keyId) AND (:location IS NULL OR KINK_LOCATION =
    // :location) ORDER BY KINK_KEYID", nativeQuery = true)

    // // @Query(value = "SELECT i.kink_keyid, i.kink_indicatorname,
    // i.kink_indicatorcode, i.kink_description, i.kink_parentid, i.kink_levelno,
    // i.kink_sortno, i.kink_ischild, i.kink_inputtype, i.kink_inputentry,
    // i.kink_identifier, i.kink_manualcalctype, i.kink_uomid, i.kink_frequency,
    // i.kink_excelname, i.kink_dept_keyid, i.kink_costarea, i.kink_targetneed,
    // i.kink_pillarid, i.kink_type, i.kink_impactarea, i.kink_goals,
    // i.kink_sourceofkpi, i.kink_kpireason, i.kink_annualtarget, i.kink_startdate,
    // i.kink_enddate, i.kink_location, i.kink_active, i.kink_createdby,
    // i.kink_createdon, i.kink_modifiedon FROM kpi_tl_indicator i,
    // kpi_vw_keyperinchildpath k WHERE 1=1 AND i.kink_keyid <> i.kink_parentid AND
    // k.keyid = i.kink_keyid AND i.kink_active = 'Y' AND (:parentId IS NULL OR
    // i.kink_parentid = :parentId) AND (:pillarId IS NULL OR i.kink_pillarid =
    // :pillarId) AND (:keyId IS NULL OR i.kink_keyid = :keyId) AND (:location IS
    // NULL OR i.kink_location = :location) ORDER BY i.kink_keyid", nativeQuery =
    // true)
    // List<KpiTlIndicator> getAllkeyInd(
    // @Param("parentId") String parentId,
    // @Param("pillarId") String pillarId,
    // @Param("keyId") String keyId,
    // @Param("location") String location);

    // @Query(value = "SELECT " +
    // "KINK_KEYID, KINK_INDICATORNAME, KINK_INDICATORCODE, KINK_DESCRIPTION,
    // KINK_PARENTID, " +
    // "KINK_LEVELNO, KINK_SORTNO, KINK_ISCHILD, KINK_INPUTTYPE, KINK_INPUTENTRY, "
    // +
    // "KINK_IDENTIFIER, KINK_MANUALCALCTYPE, KINK_UOMID, KINK_FREQUENCY,
    // KINK_EXCELNAME, " +
    // "KINK_DEPT_KEYID, KINK_COSTAREA, KINK_TARGETNEED, KINK_PILLARID, childpath "
    // +
    // "FROM KPI_TL_INDICATOR, kpi_vw_keyperInchildpath " +
    // "WHERE 1=1 " +
    // "AND KINK_KEYID <> KINK_PARENTID " +
    // "AND keyid = KINK_KEYID " +
    // "AND KINK_ACTIVE = 'Y' " +
    // "AND (:parentId IS NULL OR KINK_PARENTID = :parentId) " +
    // "AND (:pillarId IS NULL OR KINK_PILLARID = :pillarId) " +
    // "AND (:keyId IS NULL OR KINK_KEYID = :keyId) " +
    // "AND (:location IS NULL OR KINK_LOCATION = :location) " +
    // "ORDER BY KINK_KEYID", nativeQuery = true)
    // List<KpiTlIndicator> getAllkeyInd(
    // @Param("parentId") String parentId,
    // @Param("pillarId") String pillarId,
    // @Param("keyId") String keyId,
    // @Param("location") String location);

    @Query(value = """

            SELECT
                '1' AS keyid,
                '2' AS indicatorname,
                '3' AS indicatorcode,
                '4' AS description,
                '5' AS parentid,
                '6' AS levelno,
                '7' AS sortno,
                '8' AS ischild,
                '9' AS inputtype,
                '10' AS inputentry,
                '11' AS identifier,
                '12' AS manualcalctype,
                '13' AS uomid,
                '14' AS frequency,
                '15' AS excelname,
                '16' AS dept_keyid,
                '17' AS costarea,
                '18' AS targetneed,
                '19' AS pillarid,
                '20' AS childpath
            UNION ALL
            SELECT
                KINK_KEYID AS keyid,
                KINK_INDICATORNAME AS indicatorname,
                KINK_INDICATORCODE AS indicatorcode,
                KINK_DESCRIPTION AS description,
                KINK_PARENTID AS parentid,
                KINK_LEVELNO AS levelno,
                KINK_SORTNO AS sortno,
                KINK_ISCHILD AS ischild,
                KINK_INPUTTYPE AS inputtype,
                KINK_INPUTENTRY AS inputentry,
                KINK_IDENTIFIER AS identifier,
                KINK_MANUALCALCTYPE AS manualcalctype,
                KINK_UOMID AS uomid,
                KINK_FREQUENCY AS frequency,
                KINK_EXCELNAME AS excelname,
                KINK_DEPT_KEYID AS dept_keyid,
                KINK_COSTAREA AS costarea,
                KINK_TARGETNEED AS targetneed,
                KINK_PILLARID AS pillarid,
                CHILDPATH AS childpath
            FROM
                KPI_TL_INDICATOR,
                KPI_VW_KEYPERINCHILDPATH
            WHERE
                1 = 1
                AND KINK_KEYID <> KINK_PARENTID
                AND KEYID = KINK_KEYID
                AND KINK_ACTIVE = 'Y'
                AND (:parentId IS NULL OR KINK_PARENTID = :parentId)
                AND (:pillarId IS NULL OR KINK_PILLARID = :pillarId)
                AND (:keyId IS NULL OR KINK_KEYID = :keyId)
                AND (:location IS NULL OR KINK_LOCATION = :location)
            ORDER BY
                keyid
            """, nativeQuery = true)

    List<Map<String, Object>> getAllkeyInd(
            @Param("parentId") String parentId,
            @Param("pillarId") String pillarId,
            @Param("keyId") String keyId,
            @Param("location") String location);

    @Query(value = """

            SELECT
                '1' AS keyid,
                '2' AS indicatorname,
                '3' AS indicatorcode,
                '4' AS description,
                '5' AS parentid,
                '6' AS levelno,
                '7' AS sortno,
                '8' AS ischild,
                '9' AS inputtype,
                '10' AS inputentry,
                '11' AS identifier,
                '12' AS manualcalctype,
                '13' AS uomid,
                '14' AS frequency,
                '15' AS excelname,
                '16' AS dept_keyid,
                '17' AS costarea,
                '18' AS targetneed,
                '19' AS pillarid,
                '20' AS childpath
            UNION ALL
            SELECT
                KINK_KEYID AS keyid,
                KINK_INDICATORNAME AS indicatorname,
                KINK_INDICATORCODE AS indicatorcode,
                KINK_DESCRIPTION AS description,
                KINK_PARENTID AS parentid,
                KINK_LEVELNO AS levelno,
                KINK_SORTNO AS sortno,
                KINK_ISCHILD AS ischild,
                KINK_INPUTTYPE AS inputtype,
                KINK_INPUTENTRY AS inputentry,
                KINK_IDENTIFIER AS identifier,
                KINK_MANUALCALCTYPE AS manualcalctype,
                KINK_UOMID AS uomid,
                KINK_FREQUENCY AS frequency,
                KINK_EXCELNAME AS excelname,
                KINK_DEPT_KEYID AS dept_keyid,
                KINK_COSTAREA AS costarea,
                KINK_TARGETNEED AS targetneed,
                KINK_PILLARID AS pillarid,
                CHILDPATH AS childpath
            FROM
                KPI_TL_INDICATOR,
                KPI_VW_KEYPERINCHILDPATH
            WHERE
                1 = 1
                AND KINK_KEYID = KINK_PARENTID
                AND KEYID = KINK_KEYID
                AND KINK_ACTIVE = 'Y'
                AND (:parentId IS NULL OR KINK_PARENTID = :parentId)
                AND (:pillarId IS NULL OR KINK_PILLARID = :pillarId)
                AND (:keyId IS NULL OR KINK_KEYID = :keyId)
                AND (:location IS NULL OR KINK_LOCATION = :location)
                AND (:type IS NULL OR KINK_TYPE = :type)

            ORDER BY
                keyid
            """, nativeQuery = true)
    List<Map<String, Object>> getAllkeyIndvalue(
            @Param("parentId") String parentId,
            @Param("pillarId") String pillarId,
            @Param("keyId") String keyId,
            @Param("location") String location,
            @Param("type") String type);

    @Modifying
    @Transactional
    @Query(value = """
            delete from kpi_tl_indicator where kink_keyid =:keyid
             """, nativeQuery = true)
    int deletebykeyid(@Param("keyid") String keyid);

    @Query(value = """
            WITH RECURSIVE hierarchy AS (
                SELECT 1 AS level, KINK_KEYID, KINK_PARENTID
                FROM KPI_TL_INDICATOR
                WHERE KINK_KEYID = KINK_PARENTID

                UNION ALL

                SELECT h.level + 1, t.KINK_KEYID, t.KINK_PARENTID
                FROM KPI_TL_INDICATOR t
                INNER JOIN hierarchy h
                    ON t.KINK_PARENTID = h.KINK_KEYID
                WHERE t.KINK_KEYID <> t.KINK_PARENTID
            )
            SELECT level
            FROM hierarchy
            WHERE KINK_KEYID = :keyId
            """, nativeQuery = true)
    //Integer getKeyIndicatorLevel(@Param("keyId") String keyId);
    List<Integer> getKeyIndicatorLevels(@Param("keyId") String keyId);


    @Query(
        value = """
            SELECT CNFM_SETTINGVALUE
            FROM ADM_TL_CONFIGURATIONMST
            WHERE CNFM_CODE = 'QTMQUALITYOFCOST'
            AND CURRENT_DATE BETWEEN CNFM_FROMDATE AND CNFM_TILLDATE
        """,
        nativeQuery = true
    )
    //int getConfigkeyIndLevel();
    List<Integer> getConfigkeyIndLevels();


    @Query(
        value = """
            SELECT CNFM_SETTINGVALUE
            FROM ADM_TL_CONFIGURATIONMST
            WHERE CNFM_CODE = 'ENTPROGSTARTMONTH'
            """,
        nativeQuery = true
    )
    String getEntProgStartMonth();


    @Query(
    value = """
        SELECT *
        FROM KPI_TL_INDICATOR
        WHERE (:keyId IS NULL OR KINK_KEYID = :keyId)
          AND (:indicatorName IS NULL OR KINK_INDICATORNAME = :indicatorName)
          AND (:parentId IS NULL OR KINK_PARENTID = :parentId)
        """,
    nativeQuery = true
)
KpiTlIndicator findIndicators(
    @Param("keyId") String keyId,
    @Param("indicatorName") String indicatorName,
    @Param("parentId") String parentId
);

}

