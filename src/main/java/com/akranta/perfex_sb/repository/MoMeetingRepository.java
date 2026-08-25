package com.akranta.perfex_sb.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.GenTlActionPlanMst;
import com.akranta.perfex_sb.model.GenTlMommst;

public interface MoMeetingRepository extends JpaRepository<GenTlMommst, String> {
    @Query(value = """
            SELECT
            '1'        AS moms_meetingno,
            '2' AS moms_ismeetinghappen,
            '3'      AS moms_safetytalk,
            '4'         AS moms_remarks,
            '5'    AS moms_meetingtitle,
            '6'     AS moms_meetingtype,
            '7'          AS moms_agenda,
            '8'        AS moms_pillarid,
            '9'     AS moms_pillargroup
            UNION ALL
            SELECT
            COALESCE(NULLIF(moms_meetingno,'{}'),'')        AS moms_meetingno,
            COALESCE(NULLIF(moms_ismeetinghappen,'{}'),'')  AS moms_ismeetinghappen,
            COALESCE(NULLIF(moms_safetytalk,'{}'),'')       AS moms_safetytalk,
            COALESCE(NULLIF(moms_remarks,'{}'),'')          AS moms_remarks,
            COALESCE(NULLIF(moms_meetingtitle,'{}'),'')     AS moms_meetingtitle,
            COALESCE(NULLIF(moms_meetingtype,'{}'),'')      AS moms_meetingtype,
            COALESCE(NULLIF(moms_agenda,'{}'),'')           AS moms_agenda,
            COALESCE(NULLIF(moms_pillarid,'{}'),'')         AS moms_pillarid,
            COALESCE(NULLIF(moms_pillargroup,'{}'),'')      AS moms_pillargroup
            FROM gen_tl_mommst
            WHERE moms_date = :mstDate
            AND moms_flid = :flid
            AND (
                    (:type = 'JH' AND moms_shiftid = :shift AND moms_meetingtype = 'J')
                OR (:type = 'Dmt' AND moms_meetingtype = 'D')
                OR (:type = 'Production' AND moms_meetingtype = 'PD')
                OR (:type = 'Others' AND moms_meetingtype = 'O')
                OR (:type = 'Pillar' AND moms_meetingtype = 'P'  AND moms_pillarid = :pillarId)
                OR (:type NOT IN ('JH','Dmt','Production','Others','Pillar')
                    AND moms_meetingtype = :type)
            )
            """, nativeQuery = true)
    List<Map<String, Object>> recallMomMst(
            @Param("mstDate") LocalDate mstDate,
            @Param("flid") String flid,
            @Param("shift") String shift,
            @Param("type") String type,
            @Param("pillarId") String pillarId);

    // @Query(value = """
    // SELECT
    // '1' AS momd_keyid,
    // '2' AS momtype,
    // '3' AS TPMP_CODE,
    // '4' AS momdetails,
    // '5' AS kink_keyid,
    // '6' AS kink_indicatorname,
    // '7' AS btn_action_plan,
    // '8' AS MOMD_ACTIONPLAN_ID,
    // '9' AS btn_kpi,
    // '10' AS momremarks,
    // '11' AS mompillar,
    // '12' AS momtype_orig,
    // '13' AS momdetails_orig,
    // '14' AS momremarks_orig
    // UNION ALL
    // SELECT
    // momd_keyid,
    // CASE
    // WHEN momtype = 'OPL' THEN 'OPL'
    // WHEN momtype = 'PM' THEN 'PM'
    // WHEN momtype = 'OTH' THEN 'OTHERS'
    // WHEN momtype = 'MOD' THEN 'MODIFICATION'
    // WHEN momtype = 'CLT' THEN 'CLTI'
    // ELSE momtype
    // END AS momtype,
    // COALESCE(TPMP_CODE, '') AS TPMP_CODE,
    // COALESCE(momdetails, '') AS momdetails,
    // COALESCE(kink_keyid, '') AS kink_keyid,
    // COALESCE(kink_indicatorname, '') AS kink_indicatorname,
    // '' as btn_action_plan,
    // COALESCE(MOMD_ACTIONPLAN_ID, '') AS MOMD_ACTIONPLAN_ID,
    // '' as btn_kpi,
    // COALESCE(momremarks, '') AS momremarks,
    // COALESCE(mompillar, '') AS mompillar,
    // momtype AS momtype_orig,
    // COALESCE(momdetails, '') AS momdetails_orig,
    // COALESCE(momremarks, '') AS momremarks_orig
    // FROM (
    // SELECT
    // momd_keyid,
    // momd_discussion_type as momtype,
    // momd_pillar as mompillar,
    // momd_discussion_details as momdetails,
    // TPMP_CODE,
    // kink_keyid,
    // KINK_INDICATORNAME,
    // APLM_KEYID as MOMD_ACTIONPLAN_ID,
    // momd_remarks as momremarks
    // FROM gen_tl_mommst
    // INNER JOIN gen_tl_momdtl ON moms_keyid = momd_moms_keyid
    // LEFT JOIN GEN_TL_TPMPILLARMST ON TPMP_KEYID = momd_pillar
    // LEFT JOIN gen_tl_actionplanmst ON MomS_Keyid = aplm_masterrefid AND
    // Momd_Keyid = aplm_detailrefid
    // LEFT JOIN (
    // SELECT
    // STRING_AGG(kink_keyid::text, ',' ORDER BY kink_keyid) as kink_keyid,
    // STRING_AGG(kink_indicatorname, ',' ORDER BY kink_indicatorname) as
    // kink_indicatorname,
    // MAX(MOKP_MOMD_KEYID) as MOKP_MOMD_KEYID
    // FROM gen_tl_mom_kpi_link
    // LEFT JOIN kpi_tl_indicator ON mokp_kink_keyid = kink_keyid
    // GROUP BY MOKP_MOMD_KEYID
    // ) kpi_agg ON momd_keyid = MOKP_MOMD_KEYID
    // WHERE
    // (
    // COALESCE(:keyId, '') = ''
    // AND MOMD_MOMS_KEYID IN (
    // SELECT moms_keyid
    // FROM gen_tl_mommst
    // WHERE moms_flid = :flid
    // AND moms_date::date = CAST(:momdate AS DATE)
    // AND (
    // (:type = 'JH' AND moms_shiftid = :shift AND moms_meetingtype = 'J')
    // OR (:type = 'Dmt' AND moms_meetingtype = 'D')
    // OR (:type = 'Production' AND moms_meetingtype = 'PD')
    // OR (:type = 'Others' AND moms_meetingtype = 'O')
    // OR (:type = 'Pillar' AND moms_meetingtype = 'P' AND moms_pillarid =
    // :pillarid)
    // OR (:type NOT IN ('JH','Dmt','Production','Others','Pillar')
    // AND moms_meetingtype = :type)
    // )
    // )
    // )
    // OR
    // (
    // COALESCE(:keyId, '') <> ''
    // AND MOMD_MOMS_KEYID = :keyId
    // )
    // ) sub ORDER BY momd_keyid
    // """, nativeQuery = true)
    // List<Map<String, Object>> recallMomMstGridList(
    // @Param("keyId") String keyId,
    // @Param("flid") String flid,
    // @Param("momdate") String momdate,
    // @Param("shift") String shift,
    // @Param("type") String type,
    // @Param("pillarid") String pillarid);

