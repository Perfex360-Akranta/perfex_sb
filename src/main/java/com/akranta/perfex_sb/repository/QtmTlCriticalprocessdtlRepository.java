package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.QtmTlCriticalprocessdtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QtmTlCriticalprocessdtlRepository extends JpaRepository<QtmTlCriticalprocessdtl, String> {
    
    /**
     * Delete Critical Process detail by keyid
     * @param detailId - The CRPD_KEYID to delete
     * @return number of rows affected
     */
    @Modifying
    @Query(value = "DELETE FROM qtm_tl_criticalprocessdtl WHERE crpd_keyid = :detailId", 
           nativeQuery = true)
    int deleteCriticalProcessDetail(@Param("detailId") String detailId);

    
}