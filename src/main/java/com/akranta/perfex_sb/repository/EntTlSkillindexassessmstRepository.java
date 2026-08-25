package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.EntTlSkillindexassessmst;

public interface EntTlSkillindexassessmstRepository extends JpaRepository<EntTlSkillindexassessmst, String> {

    @Query(value = """
            SELECT TO_CHAR(MAX(m.SIAM_REVIEWDATE), 'YYYY-MM-DD')
            FROM ENT_TL_SKILLINDEXASSESSMST m
            JOIN ENT_TL_SKILLINDEXASSESSDTL d
              ON d.SIAD_SIAM_KEYID = m.SIAM_KEYID
            WHERE m.SIAM_FLID = :flid
              AND m.SIAM_UNIQUEPOSID = :uniqPosid
            """, nativeQuery = true)
    String getLastDoneDate(
            @Param("flid") String flid,
            @Param("uniqPosid") String uniqPosid);

    @Query(value = "SELECT SIAM_KEYID " +
            "FROM ENT_TL_SKILLINDEXASSESSMST " +
            "WHERE SIAM_REVIEWDATE = :reviewDate " +
            "AND SIAM_UNIQUEPOSID = :uniquePosId " +
            "AND SIAM_FLID = :flId LIMIT 1", nativeQuery = true)
    String findSiamMasterKeyId(
            @Param("reviewDate") LocalDateTime reviewDate,
            @Param("uniquePosId") String uniquePosId,
            @Param("flId") String flId);

    // @Query(value = """
    //         SELECT *
    //             FROM (
    //                 SELECT
    //                     '1' AS keyid,
    //                     '2' AS checks,
    //                     '3' AS code,
    //                     '4' AS name,
    //                     '5' AS total

    //                 UNION ALL

    //                 SELECT DISTINCT
    //                     emp.Empm_keyid     AS keyid,
    //                     ''                AS checks,
    //                     emp.empm_code     AS code,
    //                     emp.empm_name     AS name,
    //                     scores.TOT_SCORE  AS total
    //                 FROM
    //                 (
    //                     SELECT DISTINCT
    //                         e.Empm_keyid,
    //                         e.empm_code,
    //                         e.empm_name,
    //                         e.EMPM_ROLEID
    //                     FROM GEN_TL_EMPLOYEEMST e
    //                     INNER JOIN GEN_TL_FNLNROLETEAM frt
    //                         ON e.EMPM_KEYID = frt.FRT_EMPM_KEYID
    //                     INNER JOIN GEN_TL_TEAMTRADELINK frp
    //                         ON frp.FRP_FRT_KEYID = frt.FRT_KEYID
    //                     INNER JOIN GEN_TL_TRADEMST trd
    //                         ON trd.TRDM_KEYID = frp.FRP_TRADEID
    //                     INNER JOIN GEN_TL_EMPTYPE_MST etp
    //                         ON trd.TRDM_CLASSIFICATION = etp.ETPM_CODE
    //                     WHERE frt.FRT_FNLN_KEYID = :flid
    //                       AND etp.ETPM_KEYID = :uniqPosid
    //                       AND e.EMPM_ACTIVE = 'Y'
    //                       AND e.EMPM_EMPLOYEETYPE NOT IN ('M')
    //                 ) emp
    //                 LEFT JOIN
    //                 (
    //                     SELECT
    //                         siad.SIAD_EMPM_KEYID,
    //                         ROUND(
    //                             SUM(siad.SIAD_SCORE)::numeric
    //                             / COUNT(DISTINCT siad.SIAD_CRITERIAID)::numeric,
    //                             2
    //                         ) AS TOT_SCORE
    //                     FROM ENT_TL_SKILLINDEXASSESSMST siam
    //                     INNER JOIN ENT_TL_SKILLINDEXASSESSDTL siad
    //                         ON siad.SIAD_SIAM_KEYID = siam.SIAM_KEYID
    //                     WHERE siam.SIAM_FLID = :flid
    //                       AND siam.SIAM_UNIQUEPOSID = :uniqPosid
    //                       AND DATE_TRUNC('day', siam.SIAM_REVIEWDATE) = CAST(:reviewDate AS DATE)
    //                     GROUP BY siad.SIAD_EMPM_KEYID
    //                 ) scores
    //                     ON scores.SIAD_EMPM_KEYID = emp.Empm_keyid
    //             ) t
    //             ORDER BY
    //                 CASE WHEN t.keyid = '1' THEN 0 ELSE 1 END,
    //                 t.total DESC NULLS LAST;