    // @Query(value = """
    //         SELECT
    //             '1' AS momd_keyid,
    //             '2' AS momtype,
    //             '3' AS TPMP_CODE,
    //             '4' AS momdetails,
    //             '5' AS kink_keyid,
    //             '6' AS kink_indicatorname,
    //             '7' AS btn_action_plan,
    //             '8' AS MOMD_ACTIONPLAN_ID,
    //             '9' AS btn_kpi,
    //             '10' AS momremarks,
    //             '11' AS mompillar,
    //             '12' AS momtype_orig,
    //             '13' AS momdetails_orig,
    //             '14' AS momremarks_orig
    //         UNION ALL
    //         SELECT
    //             momd_keyid,
    //             CASE
    //                 WHEN momtype = 'OPL' THEN 'OPL'
    //                 WHEN momtype = 'PM' THEN 'PM'
    //                 WHEN momtype = 'OTH' THEN 'OTHERS'
    //                 WHEN momtype = 'MOD' THEN 'MODIFICATION'
    //                 WHEN momtype = 'CLT' THEN 'CLTI'
    //                 ELSE momtype
    //             END AS momtype,
    //             COALESCE(TPMP_CODE, '') AS TPMP_CODE,
    //             COALESCE(momdetails, '') AS momdetails,
    //             COALESCE(kink_keyid, '') AS kink_keyid,
    //             COALESCE(kink_indicatorname, '') AS kink_indicatorname,
    //             '' as btn_action_plan,
    //             COALESCE(MOMD_ACTIONPLAN_ID, '') AS MOMD_ACTIONPLAN_ID,
    //             '' as btn_kpi,
    //             COALESCE(momremarks, '') AS momremarks,
    //             COALESCE(mompillar, '') AS mompillar,
    //             momtype AS momtype_orig,
    //             COALESCE(momdetails, '') AS momdetails_orig,
    //             COALESCE(momremarks, '') AS momremarks_orig
    //         FROM (
    //             SELECT
    //                 momd_keyid,
    //                 momd_discussion_type as momtype,
    //                 momd_pillar as mompillar,
    //                 momd_discussion_details as momdetails,
    //                 TPMP_CODE,
    //                 kink_keyid,
    //                 KINK_INDICATORNAME,
    //                 APLM_KEYID as MOMD_ACTIONPLAN_ID,
    //                 momd_remarks as momremarks
    //             FROM gen_tl_mommst
    //             INNER JOIN gen_tl_momdtl ON moms_keyid = momd_moms_keyid
    //             LEFT JOIN GEN_TL_TPMPILLARMST ON TPMP_KEYID = momd_pillar
    //             LEFT JOIN gen_tl_actionplanmst ON MomS_Keyid = aplm_masterrefid AND Momd_Keyid = aplm_detailrefid
    //             LEFT JOIN (
    //                 SELECT
    //                     STRING_AGG(kink_keyid::text, ',' ORDER BY kink_keyid) as kink_keyid,
    //                     STRING_AGG(kink_indicatorname, ',' ORDER BY kink_indicatorname) as kink_indicatorname,
    //                     MAX(MOKP_MOMD_KEYID) as MOKP_MOMD_KEYID
    //                 FROM gen_tl_mom_kpi_link
    //                 LEFT JOIN kpi_tl_indicator ON mokp_kink_keyid = kink_keyid
    //                 GROUP BY MOKP_MOMD_KEYID
    //             ) kpi_agg ON momd_keyid = MOKP_MOMD_KEYID
    //             WHERE
    //                 (
    //                     COALESCE(:keyId, '') = ''
    //                     AND MOMD_MOMS_KEYID IN (
    //                         SELECT moms_keyid
    //                         FROM gen_tl_mommst
    //                         WHERE moms_flid = :flid
    //                           AND moms_date::date = CAST(:momdate AS DATE)
    //                           AND (
    //                                 (:type = 'JH' AND moms_shiftid = :shift AND moms_meetingtype = 'J')
    //                              OR (:type = 'Dmt' AND moms_meetingtype = 'D')
    //                              OR (:type = 'Production' AND moms_meetingtype = 'PD')
    //                              OR (:type = 'Others' AND moms_meetingtype = 'O')
    //                              OR (:type = 'Pillar' AND moms_meetingtype = 'P' AND moms_pillarid = :pillarid)
    //                              OR (:type NOT IN ('JH','Dmt','Production','Others','Pillar')
    //                                  AND moms_meetingtype = :type)
    //                           )
    //                     )
    //                 )
    //                 OR
    //                 (
    //                     COALESCE(:keyId, '') <> ''
    //                     AND MOMD_MOMS_KEYID = :keyId
    //                 )
    //         ) sub ORDER BY momd_keyid
    //         """, nativeQuery = true)
    // List<Map<String, Object>> recallMomMstGridList(
    //         @Param("keyId") String keyId,
    //         @Param("flid") String flid,
    //         @Param("momdate") String momdate,
    //         @Param("shift") String shift,
    //         @Param("type") String type,
    //         @Param("pillarid") String pillarid);


