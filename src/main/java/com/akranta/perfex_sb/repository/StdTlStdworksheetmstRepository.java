package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.StdTlStdworksheetmst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StdTlStdworksheetmstRepository extends JpaRepository<StdTlStdworksheetmst, String> {
}