    //             """, nativeQuery = true)
    // List<Map<String, Object>> getAllEmployees(@Param("flid") String flid,
    //         @Param("uniqPosid") String uniqPosId, @Param("reviewDate") String reviewDate);

    @Query(value = """
            SELECT *
                FROM (
                    SELECT
                        '1' AS keyid,
                        '2' AS checks,
                        '3' AS code,
                        '4' AS name,
                        '5' AS total,
                        '6' AS siamKeyid

                    UNION ALL

                    SELECT DISTINCT
                        emp.Empm_keyid     AS keyid,
                        ''                AS checks,
                        emp.empm_code     AS code,
                        emp.empm_name     AS name,
                        scores.TOT_SCORE  AS total,
                        scores.SIAM_KEYID AS siamKeyid
                    FROM
                    (
                        SELECT DISTINCT
                            e.Empm_keyid,
                            e.empm_code,
                            e.empm_name,
                            e.EMPM_ROLEID
                        FROM GEN_TL_EMPLOYEEMST e
                        INNER JOIN GEN_TL_FNLNROLETEAM frt
                            ON e.EMPM_KEYID = frt.FRT_EMPM_KEYID
                        INNER JOIN GEN_TL_TEAMTRADELINK frp
                            ON frp.FRP_FRT_KEYID = frt.FRT_KEYID
                        INNER JOIN GEN_TL_TRADEMST trd
                            ON trd.TRDM_KEYID = frp.FRP_TRADEID
                        INNER JOIN GEN_TL_EMPTYPE_MST etp
                            ON trd.TRDM_CLASSIFICATION = etp.ETPM_CODE
                        WHERE frt.FRT_FNLN_KEYID = :flid
                          AND etp.ETPM_KEYID = :uniqPosid
                          AND e.EMPM_ACTIVE = 'Y'
                          AND e.EMPM_EMPLOYEETYPE NOT IN ('M')
                    ) emp
                    LEFT JOIN
                    (
                        SELECT
                            siad.SIAD_EMPM_KEYID,
                            MAX(siam.SIAM_KEYID) AS SIAM_KEYID,
                            ROUND(
                                SUM(siad.SIAD_SCORE)::numeric
                                / COUNT(DISTINCT siad.SIAD_CRITERIAID)::numeric,
                                2
                            ) AS TOT_SCORE
                        FROM ENT_TL_SKILLINDEXASSESSMST siam
                        INNER JOIN ENT_TL_SKILLINDEXASSESSDTL siad
                            ON siad.SIAD_SIAM_KEYID = siam.SIAM_KEYID
                        WHERE siam.SIAM_FLID = :flid
                          AND siam.SIAM_UNIQUEPOSID = :uniqPosid
                          AND DATE_TRUNC('day', siam.SIAM_REVIEWDATE) = CAST(:reviewDate AS DATE)
                        GROUP BY siad.SIAD_EMPM_KEYID
                    ) scores
                        ON scores.SIAD_EMPM_KEYID = emp.Empm_keyid
                ) t
                ORDER BY
                    CASE WHEN t.keyid = '1' THEN 0 ELSE 1 END,
                    t.total DESC NULLS LAST;

                """, nativeQuery = true)
    List<Map<String, Object>> getAllEmployees(@Param("flid") String flid,
            @Param("uniqPosid") String uniqPosId, @Param("reviewDate") String reviewDate);

    // @Query(value = """
    //         SELECT *
    //         FROM (
    //             SELECT
    //                 '1' AS keyid,
    //                 '2' AS checks,
    //                 '3' AS code,
    //                 '4' AS name,
    //                 '5' AS lastdate,
    //                 '6' AS total

