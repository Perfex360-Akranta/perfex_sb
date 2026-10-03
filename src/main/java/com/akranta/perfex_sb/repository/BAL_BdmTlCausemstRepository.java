package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BAL_BdmTlCausemst;

public interface BAL_BdmTlCausemstRepository extends JpaRepository<BAL_BdmTlCausemst, String>
{
    @Modifying
    @Query(
        value = """
            UPDATE bal_bdm_tl_phncauselink
            SET bpcl_active = 'N'
            WHERE bpcl_elementid = :causeId
            AND bpcl_elementtype = 'CSE'
            """,
        nativeQuery = true
    )
    void inactivatePhncauselinkByElementId(@Param("causeId") String causeId);
}