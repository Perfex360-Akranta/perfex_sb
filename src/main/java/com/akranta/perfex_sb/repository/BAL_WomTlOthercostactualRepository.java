package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlOthercostactual;
import com.akranta.perfex_sb.model.BAL_WomTlOthercostactualId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_WomTlOthercostactualRepository
        extends JpaRepository<BAL_WomTlOthercostactual, BAL_WomTlOthercostactualId> {
                // =========================================================================
    //  NATIVE DELETE  –  mirrors legacy BAL_WomTlOthercostactualSql.getDeleteSql()
    //  Returns the number of rows deleted (0 = nothing matched the key).
    // =========================================================================
    @Modifying
    @Query(value = "DELETE FROM wom_tl_othercostactual "
            + "WHERE otcd_woid = :woid AND otcd_requestedby = :requestedby AND otcd_othercostmstid = :othercostmstid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("woid") String woid,
                              @Param("requestedby") String requestedby,
                              @Param("othercostmstid") String othercostmstid);
}