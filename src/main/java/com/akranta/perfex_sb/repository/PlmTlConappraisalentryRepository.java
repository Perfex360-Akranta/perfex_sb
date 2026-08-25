package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.PlmTlConappraisalentry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlmTlConappraisalentryRepository extends JpaRepository<PlmTlConappraisalentry, String> {
}