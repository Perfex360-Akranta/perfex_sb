package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.OplTlMst;

import java.util.List;
import java.util.Map;

public interface OplTlMstRepository extends JpaRepository<OplTlMst, String> {

    @Query(value = """
WITH meta AS (
    SELECT
        displaycode,
        COALESCE(cell_keyid,'') AS cell_keyid,
        COALESCE(fnln_originalid,'') AS org_id
    FROM gen_vw_fnln
    WHERE fnln_keyid = :cellId
),
anchor AS (
    SELECT
        CASE
            WHEN UPPER(displaycode)='MCHM' AND cell_keyid <> ''
                THEN (SELECT fnln_keyid FROM gen_vw_fnln WHERE fnln_originalid = cell_keyid LIMIT 1)
            ELSE :cellId
        END AS anchor_flid,
        CASE
            WHEN UPPER(displaycode)='MCHM' AND cell_keyid <> '' THEN cell_keyid
            ELSE org_id
        END AS anchor_originalid
    FROM meta
),
frt AS (
    SELECT DISTINCT rt.frt_fnln_keyid, rt.frt_empm_keyid, rt.frt_role_keyid
    FROM gen_tl_fnlnroleteam rt
    WHERE rt.frt_fnln_keyid IN (
        SELECT h.flid
        FROM gen_mv_flidhierarchy h, anchor a
        WHERE (a.anchor_originalid <> '' AND h.fnln_originalid = a.anchor_originalid)
           OR POSITION(a.anchor_flid IN (COALESCE(h.parentflids,'') || '-' || COALESCE(h.flid,''))) > 0
    )
),
agg AS (
    SELECT
        s.opll_oplm_keyid AS oplmkeyid,
        s.opll_teacher,
        s.opll_student,

        SUM(CASE WHEN s.opll_mtrx_keyid = 'MTX0000001' THEN 1 ELSE 0 END) AS mtx1,
        SUM(CASE WHEN s.opll_mtrx_keyid = 'MTX0000002' THEN 1 ELSE 0 END) AS mtx2,
        SUM(CASE WHEN s.opll_mtrx_keyid = 'MTX0000003' THEN 1 ELSE 0 END) AS mtx3,
        SUM(CASE WHEN s.opll_mtrx_keyid = 'MTX0000004' THEN 1 ELSE 0 END) AS mtx4,

        MAX(CASE WHEN s.opll_mtrx_keyid = 'MTX0000001' THEN s.opll_keyid END) AS keyid1,
        MAX(CASE WHEN s.opll_mtrx_keyid = 'MTX0000002' THEN s.opll_keyid END) AS keyid2,
        MAX(CASE WHEN s.opll_mtrx_keyid = 'MTX0000003' THEN s.opll_keyid END) AS keyid3,
        MAX(CASE WHEN s.opll_mtrx_keyid = 'MTX0000004' THEN s.opll_keyid END) AS keyid4,

        -- ✅ NEW: dates for keyid3 / keyid4 (so JSP can calculate days)
        MAX(CASE WHEN s.opll_mtrx_keyid = 'MTX0000003' THEN s.opll_date END) AS dt3_date,
        MAX(CASE WHEN s.opll_mtrx_keyid = 'MTX0000004' THEN s.opll_date END) AS dt4_date,

        MAX(s.opll_date) AS opll_date
        
    FROM opl_tl_student s
    WHERE
        (COALESCE(NULLIF(:oplKeyid,''), NULLIF(:oplId,'')) IS NULL)
        OR s.opll_oplm_keyid = COALESCE(NULLIF(:oplKeyid,''), NULLIF(:oplId,''))
    GROUP BY s.opll_oplm_keyid, s.opll_teacher, s.opll_student
)

SELECT
    q.col1, q.col2, q.col3, q.opll_date, q.decode_like,
    q.studentname, q.studentid,
    q.mtx1, q.keyid1, q.chk1,
    q.mtx2, q.keyid2, q.chk2,
    q.mtx3, q.keyid3, q.chk3,
    q.dtevaldte3, q.chkval3,
    q.mtx4, q.keyid4, q.chk4,
    q.dtevaldte4, q.chkval4,
    q.opllteacher, q.opll_date2,
    -- ✅ NEW (added at end to avoid breaking your existing indexes)
    q.dt3, q.dt4
FROM (
    -- ✅ row0 = column order mapping (like MOM)
    SELECT
        0 AS ord,
        '1'  AS col1,
        '2'  AS col2,
        '3'  AS col3,
        '4'  AS opll_date,
        '5'  AS decode_like,
        '6'  AS studentname,
        '7'  AS studentid,
        '8'  AS mtx1,
        '9'  AS keyid1,
        '10' AS chk1,
        '11' AS mtx2,
        '12' AS keyid2,
        '13' AS chk2,
        '14' AS mtx3,
        '15' AS keyid3,
        '16' AS chk3,
        '17' AS dtevaldte3,
        '18' AS chkval3,
        '19' AS mtx4,
        '20' AS keyid4,
        '21' AS chk4,
        '22' AS dtevaldte4,
        '23' AS chkval4,
        '24' AS opllteacher,
        '25' AS opll_date2,
        -- ✅ NEW mapping numbers
        '26' AS dt3,
        '27' AS dt4,
        NULL::text AS sort_oplmkeyid

    UNION ALL

    -- ✅ actual data rows
    SELECT
        1 AS ord,
        '' AS col1,
        '' AS col2,
        '' AS col3,
        to_char(a.opll_date,'DD-Mon-YYYY') AS opll_date,
        CASE
            WHEN (:oplKeyid IS NOT NULL AND :oplKeyid <> '' AND a.oplmkeyid = :oplKeyid) THEN e.empm_name
            ELSE ''
        END AS decode_like,
        (e.empm_name || '-' || e.empm_code) AS studentname,
        e.empm_keyid AS studentid,
        COALESCE(a.mtx1,0)::text AS mtx1,
        COALESCE(a.keyid1,'') AS keyid1,
        '' AS chk1,
        COALESCE(a.mtx2,0)::text AS mtx2,
        COALESCE(a.keyid2,'') AS keyid2,
        '' AS chk2,
        COALESCE(a.mtx3,0)::text AS mtx3,
        COALESCE(a.keyid3,'') AS keyid3,
        '' AS chk3,
        '' AS dtevaldte3,
        '' AS chkval3,
        COALESCE(a.mtx4,0)::text AS mtx4,
        COALESCE(a.keyid4,'') AS keyid4,
        '' AS chk4,
        '' AS dtevaldte4,
        '' AS chkval4,
        COALESCE(a.opll_teacher, u.usrm_ccno) AS opllteacher,
        to_char(a.opll_date,'DD-Mon-YYYY') AS opll_date2,
        -- ✅ NEW actual values
        to_char(a.dt3_date,'DD-Mon-YYYY') AS dt3,
        to_char(a.dt4_date,'DD-Mon-YYYY') AS dt4,
        a.oplmkeyid AS sort_oplmkeyid
    FROM gen_tl_employeemst e
    JOIN frt ON e.empm_keyid = frt.frt_empm_keyid
    LEFT JOIN adm_tl_usermst u ON e.empm_keyid = u.usrm_ccno
    LEFT JOIN agg a
      ON a.opll_student = e.empm_keyid
     AND (COALESCE(NULLIF(:oplKeyid,''), NULLIF(:oplId,'')) IS NULL
          OR a.oplmkeyid = COALESCE(NULLIF(:oplKeyid,''), NULLIF(:oplId,'')))
    WHERE e.empm_active='Y'
) q
ORDER BY q.ord, q.sort_oplmkeyid NULLS LAST
""", nativeQuery = true)
List<Map<String, Object>> recallOplStudents(
    @Param("oplId") String oplId,
    @Param("cellId") String cellId,
    @Param("oplKeyid") String oplKeyid
);


}