    //             UNION ALL

    //             SELECT DISTINCT
    //                 emp.Empm_keyid     AS keyid,
    //                 ''                 AS checks,
    //                 emp.empm_code      AS code,
    //                 emp.empm_name      AS name,
    //                 COALESCE(scores.LASTDATE, '') AS lastdate,
    //                 COALESCE(scores.TOT_SCORE::text, '') AS total
    //             FROM
    //             (
    //                 SELECT DISTINCT
    //                     e.Empm_keyid,
    //                     e.empm_code,
    //                     e.empm_name,
    //                     e.EMPM_ROLEID
    //                 FROM GEN_TL_EMPLOYEEMST e
    //                 INNER JOIN GEN_TL_FNLNROLETEAM frt
    //                     ON e.EMPM_KEYID = frt.FRT_EMPM_KEYID
    //                 INNER JOIN GEN_TL_TEAMTRADELINK frp
    //                     ON frp.FRP_FRT_KEYID = frt.FRT_KEYID
    //                 INNER JOIN GEN_TL_TRADEMST trd
    //                     ON trd.TRDM_KEYID = frp.FRP_TRADEID
    //                 INNER JOIN GEN_TL_EMPTYPE_MST etp
    //                     ON trd.TRDM_CLASSIFICATION = etp.ETPM_CODE
    //                 WHERE frt.FRT_FNLN_KEYID = :flid
    //                   AND etp.ETPM_KEYID = :uniqPosid
    //                   AND e.EMPM_ACTIVE = 'Y'
    //                   AND e.EMPM_EMPLOYEETYPE NOT IN ('M')
    //             ) emp
    //             LEFT JOIN
    //             (
    //                 SELECT
    //                     siad.SIAD_EMPM_KEYID,
    //                     ROUND(
    //                         SUM(siad.SIAD_SCORE)::numeric
    //                         / COUNT(DISTINCT siad.SIAD_CRITERIAID)::numeric,
    //                         2
    //                     ) AS TOT_SCORE,
    //                     TO_CHAR(MAX(siam.SIAM_REVIEWDATE), 'DD-MON-YYYY') AS LASTDATE
    //                 FROM ENT_TL_SKILLINDEXASSESSMST siam
    //                 INNER JOIN ENT_TL_SKILLINDEXASSESSDTL siad
    //                     ON siad.SIAD_SIAM_KEYID = siam.SIAM_KEYID
    //                 INNER JOIN (
    //                     SELECT
    //                         SIAD_EMPM_KEYID AS EMP,
    //                         MAX(siam_reviewdate) AS MAXDATE
    //                     FROM ent_tl_skillindexassessmst
    //                     INNER JOIN ent_tl_skillindexassessdtl
    //                         ON siad_siam_keyid = siam_keyid
    //                     WHERE siam_flid = :flid
    //                       AND siam_uniqueposid = :uniqPosid
    //                     GROUP BY SIAD_EMPM_KEYID
    //                 ) AS max_dates
    //                     ON max_dates.EMP = siad.SIAD_EMPM_KEYID
    //                     AND siam.siam_reviewdate = max_dates.MAXDATE
    //                 WHERE siam.SIAM_FLID = :flid
    //                   AND siam.SIAM_UNIQUEPOSID = :uniqPosid
    //                 GROUP BY siad.SIAD_EMPM_KEYID
    //             ) scores
    //                 ON scores.SIAD_EMPM_KEYID = emp.Empm_keyid
    //         ) final_result
    //         ORDER BY
    //             CASE WHEN total = '6' THEN 0 ELSE 1 END,
    //             CASE WHEN total ~ '^[0-9.]+$' THEN total::numeric ELSE 0 END DESC NULLS LAST
    //         """, nativeQuery = true)
    // List<Map<String, Object>> getAllEmployeesMultiple(
    //         @Param("flid") String flid,
    //         @Param("uniqPosid") String uniqPosId);