             @Query(value = """
            SELECT
                '1' AS momd_keyid,
                '2' AS momtype,
                '3' AS TPMP_CODE,
                '4' AS momdetails,
                '5' AS kink_keyid,
                '6' AS kink_indicatorname,
                '7' AS btn_action_plan,
                '8' AS MOMD_ACTIONPLAN_ID,
                '9' AS btn_kpi,
                '10' AS aplm_status,  
                '11' AS momremarks,
                '12' AS mompillar,
                '13' AS momtype_orig,
                '14' AS momdetails_orig,
                '15' AS momremarks_orig
            UNION ALL
            SELECT
                momd_keyid,
                CASE
                    WHEN momtype = 'OPL' THEN 'OPL'
                    WHEN momtype = 'PM' THEN 'PM'
                    WHEN momtype = 'OTH' THEN 'OTHERS'
                    WHEN momtype = 'MOD' THEN 'MODIFICATION'
                    WHEN momtype = 'CLT' THEN 'CLTI'
                    ELSE momtype
                END AS momtype,
                COALESCE(TPMP_CODE, '') AS TPMP_CODE,
                COALESCE(momdetails, '') AS momdetails,
                COALESCE(kink_keyid, '') AS kink_keyid,
                COALESCE(kink_indicatorname, '') AS kink_indicatorname,
                '' as btn_action_plan,
                COALESCE(MOMD_ACTIONPLAN_ID, '') AS MOMD_ACTIONPLAN_ID,
                '' as btn_kpi,
                CASE
                    WHEN UPPER(aplm_status) = 'P' THEN 'PENDING'
                    WHEN UPPER(aplm_status) = 'C' THEN 'COMPLETED'
                    ELSE aplm_status
                END AS  aplm_status,
                COALESCE(momremarks, '') AS momremarks,
                COALESCE(mompillar, '') AS mompillar,
                momtype AS momtype_orig,
                COALESCE(momdetails, '') AS momdetails_orig,
                COALESCE(momremarks, '') AS momremarks_orig
            FROM (
                SELECT
                    momd_keyid,
                    momd_discussion_type as momtype,
                    momd_pillar as mompillar,
                    momd_discussion_details as momdetails,
                    TPMP_CODE,
                    kink_keyid,
                    KINK_INDICATORNAME,
                    APLM_KEYID as MOMD_ACTIONPLAN_ID,
                    aplm_status,
                    momd_remarks as momremarks
                FROM gen_tl_mommst
                INNER JOIN gen_tl_momdtl ON moms_keyid = momd_moms_keyid
                LEFT JOIN GEN_TL_TPMPILLARMST ON TPMP_KEYID = momd_pillar
                LEFT JOIN gen_tl_actionplanmst ON MomS_Keyid = aplm_masterrefid AND Momd_Keyid = aplm_detailrefid
                LEFT JOIN (
                    SELECT
                        STRING_AGG(kink_keyid::text, ',' ORDER BY kink_keyid) as kink_keyid,
                        STRING_AGG(kink_indicatorname, ',' ORDER BY kink_indicatorname) as kink_indicatorname,
                        MAX(MOKP_MOMD_KEYID) as MOKP_MOMD_KEYID
                    FROM gen_tl_mom_kpi_link
                    LEFT JOIN kpi_tl_indicator ON mokp_kink_keyid = kink_keyid
                    GROUP BY MOKP_MOMD_KEYID
                ) kpi_agg ON momd_keyid = MOKP_MOMD_KEYID
                WHERE
                    (
                        COALESCE(:keyId, '') = ''
                        AND MOMD_MOMS_KEYID IN (
                            SELECT moms_keyid
                            FROM gen_tl_mommst
                            WHERE moms_flid = :flid
                              AND moms_date::date = CAST(:momdate AS DATE)
                              AND (
                                    (:type = 'JH' AND moms_shiftid = :shift AND moms_meetingtype = 'J')
                                 OR (:type = 'Dmt' AND moms_meetingtype = 'D')
                                 OR (:type = 'Production' AND moms_meetingtype = 'PD')
                                 OR (:type = 'Others' AND moms_meetingtype = 'O')
                                 OR (:type = 'Pillar' AND moms_meetingtype = 'P' AND moms_pillarid = :pillarid)
                                 OR (:type NOT IN ('JH','Dmt','Production','Others','Pillar')
                                     AND moms_meetingtype = :type)
                              )
                        )
                    )
                    OR
                    (
                        COALESCE(:keyId, '') <> ''
                        AND MOMD_MOMS_KEYID = :keyId
                    )
            ) sub ORDER BY momd_keyid
            """, nativeQuery = true)
    List<Map<String, Object>> recallMomMstGridList(
            @Param("keyId") String keyId,
            @Param("flid") String flid,
            @Param("momdate") String momdate,
            @Param("shift") String shift,
            @Param("type") String type,
            @Param("pillarid") String pillarid);

