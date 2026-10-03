package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostactual;
import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostactualId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_WomTlUtilitycostactualRepository
        extends JpaRepository<BAL_WomTlUtilitycostactual, BAL_WomTlUtilitycostactualId> {
                // =========================================================================
    //  NATIVE DELETE  –  mirrors legacy BAL_WomTlUtilitycostactualSql.getDeleteSql()
    //  Returns the number of rows deleted (0 = nothing matched the key).
    // =========================================================================
    @Modifying
    @Query(value = "DELETE FROM wom_tl_utilitycostactual "
            + "WHERE utca_wokeyid = :wokeyid AND utca_requestedby = :requestedby AND utca_utilitymstid = :utilitymstid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("wokeyid") String wokeyid,
                              @Param("requestedby") String requestedby,
                              @Param("utilitymstid") String utilitymstid);
}