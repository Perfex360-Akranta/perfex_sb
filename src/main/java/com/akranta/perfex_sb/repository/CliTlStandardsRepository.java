package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.CliTlStandards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CliTlStandardsRepository extends JpaRepository<CliTlStandards, String> {

    // Removes calendar rows that reference the standard (FK fk_clca_clirefid).
    // Returns the number of rows deleted.
    @Modifying
    @Query(value = "DELETE FROM public.bal_cli_tl_calendar WHERE clca_clirefid = :keyid", nativeQuery = true)
    int deleteCalendarByStandardKeyid(@Param("keyid") String keyid);

}