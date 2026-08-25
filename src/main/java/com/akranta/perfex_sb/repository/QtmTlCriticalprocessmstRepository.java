package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.QtmTlCriticalprocessmst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QtmTlCriticalprocessmstRepository extends JpaRepository<QtmTlCriticalprocessmst, String> {
    
    @Query(value = "SELECT * FROM qtm_tl_criticalprocessmst WHERE crpp_keyid = :keyid", 
           nativeQuery = true)
    QtmTlCriticalprocessmst findByKeyid(@Param("keyid") String keyid);

    @Modifying
    @Query(value = "DELETE FROM qtm_tl_criticalprocessdtl WHERE crpd_crpp_keyid = :masterId", 
           nativeQuery = true)
    int deleteAllDtl(@Param("masterId") String masterId);
    
    // Delete master record
    @Modifying
    @Query(value = "DELETE FROM qtm_tl_criticalprocessmst WHERE crpp_keyid = :masterId", 
           nativeQuery = true)
    int deleteMaster(@Param("masterId") String masterId);
}