    @Query(value = """
            SELECT
            '1' AS VISI_KEYID,
            '2' AS VISI_VISITORNAME,
            '3' AS VISI_PURPOSE,
            '4' AS btn_delete
            UNION ALL
            SELECT
            COALESCE(v.VISI_KEYID, '') AS VISI_KEYID,
            COALESCE(v.VISI_VISITORNAME, '') AS VISI_VISITORNAME,
            COALESCE(v.VISI_PURPOSE, '') AS VISI_PURPOSE,
            '' AS btn_delete


            FROM GEN_TL_VISITORS v, GEN_TL_MOMMST m
            WHERE v.VISI_MOMS_KEYID = m.MOMS_KEYID
              AND (
                    (:isValidMasterKeyid = true AND :isValidRecall = false
                     AND v.VISI_MOMS_KEYID = :masterKeyid)
                 OR (
                      (:isValidMasterKeyid = false OR :isValidRecall = true)
                      AND v.VISI_MOMS_KEYID IN (
                          SELECT m2.moms_keyid
                          FROM gen_tl_mommst m2
                          WHERE m2.moms_flid = :flid
                            AND m2.moms_date = CAST(:date AS DATE)
                            AND (
                                  (:type = 'JH' AND m2.moms_shiftid = :shift AND m2.moms_meetingtype = 'J')
                               OR (:type = 'Dmt' AND m2.moms_meetingtype = 'D')
                               OR (:type = 'Production' AND m2.moms_meetingtype = 'PD')
                               OR (:type = 'Others' AND m2.moms_meetingtype = 'O')
                               OR (:type = 'Pillar' AND m2.moms_pillarid = :pillarid AND m2.moms_meetingtype = 'P')
                               OR (:type NOT IN ('JH', 'Dmt', 'Production', 'Others', 'Pillar')
                                   AND m2.moms_meetingtype = :type)
                            )
                      )
                    )
                  )
            """, nativeQuery = true)
    List<Map<String, Object>> momGridVisitors(@Param("isValidMasterKeyid") boolean isValidMasterKeyid,
            @Param("isValidRecall") boolean isValidRecall,
            @Param("masterKeyid") String masterKeyid,
            @Param("flid") String flid,
            @Param("date") String date,
            @Param("type") String type,
            @Param("shift") String shift,
            @Param("pillarid") String pillarid);

