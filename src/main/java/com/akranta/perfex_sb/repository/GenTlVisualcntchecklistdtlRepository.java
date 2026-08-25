package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.GenTlVisualcntchecklistdtl;

import jakarta.transaction.Transactional;

import java.util.List;


public interface GenTlVisualcntchecklistdtlRepository extends JpaRepository<GenTlVisualcntchecklistdtl, String> {
    
    // Find all detail records by master keyid
    List<GenTlVisualcntchecklistdtl> findBykeyid(String vcclkeyid);

    //checking whether the key is in abnormality
    @Query(value = """
        SELECT COUNT(*)
        FROM abn_tl_abnormality
        WHERE abnm_refdocid IN (
            SELECT vcdt_keyid
            FROM gen_tl_visualcntchecklistdtl
            WHERE vcdt_vccl_keyid = :vcclKeyid
        )
        """, nativeQuery = true)
int checkAbnormalityExists(@Param("vcclKeyid") String vcclKeyid);

    
    //delete detail record
@Modifying
@Transactional
@Query(
    value = "DELETE FROM gen_tl_visualcntchecklistdtl WHERE vcdt_vccl_keyid = :masterkeyid",
    nativeQuery = true
)
int deleteChecklistDetails(@Param("masterkeyid") String masterkeyid);
}