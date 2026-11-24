package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BAL_PcsLossCapture;

public interface BAL_PcsLossCaptureRepository extends JpaRepository <BAL_PcsLossCapture,String>{
@Query(value = "SELECT to_char(sftm_starttime,'HH24:MI') FROM gen_tl_shiftmst WHERE sftm_keyid = :shiftId",
           nativeQuery = true)
    String findShiftStartTime(@Param("shiftId") String shiftId);

    @Query(value = """
        WITH p AS (
          SELECT s.sftm_keyid,
                 (CURRENT_DATE + CAST(:shiftStart AS time)) AS base_ts,
                 extract(hour FROM s.sftm_duration) * 60 + extract(minute FROM s.sftm_duration) AS assettime,
                 CAST(ceil(extract(epoch FROM (CAST(s.sftm_endtime AS time) - CAST(s.sftm_starttime AS time)
                      + CASE WHEN CAST(s.sftm_endtime AS time) <= CAST(s.sftm_starttime AS time)
                             THEN INTERVAL '24 hours' ELSE INTERVAL '0' END)) / 3600) AS int) AS nhours
          FROM gen_tl_shiftmst s
          WHERE s.sftm_keyid = :shiftId
            AND s.sftm_shiftorder <= (SELECT sftm_shiftorder FROM gen_tl_shiftmst WHERE sftm_keyid = :shiftId)
        ),
        a AS (
          SELECT p.sftm_keyid,
                 p.base_ts + (g.n - 1) * INTERVAL '1 hour' AS starttime,
                 p.base_ts + g.n * INTERVAL '1 hour' AS endtime,
                 g.n AS hournum,
                 GREATEST(0, LEAST(60, p.assettime - 60 * (g.n - 1))) AS shiftime
          FROM p CROSS JOIN LATERAL generate_series(1, p.nhours) AS g(n)
        )
        SELECT a.sftm_keyid, to_char(a.starttime,'HH24:MI'), to_char(a.endtime,'HH24:MI'),
               a.hournum, CAST(a.shiftime AS int)
        FROM a,
             (SELECT min(starttime) AS minstarttime FROM a) mn,
             (SELECT max(endtime) AS maxendtime FROM a) mx
        WHERE (
          ( a.starttime >= (SELECT max(starttime) FROM a WHERE to_char(mn.minstarttime,'YYYYMMDD') || :f >= to_char(starttime,'YYYYMMDDHH24MI'))
            AND a.endtime <= (SELECT min(endtime) FROM a WHERE to_char(starttime,'YYYYMMDD') || :t <= to_char(endtime,'YYYYMMDDHH24MI')) )
          OR ( a.starttime >= (SELECT max(starttime) FROM a WHERE to_char(mn.minstarttime,'YYYYMMDD') || :f >= to_char(starttime,'YYYYMMDDHH24MI'))
            AND a.endtime <= (SELECT min(endtime) FROM a WHERE to_char(mx.maxendtime,'YYYYMMDD') || :t <= to_char(endtime,'YYYYMMDDHH24MI')) )
          OR ( a.starttime >= (SELECT max(starttime) FROM a WHERE to_char(mn.minstarttime,'YYYYMMDD') || :f >= to_char(starttime,'YYYYMMDDHH24MI'))
            AND a.endtime <= (SELECT min(endtime) FROM a WHERE to_char(endtime,'YYYYMMDD') || :t <= to_char(endtime,'YYYYMMDDHH24MI')) )
        )
        ORDER BY a.hournum
        """, nativeQuery = true)
    List<Object[]> findLossHours(@Param("shiftId") String shiftId,
                                 @Param("shiftStart") String shiftStart,
                                 @Param("f") String from,
                                 @Param("t") String to);

//       @Query(value = """
//     SELECT TO_CHAR(sftm_starttime, 'HH24:MI')
//     FROM gen_tl_shiftmst
//     WHERE sftm_keyid = :shiftId
//     """, nativeQuery = true)
// String findShiftStartTime(@Param("shiftId") String shiftId);                           

}
