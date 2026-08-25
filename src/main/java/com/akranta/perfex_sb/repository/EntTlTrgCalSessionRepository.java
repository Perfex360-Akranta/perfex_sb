package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.EntTlTrgCalSession;

public interface EntTlTrgCalSessionRepository extends JpaRepository<EntTlTrgCalSession, String> {

       List<EntTlTrgCalSession> findByEtcsEtcmKeyid(String etcmKeyid);

    void deleteByEtcsEtcmKeyid(String etcmKeyid);

    long countByEtcsEtcmKeyid(String etcmKeyid);

    @Query(value = """
        select count(*)
        from ent_tl_trgcalsession
        where etcs_etcm_keyid = :etcmKeyid
          and to_char(etcs_sessiondate, 'DD-Mon-YYYY') = :sessionDate
          and to_char(etcs_fromdate,    'DD-Mon-YYYY HH24:MI') = :fromDateTime
          and to_char(etcs_tilldate,    'DD-Mon-YYYY HH24:MI') = :toDateTime
          and (:sessionId is null or :sessionId = '' or etcs_keyid <> :sessionId)
        """, nativeQuery = true)
    int countExactSessionSlot(@Param("etcmKeyid") String etcmKeyid,
                              @Param("sessionDate") String sessionDate,
                              @Param("fromDateTime") String fromDateTime,
                              @Param("toDateTime") String toDateTime,
                              @Param("sessionId") String sessionId);

    @Query(value = """
            select
                s.etcs_keyid,
                s.etcs_name,
                to_char(s.etcs_sessiondate, 'DD-Mon-YYYY') as session,
                to_char(s.etcs_fromdate,    'HH24:MI')     as from_time,
                to_char(s.etcs_tilldate,    'HH24:MI')     as to_time,
                '' as delete_col
            from ent_tl_trgcalsession s
            join ent_tl_trgcalmst m on s.etcs_etcm_keyid = m.etcm_keyid
            where s.etcs_etcm_keyid = :etcmKeyid
            order by s.etcs_keyid, s.etcs_name
            """, nativeQuery = true)
    List<Object[]> findSessionGrid(@Param("etcmKeyid") String etcmKeyid);
}
