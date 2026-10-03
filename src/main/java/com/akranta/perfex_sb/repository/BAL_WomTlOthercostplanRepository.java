package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlOthercostplan;
import com.akranta.perfex_sb.model.BAL_WomTlOthercostplanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_WomTlOthercostplanRepository
        extends JpaRepository<BAL_WomTlOthercostplan, BAL_WomTlOthercostplanId> {
                // =========================================================================
    //  NATIVE DELETE  –  mirrors legacy BAL_WomTlOthercostplanSql.getDeleteSql()
    //  Returns the number of rows deleted (0 = nothing matched the key).
    // =========================================================================
    @Modifying
    @Query(value = "DELETE FROM wom_tl_othercostplan "
            + "WHERE otcp_woid = :woid AND otcp_requestedby = :requestedby AND otcp_othercostmstid = :othercostmstid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("woid") String woid,
                              @Param("requestedby") String requestedby,
                              @Param("othercostmstid") String othercostmstid);
}