    @Query(value = """
            SELECT *
            FROM (
                SELECT
                    '1' AS keyid,
                    '2' AS checks,
                    '3' AS code,
                    '4' AS name,
                    '5' AS lastdate,
                    '6' AS total,
                    '7' AS roleKeyid,
                    '8' AS siamKeyid

                UNION ALL

                SELECT DISTINCT
                    emp.Empm_keyid     AS keyid,
                    ''                 AS checks,
                    emp.empm_code      AS code,
                    emp.empm_name      AS name,
                    COALESCE(scores.LASTDATE, '') AS lastdate,
                    COALESCE(scores.TOT_SCORE::text, '') AS total,
                    '' AS roleKeyid,
                    scores.siamKeyid as siamKeyid
                FROM
                (
                    SELECT DISTINCT
                        e.Empm_keyid,
                        e.empm_code,
                        e.empm_name,
                        e.EMPM_ROLEID
                    FROM GEN_TL_EMPLOYEEMST e
                    INNER JOIN GEN_TL_FNLNROLETEAM frt
                        ON e.EMPM_KEYID = frt.FRT_EMPM_KEYID
                    INNER JOIN GEN_TL_TEAMTRADELINK frp
                        ON frp.FRP_FRT_KEYID = frt.FRT_KEYID
                    INNER JOIN GEN_TL_TRADEMST trd
                        ON trd.TRDM_KEYID = frp.FRP_TRADEID
                    INNER JOIN GEN_TL_EMPTYPE_MST etp
                        ON trd.TRDM_CLASSIFICATION = etp.ETPM_CODE
                    WHERE frt.FRT_FNLN_KEYID = :flid
                      AND etp.ETPM_KEYID = :uniqPosid
                      AND e.EMPM_ACTIVE = 'Y'
                      AND e.EMPM_EMPLOYEETYPE NOT IN ('M')
                ) emp
                LEFT JOIN
                (
                    SELECT
                        siad.SIAD_EMPM_KEYID,
                        ROUND(
                            SUM(siad.SIAD_SCORE)::numeric
                            / COUNT(DISTINCT siad.SIAD_CRITERIAID)::numeric,
                            2
                        ) AS TOT_SCORE,
                        TO_CHAR(MAX(siad.SIAD_REVIEWDATE), 'DD-MON-YYYY') AS LASTDATE,
                        MAX(siam.SIAM_KEYID) AS siamKeyid
                    FROM ENT_TL_SKILLINDEXASSESSMST siam
                    INNER JOIN ENT_TL_SKILLINDEXASSESSDTL siad
                        ON siad.SIAD_SIAM_KEYID = siam.SIAM_KEYID
                    INNER JOIN (
                        SELECT
                            SIAD_EMPM_KEYID AS EMP,
                            MAX(siad_reviewdate) AS MAXDATE
                        FROM ent_tl_skillindexassessmst
                        INNER JOIN ent_tl_skillindexassessdtl
                            ON siad_siam_keyid = siam_keyid
                        WHERE siam_flid = :flid
                          AND siam_uniqueposid = :uniqPosid
                        GROUP BY SIAD_EMPM_KEYID
                    ) AS max_dates
                        ON max_dates.EMP = siad.SIAD_EMPM_KEYID
                        AND siad.siad_reviewdate = max_dates.MAXDATE
                    WHERE siam.SIAM_FLID = :flid
                      AND siam.SIAM_UNIQUEPOSID = :uniqPosid
                    GROUP BY siad.SIAD_EMPM_KEYID
                ) scores
                    ON scores.SIAD_EMPM_KEYID = emp.Empm_keyid
            ) final_result
            ORDER BY
                CASE WHEN total = '6' THEN 0 ELSE 1 END,
                CASE WHEN total ~ '^[0-9.]+$' THEN total::numeric ELSE 0 END DESC NULLS LAST
            """, nativeQuery = true)
    List<Map<String, Object>> getAllEmployeesMultiple(
            @Param("flid") String flid,
            @Param("uniqPosid") String uniqPosId);