    // @Query(value = """
    // SELECT
    // '1' AS momd_keyid,
    // '2' AS momtype_desc,
    // '3' AS TPMP_CODE,
    // '4' AS momdetails,
    // '5' AS kink_keyid,
    // '6' AS kink_indicatorname,
    // '7' AS col1,
    // '8' AS ApldActionplan,
    // '9' AS ApldStatus,
    // '10' AS col2,
    // '11' AS ApldTargetdate,
    // '12' AS cmbSdadResponsibility,
    // '13' AS Responsibiltyby,
    // '14' AS MOMD_ACTIONPLAN_ID,
    // '15' AS APLD_KEYID,
    // '16' AS col3,
    // '17' AS momremarks,
    // '18' AS mompillar,
    // '19' AS momtype
    // UNION ALL
    // SELECT
    // momd_keyid,
    // CASE momtype
    // WHEN 'OPL' THEN 'OPL'
    // WHEN 'PM' THEN 'PM'
    // WHEN 'OTH' THEN 'OTHERS'
    // WHEN 'MOD' THEN 'MODIFICATION'
    // WHEN 'CLT' THEN 'CLTI'
    // END AS momtype_desc,
    // TPMP_CODE,
    // momdetails,
    // kink_keyid,
    // kink_indicatorname,
    // '' AS col1,
    // ApldActionplan,
    // ApldStatus,
    // '' AS col2,
    // ApldTargetdate,
    // cmbSdadResponsibility,
    // Responsibiltyby,
    // MOMD_ACTIONPLAN_ID,
    // APLD_KEYID,
    // '' AS col3,
    // momremarks,
    // mompillar,
    // momtype
    // FROM (
    // SELECT
    // momd_keyid,
    // momd_discussion_type AS momtype,
    // momd_pillar AS mompillar,
    // tpmp.tpmp_code AS TPMP_CODE,
    // momd_discussion_details AS momdetails,
    // kpi_agg.kink_keyid,
    // kpi_agg.kink_indicatorname,
    // apld_actionplan AS ApldActionplan,
    // CASE apld_status
    // WHEN 'P' THEN 'PENDING'
    // WHEN 'C' THEN 'COMPLETED'
    // END AS ApldStatus,
    // TO_CHAR(apld_targetdate, 'DD-MON-YYYY') AS ApldTargetdate,
    // empm_name || '-' || empm_code AS cmbSdadResponsibility,
    // apld_responsibility AS Responsibiltyby,
    // aplm_keyid AS MOMD_ACTIONPLAN_ID,
    // apld_keyid AS APLD_KEYID,
    // momd_remarks AS momremarks
    // FROM gen_tl_mommst
    // INNER JOIN gen_tl_momdtl
    // ON moms_keyid = momd_moms_keyid

    // LEFT JOIN gen_tl_tpmpillarmst tpmp
    // ON tpmp.tpmp_keyid = momd_pillar

    // LEFT JOIN gen_tl_actionplanmst
    // ON moms_keyid = aplm_masterrefid
    // AND momd_keyid = aplm_detailrefid

    // LEFT JOIN gen_tl_actionplandtl
    // ON apld_aplm_keyid = aplm_keyid

    // LEFT JOIN gen_tl_employeemst
    // ON empm_keyid = apld_responsibility

    // LEFT JOIN (
    // SELECT
    // STRING_AGG(kink_keyid::text, ',' ORDER BY kink_keyid) AS kink_keyid,
    // STRING_AGG(kink_indicatorname, ',' ORDER BY kink_indicatorname) AS
    // kink_indicatorname,
    // MAX(mokp_momd_keyid) AS mokp_momd_keyid
    // FROM gen_tl_mom_kpi_link
    // LEFT JOIN kpi_tl_indicator
    // ON mokp_kink_keyid = kink_keyid
    // GROUP BY mokp_momd_keyid
    // ) kpi_agg
    // ON momd_keyid = kpi_agg.mokp_momd_keyid

    // WHERE momd_moms_keyid IN (
    // SELECT moms_keyid
    // FROM gen_tl_mommst
    // WHERE (
    // (:flid IS NOT NULL AND :flid <> '' AND :momdate IS NOT NULL)
    // AND moms_flid = :flid
    // AND CAST(moms_date AS DATE) = CAST(:momdate AS DATE)
    // )
    // OR (
    // (:flid IS NULL OR :flid = '' OR :momdate IS NULL)
    // AND moms_keyid = :momsKeyId
    // )
    // AND (
    // (:type = 'JH' AND moms_shiftid = :shift AND moms_meetingtype = 'J')
    // OR (:type = 'Dmt' AND moms_meetingtype = 'D')
    // OR (:type = 'Production' AND moms_meetingtype = 'PD')
    // OR (:type = 'Others' AND moms_meetingtype = 'O')
    // OR (:type = 'Pillar' AND moms_meetingtype = 'P' AND moms_pillarid =
    // :pillarid)
    // OR (:type IS NULL OR :type = '')
    // )
    // )
    // ) subquery
    // ORDER BY momd_keyid
    // """, nativeQuery = true)
    // List<Map<String, Object>> getNewMomGrid(
    // @Param("momsKeyId") String momsKeyId,
    // @Param("flid") String flid,
    // @Param("type") String type,
    // @Param("momdate") String momdate,
    // @Param("shift") String shift,
    // @Param("pillarid") String pillarid);

