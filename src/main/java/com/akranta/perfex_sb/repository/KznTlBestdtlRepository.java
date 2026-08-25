package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.KznTlBestdtl;

public interface KznTlBestdtlRepository extends JpaRepository<KznTlBestdtl, String> {

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM kzn_tl_bestdtl
            WHERE kzbd_kzbm_keyid = :kzbm_keyid
            """, nativeQuery = true)
    void deleteByKzbm_keyid(@Param("kzbm_keyid") String kzbm_keyid);
}
