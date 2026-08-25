package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.akranta.perfex_sb.model.PlmtlEquipmentfmeaDTL;
import jakarta.transaction.Transactional;

@Repository
public interface PlmEquipmentfmeaDtlRepository extends JpaRepository<PlmtlEquipmentfmeaDTL, String> {

    // List<PlmtlEquipmentfmeaDTL> findByFmeq_keyid(String keyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM plm_tl_equipmentfmeadtl WHERE fmed_keyid = :keyId", nativeQuery = true)
    int deleteByKeyId(@Param("keyId") String keyId);

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE plm_tl_equipmentfmeadtl
                SET
                    fmed_reseverity_keyid = '{}',
                    fmed_reoccurrence_keyid = '{}',
                    fmed_redetection_keyid = '{}',
                    fmed_rerpn = 0,
                    fmed_reviewby = '{}',
                    fmed_redate = CURRENT_TIMESTAMP
                WHERE fmed_keyid = :keyId
            """, nativeQuery = true)
    int updateReviewByKeyId(@Param("keyId") String keyId);
    

    @Query("SELECT COUNT(p) FROM PlmtlEquipmentfmeaDTL p WHERE p.fmeq_keyid = :mstKeyId")
    long countByMstKeyId(@Param("mstKeyId") String mstKeyId);

}