    // @Query(value = """
    // SELECT
    // '1' AS momd_keyid,
    // '2' AS momtype_desc,
    // '3' AS TPMP_CODE,
    // '4' AS momdetails,
    // '5' AS kink_keyid,
    // '6' AS kink_indicatorname,
    // '7' AS col1,
    // '8' AS ApldActionplan,
    // '9' AS ApldStatus,
    // '10' AS col2,
    // '11' AS ApldTargetdate,
    // '12' AS cmbSdadResponsibility,
    // '13' AS Responsibiltyby,
    // '14' AS MOMD_ACTIONPLAN_ID,
    // '15' AS APLD_KEYID,
    // '16' AS col3,
    // '17' AS momremarks,
    // '18' AS mompillar,
    // '19' AS momtype
    // UNION ALL
    // SELECT
    // momd_keyid,
    // CASE momtype
    // WHEN 'OPL' THEN 'OPL'
    // WHEN 'PM' THEN 'PM'
    // WHEN 'OTH' THEN 'OTHERS'
    // WHEN 'MOD' THEN 'MODIFICATION'
    // WHEN 'CLT' THEN 'CLTI'
    // END AS momtype_desc,
    // TPMP_CODE,
    // momdetails,
    // kink_keyid,
    // kink_indicatorname,
    // '' AS col1,
    // ApldActionplan,
    // ApldStatus,
    // '' AS col2,
    // ApldTargetdate,
    // cmbSdadResponsibility,
    // Responsibiltyby,
    // MOMD_ACTIONPLAN_ID,
    // APLD_KEYID,
    // '' AS col3,
    // momremarks,
    // mompillar,
    // momtype
    // FROM (
    // SELECT
    // momd_keyid,
    // momd_discussion_type AS momtype,
    // momd_pillar AS mompillar,
    // tpmp.tpmp_code AS TPMP_CODE,
    // momd_discussion_details AS momdetails,
    // kpi_agg.kink_keyid,
    // kpi_agg.kink_indicatorname,
    // apld_actionplan AS ApldActionplan,
    // CASE apld_status
    // WHEN 'P' THEN 'PENDING'
    // WHEN 'C' THEN 'COMPLETED'
    // END AS ApldStatus,
    // TO_CHAR(apld_targetdate, 'DD-MON-YYYY') AS ApldTargetdate,
    // empm_name || '-' || empm_code AS cmbSdadResponsibility,
    // apld_responsibility AS Responsibiltyby,
    // aplm_keyid AS MOMD_ACTIONPLAN_ID,
    // apld_keyid AS APLD_KEYID,
    // momd_remarks AS momremarks
    // FROM gen_tl_mommst
    // INNER JOIN gen_tl_momdtl
    // ON moms_keyid = momd_moms_keyid

    // LEFT JOIN gen_tl_tpmpillarmst tpmp
    // ON tpmp.tpmp_keyid = momd_pillar

    // LEFT JOIN gen_tl_actionplanmst
    // ON moms_keyid = aplm_masterrefid
    // AND momd_keyid = aplm_detailrefid

    // LEFT JOIN gen_tl_actionplandtl
    // ON apld_aplm_keyid = aplm_keyid

    // LEFT JOIN gen_tl_employeemst
    // ON empm_keyid = apld_responsibility

    // LEFT JOIN (
    // SELECT
    // STRING_AGG(kink_keyid::text, ',' ORDER BY kink_keyid) AS kink_keyid,
    // STRING_AGG(kink_indicatorname, ',' ORDER BY kink_indicatorname) AS
    // kink_indicatorname,
    // MAX(mokp_momd_keyid) AS mokp_momd_keyid
    // FROM gen_tl_mom_kpi_link
    // LEFT JOIN kpi_tl_indicator
    // ON mokp_kink_keyid = kink_keyid
    // GROUP BY mokp_momd_keyid
    // ) kpi_agg
    // ON momd_keyid = kpi_agg.mokp_momd_keyid

    // WHERE momd_moms_keyid IN (
    // SELECT moms_keyid
    // FROM gen_tl_mommst
    // WHERE ((
    // (:flid IS NOT NULL AND :flid <> '' AND :momdate IS NOT NULL)
    // AND moms_flid = :flid
    // AND CAST(moms_date AS DATE) = TO_DATE(:momdate, 'DD-Mon-YYYY')
    // )
    // OR (
    // (:flid IS NULL OR :flid = '' OR :momdate IS NULL)
    // AND moms_keyid = :momsKeyId
    // )
    // )
    // AND (
    // (:type = 'JH' AND moms_shiftid = :shift AND moms_meetingtype = 'J')
    // OR (:type = 'Dmt' AND moms_meetingtype = 'D')
    // OR (:type = 'Production' AND moms_meetingtype = 'PD')
    // OR (:type = 'Others' AND moms_meetingtype = 'O')
    // OR (:type = 'Pillar' AND moms_meetingtype = 'P' AND moms_pillarid =
    // :pillarid)
    // OR (:type IS NULL OR :type = '')
    // )
    // )
    // ) subquery
    // ORDER BY momd_keyid
    // """, nativeQuery = true)
    // List<Map<String, Object>> getNewMomGrid(
    // @Param("momsKeyId") String momsKeyId,
    // @Param("flid") String flid,
    // @Param("type") String type,
    // @Param("momdate") String momdate,
    // @Param("shift") String shift,
    // @Param("pillarid") String pillarid);

