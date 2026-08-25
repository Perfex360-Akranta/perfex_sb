package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.GenTlVisitors;

public interface GenTlVisitorRepository extends JpaRepository<GenTlVisitors, String> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GEN_TL_VISITORS WHERE VISI_KEYID = :visiKeyId", nativeQuery = true)
    int deleteByVisiKeyId(@Param("visiKeyId") String visiKeyId);

}
