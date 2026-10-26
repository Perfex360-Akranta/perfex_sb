package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_CliStandardsDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CliStandardsDtlRepository extends JpaRepository<BAL_CliStandardsDtl, String> {

    // clisdr_keyid has a unique index, so at most one detail row per parent standard.
    // Returns null when no detail row exists yet.
    BAL_CliStandardsDtl findByStandardsKeyid(String standardsKeyid);
}