    @Query(value = """
            SELECT
                 '1' AS momd_keyid,
                 '2' AS momtype_desc,
                 '3' AS TPMP_CODE,
                 '4' AS momdetails,
                 '5' AS kink_keyid,
                 '6' AS kink_indicatorname,
                 '7' AS col1,
                 '8' AS ApldActionplan,
                 '9' AS ApldStatus,
                 '10' AS col2,
                 '11' AS ApldTargetdate,
                 '12' AS cmbSdadResponsibility,
                 '13' AS Responsibiltyby,
                 '14' AS MOMD_ACTIONPLAN_ID,
                 '15' AS APLD_KEYID,
                 '16' AS col3,
                 '17' AS momremarks,
                 '18' AS mompillar,
                 '19' AS momtype
            UNION ALL
            SELECT
                 momd_keyid,
                 CASE momtype
                     WHEN 'OPL' THEN 'OPL'
                     WHEN 'PM'  THEN 'PM'
                     WHEN 'OTH' THEN 'OTHERS'
                     WHEN 'MOD' THEN 'MODIFICATION'
                     WHEN 'CLT' THEN 'CLTI'
                 END AS momtype_desc,
                 TPMP_CODE,
                 momdetails,
                 kink_keyid,
                 kink_indicatorname,
                 '' AS col1,
                 ApldActionplan,
                 ApldStatus,
                 '' AS col2,
                 ApldTargetdate,
                 cmbSdadResponsibility,
                 Responsibiltyby,
                 MOMD_ACTIONPLAN_ID,
                 APLD_KEYID,
                 '' AS col3,
                 momremarks,
                 mompillar,
                 momtype
            FROM (
                 SELECT
                     momd_keyid,
                     momd_discussion_type AS momtype,
                     momd_pillar AS mompillar,
                     tpmp.tpmp_code AS TPMP_CODE,
                     momd_discussion_details AS momdetails,
                     kpi_agg.kink_keyid,
                     kpi_agg.kink_indicatorname,
                     apld_actionplan AS ApldActionplan,
                     CASE apld_status
                         WHEN 'P' THEN 'PENDING'
                         WHEN 'C' THEN 'COMPLETED'
                     END AS ApldStatus,
                     TO_CHAR(apld_targetdate, 'DD-MON-YYYY') AS ApldTargetdate,
                     empm_name || '-' || empm_code AS cmbSdadResponsibility,
                     apld_responsibility AS Responsibiltyby,
                     aplm_keyid AS MOMD_ACTIONPLAN_ID,
                     apld_keyid AS APLD_KEYID,
                     momd_remarks AS momremarks
                 FROM gen_tl_mommst
                 INNER JOIN gen_tl_momdtl
                     ON moms_keyid = momd_moms_keyid

                 LEFT JOIN gen_tl_tpmpillarmst tpmp
                     ON tpmp.tpmp_keyid = momd_pillar

                 LEFT JOIN gen_tl_actionplanmst
                     ON moms_keyid = aplm_masterrefid
                    AND momd_keyid = aplm_detailrefid

                 LEFT JOIN gen_tl_actionplandtl
                     ON apld_aplm_keyid = aplm_keyid

                 LEFT JOIN gen_tl_employeemst
                     ON empm_keyid = apld_responsibility

                 LEFT JOIN (
                     SELECT
                         STRING_AGG(kink_keyid::text, ',' ORDER BY kink_keyid) AS kink_keyid,
                         STRING_AGG(kink_indicatorname, ',' ORDER BY kink_indicatorname) AS kink_indicatorname,
                         MAX(mokp_momd_keyid) AS mokp_momd_keyid
                     FROM gen_tl_mom_kpi_link
                     LEFT JOIN kpi_tl_indicator
                         ON mokp_kink_keyid = kink_keyid
                     GROUP BY mokp_momd_keyid
                 ) kpi_agg
                     ON momd_keyid = kpi_agg.mokp_momd_keyid

                 WHERE momd_moms_keyid IN (
                     SELECT moms_keyid
                     FROM gen_tl_mommst
                     WHERE ((
                             (:flid IS NOT NULL AND :flid <> '' AND :momdate IS NOT NULL)
                             AND moms_flid = :flid
                             AND CAST(moms_date AS DATE) = TO_DATE(:momdate, 'DD-Mon-YYYY')
                           )
                        OR (
                             (:flid IS NULL OR :flid = '' OR :momdate IS NULL)
                             AND moms_keyid = :momsKeyId
                           )
                            )
                       AND (
                             (:type = 'JH' AND (:shift IS NULL OR moms_shiftid = :shift) AND moms_meetingtype = 'J')
                          OR (:type = 'Dmt' AND moms_meetingtype = 'D')
                          OR (:type = 'Production' AND moms_meetingtype = 'PD')
                          OR (:type = 'Others' AND moms_meetingtype = 'O')
                          OR (:type = 'Pillar' AND moms_meetingtype = 'P' AND  (:pillarid IS NULL OR moms_pillarid = :pillarid))
                          OR (:type IS NULL OR :type = '')
                       )
                 )
            ) subquery
            ORDER BY momd_keyid
            """, nativeQuery = true)
    List<Map<String, Object>> getNewMomGrid(
            @Param("momsKeyId") String momsKeyId,
            @Param("flid") String flid,
            @Param("type") String type,
            @Param("momdate") String momdate,
            @Param("shift") String shift,
            @Param("pillarid") String pillarid);

