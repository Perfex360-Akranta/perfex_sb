package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.PcsTlLosscapture;

public interface PcsEntryRepository extends Repository<PcsTlLosscapture, String> {

    @Query(value = """
SELECT
  q.flid, q.plossdate, q.dmt, q.jh, q.shiftid, q.totalcnt
FROM (
    -- ✅ row0 = column order mapping
    SELECT
      0 AS ord,
      '1' AS flid,
      '2' AS plossdate,
      '3' AS dmt,
      '4' AS jh,
      '5' AS shiftid,
      '6' AS totalcnt,
      NULL::timestamp AS sort_plos_date

    UNION ALL

    -- ✅ data rows
    SELECT
      1 AS ord,
      x.flid AS flid,
      to_char(x.plos_date::date, 'DD-MON-YYYY') AS plossdate,
      x.dmt AS dmt,
      x.jh AS jh,
      x.shiftid AS shiftid,
      COUNT(*)::text AS totalcnt,
      MAX(x.plos_date) AS sort_plos_date
    FROM (
        SELECT
          h.flid AS flid,
          l.plos_date AS plos_date,
          split_part(h.parents, '/', 4) AS dmt,
          coalesce(array_to_string((string_to_array(h.parents,'/'))[5:], '/'), '') AS jh,
          l.plos_shiftid AS shiftid
        FROM pcs_tl_losscapture l
        JOIN gen_mv_flidhierarchy h ON l.plos_flid = h.flid
        WHERE position(:flid in (h.parentflids || '/' || h.flid)) > 0
          AND l.plos_date::date >= to_date(:fromDate,'DD-MON-YYYY')
          AND l.plos_date::date <= to_date(:toDate,'DD-MON-YYYY')
    ) x
    GROUP BY x.flid, to_char(x.plos_date::date, 'DD-MON-YYYY'), x.dmt, x.jh, x.shiftid
) q
ORDER BY q.ord, q.sort_plos_date DESC NULLS LAST
""", nativeQuery = true)
    List<Map<String, Object>> getLossEntryGrid(
        @Param("flid") String flid,
        @Param("fromDate") String fromDate,
        @Param("toDate") String toDate
    );

