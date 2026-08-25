package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.FieldAuditSheetmst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldAuditSheetmstRepository extends JpaRepository<FieldAuditSheetmst, String> {
}