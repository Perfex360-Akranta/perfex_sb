package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.FieldAuditSheetdtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FieldAuditSheetdtlRepository extends JpaRepository<FieldAuditSheetdtl, String> {
    
    /**
     * Find all details by master keyid
     */
    List<FieldAuditSheetdtl> findByMasterid(String masterid);
}