    @Query(value = """
WITH params AS (
    SELECT
        CASE
            WHEN trim(:fromDate) ~ '^[0-9]{4}-[0-9]{2}-[0-9]{2}$' THEN to_date(trim(:fromDate), 'YYYY-MM-DD')
            ELSE to_date(upper(trim(:fromDate)), 'FMDD-MON-YYYY')
        END AS fdt,
        CASE
            WHEN trim(:toDate) ~ '^[0-9]{4}-[0-9]{2}-[0-9]{2}$' THEN to_date(trim(:toDate), 'YYYY-MM-DD')
            ELSE to_date(upper(trim(:toDate)), 'FMDD-MON-YYYY')
        END AS tdt,
        CAST(:flid AS text) AS flid_param,
        nullif(:shiftId, '') AS shift_param
)
SELECT *
FROM (
    -- Header row
    SELECT
        'PldetailsId'::text        AS pldetailsid,
        'Keyid'::text              AS keyid,
        'From Date'::text          AS fromdate,
        'From Time'::text          AS fromtime,
        'To Date'::text            AS todate,
        'To Time'::text            AS totime,
        'Hours'::text              AS hours,
        'LossId'::text             AS lossid,
        'Loss Ref.No'::text        AS lossrefno,
        'LossReasonId'::text       AS lossreasonid,
        'Loss Reason'::text        AS lossreason,
        'Loss Description'::text   AS lossdescription,
        'TradeId'::text            AS tradeid,
        'Trade'::text              AS trade,
        'Prodn. Impact'::text      AS prodnimpact,
        'Prodn.Imp.Qty(Other then Downtime)'::text AS prodimpqty,
        'Equipment Name'::text     AS equipmentname,
        'Detected By'::text        AS detectedby,
        ' Why-Why'::text           AS whywhy,
        'Why-Why Ref No'::text     AS whywhyrefno,
        'Kaizen Ideas'::text       AS kaizenideas,
        'Kaizen Ref No.'::text     AS kaizenrefno,
        'Action Plan'::text        AS actionplan,
        'Action plan No.'::text    AS actionplanno,
        'Equipemntid'::text        AS equipmentid,
        'Employeeid'::text         AS employeeid,
        0 AS dataorder

    UNION ALL

    -- Data rows
    SELECT
        pl.plos_pldetailsid::text AS pldetailsid,
        pl.plos_keyid::text       AS keyid,
        to_char(pl.plos_fromtime, 'DD-Mon-YYYY')::text AS fromdate,
        to_char(pl.plos_fromtime, 'HH24:MI')::text     AS fromtime,
        to_char(pl.plos_totime,   'DD-Mon-YYYY')::text AS todate,
        to_char(pl.plos_totime,   'HH24:MI')::text     AS totime,
        ( floor(pl.plos_losstime::numeric / 60)::int::text
          || ':' || lpad(mod(pl.plos_losstime::numeric, 60)::int::text, 2, '0') )::text AS hours,
        pl.plos_lossid::text      AS lossid,
        lc.plcm_lossno::text      AS lossrefno,
        pl.plos_lossreason::text  AS lossreasonid,
        lp.plpm_name::text        AS lossreason,
        pl.plos_lossdescription::text AS lossdescription,
        pl.plos_tradeid::text     AS tradeid,
        tr.trdm_name::text        AS trade,
        pl.plos_prod_impact::text AS prodnimpact,
        (CASE WHEN pl.plos_prod_imp_qty = 0 THEN NULL ELSE pl.plos_prod_imp_qty::text END) AS prodimpqty,
        eq.ple_name::text         AS equipmentname,
        (coalesce(em.empm_name,'') || '-' || coalesce(em.empm_code,''))::text AS detectedby,
        ''::text AS whywhy,
        (SELECT string_agg(w.wwms_keyid::text, ',' ORDER BY w.wwms_keyid)
           FROM bdm_tl_whywhymst w
          WHERE w.wwms_refdocno NOT IN ('-','{}')
            AND w.wwms_refdocno = pl.plos_keyid
            AND w.wwms_refdoctype = 'PCS') AS whywhyrefno,
        ''::text AS kaizenideas,
        (SELECT string_agg(k.kzbn_keyid::text, ',' ORDER BY k.kzbn_keyid)
           FROM kzn_tl_kaizenbankmst k
          WHERE k.kzbn_refdocno <> '-'
            AND k.kzbn_refdocno = pl.plos_keyid) AS kaizenrefno,
        ''::text AS actionplan,
        (SELECT string_agg(a.aplm_keyid::text, ',' ORDER BY a.aplm_keyid)
           FROM gen_tl_actionplanmst a
          WHERE a.aplm_masterrefid NOT IN ('-','{}')
            AND a.aplm_masterrefid = pl.plos_keyid) AS actionplanno,
        pl.plos_equipment::text   AS equipmentid,
        pl.plos_detectedby::text  AS employeeid,
        1 AS dataorder
    FROM pcs_tl_losscapture pl
    JOIN pcs_tl_lossphenomenamst lp ON lp.plpm_keyid = pl.plos_lossreason
    JOIN pcs_tl_logconfiguration lc ON lc.plcm_keyid = pl.plos_lossid
    JOIN gen_tl_trademst tr ON tr.trdm_keyid = pl.plos_tradeid
    LEFT JOIN pcs_tl_equipment eq ON eq.ple_keyid = pl.plos_equipment
    LEFT JOIN gen_tl_employeemst em ON em.empm_keyid = pl.plos_detectedby
    CROSS JOIN params p
    WHERE pl.plos_totime::date BETWEEN LEAST(p.fdt, p.tdt) AND GREATEST(p.fdt, p.tdt)
      AND pl.plos_flid = p.flid_param
) t
ORDER BY t.dataorder
""", nativeQuery = true)
    List<Map<String, Object>> getPcsLossCaptureGrid(
        @Param("flid") String flid,
        @Param("fromDate") String fromDate,
        @Param("toDate") String toDate,
        @Param("shiftId") String shiftId
    );
}
