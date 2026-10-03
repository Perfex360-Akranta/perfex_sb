package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_PlmTlStandards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlmTlStandardsRepository extends JpaRepository<BAL_PlmTlStandards, String> {
}