    @Query(value = """
            SELECT DISTINCT ACHM_ACTIVITY AS Activity
            FROM GEN_TL_JHACTIVITYCHARTMST
            LEFT JOIN GEN_TL_JHACTIVITYCHARTDTL ON ACHM_KEYID = JACD_ACHM_KEYID
            INNER JOIN GEN_MV_FLIDHIERARCHY ON ACHM_FLID = FLID
            WHERE ACHM_FREQUENCY = CASE
                WHEN ACHM_FREQUENCY ~ '^[0-9]+$'
                THEN TO_CHAR(CAST(:momdate AS DATE), 'D')
                ELSE ACHM_FREQUENCY
            END
            AND ACHM_FREQUENCY <> 'D'
            AND FLID = :flid
            AND LEVEL < 7
            """, nativeQuery = true)
    List<Map<String, Object>> fillagendadata(@Param("momdate") String momdate,
            @Param("flid") String flid);

    // Delete All

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GEN_TL_MOM_KPI_LINK WHERE MOKP_MOMD_KEYID = :momdKeyId", nativeQuery = true)
    int deleteFromMomKpiLink(@Param("momdKeyId") String momdKeyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GEN_TL_MOMDTL WHERE MOMD_KEYID = :momdKeyId", nativeQuery = true)
    int deleteFromMomdtl(@Param("momdKeyId") String momdKeyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GEN_TL_ACTIONPLANDTL WHERE APLD_APLM_KEYID = :aplmKeyId", nativeQuery = true)
    int deleteFromActionPlanDtl(@Param("aplmKeyId") String aplmKeyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GEN_TL_ACTIONPLANMST a WHERE NOT EXISTS " +
            "(SELECT APLD_APLM_KEYID FROM GEN_TL_ACTIONPLANDTL " +
            "WHERE APLD_APLM_KEYID = a.APLM_KEYID AND APLD_APLM_KEYID = :aplmKeyId) " +
            "AND APLM_KEYID = :aplmKeyId", nativeQuery = true)
    int deleteFromActionPlanMst(@Param("aplmKeyId") String aplmKeyId);

    // *****************************************DELETE FULL MOM
    // ***********************************/
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GEN_TL_MOM_KPI_LINK WHERE MOKP_MOMS_KEYID = :momKeyId", nativeQuery = true)
    int deleteKpi(@Param("momKeyId") String momKeyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GEN_TL_MOMDTL WHERE MOMD_MOMS_KEYID = :momKeyId", nativeQuery = true)
    int deleteDtl(@Param("momKeyId") String momKeyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GEN_TL_VISITORS WHERE VISI_MOMS_KEYID = :momKeyId", nativeQuery = true)
    int deleteVisitors(@Param("momKeyId") String momKeyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GEN_TL_MOMATTENDANCE WHERE MOMA_MOMS_KEYID = :momKeyId", nativeQuery = true)
    int deleteAttendance(@Param("momKeyId") String momKeyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GEN_TL_MOMMST WHERE MOMS_KEYID = :momKeyId", nativeQuery = true)
    int deleteMst(@Param("momKeyId") String momKeyId);

    @Query(value = """
            SELECT MOMD_KEYID
            FROM GEN_TL_MOMDTL
            WHERE MOMD_MOMS_KEYID = :momsKeyId
            LIMIT 1
            """, nativeQuery = true)
    String getMomDetailIdActionPlan(@Param("momsKeyId") String momsKeyId);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE GEN_TL_ACTIONPLANMST
            SET APLM_STATUS = COALESCE(
                (SELECT DISTINCT APLD_STATUS
                 FROM GEN_TL_ACTIONPLANDTL
                 WHERE APLD_APLM_KEYID = :keyId
                 AND APLD_STATUS = 'P'
                 LIMIT 1),
            'C')
            WHERE APLM_KEYID = :keyId
            """, nativeQuery = true)
    int updateStatusFromDetail(@Param("keyId") String keyId);

    @Query(value = """
            SELECT MAX(MOMD_KEYID)
            FROM GEN_TL_MOMDTL
            WHERE MOMD_MOMS_KEYID = :momsKeyId
            """, nativeQuery = true)
    String getMaxMomDetailId(@Param("momsKeyId") String momsKeyId);

    @Query(value = "SELECT fnln_keyid FROM gen_tl_functionallocn WHERE fnln_originalid = :originalId", nativeQuery = true)
    String findFunctionAllocnKeyId(@Param("originalId") String originalId);

}
