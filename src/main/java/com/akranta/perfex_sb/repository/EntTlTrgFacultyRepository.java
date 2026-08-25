package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.EntTlTrgFaculty;

public interface EntTlTrgFacultyRepository extends JpaRepository<EntTlTrgFaculty, String> {

    List<EntTlTrgFaculty> findByEtcfEtcmKeyid(String etcmKeyid);

    void deleteByEtcfEtcmKeyid(String etcmKeyid);

    boolean existsByEtcfEtcmKeyid(String etcmKeyid);

    long countByEtcfEtcmKeyid(String etcmKeyid);

    @Query(value = """
            select '' as col0,
                   ftym_empm_keyid,
                   etcf_keyid,
                   ftym_name as faculty,
                   '' as deletecol
              from ent_tl_trgfaculty f
              join ent_tl_facultymst m on f.etcf_facultyid = m.ftym_keyid
              join ent_tl_trgcalmst c on f.etcf_etcm_keyid = c.etcm_keyid
             where c.etcm_keyid = :etcmKeyid
            """, nativeQuery = true)
    List<Object[]> findFacultyGrid(@Param("etcmKeyid") String etcmKeyid);
}
