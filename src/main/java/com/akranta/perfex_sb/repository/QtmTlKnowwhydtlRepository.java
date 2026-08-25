package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.QtmTlKnowwhydtl;

public interface QtmTlKnowwhydtlRepository extends JpaRepository<QtmTlKnowwhydtl, String> {

    @Modifying
    @Transactional
    @Query(value = """
                DELETE FROM QTM_TL_KNOWWHYDTL
                WHERE KNWD_KNWM_KEYID = :keyId
            """, nativeQuery = true)
    int deleteByMasterKeyId(@Param("keyId") String keyId);

}