    @Query(value = """
            SELECT
                '1' AS empm_keyid,
                '2' AS spok_code,
                '3' AS avg_score
            UNION
            SELECT

                    d.siad_empm_keyid AS empm_keyid,
                    s.spok_code AS spok_code,
                    ROUND(
                        SUM(d.siad_score)::numeric
                        / COUNT(DISTINCT d.siad_criteriaid),
                        2
                    ) AS avg_score
                FROM ent_tl_skillindexassessdtl d
                JOIN ent_tl_skillindexassessmst m
                    ON m.siam_keyid = d.siad_siam_keyid
                JOIN ent_tl_skill_reviewpointdet r
                    ON r.sird_keyid = d.siad_reviewid
                LEFT JOIN ent_tl_spokemst s
                    ON s.spok_keyid = r.sird_spok_keyid
                WHERE m.siam_reviewdate::date = TO_DATE(:fromDate, 'DD-MON-YYYY')
                  AND m.siam_flid = :flid
                  AND m.siam_uniqueposid = :uniquePosId
                  AND d.siad_empm_keyid = ANY (
                        string_to_array(:empmKeyIds, ',')
                      )
                GROUP BY d.siad_empm_keyid, s.spok_code
                ORDER BY empm_keyid, spok_code
                """, nativeQuery = true)
    List<Map<String, Object>> getSkillIndexRadarChart(
            @Param("fromDate") String fromDate,
            @Param("flid") String flid,
            @Param("uniquePosId") String uniquePosId,
            @Param("empmKeyIds") String empmKeyIds // e.g. "EMP001,EMP002,EMP003"
    );

    @Query(value = """
            SELECT COUNT(DISTINCT v.frt_empm_keyid)
            FROM gen_vw_fnln_tradeempcount v
            JOIN GEN_TL_EMPLOYEEMST e
                ON v.FRT_EMPM_KEYID = e.EMPM_KEYID
            WHERE v.flid = :flid
            AND e.EMPM_EMPLOYEETYPE <> 'M'
            AND v.trdm_classification =
                CASE :etpmCode
                    WHEN 'ETPM0001' THEN 'M'
                    WHEN 'ETPM0002' THEN 'P'
                    WHEN 'ETPM0003' THEN 'S'
                    WHEN 'ETPM0004' THEN 'T'
                END
            """, nativeQuery = true)
    Long getEmployeeCount(@Param("flid") String flid,
            @Param("etpmCode") String etpmCode);


    @Query(value = """
             SELECT
                  COUNT(DISTINCT SIAD.SIAD_EMPM_KEYID) AS ASSESSED_REMOVED_COUNT
              FROM ENT_TL_SKILLINDEXASSESSMST SIAM
              JOIN ENT_TL_SKILLINDEXASSESSDTL SIAD ON SIAD.SIAD_SIAM_KEYID = SIAM.SIAM_KEYID
              LEFT JOIN GEN_VW_FNLN_TRADEEMPCOUNT VW
                  ON VW.FRT_EMPM_KEYID = SIAD.SIAD_EMPM_KEYID
                 AND VW.FLID = SIAM.SIAM_FLID
                 AND (
                      CASE VW.TRDM_CLASSIFICATION
                          WHEN 'M' THEN 'ETPM0001'
                          WHEN 'P' THEN 'ETPM0002'
                          WHEN 'S' THEN 'ETPM0003'
                          WHEN 'T' THEN 'ETPM0004'
                      END
                 ) = SIAM.SIAM_UNIQUEPOSID
              WHERE SIAM.SIAM_FLID = :flid  AND VW.FRT_EMPM_KEYID IS NULL And  SIAM.SIAM_UNIQUEPOSID = :etpmCode
               AND SIAD.SIAD_SIAM_KEYID = :saimKeyid
                AND SIAD.SIAD_ACTIVE = 'Y'
            """, nativeQuery = true)
    Long getActiveEmployeeCount(@Param("flid") String flid,
            @Param("etpmCode") String etpmCode,@Param("saimKeyid") String saimKeyid);


           


    @Query(value = """
            SELECT siam_reviewdate
            FROM ENT_TL_SKILLINDEXASSESSmst
            WHERE siam_keyid = :siamKeyId
            """, nativeQuery = true)
    LocalDateTime getReviewDate(@Param("siamKeyId") String siamKeyId);

}
